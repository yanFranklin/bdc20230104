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
 * @version 1.0  2019-01-31
 * @description
 */
@Service
public class FwHsJbxxBgServiceImpl extends FwHsBgAbstractService{

    @Override
    public void dealBgjl(FwBgBO<FwHsDO> bgBO) {
        if(bgBO != null && CollectionUtils.isNotEmpty(bgBO.getyList())
                && CollectionUtils.isNotEmpty(bgBO.getNewList())
                && bgBO.getyList().size() == 1
                && bgBO.getNewList().size() == 1){
            FwHsDO yFwHsDO = bgBO.getyList().get(0);
            FwHsDO newFwHsDO = bgBO.getNewList().get(0);
            saveSingleHsbgjlb(bgBO.getBgbh(),bgBO.getBglx(),yFwHsDO,newFwHsDO);
        }
    }

    @Override
    public void checkParam(FwBgBO<FwHsDO> bgBO) {
        if(bgBO == null){
            throw new MissingArgumentException("bgBO");
        }
        if(StringUtils.isBlank(bgBO.getBgbh())){
            throw new MissingArgumentException("bgbh");
        }
        if(CollectionUtils.isEmpty(bgBO.getyList()) || bgBO.getyList().size() != 1){
            throw new MissingArgumentException("yFwHsDOList");
        }
        if(CollectionUtils.isEmpty(bgBO.getNewList()) || bgBO.getNewList().size() != 1){
            throw new MissingArgumentException("fwHsDOList");
        }
    }

    @Override
    public void dealZHs(FwBgBO<FwHsDO> bgBO) {
        if(bgBO != null && CollectionUtils.isNotEmpty(bgBO.getyList()) && bgBO.getyList().size() == 1
                && CollectionUtils.isNotEmpty(bgBO.getNewList()) && bgBO.getNewList().size() == 1) {
            FwHsDO yFwHs = bgBO.getyList().get(0);
            FwHsDO nFwHs = bgBO.getNewList().get(0);
            List<FwZhsDO> fwZhsDOList = fwZhsService.listFwZhsByFwHsIndex(yFwHs.getFwHsIndex());
            if(CollectionUtils.isNotEmpty(fwZhsDOList)){
                List<FwZhsDO> nFwZhsList = new ArrayList<>(fwZhsDOList.size());
                List<HFwZhsDO> bakList = new ArrayList<>(fwZhsDOList.size());
                for(FwZhsDO fwZhsDO : fwZhsDOList){
                    HFwZhsDO bakZhs = new HFwZhsDO();
                    BeanUtils.copyProperties(fwZhsDO,bakZhs);
                    bakList.add(bakZhs);

                    FwZhsDO nFwZhs = new FwZhsDO();
                    BeanUtils.copyProperties(fwZhsDO,nFwZhs);
                    nFwZhs.setFwZhsIndex(UUIDGenerator.generate());
                    nFwZhs.setFwHsIndex(nFwHs.getFwHsIndex());
                    nFwZhsList.add(nFwZhs);
                }
                // 备份
                entityMapper.insertBatchSelective(bakList);
                // 新增
                fwZhsService.batchInsert(nFwZhsList);
                // 删除
                fwZhsService.deleteZhsByFwHsIndex(yFwHs.getFwHsIndex());
            }
        }
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
                    //跟权籍保持一致，基本信息变更得时候不新增权利人
                    fwFcQlrBgService.dealQlr(bgBO.getBgbh(),yFwHs.getFwHsIndex(),yQlrList, null);
                }
            }
        }
    }

    @Override
    public void insert(FwBgBO<FwHsDO> bgBO){
        if(bgBO != null && CollectionUtils.isNotEmpty(bgBO.getNewList())){
            List<FwHsDO> newList = new ArrayList<>();
            for(FwHsDO fwHsDO : bgBO.getNewList()){
                FwHsDO newDO = new FwHsDO();
                BeanUtils.copyProperties(fwHsDO,newDO);
                newDO.setFwHsIndex(UUIDGenerator.generate());
                newList.add(newDO);
            }
            bgBO.setNewList(fwHsService.batchInsertFwHs(newList));
        }
    }

    @Override
    public void delete(FwBgBO<FwHsDO> bgBO) {
        if(bgBO != null && CollectionUtils.isNotEmpty(bgBO.getyList())){
            for(FwHsDO yFwhs:bgBO.getyList()){
                fwHsService.deleteFwhs(yFwhs,false);
            }
        }
    }

}
