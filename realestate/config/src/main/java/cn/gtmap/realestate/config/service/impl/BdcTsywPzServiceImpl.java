package cn.gtmap.realestate.config.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcTsywPzDO;
import cn.gtmap.realestate.common.core.domain.BdcTsywZdyzdxDO;
import cn.gtmap.realestate.common.core.domain.BdcZdSsjGxDO;
import cn.gtmap.realestate.common.core.domain.BdcZdTsywPzZxtDO;
import cn.gtmap.realestate.common.core.dto.config.BdcTsywPzDTO;
import cn.gtmap.realestate.common.core.enums.BdcServiceEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.config.BdcTsywPzQO;
import cn.gtmap.realestate.common.core.qo.config.BdcZdSsjGxQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.config.core.mapper.BdcTsywPzMapper;
import cn.gtmap.realestate.config.service.BdcTsywPzService;
import cn.gtmap.realestate.config.service.BdcZdSsjgxService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/3/5
 * @description YML????????????
 */
@Service
public class BdcTsywPzServiceImpl implements BdcTsywPzService {

    private static final Logger logger = LoggerFactory.getLogger(BdcTsywPzServiceImpl.class);

    /*
     * ???????????????????????? 31536000??? ????????????
     */
    private static Long INVALID_TIME = 31536000L;

    public static final String ZDYZDX_GXJKML = "gxjkml";

    @Autowired
    private BdcTsywPzMapper bdcTsywPzMapper;
    @Autowired
    private Repo repo;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    BdcTsywPzUtils bdcTsywPzUtils;

    @Autowired
    BdcZdSsjgxService bdcZdSsjgxService;

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    ConfigurableEnvironment environment;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestRpcUtils restRpcUtils;
    @PostConstruct
    public void initTsywPz() {
        //????????????
        redisUtils.deleteKey(CommonConstantUtils.REDIS_TSYW_PZ);
        Example zxtExample = new Example(BdcZdTsywPzZxtDO.class);
        List<BdcZdTsywPzZxtDO> djzxtzdList =entityMapper.selectByExample(zxtExample);
        if(CollectionUtils.isNotEmpty(djzxtzdList)){
            for(BdcZdTsywPzZxtDO bdcZdTsywPzZxtDO:djzxtzdList){
                redisUtils.deleteKey(CommonConstantUtils.REDIS_TSYW_PZ+"_"+bdcZdTsywPzZxtDO.getDm());
            }
        }

        //????????????YML??????
        Example example = new Example(BdcTsywPzDO.class);
        List<BdcTsywPzDO> bdcTsywPzDOList = entityMapper.selectByExample(example);
        if(bdcTsywPzDOList ==null){
            bdcTsywPzDOList =new ArrayList<>();
        }

        //???????????????????????????????????????map???key???value
        for(BdcTsywPzDO bdcTsywPzDO : bdcTsywPzDOList){
            if(StringUtils.isNotBlank(bdcTsywPzDO.getPzmc())) {
                //??????????????????????????????????????????????????????
                if(StringUtils.isNotBlank(bdcTsywPzDO.getPzz()) ||StringUtils.isNotBlank(bdcTsywPzDO.getPzqsz())) {
                    // ?????????redis
                    savePzzToRedis(bdcTsywPzDO);
                }
            }
        }
    }

    @Override
    public BdcTsywPzDO queryBdcTsywPzById(String pzid){
        if (StringUtils.isBlank(pzid)) {
            throw new AppException("?????????????????????????????????????????????");
        }
        return entityMapper.selectByPrimaryKey(BdcTsywPzDO.class,pzid);

    }

    @Override
    public List<BdcTsywPzDO> listBdcTsywPz(BdcTsywPzQO bdcTsywPzQO){
        if (!CheckParameter.checkAnyParameter(bdcTsywPzQO)) {
            throw new MissingArgumentException("????????????????????????");
        }
        return bdcTsywPzMapper.listBdcTsywPz(bdcTsywPzQO);


    }

