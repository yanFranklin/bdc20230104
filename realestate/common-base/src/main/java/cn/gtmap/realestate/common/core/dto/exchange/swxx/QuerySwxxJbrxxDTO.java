package cn.gtmap.realestate.common.core.dto.exchange.swxx;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-11
 * @description 代办人信息
 */
public class QuerySwxxJbrxxDTO {

    // 经办人电话
    private String jbrdh;

    // 经办人姓名
    private String jbrxm;

    public String getJbrdh() {
        return jbrdh;
    }

    public void setJbrdh(String jbrdh) {
        this.jbrdh = jbrdh;
    }

    public String getJbrxm() {
        return jbrxm;
    }

    public void setJbrxm(String jbrxm) {
        this.jbrxm = jbrxm;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QuerySwxxJbrxxDTO{");
        sb.append("jbrdh='").append(jbrdh).append('\'');
        sb.append(", jbrxm='").append(jbrxm).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
