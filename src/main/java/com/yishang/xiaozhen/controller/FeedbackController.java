package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.service.FeedbackServiceImpl;
import com.yishang.xiaozhen.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>
 * 反馈表 前端控制器
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackServiceImpl feedbackServiceImpl;

    /**
     *
     * @param createTime 查询搜索时间大的记录
     * @param nickname 微信昵称
     * @return
     */
    @GetMapping("/list")
    public String list(Integer page,Integer size,String createTime,String nickname){
        LocalDateTime createDate = LocalDateTime.parse(createTime, DateUtil.dateFormatter3);
        feedbackServiceImpl.list(page,size,createDate,nickname);
        return null;
    }

    /**
     * 导出
     * @param object
     * @return
     */
    @GetMapping("/export")
    public String export(@RequestBody Object object){

        return null;
    }
}
