package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.qo.pub.QlrQO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/12/16/11:38
 * @Description:
 */
public class EsfWqHtxxDTO implements Serializable {

    private static final long serialVersionUID = 8090709766866534718L;

    @ApiModelProperty(value = "房屋坐落")
    private String fwzl;

    @ApiModelProperty(value = "合同编号")
    private String htbh;
    @ApiModelProperty(value = "主体信息")
    private List<EsfWqHtxxZtxxDTO> ztxx;
    @ApiModelProperty(value = "响应码")
    private List<String> urlList;
    @ApiModelProperty(value = "交易价格，单位是元")
    private Double jysj;
    @ApiModelProperty(value = "交易备案时间")
    private String jybasj;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFwzl() {
        return fwzl;
    }

    public void setFwzl(String fwzl) {
        this.fwzl = fwzl;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public List<EsfWqHtxxZtxxDTO> getZtxx() {
        return ztxx;
    }

    public void setZtxx(List<EsfWqHtxxZtxxDTO> ztxx) {
        this.ztxx = ztxx;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public Double getJysj() {
        return jysj;
    }

    public void setJysj(Double jysj) {
        this.jysj = jysj;
    }

    public String getJybasj() {
        return jybasj;
    }

    public void setJybasj(String jybasj) {
        this.jybasj = jybasj;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @Override
    public String toString() {
        return "EsfWqHtxxDTO{" +
                "fwzl='" + fwzl + '\'' +
                ", htbh='" + htbh + '\'' +
                ", ztxx=" + ztxx +
                ", urlList=" + urlList +
                ", jysj=" + jysj +
                ", jybasj='" + jybasj + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                '}';
    }
}
