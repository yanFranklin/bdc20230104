package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @program: realestate
 * @description: 收件材料电子证照对照实体
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-01-02 14:42
 **/

@Table(name = "BDC_SJCL_DZZZ_DZ")
@ApiModel(value = "BdcSjclDzzzDzDO", description = "收件材料电子证照对照")
public class BdcSjclDzzzDzDO implements Serializable {
    private static final long serialVersionUID = 4760879624087194899L;

    @Id
    @ApiModelProperty(value = "对照id")
    private String dzid;

    @ApiModelProperty(value = "材料名称")
    private String clmc;

    @ApiModelProperty(value = "电子证照代码")
    private String dzzzdm;

    @ApiModelProperty(value = "电子证照名称")
    private String dzzzmc;

    public String getDzid() {
        return dzid;
    }

    public void setDzid(String dzid) {
        this.dzid = dzid;
    }

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

    public String getDzzzdm() {
        return dzzzdm;
    }

    public void setDzzzdm(String dzzzdm) {
        this.dzzzdm = dzzzdm;
    }

    public String getDzzzmc() {
        return dzzzmc;
    }

    public void setDzzzmc(String dzzzmc) {
        this.dzzzmc = dzzzmc;
    }

    @Override
    public String toString() {
        return "BdcSjclDzzzDzDO{" +
                "dzid='" + dzid + '\'' +
                ", clmc='" + clmc + '\'' +
                ", dzzzdm='" + dzzzdm + '\'' +
                ", dzzzmc='" + dzzzmc + '\'' +
                '}';
    }
}
