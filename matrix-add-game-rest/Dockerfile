FROM openjdk:8-alpine

MAINTAINER Adam Szadkowski <adam@szadkowski.info>

COPY target/matrix-add-game-rest-*.jar /app/matrix-add-game.jar

WORKDIR /app

EXPOSE 80

CMD [ "java", "-jar", "/app/matrix-add-game.jar" ]
