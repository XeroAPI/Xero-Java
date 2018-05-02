package com.xero.api.jaxb;

import com.xero.api.XeroClientException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

/**
 * Marshall and unmarshall from schema derived class and/or java
 * to schema (JAXB-annotated) mapped classes found in com.xero.model
 * Will throw {@link XeroClientException} if {@link JAXBContext}
 * cannot create the marshaller or unmarshaller
 */
public class XeroJAXBMarshaller {

    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;
    static final JAXBContext context = initContext();

    private static JAXBContext initContext() {
        try { 
            return JAXBContext.newInstance("com.xero.model", XeroJAXBMarshaller.class.getClassLoader());
        } catch (Exception e) {
            throw new XeroClientException(e.getMessage(), e);
        }        
    }

    public XeroJAXBMarshaller() {
        try { 
            //context = JAXBContext.newInstance("com.xero.model");
            marshaller = context.createMarshaller();
            unmarshaller = context.createUnmarshaller();
        } catch (Exception e) {
            throw new XeroClientException(e.getMessage(), e);
        }
    }

    
    public String marshall(JAXBElement<?> object) {
        try {
            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new IllegalStateException("Error marshalling request object " + object.getClass(), e);
        }
    }

    public <T> T unmarshall(String responseBody, Class<T> clazz) throws UnsupportedEncodingException {
        try {
            Source source = new StreamSource(new StringReader(responseBody));
            return unmarshaller.unmarshal(source, clazz).getValue();
        } catch (JAXBException e) {
            throw new IllegalStateException("Error unmarshalling response: " + responseBody, e);
        }
    }

}
