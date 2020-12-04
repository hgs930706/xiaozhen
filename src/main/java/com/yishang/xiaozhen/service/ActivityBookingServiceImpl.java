package com.yishang.xiaozhen.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.config.jwt.JwtTokenUtil;
import com.yishang.xiaozhen.entity.*;
import com.yishang.xiaozhen.entity.dto.ActivityBookingDTO;
import com.yishang.xiaozhen.enums.ApprovalStatusEnum;
import com.yishang.xiaozhen.event.ActivityBookingEvent;
import com.yishang.xiaozhen.mapper.ActivityBookingImageMapper;
import com.yishang.xiaozhen.mapper.ActivityBookingMapper;
import com.yishang.xiaozhen.mapper.ActivityCountMapper;
import com.yishang.xiaozhen.mapper.ActivityMapper;
import com.yishang.xiaozhen.util.DateUtil;
import com.yishang.xiaozhen.util.ImageUploadUtil;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
    private ImageUploadUtil imageUploadUtil;
    @Autowired
    private ActivityMapper activityMapper;
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

    @Autowired
    private ActivityBookingImageMapper activityBookingImageMapper;
    @Autowired
    private ActivityCountMapper activityCountMapper;

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
        ApprovalAction.setApprovalBy(JwtTokenUtil.currentUserName());
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
    public ResultUtil insert(ActivityBooking object, MultipartFile[] files, HttpServletRequest request) {
        ActivityCount activityCount = activityCountMapper.selectById(object.getActivityCountId());
        object.setBookingCount(activityCount.getCount());
        activityBookingMapper.insert(object);
        if (files != null && files.length > 0 && files.length < 4) {
            for (MultipartFile file : files) {
                String imageUrl = imageUploadUtil.uploadImage(file,request);
                ActivityBookingImage activityBookingImage = new ActivityBookingImage();
                activityBookingImage.setActivityBookingId(object.getId());
                activityBookingImage.setImageUrl(imageUrl);
                activityBookingImageServiceImpl.insert(activityBookingImage);
            }
        }
        return ResultUtil.success();
    }

    public Map<String, Object> list(Integer page, Integer size, String activityName, String createTime, Integer approvalStatus) {
        LocalDateTime createDate = null;
        if(!StringUtils.isEmpty(createTime)){
            createDate = LocalDateTime.parse(createTime, DateUtil.dateFormatter3);
        }
        List<ActivityBookingDTO> activityBookingDTOS = activityBookingMapper.selectPage((page - 1) * size, size, activityName, createDate, approvalStatus);
        // 拿到预约多个图片,可以优化为，批量查询
        for (ActivityBookingDTO dto : activityBookingDTOS) {
            dto.setApprovalStatusStr(ApprovalStatusEnum.getStr(dto.getApprovalStatus()));
            QueryWrapper<ActivityBookingImage>  query = new  QueryWrapper<>();
            query.eq("activity_booking_id",dto.getId());
            List<ActivityBookingImage> activityBookingImages = activityBookingImageMapper.selectList(query);
            dto.setImages(activityBookingImages);
        }

        Map<String, Object> map = new HashMap();
        map.put("list", activityBookingDTOS);
        map.put("total", activityBookingMapper.selectCount(activityName, createDate, approvalStatus));
        return map;
    }

    public ResultUtil detail(String id) {
        //预约
        ActivityBooking activityBooking = activityBookingMapper.selectById(id);
        //预约图片
        QueryWrapper<ActivityBookingImage>  query = new  QueryWrapper<>();
        query.eq("activity_booking_id",id);
        List<ActivityBookingImage> activityBookingImages = activityBookingImageMapper.selectList(query);
        //活动名称
        Activity activity = activityMapper.selectById(activityBooking.getActivityId());
        if(activity == null){
            return ResultUtil.error("该活动已无效");
        }
        ActivityCount activityCount = activityCountMapper.selectById(activityBooking.getActivityCountId());
        if(activityCount == null){
            return ResultUtil.error("该场次活动已无效");
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activityName",activity.getActivityName());
        jsonObject.put("images",activityBookingImages);
        jsonObject.put("openId",activityBooking.getOpenId());
        jsonObject.put("bookingUnit",activityBooking.getBookingUnit());
        jsonObject.put("bookingPerson",activityBooking.getBookingPerson());
        jsonObject.put("mobile",activityBooking.getMobile());
        jsonObject.put("joinPeople",activityBooking.getJoinPeople());
        jsonObject.put("bookingCount",activityBooking.getBookingCount());
        jsonObject.put("createTime",activityBooking.getCreateTime());
        jsonObject.put("activityStartTime",activityCount.getActivityCountStartTime());
        jsonObject.put("activityEndTime",activityCount.getActivityCountEndTime());

        return ResultUtil.success(jsonObject);
    }

    public Integer update(ActivityBooking meetingArea) {
        return null;
    }

    public Integer delete(String id) {
        return null;
    }


}
