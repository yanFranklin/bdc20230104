package cn.gtmap.realestate.common.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/10/31
 * @description 通用常量
 */
public class CommonConstantUtils {
    /*
   推送状态字典
    */
    public static final String TSZT_FAIL_S = "0";
    public static final String TSZT_SUCCESS_S = "1";
    public static final String TSZT_WTS_S = "2";
    public static final String TSZT_TSZ_S = "3";

    /**
     * 权属类型代码，单元号第14位
     */
    public static final String QSLXDM_A = "A";
    public static final String QSLXDM_BSXWY = "BSXWY";
    public static final String QSLXDM_C = "C";
    public static final String QSLXDM_D = "D";
    public static final String QSLXDM_E = "E";
    public static final String QSLXDM_F = "F";
    public static final String QSLXDM_G = "G";
    public static final String QSLXDM_H = "H";
    public static final String QSLXDM_J = "J";
    public static final String QSLXDM_L = "L";
    public static final String QSLXDM_N = "N";
    public static final String QSLXDM_W = "W";
    //生成编号时的前缀特征码
    public static final String BHTZM_FW = "F";

    public static final String BHTZM_TD = "T";

    public static final String BHTZM_HY = "H";

    public static final String BHTZM_LQ = "L";

    public static final String[] SLBH_TZM ={"F","T","H","L"};

    public static final String DZWTZM_TD = "W";

    public static final String DZWTZM_FW = "F";
    /**
     * 水电气业务类型
     */
    public static final String SDQYWLX_S = "1";
    public static final String SDQYWLX_D = "2";
    public static final String SDQYWLX_Q = "3";
    //数字电视
    public static final String SDQYWLX_DS = "4";
    //有线宽带
    public static final String SDQYWLX_KD = "5";

    /**
     * 水电气材料名称缩写
     */
    public static final String CLMC_SHUI1 = "(水电)";
    public static final String CLMC_SHUI2 = "(水)";
    public static final String CLMC_DIAN = "(电)";
    public static final String CLMC_QI = "(燃气)";

    /*
     * 材料名称-继承、受遗赠公证书
     * */
    public static final String CLMC_JCGZS = "继承、受遗赠公证书";
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 文件中心clientId
     */
    public static final String WJZX_CLIENTID = "clientId";
    /**
     * 文件中心clientId-电子证照路径
     */
    public static final String WJZX_CLIENTID_ECERTIFICATE = "eCertificateId";

    /**
     * 文件中心clientId-打印
     */
    public static final String WJZX_CLIENTID_PRINT = "printId";

    /**
     * 文件中心clientId-自定义js
     */
    public static final String WJZX_CLIENTID_ZDYJS = "zdyjsId";

    /**
     * 文件中心clientId-签字图片信息
     */
    public static final String WJZX_CLIENTID_QZXX = "qzxxId";


    /**
     * 文件中心spaceId-签字图片
     */
    public static final String SPACEID_QZXX = "pjqqzxx";
    /**
     * 水电气业务类型
     * 1-未申请
     * 2-已申请
     * 3-办理成功
     * 4-办理失败
     * 5-其他
     */
    public static final Integer WSQ = 1;
    public static final Integer YSQ = 2;
    public static final Integer BLCG = 3;
    public static final Integer BLSB = 4;
    public static final Integer QT = 5;

    /**
     * 水电气核验类型
     * 1-成功
     * 0-失败
     */
    public static final Integer HYJG_SUCESS = 1;
    public static final Integer HYJG_FAIL = 0;

    /**
     * 领证方式
     * 1-EMS邮递
     * 2-窗口领证
     * 3-自助打证机
     * 4-电子证照
     */
    public static final Integer LZFS_EMS = 1;
    public static final Integer LZFS_CK = 2;
    public static final Integer LZFS_ZZDZ = 3;
    public static final Integer LZFS_DZZZ = 4;
    /**
     * 宗地所有类型
     */
    public static final Integer ZDSYLX_DY = 1;
    public static final Integer ZDSYLX_GY = 2;
    /**
     * 面积单位 平方米
     */
    public static final Integer MJDW_PFM = 1;
    /**
     * 面积单位 亩
     */
    public static final Integer MJDW_M = 2;
    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 虚拟宗地宗海顺序号
     */
    public static final String ZDZHSXH_XN = "000000";

    /**
     * 宗地后缀
     */
    public static final String SUFFIX_ZD_BDCDYH = "W00000000";
    /**
     * 批量后缀
     */
    public static final String SUFFIX_PL = "等";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 虚拟量化单元号后缀
     */
    public static final String SUFFIX_XNLH_BDCDYH = "F99999999";

    /**
     * 用于标识权利其他状况是要追加在原权利其他状况值后的
     */
    public static final String ADD_QLQTZK = "add_qlqtzk";

    /**
     * 用于标识附记是要追加在原附记值后的
     */
    public static final String ADD_FJ = "add_fj";
    /**
     * 查询子系统代码
     */
    public static final String INQUIRY_DM = "inquiry";

    /**
     * register子系统代码
     */
    public static final String REGISTER_DM = "register";

    /**
     * 楼盘表子系统代码
     */
    public static final String BUILDING_DM = "building";
    /**
     * 楼盘表ui子系统代码
     */
    public static final String BUILDING_UI_DM = "building-ui";

    /**
     * 规则子系统代码
     */
    public static final String RULE_DM = "rule";

    /**
     * 受理子系统代码
     */
    public static final String ACCEPT_DM = "accept-app";

    /**
     * event：查询
     */
    public static final String LOG_EVENT_QUERY = "QUERY";

    /**
     * event：打印日志
     */
    public static final String LOG_EVENT_PRINTLOG = "PRINTLOG";

    /**
     * event：手动登簿
     */
    public static final String LOG_EVENT_SDDBLOG = "SDDB";
    /**
     * event：权籍不动产单元状态更新
     */
    public static final String LOG_EVENT_QJBDCDYZT = "QJBDCDYZT";

    /**
     * event：打印
     */
    public static final String LOG_EVENT_PRINT = "PRINT";

    /**
     * event：导出
     */
    public static final String LOG_EVENT_EXPORT = "EXPORT";

    /**
     * event：上传
     */
    public static final String LOG_EVENT_UPLOAD = "UPLOAD";

    /**
     * event：下载
     */
    public static final String LOG_EVENT_DOWNLOAD = "DOWNLOAD";

    /**
     * event：删除
     */
    public static final String LOG_EVENT_DELETE = "DELETE";

    /**
     * event：新建
     */
    public static final String LOG_EVENT_CREATE = "CREATE";

    /**
     * event
     */
    public static final String LOG_EVENT = "event";

    /**
     * 综合查询日志类型
     */
    public static final String LOG_EVENT_ZHCX = "ZHCXLOG";

    /**
     * 匹配土地证回写信息
     */
    public static final String LOG_EVENT_PPTDZHX = "PPTDZHX";

    /**
     * 标记为查询条件参数
     */
    public static final String PARAM_SUB = "param_";

    /**
     * 标记为查询台账参数
     */
    public static final String VIEW_SUB = "view_";

    /**
     * Controller Name
     */
    public static final String CONTROLLER_CLASS_NAME = "mvc.controller.class";

    /**
     * Method Name
     */
    public static final String CONTROLLER_METHOD_NAME = "mvc.controller.method";

    /**
     * 用户名
     */
    public static final String PRINCIPAL = "principal";

    /**
     * 证明编号
     */
    public static final String ZMBH = "zmbh";

    /**
     * 受理编号
     */
    public static final String SLBH = "slbh";

    /**
     * 业务流转编号
     */
    public static final String YWLZBH = "ywlzbh";

    /**
     * 权利人
     */
    public static final String QLR = "qlr";

    /**
     * 权利人证件号
     */
    public static final String QLRZJH = "qlrzjh";

    /**
     * 领证人
     */
    public static final String LZR = "lzr";

    /**
     * 领证人证件号
     */
    public static final String LZRZJH = "lzrzjh";

    /**
     * 证书打印状态
     */
    public static final String DYZT = "dyzt";

    /**
     * 权利人名称
     */
    public static final String QLRMC = "qlrmc";

    /**
     * 义务人
     */
    public static final String YWR = "ywr";

    /**
     * 坐落
     */
    public static final String ZL = "zl";

    /**
     * 不动产单元号
     */
    public static final String BDCDYH = "bdcdyh";

    /**
     * 大云平台真实姓名
     */
    public static final String ALIAS = "alias";

    /**
     * 组织机构
     */
    public static final String ORGANIZATION = "organization";

    /**
     * 查询条件
     */
    public static final String CXTJ = "cxtj";

    /**
     * 查询结果
     */
    public static final String CXJG = "cxjg";

    /**
     * 查询结果
     */
    public static final String IPADDRESS = "ipaddress";

    /**
     * 打印日志模板路径
     */
    public static final String MODELURS = "modelUrl";
    /**
     * 打印日志数据源地址
     */
    public static final String DATAURL = "dataUrl";
    /**
     * 打印日志数据源xml
     */
    public static final String PRINTTYPE = "printType";

    /**
     * 打印类型
     */
    public static final String XMLSTR = "xmlStr";


    /**
     * 响应结果
     */
    public static final String RESPONSE = "response";

    /**
     * 请求参数
     */
    public static final String REQUEST = "request";

    public static final String DETAILS = "details";

    //响应成功状态码
    public static final String RESPONSE_SUCCESS = "200";
    public static final String RESPONSE_ZTM_SUCCESS = "0000";
    //响应失败状态码
    public static final String RESPONSE_FAIL = "500";

    // 完税状态-已完税
    public static final String WSZT_YWS = "1";
    //核税状态-已核税
    public static final String WSZT_YHS = "1";
    /**
     * 大云平台流程操作内容
     */
    public static final String PROCESSDEFINITIONNAME = "processDefinitionName";
    public static final String PROCESSINSTANCEID = "processInstanceId";
    public static final String TASKID = "taskId";
    public static final String ACTIVITYNAME = "activityName";
    public static final String DELETEREASON = "reason";
    public static final String ZFOPNION = "opinion";

    /**
     * 操作IP
     */
    public static final String DETAILS_REMOTEADDRESS = "remoteAddress";

    /**
     * 操作IP
     */
    public static final String IP = "ip";

    /**
     * 操作IP
     */
    public static final String REMOTEADDR = "remoteAddr";

    /**
     * 客户端ip
     */
    public static final String CLIENT_IP = "clientIp";

    /**
     * 真实姓名
     */
    public static final String REALNAME = "realName";

    /**
     * 日志查询-模糊查询
     */
    public static final String TYPE_LIKE = "like";

    /**
     * 日志查询-精确查询
     */
    public static final String TYPE_EQUAL = "equal";

    /**
     * 查询结果中部分数据 保存的key
     */
    public static final String RESPONSE_PARAM_KEY = "response_key";

    /**
     * 台账类型
     */
    public static final String VIEW_TYPE = "viewType";


    /**
     * 所属资源
     */
    public static final String SOURCE_MEUN = "sourceMeun";

    /**
     * 台账名称
     */
    public static final String VIEW_TYPE_NAME = "viewTypeName";

    /**
     * 台账名称全量
     */
    public static final String ACTION = "action";

    /**
     * 导出内容
     */
    public static final String DCNR = "DCNR";

    /**
     * 台账地址
     */
    public static final String VIEW_ADDRESS = "viewAddress";

    /**
     * 拼接查询条件得分隔符
     */
    public static final String separate = "， ";

    /**
     * 文件名称
     */
    public static final String FILE_NAME = "fileName";

    /**
     * 文件路径
     */
    public static final String FILE_PATH = "filePath";

    /**
     * fr3 打印模板名称
     */
    public static final String FR3_TEMPLAT = "templatName";

    /**
     * 需要保存到日志中的查询参数
     */
    public static final String PARAM_LIST = "paramList";

    /**
     * gzlslid
     */
    public static final String GZLSLID = "gzlslid";
    /**
     * 终止
     */
    public static final Integer QSZT_END = 3;
    /**
     * 历史
     */
    public static final Integer QSZT_HISTORY = 2;
    /**
     * 临时
     */
    public static final Integer QSZT_TEMPORY = 0;
    /**
     * 现势
     */
    public static final Integer QSZT_VALID = 1;

    /**
     * 权籍登记状态——已登记
     */
    public static final Integer QJDJ_VALID = 1;

    /**
     * 权籍登记状态——已注销
     */
    public static final Integer QJDJ_HISTORY = 2;

    /**
     * 权利人类别：权利人
     */
    public static final String QLRLB_QLR = "1";
    /**
     * 权利人类别：义务人
     */
    public static final String QLRLB_YWR = "2";
    /**
     * 权利人类别：权利人
     */
    public static final Integer QLRLB_QLR_DM = 1;

    /**
     * 权利人类别数组
     */
    public static final String[] QLRLB_QLR_ARR = {"1", "2"};
    /**
     * 权利人类别：义务人
     */
    public static final Integer QLRLB_YWR_DM = 2;
    /**
     * 证书版式 单一版
     */
    public static final Integer ZSBS_DYB_DM = 0;
    /**
     * 是否 否
     */
    public static final Integer SF_F_DM = 0;
    /**
     * 是否 是
     */
    public static final Integer SF_S_DM = 1;

    /**
     * 收费状态 未缴费
     */
    public static final Integer SFZT_WJF = 1;

    /**
     * 收费状态 已缴费
     */
    public static final Integer SFZT_YJF = 2;

    /**
     * 收费状态 部分缴费
     */
    public static final Integer SFZT_BFJF = 3;

    /**
     * 收费状态 退款中
     */
    public static final Integer SFZT_TKZ = 4;

    /**
     * 收费状态 退款成功
     */
    public static final Integer SFZT_TKCG = 5;


    /**
     * 收费状态 已核验
     */
    public static final Integer SFZT_YHY = 7;

    /**
     * 收费状态 已失效
     */
    public static final Integer SFZT_YSX = 8;

    public static final String SF_PJDM = "320101";

    public static final String TDSF_PJDM = "320401";


    /**
     * 项目来源 不动产数据
     */
    public static final Integer XMLY_BDC_DM = 1;

    /**
     * 项目来源 存量过渡数据
     */
    public static final Integer XMLY_CLGD_DM = 2;


    /**
     * 项目来源 其他
     */
    public static final Integer XMLY_QT_DM = 3;

    /**
     * 项目来源 存量不动产数据
     */
    public static final Integer XMLY_CLBDC_DM = 5;

