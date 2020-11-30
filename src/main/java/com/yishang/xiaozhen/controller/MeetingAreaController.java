package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.MeetingArea;
import com.yishang.xiaozhen.service.MeetingAreaServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会议场地表 前端控制器
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/meetingArea")
public class MeetingAreaController {

    @Autowired
    private MeetingAreaServiceImpl meetingAreaServiceImpl;


    /**
     *
     * @param meetingName 会议室名字
     * @return
     */
    @GetMapping("/list")
    public ResultUtil list(Integer page,Integer size,String meetingName){
        if (null == page || page <= 0) {
            page = 1;
        }
        if (null == size || size <= 0) {
            size = 10;
        }

        return  meetingAreaServiceImpl.list(page,size,meetingName);
    }

    @GetMapping("/detail")
    public ResultUtil detail(String id){
        if(StringUtils.isEmpty(id)){
            return ResultUtil.error("id不能为空");
        }
        ResultUtil detail = meetingAreaServiceImpl.detail(id);
        return ResultUtil.success(detail);
    }


    @PostMapping("/insert")
    public ResultUtil insert(MeetingArea meetingArea, MultipartFile file, HttpServletRequest request){
        ResultUtil result = meetingAreaServiceImpl.insert(meetingArea,file,request);
        return result;
    }

    @PostMapping("/update")
    public ResultUtil update(MeetingArea object, MultipartFile file, HttpServletRequest request){
        if(StringUtils.isEmpty(object.getId())){
            return ResultUtil.error("id不能为空!");
        }
        meetingAreaServiceImpl.update(object,file,request);
        return null;
    }
}
