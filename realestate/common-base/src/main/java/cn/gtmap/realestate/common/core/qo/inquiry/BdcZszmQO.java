package cn.gtmap.realestate.common.core.qo.inquiry;

import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/5/16
 * @description  查询子系统：不动产证书证明查询QO实体定义
 *
 *  模糊类型：0 精确  1 左匹配  2 右匹配 3 全模糊
 *
 *  名称后加后缀 2 的为二次查询筛选时候新增的查询字段
 */
public class BdcZszmQO {
    /**
     * 合肥版： hefei
     * 南通版： nantong
     * 标准版： standard
     */
    @ApiModelProperty(value = "版本标识")
    private String version;

    @ApiModelProperty(value = "是否查询特殊证件号")
    private Boolean sfcxtszj;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "不动产单元号 模糊类型")
    private Integer bdcdyhmhlx;

    @ApiModelProperty(value = "不动产单元号2")
    private String bdcdyh2;

    @ApiModelProperty(value = "不动产单元号2 模糊类型")
    private Integer bdcdyhmhlx2;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "不动产权证号 模糊类型")
    private Integer bdcqzhmhlx;

    @ApiModelProperty(value = "不动产权证号2")
    private String bdcqzh2;

    @ApiModelProperty(value = "不动产权证号2 模糊类型")
    private Integer bdcqzhmhlx2;

    @ApiModelProperty(value = "原产权证号")
    private String ycqzh;

    @ApiModelProperty(value = "原产权证号 模糊类型")
    private Integer ycqzhmhlx;

    @ApiModelProperty(value = "权利人名称")
    private String[] qlrmc;

    @ApiModelProperty(value = "权利人名称 模糊类型")
    private Integer qlrmcmhlx;

    @ApiModelProperty(value = "权利人名称2")
    private String qlrmc2;

    @ApiModelProperty(value = "权利人名称2 模糊类型")
    private Integer qlrmcmhlx2;

    @ApiModelProperty(value = "权利人证件号")
    private String[] qlrzjh;

    @ApiModelProperty(value = "权利人证件号 模糊类型")
    private Integer qlrzjhmhlx;

    @ApiModelProperty(value = "权利人证件号2")
    private String qlrzjh2;

    @ApiModelProperty(value = "权利人证件号2 模糊类型")
    private Integer qlrzjhmhlx2;

    @ApiModelProperty(value = "义务人名称")
    private String[] ywrmc;

    @ApiModelProperty(value = "义务人名称 模糊类型")
    private Integer ywrmcmhlx;

    @ApiModelProperty(value = "义务人名称2")
    private String ywrmc2;

    @ApiModelProperty(value = "义务人名称2 模糊类型")
    private Integer ywrmcmhlx2;

    @ApiModelProperty(value = "义务人证件号")
    private String[] ywrzjh;

    @ApiModelProperty(value = "义务人证件号 模糊类型")
    private Integer ywrzjhmhlx;

    @ApiModelProperty(value = "义务人证件号2")
    private String ywrzjh2;

    @ApiModelProperty(value = "义务人证件号2 模糊类型")
    private Integer ywrzjhmhlx2;

    @ApiModelProperty(value = "证书类型")
    private Integer zslx;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "坐落 模糊类型")
    private Integer zlmhlx;

    @ApiModelProperty(value = "坐落2")
    private String zl2;

    @ApiModelProperty(value = "坐落2 模糊类型")
    private Integer zlmhlx2;

    @ApiModelProperty(value = "幢号")
    private String zh;

    @ApiModelProperty(value = "幢号 模糊类型")
    private Integer zhmhlx;

    @ApiModelProperty(value = "幢号2")
    private String zh2;

    @ApiModelProperty(value = "幢号2 模糊类型")
    private Integer zhmhlx2;

    @ApiModelProperty(value = "房间号")
    private String fjh;

    @ApiModelProperty(value = "房间号 模糊类型")
    private Integer fjhmhlx;

    @ApiModelProperty(value = "房间号2")
    private String fjh2;

    @ApiModelProperty(value = "房间号2 模糊类型")
    private Integer fjhmhlx2;

    @ApiModelProperty(value = "缮证人")
    private String szr;

    @ApiModelProperty(value = "缮证时间 起")
    private String szsjq;

    @ApiModelProperty(value = "缮证时间 止")
    private String szsjz;

    @ApiModelProperty(value = "登簿时间 起")
    private String dbsjq;

    @ApiModelProperty(value = "登簿时间 止")
    private String dbsjz;

    @ApiModelProperty(value = "权属状态")
    private Integer qszt;

    @ApiModelProperty(value = "权属状态2")
    private Integer qszt2;

    @ApiModelProperty(value = "权属状态3")
    private Integer[] qszt3;

    @ApiModelProperty(value = "证书ID")
    private String zsid;

    @ApiModelProperty(value = "业务编号")
    private String slbh;

    @ApiModelProperty(value = "业务编号模糊类型")
    private String slbhmhlx;

    @ApiModelProperty(value = "业务编号2")
    private String slbh2;

    @ApiModelProperty(value = "业务编号2 模糊类型")
    private String slbhmhlx2;

    @ApiModelProperty(value = "房屋编号")
    private String fwbh;

    @ApiModelProperty(value = "房屋编号模糊类型")
    private String fwbhmhlx;

    @ApiModelProperty(value = "房屋编号2")
    private String fwbh2;

    @ApiModelProperty(value = "房屋编号2 模糊类型")
    private String fwbhmhlx2;

    @ApiModelProperty(value = "证书流水号")
    private String zhlsh;

    @ApiModelProperty(value = "证书流水号模糊类型")
    private String zhlshmhlx;

    @ApiModelProperty(value = "证书流水号2")
    private String zhlsh2;

    @ApiModelProperty(value = "证书流水号2 模糊类型")
    private String zhlshmhlx2;

    @ApiModelProperty(value = "不动产类型")
    private Integer bdclx;

    @ApiModelProperty(value = "原地籍号")
    private String ybdcdyh;

    @ApiModelProperty(value = "原地籍号模糊类型")
    private String ybdcdyhmhlx;

    @ApiModelProperty(value = "登记原因")
    private String djyy;

    @ApiModelProperty(value = "登记原因 模糊类型")
    private String djyymhlx;

    @ApiModelProperty(value = "规划用途")
    private String ghyt;

    @ApiModelProperty(value = "定着物用途")
    private Integer dzwyt;

    @ApiModelProperty(value = "用海类型A")
    private Integer yhlxa;

    @ApiModelProperty(value = "用海类型B")
    private Integer yhlxb;

    @ApiModelProperty(value = "规划用途 模糊类型")
    private String ghytmhlx;

    @ApiModelProperty(value = "权利类型")
    private Integer[] qllx;

    @ApiModelProperty(value = "权利性质")
    private Integer qlxz;

    @ApiModelProperty(value = "权利类型不设置则不查询数据")
    private Boolean qllxpd;

    @ApiModelProperty(value = "原系统产权证号")
    private String yxtcqzh;

    @ApiModelProperty(value = "原系统产权证号 模糊类型")
    private String yxtcqzhmhlx;

    @ApiModelProperty(value = "原系统产权证号2")
    private String yxtcqzh2;

    @ApiModelProperty(value = "原系统产权证号2 模糊类型")
    private String yxtcqzhmhlx2;

    @ApiModelProperty(value = "IP地址")
    private String ipaddress;

    @ApiModelProperty(value = "（不展示的）流程登记小类代码")
    private String djxldm;


    @ApiModelProperty(value = "证书印制号")
    private String zsyzh;

    @ApiModelProperty(value = "证书印制号模糊类型")
    private String zsyzhmhlx;

    @ApiModelProperty(value = "不动产权证号简称")
    private String bdcqzhjc;

    @ApiModelProperty(value = "证书印制号模糊类型")
    private String bdcqzhjcmhlx;

    /**
     * 为空默认查询所有登记类型，否则查询指定类型
     */
    @ApiModelProperty(value = "登记类型")
    private String djlx;

    @ApiModelProperty(value = "查封文号")
    private String cfwh;

    @ApiModelProperty(value = "查封文号模糊类型")
    private String cfwhmhlx;

    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

    @ApiModelProperty(value = "区县代码集合")
    private List<String> qxdmList;

    @ApiModelProperty(value = "家庭成员名称")
    private String jtcymc;

    @ApiModelProperty(value = "家庭成员名称模糊类型")
    private String jtcymcmhlx;

    @ApiModelProperty(value = "常州是否按户登记 1:是 0:否")
    private Integer sfahdj;

    /**
     * 盐城 查询抵押不动产登记类型，BDC_ZD_DYBDCLX
     */
    @ApiModelProperty(value = "盐城抵押不动产登记类型")
    private Integer dybdclx;

    @ApiModelProperty(value = "模块编码,用于角色过滤")
    private String moduleCode;

    @ApiModelProperty(value = "查询htba数据")
    private Boolean cxhtba = false;

    @ApiModelProperty(value = "是否按照证件号匹配排序")
    private Boolean zjhpx = false;

    @ApiModelProperty(value = "合同编号")
    private String htbh;

    @ApiModelProperty(value = "合同编号模糊类型")
    private String htbhmhlx;


    public Boolean getZjhpx() {
        return zjhpx;
    }

    public void setZjhpx(Boolean zjhpx) {
        this.zjhpx = zjhpx;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getQllxpd() {
        return qllxpd;
    }

    public void setQllxpd(Boolean qllxpd) {
        this.qllxpd = qllxpd;
    }

    public Boolean getSfcxtszj() {
        return sfcxtszj;
    }

    public void setSfcxtszj(Boolean sfcxtszj) {
        this.sfcxtszj = sfcxtszj;
    }

    public Integer getDzwyt() {
        return dzwyt;
    }

    public void setDzwyt(Integer dzwyt) {
        this.dzwyt = dzwyt;
    }

    public Integer getYhlxa() {
        return yhlxa;
    }

    public void setYhlxa(Integer yhlxa) {
        this.yhlxa = yhlxa;
    }

    public Integer getYhlxb() {
        return yhlxb;
    }

    public void setYhlxb(Integer yhlxb) {
        this.yhlxb = yhlxb;
    }

    public Integer getQlxz() {
        return qlxz;
    }

    public void setQlxz(Integer qlxz) {
        this.qlxz = qlxz;
    }

    public String getZsyzh() {
        return zsyzh;
    }

    public void setZsyzh(String zsyzh) {
        this.zsyzh = zsyzh;
    }

    public String getZsyzhmhlx() {
        return zsyzhmhlx;
    }

    public void setZsyzhmhlx(String zsyzhmhlx) {
        this.zsyzhmhlx = zsyzhmhlx;
    }

    public String getDjxldm() {
        return djxldm;
    }

    public void setDjxldm(String djxldm) {
        this.djxldm = djxldm;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public Integer[] getQszt3() {
        return qszt3;
    }

    public void setQszt3(Integer[] qszt3) {
        this.qszt3 = qszt3;
    }

    public String getZl2() {
        return zl2;
    }

    public void setZl2(String zl2) {
        this.zl2 = zl2;
    }

    public Integer getZlmhlx2() {
        return zlmhlx2;
    }

    public void setZlmhlx2(Integer zlmhlx2) {
        this.zlmhlx2 = zlmhlx2;
    }

    public Integer getQszt2() {
        return qszt2;
    }

    public void setQszt2(Integer qszt2) {
        this.qszt2 = qszt2;
    }

    public String getYxtcqzh2() {
        return yxtcqzh2;
    }

    public void setYxtcqzh2(String yxtcqzh2) {
        this.yxtcqzh2 = yxtcqzh2;
    }

    public String getYxtcqzhmhlx2() {
        return yxtcqzhmhlx2;
    }

    public void setYxtcqzhmhlx2(String yxtcqzhmhlx2) {
        this.yxtcqzhmhlx2 = yxtcqzhmhlx2;
    }

    public String getYxtcqzh() {
        return yxtcqzh;
    }

    public void setYxtcqzh(String yxtcqzh) {
        this.yxtcqzh = yxtcqzh;
    }

    public String getYxtcqzhmhlx() {
        return yxtcqzhmhlx;
    }

    public void setYxtcqzhmhlx(String yxtcqzhmhlx) {
        this.yxtcqzhmhlx = yxtcqzhmhlx;
    }

    public String getSlbh2() {
        return slbh2;
    }

    public void setSlbh2(String slbh2) {
        this.slbh2 = slbh2;
    }

    public String getSlbhmhlx2() {
        return slbhmhlx2;
    }

    public void setSlbhmhlx2(String slbhmhlx2) {
        this.slbhmhlx2 = slbhmhlx2;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public Integer getBdcdyhmhlx() {
        return bdcdyhmhlx;
    }

    public void setBdcdyhmhlx(Integer bdcdyhmhlx) {
        this.bdcdyhmhlx = bdcdyhmhlx;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public Integer getBdcqzhmhlx() {
        return bdcqzhmhlx;
    }

    public void setBdcqzhmhlx(Integer bdcqzhmhlx) {
        this.bdcqzhmhlx = bdcqzhmhlx;
    }

    public String[] getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String[] qlrmc) {
        this.qlrmc = qlrmc;
    }

    public Integer getQlrmcmhlx() {
        return qlrmcmhlx;
    }

    public void setQlrmcmhlx(Integer qlrmcmhlx) {
        this.qlrmcmhlx = qlrmcmhlx;
    }

    public String[] getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String[] qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public Integer getQlrzjhmhlx() {
        return qlrzjhmhlx;
    }

    public void setQlrzjhmhlx(Integer qlrzjhmhlx) {
        this.qlrzjhmhlx = qlrzjhmhlx;
    }

    public String[] getYwrmc() {
        return ywrmc;
    }

    public void setYwrmc(String[] ywrmc) {
        this.ywrmc = ywrmc;
    }

    public Integer getYwrmcmhlx() {
        return ywrmcmhlx;
    }

    public void setYwrmcmhlx(Integer ywrmcmhlx) {
        this.ywrmcmhlx = ywrmcmhlx;
    }

    public String[] getYwrzjh() {
        return ywrzjh;
    }

    public void setYwrzjh(String[] ywrzjh) {
        this.ywrzjh = ywrzjh;
    }

    public Integer getYwrzjhmhlx() {
        return ywrzjhmhlx;
    }

    public void setYwrzjhmhlx(Integer ywrzjhmhlx) {
        this.ywrzjhmhlx = ywrzjhmhlx;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Integer getZlmhlx() {
        return zlmhlx;
    }

    public void setZlmhlx(Integer zlmhlx) {
        this.zlmhlx = zlmhlx;
    }

    public String getSzr() {
        return szr;
    }

    public void setSzr(String szr) {
        this.szr = szr;
    }

    public String getSzsjq() {
        return szsjq;
    }

    public void setSzsjq(String szsjq) {
        this.szsjq = szsjq;
    }

    public String getSzsjz() {
        return szsjz;
    }

    public void setSzsjz(String szsjz) {
        this.szsjz = szsjz;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    public Integer getYcqzhmhlx() {
        return ycqzhmhlx;
    }

    public void setYcqzhmhlx(Integer ycqzhmhlx) {
        this.ycqzhmhlx = ycqzhmhlx;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public Integer getZhmhlx() {
        return zhmhlx;
    }

    public void setZhmhlx(Integer zhmhlx) {
        this.zhmhlx = zhmhlx;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public Integer getFjhmhlx() {
        return fjhmhlx;
    }

    public void setFjhmhlx(Integer fjhmhlx) {
        this.fjhmhlx = fjhmhlx;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getSlbhmhlx() {
        return slbhmhlx;
    }

    public void setSlbhmhlx(String slbhmhlx) {
        this.slbhmhlx = slbhmhlx;
    }

    public String getFwbh() {
        return fwbh;
    }

    public void setFwbh(String fwbh) {
        this.fwbh = fwbh;
    }

    public String getFwbhmhlx() {
        return fwbhmhlx;
    }

    public void setFwbhmhlx(String fwbhmhlx) {
        this.fwbhmhlx = fwbhmhlx;
    }

    public String getZhlsh() {
        return zhlsh;
    }

    public void setZhlsh(String zhlsh) {
        this.zhlsh = zhlsh;
    }

    public String getZhlshmhlx() {
        return zhlshmhlx;
    }

    public void setZhlshmhlx(String zhlshmhlx) {
        this.zhlshmhlx = zhlshmhlx;
    }

    public Integer getBdclx() {
        return bdclx;
    }

    public void setBdclx(Integer bdclx) {
        this.bdclx = bdclx;
    }

    public String getYbdcdyh() {
        return ybdcdyh;
    }

    public void setYbdcdyh(String ybdcdyh) {
        this.ybdcdyh = ybdcdyh;
    }

    public String getYbdcdyhmhlx() {
        return ybdcdyhmhlx;
    }

    public void setYbdcdyhmhlx(String ybdcdyhmhlx) {
        this.ybdcdyhmhlx = ybdcdyhmhlx;
    }

    public String getBdcdyh2() {
        return bdcdyh2;
    }

    public void setBdcdyh2(String bdcdyh2) {
        this.bdcdyh2 = bdcdyh2;
    }

    public Integer getBdcdyhmhlx2() {
        return bdcdyhmhlx2;
    }

    public void setBdcdyhmhlx2(Integer bdcdyhmhlx2) {
        this.bdcdyhmhlx2 = bdcdyhmhlx2;
    }

    public String getBdcqzh2() {
        return bdcqzh2;
    }

    public void setBdcqzh2(String bdcqzh2) {
        this.bdcqzh2 = bdcqzh2;
    }

    public Integer getBdcqzhmhlx2() {
        return bdcqzhmhlx2;
    }

    public void setBdcqzhmhlx2(Integer bdcqzhmhlx2) {
        this.bdcqzhmhlx2 = bdcqzhmhlx2;
    }

    public String getQlrmc2() {
        return qlrmc2;
    }

    public void setQlrmc2(String qlrmc2) {
        this.qlrmc2 = qlrmc2;
    }

    public Integer getQlrmcmhlx2() {
        return qlrmcmhlx2;
    }

    public void setQlrmcmhlx2(Integer qlrmcmhlx2) {
        this.qlrmcmhlx2 = qlrmcmhlx2;
    }

    public String getQlrzjh2() {
        return qlrzjh2;
    }

    public void setQlrzjh2(String qlrzjh2) {
        this.qlrzjh2 = qlrzjh2;
    }

    public Integer getQlrzjhmhlx2() {
        return qlrzjhmhlx2;
    }

    public void setQlrzjhmhlx2(Integer qlrzjhmhlx2) {
        this.qlrzjhmhlx2 = qlrzjhmhlx2;
    }

    public String getYwrmc2() {
        return ywrmc2;
    }

    public void setYwrmc2(String ywrmc2) {
        this.ywrmc2 = ywrmc2;
    }

    public Integer getYwrmcmhlx2() {
        return ywrmcmhlx2;
    }

    public void setYwrmcmhlx2(Integer ywrmcmhlx2) {
        this.ywrmcmhlx2 = ywrmcmhlx2;
    }

    public String getYwrzjh2() {
        return ywrzjh2;
    }

    public void setYwrzjh2(String ywrzjh2) {
        this.ywrzjh2 = ywrzjh2;
    }

    public Integer getYwrzjhmhlx2() {
        return ywrzjhmhlx2;
    }

    public void setYwrzjhmhlx2(Integer ywrzjhmhlx2) {
        this.ywrzjhmhlx2 = ywrzjhmhlx2;
    }

    public String getZh2() {
        return zh2;
    }

    public void setZh2(String zh2) {
        this.zh2 = zh2;
    }

    public Integer getZhmhlx2() {
        return zhmhlx2;
    }

    public void setZhmhlx2(Integer zhmhlx2) {
        this.zhmhlx2 = zhmhlx2;
    }

    public String getFjh2() {
        return fjh2;
    }

    public void setFjh2(String fjh2) {
        this.fjh2 = fjh2;
    }

    public Integer getFjhmhlx2() {
        return fjhmhlx2;
    }

    public void setFjhmhlx2(Integer fjhmhlx2) {
        this.fjhmhlx2 = fjhmhlx2;
    }

    public String getFwbh2() {
        return fwbh2;
    }

    public void setFwbh2(String fwbh2) {
        this.fwbh2 = fwbh2;
    }

    public String getFwbhmhlx2() {
        return fwbhmhlx2;
    }

    public void setFwbhmhlx2(String fwbhmhlx2) {
        this.fwbhmhlx2 = fwbhmhlx2;
    }

    public String getZhlsh2() {
        return zhlsh2;
    }

    public void setZhlsh2(String zhlsh2) {
        this.zhlsh2 = zhlsh2;
    }

    public String getZhlshmhlx2() {
        return zhlshmhlx2;
    }

    public void setZhlshmhlx2(String zhlshmhlx2) {
        this.zhlshmhlx2 = zhlshmhlx2;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getDjyymhlx() {
        return djyymhlx;
    }

    public void setDjyymhlx(String djyymhlx) {
        this.djyymhlx = djyymhlx;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public String getGhytmhlx() {
        return ghytmhlx;
    }

    public void setGhytmhlx(String ghytmhlx) {
        this.ghytmhlx = ghytmhlx;
    }

    public Integer[] getQllx() {
        return qllx;
    }

    public void setQllx(Integer[] qllx) {
        this.qllx = qllx;
    }

    public String getCfwh() {
        return cfwh;
    }

    public void setCfwh(String cfwh) {
        this.cfwh = cfwh;
    }

    public String getCfwhmhlx() {
        return cfwhmhlx;
    }

    public void setCfwhmhlx(String cfwhmhlx) {
        this.cfwhmhlx = cfwhmhlx;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public List<String> getQxdmList() {
        return qxdmList;
    }

    public void setQxdmList(List<String> qxdmList) {
        this.qxdmList = qxdmList;
    }

    public String getBdcqzhjc() {
        return bdcqzhjc;
    }

    public void setBdcqzhjc(String bdcqzhjc) {
        this.bdcqzhjc = bdcqzhjc;
    }

    public String getBdcqzhjcmhlx() {
        return bdcqzhjcmhlx;
    }

    public void setBdcqzhjcmhlx(String bdcqzhjcmhlx) {
        this.bdcqzhjcmhlx = bdcqzhjcmhlx;
    }

    public String getJtcymc() {
        return jtcymc;
    }

    public void setJtcymc(String jtcymc) {
        this.jtcymc = jtcymc;
    }

    public Integer getSfahdj() {
        return sfahdj;
    }

    public void setSfahdj(Integer sfahdj) {
        this.sfahdj = sfahdj;
    }

    public String getJtcymcmhlx() {
        return jtcymcmhlx;
    }

    public void setJtcymcmhlx(String jtcymcmhlx) {
        this.jtcymcmhlx = jtcymcmhlx;
    }

    public Integer getDybdclx() {
        return dybdclx;
    }

    public void setDybdclx(Integer dybdclx) {
        this.dybdclx = dybdclx;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Boolean getCxhtba() {
        return cxhtba;
    }

    public void setCxhtba(Boolean cxhtba) {
        this.cxhtba = cxhtba;
    }

    public String getDbsjq() {
        return dbsjq;
    }

    public void setDbsjq(String dbsjq) {
        this.dbsjq = dbsjq;
    }

    public String getDbsjz() {
        return dbsjz;
    }

    public void setDbsjz(String dbsjz) {
        this.dbsjz = dbsjz;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getHtbhmhlx() {
        return htbhmhlx;
    }

    public void setHtbhmhlx(String htbhmhlx) {
        this.htbhmhlx = htbhmhlx;
    }

    @Override
    public String toString() {
        return "BdcZszmQO{" +
                "version='" + version + '\'' +
                ", sfcxtszj=" + sfcxtszj +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdyhmhlx=" + bdcdyhmhlx +
                ", bdcdyh2='" + bdcdyh2 + '\'' +
                ", bdcdyhmhlx2=" + bdcdyhmhlx2 +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", bdcqzhmhlx=" + bdcqzhmhlx +
                ", bdcqzh2='" + bdcqzh2 + '\'' +
                ", bdcqzhmhlx2=" + bdcqzhmhlx2 +
                ", ycqzh='" + ycqzh + '\'' +
                ", ycqzhmhlx=" + ycqzhmhlx +
                ", qlrmc=" + Arrays.toString(qlrmc) +
                ", qlrmcmhlx=" + qlrmcmhlx +
                ", qlrmc2='" + qlrmc2 + '\'' +
                ", qlrmcmhlx2=" + qlrmcmhlx2 +
                ", qlrzjh=" + Arrays.toString(qlrzjh) +
                ", qlrzjhmhlx=" + qlrzjhmhlx +
                ", qlrzjh2='" + qlrzjh2 + '\'' +
                ", qlrzjhmhlx2=" + qlrzjhmhlx2 +
                ", ywrmc=" + Arrays.toString(ywrmc) +
                ", ywrmcmhlx=" + ywrmcmhlx +
                ", ywrmc2='" + ywrmc2 + '\'' +
                ", ywrmcmhlx2=" + ywrmcmhlx2 +
                ", ywrzjh=" + Arrays.toString(ywrzjh) +
                ", ywrzjhmhlx=" + ywrzjhmhlx +
                ", ywrzjh2='" + ywrzjh2 + '\'' +
                ", ywrzjhmhlx2=" + ywrzjhmhlx2 +
                ", zslx=" + zslx +
                ", zl='" + zl + '\'' +
                ", zlmhlx=" + zlmhlx +
                ", zl2='" + zl2 + '\'' +
                ", zlmhlx2=" + zlmhlx2 +
                ", zh='" + zh + '\'' +
                ", zhmhlx=" + zhmhlx +
                ", zh2='" + zh2 + '\'' +
                ", zhmhlx2=" + zhmhlx2 +
                ", fjh='" + fjh + '\'' +
                ", fjhmhlx=" + fjhmhlx +
                ", fjh2='" + fjh2 + '\'' +
                ", fjhmhlx2=" + fjhmhlx2 +
                ", szr='" + szr + '\'' +
                ", szsjq='" + szsjq + '\'' +
                ", szsjz='" + szsjz + '\'' +
                ", dbsjq='" + dbsjq + '\'' +
                ", dbsjz='" + dbsjz + '\'' +
                ", qszt=" + qszt +
                ", qszt2=" + qszt2 +
                ", qszt3=" + Arrays.toString(qszt3) +
                ", zsid='" + zsid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", slbhmhlx='" + slbhmhlx + '\'' +
                ", slbh2='" + slbh2 + '\'' +
                ", slbhmhlx2='" + slbhmhlx2 + '\'' +
                ", fwbh='" + fwbh + '\'' +
                ", fwbhmhlx='" + fwbhmhlx + '\'' +
                ", fwbh2='" + fwbh2 + '\'' +
                ", fwbhmhlx2='" + fwbhmhlx2 + '\'' +
                ", zhlsh='" + zhlsh + '\'' +
                ", zhlshmhlx='" + zhlshmhlx + '\'' +
                ", zhlsh2='" + zhlsh2 + '\'' +
                ", zhlshmhlx2='" + zhlshmhlx2 + '\'' +
                ", bdclx=" + bdclx +
                ", ybdcdyh='" + ybdcdyh + '\'' +
                ", ybdcdyhmhlx='" + ybdcdyhmhlx + '\'' +
                ", djyy='" + djyy + '\'' +
                ", djyymhlx='" + djyymhlx + '\'' +
                ", ghyt='" + ghyt + '\'' +
                ", dzwyt=" + dzwyt +
                ", yhlxa=" + yhlxa +
                ", yhlxb=" + yhlxb +
                ", ghytmhlx='" + ghytmhlx + '\'' +
                ", qllx=" + Arrays.toString(qllx) +
                ", qlxz=" + qlxz +
                ", qllxpd=" + qllxpd +
                ", yxtcqzh='" + yxtcqzh + '\'' +
                ", yxtcqzhmhlx='" + yxtcqzhmhlx + '\'' +
                ", yxtcqzh2='" + yxtcqzh2 + '\'' +
                ", yxtcqzhmhlx2='" + yxtcqzhmhlx2 + '\'' +
                ", ipaddress='" + ipaddress + '\'' +
                ", djxldm='" + djxldm + '\'' +
                ", zsyzh='" + zsyzh + '\'' +
                ", zsyzhmhlx='" + zsyzhmhlx + '\'' +
                ", bdcqzhjc='" + bdcqzhjc + '\'' +
                ", bdcqzhjcmhlx='" + bdcqzhjcmhlx + '\'' +
                ", djlx='" + djlx + '\'' +
                ", cfwh='" + cfwh + '\'' +
                ", cfwhmhlx='" + cfwhmhlx + '\'' +
                ", qjgldm='" + qjgldm + '\'' +
                ", qxdmList=" + qxdmList +
                ", jtcymc='" + jtcymc + '\'' +
                ", jtcymcmhlx='" + jtcymcmhlx + '\'' +
                ", sfahdj=" + sfahdj +
                ", dybdclx=" + dybdclx +
                ", moduleCode='" + moduleCode + '\'' +
                ", cxhtba=" + cxhtba +
                ", zjhpx=" + zjhpx +
                '}';
    }
}
