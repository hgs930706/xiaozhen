package com.yishang.xiaozhen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yishang.xiaozhen.entity.MeetingAreaBooking;
import com.yishang.xiaozhen.entity.dto.MeetingAreaBookingDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 会议场地预约表 Mapper 接口
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Mapper
public interface MeetingAreaBookingMapper extends BaseMapper<MeetingAreaBooking> {

    List<MeetingAreaBookingDTO> selectPage(@Param("page") Integer page,
                                           @Param("size") Integer size,
                                           @Param("meetingName") String meetingName,
                                           @Param("bookingStartDate") LocalDateTime bookingStartDate,
                                           @Param("bookingEndDate") LocalDateTime bookingEndDate,
                                           @Param("approvalStatus") Integer approvalStatus);

    Integer selectCount(
            @Param("meetingName") String meetingName,
            @Param("bookingStartDate") LocalDateTime bookingStartDate,
            @Param("bookingEndDate") LocalDateTime bookingEndDate,
            @Param("approvalStatus") Integer approvalStatus);
}
