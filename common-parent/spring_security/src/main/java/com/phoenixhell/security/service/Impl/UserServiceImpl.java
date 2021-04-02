package com.phoenixhell.security.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.security.entity.User;
import com.phoenixhell.security.mapper.UserMapper;
import com.phoenixhell.security.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author phoenixhell
 * @since 2021/4/2 0002-上午 10:26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
