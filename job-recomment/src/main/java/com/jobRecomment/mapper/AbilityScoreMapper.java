package com.jobRecomment.mapper;

import com.jobRecomment.domain.bean.AbilityScore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 能力评分表 Mapper 接口
 * </p>
 *
 * @author HUANG
 * @since 2024-04-12
 */
@Mapper
public interface AbilityScoreMapper extends BaseMapper<AbilityScore> {

    @Select("select * from ability_score where student_id =#{id}")
    AbilityScore selectByStudentId(Integer id);
}
