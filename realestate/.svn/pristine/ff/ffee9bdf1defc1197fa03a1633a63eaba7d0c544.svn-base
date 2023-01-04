package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlFwtcxxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwtcxxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/12/4
 * @description 不动产受理房屋套次信息服务
 */
@Service
public class BdcSlFwtcxxServiceImpl implements BdcSlFwtcxxService {

    @Autowired
    EntityMapper entityMapper;

    /**
     * @param xmid 项目ID
     * @return 房屋套次信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据xmid获取房屋套次信息
     */
    @Override
    public List<BdcSlFwtcxxDO> listBdcSlFwtcxxByXmid(String xmid,String sqrlb){
        List<BdcSlFwtcxxDO> bdcSlFwtcxxDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlFwtcxxDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("xmid", xmid);
            if(StringUtils.isNotBlank(sqrlb)){
                criteria.andEqualTo("sqrlb", sqrlb);
            }
            bdcSlFwtcxxDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlFwtcxxDOList)) {
            bdcSlFwtcxxDOList = Collections.emptyList();
        }
        return bdcSlFwtcxxDOList;

    }

    /**
     * @param xmid 项目ID
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据xmid删除房屋套次信息
     */
    @Override
    public int deleteBdcSlFwtcxxByXmid(String xmid,String sqrlb,String sqrid){
        int result = 0;
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlFwtcxxDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("xmid", xmid);
            if(StringUtils.isNotBlank(sqrlb)){
                criteria.andEqualTo("sqrlb", sqrlb);
            }
            if(StringUtils.isNotBlank(sqrid)){
                criteria.andEqualTo("sqrid", sqrid);
            }
            result =entityMapper.deleteByExample(example);
        }
        return result;

    }

    @Override
    public int addBdcSlFwtcxx(List<BdcSlFwtcxxDO> bdcSlFwtcxxDOList,String xmid,String sqrlb){
        if(CollectionUtils.isEmpty(bdcSlFwtcxxDOList)){
            return 0;
        }

        for (BdcSlFwtcxxDO bdcSlFwtcxxDO : bdcSlFwtcxxDOList){
            if (StringUtils.isEmpty(bdcSlFwtcxxDO.getFwtcxxid())) {
                bdcSlFwtcxxDO.setFwtcxxid(UUIDGenerator.generate16());

            }
            if(StringUtils.isNotBlank(xmid)) {
                bdcSlFwtcxxDO.setXmid(xmid);
            }
            if(StringUtils.isNotBlank(sqrlb)){
                bdcSlFwtcxxDO.setSqrlb(sqrlb);
            }
        }
        return entityMapper.insertBatchSelective(bdcSlFwtcxxDOList);

    }
}
