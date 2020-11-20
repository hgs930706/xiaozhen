package com.yishang.xiaozhen.interceptor;

import com.yishang.xiaozhen.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultUtil exceptionHandler(Exception e){

        String uuid = UUID.randomUUID().toString();

        log.error("全局系统异常：{}",uuid,e);

       return ResultUtil.error(e.getMessage()+",logId="+uuid);
    }
}
