FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD target/kaddem-2.0-SNAPSHOT.war kaddem.war
ENTRYPOINT ["java","-jar","/kaddem.war"]
