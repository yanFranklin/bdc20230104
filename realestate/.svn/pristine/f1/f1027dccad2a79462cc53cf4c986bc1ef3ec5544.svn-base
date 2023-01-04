package cn.gtmap.realestate.common.core.enums.natural;

import cn.gtmap.realestate.common.core.domain.natural.*;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 自然资源自然状况类型枚举类
 */
public enum ZrzyZrzklxEnum {

    /**
     * 水流
     */
    SZY("SZY", "水流",ZrzySzyDO.class),

    /**
     * 湿地
     */
    SD("SD", "湿地",ZrzySdDO.class),

    /**
     * 森林
     */
    SL("SL", "森林",ZrzySlDO.class),

    /**
     * 草原
     */
    CY("CY", "草原", ZrzyCyDO.class),

    /**
     * 荒地
     */
    HD("HD", "荒地",ZrzyHdDO.class),

    /**
     * 海域
     */
    HY("HY", "海域",ZrzyHyDO.class),

    /**
     * 无居民海岛
     */
    WJMHD("WJMHD", "无居民海岛",ZrzyWjmhdDO.class),

    /**
     * 探明储量矿产资源
     */
    TMCLKCZY("TMCLKCZY", "探明储量矿产资源",ZrzyTmclkczyDO.class);

    /**
     * 自然状况类型代码
     */
    private String code;
    /**
     * 自然状况类型名称
     */
    private String name;

    /**
     * 自然状况实体
     */
    private Class<?> tableClass;

    ZrzyZrzklxEnum(String code, String name, Class<?> tableClass) {
        this.code = code;
        this.name = name;
        this.tableClass = tableClass;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getTableClass() {
        return tableClass;
    }

    public void setTableClass(Class<?> tableClass) {
        this.tableClass = tableClass;
    }

    /**
      * @param code 自然状况代码值
      * @return 自然状况类
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据自然状况代码值获取实体
      */
    public static Class<?> getZrzkClassByCode(String code) {
        Class<?> zrzkClass = null;
        if (StringUtils.isNotBlank(code)) {
            for (ZrzyZrzklxEnum zrzyZrzklxEnum : ZrzyZrzklxEnum.values()) {
                if (StringUtils.equals(code, zrzyZrzklxEnum.getCode())) {
                    zrzkClass = zrzyZrzklxEnum.getTableClass();
                    break;
                }
            }
        }
        return zrzkClass;
    }
}
