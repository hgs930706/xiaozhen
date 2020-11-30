package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yishang.xiaozhen.entity.MsgAction;
import com.yishang.xiaozhen.mapper.MsgActionMapper;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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


    public ResultUtil list(Integer page, Integer size, String createTime, Integer sendStatus, Integer msgType) {
        IPage<MsgAction> ipage = new Page<>(page, size);
        QueryWrapper<MsgAction> query = new QueryWrapper<>();
        if(!StringUtils.isEmpty(createTime)){
            query.ge("create_time",createTime);
        }
        if(sendStatus != null){
            query.eq("send_status", sendStatus);
        }
        if(msgType != null){
            query.eq("msg_type", msgType);
        }
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
        query.orderByDesc("create_time");
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
