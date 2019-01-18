package com.nortal.cdoc.client.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nortal.cdoc.common.enums.CdocServiceErrorCode;
import com.nortal.cdoc.common.exception.CdocServiceException;
import com.nortal.cdoc.common.model.CdocServiceError;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 */
public class CdocServiceErrorHandler extends DefaultResponseErrorHandler {
  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
    CdocServiceError error = getError(response);
    if (error != null) {
      throw new CdocServiceException(CdocServiceErrorCode.valueOf(error.getErrorCode()), error.getDescription());
    }
    throw new CdocServiceException(CdocServiceErrorCode.UNEXPECTED_ERROR,
                                   String.format("cdoc service request result: statusCode=%s, statusText=%s",
                                                 response.getStatusCode(),
                                                 response.getStatusText()));
  }

  private CdocServiceError getError(ClientHttpResponse response) {
    try {
      return objectMapper.readValue(response.getBody(), CdocServiceError.class);
    } catch (Exception e) {
    }
    return null;
  }
}
