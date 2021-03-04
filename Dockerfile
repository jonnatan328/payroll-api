FROM openjdk:11-jdk-alpine
MAINTAINER banistmo@pragma.com.co

ADD ./build/libs/ctg-ms-payroll-api.jar app.jar
EXPOSE 2030

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=America/Bogota","-jar","/app.jar"]