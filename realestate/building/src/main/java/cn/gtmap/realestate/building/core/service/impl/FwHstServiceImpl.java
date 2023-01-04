package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.core.service.FwHstService;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwHstDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/30
 * @description 房屋户室图相关服务实现
 */
@Service
public class FwHstServiceImpl implements FwHstService {
    @Autowired
    private FwHsService fwHsService;
    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param fwHstIndex
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过主键删除房屋户室图
     */
    @Override
    public Integer deleteFwHstByFwHstIndex(String fwHstIndex) {
        int result = 0;
        if (StringUtils.isNotBlank(fwHstIndex)) {
            result = entityMapper.deleteByPrimaryKey(FwHstDO.class, fwHstIndex);
            if (result == 1) {
                List<FwHsDO> fwHsDOList = fwHsService.listFwHsByFwHstIndex(fwHstIndex);
                if (CollectionUtils.isNotEmpty(fwHsDOList)) {
                    for (FwHsDO fwHsDO : fwHsDOList) {
                        fwHsDO.setFwHstIndex("");
                        fwHsService.updateFwHs(fwHsDO, true);
                    }
                }
            }
        }
        return result;
    }

    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 查看逻辑幢下户室图缺失情况
     */
    @Override
    public List<FwHsDO> listHstDeficiency(String fwDcbIndex) {
        List<FwHsDO> fwHsDOList = fwHsService.listFwHstNullByFwDcbIndex(fwDcbIndex);
        if (CollectionUtils.isNotEmpty(fwHsDOList)) {
            return fwHsDOList;
        }
        return new ArrayList<>(0);
    }

    /**
     * @param fwHstIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据户室图主键查询户室图
     */
    @Override
    public FwHstDO queryFwHstByIndex(String fwHstIndex) {
        if(StringUtils.isNotBlank(fwHstIndex)){
            return entityMapper.selectByPrimaryKey(FwHstDO.class,fwHstIndex);
        }else{
            return null;
        }
    }

}