package com.example.bishe_server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@SpringBootApplication
public class BisheServerApplication {
    private final static Log logger = LogFactory.getLog(BisheServerApplication.class);

//    @Bean(name="entityManagerFactory")
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        return sessionFactory;
//    }
    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        try {
            SpringApplication.run(BisheServerApplication.class, args);
            logger.info("Receive Service项目启动完成");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
