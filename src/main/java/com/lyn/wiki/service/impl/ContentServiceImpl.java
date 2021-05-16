package com.lyn.wiki.service.impl;

import com.lyn.wiki.domain.Content;
import com.lyn.wiki.mapper.ContentMapper;
import com.lyn.wiki.mapper.DocMapper;
import com.lyn.wiki.service.IContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 文档内容 服务实现类
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-05
 */
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements IContentService {
    @Autowired
    ContentMapper contentMapper;
    @Autowired
    DocMapper docMapper;
    @Override
    public String findContent(Long id) {
        Content content = contentMapper.selectById(id);
        docMapper.increaseViewCount(id);
        if(!ObjectUtils.isEmpty(content)){
            return content.getContent();
        }
        return "";
    }
}
