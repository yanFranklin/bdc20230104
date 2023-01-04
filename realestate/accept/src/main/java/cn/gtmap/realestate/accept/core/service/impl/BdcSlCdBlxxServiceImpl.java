package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlCdBlxxService;
import cn.gtmap.realestate.common.core.domain.BdcCdBlxxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/08/12
 * @Description: 补录信息实现方法
 */
@Service
public class BdcSlCdBlxxServiceImpl implements BdcSlCdBlxxService {
    @Autowired
    private EntityMapper entityMapper;
    /**
     * @param bdcCdBlxxDO
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 查询补录信息
     * @date : 2021/08/12
     */
    @Override
    public BdcCdBlxxDO queryBdcBlxx(BdcCdBlxxDO bdcCdBlxxDO) {
        List<BdcCdBlxxDO> bdcCdBlxxDOList = entityMapper.selectByObj(bdcCdBlxxDO);
        if(CollectionUtils.isNotEmpty(bdcCdBlxxDOList)) {
            return bdcCdBlxxDOList.get(0);
        }
        return new BdcCdBlxxDO();
    }

    /**
     * @param bdcCdBlxxDO
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 保存补录信息数据
     * @date : 2021/08/12
     */
    @Override
    public BdcCdBlxxDO saveBdcBlxx(BdcCdBlxxDO bdcCdBlxxDO) {
        if(StringUtils.isBlank(bdcCdBlxxDO.getBlxxid())) {
            bdcCdBlxxDO.setBlxxid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcCdBlxxDO);

        } else {
            entityMapper.updateByPrimaryKey(bdcCdBlxxDO);
        }
        return bdcCdBlxxDO;
    }

    /**
     * @param blxxid
     * @param xmid
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 删除补录信息
     * @date : 2021/08/12
     */
    @Override
    public int deleteBdcBlxx(String blxxid, String xmid) {
        if(StringUtils.isNotBlank(blxxid)) {
            return entityMapper.deleteByPrimaryKey(BdcCdBlxxDO.class,blxxid);
        } else if(StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcCdBlxxDO.class);
            example.createCriteria().andEqualTo("xmid",xmid);
            return entityMapper.deleteByExample(example);
        } else {
            return -1;
        }
    }
}
