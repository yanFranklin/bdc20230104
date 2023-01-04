package cn.gtmap.realestate.certificate.util;


import java.io.File;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zy
 * Date: 19-1-18
 * Time: 下午7:06
 * Des:常量配置
 * To change this template use File | Settings | File Templates.
 */
public class Constants {
    public static String BDC_DZZZ_DATA_PATH;
    public static final String BDC_DZZZ_FORMAT_PDF = ".pdf";
    public static final String BDC_DZZZ_FORMAT_JPG = ".jpg";

    public static final String BDCDJ_VERSION_2 = "2.0";
    public static final String BDCDJ_VERSION_3 = "3.0";

    //不动产电子证书
    public static final String BDC_ZZLXDM_ZS = "11100000MB03271699001";
    public static final String BDC_ZZLXDM_ZM = "11100000MB03271699022";
    public static final String[] BDC_ZZLXDM = {BDC_ZZLXDM_ZS, BDC_ZZLXDM_ZM};

    public static final String BDC_ZSLX_ZS = "zs";
    public static final String BDC_ZSLX_ZMS = "zms";
    public static final String BDC_ZSLX_MC_ZS = "不动产权";
    public static final String BDC_ZSLX_MC_ZMS = "不动产证明";
    public static final String BDC_ZSLX_MC_SCDJZ = "首次登记证";

    public static final String BDC_DZZZ = "不动产电子证照";

    public static final String BDC_DZQZ = "不动产电子签章";

    public static final String BDC_DZZZ_QLR_DM = "qlr";
    public static final String BDC_DZZZ_YWR_DM = "ywr";

    public static final String TRUE = "true";

    //证照状态字典表
    public static final Integer BDC_DZZZ_ZZZT_Y = 1;
    public static final Integer BDC_DZZZ_ZZZT_N = 2;

    //第三方签章公司名称
    public static final String SIGN_COMPANY_BRBC_MC = "BRBC";//百润百成
    public static final String SIGN_COMPANY_XSYZ_MC = "XSYZ";//翔晟云章
    public static final String SIGN_COMPANY_IDEABANK_MC = "IDEABANK";//意源

    public static final String DZZZ_BASE64="base64";
    public static final String DZZZ_SIGNUNIT="signunit";
    public static final String DZZZ_DWDM="dwdm";
    public static final String YYMC = "yymc";

    // resources文件夹路径
    public static final String RESOURCES_IMG = "img" + File.separator;
    public static final String RESOURCES_PDF = "pdf" + File.separator;
    public static final String RESOURCES_TEMPLATE = "template" + File.separator;

    //注销原因字典表
    //正常办理登记业务注销
    public static final String DZZZ_ZZZT_ZX_ZCZX="1";
    //电子证照签发错误注销
    public static final String DZZZ_ZZZT_ZX_YCZX="2";

    //水印类型字典表
    //加注件水印
    public static final String WATERMARK_TYPE_JZJ = "3";

    // 二维码类型字典表
    //无logo
    public static final String QRCODE_TYPE = "1";
    //有logo
    public static final String QRCODE_TYPE_LOGO = "2";

    // 不动产电子证照流水号锁名称
    public static final String REDISSON_LOCK_LSH = "REDISSON_LOCK_LSH";

    // 电子证照上传附件中心后缀名定义
    // 保存附录图片后缀名称
    public static final String BDC_DZZZ_FILENAME_FLIMG = "_FlImg";
    // 保存未签章PDF后缀名称
    public static final String BDC_DZZZ_FILENAME_SIGNA = "_signa";

    //未签章
    public static final Integer DZZZ_QZZT_WQZ = 1;
    //已签章
    public static final Integer DZZZ_QZZT_YQZ = 2;

    //request 参数
    public static final String REQUEST_PARAM_TOKEN = "token";
    public static final String REQUEST_PARAM_LOG_FLAG = "logFlag";

    public static final String[] DZZZ_CZZTDMLXDM = new String[]{"001","099","111","114","115","118","119","120","122","411","412","413","414"
            ,"511","516","999"};

    public static final String DZZZ_NUMBER_ZERO = "0";
    public static final String DZZZ_NUMBER_EIGHT = "8";

    //统计接口使用和占比情况
    public static final String[] regions = {"ZZPDF","ZZZX","ZZCX","ZZJS","ZZXXXZ","ZZDZXZ","ZZYSJ","ZZWJYZ"};
    public static final String[] regionZzpdf = {"生成证照","生成证照1.1"};
    public static final String[] regionZzzx = {"证照注销"};
    public static final String[] regionZzcx = {"证照查询"};
    public static final String[] regionZzjs = {"证照检索"};
    public static final String[] regionZzxxxz = {"下载证照文件"};
    public static final String[] regionZzdzxz = {"下载证照文件地址"};
    public static final String[] regionZzysj = {"证照元数据下载"};
    public static final String[] regionZzwjyz = {"证照文件验证"};

    //未同步
    public static final Integer DZZZ_TBZT_WTB = 1;
    //已同步
    public static final Integer DZZZ_TBZT_YTB = 2;

    //市级存储中转token
    public static final String REQUEST_TOKEN_RMAPCACHE = "REQUEST_TOKEN_RMAPCACHE";

    //响应enum类型
    public static final String RESPONSE = "RESPONSE";
    public static final String VERIFY_FILE_RESPONSE = "VERIFY_FILE_RESPONSE";
    public static final String VERIFY_INFO_RESPONSE = "VERIFY_INFO_RESPONSE";
    public static Map<String,String> responseMap;

    private Constants() {
    }
}
