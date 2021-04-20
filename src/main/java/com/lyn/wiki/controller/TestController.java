package com.lyn.wiki.controller;


import com.lyn.wiki.domain.Test;
import com.lyn.wiki.mapper.TestMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-04-20
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试类controller")
public class TestController {

    @Autowired
    TestMapper testMapper;

    @PostMapping("/hello")
    @ApiOperation(value = "测试方法")
    public Test hello(@RequestBody Test test){
        return testMapper.selectById(test.getId());
    }

}
