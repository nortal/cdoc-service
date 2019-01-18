package com.nortal.cdoc.common.exception;

import com.nortal.cdoc.common.enums.CdocServiceErrorCode;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 */
public class CdocServiceException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private CdocServiceErrorCode errorCode;

  public CdocServiceException(CdocServiceErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public CdocServiceException(CdocServiceErrorCode errorCode, Throwable e) {
    super(e);
    this.errorCode = errorCode;
  }

  public CdocServiceErrorCode getErrorCode() {
    return errorCode;
  }
}
