package cn.gtmap.realestate.common.core.dto.exchange.court.ywxxcx;

import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/12/07 15:13
 */
@XmlRootElement(name = "TDSYQ")
public class CourtCxTdsyqDTO {

    @XmlElement(name = "BDCDYH")
    @ApiModelProperty(value = "不动产单元号")
    public String bdcdyh;

    @XmlElement(name = "ZL")
    @ApiModelProperty(value = "坐落")
    public String zl;

    @XmlElement(name = "ZDMJ")
    @ApiModelProperty(value = "宗地面积")
    public String zdmj;

    @XmlElement(name = "MJDW")
    @ApiModelProperty(value = "面积单位")
    public String mjdw;

    @XmlElement(name = "YT")
    @ApiModelProperty(value = "用途")
    public String yt;

    @XmlElement(name = "QLXZ")
    @ApiModelProperty(value = "权利性质")
    public String qlxz;

    @XmlElement(name = "BDCQZH")
    @ApiModelProperty(value = "不动产权证号")
    public String bdcqzh;

    @XmlElement(name = "DJJG")
    @ApiModelProperty(value = "登记机构")
    public String djjg;

    @XmlElement(name = "DQDM")
    @ApiModelProperty(value = "地区代码")
    public String dqdm;

    @XmlElement(name = "YWH")
    @ApiModelProperty(value = "业务号 项目ID")
    public String ywh;

    @XmlElement(name = "BEIZ")
    @ApiModelProperty(value = "备注")
    public String bz;

    @XmlElement(name = "QLRXXLIST")
    @ApiModelProperty(value = "权利人")
    public List<CourtCxQlrDTO> qlrxxList;

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public void setZdmj(String zdmj) {
        this.zdmj = zdmj;
    }

    public void setMjdw(String mjdw) {
        this.mjdw = mjdw;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public void setDqdm(String dqdm) {
        this.dqdm = dqdm;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public void setQlrxxList(List<CourtCxQlrDTO> qlrxxList) {
        this.qlrxxList = qlrxxList;
    }
}
