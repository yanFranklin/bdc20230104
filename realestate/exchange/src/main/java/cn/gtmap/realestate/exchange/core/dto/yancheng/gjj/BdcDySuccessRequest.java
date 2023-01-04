package cn.gtmap.realestate.exchange.core.dto.yancheng.gjj;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BdcDySuccessRequest implements Serializable {

    private static final long serialVersionUID = 6498979488227960879L;

    private String ywslbh;
    private String djzt;
    private String msg;
    private String dywzl;
    private String bdcdyh;
    private String bdcdydjzmh;
    private String bdcqzh;
    private String dyqdjrq;
    private String dyqdqrq;
    private String file;
    private List<BdcDyrxxDTO> dyrxx;

    public String getYwslbh() {
        return ywslbh;
    }

    public void setYwslbh(String ywslbh) {
        this.ywslbh = ywslbh;
    }

    public String getDjzt() {
        return djzt;
    }

    public void setDjzt(String djzt) {
        this.djzt = djzt;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDywzl() {
        return dywzl;
    }

    public void setDywzl(String dywzl) {
        this.dywzl = dywzl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcdydjzmh() {
        return bdcdydjzmh;
    }

    public void setBdcdydjzmh(String bdcdydjzmh) {
        this.bdcdydjzmh = bdcdydjzmh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getDyqdjrq() {
        return dyqdjrq;
    }

    public void setDyqdjrq(String dyqdjrq) {
        this.dyqdjrq = dyqdjrq;
    }

    public String getDyqdqrq() {
        return dyqdqrq;
    }

    public void setDyqdqrq(String dyqdqrq) {
        this.dyqdqrq = dyqdqrq;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<BdcDyrxxDTO> getDyrxx() {
        return dyrxx;
    }

    public void setDyrxx(List<BdcDyrxxDTO> dyrxx) {
        this.dyrxx = dyrxx;
    }


    public static final class BdcDySuccessRequestBuilder {
        private String ywslbh;
        private String djzt;
        private String msg;
        private String dywzl;
        private String bdcdyh;
        private String bdcdydjzmh;
        private String bdcqzh;
        private String dyqdjrq;
        private String dyqdqrq;
        private String file;
        private List<BdcDyrxxDTO> dyrxx;

        private BdcDySuccessRequestBuilder() {
        }

        public static BdcDySuccessRequestBuilder aBdcDySuccessRequest() {
            return new BdcDySuccessRequestBuilder();
        }

        public BdcDySuccessRequestBuilder withYwslbh(String ywslbh) {
            this.ywslbh = ywslbh;
            return this;
        }

        public BdcDySuccessRequestBuilder withDjzt(String djzt) {
            this.djzt = djzt;
            return this;
        }

        public BdcDySuccessRequestBuilder withMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public BdcDySuccessRequestBuilder withDywzl(String dywzl) {
            this.dywzl = dywzl;
            return this;
        }

        public BdcDySuccessRequestBuilder withBdcdyh(String bdcdyh) {
            this.bdcdyh = bdcdyh;
            return this;
        }

        public BdcDySuccessRequestBuilder withBdcdydjzmh(String bdcdydjzmh) {
            this.bdcdydjzmh = bdcdydjzmh;
            return this;
        }

        public BdcDySuccessRequestBuilder withBdcqzh(String bdcqzh) {
            this.bdcqzh = bdcqzh;
            return this;
        }

        public BdcDySuccessRequestBuilder withDyqdjrq(String dyqdjrq) {
            this.dyqdjrq = dyqdjrq;
            return this;
        }

        public BdcDySuccessRequestBuilder withDyqdqrq(String dyqdqrq) {
            this.dyqdqrq = dyqdqrq;
            return this;
        }

        public BdcDySuccessRequestBuilder withFile(String file) {
            this.file = file;
            return this;
        }

        public BdcDySuccessRequestBuilder withDyrxx(List<BdcDyrxxDTO> dyrxx) {
            this.dyrxx = dyrxx;
            return this;
        }

        public BdcDySuccessRequest build() {
            BdcDySuccessRequest bdcDySuccessRequest = new BdcDySuccessRequest();
            bdcDySuccessRequest.setYwslbh(ywslbh);
            bdcDySuccessRequest.setDjzt(djzt);
            bdcDySuccessRequest.setMsg(msg);
            bdcDySuccessRequest.setDywzl(dywzl);
            bdcDySuccessRequest.setBdcdyh(bdcdyh);
            bdcDySuccessRequest.setBdcdydjzmh(bdcdydjzmh);
            bdcDySuccessRequest.setBdcqzh(bdcqzh);
            bdcDySuccessRequest.setDyqdjrq(dyqdjrq);
            bdcDySuccessRequest.setDyqdqrq(dyqdqrq);
            bdcDySuccessRequest.setFile(file);
            bdcDySuccessRequest.setDyrxx(dyrxx);
            return bdcDySuccessRequest;
        }
    }
}
