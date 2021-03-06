package com.yishang.xiaozhen.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
@Slf4j
@Component
public class ImageUploadUtil {

    @Value("${images.path}")
    private String imagePath;

    /**
     * 图片文件上传
     */
    public String uploadImage(MultipartFile file, HttpServletRequest request){
        if(file == null){
            return "";
        }

        String basePath = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort()+"/images/";


        String fileName = file.getOriginalFilename();//文件原始名称
        String suffixName = fileName.substring(fileName.lastIndexOf("."));//从最后一个.开始截取。截取fileName的后缀名
        String newFileName = UUID.randomUUID() + suffixName; //文件新名称
        //设置文件存储路径，可以存放在你想要指定的路径里面，File.separator
//        String rootPath="f:/mimi/"+ File.separator+"upload/images/"; //上传图片存放位置
        log.info("图片存放路径： {}",imagePath);
        String filePath = imagePath + newFileName;
        File newFile = new File(filePath);
        //判断目标文件所在目录是否存在
        if(!newFile.getParentFile().exists()){
            //如果目标文件所在的目录不存在，则创建父目录
            newFile.getParentFile().mkdirs();
        }

        //将内存中的数据写入磁盘
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            log.error("图片上传异常： {}",e);
        }
        //图片上传保存url
        String imgUrl = basePath + newFileName;
        return imgUrl;
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
    }
}
