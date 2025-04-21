FROM maven:3.9.5-eclipse-temurin-21

ENV PROJECT_HOME /usr/src/main
ENV JAR_NAME erp-vendas-caixa-0.0.1-SNAPSHOT.jar

RUN mkdir -p $PROJECT_HOME
WORKDIR $PROJECT_HOME

COPY . .

RUN mvn clean package -DskipTests

RUN mv $PROJECT_HOME/target/$JAR_NAME $PROJECT_HOME/
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "erp-vendas-caixa-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080