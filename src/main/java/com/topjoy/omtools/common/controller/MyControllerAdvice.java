package com.topjoy.omtools.common.controller;

import com.topjoy.omtools.common.entity.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局统一捕获异常类
 * 只要作用在@RequestMapping上的所有的类和方法，只要报错都能被捕获到。
 */
@ControllerAdvice
public class MyControllerAdvice {

    /**
     * 自定义系统异常类
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> errorHandler(Exception ex) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", -1);
        map.put("msg", ex.getMessage());
        return map;
    }

//    /**
//     * 用法 throw new MyException("100","账号密码错误")
//     * 自定义异常类
//     *
//     * @param ex
//     * @return
//     */
//    @ResponseBody
//    @ExceptionHandler(value = MyException.class)
//    public Map<String, Object> errorHandler(MyException ex) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("code", ex.getCode());
//        map.put("msg", ex.getMsg());
//        return map;
//    }


}
