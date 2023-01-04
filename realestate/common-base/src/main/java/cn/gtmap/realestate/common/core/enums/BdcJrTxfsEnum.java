package cn.gtmap.realestate.common.core.enums;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/6/29
 * @description 上报预警提醒方式
 */
public enum  BdcJrTxfsEnum {
    WXTX(0, "无需提醒"),
    XTXX(1, "系统消息"),
    DX(2, "短信"),
    XXTXDX(3, "系统消息和短信");

    /**
     * 提醒方式
     */
    private Integer txfs;

    /**
     * 提醒方式名称
     */
    private String txfsmc;

    BdcJrTxfsEnum(Integer txfs, String txfsmc) {
        this.txfs = txfs;
        this.txfsmc = txfsmc;
    }

    public Integer getTxfs() {
        return txfs;
    }

    public void setTxfs(Integer txfs) {
        this.txfs = txfs;
    }

    public String getTxfsmc() {
        return txfsmc;
    }

    public void setTxfsmc(String txfsmc) {
        this.txfsmc = txfsmc;
    }
}
