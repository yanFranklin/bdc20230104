package cn.gtmap.realestate.common.core.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/6/22
 * @description 上报相关预警类型枚举
 */
public enum AccessWarningEnum {

    STATUS_0("0", "已登簿未上报"),
    STATUS_1("1", "上报配置异常"),
    STATUS_2("2", "报文组织异常"),
    STATUS_3("3", "前置机网络异常（SFTP或FTP异常）"),
    STATUS_4("4", "报文XSD校验异常"),
    STATUS_5("5", "上报综合日志"),
    STATUS_6("6", "登簿日志上报预警"),
    STATUS_7("7", "登簿日志上报失败"),
    STATUS_8("8", "上报分数预警"),
    STATUS_9("9", "接入量小于登簿量"),
    STATUS_10("10", "接入量大于登簿量"),
    STATUS_11("11", "前一天登簿日志未上报"),
    STATUS_12("12", "接入表最后保存接入状态失败！"),
    STATUS_13("13", "上报项目数据查询到多条权利数据"),
    STATUS_14("14", "登簿日志上报失败执行补偿上报"),
    STATUS_15("15", "查询申请上报超时！"),
    STATUS_16("16", "查询申请未处理！"),
    STATUS_17("17", "查询申请上报超时总条数提醒！"),
    STATUS_18("18", "查询申请已查询未上报总条数提醒！"),
    STATUS_19("19", "查询申请未处理总条数提醒！"),
    STATUS_20("20", "查询申请部分查询总条数提醒！");

    AccessWarningEnum(String yjlx, String mc) {
        this.yjlx = yjlx;
        this.mc = mc;
    }

    public static String getMcByYjlx(String yjlx) {
        if (StringUtils.isBlank(yjlx)) {
            return "";
        }

        for (AccessWarningEnum accessWarningEnum : AccessWarningEnum.values()) {
            if (StringUtils.equals(accessWarningEnum.getYjlx(), yjlx)) {
                return accessWarningEnum.getMc();
            }
        }
        return "";
    }

    /**
     * 编码
     */
    private String mc;

    /**
     * 预警类型
     */
    private String yjlx;

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getYjlx() {
        return yjlx;
    }

    public void setYjlx(String yjlx) {
        this.yjlx = yjlx;
    }
}
