FROM eclipse-temurin:17-jdk
WORKDIR /appContainer
COPY target/springDataRedis-docker.jar /appContainer
EXPOSE 9095
CMD ["java","-jar","springDataRedis-docker.jar"]