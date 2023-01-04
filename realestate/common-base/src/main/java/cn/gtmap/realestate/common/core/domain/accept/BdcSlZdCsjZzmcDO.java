package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 长三角业务证照名称字典值
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-05-10 15:54
 **/
@Table(name = "BDC_SL_ZD_CSJZZMC")
public class BdcSlZdCsjZzmcDO {

    @Id
    @ApiModelProperty(value = "代码")
    private String dm;

    @ApiModelProperty(value = "名称")
    private String mc;

    @ApiModelProperty(value = "证照来源1-本省2-标准")
    private String zzly;

    @ApiModelProperty(value = "证照类型")
    private String zzlx;


    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getZzly() {
        return zzly;
    }

    public void setZzly(String zzly) {
        this.zzly = zzly;
    }

    public String getZzlx() {
        return zzlx;
    }

    public void setZzlx(String zzlx) {
        this.zzlx = zzlx;
    }
}
