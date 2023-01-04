package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/5/21 09:53
 */
@Table(name = "BDC_XT_BB")
public class BdcXtBbDO {
    @Id
    @ApiModelProperty(value = "版本 ID")
    private String bbid;
    @ApiModelProperty(value = "版本编号")
    private String bbbh;
    @ApiModelProperty(value = "发布人 ID")
    private String fbrid;
    @ApiModelProperty(value = "发布人")
    private String fbr;
    @ApiModelProperty(value = "发布时间", example = "2019-05-21 12:18:48")
    private Date fbsj;

    public String getBbid() {
        return bbid;
    }

    public void setBbid(String bbid) {
        this.bbid = bbid;
    }

    public String getBbbh() {
        return bbbh;
    }

    public void setBbbh(String bbbh) {
        this.bbbh = bbbh;
    }

    public String getFbrid() {
        return fbrid;
    }

    public void setFbrid(String fbrid) {
        this.fbrid = fbrid;
    }

    public String getFbr() {
        return fbr;
    }

    public void setFbr(String fbr) {
        this.fbr = fbr;
    }

    public Date getFbsj() {
        return fbsj;
    }

    public void setFbsj(Date fbsj) {
        this.fbsj = fbsj;
    }

    @Override
    public String toString() {
        return "BdcXtBbDO{" +
                "bbid='" + bbid + '\'' +
                ", bbbh='" + bbbh + '\'' +
                ", fbrid='" + fbrid + '\'' +
                ", fbr='" + fbr + '\'' +
                ", fbsj=" + fbsj +
                '}';
    }
}
