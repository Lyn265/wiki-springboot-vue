package com.lyn.wiki.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyn.wiki.domain.Ebook;
import com.lyn.wiki.mapper.EbookMapper;
import com.lyn.wiki.req.EbookReq;
import com.lyn.wiki.resp.CommonResp;
import com.lyn.wiki.resp.EbookResp;
import com.lyn.wiki.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 电子书 前端控制器
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-04-20
 */
@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Autowired
    EbookMapper ebookMapper;

    @GetMapping("/list")
    public CommonResp list(EbookReq ebookReq){
        CommonResp<List<EbookResp>> resp = new CommonResp();
        QueryWrapper<Ebook> wrapper = new QueryWrapper();
        wrapper.like("name",ebookReq.getName());
        List<Ebook> ebookList = ebookMapper.selectList(wrapper);
        List<EbookResp> respList = CopyUtil.copyList(ebookList, EbookResp.class);
        resp.setContent(respList);
        return resp;
    }
}
