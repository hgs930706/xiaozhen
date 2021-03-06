package com.yishang.xiaozhen.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 活动报名表
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ys_activity_booking")
public class ActivityBooking extends Model<ActivityBooking> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String id;

    /**
     * 活动表id
     */
    private String activityId;

    /**
     * 活动场次表id
     */
    private String activityCountId;

    /**
     * 预订人
     */
    private String bookingPerson;

    /**
     * 预约单位
     */
    private String bookingUnit;

    /**
     * 预定场次
     */
    private Integer bookingCount;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 参与人数
     */
    private Integer joinPeople;

    /**
     * 活动报名状态(0待审批，1审批通过，2审批拒绝)
     */
    private Integer approvalStatus;

    /**
     * 审批人
     */
    private String approvalBy;

    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvalTime;

    /**
     * 审批拒绝原因
     */
    private String approvalRemark;

    private String openId;

    /**
     * 机构
     */
    private String orgId;

    /**
     * 状态（1正常，0停用）
     */
    private Integer isStatus;

    private String createBy;

    //出参
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

//    @TableField(exist = false)
//    private String msgType = "activityType";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
