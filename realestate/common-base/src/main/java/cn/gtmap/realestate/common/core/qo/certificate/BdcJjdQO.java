package cn.gtmap.realestate.common.core.qo.certificate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * 交接单查询QO
 *
 * @author <a href="mailto:lixin1@gtmap.cn>lixin</a>"
 * @version 1.0, 2019/8/27
 */
@ApiModel(value = "BdcJjdQO", description = "交接单查询QO")
public class BdcJjdQO {
    @ApiModelProperty(value = "ID")
    private String jjdid;
    @ApiModelProperty(value = "交接单编号")
    private String jjdh;
    @ApiModelProperty(value = "移交时间")
    private Date yjsj;
    @ApiModelProperty(value = "查询起始时间")
    private Date qssj;
    @ApiModelProperty(value = "查询截止时间")
    private Date jzsj;
    @ApiModelProperty(value = "转发人ID")
    private String zfrid;
    @ApiModelProperty(value = "转发人")
    private String zfr;
    @ApiModelProperty(value = "转发科室")
    private String zfks;
    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "交接单类型")
    private String jjdlx;
    @ApiModelProperty(value = "交接单状态")
    private String jjdzt;
    @ApiModelProperty(value = "案件状态")
    private String ajzt;
    @ApiModelProperty(value = "接收科室")
    private String jsks;
    @ApiModelProperty(value = "接收人")
    private String jsr;
    @ApiModelProperty(value = "接收人 ID")
    private String jsrid;
    @ApiModelProperty(value = "项目ID集合")
    private List<String> listXmid;
    @ApiModelProperty(value = "工作流ID集合")
    private List<String> listGzlslid;

    @ApiModelProperty(value = "档案交接流程实例id")
    private String dajjGzlslid;

    @ApiModelProperty(value = "排序字段")
    private String field;

    @ApiModelProperty(value = "排序类型")
    private String order;


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<String> getListGzlslid() {
        return listGzlslid;
    }

    public void setListGzlslid(List<String> listGzlslid) {
        this.listGzlslid = listGzlslid;
    }

    public String getJsr() {
        return jsr;
    }

    public void setJsr(String jsr) {
        this.jsr = jsr;
    }

    public String getJsrid() {
        return jsrid;
    }

    public void setJsrid(String jsrid) {
        this.jsrid = jsrid;
    }

    public String getJsks() {
        return jsks;
    }

    public void setJsks(String jsks) {
        this.jsks = jsks;
    }

    public String getJjdzt() {
        return jjdzt;
    }

    public String getAjzt() {
        return ajzt;
    }

    public void setAjzt(String ajzt) {
        this.ajzt = ajzt;
    }

    public void setJjdzt(String jjdzt) {
        this.jjdzt = jjdzt;
    }

    public String getJjdlx() {
        return jjdlx;
    }

    public void setJjdlx(String jjdlx) {
        this.jjdlx = jjdlx;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Date getYjsj() {
        return yjsj;
    }

    public void setYjsj(Date yjsj) {
        this.yjsj = yjsj;
    }

    public String getJjdh() {
        return jjdh;
    }

    public void setJjdh(String jjdh) {
        this.jjdh = jjdh;
    }

    public String getZfr() {
        return zfr;
    }

    public void setZfr(String zfr) {
        this.zfr = zfr;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getSlbh() {
        return slbh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public List<String> getListXmid() {
        return listXmid;
    }

    public void setListXmid(List<String> listXmid) {
        this.listXmid = listXmid;
    }

    public Date getQssj() {
        return qssj;
    }

    public void setQssj(Date qssj) {
        this.qssj = qssj;
    }

    public Date getJzsj() {
        return jzsj;
    }

    public void setJzsj(Date jzsj) {
        this.jzsj = jzsj;
    }

    public String getZfrid() {
        return zfrid;
    }

    public void setZfrid(String zfrid) {
        this.zfrid = zfrid;
    }

    public String getZfks() {
        return zfks;
    }

    public void setZfks(String zfks) {
        this.zfks = zfks;
    }

    public String getJjdid() {
        return jjdid;
    }

    public void setJjdid(String jjdid) {
        this.jjdid = jjdid;
    }

    public String getDajjGzlslid() {
        return dajjGzlslid;
    }

    public void setDajjGzlslid(String dajjGzlslid) {
        this.dajjGzlslid = dajjGzlslid;
    }

    @Override
    public String toString() {
        return "BdcJjdQO{" +
                "jjdid='" + jjdid + '\'' +
                ", jjdh='" + jjdh + '\'' +
                ", yjsj=" + yjsj +
                ", qssj=" + qssj +
                ", jzsj=" + jzsj +
                ", zfrid='" + zfrid + '\'' +
                ", zfr='" + zfr + '\'' +
                ", zfks='" + zfks + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", slbh='" + slbh + '\'' +
                ", zl='" + zl + '\'' +
                ", xmid='" + xmid + '\'' +
                ", jjdlx='" + jjdlx + '\'' +
                ", jjdzt='" + jjdzt + '\'' +
                ", ajzt='" + ajzt + '\'' +
                ", jsks='" + jsks + '\'' +
                ", jsr='" + jsr + '\'' +
                ", jsrid='" + jsrid + '\'' +
                ", listXmid=" + listXmid +
                ", listGzlslid=" + listGzlslid +
                ", dajjGzlslid='" + dajjGzlslid + '\'' +
                '}';
    }
}
