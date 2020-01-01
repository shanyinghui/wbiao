package com.wbiao.controller;

import com.wbiao.annotation.Log;
import com.wbiao.util.FastdfsClient;
import com.wbiao.util.FastdfsFile;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
public class FileController {
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/jpeg", "image/gif","image/png","image/jpg");
    private static final String PATH = "http://192.168.134.66/";

    @PostMapping("/upload")
    @Log
    public String upload(@RequestParam("file")MultipartFile file) throws Exception{
        String contentType = file.getContentType();
        //检验上传的是否是图片
        if(!CONTENT_TYPES.contains(contentType)){
            return "文件类型不合法！";
        }
        //封装文件信息
        FastdfsFile fastdfsFile = new FastdfsFile(
                file.getOriginalFilename(),
                file.getBytes(),
                StringUtils.getFilenameExtension(file.getOriginalFilename())); //获取文件扩展名
        String[] upload = FastdfsClient.upload(fastdfsFile);
        String path = PATH+upload[0]+"/"+upload[1];
        return path;
    }
}