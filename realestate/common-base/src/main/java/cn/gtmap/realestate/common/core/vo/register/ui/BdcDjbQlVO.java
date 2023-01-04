package cn.gtmap.realestate.common.core.vo.register.ui;

import java.util.Map;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen/a>"
 * @version 1.0, 2019/1/17
 * @description 转换登记簿不动产单元展现权利对象
 */
public class BdcDjbQlVO {
    /**
     * 查封
     */
    Map<String, String> djbQlCf;
    /**
     * 抵押权
     */
    Map<String, String> djbQlDya;
    /**
     * 抵押权
     */
    Map<String, String> djbQlDyi;
    /**
     * 异议
     */
    Map<String, String> djbQlYy;
    /**
     * 预告
     */
    Map<String, String> djbQlYg;
    /**
     * 产权
     */
    Map<String, String> djbQlCq;


    String djbQlCfMc;
    String djbQlCfUrl;

    String djbQlDyaMc;
    String djbQlDyaUrl;

    String djbQlDyiMc;
    String djbQlDyiUrl;
    String djbQlYyMc;
    String djbQlYyUrl;
    String djbQlYgMc;
    String djbQlYgUrl;
    String djbQlCqMc;
    String djbQlCqUrl;

    public Map<String, String> getDjbQlCf() {
        return djbQlCf;
    }

    public void setDjbQlCf(Map<String, String> djbQlCf) {
        this.djbQlCf = djbQlCf;
    }
    public String getDjbQlCfMc() {
        return djbQlCfMc;
    }

    public void setDjbQlCfMc(String djbQlCfMc) {
        this.djbQlCfMc = djbQlCfMc;
    }

    public String getDjbQlDyaMc() {
        return djbQlDyaMc;
    }

    public void setDjbQlDyaMc(String djbQlDyaMc) {
        this.djbQlDyaMc = djbQlDyaMc;
    }

    public String getDjbQlDyiMc() {
        return djbQlDyiMc;
    }

    public void setDjbQlDyiMc(String djbQlDyiMc) {
        this.djbQlDyiMc = djbQlDyiMc;
    }

    public String getDjbQlYyMc() {
        return djbQlYyMc;
    }

    public void setDjbQlYyMc(String djbQlYyMc) {
        this.djbQlYyMc = djbQlYyMc;
    }

    public String getDjbQlYgMc() {
        return djbQlYgMc;
    }

    public void setDjbQlYgMc(String djbQlYgMc) {
        this.djbQlYgMc = djbQlYgMc;
    }

    public String getDjbQlCqMc() {
        return djbQlCqMc;
    }

    public void setDjbQlCqMc(String djbQlCqMc) {
        this.djbQlCqMc = djbQlCqMc;
    }

    public String getDjbQlCqUrl() {
        return djbQlCqUrl;
    }

    public void setDjbQlCqUrl(String djbQlCqUrl) {
        this.djbQlCqUrl = djbQlCqUrl;
    }

    public String getDjbQlCfUrl() {
        return djbQlCfUrl;
    }

    public void setDjbQlCfUrl(String djbQlCfUrl) {
        this.djbQlCfUrl = djbQlCfUrl;
    }

    public String getDjbQlDyaUrl() {
        return djbQlDyaUrl;
    }

    public void setDjbQlDyaUrl(String djbQlDyaUrl) {
        this.djbQlDyaUrl = djbQlDyaUrl;
    }

    public String getDjbQlDyiUrl() {
        return djbQlDyiUrl;
    }

    public void setDjbQlDyiUrl(String djbQlDyiUrl) {
        this.djbQlDyiUrl = djbQlDyiUrl;
    }

    public String getDjbQlYyUrl() {
        return djbQlYyUrl;
    }

    public void setDjbQlYyUrl(String djbQlYyUrl) {
        this.djbQlYyUrl = djbQlYyUrl;
    }

    public String getDjbQlYgUrl() {
        return djbQlYgUrl;
    }

    public void setDjbQlYgUrl(String djbQlYgUrl) {
        this.djbQlYgUrl = djbQlYgUrl;
    }


    @Override
    public String toString() {
        return "BdcDjbQlVO{" +
                "djbQlCfMc='" + djbQlCfMc + '\'' +
                ", djbQlCfUrl='" + djbQlCfUrl + '\'' +
                ", djbQlDyaMc='" + djbQlDyaMc + '\'' +
                ", djbQlDyaUrl='" + djbQlDyaUrl + '\'' +
                ", djbQlDyiMc='" + djbQlDyiMc + '\'' +
                ", djbQlDyiUrl='" + djbQlDyiUrl + '\'' +
                ", djbQlYyMc='" + djbQlYyMc + '\'' +
                ", djbQlYyUrl='" + djbQlYyUrl + '\'' +
                ", djbQlYgMc='" + djbQlYgMc + '\'' +
                ", djbQlYgUrl='" + djbQlYgUrl + '\'' +
                ", djbQlCqMc='" + djbQlCqMc + '\'' +
                ", djbQlCqUrl='" + djbQlCqUrl + '\'' +
                '}';
    }
}
