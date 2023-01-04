package cn.gtmap.realestate.building.util;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-10-27
 * @description 静态常量
 */
public class Constants {

    // 南通版本号
    public static final String SYSVERSION_NANTONG = "nantong";

    // 标准版版本号
    public static final String SYSVERSION_STANDARD = "standard";

    /**
     * 默认拼接字符串的分隔符
     */
    public static final String DEFAULT_SEPARATOR = ",";

    /**
     * 整幢关联 是否重新关联 是
     */
    public static final String ZZGL_CXGL_TRUE = "1";

    /**
     * 整幢关联 是否重新关联 否
     */
    public static final String ZZGL_CXGL_FALSE = "0";

    /**
     * 整幢关联 房屋编码
     */
    public static final String ZZGL_FWBM = "1";

    /**
     * 整幢关联 单元号、物理层数、房间号
     */
    public static final String ZZGL_DYHWLCSFJH = "2";

    /**
     * 逻辑幢表
     */
    public static final String FW_LJZ = "FW_LJZ";
    /**
     * 户室表
     */
    public static final String FW_HS_TABLE = "FW_HS";
    /**
     * 户室表
     */
    public static final String FW_YCHS_TABLE = "FW_YCHS";

    /**
     * 项目表
     */
    public static final String FW_XMXX_TABLE = "FW_XMXX";
    /**
     *
     */
    public static final String FW_ZHS_TABLE = "FW_ZHS";

    /**
     * 房屋合并子户室配置  默认 全部添加到主户室下
     */
    public static final String FWHB_ZHS_CONFIG_DEFAULT = "0";

    /**
     * 房屋合并子户室配置  可选择挂接
     */
    public static final String FWHB_ZHS_CONFIG_CHOOSE = "1";

    /**
     * ZDZH_DJXX   ISJD  解冻
     */
    public static final String ZDZH_DJXX_ISJD_JD = "1";

    /**
     * ZDZH_DJXX   ISJD  未解冻
     */
    public static final String ZDZH_DJXX_ISJD_NOJD = "0";

    /**
     * 变更类型
     */
    public static final String BGLX_CF = "拆分";
    public static final String BGLX_HB = "合并";
    public static final String BGLX_BG = "变更";
    public static final String BGLX_MS = "灭失";
    public static final String FWLX_BG = "房屋类型变更";

    /**
     * 不动产房屋类型（项目内多幢）
     */
    public static final String BDCDYFWLX_XMNDZ = "1";
    /**
     * 不动产房屋类型（独幢房屋）
     */
    public static final String BDCDYFWLX_DZ = "2";
    /**
     * 不动产房屋类型（层）
     */
    public static final String BDCDYFWLX_C = "3";
    /**
     * 不动产房屋类型（户）
     */
    public static final String BDCDYFWLX_H = "4";
    /**
     * 宗地特征码A
     */
    public static final String ZDTZM_A = "A";
    /**
     * 宗地特征码B
     */
    public static final String ZDTZM_B = "B";
    /**
     * 宗地特征码C
     */
    public static final String ZDTZM_C = "C";
    /**
     * 宗地特征码D
     */
    public static final String ZDTZM_D = "D";
    /**
     * 宗地特征码E
     */
    public static final String ZDTZM_E = "E";
    /**
     * 宗地特征码F
     */
    public static final String ZDTZM_F = "F";
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
     * 宗地特征码N
     */
    public static final String ZDTZM_N = "N";
    /**
     * 宗地特征码S
     */
    public static final String ZDTZM_S = "S";
    /**
     * 宗地特征码W
     */
    public static final String ZDTZM_W = "W";
    /**
     * 宗地特征码X
     */
    public static final String ZDTZM_X = "X";
    /**
     * 宗地特征码Y
     */
    public static final String ZDTZM_Y = "Y";
    /**
     * 土地类型（土地）
     */
    public static final String TDLX_ZD = "ZD";
    /**
     * 土地类型（权属宗地）
     */
    public static final String TDLX_QSZD = "QSZD";
    /**
     * 定着物特征码 F
     */
    public static final String BDCDY_DZWTZM_F = "F";


    /**
     * nyd_qlr表中sfldsyqr sflmsyqr  sflmsuqr 字段 “是” 的枚举值
     */
    public static final String LQ_QLRBZ = "1";

    /**
     * 更新时实体不存在 异常
     */
    public static final String UPDATE_ENTITY_NOT_FOUND = "实体不存在";


    /**
     * 更新时实体不存在 异常
     */
    public static final String GET_BDCDY_ERROR = "获取不动产单元号失败";

    /**
     * 不动产单元流水号_万
     */
    public static final Integer BDCDY_LSH_W = 10000;

    /**
     * 不动产单元流水号超过9999后从a001，a999,b00a,b999
     * 初始化s_sj_maxBdcdyh表时计算使用
     */
    public static final Integer BDCDY_LSH_NEW_RULR = 999;

    /**
     * 变更处理服务  拆分
     */
    public static final String BG_SERVICE_CF = "hsCfService";
    /**
     * 变更处理服务  合并
     */
    public static final String BG_SERVICE_HB = "hsHbService";
    /**
     * 变更处理服务  灭失
     */
    public static final String BG_SERVICE_MS = "hsMsService";
    /**
     * 变更处理服务  变更
     */
    public static final String BG_SERVICE_JBXXBG = "hsJbxxBgService";

    /**
     * 宗地 地籍信息 处理服务
     */
    public static final String DJXX_SERVICE_ZD = "zdDjxxService";

    /**
     * 权属宗地 地籍信息 处理服务
     */
    public static final String DJXX_SERVICE_QSZD = "qszdDjxxService";

