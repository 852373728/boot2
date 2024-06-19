package com.sun;

import lombok.extern.slf4j.Slf4j;

public class ThreadTest {


    public static void main(String[] args) {
        UserContext userContext = new UserContext();
        UserContext userContext1 = new UserContext();
        System.out.println(userContext.logf==userContext1.logf);
        System.out.println(UserContext.logs==UserContext.logs);
    }

}
