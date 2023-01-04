package cn.gtmap.realestate.common.core.domain.exchange;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import org.dozer.Mapping;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-02-24
 * @description 业务系统第三方系统交互日志
 */
@Table(name = "BDC_DSF_RZ")
public class BdcDsfRzDO {

    @Id
    @ApiModelProperty(value = "日志ID")
    private String rzid;

    @ApiModelProperty(value = "接口名称")
    @Mapping("viewTypeName")
    private String jkmc;

    @ApiModelProperty(value = "接口地址")
    private String dsfdz;

    @ApiModelProperty(value = "不动产地址")
    private String bdcdz;

    @ApiModelProperty(value = "共享部门标志")
    @Mapping("dsfFlag")
    private String gxbmbz;

    @ApiModelProperty(value = "请求时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date qqsj;

    @ApiModelProperty(value = "操作人登录名")
    private String czr;

    /*
     *  别称（中文姓名）
     * */
    @ApiModelProperty(value = "别称（中文姓名）")
    private String alias;

    /*
     *  区县代码
     * */
    @ApiModelProperty(value = "区县代码")
    private String qxdm;


    @ApiModelProperty(value = "请求参数")
    @Mapping("request")
    private String qqcs;

    @ApiModelProperty(value = "响应结果")
    @Mapping("response")
    private String xyjg;

    @ApiModelProperty(value = "异常信息")
    private String ycxx;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "请求方")
    @Mapping("requestr")
    private String qqf;

    @ApiModelProperty(value = "响应方")
    @Mapping("responser")
    private String xyf;

    @ApiModelProperty(value = "接口用时")
    @Mapping("useTime")
    private Long jkys;

    public String getRzid() {
        return rzid;
    }

    public void setRzid(String rzid) {
        this.rzid = rzid;
    }

    public String getJkmc() {
        return jkmc;
    }

    public void setJkmc(String jkmc) {
        this.jkmc = jkmc;
    }

    public String getDsfdz() {
        return dsfdz;
    }

    public void setDsfdz(String dsfdz) {
        this.dsfdz = dsfdz;
    }

    public String getBdcdz() {
        return bdcdz;
    }

    public void setBdcdz(String bdcdz) {
        this.bdcdz = bdcdz;
    }

    public String getGxbmbz() {
        return gxbmbz;
    }

    public void setGxbmbz(String gxbmbz) {
        this.gxbmbz = gxbmbz;
    }

    public Date getQqsj() {
        return qqsj;
    }

    public void setQqsj(Date qqsj) {
        this.qqsj = qqsj;
    }

    public String getQqcs() {
        return qqcs;
    }

    public void setQqcs(String qqcs) {
        this.qqcs = qqcs;
    }

    public String getXyjg() {
        return xyjg;
    }

    public void setXyjg(String xyjg) {
        this.xyjg = xyjg;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQqf() {
        return qqf;
    }

    public void setQqf(String qqf) {
        this.qqf = qqf;
    }

    public String getXyf() {
        return xyf;
    }

    public void setXyf(String xyf) {
        this.xyf = xyf;
    }

    public String getYcxx() {
        return ycxx;
    }

    public void setYcxx(String ycxx) {
        this.ycxx = ycxx;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public Long getJkys() {
        return jkys;
    }

    public void setJkys(Long jkys) {
        this.jkys = jkys;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }
}
