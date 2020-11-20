package com.yishang.xiaozhen.controller;


import com.alibaba.fastjson.JSONObject;
import com.yishang.xiaozhen.service.MeetingAreaBookingServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 会议场地预约表 前端控制器
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/meetingAreaBooking")
public class MeetingAreaBookingController {

    @Autowired
    private MeetingAreaBookingServiceImpl meetingAreaBookingServiceImpl;

    /**
     *
     * @param meetingName 会议场地名称
     * @param bookingStartTime 预约开始时间
     * @param bookingEndTime 预约结束时间
     * @param isStatus
     * @return
     */
    @GetMapping("/list")
    public ResultUtil list(Integer page,Integer size,String meetingName, String bookingStartTime, String bookingEndTime, Integer isStatus){

        Map<String, Object> list = meetingAreaBookingServiceImpl.list(page,size,meetingName, bookingStartTime, bookingEndTime, isStatus);
        return ResultUtil.success(list);
    }


    /**
     * 审批
     * @param jsonObject approvalStatus 1审批通过，2审批拒绝)
     * @return
     */
    @PostMapping("/approval")
    public ResultUtil approval(@RequestBody JSONObject jsonObject){
        String approvalRemark = jsonObject.getString("approvalRemark");
        Integer approvalStatus = jsonObject.getInteger("approvalStatus");
        String id = jsonObject.getString("id");
        Integer approval = meetingAreaBookingServiceImpl.approval(id, approvalRemark, approvalStatus);
        if(approval == -1){
            return ResultUtil.error("已审批");
        }
        return ResultUtil.success("审批成功");
    }

    @GetMapping("/detail")
    public ResultUtil detail(@RequestParam(name = "id") String id){
        Object detail = meetingAreaBookingServiceImpl.detail(id);
        return ResultUtil.success(detail);
    }

    /**
     * 导出
     * @param object
     * @return
     */
    @GetMapping("/export")
    public String export(@RequestBody Object object){

        return null;
    }


}