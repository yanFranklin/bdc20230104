package cn.gtmap.realestate.common.core.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021/07/05
 * @description 合肥一窗通办产权来源常量
 */
public enum BdcYctbCqlyEnum {

    //商品房买卖
    SPFMM("41", "商品房买卖"),

    //建设工程竣工验收信息查询_税收风管
    CLFMM("42","存量房买卖");


    // 查询业务类别
    private String code;

    // 服务名
    private String msg;

    BdcYctbCqlyEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getBeanName(String code) {
        String result = "";
        for(BdcYctbCqlyEnum bdcYctbCqlyEnum : BdcYctbCqlyEnum.values()) {
            if(StringUtils.equals(bdcYctbCqlyEnum.code,code)) {
                result = bdcYctbCqlyEnum.getMsg();
                return result;
            }
        }
        return result;
    }
}
