package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.SSjHsbgjlbService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-28
 * @description 户室变更记录表服务
 */
@Service
public class SSjHsbgjlbServiceImpl implements SSjHsbgjlbService{

    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param sSjHsbgljbDO
     * @return SSjHsbgljbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 户室变更记录实体
     */
    @Override
    public SSjHsbgljbDO insertHsBgjl(SSjHsbgljbDO sSjHsbgljbDO) {
        if(sSjHsbgljbDO != null){
            if(StringUtils.isBlank(sSjHsbgljbDO.getBgbh())){
                throw new MissingArgumentException("BGBH");
            }
            if(StringUtils.isBlank(sSjHsbgljbDO.getHsbgjlbIndex())){
                sSjHsbgljbDO.setHsbgjlbIndex(UUIDGenerator.generate());
            }
            int result = entityMapper.insertSelective(sSjHsbgljbDO);
            if(result <= 0){
                throw new AppException("保存SSjHsbgljbDO异常");
            }
        }
        return sSjHsbgljbDO;
    }

    /**
     * @param sSjHsbgljbDOList
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量新增户室变更记录
     */
    @Override
    public List<SSjHsbgljbDO> batchInsertHsBgjl(List<SSjHsbgljbDO> sSjHsbgljbDOList) {
        if(CollectionUtils.isNotEmpty(sSjHsbgljbDOList)){
            for(SSjHsbgljbDO sSjHsbgljbDO : sSjHsbgljbDOList){
                if(StringUtils.isBlank(sSjHsbgljbDO.getBgbh())){
                    throw new MissingArgumentException("BGBH");
                }
                if(StringUtils.isBlank(sSjHsbgljbDO.getHsbgjlbIndex())){
                    sSjHsbgljbDO.setHsbgjlbIndex(UUIDGenerator.generate());
                }
            }
            int result = entityMapper.insertBatchSelective(sSjHsbgljbDOList);
            if(result <= 0){
                throw new AppException("批量保存SSjHsbgljbDO异常");
            }
        }
        return sSjHsbgljbDOList;
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwHsIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO>
     * @description 根据现房屋户室主键查询记录表
     */
    @Override
    public List<SSjHsbgljbDO> listHsBgjlByFwHsIndex(String fwHsIndex) {
        Example example = new Example(SSjHsbgljbDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fwIndex", fwHsIndex).andNotEqualTo("bglx", Constants.FWLX_BG);
        return entityMapper.selectByExample(example);
    }
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param yfwHsIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO>
     * @description 根据原房屋户室主键查询记录表
     */
    @Override
    public List<SSjHsbgljbDO> listHsBgjlByYFwHsIndex(String yfwHsIndex) {
        Example example = new Example(SSjHsbgljbDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("yfwIndex", yfwHsIndex);
        return entityMapper.selectByExample(example);
    }
}
