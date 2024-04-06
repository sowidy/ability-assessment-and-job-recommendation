package com.jobRecomment.domain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * ClassName: StudentQuery
 * Package: com.jobRecomment.domain.query
 * Description:
 * Author by: HUANG
 * Created by: 2024/3/19 23:29
 * Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "条件查询学生实体")
public class StudentQuery extends PageQuery  {

    @ApiModelProperty("姓名")
    private String name;

//    @ApiModelProperty(value = "年龄")
//    private LocalDateTime bornYear;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("学历")
    private String education;

    @ApiModelProperty("学校")
    private String school;

    @ApiModelProperty("专业")
    private String major;

    @ApiModelProperty("专业")
    private String resumeId;
}
