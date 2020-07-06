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
  public void validationError(String objectType, com.xero.models.accounting.Error error) {
    throw new XeroBadRequestException(objectType, error);
  }

  // ASSETS Validation Errors (400)
  public void validationError(String objectType, com.xero.models.assets.Error error) {
    throw new XeroBadRequestException(objectType, error);
  }

  // BANKFEED Statements Validation Errors (400)
  public void validationError(String objectType, Statements error) {
    throw new XeroBadRequestException(objectType, error);
  }

  // BANKFEED Connections Validation Errors (400)
  public void validationError(String objectType, FeedConnections error) {
    throw new XeroBadRequestException(objectType, error);
  }

  // PAYROLL UK Validation Errors
  public void validationError(
      Integer statusCode, String objectType, com.xero.models.payrolluk.Problem error) {
    if (statusCode == 400) {
      throw new XeroBadRequestException(objectType, error);
    } else if (statusCode == 405) {
      throw new XeroMethodNotAllowedException(objectType, error);
    }
  }

  // PAYROLL AU Employees Validation Errors (400)
  public void validationError(String objectType, com.xero.models.payrollau.Employees employees) {
    throw new XeroBadRequestException(objectType, employees);
  }

  public void validationError(
      String objectType, com.xero.models.payrollau.LeaveApplications leaveApplications) {
    throw new XeroBadRequestException(objectType, leaveApplications);
  }

  public void validationError(String objectType, com.xero.models.payrollau.PayItems payItems) {
    throw new XeroBadRequestException(objectType, payItems);
  }

  public void validationError(String objectType, com.xero.models.payrollau.PayRuns payRuns) {
    throw new XeroBadRequestException(objectType, payRuns);
  }

  public void validationError(
      String objectType, com.xero.models.payrollau.PayrollCalendars payrollCalendars) {
    throw new XeroBadRequestException(objectType, payrollCalendars);
  }

  public void validationError(String objectType, com.xero.models.payrollau.SuperFunds superFunds) {
    throw new XeroBadRequestException(objectType, superFunds);
  }

  public void validationError(String objectType, com.xero.models.payrollau.Timesheets timesheets) {
    throw new XeroBadRequestException(objectType, timesheets);
  }

  public void validationError(String objectType, String msg) {
    throw new XeroBadRequestException(400, msg);
  }

  // REFACTOR GENERIC ERROR HANDLER
  public void execute(HttpResponseException e) {
    int statusCode = e.getStatusCode();

    if (statusCode == 400) {
      String message = e.getMessage();
      throw new XeroBadRequestException(statusCode, message);

    } else if (statusCode == 401) {
      String message = "Unauthorized - check your scopes and confirm access to this resource";
      throw new XeroUnauthorizedException(statusCode, message);

    } else if (statusCode == 403) {
      String message = "Forbidden - authentication unsuccessful";
      throw new XeroForbiddenException(statusCode, message);

    } else if (statusCode == 404) {
      String message = "The resource you're looking for cannot be found";
      throw new XeroNotFoundException(statusCode, message);

    } else if (statusCode == 429) {
      String message =
          "You've exceeded the per " + e.getHeaders().get("x-rate-limit-problem") + " rate limit";
      throw new XeroRateLimitException(statusCode, message);

    } else if (statusCode == 500) {
      String message =
          "An error occurred in Xero. Check the API Status page http://status.developer.xero.com"
              + " for current service status.";
      throw new XeroServerErrorException(statusCode, message);

    } else if (statusCode > 500) {
      String message = "Internal Server Error";
      throw new XeroServerErrorException(statusCode, message);

    } else {
      throw new XeroApiException(statusCode, e.getStatusMessage());
    }
  }

  // LEGACY ERROR HANDLER
  public void execute(HttpResponseException e, ApiClient apiClient)
      throws JsonParseException, JsonMappingException, IOException {
    Error error = null;
    int statusCode = e.getStatusCode();
    if (statusCode == 400) {
      TypeReference<Error> errorTypeRef = new TypeReference<Error>() {};
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
      ve.setMessage(
          "You've exceeded the per " + e.getHeaders().get("x-rate-limit-problem") + " rate limit");
      elementsItem.addValidationErrorsItem(ve);
      error.addElementsItem(elementsItem);
      throw new XeroApiException(statusCode, error);
    } else if (statusCode == 401) {
      throw new XeroApiException(
          401, "Unauthorized - check your scopes and confirm access to this resource");
    } else if (statusCode != 400) {
      throw new XeroApiException(statusCode, e.getStatusMessage());
    } else {
      throw e;
    }
  }
}
