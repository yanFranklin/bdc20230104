package cn.gtmap.realestate.exchange.core.dto.yctb;

import java.io.Serializable;

public class YctbWwcjGxrInfo implements Serializable {

    private static final long serialVersionUID = -8351870374891794808L;

    private String name; //string Y 姓名
    private Integer zjlx; //number Y 证件类型 字典见 证件种类
    private String zjhm; //string Y 证件号码
    private String lxdh; //string Y 联系电话
    private String type; //string Y 0、法人或负责人；1、代理

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static final class YctbWwcjGxrInfoBuilder {
        private String name; //string Y 姓名
        private Integer zjlx; //number Y 证件类型 字典见 证件种类
        private String zjhm; //string Y 证件号码
        private String lxdh; //string Y 联系电话
        private String type; //string Y 0、法人或负责人；1、代理

        private YctbWwcjGxrInfoBuilder() {
        }

        public static YctbWwcjGxrInfoBuilder anYctbWwcjGxrInfo() {
            return new YctbWwcjGxrInfoBuilder();
        }

        public YctbWwcjGxrInfoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public YctbWwcjGxrInfoBuilder withZjlx(Integer zjlx) {
            this.zjlx = zjlx;
            return this;
        }

        public YctbWwcjGxrInfoBuilder withZjhm(String zjhm) {
            this.zjhm = zjhm;
            return this;
        }

        public YctbWwcjGxrInfoBuilder withLxdh(String lxdh) {
            this.lxdh = lxdh;
            return this;
        }

        public YctbWwcjGxrInfoBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public YctbWwcjGxrInfo build() {
            YctbWwcjGxrInfo yctbWwcjGxrInfo = new YctbWwcjGxrInfo();
            yctbWwcjGxrInfo.setName(name);
            yctbWwcjGxrInfo.setZjlx(zjlx);
            yctbWwcjGxrInfo.setZjhm(zjhm);
            yctbWwcjGxrInfo.setLxdh(lxdh);
            yctbWwcjGxrInfo.setType(type);
            return yctbWwcjGxrInfo;
        }
    }
}
