package cn.gtmap.realestate.exchange.core.dto.wwsq.init.cfjfdj;

import cn.gtmap.realestate.exchange.core.dto.wwsq.init.InitFjxxDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.dydj.InitRequestQlr;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/12/10 09:01
 */
public class InitCfjfdjRquestDataDTO {

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "办件编号")
    private String bjbh;

    @ApiModelProperty(value = "不动产权证号 选择产权证做查封和解封时必填,解封时为查封对应的查封文号")
    private String bdcqzh;

    @ApiModelProperty(value = "查封信息")
    private InitCfjfdjRquestDataCfxxDTO cfxx;

    @ApiModelProperty(value = "附件信息")
    private List<InitFjxxDTO> fjxx;

    @ApiModelProperty(value = "权利人信息")
    private List<InitCfjfdjRquestDataQlrDTO> qlr;

    @ApiModelProperty(value = "申请登记类型 登记3.0流程工作流定义ID")
    private String sqdjlx;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "证书项目ID 选择证书做查封解封时必填，解封时为查封对应的xmid")
    private String zsxmid;

    @ApiModelProperty(value = "互联网+非银行：3，互联网+银行：6 法院子系统：9")
    private String slly;

    @ApiModelProperty(value = "受理人 用户名")
    private String slr;

    @ApiModelProperty(value = "权利类型 98")
    private String qllx;

    @ApiModelProperty(value = "区县代码")
    private String qxdm;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBjbh() {
        return bjbh;
    }

    public void setBjbh(String bjbh) {
        this.bjbh = bjbh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public InitCfjfdjRquestDataCfxxDTO getCfxx() {
        return cfxx;
    }

    public void setCfxx(InitCfjfdjRquestDataCfxxDTO cfxx) {
        this.cfxx = cfxx;
    }

    public List<InitFjxxDTO> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<InitFjxxDTO> fjxx) {
        this.fjxx = fjxx;
    }

    public List<InitCfjfdjRquestDataQlrDTO> getQlr() {
        return qlr;
    }

    public void setQlr(List<InitCfjfdjRquestDataQlrDTO> qlr) {
        this.qlr = qlr;
    }

    public String getSqdjlx() {
        return sqdjlx;
    }

    public void setSqdjlx(String sqdjlx) {
        this.sqdjlx = sqdjlx;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getZsxmid() {
        return zsxmid;
    }

    public void setZsxmid(String zsxmid) {
        this.zsxmid = zsxmid;
    }

    public String getSlly() {
        return slly;
    }

    public void setSlly(String slly) {
        this.slly = slly;
    }

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }
}
