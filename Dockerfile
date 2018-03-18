# From the OpenJDK image
FROM openjdk:8u151-jre-alpine3.7

# Listens to port 8080 for HTTP connections
EXPOSE 8080

# Create a directory for the application and its configuration
RUN mkdir -p /spring-boot/app/config

# Use the Gradle build artifact(s)
COPY ./build/libs/*.jar /spring-boot/app/application.jar

# Runs the executable JAR produced by the Gradle build
# Note: You may pass in environment variable key-pairs to override values 
#       in the Spring Boot application configuration using the 
#       "-e ENV_VARIABLE=value" option in the Docker Container Run command.
#       This is useful for supplying Spring Profile values or
#       any environment-specific overrides.
#
#       Example: Run the application on port 80 with ONLY the 'hsqldb' profile:
#       docker container run --detach --publish 80:8080 -e SPRING_PROFILES_ACTIVE=hsqldb leanstacks/skeleton-ws-spring-boot
#
CMD ["java", "-jar", "/spring-boot/app/application.jar"]