package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/8/28
 * @description
 */
@Table(name = "BDC_GZLW_SH_LOG")
public class BdcGzlwShLogDO {
    @Id
    @ApiModelProperty(value = "规则例外日志id")
    private String gzlwlogid;
    @ApiModelProperty(value = "生成日期")
    private Date scrq;
    @ApiModelProperty(value = "操作")
    private String cz;

    public String getGzlwlogid() {
        return gzlwlogid;
    }

    public void setGzlwlogid(String gzlwlogid) {
        this.gzlwlogid = gzlwlogid;
    }

    public Date getScrq() {
        return scrq;
    }

    public void setScrq(Date scrq) {
        this.scrq = scrq;
    }

    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
    }
}
