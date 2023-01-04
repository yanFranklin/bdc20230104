package cn.gtmap.realestate.common.core.enums;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/12/30
 * @description 不动产住房信息查询来源枚举类型定义
 */
public enum BdcZfxxCxlyEnum {
    DJXT("1", "不动产登记系统"),
    ZZCXJ("2", "自助查询机"),
    HLWJ("3", "互联网+ "),
    ZZJHJ("4", "自助交互机 "),
    HFBIGDATACOMPANY("5", "合肥大数据局 ");

    /**
     * 编码
     */
    private String code;

    /**
     * 查询来源
     */
    private String cxly;


    BdcZfxxCxlyEnum(String code, String cxly){
        this.code = code;
        this.cxly = cxly;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   code 编码
     * @return  {String} 查询来源
     * @description 根据编码获取查询来源名称
     */
    public static String getCxly(String code){
        for (BdcZfxxCxlyEnum bdcZslxEnum : BdcZfxxCxlyEnum.values()){
            if(bdcZslxEnum.getCode().equals(code)){
                return bdcZslxEnum.getCxly();
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

    public String getCxly() {
        return cxly;
    }

    public void setCxly(String cxly) {
        this.cxly = cxly;
    }
}
