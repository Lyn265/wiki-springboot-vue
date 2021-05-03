package com.lyn.wiki.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyn.wiki.domain.Ebook;
import com.lyn.wiki.mapper.EbookMapper;
import com.lyn.wiki.req.EbookQueryReq;
import com.lyn.wiki.req.EbookSaveReq;
import com.lyn.wiki.resp.CommonResp;
import com.lyn.wiki.resp.EbookResp;
import com.lyn.wiki.resp.PageResp;
import com.lyn.wiki.service.IEbookService;
import com.lyn.wiki.util.CopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@Slf4j
@RequestMapping("/ebook")
public class EbookController {

    @Autowired
    EbookMapper ebookMapper;

    @Autowired
    IEbookService ebookService;

    /**
     * 电子书查询
     * @param ebookQueryReq
     * @return
     */
    @GetMapping("/list")
    public CommonResp list(EbookQueryReq ebookQueryReq){
        CommonResp<PageResp> resp = new CommonResp();
        QueryWrapper<Ebook> wrapper = new QueryWrapper();
        if(!ObjectUtils.isEmpty(ebookQueryReq.getName())){
            wrapper.like("name", ebookQueryReq.getName());
        }
        IPage<Ebook> ebookPage = new Page<>(ebookQueryReq.getPage(), ebookQueryReq.getSize());
        ebookPage = ebookMapper.selectPage(ebookPage, wrapper);
        List<Ebook> ebookList = ebookPage.getRecords();
        PageResp pageResp = new PageResp<Ebook>();
        pageResp.setTotal(ebookPage.getTotal());
        List<EbookResp> respList = CopyUtil.copyList(ebookList, EbookResp.class);
        pageResp.setList(respList);
        resp.setContent(pageResp);
        return resp;
    }
    @PostMapping("/save")
    public CommonResp saveEbook(@Valid @RequestBody EbookSaveReq saveEbook){
        CommonResp commonResp = new CommonResp();
        int result = ebookService.saveEbook(saveEbook);
        if(result == 1){
            return commonResp;
        }else{
            commonResp.setSuccess(false);
            commonResp.setMessage("更新失败");
            return commonResp;
        }
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delEbook(@PathVariable Long id){
        CommonResp commonResp = new CommonResp();
        int result = ebookService.delEbook(id);
        if(result == 1){
            commonResp.setMessage("删除成功。");
            return commonResp;
        }else{
            commonResp.setSuccess(false);
            commonResp.setMessage("删除失败。");
            return commonResp;
        }
    }

}
