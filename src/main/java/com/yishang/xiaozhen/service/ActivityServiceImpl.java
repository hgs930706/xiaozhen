package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yishang.xiaozhen.entity.Activity;
import com.yishang.xiaozhen.entity.ActivityCount;
import com.yishang.xiaozhen.mapper.ActivityMapper;
import com.yishang.xiaozhen.util.ImageUploadUtil;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Service
public class ActivityServiceImpl{

    @Autowired
    private ActivityMapper activityMapper;


    @Autowired
    private ActivityCountServiceImpl activityCountServiceImpl;

    @Transactional
    public ResultUtil insert(Activity object,MultipartFile file) {
        String imageUrl = ImageUploadUtil.uploadImage(file);
        //新增活动
        object.setActivityImage(imageUrl);
        activityMapper.insert(object);

        //新增活动场次。默认第一场
        ActivityCount activityCount = new ActivityCount();
        activityCount.setCount(1);
        activityCount.setActivityCountStartTime(object.getStartTime());
        activityCount.setActivityCountEndTime(object.getEndTime());
        activityCount.setActivityId(object.getId());//回显主键
        activityCountServiceImpl.insert(activityCount);
        return ResultUtil.success();
    }


    /**
     *
     * @param activityName
     * @param isStatus
     * @param startTime
     * @param endTime
     * @return
     */
    public Map<String,Object> list(Integer page,Integer size,String activityName,Integer isStatus
            ,String startTime,String endTime) {
        // todo 这个返回的数据，要自定义返回了，因为要统计场次，活动多个场次的起始时间
        IPage<Activity> ipage = new Page<>(0, 2);

        QueryWrapper<Activity> query = new QueryWrapper<>();
        query.eq("activity_name", activityName);
        query.eq("is_status", isStatus);

        ipage = activityMapper.selectPage(ipage, query);
        Map<String,Object> map = new HashMap();
        map.put("list",ipage.getRecords());
        map.put("total",ipage.getTotal());
        return map;
    }


    public Object detail(String id) {
        Activity activity = activityMapper.selectById(id);
        Object details = activityCountServiceImpl.details(id);
        return details;
    }


    public ResultUtil update(Activity object, MultipartFile file) {
        String imageUrl = ImageUploadUtil.uploadImage(file);
        object.setActivityImage(imageUrl);
        int i = activityMapper.updateById(object);
        return ResultUtil.success(i);
    }

    public Integer delete(String id) {
        return null;
    }
}
