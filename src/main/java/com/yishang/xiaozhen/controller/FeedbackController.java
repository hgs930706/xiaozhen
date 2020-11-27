package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.service.FeedbackServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResultUtil list(Integer page, Integer size, String createTime, String nickname){
        if (null == page || page <= 0) {
            page = 1;
        }
        if (null == size || size <= 0) {
            size = 10;
        }

        return feedbackServiceImpl.list(page,size,createTime,nickname);
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
