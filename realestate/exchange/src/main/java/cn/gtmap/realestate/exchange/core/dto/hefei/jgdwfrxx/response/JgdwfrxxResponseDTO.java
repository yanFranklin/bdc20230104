package cn.gtmap.realestate.exchange.core.dto.hefei.jgdwfrxx.response;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-04
 * @description
 */
public class JgdwfrxxResponseDTO {

    // 住所
    private String activity_scope;

    // 举办单位
    private String lgl_grp;

    // 统一社会信用代码
    private String lgl_credit_no;

    // 单位名称
    private String sg_name;

    // 宗旨和经营范围
    private String lgl_scope;

    // 法定代表人
    private String lgl_name;

    public String getActivity_scope() {
        return activity_scope;
    }

    public void setActivity_scope(String activity_scope) {
        this.activity_scope = activity_scope;
    }

    public String getLgl_grp() {
        return lgl_grp;
    }

    public void setLgl_grp(String lgl_grp) {
        this.lgl_grp = lgl_grp;
    }

    public String getLgl_credit_no() {
        return lgl_credit_no;
    }

    public void setLgl_credit_no(String lgl_credit_no) {
        this.lgl_credit_no = lgl_credit_no;
    }

    public String getSg_name() {
        return sg_name;
    }

    public void setSg_name(String sg_name) {
        this.sg_name = sg_name;
    }

    public String getLgl_scope() {
        return lgl_scope;
    }

    public void setLgl_scope(String lgl_scope) {
        this.lgl_scope = lgl_scope;
    }

    public String getLgl_name() {
        return lgl_name;
    }

    public void setLgl_name(String lgl_name) {
        this.lgl_name = lgl_name;
    }
}
