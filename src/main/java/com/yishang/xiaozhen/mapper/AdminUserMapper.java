package com.yishang.xiaozhen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yishang.xiaozhen.entity.AdminUser;
import com.yishang.xiaozhen.entity.dto.AdminUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<AdminUserDTO> selectPage(@Param("page") Integer page,
                                  @Param("size") Integer size,
                                  @Param("username") String username,
                                  @Param("roleId") String roleId,
                                  @Param("isStatus") Integer isStatus);

    Integer selectCount(
            @Param("username") String username,
            @Param("roleId") String roleId,
            @Param("isStatus") Integer isStatus);
}
