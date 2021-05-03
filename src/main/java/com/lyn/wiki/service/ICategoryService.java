package com.lyn.wiki.service;

import com.lyn.wiki.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyn.wiki.req.CategorySaveReq;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-02
 */
public interface ICategoryService extends IService<Category> {

    int saveCategory(CategorySaveReq category);

    int delCategory(Long id);
}