    /**
     * 案件状态 在办
     */
    public static final Integer AJZT_ZB_DM = 1;
    /**
     * 案件状态 已办
     */
    public static final Integer AJZT_YB_DM = 2;

    /**
     * 案件状态 已办
     */
    public static final Integer AJZT_ZT_DM = 3;

    /**
     * 案件状态 已办
     */
    public static final Integer AJZT_BYDJ_DM = 4;

    /**
     * 案件状态 用户撤回
     */
    public static final Integer AJZT_YHCH_DM = 5;

    /**
     * 证书打印状态 无效
     */
    public static final String DYZT_WX = "0";

    /**
     * 证书打印状态 有效
     */
    public static final String DYZT_YX = "1";

    /**
     * 证书打印状态 待定
     */
    public static final String DYZT_DD = "2";
    /**
     * 登记类型 首次登记
     */
    public static final Integer DJLX_SCDJ_DM = 100;
    /**
     * 登记类型 首次登记
     */
    public static final Integer DJLX_ZYDJ_DM = 200;
    /**
     * 登记类型 变更登记
     */
    public static final Integer DJLX_BGDJ_DM = 300;
    /**
     * 登记类型 注销登记
     */
    public static final Integer DJLX_ZXDJ_DM = 400;
    /**
     * 登记类型 更正登记
     */
    public static final Integer DJLX_GZDJ_DM = 500;
    /**
     * 登记类型 异议登记
     */
    public static final Integer DJLX_YYDJ_DM = 600;
    /**
     * 登记类型 预告登记
     */
    public static final Integer DJLX_YGDJ_DM = 700;
    /**
     * 登记类型 查封登记
     */
    public static final Integer DJLX_CFDJ_DM = 800;
    /**
     * 登记类型 其他登记
     */
    public static final Integer DJLX_QTDJ_DM = 900;
    /**
     * 不动产类型特征码
     */
    public static final String BDCLX_TZM_W = "W";
    public static final String BDCLX_TZM_F = "F";
    public static final String BDCLX_TZM_L = "L";
    public static final String BDCLX_TZM_Q = "Q";
    /**
     * 不动产单元号的长度
     */
    public static final Integer BDCDYH_LENGTH = 28;

    /**
     * 房屋类型（多幢）
     */
    public static final Integer FWLX_DUOZH = 1;

    /**
     * 不动产房屋类型（独幢房屋）
     */
    public static final Integer BDCDYFWLX_DUZH = 2;
    /**
     * 不动产房屋类型（户室）
     */
    public static final Integer BDCDYFWLX_HS = 4;
    /**
     * 证书
     */
    public static final Integer ZSLX_ZS = 1;
    public static final String DYLX_ZS = "zs";
    /**
     * 证明
     */
    public static final Integer ZSLX_ZM = 2;
    public static final String DYLX_ZM = "zm";
    /**
     * 证明单
     */
    public static final Integer ZSLX_ZMD = 3;
    public static final String DYLX_SCZMD = "sczmd";

    //公告类型-首次登记公告
    public static final String GGLX_SCDJ = "bdcscdjgg";
    public static final Integer GGLX_SCDJ_DM = 1;

    //公告类型-不动产继承/受遗赠登记公告
    public static final String GGLX_JCYZ = "bdcjcyzgg";
    public static final Integer GGLX_JCYZ_DM = 2;

    //公告类型-无权利义务承继人不动产转移登记公告（一）
    public static final String GGLX_ZYDJ1 = "zydjgg1";
    public static final Integer GGLX_ZYDJ1_DM = 3;

    //公告类型-无权利义务承继人不动产转移登记公告（二）
    public static final String GGLX_ZYDJ2 = "zydjgg2";
    public static final Integer GGLX_ZYDJ2_DM = 4;

    //公告类型-不动产更正登记公告
    public static final String GGLX_GZDJ = "bdcgzdjgg";
    public static final Integer GGLX_GZDJ_DM = 5;

    //公告类型-不动产注销登记公告
    public static final String GGLX_ZXDJ = "bdczxdjgg";
    public static final Integer GGLX_ZXDJ_DM = 6;

    //公告类型-不动产撤销登记公告
    public static final String GGLX_CXDJ = "bdccxdjgg";
    public static final Integer GGLX_CXDJ_DM = 7;
    //公告类型-不动产权证书/登记证明作废公告
    public static final String GGLX_ZSZMZFGG = "bdczszmzfgg";
    public static final Integer GGLX_ZSZMZFDJ_DM = 8;

    //公告类型-不动产权证书/登记证明遗失（灭失）声明
    public static final String GGLX_ZSZMYSGG = "bdczszmysgg";
    public static final Integer GGLX_ZSZMYSGG_DM = 9;

    //公告类型-征询异议公告
    public static final String GGLX_ZXYY = "zxyygg";
    public static final Integer GGLX_ZXYY_DM = 10;

    //公告类型-范围注销公告
    public static final String GGLX_FWZX = "fwzxgg";
    public static final Integer GGLX_FWZX_DM = 11;


    /**
     * 证书样式-首次证明单
     */
    public static final String ZS_MODAL_SCZMD = "sczmd";

    /**
     * 地块附表
     */
    public static final String DYLX_DKFB = "dkfb";

    /**
     * 常州证照签章生成 PDF 打印类型后缀
     */
    public static final String DYLX_ZZQZ_SUFFIX = "_zzqz";
    /**
     * 常州发票非税收入打印类型
     */
    public static final String DYLX_FSSR = "fssr_fp";
    /**
     * 常州发票非税收入打印类型
     */
    public static final String DYLX_JSPZ = "jspz_fp";

    /**
     * 发票类别-结算凭证
     */
    public static final String FPLB_JSPZ = "9";
    /**
     * 发票类别-非税收入
     */
    public static final String FPLB_FSSR = "1";
    /**
     * 中文证书
     */
    public static final String ZSLX_ZS_CN = "不动产权证书";
    /**
     * 中文证明书 蚌埠
     */
    public static final String ZSLX_ZMS_CN = "不动产权利证明书";
    /**
     * 中文证明单
     */
    public static final String ZSLX_ZMD_CN = "不动产权证明单";
    /**
     * 中文证明
     */
    public static final String ZSLX_ZM_CN = "不动产登记证明";
    /**
     * 中文首次证明单
     */
    public static final String ZSLX_SCZMD_CN = "首次证明单";
    /**
     * 盐城受理界面提示信息
     */
    public static final String TSXX_YC = "该业务注销的不动产证明（书）存在印制号，请核实！";
    /**
     * 打印配置 参数
     */
    public static final String PRINT_CONFIG_CS = "CS";
    /**
     * 打印配置 detail id
     */
    public static final String DY_ZB_ID = "DYZBID";
    /**
     * 打印数据源
     */
    public static final String DY_SJY = "DYSJY";
    /**
     * 打印字表数据源
     */
    public static final String DY_ZB_SJY = "DYZBSJY";
    /**
     * 打印类型
     */
    public static final String BDC_DYLX = "dylx";
    /**
     * 配置sql xmid参数
     */
    public static final String SQL_CS_XMID = "#{xmid}";
    /**
     * 发证记录打印类型-列表批量打印
     */
    public static final String FZJL_DYLX_LIST = "fzjlList";
    /**
     * 发证记录类型——按项目打印
     */
    public static final String FZJL_DYLX_XM = "fzjl";
    // 发证记录类型——按流程合并打印
    public static final String FZJL_DYLX_HB = "fzjlHb";

    /**
     * 中文字符 ；
     */
    public static final String ZF_ZW_FH = "；";

    /**
     * 中文字符 、
     */
    public static final String ZF_ZW_DH = "、";
    /**
     * 英文分号
     */
    public static final String ZF_YW_FH = ";";

    /**
     * 英文逗号
     */
    public static final String ZF_YW_DH = ",";

    /*
     * 横杠
     * */
    public static final String ZF_YW_HG = "-";
    /**
     * 估值符号
     */
    public static final String ZF_GZH = "^";

    /**
     * 英文斜杠
     */
    public static final String ZF_YW_XG = "/";
    /**
     * 中文字符 ：
     */
    public static final String ZF_ZW_MH = "：";
    /**
     * 英文字符 ：
     */
    public static final String ZF_YW_MH = ":";
    /**
     * 换行符
     */
    public static final String ZF_HH_CHAR = "\n";

    /**
     * 英文点
     */
    public static final String ZF_YW_JH = ".";
    /**
     * 英文左中括号
     */
    public static final String ZF_YW_Z_ZKH = "[";
    /**
     * 英文左大括号
     */
    public static final String ZF_YW_Z_DKH = "{";
    /**
     * 英文左小括号
     */
    public static final String ZF_YW_Z_XKH = "(";

    /**
     * 英文右小括号
     */
    public static final String ZF_YW_Y_XKH = ")";

    /**
     * 中文左中括号
     */
    public static final String ZF_ZW_Z_ZKH = "【";

    /**
     * 中文右中括号
     */
    public static final String ZF_ZW_Y_ZKH = "】";

    /**
     * 权证号截取依据字符
     */
    public static final String DI = "第";
    public static final String HAO = "号";
    /**
     * 空格，注意是空格，不是空字符串
     */
    public static final String ZF_KG_CHAR = " ";
    /**
     * 归档信息xml地址
     */
    public static final String XML_PATH_GDXX = "/bdcGd/bdcGdxx.xml";
    /**
     * 归档名称配置xml地址
     */
    public static final String XML_PATH_GDMC = "/bdcGd/bdcGdmc.xml";
    /**
     * 收费信息xml配置地址
     */
    public static final String XML_PATH_SFXX = "/bdcSfxx/bdcSfxx.xml";
    /**
     * 登记信息是否归多条
     */
    public static final String GD_DJXXGDT = "DJXXGDT";
    /**
     * 原文是否归多条
     */
    public static final String GD_YWGDT = "YWGDT";


    /**
     * 查封类型 查封
     */
    public static final Integer CFLX_CF = 1;
    /**
     * 查封类型 轮候查封
     */
    public static final Integer CFLX_LHCF = 2;
    /**
     * 查封类型 预查封
     */
    public static final Integer CFLX_YCF = 3;
    /**
     * 查封类型 轮候预查封
     */
    public static final Integer CFLX_LHYCF = 4;
    /**
     * 查封类型 续封
     */
    public static final Integer CFLX_XF = 5;

    /**
     * 查封权利类型
     */
    public static final Integer QLLX_CF = 98;
    /**
     * 查封权利类型
     */
    public static final Integer QLLX_YY = 97;
    /**
     * 土地承包 农用地使用权 权利类型
     */
    public static final Integer QLLX_TDCBNYDSYQ = 9;
    /**
     * 农用地经营权权利类型
     */
    public static final Integer QLLX_NYDJYQ = 50;

    /**
     * 居住权权利类型
     */
    public static final Integer QLLX_JZQ = 92;

    /**
     * 预告登记种类 预售商品房买卖预告登记
     */
    public static final Integer YGDJZL_YSSPFYG = 1;
    /**
     * 其它不动产买卖预告登记
     */
    public static final Integer YGDJZL_QTYG = 2;
    /**
     * 预售商品房抵押权预告登记
     */
    public static final Integer YGDJZL_YSSPFDYYG = 3;
    /**
     * 其它不动产抵押权预告登记
     */
    public static final Integer YGDJZL_QTDYYG = 4;
    /**
     * 农村不动产确权登记 权利类型
     */
    public static final Integer QLLX_NCBDCQQ = 6;

    /*同步权籍不动产单元状态标识：现势*/
    public static final Integer BDCDYZT_XS = 1;
    /*同步权籍不动产单元状态标识：历史*/
    public static final Integer BDCDYZT_LS = -1;

    /*同步权籍不动产单元登记状态：已登记*/
    public static final Integer BDCDYDJZT_YDJ = 1;
    /*同步权籍不动产单元登记状态：已注销*/
    public static final Integer BDCDYDJZT_YZX = 2;
    /*同步权籍不动产单元登记状态：未登记*/
    public static final Integer BDCDYDJZT_WDJ = 0;

    public static final Integer[] CFLX_YCF_ARR = {3, 4};

    /*预告登记种类：预告*/
    public static final Integer[] YG_YGDJZL_ARR = {1, 2};
    /*预告登记种类：预抵押*/
    public static final Integer[] YG_YGDJZL_YDY_ARR = {3, 4};

    /*预告登记：预抵押*/
    public static final String YGDJ_YDY = "ydya";

    /*抵押不动产类型：在建建筑物*/
    public static final Integer DYBDCLX_ZJJZW = 4;

    /*抵押不动产类型：林权*/
    public static final Integer DYBDCLX_LQ = 3;

    /*抵押不动产类型：纯土地*/
    public static final Integer DYBDCLX_CTD = 1;

    /*抵押不动产类型：房地一体*/
    public static final Integer DYBDCLX_FDYT = 2;

    public static final String[] DYTJ_BDCLX = {"1", "2", "3", "4"};

    /*土地用途：工业类*/
    public static final String TDYT_GYL = "06";

    /*抵土地用途：住宅类*/
    public static final String TDYT_ZZL = "07";

    /*2为房屋*/
    public static final Integer FWBDCLX = 2;

    /**
     * 不动产单元锁定状态——锁定
     */
    public static final Integer SDZT_SD = 1;

    /**
     * 不动产单元锁定类型-裁决锁定
     */
    public static final Integer SDLX_CJSD = 9;

    /**
     * 不动产单元备案状态——备案
     */
    public static final Integer BAZT_BA = 1;

    /**
     * 不动产单元备案状态——未备案
     */
    public static final Integer BAZT_WBA = 0;

    /**
     * 不动产单元锁定状态——解锁
     */
    public static final Integer SDZT_JS = 0;


    public static final String[] ZH_TZM = {"G", "H"};

    /**
     * 宗地特征码E
     */
    public static final String ZDTZM_E = "E";

    /**
     * 宗地特征码G
     */
    public static final String ZDTZM_G = "G";

    /**
     * 宗地特征码H
     */
    public static final String ZDTZM_H = "H";

    /**
     * 宗地特征码L
     */
    public static final String ZDTZM_L = "L";

    /**
     * 宅基地特征码
     */
    public static final String ZJDTZM_JC = "JC";

    /**
     * 权籍表GZW_DCB
     */
    public static final String LX_GZW = "GZW";

    /**
     * 权籍表FW_YCHS
     */
    public static final String LX_YCHS = "YCHS";


