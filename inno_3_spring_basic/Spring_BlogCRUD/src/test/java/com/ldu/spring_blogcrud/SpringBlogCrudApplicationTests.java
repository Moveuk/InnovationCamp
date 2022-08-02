package com.ldu.spring_blogcrud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;

@SpringBootTest
class SpringBlogCrudApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads() throws Exception {
        if (applicationContext != null) {
            String[] beans = applicationContext.getBeanDefinitionNames();

            for (String bean : beans) {
                System.out.println("bean : " + bean);
            }
        }

        AuthenticationManager authenticationManager = applicationContext.getBean("AuthenticationManager", AuthenticationManager.class);
        System.out.println("authenticationManager = " + authenticationManager.getClass().getName());
    }

}
