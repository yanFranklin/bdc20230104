package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/07/14
 * @description 接收捷宇评价器回调上传文件
 */
public class JypjqDTO implements Serializable {
    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;

    @ApiModelProperty(value = "登记小类")
    private String djxl;

    @ApiModelProperty(value = "pdf类型")
    private Integer pdflx;

    @ApiModelProperty(value = "签字之后pdf的base64文件")
    private String pdfBase64;

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public Integer getPdflx() {
        return pdflx;
    }

    public void setPdflx(Integer pdflx) {
        this.pdflx = pdflx;
    }

    public String getPdfBase64() {
        return pdfBase64;
    }

    public void setPdfBase64(String pdfBase64) {
        this.pdfBase64 = pdfBase64;
    }

    @Override
    public String toString() {
        return "JypjqDTO{" +
                "gzlslid='" + gzlslid + '\'' +
                ", djxl='" + djxl + '\'' +
                ", pdflx=" + pdflx +
                ", pdfBase64='" + pdfBase64 + '\'' +
                '}';
    }
}
