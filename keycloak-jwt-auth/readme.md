# Must read
## KEYCLOAK configs
**Realm Name : spring-boot-app**

**Client Name : spring-security**

**Clients -> spring-security -> roles -> create a role**

**user -> roles -> assign a role**

**Client Authentication is turned off**

## Fetching the token

**Get the token from:** *http://localhost:8088/realms/spring-boot-app/protocol/openid-connect/token*

**method: POST**

**the headers will be following:**
- grant_type : password
- client_id : spring-security
- username : mugdho
- password : mugdho

### Using the spring boot app's API
**Set Authorization as Bearer Token 