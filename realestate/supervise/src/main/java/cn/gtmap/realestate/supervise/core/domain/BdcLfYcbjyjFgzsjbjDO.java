package cn.gtmap.realestate.supervise.core.domain;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:zhuyong@gmail.com">zhuyong</a>
 * @version V1.0, 2021/09/27
 * @description 异常办件预警-非工作时间办件表
 */
@Table(name = "BDC_LF_YCBJYJ_FGZSJBJ")
public class BdcLfYcbjyjFgzsjbjDO {
    @Id
    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "工作流定义名称")
    private String gzldymc;

    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;

    @ApiModelProperty(value = "工作流实例名称")
    private String gzlslmc;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "受理人员")
    private String slr;

    @ApiModelProperty(value = "节点开始时间")
    private String jdkssj;

    @ApiModelProperty(value = "节点结束时间")
    private String jdjssj;

    @ApiModelProperty(value = "节点名称")
    private String jdmc;

    @ApiModelProperty(value = "节点办件人")
    private String jdbjr;

    @ApiModelProperty(value = "数据同步时间")
    private Date sjtbsj;

    @ApiModelProperty(value = "审核人")
    private String shry;

    @ApiModelProperty(value = "审核人ID")
    private String shrid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "审核时间")
    private Date shsj;

    @ApiModelProperty(value = "审核人ID")
    private String shyj;

    @ApiModelProperty(value = "审核状态")
    private Integer shzt;


    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public void setShry(String shry) {
        this.shry = shry;
    }

    public String getShry() {
        return shry;
    }

    public void setShr(String shry) {
        this.shry = shry;
    }

    public String getShrid() {
        return shrid;
    }

    public void setShrid(String shrid) {
        this.shrid = shrid;
    }

    public Date getShsj() {
        return shsj;
    }

    public void setShsj(Date shsj) {
        this.shsj = shsj;
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj;
    }

    public Integer getShzt() {
        return shzt;
    }

    public void setShzt(Integer shzt) {
        this.shzt = shzt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGzldymc() {
        return gzldymc;
    }

    public void setGzldymc(String gzldymc) {
        this.gzldymc = gzldymc;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getGzlslmc() {
        return gzlslmc;
    }

    public void setGzlslmc(String gzlslmc) {
        this.gzlslmc = gzlslmc;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getJdkssj() {
        return jdkssj;
    }

    public void setJdkssj(String jdkssj) {
        this.jdkssj = jdkssj;
    }

    public String getJdjssj() {
        return jdjssj;
    }

    public void setJdjssj(String jdjssj) {
        this.jdjssj = jdjssj;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public String getJdbjr() {
        return jdbjr;
    }

    public void setJdbjr(String jdbjr) {
        this.jdbjr = jdbjr;
    }

    public Date getSjtbsj() {
        return sjtbsj;
    }

    public void setSjtbsj(Date sjtbsj) {
        this.sjtbsj = sjtbsj;
    }

    @Override
    public String toString() {
        return "BdcLfYcbjyjFgzsjbjDO{" +
                "id='" + id + '\'' +
                ", gzldymc='" + gzldymc + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", gzlslmc='" + gzlslmc + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", jdkssj='" + jdkssj + '\'' +
                ", jdjssj='" + jdjssj + '\'' +
                ", jdmc='" + jdmc + '\'' +
                ", jdbjr='" + jdbjr + '\'' +
                ", sjtbsj=" + sjtbsj +
                '}';
    }
}
