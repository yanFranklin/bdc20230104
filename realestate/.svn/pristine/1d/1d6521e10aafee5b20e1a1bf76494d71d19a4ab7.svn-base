package cn.gtmap.realestate.common.core.dto.exchange.court;

import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 法院提供的司法控制请求-反馈控制结果数据
 *
 * @author wangyinghao
 */
@XmlRootElement(name = "BDCKZJG")
public class CourtKzKzxxItem {
    @ApiModelProperty(value = "控制请求单号")
    @XmlElement(name = "CXQQDH")
    private String CXQQDH;

    @ApiModelProperty(value = "控制请求单号")
    @XmlElement(name = "RESULT")
    private String RESULT;

    @XmlElementWrapper(name = "KZXXLIST")
    @XmlElement(name = "KZXX")
    List<CourtKzKzxx> KZXXLIST;

    public void setCXQQDH(String CXQQDH) {
        this.CXQQDH = CXQQDH;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public void setKZXXLIST(List<CourtKzKzxx> KZXXLIST) {
        this.KZXXLIST = KZXXLIST;
    }

    @XmlRootElement(name = "KZXX")
    public static class CourtKzKzxx {

        @ApiModelProperty(value = "不动产权利类型")
        @XmlElement(name = "BDCQLLX")
        private String BDCQLLX;

        @ApiModelProperty(value = "不动产单元号")
        @XmlElement(name = "BDCDYH")
        private String BDCDYH;

        @ApiModelProperty(value = "不动产权证号")
        @XmlElement(name = "BDCQZH")
        private String BDCQZH;

        @ApiModelProperty(value = "控制结果")
        @XmlElement(name = "KZZT")
        private String KZZT;

        @ApiModelProperty(value = "措施始期")
        @XmlElement(name = "CSKSRQ")
        private String CSKSRQ;

        @ApiModelProperty(value = "措施终期")
        @XmlElement(name = "CSJSRQ")
        private String CSJSRQ;

        @ApiModelProperty(value = "未能控制原因")
        @XmlElement(name = "WNKZYY")
        private String WNKZYY;

        @ApiModelProperty(value = "备注")
        @XmlElement(name = "BEIZ")
        private String BEIZ;

        @ApiModelProperty(value = "业务号")
        @XmlElement(name = "YWH")
        private String YWH;

        @ApiModelProperty(value = "查封业务号")
        @XmlElement(name = "CFYWH")
        private String CFYWH;

        public void setBDCQLLX(String BDCQLLX) {
            this.BDCQLLX = BDCQLLX;
        }

        public void setBDCDYH(String BDCDYH) {
            this.BDCDYH = BDCDYH;
        }

        public void setBDCQZH(String BDCQZH) {
            this.BDCQZH = BDCQZH;
        }

        public void setKZZT(String KZZT) {
            this.KZZT = KZZT;
        }

        public void setCSKSRQ(String CSKSRQ) {
            this.CSKSRQ = CSKSRQ;
        }

        public void setCSJSRQ(String CSJSRQ) {
            this.CSJSRQ = CSJSRQ;
        }

        public void setWNKZYY(String WNKZYY) {
            this.WNKZYY = WNKZYY;
        }

        public void setBEIZ(String BEIZ) {
            this.BEIZ = BEIZ;
        }

        public void setYWH(String YWH) {
            this.YWH = YWH;
        }

        public void setCFYWH(String CFYWH) {
            this.CFYWH = CFYWH;
        }
    }
}
