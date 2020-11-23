package com.yishang.xiaozhen.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.entity.Dict;
import com.yishang.xiaozhen.mapper.DictMapper;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典表 微信端
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/api/dict")
public class DictApi {


    @Autowired
    private DictMapper dictMapper;

    /**
     * 获取小镇，所有街区
     * @return
     */
    @GetMapping("/steertTypes")
    public String steertTypes(){

        return null;
    }

    /**
     * 获取参观预约简介
     * @return
     */
    @GetMapping("/visitBooking")
    public ResultUtil visitBooking(){
        QueryWrapper<Dict> query = new QueryWrapper<>();
        query.eq("type", "townlet_visit");
        query.eq("is_status", 1);
        Dict dict = dictMapper.selectOne(query);

        return ResultUtil.success(dict.getContent());
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



}
