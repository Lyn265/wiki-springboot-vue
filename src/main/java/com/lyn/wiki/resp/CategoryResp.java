package com.lyn.wiki.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 分类表
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Category返回对象", description="分类表")
public class CategoryResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "父id")
    private Long parent;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "顺序")
    private Integer sort;


}
