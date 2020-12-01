package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.Activity;
import com.yishang.xiaozhen.service.ActivityServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 活动表 前端控制器
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityServiceImpl activityServiceImpl;


    @GetMapping("/list")
    public ResultUtil list(Integer page,Integer size,String activityName
            ,Integer isStatus
            ,String startTime
            ,String endTime){
        if (null == page || page <= 0) {
            page = 1;
        }
        if (null == size || size <= 0) {
            size = 10;
        }

        return  activityServiceImpl.list(page,size,activityName,isStatus,startTime,endTime);
    }

    @GetMapping("/detail")
    public ResultUtil detail(String id){
        if(StringUtils.isEmpty(id)){
            return ResultUtil.error("id不能为空");
        }
        ResultUtil detail = activityServiceImpl.detail2(id);
        return detail;
    }


    @PostMapping("/insert")
    public ResultUtil insert(Activity object, MultipartFile file, HttpServletRequest request){
        if(StringUtils.isEmpty(object.getId())){
            return activityServiceImpl.insert(object, file,request);
        }else{
            return activityServiceImpl.update(object, file,request);
        }
    }

    @PostMapping("/update")
    public ResultUtil update(Activity object, MultipartFile file, HttpServletRequest request){
        if(StringUtils.isEmpty(object.getId())){
            return ResultUtil.error("id不能为空!");
        }
        ResultUtil result = activityServiceImpl.update(object, file,request);
        return result;
    }
}
