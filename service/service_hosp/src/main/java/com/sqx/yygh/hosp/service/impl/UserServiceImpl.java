package com.sqx.yygh.hosp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.yygh.hosp.mapper.UserMapper;
import com.sqx.yygh.hosp.service.UserService;
import com.sqx.yygh.model.hosp.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
