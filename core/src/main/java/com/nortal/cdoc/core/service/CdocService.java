package com.nortal.cdoc.core.service;

import com.nortal.cdoc.common.model.CdocServiceData;
import com.nortal.cdoc.common.model.CdocServiceFile;

/**
* @author Lauri Lättemäe <lauri.lattemae@nortal.com>
*/
public interface CdocService {
  CdocServiceFile createCdoc(CdocServiceData data);
}
