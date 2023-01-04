package cn.gtmap.realestate.config.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcGgDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.config.core.service.BdcGgService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/21
 * @description 不动产公告服务
 */
@Service
public class BdcGgServiceImpl implements BdcGgService {

    @Autowired
    private EntityMapper entityMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatchBdcGg(List<BdcGgDO> bdcGgDOList){
        int count =0;
        if(CollectionUtils.isNotEmpty(bdcGgDOList)) {
            List<List> partList = ListUtils.subList(bdcGgDOList, 500);
            for (List data : partList) {
                count +=entityMapper.insertBatchSelective(data);
            }
        }
        return count;
    }

}
