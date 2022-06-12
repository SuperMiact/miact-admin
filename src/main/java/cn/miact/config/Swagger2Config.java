package cn.miact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * Swagger2信息
     * @return
     */
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                // API 基本信息
                .apiInfo(apiInfo())

                // 设置允许暴露接口
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("cn.miact.learning.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * API基本信息
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("ALL-LEARNING项目")
                .description("综合知识点，整合实战项目。")
                .version("1.0.0")
                .build();
    }
}
