package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.service.BdcXtYlzhService;
import cn.gtmap.realestate.common.core.domain.BdcXtYlzhDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/5
 * @description 预留证号DAO操作
 */
@Service
public class BdcXtYlzhServiceImpl implements BdcXtYlzhService {
    /**
     * ORM操作
     */
    @Autowired
    private EntityMapper entityMapper;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zsid 证书ID
     * @return {BdcXtYlzhDO} 预留证号
     * @description 获取证书对应的预留证号
     */
    @Override
    public BdcXtYlzhDO queryBdcXtYlzh(String zsid) {
        if(StringUtils.isBlank(zsid)){
            return null;
        }

        Example example = new Example(BdcXtYlzhDO.class);
        example.createCriteria().andEqualTo("zsid", zsid);
        List<BdcXtYlzhDO> bdcXtYlzhList = entityMapper.selectByExampleNotNull(example);

        if(CollectionUtils.isEmpty(bdcXtYlzhList)){
            return null;
        }
        return (BdcXtYlzhDO) CollectionUtils.get(bdcXtYlzhList, 0);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXtYlzhDO 预留证号信息实体
     * @return {int} 更新记录条数
     * @description 更新预留证号使用情况
     */
    @Override
    public int updateBdcXtYlzhSyqk(BdcXtYlzhDO bdcXtYlzhDO) {
        if(null == bdcXtYlzhDO || StringUtils.isBlank(bdcXtYlzhDO.getYlzhid())){
            return 0;
        }

        bdcXtYlzhDO.setGxrq(new Date());
        return entityMapper.updateByPrimaryKeySelective(bdcXtYlzhDO);
    }
}
