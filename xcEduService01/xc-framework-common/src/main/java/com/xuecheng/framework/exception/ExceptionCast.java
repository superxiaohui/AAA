package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * 异常抛出类
 */
public class ExceptionCast {

    public static void cast(ResultCode resultCode){
        //抛出自定义异常的状态码
        throw new CustomException(resultCode);
    }
}
