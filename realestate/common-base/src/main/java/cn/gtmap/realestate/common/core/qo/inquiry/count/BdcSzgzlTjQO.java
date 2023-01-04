package cn.gtmap.realestate.common.core.qo.inquiry.count;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/03/15/9:06
 * @Description: 缮证工作量统计QO
 */
public class BdcSzgzlTjQO {
    @ApiModelProperty("开始时间")
    private String kssj;
    @ApiModelProperty("结束时间")
    private String jzsj;
    @ApiModelProperty("部门代码")
    private String djbmdm;
    @ApiModelProperty(value = "部门名称")
    private String djbmmc;
    @ApiModelProperty(value = "人员名称")
    private String rymc;

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJzsj() {
        return jzsj;
    }

    public void setJzsj(String jzsj) {
        this.jzsj = jzsj;
    }

    public String getRymc() {
        return rymc;
    }

    public void setRymc(String rymc) {
        this.rymc = rymc;
    }

    public String getDjbmdm() {
        return djbmdm;
    }

    public void setDjbmdm(String djbmdm) {
        this.djbmdm = djbmdm;
    }

    public String getDjbmmc() {
        return djbmmc;
    }

    public void setDjbmmc(String djbmmc) {
        this.djbmmc = djbmmc;
    }

    @Override
    public String toString() {
        return "BdcSzgzlTjQO{" +
                "kssj='" + kssj + '\'' +
                ", jzsj='" + jzsj + '\'' +
                ", djbmdm='" + djbmdm + '\'' +
                ", djbmmc='" + djbmmc + '\'' +
                ", rymc='" + rymc + '\'' +
                '}';
    }
}