    /**
     * 农用地 地籍信息 处理服务
     */
    public static final String DJXX_SERVICE_NYD = "nydDjxxService";

    /**
     * 经营权 地籍信息 处理服务
     */
    public static final String DJXX_SERVICE_JYQDK = "jyqDkDjxxService";

    /**
     * 宗海 地籍信息 处理服务
     */
    public static final String DJXX_SERVICE_ZH = "zhDjxxService";

    /**
     * 林木使用权人
     */
    public static final String LQQLRLX_LMSYQR = "lmsyqr";
    /**
     * 林木所有权人
     */
    public static final String LQQLRLX_LMSUQR = "lmsuqr";
    /**
     * 林地使用权人
     */
    public static final String LQQLRLX_LDSYQR = "ldsyqr";
    /**
     * 不动产单元最大流水号
     */
    public static final Integer BDCDYH_MAX_LSH = 35974;
    /**
     * 不动产单元现势状态 登记状态 1
     */
    public static final String BDCDY_XS_ZT_DJZT = "1";

    /**
     * 不动产单元现势状态 注销状态 2
     */
    public static final String BDCDY_XS_ZT_ZXZT = "2";
    /**
     * 地籍号长度
     */
    public static final Integer DJH_LENGTH = 19;

    /**
     * 房屋类型变更——户室变独幢
     */
    public static final String FWLXBG_ANYTODZ = "fwlxBgAnyToDzService";
    /**
     * 房屋类型变更——户室变多幢
     */
    public static final String FWLXBG_ANYODUOZ = "fwlxBgAnyToDuozService";
    /**
     * 房屋类型变更——多幢变户室
     */
    public static final String FWLXBG_ANYZTOHS = "fwlxBgAnyToHsService";


    /**
     * FWHS表 BDCZT 不可用 状态 数据库枚举值
     */
    public static final String BDCZT_BKY = "0";

    /**
     * FWHS表 BDCZT 可用 状态 数据库枚举值
     */
    public static final String BDCZT_KY = "1";

    /**
     * 房屋户室权利状态-未设权利
     */
    public static final String FWHS_QLZT_WSQL = "0";

    /**
     * 房屋户室权利状态-抵押权利
     */
    public static final String FWHS_QLZT_QLDY = "2";

    /**
     * 房屋户室权利状态-查封权利
     */
    public static final String FWHS_QLZT_QLCF = "3";

    /**
     * 项目信息F后四位自然幢号
     */
    public static final String BDCDYH_XMXX_ZRZH = "9999";

    /**
     * dozer 更新方式  执行update
     */
    public static final String DOZER_UPDATE_TYPE_DEFAULT = "update";

    /**
     * dozer 更新方式 先删除后插入更新
     */
    public static final String DOZER_UPDATE_TYPE_DELANDINSERT = "deleteAndInsert";

    /**
     * dozer 插入 标志
     */
    public static final String DOZER_INSERT = "insert";
    /**
     * 预测户室
     */
    public static final String FW_YCHS = "yc";
    /**
     * 实测户室
     */
    public static final String FW_SCHS = "sc";

    /**
     * 按户构建
     */
    public static final String LPBGJ_AHGJ = "1";
    /**
     * 单元户数平均构建
     */
    public static final String LPBGJ_DYHSPJGJ = "2";
    /**
     * 单元户数动态构建
     */
    public static final String LPBGJ_DYHSDTGJ = "3";


    /**
     * 变更处理服务  变更
     */
    public static final String XMXXBG_SERVICE_BG = "xmxxBgService";

    /**
     * 变更处理服务  灭失
     */
    public static final String XMXXBG_SERVICE_MS = "xmxxMsService";


    /**
     * 变更处理服务  合并
     */
    public static final String XMXXBG_SERVICE_HB = "xmxxHbService";

    public static final String LPBCONFIG_INFO_SERVICE_COLUMN = "columnTypeServiceImpl";
    public static final String LPBCONFIG_INFO_SERVICE_ZDCOLUMN = "zdColumnTypeServiceImpl";
    public static final String LPBCONFIG_INFO_SERVICE_BUTTON = "buttonTypeServiceImpl";
    public static final String LPBCONFIG_INFO_SERVICE_CONSTANT = "constantTypeServiceImpl";
    public static final String LPBCONFIG_INFO_SERVICE_SHOWCOLUMN = "showColumnTypeServiceImpl";
    public static final String LPBCONFIG_INFO_SERVICE_JOINCOLUMN = "joinColumnTypeServiceImpl";
    public static final String LPBCONFIG_INFO_SERVICE_NVLCOLUMN = "nvlColumnTypeServiceImpl";


    /**
     * common包中  domain 文件夹地址
     */
    public static final String COMMON_DOMAIN_PATH = "cn.gtmap.realestate.common.core.domain.building";


    /**
     * 界址表表名
     */
    public static final String JZBSB_TABLE_NAME_ZD="zd_jzbsb";
    public static final String JZBSB_TABLE_NAME_QSZD="qszd_jzbsb";
    public static final String JZBSB_TABLE_NAME_NYD="nyd_jzbsb";
    public static final String JZBSB_TABLE_NAME_ZH="zh_jzbsb";


    /**
     * 土地的BDCDYH 后9位
     */
    public static final String TD_BDCDYH_SUFIX = "W00000000";

    /**
     * 房屋户室合并方式 向上
     */
    public static final String FWHS_HBFX_UP = "3";

    /**
     * 房屋户室合并方式 向下
     */
    public static final String FWHS_HBFX_DWON = "4";
}
