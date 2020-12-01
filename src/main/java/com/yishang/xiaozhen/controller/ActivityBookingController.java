package com.yishang.xiaozhen.controller;


import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.yishang.xiaozhen.service.ActivityBookingServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 活动报名表 前端控制器
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/activityBooking")
public class ActivityBookingController {

    @Autowired
    private ActivityBookingServiceImpl activityBookingServiceImpl;

    /**
     *
     * @param activityName 活动名称
     * @param createTime 提交活动报名时间
     * @param approvalStatus
     * @return
     */
    @GetMapping("/list")
    public ResultUtil list(Integer page,Integer size,String activityName, String createTime, Integer approvalStatus){
        if (null == page || page <= 0) {
            page = 1;
        }
        if (null == size || size <= 0) {
            size = 10;
        }
        Map<String, Object> list = activityBookingServiceImpl.list(page,size,activityName, createTime, approvalStatus);
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
        if(org.springframework.util.StringUtils.isEmpty(id)){
            return ResultUtil.error("id不能为空");
        }
        Integer approval = activityBookingServiceImpl.approval(id, approvalRemark, approvalStatus);
        if(approval == -1){
            return ResultUtil.error("已审批");
        }
        return ResultUtil.success("审批成功");
    }

    /**
     * 导出
     * @param object
     * @return
     */
    @GetMapping("/export")
    public ResultUtil export(@RequestBody Object object){

        return null;
    }


    @GetMapping("/detail")
    public ResultUtil detail(String id){
        if(StringUtils.isEmpty(id)){
            return ResultUtil.error("id不能为空");
        }
        return activityBookingServiceImpl.detail(id);
    }


}
