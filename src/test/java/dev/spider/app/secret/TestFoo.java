package dev.spider.app.secret;

import com.alibaba.fastjson.JSON;
import dev.spider.App;
import dev.spider.entity.ao.BodyDTO;
import dev.spider.entity.ao.Foo;
import dev.spider.util.AES;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class TestFoo {

    @Test
    public void initBody() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        BodyDTO<Foo> bodyDTO = new BodyDTO<>();
        bodyDTO.setAppId("1");
        bodyDTO.setEncryptType("RSA");
        bodyDTO.setTimestamp(String.valueOf(System.currentTimeMillis()));

        Foo foo = new Foo();
        foo.setRoute("1.0");
        String s = Base64.getEncoder().encodeToString(JSON.toJSONString(foo).getBytes(StandardCharsets.UTF_8));
        String s1 = AES.aesEncrypt(s, AES.aesKey);
        bodyDTO.setBizContent(s1);
        System.out.println(JSON.toJSONString(bodyDTO));
    }
}
