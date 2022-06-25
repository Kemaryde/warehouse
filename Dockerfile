FROM openjdk:18-jdk-slim AS maven

RUN apt-get update \
  && apt-get install -y curl procps \
  && rm -rf /var/lib/apt/lists/*

ARG USER_HOME_DIR="/root"
ARG SHA=f790857f3b1f90ae8d16281f902c689e4f136ebe584aba45e4b1fa66c80cba826d3e0e52fdd04ed44b4c66f6d3fe3584a057c26dfcac544a60b301e6d0f91c26

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz https://static.devops.jlosch.de/maven/3.8.6/apache-maven-3.8.6-bin.tar.gz \
  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

COPY mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY settings-docker.xml /usr/share/maven/ref/

ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh"]
CMD ["mvn"]
 
WORKDIR /build
COPY . .
#ENV spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mail.jlosch.de:65534/ci
ENV SPRING_DATASOURCE_USERNAME=ci
ENV SPRING_DATASOURCE_PASSWORD=C7LDy2vK344Devonx66So2gLxmhUHTWBSVMCpBUMANrceRijohyGZxFJI2HpqqAo9AM7TlmS

RUN mvn clean package


FROM openjdk:18-slim-buster
EXPOSE 8080:8080
WORKDIR /app
COPY --from=maven /build/target/warehouse-*.jar ./warehouse.jar
ENTRYPOINT ["java","-jar","warehouse.jar"]