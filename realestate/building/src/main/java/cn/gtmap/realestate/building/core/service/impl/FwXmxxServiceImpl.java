package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO;
import cn.gtmap.realestate.common.core.dto.building.XmxxResponseDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.vo.building.FwXmxxBgVO;
import cn.gtmap.realestate.common.core.vo.building.FwXmxxHbVO;
import cn.gtmap.realestate.common.util.CheckEntityUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/5
 * @description 项目信息服务接口实现
 */
@Service
public class FwXmxxServiceImpl implements FwXmxxService {
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询Repo
     */
    @Autowired
    private Repo repo;
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description entityMapper
     */
    @Autowired
    private EntityMapper entityMapper;

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋逻辑幢接口
     */
    @Autowired
    private FwLjzService fwLjzService;
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 权利人接口
     */
    @Autowired
    private FwFcqlrService fwFcqlrService;

    @Autowired
    private BdcdyhService bdcdyhService;

    @Autowired
    private FwHsService fwHsService;

    @Autowired
    private FwKService fwKService;

    @Autowired
    private FwHstService fwHstService;

    /**
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.XmxxResponseDTO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询项目信息
     */
    @Override
    public Page<XmxxResponseDTO> listXmxxByPage(Pageable pageable, Map map) {
        return repo.selectPaging("listXmxxByPageOrder", map, pageable);
    }

    /**
     * @param fwXmxxDO
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 新增项目信息
     */
    @Override
    public FwXmxxDO insertFwXmxx(FwXmxxDO fwXmxxDO) {
        if (fwXmxxDO != null && (!CheckEntityUtils.checkPk(fwXmxxDO))) {
            fwXmxxDO.setFwXmxxIndex(UUIDGenerator.generate());
        }
        if (fwXmxxDO != null &&StringUtils.isBlank(fwXmxxDO.getBdcdyh()) && StringUtils.isNotBlank(fwXmxxDO.getLszd())) {
            fwXmxxDO.setBdcdyh(bdcdyhService.creatXmxxBdcdyhByLszd(fwXmxxDO.getLszd()));
            fwXmxxDO.setBdczt(Constants.BDCZT_KY);
        }
        entityMapper.insertSelective(fwXmxxDO);
        return fwXmxxDO;
    }

    /**
     * @param fwXmxxDO
     * @param updateNull true表示空字段更新，false表示空字段不更新
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改项目信息
     */
    @Override
    public Integer updateFwXmxx(FwXmxxDO fwXmxxDO, boolean updateNull) {
        Integer updateResult = 0;
        if (fwXmxxDO != null && CheckEntityUtils.checkPk(fwXmxxDO)) {
            if (updateNull) {
                updateResult = entityMapper.updateByPrimaryKeyNull(fwXmxxDO);
            } else {
                updateResult = entityMapper.updateByPrimaryKeySelective(fwXmxxDO);
            }
        }
        return updateResult;
    }

    /**
     * @param fwXmxxIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键删除项目信息
     */
    @Override
    public Integer deleteFwXmxxByFwXmxxIndex(String fwXmxxIndex, boolean withRelated) {
        if (withRelated) {
            return deleteFwXmxxByIdxWithRelated(fwXmxxIndex, true, true);
        } else {
            return deleteFwXmxxByIdxWithRelated(fwXmxxIndex, false, false);
        }
    }

    /**
     * @param fwXmxxIndexList
     * @param delFwK
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量删除项目信息
     */
    @Override
    @Transactional
    public void batchDelFwXmxx(List<String> fwXmxxIndexList, boolean delFwK) {
        if (CollectionUtils.isNotEmpty(fwXmxxIndexList)) {
            for (String fwXmxxIndex : fwXmxxIndexList) {
                if (delFwK) {
                    fwKService.deleteFwKByFwXmxxIndex(fwXmxxIndex);
                }
                deleteFwXmxxByFwXmxxIndex(fwXmxxIndex, true);
            }
        }
    }


