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

public class CustomOffsetDateTimeDeserializer extends StdDeserializer<OffsetDateTime> {
	 
	private static final long serialVersionUID = 1L;
	
    public CustomOffsetDateTimeDeserializer() {
        this(null);
    }
 
    public CustomOffsetDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }
 
    public OffsetDateTime deserialize(JsonParser jsonparser, DeserializationContext context)
      throws IOException, JsonProcessingException {
        String date = jsonparser.getText();
        OffsetDateTime formattedDate;
        Pattern datePatt = Pattern.compile("^/Date\\((\\d+)([+-]\\d+)?\\)/$");
		Matcher m = datePatt.matcher(date);
		Pattern datePattNeg = Pattern.compile("^/Date\\(-(\\d+)([+-]\\d+)?\\)/$");
		Matcher mNeg = datePattNeg.matcher(date);
		if (m != null && m.matches()) {
			Long l = Long.parseLong(m.group(1));
			formattedDate = Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).toOffsetDateTime();
		} else if (mNeg != null && mNeg.matches()) {
			Long l = Long.parseLong(mNeg.group(1));
			formattedDate = Instant.ofEpochMilli(-l).atZone(ZoneId.systemDefault()).toOffsetDateTime();
		} else {
			throw new IllegalArgumentException("Wrong date format");
		}
		return formattedDate;
    }
}


