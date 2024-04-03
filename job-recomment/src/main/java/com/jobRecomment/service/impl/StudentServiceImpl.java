package com.jobRecomment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jobRecomment.domain.bean.Enterprise;
import com.jobRecomment.domain.bean.Student;
import com.jobRecomment.domain.dto.PageDTO;
import com.jobRecomment.domain.query.RemarkQuery;
import com.jobRecomment.domain.query.StudentQuery;
import com.jobRecomment.domain.vo.RemarkVO;
import com.jobRecomment.mapper.StudentMapper;
import com.jobRecomment.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jobRecomment.utils.Md5Util;
import com.jobRecomment.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author HUANG
 * @since 2024-03-07
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Override
    public Student getByPhone(String phone) {
        return lambdaQuery().eq(Student::getPhone, phone).one();
    }

    @Override
    public void addUser(String phone,String name, String password) {
        baseMapper.add(phone,name, Md5Util.getMD5String(password));
    }

    @Override
    public void updateUser(Student user) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        user.setId(id);
        user.setUpdateTime(LocalDateTime.now());
        updateById(user);
    }

    @Override
    public List<Student> getUserList() {
        return list();
    }

    @Override
    public void updatePassword(String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        Student u = new Student();
        u.setPassword(Md5Util.getMD5String(newPwd));
        u.setId(id);
        u.setUpdateTime(LocalDateTime.now());
        updateById(u);
    }

    @Override
    public List<Student> getAllStudent() {
        return lambdaQuery().list();
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        baseMapper.updateAvatar(id, avatarUrl);
    }

    @Override
    public void uploadResume(String resume) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        baseMapper.updateResume(id, resume);
    }

    @Override
    public Integer getResumeId() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        return baseMapper.getResumeIdById(id);
    }

    @Override
    public void addOrUpdateResume(Integer resumeId) {
        Student student = new Student();
        student.setResumeId(resumeId);
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        QueryWrapper<Student> wrapper = new QueryWrapper<Student>()
                .eq("id", id);
        baseMapper.update(student, wrapper);
    }

    @Override
    public void deleteResumeById() {
        // 从 ThreadLocal 中获取当前登录学生的 ID
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        baseMapper.deleteResumeId(id);
    }

    @Override
    public List<RemarkVO> getRemarkList() {
        List<Student> list = baseMapper.selectRemarkList();
        List<RemarkVO> voList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Student student : list) {
                RemarkVO remarkVO = BeanUtil.copyProperties(student, RemarkVO.class);
                voList.add(remarkVO);
            }
        }
        return voList;
    }

    @Override
    public PageDTO<Student> queryStudent(StudentQuery query) {
        Page<Student> page =query.toMpPage();
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(query.getName())) {
            wrapper.like("name", query.getName());
        }
//        if (query.getBornYear() != null) {
//            wrapper.like("born_year", query.getBornYear());
//        }
        if (StringUtils.isNotBlank(query.getGender())) {
            wrapper.eq("gender", query.getGender());
        }
        if (StringUtils.isNotBlank(query.getEducation())) {
            wrapper.like("education", query.getEducation());
        }
        if (StringUtils.isNotBlank(query.getSchool())) {
            wrapper.like("school", query.getSchool());
        }
        if (StringUtils.isNotBlank(query.getMajor())) {
            wrapper.like("major", query.getMajor());
        }

        Page<Student> p = baseMapper.selectPage(page, wrapper);
        return PageDTO.of(p, Student.class);
    }

    @Override
    public Student findById(String id) {
        return getById(id);
    }

    @Override
    public List<RemarkVO> getAllRemark() {
        List<Student> list = baseMapper.getAllRemark();
        List<RemarkVO> vo = new ArrayList<>();
        for (Student s:list) {
            vo.add(BeanUtil.copyProperties(s,RemarkVO.class));
        }
        return vo;
    }

    @Override
    public void deleteByIds(String ids) {
        ids = ids.replaceAll("\\[|\\]", "").trim();
        String[] idArray = ids.split(",");
        baseMapper.deleteBatchIds(Arrays.asList(idArray));
    }

    @Override
    public Integer getTotalCount() {
        return baseMapper.getTotalCount();
    }

    @Override
    public Integer getRemarkCount() {
        return baseMapper.getRemarkCount();
    }

    @Override
    public PageDTO<RemarkVO> QueryRemark(RemarkQuery query) {
        Page<Student> page =query.toMpPage();
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(query.getName())){
            wrapper.like("name",query.getName());
        }
        if(StringUtils.isNotBlank(query.getRemark())){
            wrapper.like("remark",query.getRemark());
        }
        if(StringUtils.isNotBlank(query.getRemarkText())){
            wrapper.like("remark_text",query.getRemarkText());
        }
        if(StringUtils.isNotBlank(query.getTotalScore())){
            wrapper.like("total_score",query.getTotalScore());
        }
        wrapper.isNotNull("remark").isNotNull("remark_text").isNotNull("total_score");
        Page<Student> p = baseMapper.selectPage(page, wrapper);
//        RemarkVO vo= BeanUtil.copyProperties(p,RemarkVO.class);
        return PageDTO.of(p, RemarkVO.class);
    }

    @Override
    public void updatePhone(String phone) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        baseMapper.updatePhone(id,phone);
    }

}
