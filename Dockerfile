FROM gradle:jdk11 as compile

COPY . /home/source/java
WORKDIR /home/source/java

USER root
RUN chown -R gradle /home/source/java

USER gradle
RUN gradle clean build

FROM adoptopenjdk/openjdk11-openj9:x86_64-alpine-jre-11.0.3_7_openj9-0.14.3

USER nobody
WORKDIR /home/application/java
COPY --from=compile "/home/source/java/build/libs/fizzbuzz.jar" .

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/home/application/java/fizzbuzz.jar"]
