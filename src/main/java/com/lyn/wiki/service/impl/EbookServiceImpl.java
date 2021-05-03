package com.lyn.wiki.service.impl;

import com.lyn.wiki.domain.Ebook;
import com.lyn.wiki.mapper.EbookMapper;
import com.lyn.wiki.req.EbookSaveReq;
import com.lyn.wiki.service.IEbookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyn.wiki.util.CopyUtil;
import com.lyn.wiki.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 电子书 服务实现类
 * </p>
 *
 * @author LIUCHENGLEI
 * @since 2021-04-20
 */
@Service
public class EbookServiceImpl extends ServiceImpl<EbookMapper, Ebook> implements IEbookService {

    @Autowired
    EbookMapper ebookMapper;

    @Autowired
    SnowFlake snowFlake;

    @Override
    public int saveEbook(EbookSaveReq saveReq) {
        Ebook ebook = CopyUtil.copy(saveReq, Ebook.class);
        if(ObjectUtils.isEmpty(ebook.getId())){
            ebook.setId(snowFlake.nextId());
            return ebookMapper.insert(ebook);
        }else{
           return ebookMapper.updateById(ebook);
        }
    }

    @Override
    public int delEbook(Long id) {
        return ebookMapper.deleteById(id);
    }
}
