package cn.gtmap.realestate.common.core.dto.exchange.hcp;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022/7/22
 * @description 安徽好差评窗口人员信息
 */
public class EvaluatedPersonInfo {

    /**
     * 被评价人账户
     */
    private String loginName;

    /**
     * 被评价人姓名
     */
    private String name;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
