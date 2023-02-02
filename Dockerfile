FROM openjdk:11.0.15-jdk-slim

LABEL MAINTAINER lgcnser

#1)/deploy 폴더를 생성한다.
RUN mkdir deploy

#2)build/libs 하위의 *.jar파일을 이미지의 /deploy/app.jar로 복사한다.
COPY build/libs/*.jar /deploy/app.jar

#3)["java","-Djava.security.egd=file:/dev/./urandom","-jar","/deploy/app.jar"] 옵션으로 실행되도록 구성한다.
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/deploy/app.jar"]
