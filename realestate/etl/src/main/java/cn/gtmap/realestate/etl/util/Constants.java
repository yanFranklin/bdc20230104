package cn.gtmap.realestate.etl.util;

import cn.gtmap.realestate.common.core.domain.exchange.*;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019-04-19
 * @description 常量类
 */
public class Constants {

    /**
     * 存入redis中list的Key名 (带QXDM)
     */
    public static final String ETL_REDIS_LIST_NAME = "ywhList"+getQxdm();

    public static String getQxdm(){
        return EnvironmentConfig.getEnvironment().getProperty("qxdm");
    }
    /**
     * 国家汇交接入标准ID  区县统一为AS100
     */
    public static final String ASID = "AS100";

    /**
     * DJTDJSLSQ  flag  0
     */
    public static final String DJTDJSLSQ_FLAG_ZERO = "0";
    /**
     * DJTDJSLSQ  flag  1
     */
    public static final String DJTDJSLSQ_FLAG_ONE = "1";

    /**
     * DJTDJSLSQ  flag  2
     */
    public static final String DJTDJSLSQ_FLAG_TWO = "2";
    /**
     * 没有不动产单元号的实体
     */
    public static final Class[] NO_BDCDYH_CLASS = new Class[]{DjfDjSzDO.class,
            DjfDjSqrDO.class, DjfDjShDO.class, DjfDjSfDO.class, DjfDjGdDO.class, DjfDjSjDO.class, DjfDjFzDO.class,
            FjFDO.class, KtfZdbhqkDO.class, KtfZhbhqkDO.class, KtfZhYhydzbDO.class
            , DjfDjDb.class, KtfZhYhzkDO.class, KttGyJzdDO.class, KttGyJzxDO.class, QlfQlDyiqDO.class, ZtfGyQlQlrGxDO.class, ZdKDO.class};

}
