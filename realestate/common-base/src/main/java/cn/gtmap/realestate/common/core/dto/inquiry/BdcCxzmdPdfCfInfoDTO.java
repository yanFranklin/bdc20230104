package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.Api;

import java.util.Objects;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/8/25 14:33
 */
@Api(value = "BdcCxzmdPdfCfInfoDTO", description = "常州登记3.0查询证明单pdf-查封信息")
public class BdcCxzmdPdfCfInfoDTO {

    private String xzr;

    private String xzlb;

    private String xzwh;

    private String xzqx;

    private String xzdjsj;

    private String xzrwh;

    public String getXzr() {
        return xzr;
    }

    public void setXzr(String xzr) {
        this.xzr = xzr;
    }

    public String getXzlb() {
        return xzlb;
    }

    public void setXzlb(String xzlb) {
        this.xzlb = xzlb;
    }

    public String getXzwh() {
        return xzwh;
    }

    public void setXzwh(String xzwh) {
        this.xzwh = xzwh;
    }

    public String getXzqx() {
        return xzqx;
    }

    public void setXzqx(String xzqx) {
        this.xzqx = xzqx;
    }

    public String getXzdjsj() {
        return xzdjsj;
    }

    public void setXzdjsj(String xzdjsj) {
        this.xzdjsj = xzdjsj;
    }

    public String getXzrwh() {
        return xzrwh;
    }

    public void setXzrwh(String xzrwh) {
        this.xzrwh = xzrwh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        BdcCxzmdPdfCfInfoDTO that = (BdcCxzmdPdfCfInfoDTO) o;
        return Objects.equals(xzr, that.xzr) &&
                Objects.equals(xzlb, that.xzlb) &&
                Objects.equals(xzwh, that.xzwh) &&
                Objects.equals(xzqx, that.xzqx) &&
                Objects.equals(xzdjsj, that.xzdjsj) &&
                Objects.equals(xzrwh, that.xzrwh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xzr, xzlb, xzwh, xzqx, xzdjsj, xzrwh);
    }
}
