package com.fk.visitor.lib.entity.enums;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Optional;

/**
 * @author lizz
 * @date 2020/6/5 0005 15:37
 */
public class GenderJsonSerializer extends JsonSerializer<Gender> {
    @Override
    public void serialize(Gender gender, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Optional.of(gender).ifPresent(resultType -> {
            try {
                jsonGenerator.writeObject(gender.toMap());
            } catch (IOException ignored) {
            }
        });
    }
}
