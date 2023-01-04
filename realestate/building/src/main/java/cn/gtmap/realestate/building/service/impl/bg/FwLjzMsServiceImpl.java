package cn.gtmap.realestate.building.service.impl.bg;

import cn.gtmap.realestate.building.core.bo.FwBgBO;
import cn.gtmap.realestate.building.service.bg.FwLjzBgAbstractService;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.ex.IllegalArgumentException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-01
 * @description
 */
@Service
public class FwLjzMsServiceImpl extends FwLjzBgAbstractService {

    @Override
    public void dealBgjl(FwBgBO<FwLjzDO> bgBO) {
        if(CollectionUtils.isNotEmpty(bgBO.getyList())
                && CollectionUtils.isEmpty(bgBO.getNewList())
                && bgBO.getyList().size() == 1){
            FwLjzDO yfwLjzDO = bgBO.getyList().get(0);
            FwLjzDO newFwLjzDO = new FwLjzDO();
            saveSingleHsbgjlb(bgBO.getBgbh(),bgBO.getBglx(),yfwLjzDO,newFwLjzDO);
        }
    }

    @Override
    public void checkParam(FwBgBO<FwLjzDO> bgBO) {
        if(StringUtils.isBlank(bgBO.getBgbh())){
            throw new MissingArgumentException("bgbh");
        }
        if(CollectionUtils.isEmpty(bgBO.getyList()) || bgBO.getyList().size() != 1){
            throw new IllegalArgumentException("yfwLjzDOList");
        }
        if(CollectionUtils.isNotEmpty(bgBO.getNewList())){
            throw new IllegalArgumentException("fwLjzDOList");
        }
    }

    @Override
    public void dealFwHs(FwBgBO<FwLjzDO> bgBO) {
        if(CollectionUtils.isNotEmpty(bgBO.getyList()) && bgBO.getyList().size() == 1){
            FwLjzDO yFwLjz = bgBO.getyList().get(0);
            List<FwHsDO> yfwHsDOList = fwHsService.listFwHsListByFwDcbIndex(yFwLjz.getFwDcbIndex());
            if(CollectionUtils.isNotEmpty(yfwHsDOList)){
                for(FwHsDO yFwHsDO : yfwHsDOList){
                    fwHsMsService.msBg(bgBO.getBgbh(),bgBO.getBglx(),yFwHsDO);
                }
            }
        }
    }

    @Override
    public void dealQlr(FwBgBO<FwLjzDO> bgBO) {
        if(CollectionUtils.isNotEmpty(bgBO.getyList()) && bgBO.getyList().size() == 1){
            FwLjzDO yFwLjz = bgBO.getyList().get(0);
            if(StringUtils.isNotBlank(yFwLjz.getFwDcbIndex())){
                List<FwFcqlrDO> yQlrList = fwFcqlrService.listFwFcQlrByFwIndex(yFwLjz.getFwDcbIndex());
                if(CollectionUtils.isNotEmpty(yQlrList)){
                    fwFcQlrBgService.dealQlr(bgBO.getBgbh(),yFwLjz.getFwDcbIndex(),yQlrList,null);
                }
            }
        }
    }

    @Override
    public void dealHst(FwBgBO<FwLjzDO> bgBO) {
        if(CollectionUtils.isNotEmpty(bgBO.getyList())
                && bgBO.getyList().size() == 1) {
            FwLjzDO yFwLjz = bgBO.getyList().get(0);
            if (StringUtils.isNotBlank(yFwLjz.getFwDcbIndex())) {
                String yIndex = yFwLjz.getFwDcbIndex();
                FwHstDO fwHstDO = fwHstService.queryFwHstByIndex(yIndex);
                if(fwHstDO != null){
                    fwHstBgService.dealHst(fwHstDO,null,true);
                }
            }
        }
    }
}
