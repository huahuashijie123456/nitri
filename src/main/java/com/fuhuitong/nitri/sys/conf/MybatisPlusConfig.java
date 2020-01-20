package com.fuhuitong.nitri.sys.conf;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableTransactionManagement
@Log4j2
public class MybatisPlusConfig {

    private static final String SYSTEM_TENANT_ID = "sex";
    private static final List<String> IGNORE_TENANT_TABLES = Arrays.asList("user,dict");

    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setLogicDeleteValue("Y");
        dbConfig.setLogicNotDeleteValue("N");
        globalConfig.setDbConfig(dbConfig);
        globalConfig.setSqlInjector(new LogicSqlInjector());
        return globalConfig;
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        return paginationInterceptor;
    }
    /**
     * SQL执行效率插件
     */
  /*  @Bean
    @Profile({"dev","AAAAAAAAA"})// 设置 dev AAAAAAAAA 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }*/




}
