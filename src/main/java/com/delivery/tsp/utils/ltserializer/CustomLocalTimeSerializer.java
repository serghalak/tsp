package com.delivery.tsp.utils.ltserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class CustomLocalTimeSerializer extends StdDeserializer<LocalTime> {

    private static DateTimeFormatter formatter =
            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

    protected CustomLocalTimeSerializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public LocalTime deserialize(JsonParser jsonParser
            , DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return null;
    }
}
