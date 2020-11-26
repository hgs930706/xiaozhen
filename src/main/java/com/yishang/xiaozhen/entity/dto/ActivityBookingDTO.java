package com.yishang.xiaozhen.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yishang.xiaozhen.entity.ActivityBookingImage;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ActivityBookingDTO {

    private String id;

    private String activityName;

    private String openId;

    private String bookingUnit;

    private String bookingPerson;

    private String mobile;

    private Integer joinPeople;

    private Integer bookingCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private Integer approvalStatus;

    private List<ActivityBookingImage> images;
}
