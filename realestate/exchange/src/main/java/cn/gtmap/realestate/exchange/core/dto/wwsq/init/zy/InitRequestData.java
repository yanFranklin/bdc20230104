package cn.gtmap.realestate.exchange.core.dto.wwsq.init.zy;

import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.InitGhxx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.InitSjrxx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.dydj.InitRequestSfxx;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-11
 * @description 创建产权类接口的请求结构体
 */
@IgnoreCast(ignoreNum = 1)
public class InitRequestData {

    @NotBlank
    private String ywh;

    @NotBlank
    private String sqdjlx;

    private String zl;
    private String dwdm;

    private String fczh;

    @NotBlank
    private String bdcdyh;

    private String mj;

    private String sfdy;

    private String sfcf;

    private String gyfs;

    private String barq;

    private String htqdrq;

    private String jyjg;

    private String htbh;

    private String fj;

    private List<InitRequestGxrxx> gxrxx;

    // 婚姻状态
    private String htzt;

    // 预约部门编码
    @NotBlank
    private String yybmbm;

    private String bdcdybh;

    private String zsly;

    // 契税满两年
    private String qsmln;

    private String xmid;

    // 受理人姓名
    private String slr;

    private String cjyz;

    private String slly;

    private String tdzh;

    private String tdxmid;

    private String fkfs;
    // 是否自动转发
    private String zdzf;

    private String djyydm;

    private String djyymc;

    private String fkfsdm;

    // 是否涉税 0不涉税；1涉税
    private String sfss;

    //预审人
    private String ysr;
    // 预审时间
    private String yssj;
    // 预审提示信息
    private String ystsxx;

    // 预约开始时间
    private String yykssj;

    // 预约结束时间
    private String yyjssj;

    private String ghyt;

    private String ghytmc;

    private String tdsyqlx;

    private String tdsyqlxmc;

    private String fwjg;

    private String fwjgmc;

    // 备注
    private String bz;
    // 房源编码
    private String fybm;
    // 收件人信息
    private List<InitSjrxx> wlxx;

    @ApiModelProperty(value = "收费信息")
    private List<InitRequestSfxx> sfxx;

    // 申请人id
    private String sqrid;

    private InitGhxx ghxx;

    private String roomid;

    private String tgjds;

    //不含增值税金额  购房发票单位:万元
    private String bhzzsje;
    //增值税金额  购房发票单位:万元
    private String zzsje;

    //含增值税金额 购房发票单位:万元
    private String hzzsje;

    //是否分别持证
    private String sffbcz;

    private String xzqdm;

    //抵押方式
    private String dyfs;

    //抵押方式名称
    private String dyfw;

    //权籍管理代码
    private String qjgldm;

    // 领证人信息
    private List<InitRequestLzr> lzrxx;

    @ApiModelProperty(value = "是否主房")
    private String sfzf;

    @ApiModelProperty("申请材料提交方式")
    private String sqcltjfs;

    @ApiModelProperty("土地用途")
    private String tdyt;
    @ApiModelProperty("土地用途名称")
    private String tdytmc;
    @ApiModelProperty("土地用途2")
    private String tdyt2;
    @ApiModelProperty("土地用途名称2")
    private String tdytmc2;
    @ApiModelProperty("土地用途3")
    private String tdyt3;
    @ApiModelProperty("土地用途名称3")
    private String tdytmc3;


    @ApiModelProperty("土地使用权起始时间")
    private String qsrq;
    @ApiModelProperty("土地使用权结束时间")
    private String jsrq;
    @ApiModelProperty("土地使用权起始时间")
    private String qsrq2;
    @ApiModelProperty("土地使用权结束时间")
    private String jsrq2;
    @ApiModelProperty("土地使用权起始时间")
    private String qsrq3;
    @ApiModelProperty("土地使用权结束时间")
    private String jsrq3;

    @ApiModelProperty("税务状态")
    private String swzt;

    @ApiModelProperty("是否税费托管")
    private String sfsftg;

    @ApiModelProperty("申请部门名称")
    private String sqbmmc;

    @ApiModelProperty("业务类型")
    private String ywlx;

    public String getYwlx() {
        return ywlx;
    }

    public void setYwlx(String ywlx) {
        this.ywlx = ywlx;
    }

    public String getSqbmmc() {
        return sqbmmc;
    }

    public void setSqbmmc(String sqbmmc) {
        this.sqbmmc = sqbmmc;
    }

    public String getSfsftg() {
        return sfsftg;
    }

