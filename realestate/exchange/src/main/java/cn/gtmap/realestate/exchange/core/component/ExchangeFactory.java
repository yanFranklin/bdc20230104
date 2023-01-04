package cn.gtmap.realestate.exchange.core.component;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExchangeFactory implements InitializingBean {

    @Autowired
    private RoleComponent role;

    @Autowired
    private DsfZdToBdcDmComponent dsfZdToBdcDm;

    private static RoleComponent roleComponent;
    private static DsfZdToBdcDmComponent dsfZdToBdcDmComponent;


    @Override
    public void afterPropertiesSet() throws Exception {
        roleComponent = role;
        dsfZdToBdcDmComponent = dsfZdToBdcDm;
    }

    public static RoleComponent getRole() {
        return roleComponent;
    }


    public static DsfZdToBdcDmComponent getDsfZdToBdcDm() {
        return dsfZdToBdcDmComponent;
    }
}
