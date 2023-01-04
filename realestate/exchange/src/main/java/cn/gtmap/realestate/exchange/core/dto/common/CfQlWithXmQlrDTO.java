package cn.gtmap.realestate.exchange.core.dto.common;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-16
 * @description
 */
public class CfQlWithXmQlrDTO extends QlWithXmQlrDTO {

    private BdcCfDO bdcql;

    @Override
    public BdcCfDO getBdcql() {
        return bdcql;
    }

    public void setBdcql(BdcCfDO bdcql) {
        this.bdcql = bdcql;
    }
}
