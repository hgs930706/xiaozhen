package com.yishang.xiaozhen.controller.api;


import com.alibaba.excel.util.StringUtils;
import com.yishang.xiaozhen.entity.MeetingAreaBooking;
import com.yishang.xiaozhen.service.MeetingAreaBookingServiceImpl;
import com.yishang.xiaozhen.service.MeetingAreaServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
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
    public ResultUtil list(Integer page,Integer size){
        if (null == page || page <= 0) {
            page = 1;
        }
        if (null == size || size <= 0) {
            size = 5;
        }
        return  meetingAreaServiceImpl.list(page,size);
    }

    @GetMapping("/detail")
    public ResultUtil detail(String id){
        if(org.springframework.util.StringUtils.isEmpty(id)){
            return ResultUtil.error("id不能为空");
        }
        ResultUtil detail = meetingAreaServiceImpl.detail(id);
        return detail;
    }

    /**
     * 根据当前的会议，获得下拉框的值，物品，桌型，会议类型
     * @param id
     * @return
     */
    @GetMapping("/select")
    public ResultUtil select(String id){
        if(StringUtils.isEmpty(id)){
            return ResultUtil.error("id不能为空");
        }
        ResultUtil detail = meetingAreaServiceImpl.select(id);
        return detail;
    }



}
