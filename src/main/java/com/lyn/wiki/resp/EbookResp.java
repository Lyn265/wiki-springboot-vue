package com.lyn.wiki.resp;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Ebook返回对象", description="电子书")
public class EbookResp {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "分类1")
    private Long category1_id;

    @ApiModelProperty(value = "分类2")
    private Long category2_id;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "文档数")
    private Integer doc_count;

    @ApiModelProperty(value = "阅读数")
    private Integer view_count;

    @ApiModelProperty(value = "点赞数")
    private Integer vote_count;
}
