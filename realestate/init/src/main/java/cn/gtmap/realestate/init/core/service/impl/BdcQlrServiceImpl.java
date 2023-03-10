package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.BdcQlrGroupDTO;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.common.core.dto.init.BdcQlrIdsDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmZhxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlridSyncQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.init.core.mapper.BdcQlrMapper;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.*;
import cn.gtmap.realestate.init.util.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/10/31
 * @description ??????????????????????????????
 */
@Service
@Validated
public class BdcQlrServiceImpl implements BdcQlrService {

    private final Logger LOGGER = LoggerFactory.getLogger(BdcQlrServiceImpl.class);
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    DozerBeanMapper initDozerMapper;
    @Autowired
    MessageProvider messageProvider;
    @Autowired
    BdcBdcdyService bdcBdcdyService;
    @Autowired
    BdcXmService bdcXmService;
    @Autowired
    BdcQlrMapper bdcQlrMapper;
    @Autowired
    BdcEntityFeignService bdcEntityFeignService;

    @Autowired
    BdcQllxService bdcQllxService;

    @Autowired
    BdcQlridSyncService bdcQlridSyncService;

    @Autowired
    private BdcQlrtzService bdcQlrtzService;


    @Value("${syncQlrToDsqlr:false}")
    private boolean syncQlrToDsqlr;

    @Value("${updateQlrToDsqlr:false}")
    private boolean updateQlrToDsqlr;

    /**
     * ????????????????????????????????????????????????????????????????????????
     */
    @Value("${syncCurrentYwrToDsqlr:false}")
    private boolean syncCurrentYwrToDsqlr;

    @Value("${zhlc.tbdlr:true}")
    private boolean zhlcTbDlr;

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????????????????????????????????????????????????????
     * @date : 2022/5/16 15:54
     */
    @Value("${dyaq.dsqlr.qlrlb:4}")
    private String dsqlrlb;


    /**
     * ???????????????
     */
    private static final String QLRLB = "qlrlb";
    private static final String XMID = "xmid";
    private static final String NOPARAMETER = "message.noparameter";
    private static final String GZLSLID = "gzlslid";
    private static final String QLRID = "qlrid";
    /**
     * ?????? qlridlist ???????????????????????????
     *
     * @param qlridlist ????????? ID ??????
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public void deleteBatchQlr(@NotEmpty(message = "?????????ID ??????????????????") List qlridlist) {
        bdcQlrMapper.deleteBatchQlr(qlridlist);
    }

    /**
     * ?????? gzlslid ??????????????? qlr ??????
     *@param djxl ??????
     * @param qlrlb ??????
     * @param gzlslid ??????
     */
    @Override
    public List<BdcQlrDO> listAllBdcQlr(String gzlslid,String slbh, String qlrlb,String djxl,String xmidList) {
        if(StringUtils.isBlank(gzlslid) && StringUtils.isBlank(slbh)){
            throw new MissingArgumentException(messageProvider.getMessage(NOPARAMETER));
        }
        Map paramMap=new HashMap();
        paramMap.put(GZLSLID,gzlslid);
        paramMap.put(QLRLB,qlrlb);
        paramMap.put("djxl",djxl);
        paramMap.put("slbh",slbh);
        if(StringUtils.isNotBlank(xmidList)){
            List<String> xmids = Arrays.asList(xmidList.split(",").clone());
            paramMap.put("xmidList",xmids);
        }

        return bdcQlrMapper.listAllBdcQlr(paramMap);
    }

    /**
     * ?????? bdcdyh ??????????????????????????? qlr ??????
     * *@param bdcdyh ??????
     *
     * @param bdcdyh
     * @return {List<BdcQlrDO>} ???????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @Override
    public List<BdcQlrDO> listXsCqQlr(@NotBlank(message = "??????????????????????????????") String bdcdyh) {
        Map paramMap=new HashMap();
        paramMap.put("bdcdyh",bdcdyh);
        paramMap.put(QLRLB,CommonConstantUtils.QLRLB_QLR);
        paramMap.put("qszt",CommonConstantUtils.QSZT_VALID);
        paramMap.put("qllxs",Arrays.asList(CommonConstantUtils.QLLX_FDCQ));
        return bdcQlrMapper.listAllBdcQlr(paramMap);
    }

    /**
     * @param qlrid ?????????ID
     * @return BdcQlrDO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ?????????????????????ID?????????????????????
     */
    @Override
    public BdcQlrDO queryBdcQlrByQlrid(@NotBlank(message = "?????????ID????????????") String qlrid) {
        return entityMapper.selectByPrimaryKey(BdcQlrDO.class, qlrid);
    }

    @Override
    public List<BdcQlrDO> queryBdcQlrByQlrxx(BdcQlrDO bdcQlrDO, String order) {
        Example example = entityMapper.objToExample(bdcQlrDO);
        if (example != null && StringUtils.isNotBlank(order)) {
            example.setOrderByClause(order);
        }
        return entityMapper.selectByExampleNotNull(example);
    }

    @Override
    public List<BdcQlrDO> queryBdcQlrWithMhlx(BdcQlrQO bdcQlrQO) {
        return bdcQlrMapper.queryBdcQlrWithMhlx(bdcQlrQO);
    }

    @Override
    public List<BdcDsQlrDO> queryBdcDsQlr(BdcDsQlrDO bdcDsQlrDO, String order) {
        Example example = entityMapper.objToExample(bdcDsQlrDO);
        if (example != null && StringUtils.isNotBlank(order)) {
            example.setOrderByClause(order);
        }
        return entityMapper.selectByExampleNotNull(example);
    }

    @Override
    public void insertBdcQlr(BdcQlrDO bdcQlrDO) {
        if (bdcQlrDO != null) {
            if (StringUtils.isBlank(bdcQlrDO.getQlrid())) {
                bdcQlrDO.setQlrid(UUIDGenerator.generate16());
            }
            if (Objects.isNull(bdcQlrDO.getXb())) {
                //?????????????????????????????????????????????????????????????????????3
                if (StringUtils.isNotBlank(bdcQlrDO.getZjh()) && Objects.equals(CommonConstantUtils.ZJZL_SFZ, bdcQlrDO.getZjzl()) && (StringUtils.length(bdcQlrDO.getZjh()) == 18 || StringUtils.length(bdcQlrDO.getZjh()) == 15)) {
                    bdcQlrDO.setXb(IDCardUtils.getSex(bdcQlrDO.getZjh()));
                } else {
                    bdcQlrDO.setXb(3);
                }
            }
            entityMapper.insertSelective(bdcQlrDO);
        }
    }

    @Override
    public void insertBdcDsQlr(BdcDsQlrDO bdcDsQlrDO) {
        if (bdcDsQlrDO != null) {
            if (StringUtils.isBlank(bdcDsQlrDO.getQlrid())) {
                bdcDsQlrDO.setQlrid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcDsQlrDO);
        }
    }

    @Override
    public int insertBdcDsQlrPl(List<BdcDsQlrDO> bdcDsQlrDOList){
        return entityMapper.insertBatchSelective(bdcDsQlrDOList);
    }

