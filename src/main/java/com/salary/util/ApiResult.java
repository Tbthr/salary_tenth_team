package com.salary.util;

public class ApiResult {
    public static final Integer STATUS_SUCCESS = 200;
    public static final Integer STATUS_FAILURE = 500;

    public static final String DESC_SUCCESS = "操作成功";
    public static final String DESC_FAILURE = "操作失败";

    private Integer code;
    private String msg;
    private Object data;

    private ApiResult() {}


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private ApiResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //这个方法和Builder设计模式二选一即可，功能是重复的
    public static ApiResult success(Object data) {
        return success(DESC_SUCCESS, data);
    }

    //同上
    public static ApiResult success(String msg, Object data) {
        return new ApiResult(STATUS_SUCCESS, msg, data);
    }

    //同上
    public static ApiResult failure(Integer code) {
        return failure(code, null);
    }

    //同上
    public static ApiResult failure(Integer code, String msg) {
        return failure(code, msg, null);
    }

    //同上
    public static ApiResult failure(Integer code, String msg, Object data) {
        return new ApiResult(code, msg, data);
    }

    public static Builder builder() {
        return new Builder();
    }

    //静态内部类，这里使用Builder设计模式
    public static class Builder {
        private Integer code;
        private String msg;
        private Object data;

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public ApiResult build() {
            return new ApiResult(code, msg, data);
        }
    }

}
