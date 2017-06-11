FROM openjdk:8
VOLUME /tmp
ADD *.jar /app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8010
CMD ["java","-Dspring.profiles.active=docker,distributed","-jar","/app.jar"]
