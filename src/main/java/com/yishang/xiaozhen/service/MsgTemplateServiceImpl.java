package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yishang.xiaozhen.entity.MsgTemplate;
import com.yishang.xiaozhen.mapper.MsgTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
public class MsgTemplateServiceImpl {

    @Autowired
    private MsgTemplateMapper msgTemplateMapper;

    public Integer insert(MsgTemplate object) {
        //判断模板名称，是否已存在
        if(!isCheck(object.getTemplateName())){
            return -1;
        }
        int insert = msgTemplateMapper.insert(object);
        return insert;
    }

    private  Boolean isCheck(String templateName){
        QueryWrapper<MsgTemplate> query = new QueryWrapper<>();
        query.eq("template_name",templateName);
        query.eq("is_status",1);
        MsgTemplate msgTemplate = msgTemplateMapper.selectOne(query);
        if(msgTemplate != null){
            return false;
        }
        return true;
    }


    public Map<String,Object> list(String templateName, LocalDateTime createDate) {
        IPage<MsgTemplate> page = new Page<>(0, 10);
        QueryWrapper<MsgTemplate> query = new QueryWrapper<>();
        query.like("template_name", templateName);
        query.ge("create_time", createDate);
        query.eq("is_status", 1);

        page = msgTemplateMapper.selectPage(page, query);

        List<MsgTemplate> records = page.getRecords();
        long total = page.getTotal();
        long current = page.getCurrent();
        long pages = page.getPages();
        long size = page.getSize();

        System.out.println(page);
        Map<String,Object> map = new HashMap();
        map.put("list",records);
        map.put("total",total);
        return map;
    }


    public Object detail(String id) {
        return null;
    }


    public Integer update(MsgTemplate object) {
        if(!isCheck(object.getTemplateName())){
            return -1;
        }
        int update = msgTemplateMapper.updateById(object);
        return update;
    }


    public Integer delete(String id) {
        return null;
    }
}