    /*生成证书的权利类型 仅限于判定类型,不能用于判定是否生成证 */
    public static final Integer[] BDC_ZS_QLLX = {1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 23, 24, 25, 26, 30,31,35,36,94};

    /*生成证明的权利类型  仅限于判定类型,不能用于判定是否生成证*/
    public static final Integer[] BDC_ZM_QLLX = {19, 20, 21, 22, 93, CommonConstantUtils.QLLX_DYAQ_DM, 96, 97, 98, 99};

    /*林地承包经营权*/
    public static final Integer[] BDC_LQ_CBYJQ_QLLX = {35};

    /*林地使用权*/
    public static final Integer[] BDC_LQ_SYQ_QLLX = {11,12,31,36};

    /*林地经营权*/
    public static final Integer[] BDC_LQ_JYD_QLLX = {33,34};

    /**
     * 使用情况
     */
    /**
     * 未使用
     */
    public static final Integer SYQK_WSY = 0;
    /**
     * 已打证
     */
    public static final Integer SYQK_YDZ = 1;
    /**
     * 作废
     */
    public static final Integer SYQK_ZF = 2;
    /**
     * 已使用
     */
    public static final Integer SYQK_YSY = 3;
    /**
     * 遗失
     */
    public static final Integer SYQK_YS = 4;
    /**
     * 销毁
     */
    public static final Integer SYQK_XH = 5;
    /**
     * 已领用
     */
    public static final Integer SYQK_YLY = 6;
    /**
     * 其他
     */
    public static final Integer SYQK_QT = 99;
    /**
     * 左右括号
     */
    public static final String BDCQ_BH_LEFT_BRACKET = "(";
    public static final String BDCQ_BH_RIGHT_BRACKET = ")";
    /**
     * 中文括号
     */
    public static final String BDCQ_BH_LEFT_BRACKET_CN = "（";
    public static final String BDCQ_BH_RIGHT_BRACKET_CN = "）";
    /**
     * 意见类型 默认意见
     */
    public static final Integer YJLX_MRYJ = 0;
    /**
     * 意见类型 可选意见
     */
    public static final Integer YJLX_KXYJ = 1;
    /**
     * 不动产权证号注销标识
     */
    public static final String BDCQZH_ZX = "（销）";

    public static final String BDCQZH_LS = "（临时）";

    /**
     * Redis分布式锁名称定义
     */
    public static final String REDISSON_LOCK_SJPTCXSQ = "REDISSON_LOCK_SJPTCXSQ";

    /**
     * Redis分布式锁名称定义
     */
    public static final String REDISSON_LOCK_SBXZ = "REDISSON_LOCK_SBXZ";


    public static final String REDISSON_LOCK_CLSJSBXZ = "REDISSON_LOCK_CLSJSBXZ";

    /*
     * Redis分布式锁名称定义-检查登簿接入日志量是否一致
     * */
    public static final String REDISSON_LOCK_DBJRL = "REDISSON_LOCK_DBJR";

    //*
    // Redis分布式锁名称定义-检查登簿日志是否上报
    // */
    public static final String REDISSON_LOCK_DBSB = "REDISSON_LOCK_DBSB";
    /**
     * 不动产权证号锁名称
     */
    public static final String REDISSON_LOCK_BDCQZH = "REDISSON_LOCK_BDCQZH";
    /**
     * 不动产印制号锁名称
     */
    public static final String REDISSON_LOCK_BDCYZH = "REDISSON_LOCK_BDCYZH";

    /**
     * 不动产移交单编号锁名称
     */
    public static final String REDISSON_LOCK_BDCYJDBH = "REDISSON_LOCK_BDCYJDBH";

    /**
     * 查询申请书受理编号REDIS KEY
     */
    public static final String REDIS_LOCK_CXSQS_SLBH = "REDIS_LOCK_CXSQS_SLBH";

    /**
     * 不动产项目编号锁名称
     */
    public static final String REDISSON_LOCK_BDCXMBH = "REDISSON_LOCK_BDCXMBH";

    /**
     * 自然资源项目编号锁名称
     */
    public static final String REDISSON_LOCK_ZRZYXMBH = "REDISSON_LOCK_ZRZYXMBH";

    /**
     * 不动产虚拟不动产单元锁名称
     */
    public static final String REDISSON_LOCK_XNBDCDYH = "REDISSON_LOCK_XNBDCDYH";
    /**
     * 发票号锁名称
     */
    public static final String REDISSON_LOCK_FPH = "REDISSON_LOCK_FPH";

    /**
     * 不动产查封编号锁名称
     */
    public static final String REDISSON_LOCK_CFBH = "REDISSON_LOCK_CFBH";

    /**
     * 供水供气
     */
    public static final String REDISSON_LOCK_GSGQ = "REDISSON_LOCK_GSGQ";

    /**
     * 抵押统计月报打印：key前缀
     */
    public static final String REDIS_DYATJ_MONTH_PRINT = "REDIS_DYATJ_MONTH_PRINT_";

    /**
     * 抵押统计日报打印：key前缀
     */
    public static final String REDIS_DYATJ_DAY_PRINT = "REDIS_DYATJ_DAY_PRINT_";

    /**
     * 不动产证书证明批量打印：key前缀
     */
    public static final String REDIS_BATCH_ZSZM_PRINT = "REDIS_BATCH_ZSZM_PRINT_";

    /**
     * 不动产查询：不动产单元号保存Redis key前缀
     */
    public static final String REDIS_INQUIRY_BDCDYH = "REDIS_INQUIRY_BDCDYH_";
    /**
     * 南通其他证明数据缓存Redis key
     */
    public static final String REDIS_INQUIRY_NT_QTZM = "REDIS_INQUIRY_NT_QTZM_";
    /**
     * 抵押证明：不动产单元号保存Redis key前缀
     */
    public static final String REDIS_INQUIRY_DYZM = "REDIS_INQUIRY_DYZM_";

    /**
     * 不动产住房查询：权利人证件号保存Redis key前缀
     */
    public static final String REDIS_INQUIRY_ZFXX = "REDIS_INQUIRY_ZFXX_";
    /**
     * 标准版综合查询：房产证明参数信息保存Redis key前缀
     */
    public static final String REDIS_INQUIRY_BZB_FCZM = "REDIS_INQUIRY_BZB_FCZM_";

    /**
     * 规则保存表达式内容KEY
     */
    public static final String REDIS_RULE_EXPRESSION = "REDIS_RULE_EXPRESSION";

    /**
     * 特殊业务配KEY
     */
    public static final String REDIS_TSYW_PZ = "REDIS_TSYW_PZ";
    /**
     * 收费项目xml 配置
     */
    public static final String REDIS_INQUIRY_BDC_SFXX_XML = "REDIS_INQUIRY_BDC_SFXX_XML";
    /**
     * 项目YML配置内容KEY
     */
    public static final String REDIS_INIT_YML = "REDIS_INIT_YML";
    /**
     * 自定义查询打印参数缓存Redis Key前缀
     */
    public static final String REDIS_ZDYCX_PRINT = "REDIS_ZDYCX_PRINT_";

    /**
     * 省级查询申请Redis Key前缀
     */
    public static final String REDIS_SJPT_CXSQ = "REDIS_SJPT_CXSQ";

    /**
     * 蚌埠监听外网申请FTP压缩文件进行登记创件功能 外网申请文件名称集合
     */
    public static final String REDIS_BENGBU_WWSQ = "REDIS_BENGBU_WWSQ";
    /**
     * 合肥一窗通办登记办件附件信息缓存
     */
    public static final String REDIS_HEFEI_YCTB_WWSQ_FJXX_PREFIX = "HEFEI_YCTB_WWSQ_FJXX_";
    /**
     * 盐城市级接口token缓存
     */
    public static final String REDIS_YANCHENG_CITY_INTERFACE_TOKEN_AND_CURRTIME_KEY = "YANCHENG_CITY_INTERFACE_TOKEN_AND_CURRTIME_KEY";

    /**
     * 盐城工信局接口token缓存
     */
    public static final String REDIS_YANCHENG_GGJ_INTERFACE_TOKEN_AND_CURRTIME_KEY = "YANCHENG_GXJ_INTERFACE_TOKEN_AND_CURRTIME_KEY";

    /**
     * PDF打印文件上传到大云storageId和预览页面指定UUID关联关系 Hash key
     */
    public static final String REDIS_PDF_FILE = "REDIS_PDF_FILE";

    /**
     * 批量发证签收单打印参数缓存Redis Key前缀
     */
    public static final String REDIS_PLFZQSD_PRINT = "REDIS_PLFZQSD_PRINT_";

    /**
     * 同步权籍标识：登簿还是退回
     */
    public static final String TBQJ_DB = "DB";
    public static final String TBQJ_TH = "TH";


    /**
     * 权利类型：预告
     */
    public static final Integer QLLX_YG_DM = 96;
    /**
     * 权利类型：地役权
     */
    public static final Integer QLLX_DYIQ_DM = 19;
    /**
     * 权利类型：房屋租赁
     */
    public static final Integer QLLX_FWZL_DM = 93;
    /**
     * 权利类型：抵押
     */
    public static final Integer QLLX_DYAQ_DM = 37;
    /**
     * 权利类型：林地使用权/森林、林木使用权
     */
    public static final Integer  QLLX_SLLMSYQ = 12;

    /**
     * 国有建设用地使用权
     */
    public static final String QLXX_QLLX_JSYDSYQ = "3";

    /**
     * 宅基地使用权
     */
    public static final String QLXX_QLLX_ZJDSYQ = "5";

    /**
     * 集体建设用地使用权
     */
    public static final String QLXX_QLLX_JTJSYDSYQ = "7";

    /**
     * 土地所有权
     */
    public static final String QLXX_QLLX_TDSYQ = "1";

    /**
     * 土地承包经营权、农用地的其他使用权登记信息
     */
    public static final String QLXX_QLLX_TDCBJYQNYDDQTSYQ = "9";

    /**
     * 林权登记信息
     */
    public static final String QLXX_QLLX_LQ = "31";

    /**
     * 地役权信息
     */
    public static final String QLXX_QLLX_DYQ = "19";

    /**
     * 权利类型：建筑物区分所有权业主共有部分
     */
    public static final Integer QLLX_JZWQFSYQYZGYBF_DM = 94;

    public static final Integer QLLX_FDCQ_DM = 4;
    public static final Integer QLLX_HY_DM = 15;
    public static final Integer QLLX_GZW_DM = 16;
    // 集体建设用地使用权/房屋所有权
    public static final Integer QLLX_JTJSSYQ_FWSYQ_DM = 8;
    public static final Integer QLLX_JTJSSYQ_DM = 7;
    // 国有建设用地使用权
    public static final Integer QLLX_GYJSYDSYQ_DM = 3;
    /**
     * 实体属性或对应方法名称常量定义
     */
    public static final String BDCLX = "bdclx";
    /**
     * 审核节点
     */
    public static final String BDC_SHJD_CS = "初审";
    public static final String BDC_SHJD_FS = "复审";
    public static final String BDC_SHJD_HD = "核定";
    /**
     * 审核节点ID
     */
    public static final String BDC_SHJDID_CS = "cs";
    public static final String BDC_SHJDID_FS = "fs";
    public static final String BDC_SHJDID_HD = "hd";

    /**
     * 审核节点配置
     */
    public static final String BDC_SHJD_PZ = "sign";
    /**
     * 字典表名：单位信息
     */
    public static final String TABLE_S_DM_DWXX = "s_dm_dwxx";
    /**
     * 字典表名：行政信息
     */
    public static final String TABLE_S_DM_XZXX = "s_dm_xzxx";
    /**
     * 字段名称:行政代码
     */
    public static final String COLUMN_XZDM = "XZDM";
    /**
     * 字段名称：行政名称
     */
    public static final String COLUMN_XZMC = "XZMC";
    /**
     * 字段名称：单位代码
     */
    public static final String COLUMN_DWDM = "DWDM";
    /**
     * 字段名称：单位名称
     */
    public static final String COLUMN_DWMC = "DWMC";

    /**
     * 印制号领取方式：按部门
     */
    public static final String YZH_LQFS_BM = "0";
    /**
     * 印制号领取方式：按人员
     */
    public static final String YZH_LQFS_RY = "1";
    /**
     * 印制号领取方式：只根据默认的区县代码
     */
    public static final String YZH_LQFS_QXDM = "2";
    /**
     * 不动产单元类型特征码,在单元号的位置
     */
    public static final int BDCLX_TZM_INDEX = 20;

    /**
     * 共有方式——单独所有
     */
    public static final Integer GYFS_DDSY = 0;
    /**
     * 共有方式——共同共有
     */
    public static final Integer GYFS_GTGY = 1;
    /**
     * 共有方式——按份共有
     */
    public static final Integer GYFS_AFGY = 2;
    /**
     * 共有方式——其它共有
     */
    public static final Integer GYFS_QTGY = 3;

    /**
     * 共有人标识--产权人
     */
    public static final String GYRBJ_CQR = "0";
    /**
     * 共有方式——共有人
     */
    public static final String GYRBJ_GYR = "1";

    /**
     * 默认意见数据类型——SQL
     */
    public static final Integer MRYJ_SJLX_SQL = 1;
    /**
     * 表单名称
     */
    public static final String FORM_NAME_SH = "审核信息";
    /**
     * 南通印制号特殊数据
     */
    public static final String TSYZH_NT = "AV国";
    /**
     * 提示信息验证类型：confirm
     */
    public static final Integer YZLX_CONFIRM = 1;
    /**
     * 提示信息验证类型：alert
     */
    public static final Integer YZLX_ALERT = 3;
    /**
     * 限制权利 抵押 预告 异议 查封 地役 ,居住权
     */
    public static final Integer[] BDC_QLLX_XZQL = {CommonConstantUtils.QLLX_DYAQ_DM, 96, 97, 98, 19, CommonConstantUtils.QLLX_JZQ};
    /**
     * 房产权利
     */
    public static final Integer[] QLLX_FDCQ = {4, 6, 8};

    /**
     * 出土地权利
     */
    public static final Integer[] QLLX_CTD = {1, 2, 3};
    /**
     * 权利类型
     */
    public static final String BDC_QLLX = "qllx";
    /**
     * 单元号权利类型
     */
    public static final String BDC_DYHQLLX = "dyhqllx";
    /**
     * 字典项代码
     */
    public static final String ZD_DM = "DM";

    /**
     * 电子证照
     */
    public static final String SJCL_DZZZ_NAME = "电子证照";

    /**
     * 收费标准--工本费
     */
    public static final String SFXM_BZ_GBF = "工本费/10元";

