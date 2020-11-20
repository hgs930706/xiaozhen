package com.yishang.xiaozhen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yishang.xiaozhen.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 反馈表 Mapper 接口
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {

}
