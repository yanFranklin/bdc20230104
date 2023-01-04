package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/9/24
 * @description 自定义对比页面配置
 */
@Table(name = "BDC_ZDYDBYM_PZ")
@ApiModel(value = "BdcZdydbymPzDO", description = "自定义对比页面配置")
public class BdcZdydbymPzDO {

    @Id
    @ApiModelProperty(value = "配置ID")
    private String pzid;

    @ApiModelProperty(value = "对比代号")
    private String dbdh;

    @ApiModelProperty(value = "对比名称")
    private String dbmc;

    @ApiModelProperty(value = "创建人")
    private String cjr;

    @ApiModelProperty(value = "变更人")
    private String bgr;

    @ApiModelProperty(value = "变更时间")
    private Date bgsj;

    @ApiModelProperty(value = "页面地址")
    private String url;

    @ApiModelProperty(value = "页面入参")
    private String rc;

    @ApiModelProperty(value = "对比字段")
    private String dbzd;

    public String getPzid() {
        return pzid;
    }

    public void setPzid(String pzid) {
        this.pzid = pzid;
    }

    public String getDbdh() {
        return dbdh;
    }

    public void setDbdh(String dbdh) {
        this.dbdh = dbdh;
    }

    public String getDbmc() {
        return dbmc;
    }

    public void setDbmc(String dbmc) {
        this.dbmc = dbmc;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public String getBgr() {
        return bgr;
    }

    public void setBgr(String bgr) {
        this.bgr = bgr;
    }

    public Date getBgsj() {
        return bgsj;
    }

    public void setBgsj(Date bgsj) {
        this.bgsj = bgsj;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getDbzd() {
        return dbzd;
    }

    public void setDbzd(String dbzd) {
        this.dbzd = dbzd;
    }
}
