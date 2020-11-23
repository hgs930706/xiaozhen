package com.yishang.xiaozhen.controller.api;


import com.yishang.xiaozhen.entity.MeetingAreaBooking;
import com.yishang.xiaozhen.service.MeetingAreaBookingServiceImpl;
import com.yishang.xiaozhen.service.MeetingAreaServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 会议场地预约表 微信端
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/api/meetingAreaBooking")
public class MeetingAreaBookingApi {

    @Autowired
    private MeetingAreaBookingServiceImpl meetingAreaBookingServiceImpl;

    @Autowired
    private MeetingAreaServiceImpl meetingAreaServiceImpl;


    /**
     * 会议场地新增预约
     * @param object
     * @return
     */
    @PostMapping("/insert")
    public ResultUtil insert(@RequestBody MeetingAreaBooking object){
        ResultUtil insert = meetingAreaBookingServiceImpl.insert(object);
        return insert;
    }


    @GetMapping("/list")
    public Map<String,Object> list(Integer page,Integer size){
        Map<String,Object> map = meetingAreaServiceImpl.list(page,size);
        return map;
    }

    @GetMapping("/detail")
    public ResultUtil detail(@RequestParam(name = "id") String id){
        ResultUtil detail = meetingAreaServiceImpl.detail(id);
        return detail;
    }

    @GetMapping("/select")
    public ResultUtil select(@RequestParam(name = "id") String id){
        ResultUtil detail = meetingAreaServiceImpl.select(id);
        return detail;
    }



}
