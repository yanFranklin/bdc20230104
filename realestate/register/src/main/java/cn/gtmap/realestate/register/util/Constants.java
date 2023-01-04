package cn.gtmap.realestate.register.util;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/31
 * @description 常量类
 */
public class Constants {
    /**
     * 批量坐落后缀字符,SFX 为suffix的缩写形式
     */
    public static final String ZL_SFX = "zlSFX";
    /**
     * 打印类型 申请审批表
     */
    public static final String DYLX_BDC_SQ_SPB = "bdcSqSpb";
    /**
     * 打印类型 查解封申请审批表
     */
    public static final String DYLX_BDC_SQ_SPB_CF = "bdcSqSpbcf";
    /**
     * 打印类型 居住权申请审批表
     */
    public static final String DYLX_BDC_SQ_SPB_JZ = "spbjz";
    /**
     * 打印类型 申请审批表批量
     */
    public static final String DYLX_BDC_SQ_SPB_PL = "bdcSqSpbPl";

    /**
     * 打印类型 申请审批表
     */
    public static final String DYLX_BDC_SQ_DYASPB = "dyabdcSqSpb";
    /**
     * 打印类型 申请审批表批量
     */
    public static final String DYLX_BDC_SQ_DYASPB_PL = "dyabdcSqSpbPl";

    /**
     * 打印类型 单位审批表
     */
    public static final String DYLX_BDC_SQ_SPB_DW = "dwspb";
    /**
     * 打印类型 单位审批表-注销
     */
    public static final String DYLX_BDC_SQ_SPB_DW_ZX = "dwspb_zx";
    /**
     * 打印类型 首次证明打印
     */
    public static final String DYLX_BDC_SQ_SPB_SC = "scspb";
    /**
     * 打印类型 个人打印
     */
    public static final String DYLX_BDC_SQ_SPB_GR = "grspb";
    /**
     * 打印类型 个人打印_注销
     */
    public static final String DYLX_BDC_SQ_SPB_GR_ZX = "grspb_zx";

    /**
     * 打印类型 抵押打印
     */
    public static final String DYLX_BDC_SQ_SPB_DYA = "dyaspb";
    /**
     * 打印类型 抵押打印_注销
     */
    public static final String DYLX_BDC_SQ_SPB_DYA_ZX = "dyaspb_zx";
    /**
     * 打印类型 汇总打印（南通）
     */
    public static final String DYLX_BDC_SQ_SPB_PLHZ = "plspb";

    /**
     * 打印类型 海域审批表打印（南通）
     */
    public static final String DYLX_BDC_SQ_SPB_HY = "hyspb";

    /**
     * 打印类型 海域审批表打印（南通）
     */
    public static final String DYLX_BDC_SQ_SPB_HYZX = "hyspb_zx";


    /**
     * 需要单个多张打印的类型
     */
    public static final String[] DYLX_SPB_ARR = {DYLX_BDC_SQ_SPB, DYLX_BDC_SQ_SPB_DW, DYLX_BDC_SQ_DYASPB, DYLX_BDC_SQ_SPB_CF, DYLX_BDC_SQ_SPB_JZ};
    /**
     * 需要批量合并打印的类型
     */
    public static final String[] DYLX_SPB_PLHB_ARR = {DYLX_BDC_SQ_SPB_PL, DYLX_BDC_SQ_SPB_SC, DYLX_BDC_SQ_DYASPB_PL, "bdcSqSpbpl"};

    /**
     * 预抵押
     */
    public static final String YDYA = "ydya";
    /**
     * 异议登记
     */
    public static final String QLLX_YY = "97";
    /**
     * 地役权权利类型
     */
    public static final String QLLX_DYIQ = "19";
    /**
     * 预告权利类型
     */
    public static final String QLLX_YG = "96";
    /**
     * 查封权利类型
     */
    public static final String QLLX_CF = "98";
    /**
     * 房屋租赁
     */
    public static final String QLLX_FWZL = "93";

    /**
     * 登记机构
     */
    public static final String DJJG = "DJJG";
    /**
     * 登簿人
     */
    public static final String DBR = "DBR";
    /**
     * 权属状态
     */
    public static final String QSZT = "QSZT";

