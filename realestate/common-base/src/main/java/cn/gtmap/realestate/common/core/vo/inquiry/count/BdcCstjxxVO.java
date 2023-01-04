package cn.gtmap.realestate.common.core.vo.inquiry.count;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @description 超时统计信息，返回结果
 */
public class BdcCstjxxVO {

    @ApiModelProperty(value = "部门id")
    private String bmid;
    @ApiModelProperty(value = "部门名称")
    private String bmmc;
    @ApiModelProperty(value = "超时件数量")
    private Long csjsl;

    public String getBmid() {
        return bmid;
    }

    public void setBmid(String bmid) {
        this.bmid = bmid;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public Long getCsjsl() {
        return csjsl;
    }

    public void setCsjsl(Long csjsl) {
        this.csjsl = csjsl;
    }

    @Override
    public String toString() {
        return "BdcCstjxxVO{" +
                "bmid='" + bmid + '\'' +
                ", bmmc='" + bmmc + '\'' +
                ", csjsl='" + csjsl + '\'' +
                '}';
    }
}
