package cn.gtmap.realestate.common.core.dto.accept;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0, 2022/12/26
 * @description 登记信息权利人信息
 */
public class BdcDjxxQlrDTO {

    /**
     * 姓名
     */
    private String name;

    /**
     * 证件号
     */
    private String entId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntId() {
        return entId;
    }

    public void setEntId(String entId) {
        this.entId = entId;
    }

    @Override
    public String toString() {
        return "BdcDjxxQlrDTO{" +
                "name='" + name + '\'' +
                ", entId='" + entId + '\'' +
                '}';
    }
}
