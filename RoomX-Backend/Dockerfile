# 1️⃣ Build stage
FROM eclipse-temurin:21-jdk AS build
LABEL authors="jiag293"

WORKDIR /app

# Cache gradle
COPY build.gradle settings.gradle gradlew ./
COPY gradle /app/gradle

# Tải dependencies để cache
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon

# Copy sources
COPY . .

# Build Backend
RUN chmod +x gradlew && ./gradlew build -x test --no-daemon

## 2️⃣ Production stage
FROM eclipse-temurin:21-jre AS production
WORKDIR app
#
## Copy file JAR từ build stage
COPY --from=app /app/build/libs/*.jar roomx_be.jar
#
## Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "roomx_be.jar"]
