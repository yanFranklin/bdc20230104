package cn.gtmap.realestate.engine.service.impl.expression;

import cn.gtmap.realestate.engine.core.bo.BdcGzBdsYsBO;
import cn.gtmap.realestate.engine.core.enums.BdcGzSjlYslxEnum;
import cn.gtmap.realestate.engine.service.BdcGzSjlYsService;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/15
 * @description  规则表达式中元素处理：Map类型的元素
 *
 *   场景示例： infomap.sfcf  ==  true
 */
@Service
public class MapProcessor implements BdcGzSjlYsService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzBdsYsBO 前一个元素
     * @param zgzSjlResult 数据流结果集
     * @return {Object}  元素对象
     * @description 从数据流结果集中获取前一个元素对应的实际值
     */
    @Override
    public <T> void setPreElementValObj(BdcGzBdsYsBO bdcGzBdsYsBO, Map<String, T> zgzSjlResult) {
        String[] arr = bdcGzBdsYsBO.getPreElement().split("\\.");
        Map preElement = (Map) zgzSjlResult.get(arr[0]);

        if(MapUtils.isNotEmpty(preElement)){
            bdcGzBdsYsBO.setPreElementObj(preElement.get(arr[1]));
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzBdsYsBO 前一个元素
     * @description 设置前一个元素结构类型
     */
    @Override
    public void setPreElementType(BdcGzBdsYsBO bdcGzBdsYsBO) {
        bdcGzBdsYsBO.setPreElementType(BdcGzSjlYslxEnum.MAP.getCode());
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzBdsYsBO 前一个元素
     * @description 解析前一个元素组成内容
     *   xmMap.slbh
     */
    @Override
    public void setPreElementItems(BdcGzBdsYsBO bdcGzBdsYsBO) {
        String preElement = bdcGzBdsYsBO.getPreElement();

        String[] arr = preElement.split("\\.");
        bdcGzBdsYsBO.getBdcGzBdsYszcBO().setParentItemName(arr[0]);
        bdcGzBdsYsBO.getBdcGzBdsYszcBO().setSubItemName(arr[1]);
    }
}
