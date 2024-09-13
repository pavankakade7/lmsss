FROM openjdk:17
EXPOSE 9090
ADD target/gravity-service-docker.jar gravity-service-docker.jar
ENTRYPOINT ["java","-jar","/gravity-service-docker.jar"]