package com.jobRecomment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jobRecomment.domain.bean.Resume;
import com.jobRecomment.mapper.ResumeMapper;
import com.jobRecomment.service.IResumeService;
import com.jobRecomment.service.IStudentService;
import com.jobRecomment.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * ClassName: ResumeServiceImpl
 * Package: com.jobRecomment.service.impl
 * Description:
 * Author by: HUANG
 * Created by: 2024/3/17 11:14
 * Version 1.0
 */
@Service
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements IResumeService {

    @Autowired
    private IStudentService studentService;
    @Override
    public Resume getResume() {
        Integer id = studentService.getResumeId();
        return getById(id);
    }

    @Override
    @Transactional
    public void addResume(Resume resume) {
        // 调用 Mapper 方法插入数据并返回 ID
        Long resumeId = baseMapper.insertAndGetId(resume);
        System.out.println(resumeId+"@@@@@");
        System.out.println(resume.getId()+"#####");
        studentService.addOrUpdateResume(resume.getId());
    }

    @Override
    @Transactional
    public void deleteResume() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        Integer resumeId = studentService.getResumeId();
        System.out.println(resumeId+"######");
        studentService.deleteResumeById();
        removeById(resumeId);
    }

}