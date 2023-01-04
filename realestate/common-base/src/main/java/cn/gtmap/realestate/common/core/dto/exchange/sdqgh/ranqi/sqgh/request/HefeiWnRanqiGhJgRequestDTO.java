package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.request;

import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-18
 * @description 合肥皖能燃气 申请过户 结果更新
 */
@Data
public class HefeiWnRanqiGhJgRequestDTO {
    /**
     * 业务号(slbh)
     */
    private String ywh;

    /**
     * 用户号
     */
    private String yhh;

    /**
     * 受理时间
     */
    private String slsj;

    /**
     * 审核时间
     */
    private String shsj;

    /**
     * 审核意见
     */
    private String shyj;

    /**
     * 审核结果
     * 1：通过
     * 2：驳回
     */
    private String shjg;

    /**
     * 无法受理原因
     */
    private String wfslyy;

    /**
     * 备注
     */
    private String remark;

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getYhh() {
        return yhh;
    }

    public void setYhh(String yhh) {
        this.yhh = yhh;
    }

    public String getSlsj() {
        return slsj;
    }

    public void setSlsj(String slsj) {
        this.slsj = slsj;
    }

    public String getShsj() {
        return shsj;
    }

    public void setShsj(String shsj) {
        this.shsj = shsj;
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj;
    }

    public String getShjg() {
        return shjg;
    }

    public void setShjg(String shjg) {
        this.shjg = shjg;
    }

    public String getWfslyy() {
        return wfslyy;
    }

    public void setWfslyy(String wfslyy) {
        this.wfslyy = wfslyy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
