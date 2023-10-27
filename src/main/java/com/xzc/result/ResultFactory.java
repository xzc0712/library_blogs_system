package com.xzc.result;

public class ResultFactory {

    public static Result buildSuccessResult(Object data){
        return buildRealResult(ResultCode.SUCCESS,"成功",data);
    }

    public static Result buildFailResult(Object data){
        return buildRealResult(ResultCode.FAIL,"失败",null);
    }

    private static Result buildRealResult(ResultCode resultCode, String message, Object data) {
        return buildResult(resultCode.code, message, data);
    }

    private static Result buildResult(int resultCode, String message, Object data){
        return new Result(resultCode, message, data);
    }
}
