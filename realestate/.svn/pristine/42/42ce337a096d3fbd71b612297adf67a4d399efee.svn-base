package cn.gtmap.realestate.common.core.dto.exchange.nantong.zjjy.jyqd;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class ZjClfFjxxDTO {

    /**
     * FJMC :
     * IMGXX : [{"YM":"1","IMG":""},{"YM":"2","IMG":""}]
     */
    @JSONField(name="FJMC")
    private String FJMC;
    @JSONField(name="FJLX")
    private String FJLX;
    @JSONField(name="IMGXX")
    private List<IMGXXBean> IMGXX;

    public String getFJMC() {
        return FJMC;
    }
    @JSONField(name="FJMC")
    public void setFJMC(String FJMC) {
        this.FJMC = FJMC;
    }

    public List<IMGXXBean> getIMGXX() {
        return IMGXX;
    }

    @JSONField(name="IMGXX")
    public void setIMGXX(List<IMGXXBean> IMGXX) {
        this.IMGXX = IMGXX;
    }

    public String getFJLX() {
        return FJLX;
    }

    @JSONField(name="FJLX")
    public void setFJLX(String FJLX) {
        this.FJLX = FJLX;
    }

    public static class IMGXXBean {
        /**
         * YM : 1
         * IMG :
         */
        @JSONField(name="YM")
        private String YM;
        @JSONField(name="IMG")
        private String IMG;

        public String getYM() {
            return YM;
        }

        @JSONField(name="YM")
        public void setYM(String YM) {
            this.YM = YM;
        }

        public String getIMG() {
            return IMG;
        }

        @JSONField(name="IMG")
        public void setIMG(String IMG) {
            this.IMG = IMG;
        }
    }
}
