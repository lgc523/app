package dev.spider.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("post")
public class FormController {

    @PostMapping("www")
    public String form(@RequestParam(value = "p")String p){
        return "String";
    }

}
