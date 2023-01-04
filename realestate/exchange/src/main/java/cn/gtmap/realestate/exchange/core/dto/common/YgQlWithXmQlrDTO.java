package cn.gtmap.realestate.exchange.core.dto.common;

import cn.gtmap.realestate.common.core.domain.BdcYgDO;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-14
 * @description
 */
public class YgQlWithXmQlrDTO extends QlWithXmQlrDTO {

    private BdcYgDO bdcql;

    @Override
    public BdcYgDO getBdcql() {
        return bdcql;
    }

    public void setBdcql(BdcYgDO bdcql) {
        this.bdcql = bdcql;
    }
}
