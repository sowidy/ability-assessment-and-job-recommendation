package com.jobRecomment.domain.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ClassName: RemarkQuery
 * Package: com.jobRecomment.domain.query
 * Description:
 * Author by: HUANG
 * Created by: 2024/3/30 19:25
 * Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "条件查询学生反馈")
public class RemarkQuery extends PageQuery{
    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("6方面反馈的评分")
    private String remark;

    @ApiModelProperty("反馈总评分")
    private String totalScore;

    @ApiModelProperty("反馈的文本信息")
    private String remarkText;
}
