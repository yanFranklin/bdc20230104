package cn.gtmap.realestate.exchange.util.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-23
 * @description 省级平台操作类型与接口枚举值
 */
public enum SjptRzEnum {

    /**
     *
     */
    SSCX("sjpt_sscx","11"),

    // 定时任务获取查询请求
    CXSQ("sjpt_cxsq","1",null,"data"),

    // 查询请求响应
    CXFK("sjpt_cxsq_fk","7","cxsqdhs",null),

    CXJG("sjpt_cxjg","12","data",null),

    // 查询文书
    CXWS("sjpt_cxws","4"),

    // 婚姻信息
    HYXX("xgbmcx_hyxx","5",true),

    // 社会关系
    SHGX("xgbmcx_shgx","10",true),

    // 居民身份信息
    JMSF("xgbmcx_jmsf","16"),

    // 居民身份信息查询申请反馈
    JMSFSQFK("xgbmcx_jmsfsqfk","17"),

    // 企业基本信息
    QYXX("xgbmcx_qyxx", "13"),

    // 户籍信息查询申请
    HJXXCXSQ("xgbmcx_hjxxcxsq", "18"),

    // 户籍信息查询反馈
    HJXXCXFK("xgbmcx_hjxxcxfk", "19"),

    SJPT_TOKEN_REQ("sjpt_token", "14"),

    //江苏省级平台-民政部-地名信息查询接口说明
    MZB_DMXXCX("xgbmcx_dmxxcx", "21"),

    //社会组织同意信息查询
    MZB_SHZZTYXXCX("xgbmcx_shzztyxxcx", "22"),
    //出生医学证明
    MZB_CSYXZM("xgbmcx_csyxzm", "23"),
    //死亡医学证明
    MZB_SWYXZM("xgbmcx_swyxzm", "24"),

    //基金会法人登记证书查询接口
    MZB_JJFRZM("xgbmcx_jjhfrdjzs", "25"),

    //3.4 民政部-民办非企业单位登记证书查询接口
    MZB_MBFQYDJZS("xgbmcx_mbfqydjzs", "26"),
    //3.6 民政部-婚姻登记信息核验（个人）接口
    MZB_HYXXHYGR("xgbmcx_hyxxhygr", "28"),

    //3.7 民政部-婚姻登记信息核验（双方）接口
    MZB_HYXXHYSF("xgbmcx_hyxxhysf", "29"),

    //3.8 市场监管总局-企业基本信息查询接口
    MZB_QYJBXX("xgbmcx_qyjbxx", "30"),

    //3.10 中编办-党群机关信息查询接口
    MZB_DQJG("xgbmcx_dqjg", "31"),

    //3.11 中编办-事业单位信息查询接口
    MZB_SYDW("xgbmcx_sydw", "32"),

    //3.5 民政部-社会团体法人登记证书查询接口
    MZB_SHTTFRDJZS("xgbmcx_shttfrdjzs", "27"),

    //江苏省级平台-公安部-地名信息查询接口说明
    GAB_SFHC("xgbmcx_gasfhc", "33"),

    // 3.12 人口基准信息
    RKKJZXX("xgbmcx_rkkjzxxcx","34",true),

    // 3.13 人口身份核查
    RKKSFHC("xgbmcx_rkksfhc","35",true),

    ;


    // 服务名
    private String beanName;

    // 操作类型
    private String czlx;

    // 响应成功码
    private String successCode;

    private String errorMsgKey;

    // 用于日志表中 HQQQS（获取请求数）字段 请求参数中的数组的key名
    private String qqsListNameInRequest;

    // 用于日志表中 HQQQS（获取请求数）字段 相应结果中的数组的key名
    private String qqsListNameInResponse;

    // 是否保存 GX_PE_CXJG 表
    private boolean saveCxjg;

    // 不需要记录HQQQS
    SjptRzEnum(String beanName, String czlx){
        this.beanName = beanName;
        this.czlx = czlx;
        this.successCode = "0000";
        this.errorMsgKey = "msg";
        this.saveCxjg = false;
    }

    SjptRzEnum(String beanName,String czlx,boolean saveCxjg){
        this.beanName = beanName;
        this.czlx = czlx;
        this.successCode = "0000";
        this.errorMsgKey = "msg";
        this.saveCxjg = saveCxjg;
    }

    // 需要记录 HQQQS
    SjptRzEnum(String beanName, String czlx,String qqsListNameInRequest,String qqsListNameInResponse){
        this.beanName = beanName;
        this.czlx = czlx;
        this.successCode = "0000";
        this.errorMsgKey = "msg";
        this.qqsListNameInRequest = qqsListNameInRequest;
        this.qqsListNameInResponse = qqsListNameInResponse;
        this.saveCxjg = false;
    }

    public static SjptRzEnum getEnumByBeanName(String beanName){
        SjptRzEnum[] enums = SjptRzEnum.values();
        for(SjptRzEnum temp : enums){
            if(StringUtils.equals(temp.getBeanName(),beanName)){
                return temp;
            }
        }
        return null;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param beanName
     * @return java.lang.String
     * @description  根据bean名称 获取 操作类型
     */
    public static String getCzlxByBeanName(String beanName){
        SjptRzEnum[] enums = SjptRzEnum.values();
        for(SjptRzEnum temp : enums){
            if(StringUtils.equals(temp.getBeanName(),beanName)){
                return temp.getCzlx();
            }
        }
        return null;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getCzlx() {
        return czlx;
    }

    public void setCzlx(String czlx) {
        this.czlx = czlx;
    }

    public String getSuccessCode() {
        return successCode;
    }

    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }


    public String getErrorMsgKey() {
        return errorMsgKey;
    }

    public void setErrorMsgKey(String errorMsgKey) {
        this.errorMsgKey = errorMsgKey;
    }

    public boolean isSaveCxjg() {
        return saveCxjg;
    }

    public void setSaveCxjg(boolean saveCxjg) {
        this.saveCxjg = saveCxjg;
    }

    public String getQqsListNameInRequest() {
        return qqsListNameInRequest;
    }

    public void setQqsListNameInRequest(String qqsListNameInRequest) {
        this.qqsListNameInRequest = qqsListNameInRequest;
    }

    public String getQqsListNameInResponse() {
        return qqsListNameInResponse;
    }

    public void setQqsListNameInResponse(String qqsListNameInResponse) {
        this.qqsListNameInResponse = qqsListNameInResponse;
    }
}
