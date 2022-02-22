package dev.spider.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MockBar implements Serializable {
    private String orderNo;
    private String status;

    public MockBar(String orderNo, String status) {
        this.orderNo = orderNo;
        this.status = status;
    }
}
