package com.xero.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class ApiClientTest {

    @Test
    public void revokeFormEncodesRefreshToken() throws IOException {
        HttpTransport transport = mock(HttpTransport.class);
        HttpRequestFactory requestFactory = mock(HttpRequestFactory.class);
        HttpRequest request = mock(HttpRequest.class);
        HttpResponse response = mock(HttpResponse.class);
        ArgumentCaptor<HttpContent> contentCaptor = ArgumentCaptor.forClass(HttpContent.class);

        when(transport.createRequestFactory()).thenReturn(requestFactory);
        when(requestFactory.buildRequest(
            eq(HttpMethods.POST), any(GenericUrl.class), contentCaptor.capture()
        )).thenReturn(request);
        when(request.setHeaders(any(HttpHeaders.class))).thenReturn(request);
        when(request.setConnectTimeout(anyInt())).thenReturn(request);
        when(request.setReadTimeout(anyInt())).thenReturn(request);
        when(request.execute()).thenReturn(response);

        ApiClient apiClient = new ApiClient(null, transport, null, null, null);
        apiClient.revoke("client-id", "client-secret", "refresh+token&part=value");

        ByteArrayOutputStream requestBody = new ByteArrayOutputStream();
        contentCaptor.getValue().writeTo(requestBody);

        assertThat(
            requestBody.toString(StandardCharsets.UTF_8.name()),
            is("token=refresh%2Btoken%26part%3Dvalue")
        );
    }
}
