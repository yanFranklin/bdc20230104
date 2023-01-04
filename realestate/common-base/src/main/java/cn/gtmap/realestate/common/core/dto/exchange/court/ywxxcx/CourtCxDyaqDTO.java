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
@XmlRootElement(name = "DYAQ")
public class CourtCxDyaqDTO {

    @XmlElement(name = "BDCDYH")
    @ApiModelProperty(value = "不动产单元号")
    public String bdcdyh;

    @XmlElement(name = "DYBDCLX")
    @ApiModelProperty(value = "抵押不动产类型")
    public String dybdclx;

    @XmlElement(name = "ZL")
    @ApiModelProperty(value = "坐落")
    public String zl;

    @XmlElement(name = "DYR")
    @ApiModelProperty(value = "抵押人")
    public String dyr;

    @XmlElement(name = "DYFS")
    @ApiModelProperty(value = "抵押方式")
    public String dyfs;

    @XmlElement(name = "BDBZZQSE")
    @ApiModelProperty(value = "被担保主债权数额")
    public String bdbzzqse;

    @XmlElement(name = "ZWLXQSSJ")
    @ApiModelProperty(value = "债务履行期限起")
    public String zwlxqssj;

    @XmlElement(name = "ZWLXJSSJ")
    @ApiModelProperty(value = "债务履行期限止")
    public String zwlxjssj;

    @XmlElement(name = "BDCDJZMH")
    @ApiModelProperty(value = "不动产登记证明号")
    public String bdcdjzmh;

    @XmlElement(name = "DYQR")
    @ApiModelProperty(value = "抵押权人")
    public String dyqr;

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

    public void setDybdclx(String dybdclx) {
        this.dybdclx = dybdclx;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public void setDyr(String dyr) {
        this.dyr = dyr;
    }

    public void setDyfs(String dyfs) {
        this.dyfs = dyfs;
    }

    public void setBdbzzqse(String bdbzzqse) {
        this.bdbzzqse = bdbzzqse;
    }

    public void setZwlxqssj(String zwlxqssj) {
        this.zwlxqssj = zwlxqssj;
    }

    public void setZwlxjssj(String zwlxjssj) {
        this.zwlxjssj = zwlxjssj;
    }

    public void setBdcdjzmh(String bdcdjzmh) {
        this.bdcdjzmh = bdcdjzmh;
    }

    public void setDyqr(String dyqr) {
        this.dyqr = dyqr;
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
