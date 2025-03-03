# Sử dụng Jenkins LTS làm base image
FROM jenkins/jenkins:lts-jdk21

# Chuyển sang user root để cài đặt các packages
USER root

# Cài đặt các dependencies cần thiết
RUN apt-get update && apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg \
    lsb-release \
    software-properties-common \
    unzip \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Cài đặt Docker
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg && \
    echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian \
    $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null && \
    apt-get update && \
    apt-get install -y docker-ce docker-ce-cli containerd.io && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Cài đặt Docker Compose
RUN curl -L "https://github.com/docker/compose/releases/download/v2.24.1/docker-compose-linux-x86_64" -o /usr/local/bin/docker-compose \
    && chmod +x /usr/local/bin/docker-compose

# Cài đặt Gradle
RUN curl -L "https://services.gradle.org/distributions/gradle-8.12-bin.zip" -o gradle.zip \
    && unzip gradle.zip \
    && mv gradle-8.12 /opt/gradle \
    && rm gradle.zip
ENV PATH=$PATH:/opt/gradle/bin

# Thêm jenkins vào nhóm docker để có thể sử dụng Docker
RUN groupadd docker || true && \
    usermod -aG docker jenkins

# Tạo và cấu hình script khởi động
RUN echo '#!/bin/bash\n\
# Pull required Docker images\n\
docker pull eclipse-temurin:21-jdk\n\
docker pull eclipse-temurin:21-jre\n\
docker pull redis/redis-stack:latest\n\
docker pull postgres:17.0-bookworm\n\
docker pull minio/minio:latest\n\
\n\
# Start Jenkins\n\
exec /usr/local/bin/jenkins.sh' > /usr/local/bin/init.sh && \
    chmod +x /usr/local/bin/init.sh

# Chuyển lại sang user jenkins
USER jenkins

# Cài đặt các plugins Jenkins cần thiết
RUN jenkins-plugin-cli --plugins \
    docker-workflow \
    docker-plugin \
    gradle \
    git \
    workflow-aggregator \
    pipeline-stage-view \
    blueocean \
    locale \
    configuration-as-code

# Sử dụng script khởi động
ENTRYPOINT ["/usr/local/bin/init.sh"]