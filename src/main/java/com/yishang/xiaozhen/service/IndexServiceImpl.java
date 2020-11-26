package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.entity.ActivityBooking;
import com.yishang.xiaozhen.entity.MeetingAreaBooking;
import com.yishang.xiaozhen.entity.ReceiveBooking;
import com.yishang.xiaozhen.mapper.ActivityBookingMapper;
import com.yishang.xiaozhen.mapper.IndexMapper;
import com.yishang.xiaozhen.mapper.MeetingAreaBookingMapper;
import com.yishang.xiaozhen.mapper.ReceiveBookingMapper;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class IndexServiceImpl {



    @Autowired
    private IndexMapper indexMapper;
    @Autowired
    private ActivityBookingMapper activityBookingMapper;
    @Autowired
    private ReceiveBookingMapper receiveBookingMapper;
    @Autowired
    private MeetingAreaBookingMapper meetingAreaBookingMapper;

    /**
     * 用户总数、今日新增用户、本周接待人数
     * @return
     */
    public ResultUtil userSum(){

        //总用户
        Integer userSum = indexMapper.userSum();

        //今日新增用户,获取当天零点
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        Integer todayNewlyAddedUser = indexMapper.todayNewlyAddedUser(todayStart);

        //本周接待
        LocalDateTime weekLocalDateTime = LocalDateTime.now().minusDays(7);
        Integer weekActivityReceiveCounts = indexMapper.weekActivityReceiveCounts(weekLocalDateTime);
        Integer weekReceReceiveCounts = indexMapper.weekReceReceiveCounts(weekLocalDateTime);
        Integer weekMeetReceiveCounts = indexMapper.weekMeetReceiveCounts(weekLocalDateTime);

        Map<String,Integer> map = new HashMap<>();
        map.put("userSum",userSum);
        map.put("todayNewlyAddedUser",todayNewlyAddedUser);
        map.put("receiveCounts",weekActivityReceiveCounts + weekReceReceiveCounts + weekMeetReceiveCounts);

        return ResultUtil.success(map);
    }

    /**
     * 今日待接待
     * @return
     */
    public ResultUtil waitReceive(){
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        Integer integer = indexMapper.weekActivityReceiveCounts(todayStart);
        Integer integer1 = indexMapper.weekReceReceiveCounts(todayStart);
        Integer integer2 = indexMapper.weekMeetReceiveCounts(todayStart);

        Map<String,Integer> map = new HashMap<>();
        map.put("waitActivity",integer);
        map.put("waitReceive",integer1);
        map.put("waitMeeting",integer2);

        return ResultUtil.success(map);
    }


    /**
     * 全部、预约待处理
     * @return
     */
    public ResultUtil bookingWaitHandle(){

        QueryWrapper<ActivityBooking> query = new QueryWrapper();
        query.eq("is_status",1);
        Integer activity = activityBookingMapper.selectCount(query);

        QueryWrapper<ReceiveBooking> query1 = new QueryWrapper();
        query1.eq("is_status",1);
        Integer rece = receiveBookingMapper.selectCount(query1);

        QueryWrapper<MeetingAreaBooking> query2 = new QueryWrapper();
        query2.eq("is_status",1);
        Integer meet = meetingAreaBookingMapper.selectCount(query2);


        Integer activityWaitBooking = indexMapper.activityWaitBooking();
        Integer receWaitBooking = indexMapper.receWaitBooking();
        Integer meetingWaitBooking = indexMapper.meetingWaitBooking();

        Map<String,Integer> map = new HashMap<>();
        Double a = activity == 0 ? 100 : (double)activityWaitBooking*100/activity;
        map.put("waitActivityHandle",a.intValue());

        Double b = rece == 0 ? 100 : (double)receWaitBooking*100/rece;
        map.put("waitReceiveHandle",b.intValue());

        Double c = meet == 0 ? 100 : (double)meetingWaitBooking*100/meet;
        map.put("waitMeetingHandle",c.intValue());

        return ResultUtil.success(map);
    }

    public static void main(String[] args) {
        Integer i = 0;
        Integer j = 10;
        Double a = j == 0 ? 100 : (double)i*100/j;
        System.out.println(a.intValue());
    }


    /**
     * 预约一周的统计
     * @return
     */
    public ResultUtil bookingWeekCount(){


        return null;
    }
}
