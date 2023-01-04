package cn.gtmap.realestate.common.core.dto.register;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu/a>"
 * @version 3.0, 2022/3/14
 * @description 常州自助查询机查询登记簿结果-征收冻结信息
 */
public class BdcDjbZsdjxxDTO {

    /**
     * 征收冻结单位
     */
    private String csdjdw;

    /**
     * 征收冻结时间
     */
    private String csdjsj;

    public String getCsdjdw() {
        return csdjdw;
    }

    public void setCsdjdw(String csdjdw) {
        this.csdjdw = csdjdw;
    }

    public String getCsdjsj() {
        return csdjsj;
    }

    public void setCsdjsj(String csdjsj) {
        this.csdjsj = csdjsj;
    }

    @Override
    public String toString() {
        return "BdcDjbZsdjxxDTO{" +
                "csdjdw='" + csdjdw + '\'' +
                ", csdjsj='" + csdjsj + '\'' +
                '}';
    }
}
