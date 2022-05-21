FROM openjdk:18-jdk-alpine
EXPOSE 8080

COPY ./subby /usr/local/bin/subby
COPY ./package/gateway.jar gateway.jar

ENTRYPOINT [ "java", "-jar", "/gateway.jar" ]

#RUN \
#  apk add  --no-cache ffmpeg

#RUN mv /usr/local/bin/SubbyNetCore /usr/local/bin/subby
#RUN chmod -R 777 /usr/local/bin/subby