package com.br.demo.util;

import java.io.Serializable;

/**
 * @author guifei.qin
 * @Classname HttpClientResult
 * @Description Http请求结果对象
 * @Date 2019-07-17
 * @Version V1.0
 */
public class HttpClientResult implements Serializable {

    private static final long serialVersionUID = 342786581382218327L;

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应数据
     */
    private String content;

    public HttpClientResult() {
    }

    public HttpClientResult(int code) {
        this.code = code;
    }

    public HttpClientResult(String content) {
        this.content = content;
    }

    public HttpClientResult(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "HttpClientResult [code=" + code + ", content=" + content + "]";
    }

}
