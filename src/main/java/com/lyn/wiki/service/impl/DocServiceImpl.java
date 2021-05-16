package com.lyn.wiki.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyn.wiki.domain.Content;
import com.lyn.wiki.domain.Doc;
import com.lyn.wiki.exception.BusinessException;
import com.lyn.wiki.exception.BusinessExceptionCode;
import com.lyn.wiki.mapper.ContentMapper;
import com.lyn.wiki.mapper.DocMapper;
import com.lyn.wiki.req.DocSaveReq;
import com.lyn.wiki.service.IDocService;
import com.lyn.wiki.util.CopyUtil;
import com.lyn.wiki.util.RedisUtil;
import com.lyn.wiki.util.RequestContext;
import com.lyn.wiki.util.SnowFlake;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RequestContext requestContext;

    @Autowired
    WsServiceImpl wsService;



    @Override
    @Transactional
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
            int update2 = contentMapper.insertOrUpdate(content);
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

    @Override
    public void voteCount(Long id) {

        String ip = requestContext.getIp();

        if(redisUtil.validateRepeat("DOC_VOTE_" + id +"_" + ip,300)){
            docMapper.increaseVoteCount(id);
        }else{
            throw new BusinessException(BusinessExceptionCode.VOTE_REPEAT);
        }
        //点赞消息通知
        Doc docDb = docMapper.selectById(id);
        String logId = MDC.get("LOG_ID");
        wsService.sendInfo("["+docDb.getName()+"]:被点赞!",logId);
    }

    @Override
    public void updateEbookInfo() {
        docMapper.updateEbookInfo();
    }

}
