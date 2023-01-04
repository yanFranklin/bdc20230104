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
public class BdcdyfwlxBgDzToHsServiceImpl extends BdcdyfwlxBgAbstractService {

    @Override
    public void bg(FwlxBgRequestDTO requestDTO){
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(requestDTO.getFwDcbIndex());
        if(fwLjzDO != null){
            fwLjzDO.setBdcdyh(null);
            fwLjzDO.setBdczt(Constants.BDCZT_BKY);
            fwLjzDO.setBdcdyfwlx(Constants.BDCDYFWLX_H);
            fwLjzService.updateLjz(fwLjzDO,true);
            List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwLjzDO.getFwDcbIndex());
            if(CollectionUtils.isNotEmpty(fwHsDOList)){
                for(FwHsDO fwHsDO : fwHsDOList){
                    fwHsDO.setBdcdyh(bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh(fwLjzDO.getLszd(),fwLjzDO.getZrzh()));
                    fwHsService.updateFwHsWithChangeBdcdyh(fwHsDO,true,null);
                }
            }
        }
    }

    @Override
    public void bgWithBgjl(FwlxBgRequestDTO requestDTO){
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(requestDTO.getFwDcbIndex());
        if(fwLjzDO != null){
            FwLjzDO newLjz = new FwLjzDO();
            BeanUtils.copyProperties(fwLjzDO,newLjz);
            newLjz.setFwDcbIndex(null);
            newLjz.setBdcdyh(null);
            newLjz.setBdczt(Constants.BDCZT_BKY);
            newLjz.setBdcdyfwlx(Constants.BDCDYFWLX_H);
            Object result = ljzJbxxBgServiceImpl.jbxxBg(requestDTO.getBgbh(),Constants.FWLX_BG,fwLjzDO,newLjz);
            if(result != null){
                List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(((FwLjzDO)result).getFwDcbIndex());
                // 记录户室变更记录表
                saveHsbgjlb(requestDTO.getBgbh(),fwLjzDO,fwHsDOList);
            }
        }
    }

    private void saveHsbgjlb(String bgbh, FwLjzDO fwLjzDO,List<FwHsDO> fwHsDOList){
        if(CollectionUtils.isNotEmpty(fwHsDOList)){
            List<SSjHsbgljbDO> list = new ArrayList<>();
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
                sjHsbgljbDO.setFwIndex(fwHsDO.getFwHsIndex());
                // 新 BDCDYH
                sjHsbgljbDO.setBdcdyh(fwHsDO.getBdcdyh());
                // 新 BDCDYFWLX
                sjHsbgljbDO.setBdcdyfwlx(Constants.BDCDYFWLX_H);
                // 旧 房屋主键
                sjHsbgljbDO.setYfwIndex(fwLjzDO.getFwDcbIndex());
                // 旧 BDCDYH
                sjHsbgljbDO.setYbdcdyh(fwLjzDO.getBdcdyh());
                // 旧 BDCDYFWLX
                sjHsbgljbDO.setYbdcdyfwlx(Constants.BDCDYFWLX_DZ);
                sjHsbgljbDO.setTcmc(Constants.FW_LJZ);
                list.add(sjHsbgljbDO);
            }
            sSjHsbgjlbService.batchInsertHsBgjl(list);
        }
    }
}
