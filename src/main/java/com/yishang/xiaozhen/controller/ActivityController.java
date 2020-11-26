package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.Activity;
import com.yishang.xiaozhen.service.ActivityServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public String list(Integer page,Integer size,String activityName
            ,Integer isStatus
            ,String startTime
            ,String endTime){
        activityServiceImpl.list(page,size,activityName,isStatus,startTime,endTime);
        return null;
    }

    @GetMapping("/detail")
    public ResultUtil detail(@RequestParam("id") String id){
        ResultUtil detail = activityServiceImpl.detail2(id);
        return detail;
    }


    @PostMapping("/insert")
    public ResultUtil insert(Activity object, MultipartFile file){

        ResultUtil result = activityServiceImpl.insert(object, file);
        return result;
    }

    @PostMapping("/update")
    public ResultUtil update(Activity object, MultipartFile file){
        if(StringUtils.isEmpty(object.getId())){
            return ResultUtil.error("id不能为空!");
        }
        ResultUtil result = activityServiceImpl.update(object, file);
        return result;
    }
}
