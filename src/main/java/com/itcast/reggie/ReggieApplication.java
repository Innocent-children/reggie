package com.itcast.reggie;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class ReggieApplication {
    public static void main(String[] args) {
//        Logger log = LoggerFactory.getLogger(ReggieApplication.class);   //slf4j注解替换掉了这行代码
        SpringApplication.run(ReggieApplication.class, args);
        log.info("项目启动成功...");
    }
}
