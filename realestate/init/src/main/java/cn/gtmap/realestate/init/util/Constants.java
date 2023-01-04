package cn.gtmap.realestate.init.util;


import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.util.CommonConstantUtils;

/**
 * 常量类
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0
 * @description
 */
public class Constants {
    /**
     * 默认值
     */
    public static final String DEFAULT = "DEFAULT";
    /**
     * qlrsjly 楼盘表
     */
    public static final Integer QLRSJLY_LPB = 1;
    /**
     * qlrsjly 原权利人
     */
    public static final Integer QLRSJLY_YQLR = 2;
    /**
     * qlrsjly 原义务人
     */
    public static final Integer QLRSJLY_YYWR = 3;
    /**
     * qlrsjly 查封逻辑
     */
    public static final Integer QLRSJLY_CF = 4;
    /**
     * qlrsjly 预转现逻辑
     */
    public static final Integer QLRSJLY_YZX = 5;
    /**
     * qlrsjly 宗地权利人
     */
    public static final Integer QLRSJLY_ZDQLR = 6;
    /**
     * qlrsjly 原项目中同权利的权利人
     */
    public static final Integer QLRSJLY_YTQL_QLR = 7;
    /**
     * qlrsjly 俩个项目互换权利人
     */
    public static final Integer QLRSJLY_HH_QLR = 8;
    /**
     * qlrsjly 外联项目权利人
     */
    public static final Integer QLRSJLY_WLXM_QLR = 9;
    /**
     * qlrsjly 外联项目义务人
     */
    public static final Integer QLRSJLY_WLXM_YWR = 10;
    /**
     * qlxx数据来源  楼盘表
     */
    public static final String QLSJLY_LPB = "1";
    /**
     * qlxx数据来源  原项目
     */
    public static final String QLSJLY_YXM = "2";
    /**
     * qlxx数据来源  预转现的原预告抵押项目
     */
    public static final String QLSJLY_YZX_YYD = "3";
    /**
     * qlxx数据来源  预转现的原抵押项目
     */
    public static final String QLSJLY_ZH_YDY = "4";
    /**
     * qlxx数据来源  预抵押转预抵押的原抵押项目
     */
    public static final String QLSJLY_YDY_YDY = "5";


    /**
     * qlxx数据来源  外联证书（国有建设用地使用权海域证换发土地证首次登记）
     */
    public static final String QLSJLY_WLZS_HFTDZ = "7";

    /**
     * 权利其他状况附记 全部
     */
    public static final String XT_QLQTZK_FJ_MODE_ALL = "1";
    /**
     * 权利其他状况附记 权利其他状况
     */
    public static final String XT_QLQTZK_FJ_MODE_QLQTZK = "2";
    /**
     * 权利其他状况附记 附记
     */
    public static final String XT_QLQTZK_FJ_MODE_FJ = "3";
    /**
     * 错误信息 没有参数
     */
    public static final String MESSAGE_NOPARAMETER = "message.noparameter";
    /**
     * 字段 不动产单元号bdcdyh
     */
    public static final String BDCDYH = "bdcdyh";
    /**
     * 项目ID
     */
    public static final String XMID = "xmid";
    /**
     * 部分权利其他状况
     */
    public static final String BFQLQTZK = "bfqlqtzk";
    /**
     * 工作流实例ID
     */
    public static final String GZLSLID = "gzlslid";
    /**
     * 项目IDS
     */
    public static final String XMIDS = "xmids";
    /**
     * 附记
     */
    public static final String FJ = "fj";

    /**
     * 按份共有
     */
    public static final String GYFS_AFGY = "2";
    /**
     * 平方米转换成亩的权利类型
     */
    public static final String[] PFM_ZH_M = new String[]{CommonConstantUtils.QLLX_NYDJYQ.toString(),CommonConstantUtils.QLLX_TDCBNYDSYQ.toString()};
    /**
     * 生成证明的权利实体类
     */
    public static final Class[] BDCQZM_CLASS = new Class[]{BdcCfDO.class, BdcDyaqDO.class, BdcDyiqDO.class,BdcQtxgqlDO.class,BdcFwzlDO.class,BdcYgDO.class,BdcYyDO.class};
    /**
     * 生成证书的权利实体类
     */
    public static final Class[] BDCQZS_CLASS = new Class[]{BdcFdcqDO.class,BdcTdsyqDO.class,BdcJsydsyqDO.class,BdcTdcbnydsyqDO.class,BdcLqDO.class,BdcHysyqDO.class,BdcGjzwsyqDO.class};


    /**
     * type  初始化入库
     */
    public static final Integer DATA_TYPE_INIT = 1;
    /**
     * type  初始化证书
     */
    public static final Integer DATA_TYPE_ZS = 2;
    /**
     * type  抓取楼盘表数据
     */
    public static final Integer DATA_TYPE_LPB = 3;
    /**
     * type  对照楼盘表数据
     */
    public static final Integer DATA_TYPE_LPB_DZ = 5;
    /**
     * type  普通操作逻辑
     */
    public static final Integer DATA_TYPE_PT = 4;

    /**
     * 土地使用权人 全体业主
     */
    public static final String TDSYQR_QTYZ = "全体业主";

    /************************************水电气常量**************************************/
    public static final String QI_HTQDZP = "(燃气)签订合同照片";
    public static final String QI_XHZSFZZP = "(燃气)买方（新户主）身份证照片";
    public static final String QI_BLCGZTM = "true";

    public static final String DIAN_FJLX1 = "001";
    public static final String DIAN_FJLX2 = "002";
    public static final String DIAN_XY = "(电)业务协议";
    public static final String DIAN_HT = "(电)业务合同";
    public static final String DIAN_BLCGZTM = "0000";
    public static final String DIAN_BASE64HEAD = "data:image/jpg;base64,";

    public static final String SHUIDIAN_XHZSFZFM = "（水电）新户主身份证反面";
    public static final String SHUIDIAN_XHZSFZZM = "（水电）新户主身份证正面";

    public static final String SHUI_HT = "(水)业务合同";
    public static final String SHUI_SLD = "(水)受理单";
    public static final String SHUI_FZC = "(水)业务附件-房产证";
    public static final String SHUI_LHZSFZFM = "(水)老户主身份证反面";
    public static final String SHUI_LHZSFZZM = "(水)老户主身份证正面";
    public static final String SHUI_BLCGZTM = "true";

    /************************************家庭成员**************************************/
    /**
     * 家庭成员户口簿编号  业务类型
     */
    public static final String YWLX_HKB = "YWLX_HKB";
    /**
     * 家庭成员与户主关系  户主或本人
     */
    public static final String YHZGX_HZHBR= "0";
    /**
     * 家庭成员与户主关系  本人
     */
    public static final String YHZGX_BR= "01";
    /**
     * 家庭成员与户主关系  户主
     */
    public static final String YHZGX_HZ = "02";
    /**
     * 家庭成员表初始化版本号
     */
    public static final Integer JTCY_HKB_BBH = 1;

    public static final String GGBH_REDIS_LOCK_NAME = "GGBH_REDIS_LOCK_NAME";
    ;

    /**
     * 政务推送 办件状态 1受理
     */
    public static final String ZWSB_SL = "1";

    /**
     * 政务推送 办件状态 2办结
     */
    public static final String ZWSB_BJ = "2";

    /**
     * 政务推送 办件状态 3删除
     */
    public static final String ZWSB_SC = "0";

    /**
     * 电子证照文件分割符
     */
    public static final String DZZZ_SPLIT_SLIGHT = ".";

}
