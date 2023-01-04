FROM registry.cn-hangzhou.aliyuncs.com/lanxy88/gtmap-java:v1
VOLUME /tmp
ADD app/{project.name}-{project.version}.jar app.jar
EXPOSE 8000
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]