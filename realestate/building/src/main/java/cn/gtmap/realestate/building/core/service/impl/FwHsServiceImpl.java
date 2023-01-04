package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.bo.FwbmBO;
import cn.gtmap.realestate.building.core.mapper.FwHsMapper;
import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.service.bg.CxBgService;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dto.building.DjdcbFwhsResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.FwHsHbZhsRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.FwHsHouseIdDTO;
import cn.gtmap.realestate.common.core.dto.building.FwhsQlrDTO;
import cn.gtmap.realestate.common.core.qo.building.BdcTddysfxxQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repository;
import cn.gtmap.realestate.common.core.vo.building.BatchUpdateFwhsVO;
import cn.gtmap.realestate.common.util.CheckEntityUtils;
import cn.gtmap.realestate.common.util.InterfaceCodeBeanFactory;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-10-27
 * @description 房屋户室serviceImpl
 */
@Service
public class FwHsServiceImpl extends InterfaceCodeBeanFactory implements FwHsService {

    public static final String FW_DCB_INDEX = "fwDcbIndex";
    public static final String BDCZT = "bdczt";
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FwHsServiceImpl.class);
    /**
     * 批量插入最大执行数量
     */
    public static final int PER_BATCH_INSERT_MAXNUM = 40;
    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 实体查询Mapper
     */
    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private FwZhsService fwZhsService;

    @Autowired
    private FwHstService fwHstService;

    @Autowired
    private FwFcqlrService fwFcqlrService;

    @Autowired
    private BdcdyhService bdcdyhService;

    @Autowired
    private Repository repository;

    @Autowired
    private FwHsMapper fwHsMapper;

    @Autowired
    private FwLjzService fwLjzService;
    @Autowired
    private ZdServiceImpl zdService;
    @Autowired
    private FwHsService fwHsService;



    @Autowired
    private FwXmxxService fwXmxxService;

    @Autowired
    private CxBgService cxBgService;

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据逻辑幢主键查询户室信息
     */
    @Override
    public List<FwHsDO> listFwHsListByFwDcbIndex(String fwDcbIndex) {
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("fwDcbIndex",fwDcbIndex);
            return fwHsMapper.listFwHs(paramMap);
        }
        return new ArrayList<>(0);
    }

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">caolu</a>
     * @description 根据LJZ主键查询户室列表，不进行排序
     */
    @Override
    public List<FwHsDO> listFwHsByFwDcbIndex(String fwDcbIndex) {
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            Example example = new Example(FwHsDO.class);
            example.createCriteria().andEqualTo("fwDcbIndex", fwDcbIndex);
            List<FwHsDO> fwhsList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(fwhsList)) {
                return fwhsList;
            }
        }
        return new ArrayList<>(0);
    }

    /**
     * @param fwDcbIndex
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询房屋户室信息包含权利人
     * @date : 2020/12/22 10:51
     */
    @Override
    public List<FwhsQlrDTO> listFwhsWithQlr(String fwDcbIndex) {
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("fwDcbIndex",fwDcbIndex);
            return fwHsMapper.listFwHsWithQlr(paramMap);
        }
        return new ArrayList<>(0);
    }

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据逻辑幢主键查询户室信息
     */
    @Override
    public List<FwHsDO> listFwHsZlNullByFwDcbIndex(String fwDcbIndex) {
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            Example example = new Example(FwHsDO.class);
            example.createCriteria().andEqualTo(FW_DCB_INDEX, fwDcbIndex).andIsNull("zl");
            return entityMapper.selectByExample(example);
        }
        return new ArrayList<>(0);
    }

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 查询为空的为空的户室
     */
    @Override
    public List<FwHsDO> listFwHstNullByFwDcbIndex(String fwDcbIndex) {
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            Example example = new Example(FwHsDO.class);
            example.createCriteria().andEqualTo(FW_DCB_INDEX, fwDcbIndex).andIsNull("fwHstIndex");
            return entityMapper.selectByExample(example);
        }
        return new ArrayList<>(0);
    }

    /**
     * @param fwHsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据户室主键查询户室信息
     */
    @Override
    public FwHsDO queryFwHsByIndex(String fwHsIndex) {
        if (StringUtils.isNotBlank(fwHsIndex)) {
            return entityMapper.selectByPrimaryKey(FwHsDO.class, fwHsIndex);
        }
        return null;
    }

    /**
     * @param fwHsDO
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 新增户室接口服务实现
     */
    @Override
    public FwHsDO insertFwHs(FwHsDO fwHsDO) {
        return insertFwHsCreatBdcdyh(fwHsDO, true);
    }

    /**
     * @param fwHsDO
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 新增户室接口服务 增加判断是否新增不动产单元号标示
     */
    @Override
    public FwHsDO insertFwHsCreatBdcdyh(FwHsDO fwHsDO, boolean creatBdcdyh) {
        if (fwHsDO != null) {
            if (!CheckEntityUtils.checkPk(fwHsDO)) {
                fwHsDO.setFwHsIndex(UUIDGenerator.generate());
            }
            if (creatBdcdyh && StringUtils.isBlank(fwHsDO.getBdcdyh()) && StringUtils.isNotBlank(fwHsDO.getFwDcbIndex())) {
                FwLjzDO fwLjzDO = entityMapper.selectByPrimaryKey(FwLjzDO.class, fwHsDO.getFwDcbIndex());
                if (fwLjzDO != null && StringUtils.equals(Constants.BDCDYFWLX_H, fwLjzDO.getBdcdyfwlx())) {
                    String bdcdyh = bdcdyhService.creatFwBdcdyhByFwDcbIndex(fwLjzDO.getFwDcbIndex());
                    if (StringUtils.isNotBlank(bdcdyh)) {
                        fwHsDO.setBdcdyh(bdcdyh);
                        fwHsDO.setBdczt(Constants.BDCZT_KY);
                        if (StringUtils.isBlank(fwHsDO.getFwbm()) && StringUtils.isNotBlank(fwHsDO.getBdcdyh())) {
                            fwHsDO.setFwbm(new FwbmBO(fwHsDO.getBdcdyh()).getFwbm());
                        }
                    }
                }
            }
        }
        entityMapper.insertSelective(fwHsDO);
        return fwHsDO;
    }

    /**
     * @param fwHsDO
     * @param updateNull true表示空字段更新，false表示空字段不更新
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改户室接口服务实现
     */
    @Override
    public Integer updateFwHs(FwHsDO fwHsDO, boolean updateNull) {
        Integer updateResult = 0;
        if (fwHsDO != null && CheckEntityUtils.checkPk(fwHsDO)) {
            if (updateNull) {
                updateResult = entityMapper.updateByPrimaryKeyNull(fwHsDO);
            } else {
                updateResult = entityMapper.updateByPrimaryKeySelective(fwHsDO);
            }
        }
        return updateResult;
    }

    /**
     * @param fwHsDO
     * @param updateNull
     * @return java.lang.Integer
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 更新 FW_HS表 有BDCDYH变化，并且不知道原有BDCDYH情况
     */
    @Override
    public Integer updateFwHsWithChangeBdcdyh(FwHsDO fwHsDO, boolean updateNull) {
        int result = 0;
        if (fwHsDO != null
                && StringUtils.isNotBlank(fwHsDO.getFwHsIndex())) {
            fwHsDO.setBdczt(StringUtils.isNotBlank(fwHsDO.getBdcdyh()) ? Constants.BDCZT_KY : Constants.BDCZT_BKY);
            result = updateFwHs(fwHsDO, updateNull);
        }
        return result;
    }

    /**
     * @param fwHsDO
     * @param updateNull
     * @param ybdcdyh
     * @return java.lang.Integer
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 更新 FW_HS表 有BDCDYH变化，且知道原有BDCDYH情况（包括空的情况）
     */
    @Override
    public Integer updateFwHsWithChangeBdcdyh(FwHsDO fwHsDO, boolean updateNull,
                                              String ybdcdyh) {
        int result = 0;
        if (fwHsDO != null) {
            fwHsDO.setBdczt(StringUtils.isNotBlank(fwHsDO.getBdcdyh()) ? Constants.BDCZT_KY : Constants.BDCZT_BKY);
            result = updateFwHs(fwHsDO, updateNull);
        }
        return result;
    }

    /**
     * @param fwHsIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 删除户室接口服务
     */
    @Override
    @Transactional
    public Integer deleteHsByFwHsIndex(String fwHsIndex, boolean withRelated) {
        Integer deleteResult = 0;
        if (StringUtils.isNotBlank(fwHsIndex)) {
            FwHsDO fwHsDO = entityMapper.selectByPrimaryKey(FwHsDO.class, fwHsIndex);
            deleteResult = entityMapper.deleteByPrimaryKey(FwHsDO.class, fwHsIndex);
            // 处理BDCDYH XSZT
            if (withRelated) {
                // 删除权利人
                fwFcqlrService.deleteFcqlrByFwIndex(fwHsIndex);
                // 删除子户室
                fwZhsService.deleteZhsByFwHsIndex(fwHsIndex);
                // 删除户室图
                fwHstService.deleteFwHstByFwHstIndex(fwHsDO.getFwHstIndex());
            }
        }
        return deleteResult;
    }

    /**
     * @param fwHsDO
     * @return int
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 删除户室
     */
    @Override
    @Transactional
    public int deleteFwhs(FwHsDO fwHsDO, boolean withRelated) {
        int result = 0;
        if (fwHsDO != null && StringUtils.isNotBlank(fwHsDO.getFwHsIndex())) {
            result = entityMapper.delete(fwHsDO);
            if (withRelated) {
                // 删除权利人
                fwFcqlrService.deleteFcqlrByFwIndex(fwHsDO.getFwHsIndex());
                // 删除子户室
                fwZhsService.deleteZhsByFwHsIndex(fwHsDO.getFwHsIndex());
            }
        }
        return result;
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询户室基本信息
     */
    @Override
    public FwHsDO queryFwhsByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            Example example = new Example(FwHsDO.class);
            example.createCriteria().andEqualTo("bdcdyh", bdcdyh);
            List<FwHsDO> fwHsDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(fwHsDOList)) {
                return fwHsDOList.get(0);
            }
        }
        return null;
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsHouseIdDTO
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 根据BDCDYH查询户室houseid信息
     */
    @Override
    public FwHsHouseIdDTO queryFwhsHouseIdByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("bdcdyh", bdcdyh);
            List<FwHsHouseIdDTO> fwHsHouseIdDTOList = fwHsMapper.listFwhsHouseIdByBdcdyh(paramMap);
            if (CollectionUtils.isNotEmpty(fwHsHouseIdDTOList)) {
                return fwHsHouseIdDTOList.get(0);
            }
        }
        return null;
    }

    /**
     * @param bdcdyhList
     * @return
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据BDCDYH查询户室流程状态
     */
    @Override
    public Map<String, List<String>> queryLcztByBdcdyh(List<String> bdcdyhList) {
        Map<String, List<String>> res = new HashMap<>();
        if (CollectionUtils.isNotEmpty(bdcdyhList)) {
            LOGGER.info("------根据bdcdyh查权籍的lczt,bdcdyh为{}",bdcdyhList);

            List<List<String>> bdcdyhListPartition = Lists.partition(bdcdyhList, 500);
            for (List<String> bdcdyhs : bdcdyhListPartition) {
                List<String> fwBdcdyhList = bdcdyhs.stream().filter(bdcBdcdyh -> bdcBdcdyh.indexOf("F") >= 0).collect(Collectors.toList());

                //如果bdcdyh全是土地类型，则不查户室和逻辑幢表
                if (CollectionUtils.isNotEmpty(fwBdcdyhList)) {
                    //1.查fw_hs的lczt=0的不动产单元号
                    List<String> resHs = fwHsService.queryFwhsLcztByBdcdyh(fwBdcdyhList);
                    res.put("hs", resHs);
                    LOGGER.info("1.fw_hs：在bdcdyhList{}中lczt为0的bdcdyh有{}", fwBdcdyhList, resHs);


                    //2.查fw_ljz的lczt=0的不动产单元号
                    List<String> resLjz = fwLjzService.queryLjzLcztByBdcdyh(fwBdcdyhList);
                    res.put("ljz", resLjz);
                    LOGGER.info("2.fw_ljz: 在bdcdyhList{}中lczt为0的bdcdyh有{}", fwBdcdyhList, resLjz);


                }

                //3.1截取bdcdyh获取地籍号
                List<String> djhList = new ArrayList<>(bdcdyhs.size());
                for (String bdcdyh : bdcdyhs) {
                    String djh = bdcdyh.substring(0,19);
                    djhList.add(djh);
                }
                LOGGER.info("------根据djh查宗地的lczt,djh为{}",djhList);

                //3.2zd_djdcb/zd_k_xxxx的lczt=0的地籍号
                List<String> resDjh = zdService.queryZdLcztByBdcdyh(djhList);
                LOGGER.info("3.0. zd_djdcb/zd_k_xxxx: 在djhList{}中lczt为0的djh有{}", djhList, resDjh);
                res.put("zddjh", resDjh);
                List<String> resTd = new ArrayList<>();
                //根据地籍号取对应单元号单元号
                if (CollectionUtils.isNotEmpty(resDjh)) {
                    for (String bdcdyh : bdcdyhs) {
                        String djh = bdcdyh.substring(0,19);
                        if (resDjh.contains(djh)) {
                            resTd.add(bdcdyh);
                        }
                    }
                }
                res.put("zd", resTd);
                LOGGER.info("3.1.zd_djdcb/zd_k_xxxx: 在djhList{}中lczt为0的djh有{}", djhList, resTd);
            }
        }
        LOGGER.info(" 在bdcdyhList{}中lczt为0的bdcdyh有{}", bdcdyhList, res);
        return res;
    }

    /**
     * @param bdcdyhList
     * @return
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据BDCDYH查询户室流程状态
     */
    @Override
    public List<String> queryFwhsLcztByBdcdyh(List<String> bdcdyhList) {
        if (CollectionUtils.isNotEmpty(bdcdyhList)) {
            return fwHsMapper.queryFwhsLcztByBdcdyh(bdcdyhList);
        }
        return null;
    }

    /**
     * @param fwHstIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据户室图主键查询房屋户室
     */
    @Override
    public List<FwHsDO> listFwHsByFwHstIndex(String fwHstIndex) {
        if (StringUtils.isNotBlank(fwHstIndex)) {
            Example example = new Example(FwHsDO.class);
            example.createCriteria().andEqualTo("fwHstIndex", fwHstIndex);
            return entityMapper.selectByExample(example);
        }
        return new ArrayList<>(0);
    }

    /**
     * @param fwDcbIndex
     * @param fwHsIndexList
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改房屋户室逻辑幢
     */
    @Override
    public void updateFwHsLjz(List<String> fwHsIndexList, String fwDcbIndex) {
        if (CollectionUtils.isNotEmpty(fwHsIndexList) && StringUtils.isNotBlank(fwDcbIndex)) {
            for (String fwHsIndex : fwHsIndexList) {
                if (StringUtils.isNotBlank(fwHsIndex)) {
                    FwHsDO fwHsDO = new FwHsDO();
                    fwHsDO.setFwDcbIndex(fwDcbIndex);
                    fwHsDO.setFwHsIndex(fwHsIndex);
                    updateFwHs(fwHsDO, false);
                }
            }
        }
    }

    /**
     * @param fcdah
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FCDAH查询FWHS
     */
    @Override
    public List<FwHsDO> listKyFwhsByFcdah(String fcdah) {
        if (StringUtils.isNotBlank(fcdah)) {
            Example example = new Example(FwHsDO.class);
            example.createCriteria().andEqualTo("fcdah", fcdah).andNotEqualNvlTo(BDCZT, Constants.BDCZT_BKY, Constants.BDCZT_KY);
            List<FwHsDO> resultList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(resultList)) {
                return resultList;
            }
        }
        return new ArrayList<>(0);
    }

    /**
     * @param fwbm
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FWBM查询FWHS
     */
    @Override
    public List<FwHsDO> listKyFwhsByFwbm(String fwbm) {
        if (StringUtils.isNotBlank(fwbm)) {
            Example example = new Example(FwHsDO.class);
            example.createCriteria().andEqualTo("fwbm", fwbm).andNotEqualNvlTo(BDCZT, Constants.BDCZT_BKY, Constants.BDCZT_KY);
            List<FwHsDO> resultList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(resultList)) {
                return resultList;
            }
        }
        return new ArrayList<>();
    }

    /**
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param fwbm  房屋编码
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @description 只根据房屋编码参数查询房屋户室信息
     */
    @Override
    public List<FwHsDO> queryFwhsOnlyByFwbm(String fwbm) {
        if (StringUtils.isNotBlank(fwbm)) {
            Example example = new Example(FwHsDO.class);
            example.createCriteria().andEqualTo("fwbm", fwbm);
            List<FwHsDO> resultList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(resultList)) {
                return resultList;
            }
        }
        return new ArrayList<>();
    }

    /**
     * @param ysfwbm
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据YSFWBM查询可用房屋户室
     */
    @Override
    public List<FwHsDO> listKyFwhsByYsfwbm(String ysfwbm, String zl) {
        if (StringUtils.isNotBlank(ysfwbm) || StringUtils.isNotBlank(zl)) {
            Example example = new Example(FwHsDO.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(ysfwbm)) {
                criteria.andEqualTo("ysfwbm", ysfwbm).andNotEqualNvlTo(BDCZT, Constants.BDCZT_BKY, Constants.BDCZT_KY);
            }
            if (StringUtils.isNotBlank(zl)) {
                criteria.andEqualTo("zl", zl);
            }
            List<FwHsDO> resultList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(resultList)) {
                return resultList;
            }
        }
        return new ArrayList<>();
    }

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FWDCBINDEX 查询 可用FWHS
     */
    @Override
    public List<FwHsDO> listKyFwhsByFwDcbIndex(String fwDcbIndex) {
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            Example example = new Example(FwHsDO.class);
            example.createCriteria().andEqualTo(FW_DCB_INDEX, fwDcbIndex).andNotEqualNvlTo(BDCZT, Constants.BDCZT_BKY, Constants.BDCZT_KY);
            List<FwHsDO> resultList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(resultList)) {
                return resultList;
            }
        }
        return new ArrayList<>(0);
    }

    @Override
    public List<FwHsDO> listFwhsByYcfwbm(String ycfwbm) {
        List<FwHsDO> resultList = new ArrayList<>(0);
        if (StringUtils.isNotBlank(ycfwbm)) {
            Example example = new Example(FwHsDO.class);
            example.or(example.createCriteria().andEqualTo("ycfwbm", ycfwbm));
            example.or(example.createCriteria().andLike("ycfwbm", ycfwbm + ";%"));
            example.or(example.createCriteria().andLike("ycfwbm", "%;" + ycfwbm));
            example.or(example.createCriteria().andLike("ycfwbm", "%;" + ycfwbm + ";%"));
            resultList = entityMapper.selectByExample(example);
        }
        return resultList;
    }

    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询FWHS
     */
    @Override
    public Page<FwHsDO> listFwHsByPage(Pageable pageable, Map<String, String> paramMap) {
        return repository.selectPaging("listFwHsByPageOrder", paramMap, pageable);
    }

    @Override
    public List<FwHsDO> batchInsertFwHs(List<FwHsDO> fwHsDOList) {
        List<FwHsDO> insertList = new ArrayList<>();
        List<String> bdcdyhList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fwHsDOList)) {
            for (int i = 0; i < fwHsDOList.size(); i++) {
                FwHsDO curFwHs = fwHsDOList.get(i);
                if (!CheckEntityUtils.checkFk(curFwHs)) {
                    break;
                }
                if (!CheckEntityUtils.checkPk(curFwHs)) {
                    curFwHs.setFwHsIndex(UUIDGenerator.generate());
                }
                //处理bdcdyh，fwbm
                if (StringUtils.isBlank(curFwHs.getBdcdyh())) {
                    curFwHs.setBdcdyh(bdcdyhService.creatFwBdcdyhByFwDcbIndex(curFwHs.getFwDcbIndex()));
                    if(StringUtils.isNotBlank(curFwHs.getBdcdyh())){
                        curFwHs.setBdczt(Constants.BDCZT_KY);
                    }
                }
                if (StringUtils.isBlank(curFwHs.getFwbm()) && StringUtils.isNotBlank(curFwHs.getBdcdyh())) {
                    curFwHs.setFwbm(new FwbmBO(curFwHs.getBdcdyh()).getFwbm());
                }
                bdcdyhList.add(curFwHs.getBdcdyh());
                insertList.add(curFwHs);
                if (insertList.size() == PER_BATCH_INSERT_MAXNUM) {
                    entityMapper.insertBatchSelective(insertList);
                    bdcdyhList = new ArrayList<>();
                    insertList = new ArrayList<>();
                }
            }
        }

        if (CollectionUtils.isNotEmpty(insertList)) {
            entityMapper.insertBatchSelective(insertList);
        }
        return insertList;
    }


    @Override
    public void batchUpdateFwHs(List<FwHsDO> fwHsDOList, boolean updateNull) {
        if (CollectionUtils.isNotEmpty(fwHsDOList)) {
            for (FwHsDO fwHsDO : fwHsDOList) {
                if (fwHsDO == null || !CheckEntityUtils.checkPkAndFk(fwHsDO)) {
                    break;
                }
                updateFwHsWithChangeBdcdyh(fwHsDO, updateNull);
            }
        }
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjdcbFwhsResponseDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询地籍调查表房屋信息
     */
    @Override
    public DjdcbFwhsResponseDTO queryDjdcbFwhsByBdcdyh(String bdcdyh) {
        return fwHsMapper.queryDjdcbFwhsByBdcdyh(bdcdyh);
    }

    /**
     * @param pageable
     * @param paramMap
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询FWZHS
     */
    @Override
    public Page<FwHsHbZhsRequestDTO> listHsHbZhsByPage(Pageable pageable, Map<String, String> paramMap) {
        return repository.selectPaging("listFwHsHbZhsByPageOrder", paramMap, pageable);
    }

    /**
     * @param fwHsDO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋户室实体查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByFwHsDO(FwHsDO fwHsDO) {
        List<String> bdcdyhList = new ArrayList<>();
        if (fwHsDO != null) {
            if (StringUtils.isNotBlank(fwHsDO.getFwDcbIndex())) {
                FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwHsDO.getFwDcbIndex());
                if (fwLjzDO != null && StringUtils.isNotBlank(fwLjzDO.getBdcdyfwlx())) {
                    if (StringUtils.equals(fwLjzDO.getBdcdyfwlx(), Constants.BDCDYFWLX_XMNDZ)) {
                        FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByPk(fwLjzDO.getFwXmxxIndex());
                        if (fwXmxxDO != null && StringUtils.isNotBlank(fwXmxxDO.getBdcdyh())) {
                            bdcdyhList.add(fwXmxxDO.getBdcdyh());
                        }
                    } else if (StringUtils.equals(fwLjzDO.getBdcdyfwlx(), Constants.BDCDYFWLX_DZ)) {
                        if (StringUtils.isNotBlank(fwLjzDO.getBdcdyh())) {
                            bdcdyhList.add(fwLjzDO.getBdcdyh());
                        }
                    } else if (StringUtils.equals(fwLjzDO.getBdcdyfwlx(), Constants.BDCDYFWLX_H)) {
                        if (StringUtils.isNotBlank(fwHsDO.getBdcdyh())) {
                            bdcdyhList.add(fwHsDO.getBdcdyh());
                        }
                    }
                }
            }
        }
        return bdcdyhList;
    }

    /**
     * @param fwHsIndexList
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋户室主键集合查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByFwHsIndexList(List<String> fwHsIndexList) {
        List<String> bdcdyhList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fwHsIndexList)) {
            for (String fwHsIndex : fwHsIndexList) {
                //判断String 中是否存在“，”
                if (fwHsIndex.indexOf(",") != -1) {
                    String[] fwHsIndexs = fwHsIndex.split(",");
                    for (int i = 0; i < fwHsIndexs.length; i++) {
                        FwHsDO fwHs = queryFwHsByIndex(fwHsIndexs[i]);
                        if (fwHs != null) {
                            bdcdyhList.addAll(listValidBdcdyhByFwHsDO(fwHs));
                        }
                    }
                } else {
                    FwHsDO fwHsDO = queryFwHsByIndex(fwHsIndex);
                    if (fwHsDO != null) {
                        bdcdyhList.addAll(listValidBdcdyhByFwHsDO(fwHsDO));
                    }
                }
            }
        }
        return bdcdyhList;
    }

    /**
     * @param bgbh
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据bgbh查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByBgbh(String bgbh) {
        List<String> bdcdyhList = new ArrayList<>();
        if (StringUtils.isNotBlank(bgbh)) {
            List<SSjHsbgljbDO> sSjHsbgljbDOS = cxBgService.listBgjlbByBgbh(bgbh);
            if (CollectionUtils.isNotEmpty(sSjHsbgljbDOS)) {
                for (SSjHsbgljbDO sSjHsbgljbDO : sSjHsbgljbDOS) {
                    if (sSjHsbgljbDO != null && StringUtils.isNotBlank(sSjHsbgljbDO.getFwIndex())) {
                        FwHsDO fwHsDO = queryFwHsByIndex(sSjHsbgljbDO.getFwIndex());
                        if (fwHsDO != null) {
                            bdcdyhList.addAll(listValidBdcdyhByFwHsDO(fwHsDO));
                        }
                    }
                }
            }
        }
        return bdcdyhList;
    }

    /**
     * @param fwHsIndex
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据fwHsIndex查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByFwHsIndex(String fwHsIndex) {
        List<String> bdcdyhList = new ArrayList<>();
        if (StringUtils.isNotBlank(fwHsIndex)) {
            FwHsDO fwHsDO = queryFwHsByIndex(fwHsIndex);
            if (fwHsDO != null) {
                bdcdyhList.addAll(listValidBdcdyhByFwHsDO(fwHsDO));
            }
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
        if(StringUtils.isNotBlank(jsonData)){
            bdcdyhList.addAll(listValidBdcdyhByFwHsDO((FwHsDO) BuildingUtils.gnqyzGetDO(jsonData)));
        }
        return bdcdyhList;
    }

    /**
     * @param batchUpdateFwhsVO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据批量修改户室vo查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByBatchUpdateFwhsVO(BatchUpdateFwhsVO batchUpdateFwhsVO) {
        List<String> bdcdyhList = new ArrayList<>();
        if(batchUpdateFwhsVO!=null&&CollectionUtils.isNotEmpty(batchUpdateFwhsVO.getFwHsIndexList())){
            bdcdyhList=listValidBdcdyhByFwHsIndexList(batchUpdateFwhsVO.getFwHsIndexList());
        }
        return bdcdyhList;
    }

    /**
     * @param ybdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 原BDCDYH查询可用户室列表
     */
    @Override
    public List<FwHsDO> listKyFwhsByYbdcdyh(String ybdcdyh) {
        if (StringUtils.isNotBlank(ybdcdyh)) {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("ybdcdyh", ybdcdyh);
            paramMap.put(BDCZT, Constants.BDCZT_KY);
            return fwHsMapper.listFwHs(paramMap);
        }
        return Collections.emptyList();
    }


    /**
     * @param paramMap
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询实测预测户室信息，统一处理成fwhsdo，该方法主要用于合并户室时查询展现
     * @date : 2021/4/7 15:57
     */
    @Override
    public Page<FwHsDO> listScYcHsByPage(Pageable pageable, Map<String, String> paramMap) {
        Page<FwHsDO> fwHsDOPage = repository.selectPaging("listFwHsByPageOrder", paramMap, pageable);
        if (CollectionUtils.isEmpty(fwHsDOPage.getContent())) {
            Page<FwYchsDO> fwYchsDOPage = repository.selectPaging("listYchsByPageOrder", paramMap, pageable);
            List<FwYchsDO> fwYchsDOList = fwYchsDOPage.getContent();
            List<FwHsDO> fwHsDOList = new ArrayList<>(CollectionUtils.size(fwYchsDOList));
            if (CollectionUtils.isNotEmpty(fwYchsDOList)) {
                for (FwYchsDO fwYchsDO : fwYchsDOList) {
                    FwHsDO fwHsDO = new FwHsDO();
                    BeanUtils.copyProperties(fwYchsDO, fwHsDO);
                    fwHsDOList.add(fwHsDO);
                }
            }
            fwHsDOPage = new PageImpl<>(fwHsDOList, pageable, CollectionUtils.size(fwHsDOList));
        }
        return fwHsDOPage;
    }

    /**
     * @param zl
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据zl查询户室基本信息
     */
    @Override
    public FwHsDO queryFwhsByZlAndBdcdyh(String zl,String bdcdyh) {
        Example example = new Example(FwHsDO.class);
        if (StringUtils.isNotBlank(zl)) {
            example.createCriteria().andEqualTo("zl", zl);
        }
        if (StringUtils.isNotBlank(bdcdyh)) {
            example.createCriteria().andEqualTo("bdcdyh", bdcdyh);
        }
        List<FwHsDO> fwHsDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(fwHsDOList)) {
            return fwHsDOList.get(0);
        }
        return null;
    }

    /**
     * @param houseid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据house_id 查询户室信息
     * @date : 2022/5/16 15:13
     */
    @Override
    public List<Map> queryFwhsByHouseId(String houseid) {
        if (StringUtils.isNotBlank(houseid)) {
            Map map = new HashMap(1);
            map.put("houseid", houseid);
            return fwHsMapper.listFwhsByHouseId(map);
        }
        return null;
    }

    @Override
    public void updateFwhsTddysfxx(BdcTddysfxxQO bdcTddysfxxQO){
        if(CollectionUtils.isNotEmpty(bdcTddysfxxQO.getBdcdyhList())){
            fwHsMapper.updateFwhsTddysfxx(bdcTddysfxxQO);

        }

    }

}
