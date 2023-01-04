package cn.gtmap.realestate.exchange.core.domain.yzw.yancheng;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/23
 * @description 结果证照信息表
 */
@Table(name = "T_BM_JGZZ")
public class TBmJgzzDO {

    @Id
    @ApiModelProperty(value = "唯一标识")
    private String onlymark;

    @ApiModelProperty(value = "办件编号")
    private String caseno;

    @ApiModelProperty(value = "证照实施清单主键")
    private String mouldId;

    @ApiModelProperty(value = "电子证照编号")
    private String dzzzNo;

    @ApiModelProperty(value = "证照编号")
    private String zzBh;

    @ApiModelProperty(value = "颁证时间")
    private Date bzDate;

    @ApiModelProperty(value = "有效期（起始）")
    private Date starTime;

    @ApiModelProperty(value = "有效期（截止）")
    private Date endTime;

    @ApiModelProperty(value = "颁证单位")
    private String deptName;

    @ApiModelProperty(value = "持证者")
    private String userName;

    @ApiModelProperty(value = "持证者类型")
    private String userType;

    @ApiModelProperty(value = "持证者证件类型")
    private String userPaperType;

    @ApiModelProperty(value = "持证者号码")
    private String userN0;

    @ApiModelProperty(value = "证照分类")
    private String zzType;

    @ApiModelProperty(value = "xml证照封装信息")
    private String individuation;

    @ApiModelProperty(value = "证照文件")
    private String zzFile;

    @ApiModelProperty(value = "种类")
    private String xkZl;

    @ApiModelProperty(value = "审批类别")
    private String xkSplb;

    @ApiModelProperty(value = "许可内容")
    private String xkNr;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "同步时间")
    private Date syncdatetime;

    @ApiModelProperty(value = "同步标识")
    private Integer syncsign;

    @ApiModelProperty(value = "同步错误信息")
    private String syncerrormsg;

    public String getOnlymark() {
        return onlymark;
    }

    public void setOnlymark(String onlymark) {
        this.onlymark = onlymark;
    }

    public String getCaseno() {
        return caseno;
    }

    public void setCaseno(String caseno) {
        this.caseno = caseno;
    }

    public String getMouldId() {
        return mouldId;
    }

    public void setMouldId(String mouldId) {
        this.mouldId = mouldId;
    }

    public String getDzzzNo() {
        return dzzzNo;
    }

    public void setDzzzNo(String dzzzNo) {
        this.dzzzNo = dzzzNo;
    }

    public String getZzBh() {
        return zzBh;
    }

    public void setZzBh(String zzBh) {
        this.zzBh = zzBh;
    }

    public Date getBzDate() {
        return bzDate;
    }

    public void setBzDate(Date bzDate) {
        this.bzDate = bzDate;
    }

    public Date getStarTime() {
        return starTime;
    }

    public void setStarTime(Date starTime) {
        this.starTime = starTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserPaperType() {
        return userPaperType;
    }

    public void setUserPaperType(String userPaperType) {
        this.userPaperType = userPaperType;
    }

    public String getUserN0() {
        return userN0;
    }

    public void setUserN0(String userN0) {
        this.userN0 = userN0;
    }

    public String getZzType() {
        return zzType;
    }

    public void setZzType(String zzType) {
        this.zzType = zzType;
    }

    public String getIndividuation() {
        return individuation;
    }

    public void setIndividuation(String individuation) {
        this.individuation = individuation;
    }

    public String getZzFile() {
        return zzFile;
    }

    public void setZzFile(String zzFile) {
        this.zzFile = zzFile;
    }

    public String getXkZl() {
        return xkZl;
    }

    public void setXkZl(String xkZl) {
        this.xkZl = xkZl;
    }

    public String getXkSplb() {
        return xkSplb;
    }

    public void setXkSplb(String xkSplb) {
        this.xkSplb = xkSplb;
    }

    public String getXkNr() {
        return xkNr;
    }

    public void setXkNr(String xkNr) {
        this.xkNr = xkNr;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getSyncdatetime() {
        return syncdatetime;
    }

    public void setSyncdatetime(Date syncdatetime) {
        this.syncdatetime = syncdatetime;
    }

    public Integer getSyncsign() {
        return syncsign;
    }

    public void setSyncsign(Integer syncsign) {
        this.syncsign = syncsign;
    }

    public String getSyncerrormsg() {
        return syncerrormsg;
    }

    public void setSyncerrormsg(String syncerrormsg) {
        this.syncerrormsg = syncerrormsg;
    }
}
