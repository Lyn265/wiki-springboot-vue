package com.lyn.wiki.service;

import com.lyn.wiki.domain.Ebook;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyn.wiki.req.EbookSaveReq;

/**
 * <p>
 * 电子书 服务类
 * </p>
 *
 * @author LIUCHENGLEI
 * @since 2021-04-20
 */
public interface IEbookService extends IService<Ebook> {

    int saveEbook(EbookSaveReq ebook);

    int delEbook(Long id);
}
