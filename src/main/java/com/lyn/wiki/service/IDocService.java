package com.lyn.wiki.service;

import com.lyn.wiki.domain.Doc;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyn.wiki.req.DocSaveReq;

/**
 * <p>
 * 文档 服务类
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-04
 */
public interface IDocService extends IService<Doc> {
    int saveDoc(DocSaveReq doc);

    int delDoc(String ids);
}
