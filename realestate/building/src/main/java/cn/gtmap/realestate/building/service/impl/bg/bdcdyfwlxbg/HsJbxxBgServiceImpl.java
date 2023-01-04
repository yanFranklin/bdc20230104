package cn.gtmap.realestate.building.service.impl.bg.bdcdyfwlxbg;

import cn.gtmap.realestate.building.core.bo.FwBgBO;
import cn.gtmap.realestate.building.service.impl.bg.FwHsJbxxBgServiceImpl;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-26
 * @description 户室基本信息变更，不处理变更记录，继承权利人
 */
@Service
public class HsJbxxBgServiceImpl extends FwHsJbxxBgServiceImpl {

    @Override
    public void dealBgjl(FwBgBO<FwHsDO> bgBO) {
    }

    @Override
    public void dealQlr(FwBgBO<FwHsDO> bgBO) {
        if(bgBO != null && CollectionUtils.isNotEmpty(bgBO.getyList()) && bgBO.getyList().size() == 1
                && CollectionUtils.isNotEmpty(bgBO.getNewList()) && bgBO.getNewList().size() == 1){
            FwHsDO yFwHs = bgBO.getyList().get(0);
            FwHsDO nFwHs = bgBO.getNewList().get(0);
            if(StringUtils.isNotBlank(yFwHs.getFwHsIndex())
                    && StringUtils.isNotBlank(nFwHs.getFwHsIndex())){
                List<FwFcqlrDO> yQlrList = fwFcqlrService.listFwFcQlrByFwIndex(yFwHs.getFwHsIndex());
                if(CollectionUtils.isNotEmpty(yQlrList)){
                    List<FwFcqlrDO> nQlrList = new ArrayList<>(yQlrList.size());
                    for(FwFcqlrDO yQlr:yQlrList){
                        FwFcqlrDO nQlr = new FwFcqlrDO();
                        BeanUtils.copyProperties(yQlr,nQlr);
                        nQlr.setFwIndex(nFwHs.getFwHsIndex());
                        nQlr.setFwFcqlrIndex(UUIDGenerator.generate());
                        nQlrList.add(nQlr);
                    }
                    //跟权籍保持一致，基本信息变更得时候不新增权利人
                    fwFcQlrBgService.dealQlr(bgBO.getBgbh(),yFwHs.getFwHsIndex(),yQlrList, nQlrList);
                }
            }
        }
    }
}
