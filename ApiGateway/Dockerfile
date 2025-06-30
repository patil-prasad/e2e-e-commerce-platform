# Use an official OpenJDK runtime as a parent image for Java 21
FROM eclipse-temurin:21-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper and all necessary files
COPY gradlew .
COPY gradle gradle
COPY app/build.gradle .
COPY settings.gradle .
COPY app/src src

# Ensure the gradlew script is executable
RUN chmod +x gradlew

# Download dependencies
RUN ./gradlew build -x test

# Expose port 8080 to the outside world
EXPOSE 3000

# Run the application
CMD ["./gradlew", "bootRun"]