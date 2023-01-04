package cn.gtmap.realestate.common.core.enums;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2019/1/30
 * @description 不动产类型对应特征码枚举类
 */
public enum BdclxEnum {
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 土地
     */
    TD("", "W", "", 1),
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 土地和房屋
     */
    TDFW("B,C,S,X,W,Y", "F", "", 1),
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 林地和林木
     */
    LDLM("E,L,K", "L", "", 1),
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 土地和在建建筑物
     */
    ZJJZW("B,C,S,X,W,Y", "F", "ychs", 1),
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 海域
     */
    HY("H", "W", "", 2),
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 海域和构筑物
     */
    HYGZW("H,G", "F", "", 3),
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 其他
     */
    QT("", "Q", "", 1),
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 土地和构筑物
     */
    TDGZW("B,C,S,X,W,Y", "F", "", 3),
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 土地和其他
     */
    TDQT("", "Q", "", 1),
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 海域和房屋
     */
    HYFW("H,G", "F", "", 1),
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 海域和森林林木
     */
    HYSLLM("H,G", "L", "", 1),
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 海域和其他
     */
    HYQT("H,G", "Q", "", 1),
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 无居民海岛
     */
    WJMHD("G", "W", "", 2);

    private String zdtzm;
    private String dzwtzm;
    private String fwlx;
    private Integer dyhcxlx;


    BdclxEnum(String zdtzm, String dzwtzm, String fwlx, Integer dyhcxlx) {
        this.zdtzm = zdtzm;
        this.dzwtzm = dzwtzm;
        this.fwlx = fwlx;
        this.dyhcxlx = dyhcxlx;
    }


    public String getZdtzm() {
        return zdtzm;
    }

    public String getDzwtzm() {
        return dzwtzm;
    }

    public String getFwlx() {
        return fwlx;
    }

    public Integer getDyhcxlx() {
        return dyhcxlx;
    }
}
