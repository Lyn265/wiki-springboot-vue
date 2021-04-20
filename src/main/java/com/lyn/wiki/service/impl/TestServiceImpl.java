package com.lyn.wiki.service.impl;

import com.lyn.wiki.domain.Test;
import com.lyn.wiki.mapper.TestMapper;
import com.lyn.wiki.service.ITestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LIUCHENGLEI
 * @since 2021-04-20
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {

}
