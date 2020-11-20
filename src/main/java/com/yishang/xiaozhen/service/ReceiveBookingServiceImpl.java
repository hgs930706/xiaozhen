package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yishang.xiaozhen.entity.ApprovalAction;
import com.yishang.xiaozhen.entity.ReceiveBooking;
import com.yishang.xiaozhen.entity.ReceiveBookingImage;
import com.yishang.xiaozhen.event.ReceiveBookingEvent;
import com.yishang.xiaozhen.mapper.ReceiveBookingMapper;
import com.yishang.xiaozhen.util.ImageUploadUtil;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 接待预约表 服务实现类
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Service
public class ReceiveBookingServiceImpl{

    @Autowired
    private ReceiveBookingMapper receiveBookingMapper;

    @Autowired
    private ApprovalActionServiceImpl approvalActionServiceImpl;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ReceiveBookingImageServiceImpl receiveBookingImageServiceImpl;

    public ResultUtil insert(ReceiveBooking object, MultipartFile[] files) {
        receiveBookingMapper.insert(object);
        if (files != null && files.length > 0 && files.length < 4) {
            for (MultipartFile file : files) {
                String imageUrl = ImageUploadUtil.uploadImage(file);
                ReceiveBookingImage receiveBookingImage = new ReceiveBookingImage();
                receiveBookingImage.setReceiveBookingId(object.getId());
                receiveBookingImage.setImageUrl(imageUrl);
                receiveBookingImageServiceImpl.insert(receiveBookingImage);
            }
        }
        return ResultUtil.success();
    }


    public Map<String,Object> list(Integer page,Integer size,LocalDateTime bookingDate, String createTime, Integer isStatus) {

        IPage<ReceiveBooking> ipage = new Page<>(0, 10);
        QueryWrapper<ReceiveBooking> query = new QueryWrapper<>();
        query.ge("booking_time", bookingDate);
//        query.eq("create_time", createTime);
        query.eq("is_status", 1);

        ipage = receiveBookingMapper.selectPage(ipage, query);
        Map<String,Object> map = new HashMap();
        map.put("list",ipage.getRecords());
        map.put("total",ipage.getTotal());
        return map;
    }

    public Integer approval(String id,String approvalRemark ,Integer approvalStatus){
        ReceiveBooking booking = receiveBookingMapper.selectById(id);
        if(booking.getApprovalStatus() != 0){
            return -1;
        }
        booking.setApprovalRemark(approvalRemark);
        booking.setApprovalStatus(approvalStatus);
        int integer = receiveBookingMapper.updateById(booking);

        //记录审批日志
        ApprovalAction ApprovalAction = new ApprovalAction();
        ApprovalAction.setApprovalId(id);
        ApprovalAction.setApprovalBy("当前登录用户");
        ApprovalAction.setApprovalStatus(approvalStatus);
        ApprovalAction.setApprovalRemark(approvalRemark);
        ApprovalAction.setType(2);
        approvalActionServiceImpl.insert(ApprovalAction);

        // 发送模板消息，通知用户，记录消息日志
        ReceiveBookingEvent event = new ReceiveBookingEvent(this,booking);
        applicationEventPublisher.publishEvent(event);
        return integer;
    }

    public Object detail(String id) {
        return null;
    }


    public Integer update(Object object) {
        return null;
    }


    public Integer delete(String id) {
        return null;
    }
}
