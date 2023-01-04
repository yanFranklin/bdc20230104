package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcTdsyqDO;
import cn.gtmap.realestate.common.core.domain.building.QszdZdmjDO;
import cn.gtmap.realestate.common.core.dto.building.QszdDjdcbResponseDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcTdsyqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description 从楼盘表加载数据到土地所有权
 */
@Service
public class InitLpbToBdcTdsyqServiceImpl extends InitBdcTdsyqAbstractService {

    @Value("${jsydsyq.dlmj:}")
    private String jsydsyqDlmjdm;

    @Override
    public String getVal() {
        return Constants.QLSJLY_LPB;
    }


    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        BdcTdsyqDO tdsyqDO = initTdFromLpb(initServiceQO, BdcTdsyqDO.class);
        //获取对应的地类面积
        if (initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof QszdDjdcbResponseDTO) {
            QszdDjdcbResponseDTO qszdDjdcbResponseDTO = (QszdDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO();
            List<QszdZdmjDO> list = qszdDjdcbResponseDTO.getQszdZdmjDOList();
            //list转map，key值为dldm
            if (CollectionUtils.isNotEmpty(list)) {
                Map<String, QszdZdmjDO> qszdZdmjDOMap = list.stream().collect(Collectors.toMap(QszdZdmjDO::getDldm, QszdZdmjDO -> QszdZdmjDO, (k1, k2) -> k2));
                if (MapUtils.isNotEmpty(qszdZdmjDOMap) && StringUtils.isNotBlank(jsydsyqDlmjdm)) {
                    Map dlmjdmMap = JSON.parseObject(jsydsyqDlmjdm, Map.class);
                    double nydmj = countMj(qszdZdmjDOMap, "nydmj", dlmjdmMap);
                    if (!Objects.equals(0.0, nydmj)) {
                        tdsyqDO.setNydmj(nydmj);
                    }
                    double ldmj = countMj(qszdZdmjDOMap, "ldmj", dlmjdmMap);
                    if (!Objects.equals(0.0, ldmj)) {
                        tdsyqDO.setLdmj(ldmj);
                    }
                    double cdmj = countMj(qszdZdmjDOMap, "cdmj", dlmjdmMap);
                    if (!Objects.equals(0.0, cdmj)) {
                        tdsyqDO.setCdmj(cdmj);
                    }
                    double gdmj = countMj(qszdZdmjDOMap, "gdmj", dlmjdmMap);
                    if (!Objects.equals(0.0, gdmj)) {
                        tdsyqDO.setGdmj(gdmj);
                    }
                    double qtnydmj = countMj(qszdZdmjDOMap, "qtnydmj", dlmjdmMap);
                    if (!Objects.equals(0.0, qtnydmj)) {
                        tdsyqDO.setQtnydmj(qtnydmj);
                    }
                    double jsydmj = countMj(qszdZdmjDOMap, "jsydmj", dlmjdmMap);
                    if (!Objects.equals(0.0, jsydmj)) {
                        tdsyqDO.setJsydmj(jsydmj);
                    }
                    double wlydmj = countMj(qszdZdmjDOMap, "wlydmj", dlmjdmMap);
                    if (!Objects.equals(0.0, wlydmj)) {
                        tdsyqDO.setWlydmj(wlydmj);
                    }
                }
            }
        }
        return tdsyqDO;
    }

    private double countMj(Map<String, QszdZdmjDO> qszdZdmjDOMap, String key, Map dlmjdmMap) {
        double mj = 0.0;
        String dldm = MapUtils.getString(dlmjdmMap, key, "");
        if (StringUtils.isNotBlank(dldm)) {
            String[] dlDmList = dldm.split(CommonConstantUtils.ZF_YW_DH);
            for (String dm : dlDmList) {
                if (Objects.nonNull(qszdZdmjDOMap.get(dm)) && Objects.nonNull(qszdZdmjDOMap.get(dm).getDlmj())) {
                    mj += qszdZdmjDOMap.get(dm).getDlmj();
                }
            }
        }
        return mj;
    }
}
