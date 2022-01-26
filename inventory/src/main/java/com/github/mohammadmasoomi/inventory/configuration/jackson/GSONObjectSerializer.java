package com.github.mohammadmasoomi.inventory.configuration.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;

public class GSONObjectSerializer extends SimpleSerializers {

    private static final long serialVersionUID = 2999339731763064210L;

    @Override
    public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {

        // --> Here is to set up which class will be serialised by the custom serializer.
        if (type.isTypeOrSubTypeOf(JsonObject.class) || type.isTypeOrSubTypeOf(JsonArray.class) || type.isTypeOrSubTypeOf(JsonElement.class)) {
            return new EmptySerializer(type.getRawClass());
        }
        return super.findSerializer(config, type, beanDesc);
    }


    private static class EmptySerializer extends StdSerializer<Object> {

        private static final long serialVersionUID = -1456817695323549278L;

        private final ObjectMapper objectMapper = ObjectMapperFactory.create();

        protected EmptySerializer(Class t) {
            super(t);
        }

        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            JsonNode jsonNode = objectMapper.readTree(value.toString());
            gen.writeObject(jsonNode);
        }
    }

}