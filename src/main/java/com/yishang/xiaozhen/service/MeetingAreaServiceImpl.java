package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yishang.xiaozhen.entity.MeetingArea;
import com.yishang.xiaozhen.mapper.MeetingAreaMapper;
import com.yishang.xiaozhen.util.ImageUploadUtil;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 会议场地表 服务实现类
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Service
public class MeetingAreaServiceImpl{

    @Autowired
    private MeetingAreaMapper meetingAreaMapper;

    public ResultUtil insert(MeetingArea object, MultipartFile file) {
        String imageUrl = ImageUploadUtil.uploadImage(file);
        object.setMeetingImage(imageUrl);
        int insert = meetingAreaMapper.insert(object);
        return ResultUtil.success(insert);
    }

    public ResultUtil update(MeetingArea object, MultipartFile file) {
        String imageUrl = ImageUploadUtil.uploadImage(file);
        object.setMeetingImage(imageUrl);
        meetingAreaMapper.updateById(object);
        return ResultUtil.success();
    }

    public Map<String,Object> list(Integer page,Integer size,String meetingName,Integer isStatus) {
        IPage<MeetingArea> ipage = new Page<>(0, 2);

        QueryWrapper<MeetingArea> query = new QueryWrapper<>();
        query.eq("meeting_name", meetingName);
        query.eq("is_status", isStatus);

        ipage = meetingAreaMapper.selectPage(ipage, query);

        Map<String,Object> map = new HashMap();
        map.put("list",ipage.getRecords());
        map.put("total",ipage.getTotal());

        return map;
    }


    public MeetingArea detail(String id) {
        MeetingArea meetingArea = meetingAreaMapper.selectById(id);
        return meetingArea;
    }





    public Integer delete(String id) {
        return null;
    }
}
