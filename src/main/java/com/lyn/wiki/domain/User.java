package com.lyn.wiki.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "登陆名")
    private String loginName;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

}
