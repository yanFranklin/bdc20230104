package cn.gtmap.realestate.building.service.impl.bg;

import cn.gtmap.realestate.building.core.service.FwFcqlrService;
import cn.gtmap.realestate.building.service.bg.FwFcQlrBgService;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.HFwFcqlrDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-31
 * @description 变更处理权利人
 */
@Service
public class FwFcQlrBgServiceImpl implements FwFcQlrBgService{

    @Autowired
    private FwFcqlrService fwFcqlrService;

    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param fwIndex
     * @param yfwFcqlrDOList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理权利人
     */
    @Override
    public void dealQlr(String bgbh,String fwIndex, List<FwFcqlrDO> yfwFcqlrDOList,List<FwFcqlrDO> newFwFcQlrList) {
        if(StringUtils.isNotBlank(fwIndex) && CollectionUtils.isNotEmpty(yfwFcqlrDOList)){
            // 备份
            bak(bgbh,yfwFcqlrDOList);
            // 新增
            if(CollectionUtils.isNotEmpty(newFwFcQlrList)){
                insert(newFwFcQlrList);
            }
            // 删除
            fwFcqlrService.deleteFcqlrByFwIndex(fwIndex);
        }
    }

    private void insert(List<FwFcqlrDO> newFwFcQlrList){
        for(FwFcqlrDO fwFcqlrDO : newFwFcQlrList){
            fwFcqlrDO.setFwFcqlrIndex(UUIDGenerator.generate());
        }
        fwFcqlrService.batchInsertFwFcQlr(newFwFcQlrList);
    }

    private void bak(String bgbh,List<FwFcqlrDO> yfwFcqlrDOList){
        List<HFwFcqlrDO> bakList = new ArrayList<>();
        for(FwFcqlrDO fwFcqlrDO : yfwFcqlrDOList){
            HFwFcqlrDO hFwFcqlrDO = new HFwFcqlrDO();
            BeanUtils.copyProperties(fwFcqlrDO,hFwFcqlrDO);
            hFwFcqlrDO.setBgbh(bgbh);
            bakList.add(hFwFcqlrDO);
        }
        entityMapper.insertBatchSelective(bakList);
    }
}
