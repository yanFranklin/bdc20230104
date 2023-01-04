package cn.gtmap.realestate.building.service.impl.bg.bdcdyfwlxbg;

import cn.gtmap.realestate.building.core.service.BdcdyhService;
import cn.gtmap.realestate.building.core.service.FwLjzService;
import cn.gtmap.realestate.building.core.service.FwXmxxService;
import cn.gtmap.realestate.building.core.service.SSjHsbgjlbService;
import cn.gtmap.realestate.building.service.bg.bdcdyfwlxbg.BdcdyfwlxBgAbstractService;
import cn.gtmap.realestate.building.service.bg.FwBgService;
import cn.gtmap.realestate.building.util.Constants;
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
 * @version 1.0  2019-02-26
 * @description  项目转独幢
 */
@Service
public class BdcdyfwlxBgXmToDzServiceImpl extends BdcdyfwlxBgAbstractService {

    @Autowired
    private FwLjzService fwLjzService;

    @Autowired
    private FwXmxxService fwXmxxService;

    @Resource(name = "xmMsBgServiceImpl")
    private FwBgService xmMsBgServiceImpl;

    @Resource(name = "ljzJbxxBgServiceImpl")
    private FwBgService ljzJbxxBgServiceImpl;

    @Autowired
    private BdcdyhService bdcdyhService;

    @Autowired
    private SSjHsbgjlbService sSjHsbgjlbService;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return void
     * @description 不记录变更记录场景下的变更
     */
    @Override
    public void bg(FwlxBgRequestDTO requestDTO){
        // 查询幢
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(requestDTO.getFwDcbIndex());
        if(fwLjzDO != null && StringUtils.isNotBlank(fwLjzDO.getFwXmxxIndex())){
            // 查询同项目 所有 幢
            List<FwLjzDO> fwLjzDOList = fwLjzService.listLjzByFwXmxxIndex(fwLjzDO.getFwXmxxIndex());
            // 如果 只有这一幢 或者 是处理所有项目下的逻辑幢
            if(fwLjzDOList.size() == 1 || requestDTO.isChangeAllXmLjz()){
                // 循环所有逻辑幢，变更为独幢类型 赋BDCDYH
                for(FwLjzDO ljz : fwLjzDOList){
                    updateFwLjz(ljz);
                }
                // 删除项目及其权利人
                fwXmxxService.deleteFwXmxxByIdxWithRelated(fwLjzDO.getFwXmxxIndex(),true,false);
            }else{
                // 不变更其他逻辑幢场景
                updateFwLjz(fwLjzDO);
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return void
     * @description 记录变更记录场景下的变更
     */
    @Override
    public void bgWithBgjl(FwlxBgRequestDTO requestDTO){
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(requestDTO.getFwDcbIndex());
        FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByPk(fwLjzDO.getFwXmxxIndex());
        if(fwLjzDO != null && fwXmxxDO != null){
            List<FwLjzDO> newFwLjzList = new ArrayList<>();
            // 查询同项目 所有 幢
            List<FwLjzDO> fwLjzDOList = fwLjzService.listLjzByFwXmxxIndex(fwLjzDO.getFwXmxxIndex());
            if(fwLjzDOList.size() == 1 || requestDTO.isChangeAllXmLjz()){
                // 所有逻辑幢走 基本信息变更逻辑
                for(FwLjzDO ljz : fwLjzDOList){
                    newFwLjzList.add(bgFwLjz(requestDTO.getBgbh(),ljz));
                }
                if(fwXmxxDO != null){
                    // 项目走灭失逻辑
                    xmMsBgServiceImpl.msBg(requestDTO.getBgbh(),Constants.FWLX_BG,fwXmxxDO);
                }
            }else{
                // 当前逻辑幢 走基本信息变更逻辑
                newFwLjzList.add(bgFwLjz(requestDTO.getBgbh(),fwLjzDO));
            }
            // 保存变更记录
            saveHsBgjlb(requestDTO.getBgbh(),fwXmxxDO,newFwLjzList);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bgbh
     * @param fwXmxxDO
     * @param newFwLjzList
     * @return void
     * @description 保存户室变更记录表
     */
    private void saveHsBgjlb(String bgbh,FwXmxxDO fwXmxxDO,List<FwLjzDO> newFwLjzList){
        if(CollectionUtils.isNotEmpty(newFwLjzList) && fwXmxxDO != null){
            for(FwLjzDO fwLjzDO : newFwLjzList){
                if(fwLjzDO != null){
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
                    sjHsbgljbDO.setFwIndex(fwLjzDO.getFwDcbIndex());
                    // 新 BDCDYH
                    sjHsbgljbDO.setBdcdyh(fwLjzDO.getBdcdyh());
                    // 新 BDCDYFWLX
                    sjHsbgljbDO.setBdcdyfwlx(fwLjzDO.getBdcdyfwlx());
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
    }
    private void updateFwLjz(FwLjzDO ljz){
        ljz.setFwXmxxIndex(null);
        ljz.setXmmc(null);
        ljz.setBdcdyfwlx(Constants.BDCDYFWLX_DZ);
        String ybdcdyh = ljz.getBdcdyh();
        if(StringUtils.isBlank(ybdcdyh)){
            ljz.setBdcdyh(bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh(ljz.getLszd(),ljz.getZrzh()));
        }
        fwLjzService.updateFwLjzWithChangeBdcdyh(ljz,true,ybdcdyh);
    }

    private FwLjzDO bgFwLjz(String bgbh,FwLjzDO ljz){
        FwLjzDO newLjz = new FwLjzDO();
        BeanUtils.copyProperties(ljz,newLjz);
        newLjz.setFwDcbIndex(null);
        newLjz.setBdcdyfwlx(Constants.BDCDYFWLX_DZ);
        newLjz.setFwXmxxIndex(null);
        newLjz.setXmmc(null);
        Object result = ljzJbxxBgServiceImpl.jbxxBg(bgbh,Constants.FWLX_BG,ljz,newLjz);
        if(result != null){
            return (FwLjzDO) result;
        }
        return null;
    }

}
