package cn.gtmap.realestate.exchange.core.dto.yctb;

import java.io.Serializable;

public class YctbWwcjJmsbjtcyInfo implements Serializable {

    private static final long serialVersionUID = 6479548066280393699L;

    private String guid; //string Y 主键
    private String ywh; //string Y 业务号
    private String oid; //string Y 序号
    private String name; //string Y 姓名 (买受人和出卖人共用 )
    private String idcard; //string Y 身份证号 (买受人和出卖人共用 )
    private String ismwwy; //string Y 是否满五唯一 (0 是 1否 )
    private String ispo; //string Y 是否有配偶 (0 是 1否 )
    private String poname; //string N 配偶姓名（有配偶，必填）
    private String poidcard; //string N 配偶身份证号（有配偶，必填）
    private String bs; //string Y 区分买卖方标示 (0是卖方 1是买方 )
    private String zftslx; //string Y 住房套数类型 (0 首套 1二套 3三套及以上 )
    private String ischild; //string Y 是否有子女 (0有 1否 )
    private String jmssbdsfws; //string Y 是否完税 (0未完税 1完税)
    private String sfwszf; //string Y 是否完税作废 (0未作废 1已作废)

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getIsmwwy() {
        return ismwwy;
    }

    public void setIsmwwy(String ismwwy) {
        this.ismwwy = ismwwy;
    }

    public String getIspo() {
        return ispo;
    }

    public void setIspo(String ispo) {
        this.ispo = ispo;
    }

    public String getPoname() {
        return poname;
    }

    public void setPoname(String poname) {
        this.poname = poname;
    }

    public String getPoidcard() {
        return poidcard;
    }

    public void setPoidcard(String poidcard) {
        this.poidcard = poidcard;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }

    public String getZftslx() {
        return zftslx;
    }

    public void setZftslx(String zftslx) {
        this.zftslx = zftslx;
    }

    public String getIschild() {
        return ischild;
    }

    public void setIschild(String ischild) {
        this.ischild = ischild;
    }

    public String getJmssbdsfws() {
        return jmssbdsfws;
    }

    public void setJmssbdsfws(String jmssbdsfws) {
        this.jmssbdsfws = jmssbdsfws;
    }

    public String getSfwszf() {
        return sfwszf;
    }

    public void setSfwszf(String sfwszf) {
        this.sfwszf = sfwszf;
    }

    public static final class YctbWwcjJmsbjtcyInfoBuilder {
        private String guid; //string Y 主键
        private String ywh; //string Y 业务号
        private String oid; //string Y 序号
        private String name; //string Y 姓名 (买受人和出卖人共用 )
        private String idcard; //string Y 身份证号 (买受人和出卖人共用 )
        private String ismwwy; //string Y 是否满五唯一 (0 是 1否 )
        private String ispo; //string Y 是否有配偶 (0 是 1否 )
        private String poname; //string N 配偶姓名（有配偶，必填）
        private String poidcard; //string N 配偶身份证号（有配偶，必填）
        private String bs; //string Y 区分买卖方标示 (0是卖方 1是买方 )
        private String zftslx; //string Y 住房套数类型 (0 首套 1二套 3三套及以上 )
        private String ischild; //string Y 是否有子女 (0有 1否 )
        private String jmssbdsfws; //string Y 是否完税 (0未完税 1完税)
        private String sfwszf; //string Y 是否完税作废 (0未作废 1已作废)

        private YctbWwcjJmsbjtcyInfoBuilder() {
        }

        public static YctbWwcjJmsbjtcyInfoBuilder anYctbWwcjJmsbjtcyInfo() {
            return new YctbWwcjJmsbjtcyInfoBuilder();
        }

        public YctbWwcjJmsbjtcyInfoBuilder withGuid(String guid) {
            this.guid = guid;
            return this;
        }

        public YctbWwcjJmsbjtcyInfoBuilder withYwh(String ywh) {
            this.ywh = ywh;
            return this;
        }

        public YctbWwcjJmsbjtcyInfoBuilder withOid(String oid) {
            this.oid = oid;
            return this;
        }

        public YctbWwcjJmsbjtcyInfoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public YctbWwcjJmsbjtcyInfoBuilder withIdcard(String idcard) {
            this.idcard = idcard;
            return this;
        }

        public YctbWwcjJmsbjtcyInfoBuilder withIsmwwy(String ismwwy) {
            this.ismwwy = ismwwy;
            return this;
        }

        public YctbWwcjJmsbjtcyInfoBuilder withIspo(String ispo) {
            this.ispo = ispo;
            return this;
        }

        public YctbWwcjJmsbjtcyInfoBuilder withPoname(String poname) {
            this.poname = poname;
            return this;
        }

        public YctbWwcjJmsbjtcyInfoBuilder withPoidcard(String poidcard) {
            this.poidcard = poidcard;
            return this;
        }

        public YctbWwcjJmsbjtcyInfoBuilder withBs(String bs) {
            this.bs = bs;
            return this;
        }

        public YctbWwcjJmsbjtcyInfoBuilder withZftslx(String zftslx) {
            this.zftslx = zftslx;
            return this;
        }

        public YctbWwcjJmsbjtcyInfoBuilder withIschild(String ischild) {
            this.ischild = ischild;
            return this;
        }

        public YctbWwcjJmsbjtcyInfoBuilder withJmssbdsfws(String jmssbdsfws) {
            this.jmssbdsfws = jmssbdsfws;
            return this;
        }

        public YctbWwcjJmsbjtcyInfoBuilder withSfwszf(String sfwszf) {
            this.sfwszf = sfwszf;
            return this;
        }

        public YctbWwcjJmsbjtcyInfo build() {
            YctbWwcjJmsbjtcyInfo yctbWwcjJmsbjtcyInfo = new YctbWwcjJmsbjtcyInfo();
            yctbWwcjJmsbjtcyInfo.setGuid(guid);
            yctbWwcjJmsbjtcyInfo.setYwh(ywh);
            yctbWwcjJmsbjtcyInfo.setOid(oid);
            yctbWwcjJmsbjtcyInfo.setName(name);
            yctbWwcjJmsbjtcyInfo.setIdcard(idcard);
            yctbWwcjJmsbjtcyInfo.setIsmwwy(ismwwy);
            yctbWwcjJmsbjtcyInfo.setIspo(ispo);
            yctbWwcjJmsbjtcyInfo.setPoname(poname);
            yctbWwcjJmsbjtcyInfo.setPoidcard(poidcard);
            yctbWwcjJmsbjtcyInfo.setBs(bs);
            yctbWwcjJmsbjtcyInfo.setZftslx(zftslx);
            yctbWwcjJmsbjtcyInfo.setIschild(ischild);
            yctbWwcjJmsbjtcyInfo.setJmssbdsfws(jmssbdsfws);
            yctbWwcjJmsbjtcyInfo.setSfwszf(sfwszf);
            return yctbWwcjJmsbjtcyInfo;
        }
    }
}
