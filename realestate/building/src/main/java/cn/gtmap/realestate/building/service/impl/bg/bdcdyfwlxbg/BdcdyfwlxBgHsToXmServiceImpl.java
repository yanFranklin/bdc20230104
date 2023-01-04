package cn.gtmap.realestate.building.service.impl.bg.bdcdyfwlxbg;

import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.core.service.FwLjzService;
import cn.gtmap.realestate.building.core.service.FwXmxxService;
import cn.gtmap.realestate.building.core.service.SSjHsbgjlbService;
import cn.gtmap.realestate.building.service.bg.FwBgService;
import cn.gtmap.realestate.building.service.bg.bdcdyfwlxbg.BdcdyfwlxBgAbstractService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO;
import cn.gtmap.realestate.common.core.dto.building.FwlxBgRequestDTO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-27
 * @description
 */
@Service
public class BdcdyfwlxBgHsToXmServiceImpl extends BdcdyfwlxBgAbstractService {

    @Autowired
    private FwLjzService fwLjzService;

    @Autowired
    private FwHsService fwHsService;

    @Autowired
    private FwXmxxService fwXmxxService;

    @Resource(name = "ljzJbxxBgServiceImpl")
    private FwBgService ljzJbxxBgServiceImpl;

    @Autowired
    private SSjHsbgjlbService sSjHsbgjlbService;

    @Override
    public void bg(FwlxBgRequestDTO requestDTO){
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(requestDTO.getFwDcbIndex());
        FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByPk(requestDTO.getFwXmxxIndex());
        if(fwLjzDO != null && fwXmxxDO != null){
            // 处理幢下的户室
            List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwLjzDO.getFwDcbIndex());
            if(CollectionUtils.isNotEmpty(fwHsDOList)){
                for(FwHsDO fwHsDO : fwHsDOList){
                    fwHsDO.setBdcdyh(null);
                    fwHsDO.setBdczt(Constants.BDCZT_BKY);
                    fwHsService.updateFwHs(fwHsDO,true);
                }
            }
            // 更新幢
            fwLjzDO.setXmmc(fwXmxxDO.getXmmc());
            fwLjzDO.setBdcdyfwlx(Constants.BDCDYFWLX_XMNDZ);
            if(StringUtils.isNotBlank(requestDTO.getFwXmxxIndex())){
                // 挂接项目 场景
                fwLjzDO.setFwXmxxIndex(requestDTO.getFwXmxxIndex());
            }
            fwLjzService.updateLjz(fwLjzDO,true);
        }
    }

    @Override
    public void bgWithBgjl(FwlxBgRequestDTO requestDTO){
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(requestDTO.getFwDcbIndex());
        FwXmxxDO newFwXmxxDO = fwXmxxService.queryXmxxByPk(requestDTO.getFwXmxxIndex());
        List<FwHsDO> yFwHsDOList = fwHsService.listFwHsListByFwDcbIndex(requestDTO.getFwDcbIndex());
        if(fwLjzDO != null && newFwXmxxDO != null){
            FwLjzDO newLjz = new FwLjzDO();
            BeanUtils.copyProperties(fwLjzDO,newLjz);
            newLjz.setFwDcbIndex(null);
            newLjz.setBdcdyh(null);
            newLjz.setBdczt(Constants.BDCZT_BKY);
            newLjz.setBdcdyfwlx(Constants.BDCDYFWLX_XMNDZ);
            newLjz.setFwXmxxIndex(requestDTO.getFwXmxxIndex());
            newLjz.setXmmc(newFwXmxxDO.getXmmc());
            Object result = ljzJbxxBgServiceImpl.jbxxBg(requestDTO.getBgbh(),Constants.FWLX_BG,fwLjzDO,newLjz);
            if(result != null && CollectionUtils.isNotEmpty(yFwHsDOList)){
                saveHsbgjlb(requestDTO.getBgbh(),newFwXmxxDO,yFwHsDOList);
            }
        }
    }

    private void saveHsbgjlb(String bgbh,FwXmxxDO newFwXmxxDO,List<FwHsDO> fwHsDOList){
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
                sjHsbgljbDO.setFwIndex(newFwXmxxDO.getFwXmxxIndex());
                // 新 BDCDYH
                sjHsbgljbDO.setBdcdyh(newFwXmxxDO.getBdcdyh());
                // 新 BDCDYFWLX
                sjHsbgljbDO.setBdcdyfwlx(Constants.BDCDYFWLX_XMNDZ);
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
