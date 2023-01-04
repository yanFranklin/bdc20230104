package cn.gtmap.realestate.building.core.bo;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-10
 * @description 坐落刷新BO
 */
public class ZlsxConfigBO {

    /**
     * 刷新规则
     */
    private String sxgz;

    /**
     * 是否只刷新空值
     */
    private boolean nullOnly;

    public String getSxgz() {
        return sxgz;
    }

    public void setSxgz(String sxgz) {
        this.sxgz = sxgz;
    }

    public boolean isNullOnly() {
        return nullOnly;
    }

    public void setNullOnly(boolean nullOnly) {
        this.nullOnly = nullOnly;
    }
}
