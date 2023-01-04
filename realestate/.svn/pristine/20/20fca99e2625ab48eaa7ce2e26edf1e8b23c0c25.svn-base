package cn.gtmap.realestate.building.util;

import cn.gtmap.realestate.building.config.lpb.LpbConfig;
import cn.gtmap.realestate.building.core.decorator.status.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-10-31
 * @description 户室状态枚举
 */
public enum FwHsStatusEnum {

    /**
     * 登记状态
     */
    DJ("DJ",DjStatus.class),

    /**
     * 登记状态
     */
    ZX("ZX",ZxStatus.class),

    /**
     * 草签
     */
    CQ("CQ", CqStatus.class),


    /**
     * 在建工程抵押
     */
    ZJGCDY("ZJGCDY", ZjgcdyStatus.class),

    /**
     * 预告
     */
    YG("YG", YgStatus.class),

    /**
     * 预抵押
     */
    YDYA("YDYA", YdyaStatus.class),

    /**
     * 抵押
     */
    DYA("DYA", DyaStatus.class),

    /**
     * 预查封
     */
    YCF("YCF", YcfStatus.class),

    /**
     * 查封
     */
    CF("CF", CfStatus.class),

    /**
     * 异议
     */
    YY("YY", YyStatus.class),

    /**
     * 地役
     */
    DYI("DYI", DyiStatus.class),

    /**
     * 锁定
     */
    SD("SD", SdStatus.class),

    /**
     * 可售
     */
    KS("KS", KsStatus.class),


    /**
     * 预售
     */
    YS("YS", YsStatus.class),


    /**
     * 新建商品房可售
     */
    XJSPFKS("XJSPFKS", XjspfksStatus.class),

    /**
     * 新建商品房预售
     */
    XJSPFYS("XJSPFYS", XjspfysStatus.class),

    /**
     * 存量房可售
     */
    CLFKS("CLFKS", ClfksStatus.class),
    /**
     * 生成证书
     */
    ZS("ZS", ZsStatus.class),
    /**
     * 生成证明书
     */
    ZM("ZM", ZmStatus.class),
    /**
     * 登簿不发证
     */
    BFZ("BFZ", BfzStatus.class),

    /**
     * 存量房预售
     */
    CLFYS("CLFYS", ClfysStatus.class),

    /*
    * 是否备案
    * */
    BA("BA",BaStatus.class),

    /*
     * 居住权
     * */
    JZQ("JZQ",JzqStatus.class);


    /**
     * 名称
     */
    private String name;

    /**
     * 处理类
     */
    private Class statusClass;

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LpbConfig.class);

    FwHsStatusEnum(String name, Class statusClass) {
        this.name = name;
        this.statusClass = statusClass;
    }

    /**
     * @param code
     * @param name
     * @return cn.gtmap.realestate.building.utils.FwHsStatusEnum
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据名称获取枚举
     */
    public static FwHsStatusEnum getEnum(String code, String name) {
        FwHsStatusEnum statusEnum = null;
        try {
            statusEnum = FwHsStatusEnum.valueOf(name);
        } catch (IllegalArgumentException e) {
            LOGGER.error("不存在状态实现:{}", name, e);
            // 直接把配置中的 状态 拿掉
            if(StringUtils.isNotBlank(code)){
                LpbConfig.removeErrorStatus(code, name);
            }
        }
        return statusEnum;
    }

    public String getName() {
        return name;
    }


    public Class getStatusClass() {
        return statusClass;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.util.List<java.lang.String>
     * @description 获取所有的状态
     */
    public static List<String> getAllStatus() {
        List<String> statusList = new ArrayList<>();
        FwHsStatusEnum[] arr = FwHsStatusEnum.values();
        for(FwHsStatusEnum en : arr){
            statusList.add(en.getName());
        }
        return statusList;
    }
}
