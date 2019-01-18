package com.nortal.cdoc.common.enums;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 */
public enum CdocServiceErrorCode {
  MISSING_CDOC_DATA,
  MISSING_CDOC_RECIPIENTS,
  INVALID_CDOC_RECIPIENT,
  MISSING_CDOC_FILES,
  INVALID_CDOC_FILE,
  CDOC_CREATION_FAILED,
  UNEXPECTED_ERROR;

  public String getCode() {
    return name();
  }
}
