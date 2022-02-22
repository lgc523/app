package dev.spider.entity.ao;

import lombok.Data;

import java.io.Serializable;

@Data
public class MockFoo implements Serializable {
    private String route;
    private String orderNo;
    private String amount;
}
