package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcLcTsjfGxMapper;
import cn.gtmap.realestate.accept.core.service.BdcLcTsjfGxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcLcTsjfGxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @program: realestate
 * @description: 流程与推送缴费关系方法实现层
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-09-16 14:00
 **/
@Service
public class BdcLcTsjfGxServiceImpl implements BdcLcTsjfGxService {

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcLcTsjfGxMapper bdcLcTsjfGxMapper;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id查询流程与推送缴费的关系
     * @date : 2021/9/16 13:56
     */
    @Override
    public List<BdcLcTsjfGxDO> listLcTsjfGx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return Collections.emptyList();
        }
        Example example = new Example(BdcLcTsjfGxDO.class);
        example.createCriteria().andEqualTo("gzlslid", gzlslid);
        example.setOrderByClause("xh");
        return entityMapper.selectByExample(example);
    }

    @Override
    public BdcLcTsjfGxDO queryOneLcTsjfGx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID");
        }
        return this.bdcLcTsjfGxMapper.queryOneLcTsjfGx(gzlslid);
    }

    /**
     * @param sfxxid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据收费信息id查询流程推送缴费
     * @date : 2021/10/26 16:51
     */
    @Override
    public List<BdcLcTsjfGxDO> listLcTsjfGxBySfxxid(String sfxxid) {
        if (StringUtils.isBlank(sfxxid)) {
            return Collections.emptyList();
        }
        Example example = new Example(BdcLcTsjfGxDO.class);
        example.createCriteria().andEqualTo("sfxxid", sfxxid);
        example.setOrderByClause("xh");
        return entityMapper.selectByExample(example);
    }

    @Override
    public List<BdcLcTsjfGxDO> listLcTsjfGxByParam(BdcLcTsjfGxDO bdcLcTsjfGxDO) {
        if (Objects.isNull(bdcLcTsjfGxDO)) {
            return Collections.emptyList();
        }
        Example example = new Example(BdcLcTsjfGxDO.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(bdcLcTsjfGxDO.getGzlslid())){
            criteria.andEqualTo("gzlslid", bdcLcTsjfGxDO.getGzlslid());
        }
        if(StringUtils.isNotBlank(bdcLcTsjfGxDO.getTsid())){
            criteria.andEqualTo("tsid", bdcLcTsjfGxDO.getTsid());
        }
        if(StringUtils.isNotBlank(bdcLcTsjfGxDO.getSfxxid())){
            criteria.andEqualTo("sfxxid", bdcLcTsjfGxDO.getSfxxid());
        }
        example.setOrderByClause("xh");
        return entityMapper.selectByExample(example);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id删除流程与推送缴费关系
     * @date : 2021/9/16 13:58
     */
    @Override
    public int deleteLcTsjfGx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return 0;
        }
        Example example = new Example(BdcLcTsjfGxDO.class);
        example.createCriteria().andEqualTo("gzlslid", gzlslid);
        return entityMapper.deleteByExample(example);
    }

    /**
     * @param bdcLcTsjfGxDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量新增流程与推送缴费关系数据
     * @date : 2021/9/16 13:59
     */
    @Override
    public int batchInsertLcTsjfGx(List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList) {
        if (CollectionUtils.isEmpty(bdcLcTsjfGxDOList)) {
            return 0;
        }
        return entityMapper.insertBatchSelective(bdcLcTsjfGxDOList);
    }

    /**
     * @param sfxxidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据收费信息id 删除
     * @date : 2021/10/26 16:20
     */
    @Override
    public int deleteLcTsGxBySfxxid(List<String> sfxxidList, String gzlslid) {
        if (CollectionUtils.isEmpty(sfxxidList) || StringUtils.isBlank(gzlslid)) {
            return 0;
        }
        int count = 0;
        for (String sfxxid : sfxxidList) {
            Example example = new Example(BdcLcTsjfGxDO.class);
            example.createCriteria().andEqualTo("sfxxid", sfxxid).andEqualTo("gzlslid", gzlslid);
            count += entityMapper.deleteByExampleNotNull(example);
        }
        return count;
    }

    /**
     * @param bdcLcTsjfGxDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/11/3 14:31
     */
    @Override
    public void batchUpdateLcTsjfGx(List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList) {
        if (CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)) {
            entityMapper.batchSaveSelective(bdcLcTsjfGxDOList);
        }
    }

    @Override
    public void updateLcTsjfGx(BdcLcTsjfGxDO bdcLcTsjfGxDO) {
        if(StringUtils.isBlank(bdcLcTsjfGxDO.getGxid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到关系ID");
        }
        entityMapper.updateByPrimaryKey(bdcLcTsjfGxDO);
    }

    @Override
    public void clearTsid(String gzlslid) {
        if(StringUtils.isNotBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        this.bdcLcTsjfGxMapper.clearTsid(gzlslid);
    }
}
