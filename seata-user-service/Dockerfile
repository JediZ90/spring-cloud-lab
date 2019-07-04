FROM registry.sloth.com/third/openjdk:8-jdk-alpine
ADD /target/seata-user-service.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]