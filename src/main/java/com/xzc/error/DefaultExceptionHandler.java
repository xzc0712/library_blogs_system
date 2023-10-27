package com.xzc.error;

import com.xzc.result.Result;
import com.xzc.result.ResultFactory;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handlerAuthorizationException(UnauthenticatedException e){
        String msg = "权限认证失败";
        return ResultFactory.buildFailResult(msg);
    }
}
