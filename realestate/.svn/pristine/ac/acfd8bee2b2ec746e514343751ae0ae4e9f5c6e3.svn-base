package cn.gtmap.realestate.building.service.impl.bdcdyhzt;

import cn.gtmap.realestate.building.core.service.FwFcqlrService;
import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.building.service.bdcdyhzt.BdcdyhZtAbstractService;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-03-26
 * @description
 */
@Service
public class FwFcQlrBdcdyhZtServiceImpl extends BdcdyhZtAbstractService<FwFcqlrDO> {

    @Autowired
    private FwFcqlrService fwFcqlrService;

    @Autowired
    private BdcdyService bdcdyService;

    /**
     * @param object
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存状态
     */
    @Override
    public void insertZt(Object object) {
        FwFcqlrDO fwFcqlrDO = (FwFcqlrDO)object;
        if(StringUtils.isNotBlank(fwFcqlrDO.getFwIndex())){
            // 查询户室下所有权利人
            String yQlrMc = fwFcqlrService.wmConcatQlrByFwIndex(fwFcqlrDO.getFwIndex());
            String bdcdyh = bdcdyService.queryBdcdyhByFwIndex(fwFcqlrDO.getFwIndex());
            if(StringUtils.isNotBlank(bdcdyh)){
                SSjBdcdyhxsztDO sSjBdcdyhxsztDO = new SSjBdcdyhxsztDO();
                sSjBdcdyhxsztDO.setBdcdyh(bdcdyh);
                sSjBdcdyhxsztDO.setQlr(yQlrMc);
                bdcdyZtService.updateXsztDO(sSjBdcdyhxsztDO,false);
            }
        }
    }

    /**
     * @param oldXszt
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 传递实体删除状态
     */
    @Override
    public void deleteZt(Object obj,SSjBdcdyhxsztDO oldXszt) {
        if(oldXszt != null && StringUtils.isNotBlank(oldXszt.getBdcdyh())){
            FwFcqlrDO fwFcqlrDO = (FwFcqlrDO) obj;
            if(StringUtils.isNotBlank(fwFcqlrDO.getFwIndex())){
                String yQlrMc = fwFcqlrService.wmConcatQlrByFwIndex(fwFcqlrDO.getFwIndex());
                oldXszt.setQlr(yQlrMc);
                bdcdyZtService.updateXsztDO(oldXszt,true);
            }
        }
    }

    /**
     * @param record
     * @param oldXszt
     * @param newXszt
     * @param updateNull
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 更新状态
     */
    @Override
    public void updateZt(Object record,SSjBdcdyhxsztDO oldXszt, SSjBdcdyhxsztDO newXszt, boolean updateNull) {
        if(oldXszt != null && StringUtils.isNotBlank(oldXszt.getBdcdyh())){
            FwFcqlrDO fwFcqlrDO = (FwFcqlrDO) record;
            if(StringUtils.isNotBlank(fwFcqlrDO.getFwIndex())){
                String yQlrMc = fwFcqlrService.wmConcatQlrByFwIndex(fwFcqlrDO.getFwIndex());
                oldXszt.setQlr(yQlrMc);
                bdcdyZtService.updateXsztDO(oldXszt,true);
            }
        }
    }

    /**
     * @param object
     * @return cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造 XSZT
     */
    @Override
    public SSjBdcdyhxsztDO structureXszt(Object object) {
        FwFcqlrDO fwFcqlrDO = (FwFcqlrDO) object;
        SSjBdcdyhxsztDO xszt = new SSjBdcdyhxsztDO();
        if(StringUtils.isNotBlank(fwFcqlrDO.getFwIndex())){
            String bdcdyh = bdcdyService.queryBdcdyhByFwIndex(fwFcqlrDO.getFwIndex());
            if(StringUtils.isNotBlank(bdcdyh)){
                xszt = bdcdyZtService.querySsjBdcdyhxsztDOByBdcdyh(bdcdyh);
            }
        }
        return xszt;
    }

    /**
     * @param object
     * @return cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取更新前 XSZT
     */
    @Override
    public SSjBdcdyhxsztDO getOldXszt(Object object) {
        FwFcqlrDO fwFcqlrDO = (FwFcqlrDO) object;
        SSjBdcdyhxsztDO sSjBdcdyhxsztDO = new SSjBdcdyhxsztDO();
        if(StringUtils.isNotBlank(fwFcqlrDO.getFwIndex())){
            String bdcdyh = bdcdyService.queryBdcdyhByFwIndex(fwFcqlrDO.getFwIndex());
            if(StringUtils.isNotBlank(bdcdyh)){
                sSjBdcdyhxsztDO = bdcdyZtService.querySsjBdcdyhxsztDOByBdcdyh(bdcdyh);
            }
        }
        return sSjBdcdyhxsztDO;
    }
}
