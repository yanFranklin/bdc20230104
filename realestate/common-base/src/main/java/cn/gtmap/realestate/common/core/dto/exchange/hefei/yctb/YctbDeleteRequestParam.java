package cn.gtmap.realestate.common.core.dto.exchange.hefei.yctb;

import java.io.Serializable;

/**
 * 合肥 一窗通办-驳回接口入参
 */
public class YctbDeleteRequestParam implements Serializable {

    private static final long serialVersionUID = 5661860123417043542L;

    private String ywh;
    private String bhr;
    private String bhsj;
    private String bhyy;
    private String taxbhyy;
    private String qxdm;

    public String getTaxbhyy() {
        return taxbhyy;
    }

    public void setTaxbhyy(String taxbhyy) {
        this.taxbhyy = taxbhyy;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getBhr() {
        return bhr;
    }

    public void setBhr(String bhr) {
        this.bhr = bhr;
    }

    public String getBhsj() {
        return bhsj;
    }

    public void setBhsj(String bhsj) {
        this.bhsj = bhsj;
    }

    public String getBhyy() {
        return bhyy;
    }

    public void setBhyy(String bhyy) {
        this.bhyy = bhyy;
    }

    public static final class YctbDeleteRequestParamBuilder {
        private String ywh;
        private String bhr;
        private String bhsj;
        private String bhyy;
        private String taxbhyy;
        private String qxdm;

        private YctbDeleteRequestParamBuilder() {
        }

        public static YctbDeleteRequestParamBuilder anYctbDeleteRequestParam() {
            return new YctbDeleteRequestParamBuilder();
        }

        public YctbDeleteRequestParamBuilder withYwh(String ywh) {
            this.ywh = ywh;
            return this;
        }

        public YctbDeleteRequestParamBuilder withBhr(String bhr) {
            this.bhr = bhr;
            return this;
        }

        public YctbDeleteRequestParamBuilder withBhsj(String bhsj) {
            this.bhsj = bhsj;
            return this;
        }

        public YctbDeleteRequestParamBuilder withBhyy(String bhyy) {
            this.bhyy = bhyy;
            return this;
        }

        public YctbDeleteRequestParamBuilder withTaxbhyy(String taxbhyy) {
            this.taxbhyy = taxbhyy;
            return this;
        }

        public YctbDeleteRequestParamBuilder withQxdm(String qxdm) {
            this.qxdm = qxdm;
            return this;
        }

        public YctbDeleteRequestParam build() {
            YctbDeleteRequestParam yctbDeleteRequestParam = new YctbDeleteRequestParam();
            yctbDeleteRequestParam.setYwh(ywh);
            yctbDeleteRequestParam.setBhr(bhr);
            yctbDeleteRequestParam.setBhsj(bhsj);
            yctbDeleteRequestParam.setBhyy(bhyy);
            yctbDeleteRequestParam.setTaxbhyy(taxbhyy);
            yctbDeleteRequestParam.setQxdm(qxdm);
            return yctbDeleteRequestParam;
        }
    }
}
