package com.lyn.wiki.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyn.wiki.domain.Category;
import com.lyn.wiki.mapper.CategoryMapper;
import com.lyn.wiki.req.CategorySaveReq;
import com.lyn.wiki.service.ICategoryService;
import com.lyn.wiki.util.CopyUtil;
import com.lyn.wiki.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-02
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    SnowFlake snowFlake;

    @Override
    public int saveCategory(CategorySaveReq saveReq) {
        Category category = CopyUtil.copy(saveReq, Category.class);
        if(ObjectUtils.isEmpty(category.getId())){
            category.setId(snowFlake.nextId());
            return categoryMapper.insert(category);
        }else{
            return categoryMapper.updateById(category);
        }
    }

    @Override
    public int delCategory(Long id) {
        return categoryMapper.deleteById(id);
    }
}
