package dev.spider.entity;

import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public static Result success() {
        Result<Object> out = new Result<>();
        out.setCode(200);
        out.setMsg("ok");
        return out;
    }

    public static Result success(Object data) {
        Result<Object> out = new Result<>();
        out.setCode(200);
        out.setData(data);
        out.setMsg("ok");
        return out;
    }

    public static Result<String> error(String msg) {
        Result<String> out = new Result<>();
        out.setCode(400);
        out.setMsg(msg);
        return out;
    }
}
