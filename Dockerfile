
# Use this image as a build-base
FROM openjdk:8-jdk-alpine

# The application's jar file
ARG JAR_FILE=target/tmp-service.jar

# Add the application's jar to the container
ADD ${JAR_FILE} tmp-app.jar

#RUN sh -c 'touch tmp-app.jar'

# Run the jar file 
ENTRYPOINT ["java","-jar","tmp-app.jar"]

# Make port 9200 available to the world outside this container
EXPOSE 9200
