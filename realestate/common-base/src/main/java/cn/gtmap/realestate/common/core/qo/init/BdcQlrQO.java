package cn.gtmap.realestate.common.core.qo.init;

import cn.gtmap.realestate.common.core.domain.BdcJtcyDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/10
 * @description 不动产权利人查询参数封装对象
 */
@ApiModel(value = "BdcQlrQO",description = "不动产权利人查询参数封装对象")
public class BdcQlrQO {
    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    @ApiModelProperty(value = "权利人证件号")
    private String zjh;

    @ApiModelProperty(value = "权利人类别")
    private String qlrlb;

    @ApiModelProperty(value = "权利人id")
    private String qlrid;

    @ApiModelProperty(value = "代理人名称")
    private String dlrmc;

    @ApiModelProperty(value = "代理人证件号")
    private String dlrzjh;

    @ApiModelProperty(value = "代理人证件种类")
    private String dlrzjzl;

    @ApiModelProperty(value = "代理人证件种类")
    private String zsid;

    @ApiModelProperty(value = "gzlslid")
    private String gzlslid;

    @ApiModelProperty(value = "权利人名称集合")
    private List<String> qlrmcList;

    @ApiModelProperty(value = "权利人证件号集合")
    private List<String> zjhList;

    @ApiModelProperty(value = "查询模糊类型 0:精确,1:模糊")
    private String mhlx;

    @ApiModelProperty(value = "经办人")
    private String jbr;

    @ApiModelProperty(value = "家庭成员")
    private List<BdcJtcyDO> jtcyDOList;

    @ApiModelProperty(value = "需打印的选中数据")
    private List<BdcZfxxDTO> zfxxDTOList;

    @ApiModelProperty(value = "当前登录用户的部门名称")
    private String bmmc;

    @ApiModelProperty(value = "模块编码,用于角色过滤")
    private String moduleCode;

    /**
     * 淮安有房无房采用勾选打印方式，但是提供给互联网接口需要根据权利人查询打印，为控制查询数据方式添加此参数
     * 设置true：只根据权利人查询房产信息，即使sfdyxzsj设置true也是只根据权利人查询
     * 设置false或空：sfdyxzsj设置true则根据选择数据打印，否则根据权利人查询打印
     */
    @ApiModelProperty(value = "查询打印数据")
    private Boolean cxdysj;

    @ApiModelProperty(value = "区县代码")
    private String qxdm;

    @ApiModelProperty(value = "区县代码集合")
    private List<String> qxdmList;

    /**
     * 目前支持: cf 查封 dya 抵押  yg 预告  yy 异议
     * 例如 cf,dya 代表查询是否存在查封、抵押
     */
    @ApiModelProperty(value = "限制状态查询")
    private String xzztcx;

    @ApiModelProperty(value = "是否有异地查询角色")
    private Boolean ydcxjs;

    @ApiModelProperty(value = "与申请人关系")
    private String ysqrgx;

    public Boolean getYdcxjs() {
        return ydcxjs;
    }

    public void setYdcxjs(Boolean ydcxjs) {
        this.ydcxjs = ydcxjs;
    }

    public String getXzztcx() {
        return xzztcx;
    }

    public void setXzztcx(String xzztcx) {
        this.xzztcx = xzztcx;
    }

    public List<String> getQxdmList() {
        return qxdmList;
    }

    public void setQxdmList(List<String> qxdmList) {
        this.qxdmList = qxdmList;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public Boolean getCxdysj() {
        return cxdysj;
    }

    public void setCxdysj(Boolean cxdysj) {
        this.cxdysj = cxdysj;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getDlrmc() {
        return dlrmc;
    }

    public void setDlrmc(String dlrmc) {
        this.dlrmc = dlrmc;
    }

    public List<BdcJtcyDO> getJtcyDOList() {
        return jtcyDOList;
    }

    public void setJtcyDOList(List<BdcJtcyDO> jtcyDOList) {
        this.jtcyDOList = jtcyDOList;
    }

    public String getDlrzjh() {
        return dlrzjh;
    }

    public void setDlrzjh(String dlrzjh) {
        this.dlrzjh = dlrzjh;
    }

    public String getDlrzjzl() {
        return dlrzjzl;
    }

    public void setDlrzjzl(String dlrzjzl) {
        this.dlrzjzl = dlrzjzl;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getQlrid() {
        return qlrid;
    }

    public void setQlrid(String qlrid) {
        this.qlrid = qlrid;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public List<String> getZjhList() {
        return zjhList;
    }

    public void setZjhList(List<String> zjhList) {
        this.zjhList = zjhList;
    }

    public String getMhlx() {
        return mhlx;
    }

    public void setMhlx(String mhlx) {
        this.mhlx = mhlx;
    }

    public String getJbr() {
        return jbr;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public List<BdcZfxxDTO> getZfxxDTOList() {
        return zfxxDTOList;
    }

    public void setZfxxDTOList(List<BdcZfxxDTO> zfxxDTOList) {
        this.zfxxDTOList = zfxxDTOList;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public List<String> getQlrmcList() {
        return qlrmcList;
    }

    public void setQlrmcList(List<String> qlrmcList) {
        this.qlrmcList = qlrmcList;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getYsqrgx() {
        return ysqrgx;
    }

    public void setYsqrgx(String ysqrgx) {
        this.ysqrgx = ysqrgx;
    }

    @Override
    public String toString() {
        return "BdcQlrQO{" +
                "xmid='" + xmid + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", zjh='" + zjh + '\'' +
                ", qlrlb='" + qlrlb + '\'' +
                ", qlrid='" + qlrid + '\'' +
                ", dlrmc='" + dlrmc + '\'' +
                ", dlrzjh='" + dlrzjh + '\'' +
                ", dlrzjzl='" + dlrzjzl + '\'' +
                ", zsid='" + zsid + '\'' +
                ", zjhList=" + zjhList +
                ", mhlx='" + mhlx + '\'' +
                ", jbr='" + jbr + '\'' +
                ", jtcyDOList=" + jtcyDOList +
                ", zfxxDTOList=" + zfxxDTOList +
                ", bmmc='" + bmmc + '\'' +
                ", moduleCode='" + moduleCode + '\'' +
                ", cxdysj=" + cxdysj +
                ", qxdm='" + qxdm + '\'' +
                ", qxdmList=" + qxdmList +
                ", xzztcx='" + xzztcx + '\'' +
                ", ydcxjs=" + ydcxjs +
                ", qlrmcList=" + qlrmcList +
                ", gzlslid=" + gzlslid +
                ", ysqrgx=" + ysqrgx +
                '}';
    }
}