    @Override
    public Page<BdcTsywPzDO> listBdcTsywPzByPage(Pageable pageable, BdcTsywPzQO bdcTsywPzQO){
        //????????????????????????,????????????
        bdcTsywPzQO.setPzdfbb(bdcTsywPzUtils.getStringValueOfTsywPzz("html.version",""));
        String json = JSONObject.toJSONString(bdcTsywPzQO);
        Map<String, Object> map = PageUtils.pageableSort(pageable, JSONObject.parseObject(json, HashMap.class));
        return repo.selectPaging("listBdcTsywPzByPage", map, pageable);

    }

    @Override
    public List<BdcTsywPzDO> bdcTsywPzList(BdcTsywPzQO bdcTsywPzQO){
        Map<String, Object> map =JSONObject.parseObject(JSONObject.toJSONString(bdcTsywPzQO), HashMap.class);
        return repo.selectList("listBdcTsywPzByPage", map);
    }

    @Override
    public int updateBdcTsywPz(BdcTsywPzDO bdcTsywPzDO){
        if (bdcTsywPzDO == null || StringUtils.isBlank(bdcTsywPzDO.getPzid())) {
            throw new MissingArgumentException("YML???????????????YML??????????????????");
        }
        bdcTsywPzDO.setPzzt(CommonConstantUtils.SF_S_DM);
        //?????????????????????
        BdcTsywPzDO ybdcTsywPzDO =queryBdcTsywPzById(bdcTsywPzDO.getPzid());
        int result =entityMapper.updateByPrimaryKeySelective(bdcTsywPzDO);
        refreshTsywPz(bdcTsywPzDO.getPzid(),ybdcTsywPzDO);
        refleshConfigValue(bdcTsywPzDO.getPzid());
        return result;
    }

    @Override
    public BdcTsywPzDO insertBdcTsywPz(BdcTsywPzDO bdcTsywPzDO){
        if (bdcTsywPzDO == null ||StringUtils.isAnyBlank(bdcTsywPzDO.getPzmc(), bdcTsywPzDO.getPzsm()) || bdcTsywPzDO.getPzlx() ==null) {
            throw new MissingArgumentException("YML???????????????YML?????????????????????????????????????????????????????????????????????");
        }
        if(StringUtils.isBlank(bdcTsywPzDO.getPzid())){
            bdcTsywPzDO.setPzid(UUIDGenerator.generate16());
        }
        if(StringUtils.isBlank(bdcTsywPzDO.getPzcjr())){
            UserDto userDto = userManagerUtils.getCurrentUser();
            if(userDto != null) {
                bdcTsywPzDO.setPzcjr(userDto.getAlias());
            }

        }
        bdcTsywPzDO.setPzxgsj(new Date());
        if(bdcTsywPzDO.getPzzt() ==null) {
            bdcTsywPzDO.setPzzt(CommonConstantUtils.SF_F_DM);
        }
        entityMapper.insertSelective(bdcTsywPzDO);

        //?????????redis
        savePzzToRedis(bdcTsywPzDO);
        //????????????
        refleshConfigValue(bdcTsywPzDO.getPzid());
        return bdcTsywPzDO;


    }

    @Override
    public void insertBdcTsywPzPl(List<BdcTsywPzDO> bdcTsywPzDOList){
        if(CollectionUtils.isNotEmpty(bdcTsywPzDOList)){
            UserDto userDto = userManagerUtils.getCurrentUser();
            for(BdcTsywPzDO bdcTsywPzDO:bdcTsywPzDOList){
                if (bdcTsywPzDO == null ||StringUtils.isAnyBlank(bdcTsywPzDO.getPzmc(), bdcTsywPzDO.getPzsm()) || bdcTsywPzDO.getPzlx() ==null) {
                    throw new MissingArgumentException("YML???????????????YML?????????????????????????????????????????????????????????????????????");
                }
                if(StringUtils.isBlank(bdcTsywPzDO.getPzid())){
                    bdcTsywPzDO.setPzid(UUIDGenerator.generate16());
                }
                if(StringUtils.isBlank(bdcTsywPzDO.getPzcjr())){
                    if(userDto != null) {
                        bdcTsywPzDO.setPzcjr(userDto.getAlias());
                    }
                }
                bdcTsywPzDO.setPzxgsj(new Date());
                if(bdcTsywPzDO.getPzzt() ==null) {
                    bdcTsywPzDO.setPzzt(CommonConstantUtils.SF_F_DM);
                }
            }
            entityMapper.insertBatchSelective(bdcTsywPzDOList);
            //??????????????????
            for(BdcTsywPzDO bdcTsywPzDO:bdcTsywPzDOList){
                savePzzToRedis(bdcTsywPzDO);
            }
        }

    }

