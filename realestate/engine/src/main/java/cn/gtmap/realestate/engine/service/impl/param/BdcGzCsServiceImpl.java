package cn.gtmap.realestate.engine.service.impl.param;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlCsDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzSjlDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.engine.core.enums.BdcGzSjlCsEnum;
import cn.gtmap.realestate.engine.service.BdcGzCsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/11
 * @description 规则子系统规则参数处理相关逻辑
 */
@Service
public class BdcGzCsServiceImpl implements BdcGzCsService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTOList 子规则集合
     * @param paramMap   参数集合
     * @description 设置多个子规则参数值
     */
    @Override
    public void matchParamValue(List<BdcGzZgzDTO> bdcGzZgzDTOList, Map<String, Object> paramMap) {
        if(CollectionUtils.isEmpty(bdcGzZgzDTOList) || MapUtils.isEmpty(paramMap)){
            return;
        }

        for(BdcGzZgzDTO bdcGzZgzDTO : bdcGzZgzDTOList){
            matchParamValue(bdcGzZgzDTO, paramMap);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTO 子规则
     * @param paramMap   参数集合
     * @description 设置子规则参数值
     */
    private void matchParamValue(BdcGzZgzDTO bdcGzZgzDTO, Map<String, Object> paramMap) {
        if(null == bdcGzZgzDTO || CollectionUtils.isEmpty(bdcGzZgzDTO.getBdcGzSjlDTOList())) {
            return;
        }

        for(BdcGzSjlDTO sjlDTO: bdcGzZgzDTO.getBdcGzSjlDTOList()){
            matchParamValue(sjlDTO, paramMap);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzSjlDTO 数据流
     * @param paramMap   参数集合
     * @description 设置数据流对应参数值
     */
    private void matchParamValue(BdcGzSjlDTO bdcGzSjlDTO, Map<String, Object> paramMap) {
        if(null == bdcGzSjlDTO || CollectionUtils.isEmpty(bdcGzSjlDTO.getBdcGzSjlCsDOList())) {
            return;
        }

        for(BdcGzSjlCsDO csDO : bdcGzSjlDTO.getBdcGzSjlCsDOList()){
            // 针对入参类型的参数匹配
            if(null != csDO && StringToolUtils.existItemEquals(csDO.getSjlcszl(), BdcGzSjlCsEnum.RC.getCode(), BdcGzSjlCsEnum.ST.getCode())){
                for(Map.Entry<String, Object> entry : paramMap.entrySet()){
                    if(csDO.getSjlcsmc().equals(entry.getKey())){
                        csDO.setSjlcsz(entry.getValue());
                    }
                }
            }
        }
    }
}
