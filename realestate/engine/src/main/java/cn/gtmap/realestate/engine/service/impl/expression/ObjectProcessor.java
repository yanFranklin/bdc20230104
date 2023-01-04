package cn.gtmap.realestate.engine.service.impl.expression;

import cn.gtmap.realestate.engine.core.bo.BdcGzBdsYsBO;
import cn.gtmap.realestate.engine.core.enums.BdcGzSjlYslxEnum;
import cn.gtmap.realestate.engine.service.BdcGzSjlYsService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/15
 * @description  规则表达式中元素处理：直接取值
 *
 *  场景示例： name 包含  "小明"
 */
@Service
public class ObjectProcessor implements BdcGzSjlYsService {
    /**
     * @param bdcGzBdsYsBO 前一个元素
     * @param zgzSjlResult 数据流结果集
     * @return {Object}  元素对象
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 从数据流结果集中获取前一个元素对应的实际值
     */
    @Override
    public <T> void setPreElementValObj(BdcGzBdsYsBO bdcGzBdsYsBO, Map<String, T> zgzSjlResult) {
        bdcGzBdsYsBO.setPreElementObj(zgzSjlResult.get(bdcGzBdsYsBO.getPreElement()));
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzBdsYsBO 前一个元素
     * @description 设置前一个元素结构类型
     */
    @Override
    public void setPreElementType(BdcGzBdsYsBO bdcGzBdsYsBO) {
        bdcGzBdsYsBO.setPreElementType(BdcGzSjlYslxEnum.OBJ.getCode());
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzBdsYsBO 前一个元素
     * @description 解析前一个元素组成内容
     */
    @Override
    public void setPreElementItems(BdcGzBdsYsBO bdcGzBdsYsBO) {
        bdcGzBdsYsBO.getBdcGzBdsYszcBO().setParentItemName(bdcGzBdsYsBO.getPreElement());
    }
}
