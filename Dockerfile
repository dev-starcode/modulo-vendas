# Etapa de build
FROM maven:3.9.5-eclipse-temurin-21 AS builder

ENV PROJECT_HOME=/usr/src/main
ENV JAR_NAME=erp-vendas-caixa-0.0.1-SNAPSHOT.jar

WORKDIR $PROJECT_HOME
COPY . .
RUN mvn clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:21-jdk

ENV PROJECT_HOME=/usr/src/main
ENV JAR_NAME=erp-vendas-caixa-0.0.1-SNAPSHOT.jar

WORKDIR $PROJECT_HOME
COPY --from=builder $PROJECT_HOME/target/$JAR_NAME .

EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=production", "-jar", "erp-vendas-caixa-0.0.1-SNAPSHOT.jar"]
