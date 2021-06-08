# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Run stage
#
FROM openjdk:11-jdk-slim AS run
COPY --from=build /home/app/target/heist-1.0.0.jar heist.jar
ENTRYPOINT ["java","-jar","/heist.jar"]