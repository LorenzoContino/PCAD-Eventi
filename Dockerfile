FROM openjdk:20
COPY ./target/terzolabo-0.1-jar-with-dependencies.jar /event_server.jar
EXPOSE 6000
CMD [ "java", "-jar", "/event_server.jar" ]
