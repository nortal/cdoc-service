package com.nortal.cdoc.common.model;

import java.util.Collection;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 */
public class CdocServiceData {
  private Collection<String> recipients;
  private Collection<CdocServiceFile> files;

  public CdocServiceData() {
  }

  public CdocServiceData(Collection<String> recipients, Collection<CdocServiceFile> files) {
    this.recipients = recipients;
    this.files = files;
  }

  public Collection<String> getRecipents() {
    return recipients;
  }

  public void setRecipents(Collection<String> recipents) {
    this.recipients = recipents;
  }

  public Collection<CdocServiceFile> getFiles() {
    return files;
  }

  public void setFiles(Collection<CdocServiceFile> files) {
    this.files = files;
  }
}
