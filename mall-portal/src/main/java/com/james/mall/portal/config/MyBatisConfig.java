package com.james.mall.portal.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis相关配置
 * Created by james on 2019/4/8.
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.james.mall.mapper","com.james.mall.portal.dao"})
public class MyBatisConfig {
}
