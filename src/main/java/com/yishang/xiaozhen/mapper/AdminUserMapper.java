package com.yishang.xiaozhen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yishang.xiaozhen.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 管理用户信息 Mapper 接口
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUser> {

}
