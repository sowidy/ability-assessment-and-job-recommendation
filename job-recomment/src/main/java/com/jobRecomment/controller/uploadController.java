package com.jobRecomment.controller;

import cn.hutool.core.lang.UUID;
import com.jobRecomment.domain.bean.Result;
import com.jobRecomment.domain.bean.Resume;
import com.jobRecomment.utils.AliOSSUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * ClassName: uploadController
 * Package: com.recruitment.controller
 * Description:
 * Author by: HUANG
 * Created by: 2024/3/9 20:26
 * Version 1.0
 */
@RestController
@Api(tags = "上传接口")
public class uploadController {

    @ApiOperation("上传简历接口")
    @PostMapping("/upload/resume")
    public Result<Resume> uploadResume(MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("文件为空");
        }
//        try {
//            // 将文件保存在服务器目录中
//            // 文件名称
//            String uuid = UUID.randomUUID().toString();
//            // 得到上传文件后缀
//            String originalName = file.getOriginalFilename();
//            String suffix = "." + FilenameUtils.getExtension(originalName);
//            // 新生成的文件名称
//            String fileName = uuid + suffix;
//            // 复制文件
//            File path = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\ideatempfile");
//            if (!path.exists()) path.mkdir();
//            File targetFile = new File(path, fileName);
//            FileUtils.writeByteArrayToFile(targetFile, file.getBytes());
//            return Result.success(fileName);
//        } catch (IOException e) {
//            System.out.println("保存文件到服务器（本地）失败");
//        }
//        return Result.error("上传失败");
//         文件名称，防止覆盖
//        String uuid = UUID.randomUUID().toString();
        //获取文件后缀名
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
//        String newFileName = uuid + suffix;
        String newFileName = "data" + suffix;
        //判断目录是否存在
        File path = null;
        try {
//            path = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\ideatempfile");
            path = new File("D:\\data");
            if (!path.exists()) path.mkdir();
            File targetName = new File(path, newFileName);
            FileUtils.writeByteArrayToFile(targetName, file.getBytes());
            Resume resume = new Resume();
            resume.setLinkName(newFileName);
            resume.setOriginName(file.getOriginalFilename());
            resume.setCreateTime(LocalDateTime.now());
            return Result.success(resume);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("上传失败");
    }

    @ApiOperation("下载简历接口")
    @GetMapping("/download/resume")
    public ResponseEntity<byte[]> download(HttpServletRequest request, @RequestParam("filename") String filename) {
//        String directoryPath = System.getProperty("user.home") + "\\AppData\\Roaming\\ideatempfile";
        String directoryPath = "D:\\data";
        File file = new File(directoryPath + File.separator + filename);
        if (file.exists()) {
            try {
                byte[] resumeContent = FileUtils.readFileToByteArray(file);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", filename);
                headers.setContentLength(resumeContent.length);
                return new ResponseEntity<>(resumeContent, headers, HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("用户上传头像接口")
    @PostMapping("/upload/avatar")
    public Result uploadAvatar(MultipartFile image) throws IOException {
        String url = AliOSSUtil.upload(image);
        return Result.success(url);
    }

    @ApiOperation("上传认证材料")
    @PostMapping("/upload/material")
    public Result uploadMaterial(MultipartFile file) throws IOException {
        String url = AliOSSUtil.upload(file);
        return Result.success(url);
    }
}
