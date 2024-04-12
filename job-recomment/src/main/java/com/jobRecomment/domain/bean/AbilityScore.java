package com.jobRecomment.domain.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 能力评分表
 * </p>
 *
 * @author HUANG
 * @since 2024-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ability_score")
@ApiModel(value="AbilityScore对象", description="能力评分表")
public class AbilityScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "学生ID")
    private Integer studentId;

    @ApiModelProperty(value = "学历评分")
    private String educationScore;

    @ApiModelProperty(value = "证书评分")
    private String certificateScore;

    @ApiModelProperty(value = "专业技能评分")
    private String skillsScore;

    @ApiModelProperty(value = "工作经验评分")
    private String experienceScore;

    @ApiModelProperty(value = "个人特质评分")
    private String personalScore;

    @ApiModelProperty(value = "沟通能力评分")
    private String communicationScore;

    @ApiModelProperty(value = "总评分")
    private String totalScore;

    @ApiModelProperty(value = "建议")
    private String suggest;


}
