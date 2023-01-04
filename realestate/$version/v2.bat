@echo off

:: 修改pom文件中大云骨架版本、common包切换兼容包版本
java script/VersionV2
echo =============================== pom版本切换完成 ===============================

:: 依次编译部署common-base包、大云登记端兼容包、common包
:: set list=common-base common-gc-v2 common
:: (for %%s in (%list%) do (
::    echo =============================== start package SubSystem: %%s  ===============================
::    call mvn clean install -Dmaven.test.skip -Dmaven.javadoc.skip=true -f ..\%%s\pom.xml
::    echo =============================== SubSystem package complete: %%s  =============================
:: ))