FROM amazoncorretto:11-alpine-jdk
MAINTAINER cfsm
COPY build/libs/roulette-0.0.1-SNAPSHOT.jar roulette-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/roulette-0.0.1-SNAPSHOT.jar"]