package com.yishang.xiaozhen.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.config.jwt.JwtTokenUtil;
import com.yishang.xiaozhen.entity.ActivityBooking;
import com.yishang.xiaozhen.entity.Feedback;
import com.yishang.xiaozhen.entity.MeetingAreaBooking;
import com.yishang.xiaozhen.entity.ReceiveBooking;
import com.yishang.xiaozhen.mapper.ActivityBookingMapper;
import com.yishang.xiaozhen.mapper.FeedbackMapper;
import com.yishang.xiaozhen.mapper.MeetingAreaBookingMapper;
import com.yishang.xiaozhen.mapper.ReceiveBookingMapper;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    private ActivityBookingMapper activityBookingMapper;

    @Autowired
    private MeetingAreaBookingMapper meetingAreaBookingMapper;

    @Autowired
    private ReceiveBookingMapper receiveBookingMapper;

    /**
     * 意见反馈，保存
     * @param feedback
     * @return
     */
    @PostMapping("insert")
    public ResultUtil insertFeedback(@RequestBody Feedback feedback){
        String openId = JwtTokenUtil.currentUserName();
        feedback.setOpenId(openId);
        feedbackMapper.insert(feedback);

        return ResultUtil.success();
    }

    /**
     * 我的预约列表（包括：接待预约、会议场地预约、活动报名）
     * @return
     */
    @GetMapping("/bookings")
    public ResultUtil bookings(){
        String openId = JwtTokenUtil.currentUserName();
        // 根据openid获取，三个预约的结果集，返回
        QueryWrapper<ReceiveBooking> query = new QueryWrapper<>();
//        query.eq("open_id", openId);
        query.eq("is_status", 1);
        query.orderByDesc("create_time");
        List<ReceiveBooking> receiveBookings = receiveBookingMapper.selectList(query);

        QueryWrapper<MeetingAreaBooking> query1 = new QueryWrapper<>();
//        query1.eq("open_id", openId);
        query1.eq("is_status", 1);
        query1.orderByDesc("create_time");
        List<MeetingAreaBooking> meetingAreaBookings = meetingAreaBookingMapper.selectList(query1);

        QueryWrapper<ActivityBooking> query2 = new QueryWrapper<>();
//        query2.eq("open_id", openId);
        query2.eq("is_status", 1);
        query2.orderByDesc("create_time");
        List<ActivityBooking> activityBookings = activityBookingMapper.selectList(query2);

        Map<String,Object> map = new HashMap<>();
        map.put("receiveBookings",receiveBookings);
        map.put("meetingAreaBookings",meetingAreaBookings);
        map.put("activityBookings",activityBookings);

        return ResultUtil.success(map);
    }

    /**
     * 预约详情
     * @param bookingType 预约类型，区分：活动类型activityType、接待预约（receiveType）、会议场地预约（meetingType）
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public ResultUtil detail(@RequestParam("bookingType") String bookingType, @RequestParam("id") String id){

        // 根据不同的预约类型，执行不同的预约查询
        if("activityType".equals(bookingType)){
            ActivityBooking activityBooking = activityBookingMapper.selectById(id);
            return ResultUtil.success(activityBooking);

        }else if("receiveType".equals(bookingType)){
            ReceiveBooking receiveBooking = receiveBookingMapper.selectById(id);
            return ResultUtil.success(receiveBooking);

        }else if("meetingType".equals(bookingType)){
            MeetingAreaBooking meetingAreaBooking = meetingAreaBookingMapper.selectById(id);
            return ResultUtil.success(meetingAreaBooking);

        }


        return ResultUtil.error("请输入合法参数");
    }


}
