#FROM recapscsb/scsb-base:latest
#FROM phase4-scsb-base:latest
FROM adoptopenjdk/openjdk11:jre11u-ubuntu-nightly
MAINTAINER HTC ReCAP Support "recap-support@htcindia.com"
RUN apt-get update && apt-get install -y git
ARG TAG
ENV envTag="$TAG"
ENV TERM=xterm

#Set Locale
RUN apt-get clean && apt-get update && apt-get install -y locales

RUN locale-gen en_US.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

#Set EST Timezone
ENV TZ=America/New_York
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

#Set Terminal
ENV TERM=xterm
COPY shellBuild.sh /opt/
RUN chmod 750 /opt/shellBuild.sh
RUN cd /opt && ls -l && ./shellBuild.sh ${envTag}

EXPOSE 9101

ENTRYPOINT java -jar -Denvironment=$ENV /opt/phase4-scsb-mock-sip-server-2.9.8.jar "/recap-vol/config/external-application.properties" && bash
