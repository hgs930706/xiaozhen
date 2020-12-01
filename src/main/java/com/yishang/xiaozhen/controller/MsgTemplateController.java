package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.MsgTemplate;
import com.yishang.xiaozhen.service.MsgTemplateServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    public ResultUtil list(Integer page,Integer size,String templateName, String createTime){
        if (null == page || page <= 0) {
            page = 1;
        }
        if (null == size || size <= 0) {
            size = 10;
        }
        return  msgTemplateServiceImpl.list(page,size,templateName,createTime);
    }

    @GetMapping("/detail")
    public ResultUtil detail(String id){
        if(com.alibaba.excel.util.StringUtils.isEmpty(id)){
            return ResultUtil.error("id不能为空");
        }
        Object detail = msgTemplateServiceImpl.detail(id);
        return ResultUtil.success(detail);
    }


    @PostMapping("/insert")
    public ResultUtil insert(@RequestBody MsgTemplate object){
        if(StringUtils.isEmpty(object.getId())){
            Integer insert = msgTemplateServiceImpl.insert(object);
            if(insert == -1){
                return ResultUtil.error("模板名称已存在。");
            }
        }else{
            Integer update = msgTemplateServiceImpl.update(object);
            if(update == -1){
                return ResultUtil.error("模板名称已存在。");
            }
        }
        return ResultUtil.success();
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
