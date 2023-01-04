package cn.gtmap.realestate.exchange.core.dto.yctb;

import java.io.Serializable;

public class YctbWwcjDyInfo implements Serializable {

    private static final long serialVersionUID = 9115160344732149854L;

    private String bdcqzh; //string N 不动产权证号
    private String bdcdyh; //string Y 不动产单元号

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public static final class YctbWwcjDyInfoBuilder {
        private String bdcqzh; //string N 不动产权证号
        private String bdcdyh; //string Y 不动产单元号

        private YctbWwcjDyInfoBuilder() {
        }

        public static YctbWwcjDyInfoBuilder anYctbWwcjDyInfo() {
            return new YctbWwcjDyInfoBuilder();
        }

        public YctbWwcjDyInfoBuilder withBdcqzh(String bdcqzh) {
            this.bdcqzh = bdcqzh;
            return this;
        }

        public YctbWwcjDyInfoBuilder withBdcdyh(String bdcdyh) {
            this.bdcdyh = bdcdyh;
            return this;
        }

        public YctbWwcjDyInfo build() {
            YctbWwcjDyInfo yctbWwcjDyInfo = new YctbWwcjDyInfo();
            yctbWwcjDyInfo.setBdcqzh(bdcqzh);
            yctbWwcjDyInfo.setBdcdyh(bdcdyh);
            return yctbWwcjDyInfo;
        }
    }
}
