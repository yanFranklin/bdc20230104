@echo off
chcp 65001
TITLE {project.name}
java  -Dfile.encoding=utf-8  -Xms512m -Xmx512m -Xmn256m -XX:MetaspaceSize=20M -XX:MaxMetaspaceSize=512m -Xss512K -XX:SurvivorRatio=2 -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70 -XX:+UseParNewGC -XX:CMSFullGCsBeforeCompaction=0 -XX:+UseCMSCompactAtFullCollection -jar app/{project.name}-{project.version}.jar --spring.config.location=cfg/ --logging.path=logs/ --server.tomcat.basedir=tmp/encoding