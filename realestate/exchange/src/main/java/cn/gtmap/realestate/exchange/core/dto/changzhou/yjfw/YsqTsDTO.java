package cn.gtmap.realestate.exchange.core.dto.changzhou.yjfw;

import com.alibaba.fastjson.annotation.JSONField;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2021/3/24 9:59
 * @description 易家服务
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ROOT")
public class YsqTsDTO {

    // 申请主键
    @JSONField(name = "PK_APPLY")
    private String PK_APPLY;

    // 申请单号
    @JSONField(name = "APPLYNO")
    private String APPLYNO;

    // 申请单号，格式为 YYYYMMDDHHMMSS（年月日时分秒）。如：20160911101011
    @JSONField(name = "APPLYTIME")
    private String APPLYTIME;

    // 受理编号
    @JSONField(name = "BUSINO")
    private String BUSINO;

    // 房屋坐落
    @JSONField(name = "LOCATION")
    private String LOCATION;

    // 土地用途
    @JSONField(name = "LANDUSE")
    private String LANDUSE;

    // 房屋用途
    @JSONField(name = "HOUSEUSE")
    private String HOUSEUSE;

    // 街道信息
    @JSONField(name = "STREET")
    private String STREET;

    // 客户名称（原）
    @JSONField(name = "OLDCTM")
    private String OLDCTM;

    // 证件号（原）
    @JSONField(name = "OLDID")
    private String OLDID;

    // 客户电话（原
    @JSONField(name = "OLDTEL")
    private String OLDTEL;

    // 客户签字图片（原）
    @JSONField(name = "OLDPIC")
    private String OLDPIC;

    // 客户名称（新）
    @JSONField(name = "NEWCTM")
    private String NEWCTM;

    // 证件号（新）
    @JSONField(name = "NEWID")
    private String NEWID;

    // 客户电话（新）
    @JSONField(name = "NEWTEL")
    private String NEWTEL;

    // 客户签字图片（新）
    @JSONField(name = "NEWPIC")
    private String NEWPIC;

    // 水过户
    @JSONField(name = "ISWATER")
    private Integer ISWATER;

    // 水过户单位
    @JSONField(name = "WATERORG")
    private String  WATERORG;

    // 电过户
    @JSONField(name = "ISELETRIC")
    private Integer ISELETRIC;

    // 电过户单位
    @JSONField(name = "ELETRICORG")
    private String ELETRICORG;

    // 气过户
    @JSONField(name = "ISGAS")
    private Integer ISGAS;

    // 气过户单位
    @JSONField(name = "GASORG")
    private String GASORG;

    // 有线宽带
    @JSONField(name = "ISNETWORK")
    private Integer ISNETWORK;

    // 有线宽带过户单位
    @JSONField(name = "NETWORKORG")
    private String NETWORKORG;

    // 数字电视
    @JSONField(name = "ISTV")
    private Integer ISTV;

    // 数字电视过户单位
    @JSONField(name = "TVORG")
    private String TVORG;

    // 备注
    @JSONField(name = "REMARK")
    private String REMARK;

    @JSONField(name = "PK_ORG")
    private String PK_ORG;

    @JSONField(name = "PK_ORG_V")
    private String PK_ORG_V;

    public String getPK_APPLY() {
        return PK_APPLY;
    }

    public void setPK_APPLY(String PK_APPLY) {
        this.PK_APPLY = PK_APPLY;
    }

    public String getAPPLYNO() {
        return APPLYNO;
    }

    public void setAPPLYNO(String APPLYNO) {
        this.APPLYNO = APPLYNO;
    }

    public String getAPPLYTIME() {
        return APPLYTIME;
    }

    public void setAPPLYTIME(String APPLYTIME) {
        this.APPLYTIME = APPLYTIME;
    }

    public String getBUSINO() {
        return BUSINO;
    }

