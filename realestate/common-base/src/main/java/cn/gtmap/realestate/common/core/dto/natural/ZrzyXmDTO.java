package cn.gtmap.realestate.common.core.dto.natural;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 选择产权证返回DTO
 *
 * @author wyh
 * @version 1.0
 * @date 2021/10/26 10:50
 */
@Data
@EqualsAndHashCode
@ToString
@ApiModel(value = "选择产权证返回DTO", description = "选择产权证返回DTO")
public class ZrzyXmDTO {
    /**
     * 项目ID
     */
    @ApiModelProperty(value = "项目ID")
    private String xmid;

    /**
     * 受理编号
     */
    @ApiModelProperty(value = "受理编号")
    private String slbh;

    /**
     * 工作流实例ID
     */
    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    /**
     * 自然资源登记单元号
     */
    @ApiModelProperty(value = "自然资源登记单元号")
    private String zrzydjdyh;

    /**
     * 坐落
     */
    @ApiModelProperty(value = "坐落")
    private String zl;

    /**
     * 登记类型
     */
    @ApiModelProperty(value = "登记类型")
    private Short djlx;

    /**
     * 登记原因
     */
    @ApiModelProperty(value = "登记原因")
    private String djyy;

    /**
     * 案件状态
     */
    @ApiModelProperty(value = "案件状态")
    private Short ajzt;

    /**
     * 权属状态
     */
    @ApiModelProperty(value = "权属状态")
    private Short qszt;

    /**
     * 区县代码
     */
    @ApiModelProperty(value = "区县代码")
    private String qxdm;

    /**
     * 受理时间
     */
    @ApiModelProperty(value = "受理时间")
    private Date slsj;

    /**
     * 受理人ID
     */
    @ApiModelProperty(value = "受理人ID")
    private String slrid;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private Date jssj;

    /**
     * 登记机构
     */
    @ApiModelProperty(value = "登记机构")
    private String djjg;

    /**
     * 登记时间
     */
    @ApiModelProperty(value = "登记时间")
    private Date djsj;

    /**
     * 登簿人
     */
    @ApiModelProperty(value = "登簿人")
    private String dbr;

    /**
     * 自然资源产权证号
     */
    @ApiModelProperty(value = "自然资源产权证号")
    private String zrzycqzh;

    /**
     * 原产权证号
     */
    @ApiModelProperty(value = "原产权证号")
    private String ycqzh;

    /**
     * 工作流定义ID
     */
    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;

    /**
     * 工作流定义名称
     */
    @ApiModelProperty(value = "工作流定义名称")
    private String gzldymc;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;

    /*******************************/
    /**
     * 项目ID
     */

    /**
     * 登记单元名称
     */
    @ApiModelProperty(value = "登记单元名称")
    private String djdymc;

    /**
     * 登记单元类型
     */
    @ApiModelProperty(value = "登记单元类型")
    private String djdylx;

    /**
     * 登记单元总面积
     */
    @ApiModelProperty(value = "登记单元总面积")
    private BigDecimal djdyzmj;

    /**
     * 国有面积
     */
    @ApiModelProperty(value = "国有面积")
    private BigDecimal gymj;

    /**
     * 集体面积
     */
    @ApiModelProperty(value = "集体面积")
    private BigDecimal jtmj;

    /**
     * 争议区面积
     */
    @ApiModelProperty(value = "争议区面积")
    private BigDecimal zyqmj;

    /**
     * 单元四至东
     */
    @ApiModelProperty(value = "单元四至东")
    private String dyszd;

    /**
     * 单元四至南
     */
    @ApiModelProperty(value = "单元四至南")
    private String dyszn;

    /**
     * 单元四至西
     */
    @ApiModelProperty(value = "单元四至西")
    private String dyszx;

    /**
     * 单元四至北
     */
    @ApiModelProperty(value = "单元四至北")
    private String dyszb;

    /**
     * 所有权主体
     */
    @ApiModelProperty(value = "所有权主体")
    private String syqzt;

    /**
     * 代表行使主体
     */
    @ApiModelProperty(value = "代表行使主体")
    private String dbxszt;

    /**
     * 权利行使方式
     */
    @ApiModelProperty(value = "权利行使方式")
    private String qlxsfs;

    /**
     * 代理行使主体
     */
    @ApiModelProperty(value = "代理行使主体")
    private String dlxszt;

    /**
     * 行使内容
     */
    @ApiModelProperty(value = "行使内容")
    private String xsnr;

    /**
     * 单元内自然资源总面积
     */
    @ApiModelProperty(value = "单元内自然资源总面积")
    private BigDecimal dynzrzyzmj;

    /**
     * 水流面积
     */
    @ApiModelProperty(value = "水流面积")
    private BigDecimal szymj;

    /**
     * 湿地面积
     */
    @ApiModelProperty(value = "湿地面积")
    private BigDecimal sdmj;

    /**
     * 草原面积
     */
    @ApiModelProperty(value = "草原面积")
    private BigDecimal cymj;

    /**
     * 森林面积
     */
    @ApiModelProperty(value = "森林面积")
    private BigDecimal slmj;

    /**
     * 荒地面积
     */
    @ApiModelProperty(value = "荒地面积")
    private BigDecimal hdmj;

