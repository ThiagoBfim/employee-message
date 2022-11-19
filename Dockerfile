FROM eclipse-temurin:17-jdk-focal
VOLUME /tmp


COPY /build/libs/touch-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]