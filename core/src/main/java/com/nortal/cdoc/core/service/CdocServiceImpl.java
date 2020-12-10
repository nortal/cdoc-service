package com.nortal.cdoc.core.service;

import com.nortal.cdoc.common.enums.CdocServiceErrorCode;
import com.nortal.cdoc.common.exception.CdocServiceException;
import com.nortal.cdoc.common.model.CdocServiceData;
import com.nortal.cdoc.common.model.CdocServiceFile;
import com.nortal.cdoc.common.util.CdocServiceValidationUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openeid.cdoc4j.CDOC11Builder;
import org.openeid.cdoc4j.CDOCBuilder;
import org.openeid.cdoc4j.DataFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.support.LdapNameBuilder;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 */
public class CdocServiceImpl implements CdocService {
  private static final Logger LOG = LoggerFactory.getLogger(CdocServiceImpl.class);

  @Resource
  private LdapTemplate cdocLdapTemplate;

  @Override
  public CdocServiceFile createCdoc(CdocServiceData data) {
    LOG.info("start creating cdoc");
    CdocServiceValidationUtil.validateData(data);

    LOG.info(String.format("creating cdoc to recipients=%s with files=%s",
                           StringUtils.join(data.getRecipents(), ", "),
                           StringUtils.join(data.getFiles().stream().map(CdocServiceFile::getName).collect(Collectors.toList()),
                                            ", ")));

    ByteArrayOutputStream cdocContent = new ByteArrayOutputStream();
    try {
      CDOCBuilder cdocBuilder = CDOC11Builder.defaultVersion();
      for (String recipient : data.getRecipents()) {
        cdocBuilder.withRecipient(getRecipientCert(recipient));
      }
      cdocBuilder.withDataFiles(getDataFiles(data));
      cdocBuilder.buildToOutputStream(cdocContent);
    } catch (CdocServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new CdocServiceException(CdocServiceErrorCode.CDOC_CREATION_FAILED, e);
    }
    return new CdocServiceFile(null, cdocContent.toByteArray());
  }

  private ByteArrayInputStream getRecipientCert(String recipient) {
    List<ByteArrayInputStream> certs = getRecipentCerts(recipient, "Identity card of Estonian citizen");
    if (CollectionUtils.isEmpty(certs)) {
      certs = getRecipentCerts(recipient, "Residence card of temporary residence citizen");
    }

    if (CollectionUtils.isEmpty(certs)) {
      throw new CdocServiceException(CdocServiceErrorCode.INVALID_CDOC_RECIPIENT,
              String.format("could not find cert for recipient %s", recipient));
    }

    if (certs.size() > 1) {
      throw new CdocServiceException(CdocServiceErrorCode.INVALID_CDOC_RECIPIENT,
              String.format("found multiple certs for recipient %s", recipient));
    }

    return certs.get(0);
  }

  private List<ByteArrayInputStream> getRecipentCerts(String recipient, String valueOfAttributeO) {
    LdapQuery query =
        LdapQueryBuilder.query().base(LdapNameBuilder.newInstance()
                .add("dc", "ESTEID")
                .add("o", valueOfAttributeO)
                .add("ou", "Authentication")
                .build())
                .attributes("userCertificate;binary").filter("serialNumber=PNOEE-{0}", recipient);

    return cdocLdapTemplate.search(query, (AttributesMapper<ByteArrayInputStream>) attributes
            -> new ByteArrayInputStream((byte[]) attributes.get("userCertificate;binary").get()));
  }

  private List<DataFile> getDataFiles(CdocServiceData data) {
    return data.getFiles().stream().map(f -> new DataFile(f.getName(),
                                                          f.getContent(),
                                                          f.getSize())).collect(Collectors.toList());
  }

}
