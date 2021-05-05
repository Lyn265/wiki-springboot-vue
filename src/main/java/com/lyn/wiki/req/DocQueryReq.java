package com.lyn.wiki.req;

import io.swagger.annotations.ApiModel;
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
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Category请求对象", description="电子书目录")
public class DocQueryReq extends PageReq implements Serializable {

    private static final long serialVersionUID = 1L;


}
