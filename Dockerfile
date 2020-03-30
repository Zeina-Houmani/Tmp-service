
# Use this image as a build-base
FROM openjdk:8-jdk-alpine

# The application's jar file
ARG JAR_FILE=target/blur-service.jar

# Add the application's jar to the container
ADD ${JAR_FILE} blur-app.jar

#RUN sh -c 'touch blur-app.jar'

# Run the jar file 
ENTRYPOINT ["java","-jar","blur-app.jar"]

# Make port 9200 available to the world outside this container
EXPOSE 9200
