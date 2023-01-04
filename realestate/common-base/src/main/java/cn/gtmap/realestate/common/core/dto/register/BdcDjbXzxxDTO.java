package cn.gtmap.realestate.common.core.dto.register;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu/a>"
 * @version 3.0, 2022/3/14
 * @description 常州自助查询机查询登记簿结果-限制权信息
 */
public class BdcDjbXzxxDTO {
    /**
     * 查封机构及文号
     */
    private String cfjg;

    /**
     * 查封期限
     */
    private String cfqx;

    public String getCfjg() {
        return cfjg;
    }

    public void setCfjg(String cfjg) {
        this.cfjg = cfjg;
    }

    public String getCfqx() {
        return cfqx;
    }

    public void setCfqx(String cfqx) {
        this.cfqx = cfqx;
    }

    @Override
    public String toString() {
        return "BdcDjbXzxxDTO{" +
                "cfjg='" + cfjg + '\'' +
                ", cfqx='" + cfqx + '\'' +
                '}';
    }
}
