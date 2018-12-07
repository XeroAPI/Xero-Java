package com.xero.api;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.threeten.bp.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomDateDeserializer extends StdDeserializer<LocalDate> {
	 
	private static final long serialVersionUID = 1L;
	
    public CustomDateDeserializer() {
        this(null);
    }
 
    public CustomDateDeserializer(Class<?> vc) {
        super(vc);
    }
 
    public LocalDate deserialize(JsonParser jsonparser, DeserializationContext context)
      throws IOException, JsonProcessingException {
        String date = jsonparser.getText();
        LocalDate formattedDate;
        Pattern datePatt = Pattern.compile("^/Date\\((\\d+)([+-]\\d+)?\\)/$");
		Matcher m = datePatt.matcher(date);
		if (m.matches()) {
			Long l = Long.parseLong(m.group(1));
			formattedDate = Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).toLocalDate();
		} else {
			throw new IllegalArgumentException("Wrong date format");
		}
		return formattedDate;
    }
}


