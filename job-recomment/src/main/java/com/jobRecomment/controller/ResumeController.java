package com.jobRecomment.controller;

import com.jobRecomment.domain.bean.Result;
import com.jobRecomment.domain.bean.Resume;
import com.jobRecomment.service.IResumeService;
import com.jobRecomment.utils.FileExtract;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: ResumeController
 * Package: com.jobRecomment.controller
 * Description:
 * Author by: HUANG
 * Created by: 2024/3/17 11:19
 * Version 1.0
 */
@Api(tags = "简历接口")
@RestController
@RequestMapping("/resume")
public class ResumeController {

    @Autowired
    private IResumeService resumeService;

    @GetMapping
    @ApiOperation("获取简历接口")
    public Result<Resume> getResume(){
        return Result.success(resumeService.getResume());
    }


    @ApiOperation("上传简历接口")
    @PostMapping
    public Result addResume(@RequestBody Resume resume){
//        System.out.println(resume.getOriginName());
        boolean added = resumeService.addResume(resume);
        return added?Result.success("获取成功"):Result.error("获取失败");
    }

    @ApiOperation("删除简历接口")
    @DeleteMapping
    public Result deleteResume(){
        resumeService.deleteResume();
        return Result.success();
    }
}
