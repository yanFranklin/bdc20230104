package cn.gtmap.realestate.building.service.impl.bg;

import cn.gtmap.realestate.building.core.bo.FwBgBO;
import cn.gtmap.realestate.building.service.bg.FwXmxxBgAbstractService;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwHstDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import cn.gtmap.realestate.common.core.ex.IllegalArgumentException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-31
 * @description
 */
@Service
public class FwXmxxJbxxBgServiceImpl extends FwXmxxBgAbstractService {

    @Override
    public void checkParam(FwBgBO<FwXmxxDO> bgBO) {
        if(StringUtils.isBlank(bgBO.getBgbh())){
            throw new MissingArgumentException("bgbh");
        }
        if(CollectionUtils.isEmpty(bgBO.getyList()) || bgBO.getyList().size() != 1){
            throw new IllegalArgumentException("yFwXmxxList");
        }
        if(CollectionUtils.isNotEmpty(bgBO.getNewList()) && bgBO.getNewList().size() != 1){
            throw new MissingArgumentException("fwXmxxDO");
        }
    }

    @Override
    public void saveBgjl(FwBgBO<FwXmxxDO> bgBO) {
        if(CollectionUtils.isNotEmpty(bgBO.getNewList()) && bgBO.getNewList().size()==1){
            saveSingleHsbgjlb(bgBO.getBgbh(),bgBO.getBglx(),bgBO.getyList().get(0),bgBO.getNewList().get(0));
        }
    }

    /**
     * @param bgBO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理FWFCQLR
     */
    @Override
    public void dealFwFcqlr(FwBgBO<FwXmxxDO> bgBO) {
        if(CollectionUtils.isNotEmpty(bgBO.getNewList()) && bgBO.getNewList().size()==1){
            FwXmxxDO yFwXmxxDO = bgBO.getyList().get(0);
            List<FwFcqlrDO> yQlrList = fwFcqlrService.listFwFcQlrByFwIndex(yFwXmxxDO.getFwXmxxIndex());
            if(CollectionUtils.isNotEmpty(yQlrList)){
                List<FwFcqlrDO> newList = new ArrayList<>();
                for(FwFcqlrDO fwFcqlrDO : yQlrList){
                    FwFcqlrDO nDo = new FwFcqlrDO();
                    BeanUtils.copyProperties(fwFcqlrDO,nDo);
                    nDo.setFwFcqlrIndex(UUIDGenerator.generate());
                    nDo.setFwIndex(bgBO.getNewList().get(0).getFwXmxxIndex());
                    newList.add(nDo);
                }
                fwFcQlrBgService.dealQlr(bgBO.getBgbh(),yFwXmxxDO.getFwXmxxIndex(),yQlrList,newList);
            }
        }
    }

    /**
     * @param bgBO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    public void dealHst(FwBgBO<FwXmxxDO> bgBO) {
        if(CollectionUtils.isNotEmpty(bgBO.getNewList()) && bgBO.getNewList().size()==1){
            FwXmxxDO yFwXmxxDO = bgBO.getyList().get(0);
            FwHstDO fwHstDO = fwHstService.queryFwHstByIndex(yFwXmxxDO.getFwXmxxIndex());
            if(fwHstDO != null){
                FwHstDO nDo = new FwHstDO();
                BeanUtils.copyProperties(fwHstDO,nDo);
                nDo.setFwHstIndex(bgBO.getNewList().get(0).getFwXmxxIndex());
                fwHstBgService.dealHst(fwHstDO,nDo,true);
            }
        }
    }

    /**
     * @param bgBO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理逻辑幢
     */
    @Override
    public void dealLjz(FwBgBO<FwXmxxDO> bgBO) {
        if(CollectionUtils.isNotEmpty(bgBO.getNewList()) && bgBO.getNewList().size()==1){
            List<FwLjzDO> fwLjzDOList = fwLjzService.listLjzByFwXmxxIndex(bgBO.getyList().get(0).getFwXmxxIndex());
            if(CollectionUtils.isNotEmpty(fwLjzDOList)){
                for(FwLjzDO fwLjzDO : fwLjzDOList){
                    FwLjzDO newLjz = new FwLjzDO();
                    BeanUtils.copyProperties(fwLjzDO,newLjz);
                    newLjz.setFwDcbIndex(UUIDGenerator.generate());
                    newLjz.setFwXmxxIndex(bgBO.getNewList().get(0).getFwXmxxIndex());
                    fwLjzJbxxBgService.jbxxBg(bgBO.getBgbh(),bgBO.getBglx(),fwLjzDO,newLjz);
                }
            }
        }
    }
}
