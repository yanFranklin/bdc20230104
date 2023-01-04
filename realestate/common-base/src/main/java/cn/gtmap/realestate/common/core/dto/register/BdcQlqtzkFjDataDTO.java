package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program: realestate
 * @description: 用于页面展现权力其他状况和附记内容
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-08-31 09:09
 **/
public class BdcQlqtzkFjDataDTO {
    @ApiModelProperty(value = "权力其他状况模板生成内容")
    private String qlqtzkmbnr;

    @ApiModelProperty(value = "附记模板生成内容")
    private String fjmbnr;

    @ApiModelProperty(value = "权力其他状况手动填写内容")
    private String qlqtzksdtx;

    @ApiModelProperty(value = "附记手动填写内容")
    private String fjsdtxnr;

    public String getQlqtzkmbnr() {
        return qlqtzkmbnr;
    }

    public void setQlqtzkmbnr(String qlqtzkmbnr) {
        this.qlqtzkmbnr = qlqtzkmbnr;
    }

    public String getFjmbnr() {
        return fjmbnr;
    }

    public void setFjmbnr(String fjmbnr) {
        this.fjmbnr = fjmbnr;
    }

    public String getQlqtzksdtx() {
        return qlqtzksdtx;
    }

    public void setQlqtzksdtx(String qlqtzksdtx) {
        this.qlqtzksdtx = qlqtzksdtx;
    }

    public String getFjsdtxnr() {
        return fjsdtxnr;
    }

    public void setFjsdtxnr(String fjsdtxnr) {
        this.fjsdtxnr = fjsdtxnr;
    }

    @Override
    public String toString() {
        return "BdcQlqtzkFjData{" +
                "qlqtzkmbnr='" + qlqtzkmbnr + '\'' +
                ", fjmbnr='" + fjmbnr + '\'' +
                ", qlqtzksdtx='" + qlqtzksdtx + '\'' +
                ", fjsdtxnr='" + fjsdtxnr + '\'' +
                '}';
    }
}