    /**
     * @param pzid ??????ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  ??????redis??????
     */
    private void refreshTsywPz(String pzid,BdcTsywPzDO ybdcTsywPzDO){
        //????????????redis
        deleteTsywpzRedis(ybdcTsywPzDO);
        if(StringUtils.isNotBlank(pzid)) {
            BdcTsywPzDO bdcTsywPzDO =queryBdcTsywPzById(pzid);
            if(bdcTsywPzDO != null) {
                if(StringUtils.isNotBlank(bdcTsywPzDO.getPzz()) ||StringUtils.isNotBlank(bdcTsywPzDO.getPzqsz())) {
                    savePzzToRedis(bdcTsywPzDO);
                }else{
                    deleteTsywpzRedis(bdcTsywPzDO);
                }
            }
        }


    }

    /**
     * @param bdcTsywPzDO ??????
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????redis??????
     */
    private void deleteTsywpzRedis(BdcTsywPzDO bdcTsywPzDO){
        if(bdcTsywPzDO != null) {
            List<String> pzzxtList =new ArrayList<>();
            if (StringUtils.isNotBlank(bdcTsywPzDO.getPzzxt())) {
                pzzxtList = Arrays.asList(bdcTsywPzDO.getPzzxt().split(CommonConstantUtils.ZF_YW_DH));
            }
            //?????????????????????????????????????????????????????????
            if(CommonConstantUtils.TSYWPZ_PZLX_ARR.equals(bdcTsywPzDO.getPzlx()) &&StringUtils.isNotBlank(bdcTsywPzDO.getPzz())){
                Map<String,String> map = JSONObject.parseObject(bdcTsywPzDO.getPzz(),Map.class);
                if(MapUtils.isNotEmpty(map)) {
                    for (Map.Entry entry : map.entrySet()) {
                        if(entry.getKey() != null &&StringUtils.isNotBlank(entry.getKey().toString()) &&entry.getValue() != null &&StringUtils.isNotBlank(entry.getValue().toString())) {
                            if(CollectionUtils.isNotEmpty(pzzxtList)){
                                //?????????????????????????????????redis??????key??????REDIS_TSYW_PZ_???????????????
                                for(String pzzxt:pzzxtList){
                                    redisUtils.deleteHashValue(CommonConstantUtils.REDIS_TSYW_PZ+"_"+pzzxt, bdcTsywPzDO.getPzmc() + CommonConstantUtils.ZF_YW_JH + entry.getKey().toString());
                                }
                            }else {
                                redisUtils.deleteHashValue(CommonConstantUtils.REDIS_TSYW_PZ, bdcTsywPzDO.getPzmc() + CommonConstantUtils.ZF_YW_JH + entry.getKey().toString());
                            }
                        }
                    }
                }
            }else {
                if (CollectionUtils.isNotEmpty(pzzxtList)) {
                    for (String pzzxt : pzzxtList) {
                        redisUtils.deleteHashValue(CommonConstantUtils.REDIS_TSYW_PZ + "_" + pzzxt, bdcTsywPzDO.getPzmc());
                    }
                } else {
                    redisUtils.deleteHashValue(CommonConstantUtils.REDIS_TSYW_PZ, bdcTsywPzDO.getPzmc());
                }
            }
        }
    }

