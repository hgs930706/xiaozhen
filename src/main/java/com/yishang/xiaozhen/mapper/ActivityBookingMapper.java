package com.yishang.xiaozhen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yishang.xiaozhen.entity.ActivityBooking;
import com.yishang.xiaozhen.entity.dto.ActivityBookingDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 活动报名表 Mapper 接口
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Mapper
public interface ActivityBookingMapper extends BaseMapper<ActivityBooking> {


    List<ActivityBookingDTO> selectPage(@Param("page") Integer page,
                                        @Param("size") Integer size,
                                        @Param("activityName") String activityName,
                                        @Param("createTime") LocalDateTime createTime,
                                        @Param("approvalStatus") Integer approvalStatus);

    Integer selectCount(
                                        @Param("activityName") String activityName,
                                        @Param("createTime") LocalDateTime createTime,
                                        @Param("approvalStatus") Integer approvalStatus);

}
