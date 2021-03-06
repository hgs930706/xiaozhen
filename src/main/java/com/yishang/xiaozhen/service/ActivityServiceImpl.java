package com.yishang.xiaozhen.service;

import com.alibaba.fastjson.JSONObject;
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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    private ImageUploadUtil imageUploadUtil;
    @Autowired
    private ActivityCountServiceImpl activityCountServiceImpl;

    @Transactional
    public ResultUtil insert(Activity object,MultipartFile file, HttpServletRequest request) {
        String imageUrl = imageUploadUtil.uploadImage(file, request);
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
    public ResultUtil list(Integer page,Integer size,String activityName,Integer isStatus
            ,String startTime,String endTime) {
        IPage<Activity> ipage = new Page<>(page, size);
        QueryWrapper<Activity> query = new QueryWrapper<>();
        if(!StringUtils.isEmpty(activityName)){
            query.like("activity_name",activityName);
        }
        if(isStatus != null){
            query.eq("is_status",isStatus);
        }
        //activity_name LIKE ? AND start_time >= ? AND end_time <= ?
        if(!StringUtils.isEmpty(startTime)){
            query.ge("start_time",startTime);
        }
        if(!StringUtils.isEmpty(endTime)){
            query.le("end_time",endTime);
        }
        query.orderByDesc("create_time");
        ipage = activityMapper.selectPage(ipage, query);
        List<Activity> records = ipage.getRecords();
        for (Activity record : records) {
            List<ActivityCount> details = activityCountServiceImpl.details(record.getId());
            record.setCount(details.size());
        }
        Map<String,Object> map = new HashMap();
        map.put("list",records);
        map.put("total",ipage.getTotal());
        return ResultUtil.success(map);
    }

    /**
     * 微信端默认展示两个活动
     * @param page
     * @param size
     * @return
     */
    public ResultUtil list(Integer page,Integer size) {
        IPage<Activity> ipage = new Page<>(page, size);

        QueryWrapper<Activity> query = new QueryWrapper<>();
        query.eq("is_status", 1);
        query.orderByDesc("start_time");

        ipage = activityMapper.selectPage(ipage, query);
        Map<String,Object> map = new HashMap();
        map.put("list",ipage.getRecords());
        map.put("total",ipage.getTotal());
        return ResultUtil.success(map);
    }


    /**
     * 用户端
     * @param id
     * @return
     */
    public ResultUtil detail(String id) {
        Activity activity = activityMapper.selectById(id);
        List<ActivityCount> details = activityCountServiceImpl.details(id);
        Optional<ActivityCount> min = details.stream().min(Comparator.comparing(ActivityCount::getActivityCountStartTime));
        Optional<ActivityCount> max = details.stream().max(Comparator.comparing(ActivityCount::getActivityCountEndTime));


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activityName",activity.getActivityName());
        jsonObject.put("activityImage",activity.getActivityImage());
        min.ifPresent(m ->jsonObject.put("activityStartTime",m.getActivityCountStartTime()));
        max.ifPresent(m -> jsonObject.put("activityEndTime",m.getActivityCountEndTime()));
        jsonObject.put("activityCount",details.size());
        jsonObject.put("activityRemark",activity.getActivityRemark());
        jsonObject.put("activityAddress",activity.getActivityAddress());
        jsonObject.put("activityDetail",activity.getActivityDetail());

        return ResultUtil.success(jsonObject);
    }


    public ResultUtil detail2(String id) {
        Activity activity = activityMapper.selectById(id);
        List<ActivityCount> details = activityCountServiceImpl.details(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activityName",activity.getActivityName());
        jsonObject.put("activityImage",activity.getActivityImage());
        jsonObject.put("activityRemark",activity.getActivityRemark());
        jsonObject.put("activityAddress",activity.getActivityAddress());
        jsonObject.put("activityDetail",activity.getActivityDetail());
        jsonObject.put("countDetails",details);
        return ResultUtil.success(jsonObject);
    }


    public ResultUtil update(Activity object, MultipartFile file, HttpServletRequest request) {
        String imageUrl = "";
        if(file == null){
            Activity activity = activityMapper.selectById(object.getId());
            imageUrl = activity.getActivityImage();
        }else{
            imageUrl = imageUploadUtil.uploadImage(file, request);
        }
        object.setActivityImage(imageUrl);
        activityMapper.updateById(object);
        return ResultUtil.success();
    }

    public Integer delete(String id) {
        return null;
    }

    public ResultUtil banner(){
        List<String> banner = activityMapper.banner();

        return ResultUtil.success(banner);
    }
}
