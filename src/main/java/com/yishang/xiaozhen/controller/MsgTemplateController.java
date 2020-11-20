package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.MsgTemplate;
import com.yishang.xiaozhen.service.MsgTemplateServiceImpl;
import com.yishang.xiaozhen.util.DateUtil;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 *  消息模板表 前端控制器
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/template")
public class MsgTemplateController {

    @Autowired
    private MsgTemplateServiceImpl msgTemplateServiceImpl;

    @GetMapping("/list")
    public ResultUtil list(String templateName, String createTime){
        LocalDateTime createDate = LocalDateTime.parse(createTime, DateUtil.dateFormatter3);
        Map<String, Object> list = msgTemplateServiceImpl.list(templateName,createDate);
        return ResultUtil.success(list);
    }

    @GetMapping("/detail")
    public ResultUtil detail(@RequestParam(name = "id") String id){
        msgTemplateServiceImpl.detail(id);
        return null;
    }


    @PostMapping("/insert")
    public ResultUtil insert(@RequestBody MsgTemplate object){
        Integer insert = msgTemplateServiceImpl.insert(object);
        if(insert == -1){
            return ResultUtil.error("模板名称已存在。");
        }
        return ResultUtil.success("新增模板成功。");
    }

    @PostMapping("/update")
    public ResultUtil update(@RequestBody MsgTemplate object){
        if(StringUtils.isEmpty(object.getId())){
            return ResultUtil.error("id不能为空!");
        }
        Integer update = msgTemplateServiceImpl.update(object);
        if(update == -1){
            return ResultUtil.error("模板名称已存在。");
        }
        return ResultUtil.success("更新模板成功。");
    }
}
