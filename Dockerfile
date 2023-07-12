FROM openjdk:11-jre-slim
COPY build/libs/everything-shop-0.0.1-SNAPSHOT.jar /home/app.jar
CMD ["java", "-jar", "/home/app.jar"]