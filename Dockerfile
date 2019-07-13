FROM openjdk:11-jre
WORKDIR /code
COPY build/libs/db-0.0.1-SNAPSHOT.jar /code/app.jar
ENTRYPOINT [ "sh", "-c", "java -Duser.timezone=GMT+08 -Dspring.profiles.active=prod -Djava.security.egd=file:/dev/./urandom -jar app.jar" ]
EXPOSE 8080