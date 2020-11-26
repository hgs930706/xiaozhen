package com.yishang.xiaozhen.controller;

import com.yishang.xiaozhen.service.IndexServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页统计，接口
 */
@RestController
@RequestMapping("/index")
public class IndexController {


    @Autowired
    private IndexServiceImpl indexServiceImpl;

    /**
     * 用户总数、今日新增用户、本周接待人数
     * @return
     */
    @GetMapping("/userSum")
    public ResultUtil userSum(){
        return indexServiceImpl.userSum();
    }

    /**
     * 今日待接待
     * @return
     */
    @GetMapping("/waitReceive")
    public ResultUtil waitReceive(){

        return indexServiceImpl.waitReceive();
    }


    /**
     * 全部、预约待处理,返回的百分比
     * @return
     */
    @GetMapping("/waitHandleBooking")
    public ResultUtil bookingWaitHandle(){
        return indexServiceImpl.bookingWaitHandle();
    }


    /**
     * 预约一周的统计
     * @return
     */
    @GetMapping("/bookingWeekCount")
    public ResultUtil bookingWeekCount(){


        return indexServiceImpl.bookingWeekCount();
    }


}
