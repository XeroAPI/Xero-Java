package com.xero.api.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

public class JAXBUtils {

    public static String marshall(JAXBElement<?> object) {
        try {
            JAXBContext context = JAXBContext.newInstance(object.getValue().getClass());
            Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new IllegalStateException("Error marshalling request object " + object.getClass(), e);
        }
    }

    public static <T> T unmarshall(String responseBody, Class<T> clazz) throws UnsupportedEncodingException {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller u = context.createUnmarshaller();
            Source source = new StreamSource(new ByteArrayInputStream(responseBody.getBytes("UTF-8")));
            return u.unmarshal(source, clazz).getValue();
        } catch (JAXBException e) {
            throw new IllegalStateException("Error unmarshalling response: " + responseBody, e);
        }
    }

}
