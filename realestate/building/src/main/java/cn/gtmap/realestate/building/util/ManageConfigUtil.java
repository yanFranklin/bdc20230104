package cn.gtmap.realestate.building.util;

import cn.gtmap.realestate.building.config.calculated.FttdmjConfig;
import cn.gtmap.realestate.building.config.calculated.LjzJzmjConfig;
import cn.gtmap.realestate.building.config.manage.HsHbConfig;
import cn.gtmap.realestate.building.config.manage.ZlsxConfig;
import cn.gtmap.realestate.building.core.bo.FttdMjConfigBO;
import cn.gtmap.realestate.building.core.bo.HsHbConfigBO;
import cn.gtmap.realestate.building.core.bo.LjzJzmjConfigBO;
import cn.gtmap.realestate.building.core.bo.ZlsxConfigBO;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-10
 * @description 管理配置功能 工具类
 */
public class ManageConfigUtil {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return cn.gtmap.realestate.building.core.bo.ZlsxConfigBO
     * @description  获取坐落刷新默认配置
     */
    public static ZlsxConfigBO getZlsxConfig(){
        ZlsxConfigBO zlsxConfigBO = ZlsxConfig.getZlsxConfigBO();
        if(zlsxConfigBO == null){
            ZlsxConfig.initZlsxConfig();
        }
        return ZlsxConfig.getZlsxConfigBO();
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return cn.gtmap.realestate.building.core.bo.LjzJzmjConfigBO
     * @description 获取逻辑幢建筑面积计算默认配置
     */
    public static LjzJzmjConfigBO getLjzJzmjConfig(){
        LjzJzmjConfigBO ljzJzmjConfigBO= LjzJzmjConfig.getLjzJzmjConfigBO();
        if(ljzJzmjConfigBO == null){
            LjzJzmjConfig.initLjzLjzmConfig();
        }
        return LjzJzmjConfig.getLjzJzmjConfigBO();
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return cn.gtmap.realestate.building.core.bo.FttdMjConfigBO
     * @description 获取分摊土地面积计算默认配置
     */
    public static FttdMjConfigBO getFttdmjConfig(){
        FttdMjConfigBO fttdMjConfigBO= FttdmjConfig.getFttdMjConfigBO();
        if(fttdMjConfigBO == null){
            FttdmjConfig.initFttdmjConfig();
        }
        return FttdmjConfig.getFttdMjConfigBO();
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return cn.gtmap.realestate.building.core.bo.HsHbConfigBO
     * @description 获取分摊土地面积计算默认配置
     */
    public static HsHbConfigBO getHsHbConfig(){
        HsHbConfigBO hsHbConfigBO= HsHbConfig.getHsHbConfigBO();
        if(hsHbConfigBO == null){
            HsHbConfig.initHsHbConfig();
        }
        return HsHbConfig.getHsHbConfigBO();
    }

}
