package com.yapily.paycli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.yaml.YAMLMapper;

@SpringBootApplication
@ComponentScan(basePackages = {"com.yapily.paycli"})
public class Application
{
    static void main(String[] args)
    {
        SpringApplication application = new SpringApplication(Application.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }


    @Bean
    public YAMLMapper yamlMapper()
    {
        return YAMLMapper.builder().build();
    }


    @Bean
    public JsonMapper jsonMapper()
    {
        return JsonMapper.builder()
                        //.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                        .enable(SerializationFeature.FAIL_ON_EMPTY_BEANS, SerializationFeature.FAIL_ON_SELF_REFERENCES)
                        .enable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)
                        .build();
    }
}
