package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/27
 * @description 不动产信息比对配置
 */
@Table(name = "BDC_XXBD_PZ")
@ApiModel(value = "BdcXxbdPzDO", description = "不动产信息比对配置")
public class BdcXxbdPzDO {

    @Id
    @ApiModelProperty(value = "配置ID")
    private String pzid;

    @ApiModelProperty(value = "比对类型")
    private String bdlx;

    @ApiModelProperty(value = "比对字段")
    private String bdzd;

    @ApiModelProperty(value = "配置人姓名")
    private String pzrxm;

    @ApiModelProperty(value = "配置时间")
    private Date pzsj;

    public String getPzid() {
        return pzid;
    }

    public void setPzid(String pzid) {
        this.pzid = pzid;
    }

    public String getBdlx() {
        return bdlx;
    }

    public void setBdlx(String bdlx) {
        this.bdlx = bdlx;
    }

    public String getBdzd() {
        return bdzd;
    }

    public void setBdzd(String bdzd) {
        this.bdzd = bdzd;
    }

    public String getPzrxm() {
        return pzrxm;
    }

    public void setPzrxm(String pzrxm) {
        this.pzrxm = pzrxm;
    }

    public Date getPzsj() {
        return pzsj;
    }

    public void setPzsj(Date pzsj) {
        this.pzsj = pzsj;
    }
}
