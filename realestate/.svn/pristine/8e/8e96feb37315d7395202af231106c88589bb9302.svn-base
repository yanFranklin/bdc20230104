package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/8/28
 * @description 受理初始化信息DTO
 */
@ApiModel(value = "BdcSlCshDTO", description = "受理初始化信息DTO")
public class BdcSlCshDTO {

    @ApiModelProperty(value = "基本信息ID")
    private String jbxxid;
    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;
    @ApiModelProperty(value = "工作流定义名称")
    private String gzldymc;
    @ApiModelProperty(value = "登记小类")
    private String djxl;
    @ApiModelProperty(value = "承诺期限")
    private String cnqx;
    @ApiModelProperty(value = "特殊流程")
    private boolean tslc;
    @ApiModelProperty(value = "创建人ID")
    private String czrid;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "权利人")
    private String qlrmc;
    @ApiModelProperty(value = "受理项目列表")
    private List<BdcSlXmDO> bdcSlXmDOList;
    @ApiModelProperty(value = "查档类别")
    private String cdlb;
    @ApiModelProperty(value = "查档权利人")
    private String cdqlr;
    @ApiModelProperty(value = "查档权利人证件号")
    private String cdqlrZjh;
    @ApiModelProperty(value = "查档产权证号")
    private String cdcqzh;
    @ApiModelProperty(value = "查档坐落")
    private String cdzl;
    @ApiModelProperty(value = "合同编号")
    private String htbh;
    @ApiModelProperty(value = "修正来源 1-选择台账创建 2-流程创建 3-无数据创建")
    private Integer xzly;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "流程受理时间")
    private Date slsj;
    @ApiModelProperty("节点名称")
    private String jdmc;
    @ApiModelProperty("只登记不登簿流程")
    private boolean zdjbdb = false;

    // 获取整数型的承诺期限字段, 浮点数时四舍五入
    public Integer getIntegerCnqx(){
        if(StringUtils.isNotBlank(cnqx)){
            try{
                double cnqxDouble = new Double(cnqx).doubleValue();
                int cnqxInt = (int) cnqxDouble;
                if(cnqxDouble % cnqxInt == 0){ // 判断承诺期限是否为整数
                    return cnqxInt;
                }else{
                    String intValue = new DecimalFormat("0").format(cnqxDouble);
                    return Integer.parseInt(intValue);
                }
            }catch(Exception e){
                return null;
            }
        }
        return null;
    }

    public String getCnqx() {
        return cnqx;
    }

    public void setCnqx(String cnqx) {
        this.cnqx = cnqx;
    }

    public String getJbxxid() {
        return jbxxid;
    }

    public void setJbxxid(String jbxxid) {
        this.jbxxid = jbxxid;
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

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public boolean getTslc() {
        return tslc;
    }

    public void setTslc(boolean tslc) {
        this.tslc = tslc;
    }

    public String getCzrid() {
        return czrid;
    }

    public void setCzrid(String czrid) {
        this.czrid = czrid;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public List<BdcSlXmDO> getBdcSlXmDOList() {
        return bdcSlXmDOList;
    }

    public void setBdcSlXmDOList(List<BdcSlXmDO> bdcSlXmDOList) {
        this.bdcSlXmDOList = bdcSlXmDOList;
    }

    public String getCdlb() {
        return cdlb;
    }

    public void setCdlb(String cdlb) {
        this.cdlb = cdlb;
    }

    public String getCdqlr() {
        return cdqlr;
    }

    public void setCdqlr(String cdqlr) {
        this.cdqlr = cdqlr;
    }

    public String getCdcqzh() {
        return cdcqzh;
    }

    public void setCdcqzh(String cdcqzh) {
        this.cdcqzh = cdcqzh;
    }

    public String getCdzl() {
        return cdzl;
    }

    public void setCdzl(String cdzl) {
        this.cdzl = cdzl;
    }

    public String getCdqlrZjh() {
        return cdqlrZjh;
    }

    public void setCdqlrZjh(String cdqlrZjh) {
        this.cdqlrZjh = cdqlrZjh;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public Integer getXzly() {
        return xzly;
    }

    public void setXzly(Integer xzly) {
        this.xzly = xzly;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getGzldymc() {
        return gzldymc;
    }

    public void setGzldymc(String gzldymc) {
        this.gzldymc = gzldymc;
    }

    public Date getSlsj() {
        return slsj;
    }

    public void setSlsj(Date slsj) {
        this.slsj = slsj;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public boolean getZdjbdb() {
        return zdjbdb;
    }

    public void setZdjbdb(boolean zdjbdb) {
        this.zdjbdb = zdjbdb;
    }

    @Override
    public String toString() {
        return "BdcSlCshDTO{" +
                "jbxxid='" + jbxxid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", gzldymc='" + gzldymc + '\'' +
                ", djxl='" + djxl + '\'' +
                ", cnqx=" + cnqx +
                ", tslc=" + tslc +
                ", czrid='" + czrid + '\'' +
                ", zl='" + zl + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", bdcSlXmDOList=" + bdcSlXmDOList +
                ", cdlb='" + cdlb + '\'' +
                ", cdqlr='" + cdqlr + '\'' +
                ", cdqlrZjh='" + cdqlrZjh + '\'' +
                ", cdcqzh='" + cdcqzh + '\'' +
                ", cdzl='" + cdzl + '\'' +
                ", htbh='" + htbh + '\'' +
                ", xzly=" + xzly +
                ", qxdm='" + qxdm + '\'' +
                ", slsj=" + slsj +
                ", jdmc='" + jdmc + '\'' +
                ", zdjbdb=" + zdjbdb +
                '}';
    }
}
