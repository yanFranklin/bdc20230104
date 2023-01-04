package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-12
 * @description 外网申请 创建项目 请求实体
 */
public class WwsqCjBdcXmRequestDTO {

    // 是否需要规则验证
    private boolean gzyz;

    // 受理角色标志
    private String slRoleCode;

    // 受理角色标志是否不过滤区县代码(true 不过滤 false 过滤)
    private boolean sljsbglqxdm;

    // 受理信息实体
    private BdcSlxxDTO bdcSlxxDTO;

    // 是否涉税
    private boolean sfss;

    // 是否办结一体化流程 且自动创建业务流程
    private boolean bjYthlc;

    // 数据来源
    private String sjly;

    // 是否进入认领列表
    // 当 进入认领列表为true 时 不管slr是否有值 都 进入认领列表
    // 如果进入认领列表为false 则 判断 slr是否有值 有值指定到受理人名下  没有值仍然进入认领列表
    private boolean jrrllb;

    @ApiModelProperty(value = "是否自动转发登簿 1:是，0：否")
    private String sfzdzfdb;

    @ApiModelProperty(value = "是否自动转发办结 1:是，0：否")
    private String sfzdbj;
    @ApiModelProperty(value = "是否自动转发 1:是，0：否")
    private String zdzf;
    @ApiModelProperty(value = "用户行政区代码")
    private String yhxzqdm;
    @ApiModelProperty(value = "领证方式")
    private String lzfs;

    @ApiModelProperty(value = "自动转发节点受理人")
    private String zdzfslr;

    @ApiModelProperty(value = "复审人")
    private String fsr;

    @ApiModelProperty(value = "登簿人")
    private String dbr;

    @ApiModelProperty(value = "是否发送短信")
    private boolean sendmsg;

    public String getFsr() {
        return fsr;
    }

    public void setFsr(String fsr) {
        this.fsr = fsr;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public String getYhxzqdm() {
        return yhxzqdm;
    }

    public void setYhxzqdm(String yhxzqdm) {
        this.yhxzqdm = yhxzqdm;
    }

    public String getZdzf() {
        return zdzf;
    }

    public void setZdzf(String zdzf) {
        this.zdzf = zdzf;
    }

    public String getSfzdbj() {
        return sfzdbj;
    }

    public void setSfzdbj(String sfzdbj) {
        this.sfzdbj = sfzdbj;
    }

    public boolean isGzyz() {
        return gzyz;
    }

    public void setGzyz(boolean gzyz) {
        this.gzyz = gzyz;
    }

    public BdcSlxxDTO getBdcSlxxDTO() {
        return bdcSlxxDTO;
    }

    public void setBdcSlxxDTO(BdcSlxxDTO bdcSlxxDTO) {
        this.bdcSlxxDTO = bdcSlxxDTO;
    }

    public String getSlRoleCode() {
        return slRoleCode;
    }

    public void setSlRoleCode(String slRoleCode) {
        this.slRoleCode = slRoleCode;
    }

    public boolean isSfss() {
        return sfss;
    }

    public void setSfss(boolean sfss) {
        this.sfss = sfss;
    }

    public boolean isBjYthlc() {
        return bjYthlc;
    }

    public void setBjYthlc(boolean bjYthlc) {
        this.bjYthlc = bjYthlc;
    }

    public boolean isJrrllb() {
        return jrrllb;
    }

    public void setJrrllb(boolean jrrllb) {
        this.jrrllb = jrrllb;
    }

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }

    public String getSfzdzfdb() {
        return sfzdzfdb;
    }

    public void setSfzdzfdb(String sfzdzfdb) {
        this.sfzdzfdb = sfzdzfdb;
    }

    public boolean isSljsbglqxdm() {
        return sljsbglqxdm;
    }

    public void setSljsbglqxdm(boolean sljsbglqxdm) {
        this.sljsbglqxdm = sljsbglqxdm;
    }

    public String getZdzfslr() {
        return zdzfslr;
    }

    public void setZdzfslr(String zdzfslr) {
        this.zdzfslr = zdzfslr;
    }

    public boolean isSendmsg() {
        return sendmsg;
    }

    public void setSendmsg(boolean sendmsg) {
        this.sendmsg = sendmsg;
    }

    public String getLzfs() {
        return lzfs;
    }

    public void setLzfs(String lzfs) {
        this.lzfs = lzfs;
    }

    @Override
    public String toString() {
        return "WwsqCjBdcXmRequestDTO{" +
                "gzyz=" + gzyz +
                ", slRoleCode='" + slRoleCode + '\'' +
                ", sljsbglqxdm=" + sljsbglqxdm +
                ", bdcSlxxDTO=" + bdcSlxxDTO +
                ", sfss=" + sfss +
                ", bjYthlc=" + bjYthlc +
                ", sjly='" + sjly + '\'' +
                ", jrrllb=" + jrrllb +
                ", sfzdzfdb='" + sfzdzfdb + '\'' +
                ", sfzdbj='" + sfzdbj + '\'' +
                ", zdzf='" + zdzf + '\'' +
                ", yhxzqdm='" + yhxzqdm + '\'' +
                ", lzfs='" + lzfs + '\'' +
                ", zdzfslr='" + zdzfslr + '\'' +
                ", fsr='" + fsr + '\'' +
                ", dbr='" + dbr + '\'' +
                ", sendmsg=" + sendmsg +
                '}';
    }
}
