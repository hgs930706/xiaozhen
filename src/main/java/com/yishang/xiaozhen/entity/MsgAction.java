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
 * 消息日志表
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ys_msg_action")
public class MsgAction extends Model<MsgAction> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String id;


    /**
     * 1活动消息、2接待预约消息、3会议场地预约消息、0其它
     */
    private Integer msgType;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 当前状态
     */
    private Integer approvalStatus;

    /**
     * 预约人、预约单位
     */
    private String bookingPerson;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 预约时间
     */
    private LocalDateTime bookingTime;

    /**
     * 接待地址
     */
    private String receiveAddress;

    /**
     * 推送消息状态（1成功，0失败）
     */
    private Integer sendStatus;


    /**
     * 微信昵称
     */
    private String nickname;

    /**
     * openid来区分用户
     */
    private String openId;


    /**
     * 状态（1正常，0停用）
     */
    private Integer isStatus;

    /**
     * 机构
     */
    private String orgId;

    /**
     *
     */
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
