package com.yishang.xiaozhen.entity.dto;

import com.yishang.xiaozhen.entity.ActivityBookingImage;
import lombok.Data;

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

    private String createTime;

    private Integer approvalStatus;

    private List<ActivityBookingImage> images;
}
