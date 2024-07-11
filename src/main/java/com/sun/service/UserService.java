package com.sun.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.entity.Authorities;
import com.sun.entity.User;
import com.sun.event.UserEvent;
import com.sun.mapper.AuthoritiesMapper;
import com.sun.mapper.UsersMapper;
import com.sun.vo.RegistrationUser;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

/**
 * @date 2024/5/23
 */
@Service
public class UserService implements UserDetailsService, ApplicationListener<UserEvent> {

    @Resource
    private UsersMapper usersMapper;

    @Resource
    private AuthoritiesMapper authoritiesMapper;

    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = getUserByLogin(username.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    @Override
    public void onApplicationEvent(UserEvent event) {
        Object source = event.getSource();
        System.out.println(source);
    }

    public User getUserByLogin(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = usersMapper.selectOne(queryWrapper);
        if (user!=null){
            String name = user.getUsername();
            QueryWrapper<Authorities> authoritiesWrap = new QueryWrapper<>();
            authoritiesWrap.eq("username",name);
            List<Authorities> authorities = authoritiesMapper.selectList(authoritiesWrap);
            HashSet<Authorities> authoritiesSet = new HashSet<>(authorities);
            user.setAuthorities(authoritiesSet);
        }
        return user;
    }



    public void registration(RegistrationUser registrationUser) {
        registrationUser.setPassword(passwordEncoder.encode(registrationUser.getPassword()));
        User u = new User();
        BeanUtils.copyProperties(registrationUser,u);
        u.setEnabled(true);
        usersMapper.insert(u);
    }
}