    /**
     * @param fwXmxxIndex
     * @param withLjz
     * @return java.lang.Integer
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据主键删除项目信息和相关数据 判断是否删除逻辑幢
     */
    @Override
    @Transactional
    public Integer deleteFwXmxxByIdxWithRelated(String fwXmxxIndex, boolean withQlr, boolean withLjz) {
        Integer deleteResult = 0;
        if (StringUtils.isNotBlank(fwXmxxIndex)) {
            // 删除成功后 迁移BDCDYH XSZT表数据
            deleteResult = entityMapper.deleteByPrimaryKey(FwXmxxDO.class, fwXmxxIndex);
            //删除户室图
            fwHstService.deleteFwHstByFwHstIndex(fwXmxxIndex);
            if (withQlr) {
                fwFcqlrService.deleteFcqlrByFwIndex(fwXmxxIndex);
            }
            if (withLjz) {
                List<FwLjzDO> fwLjzDOList = fwLjzService.listLjzByFwXmxxIndex(fwXmxxIndex);
                if (CollectionUtils.isNotEmpty(fwLjzDOList)) {
                    for (FwLjzDO fwLjzDO : fwLjzDOList) {
                        fwLjzService.deleteLjzByFwDcbIndex(fwLjzDO.getFwDcbIndex(), true);
                    }
                }
            }
        }
        return deleteResult;
    }

