FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copy the JAR file into the container
COPY target/*.jar app.jar

# Copy the wait-for-it.sh script from the root of the service to the container
COPY wait-for-it.sh /wait-for-it.sh

# Make it executable
RUN chmod +x /wait-for-it.sh

# Expose the port that the service runs on
EXPOSE 8083

# Entry point command to wait for MySQL and then run the application
ENTRYPOINT ["/wait-for-it.sh", "mysql-grade:3306", "--", "java", "-jar", "app.jar"]
