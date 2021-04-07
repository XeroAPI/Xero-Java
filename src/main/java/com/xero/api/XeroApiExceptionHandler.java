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

/** XeroApiExceptionHandler main method for handling and throwing errors returned by Xero API */
public class XeroApiExceptionHandler {

    /** Init Xero Api Exception Hander   */
    public XeroApiExceptionHandler() {
        super();
    }

    /** Handle Validation Errors (400)
    * @param objectType String object type being interacted with when the error was returned.
    * @param error Error object with details specific to Accounting API
    * @param e Exception object with details about the original exception
    */
    public void validationError(String objectType, com.xero.models.accounting.Error error, Exception e) {
        throw new XeroBadRequestException(objectType, error, e);
    }

    /** Handle Validation Errors (400)
    * @param objectType String object type being interacted with when the error was returned.
    * @param error Error object with details specific to Assets API
    * @param e Exception object with details about the original exception
    */
    public void validationError(String objectType, com.xero.models.assets.Error error, Exception e) {
        throw new XeroBadRequestException(objectType, error, e);
    }

    /** Handle Validation Errors (400)
    * @param objectType String object type being interacted with when the error was returned.
    * @param error Statements object with details specific to Bank Feeds API
    * @param e Exception object with details about the original exception
    */
    public void validationError(String objectType, Statements error, Exception e) {
        throw new XeroBadRequestException(objectType, error, e);
    }
    
    /** Handle Validation Errors (400)
    * @param objectType String object type being interacted with when the error was returned.
    * @param error FeedConnections object with details specific to Bank Feeds API
    * @param e Exception object with details about the original exception
    */
    public void validationError(String objectType, FeedConnections error, Exception e) {
        throw new XeroBadRequestException(objectType, error, e);
    }
    
    /** Handle Validation Errors (400)
    * @param statusCode Integer the server status code returned.
    * @param objectType String object type being interacted with when the error was returned.
    * @param error Problem object with details specific to UK Payroll API
    * @param e Exception object with details about the original exception
    */
    public void validationError(Integer statusCode, String objectType, com.xero.models.payrolluk.Problem error, Exception e) {
        if (statusCode == 400 ) {
            throw new XeroBadRequestException(objectType, error, e);
        } else if(statusCode == 405) {
            throw new XeroMethodNotAllowedException(objectType, error, e);
        }
    }
    
    /** Handle Validation Errors (400)
    * @param statusCode Integer the server status code returned.
    * @param objectType String object type being interacted with when the error was returned.
    * @param error Problem object with details specific to NZ Payroll API
    * @param e Exception object with details about the original exception
    */
    public void validationError(Integer statusCode, String objectType, com.xero.models.payrollnz.Problem error, Exception e) {
        if (statusCode == 400 ) {
            throw new XeroBadRequestException(objectType, error, e);
        } else if(statusCode == 405) {
            throw new XeroMethodNotAllowedException(objectType, error, e);
        } else if(statusCode == 409) {
            throw new XeroConflictException(objectType, error, e);
        }
    }
    
    /** Handle Validation Errors (400)
    * @param objectType String object type being interacted with when the error was returned.
    * @param employees Employees object with details specific to AU Payroll API
    * @param e Exception object with details about the original exception
    */
    public void validationError(String objectType, com.xero.models.payrollau.Employees employees, Exception e) {
        throw new XeroBadRequestException(objectType, employees, e);
    }
    
    /** Handle Validation Errors (400)
    * @param objectType String object type being interacted with when the error was returned.
    * @param leaveApplications LeaveApplications object with details specific to AU Payroll API
    * @param e Exception object with details about the original exception
    */
    public void validationError(String objectType, com.xero.models.payrollau.LeaveApplications leaveApplications, Exception e) {
        throw new XeroBadRequestException(objectType, leaveApplications, e);
    }

    /** Handle Validation Errors (400)
    * @param objectType String object type being interacted with when the error was returned.
    * @param payItems PayItems object with details specific to AU Payroll API
    * @param e Exception object with details about the original exception
    */
    public void validationError(String objectType, com.xero.models.payrollau.PayItems payItems, Exception e) {
        throw new XeroBadRequestException(objectType, payItems, e);
    }
    
    /** Handle Validation Errors (400)
    * @param objectType String object type being interacted with when the error was returned.
    * @param payRuns PayRuns object with details specific to AU Payroll API
    * @param e Exception object with details about the original exception
    */
    public void validationError(String objectType, com.xero.models.payrollau.PayRuns payRuns, Exception e) {
        throw new XeroBadRequestException(objectType, payRuns, e);
    }
    
