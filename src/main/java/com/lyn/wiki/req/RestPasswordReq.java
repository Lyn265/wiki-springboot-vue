package com.lyn.wiki.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="用户重置密码对象", description="用户信息")
public class RestPasswordReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$", message = "【密码】至少包含 数字和英文，长度6-32")
    @ApiModelProperty(value = "密码")
    private String password;
}
