@echo off
TITLE {project.name}
java -Xms512m -Xmx512m -Xmn194m -XX:MetaspaceSize=20M -XX:MaxMetaspaceSize=512m -jar app/{project.name}-{project.version}.jar --spring.config.location=cfg/ --logging.path=logs/ --server.tomcat.basedir=tmp/