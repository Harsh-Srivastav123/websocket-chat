#!/bin/bash

# Stop existing Spring Boot applications running on port 8000, 8080, and 9000
pm2 stop app1 || true

# Start new Spring Boot application on port 8000
pm2 start --name app1 java -- -jar target/websocket-chat-0.0.1-SNAPSHOT.jar --server.port=8000 --kafka.groupId=group-1 
pm2 stop app2 || true 
# Start new Spring Boot application on port 8080
pm2 start --name app2 java -- -jar target/websocket-chat-0.0.1-SNAPSHOT.jar --server.port=8080 --kafka.groupId=group-2
pm2 stop app3 || true
# Start new Spring Boot application on port 9000
# pm2 start --name app3 java -- -jar target/websocket-chat-0.0.1-SNAPSHOT.jar --server.port=9000 --kafka.groupId=group-3
