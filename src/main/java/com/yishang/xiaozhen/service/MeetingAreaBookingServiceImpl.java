package com.yishang.xiaozhen.service;

import com.alibaba.fastjson.JSONObject;
import com.yishang.xiaozhen.entity.ApprovalAction;
import com.yishang.xiaozhen.entity.MeetingArea;
import com.yishang.xiaozhen.entity.MeetingAreaBooking;
import com.yishang.xiaozhen.entity.dto.MeetingAreaBookingDTO;
import com.yishang.xiaozhen.enums.ApprovalStatusEnum;
import com.yishang.xiaozhen.event.MeetingAreaBookingEvent;
import com.yishang.xiaozhen.mapper.MeetingAreaBookingMapper;
import com.yishang.xiaozhen.mapper.MeetingAreaMapper;
import com.yishang.xiaozhen.util.DateUtil;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会议场地预约表 服务实现类
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Service
public class MeetingAreaBookingServiceImpl {

    @Autowired
    private MeetingAreaBookingMapper meetingAreaBookingMapper;
    @Autowired
    private MeetingAreaMapper meetingAreaMapper;
    @Autowired
    private ApprovalActionServiceImpl approvalActionServiceImpl;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public Integer approval(String id, String approvalRemark, Integer approvalStatus) {
        MeetingAreaBooking booking = meetingAreaBookingMapper.selectById(id);
        if (booking.getApprovalStatus() != 0) {
            return -1;
        }
        booking.setApprovalRemark(approvalRemark);
        booking.setApprovalStatus(approvalStatus);
        int integer = meetingAreaBookingMapper.updateById(booking);
        //记录审批日志
        ApprovalAction ApprovalAction = new ApprovalAction();
        ApprovalAction.setApprovalId(id);
        ApprovalAction.setApprovalBy("当前登录用户");
        ApprovalAction.setApprovalStatus(approvalStatus);
        ApprovalAction.setApprovalRemark(approvalRemark);
        ApprovalAction.setType(2);
        approvalActionServiceImpl.insert(ApprovalAction);
        // 发送模板消息，通知用户
        MeetingAreaBookingEvent event = new MeetingAreaBookingEvent(this, booking);
        applicationEventPublisher.publishEvent(event);
        return integer;
    }

    public ResultUtil insert(MeetingAreaBooking object) {
        String meetingAreaId = object.getMeetingAreaId();
        if (StringUtils.isEmpty(meetingAreaId)) {
            return ResultUtil.error("会议场地id不能为空");
        }
        meetingAreaBookingMapper.insert(object);
        return ResultUtil.success();
    }


    public Map<String, Object> list(Integer page, Integer size, String meetingName, String bookingStartTime, String bookingEndTime, Integer approvalStatus) {
        LocalDateTime bookingStartDate = null;
        if (!StringUtils.isEmpty(bookingStartTime)) {
            bookingStartDate = LocalDateTime.parse(bookingStartTime, DateUtil.dateFormatter3);

        }
        LocalDateTime bookingEndDate = null;
        if (!StringUtils.isEmpty(bookingEndTime)) {
            bookingEndDate = LocalDateTime.parse(bookingEndTime, DateUtil.dateFormatter3);
        }

        List<MeetingAreaBookingDTO> meetingAreaBookingDTOS = meetingAreaBookingMapper.selectPage((page - 1) * size, size, meetingName, bookingStartDate, bookingEndDate, approvalStatus);
        for (MeetingAreaBookingDTO dto : meetingAreaBookingDTOS) {
            dto.setApprovalStatusStr(ApprovalStatusEnum.getStr(dto.getApprovalStatus()));
        }

        Map<String, Object> map = new HashMap();
        map.put("list", meetingAreaBookingDTOS);
        map.put("total", meetingAreaBookingMapper.selectCount(meetingName, bookingStartDate, bookingEndDate, approvalStatus));
        return map;
    }


    public ResultUtil detail(String id) {
        MeetingAreaBooking meetingAreaBooking = meetingAreaBookingMapper.selectById(id);
        MeetingArea meetingArea = meetingAreaMapper.selectById(meetingAreaBooking.getMeetingAreaId());
        if(meetingArea == null){
            return ResultUtil.error("该会议场地已无效");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openId",meetingAreaBooking.getOpenId());
        jsonObject.put("bookingUnit",meetingAreaBooking.getBookingUnit());
        jsonObject.put("bookingPerson",meetingAreaBooking.getBookingPerson());
        jsonObject.put("mobile",meetingAreaBooking.getMobile());
        jsonObject.put("joinPeople",meetingAreaBooking.getJoinPeople());
        jsonObject.put("bookingStartTime",meetingAreaBooking.getBookingStartTime());
        jsonObject.put("bookingEndTime",meetingAreaBooking.getBookingEndTime());
        jsonObject.put("createTime",meetingAreaBooking.getCreateTime());
        jsonObject.put("meetingTable",meetingAreaBooking.getMeetingTable());
        jsonObject.put("meetingType",meetingAreaBooking.getMeetingType());
        jsonObject.put("meetingGoods",meetingAreaBooking.getMeetingGoods());
        jsonObject.put("remark",meetingAreaBooking.getRemark());

        jsonObject.put("meetingName",meetingArea.getMeetingName());
        jsonObject.put("meetingAddress",meetingArea.getMeetingAddress());

        return ResultUtil.success(jsonObject);
    }


    public Integer update(Object object) {
        return null;
    }


    public Integer delete(String id) {
        return null;
    }
}