    /**
     * 收费标准--登记费代码
     */
    public static final String SFXM_BZ_DJF_DM = "1";

    /**
     * 收费标准--登记费--非住宅--550--名称
     */
    public static final String SFXM_BZ_DJFMC_FZZ = "登记费(非住宅)/550元";

    /**
     * 收费标准--登记费--住宅--80--名称
     */
    public static final String SFXM_BZ_DJFMC_ZZ = "登记费(住宅)/80元";

    /**
     * 收费标准--登记费--非住宅--减半--550--名称
     */
    public static final String SFXM_BZ_DJFMC_FZZ_JB = "登记费减半(非住宅)/275元";

    /**
     * 收费标准--登记费--住宅--减半--80--名称
     */
    public static final String SFXM_BZ_DJFMC_ZZ_JB = "登记费减半(住宅)/40元";

    /**
     * 收费标准--登记费--住宅--80--金额标准
     */
    public static final Double SFXM_BZ_DJF_ZZ_JE = 80.00;

    /**
     * 收费标准--登记费--非住宅--550--金额标准
     */
    public static final Double SFXM_BZ_DJF_FZZ_JE = 550.00;


    /**
     * 收费标准--登记费--住宅 -- 减半--80--金额标准
     */
    public static final Double SFXM_BZ_DJF_ZZ_JB_JE = 40.00;

    /**
     * 收费标准--登记费--非住宅 -- 减半--275--金额标准
     */
    public static final Double SFXM_BZ_DJF_FZZ_JB_JE = 275.00;

    /**
     * 收费项目名称-登记费--住宅--80--名称
     */
    public static final String SFXMMC_DJF_ZZ = "不动产登记费（住宅）";

    /**
     * 收费项目名称--登记费--非住宅--550--名称
     */
    public static final String SFXMMC_DJF_FZZ = "不动产登记费（非住宅）";

    // 一窗水电气打印类型
    public static final String YCSDQDYLX = "ycsdqsqb";

    // 涉税申请单个人
    public static final String YCSSSQDGRDYLX = "ycslgrsqd";
    // 涉税申请单单位
    public static final String YCSSSQDDWDYLX = "ycsldwsqd";

    //所有的收费单打印类型
    public static final String[] SFD_ALL_DYLX = {"sfd", "dysfd", "sfdpl", "dysfdpl", "ycslsfd", "jnpz", "jnpzpl", "dyjnpz", "dyjnpzpl", "hmsflxd", "hmsflxdpl", "jktzs", "jktzspl", "djfyjjktzs", "yczfjktzs"};

    //需要获取收费单二维码打印类型
    public static final String[] SFDEWM_DYLX = {"ycsftzd", "ycsfjnpz"};

    //打印内容需要从受理库获取的打印类型

    public static final String[] Sl_ALL_DYLX = {"sfd", "dysfd", "sfdpl", "dysfdpl", "sjd", "sjdpl", "czsjd", "czsjdpl",
            "ycslsfd", "djgzs", "djgzspl", "qjdcsjd", "cqsjd", "dyasjd", "noczsjd", "qtsjd", "ycsftzd", "ycsjd",
            "ycspb", "jnpz", "dyjnpz", "jnpzpl", "dyjnpzpl", "ycsfjnpz", CommonConstantUtils.DYLX_ZHSJD, "xqxx",
            "lscns"
    };

    //受理页面打印需要获取登记库的打印类型

    public static final String[] Sl_DJ_DYLX = {"sqs", "zysqs", "zxsqs", "dyzxsqs", "cfjds", "sqspl", "bdcdyList", "grsqs", "dwsqs", "dyasqs", "grsqspl", "dwsqspl", "dyasqspl",
            "gyxx", "grxwbl", "dwxwbl", "dyaxwbl", "grxwblpl", "dwxwblpl", "dyaxwblpl", "rzdb", "yszm", "cfhzd", "cfhzdpl", "ysggsqs", "ysggsqspl", "qrs",
            "bdcdjbcxfcztdz", "bdcdjbcxbdcqz", "bdcdjbcxzmd", "sdcx", "jfxxd", "jfxxdpl", "zxzms", "zxzmspl", "qlrsqrd", "lhgxsqd", "zlhgxsqd"
    };

    //蚌埠申请书打印分产权和抵押,特殊打印方式
    public static final String[] Sl_DJ_DYLX_BB = {"sqs", "sqspl", "dyasqs", "dyasqspl"};

    /**
     * 申请书打印类型
     **/
    public static final String[] SL_SQS_DYLX = {"grsqs", "dwsqs", "dyasqs", "grsqspl", "dwsqspl", "dyasqspl", "grsqs-zh", "grsqs-cq", "dwsqs-cq", "grsqs-zh", "grsqs-cqzx", "dwsqs-cqzx", "dwsqs-spfscdj", "ycsqd",
            "grsqs-zhpl", "grsqs-cqpl", "dwsqs-cqpl", "grsqs-zhpl", "grsqs-cqzxpl", "dwsqs-cqzxpl", "dwsqs-spfscdjpl", "ycsqdpl"};

    /**
     * 询问笔录打印类型
     **/
    public static final String[] SL_XWBL_DYLX = {"grxwbl", "dwxwbl", "dyaxwbl", "grxwblpl", "dwxwblpl", "dyaxwblpl"};

    /**
     * 查封决定书打印类型
     **/
    public static final String[] SL_JDS_DYLX = {"cfjds", "xfjds", "lhcfjds", "jfjds", "ycfjds", "cdghjds"};

    /**
     * 共有信息和单元附页
     */
    public static final String[] DJ_OTHER_DYLX = {"gyxx", "bdcdyList"};

    /**
     * 打印内容需要从受理库获取的打印类型
     */
    public static final String[] SL_ALL_DYLX = {"sfd", "dysfd", "sfdpl", "dysfdpl", "sjd", "sjdpl", "czsjd", "czsjdpl", "ycslsfd", "djgzs", "djgzspl", "qjdcsjd", "cqsjd", "dyasjd", "noczsjd", "qtsjd", "ycsl", "ythqrd", "ythclfqrd"};

    /**
     * 缴费信息打印
     */
    public static final String[] SL_JFXX_DYLX = {"ewmtspjq", "hyzfdy"};
    /**
     * 打印需要参数需要将值替换为登记库字典数据的打印类型
     **/
    public static final String[] SL_ZD_DYLX = {"ycslgrsqd", "ycsldwsqd"};

    /**
     * 自定义打印xml数据源地址的打印类型
     * 例：
     */
    public static final String[] SL_ZDY_URL_DYLX = {"yjsfd", "xqxx"};

    /**
     * 打印种类分为pdf和fr3打印
     **/
    public static final String BDC_DYZL_PDF = "pdf";

    public static final String BDC_DYZL_FR3 = "fr3";
    /**
     * 证件种类——身份证
     */
    public static final Integer ZJZL_SFZ = 1;

    /**
     * 证件种类——其他
     */
    public static final Integer ZJZL_QT = 99;
    /**
     * 证件种类——营业执照
     */
    public static final Integer ZJZL_YYZZ = 7;

    /*
     * 证件种类-统一社会信用代码
     * */
    public static final Integer ZJZL_SHXYDM = 8;
    /**
     * 不动产异议期限
     */
    public static final Integer BDC_YY_QX = 15;
    /**
     * 规则验证类型 转发
     */
    public static final String GZYZ_LX_ZF = "zf";
    /**
     * 规则验证模板 转发
     */
    public static final String GZYZ_MB_ZF = "processKey_LCZF";

    /**
     * 规则验证模板 转发后
     */
    public static final String GZYZ_MB_ZFH = "processKey_LCZFH";


    /**
     * 规则验证类型 退回
     */
    public static final String GZYZ_LX_TH = "th";

    /**
     * 规则验证类型 退回
     */
    public static final String GZYZ_MB_TH = "processKey_LCTH";
    /**
     * 规则验证类型 删除
     */
    public static final String GZYZ_LX_SC = "sc";

    /**
     * 规则验证类型 删除
     */
    public static final String GZYZ_MB_SC = "processKey_LCSC";
    /**
     * 规则验证类型 申请删除
     */
    public static final String GZYZ_LX_SQSC = "sqsc";

    /**
     * 规则验证类型 申请删除
     */
    public static final String GZYZ_MB_SQSC = "processKey_SQSC";
    /**
     * 规则验证类型 删除
     */
    public static final String GZYZ_LX_RL = "rl";

    /**
     * 规则验证类型 删除
     */
    public static final String GZYZ_MB_RL = "processKey_LCRL";
    /**
     * 节点名称
     */
    public static final String JD_SL = "受理";

    public static final String JD_SZ = "缮证";
    /**
     * 宗地图地址
     */
    public static final String ZDT_URL = "/rest/v1.0/zs/print/zdt/{bdcdyh}";
    /**
     * 宗地图地址
     */
    public static final String ZDT_URL2 = "/rest/v1.0/zs/print/zdt/{bdcdyh}/{qjgldm}";

    /**
     * 户室图地址
     */
    public static final String HST_URL = "/rest/v1.0/zs/print/hst/{bdcdyh}";
    /**
     * 户室图地址
     */
    public static final String HST_URL2 = "/rest/v1.0/zs/print/hst/{bdcdyh}/{qjgldm}";
    /**
     * 档案信息图片地址
     */
    public static final String DAXX_IMAGE_PRINT_URL = "/rest/v1.0/print/hefei/image/{userName}/{archid}/{currentpage}";
    /**
     * 房产证明
     */
    public static final String REDIS_INQUIRY_NT_FCZM = "REDIS_INQUIRY_NT_FCZM";
    /**
     * 房产档案
     */
    public static final String REDIS_INQUIRY_FCDA = "REDIS_INQUIRY_FCDA_";
    /**
     * 标准版 综合查询房产证明
     */
    public static final String REDIS_INQUIRY_BZB_QSZM = "REDIS_INQUIRY_BZB_QSZM_";

    public static final String REDIS_INQUIRY_BDCDYCX = "REDIS_INQUIRY_BDCDYCX_";
    /**
     * 标准版 查档信息查询结果
     */
    public static final String REDIS_INQUIRY_BZB_CDXXCX = "REDIS_INQUIRY_BZB_CDXXCX_";

    /**
     * 权利其他状况附记 权利其他状况
     */
    public static final String XT_QLQTZK_FJ_MODE_QLQTZK = "2";
    /**
     * 权利其他状况附记 附记
     */
    public static final String XT_QLQTZK_FJ_MODE_FJ = "3";

    /**
     * lclx  流程类型  普通
     */
    public static final Integer LCLX_PT = 1;
    /**
     * lclx  流程类型  组合
     */
    public static final Integer LCLX_ZH = 2;
    /**
     * lclx  流程类型  组合str
     */
    public static final String LCLX_ZH_STR = "zhlc";
    /**
     * lclx  流程类型  批量
     */
    public static final Integer LCLX_PL = 3;
    /**
     * lclx  流程类型  批量str
     */
    public static final String LCLX_PL_STR = "pllc";
    /**
     * lclx  流程类型  批量组合
     */
    public static final Integer LCLX_PLZH = 4;

    public static final String PORTAL_UI = "portal-ui";

    public static final String CONFIG_UI = "config-ui";

    public static final String HLLX = "忽略不动产单元验证";

    public static final String HLNR = "HLNR";

    public static final String LWLX = "例外不动产单元验证";

    public static final String LWNR = "LWNR";

    /**
     * 规则验证-忽略-创建
     */
    public static final String GZYZ_HL = "YZHL";

    /**
     * 规则验证-忽略-创建
     */
    public static final String RZLX_HSHB = "HSHB";

    public static final String HLLX_HSHB = "忽略户室合并不动产单元验证";

    public static final String HSHB = "户室合并操作";

    /**
     * 规则验证-忽略-转发
     */
    public static final String GZYZ_HL_ZF = "YZHL_ZF";
    /**
     * 规则验证-例外
     */
    public static final String GZYZ_LW = "YZLW";
    /**
     * 规则强制验证内容导出的文件名称
     */
    public static final String GZ_QZYZ_FILE_NAME = "BdcGzQzyzFile";
    /**
     * 流程选择不动产单元时候对应的组合规则验证标识后缀
     */
    public static final String LC_ZHGZBS_XZBDCDY = "_XZBDCDY";
    /**
     * 流程转发时候对应的组合规则验证标识后缀
     */
    public static final String LC_ZHGZBS_LCZF = "_LCZF";
    /**
     * 流程选择外联证书时对应的组合规则验证标识后缀
     */
    public static final String LC_ZHGZBS_WLZS = "_WLZS";

    /**
     * 匹配单元号时候对应的组合规则验证标识后缀
     */
    public static final String LC_ZHGZBS_PPDYH = "YZPPDYH";


    /**
     * 匹配土地证时候对应的组合规则验证标识后缀
     */
    public static final String LC_ZHGZBS_PPTDZ = "YZPPTDZ";
    /**
     * 流程强制验证对应的组合规则验证标识后缀
     */
    public static final CharSequence LC_ZHGZBS_QZYZ = "_QZYZ";
    /**
     * 不动产规则数据流SQL入参默认值
     */
    public static final String BDC_GZ_SJL_RC_MRZ = "***###";
    /**
     * 不动产规则数据流服务入参默认值
     */
    public static final String BDC_GZ_RPC_RC_MRZ = "AAAZZZ";
    /**
     * 必填项验证sql,默认条件
     */
    public static final String VALID_SQL_CONDITION_DEFAULT = "default";

    /**
     * 抵押物清单打印类型
     */
    public static final String DYWQD_DYLX = "dyawqd";


    /**
     * 权利人类型：个人
     */
    public static final Integer QLRLX_GR = 1;
    /**
     * 权利人类型：企业
     */
    public static final Integer QLRLX_QY = 2;
    /**
     * 权利人类型：事业单位
     */
    public static final Integer QLRLX_SYDW = 3;
    /**
     * 权利人类型：国家机关
     */
    public static final Integer QLRLX_GJJG = 4;
    /**
     * 权利人类型：小微企业
     */
    public static final Integer QLRLX_XWQY = 98;
    /**
     * 权利人类型：其他
     */
    public static final Integer QLRLX_QT = 99;

    /**
     * 项目表审批来源  登记系统
     */
    public static final Integer SPLY_DJXT = 0;

    /**
     * 项目表审批来源  一窗受理
     */
    public static final Integer SPLY_YCSL = 1;

