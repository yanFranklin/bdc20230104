package cn.gtmap.realestate.common.core.qo.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/2/9
 * @description 不动产异议QO
 */
@Api(value = "BdcYyQO", description = "不动产异议QO")
public class BdcYyQO {

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "不动产单元号 模糊类型")
    private String bdcdyhmhlx;

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "权利人名称")
    private String[] qlrmc;

    @ApiModelProperty(value = "权利人名称 模糊类型")
    private String qlrmcmhlx;

    @ApiModelProperty(value = "权利人证件号")
    private String[] qlrzjh;

    @ApiModelProperty(value = "权利人证件号 模糊类型")
    private String qlrzjhmhlx;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "坐落 模糊类型")
    private String zlmhlx;

    @ApiModelProperty(value = "抵押证明号")
    private String bdcqzh;

    @ApiModelProperty(value = "抵押证明号 模糊类型")
    private String bdcqzhmhlx;

    @ApiModelProperty(value = "原系统产权证号")
    private String yxtcqzh;

    @ApiModelProperty(value = "原系统产权证号 模糊类型")
    private String yxtcqzhmhlx;

    @ApiModelProperty(value = "义务人")
    private String[] ywrmc;

    @ApiModelProperty(value = "义务人 模糊类型")
    private String ywrmcmhlx;

    @ApiModelProperty(value = "义务人证件号")
    private String[] ywrzjh;

    @ApiModelProperty(value = "义务人证件号 模糊类型")
    private String ywrzjhmhlx;

    @ApiModelProperty(value = "登簿人")
    private String dbr;

    @ApiModelProperty(value = "登簿人 模糊类型")
    private String dbrmhlx;

    @ApiModelProperty(value = "注销异议业务号")
    private String zxyyywh;

    @ApiModelProperty(value = "注销异议业务号 模糊类型")
    private String zxyyywhmhlx;

    @ApiModelProperty(value = "注销异议登簿人")
    private String zxyydbr;

    @ApiModelProperty(value = "注销异议登簿人 模糊类型")
    private String zxyydbrmhlx;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "受理编号 模糊类型")
    private String slbhmhlx;

    @ApiModelProperty(value = "原产权证号")
    private String ycqzh;

    @ApiModelProperty(value = "原产权证号 模糊类型")
    private String ycqzhmhlx;

    @ApiModelProperty(value = "查询类型")
    private String type;

    @ApiModelProperty(value = "权属状态")
    private Integer qszt;

    @ApiModelProperty(value = "客户端ip")
    private String clientIp;

    @ApiModelProperty(value = "受理编号集合")
    private List<String> slbhList;

    @ApiModelProperty(value = "权限代码")
    private List<String> qxdmList;

    @ApiModelProperty(value = "权属状态集合")
    private List<Integer> qsztList;

    @ApiModelProperty(value = "证书类型")
    private Integer zslx;

    @ApiModelProperty(value = "不动产唯一编号")
    private String bdcdywybh;

    @ApiModelProperty(value = "不动产唯一编号 模糊类型")
    private String bdcdywybhmhlx;

    @ApiModelProperty(value = "证书流水号")
    private String zhlsh;

    @ApiModelProperty(value = "证书流水号 模糊类型")
    private String zhlshmhlx;

    @ApiModelProperty(value = "证书印制号")
    private String zsyzh;

    @ApiModelProperty(value = "证书印制号模糊类型")
    private String zsyzhmhlx;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcdyhmhlx() {
        return bdcdyhmhlx;
    }

    public void setBdcdyhmhlx(String bdcdyhmhlx) {
        this.bdcdyhmhlx = bdcdyhmhlx;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String[] getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String[] qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrmcmhlx() {
        return qlrmcmhlx;
    }

    public void setQlrmcmhlx(String qlrmcmhlx) {
        this.qlrmcmhlx = qlrmcmhlx;
    }

    public String[] getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String[] qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getQlrzjhmhlx() {
        return qlrzjhmhlx;
    }

    public void setQlrzjhmhlx(String qlrzjhmhlx) {
        this.qlrzjhmhlx = qlrzjhmhlx;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getZlmhlx() {
        return zlmhlx;
    }

    public void setZlmhlx(String zlmhlx) {
        this.zlmhlx = zlmhlx;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getBdcqzhmhlx() {
        return bdcqzhmhlx;
    }

    public void setBdcqzhmhlx(String bdcqzhmhlx) {
        this.bdcqzhmhlx = bdcqzhmhlx;
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

    public String[] getYwrmc() {
        return ywrmc;
    }

    public void setYwrmc(String[] ywrmc) {
        this.ywrmc = ywrmc;
    }

    public String getYwrmcmhlx() {
        return ywrmcmhlx;
    }

    public void setYwrmcmhlx(String ywrmcmhlx) {
        this.ywrmcmhlx = ywrmcmhlx;
    }

    public String[] getYwrzjh() {
        return ywrzjh;
    }

    public void setYwrzjh(String[] ywrzjh) {
        this.ywrzjh = ywrzjh;
    }

    public String getYwrzjhmhlx() {
        return ywrzjhmhlx;
    }

    public void setYwrzjhmhlx(String ywrzjhmhlx) {
        this.ywrzjhmhlx = ywrzjhmhlx;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public String getDbrmhlx() {
        return dbrmhlx;
    }

    public void setDbrmhlx(String dbrmhlx) {
        this.dbrmhlx = dbrmhlx;
    }

    public String getZxyyywh() {
        return zxyyywh;
    }

    public void setZxyyywh(String zxyyywh) {
        this.zxyyywh = zxyyywh;
    }

    public String getZxyyywhmhlx() {
        return zxyyywhmhlx;
    }

    public void setZxyyywhmhlx(String zxyyywhmhlx) {
        this.zxyyywhmhlx = zxyyywhmhlx;
    }

    public String getZxyydbr() {
        return zxyydbr;
    }

    public void setZxyydbr(String zxyydbr) {
        this.zxyydbr = zxyydbr;
    }

    public String getZxyydbrmhlx() {
        return zxyydbrmhlx;
    }

    public void setZxyydbrmhlx(String zxyydbrmhlx) {
        this.zxyydbrmhlx = zxyydbrmhlx;
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

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    public String getYcqzhmhlx() {
        return ycqzhmhlx;
    }

    public void setYcqzhmhlx(String ycqzhmhlx) {
        this.ycqzhmhlx = ycqzhmhlx;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public List<String> getSlbhList() {
        return slbhList;
    }

    public void setSlbhList(List<String> slbhList) {
        this.slbhList = slbhList;
    }

    public List<String> getQxdmList() {
        return qxdmList;
    }

    public void setQxdmList(List<String> qxdmList) {
        this.qxdmList = qxdmList;
    }

    public List<Integer> getQsztList() {
        return qsztList;
    }

    public void setQsztList(List<Integer> qsztList) {
        this.qsztList = qsztList;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public String getBdcdywybhmhlx() {
        return bdcdywybhmhlx;
    }

    public void setBdcdywybhmhlx(String bdcdywybhmhlx) {
        this.bdcdywybhmhlx = bdcdywybhmhlx;
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

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcYyQO.class.getSimpleName() + "[", "]")
                .add("bdcdyh='" + bdcdyh + "'")
                .add("bdcdyhmhlx='" + bdcdyhmhlx + "'")
                .add("qlr='" + qlr + "'")
                .add("xmid='" + xmid + "'")
                .add("qlrmc=" + Arrays.toString(qlrmc))
                .add("qlrmcmhlx='" + qlrmcmhlx + "'")
                .add("qlrzjh=" + Arrays.toString(qlrzjh))
                .add("qlrzjhmhlx='" + qlrzjhmhlx + "'")
                .add("zl='" + zl + "'")
                .add("zlmhlx='" + zlmhlx + "'")
                .add("bdcqzh='" + bdcqzh + "'")
                .add("bdcqzhmhlx='" + bdcqzhmhlx + "'")
                .add("yxtcqzh='" + yxtcqzh + "'")
                .add("yxtcqzhmhlx='" + yxtcqzhmhlx + "'")
                .add("ywrmc=" + Arrays.toString(ywrmc))
                .add("ywrmcmhlx='" + ywrmcmhlx + "'")
                .add("ywrzjh=" + Arrays.toString(ywrzjh))
                .add("ywrzjhmhlx='" + ywrzjhmhlx + "'")
                .add("dbr='" + dbr + "'")
                .add("dbrmhlx='" + dbrmhlx + "'")
                .add("zxyyywh='" + zxyyywh + "'")
                .add("zxyyywhmhlx='" + zxyyywhmhlx + "'")
                .add("zxyydbr='" + zxyydbr + "'")
                .add("zxyydbrmhlx='" + zxyydbrmhlx + "'")
                .add("slbh='" + slbh + "'")
                .add("slbhmhlx='" + slbhmhlx + "'")
                .add("ycqzh='" + ycqzh + "'")
                .add("ycqzhmhlx='" + ycqzhmhlx + "'")
                .add("type='" + type + "'")
                .add("qszt=" + qszt)
                .add("clientIp='" + clientIp + "'")
                .add("slbhList=" + slbhList)
                .add("qxdmList=" + qxdmList)
                .add("qsztList=" + qsztList)
                .add("zslx=" + zslx)
                .add("bdcdywybh='" + bdcdywybh + "'")
                .add("bdcdywybhmhlx='" + bdcdywybhmhlx + "'")
                .add("zhlsh='" + zhlsh + "'")
                .add("zhlshmhlx='" + zhlshmhlx + "'")
                .add("zsyzh='" + zsyzh + "'")
                .add("zsyzhmhlx='" + zsyzhmhlx + "'")
                .toString();
    }
}
