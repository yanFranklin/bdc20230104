package cn.gtmap.realestate.building.service.impl.bdcdyhzt;

import cn.gtmap.realestate.building.core.service.BdcdyZtService;
import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.service.bdcdyhzt.BdcdyhZtAbstractService;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-03-26
 * @description 户室类型
 */
@Service
public class FwHsBdcdyhZtServiceImpl extends BdcdyhZtAbstractService<FwHsDO> {

    @Autowired
    private BdcdyZtService bdcdyZtService;

    @Autowired
    private FwHsService fwHsService;


    /**
     * @param object
     * @return cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取更新前 XSZT
     */
    @Override
    public SSjBdcdyhxsztDO getOldXszt(Object object) {
        FwHsDO fwHsDO = (FwHsDO)object;
        SSjBdcdyhxsztDO yXszt = null;
        if(StringUtils.isNotBlank(fwHsDO.getFwHsIndex())){
            FwHsDO yFwhsDO = fwHsService.queryFwHsByIndex(fwHsDO.getFwHsIndex());
            if(yFwhsDO != null && StringUtils.isNotBlank(yFwhsDO.getBdcdyh())){
                yXszt = bdcdyZtService.querySsjBdcdyhxsztDOByBdcdyh(yFwhsDO.getBdcdyh());
            }
        }
        return yXszt;
    }

}
