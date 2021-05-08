package com.lyn.wiki.mapper;

import com.lyn.wiki.domain.Content;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 文档内容 Mapper 接口
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-05
 */
public interface ContentMapper extends BaseMapper<Content> {

    int insertOrUpdate(Content content);
}
