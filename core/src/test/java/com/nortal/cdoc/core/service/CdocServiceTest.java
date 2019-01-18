package com.nortal.cdoc.core.service;

import com.nortal.cdoc.common.enums.CdocServiceErrorCode;
import com.nortal.cdoc.common.exception.CdocServiceException;
import com.nortal.cdoc.common.model.CdocServiceData;
import com.nortal.cdoc.common.model.CdocServiceFile;
import com.nortal.cdoc.core.config.CdocServiceCoreConfig;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CdocServiceCoreConfig.class})
public class CdocServiceTest {
  private static final List<String> VALID_RECIPENTS = Arrays.asList("36603150241");
  private static final List<CdocServiceFile> VALID_FILES = Arrays.asList(new CdocServiceFile("test1.txt", "abcde".getBytes()), new CdocServiceFile("test1.txt", "12345".getBytes()));
  
  @Resource
  private CdocService cdocService;

  @Test
  public void createCdocInvalidTest() {
    try {
      cdocService.createCdoc(null);
      Assert.fail();
    } catch(CdocServiceException e) {
      Assert.assertEquals(CdocServiceErrorCode.MISSING_CDOC_DATA, e.getErrorCode());
    }
    
    try {
      cdocService.createCdoc(new CdocServiceData(null, VALID_FILES));
      Assert.fail();
    } catch(CdocServiceException e) {
      Assert.assertEquals(CdocServiceErrorCode.MISSING_CDOC_RECIPIENTS, e.getErrorCode());
    }
    
    try {
      cdocService.createCdoc(new CdocServiceData(Arrays.asList("123"), VALID_FILES));
      Assert.fail();
    } catch(CdocServiceException e) {
      Assert.assertEquals(CdocServiceErrorCode.INVALID_CDOC_RECIPIENT, e.getErrorCode());
    }
    
    try {
      cdocService.createCdoc(new CdocServiceData(VALID_RECIPENTS, null));
      Assert.fail();
    } catch(CdocServiceException e) {
      Assert.assertEquals(CdocServiceErrorCode.MISSING_CDOC_FILES, e.getErrorCode());
    }
    
    try {
      cdocService.createCdoc(new CdocServiceData(VALID_RECIPENTS, Arrays.asList(new CdocServiceFile(null, "qwerty".getBytes()))));
      Assert.fail();
    } catch(CdocServiceException e) {
      Assert.assertEquals(CdocServiceErrorCode.INVALID_CDOC_FILE, e.getErrorCode());
    }
    
    try {
      cdocService.createCdoc(new CdocServiceData(VALID_RECIPENTS, Arrays.asList(new CdocServiceFile("empty.txt", null))));
      Assert.fail();
    } catch(CdocServiceException e) {
      Assert.assertEquals(CdocServiceErrorCode.INVALID_CDOC_FILE, e.getErrorCode());
    }
  }
  
  @Test
  public void createCdocTest() {
    try {
      CdocServiceFile cdoc = cdocService.createCdoc(new CdocServiceData(VALID_RECIPENTS, VALID_FILES));
      Assert.assertTrue(cdoc.getSize() > 0);
      Assert.assertNotNull(cdoc.getContent());
    } catch(Exception e) {
      Assert.fail();
    }
  }
}
