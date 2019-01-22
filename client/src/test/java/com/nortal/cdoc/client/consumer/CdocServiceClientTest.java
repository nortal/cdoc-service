package com.nortal.cdoc.client.consumer;

import com.nortal.cdoc.client.config.CdocServiceClientConfig;
import com.nortal.cdoc.common.model.CdocServiceData;
import com.nortal.cdoc.common.model.CdocServiceFile;
import java.util.Arrays;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CdocServiceClientConfig.class })
public class CdocServiceClientTest {
  @Resource
  private CdocServiceClient cdocServiceClient;

  @Test
  @Ignore // deploy cdoc-service-app to run this test 
  public void createCdocTest() {
    try {
      CdocServiceData data = new CdocServiceData(Arrays.asList("36603150241"),
                                                 Arrays.asList(new CdocServiceFile("test1.txt", "1234".getBytes()),
                                                               new CdocServiceFile("test2.txt", "abcd".getBytes())));
      CdocServiceFile cdoc = cdocServiceClient.createCdoc(data);
      Assert.assertTrue(cdoc.getSize() > 0);
      Assert.assertNotNull(cdoc.getContent());
    } catch (Exception e) {
      Assert.fail();
    }
  }
}
