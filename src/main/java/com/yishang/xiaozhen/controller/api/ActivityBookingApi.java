package com.yishang.xiaozhen.controller.api;


import com.yishang.xiaozhen.config.jwt.JwtTokenUtil;
import com.yishang.xiaozhen.entity.ActivityBooking;
import com.yishang.xiaozhen.service.ActivityBookingServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public String list(){
        //根据最新的活动，排序

        return null;
    }


    /**
     *  活动详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public String detail(@RequestParam(name = "id") String id){



        return null;
    }



    /**
     * 预约报名顶部轮播图展示，，默认展示前三个最新的活动图片
     * @return
     */
    @GetMapping("/banner")
    public String banner(){


        return null;
    }




}
