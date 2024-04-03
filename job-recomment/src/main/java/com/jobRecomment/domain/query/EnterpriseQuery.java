package com.jobRecomment.domain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ClassName: enterpriseQuery
 * Package: com.jobRecomment.domain.query
 * Description:
 * Author by: HUANG
 * Created by: 2024/3/14 20:59
 * Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "条件查询企业实体")
public class EnterpriseQuery extends PageQuery {
    @ApiModelProperty("企业名称关键字")
    private String name;
    @ApiModelProperty("职位")
    private String title;
    @ApiModelProperty("最低薪资")
    private String salaryMin;
    @ApiModelProperty("最高薪资")
    private String salaryMax;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("类型")
    private String type;
    @ApiModelProperty("认证状态 （0 未认证 | 1 已认证)")
    private String enterpriseAuth;
}
