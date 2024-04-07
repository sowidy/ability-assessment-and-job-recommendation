package com.jobRecomment.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName: RemarkVO
 * Package: com.jobRecomment.domain.vo
 * Description:
 * Author by: HUANG
 * Created by: 2024/3/18 16:56
 * Version 1.0
 */
@Data
@ApiModel(description = "用户反馈信息VO")
public class RemarkVO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("6方面反馈的评分")
    private String remark;

    @ApiModelProperty("反馈总评分")
    private String totalScore;

    @ApiModelProperty("反馈的文本信息")
    private String remarkText;
}
