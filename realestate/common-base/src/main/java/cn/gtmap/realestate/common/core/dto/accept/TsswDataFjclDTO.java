package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-09
 * @description 推送税务附件材料实体
 */
public class TsswDataFjclDTO {

    // 材料名称
    private String clmc;

    // 文件中心图片ID
    private String wjzxid;

    @ApiModelProperty(value = "资料来源")
    private String zlly;

    @ApiModelProperty(value = "附件类型")
    private String fjlx;

    @ApiModelProperty(value = "下载地址")
    private String xzdz;



    // 文件夹下子文件
    private List<TsswDataFjclXxDTO> fjxx;
    

    public List<TsswDataFjclXxDTO> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<TsswDataFjclXxDTO> fjxx) {
        this.fjxx = fjxx;
    }

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

    public String getWjzxid() {
        return wjzxid;
    }

    public void setWjzxid(String wjzxid) {
        this.wjzxid = wjzxid;
    }

    public String getZlly() {
        return zlly;
    }

    public void setZlly(String zlly) {
        this.zlly = zlly;
    }

    public String getFjlx() {
        return fjlx;
    }

    public void setFjlx(String fjlx) {
        this.fjlx = fjlx;
    }

    public String getXzdz() {
        return xzdz;
    }

    public void setXzdz(String xzdz) {
        this.xzdz = xzdz;
    }

    @Override
    public String toString() {
        return "TsswDataFjclDTO{" +
                "clmc='" + clmc + '\'' +
                ", wjzxid='" + wjzxid + '\'' +
                ", zlly='" + zlly + '\'' +
                ", fjlx='" + fjlx + '\'' +
                ", xzdz='" + xzdz + '\'' +
                ", fjxx=" + fjxx +
                '}';
    }
}
