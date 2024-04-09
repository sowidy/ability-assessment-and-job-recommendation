package com.jobRecomment.mapper;

import com.jobRecomment.domain.bean.FavoriteTitles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

/**
 * <p>
 * 收藏职位表 Mapper 接口
 * </p>
 *
 * @author HUANG
 * @since 2024-04-09
 */
@Mapper
public interface FavoriteTitlesMapper extends BaseMapper<FavoriteTitles> {

    @Select("select COUNT(1) from favorite_titles where student_id = #{studentId} and title_id = #{titleId}")
    int checkFavorite(@Param("studentId") Integer studentId,@Param("titleId") Integer titleId);

    @Select("select title_id from favorite_titles where student_id = #{studentId}")
    List<Integer> getAllTitleId(Integer studentId);

    @Delete("DELETE FROM favorite_titles WHERE student_id = #{studentId} AND title_id = #{titleId}")
    void deleteFavorite(@Param("studentId") Integer studentId,@Param("titleId") Integer titleId);

    @Delete("DELETE FROM favorite_titles WHERE student_id = #{studentId}")
    void deleteAllFavorite(Integer studentId);
}
