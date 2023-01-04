package cn.gtmap.realestate.common.core.dto.exchange.yancheng.shiji;

import cn.gtmap.realestate.common.util.ParamUtil;

import java.io.Serializable;

public class BdcShijiZzxzDTO implements Serializable {

    private static final long serialVersionUID = -8509825380399981622L;

    private String zjh;

    private String gzldyid;

    private String bjry;

    private String bjrzjh;

    private String gzlslid;

    private String certificateType;

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getBjry() {
        return bjry;
    }

    public void setBjry(String bjry) {
        this.bjry = bjry;
    }

    public String getBjrzjh() {
        return bjrzjh;
    }

    public void setBjrzjh(String bjrzjh) {
        this.bjrzjh = bjrzjh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }


    public static final class BdcShijiZzxzDTOBuilder {
        private String zjh;
        private String gzldyid;
        private String bjry;
        private String bjrzjh;
        private String gzlslid;
        private String certificateType;

        private BdcShijiZzxzDTOBuilder() {
        }

        public static BdcShijiZzxzDTOBuilder aBdcShijiZzxzDTO() {
            return new BdcShijiZzxzDTOBuilder();
        }

        public BdcShijiZzxzDTOBuilder withZjh(String zjh) {
            this.zjh = zjh;
            return this;
        }

        public BdcShijiZzxzDTOBuilder withGzldyid(String gzldyid) {
            this.gzldyid = gzldyid;
            return this;
        }

        public BdcShijiZzxzDTOBuilder withBjry(String bjry) {
            this.bjry = bjry;
            return this;
        }

        public BdcShijiZzxzDTOBuilder withBjrzjh(String bjrzjh) {
            this.bjrzjh = bjrzjh;
            return this;
        }

        public BdcShijiZzxzDTOBuilder withGzlslid(String gzlslid) {
            this.gzlslid = gzlslid;
            return this;
        }

        public BdcShijiZzxzDTOBuilder withCertificateType(String certificateType) {
            this.certificateType = certificateType;
            return this;
        }

        public BdcShijiZzxzDTO build() {
            BdcShijiZzxzDTO bdcShijiZzxzDTO = new BdcShijiZzxzDTO();
            bdcShijiZzxzDTO.setZjh(zjh);
            bdcShijiZzxzDTO.setGzldyid(gzldyid);
            bdcShijiZzxzDTO.setBjry(bjry);
            bdcShijiZzxzDTO.setBjrzjh(bjrzjh);
            bdcShijiZzxzDTO.setGzlslid(gzlslid);
            bdcShijiZzxzDTO.setCertificateType(certificateType);
            return bdcShijiZzxzDTO;
        }
    }

    public void checkParam(){
        ParamUtil.nonNull(this.gzldyid,"gzldyid不能为空");
        ParamUtil.nonNull(this.gzlslid,"gzlslid不能为空");
        ParamUtil.nonNull(this.zjh,"证件号不能为空");
        ParamUtil.nonNull(this.certificateType,"certificateType不能为空");
        ParamUtil.nonNull(this.bjry,"bjry不能为空");
        ParamUtil.nonNull(this.bjrzjh,"bjrzjh不能为空");
    }
}
