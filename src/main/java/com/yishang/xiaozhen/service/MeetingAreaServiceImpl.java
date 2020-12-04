package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yishang.xiaozhen.constant.SplitConstant;
import com.yishang.xiaozhen.entity.MeetingArea;
import com.yishang.xiaozhen.mapper.MeetingAreaMapper;
import com.yishang.xiaozhen.util.ImageUploadUtil;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private ImageUploadUtil imageUploadUtil;
    public ResultUtil insert(MeetingArea object, MultipartFile file, HttpServletRequest request) {
        String imageUrl = imageUploadUtil.uploadImage(file, request);
        object.setMeetingImage(imageUrl);
        int insert = meetingAreaMapper.insert(object);
        return ResultUtil.success(insert);
    }

    public ResultUtil update(MeetingArea object, MultipartFile file, HttpServletRequest request) {
        String imageUrl = "";
        if(file == null){
            MeetingArea obj = meetingAreaMapper.selectById(object.getId());
            imageUrl = obj.getMeetingImage();
        }else{
            imageUrl = imageUploadUtil.uploadImage(file, request);
        }
        object.setMeetingImage(imageUrl);
        meetingAreaMapper.updateById(object);
        return ResultUtil.success();
    }

    public ResultUtil list(Integer page,Integer size,String meetingName,Integer isStatus) {
        IPage<MeetingArea> ipage = new Page<>(page, size);
        QueryWrapper<MeetingArea> query = new QueryWrapper<>();
        if(!StringUtils.isEmpty(meetingName)){
            query.like("meeting_name", meetingName);
        }
        if(isStatus != null){
            query.eq("is_status", isStatus);
        }
        query.orderByDesc("order_num");
        ipage = meetingAreaMapper.selectPage(ipage, query);

        Map<String,Object> map = new HashMap();
        map.put("list",ipage.getRecords());
        map.put("total",ipage.getTotal());

        return ResultUtil.success(map);
    }

    public ResultUtil list(Integer page,Integer size) {
        IPage<MeetingArea> ipage = new Page<>(page, size);

        QueryWrapper<MeetingArea> query = new QueryWrapper<>();
        query.eq("is_status", 1);
        query.orderByDesc("order_num");

        ipage = meetingAreaMapper.selectPage(ipage, query);

        Map<String,Object> map = new HashMap();
        map.put("list",ipage.getRecords());
        map.put("total",ipage.getTotal());
        return ResultUtil.success(map);
    }



    public ResultUtil detail(String id) {
        if(StringUtils.isEmpty(id)){
            return ResultUtil.error("会议场地id不能为空");
        }
        MeetingArea meetingArea = meetingAreaMapper.selectById(id);
        return ResultUtil.success(meetingArea);
    }

    public ResultUtil select(String id) {
        if(StringUtils.isEmpty(id)){
            return ResultUtil.error("会议场地id不能为空");
        }
        MeetingArea meetingArea = meetingAreaMapper.selectById(id);

        String meetingTable = meetingArea.getMeetingTable();
        String[] meetingTables = meetingTable.split(SplitConstant.DON);

        String meetingGoods = meetingArea.getMeetingGoods();
        String[] meetingGoodss = meetingGoods.split(SplitConstant.DON);

        String meetingType = meetingArea.getMeetingType();
        String[] meetingTypes = meetingType.split(SplitConstant.DON);

        Map<String,Object> map = new HashMap<>();
        map.put("meetingTable",meetingTables);
        map.put("meetingGoods",meetingGoodss);
        map.put("meetingType",meetingTypes);

        return ResultUtil.success(map);

    }

    public static void main(String[] args) {
        String a ="v,";
        String[] split = a.split(",");
        System.out.println(split);
    }



    public Integer delete(String id) {
        return null;
    }
}
