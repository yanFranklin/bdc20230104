package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.exchange.core.support.spring.EnvironmentConfig;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/4/2 08:55
 */
public class Constants {

    /**
     * 登簿日志上报定时任务名称（redis分布式事务锁用）
     */
    public static final String ACCESS_LOG_TASK_JOB_NAME = "accessLogTaskJob";

    /**
     * 蚌埠外网申请定时任务名称（redis分布式事务锁用）
     */
    public static final String BENGBU_WWSQDTP_REDIS_LOCK_NAME = "bengbuWwsqdtpRedisLock";

    public static final String WSX_ACCESS_TYPE = "wsx";
    public static final String HEFEI_ACCESS_TYPE = "hefei";
    public static final String HEFEI_ACCESS_QXDM = "340100";

    // 外网创建 自动转发
    public static final String WWSQ_CJXM_AUTOTURN = "1";

    public static final String BUILDBEANNAME_SERVICEINFO = "buildServiceInfo";
    public static final String BUILDBEANNAME_REQUEST = "buildRequest";
    public static final String BUILDBEANNAME_SENDREQUEST = "sendRequest";
    public static final String BUILDBEANNAME_RESPONSE = "buildResponse";
    public static final String BUILDBEANNAME_LOG = "buildLog";

    // 互联网+ 合同信息 申请登记类型 存量房
    public static final String SQDJLX_CLF = "clf";

    // 互联网+ 合同信息 申请登记类型 增量房
    public static final String SQDJLX_ZLF = "zlf";

    /**
     * 定时任务获取响应报文 处理中标志
     */
    public static final String ACCESSRESPONSE_QUARTZ_DEALINGFLAG = "9999";

    /**
     * 定时任务获取响应报文 查询前几天
     */
    public static final int ACCESSRESPONSE_QUARTZ_BEFOREDATE = 3;

    /**
     * 定时任务获取响应报文 每次查询和更新的数量
     */
    public static final int ACCESSRESPONSE_QUARTZ_PERUPDATE_TIMES = 20;

    /**
     * 上报类型 国家
     */
    public static final String ACCESS_TYPE_NATIONAL = "national";

    /**
     * 上报类型 省级
     */
    public static final String ACCESS_TYPE_PROVINCE = "province";

    /**
     * 查封类型 代码
     */
    public static final String CF_QLLX_DM = "98";

    /**
     * 异议代码
     */
    public static final String YY_QLLX_DM = "97";

    /**
     * 居住权代码
     */
    public static final String JZQ_QLLX_DM = "92";

    /**
     * 预告代码
     */
    public static final String YG_QLLX_DM = "96";


    /**
     * 现势状态
     */
    public static final int QSZT_XS = 1;

    public static final String QLRLB_QLR = "1";

    public static final String QLRLB_YWR = "2";

    // 外网申请  初始化 抵押权登记 方法名
    public static final String WWSQ_INIT_DYDJ = "dydj";

    // 外网申请 初始化抵押权登记流程 定义ID
    public static final String WWSQ_INIT_DYDJ_GZLDYID = EnvironmentConfig.getEnvironment().getProperty("wwsq.init.dydj.gzldyid");

    // 外网申请  初始化 抵押注销登记 方法名
    public static final String WWSQ_INIT_DYZX = "dyzx";

    // 外网申请 初始化抵押注销登记流程 定义ID
    public static final String WWSQ_INIT_DYZX_GZLDYID = EnvironmentConfig.getEnvironment().getProperty("wwsq.init.dyzx.gzldyid");

    // 房产限购信息  是
    public static final String FCXGXX_S = "是";
    // 房产限购信息  否
    public static final String FCXGXX_F = "否";

    /**
     * 外网申请 是否枚举值 0 是 1 否
     */
    public static final Integer WWSQ_SF_S = 0;

    public static final Integer WWSQ_SF_F = 1;


    /**
     * 项目表审批来源  互联网+
     */
    public static final Integer SPLY_WWSQ = 3;

    /**
     * 项目表审批来源  一窗受理
     */
    public static final Integer SPLY_YCSL = 1;


    /**
     * 合肥市本级上报 四个区的配置
     */
    public static String[] HEFEI_QXDM_ARR = getHefeiQxDmArr();

