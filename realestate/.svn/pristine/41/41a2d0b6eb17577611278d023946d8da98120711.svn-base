package cn.gtmap.realestate.engine.service.impl.expression;

import cn.gtmap.realestate.engine.service.BdcGzSjlYsService;
import cn.gtmap.realestate.engine.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/15
 * @description  规则表达式元素处理简单工厂定义
 */
@Component
public class BdcGzBdsYsFactory {
    @Autowired
    private ListProcessor listProcessor;

    @Autowired
    private MapProcessor mapProcessor;

    @Autowired
    private ObjectProcessor objectProcessor;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param preElementName 元素
     * @return 元素处理实例
     * @description 根据元素类型获取对应的处理实例
     */
    public BdcGzSjlYsService getProcessor(String preElementName){
        if(StringUtils.isBlank(preElementName)){
            return objectProcessor;
        }

        // 是否包含 [  ，即 List<Map> 、List<T extends Object>类型
        if(preElementName.contains(Constants.STR_BRACKET_LEFT)){
            return listProcessor;
        }
        // 是否包含 .  即 Map类型
        else if(preElementName.contains(Constants.STR_POINT)){
            return mapProcessor;
        }
        // 正常取值
        else {
            return objectProcessor;
        }
    }
}
