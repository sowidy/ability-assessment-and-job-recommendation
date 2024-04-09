package com.jobRecomment.domain.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 收藏职位表
 * </p>
 *
 * @author HUANG
 * @since 2024-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@TableName("favorite_titles")
@ApiModel(value="FavoriteTitles对象", description="收藏职位表")
public class FavoriteTitles implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer studentId;
    private Integer titleId;
}