    @Override
    public List<BdcTsywZdyzdxDO> listZdyzdx(BdcTsywZdyzdxDO bdcTsywZdyzdxDO){
        if (StringUtils.isBlank(bdcTsywZdyzdxDO.getZdyzdxbs())) {
            throw new MissingArgumentException("???????????????????????????????????????");
        }
        Example example = new Example(BdcTsywZdyzdxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("zdyzdxbs", bdcTsywZdyzdxDO.getZdyzdxbs());
        if (StringUtils.isNotBlank(bdcTsywZdyzdxDO.getZdxsjz())) {
            criteria.andEqualTo("zdxsjz", StringUtils.trim(bdcTsywZdyzdxDO.getZdxsjz()));
        }
        if (StringUtils.isNotBlank(bdcTsywZdyzdxDO.getZdxxsz())) {
            criteria.andEqualTo("zdxxsz", StringUtils.trim(bdcTsywZdyzdxDO.getZdxxsz()));
        }
        List<BdcTsywZdyzdxDO> bdcTsywZdyzdxDOList = entityMapper.selectByExampleNotNull(example);
        if (CollectionUtils.isEmpty(bdcTsywZdyzdxDOList)) {
            return new ArrayList<>();
        }
        return bdcTsywZdyzdxDOList;

    }


    /**
     * ??????redis??????????????????????????????????????????????????????????????????????????????
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return: void
     */
    @Override
    public void initYmlData() {
        final String value = redisUtils.getStringValue(CommonConstantUtils.REDIS_INIT_YML);
        logger.info("?????????Yml???????????????{}", value);
        if(StringUtils.isNotBlank(value)){
            Map cacheMap = (Map) JSON.parse(value);
            if(Objects.nonNull(cacheMap)){
                BdcTsywPzQO bdcTsywPzQO = new BdcTsywPzQO();
                bdcTsywPzQO.setPzzt(CommonConstantUtils.SF_F_DM);
                List<BdcTsywPzDO> bdcTsywPzDOList = this.listBdcTsywPz(bdcTsywPzQO);
                if(CollectionUtils.isNotEmpty(bdcTsywPzDOList)){
                    for(BdcTsywPzDO bdcTsywPzDO : bdcTsywPzDOList){
                        logger.info("???????????????????????????{}",bdcTsywPzDO.getPzmc());
                        String val = YmlReadUtil.getMapVal(cacheMap, bdcTsywPzDO.getPzmc());
                        if(StringUtils.isNotBlank(val)){
                            logger.info("????????????????????????{} ; ???????????????{}",bdcTsywPzDO.getPzmc(),val);
                            bdcTsywPzDO.setPzz(val);
                            updateBdcTsywPz(bdcTsywPzDO);
                        }else if(CommonConstantUtils.TSYWPZ_PZLX_ARR.equals(bdcTsywPzDO.getPzlx())){
                            //????????????????????????????????????
                            String arrayData = YmlReadUtil.handleArrayData(cacheMap, bdcTsywPzDO);
                            if(StringUtils.isNotBlank(arrayData)){
                                logger.info("????????????????????????{} ; ???????????????{}",bdcTsywPzDO.getPzmc(),arrayData);
                                bdcTsywPzDO.setPzz(arrayData);
                                updateBdcTsywPz(bdcTsywPzDO);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public BdcTsywPzDTO queryBdcTsywPzDTO(String pzid){
        BdcTsywPzDTO bdcTsywPzDTO = new BdcTsywPzDTO();
        if(StringUtils.isNotBlank(pzid)){
            BdcTsywPzDO bdcTsywPzDO = queryBdcTsywPzById(pzid);
            if(bdcTsywPzDO != null){
                BeanUtils.copyProperties(bdcTsywPzDO,bdcTsywPzDTO);
                //????????????????????????
                if(StringUtils.isNotBlank(bdcTsywPzDO.getPzzzdxbs()) &&CommonConstantUtils.TSYWPZ_PZLX_ZDYZD.equals(bdcTsywPzDO.getPzlx()) ||CommonConstantUtils.TSYWPZ_PZLX_ZDYZDDX.equals(bdcTsywPzDO.getPzlx())){
                    BdcTsywZdyzdxDO bdcTsywZdyzdxDO =new BdcTsywZdyzdxDO();
                    bdcTsywZdyzdxDO.setZdyzdxbs(bdcTsywPzDO.getPzzzdxbs());
                    List<BdcTsywZdyzdxDO> bdcTsywZdyzdxDOList = listZdyzdx(bdcTsywZdyzdxDO);
                    if(CollectionUtils.isNotEmpty(bdcTsywZdyzdxDOList)){
                        bdcTsywPzDTO.setBdcTsywZdyzdxDOList(bdcTsywZdyzdxDOList);
                    }

                }

            }
        }
        return bdcTsywPzDTO;

    }

    @Override
    public String saveBdcTsywPzDTO(BdcTsywPzDTO bdcTsywPzDTO){
        if(null == bdcTsywPzDTO){
            return null;
        }

        //??????????????????+?????????????????????
        checkTsywPzmc(bdcTsywPzDTO);

        BdcTsywPzDO bdcTsywPzDO =new BdcTsywPzDO();
        BeanUtils.copyProperties(bdcTsywPzDTO,bdcTsywPzDO);

        // 1???????????????????????????
        if(StringUtils.isBlank(bdcTsywPzDTO.getPzid())){
            bdcTsywPzDO.setPzid(UUIDGenerator.generate());
        } else {
            // ?????????????????????????????????????????????
            delBdcTsywPzDTO(bdcTsywPzDTO);

        }

        // 2????????????????????????
        insertBdcTsywPz(bdcTsywPzDO);

        //3.????????????????????????
        if(CollectionUtils.isNotEmpty(bdcTsywPzDTO.getBdcTsywZdyzdxDOList())) {
            List<BdcTsywPzDO> bdcTsywPzDOList =listOtherTsywPzByZdxbs(bdcTsywPzDTO);
            if(CollectionUtils.isEmpty(bdcTsywPzDOList)){
                bdcTsywPzDTO.getBdcTsywZdyzdxDOList().forEach(bdcTsywZdyzdxDO -> {
                    bdcTsywZdyzdxDO.setId(UUIDGenerator.generate16());

                });
                entityMapper.insertBatchSelective(bdcTsywPzDTO.getBdcTsywZdyzdxDOList());
            }

        }
        return bdcTsywPzDO.getPzid();



    }

    @Override
    public int delBdcTsywPz(String pzid){
        if(StringUtils.isBlank(pzid)){
            throw new AppException("??????????????????????????????????????????");
        }
        return entityMapper.deleteByPrimaryKey(BdcTsywPzDO.class,pzid);

    }

    @Override
    public int delBdcTsywPzDTO(BdcTsywPzDTO bdcTsywPzDTO){
        if(bdcTsywPzDTO ==null ||StringUtils.isBlank(bdcTsywPzDTO.getPzid())){
            throw new AppException("??????????????????????????????????????????");
        }

        if(CollectionUtils.isNotEmpty(bdcTsywPzDTO.getBdcTsywZdyzdxDOList()) &&StringUtils.isNotBlank(bdcTsywPzDTO.getBdcTsywZdyzdxDOList().get(0).getZdyzdxbs())){
            //??????????????????????????????????????????????????????
            List<BdcTsywPzDO> bdcTsywPzDOList =listOtherTsywPzByZdxbs(bdcTsywPzDTO);
            if(CollectionUtils.isEmpty(bdcTsywPzDOList)){
                BdcTsywZdyzdxDO bdcTsywZdyzdxDO = new BdcTsywZdyzdxDO();
                bdcTsywZdyzdxDO.setZdyzdxbs(bdcTsywPzDTO.getBdcTsywZdyzdxDOList().get(0).getZdyzdxbs());
                entityMapper.delete(bdcTsywZdyzdxDO);
            }

        }
        //????????????????????????
        return delBdcTsywPz(bdcTsywPzDTO.getPzid());
    }

    @Override
    public List<BdcTsywPzDO> listOtherTsywPzByZdxbs(BdcTsywPzDO bdcTsywPzDO){
        List<BdcTsywPzDO> bdcTsywPzDOList =new ArrayList<>();
        if(bdcTsywPzDO ==null ||StringUtils.isBlank(bdcTsywPzDO.getPzzzdxbs())){
            return null;
        }
        //??????????????????????????????????????????????????????
        BdcTsywPzQO bdcTsywPzQO =new BdcTsywPzQO();
        bdcTsywPzQO.setPzzzdxbs(bdcTsywPzDO.getPzzzdxbs());
        List<BdcTsywPzDO> tsywPzDOList =listBdcTsywPz(bdcTsywPzQO);
        if(CollectionUtils.isNotEmpty(tsywPzDOList)){
            for(BdcTsywPzDO tsywPzDO:tsywPzDOList){
                if(!StringUtils.equals(tsywPzDO.getPzmc(),bdcTsywPzDO.getPzmc())){
                    bdcTsywPzDOList.add(tsywPzDO);

                }
            }
        }
        return bdcTsywPzDOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchModifyTsywpzGnmk(List<BdcTsywPzDO> bdcTsywPzDOList) {
        if(CollectionUtils.isNotEmpty(bdcTsywPzDOList)){
            for(BdcTsywPzDO bdcTsywPzDO:bdcTsywPzDOList){
                entityMapper.updateByPrimaryKeySelective(bdcTsywPzDO);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteTsywpz(List<String> ids) {
        if(CollectionUtils.isNotEmpty(ids)){
            for(String pzid : ids){
                BdcTsywPzDO bdcTsywPzDO =queryBdcTsywPzById(pzid);
                entityMapper.deleteByPrimaryKey(BdcTsywPzDO.class, pzid);
                //??????redis??????
                deleteTsywpzRedis(bdcTsywPzDO);
            }
        }
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  ????????????????????????
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncGxjkmlZdyzdx(){
        BdcZdSsjGxQO bdcZdSsjGxQO =new BdcZdSsjGxQO();
        List<BdcZdSsjGxDO> bdcZdSsjGxDOList =bdcZdSsjgxService.listSsjgx(bdcZdSsjGxQO);
        if(CollectionUtils.isNotEmpty(bdcZdSsjGxDOList)){
            List<BdcTsywZdyzdxDO> bdcTsywZdyzdxDOS =new ArrayList<>();
            List<String> fmldmList =new ArrayList<>();
            for(BdcZdSsjGxDO bdcZdSsjGxDO:bdcZdSsjGxDOList){
                if(!fmldmList.contains(bdcZdSsjGxDO.getFmldm())) {
                    BdcTsywZdyzdxDO bdcTsywZdyzdxDO = new BdcTsywZdyzdxDO();
                    bdcTsywZdyzdxDO.setId(UUIDGenerator.generate16());
                    bdcTsywZdyzdxDO.setZdyzdxbs(ZDYZDX_GXJKML);
                    bdcTsywZdyzdxDO.setZdxsjz(bdcZdSsjGxDO.getFmldm());
                    bdcTsywZdyzdxDO.setZdxxsz(bdcZdSsjGxDO.getFmlmc());
                    bdcTsywZdyzdxDOS.add(bdcTsywZdyzdxDO);
                    fmldmList.add(bdcZdSsjGxDO.getFmldm());
                }
            }
            if(CollectionUtils.isNotEmpty(bdcTsywZdyzdxDOS)){
                deleteZdyzdxByZdyzdxbs(ZDYZDX_GXJKML);
                entityMapper.insertBatchSelective(bdcTsywZdyzdxDOS);

            }
        }



    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????
     */
    private void deleteZdyzdxByZdyzdxbs(String zdyzdxbs){
        if(StringUtils.isNotBlank(zdyzdxbs)){
            Example example =new Example(BdcTsywZdyzdxDO.class);
            example.createCriteria().andEqualTo("zdyzdxbs",zdyzdxbs);
            entityMapper.deleteByExampleNotNull(example);
        }

    }

    /**
     * @param bdcTsywPzDO ??????????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????????????????redis??????
     */
    private void savePzzToRedis(BdcTsywPzDO bdcTsywPzDO){
        if(StringUtils.isBlank(bdcTsywPzDO.getPzz()) &&StringUtils.isBlank(bdcTsywPzDO.getPzqsz())){
            return;
        }
        //
        if(StringUtils.isBlank(bdcTsywPzDO.getPzz())){
            bdcTsywPzDO.setPzz(bdcTsywPzDO.getPzqsz());
        }
        //??????
        List<String> pzzxtList =new ArrayList<>();
        if(StringUtils.isNotBlank(bdcTsywPzDO.getPzzxt())){
            pzzxtList =Arrays.asList(bdcTsywPzDO.getPzzxt().split(CommonConstantUtils.ZF_YW_DH));
        }
        //?????????????????????????????????????????????????????????
        if(CommonConstantUtils.TSYWPZ_PZLX_ARR.equals(bdcTsywPzDO.getPzlx())){
            Map<String,String> map = JSONObject.parseObject(bdcTsywPzDO.getPzz(),Map.class);
            if(MapUtils.isNotEmpty(map)) {

                for (Map.Entry entry : map.entrySet()) {
                    if(entry.getKey() != null &&StringUtils.isNotBlank(entry.getKey().toString()) &&entry.getValue() != null &&StringUtils.isNotBlank(entry.getValue().toString())) {
                        if(CollectionUtils.isNotEmpty(pzzxtList)){
                            //?????????????????????????????????redis??????key??????REDIS_TSYW_PZ_???????????????
                            for(String pzzxt:pzzxtList){
                                redisUtils.addHashValue(CommonConstantUtils.REDIS_TSYW_PZ+"_"+pzzxt, bdcTsywPzDO.getPzmc() + CommonConstantUtils.ZF_YW_JH + entry.getKey().toString(), entry.getValue().toString(), INVALID_TIME);
                            }
                        }else {
                            redisUtils.addHashValue(CommonConstantUtils.REDIS_TSYW_PZ, bdcTsywPzDO.getPzmc() + CommonConstantUtils.ZF_YW_JH + entry.getKey().toString(), entry.getValue().toString(), INVALID_TIME);
                        }
                    }
                }
            }
        }else{
            if(CollectionUtils.isNotEmpty(pzzxtList)){
                //?????????????????????????????????redis??????key??????REDIS_TSYW_PZ_???????????????
                for(String pzzxt:pzzxtList){
                    redisUtils.addHashValue(CommonConstantUtils.REDIS_TSYW_PZ+"_"+pzzxt, bdcTsywPzDO.getPzmc(), bdcTsywPzDO.getPzz(), INVALID_TIME);
                }
            }else {
                redisUtils.addHashValue(CommonConstantUtils.REDIS_TSYW_PZ, bdcTsywPzDO.getPzmc(), bdcTsywPzDO.getPzz(), INVALID_TIME);
            }
        }


    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????
     */
    private void checkTsywPzmc(BdcTsywPzDO bdcTsywPzDO){
        if(StringUtils.isBlank(bdcTsywPzDO.getPzmc())){
            throw new AppException("???????????????????????????");
        }
        //??????????????????????????????
        List<String> pzzxtList =new ArrayList<>();
        if(StringUtils.isNotBlank(bdcTsywPzDO.getPzzxt())){
            pzzxtList =Arrays.asList(bdcTsywPzDO.getPzzxt().split(CommonConstantUtils.ZF_YW_DH));
        }
        BdcTsywPzQO bdcTsywPzQO =new BdcTsywPzQO();
        bdcTsywPzQO.setPzmc(bdcTsywPzDO.getPzmc());
        List<BdcTsywPzDO> bdcTsywPzDOList =listBdcTsywPz(bdcTsywPzQO);
        if(CollectionUtils.isNotEmpty(bdcTsywPzDOList)){
            //???????????????????????????????????????
            for(BdcTsywPzDO tsywPzDO:bdcTsywPzDOList){
                if(!StringUtils.equals(tsywPzDO.getPzid(),bdcTsywPzDO.getPzid())){
                    if(StringUtils.isBlank(tsywPzDO.getPzzxt()) ||StringUtils.isBlank(bdcTsywPzDO.getPzzxt())){
                        throw new AppException("????????????????????????,?????????");
                    }
                    List<String> ypzzxtList =Arrays.asList(tsywPzDO.getPzzxt().split(CommonConstantUtils.ZF_YW_DH));
                    if(CollectionUtils.isNotEmpty(ypzzxtList)){
                        for(String ypzzxt:ypzzxtList){
                            if(pzzxtList.contains(ypzzxt)){
                                throw new AppException("????????????????????????,?????????");
                            }
                        }

                    }


                }
            }
        }

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description ???????????????
     */
    private void refleshConfigValue(String pzid){
        BdcTsywPzDO bdcTsywPzDO = queryBdcTsywPzById(pzid);
        if(bdcTsywPzDO != null && StringUtils.isNotBlank(bdcTsywPzDO.getPzz()) && StringUtils.isNotBlank(bdcTsywPzDO.getPzzxt())) {
            if(!"public-app".equals(bdcTsywPzDO.getPzzxt())){
                //????????????????????????????????????????????????????????????????????????????????????
                List<String> pzzxtList = Arrays.asList(bdcTsywPzDO.getPzzxt().split(CommonConstantUtils.ZF_YW_DH));
                List<List<ServiceInstance>> servicesList = new ArrayList<>();
                for (String pzzxtName : pzzxtList) {
                    List<ServiceInstance> instances = discoveryClient.getInstances(pzzxtName);
                    servicesList.add(instances);
                }
                //??????????????????
                for (List<ServiceInstance> serviceInstances : servicesList) {
                    for (ServiceInstance serviceInstance : serviceInstances) {
                        //?????????????????????????????????
                        try {
                            szpzky(serviceInstance,bdcTsywPzDO);
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error("??????????????????????????????????????????{}",e.getMessage());
                        }
                    }
                }
            }
        }


    }
    /**
    * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
    * @param
    * @return
    * @description ??????????????????
    **/
    private void szpzky(ServiceInstance serviceInstance,BdcTsywPzDO bdcTsywPzDO) throws UnsupportedEncodingException {
        String serviceId = serviceInstance.getServiceId();
        String host = serviceInstance.getHost();
        String uri = host + CommonConstantUtils.ZF_YW_MH+ serviceInstance.getPort();
        String appName = "";
        Map<String,Object> params = new HashMap<>();
        params.put("bdcTsywPzDOStr", URLEncoder.encode(JSON.toJSONString(bdcTsywPzDO), "UTF-8"));
        String serviceName = BdcServiceEnum.getServiceById(serviceId);

        //uri = "192.168.90.7"+CommonConstantUtils.ZF_YW_MH+ serviceInstance.getPort();
        if(StringUtils.isNotBlank(serviceName)){
            appName = uri + CommonConstantUtils.ZF_YW_XG + serviceName;
            logger.info("?????????????????????????????????,appName:{},params:{},bdcTsywPzDO:{}",appName,params,JSON.toJSONString(bdcTsywPzDO));
            Object rpcRequest = restRpcUtils.getRpcRequest(appName, "/rest/v1.0/refleshConfig?bdcTsywPzDOStr=#{bdcTsywPzDOStr}", params);
            logger.info("????????????????????????????????????????????????{}",rpcRequest);
        }else{
            logger.info("?????????????????????????????????,serviceId:{}????????????????????????????????????",serviceId);
        }
    }


}
