package cn.gtmap.realestate.exchange.core.dto.wwsq.fcjy.rcsd;

import io.swagger.annotations.ApiModel;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/10/13
 * @description 人才锁定房源信息状态查询返回DTO
 */
@ApiModel(value = "BdcRcsdFyxxZtDTO", description = "人才锁定房源信息状态查询返回DTO")
public class BdcRcsdFyxxZtDTO {

    /**
     * cqzh : 皖(2020)淮安区不动产权第6000001号
     * bdcdyh : 3424234412432414124
     * sfdy : 1
     * sfyy : 1
     * sfcf : 1
     */

    private String bdcqzh;
    private String bdcdyh;
    private String sfdy;
    private String sfyy;
    private String sfcf;

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getSfdy() {
        return sfdy;
    }

    public void setSfdy(String sfdy) {
        this.sfdy = sfdy;
    }

    public String getSfyy() {
        return sfyy;
    }

    public void setSfyy(String sfyy) {
        this.sfyy = sfyy;
    }

    public String getSfcf() {
        return sfcf;
    }

    public void setSfcf(String sfcf) {
        this.sfcf = sfcf;
    }

    @Override
    public String toString() {
        return "BdcRcsdFyxxZtDTO{" +
                "bdcqzh='" + bdcqzh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", sfdy='" + sfdy + '\'' +
                ", sfyy='" + sfyy + '\'' +
                ", sfcf='" + sfcf + '\'' +
                '}';
    }
}
