package com.yishang.xiaozhen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yishang.xiaozhen.entity.RoleAuthority;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色与权限关系表 Mapper 接口
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Mapper
public interface RoleAuthorityMapper extends BaseMapper<RoleAuthority> {

}
