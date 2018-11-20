package com.xero.api.exception;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.http.HttpResponseException;
import com.xero.api.ApiClient;
import com.xero.api.OAuthRequestResource;
import com.xero.api.XeroApiException;
import com.xero.api.XeroClientException;
import com.xero.api.jaxb.XeroJAXBMarshaller;
import com.xero.model.ApiException;
import com.xero.models.accounting.Error;
import com.xero.models.bankfeeds.Statements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is to handle Xero API exceptions with the help of the {@link XeroJAXBMarshaller}
 */
public class XeroExceptionHandler {

    //private final static Logger logger = LogManager.getLogger(XeroExceptionHandler.class.getName());
    final static Logger logger = LogManager.getLogger(XeroExceptionHandler.class);
    
    private static final Pattern MESSAGE_PATTERN = Pattern.compile("<Message>(.*)</Message>");
    private XeroJAXBMarshaller xeroJaxbMarshaller;
    private ApiClient apiClient = new ApiClient(null,null,null,null);
	

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
     * List&lt;{@link com.xero.model.Elements}&gt; elements = xeroApiException.getApiException().getElements();
     * {@link com.xero.model.Elements} element = elements.get(0);
     * List&lt;{@link Object}&gt; dataContractBase = element.getDataContractBase();
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
     * List&lt;{@link com.xero.model.Elements}&gt; elements = xeroApiException.getApiException().getElements();
     * {@link com.xero.model.Elements} element = elements.get(0);
     * List&lt;{@link Object}&gt; dataContractBase = element.getDataContractBase();
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
            		logger.error(e);
                return convertException(httpResponseException);
            }
        } else {
            return newApiException(httpResponseException);
        }
    }
    
    public XeroApiException handleBadRequest(String content) {
        //TODO we could use the ApiException.xsd to validate that the content is an ApiException xml
        if (content.contains("ApiException")) {
            try {
                ApiException apiException = xeroJaxbMarshaller.unmarshall(content, ApiException.class);
                return new XeroApiException(412, content, apiException);
            } catch (Exception e) {
                logger.error(e);
            }
        }
		return null;
    }
    
    public XeroApiException handleBadRequest(String content, int code, boolean isJson) {
        //TODO we could use the ApiException.xsd to validate that the content is an ApiException xml
        if (isJson) {
        	TypeReference<Error> typeRef = new TypeReference<Error>() {};
			try {
				Error error =  apiClient.getObjectMapper().readValue(content, typeRef);
				return new XeroApiException(code, content, error);
			} catch (IOException e) {
				logger.error(e);
			}
        } else {
        	Error error = new Error();
        	error.setMessage(content);
        	return new XeroApiException(code, content, error);
        }
    	return null;
    }
    
    public XeroApiException handleBadRequest(String content,int code) {
        //TODO we could use the ApiException.xsd to validate that the content is an ApiException xml
        if (content.contains("ApiException")) {
            try {
                ApiException apiException = xeroJaxbMarshaller.unmarshall(content, ApiException.class);
                return new XeroApiException(code, content, apiException);
            } catch (Exception e) {
            	logger.error(e);
            }
        } else {
        	return  newApiException(content, code);
        }
		return null;
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
            		logger.error(e);
                throw new XeroClientException(e.getMessage(), e);
            }
        }

        return new XeroApiException(httpResponseException.getStatusCode(), httpResponseException.getContent());
    }
    
    public XeroApiException newApiException(String content, int code) {
        Matcher matcher = MESSAGE_PATTERN.matcher(content);
        
        StringBuilder messages = new StringBuilder();
        while (matcher.find()) {
            if (messages.length() > 0) {
                messages.append(", ");
            }
            messages.append(matcher.group(1));
        }

        if (messages.length() > 0) {
            return new XeroApiException(code, messages.toString());
        }
        if (content.contains("=")) {
            try {
                String value = URLDecoder.decode(content, "UTF-8");
                String[] keyValuePairs = value.split("&");

                Map<String, String> errorMap = new HashMap<>();
                for (String pair : keyValuePairs) {
                    String[] entry = pair.split("=");
                    errorMap.put(entry[0].trim(), entry[1].trim());
                }
                return new XeroApiException(code, errorMap);

            } catch (UnsupportedEncodingException e) {
            		logger.error(e);
                throw new XeroClientException(e.getMessage(), e);
            }
        }

        return new XeroApiException(code, content);
    }
}
