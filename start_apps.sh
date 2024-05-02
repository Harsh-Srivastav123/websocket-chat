#!/bin/bash

# Stop existing Spring Boot applications running on port 8000, 8080, and 9000
pm2 stop app1

# Start new Spring Boot application on port 8000
pm2 start --name spring-boot-app-8000 java -- -jar --server.port=8000 --kafka.groupId=group-1 --spring.kafka.consumer.group-id=group-1 target/websocket-chat-0.0.1-SNAPSHOT.jar 
pm2 stop app2
# Start new Spring Boot application on port 8080
pm2 start --name spring-boot-app-8080 java -- -jar --server.port=8080 --kafka.groupId=group-2 --spring.kafka.consumer.group-id=group-2 target/websocket-chat-0.0.1-SNAPSHOT.jar 
pm2 stop app3
# Start new Spring Boot application on port 9000
pm2 start --name spring-boot-app-9000 java -- -jar --server.port=9090 --kafka.groupId=group-3 --spring.kafka.consumer.group-id=group-3 target/websocket-chat-0.0.1-SNAPSHOT.jar 
