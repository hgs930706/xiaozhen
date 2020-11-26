package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.MsgAction;
import com.yishang.xiaozhen.service.MsgActionServiceImpl;
import com.yishang.xiaozhen.util.DateUtil;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 *  消息日志表 前端控制器
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/msgAction")
public class MsgActionController {

    @Autowired
    private MsgActionServiceImpl msgActionServiceImpl;

    @GetMapping("/list")
    public ResultUtil list(Integer page,Integer size,String nickname,Integer sendStatus,String createTime){
        if (null == page || page <= 0) {
            page = 1;
        }
        if (null == size || size <= 0) {
            size = 10;
        }
        LocalDateTime createDate = LocalDateTime.parse(createTime, DateUtil.dateFormatter3);
        Map<String, Object> list = msgActionServiceImpl.list(page,size,nickname, sendStatus,createDate);
        return ResultUtil.success(list);
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("id") String id){

        return null;
    }


    @PostMapping("/insert")
    public String insert(@RequestBody Object object){

        return null;
    }

    @PostMapping("/update")
    public ResultUtil update(@RequestBody MsgAction object){
        if(StringUtils.isEmpty(object.getId())){
            return ResultUtil.error("id不能为空!");
        }
        return null;
    }

}
