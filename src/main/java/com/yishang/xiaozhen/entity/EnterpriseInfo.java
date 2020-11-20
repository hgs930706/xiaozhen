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
 * 企业入驻信息表
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ys_enterprise_info")
public class EnterpriseInfo extends Model<EnterpriseInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String id;

    /**
     * 企业排序
     */
    private Integer orderNum;

    /**
     * 企业名称
     */
    private String enterpriseName;

    /**
     * 企业手机号
     */
    private String enterpriseMobile;

    /**
     * 企业二维码
     */
    private String enterpriseQr;

    /**
     * 企业网站
     */
    private String enterpriseWeb;

    /**
     * 企业logo
     */
    private String enterpriseLogo;

    /**
     * 企业入驻地址
     */
    private String enterpriseEnterAddress;

    /**
     * 企业详情
     */
    private String enterpriseDetail;

    /**
     * 街区类型
     */
    private Integer streetType;

    /**
     * 状态（1正常，0停用）
     */
    private Integer isStatus;

    /**
     * 机构
     */
    private String orgId;

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
