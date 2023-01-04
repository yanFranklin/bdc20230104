package cn.gtmap.realestate.exchange.core.dto.shucheng.fgj.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022/4/20
 * @description 商品房、二手房合同信息,返回值中data的buyers
 */
@ApiModel("舒城县房管局-商品房、二手房合同信息中的买方信息")
public class ResponseBuyer {

    @ApiModelProperty("房屋买受人")
    private String srfmc;

    @ApiModelProperty("房屋买受人证件类型")
    private String srfzjlx;

    @ApiModelProperty("房屋买受人证件号码")
    private String srfzjhm;

    @ApiModelProperty("房屋买受人手机号")
    private String srfsjh;

    @ApiModelProperty("房屋买受人申请人类型")
    private String srfsqrlx;

    @ApiModelProperty("房屋买受人共有方式")
    private String srfgyfs;

    @ApiModelProperty("所占份额")
    private String szfe;

    public String getSrfmc() {
        return srfmc;
    }

    public void setSrfmc(String srfmc) {
        this.srfmc = srfmc;
    }

    public String getSrfzjlx() {
        return srfzjlx;
    }

    public void setSrfzjlx(String srfzjlx) {
        this.srfzjlx = srfzjlx;
    }

    public String getSrfzjhm() {
        return srfzjhm;
    }

    public void setSrfzjhm(String srfzjhm) {
        this.srfzjhm = srfzjhm;
    }

    public String getSrfsjh() {
        return srfsjh;
    }

    public void setSrfsjh(String srfsjh) {
        this.srfsjh = srfsjh;
    }

    public String getSrfsqrlx() {
        return srfsqrlx;
    }

    public void setSrfsqrlx(String srfsqrlx) {
        this.srfsqrlx = srfsqrlx;
    }

    public String getSrfgyfs() {
        return srfgyfs;
    }

    public void setSrfgyfs(String srfgyfs) {
        this.srfgyfs = srfgyfs;
    }

    public String getSzfe() {
        return szfe;
    }

    public void setSzfe(String szfe) {
        this.szfe = szfe;
    }

    @Override
    public String toString() {
        return "ResponseBuyer{" +
                "srfmc='" + srfmc + '\'' +
                ", srfzjlx='" + srfzjlx + '\'' +
                ", srfzjhm='" + srfzjhm + '\'' +
                ", srfsjh='" + srfsjh + '\'' +
                ", srfsqrlx='" + srfsqrlx + '\'' +
                ", srfgyfs='" + srfgyfs + '\'' +
                ", szfe='" + szfe + '\'' +
                '}';
    }
}
