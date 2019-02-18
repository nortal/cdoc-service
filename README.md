# cdoc-service
Java library which simplifies the creation of CDOC containers by provided files and Estonian national identity codes.

## Dependecies
- Java 8+
- Spring 5+
- [cdoc4j](https://github.com/open-eid/cdoc4j)

## Modules
### Common
Contains CDOC service models, errors and models validation utility.

### Core
Contains CDOC creation service which creates CDOC container according to provided files and recipients certificates queried from [SK](https://www.sk.ee/repositoorium/ldap/esteid-ldap-kataloogi-kasutamine/).

Service errors:

| Error code | Description |
| :--- | :--- |
| MISSING_CDOC_DATA | No input were provided for service |
| MISSING_CDOC_RECIPIENTS | No recipients were provided for service |
| INVALID_CDOC_RECIPIENT | Invalid recipient identity code or certificate is missing |
| MISSING_CDOC_FILES | No files were provided for service |
| INVALID_CDOC_FILE | Input file name or content is missing |
| CDOC_CREATION_FAILED | CDOC creation failed possibly because of cdoc4j |
| UNEXPECTED_ERROR | - |

Service Configuration (cdoc-service-core.properties):

| Key | Description |
| :--- | :--- |
| cdoc.service.sk.ldap.url | SK LDAP location |

### App
Spring Boot application for CDOC core service. Has /create-cdoc rest endpoint which accepts collection of recipients and files as input for CDOC container creation.

### Client
CDOC service application consumer.

Service Configuration (cdoc-service-client.properties):

| Key | Description |
| :--- | :--- |
| cdoc.service.app.url | CDOC service application location |
