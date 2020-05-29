package com.domain.demo.enums;

import com.domain.demo.util.ResultInterface;

/**
 * @Description Api返回结果码
 * @Classname RequestResultEnum
 * @Author CleverApe
 * @Date 2020-05-29 12:18
 * @Version V1.0
 */
public enum  RequestResultEnum implements ResultInterface {


    SUCCESS(0,"处理成功"),
    FAILD(999999,"处理失败"),

    //服务级别
    SERVER_EXP(100000,"服务异常，请稍后重试！"),
    SERVER_BUSY(100001,"SERVER BUSY, 请稍后重试！"),


    //应用级别
    ILLEGAL_ACCESS(200001,"非法访问"),
    PARAMETER_IS_NULL(200002,"参数为空"),
    PARAMETER_IS_ERROR(200003,"参数格式不正确");


    private int code;
    private String msg;

    RequestResultEnum(int code,String msg){
        this.code=code;
        this.msg=msg;
    }
    @Override
    public int getCode() {
        return code;
    }

    public RequestResultEnum setCode(int code) {
        this.code = code;
        return this;
    }
    @Override
    public String getMsg() {
        return msg;
    }

    public RequestResultEnum setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
