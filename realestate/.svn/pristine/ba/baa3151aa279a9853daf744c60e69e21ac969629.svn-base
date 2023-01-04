package cn.gtmap.realestate.common.core.qo.inquiry;

import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/7/8
 * @description  不动产住房信息查询参数QO定义
 */
@ApiModel(value = "BdcZfxxQO", description = "不动产住房查询参数封装对象")
public class BdcZfxxQO {
    /**
     * 权利人名称与证件号需要成对，避免单独分开名称和证件号
     */
    @ApiModelProperty(value = "权利人信息")
    private List<BdcQlrQO> qlrxx;

    /**
     * 1 不动产登记系统  2  自助查询机  3 互联网+   4 （南通）自助交互机
     * 5 合肥大数据局
     */
    @ApiModelProperty(value = "查询来源")
    private String cxly;

    /**
     * 查询单位名称（主要用于记录，不用于过滤）
     */
    @ApiModelProperty(value = "查询单位名称")
    private String cxdw;
    /**
     * 发起请求机器mac地址
     */
    @ApiModelProperty(value = "来源地址")
    private String lydz;

    /**
     * 有些场景会单独传不动产单元号查询（多个的话逗号分隔）
     */
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "规划用途")
    private String ghyt;

    @ApiModelProperty(value = "是否需要过滤规划用途  Y 是 N 否")
    private String sfghyt;

    @ApiModelProperty(value = "注销产权查询年限")
    private String zxcqcxnx;

    /**
     * 多个逗号分隔
     */
    @ApiModelProperty(value = "注销产权下一手流程定义ID")
    private String zxcqxyslc;

    /**
     * 指定类型规划用途不展示
     */
    @ApiModelProperty(value = "排除规划用途")
    private String pcghyt;

    /**
     * 是否合并产权，Y 是 N 否；默认空，即不合并，例如一证多人返回每个人对应产权信息
     */
    @ApiModelProperty(value = "是否合并产权")
    private String hbcq;

    /**
     * 有房无房证明可能按照权利类型过滤
     */
    @ApiModelProperty(value = "权利类型")
    private String qllx;

    /**
     * 舒城房屋套次计算的时候排除农房的数据
     */
    @ApiModelProperty(value = "排除权利类型")
    private String pcqllx;

    /**
     * 根据证书类型
     */
    @ApiModelProperty(value = "证书类型")
    private String zslx;

    /**
     * 根据权籍管理代码过滤,多个用英文逗号隔开
     */
    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

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

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getHbcq() {
        return hbcq;
    }

    public void setHbcq(String hbcq) {
        this.hbcq = hbcq;
    }

    public String getPcghyt() {
        return pcghyt;
    }

    public void setPcghyt(String pcghyt) {
        this.pcghyt = pcghyt;
    }

    public String getZxcqxyslc() {
        return zxcqxyslc;
    }

    public void setZxcqxyslc(String zxcqxyslc) {
        this.zxcqxyslc = zxcqxyslc;
    }

    public String getZxcqcxnx() {
        return zxcqcxnx;
    }

    public void setZxcqcxnx(String zxcqcxnx) {
        this.zxcqcxnx = zxcqcxnx;
    }

    public String getSfghyt() {
        return sfghyt;
    }

    public void setSfghyt(String sfghyt) {
        this.sfghyt = sfghyt;
    }

    public List<BdcQlrQO> getQlrxx() {
        return qlrxx;
    }

    public void setQlrxx(List<BdcQlrQO> qlrxx) {
        this.qlrxx = qlrxx;
    }

    public String getCxly() {
        return cxly;
    }

    public void setCxly(String cxly) {
        this.cxly = cxly;
    }

    public String getLydz() {
        return lydz;
    }

    public void setLydz(String lydz) {
        this.lydz = lydz;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public String getCxdw() {
        return cxdw;
    }

    public void setCxdw(String cxdw) {
        this.cxdw = cxdw;
    }

    public String getZslx() {
        return zslx;
    }

    public void setZslx(String zslx) {
        this.zslx = zslx;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public String getPcqllx() {
        return pcqllx;
    }

    public void setPcqllx(String pcqllx) {
        this.pcqllx = pcqllx;
    }

    @Override
    public String toString() {
        return "BdcZfxxQO{" +
                "qlrxx=" + qlrxx +
                ", cxly='" + cxly + '\'' +
                ", cxdw='" + cxdw + '\'' +
                ", lydz='" + lydz + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", ghyt='" + ghyt + '\'' +
                ", sfghyt='" + sfghyt + '\'' +
                ", zxcqcxnx='" + zxcqcxnx + '\'' +
                ", zxcqxyslc='" + zxcqxyslc + '\'' +
                ", pcghyt='" + pcghyt + '\'' +
                ", hbcq='" + hbcq + '\'' +
                ", qllx='" + qllx + '\'' +
                ", pcqllx='" + pcqllx + '\'' +
                ", zslx='" + zslx + '\'' +
                ", qjgldm='" + qjgldm + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", qxdmList=" + qxdmList +
                ", xzztcx='" + xzztcx + '\'' +
                '}';
    }
}
