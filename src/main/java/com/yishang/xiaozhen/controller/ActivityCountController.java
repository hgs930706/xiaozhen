package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.ActivityCount;
import com.yishang.xiaozhen.service.ActivityCountServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    public ResultUtil insert(@RequestBody ActivityCount object){
        return activityCountServiceImpl.insert(object);
    }


    /**
     * 删除场次
     * @param id 场次id
     * @return
     */
    @GetMapping("/delete")
    public ResultUtil delete(String id){
        if(StringUtils.isEmpty(id)){
            return ResultUtil.error("id不能为空");
        }
        return activityCountServiceImpl.delete(id);
    }


}
