package cn.gtmap.realestate.etl.core.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(
        name = "i_recvs"
)
@ApiModel(value = "IRecvsDO",description = "附件表")
public class IRecvsDO implements Serializable {

    private String recvid;
    private String oinsid;
    private String rname;
    private String rtype;
//    private String copys;
//    private String pages;
//    private String rnum;
//    private String reed;
//    private String rorder;
//    private String remark;
//    private String state;
//    private String slbh;
//    private String flag;
//    private String status;
//    private String remarks;
//    private String status1;


    public String getRecvid() {
        return recvid;
    }

    public void setRecvid(String recvid) {
        this.recvid = recvid;
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

    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }
}
