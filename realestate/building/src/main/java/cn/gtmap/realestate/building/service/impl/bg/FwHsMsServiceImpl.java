package cn.gtmap.realestate.building.service.impl.bg;

import cn.gtmap.realestate.building.core.bo.FwBgBO;
import cn.gtmap.realestate.building.service.bg.FwHsBgAbstractService;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwZhsDO;
import cn.gtmap.realestate.common.core.domain.building.HFwZhsDO;
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
 * @version 1.0  2019-02-01
 * @description
 */
@Service
public class FwHsMsServiceImpl extends FwHsBgAbstractService {

    @Override
    public void dealBgjl(FwBgBO<FwHsDO> bgBO) {
        if(CollectionUtils.isNotEmpty(bgBO.getyList()) && bgBO.getyList().size() == 1){
            FwHsDO yFwHsDO = bgBO.getyList().get(0);
            saveSingleHsbgjlb(bgBO.getBgbh(),bgBO.getBglx(),yFwHsDO,new FwHsDO());
        }
    }

    @Override
    public void checkParam(FwBgBO<FwHsDO> bgBO) {
        if(StringUtils.isBlank(bgBO.getBgbh())){
            throw new MissingArgumentException("bgbh");
        }
        if(CollectionUtils.isEmpty(bgBO.getyList()) || bgBO.getyList().size() != 1){
            throw new MissingArgumentException("yFwHsDOList");
        }
    }

    @Override
    public void dealZHs(FwBgBO<FwHsDO> bgBO) {
        if(CollectionUtils.isNotEmpty(bgBO.getyList()) && bgBO.getyList().size() == 1) {
            FwHsDO yFwHs = bgBO.getyList().get(0);
            List<FwZhsDO> fwZhsDOList = fwZhsService.listFwZhsByFwHsIndex(yFwHs.getFwHsIndex());
            if(CollectionUtils.isNotEmpty(fwZhsDOList)){
                List<HFwZhsDO> bakList = new ArrayList<>(fwZhsDOList.size());
                for(FwZhsDO fwZhsDO : fwZhsDOList){
                    HFwZhsDO bakZhs = new HFwZhsDO();
                    BeanUtils.copyProperties(fwZhsDO,bakZhs);
                    bakList.add(bakZhs);
                }
                // 备份
                entityMapper.insertBatchSelective(bakList);
                // 删除
                fwZhsService.deleteZhsByFwHsIndex(yFwHs.getFwHsIndex());
            }
        }
    }

    @Override
    public void dealQlr(FwBgBO<FwHsDO> bgBO) {
        if(CollectionUtils.isNotEmpty(bgBO.getyList()) && bgBO.getyList().size() == 1){
            FwHsDO yFwHs = bgBO.getyList().get(0);
            if(StringUtils.isNotBlank(yFwHs.getFwHsIndex())){
                List<FwFcqlrDO> yQlrList = fwFcqlrService.listFwFcQlrByFwIndex(yFwHs.getFwHsIndex());
                if(CollectionUtils.isNotEmpty(yQlrList)){
                    fwFcQlrBgService.dealQlr(bgBO.getBgbh(),yFwHs.getFwHsIndex(),yQlrList,null);
                }
            }
        }
    }

    @Override
    public void insert(FwBgBO<FwHsDO> bgBO){
        if(CollectionUtils.isNotEmpty(bgBO.getNewList())){
            List<FwHsDO> newList = new ArrayList<>();
            for(FwHsDO fwHsDO : bgBO.getNewList()){
                FwHsDO newDO = new FwHsDO();
                BeanUtils.copyProperties(fwHsDO,newDO);
                newDO.setFwHsIndex(UUIDGenerator.generate());
                newDO.setBdcdyh(null);
                newList.add(newDO);
            }
            bgBO.setNewList(fwHsService.batchInsertFwHs(newList));
        }
    }

    @Override
    public void delete(FwBgBO<FwHsDO> bgBO) {
        if(CollectionUtils.isNotEmpty(bgBO.getyList())){
            for(FwHsDO yFwhs:bgBO.getyList()){
                fwHsService.deleteFwhs(yFwhs,false);
            }
        }
    }
}
