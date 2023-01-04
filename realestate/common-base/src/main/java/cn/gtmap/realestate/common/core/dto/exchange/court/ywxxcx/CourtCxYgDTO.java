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
@XmlRootElement(name = "YGDJ")
public class CourtCxYgDTO {

    @XmlElement(name = "BDCDYH")
    @ApiModelProperty(value = "不动产单元号")
    public String bdcdyh;

    @XmlElement(name = "YGDJZL")
    @ApiModelProperty(value = "预告登记种类")
    public String ygdjzl;

    @XmlElement(name = "ZL")
    @ApiModelProperty(value = "坐落")
    public String zl;

    @XmlElement(name = "GHYT")
    @ApiModelProperty(value = "规划用途")
    public String ghyt;

    @XmlElement(name = "JZMJ")
    @ApiModelProperty(value = "建筑面积")
    public String jzmj;

    @XmlElement(name = "BDCDJZMH")
    @ApiModelProperty(value = "不动产登记证明号")
    public String bdcdjzmh;

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
    List<CourtCxQlrDTO> qlrxxList;

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public void setYgdjzl(String ygdjzl) {
        this.ygdjzl = ygdjzl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public void setBdcdjzmh(String bdcdjzmh) {
        this.bdcdjzmh = bdcdjzmh;
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
