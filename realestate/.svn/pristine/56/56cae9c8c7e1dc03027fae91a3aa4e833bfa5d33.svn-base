package cn.gtmap.realestate.building.service.impl.bg;

import cn.gtmap.realestate.building.core.service.FwHstService;
import cn.gtmap.realestate.building.service.bg.FwHstBgService;
import cn.gtmap.realestate.common.core.domain.building.FwHstDO;
import cn.gtmap.realestate.common.core.domain.building.HFwHstDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-31
 * @description 变更  户室图相关服务
 */
@Service
public class FwHstBgServiceImpl implements FwHstBgService{

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private FwHstService fwHstService;
    /**
     * @param yfwHstDO
     * @param newfwHstDO
     * @param deleteFlag
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理户室图
     */
    @Override
    public void dealHst(FwHstDO yfwHstDO, FwHstDO newfwHstDO, boolean deleteFlag) {
        if(yfwHstDO != null){
            // 备份
            HFwHstDO hFwHstDO = new HFwHstDO();
            BeanUtils.copyProperties(yfwHstDO,hFwHstDO);
            entityMapper.insertSelective(hFwHstDO);

            // 新增
            if(newfwHstDO != null){
                entityMapper.insertSelective(newfwHstDO);
            }

            // 删除
            fwHstService.deleteFwHstByFwHstIndex(yfwHstDO.getFwHstIndex());
        }
    }
}
