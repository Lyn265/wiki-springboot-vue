package com.lyn.wiki.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyn.wiki.domain.Category;
import com.lyn.wiki.mapper.CategoryMapper;
import com.lyn.wiki.req.CategoryQueryReq;
import com.lyn.wiki.req.CategorySaveReq;
import com.lyn.wiki.resp.CategoryResp;
import com.lyn.wiki.resp.CommonResp;
import com.lyn.wiki.resp.PageResp;
import com.lyn.wiki.service.ICategoryService;
import com.lyn.wiki.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-02
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ICategoryService categoryService;

    /**
     * 目录查询
     * @param
     * @return
     */
    @GetMapping("/all")
    public List<CategoryResp> all(){
        QueryWrapper<Category> wrapper = new QueryWrapper();
        wrapper.orderByAsc("sort");
        List<Category> categories = categoryMapper.selectList(wrapper);
        List<CategoryResp> respList = CopyUtil.copyList(categories, CategoryResp.class);
        return respList;
    }
    @GetMapping("/list")
    public CommonResp list(CategoryQueryReq categoryQueryReq){
        CommonResp<PageResp> resp = new CommonResp();
        QueryWrapper<Category> wrapper = new QueryWrapper();
        IPage<Category> categoryPage = new Page<>(categoryQueryReq.getPage(), categoryQueryReq.getSize());
        categoryPage = categoryMapper.selectPage(categoryPage, wrapper);
        List<Category> categoryList = categoryPage.getRecords();
        PageResp pageResp = new PageResp<Category>();
        pageResp.setTotal(categoryPage.getTotal());
        List<CategoryResp> respList = CopyUtil.copyList(categoryList, CategoryResp.class);
        pageResp.setList(respList);
        resp.setContent(pageResp);
        return resp;
    }
    @PostMapping("/save")
    public CommonResp saveCategory(@Valid @RequestBody CategorySaveReq saveCategory){
        CommonResp commonResp = new CommonResp();
        int result = categoryService.saveCategory(saveCategory);
        if(result == 1){
            return commonResp;
        }
            commonResp.setSuccess(false);
            commonResp.setMessage("更新失败");
            return commonResp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delCategory(@PathVariable Long id){
        CommonResp commonResp = new CommonResp();
        int result = categoryService.delCategory(id);
        if(result == 1){
            commonResp.setMessage("删除成功。");
            return commonResp;
        }
            commonResp.setSuccess(false);
            commonResp.setMessage("删除失败。");
            return commonResp;
    }
}
