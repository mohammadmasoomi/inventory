package com.github.mohammadmasoomi.inventory.configuration.jackson;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;


public class ObjectMapperFactory {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
    }

    public static ObjectMapper create() {
        return objectMapper;
    }
}