    /** Handle Validation Errors (400)
    * @param objectType String object type being interacted with when the error was returned.
    * @param payrollCalendars PayrollCalendars object with details specific to AU Payroll API
    * @param e Exception object with details about the original exception
    */
    public void validationError(String objectType, com.xero.models.payrollau.PayrollCalendars payrollCalendars, Exception e) {
        throw new XeroBadRequestException(objectType, payrollCalendars, e);
    }

    /** Handle Validation Errors (400)
    * @param objectType String object type being interacted with when the error was returned.
    * @param superFunds SuperFunds object with details specific to AU Payroll API
    * @param e Exception object with details about the original exception
    */
    public void validationError(String objectType, com.xero.models.payrollau.SuperFunds superFunds, Exception e) {
        throw new XeroBadRequestException(objectType, superFunds, e);
    }
    
    /** Handle Validation Errors (400)
    * @param objectType String object type being interacted with when the error was returned.
    * @param timesheets Timesheets object with details specific to AU Payroll API
    * @param e Exception object with details about the original exception
    */
    public void validationError(String objectType, com.xero.models.payrollau.Timesheets timesheets, Exception e) {
        throw new XeroBadRequestException(objectType, timesheets, e);
    }
    
    /** Handle Validation Errors (400)
    * @param objectType String object type being interacted with when the error was returned.
    * @param msg Sting with details specific to Accounting API
    * @param e Exception object with details about the original exception
    */
    public void validationError(String objectType, String msg, Exception e) {
        throw new XeroBadRequestException(400, msg, e);
    }
    
    /** GENERIC ERROR HANDLER
    * @param e HttpResponseException object with details about the original exception
    */
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
            String minuteLimitRemainingStr = e.getHeaders().getFirstHeaderStringValue("X-MinLimit-Remaining");
            Integer minuteLimitRemaining = minuteLimitRemainingStr == null ? null : Integer.parseInt(minuteLimitRemainingStr);

            String dayLimitRemainingStr = e.getHeaders().getFirstHeaderStringValue("X-DayLimit-Remaining");
            Integer dayLimitRemaining = dayLimitRemainingStr == null ? null : Integer.parseInt(dayLimitRemainingStr);

            String appMinuteLimitRemainingStr = e.getHeaders().getFirstHeaderStringValue("X-AppMinLimit-Remaining");
            Integer appMinuteLimitRemaining = appMinuteLimitRemainingStr == null ? null : Integer.parseInt(appMinuteLimitRemainingStr);

            String retryAfterSecondsStr = e.getHeaders().getRetryAfter();
            Long retryAfterSeconds = retryAfterSecondsStr == null ? null : Long.parseLong(retryAfterSecondsStr);

            String rateLimitProblem = e.getHeaders().getFirstHeaderStringValue("X-Rate-Limit-Problem");
            String message = "You've exceeded the per " + rateLimitProblem + " rate limit";

            if (minuteLimitRemaining != null && minuteLimitRemaining <= 0) {
                throw new XeroMinuteRateLimitException(statusCode, appMinuteLimitRemaining, dayLimitRemaining, minuteLimitRemaining, retryAfterSeconds, message, e);
            } else if (dayLimitRemaining != null && dayLimitRemaining <= 0) {
                throw new XeroDailyRateLimitException(statusCode, appMinuteLimitRemaining, dayLimitRemaining, minuteLimitRemaining, retryAfterSeconds, message, e);
            } else if (appMinuteLimitRemaining != null && appMinuteLimitRemaining <= 0) {
                throw new XeroAppMinuteRateLimitException(statusCode, appMinuteLimitRemaining, dayLimitRemaining, minuteLimitRemaining, retryAfterSeconds, message, e);
            } else {
                throw new XeroRateLimitException(statusCode, appMinuteLimitRemaining, dayLimitRemaining, minuteLimitRemaining, retryAfterSeconds, message, e);
            }

        } else if (statusCode == 500) {
            String message = "An error occurred in Xero. Check the API Status page http://status.developer.xero.com for current service status.";
            throw new XeroServerErrorException(statusCode, message, e);

        } else if (statusCode == 501) {
            String message = "The method you have called has not been implemented";
            throw new XeroNotImplementedException(statusCode, message, e);

        } else if (statusCode == 503) {
            throw new XeroNotAvailableException(statusCode, e.getStatusMessage(), e);

        } else {
            throw new XeroApiException(statusCode, e.getStatusMessage(), e);
        }
    }
    
    /** LEGACY ERROR HANDLER
    * @param e HttpResponseException object with details about the original exception
    * @param apiClient ApiClient class for use in deserializing http response
    * @throws JsonParseException for parsing errors
    * @throws JsonMappingException for mapping errors
    * @throws IOException for failed or interrupted I/O operations
    */
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

