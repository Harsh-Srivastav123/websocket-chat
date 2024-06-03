# ChatRoomApp - Spring Boot Project

ChatRoomApp, powered by Java Spring Boot, is a real-time chatting application that leverages WebSocket technology for seamless communication. Users can join chatrooms and engage in conversations with other participants. The application ensures persistent chat history using a MySQL database, allowing users to rejoin chatrooms and retrieve previous conversations effortlessly. Additionally, ChatRoomApp features scalability enhancements through Apache Kafka, enabling real-time communication across microservices. 

The CI/CD pipeline, established with GitHub Actions, expedites deployment processes, while YAML configurations facilitate Maven project builds and jar execution on AWS EC2 instances. Nginx is employed as a load balancer and reverse proxy for enhanced performance and availability.

Integration of multimedia sharing capabilities is underway, utilizing AWS S3 for efficient storage and retrieval of multimedia content within chats.

![Deployment](https://github.com/Harsh-Srivastav123/websocket-chat/blob/main/chatroom-1.png)

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Configuration](#configuration)

- [Deployment](#deployment)
- [Key Points](#key-points)

## Project Overview

ChatRoomApp facilitates real-time communication through the following features:

- **Real-time Chatting:** Users can join chatrooms and engage in real-time conversations with other participants.
- **Persistent Chat History:** All chatroom conversations are stored in a MySQL database, ensuring users can retrieve previous chats upon rejoining.
- **Scalability:** Integration with Apache Kafka enables real-time communication across different microservices, enhancing scalability.
- **CI/CD Pipeline:** GitHub Actions streamline deployment processes, ensuring rapid iteration and delivery of new features.
- **Load Balancing:** Nginx acts as a load balancer and reverse proxy, enhancing server availability and performance.

## Features

ChatRoomApp offers the following features:

### Real-time Chatting:
- **WebSocket Communication:** Utilizes WebSocket technology for real-time communication between users.
- **Chatroom Creation:** Users can create chatroom groups for specific discussions or topics.
- **Join and Leave Chatrooms:** Participants can join or leave chatrooms at any time.
- **Persistent Chat History:** All chatroom conversations are stored in a MySQL database, allowing users to retrieve previous chats.




## WebSocket Scaling

WebSocket scaling in ChatRoomApp is achieved through integration with Apache Kafka, facilitating real-time communication across different microservices. This section details the implementation and benefits of WebSocket scaling:

## Implementation

1. **Integration with Apache Kafka:** ChatRoomApp integrates with Apache Kafka, a distributed event streaming platform, to enable real-time messaging and scaling.
   
2. **Message Publishing:** When a user sends a message in a chatroom, the message is published to a Kafka topic dedicated to chatroom communication.
   
3. **Message Consumption:** Multiple instances of ChatRoomApp microservices subscribe to the Kafka topic and consume messages in real-time. This allows horizontal scaling of WebSocket communication, as each instance handles a portion of the message load.
   
4. **Load Balancing:** Nginx, acting as a load balancer, distributes incoming WebSocket connections across multiple instances of ChatRoomApp microservices. This ensures efficient utilization of resources and optimal performance.

## Benefits

1. **Scalability:** Integration with Apache Kafka enables horizontal scaling of WebSocket communication. As the number of users and messages increases, additional instances of ChatRoomApp microservices can be deployed to handle the load.
   
2. **Fault Tolerance:** Kafka provides fault tolerance by replicating messages across multiple brokers. In case of failure, messages can still be consumed by other microservice instances, ensuring continuity of WebSocket communication.
   
3. **Real-time Communication:** WebSocket scaling ensures that users experience real-time communication without delays or interruptions, even during peak usage periods.
   
4. **Efficient Resource Utilization:** Load balancing and message distribution across multiple microservice instances ensure efficient resource utilization, optimizing performance and reliability.

## Future Considerations

1. **Monitoring and Optimization:** Continuous monitoring and optimization of Kafka and microservice instances are essential to maintain optimal performance and scalability.
   
2. **Auto-scaling:** Implementing auto-scaling policies based on metrics such as message queue length and CPU utilization can further enhance scalability and resource efficiency.
   
3. **Security Considerations:** Ensure proper security measures are in place to protect Kafka clusters and WebSocket connections from potential threats, such as unauthorized access or data breaches.
  
5. **AWS DynamoDB:** Shift towards using AWS DynamoDB for chat history storage to leverage its scalability and performance.

6. **Microservices Architecture:** Enhance microservice architecture by integrating Kafka for separate microservices, focusing on chat saving.

7. **Monitoring and Observability:** Implement Grafana, Loki, and Prometheus for robust log monitoring and observability.

WebSocket scaling with Apache Kafka in ChatRoomApp provides a robust foundation for real-time communication, ensuring scalability, fault tolerance, and optimal performance across microservices.


## CI/CD Pipeline:
- **GitHub Actions:** Establishes a CI/CD pipeline for automated build, test, and deployment processes, ensuring rapid delivery of updates.

### Multimedia Integration :
- **AWS S3 Integration:** Work in progress for multimedia integration, enabling users to share multimedia content within chats. Utilizes AWS S3 for efficient storage and retrieval.

## Technologies Used

- Java Spring Boot
- WebSocket
- SockJS and STOMP
- MySQL Database
- Apache Kafka
- GitHub Actions
- AWS EC2
- Nginx
- AWS S3 

## Getting Started

### Prerequisites

Ensure the following are installed:

- Java Development Kit (JDK)
- MySQL Database
- Apache Maven
- AWS Account
- Docker (optional for containerization)

### Installation

1. Clone the repository:

   ```shell
   git clone https://github.com/yourusername/websocket-chat.git
