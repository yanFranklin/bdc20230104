package cn.gtmap.realestate.etl.core.domian.huaian;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2017/10/24
 * @description 南通证照信息表
 */
@Table(name = "t_zz_zm_info")
public class TZzZmInfo {
    @Id
    private String id;//主键
    private String mouldId;//证照清单标识
    private String dzzzNo;
    private String zzBh;
    private Date bzDate;
    private Date starTime;
    private Date endTime;
    private String deptName;
    private String userName;
    private String userType;
    private String userZjType;
    private String userN0;
    private String zzType;
    private String individuation;
    private byte[] zzFile;
    private Date createDate;
    private String state;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUserZjType() {
        return userZjType;
    }

    public void setUserZjType(String userZjType) {
        this.userZjType = userZjType;
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

    public byte[] getZzFile() {
        return zzFile;
    }

    public void setZzFile(byte[] zzFile) {
        this.zzFile = zzFile;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
