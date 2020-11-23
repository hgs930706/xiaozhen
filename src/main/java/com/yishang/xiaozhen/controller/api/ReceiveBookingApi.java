package com.yishang.xiaozhen.controller.api;


import com.yishang.xiaozhen.entity.ReceiveBooking;
import com.yishang.xiaozhen.service.ReceiveBookingServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 接待预约表 微信端
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/api/receiveBooking")
public class ReceiveBookingApi {

    @Autowired
    private ReceiveBookingServiceImpl receiveBookingServiceImpl;


    /**
     *
     * @param object 接待预约实体
     * @return
     */
    @PostMapping("/insert")
    public ResultUtil insert(ReceiveBooking object, MultipartFile[] files){
        ResultUtil result = receiveBookingServiceImpl.insert(object, files);
        return result;
    }
}
