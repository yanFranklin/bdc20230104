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
@ConfigurationProperties(prefix = "dajj.Jtdahprefix")
public class DajjDahPrefixJtConfig {

    @Value("#{'${jtqzlql:}'.split(',')}")
    private List<String> qzlql;

    @Value("#{'${jtzmlql:}'.split(',')}")
    private List<String> jtzmlql;

    private String qzh;

    private String qzlprefix;

    private String zmlprefix;

    private String cqzxprefix;

    private String dyqzxprefix;

    private String qtzxprefix;

    public List<String> getQzlql() {
        return qzlql;
    }

    public List<String> getJtzmlql() {
        return jtzmlql;
    }

    public void setJtzmlql(List<String> jtzmlql) {
        this.jtzmlql = jtzmlql;
    }

    public void setQzlql(List<String> qzlql) {
        this.qzlql = qzlql;
    }



    public String getQzh() {
        return qzh;
    }

    public void setQzh(String qzh) {
        this.qzh = qzh;
    }

    public String getQzlprefix() {
        return qzlprefix;
    }

    public void setQzlprefix(String qzlprefix) {
        this.qzlprefix = qzlprefix;
    }

    public String getZmlprefix() {
        return zmlprefix;
    }

    public void setZmlprefix(String zmlprefix) {
        this.zmlprefix = zmlprefix;
    }

    public String getCqzxprefix() {
        return cqzxprefix;
    }

    public void setCqzxprefix(String cqzxprefix) {
        this.cqzxprefix = cqzxprefix;
    }

    public String getDyqzxprefix() {
        return dyqzxprefix;
    }

    public void setDyqzxprefix(String dyqzxprefix) {
        this.dyqzxprefix = dyqzxprefix;
    }

    public String getQtzxprefix() {
        return qtzxprefix;
    }

    public void setQtzxprefix(String qtzxprefix) {
        this.qtzxprefix = qtzxprefix;
    }
}
