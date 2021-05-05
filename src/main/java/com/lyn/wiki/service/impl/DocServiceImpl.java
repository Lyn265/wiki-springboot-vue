package com.lyn.wiki.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyn.wiki.domain.Content;
import com.lyn.wiki.domain.Doc;
import com.lyn.wiki.mapper.ContentMapper;
import com.lyn.wiki.mapper.DocMapper;
import com.lyn.wiki.req.DocSaveReq;
import com.lyn.wiki.service.IDocService;
import com.lyn.wiki.util.CopyUtil;
import com.lyn.wiki.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 文档 服务实现类
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-04
 */
@Service
public class DocServiceImpl extends ServiceImpl<DocMapper, Doc> implements IDocService {

    @Autowired
    ContentMapper contentMapper;

    @Autowired
    DocMapper docMapper;

    @Autowired
    SnowFlake snowFlake;

    @Override
    public int saveDoc(DocSaveReq saveReq) {
        //没有content属性字段
        Doc doc = CopyUtil.copy(saveReq, Doc.class);
        //只拷贝id,content属性字段
        Content content = CopyUtil.copy(saveReq,Content.class);
        if(ObjectUtils.isEmpty(doc.getId())){
            doc.setId(snowFlake.nextId());
            int insert1 = docMapper.insert(doc);
            content.setId(doc.getId());
            int insert2 = contentMapper.insert(content);
            return insert1 + insert2;

        }else{
            int update1 = docMapper.updateById(doc);
            int update2 = contentMapper.updateById(content);
            return update1 + update2;
        }
    }

    @Override
    public int delDoc(String ids) {
        List<String> idsStr = Arrays.asList(ids.split(","));
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.in("id",idsStr);
        return docMapper.delete(wrapper);
    }
}
