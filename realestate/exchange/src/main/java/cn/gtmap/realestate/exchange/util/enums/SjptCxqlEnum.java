package cn.gtmap.realestate.exchange.util.enums;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.domain.sjpt.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-26
 * @description
 */
public enum SjptCxqlEnum {

    YG(GxPeYg.class,BdcYgDO.class,new String[]{"96"},true,false),

    YY(GxPeYy.class,BdcYyDO.class,new String[]{"97"},false,false),

    NYDSYQ(GxPeNydsyq.class, BdcTdcbnydsyqDO.class,new String[]{"9","13","14","23"}),

    LQ(GxPeLq.class, BdcLqDO.class,new String[]{"10","11","12"}),

    JSYDSYQ(GxPeJsydsyq.class, BdcJsydsyqDO.class,new String[]{"3","5","7"}),

    HYSYQ(GxPeHysyq.class, BdcHysyqDO.class,new String[]{"15","17"}),

    GJZWSYQ(GxPeGjzwsyq.class, BdcGjzwsyqDO.class,new String[]{"16","18","24","25","26","27","28"}),

    FDCQ(GxPeFdcq.class, BdcFdcqDO.class,new String[]{"4","6","8"}),

    TDSYQ(GxPeTdsyq.class, BdcTdsyqDO.class,new String[]{"1","2"}),

    DYAQ(GxPeDyaq.class, BdcDyaqDO.class,new String[]{CommonConstantUtils.QLLX_DYAQ_DM.toString()},false,false),

    CF(GxPeCf.class, BdcCfDO.class,new String[]{"98"},false,false);

    // 共享权利实体类
    private Class gxQlClass;

    // 不动产权利实体
    private Class bdcQlClass;

    // 权利类型字典项 数组
    private String[] qllxArr;

    // 是否查询共有人 默认查询
    private boolean needGyr;

    // 是否查询限制权利 默认查询
    private boolean needXzql;

    SjptCxqlEnum(Class gxQlClass,Class bdcQlClass,String[] qllxArr){
        this.gxQlClass = gxQlClass;
        this.bdcQlClass = bdcQlClass;
        this.qllxArr = qllxArr;
        this.needGyr = true;
        this.needXzql = true;
    }

    SjptCxqlEnum(Class gxQlClass,Class bdcQlClass,String[] qllxArr,boolean gyr,boolean xzql){
        this.gxQlClass = gxQlClass;
        this.bdcQlClass = bdcQlClass;
        this.qllxArr = qllxArr;
        this.needGyr = gyr;
        this.needXzql = xzql;
    }

    public Class getBdcQlClass() {
        return bdcQlClass;
    }

    public void setBdcQlClass(Class bdcQlClass) {
        this.bdcQlClass = bdcQlClass;
    }

    public Class getGxQlClass() {
        return gxQlClass;
    }

    public void setGxQlClass(Class gxQlClass) {
        this.gxQlClass = gxQlClass;
    }

    public String[] getQllxArr() {
        return qllxArr;
    }

    public void setQllxArr(String[] qllxArr) {
        this.qllxArr = qllxArr;
    }

    public boolean isNeedGyr() {
        return needGyr;
    }

    public void setNeedGyr(boolean needGyr) {
        this.needGyr = needGyr;
    }

    public boolean isNeedXzql() {
        return needXzql;
    }

    public void setNeedXzql(boolean needXzql) {
        this.needXzql = needXzql;
    }
}
