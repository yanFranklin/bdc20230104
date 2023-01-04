package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/4/11
 * @description 问题数据
 */
@Table(name = "BDC_WTSJ")
@ApiModel(value = "BdcWtsjDO", description = "问题数据")
public class BdcWtsjDO {

    @Id
    @ApiModelProperty(value = "问题数据ID")
    private String wtsjid;

    @ApiModelProperty(value = "问题数据类别 1：不动产单元号 2：不动产权证号")
    private Integer wtsjlb;

    @ApiModelProperty(value = "问题数据")
    private String wtsj;

    @ApiModelProperty(value = "创建人")
    private String cjr;

    @ApiModelProperty(value = "创建人ID")
    private String cjrid;

    @ApiModelProperty(value = "创建时间")
    private Date cjsj;

    @ApiModelProperty(value = "问题内容")
    private String wtnr;

    @ApiModelProperty(value = "问题数据状态")
    private Integer wtsjzt;

    @ApiModelProperty(value = "解决人")
    private String jjr;

    @ApiModelProperty(value = "解决人ID")
    private String jjrid;

    @ApiModelProperty(value = "解决时间")
    private Date jjsj;

    @ApiModelProperty(value = "解决方案")
    private String jjfa;

    @ApiModelProperty(value = "问题类型")
    private String lsylwtlx;


    public String getWtsjid() {
        return wtsjid;
    }

    public void setWtsjid(String wtsjid) {
        this.wtsjid = wtsjid;
    }

    public Integer getWtsjlb() {
        return wtsjlb;
    }

    public void setWtsjlb(Integer wtsjlb) {
        this.wtsjlb = wtsjlb;
    }

    public String getWtsj() {
        return wtsj;
    }

    public void setWtsj(String wtsj) {
        this.wtsj = wtsj;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getWtnr() {
        return wtnr;
    }

    public void setWtnr(String wtnr) {
        this.wtnr = wtnr;
    }

    public Integer getWtsjzt() {
        return wtsjzt;
    }

    public void setWtsjzt(Integer wtsjzt) {
        this.wtsjzt = wtsjzt;
    }

    public String getJjr() {
        return jjr;
    }

    public void setJjr(String jjr) {
        this.jjr = jjr;
    }

    public Date getJjsj() {
        return jjsj;
    }

    public void setJjsj(Date jjsj) {
        this.jjsj = jjsj;
    }

    public String getJjfa() {
        return jjfa;
    }

    public void setJjfa(String jjfa) {
        this.jjfa = jjfa;
    }

    public String getCjrid() {
        return cjrid;
    }

    public void setCjrid(String cjrid) {
        this.cjrid = cjrid;
    }

    public String getJjrid() {
        return jjrid;
    }

    public void setJjrid(String jjrid) {
        this.jjrid = jjrid;
    }

    public String getLsylwtlx() {
        return lsylwtlx;
    }

    public void setLsylwtlx(String lsylwtlx) {
        this.lsylwtlx = lsylwtlx;
    }

    @Override
    public String toString() {
        return "BdcWtsjDO{" +
                "wtsjid='" + wtsjid + '\'' +
                ", wtsjlb=" + wtsjlb +
                ", wtsj='" + wtsj + '\'' +
                ", cjr='" + cjr + '\'' +
                ", cjrid='" + cjrid + '\'' +
                ", cjsj=" + cjsj +
                ", wtnr='" + wtnr + '\'' +
                ", wtsjzt=" + wtsjzt +
                ", jjr='" + jjr + '\'' +
                ", jjrid='" + jjrid + '\'' +
                ", jjsj=" + jjsj +
                ", jjfa='" + jjfa + '\'' +
                '}';
    }
}
