package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.MeetingArea;
import com.yishang.xiaozhen.service.MeetingAreaServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * @param isStatus 会议室状态
     * @return
     */
    @GetMapping("/list")
    public String list(Integer page,Integer size,String meetingName,Integer isStatus){
        meetingAreaServiceImpl.list(page,size,meetingName,isStatus);
        return null;
    }

    @GetMapping("/detail")
    public ResultUtil detail(@RequestParam(name = "id") String id){
        meetingAreaServiceImpl.detail(id);
        return ResultUtil.success();
    }


    @PostMapping("/insert")
    public ResultUtil insert(MeetingArea meetingArea, MultipartFile file){
        ResultUtil result = meetingAreaServiceImpl.insert(meetingArea,file);
        return ResultUtil.success(result);
    }

    @PostMapping("/update")
    public ResultUtil update(MeetingArea object, MultipartFile file){
        if(StringUtils.isEmpty(object.getId())){
            return ResultUtil.error("id不能为空!");
        }
        meetingAreaServiceImpl.update(object,file);
        return null;
    }
}
