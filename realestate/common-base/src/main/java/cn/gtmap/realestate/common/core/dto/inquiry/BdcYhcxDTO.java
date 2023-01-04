package cn.gtmap.realestate.common.core.dto.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/11/28
 * @description 银行查询返回值DTO
 */
public class BdcYhcxDTO {
    @ApiModelProperty(value = "权利人")
    private String qlr;
    @ApiModelProperty(value = "证书号")
    private String bdcqzh;
    @ApiModelProperty(value = "登记时间")
    private String djsj;
    @ApiModelProperty(value = "共有情况")
    private String gyfs;
    @ApiModelProperty(value = "是否注销")
    private String sfzx;
    @ApiModelProperty(value = "注销时间")
    private String zxsj;
    @ApiModelProperty(value = "附记")
    private String fj;

    @ApiModelProperty(value = "土地使用权取得方式")
    private String qlxz;
    @ApiModelProperty(value = "土地使用年限")
    private String tdsyjssj;

    @ApiModelProperty(value = "产权信息")
    private List<BdcYhcxCqDTO> bdcYhcxCqDTOList;
    @ApiModelProperty(value = "抵押信息")
    private List<BdcYhcxDyaDTO> bdcDyaqDOList;
    @ApiModelProperty(value = "查封信息")
    private List<BdcCfDO> bdcCfDOList;

    @ApiModelProperty(value = "异议信息")
    private String yyxx;

    @ApiModelProperty(value = "锁定信息")
    private String sdxx;

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getDjsj() {
        return djsj;
    }

    public void setDjsj(String djsj) {
        this.djsj = djsj;
    }

    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public String getSfzx() {
        return sfzx;
    }

    public void setSfzx(String sfzx) {
        this.sfzx = sfzx;
    }

    public String getZxsj() {
        return zxsj;
    }

    public void setZxsj(String zxsj) {
        this.zxsj = zxsj;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public String getTdsyjssj() {
        return tdsyjssj;
    }

    public void setTdsyjssj(String tdsyjssj) {
        this.tdsyjssj = tdsyjssj;
    }

    public List<BdcYhcxCqDTO> getBdcYhcxCqDTOList() {
        return bdcYhcxCqDTOList;
    }

    public void setBdcYhcxCqDTOList(List<BdcYhcxCqDTO> bdcYhcxCqDTOList) {
        this.bdcYhcxCqDTOList = bdcYhcxCqDTOList;
    }

    public List<BdcYhcxDyaDTO> getBdcDyaqDOList() {
        return bdcDyaqDOList;
    }

    public void setBdcDyaqDOList(List<BdcYhcxDyaDTO> bdcDyaqDOList) {
        this.bdcDyaqDOList = bdcDyaqDOList;
    }

    public List<BdcCfDO> getBdcCfDOList() {
        return bdcCfDOList;
    }

    public void setBdcCfDOList(List<BdcCfDO> bdcCfDOList) {
        this.bdcCfDOList = bdcCfDOList;
    }

    public String getYyxx() {
        return yyxx;
    }

    public void setYyxx(String yyxx) {
        this.yyxx = yyxx;
    }

    public String getSdxx() {
        return sdxx;
    }

    public void setSdxx(String sdxx) {
        this.sdxx = sdxx;
    }

    @Override
    public String toString() {
        return "BdcYhcxDTO{" +
                "qlr='" + qlr + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", djsj='" + djsj + '\'' +
                ", gyfs='" + gyfs + '\'' +
                ", sfzx='" + sfzx + '\'' +
                ", zxsj='" + zxsj + '\'' +
                ", fj='" + fj + '\'' +
                ", qlxz='" + qlxz + '\'' +
                ", tdsyjssj='" + tdsyjssj + '\'' +
                ", bdcYhcxCqDTOList=" + bdcYhcxCqDTOList +
                ", bdcDyaqDOList=" + bdcDyaqDOList +
                ", bdcCfDOList=" + bdcCfDOList +
                ", yyxx='" + yyxx + '\'' +
                ", sdxx='" + sdxx + '\'' +
                '}';
    }
}
