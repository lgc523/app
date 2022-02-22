package dev.spider.api;

import dev.spider.entity.Result;
import dev.spider.service.TempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("aspect")
public class AspectController {

    @Autowired
    TempService tempService;

    @GetMapping("simple")
    public Result aspectSimple(@RequestParam(value = "val") String val) {
        return Result.success(val);
    }

    @GetMapping("annotation")
    public Result aspectAnnotation() {
        return tempService.foo();
    }
}
