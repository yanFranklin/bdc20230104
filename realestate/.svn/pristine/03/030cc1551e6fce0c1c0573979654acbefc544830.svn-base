package cn.gtmap.realestate.building.service.impl.bg;

import cn.gtmap.realestate.building.core.bo.FwBgBO;
import cn.gtmap.realestate.building.service.bg.FwXmxxBgAbstractService;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwHstDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import cn.gtmap.realestate.common.core.ex.IllegalArgumentException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-01
 * @description
 */
@Service
public class FwXmxxMsServiceImpl extends FwXmxxBgAbstractService{

    /**
     * @param bgBO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 验证参数
     */
    @Override
    public void checkParam(FwBgBO<FwXmxxDO> bgBO) {
        if(StringUtils.isBlank(bgBO.getBgbh())){
            throw new MissingArgumentException("bgbh");
        }
        if(CollectionUtils.isEmpty(bgBO.getyList()) || bgBO.getyList().size() != 1){
            throw new IllegalArgumentException("yFwXmxxList");
        }
    }

    /**
     * @param bgBO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存变更记录
     */
    @Override
    public void saveBgjl(FwBgBO<FwXmxxDO> bgBO) {
        saveSingleHsbgjlb(bgBO.getBgbh(), bgBO.getBglx(),bgBO.getyList().get(0),new FwXmxxDO());
    }

    /**
     * @param bgBO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理FWFCQLR
     */
    @Override
    public void dealFwFcqlr(FwBgBO<FwXmxxDO> bgBO) {
        FwXmxxDO yFwXmxxDO = bgBO.getyList().get(0);
        List<FwFcqlrDO> yQlrList = fwFcqlrService.listFwFcQlrByFwIndex(yFwXmxxDO.getFwXmxxIndex());
        if(CollectionUtils.isNotEmpty(yQlrList)){
            fwFcQlrBgService.dealQlr(bgBO.getBgbh(),yFwXmxxDO.getFwXmxxIndex(),yQlrList,null);
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
        for(FwXmxxDO yFwXmxxDO : bgBO.getyList()){
            FwHstDO fwHstDO = fwHstService.queryFwHstByIndex(yFwXmxxDO.getFwXmxxIndex());
            if(fwHstDO != null){
                fwHstBgService.dealHst(fwHstDO,null,true);
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
        List<FwLjzDO> fwLjzDOList = fwLjzService.listLjzByFwXmxxIndex(bgBO.getyList().get(0).getFwXmxxIndex());
        if(CollectionUtils.isNotEmpty(fwLjzDOList)){
            for(FwLjzDO fwLjzDO : fwLjzDOList){
                fwLjzMsService.msBg(bgBO.getBgbh(),bgBO.getBglx(),fwLjzDO);
            }
        }
    }
}
