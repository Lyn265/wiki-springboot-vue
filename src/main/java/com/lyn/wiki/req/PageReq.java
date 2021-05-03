package com.lyn.wiki.req;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
public class PageReq {
    @NotNull(message = "page不能为空。")
    private long page;
    @NotNull(message = "size不能为空。")
    @Max(value = 100,message = "每页显示不能超过100件。")
    private long size;
}
