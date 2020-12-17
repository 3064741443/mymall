package com.james.mall.demo.config;

import com.james.mall.common.config.BaseSwaggerConfig;
import com.james.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文档相关配置
 * Created by james on 2019/4/8.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.james.mall.demo.controller")
                .title("mall-demo系统")
                .description("SpringBoot版本中的一些示例")
                .contactName("james")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }

}
