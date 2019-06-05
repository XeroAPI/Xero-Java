package com.xero.api;

import com.xero.api.client.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.threetenbp.ThreeTenModule;
import org.threeten.bp.*;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.AbstractHttpContent;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.Json;
import java.io.IOException;
import java.io.OutputStream;


public class ApiClient {
    private final String basePath;
    private final HttpRequestFactory httpRequestFactory;
    private final ObjectMapper objectMapper;

    private static final String defaultBasePath = "https://api.xero.com/api.xro/2.0";

    // A reasonable default object mapper. Client can pass in a chosen ObjectMapper anyway, this is just for reasonable defaults.
    private static ObjectMapper createDefaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setDateFormat(new RFC3339DateFormat())
            .setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        
        ThreeTenModule module = new ThreeTenModule();
        module.addDeserializer(Instant.class, CustomInstantDeserializer.INSTANT);
        module.addDeserializer(OffsetDateTime.class, CustomInstantDeserializer.OFFSET_DATE_TIME);
        module.addDeserializer(ZonedDateTime.class, CustomInstantDeserializer.ZONED_DATE_TIME);
        objectMapper.registerModule(module);
        return objectMapper;
    }

    public ApiClient() {
        this(null, null, null, null);
    }

    public ApiClient(
        String basePath,
        HttpTransport httpTransport,
        HttpRequestInitializer initializer,
        ObjectMapper objectMapper
    ) {
        this.basePath = basePath == null ? defaultBasePath : (
            basePath.endsWith("/") ? basePath.substring(0, basePath.length() - 1) : basePath
        );
        this.httpRequestFactory = (httpTransport == null ? Utils.getDefaultTransport() : httpTransport).createRequestFactory(initializer);
        this.objectMapper = (objectMapper == null ? createDefaultObjectMapper() : objectMapper);
    }

    public HttpRequestFactory getHttpRequestFactory() {
        return httpRequestFactory;
    }

    public String getBasePath() {
        return basePath;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public class JacksonJsonHttpContent extends AbstractHttpContent {
        /* A POJO that can be serialized with a com.fasterxml Jackson ObjectMapper */
        private final Object data;

        public JacksonJsonHttpContent(Object data) {
            super(Json.MEDIA_TYPE);
            this.data = data;
        }

        @Override
        public void writeTo(OutputStream out) throws IOException {
            objectMapper.writeValue(out, data);
        }
    }

    // Builder pattern to get API instances for this client.
    
    public AccountingApi accountingApi() {
        return new AccountingApi(this);
    }
    
}
