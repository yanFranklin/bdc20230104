package cn.gtmap.realestate.common.core.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.StringJoiner;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/1/5
 * @description 不动产量化信息操作日志DO
 */
@Table(
        name = "BDC_LHXX_CZRZ"
)
@ApiModel(value = "BdcLhxxCzrzDO",description = "不动产量化信息操作日志")
public class BdcLhxxCzrzDO {

    @Id
    @ApiModelProperty(value = "量化信息操作ID")
    private String lhxxczid;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;

    @ApiModelProperty(value = "逻辑幢主键")
    private String fwDcbIndex;

    @ApiModelProperty(value = "自然幢号")
    private String zh;

    @ApiModelProperty(value = "隶属宗地")
    private String lszd;

    @ApiModelProperty(value = "量化类型")
    private Integer lhlx;

    @ApiModelProperty(value = "量化状态值")
    private Integer lhzt;

    @ApiModelProperty(value = "操作时间")
    private Date czsj;

    @ApiModelProperty(value = "操作")
    private Integer czlx;

    public String getLhxxczid() {
        return lhxxczid;
    }

    public void setLhxxczid(String lhxxczid) {
        this.lhxxczid = lhxxczid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getFwDcbIndex() {
        return fwDcbIndex;
    }

    public void setFwDcbIndex(String fwDcbIndex) {
        this.fwDcbIndex = fwDcbIndex;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getLszd() {
        return lszd;
    }

    public void setLszd(String lszd) {
        this.lszd = lszd;
    }

    public Integer getLhlx() {
        return lhlx;
    }

    public void setLhlx(Integer lhlx) {
        this.lhlx = lhlx;
    }

    public Integer getLhzt() {
        return lhzt;
    }

    public void setLhzt(Integer lhzt) {
        this.lhzt = lhzt;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public Integer getCzlx() {
        return czlx;
    }

    public void setCzlx(Integer czlx) {
        this.czlx = czlx;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcLhxxCzrzDO.class.getSimpleName() + "[", "]")
                .add("lhxxczid='" + lhxxczid + "'")
                .add("gzlslid='" + gzlslid + "'")
                .add("gzldyid='" + gzldyid + "'")
                .add("fwDcbIndex='" + fwDcbIndex + "'")
                .add("zh='" + zh + "'")
                .add("lszd='" + lszd + "'")
                .add("lhlx=" + lhlx)
                .add("lhzt=" + lhzt)
                .add("czsj=" + czsj)
                .add("czlx=" + czlx)
                .toString();
    }
}
