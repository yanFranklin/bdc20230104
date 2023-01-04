package cn.gtmap.realestate.etl.core.domian;

import io.swagger.annotations.ApiModel;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(
        name = "i_recvimg_data"
)
@ApiModel(value = "IRecvingDataDO",description = "附件路径表")
public class IRecvimgDataDO implements Serializable {

    private static final long serialVersionUID = -5961699938166842418L;

    private String rimgid;
    private String recvid;
    private Integer riorder;
    private String remark;
    private Integer state;
    private String scanman;
    private String scantime;
    private String oinsid;
    private String rname;

    public String getRimgid() {
        return rimgid;
    }

    public void setRimgid(String rimgid) {
        this.rimgid = rimgid;
    }

    public String getRecvid() {
        return recvid;
    }

    public void setRecvid(String recvid) {
        this.recvid = recvid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getScanman() {
        return scanman;
    }

    public void setScanman(String scanman) {
        this.scanman = scanman;
    }

    public String getScantime() {
        return scantime;
    }

    public void setScantime(String scantime) {
        this.scantime = scantime;
    }

    public String getOinsid() {
        return oinsid;
    }

    public void setOinsid(String oinsid) {
        this.oinsid = oinsid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public Integer getRiorder() {
        return riorder;
    }

    public void setRiorder(Integer riorder) {
        this.riorder = riorder;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
