package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.ActivityCount;
import com.yishang.xiaozhen.service.ActivityCountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
