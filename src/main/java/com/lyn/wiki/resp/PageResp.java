package com.lyn.wiki.resp;

import lombok.Data;

@Data
public class PageResp<T> {

    private long total;

    private T list;
}
