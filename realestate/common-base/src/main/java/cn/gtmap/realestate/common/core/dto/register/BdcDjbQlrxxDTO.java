package cn.gtmap.realestate.common.core.dto.register;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu/a>"
 * @version 3.0, 2022/3/14
 * @description 常州自助查询机查询登记簿结果-权属状况-权利人信息
 */
public class BdcDjbQlrxxDTO {
    /**
     * 权利人
     */
    private String qlrmc;

    /**
     * 共有情况
     */
    private String gyqk;

    /**
     * 房屋所有权证号/不动产权证号
     */
    private String bdcqzh;

    /**
     * 土地使用权证号
     */
    private String tdz;

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getTdz() {
        return tdz;
    }

    public void setTdz(String tdz) {
        this.tdz = tdz;
    }

    @Override
    public String toString() {
        return "BdcDjbQlrxxDTO{" +
                "qlrmc='" + qlrmc + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", tdz='" + tdz + '\'' +
                '}';
    }
}
