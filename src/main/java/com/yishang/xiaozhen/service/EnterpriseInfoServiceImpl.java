package com.yishang.xiaozhen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yishang.xiaozhen.entity.EnterpriseInfo;
import com.yishang.xiaozhen.mapper.EnterpriseInfoMapper;
import com.yishang.xiaozhen.util.ImageUploadUtil;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 企业入驻信息表 服务实现类
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@Service
public class EnterpriseInfoServiceImpl{

    @Autowired
    private EnterpriseInfoMapper enterpriseInfoMapper;

    public ResultUtil insert(EnterpriseInfo object, MultipartFile file, MultipartFile fileQr, HttpServletRequest request) {
        String imageUrl = ImageUploadUtil.uploadImage(file, request);
        String imageUrlQr = ImageUploadUtil.uploadImage(fileQr, request);
        object.setEnterpriseLogo(imageUrl);
        object.setEnterpriseQr(imageUrlQr);
        enterpriseInfoMapper.insert(object);
        return ResultUtil.success();
    }


    public ResultUtil list(Integer page,Integer size,String enterpriseName,Integer streetType) {
        IPage<EnterpriseInfo> ipage = new Page<>(page, size);
        QueryWrapper<EnterpriseInfo> query = new QueryWrapper<>();
//        query.like("enterprise_name", enterpriseName);
//        query.eq("street_type", streetType);
        query.eq("is_status", 1);

        ipage = enterpriseInfoMapper.selectPage(ipage, query);
        Map<String,Object> map = new HashMap();
        map.put("list",ipage.getRecords());
        map.put("total",ipage.getTotal());
        return ResultUtil.success(map);
    }


    public Object detail(String id) {
        return null;
    }


    public ResultUtil update(EnterpriseInfo object, MultipartFile file, MultipartFile fileQr, HttpServletRequest request) {
        String imageUrl = ImageUploadUtil.uploadImage(file, request);
        String imageUrlQr = ImageUploadUtil.uploadImage(fileQr, request);
        object.setEnterpriseLogo(imageUrl);
        object.setEnterpriseQr(imageUrlQr);
        enterpriseInfoMapper.updateById(object);
        return ResultUtil.success();
    }


    public Integer delete(String id) {
        return null;
    }

}
