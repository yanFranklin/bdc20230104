package cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.jsdjbjjgxx;

import io.swagger.annotations.ApiModelProperty;
/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/12/07 15:13
 */
public class CourtJsdjbjjgxxRequestYwwxxDTO {

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "业务号")
    private String ywh;

    @ApiModelProperty(value = "备注")
    private String bz;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Override
    public String toString() {
        return "CourtJsdjbjjgxxRequestYwwxxDTO{" +
                "bdcdyh='" + bdcdyh + '\'' +
                ", ywh='" + ywh + '\'' +
                ", bz='" + bz + '\'' +
                '}';
    }
}
