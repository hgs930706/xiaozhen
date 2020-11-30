package com.yishang.xiaozhen.controller.api;


import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yishang.xiaozhen.entity.EnterpriseInfo;
import com.yishang.xiaozhen.mapper.EnterpriseInfoMapper;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 企业入驻信息表 微信端
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/api/enterpriseInfo")
public class EnterpriseInfoApi {

    @Autowired
    private EnterpriseInfoMapper enterpriseInfoMapper;

    /**
     * 企业列表，
     * @param streetType 街区类型
     * @return 返回当前街区类型，所有企业
     */
    @GetMapping("/list")
    public ResultUtil list(Integer streetType){
        if(streetType == null){
            return ResultUtil.error("街区类型不能为空");
        }
        QueryWrapper<EnterpriseInfo> query = new QueryWrapper<>();
        query.eq("street_type", streetType);
        query.eq("is_status", 1);
        query.orderByDesc("order_num");
        List<EnterpriseInfo> enterpriseInfos = enterpriseInfoMapper.selectList(query);

        return ResultUtil.success(enterpriseInfos);
    }


    /**
     * 企业详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public ResultUtil detail(String id){
        if(StringUtils.isEmpty(id)){
            return ResultUtil.error("id不能为空");
        }
        EnterpriseInfo enterpriseInfo = enterpriseInfoMapper.selectById(id);

        return ResultUtil.success(enterpriseInfo);
    }



    /**
     * 默认返回一个企业，用于首页的展示
     * @return
     */
    @GetMapping("/default")
    public ResultUtil default_(){
        QueryWrapper<EnterpriseInfo> query = new QueryWrapper<>();
        query.eq("is_status", 1);
        List<EnterpriseInfo> enterpriseInfos = enterpriseInfoMapper.selectList(query);
        if(enterpriseInfos.size() > 0){
            return ResultUtil.success(enterpriseInfos.get(0));
        }
        return ResultUtil.success(new ArrayList<>());
    }

}
