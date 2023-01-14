FROM adoptopenjdk/openjdk11:alpine-jre as app
ADD target/spring-mvc-simple.war app.war
ENTRYPOINT ["java","-jar","app.war"]
