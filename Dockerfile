#设置镜像基础，jdk8
FROM openjdk:17
#维护人员信息
MAINTAINER DX
#设置镜像对外暴露端口
EXPOSE 1883
#将当前 target 目录下的 jar 放置在根目录下，命名为 app.jar，推荐使用绝对路径。
ADD target/datacenter-2.0-jar-with-dependencies.jar /app.jar
#执行启动命令
ENTRYPOINT ["java", "-jar","/app.jar"]
#FROM openjdk:17
#VOLUME /tmp
#ADD target/Server-0.0.2-SNAPSHOT.jar /app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
