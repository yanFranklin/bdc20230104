package cn.gtmap.realestate.exchange.core.component;

import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
/**
 * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description //roleCode组件
 * @Date 2022/5/17 11:10
 **/
@Component
public class RoleComponent {

    public String getSlRoleCode(String param) {
        String slRoleCode = EnvironmentConfig.getEnvironment().getProperty("slRoleCode.sfcz");
        if (StringUtils.equals("false", slRoleCode)) {
            return "";
        }
        return param;
    }

}
