package com.yishang.xiaozhen.service;

import com.yishang.xiaozhen.entity.ReceiveBookingImage;
import com.yishang.xiaozhen.mapper.ReceiveBookingImageMapper;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 接待预约图片素材表 服务实现类
 * </p>
 *
 * @author hujun
 * @since 2020-11-20
 */
@Service
public class ReceiveBookingImageServiceImpl{

    @Autowired
    private ReceiveBookingImageMapper receiveBookingImageMapper;

    public ResultUtil insert(ReceiveBookingImage object){
        receiveBookingImageMapper.insert(object);
        return null;
    }
}
