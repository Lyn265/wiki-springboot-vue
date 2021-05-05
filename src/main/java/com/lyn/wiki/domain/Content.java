package com.lyn.wiki.domain;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文档内容
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Content对象", description="文档内容")
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文档id")
    private Long id;

    @ApiModelProperty(value = "内容")
    private String content;


}
