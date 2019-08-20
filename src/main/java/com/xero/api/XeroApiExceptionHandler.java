package com.xero.api;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.api.client.http.HttpResponseException;
import com.xero.models.accounting.Element;
import com.xero.models.accounting.Error;
import com.xero.models.accounting.ValidationError;

public class XeroApiExceptionHandler {

    public XeroApiExceptionHandler() {
        super();
    }

    public void execute(HttpResponseException e, ApiClient apiClient)
            throws JsonParseException, JsonMappingException, IOException {
        Error error = null;
        int statusCode = e.getStatusCode();
        if (statusCode == 400) {
            TypeReference<Error> errorTypeRef = new TypeReference<Error>() {
            };
            error = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
            throw new XeroApiException(statusCode, e.getStatusMessage(), error);
        } else if (statusCode == 404) {
            error = new Error();
            Element elementsItem = new Element();
            ValidationError ve = new ValidationError();
            ve.setMessage("The resource you're looking for cannot be found");
            elementsItem.addValidationErrorsItem(ve);
            error.addElementsItem(elementsItem);
            throw new XeroApiException(statusCode, error);
        } else if (statusCode == 429) {
            error = new Error();
            Element elementsItem = new Element();
            ValidationError ve = new ValidationError();
            ve.setMessage("You've exceeded the per " + e.getHeaders().get("x-rate-limit-problem") + " rate limit");
            elementsItem.addValidationErrorsItem(ve);
            error.addElementsItem(elementsItem);
            throw new XeroApiException(statusCode, error);
        } else if (statusCode != 400) {
            throw new XeroApiException(statusCode, e.getStatusMessage());
        } else {
            throw e;
        }
    }

}
