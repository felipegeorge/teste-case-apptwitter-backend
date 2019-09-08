##==> [docker image build -t apptwitter-backend . ]
##==> [docker run -it --name apptwitter-backend -p 8081:8081 apptwitter-backend:latest]
##==> [docker exec -it apptwitter-backend sh]
##==> [docker logs -tf apptwitter-backend]
FROM openjdk:8-jdk-alpine
MAINTAINER "felipegeorge13 <felipegeorge13@gmail.com>"

VOLUME /tmp

RUN apk add git

RUN mkdir /home/apptwitter
WORKDIR /home/apptwitter

RUN git clone https://github.com/felipegeorge/teste-case-apptwitter-backend.git
WORKDIR /home/apptwitter/teste-case-apptwitter-backend

CMD ["java", "-Dspring.profiles.active=docker", "-jar", "AppTwitter/target/AppTwitter-1.0.0.jar", "&"]
