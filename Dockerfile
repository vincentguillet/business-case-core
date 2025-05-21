# api-mmo-swtor/Dockerfile

FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests business-case-core


FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /build/business-case-core/target/business-case-core-1.0-SNAPSHOT.jar app.jar

COPY ../wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

ENTRYPOINT ["/wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "app.jar"]
