package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.config.ftp.FcfhtFtpConfig;
import cn.gtmap.realestate.building.core.bo.FwbmBO;
import cn.gtmap.realestate.building.core.mapper.FwHsMapper;
import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.service.bg.CxBgService;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.util.FtpUtil;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.building.BdcTddysfxxQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repository;
import cn.gtmap.realestate.common.core.vo.building.BatchUpdateFwhsVO;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-10-27
 * @description ????????????serviceImpl
 */
@Service
public class FwHsServiceImpl extends InterfaceCodeBeanFactory implements FwHsService {

    public static final String FW_DCB_INDEX = "fwDcbIndex";
    public static final String BDCZT = "bdczt";
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FwHsServiceImpl.class);
    /**
     * ??????????????????????????????
     */
    public static final int PER_BATCH_INSERT_MAXNUM = 40;
    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ????????????Mapper
     */
    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;

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
     * ???????????????ftp??????
     */
    @Autowired
    FcfhtFtpConfig fcfhtFtpConfig;

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ???????????????????????????????????????
     */
    @Override
    public List<FwHsDO> listFwHsListByFwDcbIndex(String fwDcbIndex) {
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("fwDcbIndex", fwDcbIndex);
            return fwHsMapper.listFwHs(paramMap);
        }
        return new ArrayList<>(0);
    }

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">caolu</a>
     * @description ??????LJZ??????????????????????????????????????????
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
     * @description ???????????????????????????????????????
     * @date : 2020/12/22 10:51
     */
    @Override
    public List<FwhsQlrDTO> listFwhsWithQlr(String fwDcbIndex) {
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("fwDcbIndex", fwDcbIndex);
            return fwHsMapper.listFwHsWithQlr(paramMap);
        }
        return new ArrayList<>(0);
    }

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description ???????????????????????????????????????
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
     * @description ??????????????????????????????
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
     * @description ????????????????????????????????????
     */
    @Override
    public FwHsDO queryFwHsByIndex(String fwHsIndex) {
        if (StringUtils.isNotBlank(fwHsIndex)) {
            return entityMapper.selectByPrimaryKey(FwHsDO.class, fwHsIndex);
        }
        return null;
    }


    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????????????????????????????????????????????
     */
    @Override
    public List<FwHsDO> queryFwycHsByIndexAndScmj(String fwDcbIndex) {
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            Example example = new Example(FwHsDO.class);
            example.createCriteria().andEqualTo("fwDcbIndex", fwDcbIndex)
                    .andEqualNvlTo("scjzmj",0,0);
            return entityMapper.selectByExample(example);
        }
        return null;
    }

    /**
     * @param fwHsDO
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description ??????????????????????????????
     */
    @Override
    public FwHsDO insertFwHs(FwHsDO fwHsDO) {
        return insertFwHsCreatBdcdyh(fwHsDO, true);
    }

    /**
     * @param fwHsDO
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description ???????????????????????? ????????????????????????????????????????????????
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
     * @param updateNull true????????????????????????false????????????????????????
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description ??????????????????????????????
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
     * @description ?????? FW_HS??? ???BDCDYH??????????????????????????????BDCDYH??????
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
     * @description ?????? FW_HS??? ???BDCDYH????????????????????????BDCDYH??????????????????????????????
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
     * @description ????????????????????????
     */
    @Override
    @Transactional
    public Integer deleteHsByFwHsIndex(String fwHsIndex, boolean withRelated) {
        Integer deleteResult = 0;
        if (StringUtils.isNotBlank(fwHsIndex)) {
            FwHsDO fwHsDO = entityMapper.selectByPrimaryKey(FwHsDO.class, fwHsIndex);
            deleteResult = entityMapper.deleteByPrimaryKey(FwHsDO.class, fwHsIndex);
            // ??????BDCDYH XSZT
            if (withRelated) {
                // ???????????????
                fwFcqlrService.deleteFcqlrByFwIndex(fwHsIndex);
                // ???????????????
                fwZhsService.deleteZhsByFwHsIndex(fwHsIndex);
                // ???????????????
                fwHstService.deleteFwHstByFwHstIndex(fwHsDO.getFwHstIndex());
            }
        }
        return deleteResult;
    }

    /**
     * @param fwHsDO
     * @return int
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ????????????
     */
    @Override
    @Transactional
    public int deleteFwhs(FwHsDO fwHsDO, boolean withRelated) {
        int result = 0;
        if (fwHsDO != null && StringUtils.isNotBlank(fwHsDO.getFwHsIndex())) {
            result = entityMapper.delete(fwHsDO);
            if (withRelated) {
                // ???????????????
                fwFcqlrService.deleteFcqlrByFwIndex(fwHsDO.getFwHsIndex());
                // ???????????????
                fwZhsService.deleteZhsByFwHsIndex(fwHsDO.getFwHsIndex());
            }
        }
        return result;
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??????BDCDYH????????????????????????
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
     * @description ??????BDCDYH????????????houseid??????
     */
    @Override
    public FwHsHouseIdDTO queryFwhsHouseIdByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            Map<String, String> paramMap = new HashMap<>();
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
     * @description ??????BDCDYH????????????????????????
     */
    @Override
    public Map<String, List<String>> queryLcztByBdcdyh(List<String> bdcdyhList) {
        Map<String, List<String>> res = new HashMap<>();
        if (CollectionUtils.isNotEmpty(bdcdyhList)) {
            LOGGER.info("------??????bdcdyh????????????lczt,bdcdyh???{}", bdcdyhList);

            List<List<String>> bdcdyhListPartition = Lists.partition(bdcdyhList, 500);
            for (List<String> bdcdyhs : bdcdyhListPartition) {
                List<String> fwBdcdyhList = bdcdyhs.stream().filter(bdcBdcdyh -> bdcBdcdyh.indexOf("F") >= 0).collect(Collectors.toList());

                //??????bdcdyh???????????????????????????????????????????????????
                if (CollectionUtils.isNotEmpty(fwBdcdyhList)) {
                    //1.???fw_hs???lczt=0?????????????????????
                    List<String> resHs = fwHsService.queryFwhsLcztByBdcdyh(fwBdcdyhList);
                    res.put("hs", resHs);
                    LOGGER.info("1.fw_hs??????bdcdyhList{}???lczt???0???bdcdyh???{}", fwBdcdyhList, resHs);


                    //2.???fw_ljz???lczt=0?????????????????????
                    List<String> resLjz = fwLjzService.queryLjzLcztByBdcdyh(fwBdcdyhList);
                    res.put("ljz", resLjz);
                    LOGGER.info("2.fw_ljz: ???bdcdyhList{}???lczt???0???bdcdyh???{}", fwBdcdyhList, resLjz);


                }

                //3.1??????bdcdyh???????????????
                List<String> djhList = new ArrayList<>(bdcdyhs.size());
                for (String bdcdyh : bdcdyhs) {
                    String djh = bdcdyh.substring(0, 19);
                    djhList.add(djh);
                }
                LOGGER.info("------??????djh????????????lczt,djh???{}", djhList);

                //3.2zd_djdcb/zd_k_xxxx???lczt=0????????????
                List<String> resDjh = zdService.queryZdLcztByBdcdyh(djhList);
                LOGGER.info("3.0. zd_djdcb/zd_k_xxxx: ???djhList{}???lczt???0???djh???{}", djhList, resDjh);
                res.put("zddjh", resDjh);
                List<String> resTd = new ArrayList<>();
                //??????????????????????????????????????????
                if (CollectionUtils.isNotEmpty(resDjh)) {
                    for (String bdcdyh : bdcdyhs) {
                        String djh = bdcdyh.substring(0, 19);
                        if (resDjh.contains(djh)) {
                            resTd.add(bdcdyh);
                        }
                    }
                }
                res.put("zd", resTd);
                LOGGER.info("3.1.zd_djdcb/zd_k_xxxx: ???djhList{}???lczt???0???djh???{}", djhList, resTd);
            }
        }
        LOGGER.info(" ???bdcdyhList{}???lczt???0???bdcdyh???{}", bdcdyhList, res);
        return res;
    }

    /**
     * @param bdcdyhList
     * @return
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description ??????BDCDYH????????????????????????
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
     * @description ???????????????????????????????????????
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
     * @description ???????????????????????????
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
     * @description ??????FCDAH??????FWHS
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
     * @description ??????FWBM??????FWHS
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
     * @param fwbm ????????????
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description ???????????????????????????????????????????????????
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
     * @description ??????YSFWBM????????????????????????
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
     * @description ??????FWDCBINDEX ?????? ??????FWHS
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
     * @description ????????????FWHS
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
                //??????bdcdyh???fwbm
                if (StringUtils.isBlank(curFwHs.getBdcdyh())) {
                    curFwHs.setBdcdyh(bdcdyhService.creatFwBdcdyhByFwDcbIndex(curFwHs.getFwDcbIndex()));
                    if (StringUtils.isNotBlank(curFwHs.getBdcdyh())) {
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
     * @description ?????????????????????????????????????????????????????????
     */
    @Override
    public DjdcbFwhsResponseDTO queryDjdcbFwhsByBdcdyh(String bdcdyh) {
        return fwHsMapper.queryDjdcbFwhsByBdcdyh(bdcdyh);
    }

    /**
     * @param pageable
     * @param paramMap
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description ????????????FWZHS
     */
    @Override
    public Page<FwHsHbZhsRequestDTO> listHsHbZhsByPage(Pageable pageable, Map<String, String> paramMap) {
        return repository.selectPaging("listFwHsHbZhsByPageOrder", paramMap, pageable);
    }

    /**
     * @param fwHsDO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description ?????????????????????????????????????????????????????????
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
     * @description ???????????????????????????????????????????????????????????????
     */
    @Override
    public List<String> listValidBdcdyhByFwHsIndexList(List<String> fwHsIndexList) {
        List<String> bdcdyhList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fwHsIndexList)) {
            for (String fwHsIndex : fwHsIndexList) {
                //??????String ????????????????????????
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
     * @description ??????bgbh?????????????????????????????????
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
     * @description ??????fwHsIndex?????????????????????????????????
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
     * @description ??????json?????????????????????????????????
     */
    @Override
    public List<String> listValidBdcdyhByJson(String jsonData) {
        List<String> bdcdyhList = new ArrayList<>();
        if (StringUtils.isNotBlank(jsonData)) {
            bdcdyhList.addAll(listValidBdcdyhByFwHsDO((FwHsDO) BuildingUtils.gnqyzGetDO(jsonData)));
        }
        return bdcdyhList;
    }

    /**
     * @param batchUpdateFwhsVO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description ????????????????????????vo?????????????????????????????????
     */
    @Override
    public List<String> listValidBdcdyhByBatchUpdateFwhsVO(BatchUpdateFwhsVO batchUpdateFwhsVO) {
        List<String> bdcdyhList = new ArrayList<>();
        if (batchUpdateFwhsVO != null && CollectionUtils.isNotEmpty(batchUpdateFwhsVO.getFwHsIndexList())) {
            bdcdyhList = listValidBdcdyhByFwHsIndexList(batchUpdateFwhsVO.getFwHsIndexList());
        }
        return bdcdyhList;
    }

    /**
     * @param ybdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ???BDCDYH????????????????????????
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
     * @description ??????????????????????????????????????????????????????fwhsdo???????????????????????????????????????????????????
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
     * @description ??????zl????????????????????????
     */
    @Override
    public FwHsDO queryFwhsByZlAndBdcdyh(String zl, String bdcdyh) {
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
     * @description ??????house_id ??????????????????
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
    public void updateFwhsTddysfxx(BdcTddysfxxQO bdcTddysfxxQO) {
        if (CollectionUtils.isNotEmpty(bdcTddysfxxQO.getBdcdyhList())) {
            fwHsMapper.updateFwhsTddysfxx(bdcTddysfxxQO);

        }

    }


    @Override
    public void downloadFcfhtHefei(FhtDTO fhtDTO) throws IOException {
        String base64 = "";
        String bdcdyh = "";
        Integer page = 0;

        if (Objects.nonNull(fhtDTO)){
            base64 = fhtDTO.getBase64();
            bdcdyh = fhtDTO.getBdcdyh();
            page = fhtDTO.getNowPage();
        }

        //FTP????????????
        LOGGER.info("FTP????????????,bdcdyh:{}", bdcdyh);
        FTPClient ftpClient = null;
        boolean flag = false;
        if (StringUtils.isNotBlank(base64)) {
            String localCharset = StringToolUtils.ENCODING_GBK;
            ftpClient = FtpUtil.getFtpClient(fcfhtFtpConfig);
            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
                localCharset = StringToolUtils.ENCODING_UTF8;
                StringBuilder ftp = new StringBuilder();
                String path = fcfhtFtpConfig.getPath();
                String uploadFileNameNoTmp = bdcdyh + page + CommonConstantUtils.WJHZ_JPG;
                ftp.append(StringUtils.defaultString(path, "")).append("/").append(bdcdyh);
                LOGGER.info("????????????:{}",ftp + "/" + uploadFileNameNoTmp);
                ftpClient.setControlEncoding(localCharset);
                ftpClient.enterLocalPassiveMode();// ??????????????????
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);//?????????????????????FTP???????????????
                FtpUtil.createDirs(ftpClient, new String(ftp.toString().getBytes(localCharset), FTP.DEFAULT_CONTROL_ENCODING));
                flag = ftpClient.storeFile(new String(uploadFileNameNoTmp.getBytes(localCharset), FTP
                        .DEFAULT_CONTROL_ENCODING), new ByteArrayInputStream(Base64Utils.decodeBase64StrToByte(base64)));
            }
        }
        if (flag) {
            LOGGER.info("FTP????????????,bdcdyh:{}", bdcdyh);
        } else {
            LOGGER.error("FTP???????????????bdcdyh:{}", bdcdyh);
        }
    }

    @Override
    public List<String> getFcfhtFromFTP(String bdcdyh) throws IOException {
        List<String> baseCodeList = new ArrayList<>();
        if(StringUtils.isBlank(bdcdyh)){
            throw new MissingArgumentException("????????????bdcdyh??????");
        }
        //???????????????????????????
        FTPClient ftpClient = null;
        ftpClient = FtpUtil.getFtpClient(fcfhtFtpConfig);
        String pathName = new StringBuilder(CommonConstantUtils.ZF_YW_XG).append(bdcdyh).toString();
        boolean isOpen = ftpClient.changeWorkingDirectory(pathName);
        if(isOpen){
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if(file.isFile() && file.getName().endsWith(".jpg")){
                    InputStream in = FtpUtil.downloadFtpFile(ftpClient, pathName, file.getName());
                    if (in != null) {
                        String baseCode = BuildingUtils.getBase64FromInputStream(in);
                        baseCodeList.add(baseCode);
                        in.close();
                        ftpClient.completePendingCommand();
                    }
                }
            }
        }
        return baseCodeList;
    }

    @Override
    public List<String> getFxfhtFromDaxx(String bdcdyh, String slbh, String bdcqzh,String qjgldm) throws IOException {
       List<String> hstBase64CodeList = new ArrayList<>();
        //??????????????????
        Map param = new HashMap();
        if (StringUtils.isNotBlank(slbh)) {
            param.put("docId", slbh);
        }
        param.put("type","1");
        if (StringUtils.isNotBlank(bdcqzh)) {
            try {
                param.put("zqzh", URLDecoder.decode(bdcqzh, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("", e);
            }
        } else {
            param.put("zqzh", "");

        }
        if (MapUtils.isEmpty(param)) {
            throw new MissingArgumentException("?????????????????????");
        }
        Object hfDaJbxx = exchangeInterfaceFeignService.requestInterface("hfDaJbxx", param);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(hfDaJbxx);
        LOGGER.info("bdcdyh???{} ??????????????????????????? ???{}",bdcdyh,jsonObject);
        if(jsonObject != null){
            String imgid = jsonObject.get("IMGID").toString();
            if(StringUtils.isNotBlank(imgid)) {
                //????????????
                Map fcfhtMlxxParamMap = new HashMap();
                fcfhtMlxxParamMap.put("archid", imgid);
                Object hfFcfhtMlxx = exchangeInterfaceFeignService.requestInterface("hfFcfhtMlxx", fcfhtMlxxParamMap);
                JSONArray objects = JSONArray.parseArray((String) JSON.toJSON(hfFcfhtMlxx));

                LOGGER.info("bdcdyh???{} ????????????????????? ???{}",bdcdyh,objects);
                if(objects != null){
                    for (Object object : objects) {
                        JSONObject daml = (JSONObject) JSON.toJSON(object);
                        String frompage = daml.get("FROMPAGE").toString();
                        if(StringUtils.isNotBlank(frompage)){
                            Map fjxxParamMap = new HashMap();
                            fjxxParamMap.put("username", userManagerUtils.getCurrentUserName());
                            fjxxParamMap.put("archid", imgid);
                            fjxxParamMap.put("currentpage", frompage);
                            fjxxParamMap.put("type", "1");
                            Object hfDaFjxx = exchangeInterfaceFeignService.requestInterface("hfDaFjxx", fjxxParamMap);
                            JSONObject daFjxxJsonObject = (JSONObject) JSON.toJSON(hfDaFjxx);
                            LOGGER.info("bdcdyh???{} ,archid???{}, ????????????????????? ???{}",bdcdyh,imgid,daFjxxJsonObject);
                            if(daFjxxJsonObject != null ){
                                Object data = daFjxxJsonObject.get("data");
                                if(data != null){
                                    hstBase64CodeList.add(data.toString());
                                    FhtDTO fhtDTO = new FhtDTO();
                                    fhtDTO.setBase64(data.toString());
                                    fhtDTO.setBdcdyh(bdcdyh);
                                    fhtDTO.setNowPage(Integer.parseInt(frompage));
                                    downloadFcfhtHefei(fhtDTO);
                                }
                            }
                        }
                    }
                }
            }
        }
        return hstBase64CodeList;
    }
}


