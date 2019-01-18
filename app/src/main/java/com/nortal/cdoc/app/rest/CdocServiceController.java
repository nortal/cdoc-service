package com.nortal.cdoc.app.rest;

import com.nortal.cdoc.common.model.CdocServiceData;
import com.nortal.cdoc.common.model.CdocServiceFile;
import com.nortal.cdoc.core.service.CdocService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 */
@RestController
@RequestMapping("/api")
public class CdocServiceController {
  @Resource
  private CdocService cdocService;

  @PostMapping(value = "/create-cdoc", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public @ResponseBody byte[] createCdoc(@NotNull @RequestParam("recipents") List<String> recipents,
                                         @NotNull @RequestParam("files") List<MultipartFile> files)
      throws IOException {
    CdocServiceData data = new CdocServiceData();
    data.setRecipents(recipents);
    List<CdocServiceFile> cdocFiles = new ArrayList<>();
    for (MultipartFile file : files) {
      cdocFiles.add(new CdocServiceFile(file.getOriginalFilename(), file.getInputStream(), file.getSize()));
    }
    data.setFiles(cdocFiles);
    return IOUtils.toByteArray(cdocService.createCdoc(data).getContent());
  }
}
