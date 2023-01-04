package cn.gtmap.realestate.exchange.core.dto.nantong.sw.fjxx;

import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclmxDTO;

import java.util.List;

public class FjxxBean {
        /**
         * clmc : 申请书
         * ys : 2
         * fs : 1
         * clnr : [{"fjid":"ff8080816da4d23f016da5cd4865018f","fjurl":"http://127.0.0.1:8080/fileCenter/file/get.do?token=whosyourdaddy&fid=2929865","fjnr":"","fjmc":"申请书.jpg"}]
         * mrfjys : 1
         * fjlx : 复印件
         */

        private String clmc;
        private String ys;
        private String fs;
        private String mrfjys;
        private String fjlx;
        private List<FjclmxDTO> clnr;

        public String getClmc() {
            return clmc;
        }

        public void setClmc(String clmc) {
            this.clmc = clmc;
        }

        public String getYs() {
            return ys;
        }

        public void setYs(String ys) {
            this.ys = ys;
        }

        public String getFs() {
            return fs;
        }

        public void setFs(String fs) {
            this.fs = fs;
        }

        public String getMrfjys() {
            return mrfjys;
        }

        public void setMrfjys(String mrfjys) {
            this.mrfjys = mrfjys;
        }

        public String getFjlx() {
            return fjlx;
        }

        public void setFjlx(String fjlx) {
            this.fjlx = fjlx;
        }

        public List<FjclmxDTO> getClnr() {
            return clnr;
        }

        public void setClnr(List<FjclmxDTO> clnr) {
            this.clnr = clnr;
        }


}