    public static String[] getHefeiQxDmArr() {
        String[] hefeiQxdmArr = new String[0];
        String hefeiQxdms = EnvironmentConfig.getEnvironment().getProperty("hefei.access.qxdm");
        if (StringUtils.isNotBlank(hefeiQxdms)) {
            hefeiQxdmArr = hefeiQxdms.split(",");
        }
        return hefeiQxdmArr;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return
     * @description 需要过滤的行政区代码
     */
    public static String[] ACCESS_FILTER_ARR = getAccessFilterXzqdmArr();

    public static String[] getAccessFilterXzqdmArr() {
        String[] accessFilterXzqdmArr = new String[0];
        String hefeiQxdms = EnvironmentConfig.getEnvironment().getProperty("access.filter.xzqdm");
        if (StringUtils.isNotBlank(hefeiQxdms)) {
            accessFilterXzqdmArr = hefeiQxdms.split(",");
        }
        return accessFilterXzqdmArr;
    }

    public static final String BH_SL = "slbh";
    public static final String ZZSJFW_DAY = "DAY";
    public static final String ASID = "AS100";//国家汇交接入标准ID  区县统一为AS100
    public static final String sys_version_yn = "yunnan";//云南
    public static final String sys_version_yizheng = "321081";//仪征
    public static final String SUCCESS = "success";

    /**
     * 外网申请的 成功标志
     */
    public static final String CODE_SUCCESS = "0000";

    /**
     * 外网申请失败代码
     */
    public static final String CODE_ERROR = "2000";

    public static final String CODE_ERROR_MSG = "代码未知错误";


    /**
     * 失败
     */
    public static final String CODE_SEARCH_ERROR = "1000";
    /**
     * 用户名密码验证错误
     */
    public static final String CODE_USER_ERROR = "2000";
    /**
     * 安全token错误
     */
    public static final String CODE_TOKEN_ERROR = "2010";
    /**
     * returncode
     */
    public static final String RETURNCODE = "returncode";
    /**
     * returncode
     */
    public static final String STATUSCODE = "statusCode";
    /**
     * msg
     */
    public static final String MSG = "msg";
    /**
     * status ok
     */
    public static final String STATUS_OK = "OK";

    // 省级平台存储文书信息的 SPACE_ID
    public static final String WJZX_SPACEID_SJPTWSXX = "sjptwsxx";

    // 第三方子系统的 clientId
    public static final String WJZX_CLIENTID_EXCHANGE = "exchangeClientId";

    // 业务系统的存储附件的 clientId
    public static final String WJZX_CLIENTID = "clientId";

    // 第三方子系统 向ES写日志的 固定类型
    public final static String EXCHANGE_ES_RZLX = "DSFJK";

    // 第三方子系统 向ES写档案查询日志的 固定类型
    public final static String EXCHANGE_ES_DACX_RZLX = "DSFDACXJK";

    // 第三方子系统 向ES写档案查询日志的 固定类型
    public final static String EXCHANGE_ES_SHIJI_GX_RZLX = "DSFSHIJIGXJK";

    // 异常信息的 开头
    public final static String EXCHANGE_RZ_YCXX_PRE = "异常信息:";

    /**
     * 业务请求结果
     */
    public interface TOKEN {
        String SHUI_TOKEN = "shuiToken";
        String ZZQZ_TOKEN = "zzqzToken";
        String YKQ_TOKEN = "ykqToken";
        String JT_ZZQZ_TOKEN = "jt_zzqzToken";
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网验证类型-超时24小时收件
     */
    public static final String YZWYZLX_SJCS = "1";
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网验证类型-超期办结
     */
    public static final String YZWYZLX_XMCQBJ = "2";
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网验证类型-超期未办结
     */
    public static final String YZWYZLX_XMCQWBJ = "3";
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网验证类型-退件未推送结果数据
     */
    public static final String YZWYZLX_TJWTSJG = "4";
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网验证类型-缺失过程信息
     */
    public static final String YZWYZLX_QSGCXX = "5";
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网验证类型-证件号码不规范
     */
    public static final String YZWYZLX_ZJHMBGH = "6";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网失败类型-异常
     */
    public static final Integer SBLX_YC = 1;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网失败类型-验证失败
     */
    public static final Integer SBLX_YZSB = 2;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网推送状态-推送失败
     */
    public static final Integer YZW_TSZT_TSSB = 0;


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网推送状态-推送成功
     */
    public static final Integer YZW_TSZT_TSCG = 1;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网推送状态-未推送
     */
    public static final Integer YZW_TSZT_WTS = 2;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网验证状态-验证失败
     */
    public static final Integer YZW_YZZT_YZSB = 0;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网验证状态-验证成功
     */
    public static final Integer YZW_YZZT_YZCG = 1;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网业务动作-通过
     */
    public static final String YZW_YWDZ_TG = "1";


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网办结结果-办结
     */
    public static final String YZW_BJJG_BJ = "1";


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一张网办结结果-退回
     */
    public static final String YZW_BJJG_TH = "2";

    /**************************************电子证照相关常量****************************************/
    public static final String KEY_TOKEN = "token";
    public static final String KEY_STATUS = "status";
    public static final String KEY_HEAD = "head";
    public static final String KEY_DATA = "data";
    public static final String KEY_ZZBS = "zzbs";
    public static final String KEY_INFO = "info";
    public static final String KEY_ZZBGYY = "zzbgyy";
    public static final String KEY_CONTENT = "content";
    public static final String STATUS_SUCCESS = "0";
    public static final String ZZLXDM_ZS = "11100000MB03271699001";
    public static final String ZZLXDM_ZM = "11100000MB03271699022";

    public static final String DATA_VERSION_YANCHENG = "yancheng";

    public static final String RESPONSE_SUCCESS_CODE = "0000";
    public static final String RESPONSE_FAIL_CODE = "9999";
    public static final String RESPONSE_SUCCESS_MSG = "调用成功";
    //响应成功状态码
    public static final Integer RESPONSE_SUCCESS_0 = 0;
    public static final Integer RESPONSE_SUCCESS_200 = 200;

    /**
     * 统一返回状态码 1是成功，0为失败
     */
    public static final String SUCCESS_CODE = "1";
    public static final String FAIL_CODE = "0";

    // 房产限购信息  是
    public static final String ZDT_FILETYPE = "1";
    // 房产限购信息  否
    public static final String HST_FILETYPE = "2";

    public static final String DATE_NYR = "yyyy-MM-dd";

    //是否上报，1是，0否
    public static final String SFSB_S = "1";
    public static final String SFSB_F = "0";
}
