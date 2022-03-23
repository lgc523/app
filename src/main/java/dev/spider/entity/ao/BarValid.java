package dev.spider.entity.ao;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class BarValid implements Serializable {
    @NotBlank(message = "bar name absent")
    private String name;
    @Valid
    private Alpha alpha;

    @Data
    public static class Alpha implements Serializable {
        @NotBlank(message = "alpha name absent")
        String name;
    }
}


