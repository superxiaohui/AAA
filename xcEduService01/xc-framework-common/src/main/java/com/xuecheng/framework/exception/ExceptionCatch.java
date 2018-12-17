package com.xuecheng.framework.exception;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 异常捕获类
 */
//注解--控制器增强器
@ControllerAdvice
public class ExceptionCatch {

    //引入日志
    private static final Logger LOGGER= LoggerFactory.getLogger(CustomException.class);

    //错误集合—————键为所有异常（继承了Throwable的类）--值为错误代码类
    protected static ImmutableMap<Class<? extends Throwable>,ResultCode> EXCEPTIONS;

    //创建错误码集合,此集合已经创建就只读且线程安全
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();

    //用注解指定捕获异常的类型--并且在捕获时将异常传入方法
    @ExceptionHandler(CustomException.class)
    @ResponseBody   //将返回json数据到前台界面
    public ResponseResult catchException(CustomException customException){

        //记录日志
        LOGGER.error(String.valueOf(new Date())+"--catch One Exception:"+customException.getResultCode()+customException.getMessage());

        //获取所捕获异常的错误代码
        ResultCode resultCode = customException.getResultCode();
        //返回异常信息--json数据到前台界面
        return new ResponseResult(resultCode);
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody   //将返回json数据到前台界面
    public ResponseResult catchError(Exception exception){
        //记录日志
        LOGGER.error(String.valueOf(new Date())+"--catch One Exception:"+exception.getMessage()+"Message："+Throwables.getStackTraceAsString(exception));


        //构建错误集合
        if(EXCEPTIONS==null){
            EXCEPTIONS = builder.build();
        }

        //获取当前错误的错误代码
        final ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        if (resultCode!=null){
            return new ResponseResult(resultCode);
        }
        //返回异常信息--json数据到前台界面
        return new ResponseResult(CommonCode.SERVER_ERROR);
    }

    static {
        //在这里加入一些基础的异常类型判断
        builder.put(HttpMessageNotReadableException.class,CommonCode.INVALID_PARAM);
    }


}
