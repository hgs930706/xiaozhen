package com.yishang.xiaozhen.controller;


import com.yishang.xiaozhen.entity.EnterpriseInfo;
import com.yishang.xiaozhen.service.EnterpriseInfoServiceImpl;
import com.yishang.xiaozhen.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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
        if (null == page || page <= 0) {
            page = 1;
        }
        if (null == size || size <= 0) {
            size = 10;
        }
        return enterpriseInfoServiceImpl.list(page,size,enterpriseName, streetType);
    }

    @GetMapping("/detail")
    public String detail(String id){

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
    public ResultUtil insert(EnterpriseInfo object, MultipartFile file, MultipartFile fileQr, HttpServletRequest request){
        if(object.getStreetType() == null){
            return ResultUtil.error("街道必填项。");
        }
        ResultUtil result = enterpriseInfoServiceImpl.insert(object, file, fileQr,request);

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
    public ResultUtil update(EnterpriseInfo object, MultipartFile file, MultipartFile fileQr, HttpServletRequest request){
        if(StringUtils.isEmpty(object.getId())){
            return ResultUtil.error("id不能为空!");
        }
        if(object.getStreetType() == null){
            return ResultUtil.error("街道必填项。");
        }
        ResultUtil result = enterpriseInfoServiceImpl.update(object, file, fileQr,request);
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
