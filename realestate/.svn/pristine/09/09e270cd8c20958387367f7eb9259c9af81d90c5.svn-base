package cn.gtmap.realestate.etl.util;

import cn.gtmap.realestate.common.core.domain.exchange.*;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;

public class Constants {

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 数据回写类型-创建回写
      */
    public static final Integer SJHXLX_CJHX = 1;

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 互联网审核状态-审核不通过
      */
    public static final Integer SHZT_ABANDON = 2;

    /**
     * 外网申请创建定时任务名称（redis分布式事务锁用）
     */
    public static final String QUARTZ_WWSQCJ_JOB_NAME = "WwsqcjQuartzJob";

    /**
     * 外网申请创建定时任务名称（redis分布式事务锁用）
     */
    public static final String QUARTZ_CXWWSQCJ_JOB_NAME = "CxWwsqcjQuartzJob";

    /**
     * 外网申请创建定时任务名称（redis分布式事务锁用）
     */
    public static final String QUARTZ_WWSQZTTZ_JOB_NAME = "WwsqzttzQuartzJob";

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
    public static final Class[] NO_BDCDYH_CLASS= new Class[]{DjfDjSzDO.class,
            DjfDjSqrDO.class, DjfDjShDO.class, DjfDjSfDO.class, DjfDjGdDO.class, DjfDjSjDO.class,DjfDjFzDO.class,
            FjFDO.class, KtfZdbhqkDO.class, KtfZhbhqkDO.class, KtfZhYhydzbDO.class
            , KtfZhYhzkDO.class, KttGyJzdDO.class, KttGyJzxDO.class, QlfQlDyiqDO.class,ZtfGyQlQlrGxDO.class,ZdKDO.class};

    public static final String CONFIRM = "confirm";

    public static final String CONFIRMANDCREATE = "confirmAndCreate";

    public static final String ALERT = "alert";

    public static final String ALERT_EXCLUDE = "alert-exclude";

    public static final String FWXZ = "fwxz";

    public static final String FWLX = "fwlx";

    public static final String FWJG = "fwjg";

    public static final String QX = "qx";

    public static final String FWYT = "fwyt";

    public static final String QSZT = "qszt";

    public static final String QLRLX = "qlrlx";


    public static final String QLXZ = "qlxz";

    public static final String GYFS = "gyfs";

    public static final String JYLX = "jylx";

    public static final String QSZT_XS = "1";

    public static final String QSZT_LS = "2";
}
