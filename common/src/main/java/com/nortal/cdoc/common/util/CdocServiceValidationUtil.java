package com.nortal.cdoc.common.util;

import com.nortal.cdoc.common.enums.CdocServiceErrorCode;
import com.nortal.cdoc.common.exception.CdocServiceException;
import com.nortal.cdoc.common.model.CdocServiceData;
import com.nortal.cdoc.common.model.CdocServiceFile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 */
public class CdocServiceValidationUtil {
  private CdocServiceValidationUtil() {
  }

  public static void validateData(CdocServiceData data) {
    if (data == null) {
      throw new CdocServiceException(CdocServiceErrorCode.MISSING_CDOC_DATA, "cdoc data is mandatory");
    }
    Set<String> recipents = new HashSet<>();
    if (CollectionUtils.isNotEmpty(data.getRecipents())) {
      data.getRecipents().stream().filter(CdocServiceValidationUtil::isValidRecipent).forEach(recipents::add);
    }
    if (recipents.isEmpty()) {
      throw new CdocServiceException(CdocServiceErrorCode.MISSING_CDOC_RECIPIENTS, "cdoc recipients are mandatory");
    }

    List<CdocServiceFile> files = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(data.getFiles())) {
      data.getFiles().stream().filter(CdocServiceValidationUtil::isValidFile).forEach(files::add);
    }
    if (files.isEmpty()) {
      throw new CdocServiceException(CdocServiceErrorCode.MISSING_CDOC_FILES, "cdoc files are mandatory");
    }
  }

  // TODO: Check for identity code?
  public static void validateRecipent(String recipent) {
    if (StringUtils.isBlank(recipent)) {
      throw new CdocServiceException(CdocServiceErrorCode.INVALID_CDOC_RECIPIENT, "invalid cdoc recipient");
    }
  }

  private static boolean isValidRecipent(String recipent) {
    validateRecipent(recipent);
    return true;
  }

  public static void validateFile(CdocServiceFile file) {
    if (file == null || StringUtils.isBlank(file.getName()) || file.getContent() == null || file.getSize() == 0) {
      throw new CdocServiceException(CdocServiceErrorCode.INVALID_CDOC_FILE, "invalid cdoc file");
    }
  }

  private static boolean isValidFile(CdocServiceFile file) {
    validateFile(file);
    return true;
  }
}
