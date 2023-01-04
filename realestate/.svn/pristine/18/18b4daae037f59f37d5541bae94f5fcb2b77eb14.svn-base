package cn.gtmap.realestate.common.core.dto.exchange;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021-04-23
 * @description 业务系统第三方系统档案查询日志
 */
public class BdcDaCxLogDTO implements Serializable {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "档案日志类型 1: ES 2:STORAGE")
    private String dacxloglx;

    @ApiModelProperty(value = "外健 当存储方式为ES时，该字段记录的为对应log存储的ID当存储方式为STORAGE时为大云文件存储id")
    private String foreign;

    @ApiModelProperty(value = "档案归属")
    private String dags;

    @ApiModelProperty(value = "档案查询日志状态 :0:新建 1:已查询 9:废弃")
    private String dacxlogzt;

    @ApiModelProperty(value = "创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cjsj;

    @ApiModelProperty(value = "当存储的log类型为STORAGE时，存放文件的spaceId")
    private String pdfSpaceId;

    private String pdfJsonStr;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPdfJsonStr() {
        return pdfJsonStr;
    }

    public void setPdfJsonStr(String pdfJsonStr) {
        this.pdfJsonStr = pdfJsonStr;
    }

    public String getPdfSpaceId() {
        return pdfSpaceId;
    }

    public void setPdfSpaceId(String pdfSpaceId) {
        this.pdfSpaceId = pdfSpaceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDacxloglx() {
        return dacxloglx;
    }

    public void setDacxloglx(String dacxloglx) {
        this.dacxloglx = dacxloglx;
    }

    public String getForeign() {
        return foreign;
    }

    public void setForeign(String foreign) {
        this.foreign = foreign;
    }

    public String getDags() {
        return dags;
    }

    public void setDags(String dags) {
        this.dags = dags;
    }

    public String getDacxlogzt() {
        return dacxlogzt;
    }

    public void setDacxlogzt(String dacxlogzt) {
        this.dacxlogzt = dacxlogzt;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public static final class BdcDaCxLogDTOBuilder {
        private String id;
        private String dacxloglx;
        private String foreign;
        private String dags;
        private String dacxlogzt;
        private Date cjsj;
        private String pdfSpaceId;
        private String pdfJsonStr;
        private String url;

        private BdcDaCxLogDTOBuilder() {
        }

        public static BdcDaCxLogDTOBuilder aBdcDaCxLogDTO() {
            return new BdcDaCxLogDTOBuilder();
        }

        public BdcDaCxLogDTOBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public BdcDaCxLogDTOBuilder withDacxloglx(String dacxloglx) {
            this.dacxloglx = dacxloglx;
            return this;
        }

        public BdcDaCxLogDTOBuilder withForeign(String foreign) {
            this.foreign = foreign;
            return this;
        }

        public BdcDaCxLogDTOBuilder withDags(String dags) {
            this.dags = dags;
            return this;
        }

        public BdcDaCxLogDTOBuilder withDacxlogzt(String dacxlogzt) {
            this.dacxlogzt = dacxlogzt;
            return this;
        }

        public BdcDaCxLogDTOBuilder withCjsj(Date cjsj) {
            this.cjsj = cjsj;
            return this;
        }

        public BdcDaCxLogDTOBuilder withPdfSpaceId(String pdfSpaceId) {
            this.pdfSpaceId = pdfSpaceId;
            return this;
        }

        public BdcDaCxLogDTOBuilder withPdfJsonStr(String pdfJsonStr) {
            this.pdfJsonStr = pdfJsonStr;
            return this;
        }

        public BdcDaCxLogDTOBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public BdcDaCxLogDTO build() {
            BdcDaCxLogDTO bdcDaCxLogDTO = new BdcDaCxLogDTO();
            bdcDaCxLogDTO.setId(id);
            bdcDaCxLogDTO.setDacxloglx(dacxloglx);
            bdcDaCxLogDTO.setForeign(foreign);
            bdcDaCxLogDTO.setDags(dags);
            bdcDaCxLogDTO.setDacxlogzt(dacxlogzt);
            bdcDaCxLogDTO.setCjsj(cjsj);
            bdcDaCxLogDTO.setPdfSpaceId(pdfSpaceId);
            bdcDaCxLogDTO.setPdfJsonStr(pdfJsonStr);
            bdcDaCxLogDTO.setUrl(url);
            return bdcDaCxLogDTO;
        }
    }

}
