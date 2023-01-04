package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcFdcqFdcqxmDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.init.core.service.BdcFdcqFdcqxmService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/12/17.
 * @description
 */
@Service
public class BdcFdcqFdcqxmServiceImpl  implements BdcFdcqFdcqxmService {

    @Autowired
    EntityMapper entityMapper;

    /**
     * 通过房地产权id获取房地产权多幢项目信息(根据幢号和总层数排序)
     *
     * @param qlid 房地产权id
     * @return List<BdcFdcqFdcqxmDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public List<BdcFdcqFdcqxmDO> queryFdcqxmListByQlid(String qlid) {
        if(StringUtils.isBlank(qlid)){
            return  Collections.emptyList();
        }
        Example example = new Example(BdcFdcqFdcqxmDO.class);
        //排序
        example.setOrderByClause("REGEXP_SUBSTR(zh,'[0-9a-zA-Z]+'),zcs");
        example.createCriteria().andEqualTo("qlid", qlid);
        return entityMapper.selectByExample(example);
    }

    @Override
    public int updateFdcqxmByQlid(String qlid, BdcFdcqFdcqxmDO bdcFdcqFdcqxmDO) {
        if(StringUtils.isBlank(qlid)){
            throw new MissingArgumentException("缺失参数权利ID");
        }
        Example example = new Example(BdcFdcqFdcqxmDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("qlid", qlid);
        return entityMapper.updateByExampleSelectiveNotNull(bdcFdcqFdcqxmDO, example);
    }


}
