package com.lyn.wiki.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 文档
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Doc对象", description="文档")
public class DocSaveReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;
    @NotNull(message = "【电子书id】不能为空。")
    @ApiModelProperty(value = "电子书id")
    private Long ebookId;
    @NotNull(message = "【父id】不能为空。")
    @ApiModelProperty(value = "父id")
    private Long parent;
    @NotNull(message = "【名称】不能为空。")
    @ApiModelProperty(value = "名称")
    private String name;
    @NotNull(message = "【顺序】不能为空。")
    @ApiModelProperty(value = "顺序")
    private Integer sort;
    @NotNull(message = "【内容】不能为空。")
    @ApiModelProperty(value = "富文本内容")
    private String content;

    @ApiModelProperty(value = "阅读数")
    private Integer viewCount;

    @ApiModelProperty(value = "点赞数")
    private Integer voteCount;


}