    /**
     * 原项目权属状态
     */
    public static final String YXM_QSZT = "YXM_QSZT";
    /**
     * 不动产单元号
     */
    public static final String BDCDYH = "BDCDYH";
    /**
     * 坐落
     */
    public static final String ZL = "ZL";
    /**
     * 共有方式
     */
    public static final String GYFS = "GYFS";
    /**
     * 权利类型
     */
    public static final String QLLX = "QLLX";
    /**
     * 登记类型
     */
    public static final String DJLX = "DJLX";
    /**
     * 不动产权证号
     */
    public static final String BDCQZH = "BDCQZH";
    /**
     * 拼接的不动产权证号
     */
    public static final String BDCQZH_HB = "BDCQZH_HB";
    /**
     * 权利人
     */
    public static final String QLR = "QLR";
    /**
     * 义务人
     */
    public static final String YWR = "YWR";
    /**
     * 权利人证件号
     */
    public static final String QLRZJH = "QLRZJH";
    /**
     * 义务人证件号
     */
    public static final String YWRZJH = "YWRZJH";
    /**
     * 权利人证件种类
     */
    public static final String QLRZJZL = "QLRZJZL";
    /**
     * 义务人证件种类
     */
    public static final String YWRZJZL = "YWRZJZL";

    /**
     * message提示信息,未传入有效参数
     */
    public static final String MESSAGE_NOPARAMETER = "message.noparameter";

    /**
     * message提示信息,未查到有效数据
     */
    public static final String MESSAGE_NORESULT ="message.noresult";

    /**
     * message提示信息,未获取到用户信息
     */
    public static final String MESSAGE_NOUSER = "message.nouser";

    /**
     * 百分号
     */
    public static final String FH_BFH = "%";

    /**
     * 点号
     */
    public static final String FH_DH = ".";

    /**
     * 斜杆
     */
    public static final String FH_XG = "/";

    /**
     * 补录类型，新增
     */
    public static final Integer BLLX_NEW = 0;

    /**
     * 补录类型，修改
     */
    public static final Integer BLLX_UPDATE = 1;

    /**
     * 补录状态，审核中
     */
    public static final Integer BLZT_ONGOING = 0;

    /**
     * 补录状态，审核完成
     */
    public static final Integer BLZT_END = 1;

    /**
     * 补录状态，退回
     */
    public static final Integer BLZT_BACK = 2;

    /**
     * 补录状态，未转发
     */
    public static final Integer BLZT_WZF = 3;
    /**
     * 补录审核类型，不需要审核
     */
    public static final Integer BLSHLX_NO = -1;

    /**
     * 补录审核类型，市级
     */
    public static final Integer BLSHLX_SJ = 1;

    /**
     * 补录审核类型，县级
     */
    public static final Integer BLSHLX_XJ = 2;

    /**
     * 房屋用途标识-阁楼
     */
    public static final String FWYT_GL = "阁楼";

    /**
     * 房屋用途标识-车库
     */
    public static final String FWYT_CK = "车库";

    /**
     * 房屋用途标识-车位
     */
    public static final String FWYT_CW = "车位";

    /**
     * 接口返回状态-成功
     */
    public static final Integer SUCCEED_CODE = 200;

    /**
     * 房改房办理状态-可以办理
     */
    public static final String KYBL = "1";

    /**
     * 房改房办理状态-不予办理
     */
    public static final String BYBL = "0";

    /**
     * 字典名称
     */
    public static final String FWXZ = "fwxz";

    public static final String GHYT = "fwyt";

    /**
     * 打印类型：建设用地逻辑幢
     */
    public static final String DYLX_JSYD_LJZ = "jsydljz";

    /**
     * 登记簿排序第一标准
     */
    public static final String ORDERBYFIRST = "orderbyFirst";

    /**
     * 登记簿排序第二标准
     */
    public static final String ORDERBYSECOND = "orderbySecond";

    /**
     * 登记簿排序添加拼接字段前缀
     */
    public static final String PREFIXORDERBY = "t.";

    /**
     * 量化操作： +1
     */
    public static final Integer LHCZ_ADD = 1;
    /**
     * 量化操作： -1
     */
    public static final Integer LHCZ_SUBTRACT = -1;

    /**
     * 证书样式：电子证
     */
    public static final String ZSYS_DZZ = "电子证";
    /**
     * 证书样式：纸质证
     */
    public static final String ZSYS_ZZZ = "纸质证";
    /**
     * 证书数量：默认值 1
     */
    public static final Integer ZSSL_MRZ = 1;

    /**
     * 抵押流程证书序号默认值
     */
    public static final Integer ZSXH_DYAQ_MRZ= 99;
}
