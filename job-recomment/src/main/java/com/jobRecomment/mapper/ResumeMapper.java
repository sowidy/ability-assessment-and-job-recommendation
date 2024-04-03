package com.jobRecomment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jobRecomment.domain.bean.Enterprise;
import com.jobRecomment.domain.bean.Resume;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * ClassName: ResumeMapper
 * Package: com.jobRecomment.mapper
 * Description:
 * Author by: HUANG
 * Created by: 2024/3/17 11:12
 * Version 1.0
 */
@Mapper
public interface ResumeMapper  extends BaseMapper<Resume> {
    Long insertAndGetId(Resume resume);
}
