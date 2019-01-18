package com.nortal.cdoc.common.model;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 */
public class CdocServiceError {
  private String errorCode;
  private String description;

  public CdocServiceError() {
  }

  public CdocServiceError(String code, String description) {
    this.errorCode = code;
    this.description = description;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}