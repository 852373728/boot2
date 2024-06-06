package com.sun;

import com.sun.entity.User;
import com.sun.mapper.UsersMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class SpringTest {

    @Resource
    private UsersMapper usersMapper;

    @Test
    public void t1(){
        List<User> userList = usersMapper.selectList(null);
        System.out.println(userList.size());

    }

}