    /**
     * @param fwXmxxDO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据查询条目删除项目信息
     */
    @Override
    @Transactional
    public Integer deleteFwXmxx(FwXmxxDO fwXmxxDO, boolean withRelated) {
        int result = 0;
        if (fwXmxxDO != null && StringUtils.isNotBlank(fwXmxxDO.getFwXmxxIndex())) {
            result = entityMapper.delete(fwXmxxDO);
            if (withRelated) {
                // 删除权利人
                fwFcqlrService.deleteFcqlrByFwIndex(fwXmxxDO.getFwXmxxIndex());
                // 处理项目下逻辑幢
                List<FwLjzDO> fwLjzDOList = fwLjzService.listLjzByFwXmxxIndex(fwXmxxDO.getFwXmxxIndex());
                if (CollectionUtils.isNotEmpty(fwLjzDOList)) {
                    for (FwLjzDO fwLjzDO : fwLjzDOList) {
                        fwLjzService.deleteLjzByFwDcbIndex(fwLjzDO.getFwDcbIndex(), true);
                    }
                }
            }
        }
        return result;
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 查询项目信息
     */
    @Override
    public FwXmxxDO queryXmxxByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            Example example = new Example(FwXmxxDO.class);
            example.createCriteria().andEqualTo("bdcdyh", bdcdyh);
            List<FwXmxxDO> xmlist = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(xmlist)) {
                return xmlist.get(0);
            }
        }
        return null;
    }

    /**
     * @param fwXmxxIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据主键查询项目信息
     */
    @Override
    public FwXmxxDO queryXmxxByPk(String fwXmxxIndex) {
        if (StringUtils.isNotBlank(fwXmxxIndex)) {
            return entityMapper.selectByPrimaryKey(FwXmxxDO.class, fwXmxxIndex);
        }
        return null;
    }

    /**
     * @param fwXmxxDO
     * @param updateNull
     * @return java.lang.Integer
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 更新 FW_HS表 有BDCDYH变化，并且不知道原有BDCDYH情况
     */
    @Override
    public Integer updateFwXmxxWithChangeBdcdyh(FwXmxxDO fwXmxxDO, boolean updateNull) {
        int result = 0;
        if (fwXmxxDO != null
                && StringUtils.isNotBlank(fwXmxxDO.getFwXmxxIndex())) {
            fwXmxxDO.setBdczt(StringUtils.isNotBlank(fwXmxxDO.getBdcdyh()) ? Constants.BDCZT_KY : Constants.BDCZT_BKY);
            result = updateFwXmxx(fwXmxxDO, updateNull);
        }
        return result;
    }

    /**
     * @param fwXmxxDO
     * @param updateNull
     * @param ybdcdyh
     * @return java.lang.Integer
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 更新 FW_HS表 有BDCDYH变化，且知道原有BDCDYH情况（包括空的情况）
     */
    @Override
    public Integer updateFwXmxxWithChangeBdcdyh(FwXmxxDO fwXmxxDO, boolean updateNull, String ybdcdyh) {
        int result = 0;
        if (fwXmxxDO != null
                && StringUtils.isNotBlank(fwXmxxDO.getFwXmxxIndex())) {
            fwXmxxDO.setBdczt(StringUtils.isNotBlank(fwXmxxDO.getBdcdyh()) ? Constants.BDCZT_KY : Constants.BDCZT_BKY);
            result = updateFwXmxx(fwXmxxDO, updateNull);
        }
        return result;
    }

    /**
     * @param fwXmxxIndex
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 项目信息关联逻辑幢
     */
    @Override
    public Integer relevanceLjz(String fwXmxxIndex, String fwDcbIndex) {
        int result = 0;
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
            if (fwLjzDO != null) {
                if (StringUtils.equals(fwLjzDO.getBdcdyfwlx(), Constants.BDCDYFWLX_DZ)) {
                    //处理逻辑幢
                    fwLjzDO.setBdczt(Constants.BDCZT_BKY);
                    updateBdcxsztToBky(fwLjzDO.getBdcdyh());
                } else if (StringUtils.equals(fwLjzDO.getBdcdyfwlx(), Constants.BDCDYFWLX_H)) {
                    //处理户室
                    List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwLjzDO.getFwDcbIndex());
                    if (CollectionUtils.isNotEmpty(fwHsDOList)) {
                        for (FwHsDO fwHsDO : fwHsDOList) {
                            fwHsDO.setBdczt(Constants.BDCZT_BKY);
                            updateBdcxsztToBky(fwHsDO.getBdcdyh());
                            fwHsService.updateFwHs(fwHsDO, false);
                        }
                    }
                }
                fwLjzDO.setFwXmxxIndex(fwXmxxIndex);
                fwLjzDO.setBdcdyfwlx(Constants.BDCDYFWLX_XMNDZ);
                result = fwLjzService.updateLjz(fwLjzDO, false);
            }
        }
        return result;
    }

    /**
     * @param bdcdyh
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改不动产现势状态为不可用
     */
    private void updateBdcxsztToBky(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            SSjBdcdyhxsztDO sSjBdcdyhxsztDO = new SSjBdcdyhxsztDO();
            sSjBdcdyhxsztDO.setBdcdyh(bdcdyh);
            sSjBdcdyhxsztDO.setBdcdyzt(Constants.BDCZT_BKY);
            entityMapper.updateByPrimaryKeySelective(sSjBdcdyhxsztDO);
        }
    }

    /**
     * @param bdcdyh
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改不动产现势状态为可用
     */
    private void updateBdcxsztToKy(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            SSjBdcdyhxsztDO sSjBdcdyhxsztDO = new SSjBdcdyhxsztDO();
            sSjBdcdyhxsztDO.setBdcdyh(bdcdyh);
            sSjBdcdyhxsztDO.setBdcdyzt(Constants.BDCZT_KY);
            entityMapper.updateByPrimaryKeySelective(sSjBdcdyhxsztDO);
        }
    }

    /**
     * @param fwDcbIndex
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 项目信息取消关联逻辑幢
     */
    @Override
    public Integer cancelRelevanceLjz(String fwDcbIndex, String bdcdyfwlx) {
        int result = 0;
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
        if (fwLjzDO != null) {
            fwLjzDO.setFwXmxxIndex(null);
            fwLjzDO.setBdcdyfwlx(bdcdyfwlx);
            String bdcdyh;
            //取消后为独幢
            if (StringUtils.equals(Constants.BDCDYFWLX_DZ, bdcdyfwlx)) {
                if (StringUtils.isNotBlank(fwLjzDO.getBdcdyh())) {
                    fwLjzDO.setBdczt(Constants.BDCZT_KY);
                    updateBdcxsztToKy(fwLjzDO.getBdcdyh());
                } else {
                    bdcdyh = bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh(fwLjzDO.getLszd(), fwLjzDO.getZrzh());
                    fwLjzDO.setBdcdyh(bdcdyh);
                    fwLjzDO.setBdczt(Constants.BDCZT_KY);
                }
            } else if (StringUtils.equals(Constants.BDCDYFWLX_H, bdcdyfwlx)) {
                //取消后为户
                List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwDcbIndex);
                if (CollectionUtils.isNotEmpty(fwHsDOList)) {
                    for (FwHsDO fwHsDO : fwHsDOList) {
                        if (fwHsDO != null && StringUtils.isNotBlank(fwHsDO.getBdcdyh())) {
                            fwHsDO.setBdczt(Constants.BDCZT_KY);
                            updateBdcxsztToKy(fwHsDO.getBdcdyh());
                        } else if(fwHsDO != null){
                            fwHsDO.setBdczt(Constants.BDCZT_KY);
                            bdcdyh = bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh(fwLjzDO.getLszd(), fwLjzDO.getZrzh());
                            fwHsDO.setBdcdyh(bdcdyh);
                            fwHsService.updateFwHs(fwHsDO, false);
                        }
                    }
                }

            }
            result = fwLjzService.updateLjz(fwLjzDO, true);
        }
        return result;
    }

    /**
     * @param fwXmxxDO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息实体查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByXmxxDO(FwXmxxDO fwXmxxDO) {
        List<String> bdcdyhList = new ArrayList<>();
        if (fwXmxxDO != null && StringUtils.isNotBlank(fwXmxxDO.getBdcdyh())) {
            bdcdyhList.add(fwXmxxDO.getBdcdyh());
        }
        return bdcdyhList;
    }

    /**
     * @param fwXmxxIndexList
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息主键list查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByXmxxList(List<String> fwXmxxIndexList) {
        List<String> bdcdyhList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fwXmxxIndexList)) {
            for (String fwXmxxIndex : fwXmxxIndexList) {
                FwXmxxDO fwXmxxDO = queryXmxxByPk(fwXmxxIndex);
                if (fwXmxxDO != null) {
                    bdcdyhList.addAll(listValidBdcdyhByXmxxDO(fwXmxxDO));
                }
            }
        }
        return bdcdyhList;
    }

    /**
     * @param fwXmxxIndex
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息主键list查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByXmxxIndex(String fwXmxxIndex) {
        List<String> bdcdyhList = new ArrayList<>();
        if (StringUtils.isNotBlank(fwXmxxIndex)) {
            FwXmxxDO fwXmxxDO = queryXmxxByPk(fwXmxxIndex);
            if (fwXmxxDO != null) {
                bdcdyhList.addAll(listValidBdcdyhByXmxxDO(fwXmxxDO));
            }
        }
        return bdcdyhList;
    }

    /**
     * @param fwXmxxBgVO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据FwXmxxBgVO查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByFwXmxxBgVO(FwXmxxBgVO fwXmxxBgVO) {
        List<String> bdcdyhList = new ArrayList<>();
        if (fwXmxxBgVO != null && StringUtils.isNotBlank(fwXmxxBgVO.getFwXmxxIndex())) {
            bdcdyhList = listValidBdcdyhByXmxxIndex(fwXmxxBgVO.getFwXmxxIndex());
        }
        return bdcdyhList;
    }

    /**
     * @param fwXmxxHbVO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据FwXmxxHbVO查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByFwXmxxHbVO(FwXmxxHbVO fwXmxxHbVO) {
        List<String> bdcdyhList = new ArrayList<>();
        if (fwXmxxHbVO != null && CollectionUtils.isNotEmpty(fwXmxxHbVO.getyXmxxIndexList())) {
            bdcdyhList = listValidBdcdyhByXmxxList(fwXmxxHbVO.getyXmxxIndexList());
        }
        return bdcdyhList;
    }

    /**
     * @param jsonData
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据json查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByJson(String jsonData) {
        List<String> bdcdyhList = new ArrayList<>();
        if (StringUtils.isNotBlank(jsonData)) {
            bdcdyhList.addAll(listValidBdcdyhByXmxxDO((FwXmxxDO) BuildingUtils.gnqyzGetDO(jsonData)));
        }
        return bdcdyhList;
    }

    /**
     * @param xmbm
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwXmxxDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据XMBM 查询 可用的FWXMXX
     */
    @Override
    public List<FwXmxxDO> listKyFwXmxxByXmbm(String xmbm) {
        if (StringUtils.isNotBlank(xmbm)) {
            Example example = new Example(FwXmxxDO.class);
            example.createCriteria().andEqualTo("xmbm", xmbm)
                    .andNotEqualNvlTo("bdczt", Constants.BDCZT_BKY, Constants.BDCZT_KY);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }

}