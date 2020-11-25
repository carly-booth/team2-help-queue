FROM maven:latest AS build-stage
COPY . /build
WORKDIR /build
RUN mvn clean install

FROM java:8 AS runtime
WORKDIR /opt/app
COPY --from=build-stage /build/target/Help-Queue-0.0.1-SNAPSHOT.jar app.jar 
ENTRYPOINT ["/usr/bin/java", "-jar", "app.jar"]