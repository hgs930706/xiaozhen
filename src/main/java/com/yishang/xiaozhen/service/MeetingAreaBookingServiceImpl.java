package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yishang.xiaozhen.entity.ApprovalAction;
import com.yishang.xiaozhen.entity.MeetingAreaBooking;
import com.yishang.xiaozhen.event.MeetingAreaBookingEvent;
import com.yishang.xiaozhen.mapper.MeetingAreaBookingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
public class MeetingAreaBookingServiceImpl{

    @Autowired
    private MeetingAreaBookingMapper meetingAreaBookingMapper;
    @Autowired
    private ApprovalActionServiceImpl approvalActionServiceImpl;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public Integer approval(String id,String approvalRemark ,Integer approvalStatus){
        MeetingAreaBooking booking = meetingAreaBookingMapper.selectById(id);
        if(booking.getApprovalStatus() != 0){
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
        MeetingAreaBookingEvent event = new MeetingAreaBookingEvent(this,booking);
        applicationEventPublisher.publishEvent(event);
        return integer;
    }

    public Integer insert(MeetingAreaBooking object) {
        meetingAreaBookingMapper.insert(object);
        return null;
    }


    public Map<String,Object> list(Integer page,Integer size,String meetingName, String bookingStartTime, String bookingEndTime, Integer isStatus) {
        IPage<MeetingAreaBooking> ipage = new Page<>(0, 10);
        QueryWrapper<MeetingAreaBooking> query = new QueryWrapper<>();
//        query.eq("booking_time", bookingTime);
//        query.eq("create_time", createTime);
        query.eq("is_status", 1);

        ipage = meetingAreaBookingMapper.selectPage(ipage, query);

        Map<String,Object> map = new HashMap();
        map.put("list",ipage.getRecords());
        map.put("total",ipage.getTotal());
        return map;
    }


    public Object detail(String id) {
        MeetingAreaBooking meetingAreaBooking = meetingAreaBookingMapper.selectById(id);
        return meetingAreaBooking;
    }


    public Integer update(Object object) {
        return null;
    }


    public Integer delete(String id) {
        return null;
    }
}
