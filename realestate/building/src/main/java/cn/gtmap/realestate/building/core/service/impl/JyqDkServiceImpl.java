package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.JyqDkService;
import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.common.core.domain.building.JyqDkDcbDO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2020/11/6
 * @description 经营权地块调查表
 */
@Service
public class JyqDkServiceImpl implements JyqDkService {
    @Autowired
    BdcdyService bdcdyService;


    /**
     * @param bdcdyh 不动产单元号
     * @return cn.gtmap.realestate.common.core.domain.building.JyqDkDcbDO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据不动产单元号查询经营权地块
     */
    @Override
    public JyqDkDcbDO queryJyqDkDcbByBdcdyh(String bdcdyh) {
        return bdcdyService.queryDjxxByBdcdyh(bdcdyh, JyqDkDcbDO.class);
    }


}