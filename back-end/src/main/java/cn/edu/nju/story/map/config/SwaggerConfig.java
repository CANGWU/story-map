package cn.edu.nju.story.map.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 * Swagger配置
 * @author xuan
 * @date 2018/8/2
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.edu.nju.story.map.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("xuan", "", "mf1832062@smail.nju.edu.cn");
        return new ApiInfoBuilder()
                .title("story map")
                .description("story map后端api文档")
                .contact(contact)
                .version("1.0.0")
                .build();

    }


}
