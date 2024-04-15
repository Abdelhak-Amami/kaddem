FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD target/kaddem-SNAPSHOT.war kaddem-SNAPSHOT.war
ENTRYPOINT ["java","-jar","/kaddem.war"]
