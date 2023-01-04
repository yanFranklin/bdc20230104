package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/5/27
 * @description 不动产收费收税订单信息
 */
@Table(
        name = "BDC_SL_SFSS_DDXX"
)
@ApiModel(value = "BdcSlSfssDdxxDO",description = "不动产收费收税订单信息")
public class BdcSlSfssDdxxDO {

    @Id
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "订单编号（一卡清订单编号）")
    private String ddbh;

    @ApiModelProperty(value = "订单金额")
    private Double ddje;

    @ApiModelProperty(value = "总额")
    private Double ze;

    @ApiModelProperty(value = "税费关联ID，类型为5时值为项目ID")
    private String sfglid;

    @ApiModelProperty(value = "税费关联ID类型")
    private Integer sfglidlx;

    @ApiModelProperty(value = "缴费URL")
    private String jfurl;

    @ApiModelProperty(value = "支付状态")
    private Integer jfzt;

    @ApiModelProperty(value = "订单状态")
    private Integer ddzt;

    @ApiModelProperty(value = "终端号")
    private String zdh;

    @ApiModelProperty(value = "参考号")
    private String ckh;

    @ApiModelProperty(value = "商户代码")
    private String shdm;

    @ApiModelProperty(value = "退款单号")
    private String tkdh;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value= "权利人类别")
    private String qlrlb;

    @ApiModelProperty(value= "第三方订单编号(缴费书编码）")
    private String dsfddbh;

    @ApiModelProperty(value= "订单生成时间")
    private Date ddscsj;

    @ApiModelProperty(value= "月结单号")
    private String yjdh;

    @ApiModelProperty(value = "缴款码")
    private String jkm;

    @ApiModelProperty(value = "订单类型： 0：税费统缴订单，1；纳税订单， 2： 收费订单")
    private Integer ddlx;

    @ApiModelProperty(value = "合一支付缴费途径1.商业银行 2. 合一支付")
    private Integer hyzfjftj;

    public String getJkm() {
        return jkm;
    }

    public void setJkm(String jkm) {
        this.jkm = jkm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDdbh() {
        return ddbh;
    }

    public void setDdbh(String ddbh) {
        this.ddbh = ddbh;
    }

    public Double getDdje() {
        return ddje;
    }

    public void setDdje(Double ddje) {
        this.ddje = ddje;
    }

    public Double getZe() {
        return ze;
    }

    public void setZe(Double ze) {
        this.ze = ze;
    }

    public String getSfglid() {
        return sfglid;
    }

    public void setSfglid(String sfglid) {
        this.sfglid = sfglid;
    }

    public Integer getSfglidlx() {
        return sfglidlx;
    }

    public void setSfglidlx(Integer sfglidlx) {
        this.sfglidlx = sfglidlx;
    }

    public String getJfurl() {
        return jfurl;
    }

    public void setJfurl(String jfurl) {
        this.jfurl = jfurl;
    }

    public Integer getJfzt() {
        return jfzt;
    }

    public void setJfzt(Integer jfzt) {
        this.jfzt = jfzt;
    }

    public String getZdh() {
        return zdh;
    }

    public void setZdh(String zdh) {
        this.zdh = zdh;
    }

    public String getCkh() {
        return ckh;
    }

    public void setCkh(String ckh) {
        this.ckh = ckh;
    }

    public String getShdm() {
        return shdm;
    }

    public void setShdm(String shdm) {
        this.shdm = shdm;
    }

    public String getTkdh() {
        return tkdh;
    }

    public void setTkdh(String tkdh) {
        this.tkdh = tkdh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getDsfddbh() {
        return dsfddbh;
    }

    public void setDsfddbh(String dsfddbh) {
        this.dsfddbh = dsfddbh;
    }

    public Date getDdscsj() {
        return ddscsj;
    }

    public void setDdscsj(Date ddscsj) {
        this.ddscsj = ddscsj;
    }

    public String getYjdh() {
        return yjdh;
    }

    public void setYjdh(String yjdh) {
        this.yjdh = yjdh;
    }

    public Integer getDdzt() {
        return ddzt;
    }

    public void setDdzt(Integer ddzt) {
        this.ddzt = ddzt;
    }

    public Integer getDdlx() {
        return ddlx;
    }

    public void setDdlx(Integer ddlx) {
        this.ddlx = ddlx;
    }

    public Integer getHyzfjftj() {
        return hyzfjftj;
    }

    public void setHyzfjftj(Integer hyzfjftj) {
        this.hyzfjftj = hyzfjftj;
    }

    @Override
    public String toString() {
        return "BdcSlSfssDdxxDO{" +
                "id='" + id + '\'' +
                ", ddbh='" + ddbh + '\'' +
                ", ddje=" + ddje +
                ", ze=" + ze +
                ", sfglid='" + sfglid + '\'' +
                ", sfglidlx=" + sfglidlx +
                ", jfurl='" + jfurl + '\'' +
                ", jfzt=" + jfzt +
                ", ddzt=" + ddzt +
                ", zdh='" + zdh + '\'' +
                ", ckh='" + ckh + '\'' +
                ", shdm='" + shdm + '\'' +
                ", tkdh='" + tkdh + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", qlrlb='" + qlrlb + '\'' +
                ", dsfddbh='" + dsfddbh + '\'' +
                ", ddscsj=" + ddscsj +
                ", yjdh='" + yjdh + '\'' +
                ", jkm='" + jkm + '\'' +
                ", ddlx=" + ddlx +
                ", hyzfjftj=" + hyzfjftj +
                '}';
    }

    public static class Builder{
        private BdcSlSfssDdxxDO bdcSlSfssDdxxDO = new BdcSlSfssDdxxDO();
        public Builder(Double ddje, Double ze, String gzlslid){
            bdcSlSfssDdxxDO.ddje = ddje;
            bdcSlSfssDdxxDO.ze = ze;
            bdcSlSfssDdxxDO.gzlslid = gzlslid;
        }
        public Builder sfglid(String sfglid){
            bdcSlSfssDdxxDO.sfglid = sfglid;
            return this;
        }

        public Builder sfglidlx(int sfglidlx){
            bdcSlSfssDdxxDO.sfglidlx = sfglidlx;
            return this;
        }

        public Builder id(String id){
            bdcSlSfssDdxxDO.id = id;
            return this;
        }

        public Builder qlrlb(String qlrlb){
            bdcSlSfssDdxxDO.qlrlb = qlrlb;
            return this;
        }

        public BdcSlSfssDdxxDO build(){
            return bdcSlSfssDdxxDO;
        }
    }

}
