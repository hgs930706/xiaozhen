package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.EnterpriseInfo;
import com.yishang.xiaozhen.service.EnterpriseInfoServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 * 企业入驻信息表 前端控制器
 * </p>
 *
 * @author hujun
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/enterpriseInfo")
public class EnterpriseInfoController {

    @Autowired
    private EnterpriseInfoServiceImpl enterpriseInfoServiceImpl;

    @GetMapping("/list")
    public ResultUtil list(Integer page,Integer size,String enterpriseName,Integer streetType){
        Map<String, Object> list = enterpriseInfoServiceImpl.list(page,size,enterpriseName, streetType);
        return ResultUtil.success(list);
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(name = "id") String id){

        return null;
    }


    /**
     *
     * @param object
     * @param file 企业头像
     * @param fileQr 企业二维码
     * @return
     */
    @PostMapping("/insert")
    public ResultUtil insert(EnterpriseInfo object, MultipartFile file, MultipartFile fileQr){
        if(object.getStreetType() == null){
            return ResultUtil.error("街道必填项。");
        }
        ResultUtil result = enterpriseInfoServiceImpl.insert(object, file, fileQr);

        return result;
    }


    /**
     *
     * @param object
     * @param file 企业头像
     * @param fileQr 企业二维码
     * @return
     */
    @PostMapping("/update")
    public ResultUtil update(EnterpriseInfo object, MultipartFile file, MultipartFile fileQr){
        if(StringUtils.isEmpty(object.getId())){
            return ResultUtil.error("id不能为空!");
        }
        if(object.getStreetType() == null){
            return ResultUtil.error("街道必填项。");
        }
        ResultUtil result = enterpriseInfoServiceImpl.update(object, file, fileQr);
        return result;
    }

    /**
     * 导出
     * @param object
     * @return
     */
    @GetMapping("/export")
    public String export(@RequestBody Object object){


        return null;
    }
}
