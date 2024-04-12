package com.jobRecomment.service;

import com.jobRecomment.domain.bean.AbilityScore;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 能力评分表 服务类
 * </p>
 *
 * @author HUANG
 * @since 2024-04-12
 */
public interface IAbilityScoreService extends IService<AbilityScore> {

    AbilityScore getScoreByStudentId();
}
