package com.james.mall.search.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis相关配置
 * Created by james on 2019/4/8.
 */
@Configuration
@MapperScan({"com.james.mall.mapper","com.james.mall.search.dao"})
public class MyBatisConfig {
}
