package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.core.service.FwZhsService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.domain.building.FwZhsDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repository;
import cn.gtmap.realestate.common.util.CheckEntityUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/6
 * @description 子户室服务接口实现
 */
@Service
public class FwZhsServiceImpl implements FwZhsService {
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 实体查询Mapper
     */
    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private Repository repo;

    @Autowired
    private FwHsService fwHsService;

    /**
     * @param fwZhsIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据子户室主键删除子户室
     */
    @Override
    public Integer deleteZhsByFwZhsIndex(String fwZhsIndex) {
        Integer deleteResult = 0;
        if (StringUtils.isNotBlank(fwZhsIndex)) {
            deleteResult = entityMapper.deleteByPrimaryKey(FwZhsDO.class, fwZhsIndex);
        }
        return deleteResult;
    }

    /**
     * @param fwHsIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据户室主键删除子户室
     */
    @Override
    public void deleteZhsByFwHsIndex(String fwHsIndex) {
        if (StringUtils.isNotBlank(fwHsIndex)) {
            Example example = new Example(FwZhsDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("fwHsIndex", fwHsIndex);
            entityMapper.deleteByExample(FwZhsDO.class, example);
        }
    }

    /**
     * @param fwZhsDO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 新增子户室
     */
    @Override
    public FwZhsDO insertZhs(FwZhsDO fwZhsDO) {
        if (fwZhsDO != null && (!CheckEntityUtils.checkPk(fwZhsDO))) {
            fwZhsDO.setFwZhsIndex(UUIDGenerator.generate());
        }
        entityMapper.insertSelective(fwZhsDO);
        return fwZhsDO;
    }

    /**
     * @param fwZhsDO
     * @return java.lang.Integer
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 更新子户室
     */
    @Override
    public Integer updateZhs(FwZhsDO fwZhsDO) {
        if (fwZhsDO != null && CheckEntityUtils.checkPkAndFk(fwZhsDO)) {
            return entityMapper.updateByPrimaryKeyNull(fwZhsDO);
        }
        return 0;
    }

    /**
     * @param fwHsIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwZhsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据户室主键查询子户室
     */
    @Override
    public List<FwZhsDO> listFwZhsByFwHsIndex(String fwHsIndex) {
        if (StringUtils.isNotBlank(fwHsIndex)) {
            Example example = new Example(FwZhsDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("fwHsIndex", fwHsIndex);
            return entityMapper.selectByExample(FwZhsDO.class, example);
        }
        return new ArrayList<>(0);
    }

    /**
     * @param fwZhsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwZhsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据主键查询子户室
     */
    @Override
    public FwZhsDO queryFwZhsByPk(String fwZhsIndex) {
        if (StringUtils.isNotBlank(fwZhsIndex)) {
            return entityMapper.selectByPrimaryKey(FwZhsDO.class, fwZhsIndex);
        }
        return null;
    }

    /**
     * @param fwZhsDOList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量增加子户室
     */
    @Override
    public List<FwZhsDO> batchInsert(List<FwZhsDO> fwZhsDOList) {
        List<FwZhsDO> insertList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fwZhsDOList)) {
            for (FwZhsDO fwZhsDO : fwZhsDOList) {
                if (!CheckEntityUtils.checkPk(fwZhsDO)) {
                    fwZhsDO.setFwZhsIndex(UUIDGenerator.generate());
                }
                insertList.add(fwZhsDO);
            }
        }
        if (CollectionUtils.isNotEmpty(insertList)) {
            entityMapper.insertBatchSelective(insertList);
        }
        return insertList;
    }

    /**
     * @param fwZhsDOList
     * @param fwHsIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 关联子户室
     */
    @Override
    public void relevanceZhs(List<FwZhsDO> fwZhsDOList, String fwHsIndex) {
        if (StringUtils.isNotBlank(fwHsIndex)) {
            relevanceOrCancelRelevanceZhs(fwZhsDOList, fwHsIndex);
        }
    }

    /**
     * @param fwZhsDOList
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 取消关联子户室
     */
    @Override
    public void cancelRelevanceZhs(List<FwZhsDO> fwZhsDOList) {
        relevanceOrCancelRelevanceZhs(fwZhsDOList, "");
    }

