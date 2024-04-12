package com.jobRecomment.controller;


import com.jobRecomment.domain.bean.AbilityScore;
import com.jobRecomment.domain.bean.Result;
import com.jobRecomment.service.IAbilityScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 能力评分表 前端控制器
 * </p>
 *
 * @author HUANG
 * @since 2024-04-12
 */
@RestController
@Api(tags = "能力评价表")
@RequestMapping("/ability-score")
public class AbilityScoreController {
    @Autowired
    private IAbilityScoreService abilityScoreService;

    @GetMapping
    @ApiOperation("获取学生能力评价分数和建议")
    public Result getAbilityScoreByStudentId(){
        AbilityScore score = abilityScoreService.getScoreByStudentId();
        return Result.success(score);
    }
}
