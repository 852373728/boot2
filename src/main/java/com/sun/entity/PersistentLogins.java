package com.sun.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import java.util.Date;

/**
 * @date 2024/7/5
 */
@TableName("persistent_logins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersistentLogins {



    private String series;
    private String username;
    private String token;
    private Date lastUsed;


}
