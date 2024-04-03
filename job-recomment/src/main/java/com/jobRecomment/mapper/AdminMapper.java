package com.jobRecomment.mapper;

import com.jobRecomment.domain.bean.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 管理员信息表 Mapper 接口
 * </p>
 *
 * @author HUANG
 * @since 2024-03-22
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    @Insert("insert into admin (phone,password,create_time) values(#{phone},#{password},now())")
    void add(String phone, String md5String);
}
