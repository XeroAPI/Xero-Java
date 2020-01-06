package com.xero.api;

import java.io.IOException;

import org.threeten.bp.*;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeFormatterBuilder;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomLocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {
    private static final long serialVersionUID = 1L;
    
    public CustomLocalDateTimeDeserializer() {
        this(null);
    }

    public CustomLocalDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    public LocalDateTime deserialize(JsonParser jsonparser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                // here is the same as your code
                .append(DateTimeFormatter.ISO_DATE_TIME)
                // create formatter
                .toFormatter();
               
        LocalDateTime formatted = null;
        String dateString = jsonparser.getText();
       
        if (dateString.isEmpty()) {
            throw new IllegalArgumentException("date object null");
        }
        formatted = LocalDateTime.parse(dateString, formatter);

        return formatted;
    }
}
