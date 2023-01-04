package cn.gtmap.realestate.common.property.registerui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
 * @create: 2022-02-08
 * @description: 【常州】金坛档案号生成规则配置
 **/
@Component
@ConfigurationProperties(prefix = "dajj.lyConfig")
public class DajjLyConfig {
    private String lyqh;
    @Value("#{'${lycqlql:}'.split(',')}")
    private List<String> lycqlql;

    @Value("#{'${lyzmlql:}'.split(',')}")
    private List<String> lyzmlql;
    /**
    * 查封登记小类
    **/
    @Value("#{'${lyCfDjxl:}'.split(',')}")
    private List<String> lyCfDjxl;
    /**
     * 全宗号
     */
    private String qzh;
    /**
     * 分类号
     */
    private String flh;
    /**
     * 证明类目录号
     */
    private String zmlqlmlh;
    /**
     * 产权类目录号
     */
    private String cqlqlmlh;

    /**
     * 查封目录号
     */
    private String cfmlh;

    public String getLyqh() {
        return lyqh;
    }

    public void setLyqh(String lyqh) {
        this.lyqh = lyqh;
    }

    public List<String> getLycqlql() {
        return lycqlql;
    }

    public void setLycqlql(List<String> lycqlql) {
        this.lycqlql = lycqlql;
    }

    public List<String> getLyzmlql() {
        return lyzmlql;
    }

    public void setLyzmlql(List<String> lyzmlql) {
        this.lyzmlql = lyzmlql;
    }

    public String getQzh() {
        return qzh;
    }

    public void setQzh(String qzh) {
        this.qzh = qzh;
    }

    public String getFlh() {
        return flh;
    }

    public void setFlh(String flh) {
        this.flh = flh;
    }

    public String getZmlqlmlh() {
        return zmlqlmlh;
    }

    public void setZmlqlmlh(String zmlqlmlh) {
        this.zmlqlmlh = zmlqlmlh;
    }

    public String getCqlqlmlh() {
        return cqlqlmlh;
    }

    public void setCqlqlmlh(String cqlqlmlh) {
        this.cqlqlmlh = cqlqlmlh;
    }

    public List<String> getLyCfDjxl() {
        return lyCfDjxl;
    }

    public void setLyCfDjxl(List<String> lyCfDjxl) {
        this.lyCfDjxl = lyCfDjxl;
    }

    public String getCfmlh() {
        return cfmlh;
    }

    public void setCfmlh(String cfmlh) {
        this.cfmlh = cfmlh;
    }
}
