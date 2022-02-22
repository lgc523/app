package dev.spider.api;

import dev.spider.hook.valid.ValidGroup;
import dev.spider.entity.Result;
import dev.spider.entity.ao.FooValid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * alias pst='curl -H "Content-type:application/json"'
 */
@RestController
@RequestMapping("valid")
public class ValidController {

    /**
     * pst localhost:8080/valid/simple -d '{}' | json
     * {
     * "code": 400,
     * "msg": "der absent",
     * "data": null
     * }
     *
     * @param fooValid foo
     * @return result
     */
    @PostMapping("simple")
    @SuppressWarnings("rawtypes")
    public Result validS(@Valid @RequestBody FooValid fooValid) {
        return Result.success(fooValid);
    }

    /**
     * pst localhost:8080/valid/group -d '{"foo":"f","bars":[{"bar":""}]}' | json
     * {
     * "code": 400,
     * "msg": "bar absent",
     * "data": null
     * }
     *
     * @param fooValid foo
     * @return result
     */
    @PostMapping("group")
    @SuppressWarnings("rawtypes")
    public Result validGroup(@Validated(ValidGroup.BarGroup.class) @RequestBody FooValid fooValid) {
        return Result.success(fooValid);
    }

    /**
     * pst localhost:8080/valid/embed -d '{"foo":"f","bars":[{"bar":""}]}' | json
     * {
     * "code": 400,
     * "msg": "der absent",
     * "data": null
     * }
     *
     * @param fooValid foo
     * @return result
     */
    @PostMapping("embed")
    @SuppressWarnings("rawtypes")
    public Result validEmbed(@Valid @RequestBody FooValid fooValid) {
        return Result.success(fooValid);
    }

}
