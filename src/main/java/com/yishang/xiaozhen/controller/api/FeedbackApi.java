package com.yishang.xiaozhen.controller.api;


import com.yishang.xiaozhen.entity.Feedback;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 微信端
 *
 * 我的预约、我的消息、意见反馈
 *
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/api/feedback")
public class FeedbackApi {


    /**
     * 意见反馈，保存
     * @param feedback
     * @return
     */
    @PostMapping("insert")
    public String insertFeedback(@RequestBody Feedback feedback){


        return null;
    }

    /**
     * 我的预约列表（包括：接待预约、会议场地预约、活动报名）
     * @return
     */
    @GetMapping("/bookings")
    public String bookings(String openId){

        // 根据openid获取，三个预约的结果集，返回

        return null;
    }

    /**
     * 预约详情
     * @param bookingType 预约类型，区分：接待预约（receiveType）、会议场地预约（meetingType）
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public String detail(@RequestParam("bookingType") String bookingType, @RequestParam("id") String id){

        // 根据不同的预约类型，执行不同的预约查询


        return null;
    }


}
