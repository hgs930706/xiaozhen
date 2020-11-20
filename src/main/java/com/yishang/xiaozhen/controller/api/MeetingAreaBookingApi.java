package com.yishang.xiaozhen.controller.api;


import com.yishang.xiaozhen.entity.MeetingAreaBooking;
import com.yishang.xiaozhen.service.MeetingAreaBookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 会议场地新增预约
     * @param object
     * @return
     */
    @PostMapping("/insert")
    public String insert(@RequestBody MeetingAreaBooking object){
        meetingAreaBookingServiceImpl.insert(object);
        return null;
    }


    @GetMapping("/list")
    public String list(){

        return null;
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(name = "id") String id){

        return null;
    }



}
