package com.sun.event;

import org.springframework.context.ApplicationEvent;

/**
 * @date 2024/7/9
 */
public class UserEvent extends ApplicationEvent {


    public UserEvent(Object source) {
        super(source);
    }
}
