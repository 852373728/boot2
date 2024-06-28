package com.sun.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @date 2024/6/28
 */
public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg) {
        super(msg);
    }

    public CaptchaException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
