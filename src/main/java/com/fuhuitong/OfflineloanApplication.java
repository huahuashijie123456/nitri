package com.fuhuitong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@MapperScan("com.fuhuitong.nitri.*.mapper")
@EnableCaching
public class OfflineloanApplication {
    public static void main(String[] args) {

        SpringApplication.run(OfflineloanApplication.class, args);
    }


    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize("102400KB"); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize("1024000KB");
        return factory.createMultipartConfig();
    }

}
