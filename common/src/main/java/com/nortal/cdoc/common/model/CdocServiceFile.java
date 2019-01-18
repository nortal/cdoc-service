package com.nortal.cdoc.common.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 */
public class CdocServiceFile {
  private String name;
  private InputStream content;
  private long size;

  public CdocServiceFile() {
  }

  public CdocServiceFile(String name, byte[] content) {
    this(name, content != null ? new ByteArrayInputStream(content) : null, content != null ? content.length : 0);
  }

  public CdocServiceFile(String name, InputStream content, long size) {
    this.name = name;
    this.content = content;
    this.size = size;
  }

  public String getName() {
    return name;
  }

  public InputStream getContent() {
    return content;
  }

  public long getSize() {
    return size;
  }
}
