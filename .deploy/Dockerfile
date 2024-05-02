FROM amazoncorretto:17-alpine
COPY /build/libs/Asap-Backend.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar",  "/app.jar"]