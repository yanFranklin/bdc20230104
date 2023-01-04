package cn.gtmap.realestate.common.core.dto.certificate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/27
 * @description 发证记录所需的证书信息
 */
@ApiModel(value = "BdcFzjlZsDTO", description = "发证记录所需的证书信息")
public class BdcFzjlZsDTO {
    @ApiModelProperty(value = "不动产权证", hidden = true)
    private String bdcqzh;

    @ApiModelProperty(value = "权证印刷序列号", hidden = true)
    private String qzysxlh;

    @ApiModelProperty(value = "持证权利人", hidden = true)
    private String qlr;

    @ApiModelProperty(value = "领证人", required = true)
    private String lzr;

    @ApiModelProperty(value = "领证人证件号", required = true)
    private String lzrzjh;

    @ApiModelProperty(value = "领证人电话", required = true)
    private String lzrdh;

    @ApiModelProperty(value = "领证人证件种类", required = true)
    private Integer lzrzjzl;

    private Date lzsj;

    @ApiModelProperty(value = "证书idList")
    List<String> zsidList;

    @ApiModelProperty(value = "工作流实例ID集合,用于批量更新证书领证人信息")
    List<String> gzlslidList;

    public Date getLzsj() {
        return lzsj;
    }

    public void setLzsj(Date lzsj) {
        this.lzsj = lzsj;
    }
    public Integer getLzrzjzl() {
        return lzrzjzl;
    }

    public void setLzrzjzl(Integer lzrzjzl) {
        this.lzrzjzl = lzrzjzl;
    }

    public String getLzrdh() {
        return lzrdh;
    }

    public void setLzrdh(String lzrdh) {
        this.lzrdh = lzrdh;
    }
    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getQzysxlh() {
        return qzysxlh;
    }

    public void setQzysxlh(String qzysxlh) {
        this.qzysxlh = qzysxlh;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getLzr() {
        return lzr;
    }

    public void setLzr(String lzr) {
        this.lzr = lzr;
    }

    public String getLzrzjh() {
        return lzrzjh;
    }

    public void setLzrzjh(String lzrzjh) {
        this.lzrzjh = lzrzjh;
    }

    public List<String> getZsidList() {
        return zsidList;
    }

    public void setZsidList(List<String> zsidList) {
        this.zsidList = zsidList;
    }

    public List<String> getGzlslidList() {
        return gzlslidList;
    }

    public void setGzlslidList(List<String> gzlslidList) {
        this.gzlslidList = gzlslidList;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcFzjlZsDTO.class.getSimpleName() + "[", "]")
                .add("bdcqzh='" + bdcqzh + "'")
                .add("qzysxlh='" + qzysxlh + "'")
                .add("qlr='" + qlr + "'")
                .add("lzr='" + lzr + "'")
                .add("lzrzjh='" + lzrzjh + "'")
                .add("lzrdh='" + lzrdh + "'")
                .add("lzrzjzl=" + lzrzjzl)
                .add("lzsj=" + lzsj)
                .add("zsidList=" + zsidList)
                .add("gzlslidList=" + gzlslidList)
                .toString();
    }
}
