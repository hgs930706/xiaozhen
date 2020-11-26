package com.yishang.xiaozhen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface IndexMapper {

    //用户总数
    Integer userSum();

    //今日新增用户
    Integer  todayNewlyAddedUser(@Param("todayStart") LocalDateTime todayStart);


    //本周接待人数、今日待接待
    Integer weekActivityReceiveCounts(@Param("weekLocalDateTime") LocalDateTime weekLocalDateTime);
    Integer weekReceReceiveCounts(@Param("weekLocalDateTime") LocalDateTime weekLocalDateTime);
    Integer weekMeetReceiveCounts(@Param("weekLocalDateTime") LocalDateTime weekLocalDateTime);



    //全部、预约待处理
    Integer  activityWaitBooking();
    Integer  receWaitBooking();
    Integer  meetingWaitBooking();

    // 图表
    List<Map<String,String>> chartActivity();
    List<Map<String,String>> chartRece();
    List<Map<String,String>> chartMeeting();
}
