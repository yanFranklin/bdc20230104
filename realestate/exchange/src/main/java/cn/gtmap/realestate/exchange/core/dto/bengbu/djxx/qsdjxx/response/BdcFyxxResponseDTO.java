package cn.gtmap.realestate.exchange.core.dto.bengbu.djxx.qsdjxx.response;

/**
 * @description 房源信息
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @date 2022/12/22 11:10
 */
public class BdcFyxxResponseDTO {

    /**
     * 不动产单元号
     */
    private String bdcdyh;

    /**
     * 房屋唯一号
     */
    private String estateid;

    /**
     * 区域编码
     */
    private String regioncode;

    /**
     * 区域
     */
    private String regioncodeTs;

    /**
     * 街道
     */
    private String streetTs;

    /**
     * 门牌号
     */
    private String sno;

    /**
     * 附号
     */
    private String saddno;

    /**
     * 自然幢号
     */
    private String natuno;

    /**
     * 单元
     */
    private String uno;

    /**
     * 楼层
     */
    private String fno;

    /**
     * 房号
     */
    private String rno;

    /**
     * 建筑面积
     */
    private String area;

    /**
     * 套内面积
     */
    private String inarea;

    /**
     * 公摊面积
     */
    private String outarea;

    /**
     * 房屋用途
     */
    private String houUsageTs;

    /**
     * 规划用途
     */
    private String plUsageTs;

    /**
     * 房屋性质
     */
    private String houCharactorTs;

    /**
     * 结构
     */
    private String structTs;

    /**
     * 房屋坐落
     */
    private String address;

    /**
     * 登簿时间
     */
    private String loadTime;

    /**
     * 房屋权属状态
     */
    private BdcQsztResponseDTO houseRightStatusPro;


    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getEstateid() {
        return estateid;
    }

    public void setEstateid(String estateid) {
        this.estateid = estateid;
    }

    public String getRegioncode() {
        return regioncode;
    }

    public void setRegioncode(String regioncode) {
        this.regioncode = regioncode;
    }

    public String getRegioncodeTs() {
        return regioncodeTs;
    }

    public void setRegioncodeTs(String regioncodeTs) {
        this.regioncodeTs = regioncodeTs;
    }

    public String getStreetTs() {
        return streetTs;
    }

    public void setStreetTs(String streetTs) {
        this.streetTs = streetTs;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSaddno() {
        return saddno;
    }

    public void setSaddno(String saddno) {
        this.saddno = saddno;
    }

    public String getNatuno() {
        return natuno;
    }

    public void setNatuno(String natuno) {
        this.natuno = natuno;
    }

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }

    public String getFno() {
        return fno;
    }

    public void setFno(String fno) {
        this.fno = fno;
    }

    public String getRno() {
        return rno;
    }

    public void setRno(String rno) {
        this.rno = rno;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getInarea() {
        return inarea;
    }

    public void setInarea(String inarea) {
        this.inarea = inarea;
    }

    public String getOutarea() {
        return outarea;
    }

    public void setOutarea(String outarea) {
        this.outarea = outarea;
    }

    public String getHouUsageTs() {
        return houUsageTs;
    }

    public void setHouUsageTs(String houUsageTs) {
        this.houUsageTs = houUsageTs;
    }

    public String getPlUsageTs() {
        return plUsageTs;
    }

    public void setPlUsageTs(String plUsageTs) {
        this.plUsageTs = plUsageTs;
    }

    public String getHouCharactorTs() {
        return houCharactorTs;
    }

    public void setHouCharactorTs(String houCharactorTs) {
        this.houCharactorTs = houCharactorTs;
    }

    public String getStructTs() {
        return structTs;
    }

    public void setStructTs(String structTs) {
        this.structTs = structTs;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(String loadTime) {
        this.loadTime = loadTime;
    }

    public BdcQsztResponseDTO getHouseRightStatusPro() {
        return houseRightStatusPro;
    }

    public void setHouseRightStatusPro(BdcQsztResponseDTO houseRightStatusPro) {
        this.houseRightStatusPro = houseRightStatusPro;
    }

    @Override
    public String toString() {
        return "BdcFyxxResponseDTO{" +
                "bdcdyh='" + bdcdyh + '\'' +
                ", estateid='" + estateid + '\'' +
                ", regioncode='" + regioncode + '\'' +
                ", regioncodeTs='" + regioncodeTs + '\'' +
                ", streetTs='" + streetTs + '\'' +
                ", sno='" + sno + '\'' +
                ", saddno='" + saddno + '\'' +
                ", natuno='" + natuno + '\'' +
                ", uno='" + uno + '\'' +
                ", fno='" + fno + '\'' +
                ", rno='" + rno + '\'' +
                ", area='" + area + '\'' +
                ", inarea='" + inarea + '\'' +
                ", outarea='" + outarea + '\'' +
                ", houUsageTs='" + houUsageTs + '\'' +
                ", plUsageTs='" + plUsageTs + '\'' +
                ", houCharactorTs='" + houCharactorTs + '\'' +
                ", structTs='" + structTs + '\'' +
                ", address='" + address + '\'' +
                ", loadTime='" + loadTime + '\'' +
                ", houseRightStatusPro=" + houseRightStatusPro +
                '}';
    }
}
