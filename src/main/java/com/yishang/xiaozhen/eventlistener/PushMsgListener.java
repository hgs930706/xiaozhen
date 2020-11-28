package com.yishang.xiaozhen.eventlistener;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.constant.MsgSendConstant;
import com.yishang.xiaozhen.constant.TemplateConstant;
import com.yishang.xiaozhen.entity.*;
import com.yishang.xiaozhen.event.ActivityBookingEvent;
import com.yishang.xiaozhen.event.MeetingAreaBookingEvent;
import com.yishang.xiaozhen.event.ReceiveBookingEvent;
import com.yishang.xiaozhen.mapper.*;
import com.yishang.xiaozhen.service.MsgActionServiceImpl;
import com.yishang.xiaozhen.util.HttpClientUtil;
import com.yishang.xiaozhen.util.TemplateData;
import com.yishang.xiaozhen.util.WechatTemplate;
import com.yishang.xiaozhen.config.WxBaseConfig;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PushMsgListener {

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private MsgActionServiceImpl msgActionServiceImpl;

    @Autowired
    private ActivityMapper ActivityMapper;

    @Autowired
    private ActivityCountMapper ActivityCountMapper;

    @Autowired
    private MeetingAreaMapper meetingAreaMapper;

    @Autowired
    private MsgTemplateMapper msgTemplateMapper;

    @Autowired
    private WxUserMapper wxUserMapper;

    private static Map<String, String> maps = new HashMap<>();

    /**
     * 活动，审批通知
     *
     * @param
     * @注解的写法
     */
    @Async("taskExecutor")
    @EventListener
    public void handleActivityBookingEvent(ActivityBookingEvent event) {

        try {
            ActivityBooking booking = event.getActivityBooking();
            Activity activity = ActivityMapper.selectById(booking.getActivityId());
            QueryWrapper<ActivityCount> query = new QueryWrapper();
            query.eq("activity_id", booking.getActivityId());
            query.eq("count", booking.getBookingCount());
            query.eq("is_status", 1);
            ActivityCount activityCount = ActivityCountMapper.selectOne(query);

            String successMsg = "";
            if (booking.getApprovalStatus() == 1) {
                successMsg = sendActivityBookingSuccess(booking, activity, activityCount);
            } else if (booking.getApprovalStatus() == 2) {
                successMsg = sendActivityBookingFail(booking, activity, activityCount);
            }
            String result = HttpClientUtil.post(WxBaseConfig.getTemplateURL(), successMsg, HttpClientUtil.DEFAULT_ENCODING);
            saveMsgAction(result, booking.getOpenId(),
                    booking.getApprovalStatus(), 1,
                    booking.getApprovalRemark(), booking.getBookingPerson(),
                    activity.getActivityName(), activityCount.getActivityCountStartTime()
                    , activity.getActivityAddress());
        } catch (Exception e) {
            log.error("活动审批消息，发生异常：{}", e);
        }
    }

    /**
     * 接待预约，审批通知
     *
     * @param event
     */
    @Async("taskExecutor")
    @EventListener
    public void handleReceiveBookingEvent(ReceiveBookingEvent event) {
        try {
            ReceiveBooking booking = event.getReceiveBooking();
            String successMsg = "";
            if (booking.getApprovalStatus() == 1) {
                successMsg = sendReceiveBookingSuccess(booking);
            } else if (booking.getApprovalStatus() == 2) {
                successMsg = sendReceiveBookingFail(booking);
            }
            String result = HttpClientUtil.post(WxBaseConfig.getTemplateURL(), successMsg, HttpClientUtil.DEFAULT_ENCODING);

            //记录消息日志
            saveMsgAction(result, booking.getOpenId(),
                    booking.getApprovalStatus(), 2,
                    booking.getApprovalRemark(), booking.getBookingName(),
                    booking.getBookingName(), booking.getBookingTime()
                    , "地址就是小镇地址");
        } catch (Exception e) {
            log.error("接待预约审批消息，发生异常：{}", e);
        }
    }

    /**
     * 会议场地预约，审批通知
     *
     * @param event
     */
    @Async("taskExecutor")
    @EventListener
    public void handleMeetingAreaBookingEvent(MeetingAreaBookingEvent event) {
        try {
            MeetingAreaBooking booking = event.getMeetingAreaBooking();
            MeetingArea meetingArea = meetingAreaMapper.selectById(booking.getMeetingAreaId());
            String successMsg = "";
            if (booking.getApprovalStatus() == 1) {
                successMsg = sendMeetingAreaBookingSuccess(booking, meetingArea);
            } else if (booking.getApprovalStatus() == 2) {
                successMsg = sendMeetingAreaBookingFail(booking, meetingArea);
            }
            String result = HttpClientUtil.post(WxBaseConfig.getTemplateURL(), successMsg, HttpClientUtil.DEFAULT_ENCODING);
            //记录消息日志
            saveMsgAction(result, booking.getOpenId(),
                    booking.getApprovalStatus(), 3,
                    booking.getApprovalRemark(), booking.getBookingPerson(),
                    meetingArea.getMeetingName(), booking.getBookingStartTime()
                    , meetingArea.getMeetingAddress());
        } catch (Exception e) {
            log.error("会议场地审批消息，发生异常：{}", e);
        }
    }

    private String sendActivityBookingSuccess(ActivityBooking booking, Activity activity, ActivityCount activityCount) {
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id(getTemplateId(TemplateConstant.ACTIVITY_SUCCESS));
        wechatTemplate.setTouser(booking.getOpenId());   // openid来自报名openid字段，当前登录人是审批人
        wechatTemplate.setUrl("http://news.baidu.com");
        Map<String, TemplateData> m = new HashMap<>();
        m.put("first", new TemplateData(MsgSendConstant.ACTIVITY_SUCCESS_FIRST));
        //名称
        m.put("keyword1", new TemplateData(activity.getActivityName()));
        //时间
        m.put("keyword2", new TemplateData(activityCount.getActivityCountStartTime() + "-" + activityCount.getActivityCountEndTime()));
        //地点
        m.put("keyword3", new TemplateData(activity.getActivityAddress()));
        //预约人
        m.put("keyword4", new TemplateData(booking.getBookingPerson()));
        //备注
        m.put("remark", new TemplateData(MsgSendConstant.ACTIVITY_SUCCESS_REMARK));
        wechatTemplate.setData(m);
        return JSONObject.toJSONString(wechatTemplate);
    }

    private String sendActivityBookingFail(ActivityBooking booking, Activity activity, ActivityCount activityCount) {
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id(getTemplateId(TemplateConstant.ACTIVITY_FAIL));
        wechatTemplate.setTouser(booking.getOpenId());   // openid来自，当前登录的用户
        wechatTemplate.setUrl("http://news.baidu.com");
        Map<String, TemplateData> m = new HashMap<>();
        m.put("first", new TemplateData(MsgSendConstant.ACTIVITY_FAIL_FIRST));
        //名称
        m.put("keyword1", new TemplateData(activity.getActivityName()));
        //时间
        m.put("keyword2", new TemplateData(activityCount.getActivityCountStartTime() + "-" + activityCount.getActivityCountEndTime()));
        //地点
        m.put("keyword3", new TemplateData(activity.getActivityAddress()));
        //预约人
        m.put("keyword4", new TemplateData(booking.getBookingPerson()));
        //备注
        m.put("remark", new TemplateData(booking.getApprovalRemark()));
        wechatTemplate.setData(m);
        return JSONObject.toJSONString(wechatTemplate);
    }

    private String sendReceiveBookingSuccess(ReceiveBooking booking) {
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id(getTemplateId(TemplateConstant.BOOKING_SUCCESS));
        wechatTemplate.setTouser(booking.getOpenId());   // openid来自报名openid字段，当前登录人是审批人
        wechatTemplate.setUrl("http://news.baidu.com");
        Map<String, TemplateData> m = new HashMap<>();
        m.put("first", new TemplateData(MsgSendConstant.RECEIVE_SUCCESS_FIRST));
        //名称
        m.put("keyword1", new TemplateData("参观预约"));
        //地点
        m.put("keyword2", new TemplateData("艺尚小镇"));
        //时间
        m.put("keyword3", new TemplateData(booking.getBookingTime() + ""));
        //预约人
        m.put("keyword4", new TemplateData(booking.getBookingName()));
        //备注
        m.put("remark", new TemplateData(MsgSendConstant.RECEIVE_SUCCESS_REMARK));
        wechatTemplate.setData(m);
        return JSONObject.toJSONString(wechatTemplate);
    }

    private String sendReceiveBookingFail(ReceiveBooking booking) {
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id(getTemplateId(TemplateConstant.BOOKING_FAIL));
        wechatTemplate.setTouser(booking.getOpenId());   // openid来自报名openid字段，当前登录人是审批人
        wechatTemplate.setUrl("http://news.baidu.com");
        Map<String, TemplateData> m = new HashMap<>();
        m.put("first", new TemplateData(MsgSendConstant.RECEIVE_FAIL_FIRST));
        //名称
        m.put("keyword1", new TemplateData("参观预约"));
        //时间
        m.put("keyword2", new TemplateData(booking.getBookingTime() + ""));
        //地点
        m.put("keyword3", new TemplateData("艺尚小镇"));
        //预约人
        m.put("keyword4", new TemplateData(booking.getBookingName()));
        //备注
        m.put("remark", new TemplateData(booking.getApprovalRemark()));
        wechatTemplate.setData(m);
        return JSONObject.toJSONString(wechatTemplate);
    }

    private String sendMeetingAreaBookingSuccess(MeetingAreaBooking booking, MeetingArea meetingArea) {
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id(getTemplateId(TemplateConstant.BOOKING_SUCCESS));
        wechatTemplate.setTouser(booking.getOpenId());   // openid来自报名openid字段，当前登录人是审批人
        wechatTemplate.setUrl("http://news.baidu.com");
        Map<String, TemplateData> m = new HashMap<>();
        m.put("first", new TemplateData(MsgSendConstant.MEETING_SUCCESS_FIRST));
        //名称
        m.put("keyword1", new TemplateData(meetingArea.getMeetingName()));
        //地点
        m.put("keyword2", new TemplateData(meetingArea.getMeetingAddress()));
        //时间
        m.put("keyword3", new TemplateData(booking.getBookingStartTime() + "-" + booking.getBookingEndTime()));
        //预约人
        m.put("keyword4", new TemplateData(booking.getBookingPerson()));
        //备注
        m.put("remark", new TemplateData(MsgSendConstant.MEETING_SUCCESS_REMARK));
        wechatTemplate.setData(m);
        return JSONObject.toJSONString(wechatTemplate);
    }

    private String sendMeetingAreaBookingFail(MeetingAreaBooking booking, MeetingArea meetingArea) {
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id(getTemplateId(TemplateConstant.BOOKING_FAIL));
        wechatTemplate.setTouser(booking.getOpenId());   // openid来自报名openid字段，当前登录人是审批人
        wechatTemplate.setUrl("http://news.baidu.com");
        Map<String, TemplateData> m = new HashMap<>();
        m.put("first", new TemplateData(MsgSendConstant.MEETING_FAIL_FIRST));
        //名称
        m.put("keyword1", new TemplateData(meetingArea.getMeetingName()));
        //时间
        m.put("keyword2", new TemplateData(booking.getBookingStartTime() + "-" + booking.getBookingEndTime()));
        //地点
        m.put("keyword3", new TemplateData(meetingArea.getMeetingAddress()));
        //预约人
        m.put("keyword4", new TemplateData(booking.getBookingPerson()));
        //备注
        m.put("remark", new TemplateData(booking.getApprovalRemark()));
        wechatTemplate.setData(m);
        return JSONObject.toJSONString(wechatTemplate);
    }

    private void saveMsgAction(String result, String openId,
                               Integer approvalStatus, Integer msgType,
                               String msgContent, String bookingPerson,
                               String name, LocalDateTime bookingTime,
                               String receiveAddress) {
        //保存我的消息
        MsgAction msgAction = new MsgAction();
        msgAction.setNickname(getNickname(openId));
        msgAction.setOpenId(openId);
        msgAction.setApprovalStatus(approvalStatus);
        msgAction.setMsgType(msgType);
        msgAction.setMsgContent(msgContent);
        msgAction.setBookingPerson(bookingPerson);
        msgAction.setName(name);
        msgAction.setBookingTime(bookingTime);
        msgAction.setReceiveAddress(receiveAddress);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Integer errcode = jsonObject.getInteger("errcode");
        if (errcode == 0) {
            msgAction.setSendStatus(1);
            log.info("消息发送成功。");
        } else {
            msgAction.setSendStatus(0);
            log.info("消息发送失败：{}", result);
        }
        msgActionServiceImpl.insert(msgAction);
    }

    private String getTemplateId(String key) {
        if (maps.get(key) == null) {
            List<MsgTemplate> msgTemplates = msgTemplateMapper.selectList(null);
            if (Collections.isEmpty(msgTemplates)) {
                log.error("请维护模板库。");
                return null;
            }
            maps = msgTemplates.stream().collect(
                    Collectors.toMap(MsgTemplate::getTemplateName, MsgTemplate::getTemplateId, (key1, key2) -> key2)
            );
        }
        return maps.get(key);
    }

    private String getNickname(String openId) {
        QueryWrapper<WxUser> query = new QueryWrapper();
        query.eq("open_id", openId);
        query.eq("is_status", 1);
        WxUser wxUser = wxUserMapper.selectOne(query);
        return wxUser.getNickname();
    }


    /**
     * 自动注入的写法
     * @param event
     */
    //    @EventListener
    //    public void handleDemoEvent(MyEvent event) {
    //        taskExecutor.submit(() -> log.info("发布的data为1:{}", event.getData()+Thread.currentThread().getName()));
    //    }

}
