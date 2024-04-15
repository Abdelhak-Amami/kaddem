FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD target/SNAPSHOT.war kaddem-SNAPSHOT.war
ENTRYPOINT ["java","-jar","/kaddem.war"]
