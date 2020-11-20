package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.ActivityCount;
import com.yishang.xiaozhen.service.ActivityCountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 活动场次表 前端控制器
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/activityCount")
public class ActivityCountController {

    @Autowired
    private ActivityCountServiceImpl activityCountServiceImpl;

    @PostMapping("/insert")
    public String insert(@RequestBody ActivityCount object){
        object.setCount(3);
        object.setActivityCountStartTime(LocalDateTime.now());
        object.setActivityCountEndTime(LocalDateTime.now());
        object.setActivityId("de444bad893a79f9137b2ccc581fbfd2");
        Integer insert = activityCountServiceImpl.insert(object);
        return null;
    }


    /**
     * 删除场次
     * @param id 场次id
     * @return
     */
    @GetMapping("/delete")
    public String delete(@RequestParam("id") String id){
        Integer insert = activityCountServiceImpl.delete(id);
        return null;
    }


}
