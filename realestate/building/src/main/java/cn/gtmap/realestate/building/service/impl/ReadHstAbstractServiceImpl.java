package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.mapper.FwHstMapper;
import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.core.service.FwHstService;
import cn.gtmap.realestate.building.service.ReadHstService;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwHstDO;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-23
 * @description 读取户室图
 */
public abstract class ReadHstAbstractServiceImpl implements ReadHstService{


    @Autowired
    protected StorageClientMatcher storageClient;

    @Autowired
    protected UserManagerUtils userManagerUtils;


    @Autowired
    FwHstMapper fwHstMapper;

    @Autowired
    private FwHstService fwHstService;

    @Autowired
    private FwHsService fwHsService;



    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @description  根据FW_HS_INDEX 查询户室图
     */
    protected FwHstDO queryFwHstByFwHsIndex(String fwHsIndex){
        FwHsDO fwHsDO = fwHsService.queryFwHsByIndex(fwHsIndex);
        if(fwHsDO != null && StringUtils.isNotBlank(fwHsDO.getFwHstIndex())){
            return fwHstService.queryFwHstByIndex(fwHsDO.getFwHstIndex());
        }
        return null;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHstIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @description  根据FW_HST_INDEX 查询户室图
     */
    protected FwHstDO queryFwHstByFwHstIndex(String fwHstIndex){
        return fwHstService.queryFwHstByIndex(fwHstIndex);
    }
}