    /**
     * @param pageable
     * @param fwHsIndex
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.FwZhsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询子户室
     */
    @Override
    public Page<FwZhsDO> listByPage(Pageable pageable, String fwHsIndex) {
        Map<String, String> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(fwHsIndex)) {
            paramMap.put("fwHsIndex", fwHsIndex);
        }
        return repo.selectPaging("listZhsByPageOrder", paramMap, pageable);
    }

    /**
     * @param
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 户室合并处理子户室
     */
    @Override
    public void hsBgDealZhs(FwHsDO fwHsDO, FwYchsDO fwYchsDO, List<FwZhsDO> fwZhsDOList) {
        if (CollectionUtils.isNotEmpty(fwZhsDOList)) {
            List<FwZhsDO> addFwZhsList = new ArrayList<>(fwZhsDOList.size());
            for (FwZhsDO fwZhsDO : fwZhsDOList) {
                if (fwZhsDO != null) {
                    //子户室数据实测没有取预测
                    fwZhsDO.setFwHsIndex(Objects.nonNull(fwHsDO) && StringUtils.isNotBlank(fwHsDO.getFwHsIndex()) ? fwHsDO.getFwHsIndex() : fwYchsDO.getFwHsIndex());
                    fwZhsDO.setFwZhsIndex(UUIDGenerator.generate());
                    addFwZhsList.add(fwZhsDO);
                }
            }
            batchInsert(addFwZhsList);
        }
    }

    /**
     * @param
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取房屋户室合并需要继承的子户室列表（根据配置）
     */
    @Override
    public List<FwZhsDO> listHsHbZhsByIndex(List<String> fwZhsList) {
        List<FwZhsDO> allfwZhsDOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fwZhsList)) {
            for (String zhsIndex : fwZhsList) {
                if (StringUtils.isNotBlank(zhsIndex)) {
                    FwZhsDO fwZhsDO = new FwZhsDO();
                    if (zhsIndex.contains(Constants.FW_HS_TABLE)) {
                        FwHsDO fwHsDO = fwHsService.queryFwHsByIndex(zhsIndex.replace(Constants.FW_HS_TABLE, ""));
                        if (fwHsDO != null) {
                            BeanUtils.copyProperties(fwHsDO, fwZhsDO);
                        }
                    } else if (zhsIndex.contains(Constants.FW_ZHS_TABLE)) {
                        fwZhsDO = queryFwZhsByPk(zhsIndex.replace(Constants.FW_ZHS_TABLE, ""));
                    }
                    allfwZhsDOList.add(fwZhsDO);
                }
            }
        }

        return allfwZhsDOList;

    }

    /**
     * @param fwZhsDO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据子户室信息实体查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByFwZhsDO(FwZhsDO fwZhsDO) {
        List<String> bdcdyhList = new ArrayList<>();
        if (fwZhsDO != null && StringUtils.isNotBlank(fwZhsDO.getFwHsIndex())) {
            FwHsDO fwHsDO = fwHsService.queryFwHsByIndex(fwZhsDO.getFwHsIndex());
            if (fwHsDO != null) {
                bdcdyhList.addAll(fwHsService.listValidBdcdyhByFwHsDO(fwHsDO));
            }
        }
        return bdcdyhList;
    }

    /**
     * @param fwZhsIndexList
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据子户室主键list查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByFwZhsIndexList(List<String> fwZhsIndexList) {
        List<String> bdcdyhList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fwZhsIndexList)) {
            for (String fwZhsIndex : fwZhsIndexList) {
                FwZhsDO fwZhsDO = queryFwZhsByPk(fwZhsIndex);
                if (fwZhsDO != null) {
                    bdcdyhList.addAll(listValidBdcdyhByFwZhsDO(fwZhsDO));
                }
            }
        }
        return bdcdyhList;
    }

    /**
     * @param fwZhsDOList
     * @param fwHsIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 关联/取消关联子户室
     */
    private void relevanceOrCancelRelevanceZhs(List<FwZhsDO> fwZhsDOList, String fwHsIndex) {
        if (CollectionUtils.isNotEmpty(fwZhsDOList)) {
            for (FwZhsDO fwZhsDO : fwZhsDOList) {
                if (fwZhsDO != null && StringUtils.isNotBlank(fwZhsDO.getFwZhsIndex())) {
                    fwZhsDO.setFwHsIndex(fwHsIndex);
                    entityMapper.updateByPrimaryKeySelective(fwZhsDO);
                }
            }
        }
    }


}