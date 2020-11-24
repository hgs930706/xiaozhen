package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yishang.xiaozhen.constant.DictConstant;
import com.yishang.xiaozhen.entity.Dict;
import com.yishang.xiaozhen.mapper.DictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Service
public class DictServiceImpl{



    @Autowired
    private DictMapper dictMapper;

    public Map<String,Object> maps() {
        IPage<Dict> page = new Page<>(0, 10);
        QueryWrapper<Dict> query = new QueryWrapper<>();

        query.eq("type", DictConstant.MAP_TYPE);
        query.eq("is_status", 1);

        page = dictMapper.selectPage(page, query);

        List<Dict> records = page.getRecords();
        long total = page.getTotal();

        System.out.println(page);
        Map<String,Object> map = new HashMap();
        map.put("list",records);
        map.put("total",total);
        return map;
    }

    public Integer insert(Object object) {
        return null;
    }


    public Map<String,Object> list(Object object) {
        return null;
    }


    public Object detail(String id) {
        return null;
    }


    public Integer update(Object object) {
        return null;
    }

    public Integer mapUpdate(Dict object) {
        dictMapper.updateById(object);
        return null;
    }
}
