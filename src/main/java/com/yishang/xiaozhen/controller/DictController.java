package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.Dict;
import com.yishang.xiaozhen.service.DictServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictServiceImpl dictServiceImpl;


    /**
     * 获取地图管理列表
     * @return
     */
    @GetMapping("maps")
    public ResultUtil maps(){
        Map<String, Object> maps = dictServiceImpl.maps();
        return ResultUtil.success(maps);
    }

    /**
     * 编辑地图
     * @return
     */
    @GetMapping("mapUpdate")
    public ResultUtil mapUpdate(@RequestBody Dict object){
        if(StringUtils.isEmpty(object.getId())){
            return ResultUtil.error("id不能为空!");
        }
        dictServiceImpl.mapUpdate(object);
        return ResultUtil.success();
    }

}
