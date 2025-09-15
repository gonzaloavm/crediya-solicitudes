# Build Stage
FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /app

# Copy Gradle wrapper and configuration files
COPY gradlew .
COPY gradle/wrapper/gradle-wrapper.jar gradle/wrapper/

# Copy build scripts
COPY build.gradle .
COPY settings.gradle .
COPY main.gradle .
COPY lombok.config .

# Copy source code for all modules
COPY applications applications/
COPY domain domain/
COPY infrastructure infrastructure/

# Make gradlew executable
RUN chmod +x gradlew

# Build the application
# Use --no-daemon to avoid issues in Docker builds
# Use -x test to skip tests during build, can be removed if tests are fast and reliable
RUN ./gradlew bootJar --no-daemon -x test

# Run Stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the JAR from the build stage
# The JAR is typically found in applications/app-service/build/libs/
COPY --from=builder /app/applications/app-service/build/libs/app-service-*.jar crediya-solicitudes.jar

VOLUME /tmp

ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"

# Use a non-root user for security
USER nobody

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar crediya-solicitudes.jar" ]