package cn.gtmap.realestate.common.core.enums;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/3/30
 * @description 不动产打印请求bean枚举
 */
public enum BdcDyBeanEnum {
    BdcSlConfigMapper("bdcSlConfigMapper"),
    BuildingConfigMapper("buildingConfigMapper"),
    BdcRegisterConfigMapper("bdcRegisterConfigMapper"),
    BdcDyConfigService("bdcDyConfigServiceImpl");

    BdcDyBeanEnum(String beanName) {
        this.beanName = beanName;
    }

    private  String beanName;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
