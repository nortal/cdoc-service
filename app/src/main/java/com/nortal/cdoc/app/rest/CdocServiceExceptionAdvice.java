package com.nortal.cdoc.app.rest;

import com.nortal.cdoc.common.enums.CdocServiceErrorCode;
import com.nortal.cdoc.common.exception.CdocServiceException;
import com.nortal.cdoc.common.model.CdocServiceError;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CdocServiceExceptionAdvice extends ResponseEntityExceptionHandler {
  private static final Logger LOG = LoggerFactory.getLogger(CdocServiceExceptionAdvice.class);

  @ExceptionHandler(CdocServiceException.class)
  public ResponseEntity<CdocServiceError> handle(HttpServletResponse response, CdocServiceException e) {
    LOG.error("Application error: ", e);
    return handle(response, e.getErrorCode(), e);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<CdocServiceError> handle(HttpServletResponse response, Exception e) {
    LOG.error("Unexpected error: ", e);
    return handle(response, CdocServiceErrorCode.UNEXPECTED_ERROR, e);
  }

  private ResponseEntity<CdocServiceError> handle(HttpServletResponse response,
                                                  CdocServiceErrorCode code,
                                                  Exception e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CdocServiceError(code.getCode(), e.getMessage()));
  }
}
