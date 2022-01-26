package com.github.mohammadmasoomi.inventory.configuration.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.Collections;

@Configuration
public class JacksonConfiguration {

    @Bean
    public ObjectMapper createMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.setSerializers(new GSONObjectSerializer());
        return Jackson2ObjectMapperBuilder.json().modules(Collections.singletonList(simpleModule)).build();
    }

}
