package cn.gtmap.realestate.common.core.domain.check;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lst
 * @version 1.0, 2020-01-03
 * @description 原因及解决方案表
 */
@Table(name = "CHECK_GZ_YYJJFA")
@ApiModel(value = "CheckGzYyJjfaDO",description = "原因及解决方案表")
public class CheckGzYyJjfaDO {
    @Id
    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "规则主键")
    private String gzid;
    @ApiModelProperty(value = "可能原因")
    private String knyy;
    @ApiModelProperty(value = "解决方案")
    private String jjfa;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGzid() {
        return gzid;
    }

    public void setGzid(String gzid) {
        this.gzid = gzid;
    }

    public String getKnyy() {
        return knyy;
    }

    public void setKnyy(String knyy) {
        this.knyy = knyy;
    }

    public String getJjfa() {
        return jjfa;
    }

    public void setJjfa(String jjfa) {
        this.jjfa = jjfa;
    }

    @Override
    public String toString() {
        return "CheckGzYyJjfaDO{" +
                "id='" + id + '\'' +
                ", gzid='" + gzid + '\'' +
                ", knyy='" + knyy + '\'' +
                ", jjfa='" + jjfa + '\'' +
                '}';
    }
}
