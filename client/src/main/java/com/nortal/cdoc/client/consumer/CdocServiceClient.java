package com.nortal.cdoc.client.consumer;

import com.nortal.cdoc.common.enums.CdocServiceErrorCode;
import com.nortal.cdoc.common.exception.CdocServiceException;
import com.nortal.cdoc.common.model.CdocServiceData;
import com.nortal.cdoc.common.model.CdocServiceFile;
import com.nortal.cdoc.common.util.CdocServiceValidationUtil;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 */
public class CdocServiceClient {
  @Resource
  private RestTemplate cdocServiceRestTemplate;
  @Resource
  private Environment env;

  public CdocServiceFile createCdoc(CdocServiceData data) {
    return createCdoc(null, data);
  }

  public CdocServiceFile createCdoc(String cdocServiceAppUrl, CdocServiceData data) {
    CdocServiceValidationUtil.validateData(data);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.addAll("recipents", new ArrayList<>(data.getRecipents()));
    body.addAll("files", data.getFiles().stream().map(f -> {
      try {
        return new CdocServiceResource(f);
      } catch (IOException e) {
        throw new CdocServiceException(CdocServiceErrorCode.INVALID_CDOC_FILE, e);
      }
    }).collect(Collectors.toList()));
    HttpEntity<MultiValueMap<String, Object>> req = new HttpEntity<>(body, headers);

    try {
      ResponseEntity<byte[]> rsp =
          cdocServiceRestTemplate.postForEntity(getUri(cdocServiceAppUrl, "api/create-cdoc"), req, byte[].class);
      return new CdocServiceFile(null, rsp.getBody());
    } catch(CdocServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new CdocServiceException(CdocServiceErrorCode.UNEXPECTED_ERROR, e);
    }
  }

  private URI getUri(String cdocServiceAppUrl, String path) {
    String appUrl = StringUtils.defaultIfBlank(cdocServiceAppUrl, env.getProperty("cdoc.service.app.url"));
    if (!StringUtils.endsWith(appUrl, "/")) {
      appUrl += "/";
    }
    return UriComponentsBuilder.fromUriString(appUrl).path(path).build().toUri();
  }

  private static class CdocServiceResource extends ByteArrayResource {
    private String fileName;

    public CdocServiceResource(CdocServiceFile file) throws IOException {
      super(StreamUtils.copyToByteArray(file.getContent()));
      this.fileName = file.getName();
    }

    public String getFilename() {
      return fileName;
    }
  }
}
