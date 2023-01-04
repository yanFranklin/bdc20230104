package cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "HEAD")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"APP", "VER", "SRC", "DES", "msgNo", "msgID", "msgRef", "workDate", "reserve"})
public class FsczHead {


    /**
     * APP : CNNONTAX
     * VER : 1.0
     * SRC : F320600
     * DES : K3206000003
     * MsgNo : 5001
     * MsgID : 1418139546045906944
     * MsgRef : 2021072276E114182F2C
     * WorkDate : 20210722
     */
    private String APP;
    private String VER;
    private String SRC;
    private String DES;
    private String MsgNo;
    private String MsgID;
    private String MsgRef;
    private String WorkDate;
    private String Reserve;


    @XmlElement(name = "APP")
    public String getAPP() {
        return APP;
    }

    public void setAPP(String APP) {
        this.APP = APP;
    }

    @XmlElement(name = "VER")
    public String getVER() {
        return VER;
    }

    public void setVER(String VER) {
        this.VER = VER;
    }

    @XmlElement(name = "SRC")
    public String getSRC() {
        return SRC;
    }

    public void setSRC(String SRC) {
        this.SRC = SRC;
    }

    @XmlElement(name = "DES")
    public String getDES() {
        return DES;
    }

    public void setDES(String DES) {
        this.DES = DES;
    }

    @XmlElement(name = "MsgNo")
    public String getMsgNo() {
        return MsgNo;
    }

    public void setMsgNo(String MsgNo) {
        this.MsgNo = MsgNo;
    }

    @XmlElement(name = "MsgID")
    public String getMsgID() {
        return MsgID;
    }

    public void setMsgID(String MsgID) {
        this.MsgID = MsgID;
    }

    @XmlElement(name = "MsgRef")
    public String getMsgRef() {
        return MsgRef;
    }

    public void setMsgRef(String MsgRef) {
        this.MsgRef = MsgRef;
    }

    @XmlElement(name = "WorkDate")

    public String getWorkDate() {
        return WorkDate;
    }

    public void setWorkDate(String WorkDate) {
        this.WorkDate = WorkDate;
    }

    @XmlElement(name = "Reserve")
    public String getReserve() {
        return Reserve;
    }

    public void setReserve(String reserve) {
        Reserve = reserve;
    }

    @Override
    public String toString() {
        return "FsczHead{" +
                "APP='" + APP + '\'' +
                ", VER='" + VER + '\'' +
                ", SRC='" + SRC + '\'' +
                ", DES='" + DES + '\'' +
                ", MsgNo='" + MsgNo + '\'' +
                ", MsgID='" + MsgID + '\'' +
                ", MsgRef='" + MsgRef + '\'' +
                ", WorkDate='" + WorkDate + '\'' +
                '}';
    }
}
