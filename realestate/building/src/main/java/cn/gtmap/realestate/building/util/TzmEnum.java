package cn.gtmap.realestate.building.util;

import cn.gtmap.realestate.common.core.domain.building.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-15
 * @description 特征码、处理地籍信息Service 和相关实体的 对照关系枚举
 */
public enum TzmEnum {

    /**
     * 土地所有权宗地
     */
    AW("AW", new String[]{Constants.DJXX_SERVICE_QSZD}, QszdDjdcbDO.class),

    /**
     * 建设用地使用权宗地（地表）
     */
    BW("BW", new String[]{Constants.DJXX_SERVICE_ZD}, ZdDjdcbDO.class),
    BF("BF", new String[]{Constants.DJXX_SERVICE_ZD}, ZdDjdcbDO.class),
    BQ("BQ", new String[]{Constants.DJXX_SERVICE_ZD}, ZdDjdcbDO.class),

    /**
     * 宅基地使用权宗地
     */
    CW("CW", new String[]{Constants.DJXX_SERVICE_ZD}, ZdDjdcbDO.class, ZdZjdxxDO.class),
    CF("CF", new String[]{Constants.DJXX_SERVICE_ZD}, ZdDjdcbDO.class, ZdZjdxxDO.class),
    CQ("CQ", new String[]{Constants.DJXX_SERVICE_ZD}, ZdDjdcbDO.class, ZdZjdxxDO.class),

    /**
     * 土地承包经营权宗地（耕地），经营权地块(耕地)
     */
    DW("DW", new String[]{Constants.DJXX_SERVICE_NYD,Constants.DJXX_SERVICE_JYQDK}, NydDjdcbDO.class, CbzdDcbDO.class),
    /**
     * 土地承包经营权宗地（水域滩涂养殖）
     */
    YW("YW", new String[]{Constants.DJXX_SERVICE_NYD}, NydDjdcbDO.class, CbzdDcbDO.class),

    /**
     * 土地承包经营权宗地（林地），经营权地块(林地)
     */
    EL("EL", new String[]{Constants.DJXX_SERVICE_NYD, Constants.DJXX_SERVICE_JYQDK}, NydDjdcbDO.class, CbzdDcbDO.class, LqDcbDO.class),
    EQ("EQ", new String[]{Constants.DJXX_SERVICE_NYD}, NydDjdcbDO.class, CbzdDcbDO.class),

    /**
     * 土地承包经营权宗地（土地），经营权地块(土地)
     */
    EW("EW", new String[]{Constants.DJXX_SERVICE_NYD,Constants.DJXX_SERVICE_JYQDK}, NydDjdcbDO.class, CbzdDcbDO.class),

    /**
     * 土地承包经营权宗地（草地），经营权地块(草地)
     */
    FW("FW", new String[]{Constants.DJXX_SERVICE_NYD,Constants.DJXX_SERVICE_JYQDK}, NydDjdcbDO.class, CbzdDcbDO.class),


    /**
     * 无居民海岛使用权海岛
     */
    GW("GW", new String[]{Constants.DJXX_SERVICE_ZH}, ZhDjdcbDO.class),
    GF("GF", new String[]{Constants.DJXX_SERVICE_ZH}, ZhDjdcbDO.class),
    GL("GL", new String[]{Constants.DJXX_SERVICE_ZH}, ZhDjdcbDO.class),
    GQ("GQ", new String[]{Constants.DJXX_SERVICE_ZH}, ZhDjdcbDO.class),

    /**
     * 海域使用权宗海
     */
    HW("HW", new String[]{Constants.DJXX_SERVICE_ZH}, ZhDjdcbDO.class),
    HF("HF", new String[]{Constants.DJXX_SERVICE_ZH}, ZhDjdcbDO.class),
    HL("HL", new String[]{Constants.DJXX_SERVICE_ZH}, ZhDjdcbDO.class),
    HQ("HQ", new String[]{Constants.DJXX_SERVICE_ZH}, ZhDjdcbDO.class),

    /**
     * 林地使用权宗地（承包经营权以外的）
     */
    LL("LL", new String[]{Constants.DJXX_SERVICE_NYD}, NydDjdcbDO.class, LqDcbDO.class),
    LQ("LQ", new String[]{Constants.DJXX_SERVICE_NYD}, NydDjdcbDO.class),
    LW("LW", new String[]{Constants.DJXX_SERVICE_NYD}, NydDjdcbDO.class),

    /**
     * 农用地使用权宗地（承包经营以外的、非林地）
     */
    NW("NW", new String[]{Constants.DJXX_SERVICE_NYD}, NydDjdcbDO.class),

    /**
     * 建设用地使用权宗地（地上）
     */
    SW("SW", new String[]{Constants.DJXX_SERVICE_ZD}, ZdDjdcbDO.class),
    SF("SF", new String[]{Constants.DJXX_SERVICE_ZD}, ZdDjdcbDO.class),
    SQ("SQ", new String[]{Constants.DJXX_SERVICE_ZD}, ZdDjdcbDO.class),

    /**
     * 建设用地使用权宗地（地下）
     */
    XW("XW", new String[]{Constants.DJXX_SERVICE_ZD}, ZdDjdcbDO.class),
    XF("XF", new String[]{Constants.DJXX_SERVICE_ZD}, ZdDjdcbDO.class),
    XQ("XQ", new String[]{Constants.DJXX_SERVICE_ZD}, ZdDjdcbDO.class);


    /**
     * 特征码
     */
    private String tzm;

    /**
     * DO类数组
     */
    private Class[] doArr;

    /**
     * 处理地籍信息的Bean名称
     */
    private String[] djxxServiceBeanName;

    TzmEnum(String tzm,String[] djxxServiceBeanName,Class... doArr){
        this.tzm = tzm;
        this.djxxServiceBeanName = djxxServiceBeanName;
        this.doArr = doArr;
    }

    public String getTzm() {
        return tzm;
    }

    public Class[] getDoArr() {
        return doArr;
    }

    public String[] getDjxxServiceBeanName() {
        return djxxServiceBeanName;
    }

}
