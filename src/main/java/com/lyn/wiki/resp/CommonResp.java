package com.lyn.wiki.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "共通业务实体返回对象")
public class CommonResp<T> {
    @ApiModelProperty(value = "返回成功或者失败，默认成功")
    private boolean success = true;
    @ApiModelProperty(value = "返回信息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private T content;

}
