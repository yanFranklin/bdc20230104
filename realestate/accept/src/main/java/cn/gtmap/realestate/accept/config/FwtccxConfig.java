package cn.gtmap.realestate.accept.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/19
 * @description 房屋套次查询配置项
 */
@Component
@ConfigurationProperties(prefix = "fwtccx")
public class FwtccxConfig {

    /**
     * 规划用途
     */
    private String ghyt;

    /**
     * 权籍管理代码配置关系
     */
    private Map<String, String> qjgldm=new HashMap<>();

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public Map<String, String> getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(Map<String, String> qjgldm) {
        this.qjgldm = qjgldm;
    }
}


