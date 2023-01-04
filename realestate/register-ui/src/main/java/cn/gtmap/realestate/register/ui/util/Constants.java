package cn.gtmap.realestate.register.ui.util;

/**
 * 常量类
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/6/27 15:22
 */
public class Constants {
    /**
     * 系统版本-南通
     */
    public static final String VERSION_NANTONG = "nantong";
    /**
     * 系统版本-合肥
     */
    public static final String VERSION_HEFEI = "hefei";

    /**
     * 系统版本-蚌埠
     */
    public static final String VERSION_BENGBU = "bengbu";

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
     * 信息补录日志参数：记录操作的改变
     */
    public static final String XMID = "xmid";

    /**
     * 信息补录日志参数：记录操作信息
     */
    public static final String XXBL = "XXBL";
    /**
     * 条数常量，用于判断
     */
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int MOULD_LOW = 3;
    public static final int MOULD = 4;
    public static final int MOULDONE = 8;
    public static final int MOULDTWO = 16;
    public static final int MOULDTHREE = 24;

    /**
     * 土地承包经营权数量
     */
    public static final int MOULDTDCBONE = 6;
    /**
     * 建构筑物共有权数量
     */
    public static final int MOULDJZWGYONE = 15;
    public static final int MOULDJZWGYTWO = 30;

    /**
     * 方式类型 1、2...
     */

    public static final String TYPE1 = "1";
    public static final String TYPE2 = "2";

    /**
     * 字典转换常量
     */
    public static final String BFQLQTZK = "bfqlqtzk";
    public static final String FJ = "fj";
    public static final String FWYT = "fwyt";
    public static final String TDYT = "tdyt";
    public static final String QLXZ = "qlxz";
    public static final String GYFS = "gyfs";
    public static final String ZSLX = "zslx";
    public static final String QLLX = "qllx";
    public static final String JZMJ = "jzmj";
    public static final String ZL = "zl";
    public static final String FWGHYT = "ghyt";
    public static final String JYHTH = "jyhth";

    /**
     * 查封权利备注
     */
    public static final String CFBZ = "cfbz";
    public static final String DJLX = "djlx";
    public static final String FWXZ = "fwxz";
    public static final String GHYT = "fwyt";
    public static final String FWJG = "fwjg";
    public static final String MJDW = "mjdw";
    public static final String DYBDCLX = "dybdclx";
    public static final String DYFS = "dyfs";
    public static final String GJZWLX = "gjzwlx";
    public static final String YGDJZL = "ygdjzl";
    public static final String CFLX = "cflx";
    public static final String LDSYQXZ = "ldsyqxz";
    public static final String LZ = "lz";
    public static final String QY = "qy";
    public static final String TDSYQXZ = "tdsyqxz";
    public static final String SYTTLX = "syttlx";
    public static final String YZYFS = "yzyfs";
    public static final String FDCQ = "fdcq";
    public static final String ZDZHYT = "zdzhyt";
    /**
     * 常量字符串
     */
    public static final String ZDT = "zdt";
    public static final String HST = "hst";

    /**
     * 打印xml常量
     */
    // 宗地图打印xml
    public static final String ZDT_DY_XML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
            "<fetchdatas>" +
            "    <page>" +
            "        <datas>" +
            "            <data name=\"zdt\" type=\"Image\">$ZDT</data>" +
            "        </datas>" +
            "    </page>" +
            "</fetchdatas>";

    // 户室图打印xml
    public static final String HST_DY_XML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
            "<fetchdatas>" +
            "    <page>" +
            "        <datas>" +
            "            <data name=\"hst\" type=\"Image\">$HST</data>" +
            "        </datas>" +
            "    </page>" +
            "</fetchdatas>";

    // 宗地图户室图打印xml
    public static final String ZDTHST_DY_XML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
            "<fetchdatas>" +
            "    <page>" +
            "        <datas>" +
            "            <data name=\"zdt\" type=\"Image\">$ZDT</data>" +
            "            <data name=\"hst\" type=\"Image\">$HST</data>" +
            "        </datas>" +
            "    </page>" +
            "</fetchdatas>";


    /**
     * 字符串中为null
     */
    public static final String STR_NULL = "null";
    /**
     * 字符串中为undefined
     */
    public static final String STR_UNDEFINED = "undefined";

    /**
     * 登记簿打印常量
     */
    public static final String BDCDYH = "bdcdyh";
    public static final String APPLICATION = "application/pdf;charset=utf-8";
    public static final String QLRLX = "qlrlx";
    public static final String TDSYQR = "tdsyqr";
    public static final String FTTDMJ = "fttdmj";
    public static final String SYQMJ = "syqmj";

    /**
     * 南通打印类型
     */
    public static final String[] DYLX_ARR_NANTONG = {"grspb", "dwspb", "plspb", "dyaspb", "scspb", "grspb_zx", "dwspb_zx", "dyaspb_zx","hyspb","hyspb_zx"};

    /**
     * 登记历史跳转页
     */
    public static final String BDCDYDJLSGXCQHTML = "bdcdyDjLsgxcq.html";
    public static final String BDCDYDJLSGXXZQLHTML = "bdcdyDjLsgxxzql.html";

    /**
     * 常州武进档案号流水号区分类型
     */
    public static final String BDC_CZWJDAH_1 = "BDC_CZWJDAH_1";
    public static final String BDC_CZWJDAH_2 = "BDC_CZWJDAH_2";
    public static final String BDC_CZWJDAH_3 = "BDC_CZWJDAH_3";

    /**
     * 档案号类型
     */
    public static final String BDC_DAHLX_ZS = "ZS";
    public static final String BDC_DAHLX_ZM = "ZM";

    /**
     * 档案交接打印类型
     */
    public static final String DDJJ_DYLX_SPB = "审批表";
    public static final String DAJJ_DYLX_ML = "目录";
    public static final String DAJJ_DYLX_FM = "封面";
    public static final String DAJJ_DYLX_QB = "全部";

    public static final String DAJJ_DYLX = "DAJJ_DYLX";
    public static final String DAJJ_DY = "DAJJDY";

    public static final String BDC_JT_DAHLX_QZ = "QZ";
    public static final String BDC_JT_DAHLX_ZM = "JTZM";
    public static final String BDC_JT_DAHLX_CQZX = "CQZX";
    public static final String BDC_JT_DAHLX_DYQZX = "DYQZX";
    public static final String BDC_JT_DAHLX_QTZX = "QTZX";

    /**
     * 溧阳档案交接档案号类型
     */
    public static final String BDC_LY_DAHLX_CQ = "LYCQ";
    public static final String BDC_LY_DAHLX_ZM = "LYZM";
    public static final String BDC_LY_DAHLX_CF = "LYCF";


    /**
     * 溧阳档案交接案卷号生成业务类型
     */
    public static final String BDC_LY_AJHLX_CQ = "LYCQAJH";
    public static final String BDC_LY_AJHLX_ZM = "LYZMAJH";

    public static final String ZZSJFW_YEAR = "YEAR";

    public static final String DAH_REDIS_LOCK_NAME = "DAH_REDIS_LOCK_NAME";

    /**
     * 数据修改参数中文
     */
    public static final String SJXG_PARAMCH = "paramCha";

    /**
     * @description 数据修改
     */
    public static final String SJXG = "SJXG";

    /**
     * 数据修改日志参数：记录修改的内容
     */
    public static final String SJXG_CHANGE = "change";
}
