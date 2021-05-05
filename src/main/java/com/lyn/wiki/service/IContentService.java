package com.lyn.wiki.service;

import com.lyn.wiki.domain.Content;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文档内容 服务类
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-05
 */
public interface IContentService extends IService<Content> {

    String findContent(Long id);
}
