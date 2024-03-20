FROM openjdk:17-slim

## Set the working directory
#WORKDIR /app

# Copy the JAR file from the build image
#COPY --from=build /app/target/your-application-name.jar ./app.jar
ADD target/websocket-chat-0.0.1-SNAPSHOT.jar websocket-chat-0.0.1-SNAPSHOT.jar
# Expose port 8080


VOLUME /tmp

# Run the application
ENTRYPOINT ["java", "-jar", "websocket-chat-0.0.1-SNAPSHOT.jar"]