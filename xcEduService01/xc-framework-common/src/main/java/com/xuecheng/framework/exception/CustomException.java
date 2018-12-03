package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * 自定义异常类
 */
public class CustomException extends RuntimeException {

    private ResultCode resultCode;

    //设置自定义异常类的构造方法
    public CustomException(ResultCode resultCode){

        super("错误代码："+resultCode.code()+"错误信息："+resultCode.message());
        this.resultCode=resultCode;
    }
    //获取错误代码
    public ResultCode getResultCode(){
        return this.resultCode;
    }

}
