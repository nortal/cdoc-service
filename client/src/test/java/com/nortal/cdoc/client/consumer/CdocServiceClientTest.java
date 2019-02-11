package com.nortal.cdoc.client.consumer;

import com.nortal.cdoc.client.config.CdocServiceClientConfig;
import com.nortal.cdoc.common.model.CdocServiceData;
import com.nortal.cdoc.common.model.CdocServiceFile;
import java.io.FileInputStream;
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
  //@Ignore // deploy cdoc-service-app to run this test 
  public void createCdocTest() {
    try {
      for (int i = 0; i < 10; i++) {
        CdocServiceData data = new CdocServiceData(Arrays.asList("36603150241"),
                                                   Arrays.asList(new CdocServiceFile("treatment.hc_page4.template_06-02-2019-12-14-33.pdf", new FileInputStream("C:\\projects\\git\\cdoc-service\\client\\src\\test\\resources\\treatment.hc_page4.template_06-02-2019-12-14-33.pdf"), -1)));
        CdocServiceFile cdoc = cdocServiceClient.createCdoc(data);
        System.out.println(String.format("Size %d", cdoc.getSize()));
        Assert.assertTrue(cdoc.getSize() > 0);
        Assert.assertNotNull(cdoc.getContent());
        Thread.currentThread().sleep(2000);
      }
    } catch (Exception e) {
      Assert.fail();
    }
  }
}
