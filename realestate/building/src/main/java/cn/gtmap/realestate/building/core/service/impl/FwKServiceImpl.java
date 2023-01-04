package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.mapper.FwKMapper;
import cn.gtmap.realestate.building.core.service.FwKService;
import cn.gtmap.realestate.building.core.service.FwLjzService;
import cn.gtmap.realestate.building.core.service.FwXmxxService;
import cn.gtmap.realestate.common.core.domain.building.FwKDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-28
 * @description
 */
@Service
public class FwKServiceImpl implements FwKService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FwKServiceImpl.class);

    @Autowired
    private FwKMapper fwKMapper;

    @Autowired
    private FwLjzService fwLjzService;

    @Autowired
    private FwXmxxService fwXmxxService;

    @Value("#{${qjgldm.fwk.mapping:{'320400': 'fw_k_3204'}}}")
    private Map<String, String> qjgldmFwkMap;

    @Value("${fwkTableName:}")
    private String fwkTableName;

    /**
     * @param lszd
     * @param zrzh
     * @return cn.gtmap.realestate.common.core.domain.building.FwKDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据 djh 和 ZRZH 查询 自然幢
     */
    @Override
    public FwKDO queryFwKByLszdAndZrzh(String lszd, String zrzh, String qjgldm) {
        if (StringUtils.isNotBlank(lszd) && StringUtils.isNotBlank(zrzh)) {
            Map<String, String> paramMap = new HashMap();
            paramMap.put("lszd", lszd);
            paramMap.put("zrzh", zrzh);
            LOGGER.info("查询 自然幢qjgldm:{}", qjgldm);

            if (!qjgldmFwkMap.isEmpty()) {
                LOGGER.info(qjgldmFwkMap.toString());
                if (StringUtils.isNotBlank(qjgldm)) {
                    LOGGER.info("查询 自然幢:{}", qjgldm);
                    LOGGER.info("查询 自然幢:{}", qjgldmFwkMap.get(qjgldm));
                    paramMap.put("fw_k", qjgldmFwkMap.get(qjgldm));

                } else {
                    LOGGER.info("查询 自然幢fwkTableName:{}", fwkTableName);
                    paramMap.put("fw_k", fwkTableName);

                }


            }
            List<FwKDO> fwkList = fwKMapper.queryFwKList(paramMap);
            if (CollectionUtils.isNotEmpty(fwkList)) {
                return fwkList.get(0);
            }
        }
        return null;
    }

    /**
     * @param lszd
     * @param zrzhList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据 lszd 和 ZRZH 删除 自然幢
     */
    @Override
    public void deleteFwKWithLszdAndZrzh(String lszd, List<String> zrzhList) {
        if(StringUtils.isNotBlank(lszd) && CollectionUtils.isNotEmpty(zrzhList)){
            Map<String,Object> paramMap = new HashMap();
            paramMap.put("lszd",lszd);
            paramMap.put("zrzhList",zrzhList);
            fwKMapper.deleteFwK(paramMap);
        }
    }

    /**
     * @param fwXmxxIndex
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据项目主键 删除 FW_K表
     */
    @Override
    public void deleteFwKByFwXmxxIndex(String fwXmxxIndex) {
        // 查询 项目下 所有逻辑幢的自然幢号
        FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByPk(fwXmxxIndex);
        List<FwLjzDO> fwLjzDOList = fwLjzService.listLjzByFwXmxxIndex(fwXmxxIndex);
        if(CollectionUtils.isNotEmpty(fwLjzDOList) && fwXmxxDO != null
                && StringUtils.isNotBlank(fwXmxxDO.getLszd())){
            HashSet<String> zrzhSet = new HashSet<>();
            for(FwLjzDO ljzDO : fwLjzDOList){
                // 判断 逻辑幢的 LSZD 和 项目的 LSZD 是否相等
                if(StringUtils.isNotBlank(ljzDO.getZrzh())
                        && StringUtils.equals(fwXmxxDO.getLszd(),ljzDO.getLszd())){
                    zrzhSet.add(ljzDO.getZrzh());
                }
            }
            if(CollectionUtils.isNotEmpty(zrzhSet)){
                List<String> ljzList = new ArrayList<>();
                ljzList.addAll(zrzhSet);
                deleteFwKWithLszdAndZrzh(fwXmxxDO.getLszd(),ljzList);
            }
        }
    }
}
