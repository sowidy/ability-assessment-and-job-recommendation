package com.jobRecomment.service;

import com.jobRecomment.domain.bean.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jobRecomment.domain.dto.PageDTO;
import com.jobRecomment.domain.query.RemarkQuery;
import com.jobRecomment.domain.query.StudentQuery;
import com.jobRecomment.domain.vo.RemarkVO;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author HUANG
 * @since 2024-03-07
 */
public interface IStudentService extends IService<Student> {

    Student getByPhone(String phone);

    void addUser(String phone,String name, String password);

    void updateUser(Student user);

    List<Student> getUserList();

    void updatePassword(String newPwd);

    List<Student> getAllStudent(Integer size);

    void updateAvatar(String avatarUrl);

    void uploadResume(String resume);

    Integer getResumeId();

    void addOrUpdateResume(Integer resumeId);

    void deleteResumeById();

    List<RemarkVO> getRemarkList();

    PageDTO<Student> queryStudent(StudentQuery query);

    Student findById(String id);

    List<RemarkVO> getAllRemark();

    void deleteByIds(String ids);

    Integer getTotalCount();

    Integer getRemarkCount();

    PageDTO<RemarkVO> QueryRemark(RemarkQuery query);

    void updatePhone(String phone);

    void updateUserById(Student user);

    List<Student> queryBySkills(String skill);

    void deleteRemarkByIds(String ids);
}
