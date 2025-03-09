package com.examly.springapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

   @Override
    public void addCorsMappings(@NonNull CorsRegistry registry){
        registry.addMapping("/**")
          .allowedOrigins("https://8081-eacadeababedeffaddfeeacfcadcfdab.premiumproject.examly.io")
          .allowedMethods("GET","POST","PUT","DELETE","PATCH")
          .allowedHeaders("Authorization","Content-type")
          .allowCredentials(false);
    }
}