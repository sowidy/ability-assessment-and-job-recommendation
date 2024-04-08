package com.jobRecomment.controller;

import com.jobRecomment.domain.bean.Enterprise;
import com.jobRecomment.domain.bean.Result;
import com.jobRecomment.domain.bean.Resume;
import com.jobRecomment.domain.bean.Student;
import com.jobRecomment.domain.dto.PageDTO;
import com.jobRecomment.domain.query.EnterpriseQuery;
import com.jobRecomment.domain.query.StudentQuery;
import com.jobRecomment.domain.vo.RemarkVO;
import com.jobRecomment.service.IEnterpriseService;
import com.jobRecomment.service.IResumeService;
import com.jobRecomment.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: HomeController
 * Package: com.jobRecomment.controller
 * Description:
 * Author by: HUANG
 * Created by: 2024/3/14 18:42
 * Version 1.0
 */
@Api(tags = "公共接口")
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private IEnterpriseService enterpriseService;
    @Autowired
    private IStudentService studentService;

    @Autowired
    private IResumeService resumeService;

    @GetMapping("/enterprise/list")
    @ApiOperation("获取所有认证企业列表接口")
    public Result getAllAuthEnterprise(Integer size){
        List<Enterprise> e = enterpriseService.getAllAuthList(size);
        return Result.success(e);
    }
    @ApiOperation("随机获取几条数据")
    @GetMapping("/getByRandom")
    public Result<List<Enterprise>> getByRandomSize(Integer size){
        List<Enterprise> randomSize = enterpriseService.getByRandomSize(size);
        return Result.success(randomSize);
    }
    @GetMapping("/student/list")
    @ApiOperation("获取所有学生列表接口")
    public Result getAllStudent(Integer size){
        List<Student> s = studentService.getAllStudent(size);
        return Result.success(s);
    }
    @ApiOperation("按不同条件查询企业")
    @GetMapping("/queryByCondition")
    public Result<PageDTO<Enterprise>> conditionQuery(EnterpriseQuery query){
        System.out.println(query);
        PageDTO<Enterprise> list = enterpriseService.conditionQuery(query);
        return Result.success(list);
    }

    @ApiOperation("根据期望职业查询职位")
    @GetMapping("/queryByName")
    public Result<List<Enterprise>> queryByName(@RequestParam String title){
//        System.out.println(name);
        List<Enterprise> enterprises = enterpriseService.queryByName(title);
        return Result.success(enterprises);
    }

    @ApiOperation("根据id查询企业")
    @GetMapping("/getEnterpriseByID/{id}")
    public Result findEnterpriseById(@PathVariable String id){
        return Result.success(enterpriseService.findById(id));
    }


    @ApiOperation("按不同条件查询学生")
    @GetMapping("/query/student")
    public Result<PageDTO<Student>> queryStudent(StudentQuery query) {
//        System.out.println(query);
        PageDTO<Student> list = studentService.queryStudent(query);
        return Result.success(list);
    }
    @ApiOperation("通过id查询学生")
    @GetMapping("/getStudentByID/{id}")
    public Result findStudentById(@PathVariable String id){
        return Result.success(studentService.findById(id));
    }

    @ApiOperation("根据id查询简历")
    @GetMapping("/resumeById")
    public Result getResumeById(String id){
        Resume resume = resumeService.getById(id);
        return  Result.success(resume);
    }
}
