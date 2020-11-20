package com.yishang.xiaozhen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yishang.xiaozhen.entity.WxUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 微信用户信息 Mapper 接口
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Mapper
public interface WxUserMapper extends BaseMapper<WxUser> {

}
