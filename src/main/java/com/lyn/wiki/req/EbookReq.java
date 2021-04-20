package com.lyn.wiki.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value="Ebook请求对象", description="电子书")
public class EbookReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

}
