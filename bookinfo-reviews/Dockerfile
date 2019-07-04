FROM registry.sloth.com/third/openjdk:8-jdk-alpine
ADD /target/bookinfo-reviews.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]