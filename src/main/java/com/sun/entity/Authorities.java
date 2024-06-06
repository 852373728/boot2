package com.sun.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.security.core.GrantedAuthority;

/**
 * @date 2024/5/24
 */

@TableName("t_authorities")
public class Authorities implements GrantedAuthority {


    private String username;
    private String authority;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
