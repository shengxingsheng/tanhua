package com.tanhua.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * @author sxs
 * @create 2022-09-06 16:33
 */
@SpringBootApplication(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
})
//@EnableCaching
public class AppServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppServerApplication.class, args);
    }
}
