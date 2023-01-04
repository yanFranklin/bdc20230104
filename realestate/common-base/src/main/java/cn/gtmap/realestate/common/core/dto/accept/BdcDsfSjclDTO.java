package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/8/16
 * @description 不动产第三方收件材料
 */
@ApiModel(value = "BdcDsfSjclDTO", description = "不动产第三方收件材料")
public class BdcDsfSjclDTO {

    @ApiModelProperty(value = "序号")
    private Integer xh;

    @ApiModelProperty(value = "文件名称")
    private String fjmc;

    @ApiModelProperty(value = "材料名称")
    private String clmc;

    @ApiModelProperty(value = "份数")
    private Integer fs;

    @ApiModelProperty(value = "页数")
    private Integer ys;


    @ApiModelProperty(value = "附件类型")
    private String fjlx;

    @ApiModelProperty(value = "base64附件")
    private String base64fj;

    @ApiModelProperty(value = "base64附件-互联网传参使用")
    private String bytes;

    @ApiModelProperty(value = "多个base64附件")
    private List<String> base64fjList;

    public String getBytes() {
        return bytes;
    }

    public void setBytes(String bytes) {
        this.bytes = bytes;
    }

    public String getFjlx() {
        return fjlx;
    }

    public void setFjlx(String fjlx) {
        this.fjlx = fjlx;
    }

    public String getFjmc() {
        return fjmc;
    }

    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

    public Integer getFs() {
        return fs;
    }

    public void setFs(Integer fs) {
        this.fs = fs;
    }

    public Integer getYs() {
        return ys;
    }

    public void setYs(Integer ys) {
        this.ys = ys;
    }

    public String getBase64fj() {
        return base64fj;
    }

    public void setBase64fj(String base64fj) {
        this.base64fj = base64fj;
    }

    public List<String> getBase64fjList() {
        return base64fjList;
    }

    public void setBase64fjList(List<String> base64fjList) {
        this.base64fjList = base64fjList;
    }

    @Override
    public String toString() {
        return "BdcDsfSjclDTO{" +
                "xh=" + xh +
                ", fjmc='" + fjmc + '\'' +
                ", clmc='" + clmc + '\'' +
                ", fs=" + fs +
                ", ys=" + ys +
                ", fjlx='" + fjlx + '\'' +
                ", base64fj='" + base64fj + '\'' +
                ", bytes='" + bytes + '\'' +
                ", base64fjList=" + base64fjList +
                '}';
    }
}