    /**
     * 项目表审批来源  互联网+
     */
    public static final Integer SPLY_WWSQ = 3;

    /**
     * 项目表审批来源  互联网+详细,
     * 互联网+非银行3                   3
     * 互联网+银行：6，                  6
     * 12:互联网+法院,                  15
     * 13:互联网+中介,                  17
     * 14:互联网+个人,                  18
     * 15:互联网+开发商                 19
     * 15:互联网+拆迁办                 22
     */
    public static final List<Integer> SPLY_WWSQ_DETAIL = Arrays.asList(3, 15, 17, 18, 19, 22);

    /**
     * 项目表审批来源  银行系统
     */
    public static final Integer SPLY_YHXT = 4;

    /**
     * 项目表审批来源  一窗受理 +互联网+
     */
    public static final Integer SPLY_WWSQ_YCSL = 5;
    /**
     * 项目表审批来源  互联网+开发的银行系统
     */
    public static final Integer SPLY_WWSQ_YHXT = 6;
    /**
     * 项目表审批来源  盐城征迁
     */
    public static final Integer SPLY_YC_ZQ = 7;
    /**
     * 项目表审批来源  法院子系统
     */
    public static final Integer SPLY_FY = 9;
    /**
     * 项目表审批来源  一窗通办系统
     */
    public static final Integer SPLY_YCTB = 10;
    /**
     * 项目表审批来源  一窗通办系统 涉税
     */
    public static final Integer SPLY_YCTB_SS = 11;

    /**
     * 项目表审批来源  舒城共享系统
     */
    public static final Integer SPLY_GXXT = 15;

    /**
     * 项目表审批来源  工改系统
     */
    public static final Integer SPLY_GGXT = 23;


    /**
     * 项目表审批来源  合肥省金融平台
     */
    public static final Integer SPLY_SJYPT = 20;
    /**
     * 项目表审批来源  一窗通办系统 标识
     */
    public static final String SPLY_YCTB_NAME = "YCTB";
    /**
     * 南通区县代码
     */
    public static final String QXDM_NT = "320600";
    /**
     * 自助打证机印制号使用明细原因
     */
    public static final String YZH_SYYY = "证书印制号获取！";


    /**
     * 审批来源字典码
     */
    public static final String SPLY_ZD = "sply";

    /**
     * 登记小类字典码
     */
    public static final String DJXL_ZD = "djxl";
    /**
     * 登记类型字典码
     */
    public static final String DJLX_ZD = "djlx";
    /**
     * 登记类型字典码
     */
    public static final String AJZT_ZD = "ajzt";

    /**
     * 不动产权证号
     */
    public static final String BDCQZH = "bdcqzh";
    /**
     * 登簿验证组合标识
     */
    public static final String processKey_DBYZ = "processKey_DBYZ";

    /**
     * 流水号生成自增时间范围：年
     */
    public static final String ZZSJFW_YEAR = "YEAR";

    /**
     * 流水号生成自增时间范围：月
     */
    public static final String ZZSJFW_MONTH = "MONTH";

    /**
     * 流水号生成自增时间范围：日
     */
    public static final String ZZSJFW_DAY = "DAY";
    /**
     * qlxx数据来源  权籍
     */
    public static final String QLSJLY_LPB = "1";
    /**
     * qlxx数据来源  原项目
     */
    public static final String QLSJLY_YXM = "2";

    /**
     * qlxx数据来源 原抵押项目
     */
    public static final String QLSJLY_ZH_YDY = "4";

    /**
     * qlxx数据来源  原项目中同权利
     */
    public static final String QLSJLY_YTQL = "6";

    /**
     * qlr数据来源  权籍
     */
    public static final Integer QLRSJLY_LPB = 1;

    /**
     * PDF、WORD模板子表ID前缀
     */
    public static final String PDF_WORD_SUBTABLE_PRE = "TABLE_";

    /**
     * PDF、WORD模板图片高度
     */
    public static final String PDF_WORD_EWMNR_HEIGHT = "height";

    /**
     * PDF、WORD模板图片高度
     */
    public static final String PDF_WORD_EWMNR_WIDTH = "width";

    /*
     * PDF、WORD模板单元格内嵌表ID前缀
     */
    public static final String PDF_WORD_INCELL_TABLE_PRE1 = "TABLE_NQ_";
    public static final String PDF_WORD_INCELL_TABLE_PRE2 = "TABLE_nq_";

    /**
     * PDF、WORD模板整体模式子表ID前缀
     */
    public static final String PDF_WORD_SUBTABLE_ZT1_PRE = "TABLE_ZT_";
    public static final String PDF_WORD_SUBTABLE_ZT2_PRE = "TABLE_zt_";

    /**
     * PDF、WORD导出checkbox字段默认前缀
     */
    public static final String PDF_WORD_CHECKBOX_UPPER_PRE = "CHECKBOX#";
    public static final String PDF_WORD_CHECKBOX_LOWER_PRE = "checkbox#";

    /**
     * PDF、WORD导出复选框图片名称
     */
    public static final String PDF_WORD_CHECKBOX_PICTURE_Y = "CheckboxY.png";
    public static final String PDF_WORD_CHECKBOX_PICTURE_N = "CheckboxN.png";

    /**
     * WORD转PDF快速方案标识
     */
    public static final String PDF_FWTP = "fwtp";

    /**
     * 自助打证 缮证节点名称
     */
    public static final String ZZDZ_SZ = "缮证";

    /**
     * 查询缴费缴税信息查询标志 查询收费
     */
    public static final String SFSSXX_CXBZ_SF = "1";
    /**
     * 查询缴费缴税信息查询标志 查询收税
     */
    public static final String SFSSXX_CXBZ_SS = "2";

    /**
     * 查询缴费缴税信息查询标志 查询收费和收税
     */
    public static final String SFSSXX_CXBZ_SF_SS = "3";

    /*
     * html版本
     * */

    public static final String SYSTEM_VERSION_NT = "nantong";

    /*
     * 版本:合肥
     * */

    public static final String SYSTEM_VERSION_HF = "hefei";

    /*
     * 版本:宣城
     * */

    public static final String SYSTEM_VERSION_XC = "xuancheng";

    /*
     * 版本:标准版
     * */

    public static final String SYSTEM_VERSION_STD = "standard";

    /*
     * 版本:海门
     * */

    public static final String SYSTEM_VERSION_HM = "haimen";

    /*
     * 版本:bengbu
     * */

    public static final String SYSTEM_VERSION_BB = "bengbu";
    /*
     * 版本:常州
     * */

    public static final String SYSTEM_VERSION_CZ = "changzhou";

    /*
     * 版本:盐城
     * */
    public static final String SYSTEM_VERSION_YC = "yancheng";

    /*
    启东版本
    * */

    public static final String SYSTEM_VERSION_QD = "qidong";

    /*
     * 版本:如东
     * */

    public static final String SYSTEM_VERSION_RD = "rudong";

    /*
     * 版本:如东
     * */

    public static final String SYSTEM_VERSION_RG = "rugao";

    /*
     * 版本:舒城
     * */

    public static final String SYSTEM_VERSION_SC = "shucheng";


    /**
     * 淮安版本
     */
    public static final String SYSTEM_VERSION_HA = "huaian";

    /**
     * 连云港
     */
    public static final String SYSTEM_VERSION_LYG = "lianyungang";


    /*
     * 版本:南通大市版本
     * */
    public static final String[] SYSTEM_VERSION_NTDS = {SYSTEM_VERSION_HM, SYSTEM_VERSION_NT, SYSTEM_VERSION_QD};

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 安徽版本
     */
    public static final String[] SYSTEM_VERSION_AH = {SYSTEM_VERSION_BB, SYSTEM_VERSION_HF};

    /**
     * 房屋套次-第零套
     */
    public static final String FWTC_ZERO = "0";

    /**
     * 房屋套次-第一套
     */
    public static final String FWTC_ONE = "1";

    /**
     * 是否自动办结 是
     */
    public static final String SF_ZDBJ = "1";

    /**
     * 房屋套次-其他套
     */
    public static final String FWTC_QT = "9";

    /**
     * 缴款方式-线上缴费
     */
    public static final String JKFS_XSJF = "2";

    /**
     * 收费信息开票方式：电子发票
     */
    public static final String KPFS_DZFP = "2";


    /**
     * 特殊业务配置-配置类型(普通字符串)
     */
    public static final Integer TSYWPZ_PZLX_ZFC = 1;

    /**
     * 特殊业务配置-配置类型(流程多选)
     */
    public static final Integer TSYWPZ_PZLX_LC = 2;


    /**
     * 特殊业务配置-配置类型(数据库字典项多选)
     */
    public static final Integer TSYWPZ_PZLX_SJZDDX = 3;

    /**
     * 特殊业务配置-配置类型(数据库字典项单选)
     */
    public static final Integer TSYWPZ_PZLX_SJZD = 4;

    /**
     * 特殊业务配置-配置类型(自定义字典项多选)
     */
    public static final Integer TSYWPZ_PZLX_ZDYZDDX = 5;

    /**
     * 特殊业务配置-配置类型(自定义字典项单选)
     */
    public static final Integer TSYWPZ_PZLX_ZDYZD = 6;

    /**
     * 特殊业务配置-配置类型(数组字符串)
     */
    public static final Integer TSYWPZ_PZLX_ARR = 8;


    /**
     * 节点自定义字典
     */
    public static final Map<String, String> JDZDMAP = new HashMap<>();

    /**
     * 德宽系统标识
     */
    public static final String SW_DK = "sw_dk";
    public static final String BDC_ZD_FILE = "BDC_ZD_FILE";

    // 静态代码块
    static {
        initAllConstantVariable();
    }

    private static void initAllConstantVariable() {
        JDZDMAP.put("sl", "受理");
        JDZDMAP.put("dbzz", "登簿制证");
        JDZDMAP.put("db", "登簿");
        JDZDMAP.put("sh", "审核");
        JDZDMAP.put("sz", "缮证");
        JDZDMAP.put("cs", "初审");
        JDZDMAP.put("hd", "核定");
        JDZDMAP.put("fs", "复审");
        JDZDMAP.put("fz", "发证");
        JDZDMAP.put("fh", "复核");
    }

    /**************************************电子证照相关常量****************************************/
    public static final String STATUS_SUCCESS = "0";
    public static final String GYFS = "gyfs";
    public static final String QLLX = "qllx";
    public static final String ZSID = "zsid";

    /**
     * 不动产数据(非存量过渡数据)产权证号标识
     */
    public static final String BDC_BDCQZH_BS = "不动产";

    /**
     * 是否注销原权利 1 注销
     */
    public static final Integer ZXYQL_ZX = 1;

    /**
     * 税务更新类型-先删除后插入（重新生成）
     */
    public static final String SW_GXLX_CXSC = "1";


    /**
     * 税务更新类型-根据纳税人识别号更新税务信息
     */
    public static final String SW_GXLX_UPDATE = "2";

    /**
     * 税务更新类型-不更新
     */
    public static final String SW_GXLX_BGX = "0";


    /**
     * 税务更新类型-有值更新无值插入
     */
    public static final String SW_GXLX_INSERT_UPDATE = "3";


    /**
     * 土地的BDCLX
     */
    public static final String BDCLX_TD_DM = "1";

    /**
     * 信息补录参数中文
     */
    public static final String XXBL_PARAMCH = "paramCha";
    /**
     * 信息补录日志参数：记录操作后的信息
     */
    public static final String XXBL_AFTER = "after";
    /**
     * 信息补录日志参数：记录操作前的信息, 用于日志回溯
     */
    public static final String XXBL_BEFORE = "before";
    /**
     * 信息补录日志参数：记录操作的改变
     */
    public static final String XXBL_CHANGE = "change";

    /**
     * 信息补录日志参数：记录操作信息
     */
    public static final String XXBL = "XXBL";

    /**
     * 婚姻状况：已婚
     */
    public static final String HYZK_YH_MC = "已婚";

    /**
     * 与申请人关系：配偶
     */
    public static final String YSQRGX_PO_MC = "配偶";

    /**
     * 与申请人关系：未成年子女
     */
    public static final String YSQRGX_WCNZN_MC = "未成年子女";

    /**
     * 与申请人关系：本人
     */
    public static final String YSQRGX_BR_MC = "本人";

    /**
     * 婚姻登记业务类型：结婚
     */
    public static final String HYDJYWLX_JH = "结婚";


    /**
     * 受理信息删除：购物车删除
     */
    public static final String SL_DELETE_GWC = "1";


    /**
     * 受理信息删除：全部受理信息删除
     */
    public static final String SL_DELETE_ALL = "2";

    // 受理基本信息类型：一窗受理
    public static final String JBXX_TYPE_YCSL = "1";

    // 受理基本信息类型：登记
    public static final String JBXX_TYPE_DJ = "2";

    // 税费关联ID类型: 收费信息ID
    public static final Integer SFGLID_LX_SFXXID = 1;
    // 税费关联ID类型: 收费项目ID
    public static final Integer SFGLID_LX_SFXMID = 2;
    // 税费关联ID类型: 核税信息ID
    public static final Integer SFGLID_LX_HSXXID = 3;
    // 税费关联ID类型: 核税明细ID
    public static final Integer SFGLID_LX_HSMXID = 4;
    // 税费关联ID类型
    public static final Integer SFGLID_LX_SFSSXXZJ = 5;
    // 税费关联ID类型-其他
    public static final Integer SFGLID_LX_QT = 9;
    // 缴费方式：1 POS机
    public static final String JFFS_POS = "4";
    // 缴费方式：2 其他
    public static final String JFFS_OTHER = "2";
    // 缴款渠道： 0 线上
    public static final String JKQD_XS = "0";
    // 缴款渠道： 1 线下
    public static final String JKQD_XX = "1";

    // 房产交易类型：存量房
    public static final String FCJY_TYPE_CLF = "clf";

    // 房产交易类型：商品房
    public static final String FCJY_TYPE_SPF = "spf";

    //南通版本标识
    public static final String NANTONG = "nantong";

    //招商银行标志
    public static final String ZSYH = "ZSYH";

    //注销电子证照服务地市
    public static final String DZZZ_HEFEI = "hefei";

    //注销电子证照服务地市
    public static final String DZZZ_BENGBU = "bengbu";

    //上传附件材料名称-交易合同
    public static final String CLMC_JYHT = "交易合同";

    //上传附件材料名称-确认书
    public static final String CLMC_QRS = "确认书";

    //上传附件材料名称-申请书
    public static final String CLMC_SQS = "申请书";

