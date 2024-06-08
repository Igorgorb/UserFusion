# Stage 1: Building a minimal image for copying and extracting the jar file
FROM eclipse-temurin:17-jre-alpine AS extracted
#WORKDIR /app/logs
WORKDIR /app
ARG BUILD_PATH
COPY ./${BUILD_PATH}/build/libs/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# Stage 2: Creating the final Docker image
FROM eclipse-temurin:17-jre-alpine AS runtime
COPY --from=extracted /app/dependencies/ ./
COPY --from=extracted /app/spring-boot-loader/ ./
COPY --from=extracted /app/snapshot-dependencies/ ./
COPY --from=extracted /app/application/ ./
EXPOSE 80
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]