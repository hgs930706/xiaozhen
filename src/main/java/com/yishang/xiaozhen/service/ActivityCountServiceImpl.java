package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.entity.ActivityCount;
import com.yishang.xiaozhen.mapper.ActivityCountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动场次表 服务实现类
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Service
public class ActivityCountServiceImpl{

    @Autowired
    private ActivityCountMapper activityCountMapper;

    public Integer insert(ActivityCount object) {
        activityCountMapper.insert(object);
        return null;
    }


    public Map<String,Object> list(Object object) {
        return null;
    }


    public Object details(String activityId) {
        QueryWrapper<ActivityCount> query = new QueryWrapper();
        query.eq("activity_id",activityId);
        query.eq("is_status",1);
        List<ActivityCount> activityCountList = activityCountMapper.selectList(query);
        return activityCountList;
    }


    public Integer update(Object object) {
        return null;
    }

    public Integer delete(String id) {
//        activityCountMapper.deleteById(id);
        //执行逻辑删除
        ActivityCount activityCount = activityCountMapper.selectById(id);
        activityCount.setIsStatus(0);
        activityCountMapper.updateById(activityCount);
        return null;

    }
}
