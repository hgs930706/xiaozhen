package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yishang.xiaozhen.entity.MsgAction;
import com.yishang.xiaozhen.mapper.MsgActionMapper;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Service
public class MsgActionServiceImpl{

    @Autowired
    private MsgActionMapper msgActionMapper;

    public Integer insert(MsgAction object) {
        int insert = msgActionMapper.insert(object);
        return insert;
    }


    public ResultUtil list(Integer page, Integer size, String nickname, Integer sendStatus, String createTime) {
        IPage<MsgAction> ipage = new Page<>(0, 10);
        QueryWrapper<MsgAction> query = new QueryWrapper<>();
//        LocalDateTime createDate = LocalDateTime.parse(createTime, DateUtil.dateFormatter3);
//        query.ge("create_time",createDate);
//        query.like("nickname", nickname);
//        query.eq("send_status", sendStatus);
//        query.eq("is_status", 1);
        ipage = msgActionMapper.selectPage(ipage, query);

        Map<String,Object> map = new HashMap();
        map.put("list",ipage.getRecords());
        map.put("total",ipage.getTotal());
        return ResultUtil.success(map);
    }

    public ResultUtil list(Integer page,Integer size,String openId) {
        IPage<MsgAction> ipage = new Page<>(page,size);
        QueryWrapper<MsgAction> query = new QueryWrapper<>();
//        query.eq("open_id",openId);
        query.eq("is_status", 1);
        ipage = msgActionMapper.selectPage(ipage, query);

        Map<String,Object> map = new HashMap();
        map.put("list",ipage.getRecords());
        map.put("total",ipage.getTotal());
        return ResultUtil.success(map);
    }


    public Object detail(String id) {
        return null;
    }


    public Integer update(Object object) {
        return null;
    }

    public Integer delete(String id) {
        return null;
    }
}
