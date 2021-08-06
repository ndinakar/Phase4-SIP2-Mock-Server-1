# 3M SIP Circulation Library for Java

3M SIP is an industry standard protocol by 3M to allow automatec check out terminals communicate with library systems.

### build Docker image
Build the application
```
mvn clean install
```
Build the container:
```
docker build -t phase4-scsb-sip-mock-server .
```
Run the container (example)
```
docker run --name phase4-scsb-sip-mock-server -p 9101:9101 -e "ENV= -Dorg.apache.activemq.SERIALIZABLE_PACKAGES="*" -Dspring.config.location=/vol/config/external-sip-application.properties " -d phase4-scsb-sip-mock-server
```

## Properties
ils.mock.sip.server.url = 172.17.0.8

ils.mock.sip.server.port = 9101

