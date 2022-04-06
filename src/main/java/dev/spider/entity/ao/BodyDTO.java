package dev.spider.entity.ao;

import lombok.Data;

import java.io.Serializable;

@Data
public class BodyDTO<T> implements Serializable {
    private String appId;
    private String format = "json";
    private String requestId;
    private String timestamp;
    private String signType = "rsa2";
    private String encryptType = "";
    private String sign;
    private String bizContent;
}
