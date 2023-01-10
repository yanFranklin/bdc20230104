package cn.gtmap.realestate.common.config;


import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;

/**
 * 日志系统开关配置
 */
public class LogSwitchConfig {

    private static LogSwitchConfig instance;

    /**
     * ui应用记录请求入参开关
     */
    public boolean springSwitch;
    /**
     * logback日志记录开关
     */
    public boolean logbackSwitch;
    /**
     * 数据库日志记录开关
     */
    public boolean mybatisSwitch;

    public static synchronized LogSwitchConfig getInstance(){
        if(instance == null){
            instance = new LogSwitchConfig();
        }
        return instance;
    }

    public LogSwitchConfig() {
        this.springSwitch = false;
        this.logbackSwitch = false;
        this.mybatisSwitch = false;
    }

    public boolean isSpringSwitch() {
        return springSwitch;
    }

    public void setSpringSwitch(boolean springSwitch) {
        this.springSwitch = springSwitch;
    }

    public boolean isLogbackSwitch() {
        return logbackSwitch;
    }

    public void setLogbackSwitch(boolean logbackSwitch) {
        this.logbackSwitch = logbackSwitch;
    }

    public boolean isMybatisSwitch() {
        return mybatisSwitch;
    }

    public void setMybatisSwitch(boolean mybatisSwitch) {
        this.mybatisSwitch = mybatisSwitch;
    }

}
