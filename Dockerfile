FROM maven:3.6.3-jdk-8 as builder
COPY . /app
WORKDIR /app
RUN mvn clean package

FROM openjdk:8-jre-alpine
COPY --from=builder /app/build/*.jar /app/app.jar
ENV PORT=8080
EXPOSE ${PORT}
ENTRYPOINT ["java", "-jar", "/app/app.jar"]