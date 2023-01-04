package cn.gtmap.realestate.certificate.util;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/6
 * @description 一般常用常量类
 */
public class Constants {
    /**
     * 页面资源名称-发证记录列表
     */
    public static final String RESOURCE_NAME_FZJL_LIST = "bdcFzjlList";
    /**
     * listZsid
     */
    public static final String LIST_ZSID = "listZsid";
    /**
     * zsid
     */
    public static final String ZSID = "zsid";
    /**
     * xmid
     */
    public static final String XMID = "xmid";
    /**
     * bdcdyh
     */
    public static final String BDCDYH = "bdcdyh";

    public static final String GYFS = "gyfs";

    public static final String QLLX = "qllx";
    /**
     * ewm
     */
    public static final String EWM = "ewm";
    /**
     * 发证记录领证人签字
     */
    public static final String LZRQZ = "lzrqztp";
    /**
     * 签字内容
     */
    public static final String QZNR = "qznr";
    /**
     * 批量坐落后缀字符,SFX 为suffix的缩写形式
     */
    public static final String ZL_SFX = "zlSFX";
    /**
     * 批量证书后缀字符，SFX 为suffix的缩写形式
     */
    public static final String ZS_SFX = "zsSFX";
    /**
     * 批量流程标识
     */
    public static final int PLLC = 3;
    /**
     * 批量流程生成证号场景：不生成
     */
    public static final String PLLC_NULL = "0";
    /**
     * 批量流程生成证号场景：不换证，即沿用之前证号，不生成新的
     */
    public static final String PLLC_BHZ = "1";
    /**
     * 批量流程生成证号场景：新生成证号
     */
    public static final String PLLC_NEW = "2";

    /**
     * 南通展示移交单的方式
     */
    public static final Integer SHOW_GDXX_TYPE_NT = 1;
    /**
     * 合肥展示移交单的方式
     */
    public static final Integer SHOW_GDXX_TYPE_HF = 0;

    /**************************************电子证照相关常量****************************************/
    public static final String KEY_TOKEN = "token";
    public static final String KEY_STATUS = "status";
    public static final String KEY_HEAD = "head";
    public static final String KEY_DATA = "data";
    /**
     * 证照附属信息 since 2022-07
     */
    public static final String KEY_ZZFSXX = "zzfsxxMap";
    public static final String KEY_ZZBS = "zzbs";
    public static final String KEY_INFO = "info";
    public static final String KEY_ZZBGYY = "zzbgyy";
    public static final String KEY_CONTENT = "content";
    public static final String STATUS_SUCCESS = "0";
    public static final String ZZLXDM_ZS = "11100000MB03271699001";
    public static final String ZZLXDM_ZM = "11100000MB03271699022";
    /**
     * 电子证照注销原因代码：正常办理登记业务注销
     */
    public static final String ZZBGYY_BDC_ZX = "1";

    /**
     * 电子证照注销原因代码：电子证照签发错误注销
     */
    public static final String ZZBGYY_BDC_ZF = "2";

    /**
     * 文件中心clientId-电子证照路径
     */
    public static final String WJZX_CLIENTID_ECERTIFICATE = "eCertificateId";
    /**
     * 文件中心clientId-附件路径
     */
    public static final String WJZX_CLIENTID = "clientId";

    /**
     * 海门移交单编号类型
     */
    public static final String HAIMEN_YJD = "HAIMEN_YJD";

    /**
     * 发证记录领证人签字(保存到签字信息标表单类型字典项,对应字典项 BDC_ZD_BDLX)
     */
    public static final Integer FZJL_LZR_QZ = 3;

    /**
     * 电子证照（用于给档案系统附件信息过滤电子证照附件）
     */
    public static final String ELECTRONIC_LICENSE_FLODER_NAME = "电子证照";

    /**
     * 当前阶段已生成证号缓存Redis Key
     */
    public static final String REDIS_APPLICATION_BDCQZH = "REDIS_APPLICATION_BDCQZH";

    /**
     * 证号模板配置Redis Key
     */
    public static final String REDIS_BDCQZH_MBPZ = "REDIS_BDCQZH_MBPZ";

    /**
     * 证号序列号Redis Key前缀
     */
    public static final String REDIS_ZHXLH = "REDIS_ZHXLH_";

    /**
     * 回收跳号证号Redis key前缀
     */
    public static final String REDIS_ZHTHHS = "REDIS_ZHTHHS_";

    /**
     * message提示信息,未传入有效参数
     */
    public static final String MESSAGE_NOPARAMETER = "message.noparameter";

    /**
     * 电子证照
     */
    public static final String DZZZ = "DZZZ";

    /**
     * 电子签章
     */
    public static final String DZQZ = "DZQZ";

    /**
     * 异步电子证照生成补偿事件定时任务名称（redis分布式事务锁用）
     */
    public static final String QUARTZ_YBSCDZZZ_JOB_NAME = "syncCreateDzzzJob";

    /**
     * 合肥：异步电子证照生成补偿事件定时任务名称（redis分布式事务锁用）
     */
    public static final String QUARTZ_HFTBZZ_JOB_NAME = "hfSyncTbDzzzJob";

    /**
     * 合肥：异步存量电子证照生成补偿事件定时任务名称（redis分布式事务锁用）
     */
    public static final String QUARTZ_HFTBCLZZ_JOB_NAME = "hfSyncTbClDzzzJob";

    /**
     * 异步电子证照注销补偿事件定时任务名称（redis分布式事务锁用）
     */
    public static final String QUARTZ_YBDZZZZX_JOB_NAME = "syncZxDzzzJob";

    /**
     * 电子证照信息补偿事件定时任务名称（redis分布式事务锁用）
     */
    public static final String QUARTZ_DZZZBC_JOB_NAME = "dzzzBcsjJob";
}
