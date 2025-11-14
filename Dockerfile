FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy jar from host
COPY target/jira-mcp-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
