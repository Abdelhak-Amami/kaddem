FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD target/kaddem-abdelhak-SNAPSHOT.war kaddem.war
ENTRYPOINT ["java","-jar","/kaddem.war"]
