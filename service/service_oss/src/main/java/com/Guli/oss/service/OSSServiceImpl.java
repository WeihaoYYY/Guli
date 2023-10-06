package com.Guli.oss.service;

import com.Guli.oss.utils.ConstandPropertiesUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.io.InputStream;

@Service
public class OSSServiceImpl implements OSSService{

    @Override
    public String uploadFileAvatar(MultipartFile file) {

        String endPoint = ConstandPropertiesUtils.END_POINT;
        String accessKeyId = ConstandPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstandPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstandPropertiesUtils.BUCKET_NAME;

        //创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

        //上传文件流
        try {
            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();
            //1.在文件名称里面添加随机唯一的值
            String uuid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;
            //2.把文件按照日期进行分类
            //获取当前日期
            //String datePath = new DateTime().toString("yyyy/MM/dd");
            //fileName = datePath + "/" + fileName;
            fileName = "avatar/" + fileName;
            //调用oss方法实现上传
            //第一个参数：Bucket名称
            //第二个参数：上传到oss文件路径和文件名称
            //第三个参数：上传文件输入流
            ossClient.putObject(bucketName, fileName, file.getInputStream());
            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            String url = "https://" + bucketName + "." + endPoint + "/" + fileName;
            //在控制台输出信息
            //System.out.println(url);

            //关闭OSSClient
            ossClient.shutdown();
            return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
