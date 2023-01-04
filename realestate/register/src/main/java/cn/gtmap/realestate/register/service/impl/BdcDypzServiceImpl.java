package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.register.core.mapper.BdcDypzMapper;
import cn.gtmap.realestate.register.service.BdcDypzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/1/17
 * @description 打印配置表业务类
 */
@Service
public class BdcDypzServiceImpl implements BdcDypzService {

    @Autowired
    BdcDypzMapper bdcDypzMapper;
    /**
     * @param bdcDysjPzDO
     * @return List<BdcDysjPzDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取不动产打印配置主表数据
     */
    @Override
    public List<BdcDysjPzDO> listBdcDysjPz(BdcDysjPzDO bdcDysjPzDO) {
        if(bdcDysjPzDO==null){
            throw new MissingArgumentException("BdcDysjPzDO");
        }
        return bdcDypzMapper.listBdcDyPz(bdcDysjPzDO);
    }

    /**
     * @param bdcDysjZbPzDO
     * @return List<BdcDysjZbPzDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印配置子表数据
     */
    @Override
    public List<BdcDysjZbPzDO> listBdcDysjZbPz(BdcDysjZbPzDO bdcDysjZbPzDO) {
        if(bdcDysjZbPzDO==null){
            throw new MissingArgumentException("BdcDysjZbPzDO");
        }
        return bdcDypzMapper.listBdcDyzbPz(bdcDysjZbPzDO);
    }
}
