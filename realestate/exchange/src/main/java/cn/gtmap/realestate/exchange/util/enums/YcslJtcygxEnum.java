package cn.gtmap.realestate.exchange.util.enums;

/**
 * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
 * @version 1.0 2019-5-22
 * @description 一窗受理家庭成员关系 枚举
 */
public enum YcslJtcygxEnum {

    /**
    *  未成年子女
    */
    YCSL_JTCY_WCNZNV("未成年子女","未成年子女"),

    /**
    *  未成年兄妹
    */
    YCSL_JTCY_WCNXM("未成年兄妹","未成年兄妹"),
    /**
     *  父母
     */
    YCSL_JTCY_FM("父母","父母"),

    /**
    * 夫妻
    */
    YCSL_JTCY_FQ("夫妻","配偶");


    /**
     * 家庭成员关系代码
     */
    private String dm;

    /**
     * 家庭成员关系名称
     */
    private String mc;

    YcslJtcygxEnum(String dm, String mc) {
        this.dm = dm;
        this.mc = mc;
    }

    public String getDm() {
        return dm;
    }

    public String getMc() {
        return mc;
    }
}
