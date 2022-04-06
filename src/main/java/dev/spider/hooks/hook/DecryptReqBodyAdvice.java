package dev.spider.hooks.hook;

import com.alibaba.fastjson.JSON;
import com.fasterxml.classmate.GenericType;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import dev.spider.annotation.SIProtection;
import dev.spider.configs.SecretProcess;
import dev.spider.entity.ao.BodyDTO;
import dev.spider.hooks.valid.ValidGroup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
@ConditionalOnProperty(name = "secret.enabled", havingValue = "true")
@SuppressWarnings("rawtypes")
@Slf4j
public class DecryptReqBodyAdvice extends RequestBodyAdviceAdapter {

    static final HashMap<String, Pair<Class[], String>> routeMap = new HashMap<>();

    static {
        //jft 1.0-b2c, 1.1-b2b
        routeMap.put("1.0", Pair.of(new Class[]{ValidGroup.FooGroup.class}, "jftService"));
        routeMap.put("1.1", Pair.of(new Class[]{ValidGroup.BarGroup.class}, "jftService"));
        routeMap.put("2", Pair.of(new Class[]{ValidGroup.DefaultGroup.class}, "hxService"));
    }

    @Resource
    private SecretProcess secretProcess;


    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(SIProtection.class)
                || methodParameter.getMethod().getDeclaringClass().isAnnotationPresent(SIProtection.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        String ins = StreamUtils.copyToString(inputMessage.getBody(), StandardCharsets.UTF_8);
        BodyDTO bodyDTO = JSON.parseObject(ins, BodyDTO.class);

        //base64 decode & decrypt
        String body = secretProcess.decrypt(bodyDTO.getBizContent());
        String biz = new String(Base64.getDecoder().decode(body.getBytes(StandardCharsets.UTF_8)));
        bodyDTO.setBizContent(biz);
        String plainBiz = JSON.toJSONString(bodyDTO);

        //reflect
        Type genericParameterType = parameter.getGenericParameterType();
        if (genericParameterType instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericParameterType).getActualTypeArguments();
            Type actualTypeArgument = actualTypeArguments[0];
            Object bizObj = JSON.parseObject(biz, actualTypeArgument);

            PropertyDescriptor pd;
            String routeVal = null;
            try {
                pd = new PropertyDescriptor("route", (Class<?>) actualTypeArgument);
                Method readMethod = pd.getReadMethod();
                routeVal = (String) readMethod.invoke(bizObj);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //valid & alert
            Pair<Class[], String> classPair = routeMap.get(routeVal);
            Class[] validGroupClassArr = classPair.getLeft();
            String serviceInvoke = classPair.getRight();

            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<Object>> allSet = Sets.newHashSet();
            for (Class aClass : validGroupClassArr) {
                Set<ConstraintViolation<Object>> validate = validator.validate(bizObj, aClass);
                allSet.addAll(validate);
            }
            if (!CollectionUtils.isEmpty(allSet)) {
                Set<String> validResult = allSet.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
                String validErrStr = Joiner.on(",").join(validResult);
                log.error("valid error:{}", validErrStr);
                throw new RuntimeException(validErrStr);
            }
        }
        return new HttpInputMessage() {
            @Override
            public HttpHeaders getHeaders() {
                return inputMessage.getHeaders();
            }

            @Override
            public InputStream getBody() {
                return new ByteArrayInputStream(plainBiz.getBytes());
            }
        };
    }

}