package cn.gtmap.realestate.common.core.dto.init;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/11/29
 * @description 复制并且替换业务信息参数
 */
public class BdcCopyReplaceYwxxDTO {

    @ApiModelProperty(value = "查询项目ID")
    private String queryXmid;

    @ApiModelProperty(value = "替换后的项目ID,不传则随机生成")
    private String xmid;

    @ApiModelProperty(value = "替换后的不动产单元号,不传则不替换")
    private String bdcdyh;

    @ApiModelProperty(value = "替换后的工作流实例ID,不传则不替换")
    private String gzlslid;

    @ApiModelProperty(value = "替换后的受理编号,不传则不替换")
    private String slbh;

    @ApiModelProperty(value = "是否复制登记薄数据")
    private boolean copyDjb;

    @ApiModelProperty(value = "是否生成证书数据")
    private boolean copyZs;


    public String getQueryXmid() {
        return queryXmid;
    }

    public void setQueryXmid(String queryXmid) {
        this.queryXmid = queryXmid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public boolean isCopyDjb() {
        return copyDjb;
    }

    public void setCopyDjb(boolean copyDjb) {
        this.copyDjb = copyDjb;
    }

    public boolean isCopyZs() {
        return copyZs;
    }

    public void setCopyZs(boolean copyZs) {
        this.copyZs = copyZs;
    }
}
