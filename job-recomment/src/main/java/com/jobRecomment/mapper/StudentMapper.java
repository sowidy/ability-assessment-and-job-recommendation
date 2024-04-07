package com.jobRecomment.mapper;

import com.jobRecomment.domain.bean.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author HUANG
 * @since 2024-03-07
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @Insert("insert into student (phone,`name`,password,create_time,update_time) values(#{phone},#{name},#{password},now(),now())")
    void add(@Param("phone") String phone,@Param("name") String name,@Param("password") String password);

    @Update("update student set avatar = #{avatar},update_time = now() where id = #{id}")
    void updateAvatar(@Param("id") Integer id, @Param("avatar") String avatar);

    @Update("update student set resume = #{resume},update_time = now() where id = #{id}")
    void updateResume(@Param("id")Integer id,@Param("resume") String resume);

    @Select("select resume_id from student where id = #{id} ")
    Integer getResumeIdById(Integer id);

    @Update("update student set resume_id = null where id = #{id}")
    void deleteResumeId(Integer id);

    @Select("SELECT * FROM student WHERE remark IS NOT NULL ORDER BY RAND() LIMIT 3")
    List<Student> selectRemarkList();

    @Select("SELECT * FROM student WHERE remark IS NOT NULL")
    List<Student> getAllRemark();

    @Select("SELECT COUNT(ID) FROM student")
    Integer getTotalCount();

    @Select("SELECT COUNT(remark) from student where remark  IS not NULL")
    Integer getRemarkCount();

    @Update("update student set phone = #{phone},update_time = now() where id = #{id}")
    void updatePhone(@Param("id")Integer id ,@Param("phone")String phone);

    int updateBatchById(@Param("students") List<Student> students);
}
