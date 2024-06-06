package com.sun;


import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.web.DefaultSecurityFilterChain;


@SpringBootApplication
@ServletComponentScan(basePackages = {"com.sun.filter"})
@MapperScan("com.sun.mapper")
public class Boot2Application {

    private static Logger logger = LoggerFactory.getLogger(Boot2Application.class);

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(Boot2Application.class, args);
        String[] beanDefinitionNames = run.getBeanDefinitionNames();


        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        logger.info("启动完成");



    }
}
