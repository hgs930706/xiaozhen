package com.yishang.xiaozhen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.constant.DictConstant;
import com.yishang.xiaozhen.entity.Dict;
import com.yishang.xiaozhen.mapper.DictMapper;
import com.yishang.xiaozhen.service.DictServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private DictMapper dictMapper;

    @GetMapping("/street")
    public ResultUtil street(){
        QueryWrapper<Dict> query = new QueryWrapper<>();
        query.eq("type", DictConstant.TOWNLET_STREET_TYPE);
        query.eq("is_status", 1);
        List<Dict> dicts = dictMapper.selectList(query);
        return ResultUtil.success(dicts);
    }

    @GetMapping("/meetingParams")
    public ResultUtil meetingParams(){
        //返回：会议类型、会议桌型，会议物品
        QueryWrapper<Dict> query = new QueryWrapper<>();
        query.in("type", "meeting_type","table_type","goods_type");
        query.eq("is_status", 1);
        List<Dict> dicts = dictMapper.selectList(query);
        Map<String, List<Dict>> collect = dicts.stream().collect(Collectors.groupingBy(Dict::getType));

        List<Dict> meeting_type = collect.get("meeting_type");
        meeting_type.sort(Comparator.comparing(Dict::getCode));
        List<Dict> table_type = collect.get("table_type");
        table_type.sort(Comparator.comparing(Dict::getCode));
        List<Dict> goods_type = collect.get("goods_type");
        goods_type.sort(Comparator.comparing(Dict::getCode));

        Map<String,Object> map = new HashMap();
        map.put("meeting_type",meeting_type);
        map.put("table_type",table_type);
        map.put("goods_type",goods_type);
        return ResultUtil.success(map);
    }

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
