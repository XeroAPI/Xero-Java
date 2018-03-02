package com.xero.api.exception;

import com.google.api.client.http.HttpResponseException;
import com.xero.api.XeroApiException;
import com.xero.api.XeroClientException;
import com.xero.api.jaxb.XeroJAXBMarshaller;
import com.xero.model.ApiException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is to handle Xero API exceptions with the help of the {@link XeroJAXBMarshaller}
 */
public class XeroExceptionHandler {

    private final static Logger LOGGER = Logger.getLogger(XeroExceptionHandler.class.getName());

    private static final Pattern MESSAGE_PATTERN = Pattern.compile("<Message>(.*)</Message>");
    private XeroJAXBMarshaller xeroJaxbMarshaller;

    public XeroExceptionHandler() {
        this.xeroJaxbMarshaller = new XeroJAXBMarshaller();
    }

    /**
     * Handle a HTTP 400 Bad Request from Xero. This method will build a {@link XeroApiException}
     * containing {@link ApiException} element with a useful summary of the reason for the error.
     * Use {@link ApiException} to get a list of the errors pertaining to the call in question.
     * <p>
     * For example: if you use {@link com.xero.api.XeroClient#createPayments(List)} and this fails
     * with a XeroApiException, you can extract details from the exception with the following code example.
     * <pre>
     * List<{@link com.xero.model.Elements}> elements = xeroApiException.getApiException().getElements();
     * {@link com.xero.model.Elements} element = elements.get(0);
     * List<{@link Object}> dataContractBase = element.getDataContractBase();
     * for (Object dataContract : dataContractBase) {
     *      {@link com.xero.model.Payment} failedPayment = ({@link com.xero.model.Payment}) dataContract;
     *      {@link com.xero.model.ArrayOfValidationError} validationErrors = failedPayment.getValidationErrors();
     *      ...
     * }
     * </pre>
     * <p>
     * Or if you use {@link com.xero.api.XeroClient#createInvoices(List)} and this fails
     * with a XeroApiException, you can extract details from the exception with the following code example.
     * <pre>
     * List<{@link com.xero.model.Elements}> elements = xeroApiException.getApiException().getElements();
     * {@link com.xero.model.Elements} element = elements.get(0);
     * List<{@link Object}> dataContractBase = element.getDataContractBase();
     * for (Object dataContract : dataContractBase) {
     *      {@link com.xero.model.Invoice} failedInvoice = ({@link com.xero.model.Invoice}) dataContract;
     *      {@link com.xero.model.ArrayOfValidationError} validationErrors = failedInvoice.getValidationErrors();
     *      ...
     * }
     * </pre>
     *
     * @param httpResponseException the exception to handle
     * @return XeroApiException containing {@link ApiException} with a useful summary of the reason for the error.
     */
    public XeroApiException handleBadRequest(HttpResponseException httpResponseException) {
        String content = httpResponseException.getContent();

        //TODO we could use the ApiException.xsd to validate that the content is an ApiException xml
        if (content.contains("ApiException")) {
            try {
                ApiException apiException = xeroJaxbMarshaller.unmarshall(content, ApiException.class);
                return new XeroApiException(httpResponseException.getStatusCode(), content, apiException);
            } catch (Exception e) {
                LOGGER.severe(e.getMessage());
                return convertException(httpResponseException);
            }
        } else {
            return convertException(httpResponseException);
        }
    }

    /**
     * For backwards comparability with xero-java-sdk version 0.6.0 keep the old way of handling exceptions
     *
     * @param ioe exception to convert
     * @return the converted exception
     */
    public XeroApiException convertException(IOException ioe) {
        if (ioe instanceof HttpResponseException) {
            HttpResponseException httpResponseException = (HttpResponseException) ioe;
            if (httpResponseException.getStatusCode() == 400) {
                return handleBadRequest(httpResponseException);
            } else if (httpResponseException.getStatusCode() == 401 ||
                httpResponseException.getStatusCode() == 404 ||
                httpResponseException.getStatusCode() == 500 ||
                httpResponseException.getStatusCode() == 503) {
                return newApiException(httpResponseException);
            } else {
                return newApiException(httpResponseException);
            }
        }
        throw new XeroClientException(ioe.getMessage(), ioe);
    }

    /**
     * For backwards comparability with xero-java-sdk version 0.6.0 keep the old way of handling exceptions
     *
     * @param httpResponseException exception to handle
     * @return XeroApiException
     */
    public XeroApiException newApiException(HttpResponseException httpResponseException) {
        Matcher matcher = MESSAGE_PATTERN.matcher(httpResponseException.getContent());
        StringBuilder messages = new StringBuilder();
        while (matcher.find()) {
            if (messages.length() > 0) {
                messages.append(", ");
            }
            messages.append(matcher.group(1));
        }

        if (messages.length() > 0) {
            return new XeroApiException(httpResponseException.getStatusCode(), messages.toString());
        }
        if (httpResponseException.getContent().contains("=")) {
            try {
                String value = URLDecoder.decode(httpResponseException.getContent(), "UTF-8");
                String[] keyValuePairs = value.split("&");

                Map<String, String> errorMap = new HashMap<>();
                for (String pair : keyValuePairs) {
                    String[] entry = pair.split("=");
                    errorMap.put(entry[0].trim(), entry[1].trim());
                }
                return new XeroApiException(httpResponseException.getStatusCode(), errorMap);

            } catch (UnsupportedEncodingException e) {
                LOGGER.severe(e.getMessage());
                throw new XeroClientException(e.getMessage(), e);
            }
        }

        return new XeroApiException(httpResponseException.getStatusCode(), httpResponseException.getContent());
    }

}

