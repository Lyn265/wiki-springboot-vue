package com.lyn.wiki.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 电子书
 * </p>
 *
 * @author LIUCHENGLEI
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Category对象", description="电子书目录")
public class CategorySaveReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "父id")
    private Long parent;

    @ApiModelProperty(value = "名称")
    @NotNull(message = "【名称】不能为空。")
    private String name;
    @NotNull(message = "【顺序】不能为空。")
    @ApiModelProperty(value = "顺序")
    private Integer sort;


}
