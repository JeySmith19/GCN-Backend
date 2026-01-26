FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java","-jar","target/Subastas-0.0.1-SNAPSHOT.jar"]