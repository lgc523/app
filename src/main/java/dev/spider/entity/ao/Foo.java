package dev.spider.entity.ao;

import dev.spider.hooks.valid.ValidGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Foo implements Serializable {
    @NotBlank(groups = ValidGroup.DefaultGroup.class, message = "name  absent")
    private String name;
    @NotBlank(groups = ValidGroup.FooGroup.class)
    private String route;
}
