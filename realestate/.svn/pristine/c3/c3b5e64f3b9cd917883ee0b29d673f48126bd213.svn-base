package cn.gtmap.realestate.building.service.impl.bg;

import cn.gtmap.realestate.building.core.bo.FwBgBO;
import cn.gtmap.realestate.building.core.service.BdcdyhService;
import cn.gtmap.realestate.building.service.bg.FwBgService;
import cn.gtmap.realestate.building.service.bg.FwLjzBgAbstractService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-31
 * @description 逻辑幢 基本信息变更
 */
@Service
public class FwLjzJbxxBgServiceImpl extends FwLjzBgAbstractService {

    @Autowired
    private BdcdyhService bdcdyhService;

    @Override
    public void dealBgjl(FwBgBO<FwLjzDO> bgBO) {
        if(CollectionUtils.isNotEmpty(bgBO.getyList())
                && CollectionUtils.isNotEmpty(bgBO.getNewList())
                && bgBO.getyList().size() == 1
                && bgBO.getNewList().size() == 1){
            FwLjzDO yfwLjzDO = bgBO.getyList().get(0);
            FwLjzDO newFwLjzDO = bgBO.getNewList().get(0);
            saveSingleHsbgjlb(bgBO.getBgbh(),bgBO.getBglx(),yfwLjzDO,newFwLjzDO);
        }
    }

    @Override
    public void checkParam(FwBgBO<FwLjzDO> bgBO) {
        if(StringUtils.isBlank(bgBO.getBgbh())){
            throw new MissingArgumentException("bgbh");
        }
        if(CollectionUtils.isEmpty(bgBO.getyList()) || bgBO.getyList().size() != 1){
            throw new MissingArgumentException("yfwLjzDOList");
        }
        if(CollectionUtils.isEmpty(bgBO.getNewList()) || bgBO.getNewList().size() != 1){
            throw new MissingArgumentException("fwLjzDOList");
        }
    }

    @Override
    public void dealFwHs(FwBgBO<FwLjzDO> bgBO) {
        dealFwHs(fwHsJbxxBgService,bgBO);
    }

    @Transactional
    public void dealFwHs(FwBgService fwBgService,FwBgBO<FwLjzDO> bgBO) {
        if(CollectionUtils.isNotEmpty(bgBO.getyList()) && bgBO.getyList().size() == 1
                && CollectionUtils.isNotEmpty(bgBO.getNewList()) && bgBO.getNewList().size() == 1){
            FwLjzDO yFwLjz = bgBO.getyList().get(0);
            FwLjzDO nFwLjz = bgBO.getNewList().get(0);
            List<FwHsDO> yfwHsDOList = fwHsService.listFwHsListByFwDcbIndex(yFwLjz.getFwDcbIndex());
            if(CollectionUtils.isNotEmpty(yfwHsDOList)){
                for(FwHsDO yFwHsDO : yfwHsDOList){
                    FwHsDO nFwHsDO = new FwHsDO();
                    BeanUtils.copyProperties(yFwHsDO,nFwHsDO);
                    nFwHsDO.setFwHsIndex(UUIDGenerator.generate());
                    nFwHsDO.setFwDcbIndex(nFwLjz.getFwDcbIndex());
                    if(!StringUtils.equals(nFwLjz.getBdcdyfwlx(),yFwLjz.getBdcdyfwlx())){
                        if(StringUtils.equals(nFwLjz.getBdcdyfwlx(), Constants.BDCDYFWLX_H)){
                            nFwHsDO.setBdcdyh(bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh(nFwLjz.getLszd(),nFwLjz.getZrzh()));
                            nFwHsDO.setBdczt(Constants.BDCZT_KY);
                        }else{
                            nFwHsDO.setBdcdyh(null);
                            nFwHsDO.setBdczt(Constants.BDCZT_BKY);
                        }
                    }
                    fwBgService.jbxxBg(bgBO.getBgbh(),bgBO.getBglx(),yFwHsDO,nFwHsDO);
                }
            }
        }
    }

    @Override
    public void dealQlr(FwBgBO<FwLjzDO> bgBO) {
        if(CollectionUtils.isNotEmpty(bgBO.getyList()) && bgBO.getyList().size() == 1
                && CollectionUtils.isNotEmpty(bgBO.getNewList()) && bgBO.getNewList().size() == 1){
            FwLjzDO yFwLjz = bgBO.getyList().get(0);
            FwLjzDO nFwLjz = bgBO.getNewList().get(0);
            if(StringUtils.isNotBlank(yFwLjz.getFwDcbIndex())
                    && StringUtils.isNotBlank(nFwLjz.getFwDcbIndex())){
                List<FwFcqlrDO> yQlrList = fwFcqlrService.listFwFcQlrByFwIndex(yFwLjz.getFwDcbIndex());
                if(CollectionUtils.isNotEmpty(yQlrList)){
                    List<FwFcqlrDO> newQlrList = new ArrayList<>(yQlrList.size());
                    for(FwFcqlrDO yQlr:yQlrList){
                        FwFcqlrDO nQlr = new FwFcqlrDO();
                        BeanUtils.copyProperties(yQlr,nQlr);
                        nQlr.setFwFcqlrIndex(UUIDGenerator.generate());
                        nQlr.setFwIndex(nFwLjz.getFwDcbIndex());
                        newQlrList.add(nQlr);
                    }
                    fwFcQlrBgService.dealQlr(bgBO.getBgbh(),yFwLjz.getFwDcbIndex(),yQlrList,newQlrList);
                }
            }
        }
    }

    @Override
    public void dealHst(FwBgBO<FwLjzDO> bgBO) {
        if(CollectionUtils.isNotEmpty(bgBO.getyList()) && bgBO.getyList().size() == 1
                && CollectionUtils.isNotEmpty(bgBO.getNewList()) && bgBO.getNewList().size() == 1) {
            FwLjzDO yFwLjz = bgBO.getyList().get(0);
            FwLjzDO nFwLjz = bgBO.getNewList().get(0);
            if (StringUtils.isNotBlank(yFwLjz.getFwDcbIndex())
                    && StringUtils.isNotBlank(nFwLjz.getFwDcbIndex())) {
                String yIndex = yFwLjz.getFwDcbIndex();
                String nIndex = nFwLjz.getFwDcbIndex();
                FwHstDO fwHstDO = fwHstService.queryFwHstByIndex(yIndex);
                if(fwHstDO != null){
                    FwHstDO nDo = new FwHstDO();
                    BeanUtils.copyProperties(fwHstDO,nDo);
                    nDo.setFwHstIndex(nIndex);
                    fwHstBgService.dealHst(fwHstDO,nDo,true);
                }
            }
        }
    }
}