    @Override
    public int updateBdcQlr(BdcQlrDO bdcQlrDO) {
        int num;
        if (bdcQlrDO != null && StringUtils.isNotBlank(bdcQlrDO.getQlrid())) {
            num = entityMapper.updateByPrimaryKeySelective(bdcQlrDO);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(NOPARAMETER));
        }
        return num;
    }

    @Override
    public int updateBdcDsQlr(BdcDsQlrDO bdcDsQlrDO) {
        int num;
        if (bdcDsQlrDO != null && StringUtils.isNotBlank(bdcDsQlrDO.getQlrid())) {
            num = entityMapper.updateByPrimaryKeySelective(bdcDsQlrDO);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(NOPARAMETER));
        }
        return num;
    }

    @Override
    public int deleteQlr(String xmId, String qlrLb) {
        int num = 0;
        if (StringUtils.isNotBlank(xmId)) {
            Example example = new Example(BdcQlrDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo(XMID, xmId);
            if (StringUtils.isNotBlank(qlrLb)) {
                criteria.andEqualTo(QLRLB, qlrLb);
            }
            num = entityMapper.deleteByExample(BdcQlrDO.class, example);
            //????????????qlrid??????????????????
            BdcQlridSyncQO bdcQlridSyncQO =new BdcQlridSyncQO();
            bdcQlridSyncQO.setXmid(xmId);
            bdcQlridSyncQO.setQlrlb(qlrLb);
            bdcQlridSyncService.deleteQlrSync(bdcQlridSyncQO);

        }
        return num;
    }

    /**
     * @param gzlslid
     * @param qlrLb   ??????
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ??????gzlslid???qlrLb????????????????????????????????????
     */
    @Override
    public void deleteBatchQlr(@NotBlank(message = "???????????????ID????????????") String gzlslid, String qlrLb) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Map map = new HashMap<>();
            map.put(GZLSLID, gzlslid);
            map.put(QLRLB, qlrLb);
            bdcQlrMapper.deleteQlr(map);
        }
    }

    /**
     * @param gzlslid
     * @param bdcQlrDO
     * @param djxl
     * @return List<BdcQlrDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ??????gzlslid???bdcQlrDO?????????????????????
     */
    @Override
    public List<BdcQlrDO> insertBatchQlr(String gzlslid,String djxl, BdcQlrDO bdcQlrDO) {
        List<BdcQlrDO> listQlr = new ArrayList<>();
        if (StringUtils.isNotBlank(gzlslid) && bdcQlrDO != null) {
            List<BdcXmDTO> list = bdcXmService.listXmBfxx(gzlslid, null);
            if (CollectionUtils.isNotEmpty(list)) {
                for (BdcXmDTO xmDO : list) {
                    //???????????????
                    if(StringUtils.isBlank(djxl) ||  StringUtils.equals(xmDO.getDjxl(),djxl)) {
                        BdcQlrDO qlrDO = new BdcQlrDO();
                        BeanUtils.copyProperties(bdcQlrDO, qlrDO);
                        qlrDO.setXmid(xmDO.getXmid());
                        qlrDO.setQlrid(UUIDGenerator.generate16());
                        if (Objects.isNull(qlrDO.getXb())) {
                            //?????????????????????????????????????????????????????????????????????3
                            if (StringUtils.isNotBlank(qlrDO.getZjh()) && Objects.equals(CommonConstantUtils.ZJZL_SFZ, qlrDO.getZjzl()) && (StringUtils.length(qlrDO.getZjh()) == 18 || StringUtils.length(qlrDO.getZjh()) == 15)) {
                                qlrDO.setXb(IDCardUtils.getSex(qlrDO.getZjh()));
                            } else {
                                qlrDO.setXb(3);
                            }
                        }
                        listQlr.add(qlrDO);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(listQlr)) {
                List<List> partList = ListUtils.subList(listQlr, 500);
                for (List data : partList) {
                    entityMapper.insertBatchSelective(data);
                }
                //????????????????????????
                listPlBdcQlr(listQlr,CommonConstantUtils.FUN_INSERT);
            }
        }
        return listQlr;
    }

    @Override
    public List<BdcQlrDO> insertBatchBdcQlrByXmids(BdcQlrIdsDTO bdcQlrIdsDTO){
        if(bdcQlrIdsDTO.getBdcQlrDO() == null ||CollectionUtils.isEmpty(bdcQlrIdsDTO.getXmidlist())){
            throw new AppException("???????????????????????????");
        }
        List<BdcQlrDO> listQlr = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcQlrIdsDTO.getXmidlist())) {
            for (String xmid : bdcQlrIdsDTO.getXmidlist()) {
                BdcQlrDO qlrDO = new BdcQlrDO();
                BeanUtils.copyProperties(bdcQlrIdsDTO.getBdcQlrDO(), qlrDO);
                qlrDO.setXmid(xmid);
                qlrDO.setQlrid(UUIDGenerator.generate16());
                if (Objects.isNull(qlrDO.getXb())) {
                    //?????????????????????????????????????????????????????????????????????3
                    if (StringUtils.isNotBlank(qlrDO.getZjh()) && Objects.equals(CommonConstantUtils.ZJZL_SFZ, qlrDO.getZjzl()) && (StringUtils.length(qlrDO.getZjh()) == 18 || StringUtils.length(qlrDO.getZjh()) == 15)) {
                        qlrDO.setXb(IDCardUtils.getSex(qlrDO.getZjh()));
                    } else {
                        qlrDO.setXb(3);
                    }
                }
                listQlr.add(qlrDO);
            }
        }
        if (CollectionUtils.isNotEmpty(listQlr)) {
            List<List> partList = ListUtils.subList(listQlr, 500);
            for (List data : partList) {
                entityMapper.insertBatchSelective(data);
            }
        }
        return listQlr;


    }

    /**
     * @param gzlslid
     * @param bdcQlrDOList
     * @return List<BdcQlrDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ??????gzlslid???bdcQlrDOList?????????????????????
     */
    @Override
    public List<BdcQlrDO> insertBatchQlr(String gzlslid, List<BdcQlrDO> bdcQlrDOList) {
        List<BdcQlrDO> listQlr = new ArrayList<>();
        if (StringUtils.isNotBlank(gzlslid) && CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            List<BdcXmDTO> list = bdcXmService.listXmBfxx(gzlslid, null);
            if (CollectionUtils.isNotEmpty(list)) {
                for (BdcXmDTO xmDO : list) {
                    for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                        BdcQlrDO qlrDO = new BdcQlrDO();
                        BeanUtils.copyProperties(bdcQlrDO, qlrDO);
                        qlrDO.setXmid(xmDO.getXmid());
                        qlrDO.setQlrid(UUIDGenerator.generate16());
                        if (Objects.isNull(qlrDO.getXb())) {
                            //?????????????????????????????????????????????????????????????????????3
                            if (StringUtils.isNotBlank(qlrDO.getZjh()) && Objects.equals(CommonConstantUtils.ZJZL_SFZ, qlrDO.getZjzl()) && (StringUtils.length(qlrDO.getZjh()) == 18 || StringUtils.length(qlrDO.getZjh()) == 15)) {
                                qlrDO.setXb(IDCardUtils.getSex(qlrDO.getZjh()));
                            } else {
                                qlrDO.setXb(3);
                            }
                        }
                        listQlr.add(qlrDO);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(listQlr)) {
                List<List> partList = ListUtils.subList(listQlr, 500);
                for (List data : partList) {
                    entityMapper.insertBatchSelective(data);
                }
            }
        }
        return listQlr;
    }

    @Override
    public int deleteBdcQlrByQlrId(String qlrId) {
        int num = 0;
        if (StringUtils.isNotBlank(qlrId)) {
            num = entityMapper.deleteByPrimaryKey(BdcQlrDO.class, qlrId);
        }
        return num;
    }

    @Override
    public int deleteBdcDsQlrByQlrId(String qlrId) {
        int num = 0;
        if (StringUtils.isNotBlank(qlrId)) {
            num = entityMapper.deleteByPrimaryKey(BdcDsQlrDO.class, qlrId);
        }
        return num;
    }

    @Override
    public int delBdcDsQlr(String xmId, String qlrLb){
        int num = 0;
        if (StringUtils.isNotBlank(xmId)) {
            Example example = new Example(BdcDsQlrDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo(XMID, xmId);
            if (StringUtils.isNotBlank(qlrLb)) {
                criteria.andEqualTo(QLRLB, qlrLb);
            }
            num = entityMapper.deleteByExample(BdcDsQlrDO.class, example);
        }
        return num;

    }

    /**
     * @param bdcQlrDO
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ????????????????????????????????????
     */
    @Override
    public int deleteBdcQlr(BdcQlrDO bdcQlrDO) {
        if (bdcQlrDO != null) {
            Example example = entityMapper.objToExample(bdcQlrDO);
            if (example == null) {
                throw new MissingArgumentException(messageProvider.getMessage(NOPARAMETER));
            }
            return entityMapper.deleteByExampleNotNull(example);
        }
        return 0;
    }

    /**
     * @param gzlslid
     * @param bdcQlrDO ?????? qlrlb  qlrmc zjh
     * @param djxl
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ?????????????????????????????????????????????????????????????????????
     */
    @Override
    public void deleteBdcQlrsByQlrxx(@NotBlank(message = "???????????????ID????????????") String gzlslid,String djxl, BdcQlrDO bdcQlrDO,List<String> xmidList) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Map map = new HashMap<>();
            map.put(GZLSLID, gzlslid);
            map.put(QLRLB, bdcQlrDO.getQlrlb());
            map.put("qlrmc", bdcQlrDO.getQlrmc());
            map.put("zjh", bdcQlrDO.getZjh());
            map.put("djxl", djxl);
            if(CollectionUtils.isNotEmpty(xmidList)){
                List<List> partList = ListUtils.subList(xmidList, 1000);
                for (List data : partList) {
                    map.put("xmidList", data);
                    bdcQlrMapper.deleteQlr(map);
                }

            }else {
                bdcQlrMapper.deleteQlr(map);
            }
        }
    }

    /**
     * @param ybdcQlrDOList
     * @param xmId
     * @param qlrLb
     * @return
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description ?????????????????????????????????????????????????????????????????????????????????????????????????????????
     */
    @Override
    public List<BdcQlrDO> inheritYxmBdcQlr(List<BdcQlrDO> ybdcQlrDOList, String xmId, String qlrLb) {
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ybdcQlrDOList)) {
            for (BdcQlrDO ybdcQlrDO : ybdcQlrDOList) {
                //????????????????????????????????????
                if (ybdcQlrDO != null && StringUtils.isNotBlank(ybdcQlrDO.getQlrlb()) && StringUtils.equals(ybdcQlrDO.getQlrlb(), CommonConstantUtils.QLRLB_QLR)) {
                    ybdcQlrDO = turnQlrAccordQlrLb(ybdcQlrDO, xmId, qlrLb);
                    bdcQlrDOList.add(ybdcQlrDO);
                }
            }
        }
        return bdcQlrDOList;
    }

    /**
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description ?????????????????????????????????????????????????????????????????????????????????
     */
    @Override
    public List<BdcQlrDO> inheritYxmBdcQlr(String yxmId, String xmId, String qlrLb) {
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(yxmId)) {
            BdcQlrDO bdcQlrDO = new BdcQlrDO();
            bdcQlrDO.setXmid(yxmId);
            bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            /**
             *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
             *@description ???????????????????????????????????????
             */
            List<BdcQlrDO> ybdcQlrDOList = queryBdcQlrByQlrxx(bdcQlrDO, null);

            bdcQlrDOList = inheritYxmBdcQlr(ybdcQlrDOList, xmId, qlrLb);
        }
        return bdcQlrDOList;
    }

    /**
     * @param ybdcQlrDOList
     * @param xmId
     * @param qlrLb
     * @return
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description ?????????????????????????????????????????????????????????????????????????????????????????????????????????
     */
    @Override
    public List<BdcQlrDO> inheritYxmBdcYwr(List<BdcQlrDO> ybdcQlrDOList, String xmId, String qlrLb) {
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ybdcQlrDOList)) {
            for (BdcQlrDO ybdcQlrDO : ybdcQlrDOList) {
                //????????????????????????????????????
                if (ybdcQlrDO != null && StringUtils.isNotBlank(ybdcQlrDO.getQlrlb()) && StringUtils.equals(ybdcQlrDO.getQlrlb(), CommonConstantUtils.QLRLB_YWR)) {
                    ybdcQlrDO = turnQlrAccordQlrLb(ybdcQlrDO, xmId, qlrLb);
                    bdcQlrDOList.add(ybdcQlrDO);
                }
            }
        }
        return bdcQlrDOList;
    }

    /**
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description ?????????????????????????????????????????????????????????????????????????????????
     */
    @Override
    public List<BdcQlrDO> inheritYxmBdcYwr(String yxmId, String xmId, String qlrLb) {
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(yxmId)) {
            BdcQlrDO bdcQlrDO = new BdcQlrDO();
            bdcQlrDO.setXmid(yxmId);
            bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
            /**
             *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
             *@description ???????????????????????????????????????
             */
            List<BdcQlrDO> ybdcYwrDOList = queryBdcQlrByQlrxx(bdcQlrDO, null);

            bdcQlrDOList = inheritYxmBdcYwr(ybdcYwrDOList, xmId, qlrLb);
        }
        return bdcQlrDOList;
    }

    /**
     * @param fwFcqlrDOList
     * @param xmId
     * @param qlrLb
     * @param bdcdyh
     * @param
     * @return
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description ????????????????????????????????????????????????
     */
    @Override
    public List<BdcQlrDO> inheritLpbQlr(List<Object> fwFcqlrDOList, String xmId, String bdcdyh, String qlrLb) {
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fwFcqlrDOList)) {
            addQlrList(fwFcqlrDOList, xmId, qlrLb, bdcQlrDOList);
        }
        return bdcQlrDOList;
    }

    @Override
    public List<BdcQlrDO> listBdcQlrOrderBySxh(String xmid, String qlrlb) {
        //??????ID???????????????????????????
        if (StringUtils.isBlank(xmid)) {
            return Collections.emptyList();
        }
        Example example = new Example(BdcQlrDO.class);
        Example.Criteria criteriaBdcYwr = example.createCriteria().andEqualTo(XMID, xmid);
        if (StringUtils.isNotBlank(qlrlb)) {
            criteriaBdcYwr.andEqualTo(QLRLB, qlrlb);
        }
        example.setOrderByClause("sxh asc");
        return entityMapper.selectByExample(example);
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param initServiceQO
     * @return List<?> ?????????????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public List<Object> queryDjQlr(InitServiceQO initServiceQO,String qlrlb) {
        List<Object> qlrList = new ArrayList<>();
        if (initServiceQO != null) {
            Boolean isFw = bdcBdcdyService.judgeIsFwByBdcdyh(initServiceQO.getBdcdyh());
            if (isFw) {
                Object bdcdyDTO = initServiceQO.getBdcdyDTO();
                if (bdcdyDTO instanceof BdcdyResponseDTO) {
                    List<FwFcqlrDO> list = ((BdcdyResponseDTO) bdcdyDTO).getQlrList();
                    if (CollectionUtils.isNotEmpty(list)) {
                        qlrList.addAll(list);
                    }
                } else if (bdcdyDTO instanceof GzwDcbResponseDTO) {
                    List<GzwQlrDO> list = initServiceQO.getGzwDcbResponseDTO().getGzwQlrDOList();
                    if (CollectionUtils.isNotEmpty(list)) {
                        qlrList.addAll(list);
                    }
                }
            } else {
                //???????????? ?????????????????????  ?????????????????????,????????????????????????????????????????????????,?????????????????????
                if(initServiceQO.getBdcXm() !=null  &&initServiceQO.getDjxxResponseDTO() != null && initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() != null){
                    if(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof NydDjdcbResponseDTO){
                        if(CommonConstantUtils.QLRLB_QLR.equals(qlrlb)){
                            List<CbzdCbfDO> list=((NydDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getCbzdCbfDOList();
                            if(CollectionUtils.isNotEmpty(list)){
                                qlrList.addAll(list);
                            }
                        }else if(CommonConstantUtils.QLRLB_YWR.equals(qlrlb)){
                            List<CbzdFbfDO> list= ((NydDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getCbzdFbfDOList();
                            if(CollectionUtils.isNotEmpty(list)){
                                qlrList.addAll(list);
                            }
                        }
                    }else if(initServiceQO.getDjxxResponseDTO() != null && initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() != null
                            && initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof JyqDkDcbResponseDTO){
                        if(CommonConstantUtils.QLRLB_QLR.equals(qlrlb)){
                            List<JyqDkQlrDO> list=((JyqDkDcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getJyqDkQlrDOList();
                            if(CollectionUtils.isNotEmpty(list)){
                                qlrList.addAll(list);
                            }
                        }else if(CommonConstantUtils.QLRLB_YWR.equals(qlrlb)){
                            JyqDkLcfDO jyqDkLcfDO = ((JyqDkDcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()).getJyqDkLcfDO();
                            if(jyqDkLcfDO != null){
                                qlrList.add(jyqDkLcfDO);
                            }
                        }
                    }
                }
                //???????????????????????????,??????????????????
                if(CollectionUtils.isEmpty(qlrList)){
                    List<DjxxQlrDTO> list=initServiceQO.getDjxxResponseDTO().getQlrList();
                    if(CollectionUtils.isNotEmpty(list)){
                        qlrList.addAll(list);
                    }
                }
            }
        }
        return qlrList;
    }

    /**
     * ????????????????????????????????????
     *
     * @param xmidlist ???????????????????????? ?????? id ??????
     * @param bdcQlrDO ????????????
     * @return {List<BdcQlrDO>}
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public List<BdcQlrDO> insertBdcQlrByNum(BdcQlrDO bdcQlrDO, List<String> xmidlist) {
        List<BdcQlrDO> listQlr = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(xmidlist) && bdcQlrDO != null) {
            for (String xmid : xmidlist) {
                BdcQlrDO qlrDO = new BdcQlrDO();
                BeanUtils.copyProperties(bdcQlrDO, qlrDO);
                qlrDO.setQlrid(UUIDGenerator.generate16());
                qlrDO.setXmid(xmid);
                if (Objects.isNull(qlrDO.getXb())) {
                    //?????????????????????????????????????????????????????????????????????3
                    if (StringUtils.isNotBlank(qlrDO.getZjh()) && Objects.equals(CommonConstantUtils.ZJZL_SFZ, qlrDO.getZjzl()) && (StringUtils.length(qlrDO.getZjh()) == 18 || StringUtils.length(qlrDO.getZjh()) == 15)) {
                        qlrDO.setXb(IDCardUtils.getSex(qlrDO.getZjh()));
                    } else {
                        qlrDO.setXb(3);
                    }
                }
                listQlr.add(qlrDO);
            }
            if (CollectionUtils.isNotEmpty(listQlr)) {
                List<List> partList = ListUtils.subList(listQlr, 500);
                for (List data : partList) {
                    entityMapper.insertBatchSelective(data);
                }
            }
        }
        return listQlr;
    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @param gzlslid ???????????????id
     * @param qlrlb   ???????????????
     * @param djxl    ????????????
     * @return ???????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @Override
    public String queryQlrsYbzs(@NotBlank(message = "gzlslid ????????????") String gzlslid, @NotBlank(message = "qlrlb ????????????") String qlrlb,String djxl) {
        StringBuilder stringBuilder=new StringBuilder();
        List<BdcQlrDO> list=listAllBdcQlr(gzlslid,null,qlrlb,djxl,null);
        //?????????????????????????????????
        list.sort((o1, o2) -> o1.getXmid().compareTo(o2.getXmid()));
        if(CollectionUtils.isNotEmpty(list)){
            Set<String> ywr=new LinkedHashSet<>();
            //???????????????
            boolean isQy=CommonConstantUtils.QLRLX_QY.equals(list.get(0).getQlrlx());
            for(BdcQlrDO bdcQlrDO:list){
                String zjh=bdcQlrDO.getZjh();
                //???????????????????????????
                if(isQy){
                    zjh=StringUtils.EMPTY;
                }
                if(StringUtils.isNotBlank(zjh) && zjh.length()==18 && CardNumberTransformation.idCard18(zjh)){
                    zjh=CardNumberTransformation.idCard18to15(zjh);
                }
                if(StringUtils.isNotBlank(bdcQlrDO.getQlrmc()) && !ywr.contains(bdcQlrDO.getQlrmc()+zjh)){
                    ywr.add(bdcQlrDO.getQlrmc()+zjh);
                    if(stringBuilder.length()+bdcQlrDO.getQlrmc().length()<30){
                        stringBuilder.append(bdcQlrDO.getQlrmc()).append(" ");
                    }else if(stringBuilder.length() ==0){
                        stringBuilder.append(bdcQlrDO.getQlrmc()).append(CommonConstantUtils.SUFFIX_PL);
                        break;
                    }else{
                        stringBuilder.setLength(stringBuilder.length()-1);
                        stringBuilder.append(CommonConstantUtils.SUFFIX_PL);
                        break;
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * ?????????????????????id??????????????????zsid
     * @param gzlslid ???????????????id
     * @param zsid    ??????id
     *  @param qlrmc ?????????
     * @param zjh ???????????????
     */
    @Override
    public void updateQlrZsid(@NotBlank(message = "gzlslid ????????????") String gzlslid, @NotBlank(message = "zsid ????????????") String zsid,String qlrmc,String zjh) {
        Map map=new HashMap();
        map.put(GZLSLID,gzlslid);
        map.put("zsid",zsid);
        map.put("qlrmc",qlrmc);
        map.put("zjh",zjh);
        bdcQlrMapper.updateQlrZsid(map);
    }

    @Override
    public int updateBatchBdcQlr(BdcDjxxUpdateQO bdcDjxxUpdateQO) throws ClassNotFoundException{
        if (bdcDjxxUpdateQO ==null ||StringUtils.isAnyBlank(bdcDjxxUpdateQO.getJsonStr()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            throw new NullPointerException("??????????????????");
        }
        //????????????json??????
        JSONObject jsonObject = JSON.parseObject(bdcDjxxUpdateQO.getJsonStr());
        Map map = new HashMap();
        //????????????????????????
        String statement = SqlUtils.getBatchUpdateStatement(jsonObject, BdcQlrDO.class.getName());
        if(!statement.contains("SET")) {
            return 0;
        }
        map.put("statement", statement);
        //where ??????
        map.putAll(bdcDjxxUpdateQO.getWhereMap());
        //??????????????????
        Object object = JSON.parseObject(bdcDjxxUpdateQO.getJsonStr(), BdcQlrDO.class);
        map.put("record", object);
        return bdcQlrMapper.updateBatchBdcQlr(map);

    }

    @Override
    public List<BdcQlrGroupDTO> groupQlrYwrByZjh(@NotBlank(message = "gzlslid ????????????") String gzlslid, String qlrlb, String djxl,String xmidList) {
        //????????????????????????????????????/?????????
        List<BdcQlrDO> bdcQlrDOList = listAllBdcQlr(gzlslid,null, qlrlb, djxl, xmidList);
        List<BdcQlrGroupDTO> bdcQlrGroupDTOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            Map<String, List<BdcQlrDO>> qlrMap =new HashMap<>();
            //????????????????????????????????????????????????
            List<BdcQlrDO> qyList = bdcQlrDOList.stream().filter(bdcQlrDO -> CommonConstantUtils.QLRLX_QY.equals(bdcQlrDO.getQlrlx())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(qyList)) {
                //??????????????????????????????
                qlrMap.putAll(qyList.stream().collect(Collectors.groupingBy(BdcQlrDO::getQlrmc)));
                bdcQlrDOList.removeAll(qyList);
            }

            if(CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                //?????????????????????????????????????????????,??????????????????
                List<BdcQlrDO> qlrList = bdcQlrDOList.stream().filter(bdcQlrDO -> StringUtils.isBlank(bdcQlrDO.getZjh())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(qlrList)) {
                    //????????????????????????????????????
                    qlrMap.putAll(qlrList.stream().collect(Collectors.groupingBy(BdcQlrDO::getQlrmc)));
                }
                //?????????????????????????????????????????????????????????
                //??????????????????????????????????????????15???18????????????????????????18????????????????????????
                bdcQlrDOList = bdcQlrDOList.stream().filter(bdcQlrDO -> StringUtils.isNotBlank(bdcQlrDO.getZjh())).collect(Collectors.toList());
                bdcQlrDOList.forEach(bdcQlrDO ->
                        bdcQlrDO.setZjh(CardNumberTransformation.idCard15to18(bdcQlrDO.getZjh()))
                        );
                qlrMap.putAll(bdcQlrDOList.stream().collect(Collectors.groupingBy(BdcQlrDO::getZjh)));
            }
            if(MapUtils.isNotEmpty(qlrMap)) {
                for (Map.Entry<String, List<BdcQlrDO>> entry : qlrMap.entrySet()) {
                    BdcQlrGroupDTO bdcQlrGroupDTO = new BdcQlrGroupDTO();
                    List<String> otherBdcQlrDOList = new ArrayList<>();
                    List<String> otherXmidList = new ArrayList<>();
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        //??????????????????????????????????????????????????????list??????
                        if (i == 0) {
                            BdcQlrDO bdcQlrDO = entry.getValue().get(0);
                            bdcQlrGroupDTO.setBdcQlrDO(bdcQlrDO);
                        } else {
                            otherBdcQlrDOList.add(entry.getValue().get(i).getQlrid());
                            otherXmidList.add(entry.getValue().get(i).getXmid());
                            bdcQlrGroupDTO.setOtherBdcQlridList(otherBdcQlrDOList);
                            bdcQlrGroupDTO.setOtherXmidList(otherXmidList);
                        }
                    }
                    bdcQlrGroupDTOList.add(bdcQlrGroupDTO);
                }
            }
            return bdcQlrGroupDTOList;
        } else {
            return Collections.emptyList();
        }

    }

    @Override
    public List<JSONObject> listZhBdcQlr(JSONObject obj, String type){
        List<JSONObject> jsonObjectList = new ArrayList<>();
        jsonObjectList.add(obj);
        JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(obj));
        List<BdcXmZhxxDTO> bdcXmZhxxDTOList = bdcXmService.queryBdcXmZhxx(object.get("xmid").toString(),true);
        if (CollectionUtils.isNotEmpty(bdcXmZhxxDTOList)) {
            for (int j = 0; j < bdcXmZhxxDTOList.size(); j++) {
                BdcXmZhxxDTO bdcXmZhxxDTO = bdcXmZhxxDTOList.get(j);
                if (!StringUtils.equals(bdcXmZhxxDTO.getXmid(), obj.get("xmid").toString())) {
                    /**
                     * @author <a href="mailto:sunchao@gtmap.cn">liaoxiang</a>
                     * @description ??????????????????????????????????????????
                     */
                    if (bdcXmZhxxDTO.getSxh() > 1 && object.get(QLRLB) != null &&StringUtils.equals(object.get(QLRLB).toString(), CommonConstantUtils.QLRLB_QLR)) {
                        //?????????????????????????????????
                        BdcCshFwkgSlDO bdcCshFwkgSl =bdcXmService.queryCshFwkgSl(bdcXmZhxxDTO.getXmid());
                        if(bdcCshFwkgSl != null){
                            //??????????????????????????????????????????????????????,?????????????????????????????????
                            if(Constants.QLRSJLY_YQLR.equals(bdcCshFwkgSl.getQlrsjly())){
                                if (StringUtils.equals(type, CommonConstantUtils.FUN_UPDATE) || StringUtils.equals(type, CommonConstantUtils.FUN_DELETE)) {
                                    jsonObjectList.add(getUpdateQlr(obj, bdcXmZhxxDTO.getXmid(), CommonConstantUtils.QLRLB_QLR));
                                }
                                if (StringUtils.equals(type, CommonConstantUtils.FUN_INSERT)) {
                                    jsonObjectList.add(getInsertQlr(obj, bdcXmZhxxDTO.getXmid(), CommonConstantUtils.QLRLB_QLR,bdcXmZhxxDTO.getQllx()));
                                }
                            }
                            if(Constants.QLRSJLY_YQLR.equals(bdcCshFwkgSl.getYwrsjly())){
                                JSONObject syccQlrJson = null;
                                if (StringUtils.equals(type, CommonConstantUtils.FUN_UPDATE) || StringUtils.equals(type, CommonConstantUtils.FUN_DELETE)) {
                                    syccQlrJson = getUpdateQlr(obj, bdcXmZhxxDTO.getXmid(), CommonConstantUtils.QLRLB_YWR);
                                    jsonObjectList.add(syccQlrJson);
                                }
                                if (StringUtils.equals(type, CommonConstantUtils.FUN_INSERT)) {
                                    syccQlrJson = getInsertQlr(obj, bdcXmZhxxDTO.getXmid(), CommonConstantUtils.QLRLB_YWR,bdcXmZhxxDTO.getQllx());
                                    jsonObjectList.add(syccQlrJson);
                                }
                                //????????????????????????????????????????????????????????????
                                if(syncQlrToDsqlr && (StringUtils.equals(type, CommonConstantUtils.FUN_INSERT) || updateQlrToDsqlr)){
                                    syncDsfQlr(type, bdcXmZhxxDTO, syccQlrJson);
                                }
                            }
                        }

                    }
                    /**
                     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
                     * @description ??????????????????????????????????????????
                     */
                    if (bdcXmZhxxDTO.getSxh() == 1 && object.get(QLRLB) != null && StringUtils.equals(object.get(QLRLB).toString(), CommonConstantUtils.QLRLB_YWR)) {
                        //??????????????????????????????????????????
                        BdcCshFwkgSlDO bdcCshFwkgSl =bdcXmService.queryCshFwkgSl(obj.get("xmid").toString());
                        if(bdcCshFwkgSl != null){
                            //???????????????????????????????????????????????????????????????,??????????????????????????????????????????
                            if(Constants.QLRSJLY_YQLR.equals(bdcCshFwkgSl.getYwrsjly())){
                                if (StringUtils.equals(type, CommonConstantUtils.FUN_UPDATE) || StringUtils.equals(type, CommonConstantUtils.FUN_DELETE)) {
                                    jsonObjectList.add(getUpdateQlr(obj, bdcXmZhxxDTO.getXmid(), CommonConstantUtils.QLRLB_QLR));
                                }
                                if (StringUtils.equals(type, CommonConstantUtils.FUN_INSERT)) {
                                    jsonObjectList.add(getInsertQlr(obj, bdcXmZhxxDTO.getXmid(), CommonConstantUtils.QLRLB_QLR,bdcXmZhxxDTO.getQllx()));
                                }

                            }
                            if(Constants.QLRSJLY_YYWR.equals(bdcCshFwkgSl.getYwrsjly())){
                                if (StringUtils.equals(type, CommonConstantUtils.FUN_UPDATE) || StringUtils.equals(type, CommonConstantUtils.FUN_DELETE)) {
                                    jsonObjectList.add(getUpdateQlr(obj, bdcXmZhxxDTO.getXmid(), CommonConstantUtils.QLRLB_YWR));
                                }
                                if (StringUtils.equals(type, CommonConstantUtils.FUN_INSERT)) {
                                    jsonObjectList.add(getInsertQlr(obj, bdcXmZhxxDTO.getXmid(), CommonConstantUtils.QLRLB_YWR,bdcXmZhxxDTO.getQllx()));
                                }

                            }

                        }

                    }
                } else {
                    /**
                     * @author wangyinghao
                     * @description ????????????????????????
                     * ????????????????????????????????????????????????????????????????????????
                     */
                    if(syncCurrentYwrToDsqlr && CommonConstantUtils.QLRLB_YWR.equals(obj.get("qlrlb"))) {
                        JSONObject syccQlrJson = null;
                        if (StringUtils.equals(type, CommonConstantUtils.FUN_UPDATE) || StringUtils.equals(type, CommonConstantUtils.FUN_DELETE)) {
                            syccQlrJson = getUpdateQlr(obj, bdcXmZhxxDTO.getXmid(), CommonConstantUtils.QLRLB_YWR);
                        }
                        if (StringUtils.equals(type, CommonConstantUtils.FUN_INSERT)) {
                            syccQlrJson = getInsertQlr(obj, bdcXmZhxxDTO.getXmid(), CommonConstantUtils.QLRLB_YWR, bdcXmZhxxDTO.getQllx());
                            if(StringUtils.isBlank(jsonObjectList.get(0).getString(QLRID))){
                                jsonObjectList.get(0).put(QLRID,syccQlrJson.getString(QLRID));
                            }
                        }
                       syncDsfQlr(type, bdcXmZhxxDTO, syccQlrJson);
                    }
                }
            }
        }
        return jsonObjectList;

    }

    /**
     * @param updateBdcQlrDOList  ???????????????  -- ????????????,??????????????????????????????????????????????????????
     * @param type ???????????????update:??????,insert:?????????DELETE:??????
     * @return ???????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">wangyinghao</a>
     * @description ?????????????????????????????????????????????????????????
     */
    @Override
    public List<BdcQlrDO> listPlBdcQlr(List<BdcQlrDO> updateBdcQlrDOList, String type) {
        List<BdcQlrDO> jsonObjectList = new ArrayList<>();
        BdcQlrDO updateBdcQlrDO = updateBdcQlrDOList.get(0);
        BdcXmDO bdcXmDO = bdcXmService.queryBdcXmByPrimaryKey(updateBdcQlrDO.getXmid());
        if(Objects.isNull(bdcXmDO)){
            return jsonObjectList;
        }
        String xmid = updateBdcQlrDO.getXmid();
        Boolean isYdya = false;
        BdcQl bdcQl = bdcQllxService.queryQllxDO(xmid);
        if (bdcQl instanceof BdcYgDO) {
            BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
            if (bdcYgDO.getYgdjzl() != null && ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, bdcYgDO.getYgdjzl())) {
                isYdya = true;
            }
        }
        //???????????????????????????
        if (!(bdcXmDO.getQllx().equals(CommonConstantUtils.QLLX_DYAQ_DM) || isYdya)) {
           //return jsonObjectList;
        }

        /**
         * ????????????????????????????????????????????????????????????????????????
         * ??????????????????????????????????????????????????????????????????,
         * ?????????????????????????????????,?????????,?????????id,????????????????????????????????????
         * ???????????????,???????????????????????????????????????
         */
        if(syncCurrentYwrToDsqlr && CommonConstantUtils.QLRLB_YWR.equals(updateBdcQlrDO.getQlrlb())) {
            JSONObject syccQlrJson = null;
            if (StringUtils.equals(type, CommonConstantUtils.FUN_UPDATE)
                    || StringUtils.equals(type, CommonConstantUtils.FUN_DELETE)) {
                if (updateBdcQlrDO.getQlrid() != null) {
                    //????????????????????????xmid
                    List<BdcXmDO> bdcXmDOS = bdcXmService.listBdcXm(bdcXmDO.getGzlslid());
                    List<Object> xmids = bdcXmDOS.stream().map(BdcXmDO::getXmid).collect(Collectors.toList());
                    //???????????????
                    BdcQlrDO orgBdcQlrDO = queryBdcQlrByQlrid(updateBdcQlrDO.getQlrid());
                    if (orgBdcQlrDO != null) {
                        //?????????????????????????????????????????????
                        Example example = new Example(BdcDsQlrDO.class);
                        Example.Criteria criteria = example.createCriteria();
                        criteria.andIn("xmid",xmids)
                                .andEqualTo("qlrlb",dsqlrlb)
                                .andEqualTo("qlrmc",orgBdcQlrDO.getQlrmc());
                        //?????????????????????????????????
                        if ((orgBdcQlrDO.getQlrlx() ==null ||!ArrayUtils.contains(CommonConstantUtils.QLRLX_QYJGGJ, orgBdcQlrDO.getQlrlx())) && StringUtils.isNotBlank(orgBdcQlrDO.getZjh())) {
                            criteria.andEqualTo("zjh", orgBdcQlrDO.getZjh());
                        }
                        if (StringUtils.equals(type, CommonConstantUtils.FUN_UPDATE)) {
                            BdcDsQlrDO bdcDsQlrDO = new BdcDsQlrDO();
                            BeanUtils.copyProperties(updateBdcQlrDO, bdcDsQlrDO);
                            bdcDsQlrDO.setQlrlb(dsqlrlb);
                            bdcDsQlrDO.setXmid(null);
                            bdcDsQlrDO.setQlrid(null);
                            //??????????????????,?????????,???????????????????????????
                            entityMapper.updateByExampleSelective(bdcDsQlrDO, example);
                        }else if(StringUtils.equals(type, CommonConstantUtils.FUN_DELETE)){
                            //??????????????????????????????????????????????????????????????????
                            entityMapper.deleteByExample(example);
                        }
                    }
                }
            }
            if (StringUtils.equals(type, CommonConstantUtils.FUN_INSERT)) {
                //??????????????????
                List<BdcDsQlrDO> bdcDsQlrDOS = new ArrayList<>();
                for (BdcQlrDO bdcQlrDO : updateBdcQlrDOList) {
                    BdcDsQlrDO bdcDsQlrDO = new BdcDsQlrDO();
                    BeanUtils.copyProperties(bdcQlrDO,bdcDsQlrDO);
                    bdcDsQlrDO.setQlrlb(dsqlrlb);
                    bdcDsQlrDOS.add(bdcDsQlrDO);
                }
                if(CollectionUtils.isNotEmpty(bdcDsQlrDOS)) {
                    List<Object> qlridList = bdcDsQlrDOS
                            .stream()
                            .map(BdcDsQlrDO::getQlrid)
                            .collect(Collectors.toList());
                    //?????????qlrid????????????,???????????????????????????????????????????????????
                    Example example = new Example(BdcDsQlrDO.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andIn("qlrid", qlridList);
                    entityMapper.deleteByExample(example);
                    entityMapper.insertBatchSelective(bdcDsQlrDOS);
                }
            }
        }
        return jsonObjectList;
    }


    /**
     * ???????????????????????????????????????
     * @param type
     * @param bdcXmZhxxDTO
     * @param syccQlrJson
     */
    private void syncDsfQlr(String type, BdcXmZhxxDTO bdcXmZhxxDTO, JSONObject syccQlrJson) {
        // ???????????????????????? ?????????????????????
        boolean isYdya = false;
        String xmid = bdcXmZhxxDTO.getXmid();
        BdcQl bdcQl = bdcQllxService.queryQllxDO(xmid);
        if (bdcQl instanceof BdcYgDO) {
            BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
            if (bdcYgDO.getYgdjzl() != null && ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, bdcYgDO.getYgdjzl())) {
                isYdya = true;
            }
        }
        if (null != syccQlrJson && (bdcXmZhxxDTO.getQllx().equals(CommonConstantUtils.QLLX_DYAQ_DM) || isYdya)) {
            if (!StringUtils.equals(type, "DELETE")) {
                BdcDsQlrDO bdcDsQlrDO = JSONObject.toJavaObject(syccQlrJson, BdcDsQlrDO.class);
                if (StringUtils.isNoneBlank(bdcDsQlrDO.getQlrmc(), bdcDsQlrDO.getXmid(), bdcDsQlrDO.getQlrid())) {
                    // ??????????????????????????????????????????3???4 ????????????????????????????????? ????????????qlrid??????
                    Example example = new Example(BdcDsQlrDO.class);
                    Example.Criteria criteria = example.createCriteria();
                    //?????????????????????+??????ID?????????????????????
                    criteria.andEqualTo("xmid", bdcDsQlrDO.getXmid())
                            .andEqualTo("qlrid", bdcDsQlrDO.getQlrid())
                            .andEqualTo("qlrlb", dsqlrlb);
                    List<BdcDsQlrDO> bdcDsQlrDOS = entityMapper.selectByExample(example);
                    if (CollectionUtils.isEmpty(bdcDsQlrDOS)){
                        example.clear();
                        example.createCriteria()
                                .andEqualTo("xmid", bdcDsQlrDO.getXmid())
                                .andEqualTo("qlrmc", bdcDsQlrDO.getQlrmc())
                                .andEqualTo("qlrlb", dsqlrlb);
                        bdcDsQlrDOS = entityMapper.selectByExample(example);
                    }
                    // ?????????????????????????????????????????? ??????????????? ??????????????????dsqlr ???????????????qlr?????????
                    if (CollectionUtils.isNotEmpty(bdcDsQlrDOS)) {
                        LOGGER.info("??????????????????????????????????????????????????????????????????????????????????????????{}", bdcDsQlrDO);
                        bdcDsQlrDO.setQlrid(bdcDsQlrDOS.get(0).getQlrid());
                        bdcDsQlrDO.setQlrlb(dsqlrlb);
                        bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(bdcDsQlrDO), BdcDsQlrDO.class.getName());
                    } else {
                        bdcDsQlrDO.setQlrlb(dsqlrlb);
                        insertBdcDsQlr(bdcDsQlrDO);
                    }
                }
            } else {
                BdcDsQlrDO bdcDsQlrDO = JSONObject.toJavaObject(syccQlrJson, BdcDsQlrDO.class);
                deleteBdcDsQlrByQlrId(bdcDsQlrDO.getQlrid());
            }
        }
    }

    @Override
    public List<BdcQlrDO> listBdcQlrByCondition(BdcQlrDO bdcQlrDO) {
        Example example = entityMapper.objToExample(bdcQlrDO);
        return entityMapper.selectByExampleNotNull(example);
    }

    /**
     * @param gzlslid
     * @param djxl
     * @param bdcDsQlrDO
     * @param xmidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/2/7 10:31
     */
    @Override
    public void deleteDsQlrsByQlrxx(String gzlslid, String djxl, BdcDsQlrDO bdcDsQlrDO, List<String> xmidList) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Map map = new HashMap<>();
            map.put(GZLSLID, gzlslid);
            map.put(QLRLB, bdcDsQlrDO.getQlrlb());
            map.put("qlrmc", bdcDsQlrDO.getQlrmc());
            map.put("zjh", bdcDsQlrDO.getZjh());
            map.put("djxl", djxl);
            if (CollectionUtils.isNotEmpty(xmidList)) {
                List<List> partList = ListUtils.subList(xmidList, 1000);
                for (List data : partList) {
                    map.put("xmidList", data);
                    bdcQlrMapper.deleteDsQlr(map);
                }

            } else {
                bdcQlrMapper.deleteDsQlr(map);
            }
        }
    }

    @Override
    public List<BdcQlrDO> listBdcQlrByXmidList(List<String> xmidList,String qlrlb){
        List<BdcQlrDO> bdcQlrDOList =new ArrayList<>();
        if(CollectionUtils.isNotEmpty(xmidList)){
            List<List> partList = ListUtils.subList(xmidList, 1000);
            Map map =new HashMap();
            for (List data : partList) {
                map.put("xmidList", data);
                map.put("qlrlb",qlrlb);
                List<BdcQlrDO> qlrList =bdcQlrMapper.listAllBdcQlr(map);
                if(CollectionUtils.isNotEmpty(qlrList)){
                    bdcQlrDOList.addAll(qlrList);
                }
            }

        }
        return bdcQlrDOList;

    }

    /**
     * ???????????????????????????????????????????????????????????????
     * @param zsid ??????ID
     * @param qlrlb ???????????????
     * @return {List} ???????????????
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public List<BdcQlrDO> listBdcQlrByZsid(String zsid, String qlrlb) {
        if(StringUtils.isBlank(zsid) || StringUtils.isBlank(qlrlb)) {
            throw new MissingArgumentException("????????????ID????????????????????????");
        }

        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setZsid(zsid);
        bdcQlrQO.setQlrlb(qlrlb);
        return bdcQlrMapper.listBdcQlrByZsid(bdcQlrQO);
    }


    private JSONObject getUpdateQlr(JSONObject obj, String xmid, String qlrlb) {
        JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(obj));
        if (object.get(QLRID) != null) {
            BdcQlrDO qlrDO = queryBdcQlrByQlrid(object.get(QLRID).toString());
            if (qlrDO != null) {
                BdcQlrDO bdcQlrQO = new BdcQlrDO();
                bdcQlrQO.setQlrlb(qlrlb);
                bdcQlrQO.setQlrmc(qlrDO.getQlrmc());
                bdcQlrQO.setZjh(qlrDO.getZjh());
                bdcQlrQO.setXmid(xmid);
                List<BdcQlrDO> bdcQlrDOList = queryBdcQlrByQlrxx(bdcQlrQO,"qlrlb asc,sxh asc");
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    BdcQlrDO bdcQlrDO = bdcQlrDOList.get(0);
                    object.put(QLRLB, bdcQlrDO.getQlrlb());
                    object.put("xmid", bdcQlrDO.getXmid());
                    object.put(QLRID, bdcQlrDO.getQlrid());
                    //???????????????????????????
                    if (!zhlcTbDlr) {
                        object.remove("dlrmc");
                        object.remove("dlrdh");
                        object.remove("dlrzjzl");
                        object.remove("dlrzjh");
                        object.remove("dljg");
                    }
                }
            }
        }
        return object;
    }

    private JSONObject getInsertQlr(JSONObject obj, String xmid, String qlrlb,Integer qllx) {
        JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(obj));
        object.put(QLRLB, qlrlb);
        object.put("xmid", xmid);
        object.put(QLRID, UUIDGenerator.generate16());
        //???????????????????????????
        if (!zhlcTbDlr) {
            object.remove("dlrmc");
            object.remove("dlrdh");
            object.remove("dlrzjzl");
            object.remove("dlrzjh");
            object.remove("dljg");
        }
        object.put("qlrtz",bdcQlrtzService.getQlrtzMrz(qllx,qlrlb));
        return object;
    }

    /**
     * ?????????????????????
     *
     * @param ybdcQlrDO
     * @param xmId
     * @param qlrLb
     * @return
     */
    private BdcQlrDO turnQlrAccordQlrLb(BdcQlrDO ybdcQlrDO, String xmId, String qlrLb) {
        BdcQlrDO bdcQlrDO = new BdcQlrDO();
        if (ybdcQlrDO != null && StringUtils.isNotBlank(xmId) && StringUtils.isNotBlank(qlrLb)) {
            bdcQlrDO.setXmid(xmId);
            bdcQlrDO.setQlrlb(qlrLb);
            bdcQlrDO.setQlrid(UUIDGenerator.generate16());
            //??????xml?????????????????????????????????
            if (CommonConstantUtils.QLRLB_YWR.equals(qlrLb)) {
                initDozerMapper.map(ybdcQlrDO, bdcQlrDO, "ywr_yxm");
            } else {
                initDozerMapper.map(ybdcQlrDO, bdcQlrDO);
            }
            if (Objects.isNull(bdcQlrDO.getXb())) {
                //?????????????????????????????????????????????????????????????????????3
                if (StringUtils.isNotBlank(bdcQlrDO.getZjh()) && Objects.equals(CommonConstantUtils.ZJZL_SFZ, bdcQlrDO.getZjzl()) &&
                        (StringUtils.length(bdcQlrDO.getZjh()) == 18 || StringUtils.length(bdcQlrDO.getZjh()) == 15)) {
                    bdcQlrDO.setXb(IDCardUtils.getSex(bdcQlrDO.getZjh()));
                } else {
                    bdcQlrDO.setXb(3);
                }
            }
        }
        return bdcQlrDO;
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param qlrList
     * @param xmId
     * @param qlrLb
     * @param bdcQlrDOList
     * @param <T>
     */
    private <T> void addQlrList(List<T> qlrList, String xmId, String qlrLb, List<BdcQlrDO> bdcQlrDOList) {
        for (T qlrDO : qlrList) {
            if (qlrDO != null) {
                BdcQlrDO bdcQlrDO = new BdcQlrDO();
                bdcQlrDO.setXmid(xmId);
                bdcQlrDO.setQlrlb(qlrLb);
                bdcQlrDO.setQlrid(UUIDGenerator.generate16());
                if (CommonConstantUtils.QLRLB_YWR.equals(qlrLb) && qlrDO instanceof DjxxQlrDTO) {
                    initDozerMapper.map(qlrDO, bdcQlrDO, "ywr_lpb");
                } else {
                    initDozerMapper.map(qlrDO, bdcQlrDO);
                }
                if (Objects.isNull(bdcQlrDO.getXb())) {
                    //?????????????????????????????????????????????????????????????????????3
                    if (StringUtils.isNotBlank(bdcQlrDO.getZjh()) && Objects.equals(CommonConstantUtils.ZJZL_SFZ, bdcQlrDO.getZjzl()) &&
                            (StringUtils.length(bdcQlrDO.getZjh()) == 18 || StringUtils.length(bdcQlrDO.getZjh()) == 15)) {
                        bdcQlrDO.setXb(IDCardUtils.getSex(bdcQlrDO.getZjh()));
                    } else {
                        bdcQlrDO.setXb(3);
                    }
                }

                bdcQlrDOList.add(bdcQlrDO);
            }
        }
    }
}
