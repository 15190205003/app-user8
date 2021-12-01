package com.hiwoo.utils;


import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * OSS上传、删除文件工具类
 */
@Configuration
public class OSSImageUtils {
    //log日志
    private static Logger logger = LoggerFactory.getLogger(OSSImageUtils.class);

    private static String baseUrl;

    private static String headPortraitFile;

    public String getBaseUrl() {
        return baseUrl;
    }

    @Value("${application.baseUrl}")
    public void setBaseUrl(String baseUrl) {
        OSSImageUtils.baseUrl = baseUrl;
    }

    public String getHeadPortraitFile() {
        return headPortraitFile;
    }

    @Value("${application.headPortraitFile}")
    public void setHeadPortraitFile(String headPortraitFile) {
        OSSImageUtils.headPortraitFile = headPortraitFile;
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static String UploadPicFile(MultipartFile multipartFile, String dirName) {

        //原图路径
        String originalPic = "";
        if (multipartFile != null && multipartFile.getSize() > 0) {
            // 准备数据,文件输入流
            InputStream fis = null;
            try {
                fis = multipartFile.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                logger.debug("上传文件不能为空....");
                return null;
            }

            // 获取文件扩展名
            String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            // 生成原图文件名
            originalPic = sdf.format(new Date()) + RandomStringUtils.randomNumeric(8);
            // 组装文件
            originalPic = originalPic + "." + ext;

            String path = headPortraitFile;
            if (StringUtils.isNotEmpty(dirName)) {
                path = headPortraitFile + "/" + dirName;
            }

            try {
                File file = new File(path);
                if (!file.exists() && !file.isDirectory()) {
                    file.mkdirs();
                    logger.debug("创建图片路径....");
                }
                path += "/" + originalPic;
                File file1 = new File(path);
                multipartFile.transferTo(file1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String resImageUrl = baseUrl + headPortraitFile + "/" + originalPic;
            if (StringUtils.isNotEmpty(dirName)) {
                resImageUrl = baseUrl + headPortraitFile + "/" + dirName + "/" + originalPic;
            }
            return resImageUrl;
        } else {
            new RuntimeException("上传文件不能为空!");
            return null;
        }
    }

    public static String getDefaultheadPortraitFile() {
        return baseUrl + headPortraitFile + "/user/defaultLogo.png";
    }

}
