package com.jobRecomment.domain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName: adminQuery
 * Package: com.jobRecomment.domain.query
 * Description:
 * Author by: HUANG
 * Created by: 2024/3/22 11:31
 * Version 1.0
 */
@Data
@ApiModel(description = "条件查询学生实体")
public class AdminQuery {

    @ApiModelProperty("账号")
    private String phone;

    @ApiModelProperty("密码")
    private String password;
}
