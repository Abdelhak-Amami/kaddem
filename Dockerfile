FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD /var/lib/jenkins/workspace/kaddem/target/kaddem-abdelhak.jar kaddem.war
ENTRYPOINT ["java","-jar","/kaddem.war"]
