@echo off

:: 修改pom文件中大云骨架版本、common包切换兼容包版本
java script/VersionV1
echo ===================================== pom版本切换完成 ==================================

:: 依次编译部署common-base包、大云登记端兼容包、common包
:: set list=common-base common-gc-v1 common
:: (for %%s in (%list%) do (
::    echo =============================== start   install : %%s  =============================
::    call mvn clean install -Dmaven.test.skip -Dmaven.javadoc.skip=true -f ..\%%s\pom.xml
::    echo =============================== install complete: %%s  =============================
:: ))