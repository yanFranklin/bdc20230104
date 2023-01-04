package cn.gtmap.realestate.exchange.core.dto.yctb;

import java.io.Serializable;
import java.util.List;

public class YctbWwcjSjdInfo implements Serializable {

    private static final long serialVersionUID = 2547294951989497193L;

    private String ax_owner; //string Y 目录标识（UUID）
    private String sjsl; //string Y 收件数量
    private String clmc; //string Y 材料名称
    private List<YctbWwcjFjInfo> fjxx;

    public List<YctbWwcjFjInfo> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<YctbWwcjFjInfo> fjxx) {
        this.fjxx = fjxx;
    }

    public String getAx_owner() {
        return ax_owner;
    }

    public void setAx_owner(String ax_owner) {
        this.ax_owner = ax_owner;
    }

    public String getSjsl() {
        return sjsl;
    }

    public void setSjsl(String sjsl) {
        this.sjsl = sjsl;
    }

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

    public static final class YctbWwcjSjdInfoBuilder {
        private String ax_owner; //string Y 目录标识（UUID）
        private String sjsl; //string Y 收件数量
        private String clmc; //string Y 材料名称
        private List<YctbWwcjFjInfo> fjxx;

        private YctbWwcjSjdInfoBuilder() {
        }

        public static YctbWwcjSjdInfoBuilder anYctbWwcjSjdInfo() {
            return new YctbWwcjSjdInfoBuilder();
        }

        public YctbWwcjSjdInfoBuilder withAx_owner(String ax_owner) {
            this.ax_owner = ax_owner;
            return this;
        }

        public YctbWwcjSjdInfoBuilder withSjsl(String sjsl) {
            this.sjsl = sjsl;
            return this;
        }

        public YctbWwcjSjdInfoBuilder withClmc(String clmc) {
            this.clmc = clmc;
            return this;
        }

        public YctbWwcjSjdInfoBuilder withFjxx(List<YctbWwcjFjInfo> fjxx) {
            this.fjxx = fjxx;
            return this;
        }

        public YctbWwcjSjdInfo build() {
            YctbWwcjSjdInfo yctbWwcjSjdInfo = new YctbWwcjSjdInfo();
            yctbWwcjSjdInfo.setAx_owner(ax_owner);
            yctbWwcjSjdInfo.setSjsl(sjsl);
            yctbWwcjSjdInfo.setClmc(clmc);
            yctbWwcjSjdInfo.setFjxx(fjxx);
            return yctbWwcjSjdInfo;
        }
    }
}
