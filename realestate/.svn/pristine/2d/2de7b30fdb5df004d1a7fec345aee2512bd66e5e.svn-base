package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.service.BdcSwTsclService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.TsswDataDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/6/28
 * @description 税务信息特殊处理南通版本
 */
@Service
public class BdcSwTsclServiceImpl_nantong implements BdcSwTsclService {

    /**
     * @return 接口标识码，同一接口中的标识码不能出现重复
     * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取实现类的标识码
     */
    @Override
    public Set<String> getInterfaceCode() {
        Set<String> set = new LinkedHashSet<>(1);
        set.add("nantong");
        return set;
    }

    /**
     * @param tsswDataDTOList 推送税务基本信息
     * @return 推送税务实体
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 组装处理推送税务信息
     */
    public TsswDataDTO handleTsswData(List<TsswDataDTO> tsswDataDTOList){
        TsswDataDTO tsswDataDTO =new TsswDataDTO();
        if(CollectionUtils.isEmpty(tsswDataDTOList)){
            return tsswDataDTO;
        }
        tsswDataDTO =tsswDataDTOList.get(0);
        //获取主房项目
        for(TsswDataDTO tsswData:tsswDataDTOList){
            if(tsswData.getBdcSlXm() != null &&CommonConstantUtils.SF_S_DM.equals(tsswData.getBdcSlXm().getSfzf())){
                tsswDataDTO =tsswData;
                break;
            }
        }


        List<BdcSlXmDO> bdcSlXmDOList =new ArrayList<>();
        tsswDataDTOList.forEach(dataDTO -> {
            if(dataDTO.getBdcSlXm() != null) {
                bdcSlXmDOList.add(dataDTO.getBdcSlXm());
            }
        });

        if(tsswDataDTO.getBdcSlXm() != null) {
            //拼接主房+附房坐落
            String zl = StringToolUtils.resolveBeanToAppendStr(bdcSlXmDOList, "getZl", ",");
            tsswDataDTO.getBdcSlXm().setZl(zl);
        }
        return tsswDataDTO;

    }
}
