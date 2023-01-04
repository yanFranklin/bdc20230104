package cn.gtmap.realestate.common.core.enums;

/**
 * 不动产操作日志类型
 *
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0
 */
public enum ShijiInterfaceEnum {

    PROVICE_AGENCY_INTERFACE_RESPONSE_SUCCESS("0", "success","市级代理省份接口统一出参:成功","responseDeal","status","msg"),
    PROVICE_AGENCY_INTERFACE_RESPONSE_ERROR_WITH_PARAM("-1", "输入参数不正确","市级代理省份接口统一出参:失败","responseDeal","status","msg"),
    PROVICE_AGENCY_INTERFACE_RESPONSE_UNKONW_ERROR("-2", "其他未定义异常","市级代理省份接口统一出参:失败","responseDeal","status","msg"),
    PROVICE_AGENCY_INTERFACE_RESPONSE_ERROR("-3", "市级接口code:-3却未有异常信息返回","市级代理省份接口统一出参:失败,市级接口有返回msg直接返回，无则返回当前msg","responseDeal","status","msg"),
    CITY_INTERFACE_RESPONSE_SUCCESS("200", "success","市级接口统一出参","responseDeal","code","message"),
    CITY_INTERFACE_RESPONSE_ERROR("400", "市级接口报错却未有异常信息返回","市级接口统一返回:失败,市级接口有返回msg直接返回，无则返回当前msg","responseDeal","code","message");

    ShijiInterfaceEnum(String code, String msg, String desc, String typeName, String codeName, String msgName) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
        this.typeName = typeName;
        this.codeName = codeName;
        this.msgName = msgName;
    }

    private String code;
    private String msg;
    private String desc;
    private String typeName;
    private String codeName;
    private String msgName;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getMsgName() {
        return msgName;
    }

    public void setMsgName(String msgName) {
        this.msgName = msgName;
    }
}
