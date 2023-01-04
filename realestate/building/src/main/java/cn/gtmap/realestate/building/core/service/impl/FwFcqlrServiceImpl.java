package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.TzmUtils;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.building.core.service.FwFcqlrService;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjdcbFwQlrResponseDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CheckEntityUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/6
 * @description 权利人服务接口实现
 */
@Service
public class FwFcqlrServiceImpl implements FwFcqlrService {
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 实体查询Mapper
     */
    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private Repo repo;

    @Autowired
    private BdcdyService bdcdyService;

    /**
     * @param fwFcqlrIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过权利人主键删除权利人
     */
    @Override
    public Integer deleteFcqlrByFwFcqlrIndex(String fwFcqlrIndex) {
        Integer deleteResult = 0;
        if (StringUtils.isNotBlank(fwFcqlrIndex)) {
            deleteResult = entityMapper.deleteByPrimaryKey(FwFcqlrDO.class, fwFcqlrIndex);
        }
        return deleteResult;
    }

    /**
     * @param fwIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过房屋户室主键删除权利人
     */
    @Override
    public void deleteFcqlrByFwIndex(String fwIndex) {
        if (StringUtils.isNotBlank(fwIndex)) {
            Example example = new Example(FwFcqlrDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("fwIndex", fwIndex);
            entityMapper.deleteByExample(FwFcqlrDO.class, example);
        }
    }

    /**
     * @param fwFcqlrDOList
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 新增房屋房产权利人
     */
    @Override
    public List<FwFcqlrDO> batchInsertFwFcQlr(List<FwFcqlrDO> fwFcqlrDOList) {
        List<FwFcqlrDO> insertList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fwFcqlrDOList)) {
            for (FwFcqlrDO fwFcqlrDO : fwFcqlrDOList) {
                if (!CheckEntityUtils.checkPk(fwFcqlrDO)) {
                    fwFcqlrDO.setFwFcqlrIndex(UUIDGenerator.generate());
                }
                insertList.add(fwFcqlrDO);
            }
            if (CollectionUtils.isNotEmpty(insertList)) {
                // 批量新增
                entityMapper.insertBatchSelective(insertList);
            }
        }
        return insertList;
    }

    /**
     * @param fwFcqlrDO
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 新增房屋房产权利人（单个）
     */
    @Override
    public FwFcqlrDO insertFwFcQlr(FwFcqlrDO fwFcqlrDO) {
        if (fwFcqlrDO != null && (!CheckEntityUtils.checkPk(fwFcqlrDO))) {
            fwFcqlrDO.setFwFcqlrIndex(UUIDGenerator.generate());
        }
        entityMapper.insertSelective(fwFcqlrDO);
        return fwFcqlrDO;
    }

    /**
     * @param fwFcqlrDO
     * @param updateNull true表示空字段更新，false表示空字段不更新
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改房屋房产权利人（单个）
     */
    @Override
    public Integer updateFwFcQlr(FwFcqlrDO fwFcqlrDO, boolean updateNull) {
        Integer updateResult = 0;
        if (fwFcqlrDO != null && StringUtils.isNotBlank(fwFcqlrDO.getFwFcqlrIndex())) {
            if (updateNull) {
                updateResult = entityMapper.updateByPrimaryKeyNull(fwFcqlrDO);
            } else {
                updateResult = entityMapper.updateByPrimaryKeySelective(fwFcqlrDO);
            }
        }
        return updateResult;
    }

    /**
     * @param fwFcqlrDOList
     * @param updateNull     true表示空字段更新，false表示空字段不更新
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改房屋房产权利人（批量）
     */
    @Override
    public void batchUpdateFwFcQlr(List<FwFcqlrDO> fwFcqlrDOList, boolean updateNull) {
        if (CollectionUtils.isNotEmpty(fwFcqlrDOList)) {
            for (FwFcqlrDO fwFcqlrDO : fwFcqlrDOList) {
                updateFwFcQlr(fwFcqlrDO, updateNull);
            }
        }
    }

    /**
     * @param fwHsIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据fwHsIndex查询权利人信息
     */
    @Override
    public List<FwFcqlrDO> listFwFcQlrByFwIndex(String fwHsIndex) {
        if (StringUtils.isNotBlank(fwHsIndex)) {
            Example example = new Example(FwFcqlrDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("fwIndex", fwHsIndex);
            return entityMapper.selectByExample(example);
        }
        return new ArrayList<>(0);
    }

    /**
     * @param fwIndex
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据fwIndex 聚合 QLR
     */
    @Override
    public String wmConcatQlrByFwIndex(String fwIndex) {
        String qlr = "";
        if (StringUtils.isNotBlank(fwIndex)) {
            List<FwFcqlrDO> qlrList = listFwFcQlrByFwIndex(fwIndex);
            List<String> qlrmcList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(qlrList)) {
                for (FwFcqlrDO fwFcqlrDO : qlrList) {
                    if(StringUtils.isNotBlank(fwFcqlrDO.getQlr())){
                        qlrmcList.add(fwFcqlrDO.getQlr());
                    }
                }
            }
            if(CollectionUtils.isNotEmpty(qlrmcList)){
                qlr = BuildingUtils.wmQlrMcWithList(qlrmcList);
            }
        }
        return qlr;
    }

    /**
     * @param fwFcqlrIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据主键查询房屋房产权利人
     */
    @Override
    public FwFcqlrDO queryFwFcByPk(String fwFcqlrIndex) {
        if (StringUtils.isNotBlank(fwFcqlrIndex)) {
            return entityMapper.selectByPrimaryKey(FwFcqlrDO.class, fwFcqlrIndex);
        }
        return null;
    }

    /**
     * @param bdcdyh
     * @param fwFcQlrDO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 处理 房屋房产权利人的外键
     */
    @Override
    public void setFwFcQlrFkVal(String bdcdyh, FwFcqlrDO fwFcQlrDO) {
        if(StringUtils.isNotBlank(bdcdyh)){
            BdcdyResponseDTO bdcdyResponseDTO = bdcdyService.queryBdcdy(bdcdyh,null);
            if(bdcdyResponseDTO != null
                    && StringUtils.isNotBlank(bdcdyResponseDTO.getDjid())){
                fwFcQlrDO.setFwIndex(bdcdyResponseDTO.getDjid());
            }
        }
    }

    /**
     * @param bdcdyh
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 判断是否 应该有 FWFCQLR实体
     */
    @Override
    public boolean checkNeedFwFcQlr(String bdcdyh) {
        return TzmUtils.isFwBdcdy(bdcdyh);
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param pageable
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.DjdcbFwQlrResponseDTO>
     * @description 权利人信息
     */
    @Override
    public Page<DjdcbFwQlrResponseDTO> listQlrByPageJson(Pageable pageable, Map map) {
        return repo.selectPaging("listQlrByPageOrder", map, pageable);
    }

    /**
     * @param fwFcqlrDOList
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量验证外键
     */
    @Override
    public boolean validateBatchFk(List<FwFcqlrDO> fwFcqlrDOList) {
        boolean flag = true;
        for (FwFcqlrDO qlr : fwFcqlrDOList) {
            flag = CheckEntityUtils.checkFk(qlr);
            if (!flag) {
                break;
            }
        }
        return flag;
    }

    /**
     * @param fwFcqlrDOList
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量验证主键和外键
     */
    @Override
    public boolean validateBatchPKAndFk(List<FwFcqlrDO> fwFcqlrDOList) {
        boolean flag = true;
        for (FwFcqlrDO qlr : fwFcqlrDOList) {
            flag = CheckEntityUtils.checkPkAndFk(qlr);
            if (!flag) {
                break;
            }
        }
        return flag;
    }
}