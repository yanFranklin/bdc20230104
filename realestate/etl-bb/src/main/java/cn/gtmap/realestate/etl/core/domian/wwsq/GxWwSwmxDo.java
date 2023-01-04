package cn.gtmap.realestate.etl.core.domian.wwsq;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 2020/06/01,1.0
 * @description 外网申请实体do
 */
@Table(name = "gx_ww_swmx")
public class GxWwSwmxDo {

    /**
     * 税务明细id
     */
    @Id
    private String swmxid;
    /**
     * 税务id
     */
    private String swid;
    /**
     * 征收项目代码
     */
    private String zsxmdm;
    /**
     * 征收项目名称
     */
    private String zsxmmc;
    /**
     * 征收品目代码
     */
    private String zspmdm;
    /**
     * 征收品目名称
     */
    private String zspmmc;
    /**
     * 征收子目代码
     */
    private String zszmdm;
    /**
     * 征收子目名称
     */
    private String zszmmc;
    /**
     * 计税依据
     */
    private String jsyj;
    /**
     * 税率
     */
    private String sl;
    /**
     * 减除项
     */
    private String jcx;
    /**
     * 减免金额
     */
    private String jmje;
    /**
     * 实际缴纳金额
     */
    private String sjje;


    public String getSwmxid() {
        return swmxid;
    }

    public void setSwmxid(String swmxid) {
        this.swmxid = swmxid;
    }


    public String getSwid() {
        return swid;
    }

    public void setSwid(String swid) {
        this.swid = swid;
    }


    public String getZsxmdm() {
        return zsxmdm;
    }

    public void setZsxmdm(String zsxmdm) {
        this.zsxmdm = zsxmdm;
    }


    public String getZsxmmc() {
        return zsxmmc;
    }

    public void setZsxmmc(String zsxmmc) {
        this.zsxmmc = zsxmmc;
    }


    public String getZspmdm() {
        return zspmdm;
    }

    public void setZspmdm(String zspmdm) {
        this.zspmdm = zspmdm;
    }


    public String getZspmmc() {
        return zspmmc;
    }

    public void setZspmmc(String zspmmc) {
        this.zspmmc = zspmmc;
    }


    public String getZszmdm() {
        return zszmdm;
    }

    public void setZszmdm(String zszmdm) {
        this.zszmdm = zszmdm;
    }


    public String getZszmmc() {
        return zszmmc;
    }

    public void setZszmmc(String zszmmc) {
        this.zszmmc = zszmmc;
    }


    public String getJsyj() {
        return jsyj;
    }

    public void setJsyj(String jsyj) {
        this.jsyj = jsyj;
    }


    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }


    public String getJcx() {
        return jcx;
    }

    public void setJcx(String jcx) {
        this.jcx = jcx;
    }


    public String getJmje() {
        return jmje;
    }

    public void setJmje(String jmje) {
        this.jmje = jmje;
    }


    public String getSjje() {
        return sjje;
    }

    public void setSjje(String sjje) {
        this.sjje = sjje;
    }

}
