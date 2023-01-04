package cn.gtmap.realestate.building.util;

import cn.gtmap.realestate.common.core.domain.building.*;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-11-15
 * @description 特征码和相关实体的 对照关系枚举
 */
public enum DjdcbEnum {

    /**
     * 土地所有权宗地
     */
    AW("AW",Constants.JZBSB_TABLE_NAME_QSZD, QszdDjdcbDO.class, QszdQlrDO.class),

    /**
     * 建设用地使用权宗地（地表）
     */
    BW("BW", Constants.JZBSB_TABLE_NAME_ZD, ZdDjdcbDO.class, ZdQlrDO.class),
    BF("BF", Constants.JZBSB_TABLE_NAME_ZD, ZdDjdcbDO.class, ZdQlrDO.class),
    BQ("BQ", Constants.JZBSB_TABLE_NAME_ZD, ZdDjdcbDO.class, ZdQlrDO.class),

    /**
     * 宅基地使用权宗地
     */
    CW("CW",  Constants.JZBSB_TABLE_NAME_ZD, ZdDjdcbDO.class, ZdQlrDO.class),
    CF("CF",  Constants.JZBSB_TABLE_NAME_ZD, ZdDjdcbDO.class, ZdQlrDO.class),
    CQ("CQ",  Constants.JZBSB_TABLE_NAME_ZD, ZdDjdcbDO.class, ZdQlrDO.class),

    /**
     * 土地承包经营权宗地（耕地）
     */
    DW("DW",Constants.JZBSB_TABLE_NAME_NYD,NydDjdcbDO.class,NydQlrDO.class),

    /**
     * 土地承包经营权宗地（林地）
     */
    EL("EL", Constants.JZBSB_TABLE_NAME_NYD,NydDjdcbDO.class,NydQlrDO.class),
    EQ("EQ", Constants.JZBSB_TABLE_NAME_NYD,NydDjdcbDO.class,NydQlrDO.class),
    EW("EW", Constants.JZBSB_TABLE_NAME_NYD,NydDjdcbDO.class,NydQlrDO.class),

    /**
     * 土地承包经营权宗地（草地）
     */
    FW("FW", Constants.JZBSB_TABLE_NAME_NYD,NydDjdcbDO.class,NydQlrDO.class),


    /**
     * 无居民海岛使用权海岛
     */
    GW("GW",  Constants.JZBSB_TABLE_NAME_ZH,ZhDjdcbDO.class,ZhQlrDO.class),
    GF("GF",  Constants.JZBSB_TABLE_NAME_ZH,ZhDjdcbDO.class,ZhQlrDO.class),
    GL("GL",  Constants.JZBSB_TABLE_NAME_ZH,ZhDjdcbDO.class,ZhQlrDO.class),
    GQ("GQ",  Constants.JZBSB_TABLE_NAME_ZH,ZhDjdcbDO.class,ZhQlrDO.class),

    /**
     * 海域使用权宗海
     */
    HW("HW",  Constants.JZBSB_TABLE_NAME_ZH,ZhDjdcbDO.class,ZhQlrDO.class),
    HF("HF",  Constants.JZBSB_TABLE_NAME_ZH,ZhDjdcbDO.class,ZhQlrDO.class),
    HL("HL",  Constants.JZBSB_TABLE_NAME_ZH,ZhDjdcbDO.class,ZhQlrDO.class),
    HQ("HQ",  Constants.JZBSB_TABLE_NAME_ZH,ZhDjdcbDO.class,ZhQlrDO.class),

    /**
     * 林地使用权宗地（承包经营权以外的）
     */
    LL("LL", Constants.JZBSB_TABLE_NAME_NYD,NydDjdcbDO.class,NydQlrDO.class),
    LQ("LQ", Constants.JZBSB_TABLE_NAME_NYD,NydDjdcbDO.class,NydQlrDO.class),
    LW("LW", Constants.JZBSB_TABLE_NAME_NYD,NydDjdcbDO.class,NydQlrDO.class),

    /**
     * 农用地使用权宗地（承包经营以外的、非林地）
     */
    NW("NW", Constants.JZBSB_TABLE_NAME_NYD,NydDjdcbDO.class,NydQlrDO.class),

    /**
     * 建设用地使用权宗地（地上）
     */
    SW("SW",  Constants.JZBSB_TABLE_NAME_ZH, ZhDjdcbDO.class,ZhQlrDO.class),
    SF("SF",  Constants.JZBSB_TABLE_NAME_ZH,ZhDjdcbDO.class,ZhQlrDO.class),
    SQ("SQ", Constants.JZBSB_TABLE_NAME_ZH,ZhDjdcbDO.class,ZhQlrDO.class),

    /**
     * 建设用地使用权宗地（地下）
     */
    XW("XW",  Constants.JZBSB_TABLE_NAME_ZD, ZdDjdcbDO.class, ZdQlrDO.class),
    XF("XF",  Constants.JZBSB_TABLE_NAME_ZD, ZdDjdcbDO.class, ZdQlrDO.class),
    XQ("XQ",  Constants.JZBSB_TABLE_NAME_ZD, ZdDjdcbDO.class, ZdQlrDO.class);

    /**
     * 特征码
     */
    private String tzm;

    /**
     * 界址表名字
     */
    private String tableName;

    /**
     * 宗地表名
     */
    private Class zdClass;
    /**
     * 宗地权利人表名
     */
    private Class zdQlrClass;



    DjdcbEnum(String tzm, String tableName, Class zdClass, Class zdQlrClass){
        this.tzm = tzm;
        this.tableName = tableName;
        this.zdClass = zdClass;
        this.zdQlrClass = zdQlrClass;
    }

    public String getTzm() {
        return tzm;
    }

    public String getTableName() {
        return tableName;
    }

    public Class getZdClass() {
        return zdClass;
    }

    public Class getZdQlrClass() {
        return zdQlrClass;
    }
}
