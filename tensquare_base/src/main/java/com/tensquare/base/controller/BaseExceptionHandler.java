package com.tensquare.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName BaseExceptionHandler
 * @Author admin
 * @Date 2019/11/11 14:41
 **/

@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result exception(Exception e){

        return new Result(false,StatusCode.ERROR,e.getMessage());
    }
}
