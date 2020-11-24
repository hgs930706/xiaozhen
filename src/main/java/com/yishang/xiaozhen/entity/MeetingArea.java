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
 * 会议场地表
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ys_meeting_area")
public class MeetingArea extends Model<MeetingArea> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String id;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 会议场地名称
     */
    private String meetingName;

    /**
     * 预约会议场地开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bookingStartTime;

    /**
     * 预约会议场地结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime booingEndTime;

    /**
     * 会议场地地址
     */
    private String meetingAddress;

    /**
     * 会议场地详情
     */
    private String meetingDateil;

    /**
     * 会议场地容纳人数
     */
    private Integer meetingCapacity;

    /**
     * 会议场地备注
     */
    private String meetingRemark;

    /**
     * 会议场地图片
     */
    private String meetingImage;

    /**
     * 会议桌型
     */
    private String meetingTable;

    /**
     * 会议类型
     */
    private String meetingType;

    /**
     * 会议室物品
     */
    private String meetingGoods;

    /**
     * 机构
     */
    private String orgId;

    /**
     * 会议室状态（1正常，0停用）
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
