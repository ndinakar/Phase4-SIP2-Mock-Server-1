#FROM recapscsb/scsb-base:latest
#FROM phase4-scsb-base:latest
FROM adoptopenjdk/openjdk11
MAINTAINER HTC ReCAP Support "recap-support@htcindia.com"
RUN apt-get update && apt-get install -y git
ARG TAG
ENV envTag="$TAG"
COPY shellBuild.sh /opt/
RUN chmod 750 /opt/shellBuild.sh
RUN cd /opt && ls -l && ./shellBuild.sh ${envTag}

EXPOSE 9101

ENTRYPOINT java -jar -Denvironment=$ENV /opt/phase4-scsb-mock-sip-server-2.9.8.jar "/opt/application.properties" && bash
