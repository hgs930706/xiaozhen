package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yishang.xiaozhen.entity.ActivityBooking;
import com.yishang.xiaozhen.entity.ActivityBookingImage;
import com.yishang.xiaozhen.entity.ApprovalAction;
import com.yishang.xiaozhen.event.ActivityBookingEvent;
import com.yishang.xiaozhen.mapper.ActivityBookingMapper;
import com.yishang.xiaozhen.util.ImageUploadUtil;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 活动报名表 服务实现类
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Service
public class ActivityBookingServiceImpl {

    @Autowired
    private ActivityBookingMapper activityBookingMapper;

    @Autowired
    private MsgActionServiceImpl msgActionServiceImpl;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ApprovalActionServiceImpl approvalActionServiceImpl;

    @Autowired
    private ActivityBookingImageServiceImpl activityBookingImageServiceImpl;

    @Transactional
    public Integer approval(String id, String approvalRemark, Integer approvalStatus) {
        //更新审批
        ActivityBooking booking = activityBookingMapper.selectById(id);
        if (booking.getApprovalStatus() != 0) {
            return -1;
        }
        booking.setApprovalRemark(approvalRemark);
        booking.setApprovalStatus(approvalStatus);
        int integer = activityBookingMapper.updateById(booking);
        //记录审批日志
        ApprovalAction ApprovalAction = new ApprovalAction();
        ApprovalAction.setApprovalId(id);
        ApprovalAction.setApprovalBy("当前登录用户");
        ApprovalAction.setApprovalStatus(approvalStatus);
        ApprovalAction.setApprovalRemark(approvalRemark);
        ApprovalAction.setType(2);
        approvalActionServiceImpl.insert(ApprovalAction);
        // 发送模板消息，通知用户
        ActivityBookingEvent event = new ActivityBookingEvent(this, booking);
        applicationEventPublisher.publishEvent(event);
        return integer;
    }

    @Transactional
    public ResultUtil insert(ActivityBooking object, MultipartFile[] files) {
        activityBookingMapper.insert(object);
        if (files != null && files.length > 0 && files.length < 4) {
            for (MultipartFile file : files) {
                String imageUrl = ImageUploadUtil.uploadImage(file);
                ActivityBookingImage activityBookingImage = new ActivityBookingImage();
                activityBookingImage.setActivityBookingId(object.getId());
                activityBookingImage.setImgeUrl(imageUrl);
                activityBookingImageServiceImpl.insert(activityBookingImage);
            }
        }
        return ResultUtil.success();
    }


    public Map<String, Object> list(Integer page, Integer size, String activityName, String createTime, Integer isStatus) {
        IPage<ActivityBooking> ipage = new Page<>(0, 10);
        QueryWrapper<ActivityBooking> query = new QueryWrapper<>();
//        query.eq("booking_time", bookingTime);
//        query.eq("create_time", createTime);
        query.eq("is_status", 1);

        ipage = activityBookingMapper.selectPage(ipage, query);

        Map<String, Object> map = new HashMap();
        map.put("list", ipage.getRecords());
        map.put("total", ipage.getTotal());
        return map;
    }

    public Object detail(String id) {
        return null;
    }

    public Integer update(ActivityBooking meetingArea) {
        return null;
    }

    public Integer delete(String id) {
        return null;
    }
}
