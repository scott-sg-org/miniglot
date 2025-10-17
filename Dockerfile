FROM tomcat:9.0-jdk-21-temurin-noble

EXPOSE 8080

COPY app/build/libs/miniglot.war /usr/local/tomcat/webapps/
