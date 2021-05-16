package com.lyn.wiki.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyn.wiki.domain.Doc;
import com.lyn.wiki.mapper.DocMapper;
import com.lyn.wiki.req.DocQueryReq;
import com.lyn.wiki.req.DocSaveReq;
import com.lyn.wiki.resp.CommonResp;
import com.lyn.wiki.resp.DocResp;
import com.lyn.wiki.resp.PageResp;
import com.lyn.wiki.service.IContentService;
import com.lyn.wiki.service.IDocService;
import com.lyn.wiki.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 文档 前端控制器
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-04
 */
@RestController
@RequestMapping("/doc")
public class DocController {

    @Autowired
    DocMapper docMapper;

    @Autowired
    IDocService docService;

    @Autowired
    IContentService contentService;

    /**
     * 目录查询
     * @param
     * @return
     */
    @GetMapping("/all/{ebookId}")
    public CommonResp all(@PathVariable Long ebookId){
        CommonResp commonResp = new CommonResp();
        QueryWrapper<Doc> wrapper = new QueryWrapper();
        wrapper.eq("ebook_id",ebookId);
        wrapper.orderByAsc("sort");
        List<Doc> categories = docMapper.selectList(wrapper);
        List<DocResp> respList = CopyUtil.copyList(categories, DocResp.class);
        commonResp.setContent(respList);
        return commonResp;
    }
    @GetMapping("/list")
    public CommonResp list(DocQueryReq docQueryReq){
        CommonResp<PageResp> resp = new CommonResp();
        QueryWrapper<Doc> wrapper = new QueryWrapper();
        IPage<Doc> docPage = new Page<>(docQueryReq.getPage(), docQueryReq.getSize());
        docPage = docMapper.selectPage(docPage, wrapper);
        List<Doc> docList = docPage.getRecords();
        PageResp pageResp = new PageResp<Doc>();
        pageResp.setTotal(docPage.getTotal());
        List<DocResp> respList = CopyUtil.copyList(docList, DocResp.class);
        pageResp.setList(respList);
        resp.setContent(pageResp);
        return resp;
    }
    //电子书保存
    @PostMapping("/save")
    public CommonResp saveDoc(@Valid @RequestBody DocSaveReq saveDoc){
        CommonResp commonResp = new CommonResp();
        int result = docService.saveDoc(saveDoc);
        if(result > 1){
            return commonResp;
        }
            commonResp.setSuccess(false);
            commonResp.setMessage("更新失败");
            return commonResp;
    }
    @DeleteMapping("/delete/{ids}")
    public CommonResp delDoc(@PathVariable String ids){
        CommonResp commonResp = new CommonResp();
        int result = docService.delDoc(ids);
        if(result > 0){
            commonResp.setMessage("删除成功。");
            return commonResp;
        }
            commonResp.setSuccess(false);
            commonResp.setMessage("删除失败。");
            return commonResp;
    }

    @GetMapping("/find-content/{id}")
    public CommonResp findContent(@PathVariable Long id){
        CommonResp<String> resp = new CommonResp();
        String content = contentService.findContent(id);
        if(content!= null){
            resp.setContent(content);
        }else{
            resp.setSuccess(false);
            resp.setMessage("该文档没有任何内容");
        }
        return resp;
    }

    /**
     * 点赞功能
     * @param id
     * @return
     */
    @GetMapping("/vote/{id}")
    public CommonResp voteCount(@PathVariable Long id){
        CommonResp resp = new CommonResp();
        docService.voteCount(id);
        return resp;
    }

}
