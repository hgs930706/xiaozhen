package com.yishang.xiaozhen.controller.api;


import com.yishang.xiaozhen.config.jwt.JwtTokenUtil;
import com.yishang.xiaozhen.entity.ActivityBooking;
import com.yishang.xiaozhen.service.ActivityBookingServiceImpl;
import com.yishang.xiaozhen.service.ActivityServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 * 活动报名表 微信端
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/api/activityBooking")
public class ActivityBookingApi {


    @Autowired
    private ActivityBookingServiceImpl activityBookingServiceImpl;

    @Autowired
    private ActivityServiceImpl activityServiceImpl;

    /**
     *  活动详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public ResultUtil detail(@RequestParam(name = "id") String id){
        ResultUtil detail = activityServiceImpl.detail(id);
        return detail;
    }

    /**
     * 活动预约
     * @param object
     * @return
     */
    @PostMapping("/insert")
    public ResultUtil insert(ActivityBooking object, MultipartFile[] files){

        object.setOpenId(JwtTokenUtil.currentUserName());
        ResultUtil result = activityBookingServiceImpl.insert(object, files);

        return result;
    }

    /**
     * 获取活动列表，支持分页，每次下拉，相当于获取下一页
     * @return
     */
    @GetMapping("/list")
    public ResultUtil list(Integer page,Integer size){
        //根据最新的活动，排序
        Map<String, Object> list = activityServiceImpl.list(page, size);
        return ResultUtil.success(list);
    }






    /**
     * 预约报名顶部轮播图展示，，默认展示前三个最新的活动图片
     * @return
     */
    @GetMapping("/banner")
    public ResultUtil banner(){
        ResultUtil banner = activityServiceImpl.banner();

        return banner;
    }




}
