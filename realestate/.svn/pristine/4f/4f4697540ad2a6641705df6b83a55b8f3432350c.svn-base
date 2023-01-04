package cn.gtmap.realestate.common.core.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/16
 * @description 共享接口请求地址与接口beanName枚举值
 */
public enum GxJkEnum {

    //市公安局_台湾人口信息查询接口
    TWJMCX("twjmcx", "hf_twjmcx"),

    //建设工程竣工验收信息查询_税收风管
    JSGCJG("jsgcjg","hf_jsgcjgcx"),

    //市公安局_常住人口信息查询接口
    CZRKCX("czrkcx", "hf_czrkcx"),

    //新人口库-常住人口信息
    CZRKXX("czrkxx", "ha_czrkxx"),

    //新人口库-常住个人信息
    CZRKGRXX("czrkgrxx", "ha_czrkgrxx"),

    //新人口库-户号查询家庭信息
    CZRKXXHH("czrkxxhh", "ha_czrkxxhh");




    // 查询业务类别
    private String cxywlb;

    // 服务名
    private String beanName;

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

    GxJkEnum(String cxywlb, String beanName){
        this.cxywlb = cxywlb;
        this.beanName = beanName;
    }

    public static String getBeanName(String cxywlb) {
        String result = "";
        for(GxJkEnum gxJkEnum : GxJkEnum.values()) {
            if(StringUtils.equals(gxJkEnum.cxywlb,cxywlb)) {
                result = gxJkEnum.getBeanName();
                return result;
            }
        }
        return result;
    }
}
