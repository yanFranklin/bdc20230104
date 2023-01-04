package cn.gtmap.realestate.common.core.dto.etl;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 2021/01/16,1.0
 * @description
 */
public class BdcCzRlsbDTO {

    /**
     * 项目id
     */
    private String xmid;

    /**
     * 人脸识别业务类型（常州）
     */
    private String ywlx;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getYwlx() {
        return ywlx;
    }

    public void setYwlx(String ywlx) {
        this.ywlx = ywlx;
    }


    public static final class BdcCzRlsbDTOBuilder {
        private String xmid;
        private String ywlx;

        private BdcCzRlsbDTOBuilder() {
        }

        public static BdcCzRlsbDTOBuilder aBdcCzRlsbDTO() {
            return new BdcCzRlsbDTOBuilder();
        }

        public BdcCzRlsbDTOBuilder withXmid(String xmid) {
            this.xmid = xmid;
            return this;
        }

        public BdcCzRlsbDTOBuilder withYwlx(String ywlx) {
            this.ywlx = ywlx;
            return this;
        }

        public BdcCzRlsbDTO build() {
            BdcCzRlsbDTO bdcCzRlsbDTO = new BdcCzRlsbDTO();
            bdcCzRlsbDTO.setXmid(xmid);
            bdcCzRlsbDTO.setYwlx(ywlx);
            return bdcCzRlsbDTO;
        }
    }
}