    /**
     * 其他面积
     */
    @ApiModelProperty(value = "其他面积")
    private BigDecimal qtmj;

    /**
     * 非自然资源总面积
     */
    @ApiModelProperty(value = "非自然资源总面积")
    private BigDecimal fzrzyzmj;

    /**
     * 附记
     */
    @ApiModelProperty(value = "附记")
    private String fj;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getZrzydjdyh() {
        return zrzydjdyh;
    }

    public void setZrzydjdyh(String zrzydjdyh) {
        this.zrzydjdyh = zrzydjdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Short getDjlx() {
        return djlx;
    }

    public void setDjlx(Short djlx) {
        this.djlx = djlx;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public Short getAjzt() {
        return ajzt;
    }

    public void setAjzt(Short ajzt) {
        this.ajzt = ajzt;
    }

    public Short getQszt() {
        return qszt;
    }

    public void setQszt(Short qszt) {
        this.qszt = qszt;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public Date getSlsj() {
        return slsj;
    }

    public void setSlsj(Date slsj) {
        this.slsj = slsj;
    }

    public String getSlrid() {
        return slrid;
    }

    public void setSlrid(String slrid) {
        this.slrid = slrid;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public String getZrzycqzh() {
        return zrzycqzh;
    }

    public void setZrzycqzh(String zrzycqzh) {
        this.zrzycqzh = zrzycqzh;
    }

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getGzldymc() {
        return gzldymc;
    }

    public void setGzldymc(String gzldymc) {
        this.gzldymc = gzldymc;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getDjdymc() {
        return djdymc;
    }

    public void setDjdymc(String djdymc) {
        this.djdymc = djdymc;
    }

    public String getDjdylx() {
        return djdylx;
    }

    public void setDjdylx(String djdylx) {
        this.djdylx = djdylx;
    }

    public BigDecimal getDjdyzmj() {
        return djdyzmj;
    }

    public void setDjdyzmj(BigDecimal djdyzmj) {
        this.djdyzmj = djdyzmj;
    }

    public BigDecimal getGymj() {
        return gymj;
    }

    public void setGymj(BigDecimal gymj) {
        this.gymj = gymj;
    }

    public BigDecimal getJtmj() {
        return jtmj;
    }

    public void setJtmj(BigDecimal jtmj) {
        this.jtmj = jtmj;
    }

    public BigDecimal getZyqmj() {
        return zyqmj;
    }

    public void setZyqmj(BigDecimal zyqmj) {
        this.zyqmj = zyqmj;
    }

    public String getDyszd() {
        return dyszd;
    }

    public void setDyszd(String dyszd) {
        this.dyszd = dyszd;
    }

    public String getDyszn() {
        return dyszn;
    }

    public void setDyszn(String dyszn) {
        this.dyszn = dyszn;
    }

    public String getDyszx() {
        return dyszx;
    }

    public void setDyszx(String dyszx) {
        this.dyszx = dyszx;
    }

    public String getDyszb() {
        return dyszb;
    }

    public void setDyszb(String dyszb) {
        this.dyszb = dyszb;
    }

    public String getSyqzt() {
        return syqzt;
    }

    public void setSyqzt(String syqzt) {
        this.syqzt = syqzt;
    }

    public String getDbxszt() {
        return dbxszt;
    }

    public void setDbxszt(String dbxszt) {
        this.dbxszt = dbxszt;
    }

    public String getQlxsfs() {
        return qlxsfs;
    }

    public void setQlxsfs(String qlxsfs) {
        this.qlxsfs = qlxsfs;
    }

    public String getDlxszt() {
        return dlxszt;
    }

    public void setDlxszt(String dlxszt) {
        this.dlxszt = dlxszt;
    }

    public String getXsnr() {
        return xsnr;
    }

    public void setXsnr(String xsnr) {
        this.xsnr = xsnr;
    }

    public BigDecimal getDynzrzyzmj() {
        return dynzrzyzmj;
    }

    public void setDynzrzyzmj(BigDecimal dynzrzyzmj) {
        this.dynzrzyzmj = dynzrzyzmj;
    }

    public BigDecimal getSzymj() {
        return szymj;
    }

    public void setSzymj(BigDecimal szymj) {
        this.szymj = szymj;
    }

    public BigDecimal getSdmj() {
        return sdmj;
    }

    public void setSdmj(BigDecimal sdmj) {
        this.sdmj = sdmj;
    }

    public BigDecimal getCymj() {
        return cymj;
    }

    public void setCymj(BigDecimal cymj) {
        this.cymj = cymj;
    }

    public BigDecimal getSlmj() {
        return slmj;
    }

    public void setSlmj(BigDecimal slmj) {
        this.slmj = slmj;
    }

    public BigDecimal getHdmj() {
        return hdmj;
    }

    public void setHdmj(BigDecimal hdmj) {
        this.hdmj = hdmj;
    }

    public BigDecimal getQtmj() {
        return qtmj;
    }

    public void setQtmj(BigDecimal qtmj) {
        this.qtmj = qtmj;
    }

    public BigDecimal getFzrzyzmj() {
        return fzrzyzmj;
    }

    public void setFzrzyzmj(BigDecimal fzrzyzmj) {
        this.fzrzyzmj = fzrzyzmj;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }
}
