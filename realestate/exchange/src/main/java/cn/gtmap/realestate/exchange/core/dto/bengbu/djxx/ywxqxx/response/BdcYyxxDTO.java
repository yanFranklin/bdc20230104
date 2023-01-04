package cn.gtmap.realestate.exchange.core.dto.bengbu.djxx.ywxqxx.response;

/**
 * @description 不动产异议信息
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @date 2022/12/22 18:04
 */
public class BdcYyxxDTO {

    /**
     * 不动产业务号
     */
    private String bdcywh;

    /**
     * 业务事项名称
     */
    private String ywsxmc;

    /**
     * 异议事项
     */
    private String yysx;

    /**
     * 异议申请人
     */
    private String yysqr;

    /**
     * 异议原因
     */
    private String yyyy;

    /**
     * 异议时间
     */
    private String yysj;

    public String getBdcywh() {
        return bdcywh;
    }

    public void setBdcywh(String bdcywh) {
        this.bdcywh = bdcywh;
    }

    public String getYwsxmc() {
        return ywsxmc;
    }

    public void setYwsxmc(String ywsxmc) {
        this.ywsxmc = ywsxmc;
    }

    public String getYysx() {
        return yysx;
    }

    public void setYysx(String yysx) {
        this.yysx = yysx;
    }

    public String getYysqr() {
        return yysqr;
    }

    public void setYysqr(String yysqr) {
        this.yysqr = yysqr;
    }

    public String getYyyy() {
        return yyyy;
    }

    public void setYyyy(String yyyy) {
        this.yyyy = yyyy;
    }

    public String getYysj() {
        return yysj;
    }

    public void setYysj(String yysj) {
        this.yysj = yysj;
    }

    @Override
    public String toString() {
        return "BdcYyxxDTO{" +
                "bdcywh='" + bdcywh + '\'' +
                ", ywsxmc='" + ywsxmc + '\'' +
                ", yysx='" + yysx + '\'' +
                ", yysqr='" + yysqr + '\'' +
                ", yyyy='" + yyyy + '\'' +
                ", yysj='" + yysj + '\'' +
                '}';
    }
}
