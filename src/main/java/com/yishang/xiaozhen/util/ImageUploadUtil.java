package com.yishang.xiaozhen.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class ImageUploadUtil {

    public static String uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            return "";
        }
        //文件名
        String fileName = file.getOriginalFilename();
        // 后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 新文件名
        fileName = UUID.randomUUID() + suffixName;
        // 上传后的路径
        String filePath = "D:\\image_xz\\";
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(filePath + fileName);

        return filePath + fileName;
    }

    private void uploadImage(MultipartFile file, HttpServletRequest request){
        try {
            //-------把图片文件写入磁盘 start ----------------
            File dest = new File("D://image_xz//234.png");
            FileOutputStream fos = new FileOutputStream(dest);
            //获取本地文件输入流
            InputStream stream = file.getInputStream();
            //写入目标文件
            byte[] buffer = new byte[1024 * 1024];
            int byteRead = 0;
            //stream.read(buffer) 每次读到的数据存放在 buffer 数组中
            while ((byteRead = stream.read(buffer)) != -1) {
                //在 buffer 数组中 取出数据 写到 （输出流）磁盘上
                fos.write(buffer, 0, byteRead);
                fos.flush();
            }
            fos.close();
            stream.close();
            //-------把图片文件写入磁盘 end ----------------


            //服务器图片地址
            String baseURL = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort()+"/mimi/upload/images/";
            String imgUrl = baseURL+"新文件名字";
            // http://localhost:8081/mimi/upload/images/新文件名字


        }catch (Exception e){
            System.out.println("");
        }
    }
}
