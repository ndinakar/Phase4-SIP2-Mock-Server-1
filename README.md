# SIP2-Mock-Server

3M SIP is an industry standard protocol by 3M to allow automatec check out terminals communicate with library systems.

## Software Required

      - Java 11
      - Docker 19.03.13  
      
## Prerequisite

1. external-sip-application properties

      - spring.datasource.url=jdbc:mysql://<MysqlDOckerIP>:3306/pul?autoReconnect=true&serverTimezone=America/New_York&useSSL=false
      - spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
      - spring.datasource.username=
      - spring.datasource.password=
      - spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect 
      
2. Update the below Property Values in scsb_properteis_t table.

     - ils.mock.sip.server.url= <Docker IP of SIP Mock Server >
     - ils.mock.sip.server.port=9101

## Build

Download the Project , navigate inside project folder and build the project using below command

**mvn clean install**

## Docker Image Creation

Naviagte Inside project folder where Dockerfile is present and Execute the below command

**sudo docker build -t phase4-scsb-sip-mock-server .**

## Docker Run

User the below command to Run the Docker
  
**sudo docker run --name phase4-scsb-sip-mock-server -v /data:/recap-vol -p 9101:9101 -e "ENV= -Dorg.apache.activemq.SERIALIZABLE_PACKAGES="*" -Dspring.config.location=/recap-vol/config/external-sip-application.properties " -d phase4-scsb-sip-mock-server**

