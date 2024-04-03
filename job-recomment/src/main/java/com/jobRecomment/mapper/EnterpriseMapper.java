package com.jobRecomment.mapper;

import com.jobRecomment.domain.bean.Enterprise;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

/**
 * <p>
 * 企业信息表 Mapper 接口
 * </p>
 *
 * @author HUANG
 * @since 2024-03-09
 */
@Mapper
public interface EnterpriseMapper extends BaseMapper<Enterprise> {
    @Insert("insert into enterprise (phone,`name`,password,create_time,update_time) values(#{phone},#{name},#{password},now(),now())")
    void add(@Param("phone") String phone,@Param("name")String name, @Param("password") String password);

    @Update("update enterprise set logo = #{avatar},update_time = now() where id = #{id}")
    void updateAvatar(@Param("id") Integer id, @Param("avatar") String avatar);

    @Select("SELECT COUNT(ID) from enterprise")
    Integer getTotalCount();

    @Update("update enterprise set phone = #{phone},update_time = now() where id = #{id}")
    void updatePhone(Integer id, String phone);
}
