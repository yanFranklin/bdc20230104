package cn.gtmap.realestate.common.core.domain.exchange;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import org.dozer.Mapping;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021-04-23
 * @description 业务系统第三方系统档案查询日志
 */
@Table(name = "BDC_DA_CX_LOG")
public class BdcDaCxLog implements Serializable {

    private static final long serialVersionUID = -8096861230536326004L;

    @Id
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


    public static final class BdcDaCxLogBuilder {
        private String id;
        private String dacxloglx;
        private String foreign;
        private String dags;
        private String dacxlogzt;
        private Date cjsj;
        private String pdfSpaceId;

        private BdcDaCxLogBuilder() {
        }

        public static BdcDaCxLogBuilder aBdcDaCxLog() {
            return new BdcDaCxLogBuilder();
        }

        public BdcDaCxLogBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public BdcDaCxLogBuilder withDacxloglx(String dacxloglx) {
            this.dacxloglx = dacxloglx;
            return this;
        }

        public BdcDaCxLogBuilder withForeign(String foreign) {
            this.foreign = foreign;
            return this;
        }

        public BdcDaCxLogBuilder withDags(String dags) {
            this.dags = dags;
            return this;
        }

        public BdcDaCxLogBuilder withDacxlogzt(String dacxlogzt) {
            this.dacxlogzt = dacxlogzt;
            return this;
        }

        public BdcDaCxLogBuilder withCjsj(Date cjsj) {
            this.cjsj = cjsj;
            return this;
        }

        public BdcDaCxLogBuilder withPdfSpaceId(String pdfSpaceId) {
            this.pdfSpaceId = pdfSpaceId;
            return this;
        }

        public BdcDaCxLog build() {
            BdcDaCxLog bdcDaCxLog = new BdcDaCxLog();
            bdcDaCxLog.setId(id);
            bdcDaCxLog.setDacxloglx(dacxloglx);
            bdcDaCxLog.setForeign(foreign);
            bdcDaCxLog.setDags(dags);
            bdcDaCxLog.setDacxlogzt(dacxlogzt);
            bdcDaCxLog.setCjsj(cjsj);
            bdcDaCxLog.setPdfSpaceId(pdfSpaceId);
            return bdcDaCxLog;
        }
    }
}
