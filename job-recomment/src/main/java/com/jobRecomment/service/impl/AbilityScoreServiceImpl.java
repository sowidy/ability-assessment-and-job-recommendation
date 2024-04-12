package com.jobRecomment.service.impl;

import com.jobRecomment.domain.bean.AbilityScore;
import com.jobRecomment.mapper.AbilityScoreMapper;
import com.jobRecomment.service.IAbilityScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jobRecomment.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 能力评分表 服务实现类
 * </p>
 *
 * @author HUANG
 * @since 2024-04-12
 */
@Service
public class AbilityScoreServiceImpl extends ServiceImpl<AbilityScoreMapper, AbilityScore> implements IAbilityScoreService {

    @Override
    public AbilityScore getScoreByStudentId() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        return baseMapper.selectByStudentId(id);
    }
}
