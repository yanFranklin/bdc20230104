package cn.gtmap.realestate.common.core.dto.certificate;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/8/19 09:59
 */
public class BdcHaimenYhYjdDTO {

    @ApiModelProperty(value = "移交单编号")
    private String yjdbh;

    @ApiModelProperty(value = "移交人")
    private String yjr;

    @ApiModelProperty(value = "交接单状态")
    private String jjdzt;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "移交时间")
    private Date yjsj;

    @ApiModelProperty(value = "受理编号列表")
    private List<String> slbhList;

    public String getYjdbh() {
        return yjdbh;
    }

    public void setYjdbh(String yjdbh) {
        this.yjdbh = yjdbh;
    }

    public String getYjr() {
        return yjr;
    }

    public void setYjr(String yjr) {
        this.yjr = yjr;
    }

    public String getJjdzt() {
        return jjdzt;
    }

    public void setJjdzt(String jjdzt) {
        this.jjdzt = jjdzt;
    }

    public List<String> getSlbhList() {
        return slbhList;
    }

    public void setSlbhList(List<String> slbhList) {
        this.slbhList = slbhList;
    }

    public Date getYjsj() {
        return yjsj;
    }

    public void setYjsj(Date yjsj) {
        this.yjsj = yjsj;
    }

    @Override
    public String toString() {
        return "BdcHaimenYhYjdDTO{" +
                "yjdbh='" + yjdbh + '\'' +
                ", yjr='" + yjr + '\'' +
                ", jjdzt='" + jjdzt + '\'' +
                ", slbhList=" + slbhList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof BdcHaimenYhYjdDTO)){
            return false;
        }
        BdcHaimenYhYjdDTO that = (BdcHaimenYhYjdDTO) o;
        return Objects.equals(getYjdbh(), that.getYjdbh()) &&
                Objects.equals(getYjr(), that.getYjr()) &&
                Objects.equals(getJjdzt(), that.getJjdzt()) &&
                Objects.equals(getSlbhList(), that.getSlbhList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getYjdbh(), getYjr(), getJjdzt(), getSlbhList());
    }
}