    public void setBUSINO(String BUSINO) {
        this.BUSINO = BUSINO;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getLANDUSE() {
        return LANDUSE;
    }

    public void setLANDUSE(String LANDUSE) {
        this.LANDUSE = LANDUSE;
    }

    public String getHOUSEUSE() {
        return HOUSEUSE;
    }

    public void setHOUSEUSE(String HOUSEUSE) {
        this.HOUSEUSE = HOUSEUSE;
    }

    public String getSTREET() {
        return STREET;
    }

    public void setSTREET(String STREET) {
        this.STREET = STREET;
    }

    public String getOLDCTM() {
        return OLDCTM;
    }

    public void setOLDCTM(String OLDCTM) {
        this.OLDCTM = OLDCTM;
    }

    public String getOLDID() {
        return OLDID;
    }

    public void setOLDID(String OLDID) {
        this.OLDID = OLDID;
    }

    public String getOLDTEL() {
        return OLDTEL;
    }

    public void setOLDTEL(String OLDTEL) {
        this.OLDTEL = OLDTEL;
    }

    public String getOLDPIC() {
        return OLDPIC;
    }

    public void setOLDPIC(String OLDPIC) {
        this.OLDPIC = OLDPIC;
    }

    public String getNEWCTM() {
        return NEWCTM;
    }

    public void setNEWCTM(String NEWCTM) {
        this.NEWCTM = NEWCTM;
    }

    public String getNEWID() {
        return NEWID;
    }

    public void setNEWID(String NEWID) {
        this.NEWID = NEWID;
    }

    public String getNEWTEL() {
        return NEWTEL;
    }

    public void setNEWTEL(String NEWTEL) {
        this.NEWTEL = NEWTEL;
    }

    public String getNEWPIC() {
        return NEWPIC;
    }

    public void setNEWPIC(String NEWPIC) {
        this.NEWPIC = NEWPIC;
    }

    public Integer getISWATER() {
        return ISWATER;
    }

    public void setISWATER(Integer ISWATER) {
        this.ISWATER = ISWATER;
    }

    public String getWATERORG() {
        return WATERORG;
    }

    public void setWATERORG(String WATERORG) {
        this.WATERORG = WATERORG;
    }

    public Integer getISELETRIC() {
        return ISELETRIC;
    }

    public void setISELETRIC(Integer ISELETRIC) {
        this.ISELETRIC = ISELETRIC;
    }

    public String getELETRICORG() {
        return ELETRICORG;
    }

    public void setELETRICORG(String ELETRICORG) {
        this.ELETRICORG = ELETRICORG;
    }

    public Integer getISGAS() {
        return ISGAS;
    }

    public void setISGAS(Integer ISGAS) {
        this.ISGAS = ISGAS;
    }

    public String getGASORG() {
        return GASORG;
    }

    public void setGASORG(String GASORG) {
        this.GASORG = GASORG;
    }

    public Integer getISNETWORK() {
        return ISNETWORK;
    }

    public void setISNETWORK(Integer ISNETWORK) {
        this.ISNETWORK = ISNETWORK;
    }

    public String getNETWORKORG() {
        return NETWORKORG;
    }

    public void setNETWORKORG(String NETWORKORG) {
        this.NETWORKORG = NETWORKORG;
    }

    public Integer getISTV() {
        return ISTV;
    }

    public void setISTV(Integer ISTV) {
        this.ISTV = ISTV;
    }

    public String getTVORG() {
        return TVORG;
    }

    public void setTVORG(String TVORG) {
        this.TVORG = TVORG;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getPK_ORG() {
        return PK_ORG;
    }

    public void setPK_ORG(String PK_ORG) {
        this.PK_ORG = PK_ORG;
    }

    public String getPK_ORG_V() {
        return PK_ORG_V;
    }

    public void setPK_ORG_V(String PK_ORG_V) {
        this.PK_ORG_V = PK_ORG_V;
    }
}
