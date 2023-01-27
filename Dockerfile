FROM maven:3.6.3 AS maven                       

WORKDIR /usr/src/app
COPY . /usr/src/app/

RUN mvn package

FROM adoptopenjdk/openjdk11:alpine-jre                              

ARG JAR_FILE=DevOpsUsach2020.jar

COPY --from=maven /usr/src/app/build/${JAR_FILE} /opt/app/    

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/app/DevOpsUsach2020.jar"] 