package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.MsgAction;
import com.yishang.xiaozhen.service.MsgActionServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    public ResultUtil list(Integer page,Integer size,String createTime,Integer sendStatus,Integer msgType){
        if (null == page || page <= 0) {
            page = 1;
        }
        if (null == size || size <= 0) {
            size = 10;
        }

        return msgActionServiceImpl.list(page,size,createTime, sendStatus,msgType);
    }

    @GetMapping("/detail")
    public String detail(String id){

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
