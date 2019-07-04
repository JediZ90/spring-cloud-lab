FROM registry.sloth.com/third/openjdk:8-jdk-alpine
ADD /target/seata-order-service.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]