    //上传附件材料名称-收件单
    public static final String CLMC_SJD = "收件单";

    //    存量房买卖合同打印类型
    public static final String DYLX_CLFMMHT = "clfmmht";

    //存量房买卖合同打印类型
    public static final String DYLX_YTHCLFHT = "ythclfht";

    //查询备案合同界面，备案合同查询类型
    public static final String BAHTCXLX_CLF = "clf";
    public static final String BAHTCXLX_SPF = "spf";
    //通过接口获取打印数据打印类型
    public static final String[] DYLX_JK = {"ythclfht"};

    //    确认书打印类型
    public static final String DYLX_QRS = "qrs";

    // 存量房买卖合同名称
    public static final String WJMC_CLFMMHT = "存量房买卖合同";

    //文件后缀-pdf
    public static final String WJHZ_PDF = ".pdf";

    //文件后缀-jpg
    public static final String WJHZ_JPG = ".jpg";

    //文件后缀-fr3
    public static final String WJHZ_FR3 = ".fr3";

    //文件后缀-docx
    public static final String WJHZ_DOCX = ".docx";

    //文件后缀-png
    public static final String WJHZ_PNG = ".png";

    //打印类型-收件单
    public static final String DYLX_SJD = "sjd";

    //打印类型-批量收件单
    public static final String DYLX_SJDPL = "sjdpl";

    //打印类型-出证收件单
    public static final String DYLX_CZSJD = "czsjd";

    //打印类型-出证收件单
    public static final String DYLX_NOCZSJD = "noczsjd";

    //打印类型-产权收件单
    public static final String DYLX_CQSJD = "cqsjd";

    //打印类型-不需要打印收件单
    public static final String DYLX_NOSJD = "nosjd";

    //打印类型-一窗收件单
    public static final String DYLX_YCSJD = "ycsjd";

    //打印类型-一窗批量收件单
    public static final String DYLX_YCSJDPL = "ycsjdpl";

    //打印类型-一窗出证收件单
    public static final String DYLX_YCCZSJD = "ycczsjd";

    //打印类型-一窗出证收件单
    public static final String DYLX_YCNOCZSJD = "ycnoczsjd";

    //打印类型-一窗产权收件单
    public static final String DYLX_YCCQSJD = "yccqsjd";

    //打印类型-一窗不需要打印收件单
    public static final String DYLX_YCNOSJD = "ycnosjd";

    //打印类型-一窗出证收件单批量
    public static final String DYLX_YCCZSJDPL = "ycczsjdpl";

    //打印类型-一窗出证收件单批量
    public static final String DYLX_CZSJDPL = "czsjdpl";

    //打印类型-抵押收件单
    public static final String DYLX_DYASJD = "dyasjd";

    //打印类型-抵押收件单批量
    public static final String DYLX_DYASJDPL = "dyasjdpl";

    //打印类型-其他收件单
    public static final String DYLX_QTSJD = "qtsjd";

    //打印类型-组合收件单
    public static final String DYLX_ZHSJD = "zhsjd";

    //打印类型-组合收件单批量
    public static final String DYLX_ZHSJDPL = "zhsjdpl";
    //打印类型-申请书
    public static final String DYLX_SQS = "sqs";

    //打印类型-申请书批量
    public static final String DYLX_SQSPL = "sqspl";

    //打印类型-收费单
    public static final String DYLX_SFD = "sfd";

    //打印类型-收费单批量
    public static final String DYLX_SFDPL = "sfdpl";

    //打印类型-抵押收费单
    public static final String DYLX_DYASFD = "dysfd";

    //打印类型-抵押收费单批量
    public static final String DYLX_DYASFDPL = "dysfdpl";

    //打印类型-决定书
    public static final String DYLX_JDS = "jds";

    //打印类型-轮候查封决定书
    public static final String DYLX_LHCFJDS = "lhcfjds";

    //打印类型-查封决定书
    public static final String DYLX_CFJDS = "cfjds";

    //打印类型-预查封决定书
    public static final String DYLX_YCFJDS = "ycfjds";

    //打印类型-续封决定书
    public static final String DYLX_XFJDS = "xfjds";

    //打印类型-裁定过户决定书
    public static final String DYLX_CDGHJDS = "cdghjds";

    //打印类型-解封决定书
    public static final String DYLX_JFJDS = "jfjds";

    //打印类型-权籍调查收件单
    public static final String DYLX_QJDCSJD = "qjdcsjd";

    //打印类型-海门收费联系单
    public static final String DYLX_HMSFLXD = "hmsflxd";

    //打印类型-海门收费联系单批量
    public static final String DYLX_HMSFLXDPL = "hmsflxdpl";

    //打印类型-缴纳凭证
    public static final String DYLX_JNPZ = "jnpz";

    //打印类型-批量后缀
    public static final String DYLX_HZPL = "pl";

    //布尔false字符串
    public static final String BOOL_FALSE = "false";

    //布尔true字符串
    public static final String BOOL_TRUE = "true";

    //无项目id字符串
    public static final String NOXMID = "noxmid";

    //打印模板路径
    public static final String ModelPath = "/static/printmodel/";

    //base64 前缀-pdf
    public static final String BASE64_QZ_PDF = "data:application/pdf;base64,";

    //base64 前缀-jpg
    public static final String BASE64_QZ_IMAGE = "data:image/jpg;base64,";
    /**
     * 规则例外审核状态-新建
     */
    public static final Integer GZLW_SHZT_XJ = 0;

    /**
     * 规则例外审核状态-通过
     */
    public static final Integer GZLW_SHZT_TG = 1;

    /**
     * 规则例外审核状态-不通过
     */
    public static final Integer GZLW_SHZT_BTG = 2;

    // 评价器签字图片文件名称
    public static final String WJMC_QZXX = "签名图片";

    // 抵押审批表打印类型
    public static final String DYLX_DYASPB = "dyaspb";

    // 抵押审批表文件名称
    public static final String WJMC_DYASPB = "抵押审批表.pdf";

    // 合肥税务系统版本--德宽版
    public static final String SYSTEM_SW_DK = "dk";

    /**
     * 海门合并不动产单元生成新的项目副表中权利拆分标识字段保存的标识值
     */
    public static final String BDCDY_HBJL = "BDCDYHBJL";
    /**
     * 有房无房证明
     */
    public static final String YFWFZM = "yfwfzm";
    /**
     * 迁户口证明
     */
    public static final String QHKZM = "qhkzm";

    /**
     * 更新方法
     */
    public static final String FUN_UPDATE = "update";

    /**
     * 新增方法
     */
    public static final String FUN_INSERT = "insert";

    /**
     * 删除方法
     */
    public static final String FUN_DELETE = "DELETE";

    /**
     * 成功码
     */
    public static final String SUCCESS_CODE = "00000000";

    /**
     * 签字人类别：权利人
     */
    public static final Integer QZRLB_QLR = 1;

    /**
     * 签字人类别：义务人
     */
    public static final Integer QZRLB_YWR = 3;
    /**
     * 限制权利模型初始化的主键
     */
    public static final String XZQL_MODEL_KEY_CF = "BdcCfDO";
    public static final String XZQL_MODEL_KEY_DY = "BdcDyaqDO";
    public static final String XZQL_MODEL_KEY_YG = "BdcYgDO";
    public static final String XZQL_MODEL_KEY_YY = "BdcYyDO";
    public static final String XZQL_MODEL_KEY_DYI = "BdcDyiqDO";
    public static final String XZQL_MODEL_KEY_JZQ = "BdcJzqDO";

    /**
     * 虚拟印制号流水号编码
     */
    public static final String BDC_XNYZH = "BDC_XNYZH_";
    /**
     * 安徽
     */
    public static final String XZQH_AH = "34";
    /**
     * 合肥
     */
    public static final String XZQH_HF = "HF";

    //    查档信息打印类型
    public static final String DYLX_CDXX = "cdxx";
    //    查档打印类型
    public static final String DYLX_CDXX_BDCQZ = "bdcdjbcxbdcqz";
    //查档类型-房产证土地证
    public static final String DYLX_CDXX_FCTDZ = "bdcdjbcxfcztdz";
    //    查档打印类型
    public static final String DYLX_QSZM_BDCQZ = "qszmbdcqz";
    //查档类型-房产证土地证
    public static final String DYLX_QSZM_FCTDZ = "qszmfcztdz";

    //    首套房查询打印
    public static final String DYLX_STFCX = "bdcdjbcxzmd";
    // 首套房证明
    public static final String DYLX_STFZM = "stfzm";
    //    首套房证明无数据
    public static final String DYLX_STFZM_0 = "stfzm_0";
    //三调流程打印类型
    public static final String DYLX_SDDY = "sdcx";

