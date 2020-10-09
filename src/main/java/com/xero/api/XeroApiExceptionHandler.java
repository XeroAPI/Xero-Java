package com.xero.api;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.api.client.http.HttpResponseException;
import com.xero.models.accounting.Element;
import com.xero.models.accounting.Error;
import com.xero.models.accounting.ValidationError;
import com.xero.models.bankfeeds.FeedConnections;
import com.xero.models.bankfeeds.Statements;

public class XeroApiExceptionHandler {

    public XeroApiExceptionHandler() {
        super();
    }

    // REFACTOR ERROR HANDLER
    // ACCOUNTING Validation Errors (400)
    public void validationError(String objectType, com.xero.models.accounting.Error error, Exception e) {
        throw new XeroBadRequestException(objectType, error, e);
    }

    // ASSETS Validation Errors (400)
    public void validationError(String objectType, com.xero.models.assets.Error error, Exception e) {
        throw new XeroBadRequestException(objectType, error, e);
    }

    // BANKFEED Statements Validation Errors (400)
    public void validationError(String objectType, Statements error, Exception e) {
        throw new XeroBadRequestException(objectType, error, e);
    }
    
    // BANKFEED Connections Validation Errors (400)
    public void validationError(String objectType, FeedConnections error, Exception e) {
        throw new XeroBadRequestException(objectType, error, e);
    }
    
    // PAYROLL UK Validation Errors 
    public void validationError(Integer statusCode, String objectType, com.xero.models.payrolluk.Problem error, Exception e) {
        if (statusCode == 400 ) {
            throw new XeroBadRequestException(objectType, error, e);
        } else if(statusCode == 405) {
            throw new XeroMethodNotAllowedException(objectType, error, e);
        }
    }
    
    // PAYROLL NZ Validation Errors 
    public void validationError(Integer statusCode, String objectType, com.xero.models.payrollnz.Problem error, Exception e) {
        if (statusCode == 400 ) {
            throw new XeroBadRequestException(objectType, error, e);
        } else if(statusCode == 405) {
            throw new XeroMethodNotAllowedException(objectType, error, e);
        } else if(statusCode == 409) {
            throw new XeroConflictException(objectType, error, e);
        }
    }
    
    // PAYROLL AU Employees Validation Errors (400)
    public void validationError(String objectType, com.xero.models.payrollau.Employees employees, Exception e) {
        throw new XeroBadRequestException(objectType, employees, e);
    }
    
    public void validationError(String objectType, com.xero.models.payrollau.LeaveApplications leaveApplications, Exception e) {
        throw new XeroBadRequestException(objectType, leaveApplications, e);
    }
    
    public void validationError(String objectType, com.xero.models.payrollau.PayItems payItems, Exception e) {
        throw new XeroBadRequestException(objectType, payItems, e);
    }
    
    public void validationError(String objectType, com.xero.models.payrollau.PayRuns payRuns, Exception e) {
        throw new XeroBadRequestException(objectType, payRuns, e);
    }
    
    public void validationError(String objectType, com.xero.models.payrollau.PayrollCalendars payrollCalendars, Exception e) {
        throw new XeroBadRequestException(objectType, payrollCalendars, e);
    }
    
    public void validationError(String objectType, com.xero.models.payrollau.SuperFunds superFunds, Exception e) {
        throw new XeroBadRequestException(objectType, superFunds, e);
    }
    
    public void validationError(String objectType, com.xero.models.payrollau.Timesheets timesheets, Exception e) {
        throw new XeroBadRequestException(objectType, timesheets, e);
    }
    
    public void validationError(String objectType, String msg, Exception e) {
        throw new XeroBadRequestException(400, msg, e);
    }
    
    // REFACTOR GENERIC ERROR HANDLER
    public void execute(HttpResponseException e) {
        int statusCode = e.getStatusCode();
        
        if (statusCode == 400) {
            String message = e.getMessage();
            throw new XeroBadRequestException(statusCode, message, e);

        } else if (statusCode == 401) {
            String message = "Unauthorized - check your scopes and confirm access to this resource";
            throw new XeroUnauthorizedException(statusCode, message, e);
            
        } else if (statusCode == 403) {
            String message = "Forbidden - authentication unsuccessful";
            throw new XeroForbiddenException(statusCode, message, e);
            
        } else if (statusCode == 404) {
            String message = "The resource you're looking for cannot be found";
            throw new XeroNotFoundException(statusCode, message, e);
                   
        } else if (statusCode == 429) {
            String message = "You've exceeded the per " + e.getHeaders().get("x-rate-limit-problem") + " rate limit";
            throw new XeroRateLimitException(statusCode, message, e);
        
        } else if (statusCode == 500) {
            String message = "An error occurred in Xero. Check the API Status page http://status.developer.xero.com for current service status.";
            throw new XeroServerErrorException(statusCode, message, e);

        } else if (statusCode > 500) {
            String message = "Internal Server Error";
            throw new XeroServerErrorException(statusCode, message, e);
            
        } else {
            throw new XeroApiException(statusCode, e.getStatusMessage(), e);
        }
    }
    
    
    // LEGACY ERROR HANDLER
    public void execute(HttpResponseException e, ApiClient apiClient)
            throws JsonParseException, JsonMappingException, IOException {
        Error error = null;
        int statusCode = e.getStatusCode();
        if (statusCode == 400) {
            TypeReference<Error> errorTypeRef = new TypeReference<Error>() {
            };
            error = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
            throw new XeroApiException(statusCode, e.getStatusMessage(), error, e);
        } else if (statusCode == 404) {
            error = new Error();
            Element elementsItem = new Element();
            ValidationError ve = new ValidationError();
            ve.setMessage("The resource you're looking for cannot be found");
            elementsItem.addValidationErrorsItem(ve);
            error.addElementsItem(elementsItem);
            throw new XeroApiException(statusCode, error, e);
        } else if (statusCode == 429) {
            error = new Error();
            Element elementsItem = new Element();
            ValidationError ve = new ValidationError();
            ve.setMessage("You've exceeded the per " + e.getHeaders().get("x-rate-limit-problem") + " rate limit");
            elementsItem.addValidationErrorsItem(ve);
            error.addElementsItem(elementsItem);
            throw new XeroApiException(statusCode, error, e);
        } else if (statusCode == 401) {
            throw new XeroApiException(statusCode, "Unauthorized - check your scopes and confirm access to this resource", e);
        } else if (statusCode != 400) {
            throw new XeroApiException(statusCode, e.getStatusMessage(), e);
        } else {
            throw e;
        }
    }

}
