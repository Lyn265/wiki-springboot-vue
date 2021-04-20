package com.lyn.wiki.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author LIUCHENGLEI
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "test对象")
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "name")
    private String name;
    @ApiModelProperty(value = "password")
    private String password;


}
