package com.lyn.wiki.service;

import com.lyn.wiki.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyn.wiki.req.UserSaveReq;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-08
 */
public interface IUserService extends IService<User> {

    int saveUser(UserSaveReq user);

    int delUser(Long id);
}
