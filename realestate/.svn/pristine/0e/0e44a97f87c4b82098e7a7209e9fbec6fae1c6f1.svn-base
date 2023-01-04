package cn.gtmap.realestate.common.core.enums;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/3/10
 * @description 不动产打印数据来源枚举类定义
 */
public enum BdcDySjlyEnum {
    SQL("1", "SQL"),
    FW("2", "服务");

    private String code;

    private String sjly;


    BdcDySjlyEnum(String code, String sjly){
        this.code = code;
        this.sjly = sjly;
    }


    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   code 数据来源标识
     * @return  {String} 数据来源名称
     * @description 根据标识获取对应的数据来源
     */
    public static String getSjly(String code){
        for (BdcDySjlyEnum sjllyEnum : BdcDySjlyEnum.values()){
            if(sjllyEnum.getCode().equals(code)){
                return sjllyEnum.getSjly();
            }
        }

        return null;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }
}
