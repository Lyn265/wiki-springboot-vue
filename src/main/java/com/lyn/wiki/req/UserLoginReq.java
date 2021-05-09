package com.lyn.wiki.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
public class UserLoginReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "【用户名】不能为空")
    @ApiModelProperty(value = "登陆名")
    private String loginName;

    @NotEmpty(message = "【密码】不能为空")
    // @Length(min = 6, max = 20, message = "【密码】6~20位")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$", message = "【密码】校验规则不正确")
    @ApiModelProperty(value = "密码")
    private String password;


}
