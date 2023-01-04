package cn.gtmap.realestate.exchange.core.dto.yctb;

import cn.gtmap.realestate.common.util.ParamUtil;

import java.io.Serializable;

public class YctbBjxnjgRequest implements Serializable {

    private static final long serialVersionUID = 3950027936639283503L;

    private String ywh;//string Y 业务号
    private String qxdm; //string Y 区县代码
    private String djlx; //string Y 字典见 登记类型
    private String lcmc; //string Y 登记类型名称
    private String dqhj; //string Y 枚举备注: 4：受理；11：审核；12：登簿；13：缮证；14：发证；15：办结
    private String begindate; //string N 受理时间，格式YYYY-MM-DD HH24:MI:SS
    private String enddate; //string N 办结时间，格式YYYY-MM-DD HH24:MI:SS

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public String getDqhj() {
        return dqhj;
    }

    public void setDqhj(String dqhj) {
        this.dqhj = dqhj;
    }

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public void checkParam(){
        ParamUtil.nonNull(this.ywh, "业务号不能为空");
        ParamUtil.nonNull(this.qxdm,"区县代码不能为空");
    }

    public static final class YctbBjxnjgRequestBuilder {
        private String ywh;//string Y 业务号
        private String qxdm; //string Y 区县代码
        private String djlx; //string Y 字典见 登记类型
        private String lcmc; //string Y 登记类型名称
        private String dqhj; //string Y 枚举备注: 4：受理；11：审核；12：登簿；13：缮证；14：发证；15：办结
        private String begindate; //string N 受理时间，格式YYYY-MM-DD HH24:MI:SS
        private String enddate; //string N 办结时间，格式YYYY-MM-DD HH24:MI:SS

        private YctbBjxnjgRequestBuilder() {
        }

        public static YctbBjxnjgRequestBuilder anYctbBjxnjgRequest() {
            return new YctbBjxnjgRequestBuilder();
        }

        public YctbBjxnjgRequestBuilder withYwh(String ywh) {
            this.ywh = ywh;
            return this;
        }

        public YctbBjxnjgRequestBuilder withQxdm(String qxdm) {
            this.qxdm = qxdm;
            return this;
        }

        public YctbBjxnjgRequestBuilder withDjlx(String djlx) {
            this.djlx = djlx;
            return this;
        }

        public YctbBjxnjgRequestBuilder withLcmc(String lcmc) {
            this.lcmc = lcmc;
            return this;
        }

        public YctbBjxnjgRequestBuilder withDqhj(String dqhj) {
            this.dqhj = dqhj;
            return this;
        }

        public YctbBjxnjgRequestBuilder withBegindate(String begindate) {
            this.begindate = begindate;
            return this;
        }

        public YctbBjxnjgRequestBuilder withEnddate(String enddate) {
            this.enddate = enddate;
            return this;
        }

        public YctbBjxnjgRequest build() {
            YctbBjxnjgRequest yctbBjxnjgRequest = new YctbBjxnjgRequest();
            yctbBjxnjgRequest.setYwh(ywh);
            yctbBjxnjgRequest.setQxdm(qxdm);
            yctbBjxnjgRequest.setDjlx(djlx);
            yctbBjxnjgRequest.setLcmc(lcmc);
            yctbBjxnjgRequest.setDqhj(dqhj);
            yctbBjxnjgRequest.setBegindate(begindate);
            yctbBjxnjgRequest.setEnddate(enddate);
            return yctbBjxnjgRequest;
        }
    }
}
