package com.yishang.xiaozhen.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 会议场地预约表
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ys_meeting_area_booking")
public class MeetingAreaBooking extends Model<MeetingAreaBooking> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String id;

    /**
     * 会议场地id
     */
    private String meetingAreaId;

    /**
     * 预订人/预约单位
     */
    private String bookingPerson;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 备注信息
     */
    private String remarkInfo;

    /**
     * 预约开始时间
     */
    private LocalDateTime bookingStartTime;

    /**
     * 预约结束时间
     */
    private LocalDateTime bookingEndTime;

    /**
     * 接待地址
     */
    private String receiveAddress;

    /**
     * 参会人数
     */
    private Integer joinPeople;

    /**
     * 会议桌型(课桌式、分组式)
     */
    private String meetingTable;

    /**
     * 会议类型(企业会议、公务接待、政府性会议、其它会议)
     */
    private String meetingType;

    /**
     * 会议物品(矿泉水、茶杯、咖啡、纸笔、指示牌、投影、LED屏、音响设备、其它设备)
     */
    private String meetingGoods;

    /**
     * 预约状态(0待审批，1审批通过，2审批拒绝)
     */
    private Integer approvalStatus;

    /**
     * 审批人
     */
    private String approvalBy;

    /**
     * 审批时间
     */
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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