    public void setSfsftg(String sfsftg) {
        this.sfsftg = sfsftg;
    }

    public String getSwzt() {
        return swzt;
    }

    public void setSwzt(String swzt) {
        this.swzt = swzt;
    }

    public List<InitRequestSfxx> getSfxx() {
        return sfxx;
    }

    public void setSfxx(List<InitRequestSfxx> sfxx) {
        this.sfxx = sfxx;
    }

    public String getDyfs() {
        return dyfs;
    }

    public void setDyfs(String dyfs) {
        this.dyfs = dyfs;
    }

    public String getDyfw() {
        return dyfw;
    }

    public void setDyfw(String dyfw) {
        this.dyfw = dyfw;
    }

    public String getTdyt() {
        return tdyt;
    }

    public void setTdyt(String tdyt) {
        this.tdyt = tdyt;
    }

    public String getTdytmc() {
        return tdytmc;
    }

    public void setTdytmc(String tdytmc) {
        this.tdytmc = tdytmc;
    }

    public String getTdyt2() {
        return tdyt2;
    }

    public void setTdyt2(String tdyt2) {
        this.tdyt2 = tdyt2;
    }

    public String getTdytmc2() {
        return tdytmc2;
    }

    public void setTdytmc2(String tdytmc2) {
        this.tdytmc2 = tdytmc2;
    }

    public String getTdyt3() {
        return tdyt3;
    }

    public void setTdyt3(String tdyt3) {
        this.tdyt3 = tdyt3;
    }

    public String getTdytmc3() {
        return tdytmc3;
    }

    public void setTdytmc3(String tdytmc3) {
        this.tdytmc3 = tdytmc3;
    }

    public String getQsrq() {
        return qsrq;
    }

    public void setQsrq(String qsrq) {
        this.qsrq = qsrq;
    }

    public String getJsrq() {
        return jsrq;
    }

    public void setJsrq(String jsrq) {
        this.jsrq = jsrq;
    }

    public String getQsrq2() {
        return qsrq2;
    }

    public void setQsrq2(String qsrq2) {
        this.qsrq2 = qsrq2;
    }

    public String getJsrq2() {
        return jsrq2;
    }

    public void setJsrq2(String jsrq2) {
        this.jsrq2 = jsrq2;
    }

    public String getQsrq3() {
        return qsrq3;
    }

    public void setQsrq3(String qsrq3) {
        this.qsrq3 = qsrq3;
    }

    public String getJsrq3() {
        return jsrq3;
    }

    public void setJsrq3(String jsrq3) {
        this.jsrq3 = jsrq3;
    }

    public String getSqcltjfs() {
        return sqcltjfs;
    }

    public void setSqcltjfs(String sqcltjfs) {
        this.sqcltjfs = sqcltjfs;
    }

    public String getDwdm() {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }

    public String getSffbcz() {
        return sffbcz;
    }

    public void setSffbcz(String sffbcz) {
        this.sffbcz = sffbcz;
    }

    public String getBhzzsje() {
        return bhzzsje;
    }

    public void setBhzzsje(String bhzzsje) {
        this.bhzzsje = bhzzsje;
    }

    public String getZzsje() {
        return zzsje;
    }

    public void setZzsje(String zzsje) {
        this.zzsje = zzsje;
    }

    public String getHzzsje() {
        return hzzsje;
    }

    public void setHzzsje(String hzzsje) {
        this.hzzsje = hzzsje;
    }

    public String getTgjds() {
        return tgjds;
    }

    public void setTgjds(String tgjds) {
        this.tgjds = tgjds;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public InitGhxx getGhxx() {
        return ghxx;
    }

    public void setGhxx(InitGhxx ghxx) {
        this.ghxx = ghxx;
    }

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid;
    }

    public List<InitSjrxx> getWlxx() {
        return wlxx;
    }

    public void setWlxx(List<InitSjrxx> wlxx) {
        this.wlxx = wlxx;
    }

    public String getFybm() {
        return fybm;
    }

