package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yishang.xiaozhen.entity.Feedback;
import com.yishang.xiaozhen.mapper.FeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 反馈表 服务实现类
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Service
public class FeedbackServiceImpl{

    @Autowired
    private FeedbackMapper feedbackMapper;

    public Integer insert(Object object) {
        return null;
    }


    public Map<String,Object> list(Integer page,Integer size,LocalDateTime createDate,String nickname) {
        IPage<Feedback> ipage = new Page<>(0, 10);
        QueryWrapper<Feedback> query = new QueryWrapper<>();
        query.ge("create_time", createDate);
        query.like("create_by", nickname);
        query.eq("is_status", 1);

        ipage = feedbackMapper.selectPage(ipage, query);

        Map<String,Object> map = new HashMap();
        map.put("list",ipage.getRecords());
        map.put("total",ipage.getTotal());
        return map;
    }


    public Object detail(String id) {
        return null;
    }


    public Integer update(Object object) {
        return null;
    }


    public Integer delete(String id) {
        return null;
    }
}