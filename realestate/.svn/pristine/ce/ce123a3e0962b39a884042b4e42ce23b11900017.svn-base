package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 常州查询证明单PDF（大数据局接口）居住权信息实体
 */
@Api(value = "BdcCxzmdPdfJzqDTO", description = "常州查询证明单PDF（大数据局接口）居住权信息实体")
public class BdcCxzmdPdfJzqDTO {
    @ApiModelProperty(value = "居住权证流水号")
    private String jzqzlsh;

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "查档证明期限")
    private String cdzmqx;


    public String getJzqzlsh() {
        return jzqzlsh;
    }

    public void setJzqzlsh(String jzqzlsh) {
        this.jzqzlsh = jzqzlsh;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getCdzmqx() {
        return cdzmqx;
    }

    public void setCdzmqx(String cdzmqx) {
        this.cdzmqx = cdzmqx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        BdcCxzmdPdfJzqDTO that = (BdcCxzmdPdfJzqDTO) o;
        return Objects.equals(jzqzlsh, that.jzqzlsh) &&
                Objects.equals(qlr, that.qlr) &&
                Objects.equals(cdzmqx, that.cdzmqx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jzqzlsh, qlr, cdzmqx);
    }
}
