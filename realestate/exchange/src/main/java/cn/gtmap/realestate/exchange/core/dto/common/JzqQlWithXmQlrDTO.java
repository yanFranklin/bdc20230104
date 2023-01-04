package cn.gtmap.realestate.exchange.core.dto.common;

import cn.gtmap.realestate.common.core.domain.BdcJzqDO;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2021-07-27
 * @description
 */
public class JzqQlWithXmQlrDTO extends QlWithXmQlrDTO {

    private BdcJzqDO bdcql;


    @Override
    public BdcJzqDO getBdcql() {
        return bdcql;
    }

    public void setBdcql(BdcJzqDO bdcql) {
        this.bdcql = bdcql;
    }
}
