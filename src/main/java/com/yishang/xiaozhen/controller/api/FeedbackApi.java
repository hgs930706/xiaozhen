package com.yishang.xiaozhen.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.config.jwt.JwtTokenUtil;
import com.yishang.xiaozhen.entity.*;
import com.yishang.xiaozhen.entity.dto.MyBookingDTO;
import com.yishang.xiaozhen.enums.ApprovalStatusEnum;
import com.yishang.xiaozhen.mapper.*;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    @Autowired
    private ReceiveBookingImageMapper receiveBookingImageMapper;


    @Autowired
    private MeetingAreaMapper meetingAreaMapper;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivityBookingImageMapper activityBookingImageMapper;


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

        List<MyBookingDTO> list = new ArrayList<>();
        // 根据openid获取，三个预约的结果集，返回
        QueryWrapper<ReceiveBooking> query = new QueryWrapper<>();
//        query.eq("open_id", openId);
        query.eq("is_status", 1);
        query.orderByDesc("create_time");
        List<ReceiveBooking> receiveBookings = receiveBookingMapper.selectList(query);
        for (ReceiveBooking booking : receiveBookings) {
            MyBookingDTO dto = new MyBookingDTO();
            dto.setId(booking.getId());
            dto.setName("接待预约");
            dto.setBookingType("receiveType");
            //0待审批，1审批通过，2审批拒绝
            dto.setStatus(ApprovalStatusEnum.getStr(booking.getApprovalStatus()));
            dto.setBookingTime(booking.getCreateTime());
            dto.setBookingAddress("小镇地址");
            list.add(dto);
        }

        QueryWrapper<MeetingAreaBooking> query1 = new QueryWrapper<>();
//        query1.eq("open_id", openId);
        query1.eq("is_status", 1);
        query1.orderByDesc("create_time");
        List<MeetingAreaBooking> meetingAreaBookings = meetingAreaBookingMapper.selectList(query1);
        for (MeetingAreaBooking booking : meetingAreaBookings) {
            MyBookingDTO dto = new MyBookingDTO();
            dto.setId(booking.getId());
            dto.setName("会议场地预约");
            dto.setBookingType("meetingType");
            //0待审批，1审批通过，2审批拒绝
            dto.setStatus(ApprovalStatusEnum.getStr(booking.getApprovalStatus()));
            dto.setBookingTime(booking.getCreateTime());
            MeetingArea meetingArea = meetingAreaMapper.selectById(booking.getMeetingAreaId());
            dto.setBookingAddress(meetingArea.getMeetingAddress());
            list.add(dto);
        }

        QueryWrapper<ActivityBooking> query2 = new QueryWrapper<>();
//        query2.eq("open_id", openId);
        query2.eq("is_status", 1);
        query2.orderByDesc("create_time");
        List<ActivityBooking> activityBookings = activityBookingMapper.selectList(query2);
        for (ActivityBooking booking : activityBookings) {
            MyBookingDTO dto = new MyBookingDTO();
            dto.setId(booking.getId());
            dto.setName("活动报名");
            dto.setBookingType("activityType");
            //0待审批，1审批通过，2审批拒绝
            dto.setStatus(ApprovalStatusEnum.getStr(booking.getApprovalStatus()));
            dto.setBookingTime(booking.getCreateTime());
            Activity activity = activityMapper.selectById(booking.getActivityId());
            dto.setBookingAddress(activity.getActivityAddress());
            list.add(dto);
        }
        list.sort(Comparator.comparing(MyBookingDTO::getBookingTime,Comparator.reverseOrder()));

        return ResultUtil.success(list);
    }

    /**
     * 预约详情
     * @param bookingType 预约类型，区分：活动类型activityType、接待预约（receiveType）、会议场地预约（meetingType）
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public ResultUtil detail(String bookingType,String id){
        if(StringUtils.isEmpty(id)){
            return ResultUtil.error("id不能为空");
        }
        if(StringUtils.isEmpty(bookingType)){
            return ResultUtil.error("预约类型不能为空");
        }
        // 根据不同的预约类型，执行不同的预约查询
        if("activityType".equals(bookingType)){
            ActivityBooking booking = activityBookingMapper.selectById(id);
            MyBookingDTO dto = new MyBookingDTO();
            dto.setId(booking.getId());
            dto.setName("活动报名");
            dto.setStatus(ApprovalStatusEnum.getStr(booking.getApprovalStatus()));
            dto.setBookingTime(booking.getCreateTime());
            dto.setBookingPerson(booking.getBookingPerson());
            dto.setBookingContent("");
            Activity activity = activityMapper.selectById(booking.getActivityId());
            dto.setBookingAddress(activity.getActivityAddress());
            QueryWrapper<ActivityBookingImage>  query = new  QueryWrapper<>();
            query.eq("activity_booking_id",booking.getId());
            List<ActivityBookingImage> images = activityBookingImageMapper.selectList(query);
            if(images != null && images.size() > 0){
                dto.setImages(images.get(0).getImgeUrl());
            }
            return ResultUtil.success(dto);

        }else if("receiveType".equals(bookingType)){
            ReceiveBooking booking = receiveBookingMapper.selectById(id);
            MyBookingDTO dto = new MyBookingDTO();
            dto.setId(booking.getId());
            dto.setName("接待预约");
            dto.setStatus(ApprovalStatusEnum.getStr(booking.getApprovalStatus()));
            dto.setBookingTime(booking.getCreateTime());
            dto.setBookingAddress("小镇地址");
            dto.setBookingPerson(booking.getBookingName());
            dto.setBookingContent(booking.getRemark());
            QueryWrapper<ReceiveBookingImage>  query = new  QueryWrapper<>();
            query.eq("receive_booking_id",booking.getId());
            List<ReceiveBookingImage> images = receiveBookingImageMapper.selectList(query);
            if(images != null && images.size() > 0){
                dto.setImages(images.get(0).getImageUrl());
            }
            return ResultUtil.success(dto);

        }else if("meetingType".equals(bookingType)){
            MeetingAreaBooking booking = meetingAreaBookingMapper.selectById(id);
            MyBookingDTO dto = new MyBookingDTO();
            dto.setId(booking.getId());
            dto.setName("会议场地预约");
            dto.setStatus(ApprovalStatusEnum.getStr(booking.getApprovalStatus()));
            dto.setBookingTime(booking.getCreateTime());
            MeetingArea meetingArea = meetingAreaMapper.selectById(booking.getMeetingAreaId());
            dto.setBookingAddress(meetingArea.getMeetingAddress());
            dto.setBookingPerson(booking.getBookingPerson());
            dto.setBookingContent(booking.getRemark());
            dto.setImages("");
            return ResultUtil.success(dto);
        }
        return ResultUtil.error("请输入合法参数");
    }


}
