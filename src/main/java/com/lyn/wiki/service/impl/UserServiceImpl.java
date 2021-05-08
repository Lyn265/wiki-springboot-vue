package com.lyn.wiki.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyn.wiki.domain.User;
import com.lyn.wiki.exception.BusinessException;
import com.lyn.wiki.exception.BusinessExceptionCode;
import com.lyn.wiki.mapper.UserMapper;
import com.lyn.wiki.req.UserSaveReq;
import com.lyn.wiki.service.IUserService;
import com.lyn.wiki.util.CopyUtil;
import com.lyn.wiki.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    SnowFlake snowFlake;

    @Override
    public int saveUser(UserSaveReq saveReq) {
        User user = CopyUtil.copy(saveReq, User.class);
        //新增
        if(ObjectUtils.isEmpty(user.getId())){
            //登陆名是否存在
            User userDB = selectByLoginName(saveReq.getLoginName());
            if(ObjectUtils.isEmpty(userDB)){
                user.setId(snowFlake.nextId());
                return userMapper.insert(user);
            }else {
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        }else{
            //更新
            user.setLoginName(null);
            return userMapper.updateById(user);
        }
    }
    @Override
    public int delUser(Long id) {
        return userMapper.deleteById(id);
    }

    public User selectByLoginName(String loginName){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("login_name",loginName);
        return userMapper.selectOne(wrapper);
    }
}
