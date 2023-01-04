package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-10-23
 * @description 不动产自助打证requestDTO
 */
@Api(value = "BdcZzdzNtRequestDTO", description = "不动产自助打证requestDTO(南通)")
public class BdcZzdzNtRequestDTO {

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    @ApiModelProperty(value = "证书号")
    private String zsh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "是否查询过渡（1：查询， 0：不查询，默认为0）")
    private String queryGd;

    @ApiModelProperty(value = "是否查询已打印数据（1：查询，0：不查询，默认为0）")
    private String queryPrint;

    @ApiModelProperty(value = "是否查询历史（1：查询， 0：不查询，默认为0）")
    private String queryLs;

    @ApiModelProperty(value = "证书类型")
    private String zstype;

    @ApiModelProperty(value = "操作人身份证号")
    private String idNo;

    @ApiModelProperty(value = "自助打证机编号")
    private String zzjId;

    @ApiModelProperty(value = "自助打证机用户名")
    private String zzjUsername;

    @ApiModelProperty(value = "不动产证明印刷号")
    private String publishNo;

    @ApiModelProperty(value = "打印时间")
    private String printDate;

    @ApiModelProperty(value = "证明权力或事项")
    private String zmql;

    @ApiModelProperty(value = "登记系统业务流水号")
    private String transtionId;

    @ApiModelProperty(value = "领证人证件号")
    private String lzrzjh;

    @ApiModelProperty(value = "领证人")
    private String lzr;

    @ApiModelProperty(value = "领证方式")
    private String lzfs;

    @ApiModelProperty(value = "领证人签名图片Base64字符")
    private String lzrqz;

    @ApiModelProperty(value = "证书id")
    private String zsid;

    @ApiModelProperty(value = "发证日期")
    private String fzrq;

    @ApiModelProperty(value = "申请类型")
    private String sqlx;

    @ApiModelProperty(value = "需要排除的wdid")
    private String excludeWdid;

    @ApiModelProperty(value = "是否验证lzrzjh跟slbh一致")
    private String isCheckSlbhAndLzrzjh;

    @ApiModelProperty(value = "检查流程节点信息")
    private String checkActivityName;

    @ApiModelProperty(value = "需要排除的wdid集合")
    private List<String> excludeWdidList;


    public String getLzrqz() {
        return lzrqz;
    }

    public void setLzrqz(String lzrqz) {
        this.lzrqz = lzrqz;
    }

    public String getZzjUsername() {
        return zzjUsername;
    }

    public void setZzjUsername(String zzjUsername) {
        this.zzjUsername = zzjUsername;
    }

    public String getLzfs() {
        return lzfs;
    }

    public void setLzfs(String lzfs) {
        this.lzfs = lzfs;
    }

    public String getSqlx() {
        return sqlx;
    }

    public void setSqlx(String sqlx) {
        this.sqlx = sqlx;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrZjh) {
        this.qlrzjh = qlrZjh;
    }

    public String getZsh() {
        return zsh;
    }

    public void setZsh(String zsh) {
        this.zsh = zsh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getQueryGd() {
        return StringUtils.isNotBlank(queryGd) ? queryGd : "0";
    }

    public void setQueryGd(String queryGd) {
        this.queryGd = queryGd;
    }

    public String getQueryPrint() {
        return StringUtils.isNotBlank(queryPrint) ? queryPrint : "0";
    }

    public void setQueryPrint(String queryPrint) {
        this.queryPrint = queryPrint;
    }

    public String getQueryLs() {
        return StringUtils.isNotBlank(queryLs) ? queryLs : "0";
    }

    public void setQueryLs(String queryLs) {
        this.queryLs = queryLs;
    }

    public String getZstype() {
        return zstype;
    }

    public void setZstype(String zstype) {
        this.zstype = zstype;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getZzjId() {
        return zzjId;
    }

    public void setZzjId(String zzjId) {
        this.zzjId = zzjId;
    }

    public String getPublishNo() {
        return publishNo;
    }

    public void setPublishNo(String publishNo) {
        this.publishNo = publishNo;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public String getZmql() {
        return zmql;
    }

    public void setZmql(String zmql) {
        this.zmql = zmql;
    }

    public String getTranstionId() {
        return transtionId;
    }

    public void setTranstionId(String transtionId) {
        this.transtionId = transtionId;
    }

    public String getLzrzjh() {
        return lzrzjh;
    }

    public void setLzrzjh(String lzrzjh) {
        this.lzrzjh = lzrzjh;
    }

    public String getLzr() {
        return lzr;
    }

    public void setLzr(String lzr) {
        this.lzr = lzr;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public String getFzrq() {
        return fzrq;
    }

    public void setFzrq(String lzrq) {
        this.fzrq = lzrq;
    }

    public String getIsCheckSlbhAndLzrzjh() {
        return isCheckSlbhAndLzrzjh;
    }

    public void setIsCheckSlbhAndLzrzjh(String isCheckSlbhAndLzrzjh) {
        this.isCheckSlbhAndLzrzjh = isCheckSlbhAndLzrzjh;
    }

    public String getCheckActivityName() {
        return checkActivityName;
    }

    public void setCheckActivityName(String checkActivityName) {
        this.checkActivityName = checkActivityName;
    }

    public String getExcludeWdid() {
        return excludeWdid;
    }

    public void setExcludeWdid(String excludeWdid) {
        this.excludeWdid = excludeWdid;
    }

    public List<String> getExcludeWdidList() {
        return excludeWdidList;
    }

    public void setExcludeWdidList(List<String> excludeWdidList) {
        this.excludeWdidList = excludeWdidList;
    }

    @Override
    public String toString() {
        return "BdcZzdzNtRequestDTO{" +
                "qlr='" + qlr + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", zsh='" + zsh + '\'' +
                ", zl='" + zl + '\'' +
                ", zzjUsername='" + zzjUsername + '\'' +
                ", lzfs='" + lzfs + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", queryGd='" + queryGd + '\'' +
                ", queryPrint='" + queryPrint + '\'' +
                ", queryLs='" + queryLs + '\'' +
                ", zstype='" + zstype + '\'' +
                ", idNo='" + idNo + '\'' +
                ", zzjId='" + zzjId + '\'' +
                ", publishNo='" + publishNo + '\'' +
                ", printDate='" + printDate + '\'' +
                ", zmql='" + zmql + '\'' +
                ", transtionId='" + transtionId + '\'' +
                ", lzrzjh='" + lzrzjh + '\'' +
                ", lzr='" + lzr + '\'' +
                ", zsid='" + zsid + '\'' +
                ", fzrq='" + fzrq + '\'' +
                ", sqlx='" + sqlx + '\'' +
                ", excludeWdid='" + excludeWdid + '\'' +
                ", isCheckSlbhAndLzrzjh='" + isCheckSlbhAndLzrzjh + '\'' +
                ", checkActivityName='" + checkActivityName + '\'' +
                ", excludeWdidList=" + excludeWdidList +
                '}';
    }
}
