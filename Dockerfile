FROM openjdk:8-jdk-alpine
EXPOSE 8089
ADD target/kaddem-abdelhak.war kaddem.war
ENTRYPOINT ["java","-jar","/kaddem.war"]
#
