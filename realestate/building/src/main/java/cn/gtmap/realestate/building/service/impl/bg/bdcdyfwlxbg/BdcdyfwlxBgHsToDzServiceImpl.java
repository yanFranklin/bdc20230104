package cn.gtmap.realestate.building.service.impl.bg.bdcdyfwlxbg;

import cn.gtmap.realestate.building.service.bg.bdcdyfwlxbg.BdcdyfwlxBgAbstractService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO;
import cn.gtmap.realestate.common.core.dto.building.FwlxBgRequestDTO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-26
 * @description
 */
@Service
public class BdcdyfwlxBgHsToDzServiceImpl extends BdcdyfwlxBgAbstractService {

    @Override
    public void bg(FwlxBgRequestDTO requestDTO){
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(requestDTO.getFwDcbIndex());
        if(fwLjzDO != null){
            fwLjzDO.setBdcdyh(bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh(fwLjzDO.getLszd(),fwLjzDO.getZrzh()));
            fwLjzDO.setBdcdyfwlx(Constants.BDCDYFWLX_DZ);
            fwLjzService.updateFwLjzWithChangeBdcdyh(fwLjzDO,true,null);
            List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwLjzDO.getFwDcbIndex());
            if(CollectionUtils.isNotEmpty(fwHsDOList)){
                for(FwHsDO fwHsDO : fwHsDOList){
                    fwHsDO.setBdcdyh(null);
                    fwHsDO.setBdczt(Constants.BDCZT_BKY);
                    fwHsService.updateFwHs(fwHsDO,true);
                }
            }
        }
    }

    @Override
    public void bgWithBgjl(FwlxBgRequestDTO requestDTO){
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(requestDTO.getFwDcbIndex());
        List<FwHsDO> yFwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwLjzDO.getFwDcbIndex());
        if(fwLjzDO != null){
            FwLjzDO newLjz = new FwLjzDO();
            BeanUtils.copyProperties(fwLjzDO,newLjz);
            newLjz.setFwDcbIndex(null);
            newLjz.setBdcdyh(bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh(newLjz.getLszd(),newLjz.getZrzh()));
            newLjz.setBdcdyfwlx(Constants.BDCDYFWLX_DZ);
            newLjz.setBdczt(Constants.BDCZT_KY);
            Object result = ljzJbxxBgServiceImpl.jbxxBg(requestDTO.getBgbh(),Constants.FWLX_BG,fwLjzDO,newLjz);
            if(result != null && CollectionUtils.isNotEmpty(yFwHsDOList)){
                saveHsbgjlb(requestDTO.getBgbh(),((FwLjzDO)result),yFwHsDOList);
            }
        }
    }

    private void saveHsbgjlb(String bgbh,FwLjzDO newLjz,List<FwHsDO> fwHsDOList){
        if(CollectionUtils.isNotEmpty(fwHsDOList)){
            List<SSjHsbgljbDO> jlList = new ArrayList<>();
            for(FwHsDO fwHsDO : fwHsDOList){
                SSjHsbgljbDO sjHsbgljbDO = new SSjHsbgljbDO();
                // 主键
                sjHsbgljbDO.setHsbgjlbIndex(UUIDGenerator.generate());
                // 变更编号
                sjHsbgljbDO.setBgbh(bgbh);
                // 变更类型
                sjHsbgljbDO.setBglx(Constants.FWLX_BG);
                // 变更日期
                sjHsbgljbDO.setBgrq(new Date());
                // 新 房屋主键
                sjHsbgljbDO.setFwIndex(newLjz.getFwDcbIndex());
                // 新 BDCDYH
                sjHsbgljbDO.setBdcdyh(newLjz.getBdcdyh());
                // 新 BDCDYFWLX
                sjHsbgljbDO.setBdcdyfwlx(Constants.BDCDYFWLX_DZ);
                // 旧 房屋主键
                sjHsbgljbDO.setYfwIndex(fwHsDO.getFwHsIndex());
                // 旧 BDCDYH
                sjHsbgljbDO.setYbdcdyh(fwHsDO.getBdcdyh());
                // 旧 BDCDYFWLX
                sjHsbgljbDO.setYbdcdyfwlx(Constants.BDCDYFWLX_H);
                sjHsbgljbDO.setTcmc(Constants.FW_HS_TABLE);
                jlList.add(sjHsbgljbDO);
            }
            sSjHsbgjlbService.batchInsertHsBgjl(jlList);
        }
    }

}
