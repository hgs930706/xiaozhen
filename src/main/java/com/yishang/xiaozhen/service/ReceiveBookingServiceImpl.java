package com.yishang.xiaozhen.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yishang.xiaozhen.config.jwt.JwtTokenUtil;
import com.yishang.xiaozhen.entity.ApprovalAction;
import com.yishang.xiaozhen.entity.ReceiveBooking;
import com.yishang.xiaozhen.entity.ReceiveBookingImage;
import com.yishang.xiaozhen.event.ReceiveBookingEvent;
import com.yishang.xiaozhen.mapper.ReceiveBookingImageMapper;
import com.yishang.xiaozhen.mapper.ReceiveBookingMapper;
import com.yishang.xiaozhen.util.DateUtil;
import com.yishang.xiaozhen.util.ImageUploadUtil;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private ReceiveBookingImageMapper receiveBookingImageMapper;

    public ResultUtil insert(ReceiveBooking object, MultipartFile[] files, HttpServletRequest request) {
        receiveBookingMapper.insert(object);
        if (files != null && files.length > 0 && files.length < 4) {
            for (MultipartFile file : files) {
                String imageUrl = ImageUploadUtil.uploadImage(file, request);
                ReceiveBookingImage receiveBookingImage = new ReceiveBookingImage();
                receiveBookingImage.setReceiveBookingId(object.getId());
                receiveBookingImage.setImageUrl(imageUrl);
                receiveBookingImageServiceImpl.insert(receiveBookingImage);
            }
        }
        return ResultUtil.success();
    }


    public Map<String,Object> list(Integer page,Integer size,String bookingTime, String createTime, Integer approvalStatus) {
        IPage<ReceiveBooking> ipage = new Page<>(page,size);
        QueryWrapper<ReceiveBooking> query = new QueryWrapper<>();

        if(!StringUtils.isEmpty(bookingTime)){
            LocalDateTime bookingDate = LocalDateTime.parse(createTime, DateUtil.dateFormatter3);
            query.ge("booking_time", bookingDate);
        }
        if(!StringUtils.isEmpty(createTime)){
            LocalDateTime createDate = LocalDateTime.parse(createTime, DateUtil.dateFormatter3);
            query.ge("create_time", createDate);
        }
        if(approvalStatus != null){
            query.eq("approval_status", approvalStatus);
        }
        query.eq("is_status", 1);
        ipage = receiveBookingMapper.selectPage(ipage, query);

        List<ReceiveBooking> records = ipage.getRecords();
        // 拿到预约多个图片
        for (ReceiveBooking record : records) {
            QueryWrapper<ReceiveBookingImage> query2 = new  QueryWrapper<>();
            query2.eq("receive_booking_id",record.getId());
            List<ReceiveBookingImage> activityBookingImages = receiveBookingImageMapper.selectList(query2);
            record.setImages(activityBookingImages);
        }
        Map<String,Object> map = new HashMap();
        map.put("list",records);
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
        ApprovalAction.setApprovalBy(JwtTokenUtil.currentUserName());
        ApprovalAction.setApprovalStatus(approvalStatus);
        ApprovalAction.setApprovalRemark(approvalRemark);
        ApprovalAction.setType(2);
        approvalActionServiceImpl.insert(ApprovalAction);

        // 发送模板消息，通知用户，记录消息日志
        ReceiveBookingEvent event = new ReceiveBookingEvent(this,booking);
        applicationEventPublisher.publishEvent(event);
        return integer;
    }

    public ResultUtil detail(String id) {
        ReceiveBooking receiveBooking = receiveBookingMapper.selectById(id);
        QueryWrapper<ReceiveBookingImage>  query = new  QueryWrapper<>();
        query.eq("receive_booking_id",id);
        List<ReceiveBookingImage> receiveBookingImages = receiveBookingImageMapper.selectList(query);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("images",receiveBookingImages);
        jsonObject.put("openId",receiveBooking.getOpenId());
        jsonObject.put("bookingCompany",receiveBooking.getBookingCompany());
        jsonObject.put("bookingName",receiveBooking.getBookingName());
        jsonObject.put("mobile",receiveBooking.getMobile());
        jsonObject.put("visitCount",receiveBooking.getVisitCount());
        jsonObject.put("visitType",receiveBooking.getVisitType());
        jsonObject.put("bookingTime",DateUtil.getDateTimeAsString(receiveBooking.getBookingTime()));
        jsonObject.put("createTime",DateUtil.getDateTimeAsString(receiveBooking.getCreateTime()));
        jsonObject.put("remark", receiveBooking.getRemark());
        return ResultUtil.success(jsonObject);
    }


    public Integer update(Object object) {
        return null;
    }


    public Integer delete(String id) {
        return null;
    }
}
