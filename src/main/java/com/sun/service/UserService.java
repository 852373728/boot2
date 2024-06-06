package com.sun.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.entity.User;
import com.sun.mapper.UsersMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @date 2024/5/23
 */
@Service
public class UserService implements UserDetailsService {

    @Resource
    private UsersMapper usersMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = getUserByLogin(username.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public User getUserByLogin(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = usersMapper.selectOne(queryWrapper);
        return user;
    }


}
