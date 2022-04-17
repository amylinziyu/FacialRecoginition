# syntax=docker/dockerfile:1

FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY target/demo-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "/app/demo-0.0.1-SNAPSHOT.jar"]

#docker build -t back-end .
#docker -p 8080:8080 back-end

#docker login containerregistry2022x.azurecr.io
#Username: containerregistry2022x
#Password: 1PfAm3ZzufUpO9E+jxsZ8m3pS2JtKUe8
#docker tag back-end containerregistry2022x.azurecr.io/back-end
#docker push containerregistry2022x.azurecr.io/back-end