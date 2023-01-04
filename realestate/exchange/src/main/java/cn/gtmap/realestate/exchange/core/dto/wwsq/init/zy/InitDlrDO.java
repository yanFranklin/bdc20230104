package cn.gtmap.realestate.exchange.core.dto.wwsq.init.zy;

import io.swagger.annotations.ApiModel;

import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 代理人实体信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-21 10:17
 **/
@ApiModel(value = "InitDlrDO", description = "代理人")
public class InitDlrDO {

    /**
     * gxrdlr :
     * gxrdlrzjzl :
     * gxrdlrzjh :
     * gxrdlrdh :
     * gxrdlrzjzlmc :
     * wtsx :
     */

    private String gxrdlr;
    private String gxrdlrzjzl;
    private String gxrdlrzjh;
    private String gxrdlrdh;
    private String gxrdlrzjzlmc;
    private String wtsx;

    public String getGxrdlr() {
        return gxrdlr;
    }

    public void setGxrdlr(String gxrdlr) {
        this.gxrdlr = gxrdlr;
    }

    public String getGxrdlrzjzl() {
        return gxrdlrzjzl;
    }

    public void setGxrdlrzjzl(String gxrdlrzjzl) {
        this.gxrdlrzjzl = gxrdlrzjzl;
    }

    public String getGxrdlrzjh() {
        return gxrdlrzjh;
    }

    public void setGxrdlrzjh(String gxrdlrzjh) {
        this.gxrdlrzjh = gxrdlrzjh;
    }

    public String getGxrdlrdh() {
        return gxrdlrdh;
    }

    public void setGxrdlrdh(String gxrdlrdh) {
        this.gxrdlrdh = gxrdlrdh;
    }

    public String getGxrdlrzjzlmc() {
        return gxrdlrzjzlmc;
    }

    public void setGxrdlrzjzlmc(String gxrdlrzjzlmc) {
        this.gxrdlrzjzlmc = gxrdlrzjzlmc;
    }

    public String getWtsx() {
        return wtsx;
    }

    public void setWtsx(String wtsx) {
        this.wtsx = wtsx;
    }
}
