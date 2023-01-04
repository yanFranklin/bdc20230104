package cn.gtmap.realestate.common.core.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021/4/16
 * @description 省级共享请求地址与接口beanName枚举值
 */
public enum GxEnum {

    GASFHC("shengjjk_gasfhc", "xgbmcx_gasfhc","公安部-身份核查"),

    DMXXCX("shengjjk_dmxx", "xgbmcx_dmxxcx","民政部-地名信息查询接口"),

    MARRIAGEQUERY("shengjjk_hydjxx", "marriageQuery","民政部-婚姻登记信息查询服务接口"),

    SHZZTYXXCX("shengjjk_shxydmxx", "shzztyxxcx","民政部-社会组织统一社会信用代码信息查询服务接口"),

    FINANCIALQUERY("shengjjk_jrxkz", "financialQuery","银保监会-金融许可证查询接口"),

    ORGANQUERY("shengjjk_sydwdjxx", "organQuery","中编办-事业单位登记信息（含机关、群团信息）查询接口"),

    DECISIONAPPLY("shengjjk_sfpjxx", "decisionApply","最高法-司法判决信息查询申请接口"),

    DECISIONRESPONSE("shengjjk_sfpjxxfk", "decisionResponse","最高法-司法判决信息结果反馈接口"),

    CSYXZM("shengjjk_csyxzm", "csyxzm","卫健委-出生医学证明查询接口"),

    SWYXZM("shengjjk_swyxzm", "swyxzm","卫健委-死亡医学证明查询接口"),

    JJHFRDJZS("shengjjk_jjhfrdjzs", "jjhfrdjzs","民政部-基金会法人登记证书查询接口"),

    MBFQYDJZS("shengjjk_mbfqydwdjzs", "mbfqydjzs","民政部-民办非企业单位登记证书查询接口"),

    SHTTFRDJZS("shengjjk_shttfrdjzs", "shttfrdjzs","民政部-社会团体法人登记证书查询接口"),

    HYXXHYGR("shengjjk_hyxxhygr", "hyxxhygr","民政部-婚姻登记信息核验（个人）接口"),

    HYXXHYSF("shengjjk_hyxxhysf", "hyxxhysf","民政部-婚姻登记信息核验（双方）接口"),

    QYJBXX("shengjjk_qyjbxx", "qyjbxx", "市场监管总局-企业基本信息查询接口"),

    QYJBXXYZ("shengjjk_qyjbxxyz", "qyjbxxyz", "市场监管总局-企业基本信息验证接口"),

    DQJG("shengjjk_dqjg", "dqjg", "中编办-党群机关信息查询接口"),

    SYDW("shengjjk_sydw", "sydw", "中编办-事业单位信息查询接口"),

    POLICEREALNAME("shengjjk_gmjbxxzxbd", "policeRealName", "省公安厅-公民基本信息在线比对接口"),

    RDBD("shengjijk_gmjbxxrxbd", "xgbmcx_rxbd", "省公安厅-公民基本信息人像比对接口"),

    ZJJGDBXX("zjjg_tsdbxx", "zjjg_tsdbxx", "资金监管-推送登簿信息接口"),

    POLICEHOUSEHOLDMEMBERS("shengjjk_jmcyxx", "policeHouseholdMembers", "省公安厅-居民户成员信息在线查询接口");

    // 查询业务类别
    private String cxywlb;

    // 服务名
    private String beanName;

    private String interfaceName;

    public String getCxywlb() {
        return cxywlb;
    }

    public void setCxywlb(String cxywlb) {
        this.cxywlb = cxywlb;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    GxEnum(String cxywlb, String beanName, String interfaceName){
        this.cxywlb = cxywlb;
        this.beanName = beanName;
        this.interfaceName = interfaceName;
    }

    public static String getBeanName(String cxywlb) {
        String result = "";
        for(GxEnum shijgxEnum : GxEnum.values()) {
            if(StringUtils.equals(shijgxEnum.cxywlb,cxywlb)) {
                result = shijgxEnum.getBeanName();
                return result;
            }
        }
        return result;
    }

    public static String getInterfaceUrl(String cxywlb) {
        String result = "";
        for(GxEnum shijgxEnum : GxEnum.values()) {
            if(StringUtils.equals(shijgxEnum.cxywlb,cxywlb)) {
                result = shijgxEnum.getInterfaceName();
                return result;
            }
        }
        return result;
    }





}
