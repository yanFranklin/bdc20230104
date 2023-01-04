package cn.gtmap.realestate.building.service.impl.bg.bdcdyfwlxbg;

import cn.gtmap.realestate.building.service.bg.bdcdyfwlxbg.BdcdyfwlxBgAbstractService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO;
import cn.gtmap.realestate.common.core.dto.building.FwlxBgRequestDTO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-26
 * @description
 */
@Service
public class BdcdyfwlxBgDzToXmServiceImpl extends BdcdyfwlxBgAbstractService {

    @Override
    public void bg(FwlxBgRequestDTO requestDTO){
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(requestDTO.getFwDcbIndex());
        FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByPk(requestDTO.getFwXmxxIndex());
        if(fwLjzDO != null && fwXmxxDO != null){
            fwLjzDO.setBdcdyh(null);
            fwLjzDO.setBdczt(Constants.BDCZT_BKY);
            fwLjzDO.setBdcdyfwlx(Constants.BDCDYFWLX_XMNDZ);
            fwLjzDO.setFwXmxxIndex(requestDTO.getFwXmxxIndex());
            fwLjzDO.setXmmc(fwXmxxDO.getXmmc());
            fwLjzService.updateLjz(fwLjzDO,true);
        }
    }

    @Override
    public void bgWithBgjl(FwlxBgRequestDTO requestDTO){
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(requestDTO.getFwDcbIndex());
        FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByPk(requestDTO.getFwXmxxIndex());
        if(fwLjzDO != null && fwXmxxDO != null){
            FwLjzDO newLjz = new FwLjzDO();
            BeanUtils.copyProperties(fwLjzDO,newLjz);
            newLjz.setFwDcbIndex(null);
            newLjz.setBdcdyh(null);
            newLjz.setBdczt(Constants.BDCZT_BKY);
            newLjz.setBdcdyfwlx(Constants.BDCDYFWLX_XMNDZ);
            newLjz.setFwXmxxIndex(requestDTO.getFwXmxxIndex());
            newLjz.setXmmc(fwXmxxDO.getXmmc());
            Object result = ljzJbxxBgServiceImpl.jbxxBg(requestDTO.getBgbh(),Constants.FWLX_BG,fwLjzDO,newLjz);
            if(result != null){
                // 记录户室变更记录表
                saveHsbgjlb(requestDTO.getBgbh(),fwLjzDO,fwXmxxDO);
            }
        }
    }

    private void saveHsbgjlb(String bgbh, FwLjzDO fwLjzDO, FwXmxxDO newFwXmxxDO){
        if(newFwXmxxDO != null){
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
            sjHsbgljbDO.setYfwIndex(fwLjzDO.getFwDcbIndex());
            // 旧 BDCDYH
            sjHsbgljbDO.setYbdcdyh(fwLjzDO.getBdcdyh());
            // 旧 BDCDYFWLX
            sjHsbgljbDO.setYbdcdyfwlx(Constants.BDCDYFWLX_DZ);
            sjHsbgljbDO.setTcmc(Constants.FW_LJZ);
            sSjHsbgjlbService.insertHsBgjl(sjHsbgljbDO);
        }
    }
}
