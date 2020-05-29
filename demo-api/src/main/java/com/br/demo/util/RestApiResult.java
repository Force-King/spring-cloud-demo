package com.br.demo.util;


import com.br.demo.enums.RequestResultEnum;

/**
 * @author CleverApe
 * @Classname RestApiResult
 * @Description api返回结果对象
 * @Date 2019-03-05 15:49
 * @Version V1.0
 */
public class RestApiResult<T> {

    //响应码
    private Integer code;
    //响应消息
    private String msg;
    //结果数据
    private T data;

    public static RestApiResult success() {
        return new RestApiResult(RequestResultEnum.SUCCESS.getCode(), RequestResultEnum.SUCCESS.getMsg(),null);
    }

    public static RestApiResult faild() {
        return new RestApiResult(RequestResultEnum.FAILD.getCode(), RequestResultEnum.FAILD.getMsg(),null);
    }

    public static RestApiResult build(Integer code, String msg) {
        return new RestApiResult(code, msg,null);
    }

    public static RestApiResult buildEnum(ResultInterface re) {
        return new RestApiResult(re.getCode(),re.getMsg(),null);
    }

    public RestApiResult success(T data) {
        return new RestApiResult(RequestResultEnum.SUCCESS.getCode(), RequestResultEnum.SUCCESS.getMsg(), data);
    }
    public RestApiResult build(Integer code, String msg, T data) {
        return new RestApiResult(code, msg,data);
    }

    public RestApiResult buildEnum(ResultInterface re, T data) {
        return new RestApiResult(re.getCode(),re.getMsg(), data);
    }

    public RestApiResult() {}
    public RestApiResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data=data;
    }

    public Integer getCode() {
        return code;
    }

    public RestApiResult setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RestApiResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public RestApiResult setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "{\"code\":" + code + ",\"msg\":\"" + msg + "\",\"data\":" + data + "}";
    }


}
