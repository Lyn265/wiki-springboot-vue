package com.lyn.wiki.resp;

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
@ApiModel(value="用户对象", description="用户信息")
public class UserLoginResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "登陆名")
    private String loginName;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "令牌")
    private String token;

}
