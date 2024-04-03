package com.jobRecomment.domain.bean;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 企业信息表
 * </p>
 *
 * @author HUANG
 * @since 2024-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("enterprise")
@ApiModel(value="Enterprise对象", description="企业信息表")
public class Enterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "logo")
    private String logo;

    @ApiModelProperty(value = "企业描述")
    private String enterpriseDescription;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "职位")
    private String title;

    @ApiModelProperty(value = "职位描述")
    private String titleDescription;

    @ApiModelProperty(value = "最低薪资")
    private BigDecimal salaryMin;

    @ApiModelProperty(value = "最高薪资")
    private BigDecimal salaryMax;

    @ApiModelProperty(value = "职位外链")
    private String titleLink;

    @ApiModelProperty(value = "职位要求")
    private String titleRequire;

    @ApiModelProperty(value = "职位联系人")
    private String hiringManager;

    @ApiModelProperty(value = "联系人电话")
    private String hirePhone;
    @ApiModelProperty(value = "认证材料")
    private String materials;

    @ApiModelProperty(value = "认证状态 （0 未认证 | 1 已认证)")
    private String enterpriseAuth;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
