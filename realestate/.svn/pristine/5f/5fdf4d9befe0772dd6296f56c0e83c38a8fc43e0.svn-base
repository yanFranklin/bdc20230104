package cn.gtmap.realestate.engine.service.impl.expression;

import cn.gtmap.realestate.engine.core.bo.BdcGzBdsYsBO;
import cn.gtmap.realestate.engine.core.enums.BdcGzSjlYslxEnum;
import cn.gtmap.realestate.engine.service.BdcGzSjlYsService;
import cn.gtmap.realestate.engine.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/15
 * @description 规则表达式中元素处理：List<Map>类型的元素
 *
 *   场景示例 ： xmList[0].SLBH != "2018121201"
 */
@Service
public class ListProcessor implements BdcGzSjlYsService{
    /**
     * 数字正则
     */
    private static Pattern pattern = Pattern.compile("\\[\\d+\\]");


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzBdsYsBO 前一个元素
     * @param zgzSjlResult 数据流结果集
     * @return {Object}  元素对象
     * @description 从数据流结果集中获取前一个元素对应的实际值
     */
    @Override
    public <T> void setPreElementValObj(BdcGzBdsYsBO bdcGzBdsYsBO, Map<String, T> zgzSjlResult) {
        String[] arr = bdcGzBdsYsBO.getPreElement().split("\\[");
        List<Map> list = (List<Map>) zgzSjlResult.get(arr[0]);
        if(CollectionUtils.isEmpty(list)){
            return;
        }

        String index = null;
        Matcher m = pattern.matcher(bdcGzBdsYsBO.getPreElement());
        if(m.find()) {
            index = m.group(0).replace("[", "").replace("]", "");
        }
        if(Integer.parseInt(index) >= list.size()){
            return;
        }

        // List<Map>  ===>>> xmList[0].SLBH
        if(bdcGzBdsYsBO.getPreElement().contains(Constants.STR_POINT)) {
            Map<String, Object> map = list.get(Integer.parseInt(index));
            if(MapUtils.isEmpty(map)){
                return;
            }

            arr = bdcGzBdsYsBO.getPreElement().split("\\.");
            bdcGzBdsYsBO.setPreElementObj(map.get(arr[1]));
        } else {
            // List<T extends Object>  ===>>> xmList[0]
            bdcGzBdsYsBO.setPreElementObj(list.get(Integer.parseInt(index)));
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzBdsYsBO 前一个元素
     * @description 设置前一个元素结构类型
     */
    @Override
    public void setPreElementType(BdcGzBdsYsBO bdcGzBdsYsBO) {
        if(bdcGzBdsYsBO.getPreElement().contains(Constants.STR_POINT)) {
            bdcGzBdsYsBO.setPreElementType(BdcGzSjlYslxEnum.LIST_MAP.getCode());
        } else {
            bdcGzBdsYsBO.setPreElementType(BdcGzSjlYslxEnum.LIST.getCode());
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzBdsYsBO 前一个元素
     * @description 解析前一个元素组成内容
     */
    @Override
    public void setPreElementItems(BdcGzBdsYsBO bdcGzBdsYsBO) {
        String preElement = bdcGzBdsYsBO.getPreElement();

        String[] arr = preElement.split("\\[");
        bdcGzBdsYsBO.getBdcGzBdsYszcBO().setParentItemName(arr[0]);

        Matcher m = pattern.matcher(preElement);
        if (m.find()) {
            bdcGzBdsYsBO.getBdcGzBdsYszcBO().setIndex(Integer.valueOf(m.group(0).replace("[", "").replace("]", "")));
        }

        // xmList[0].SLBH
        if(preElement.contains(Constants.STR_POINT)){
            arr = preElement.split("\\.");
            bdcGzBdsYsBO.getBdcGzBdsYszcBO().setSubItemName(arr[1]);
        }
    }
}
