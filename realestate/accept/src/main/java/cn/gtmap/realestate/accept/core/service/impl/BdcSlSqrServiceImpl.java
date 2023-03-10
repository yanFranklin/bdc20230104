package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcSlSqrMapper;
import cn.gtmap.realestate.accept.core.service.BdcSlDyaqService;
import cn.gtmap.realestate.accept.core.service.BdcSlJbxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlJtcyService;
import cn.gtmap.realestate.accept.core.service.BdcSlSqrService;
import cn.gtmap.realestate.accept.core.service.BdcSlXmLsgxService;
import cn.gtmap.realestate.accept.core.service.BdcSlXmService;
import cn.gtmap.realestate.accept.service.BdcCommonSlService;
import cn.gtmap.realestate.accept.util.CommonUtil;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlDeleteCsDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlJtcyGroupDTO;
import cn.gtmap.realestate.common.core.dto.accept.YcslYmxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJtcyQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSqrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description ????????????????????????????????????
 */
@Service
public class BdcSlSqrServiceImpl implements BdcSlSqrService,BdcCommonSlService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private BdcSlSqrMapper bdcSlSqrMapper;
    @Autowired
    BdcSlJtcyService bdcSlJtcyService;
    @Autowired
    private DozerBeanMapper acceptDozerMapper;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcSlXmService bdcSlXmService;
    @Autowired
    BdcSlJbxxService bdcSlJbxxService;
    @Autowired
    BdcSlXmLsgxService bdcSlXmLsgxService;
    @Autowired
    BdcSlDyaqService bdcSlDyaqService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Value("${default.gtgyDjxl:}")
    private String gtgyDjxl;

    /**
     * ????????????????????????????????????????????? ?????????false??????????????????????????????????????????????????????
     */
    @Value("${default.gyfsdgyz:true}")
    private boolean gyfsdgyz;

    /**
     * @param sqrid ????????????ID
     * @return ????????????????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ??????????????????ID??????????????????????????????
     */
    @Override
    public BdcSlSqrDO queryBdcSlSqrBySqrid(String sqrid) {
        if (StringUtils.isBlank(sqrid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return entityMapper.selectByPrimaryKey(BdcSlSqrDO.class, sqrid);
    }

    /**
     * @param xmid ??????ID
     * @return ????????????????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ????????????ID??????????????????????????????
     */
    @Override
    public List<BdcSlSqrDO> listBdcSlSqrByXmid(String xmid,String sqrlb) {
        List<BdcSlSqrDO> bdcSlSqrDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlSqrDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("xmid", xmid);
            if(StringUtils.isNotBlank(sqrlb)){
                criteria.andEqualTo("sqrlb", sqrlb);
            }
            bdcSlSqrDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlSqrDOList)) {
            bdcSlSqrDOList = Collections.emptyList();
        }
        return bdcSlSqrDOList;
    }

    /**
     * @param bdcSlSqrDO ????????????????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ??????????????????????????????
     */
    @Override
    public BdcSlSqrDO insertBdcSlSqr(BdcSlSqrDO bdcSlSqrDO) {
        if (bdcSlSqrDO != null) {
            if (StringUtils.isBlank(bdcSlSqrDO.getSqrid())) {
                bdcSlSqrDO.setSqrid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcSlSqrDO);
        }
        return bdcSlSqrDO;
    }

    /**
     * @param bdcSlSqrDO ????????????????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ??????????????????????????????
     */
    @Override
    public Integer updateBdcSlSqr(BdcSlSqrDO bdcSlSqrDO) {
        int result;
        if (bdcSlSqrDO != null && StringUtils.isNotBlank(bdcSlSqrDO.getSqrid())) {
            result = entityMapper.updateByPrimaryKeySelective(bdcSlSqrDO);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return result;
    }

    /**
     * @param sqrid ????????????ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ??????????????????ID??????????????????????????????
     */
    @Override
    public Integer deleteBdcSlSqrBySqrid(String sqrid) {
        int result = 0;
        if (StringUtils.isNotBlank(sqrid)) {
            result = entityMapper.deleteByPrimaryKey(BdcSlSqrDO.class, sqrid);
        }
        return result;
    }

    /**
     * @param xmid ??????ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ????????????ID??????????????????????????????
     */
    @Override
    public Integer deleteBdcSlSqrByXmid(String xmid,String sqrlb) {
        int result = 0;
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlSqrDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("xmid", xmid);
            if(StringUtils.isNotBlank(sqrlb)){
                criteria.andEqualTo("sqrlb", sqrlb);
            }
            result = entityMapper.deleteByExample(example);
        }
        return result;
    }

    @Override
    public void batchDeleteSlSqr(String jbxxid){
        if (StringUtils.isBlank(jbxxid)){
            throw new MissingArgumentException("jbxxid");
        }
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("jbxxid",jbxxid);
        bdcSlSqrMapper.deleteBdcSlSqr(paramMap);


    }

    @Override
    public List<BdcSlSqrDO> changeQlrToYwr(List<BdcSlSqrDO> bdcSlSqrDOList){
        List<BdcSlSqrDO> newbdcSlSqrDOList =new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcSlSqrDOList)){
            for(BdcSlSqrDO bdcSlSqrDO:bdcSlSqrDOList){
                if(CommonConstantUtils.QLRLB_QLR.equals(bdcSlSqrDO.getSqrlb())){
                    BdcSlSqrDO newbdcSlSqrDO = new BdcSlSqrDO();
                    BeanUtils.copyProperties(bdcSlSqrDO, newbdcSlSqrDO);
                    newbdcSlSqrDO.setSqrlb(CommonConstantUtils.QLRLB_YWR);
                    newbdcSlSqrDOList.add(newbdcSlSqrDO);
                }
            }
        }
        return newbdcSlSqrDOList;

    }

    @Override
    public List<BdcSlSqrDO> listBdcSlSqrBySqrmcAndZjh(String sqrmc,String zjh,String sqrlb){
        List<BdcSlSqrDO> bdcSlSqrDOList =new ArrayList<>();
        if (StringUtils.isNotBlank(zjh)) {
            Example example = new Example(BdcSlSqrDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("zjh", zjh);
            if(StringUtils.isNotBlank(sqrmc)){
                criteria.andEqualTo("sqrmc", sqrmc);
            }
            if(StringUtils.isNotBlank(sqrlb)){
                criteria.andEqualTo("sqrlb", sqrlb);
            }
            bdcSlSqrDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlSqrDOList)) {
            bdcSlSqrDOList = Collections.emptyList();
        }
        return bdcSlSqrDOList;

    }

    @Override
    public void deleteSqrAndJtcyByXmid(String xmid,String sqrlb){
        List<BdcSlSqrDO> bdcSlSqrDOList = listBdcSlSqrByXmid(xmid, sqrlb);
        if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
            for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
                //???????????????????????????????????????
                bdcSlJtcyService.deleteBdcSlJtcyBySqrid(bdcSlSqrDO.getSqrid());
            }
            //???????????????
            deleteBdcSlSqrByXmid(xmid,sqrlb);
        }

    }

    /**
     * ??????????????????????????????QO???????????????????????????
     *
     * @param bdcSlSqrQO
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [bdcSlSqrQO] ???????????????QO
     * @return: List<BdcSlSqrDO> ???????????????DO
     */
    @Override
    public List<BdcSlSqrDO> queryBdcSlSqrBySqrQO(BdcSlSqrQO bdcSlSqrQO) {
        Example example = new Example(BdcSlSqrDO.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(bdcSlSqrQO.getSqrid())){
            criteria.andEqualTo("sqrid", bdcSlSqrQO.getSqrid());
        }
        if(StringUtils.isNotBlank(bdcSlSqrQO.getSqrlb())){
            criteria.andEqualTo("sqrlb", bdcSlSqrQO.getSqrlb());
        }
        if(StringUtils.isNotBlank(bdcSlSqrQO.getXmid())){
            criteria.andEqualTo("xmid", bdcSlSqrQO.getXmid());
        }
        if(StringUtils.isNotBlank(bdcSlSqrQO.getZjh())){
            criteria.andEqualTo("zjh", bdcSlSqrQO.getZjh());
        }
        if(StringUtils.isNotBlank(bdcSlSqrQO.getSqrmc())){
            criteria.andEqualTo("sqrmc", bdcSlSqrQO.getSqrmc());
        }
        return entityMapper.selectByExampleNotNull(example);
    }

    @Override
    public YcslYmxxDTO getSqrxxGroupByJt(String xmid){
        if(StringUtils.isBlank(xmid)){
            throw new NullPointerException("xmid??????");
        }
        YcslYmxxDTO ycslYmxxDTO =new YcslYmxxDTO();
        //???????????????
        ycslYmxxDTO.setBdcQlrGroupDTOLists(getSqrxxGroupByJtWithSqrlb(xmid,CommonConstantUtils.QLRLB_QLR,true));
        //???????????????
        ycslYmxxDTO.setBdcYwrGroupDTOLists(getSqrxxGroupByJtWithSqrlb(xmid,CommonConstantUtils.QLRLB_YWR,true));
        return ycslYmxxDTO;

    }

    @Override
    public void delBatchSqrxx(String gzlslid, String sqrlb){
        if (StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("gzlslid");
        }
        Map<String,Object> paramMap = new HashMap<>();
        List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        //????????????,????????????????????????????????????????????????
        List<String> xmids =new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                xmids.add(bdcXmDTO.getXmid());
            }
            paramMap.put("xmids",xmids);
            paramMap.put("sqrlb",sqrlb);
            bdcSlSqrMapper.delBatchSqrxx(paramMap);
        }
    }

    @Override
    public void delBatchSqrAndJtcyByXmid(String gzlslid,String xmid,String sqrlb){
        if (StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("gzlslid");
        }
        if(StringUtils.isNotBlank(xmid)){
            deleteSqrAndJtcyByXmid(xmid,sqrlb);
        }else{
            //??????????????????
            bdcSlJtcyService.delBatchJtcyxx(gzlslid, sqrlb);
            //???????????????
            delBatchSqrxx(gzlslid, sqrlb);

        }


    }

    @Override
    public List<BdcSlSqrDO> insertBatchSqr(String gzlslid, String qllx, BdcSlSqrDO bdcSlSqrDO, String djxl) {
        List<BdcSlSqrDO> listQlr = new ArrayList<>();
        List<String> xmids = new ArrayList<>();
        if (StringUtils.isNotBlank(gzlslid) && bdcSlSqrDO != null && StringUtils.isNotBlank(bdcSlSqrDO.getSqrmc())) {

            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                for (BdcSlXmDO slXmDO : bdcSlXmDOList) {
                    //???????????????
                    if (StringUtils.isBlank(qllx) || (slXmDO.getQllx() != null && StringUtils.equals(slXmDO.getQllx().toString(), qllx) && StringUtils.isNotBlank(djxl) && StringUtils.equals(djxl, slXmDO.getDjxl()))) {
                        BdcSlSqrDO slSqrDO = new BdcSlSqrDO();
                        BeanUtils.copyProperties(bdcSlSqrDO, slSqrDO);
                        slSqrDO.setXmid(slXmDO.getXmid());
                        if (StringUtils.isBlank(bdcSlSqrDO.getSqrid()) || !StringUtils.equals(slXmDO.getXmid(), bdcSlSqrDO.getXmid())) {
                            slSqrDO.setSqrid(UUIDGenerator.generate16());
                        }
                        listQlr.add(slSqrDO);
                        xmids.add(slXmDO.getXmid());
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(listQlr)) {
                List<List> partList = ListUtils.subList(listQlr, 500);
                for (List data : partList) {
                    entityMapper.insertBatchSelective(data);
                }
                //??????????????????
                updateSqrRyzdPl(xmids);
            }
        }
        return listQlr;

    }

    @Override
    public void deleteBdcSqrsBySqrxx(String sqrmc, String zjh, String gzlslid, String sqrlb, String qllx, String djxl) {
        //??????????????????????????????id??????????????????
        if (StringUtils.isBlank(sqrmc) || StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("????????????");
        }

        List<String> xmids = new ArrayList<>();
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            for (BdcSlXmDO slXmDO : bdcSlXmDOList) {
                //???????????????
                if (StringUtils.isBlank(qllx) || (slXmDO.getQllx() != null && StringUtils.equals(slXmDO.getQllx().toString(), qllx) && StringUtils.isNotBlank(djxl) && StringUtils.equals(djxl, slXmDO.getDjxl()))) {
                    xmids.add(slXmDO.getXmid());
                }
            }
        }
        if(CollectionUtils.isNotEmpty(xmids)) {
            Map map = new HashMap<>();
            map.put("xmids", xmids);
            map.put("sqrlb", sqrlb);
            map.put("sqrmc", sqrmc);
            map.put("zjh", zjh);
            bdcSlSqrMapper.delBatchSqrxx(map);
        }
    }

    @Override
    public List<BdcSlSqrDO> saveBatchBdcSlSqrWithZh(String json, String gzlslid, String qllx, String djxl) {
        if (StringUtils.isBlank(json) || StringUtils.isBlank(gzlslid)) {
            throw new NullPointerException("??????????????????");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        //???????????????????????????
        dealZhSqr(jsonObject, gzlslid, Constants.FUN_SAVE, djxl);
        //???????????????????????????
        return saveBatchBdcSlSqr(json, gzlslid, qllx, djxl);
    }

    @Override
    public void deleteBdcSqrsBySqrxxWithZh(String sqrid, String gzlslid, String qllx, String djxl) {
        if (StringUtils.isBlank(sqrid) || StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("????????????");
        }
        BdcSlSqrDO bdcSlSqrDO = queryBdcSlSqrBySqrid(sqrid);
        if (bdcSlSqrDO != null) {
            if (StringUtils.isBlank(bdcSlSqrDO.getSqrmc())) {
                throw new AppException("?????????????????????,???????????????");
            }
            //???????????????????????????
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(bdcSlSqrDO));
            dealZhSqr(jsonObject, gzlslid, Constants.FUN_DELETE, djxl);
            deleteBdcSqrsBySqrxx(bdcSlSqrDO.getSqrmc(), bdcSlSqrDO.getZjh(), gzlslid, bdcSlSqrDO.getSqrlb(), qllx, djxl);
        }
    }

    @Override
    public void syncSqrxx(List<BdcQlrDO> bdcQlrDOList, String gzlslid, String type) {
        if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
            List<BdcSlSqrDO> bdcSlSqrDOList = acceptDozerMapper.mapList(bdcQlrDOList, BdcSlSqrDO.class);
            if(Constants.FUN_INSERT.equals(type) && CollectionUtils.isNotEmpty(bdcQlrDOList)){
                for(BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList){
                    this.insertBdcSlSqr(bdcSlSqrDO);
                }
            }
            if(Constants.FUN_UPDATE.equals(type)){
                for(BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList){
                    this.batchUpdateBdcSlSqr(bdcSlSqrDO, gzlslid);
                }
            }
            if(Constants.FUN_DELETE.equals(type)){
                for(BdcQlrDO bdcQlrDO : bdcQlrDOList){
                    batchDeleteBdcSlSqr(bdcQlrDO.getQlrid(), bdcQlrDO.getXmid(), gzlslid);
                }
            }
        }
    }

    /**
     * ??????????????????????????????????????????
     */
    private void batchUpdateBdcSlSqr(BdcSlSqrDO bdcSlSqrDO, String gzlslid){
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(bdcSlSqrDO));
        // ?????????????????? xmid ??? ?????????id
        jsonObject.remove("xmid");
        jsonObject.remove("sqrid");
        String statement = SqlUtils.getBatchUpdateStatement(jsonObject, BdcSlSqrDO.class.getName());
        if(statement.contains("SET")) {
            Map map = new HashMap();
            map.put("statement", statement);
            // ????????????
            map.put("gzlslid", gzlslid);
            map.put("sqrmc", bdcSlSqrDO.getSqrmc());
            map.put("zjh", bdcSlSqrDO.getZjh());
            map.put("sqrlb", bdcSlSqrDO.getSqrlb());
            map.put("xmid", bdcSlSqrDO.getXmid());
            map.put("record", bdcSlSqrDO);
            this.bdcSlSqrMapper.batchUpdateSlSqr(map);
        }
    }

    /**
     * ?????????????????????????????????
     * <p>?????? xmid???sqrmc???zjh???sqrlb ??????????????????</p>
     */
    private void batchDeleteBdcSlSqr(String qlrid, String xmid, String gzlslid){
        if(StringUtils.isNotBlank(gzlslid)){
            Set<String> xmids = new HashSet<>(10);
            if(StringUtils.isNotBlank(xmid)){
                xmids.add(xmid);
            }
            if(CollectionUtils.isEmpty(xmids)){
                List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
                if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                    xmids.addAll(bdcSlXmDOList.stream().map(BdcSlXmDO::getXmid).collect(Collectors.toList()));
                }
            }
            if(CollectionUtils.isNotEmpty(xmids)) {
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setQlrid(qlrid);
                List<BdcQlrDO> bdcQlrDOList =  bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
                    Map map = new HashMap<>();
                    map.put("xmids", xmids);
                    map.put("sqrlb", bdcQlrDOList.get(0).getQlrlb());
                    map.put("sqrmc", bdcQlrDOList.get(0).getQlrmc());
                    map.put("zjh", bdcQlrDOList.get(0).getZjh());
                    bdcSlSqrMapper.delBatchSqrxx(map);
                }
            }
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????
     */
    private List<BdcSlSqrDO> saveBatchBdcSlSqr(String json, String gzlslid, String qllx, String djxl) {
        if (StringUtils.isBlank(json) || StringUtils.isBlank(gzlslid)) {
            throw new NullPointerException("??????????????????");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        BdcSlSqrDO bdcSlSqrDO = JSONObject.parseObject(json, BdcSlSqrDO.class);
        if (StringUtils.isNotBlank(bdcSlSqrDO.getSqrid())) {
            BdcSlSqrDO ysqr = queryBdcSlSqrBySqrid(bdcSlSqrDO.getSqrid());
            deleteBdcSqrsBySqrxx(ysqr.getSqrmc(), ysqr.getZjh(), gzlslid, ysqr.getSqrlb(), qllx, djxl);
            //?????????????????????????????????ysqr,??????????????????????????????
            CommonUtil.cloneJsonObject(jsonObject, ysqr);
            BeanUtils.copyProperties(ysqr, bdcSlSqrDO);
        }
        return insertBatchSqr(gzlslid, qllx, bdcSlSqrDO, djxl);

    }


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ???????????????????????????????????????????????????????????????????????????:?????????????????????????????????????????????????????????????????????????????????????????????
     * @date : 2021/2/1 14:23
     */
    private void dealZhSqr(JSONObject obj, String gzlslid, String type, String djxl) {
        if (obj.get("xmid") != null && StringUtils.isNotBlank(gzlslid)) {
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
            if (bdcSlJbxxDO != null) {
                List<BdcSlXmDO> bdcSlXmDOList = new ArrayList<>();
                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
                if (CommonConstantUtils.QLRLB_QLR.equals(obj.getString("sqrlb"))) {
                    //???????????????????????????????????????????????????
                    bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx("", obj.getString("xmid"), CommonConstantUtils.SF_F_DM);

                } else if (CommonConstantUtils.QLRLB_YWR.equals(obj.getString("sqrlb"))) {
                    //???????????????????????????????????????????????????
                    bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx(obj.getString("xmid"), "", CommonConstantUtils.SF_F_DM);
                }
                //????????????????????????????????????????????????
                if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                    for(BdcSlXmLsgxDO bdcSlXmLsgxDO:bdcSlXmLsgxDOList){
                        BdcSlXmDO bdcSlXmDO =new BdcSlXmDO();
                        if(CommonConstantUtils.QLRLB_QLR.equals(obj.getString("sqrlb")) &&StringUtils.isNotBlank(bdcSlXmLsgxDO.getXmid())){
                            bdcSlXmDO =bdcSlXmService.queryBdcSlXmByXmid(bdcSlXmLsgxDO.getXmid());
                        } else if (CommonConstantUtils.QLRLB_YWR.equals(obj.getString("sqrlb")) && StringUtils.isNotBlank(bdcSlXmLsgxDO.getYxmid())) {
                            bdcSlXmDO = bdcSlXmService.queryBdcSlXmByXmid(bdcSlXmLsgxDO.getYxmid());

                        }
                        if (bdcSlXmDO != null && StringUtils.equals(bdcSlJbxxDO.getJbxxid(), bdcSlXmDO.getJbxxid())) {
                            bdcSlXmDOList.add(bdcSlXmDO);
                        }
                    }
                }


                //?????????????????????????????????
                if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                    Set<BdcSlXmDO> slXmSet = new TreeSet<>(Comparator.comparing(BdcSlXmDO::getDjxl));
                    slXmSet.addAll(bdcSlXmDOList);
                    String sqrlb = "";
                    if (CommonConstantUtils.QLRLB_QLR.equals(obj.getString("sqrlb"))) {
                        sqrlb = CommonConstantUtils.QLRLB_YWR;
                    } else if (CommonConstantUtils.QLRLB_YWR.equals(obj.getString("sqrlb"))) {
                        sqrlb = CommonConstantUtils.QLRLB_QLR;
                    }
                    String yqlrid = obj.getString("sqrid");
                    for (BdcSlXmDO bdcSlXmDO : slXmSet) {
                        BdcSlSqrDO ysqr = null;
                        if (StringUtils.isNotBlank(yqlrid)) {
                            //??????sqrid
                            ysqr = queryBdcSlSqrBySqrid(yqlrid);
                            if (ysqr != null) {
                                Example example = new Example(BdcSlSqrDO.class);
                                Example.Criteria criteria = example.createCriteria();
                                criteria.andEqualTo("zjh", ysqr.getZjh());
                                criteria.andEqualTo("sqrmc", ysqr.getSqrmc());
                                criteria.andEqualTo("sqrlb", sqrlb);
                                criteria.andEqualTo("xmid", bdcSlXmDO.getXmid());
                                List<BdcSlSqrDO> bdcSlSqrDOList = entityMapper.selectByExample(example);
                                if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                                    obj.put("sqrid", bdcSlSqrDOList.get(0).getSqrid());
                                }
                            }
                        }
                        obj.put("sqrlb",sqrlb);
                        if(Constants.FUN_DELETE.equals(type)){
                            if(ysqr != null &&StringUtils.isNotBlank(ysqr.getSqrmc())) {
                                deleteBdcSqrsBySqrxx(ysqr.getSqrmc(), ysqr.getZjh(), gzlslid, sqrlb, bdcSlXmDO.getQllx().toString(), bdcSlXmDO.getDjxl());
                            }

                        }else {
                            saveBatchBdcSlSqr(JSON.toJSONString(obj), gzlslid, bdcSlXmDO.getQllx().toString(), bdcSlXmDO.getDjxl());
                        }
                    }
                }
            }

        }

    }



    /**
     * @param xmid ??????ID
     * @param sqrlb ???????????????
     * @param sfhb ???????????????????????????
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ???????????????????????????????????????
     */
    @Override
    public List<List<BdcSlJtcyGroupDTO>> getSqrxxGroupByJtWithSqrlb(String xmid,String sqrlb,Boolean sfhb){
        List<List<BdcSlJtcyGroupDTO>> lists =new ArrayList<>();
        List<BdcSlSqrDO> bdcSlSqrDOList =listBdcSlSqrByXmid(xmid,sqrlb);
        if(CollectionUtils.isNotEmpty(bdcSlSqrDOList)){
            //??????????????????????????????
            List<String> zjhList =new ArrayList<>();
            for(BdcSlSqrDO bdcSlSqrDO:bdcSlSqrDOList){
                //?????????????????????????????????
                List<String> jtzjhList =new ArrayList<>();
                //?????????????????????????????????????????????
                if(StringUtils.isBlank(bdcSlSqrDO.getZjh()) ||!zjhList.contains(bdcSlSqrDO.getZjh())) {
                    //???????????????
                    List<BdcSlJtcyGroupDTO> bdcSlJtcyGroupDTOList = new ArrayList<>();
                    BdcSlJtcyGroupDTO bdcSlJtcyGroupDTO = new BdcSlJtcyGroupDTO();
                    acceptDozerMapper.map(bdcSlSqrDO, bdcSlJtcyGroupDTO);
                    bdcSlJtcyGroupDTOList.add(bdcSlJtcyGroupDTO);
                    if(StringUtils.isNotBlank(bdcSlSqrDO.getZjh())) {
                        zjhList.add(bdcSlSqrDO.getZjh());
                        jtzjhList.add(bdcSlSqrDO.getZjh());

                        //?????????????????????,
                        // ???????????????false???????????????????????????????????????
                        if(StringUtils.isNotBlank(bdcSlSqrDO.getFwtc()) && sfhb != null &&sfhb) {
                            //????????????????????????????????????????????????????????????????????????
                            BdcSlJtcyQO bdcSlJtcyQO = new BdcSlJtcyQO();
                            bdcSlJtcyQO.setJtcymc(bdcSlSqrDO.getSqrmc());
                            bdcSlJtcyQO.setZjh(bdcSlSqrDO.getZjh());
                            bdcSlJtcyQO.setXmid(bdcSlSqrDO.getXmid());
                            bdcSlJtcyQO.setSqrlb(bdcSlSqrDO.getSqrlb());
                            List<BdcSlJtcyDO> bdcSlJtcyDOList = bdcSlJtcyService.listBdcSlJtcy(bdcSlJtcyQO);
                            if (CollectionUtils.isNotEmpty(bdcSlJtcyDOList)) {
                                //????????????????????????????????????,??????????????????????????????????????????????????????
                                for (BdcSlJtcyDO bdcSlJtcyDO : bdcSlJtcyDOList) {
                                    BdcSlSqrDO slSqrDO = queryBdcSlSqrBySqrid(bdcSlJtcyDO.getSqrid());
                                    if (slSqrDO != null &&StringUtils.equals(bdcSlSqrDO.getFwtc(),slSqrDO.getFwtc())) {
                                        bdcSlJtcyGroupDTO = new BdcSlJtcyGroupDTO();
                                        acceptDozerMapper.map(slSqrDO, bdcSlJtcyGroupDTO);
                                        bdcSlJtcyGroupDTOList.add(bdcSlJtcyGroupDTO);
                                        if (StringUtils.isNotBlank(slSqrDO.getZjh())) {
                                            zjhList.add(slSqrDO.getZjh());
                                            jtzjhList.add(slSqrDO.getZjh());
                                        }
                                    }
                                }
                            }
                        }
                    }

                    //????????????????????????????????????ID??????
                    List<String> sqridList = bdcSlJtcyGroupDTOList.stream().map(BdcSlJtcyGroupDTO::getId).collect(Collectors.toList());


                    //????????????????????????????????????
                    List<BdcSlJtcyDO> slJtcyDOList =bdcSlJtcyService.listBdcSlJtcyBySqrid(bdcSlSqrDO.getSqrid());
                    if(CollectionUtils.isNotEmpty(slJtcyDOList)){
                        for (BdcSlJtcyDO slJtcyDO : slJtcyDOList) {
                            if(StringUtils.isBlank(slJtcyDO.getZjh()) ||!jtzjhList.contains(slJtcyDO.getZjh())) {
                                bdcSlJtcyGroupDTO = new BdcSlJtcyGroupDTO();
                                acceptDozerMapper.map(slJtcyDO, bdcSlJtcyGroupDTO);
                                bdcSlJtcyGroupDTOList.add(bdcSlJtcyGroupDTO);
                            }
                        }
                    }

                    //???????????????ID??????
                    if(CollectionUtils.isNotEmpty(sqridList) &&CollectionUtils.isNotEmpty(bdcSlJtcyGroupDTOList)) {
                        for(BdcSlJtcyGroupDTO slJtcyGroupDTO:bdcSlJtcyGroupDTOList) {
                            slJtcyGroupDTO.setSqridList(sqridList);
                        }
                    }

                    lists.add(bdcSlJtcyGroupDTOList);

                }
            }
        }
        return lists;

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  ???????????????????????????
     */
    private void updateSqrRyzdPl(List<String> xmids){
        if(CollectionUtils.isNotEmpty(xmids) &&StringUtils.isNotBlank(xmids.get(0))) {
            BdcSlXmDO bdcSlXmDO = bdcSlXmService.queryBdcSlXmByXmid(xmids.get(0));
            if(bdcSlXmDO != null) {
                List<BdcSlSqrDO> ywrList = listBdcSlSqrByXmid(xmids.get(0), CommonConstantUtils.QLRLB_YWR);
                String ywr = StringToolUtils.resolveBeanToAppendStr(ywrList, "getSqrmc", ",");
                if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcSlXmDO.getQllx())) {
                    //?????????????????????
                    Map map = new HashMap();
                    map.put("xmidList", xmids);
                    map.put("zwr", ywr);
                    bdcSlDyaqService.updateBdcSlDyaqRyzdPl(map);

                }
            }
        }

    }

    @Override
    public void batchDelete(BdcSlDeleteCsDTO bdcSlDeleteCsDTO) {
        if (!CheckParameter.checkAnyParameter(bdcSlDeleteCsDTO)) {
            throw new AppException("??????????????????" + JSONObject.toJSONString(bdcSlDeleteCsDTO));
        }
        bdcSlSqrMapper.batchDeleteBdcSlSqr(bdcSlDeleteCsDTO);
    }

    /**
     * @param bdcSlSqrDOList
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????????????????
     * @date : 2022/9/20 9:23
     */
    @Override
    public List<BdcSlSqrDO> changeSlSqrGyfs(List<BdcSlSqrDO> bdcSlSqrDOList, String xmid) {
        //??????????????????????????????????????????????????????????????????
        Boolean isChange = false;
        //?????????????????????????????????????????????????????????
        if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
            //2 ??????????????????
            if (bdcSlSqrDOList.size() == 1) {
                // 2019-11-02 lyq 24806 ???????????????????????????????????????????????????????????????????????????????????????
                if (checkGyfsDefaultGtgy(xmid)) {
                    //????????????????????????,????????????????????????,???????????????????????????????????????????????????
                    if (bdcSlSqrDOList.get(0).getGyfs() == null) {
                        bdcSlSqrDOList.get(0).setGyfs(CommonConstantUtils.GYFS_GTGY);
                        bdcSlSqrDOList = updateBdcSlSqrGyfs(bdcSlSqrDOList);
                    }
                } else if (!CommonConstantUtils.GYFS_DDSY.equals(bdcSlSqrDOList.get(0).getGyfs()) && gyfsdgyz) {
                    // ???????????????????????????????????????????????????true?????????????????????????????????????????????????????????
                    //?????????????????????????????????????????????????????????
                    bdcSlSqrDOList.get(0).setGyfs(CommonConstantUtils.GYFS_DDSY);
                    bdcSlSqrDOList = updateBdcSlSqrGyfs(bdcSlSqrDOList);
                }
            } else if (bdcSlSqrDOList.size() == 2) {
                //??????????????????????????????????????????????????????????????????????????????????????????
                if (bdcSlSqrDOList.get(0).getGyfs() != null && bdcSlSqrDOList.get(1).getGyfs() != null) {
                    if (CommonConstantUtils.GYFS_DDSY.equals(bdcSlSqrDOList.get(0).getGyfs()) && !CommonConstantUtils.GYFS_DDSY.equals(bdcSlSqrDOList.get(1).getGyfs())) {
                        bdcSlSqrDOList.get(0).setGyfs(bdcSlSqrDOList.get(1).getGyfs());
                        isChange = true;
                    } else if (!CommonConstantUtils.GYFS_DDSY.equals(bdcSlSqrDOList.get(0).getGyfs()) && CommonConstantUtils.GYFS_DDSY.equals(bdcSlSqrDOList.get(1).getGyfs())) {
                        bdcSlSqrDOList.get(1).setGyfs(bdcSlSqrDOList.get(0).getGyfs());
                        isChange = true;
                    }
                } else {
                    //???????????????????????????????????????
                    if (bdcSlSqrDOList.get(0).getGyfs() == null || CommonConstantUtils.GYFS_DDSY.equals(bdcSlSqrDOList.get(0).getGyfs())) {
                        bdcSlSqrDOList.get(0).setGyfs(CommonConstantUtils.GYFS_GTGY);
                        isChange = true;
                    }
                    if (bdcSlSqrDOList.get(1).getGyfs() == null || CommonConstantUtils.GYFS_DDSY.equals(bdcSlSqrDOList.get(1).getGyfs())) {
                        bdcSlSqrDOList.get(1).setGyfs(CommonConstantUtils.GYFS_GTGY);
                        isChange = true;
                    }
                }
                if (bdcSlSqrDOList.get(0).getQlbl() != null || bdcSlSqrDOList.get(1).getQlbl() != null &&
                        (!CommonConstantUtils.GYFS_AFGY.equals(bdcSlSqrDOList.get(0).getGyfs()) || !CommonConstantUtils.GYFS_AFGY.equals(bdcSlSqrDOList.get(1).getGyfs()))) {
                    bdcSlSqrDOList.get(0).setGyfs(CommonConstantUtils.GYFS_AFGY);
                    bdcSlSqrDOList.get(1).setGyfs(CommonConstantUtils.GYFS_AFGY);
                    isChange = true;
                }
                if (isChange) {
                    bdcSlSqrDOList = updateBdcSlSqrGyfs(bdcSlSqrDOList);
                }
            } else if (bdcSlSqrDOList.size() > 2) {
                //??????????????????????????????????????????????????????,????????????????????????????????????
                List<BdcSlSqrDO> newQlrList = new ArrayList<>();
                for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
                    if (bdcSlSqrDO.getGyfs() != null) {
                        if (CommonConstantUtils.GYFS_DDSY.equals(bdcSlSqrDO.getGyfs())) {
                            bdcSlSqrDO.setGyfs(CommonConstantUtils.GYFS_GTGY);
                            isChange = true;
                        }
                    } else {
                        bdcSlSqrDO.setGyfs(CommonConstantUtils.GYFS_GTGY);
                        isChange = true;

                    }
                    //??????????????????????????????????????????
                    if (bdcSlSqrDO.getQlbl() != null && !CommonConstantUtils.GYFS_AFGY.equals(bdcSlSqrDO.getGyfs())) {
                        bdcSlSqrDO.setGyfs(CommonConstantUtils.GYFS_AFGY);
                        isChange = true;
                    }
                    newQlrList.add(bdcSlSqrDO);
                }
                if (isChange) {
                    bdcSlSqrDOList = updateBdcSlSqrGyfs(newQlrList);
                }
            }
        }
        return bdcSlSqrDOList;
    }

    @Override
    public List<BdcSlSqrDO> listSlSqrPl(List<String> xmidList){
        List<BdcSlSqrDO> bdcSlSqrDOList=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(xmidList)){
            List<List> lists = ListUtils.subList(xmidList, 500);
            for (List partXmids : lists) {
                Example example =new Example(BdcSlSqrDO.class);
                example.createCriteria().andInWithListString("xmid",partXmids);
                List<BdcSlSqrDO> slSqrDOS=entityMapper.selectByExampleNotNull(example);
                if(CollectionUtils.isNotEmpty(slSqrDOS)){
                    bdcSlSqrDOList.addAll(slSqrDOS);
                }
            }
        }
        return bdcSlSqrDOList;
    }


    /**
     * @param xmid
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ????????????????????????????????????????????????
     */
    private boolean checkGyfsDefaultGtgy(String xmid) {
        if (StringUtils.isNotBlank(gtgyDjxl) && StringUtils.isNotBlank(xmid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmList)) {
                BdcXmDO bdcXmDO = bdcXmList.get(0);
                if (StringUtils.contains(gtgyDjxl, bdcXmDO.getDjxl())) {
                    return true;
                }
            }
        }
        return false;
    }


    private List<BdcSlSqrDO> updateBdcSlSqrGyfs(List<BdcSlSqrDO> bdcSlSqrDOList) {
        for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
            this.updateBdcSlSqr(bdcSlSqrDO);
        }
        return bdcSlSqrDOList;
    }


}