    public void setFybm(String fybm) {
        this.fybm = fybm;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public String getGhytmc() {
        return ghytmc;
    }

    public void setGhytmc(String ghytmc) {
        this.ghytmc = ghytmc;
    }

    public String getTdsyqlx() {
        return tdsyqlx;
    }

    public void setTdsyqlx(String tdsyqlx) {
        this.tdsyqlx = tdsyqlx;
    }

    public String getTdsyqlxmc() {
        return tdsyqlxmc;
    }

    public void setTdsyqlxmc(String tdsyqlxmc) {
        this.tdsyqlxmc = tdsyqlxmc;
    }

    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    public String getFwjgmc() {
        return fwjgmc;
    }

    public void setFwjgmc(String fwjgmc) {
        this.fwjgmc = fwjgmc;
    }

    public String getYykssj() {
        return yykssj;
    }

    public void setYykssj(String yykssj) {
        this.yykssj = yykssj;
    }

    public String getYyjssj() {
        return yyjssj;
    }

    public void setYyjssj(String yyjssj) {
        this.yyjssj = yyjssj;
    }

    public String getYsr() {
        return ysr;
    }

    public void setYsr(String ysr) {
        this.ysr = ysr;
    }

    public String getYssj() {
        return yssj;
    }

    public void setYssj(String yssj) {
        this.yssj = yssj;
    }

    public String getYstsxx() {
        return ystsxx;
    }

    public void setYstsxx(String ystsxx) {
        this.ystsxx = ystsxx;
    }

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
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

    public String getFczh() {
        return fczh;
    }

    public void setFczh(String fczh) {
        this.fczh = fczh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getMj() {
        return mj;
    }

    public void setMj(String mj) {
        this.mj = mj;
    }

    public String getSfdy() {
        return sfdy;
    }

    public void setSfdy(String sfdy) {
        this.sfdy = sfdy;
    }

    public String getSfcf() {
        return sfcf;
    }

    public void setSfcf(String sfcf) {
        this.sfcf = sfcf;
    }

    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public String getBarq() {
        return barq;
    }

    public void setBarq(String barq) {
        this.barq = barq;
    }

    public String getHtqdrq() {
        return htqdrq;
    }

    public void setHtqdrq(String htqdrq) {
        this.htqdrq = htqdrq;
    }

    public String getJyjg() {
        return jyjg;
    }

    public void setJyjg(String jyjg) {
        this.jyjg = jyjg;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }


    public String getHtzt() {
        return htzt;
    }

    public void setHtzt(String htzt) {
        this.htzt = htzt;
    }

    public String getYybmbm() {
        return yybmbm;
    }

    public void setYybmbm(String yybmbm) {
        this.yybmbm = yybmbm;
    }

    public String getBdcdybh() {
        return bdcdybh;
    }

    public void setBdcdybh(String bdcdybh) {
        this.bdcdybh = bdcdybh;
    }

    public String getZsly() {
        return zsly;
    }

    public void setZsly(String zsly) {
        this.zsly = zsly;
    }

    public String getQsmln() {
        return qsmln;
    }

    public void setQsmln(String qsmln) {
        this.qsmln = qsmln;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public List<InitRequestGxrxx> getGxrxx() {
        return gxrxx;
    }

    public void setGxrxx(List<InitRequestGxrxx> gxrxx) {
        this.gxrxx = gxrxx;
    }

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public String getCjyz() {
        return cjyz;
    }

    public void setCjyz(String cjyz) {
        this.cjyz = cjyz;
    }

    public String getSlly() {
        return slly;
    }

    public void setSlly(String slly) {
        this.slly = slly;
    }

    public String getTdzh() {
        return tdzh;
    }

    public void setTdzh(String tdzh) {
        this.tdzh = tdzh;
    }

    public String getTdxmid() {
        return tdxmid;
    }

    public void setTdxmid(String tdxmid) {
        this.tdxmid = tdxmid;
    }

    public String getFkfs() {
        return fkfs;
    }

    public void setFkfs(String fkfs) {
        this.fkfs = fkfs;
    }

    public String getZdzf() {
        return zdzf;
    }

    public void setZdzf(String zdzf) {
        this.zdzf = zdzf;
    }

    public String getDjyydm() {
        return djyydm;
    }

    public void setDjyydm(String djyydm) {
        this.djyydm = djyydm;
    }

    public String getDjyymc() {
        return djyymc;
    }

    public void setDjyymc(String djyymc) {
        this.djyymc = djyymc;
    }

    public String getFkfsdm() {
        return fkfsdm;
    }

    public void setFkfsdm(String fkfsdm) {
        this.fkfsdm = fkfsdm;
    }

    public String getSfss() {
        return sfss;
    }

    public void setSfss(String sfss) {
        this.sfss = sfss;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getSfzf() {
        return sfzf;
    }

    public void setSfzf(String sfzf) {
        this.sfzf = sfzf;
    }

    public List<InitRequestLzr> getLzrxx() {
        return lzrxx;
    }

    public void setLzrxx(List<InitRequestLzr> lzrxx) {
        this.lzrxx = lzrxx;
    }

    @Override
    public String toString() {
        return "InitRequestData{" +
                "ywh='" + ywh + '\'' +
                ", sqdjlx='" + sqdjlx + '\'' +
                ", zl='" + zl + '\'' +
                ", dwdm='" + dwdm + '\'' +
                ", fczh='" + fczh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", mj='" + mj + '\'' +
                ", sfdy='" + sfdy + '\'' +
                ", sfcf='" + sfcf + '\'' +
                ", gyfs='" + gyfs + '\'' +
                ", barq='" + barq + '\'' +
                ", htqdrq='" + htqdrq + '\'' +
                ", jyjg='" + jyjg + '\'' +
                ", htbh='" + htbh + '\'' +
                ", fj='" + fj + '\'' +
                ", gxrxx=" + gxrxx +
                ", htzt='" + htzt + '\'' +
                ", yybmbm='" + yybmbm + '\'' +
                ", bdcdybh='" + bdcdybh + '\'' +
                ", zsly='" + zsly + '\'' +
                ", qsmln='" + qsmln + '\'' +
                ", xmid='" + xmid + '\'' +
                ", slr='" + slr + '\'' +
                ", cjyz='" + cjyz + '\'' +
                ", slly='" + slly + '\'' +
                ", tdzh='" + tdzh + '\'' +
                ", tdxmid='" + tdxmid + '\'' +
                ", fkfs='" + fkfs + '\'' +
                ", zdzf='" + zdzf + '\'' +
                ", djyydm='" + djyydm + '\'' +
                ", djyymc='" + djyymc + '\'' +
                ", fkfsdm='" + fkfsdm + '\'' +
                ", sfss='" + sfss + '\'' +
                ", ysr='" + ysr + '\'' +
                ", yssj='" + yssj + '\'' +
                ", ystsxx='" + ystsxx + '\'' +
                ", yykssj='" + yykssj + '\'' +
                ", yyjssj='" + yyjssj + '\'' +
                ", ghyt='" + ghyt + '\'' +
                ", ghytmc='" + ghytmc + '\'' +
                ", tdsyqlx='" + tdsyqlx + '\'' +
                ", tdsyqlxmc='" + tdsyqlxmc + '\'' +
                ", fwjg='" + fwjg + '\'' +
                ", fwjgmc='" + fwjgmc + '\'' +
                ", bz='" + bz + '\'' +
                ", fybm='" + fybm + '\'' +
                ", wlxx=" + wlxx +
                ", sfxx=" + sfxx +
                ", sqrid='" + sqrid + '\'' +
                ", ghxx=" + ghxx +
                ", roomid='" + roomid + '\'' +
                ", tgjds='" + tgjds + '\'' +
                ", bhzzsje='" + bhzzsje + '\'' +
                ", zzsje='" + zzsje + '\'' +
                ", hzzsje='" + hzzsje + '\'' +
                ", sffbcz='" + sffbcz + '\'' +
                ", xzqdm='" + xzqdm + '\'' +
                ", dyfs='" + dyfs + '\'' +
                ", dyfw='" + dyfw + '\'' +
                ", qjgldm='" + qjgldm + '\'' +
                ", lzrxx=" + lzrxx +
                ", sfzf='" + sfzf + '\'' +
                ", sqcltjfs='" + sqcltjfs + '\'' +
                ", tdyt='" + tdyt + '\'' +
                ", tdytmc='" + tdytmc + '\'' +
                ", tdyt2='" + tdyt2 + '\'' +
                ", tdytmc2='" + tdytmc2 + '\'' +
                ", tdyt3='" + tdyt3 + '\'' +
                ", tdytmc3='" + tdytmc3 + '\'' +
                ", qsrq='" + qsrq + '\'' +
                ", jsrq='" + jsrq + '\'' +
                ", qsrq2='" + qsrq2 + '\'' +
                ", jsrq2='" + jsrq2 + '\'' +
                ", qsrq3='" + qsrq3 + '\'' +
                ", jsrq3='" + jsrq3 + '\'' +
                ", swzt='" + swzt + '\'' +
                ", sfsftg='" + sfsftg + '\'' +
                ", sqbmmc='" + sqbmmc + '\'' +
                ", ywlx='" + ywlx + '\'' +
                '}';
    }
}
