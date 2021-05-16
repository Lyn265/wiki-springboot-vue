package com.lyn.wiki.mapper;

import com.lyn.wiki.domain.Doc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 文档 Mapper 接口
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-04
 */
public interface DocMapper extends BaseMapper<Doc> {

    void increaseViewCount(@Param("id") Long id);

    void increaseVoteCount(@Param("id") Long id);

    void updateEbookInfo();
}
