package cn.gtmap.realestate.exchange.core.dto.wwsq.gzwsyqcx.request;

import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/6/28
 * @description 3构（建）筑物信息查询（不动产单元信息）QO
 */
@IgnoreCast(ignoreNum = 6)
public class GzwsyqcxQO {


    /**
     * qlrmc :
     * bdcdyh :
     * zlmh :
     */

    private String qlrmc;
    private String bdcdyh;
    private String zlmh;
    /**
     * cqzh :
     * qszt :
     * qlrzjh :
     * cqzhjc :
     * zl :
     */

    private String cqzh;
    private String qszt;
    private String qlrzjh;
    private String cqzhjc;
    private String zl;


    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZlmh() {
        return zlmh;
    }

    public void setZlmh(String zlmh) {
        this.zlmh = zlmh;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getCqzhjc() {
        return cqzhjc;
    }

    public void setCqzhjc(String cqzhjc) {
        this.cqzhjc = cqzhjc;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }


    public static final class GzwxxcxQOBuilder {

        private String qlrmc;
        private String bdcdyh;
        private String zlmh;

        private GzwxxcxQOBuilder() {
        }

        public static GzwxxcxQOBuilder aGzwxxcxQO() {
            return new GzwxxcxQOBuilder();
        }

        public GzwxxcxQOBuilder withQlrmc(String qlrmc) {
            this.qlrmc = qlrmc;
            return this;
        }

        public GzwxxcxQOBuilder withBdcdyh(String bdcdyh) {
            this.bdcdyh = bdcdyh;
            return this;
        }

        public GzwxxcxQOBuilder withZlmh(String zlmh) {
            this.zlmh = zlmh;
            return this;
        }

        public GzwsyqcxQO build() {
            GzwsyqcxQO gzwsyqcxQO = new GzwsyqcxQO();
            gzwsyqcxQO.setQlrmc(qlrmc);
            gzwsyqcxQO.setBdcdyh(bdcdyh);
            gzwsyqcxQO.setZlmh(zlmh);
            return gzwsyqcxQO;
        }
    }

    @Override
    public String toString() {
        return "GzwxxcxQO{" +
                "qlrmc='" + qlrmc + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zlmh='" + zlmh + '\'' +
                ", cqzh='" + cqzh + '\'' +
                ", qszt='" + qszt + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", cqzhjc='" + cqzhjc + '\'' +
                ", zl='" + zl + '\'' +
                '}';
    }
}