    public static final String[] BDCDYCX_QLLX = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "31", "11", "12", "13", "14", "15", "16", "17"
            , "18", "19", "20", "21", "22", "23", "24", "25", "26",  "93", "94"};

    // 规则操作方法名
    public static final String METHOD_NAME_ZGZ_SAVE = "saveBdcGzZgz";
    public static final String METHOD_NAME_ZGZ_DELETE = "deleteBdcGzZgz";
    public static final String METHOD_NAME_ZHGZ_SAVE = "insertBdcGzZhGz";
    public static final String METHOD_NAME_ZHGZ_UPDATE = "updateBdcGzZhGz";
    public static final String METHOD_NAME_ZHGZ_DELETE = "delBdcGzZhGz";

    // 短信注解需要再bofore中处理的方法，可逗号分隔
    public static final String SENDMSG_AOP_DOBEFORE_NAME = "delBdcGzZhGz";


    //公安验证接口返回校验code
    public static final String GAYZ_RESULT_CODE = "000";

    //社会组织查询type
    public static final String SHZZYZ_TYPE = "10";

    //企业验证返回校验code
    public static final String QYYZ_RESULT_CODE = "03";

    //抵押方式 最高额抵押
    public static final Integer DYFS_ZGEDY = 2;

    //抵押方式 一般抵押
    public static final Integer DYFS_YBDY = 1;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 任务退回状态-被退回
     */
    public static final Integer TASK_THZT_BTH = 2;

    /*
    是否备案
    * */
    public static final String BAZT_S = "1";


    /*
    是否备案
    * */
    public static final String BAZT_F = "0";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 百分数格式
     */
    public static final String FORMAT_BFS = "0.0000%";

    /**
     * 正反标识
     */
    public static final String YES = "Y";
    public static final String NO = "N";

    /**
     * 文件类型：目录
     */
    public static final Integer FILETYPE_ML = 0;

    /**
     * 文件类型：文档
     */
    public static final Integer FILETYPE_WD = 3;
    /**
     * 文件类型：图片
     */
    public static final Integer FILETYPE_TP = 2;

    /**
     * 文件类型：其他
     */
    public static final Integer FILETYPE_QT = 6;

    /**
     * 第三权利人类别-预购人
     */
    public static final String DSQLR_QLRLB_YGR = "1";
    /**
     * 第三权利人类别：买受人
     */
    public static final String DSQLR_QLRLB_MSR = "2";
    /**
     * 第三权利人类别：出卖人
     */
    public static final String DSQLR_QLRLB_CMR = "3";

    /**
     * 第三权利人类别：债务人
     */
    public static final String DSQLR_QLRLB_ZWR = "4";

    /**
     * 文件名：文件
     */
    public static final String WJM_WJ = "doc,docx,xlsx,xls,txt,zip,rar";
    /**
     * 文件名：文件
     */
    public static final String WJM_TP = "bmp,jpg,png,tif,gif,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,WMF,webp,avif";

    /**
     * 盐城综合查询打印清单序号标识前缀
     */
    public static final String YC_ZHCX_DYQD_SJ = "YC_ZHCX_DYQD_";

    /**
     * 盐城综合查询打印有无土地信息标识前缀
     */
    public static final String YC_ZHCX_YWTDXXZM = "YC_ZHCX_YWTDXXZM_";

    /*
     * 楼盘表展示资源code
     * */
    public static final String LPB_RES_CODE = "bdcres";

    public static final String LPB_HTBA_CODE = "htba";

    public static final String LPB_ACCEPT_CODE = "accept";

    public static final String LPB_ANALYSIS_CODE = "analysis";

    /*
     * 页面类型后缀
     * */
    public static final String FILE_FTL = ".ftl";

    /*预售商品房抵押权预告登记*/
    public static final Integer YGDJZL_YSSPF = 3;
    /*其它不动产买卖预告登记*/
    public static final Integer YGDJZL_QTBDCMM = 2;

    /**
     * 收件材料收取部门: 登记
     */
    public static final String SJCL_SQBM_DJ = "1";
    /**
     * 收件材料收取部门: 税务
     */
    public static final String SJCL_SQBM_SW_MC = "税务";

    /**
     * 收件材料收取部门: 登记
     */
    public static final String SJCL_SQBM_DJ_MC = "登记";

    /**
     * 自定义查询外部传参采用临时参数表，参数名称前缀
     */
    public static final String ZDYCX_LSCS = "ZDYCX_LSCS_";

    /**
     * 共享接口数据：保存Redis key前缀
     */
    public static final String GXJK_DATA_ = "GXJK_DATA_";

    /**
     * 自动转发办理人： key前缀
     */
    public static final String ZDZF_SLR = "ZDZF_SLR_";

    /**
     * 一张网推送默认电话值
     */
    public static final String YZW_DHMRZ = "00000";

    /**
     * 缴费汇总费用代码
     */
    public static final String JFHZFYDM = "A,B";
    public static final String JFHZFYDM_A = "A";
    public static final String JFHZFYDM_B = "B";

    /**
     * 缴费汇总费用名称
     */
    public static final String JFHZFYMC = "不动产登记费,土地使用权交易服务费";
    public static final String JFHZFYMC_A = "不动产登记费";
    public static final String JFHZFYMC_B = "土地使用权交易服务费";

    /**
     * 常州易家服务水电气过户单位
     */
    public static final String WATER_ORG = "常州通用自来水有限公司";
    public static final String ELETRIC_ORG = "国网江苏省电力有限公司常州供电分公司";
    public static final String GAS_ORG = "常州港华燃气有限公司";
    public static final String NETWORK_ORG = "江苏省广电有线网络股份有限公司常州分公司";
    public static final String TV_ORG = "江苏省广电有线网络股份有限公司常州分公司";

    /**
     * 操作类型：外网退件
     */
    public static final Integer CZRZ_CZLX_WWTJ = 1;

    /**
     * 质检状态：已质检、质检通过
     */
    public static final Integer ZJZT_YZJ = 1;
    /**
     * 质检状态：未质检
     */
    public static final Integer ZJZT_WZJ = 0;
    /**
     * 质检状态：质检不通过
     */
    public static final Integer ZJZT_BTG = -1;

    /**
     * 机构类别 企业
     */
    public static final Integer JGLB_QY = 4;

    /**
     * 机构类别 事业单位
     */
    public static final Integer JGLB_SYDW = 5;

    /*
     * 户室类型-实测
     * */
    public static final String HSLX_SC = "sc";

    /*
     * 户室类型-预测
     * */
    public static final String HSLX_YC = "yc";

    /**
     * 查询条件-是否查询已办结
     */
    public static final String QUERYBJ = "1";

    /*
     * 规则验证-组合标识-撤销备案
     * */
    public static final String GZYZ_CXBA = "CXBA";

    /*
     * 字典项来源-受理
     * */
    public static final String ZDLY_SL = "2";

    /*
     * 字典项来源-登记
     * */
    public static final String ZDLY_DJ = "1";

    /*
     * 字典项来源-大云
     * */
    public static final String ZDLY_DY = "3";

    /*
     * 字典项-流程
     * */
    public static final String ZDNAME_LC = "lcmc";

    /*
     * 修正信息来源-选择台账
     * */
    public static final Integer XZLY_XZTZ = 1;

    /*
     * 修正信息来源-无数据创建
     * */
    public static final Integer XZLY_WSJ = 3;

    /*
     * 修正信息来源-流程内创建
     * */
    public static final Integer XZLY_LCNCJ = 2;

    /*
     * 修正信息来源-选择台账创建，且当前数据存在正在办理的登记业务
     * */
    public static final Integer XZLY_XZTZ_ZZBL = 4;


    /*
     * 是否中文-是
     * */
    public static final String SF_ZW_S = "是";

    /*
     * 材料清单文件夹名称
     * */
    public static final String SJCLMC_CLQD = "材料清单";

    public static final String SUCCESS_CODE_0000 = "0000";

    public static final String SUCCESS_STATUS_COMPLETE = "COMPLETE";
    public static final String SUCCESS_STATUS_FAIL = "FAIL";
    public static final String PASS = "1";
    public static final String NOTPASS = "2";

    // 权利人来源- 手动输入
    public static final Integer QLRLY_SD = 1;

    // 权利人来源- 住建
    public static final Integer QLRLY_JY = 6;

    /**
     * 收件材料类型：其他    `
     */
    public static final int SJLX_QT = 99;

    /**
     * 收费项目计算方法-取住宅面积和
     */
    public static final String SFXMJSFF_ZZ = "1";

    /**
     * 收费项目计算方法-取非住宅面积和
     */
    public static final String SFXMJSFF_FZZ = "2";

    /**
     * 收费项目计算方法-总土地权利面积
     */
    public static final String SFXMJSFF_ALL = "3";

    /**
     * 收费项目计算方法-总土地权利面积
     */
    public static final String SFXMJSFF_ALLGBFSL = "4";

    /**
     * 收费项目计算方法-总土地权利面积
     */
    public static final String SFXMJSFF_GBFSLJM = "5";

    /**
     * 权籍管理代码字段名称
     */
    public static final Object QJGLDM = "qjgldm";
    /**
     * 资金监管状态：已撤销
     */
    public static final Integer ZJJG_ZT_YCX = 1;
    /**
     * 资金监管状态：未撤销
     */
    public static final Integer ZJJG_ZT_WCX = 0;

    /**
     * FormKey 查询表单状态，过滤 Portal-ui 的链接地址
     */
    public static final String FORMKEY_FILTER_URL_PORTAL_UI = "/realestate-portal-ui";

    /**
     * FormKey 查询过滤操作 1： like
     */
    public static final int FORMKEY_FILTER_OPERATE_LIKE = 1;

    /**
     * FormKey 查询过滤操作 2： not like
     */
    public static final int FORMKEY_FILTER_OPERATE_NOTLIKE = 2;

    /**
     * PDF打印换行标志符号
     */
    public static final String PDF_HHF = "&HH&";

    /**
     * PDF打印换行word字段开头标识
     */
    public static final String PDF_HHBS = "HH_";

    /**
     * 特殊符号
     */
    public static final String TS_FH_1 = "&";

    /**
     * PDF打印内嵌表格字体
     */
    public static final String PDF_FONTS = "Fonts";

    /**
     * PDF打印内嵌表格字体大小
     */
    public static final String PDF_FONTSIZE = "FontSize";

    /**
     * PDF打印内嵌表格行高
     */
    public static final String PDF_ROWHEIGHT = "RowHeight";


    public static final String QI = "起";


    public static final String ZHI = "止";

    /**
     * PDF打印内嵌表格列宽设置标识
     */
    public static final String PDF_COLUMN_WIDTH = "Width";

    /**
     * PDF打印内嵌表格边框宽度设置标识
     */
    public static final String PDF_COLUMN_BORDER = "Border";

    /**
     * PDF打印内嵌表格空行数据标识
     */
    public static final String PDF_NULL_ROW_DATA = "&NR";

    /**
     * 数据源水印参数
     */
    public static final String WATERMARK = "watermark";

    /**
     * 数据源图片水印参数
     */
    public static final String PICWATERMARK = "picWatermark";

    /**
     * 数据源水印字体大小
     */
    public static final String WATERMARK_FONTSIZE = "FontSize";

    /**
     * 数据源水印字体大小
     */
    public static final String WATERMARK_FACTOR = "Factor";

    /**
     * 签章图片路径
     */
    public static final String QZPICPATH = "qzPicPath";

    /**
     * 用于定位签章图片的关键字
     */
    public static final String QZKEYWORDS = "qzKeyWords";

    /**
     * 用于定位签章图片的大小
     */
    public static final String QZPICSIZE = "qzPicSize";

    /**
     * 设置非最后一页签章图片的位置
     */
    public static final String QZPICPOSITION = "qzPicPosition";

    /**
     * 常州证明附表新增的配置
     */
    public static final String QZDYLX = "qzDylx";

    /**
     * 常州证明附表新增的配置
     */
    public static final String QZDYLXZMFB = "zmfb";

    /*
     * 常州推送缴费推送来源 1 表示收费信息页面推送
     * */
    public static final String SF_TSLY_SFYM = "1";

    /*
     * 常州推送缴费推送来源 2 表示流程创建收费台账推送
     * */
    public static final String SF_TSLY_SFTZ = "2";

    /**
     * 黑名单主体：人
     */
    public static final int HMD_ZT_PERSON = 1;
    /**
     * 黑名单主体：物
     */
    public static final int HMD_ZT_BDCDY = 2;
    /**
     * 黑名单主体：证书
     */
    public static final int HMD_ZT_ZS = 3;
    /**
     * 现势
     */
    public static final Integer HMD_STATUS_VALID = 1;
    /**
     * 历史
     */
    public static final Integer HMD_STATUS_HISTORY = 0;

    /**
     * 不动产类型  1：土地
     */
    public static final String BDCLX_TD = "1";

    /**
     * 业务类型：受理编号
     */
    public static final String YWLX_SLBH = "slbh";

    /**
     * 业务类型：缴款码
     */
    public static final String YWLX_JFSBM = "jfsbm";

    /**
     * 批量缴费推送类型： 产权
     */
    public static final String PLSF_TSLX_CQ = "产权";

    /**
     * 批量缴费推送类型： 抵押
     */
    public static final String PLSF_TSLX_DYAQ = "抵押";

    /**
     * 批量缴费流程： 小微企业
     */
    public static final String PLSF_TSLX_XWQY = "小微企业";

    /**
     * 登记历史页面常州版本
     */
    public static final String DJLSVERSION_CHANGZHOU = "changzhou";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    public static final Integer[] DDJB1_3 = {1, 2, 3};

    public static final Integer[] DDJB4_6 = {4, 5, 6};

    //档案目录打印材料名称-其他证明材料

    public static final String DAML_SJCL_QTZMCL = "其他证明材料";


    //档案目录打印材料名称-登记审核表

    public static final String DAML_SJCL_DJSHB = "登记审核表";

    /**
     * 文件中心自定义js保存区域
     */
    public static final String SPACEID_ZDYJS_MODEL = "zdyjsModel";

    /**
     * 抵押注销权利证明类型——纸质证明
     */
    public static final String DYZX_ZMLX_ZZZM = "zzzm";
    /**
     * 抵押注销权利证明类型——电子证照
     */
    public static final String DYZX_ZMLX_DZZZ = "dzzz";

    /**
     * 溧阳市单位代码——电子证照
     */
    public static final String QXDM_LY_DZZZ = "320481";

    /**
     * 问题数据状态——存在问题
     */
    public static final Integer WTSJZT_CZWT = 1;

    /**
     * 问题数据状态——已解决
     */
    public static final Integer WTSJZT_YJJ = 2;

    /**
     * Excel导出的实现类名称
     */
    public static final String EXCELEXPANDSERVICE_YC = "ExcelExpandYcService";

    /**
     * 顺序号上调
     */
    public static final String SXH_UP = "up";

    /**
     * 顺序号下调
     */
    public static final String SXH_DOWN = "down";

    /**
     * 评价器版本--摩科老版本
     */
    public static final String PJQBB_MK_L = "MK_L";

    /**
     * 评价器版本--摩科新版本
     */
    public static final String PJQBB_MK_N = "MK_N";

    /**
     * 评价器版本--青大维森
     */
    public static final String PJQBB_QDWS = "QDWS";

    /**
     * 南通：供水供气户号验证成功返回码
     */
    public static final String NANTONG_SQ_CHECK_RETURN_CODE_SUCCESS = "1";
    /**
     * 南通：供水户号验证成功返回码
     */
    public static final String NANTONG_S_CHECK_RETURN_CODE_SUCCESS = "0000";
    /**
     * 南通：供水供气户号验证失败返回码
     */
    public static final String NANTONG_SQ_CHECK_RETURN_CODE_FAIL = "0";

    /**
     * 南通：非税配置src发起节点代码
     */
    public static final String FS_SRC = "fs.src";
    /**
     * 南通：非税配置des接收节点代码
     */
    public static final String FS_DES = "fs.des";
    /**
     * 南通：非税配置app
     */
    public static final String FS_APP = "fs.app";
    /**
     * 南通：非税配置ver版本号
     */
    public static final String FS_VER = "fs.ver";
    /**
     * 南通：非税配置token
     */
    public static final String FS_TOKEN = "fs.token";
    /**
     * 南通：非税配置行政区县代码
     */
    public static final String FS_XZQDM = "fs.xzqdm";

    /**
     * 舒城：供水验证成功返回码
     */
    public static final String SHUCHENG_RETURN_CODE_SUCCESS = "0000";

    /*
     * 接口状态success标识

     * */
    public static final String JK_RESPONSE_SUCCESS = "SUCCESS";
    /**
     * 上报字段构建方式： 默认
     */
    public static final Integer ACCESS_DATA_PART_FIELD_DEFAULT = 1;
    /**
     * 上报字段构建方式： 从BDC_JR_SJJL中获取
     */
    public static final Integer ACCESS_DATA_PART_FIELD_FROM_BDC_JR_SJJL = 2;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 模糊类型-精确
     */
    public static final String MHLX_JQ = "1";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 模糊类型-全模糊
     */
    public static final String MHLX_QMH = "4";

    /*
     * 销账报文类型-接入失败
     * */
    public static final String XZBWLX_JRSB = "1";

    /*
     * 销账报文类型-入库失败
     * */
    public static final String XZBWLX_RKSB = "2";

    /*
     * 销账报文类型-未上报
     * */
    public static final String XZBWLX_WSB = "3";

    /*
     * 销账报文类型-
     * */
    public static final String XZBWLX_DBBYZ = "4";
    /*
     * 销账状态-未销账
     * */
    public static final String XZZT_WXZ = "1";

    /*
     * 销账状态-正在销账
     * */
    public static final String XZZT_ZZXZ = "2";

    /*
     * 销账状态-销账成功
     * */
    public static final String XZZT_XZCG = "3";

    /*
     * 销账状态-销账失败
     * */
    public static final String XZZT_XZSB = "4";

    /**
     * 大屏统计住宅统计
     **/
    public static final String REDIS_INQUIRY_ZZTJ = "REDIS_INQUIRY_ZZTJ";


    /**
     * 个人对应的证件种类代码，1：身份证，2：港澳台身份证，3：护照，4：户口簿，5：军官证（士兵证）
     */
    public static final Integer[] ZJZL_GR_DM = {1, 2, 3, 4, 5};

    /**
     * 企业对应的证件种类代码，6：组织机构代码，7：营业执照，8：统一社会信息代码
     */
    public static final Integer[] ZJZL_QY_DM = {6, 7, 8};
    /**
     * 大屏统计今日登记类型统计
     **/
    public static final String REDIS_INQUIRY_JRDJLX = "REDIS_INQUIRY_JRDJLX";

    /**
     * 工作量统计，统计维度： 部门
     */
    public static final String GZLTJ_TJWD_BM = "wd-bm";
    /**
     * 工作量统计，统计维度： 人员
     */
    public static final String GZLTJ_TJWD_RY = "wd-ry";

    /**
     * 工作量统计，统计维度： 按项目
     */
    public static final String GZLTJ_TJLX_XM = "1";
    /**
     * 工作量统计，统计类型：按件数
     */
    public static final String GZLTJ_TJLZ_JS = "2";

    /**
     * 证书样式：纸质证
     */
    public static final Integer ZSYS_ZZZ = 1;
    /**
     * 证书样式：电子证
     */
    public static final Integer ZSYS_DZZ = 2;
    /**
     * 证书样式：既是纸质证又是电子证
     */
    public static final Integer ZSYS_ZZZDZZ = 3;

    /**
     * 权利人顺序号
     */
    public static final Integer QLR_SXH = 1;

    /**
     * 好差评系统字典项-申请人类型
     */
    public static final String HCP_ZD_SQRLX = "HCP_ZD_SQRLX";

    /**
     * 好差评系统字典项-申请人证件种类
     */
    public static final String HCP_ZD_SQRZJZL = "HCP_ZD_SQRZJZL";

    /**
     * 好差评系统字典项-办件状态dm
     */
    public static final String HCP_ZD_BJZTDM = "HCP_ZD_BJZTDM";

    /**
     * 好差评系统字典项-办件状态mc
     */
    public static final String HCP_ZD_BJZTMC = "HCP_ZD_BJZTMC";

    /**
     * 公民身份证号码
     */
    public static final String GMSFZHM = "公民身份号码";

    /**
     * 统一社会信用代码
     */
    public static final String TYSHXYDM = "统一社会信用代码";
    /**
     * 发票状态：正常
     */
    public static final Integer FPZT_ZC = 1;
    /**
     * 发票状态：冲红
     */
    public static final Integer FPZT_CH = 4;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  数据源 1:不动产登记 2:受理库 3:权籍库 4:登记2.0整合库 5:登记2.0备份库
     */
    public static final String SJY_BDCDJ = "1";

    public static final String SJY_BDCSL = "2";

    public static final String SJY_BDCQJ = "3";

    public static final String SJY_YDJZH = "4";

    public static final String SJY_YDJBF = "5";

    public static final String QSZT_XS = "现势";

    public static final String QSZT_HIS = "历史";

    public static final String QSZT_LS = "临时";

    public static final String QSZT_ZZ = "终止";

    /**
     * 电子证照状态：未制证
     */
    public static final Integer DZZZZT_WZZ = 0;

    /**
     * 电子证照状态：已制证
     */
    public static final Integer DZZZZT_YZZ = 1;

    /**
     * 电子证照状态：已注销
     */
    public static final Integer DZZZZT_YZX = 2;

    /**
     * 电子证照状态：制证失败
     */
    public static final Integer DZZZZT_ZZSB = 3;

    /**
     * 权利人类型集合： 企业、事业单位、国家
     */
    public final static int[] QLRLX_QYJGGJ = new int[]{CommonConstantUtils.QLRLX_QY, CommonConstantUtils.QLRLX_SYDW, CommonConstantUtils.QLRLX_GJJG};

    /**
     * 定时推送评价数据到省平台定时任务
     */
    public static final String REDISSON_LOCK_PJSJTS = "REDISSON_LOCK_PJSJTS";

    /**
     * 关系类别
     * 1-单元号落宗
     * 2-房地匹配
     * 3-复制土地项目匹配
     * 4-单元号更正(台账)
     * 5-单元号更正登记流程
     */
    public static final Integer GXLB_DYHLZ = 1;
    public static final Integer GXLB_FDPP = 2;
    public static final Integer GXLB_FZTDXMPP = 3;
    public static final Integer GXLB_DYHGZ = 4;
    public static final Integer GXLB_DYHGZ_LC = 5;

    /**
     * 宣城户籍人口查询接口请求参数 condition
     */
    public static final String HJRKCX_REQUEST_CONDITION = "<condition><item><GMSFHM></GMSFHM><XM></XM></item></condition>";

    /**
     * 宣城户籍查询接口请求参数 condition
     */
    public static final String HJCX_REQUEST_CONDITION = "<condition><item><ZJH></ZJH></item></condition>";

    /**
     * 宣城户籍人口查询接口请求参数 requiredItems
     */
    public static final String HJRKCX_REQUEST_REQUIREDITEMS = "<requiredItems><item><CSDGJDQ>出生地国籍地区</CSDGJDQ><CSDSSXQ>出生地省市县区</CSDSSXQ><CSRQ>出生日期</CSRQ><GMSFHM>公民身份号码</GMSFHM><MZ>民族</MZ><SWZXLB>死亡注销类别</SWZXLB><XB>性别</XB><XM>姓名</XM></item></requiredItems>";

    /**
     * 宣城户籍查询接口请求参数 requiredItems
     */
    public static final String HJCX_REQUEST_REQUIREDITEMS = "<requiredItems><item><CSRQ>出生日期</CSRQ><GJ>国籍</GJ><HJDZ>户籍地址</HJDZ><HSLBZ>何时来本址</HSLBZ><HXZLBZ>何详址来本址</HXZLBZ><HYZK>婚姻状况</HYZK><JHRQ>结婚日期</JHRQ><MZ>民族</MZ><NVXM>婚姻女方姓名</NVXM><NVZJH>婚姻女方证件号</NVZJH><NXM>婚姻男方姓名</NXM><NZJH>婚姻男方证件号</NZJH><QFJG>签发机构</QFJG><SG>身高</SG><SSXQ>省市县区</SSXQ><WHCD>文化程度</WHCD><XB>性别</XB><XM>姓名</XM><YHZGX>与户主关系</YHZGX><YXQXQSRQ>登记日期</YXQXQSRQ><ZJH>身份证</ZJH><ZJXY>宗教信仰</ZJXY></item></requiredItems>";

    /**
     * 宣城接口请求参数 clientInfo
     */
    public static final String REQUEST_CLIENTINFO = "<clientInfo><loginName>sadmin</loginName></clientInfo>";

    /**
     * 宣城企业信息查询接口请求参数 condition
     */
    public static final String QYXXCX_REQUEST_CONDITION = "<condition><item><QYMC></QYMC><SHTYXYDM></SHTYXYDM></item></condition>";

    /**
     * 宣城企业信息查询接口请求参数 requiredItems
     */
    public static final String QYXXCX_REQUEST_REQUIREDITEMS = "<requiredItems><item><CJRQ>创建日期</CJRQ><DJBZ>注册资本币种</DJBZ><DJJG>登记机构</DJJG><FDDBR>法定代表人</FDDBR><HZSJ>核准日期</HZSJ><JYDZ>经营地址</JYDZ><JYFW>经营范围</JYFW><JYQXQ>经营期限起</JYQXQ><JYQXZ>经营期限止</JYQXZ><QYLXZL>企业类型种类</QYLXZL><QYMC>企业名称</QYMC><QYZT>企业状态</QYZT><SHTYXYDM>统一社会信用代码</SHTYXYDM><ZCH>注册号</ZCH><ZCZB>注册资本</ZCZB></item></requiredItems>";

    /**
     * 宣城出生证明查询接口请求参数 condition
     */
    public static final String CSZMCX_REQUEST_CONDITION = "<condition><item><CSYXZMBH></CSYXZMBH><FQXM></FQXM><MQXM></MQXM></item></condition>";

    /**
     * 宣城出生证明查询接口请求参数 requiredItems
     */
    public static final String CSZMCX_REQUEST_REQUIREDITEMS = "<requiredItems><item><CSDQ>出生地区</CSDQ><CSSJ>出生时间</CSSJ><CSYXZMBH>出生医学证明编号</CSYXZMBH><FQXM>父亲姓名</FQXM><FQZJBH>父亲证件编号</FQZJBH><MQXM>母亲姓名</MQXM><MQZJBH>母亲证件编号</MQZJBH><XSRXB>新生儿性别</XSRXB><XSRXBDM>新生儿性别代码</XSRXBDM><XSRXM>新生儿姓名</XSRXM></item></requiredItems>";

    /**
     * 宣城单位法人查询接口请求参数 condition
     */
    public static final String DWFRCX_REQUEST_CONDITION = "<condition><item><MC></MC><TYSHXYDM></TYSHXYDM></item></condition>";

    /**
     * 宣城单位法人查询接口请求参数 requiredItems
     */
    public static final String DWFRCX_REQUEST_REQUIREDITEMS = "<requiredItems><item><DJGLJG>登记管理机构</DJGLJG><FDDBR>法定代表人</FDDBR><JBDW>举办单位名称</JBDW><JFLY>经费来源</JFLY><KBZJ>开办资金</KBZJ><MC>名称</MC><TYSHXYDM>统一社会信用代码</TYSHXYDM><ZS>住所</ZS><ZSYXJSRQ>证书有效截止日期</ZSYXJSRQ><ZSYXQSRQ>证书有效起始日期</ZSYXQSRQ><ZZHYWFW>宗旨和业务范围</ZZHYWFW></item></requiredItems>";

    /**
     * 宣城死亡证明查询接口请求参数 condition
     */
    public static final String SWZMCX_REQUEST_CONDITION = "<condition><item><XM></XM><ZJHM></ZJHM></item></condition>";

    /**
     * 宣城死亡证明查询查询接口请求参数 requiredItems
     */
    public static final String SWZMCX_REQUEST_REQUIREDITEMS = "<requiredItems><item><GMSFHM>公民身份号码</GMSFHM><MZ>民族</MZ><SWYY>死亡原因</SWYY><XB>性别</XB><XM>姓名</XM></item></requiredItems>";

    /**
     * 宣城婚姻登记查询接口请求参数 condition
     */
    public static final String HYDJCX_REQUEST_CONDITION = "<condition><item><GMSFHM></GMSFHM><XM></XM></item></condition>";

    /**
     * 宣城婚姻登记查询接口请求参数 requiredItems
     */
    public static final String HYDJCX_REQUEST_REQUIREDITEMS = "<requiredItems><item><DJJG>登记机构</DJJG><DJRQ>登记日期</DJRQ><DJZH>登记证号</DJZH><GMSFHM>公民身份号码</GMSFHM><HYDJYWLX>婚姻登记业务类型</HYDJYWLX><LFCSRQ>女方出生日期</LFCSRQ><LFGJ>女方国籍</LFGJ><LFGMSFHM>女方公民身份号码</LFGMSFHM><LFXM>女方姓名</LFXM><NFCSRQ>男方出生日期</NFCSRQ><NFGJ>男方国籍</NFGJ><NFGMSFHM>男方公民身份号码</NFGMSFHM><NFXM>男方姓名</NFXM><XM>姓名</XM></item></requiredItems>";

    /**
     * 宣城婚姻登记查询接口请求参数 condition
     */
    public static final String JTCYCX_REQUEST_CONDITION = "<condition><item><ZJH></ZJH></item></condition>";

    /**
     * 宣城婚姻登记查询接口请求参数 requiredItems
     */
    public static final String JTCYCX_REQUEST_REQUIREDITEMS = "<requiredItems><item><CSRQ>出生日期</CSRQ><GJ>国籍</GJ><HJDZ>户籍地址</HJDZ><HSLBZ>何时来本址</HSLBZ><HXZLBZ>何详址来本址</HXZLBZ><HYZK>婚姻状况</HYZK><JHRQ>结婚日期</JHRQ><MZ>民族</MZ><NVXM>婚姻女方姓名</NVXM><NVZJH>婚姻女方证件号</NVZJH><NXM>婚姻男方姓名</NXM><NZJH>婚姻男方证件号</NZJH><QFJG>签发机构</QFJG><SG>身高</SG><SSXQ>省市县区</SSXQ><WHCD>文化程度</WHCD><XB>性别</XB><XM>姓名</XM><YHZGX>与户主关系</YHZGX><YXQXQSRQ>登记日期</YXQXQSRQ><ZJH>身份证</ZJH><ZJXY>宗教信仰</ZJXY></item></requiredItems>";


    /**
     * 宣称林权统计表名称
     */
    public static final String XCLQTJ_SHEETNAME = "宣州区林权类不动产登记业务统计表";

    /**
     * 宣称林权统计
     */
    public static final String XCLQTJDJLXMC_CFDJ = "查封登记";

    /**
     * 宣称林权统计
     */
    public static final String XCLQTJDJLXMC_DYSCDJ = "抵押首次登记";

    /**
     * 宣称林权统计
     */
    public static final String XCLQTJDJLXMC_DYBGDJ = "抵押变更登记";

    /**
     * 宣称林权统计
     */
    public static final String XCLQTJDJLXDM_DY= "dy";

    /**
     * 宣称林权统计
     */
    public static final String XCLQTJDJLXDM_CFDJ = "cfdj";

    /**
     * 宣称林权统计
     */
    public static final String XCLQTJDJLXMC_JFDJ = "解封登记";

    /**
     * 宣称林权统计
     */
    public static final String XCLQTJDJLXMC_ZXDJ = "注销登记";

    /**
     * 宣称林权统计
     */
    public static final String XCLQTJDJLXDM_ZXDJ = "zxdj";

    /**
     * 宣称林权统计
     */
    public static final String XCLQTJDJLXMC_DYZXDJ = "抵押注销登记";

    /**
     * 宣称林权统计
     */
    public static final String XCLQTJDJLXDM_DYZXDJ = "dyzxdj";

    /**
     * 宣称林权统计
     */
    public static final String XCLQTJDJLXMC_DIYSCDJ = "地役首次登记";

    /**
     * 宣称林权统计
     */
    public static final String XCLQTJDJLXMC_DIYZXDJ = "地役注销登记";

    /**
     * 宣称林权统计
     */
    public static final String XCLQTJDJLXMC_DIYBGDJ = "地役变更登记";


    /**
     * 宣称登记小类缺失时，配置 :无
     */
    public static final String XCLQTJ_DJXLWU= "无";

    /**
     * 订单信息：渠道(1: 线上)
     */
    public static final String DDXX_QD_XS = "1";
    /**
     * 订单信息：渠道( 2: 线下)
     */
    public static final String DDXX_QD_XX = "2";
    /**
     * 订单信息：支付类型(0:税费统缴)
     */
    public static final String DDXX_ZFLX_SFTJ = "0";
    /**
     * 订单信息：支付类型(1：缴纳税)
     */
    public static final String DDXX_ZFLX_JNS = "1";
    /**
     * 订单信息：支付类型(2：缴纳费)
     */
    public static final String DDXX_ZFLX_JNF = "2";
    /**
     * 订单信息：订单类型(0：税费统缴)
     */
    public static final Integer DDXX_DDLX_SFTJ = 0;
    /**
     * 订单信息：订单类型(1：缴纳税)
     */
    public static final Integer DDXX_DDLX_JNS = 1;
    /**
     * 订单信息：订单类型(2：缴纳费)
     */
    public static final Integer DDXX_DDLX_JNF = 2;
    /**
     * 缴款途径：合一支付
     */
    public static final Integer JKTJ_HYZF = 2;
    /**
     * 缴款途径：商业银行
     */
    public static final Integer JKTJ_SYYH = 1;


    //  不动产登记簿查询pdf接口打印类型
    public static final String DYLX_PDFJK_CDXX_BDCQZ = "pdfjk_bdcdjbcxbdcqz";
    //不动产登记簿查询pdf接口打印类型，房产证土地证
    public static final String DYLX_PDFJK_CDXX_FCTDZ = "pdfjk_bdcdjbcxfcztdz";
    //房屋套次查询pdf接口
    public static final String DYLX_PDFJK_STFZM = "pdfjk_stfzm";
    //房屋套次查询pdf接口打印类型，首套房证明无数据
    public static final String DYLX_PDFJK_STFZM_0 = "pdfjk_stfzm_0";

    /*
     * 海域使用权不动产类型
     * */
    public static final Integer[] BDCLX_HYSYQ = {5, 6, 10, 11, 12, 13};

}

