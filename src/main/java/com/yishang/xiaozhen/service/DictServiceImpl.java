package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.constant.DictConstant;
import com.yishang.xiaozhen.entity.Dict;
import com.yishang.xiaozhen.mapper.DictMapper;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ResultUtil maps() {

        QueryWrapper<Dict> query = new QueryWrapper<>();

        query.eq("type", DictConstant.MAP_TYPE);
        query.eq("is_status", 1);

        List<Dict> dicts = dictMapper.selectList(query);


        return ResultUtil.success(dicts);
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
