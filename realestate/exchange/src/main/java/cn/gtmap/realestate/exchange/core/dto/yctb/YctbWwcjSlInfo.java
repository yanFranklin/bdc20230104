package cn.gtmap.realestate.exchange.core.dto.yctb;

import cn.gtmap.realestate.common.util.ParamUtil;

import java.io.Serializable;

public class YctbWwcjSlInfo implements Serializable {

    private static final long serialVersionUID = -1162018525386302082L;
    //TODO
    private Integer sqzsbs; //申请证书版式 （默认为0）0:单一版 1:集成版
    private Integer sqfbcz; //申请分别持证 0:分别持证 1:集中持证
    private String bz; //备注
    private String djyy; //登记原因
    private String qllx; //字典见 权利类型
    private String qdjg; //取得价格
    //TODO
    private String cqly; //字典见 产权来源
    //jyxx?
    private String jyrq; //交易日期
    private String bdbzqse; //被担保主债权数额
    private String zwlxqxs; //债务履行期限始
    private String zwlxqxz; //债务履行期限止
    private String dyfs; //字典见 抵押方式
    private String dywmj; //抵押物面积（房屋）
    private String dywmj1; //抵押物面积（宗地）

    public void checkParam(){
        ParamUtil.nonNull(this.qllx, "权利类型不能为空");
        ParamUtil.nonNull(this.djyy,"登记原因不能为空");
    }

    public Integer getSqzsbs() {
        return sqzsbs;
    }

    public void setSqzsbs(Integer sqzsbs) {
        this.sqzsbs = sqzsbs;
    }

    public Integer getSqfbcz() {
        return sqfbcz;
    }

    public void setSqfbcz(Integer sqfbcz) {
        this.sqfbcz = sqfbcz;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getQdjg() {
        return qdjg;
    }

    public void setQdjg(String qdjg) {
        this.qdjg = qdjg;
    }

    public String getCqly() {
        return cqly;
    }

    public void setCqly(String cqly) {
        this.cqly = cqly;
    }

    public String getJyrq() {
        return jyrq;
    }

    public void setJyrq(String jyrq) {
        this.jyrq = jyrq;
    }

    public String getBdbzqse() {
        return bdbzqse;
    }

    public void setBdbzqse(String bdbzqse) {
        this.bdbzqse = bdbzqse;
    }

    public String getZwlxqxs() {
        return zwlxqxs;
    }

    public void setZwlxqxs(String zwlxqxs) {
        this.zwlxqxs = zwlxqxs;
    }

    public String getZwlxqxz() {
        return zwlxqxz;
    }

    public void setZwlxqxz(String zwlxqxz) {
        this.zwlxqxz = zwlxqxz;
    }

    public String getDyfs() {
        return dyfs;
    }

    public void setDyfs(String dyfs) {
        this.dyfs = dyfs;
    }

    public String getDywmj() {
        return dywmj;
    }

    public void setDywmj(String dywmj) {
        this.dywmj = dywmj;
    }

    public String getDywmj1() {
        return dywmj1;
    }

    public void setDywmj1(String dywmj1) {
        this.dywmj1 = dywmj1;
    }

    public static final class YctbWwcjSlInfoBuilder {
        private Integer sqzsbs; //申请证书版式 （默认为0）0:单一版 1:集成版
        private Integer sqfbcz; //申请分别持证 0:分别持证 1:集中持证
        private String bz; //备注
        private String djyy; //登记原因
        private String qllx; //字典见 权利类型
        private String qdjg; //取得价格
        private String cqly; //字典见 产权来源
        private String jyrq; //交易日期
        private String bdbzqse; //被担保主债权数额
        private String zwlxqxs; //债务履行期限始
        private String zwlxqxz; //债务履行期限止
        private String dyfs; //字典见 抵押方式
        private String dywmj; //抵押物面积（房屋）
        private String dywmj1; //抵押物面积（宗地）

        private YctbWwcjSlInfoBuilder() {
        }

        public static YctbWwcjSlInfoBuilder anYctbWwcjSlInfo() {
            return new YctbWwcjSlInfoBuilder();
        }

        public YctbWwcjSlInfoBuilder withSqzsbs(Integer sqzsbs) {
            this.sqzsbs = sqzsbs;
            return this;
        }

        public YctbWwcjSlInfoBuilder withSqfbcz(Integer sqfbcz) {
            this.sqfbcz = sqfbcz;
            return this;
        }

        public YctbWwcjSlInfoBuilder withBz(String bz) {
            this.bz = bz;
            return this;
        }

        public YctbWwcjSlInfoBuilder withDjyy(String djyy) {
            this.djyy = djyy;
            return this;
        }

        public YctbWwcjSlInfoBuilder withQllx(String qllx) {
            this.qllx = qllx;
            return this;
        }

        public YctbWwcjSlInfoBuilder withQdjg(String qdjg) {
            this.qdjg = qdjg;
            return this;
        }

        public YctbWwcjSlInfoBuilder withCqly(String cqly) {
            this.cqly = cqly;
            return this;
        }

        public YctbWwcjSlInfoBuilder withJyrq(String jyrq) {
            this.jyrq = jyrq;
            return this;
        }

        public YctbWwcjSlInfoBuilder withBdbzqse(String bdbzqse) {
            this.bdbzqse = bdbzqse;
            return this;
        }

        public YctbWwcjSlInfoBuilder withZwlxqxs(String zwlxqxs) {
            this.zwlxqxs = zwlxqxs;
            return this;
        }

        public YctbWwcjSlInfoBuilder withZwlxqxz(String zwlxqxz) {
            this.zwlxqxz = zwlxqxz;
            return this;
        }

        public YctbWwcjSlInfoBuilder withDyfs(String dyfs) {
            this.dyfs = dyfs;
            return this;
        }

        public YctbWwcjSlInfoBuilder withDywmj(String dywmj) {
            this.dywmj = dywmj;
            return this;
        }

        public YctbWwcjSlInfoBuilder withDywmj1(String dywmj1) {
            this.dywmj1 = dywmj1;
            return this;
        }

        public YctbWwcjSlInfo build() {
            YctbWwcjSlInfo yctbWwcjSlInfo = new YctbWwcjSlInfo();
            yctbWwcjSlInfo.setSqzsbs(sqzsbs);
            yctbWwcjSlInfo.setSqfbcz(sqfbcz);
            yctbWwcjSlInfo.setBz(bz);
            yctbWwcjSlInfo.setDjyy(djyy);
            yctbWwcjSlInfo.setQllx(qllx);
            yctbWwcjSlInfo.setQdjg(qdjg);
            yctbWwcjSlInfo.setCqly(cqly);
            yctbWwcjSlInfo.setJyrq(jyrq);
            yctbWwcjSlInfo.setBdbzqse(bdbzqse);
            yctbWwcjSlInfo.setZwlxqxs(zwlxqxs);
            yctbWwcjSlInfo.setZwlxqxz(zwlxqxz);
            yctbWwcjSlInfo.setDyfs(dyfs);
            yctbWwcjSlInfo.setDywmj(dywmj);
            yctbWwcjSlInfo.setDywmj1(dywmj1);
            return yctbWwcjSlInfo;
        }
    }
}
