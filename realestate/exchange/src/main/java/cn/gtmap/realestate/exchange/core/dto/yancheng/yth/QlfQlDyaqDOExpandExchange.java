package cn.gtmap.realestate.exchange.core.dto.yancheng.yth;

import cn.gtmap.realestate.common.core.domain.exchange.QlfQlDyaqDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlDyaqOldDO;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/3/23
 * @description 一体化创建扩展字段
 */
public class QlfQlDyaqDOExpandExchange extends QlfQlDyaqOldDO {

    //担保范围
    private String dbfw;

    //贷款方式
    private String dkfs;

    private String dyqjdywzr;

    public String getDyqjdywzr() {
        return dyqjdywzr;
    }

    public void setDyqjdywzr(String dyqjdywzr) {
        this.dyqjdywzr = dyqjdywzr;
    }

    public String getDkfs() {
        return dkfs;
    }

    public void setDkfs(String dkfs) {
        this.dkfs = dkfs;
    }

    public String getDbfw() {
        return dbfw;
    }

    public void setDbfw(String dbfw) {
        this.dbfw = dbfw;
    }

}
