package com.jobRecomment.domain.bean;

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
 * 简历信息表
 * </p>
 *
 * @author HUANG
 * @since 2024-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("resume")
@ApiModel(value="Resume对象", description="简历信息表")
public class Resume implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文件原来名称")
    private String originName;

    @ApiModelProperty(value = "上传后存储的名称")
    private String linkName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


}
