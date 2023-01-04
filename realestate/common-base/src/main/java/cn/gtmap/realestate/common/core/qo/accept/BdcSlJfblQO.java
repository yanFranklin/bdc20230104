package cn.gtmap.realestate.common.core.qo.accept;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program: realestate
 * @description: 受理缴费办理QO
 * @author: <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @create: 2022-5-31 10:15
 **/
public class BdcSlJfblQO implements Serializable {

    private static final long serialVersionUID = 3087898564005120374L;

    @ApiModelProperty(value = "收费信息id")
    private String sfxxid;

    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;

    @ApiModelProperty(value = "缴费人姓名")
    private String jfrxm;

    @ApiModelProperty(value = "缴费人电话")
    private String jfrdh;

    public String getSfxxid() {
        return sfxxid;
    }

    public void setSfxxid(String sfxxid) {
        this.sfxxid = sfxxid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getJfrxm() {
        return jfrxm;
    }

    public void setJfrxm(String jfrxm) {
        this.jfrxm = jfrxm;
    }

    public String getJfrdh() {
        return jfrdh;
    }

    public void setJfrdh(String jfrdh) {
        this.jfrdh = jfrdh;
    }

    @Override
    public String toString() {
        return "BdcSlJfblQO{" +
                "sfxxid='" + sfxxid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", jfrxm='" + jfrxm + '\'' +
                ", jfrdh='" + jfrdh + '\'' +
                '}';
    }
}
