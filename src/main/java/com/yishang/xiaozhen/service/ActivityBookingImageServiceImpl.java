package com.yishang.xiaozhen.service;

import com.yishang.xiaozhen.entity.ActivityBookingImage;
import com.yishang.xiaozhen.mapper.ActivityBookingImageMapper;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 活动报名图片素材表 服务实现类
 * </p>
 *
 * @author hujun
 * @since 2020-11-20
 */
@Service
public class ActivityBookingImageServiceImpl{

    @Autowired
    private ActivityBookingImageMapper activityBookingImageMapper;

    public ResultUtil insert(ActivityBookingImage object){
        activityBookingImageMapper.insert(object);
        return null;
    }

}
