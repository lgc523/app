package dev.spider.entity.ao;

import dev.spider.hook.valid.ValidGroup;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Valid
public class FooValid {
    @NotBlank(message = "foo absent", groups = ValidGroup.BarGroup.class)
    private String foo;
    @NotBlank(message = "der absent")
    private String der;
    @Valid
    private List<Bar> bars;

    @Data
    public static class Bar {
        @NotBlank(groups = ValidGroup.BarGroup.class, message = "bar absent")
        private String bar;
    }
}
