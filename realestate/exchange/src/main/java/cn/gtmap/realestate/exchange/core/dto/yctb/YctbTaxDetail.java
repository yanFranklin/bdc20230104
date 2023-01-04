package cn.gtmap.realestate.exchange.core.dto.yctb;

import java.io.Serializable;
import java.util.List;

public class YctbTaxDetail implements Serializable {

    private static final long serialVersionUID = 6863631925037553543L;

    private String ywh; //string Y 业务号
    private String jyuuid; //string Y jyuuid就是fwuuid
    private List<YctbJyskb> jyskbList;

    public List<YctbJyskb> getJyskbList() {
        return jyskbList;
    }

    public void setJyskbList(List<YctbJyskb> jyskbList) {
        this.jyskbList = jyskbList;
    }

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getJyuuid() {
        return jyuuid;
    }

    public void setJyuuid(String jyuuid) {
        this.jyuuid = jyuuid;
    }


    public static final class YctbTaxDetailBuilder {
        private String ywh; //string Y 业务号
        private String jyuuid; //string Y jyuuid就是fwuuid
        private List<YctbJyskb> jyskbList;

        private YctbTaxDetailBuilder() {
        }

        public static YctbTaxDetailBuilder anYctbTaxDetail() {
            return new YctbTaxDetailBuilder();
        }

        public YctbTaxDetailBuilder withYwh(String ywh) {
            this.ywh = ywh;
            return this;
        }

        public YctbTaxDetailBuilder withJyuuid(String jyuuid) {
            this.jyuuid = jyuuid;
            return this;
        }

        public YctbTaxDetailBuilder withJyskbList(List<YctbJyskb> jyskbList) {
            this.jyskbList = jyskbList;
            return this;
        }

        public YctbTaxDetail build() {
            YctbTaxDetail yctbTaxDetail = new YctbTaxDetail();
            yctbTaxDetail.setYwh(ywh);
            yctbTaxDetail.setJyuuid(jyuuid);
            yctbTaxDetail.setJyskbList(jyskbList);
            return yctbTaxDetail;
        }
    }
}
