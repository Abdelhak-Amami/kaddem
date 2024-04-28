FROM openjdk:8-jdk-alpine
EXPOSE 8089
ADD target/kaddem-louay.war kaddem.war
ENTRYPOINT ["java","-jar","/kaddem.war"]
#
