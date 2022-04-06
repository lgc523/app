package dev.spider.api;

import dev.spider.annotation.SIProtection;
import dev.spider.entity.Result;
import dev.spider.entity.ao.BodyDTO;
import dev.spider.entity.ao.Foo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("secret")
@SuppressWarnings("rawtypes")
public class SecretController {


    @PostMapping("test")
    @SIProtection
    public Result secret(@RequestBody BodyDTO<Foo> req) {
        return Result.success(req);
    }
}
