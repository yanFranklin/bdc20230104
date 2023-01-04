@echo off
set repo=registry.cn-hangzhou.aliyuncs.com/lanxy88
@echo Commit Docker Image ({project.name}:{project.version}) to Nexus Repository (%repo%)
docker login %repo%
docker tag {project.name}:{project.version} %repo%/{project.name}:{project.version}
docker push %repo%/{project.name}:{project.version}
@pause