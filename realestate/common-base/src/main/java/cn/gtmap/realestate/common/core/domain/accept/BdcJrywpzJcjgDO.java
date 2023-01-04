package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 接入业务配置检查结果
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-08 11:40
 **/
@Table(name = "BDC_JRYWPZ_JCJG")
public class BdcJrywpzJcjgDO {

    @Id
    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("流程定义id")
    private String gzldyid;
    @ApiModelProperty("流程名称")
    private String lcmc;
    @ApiModelProperty("登记小类")
    private String djxl;
    @ApiModelProperty("不动产类型")
    private String bdclx;
    @ApiModelProperty("不动产单元房屋类型")
    private String bdcdyfwlx;
    @ApiModelProperty("权利类型")
    private String qllx;
    @ApiModelProperty("检查结果")
    private String jcjg;

    @ApiModelProperty("配置id")
    private String pzid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public String getBdcdyfwlx() {
        return bdcdyfwlx;
    }

    public void setBdcdyfwlx(String bdcdyfwlx) {
        this.bdcdyfwlx = bdcdyfwlx;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getJcjg() {
        return jcjg;
    }

    public void setJcjg(String jcjg) {
        this.jcjg = jcjg;
    }

    public String getPzid() {
        return pzid;
    }

    public void setPzid(String pzid) {
        this.pzid = pzid;
    }

    @Override
    public String toString() {
        return "BdcJrywpzJcjgDO{" +
                "id='" + id + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", lcmc='" + lcmc + '\'' +
                ", djxl='" + djxl + '\'' +
                ", bdclx='" + bdclx + '\'' +
                ", bdcdyfwlx='" + bdcdyfwlx + '\'' +
                ", qllx='" + qllx + '\'' +
                ", jcjg='" + jcjg + '\'' +
                ", pzid='" + pzid + '\'' +
                '}';
    }
}
