package cn.gtmap.realestate.building.service.impl.bg.bdcdyfwlxbg;

import cn.gtmap.realestate.building.core.service.*;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-26
 * @description
 */
@Service
public class BdcdyfwlxBgXmToHsServiceImpl extends BdcdyfwlxBgAbstractService {

    @Autowired
    private FwLjzService fwLjzService;

    @Autowired
    private BdcdyhService bdcdyhService;

    @Autowired
    private FwHsService fwHsService;

    @Autowired
    private FwXmxxService fwXmxxService;

    @Resource(name = "ljzJbxxBgServiceImpl")
    private FwBgService ljzJbxxBgServiceImpl;

    @Resource(name = "xmMsBgServiceImpl")
    private FwBgService xmMsBgServiceImpl;

    @Autowired
    private SSjHsbgjlbService sSjHsbgjlbService;


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return void
     * @description 不记录 变更记录表的 变更
     */
    @Override
    public void bg(FwlxBgRequestDTO requestDTO){
        // 查询幢
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(requestDTO.getFwDcbIndex());
        FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByPk(fwLjzDO.getFwXmxxIndex());
        if(fwXmxxDO != null && fwLjzDO != null && StringUtils.isNotBlank(fwLjzDO.getFwXmxxIndex())){
            // 查询同项目 所有 幢
            List<FwLjzDO> fwLjzDOList = fwLjzService.listLjzByFwXmxxIndex(fwLjzDO.getFwXmxxIndex());
            // 如果 只有这一幢 或者 是处理所有项目下的逻辑幢
            if(fwLjzDOList.size() == 1 || requestDTO.isChangeAllXmLjz()){
                // 循环所有逻辑幢，变更为独幢类型 赋BDCDYH
                for(FwLjzDO ljz : fwLjzDOList){
                    updateLjzWithHs(ljz);
                }
                // 删除项目及其权利人
                fwXmxxService.deleteFwXmxxByIdxWithRelated(fwLjzDO.getFwXmxxIndex(),true,false);
            }else{
                // 不变更其他逻辑幢场景
                updateLjzWithHs(fwLjzDO);
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return void
     * @description 记录变更记录表的场景
     */
    @Override
    public void bgWithBgjl(FwlxBgRequestDTO requestDTO){
        // 查询幢
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(requestDTO.getFwDcbIndex());
        FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByPk(fwLjzDO.getFwXmxxIndex());
        if(fwLjzDO != null && StringUtils.isNotBlank(fwLjzDO.getFwXmxxIndex())){
            // 查询同项目 所有 幢
            List<FwLjzDO> fwLjzDOList = fwLjzService.listLjzByFwXmxxIndex(fwLjzDO.getFwXmxxIndex());
            List<FwHsDO> fwHsDOList = new ArrayList<>();
            // 如果 只有这一幢 或者 是处理所有项目下的逻辑幢
            if(fwLjzDOList.size() == 1 || requestDTO.isChangeAllXmLjz()){
                // 循环所有逻辑幢，变更为独幢类型 赋BDCDYH
                for(FwLjzDO ljz : fwLjzDOList){
                    fwHsDOList.addAll(bgLjzWithHs(requestDTO.getBgbh(),ljz));
                }
                if(fwXmxxDO != null){
                    // 项目走灭失逻辑
                    xmMsBgServiceImpl.msBg(requestDTO.getBgbh(),Constants.FWLX_BG,fwXmxxDO);
                }
                // 删除项目及其权利人
                fwXmxxService.deleteFwXmxxByIdxWithRelated(fwLjzDO.getFwXmxxIndex(),true,false);
            }else{
                // 不变更其他逻辑幢场景
                fwHsDOList.addAll(bgLjzWithHs(requestDTO.getBgbh(),fwLjzDO));
            }

            // 生成变更记录
            if(CollectionUtils.isNotEmpty(fwHsDOList)){
                saveHsbgjlb(requestDTO.getBgbh(),fwXmxxDO,fwHsDOList);
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgbh
     * @param fwXmxxDO
     * @param fwHsDOList
     * @return void
     * @description 保存变更记录
     */
    private void saveHsbgjlb(String bgbh,FwXmxxDO fwXmxxDO,List<FwHsDO> fwHsDOList){
        if(CollectionUtils.isNotEmpty(fwHsDOList) && fwXmxxDO != null){
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
                sjHsbgljbDO.setYfwIndex(fwXmxxDO.getFwXmxxIndex());
                // 旧 BDCDYH
                sjHsbgljbDO.setYbdcdyh(fwXmxxDO.getBdcdyh());
                // 旧 BDCDYFWLX
                sjHsbgljbDO.setYbdcdyfwlx(Constants.BDCDYFWLX_XMNDZ);
                sjHsbgljbDO.setTcmc(Constants.FW_XMXX_TABLE);
                sSjHsbgjlbService.insertHsBgjl(sjHsbgljbDO);
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgbh
     * @param ljz
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @description 变更逻辑幢 和 逻辑幢下户室
     */
    private List<FwHsDO> bgLjzWithHs(String bgbh,FwLjzDO ljz){
        FwLjzDO newLjz = new FwLjzDO();
        BeanUtils.copyProperties(ljz,newLjz);
        newLjz.setFwDcbIndex(null);
        newLjz.setBdcdyfwlx(Constants.BDCDYFWLX_H);
        newLjz.setFwXmxxIndex(null);
        newLjz.setXmmc(null);
        Object result = ljzJbxxBgServiceImpl.jbxxBg(bgbh,Constants.FWLX_BG,ljz,newLjz);
        if(result != null){
            List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(((FwLjzDO) result).getFwDcbIndex());
            if(CollectionUtils.isNotEmpty(fwHsDOList)){
                return fwHsDOList;
            }
        }
        return Collections.emptyList();
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ljz
     * @return void
     * @description 更新逻辑幢
     */
    private void updateLjzWithHs(FwLjzDO ljz){
        List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(ljz.getFwDcbIndex());
        if(CollectionUtils.isNotEmpty(fwHsDOList)
                && StringUtils.isNotBlank(ljz.getLszd())
                && StringUtils.isNotBlank(ljz.getZrzh())){
            for(FwHsDO fwHsDO : fwHsDOList){
                fwHsDO.setBdcdyh(bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh(ljz.getLszd(),ljz.getZrzh()));
                fwHsService.updateFwHsWithChangeBdcdyh(fwHsDO,true,null);
            }
        }
        ljz.setFwXmxxIndex(null);
        ljz.setXmmc(null);
        ljz.setBdcdyfwlx(Constants.BDCDYFWLX_H);
        fwLjzService.updateLjz(ljz,true);
    }

}
