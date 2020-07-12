package com.james.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis配置类
 * Created by James on 2019/4/8.
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.james.mall.mapper","com.james.mall.dao"})
public class MyBatisConfig {
}
