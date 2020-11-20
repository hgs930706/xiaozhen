package com.yishang.xiaozhen.controller.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 字典表 微信端
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/api/dict")
public class DictApi {

    /**
     * 获取小镇，所有街区
     * @return
     */
    @GetMapping("/steertTypes")
    public String steertTypes(){

        return null;
    }

    /**
     * 获取参观预约简介
     * @return
     */
    @GetMapping("/visitBooking")
    public String visitBooking(){


        return null;
    }


    @GetMapping("/meetingParams")
    public String meetingParams(){
        //返回：会议类型、会议桌型，会议物品

        return null;
    }



}
