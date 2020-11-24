package com.yishang.xiaozhen.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.entity.Dict;
import com.yishang.xiaozhen.mapper.DictMapper;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
