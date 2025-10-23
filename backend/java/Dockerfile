FROM gradle:8.12.1-jdk17 AS build

WORKDIR /app
COPY ./java ./java
COPY ./protobuf ./protobuf
WORKDIR /app/java

RUN apt update && apt install protobuf-compiler -y && \
    gradle build --no-daemon -x test

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app
COPY --from=build /app/java/build/libs/book-exchange-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
