package cn.gtmap.realestate.exchange.core.dto.wwsq.init.dydj;

import cn.gtmap.realestate.common.core.dto.exchange.wwsq.GltdzxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.InitZwrXxDTO;
import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.InitFjxxDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.InitSjrxx;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-12
 * @description 抵押登记 初始化 请求DATA 结构体
 */
@IgnoreCast(ignoreNum = 3)
public class InitRequestData {

    private String dyhth;

    private String cqzh;

    //    @NotBlank
    private String zsxmid;

    private String zsly;

    @NotBlank
    private String bjbh;

    @NotBlank
    private String bdcdyh;

    @NotBlank
    private String yybmbm;

    private String bdcdybh;

    @NotBlank
    private String sqdjlx;

    private String zl;

    private String slr;

    private List<InitRequestQlr> qlr;

    private List<InitRequestDyQlr> dyqlr;

    @ApiModelProperty(value = "收费信息")
    private List<InitRequestSfxx> sfxx;

    private InitRequestDyxx dyxx;

    private List<InitFjxxDTO> fjxx;

    private String cjyz;

    private String slly;

    private String djyydm;

    private String djyymc;

    // 0不涉税；1涉税
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

    // 收件人信息
    private List<InitSjrxx> wlxx;
    // 申请人id
    private String sqrid;

    private String zdzf;

    private String bdcpgjg;

    @ApiModelProperty(value = "跳过节点数")
    private String tgjds;

    private String roomid;

    //41873 【南通大市】登记系统中流程创建接口对xzqdm参数做处理
    private String xzqdm;

    /**
     * 【35802】
     */
    @ApiModelProperty(value = "债务人信息")
    private List<InitZwrXxDTO> zwrxx;

    /**
     * 36673 【盐城】抵押首次创建接口支持房产证和土地证一并创建
     */
    @ApiModelProperty(value = "关联土地证信息")
    private List<GltdzxxDTO> gltdzxx;

    /**
     * 37562 【南通大市】互联网+不动产交互需求
     */
    @ApiModelProperty(value = "是否主房")
    private String sfzf;


    @ApiModelProperty(value = "登簿人")
    private String dbr;

    @ApiModelProperty(value = "复审人")
    private String fsr;

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

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public String getFsr() {
        return fsr;
    }

    public void setFsr(String fsr) {
        this.fsr = fsr;
    }

    public List<InitRequestSfxx> getSfxx() {
        return sfxx;
    }

    public void setSfxx(List<InitRequestSfxx> sfxx) {
        this.sfxx = sfxx;
    }

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }

    public String getSfzf() {
        return sfzf;
    }

    public void setSfzf(String sfzf) {
        this.sfzf = sfzf;
    }

    public List<GltdzxxDTO> getGltdzxx() {
        return gltdzxx;
    }

    public void setGltdzxx(List<GltdzxxDTO> gltdzxx) {
        this.gltdzxx = gltdzxx;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getBdcpgjg() {
        return bdcpgjg;
    }

    public void setBdcpgjg(String bdcpgjg) {
        this.bdcpgjg = bdcpgjg;
    }

    public String getZdzf() {
        return zdzf;
    }

    public void setZdzf(String zdzf) {
        this.zdzf = zdzf;
    }

    public List<InitSjrxx> getWlxx() {
        return wlxx;
    }

    public void setWlxx(List<InitSjrxx> wlxx) {
        this.wlxx = wlxx;
    }

    public String getDyhth() {
        return dyhth;
    }

    public void setDyhth(String dyhth) {
        this.dyhth = dyhth;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getZsxmid() {
        return zsxmid;
    }

    public void setZsxmid(String zsxmid) {
        this.zsxmid = zsxmid;
    }

    public String getZsly() {
        return zsly;
    }

    public void setZsly(String zsly) {
        this.zsly = zsly;
    }

    public String getBjbh() {
        return bjbh;
    }

    public void setBjbh(String bjbh) {
        this.bjbh = bjbh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
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

    public List<InitRequestQlr> getQlr() {
        return qlr;
    }

    public void setQlr(List<InitRequestQlr> qlr) {
        this.qlr = qlr;
    }

    public InitRequestDyxx getDyxx() {
        return dyxx;
    }

    public void setDyxx(InitRequestDyxx dyxx) {
        this.dyxx = dyxx;
    }

    public List<InitRequestDyQlr> getDyqlr() {
        return dyqlr;
    }

    public void setDyqlr(List<InitRequestDyQlr> dyqlr) {
        this.dyqlr = dyqlr;
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

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public List<InitFjxxDTO> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<InitFjxxDTO> fjxx) {
        this.fjxx = fjxx;
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

    public String getSfss() {
        return sfss;
    }

    public void setSfss(String sfss) {
        this.sfss = sfss;
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

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid;
    }

    public String getTgjds() {
        return tgjds;
    }

    public void setTgjds(String tgjds) {
        this.tgjds = tgjds;
    }

    public List<InitZwrXxDTO> getZwrxx() {
        return zwrxx;
    }

    public void setZwrxx(List<InitZwrXxDTO> zwrxx) {
        this.zwrxx = zwrxx;
    }

    @Override
    public String toString() {
        return "InitRequestData{" +
                "dyhth='" + dyhth + '\'' +
                ", cqzh='" + cqzh + '\'' +
                ", zsxmid='" + zsxmid + '\'' +
                ", zsly='" + zsly + '\'' +
                ", bjbh='" + bjbh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", yybmbm='" + yybmbm + '\'' +
                ", bdcdybh='" + bdcdybh + '\'' +
                ", sqdjlx='" + sqdjlx + '\'' +
                ", zl='" + zl + '\'' +
                ", slr='" + slr + '\'' +
                ", qlr=" + qlr +
                ", dyqlr=" + dyqlr +
                ", sfxx=" + sfxx +
                ", dyxx=" + dyxx +
                ", fjxx=" + fjxx +
                ", cjyz='" + cjyz + '\'' +
                ", slly='" + slly + '\'' +
                ", djyydm='" + djyydm + '\'' +
                ", djyymc='" + djyymc + '\'' +
                ", sfss='" + sfss + '\'' +
                ", ysr='" + ysr + '\'' +
                ", yssj='" + yssj + '\'' +
                ", ystsxx='" + ystsxx + '\'' +
                ", yykssj='" + yykssj + '\'' +
                ", yyjssj='" + yyjssj + '\'' +
                ", wlxx=" + wlxx +
                ", sqrid='" + sqrid + '\'' +
                ", zdzf='" + zdzf + '\'' +
                ", bdcpgjg='" + bdcpgjg + '\'' +
                ", tgjds='" + tgjds + '\'' +
                ", roomid='" + roomid + '\'' +
                ", xzqdm='" + xzqdm + '\'' +
                ", zwrxx=" + zwrxx +
                ", gltdzxx=" + gltdzxx +
                ", sfzf='" + sfzf + '\'' +
                ", dbr='" + dbr + '\'' +
                ", fsr='" + fsr + '\'' +
                ", sqbmmc='" + sqbmmc + '\'' +
                ", ywlx='" + ywlx + '\'' +
                '}';
    }
}
