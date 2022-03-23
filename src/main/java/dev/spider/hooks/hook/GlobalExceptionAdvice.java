package dev.spider.hooks.hook;

import com.google.common.base.Joiner;
import dev.spider.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {


    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<String> exceptionHandler(MethodArgumentNotValidException ex) {
        String err = Joiner.on(",").join(ex.getBindingResult().getFieldErrors().
                stream().map(FieldError::getDefaultMessage).collect(Collectors.toSet()));
        log.error("MethodArgumentNotValidException:\n{}", ex.getMessage());
        return Result.error(err);
    }
}
