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
 * @description 不动产受理申请人数据服务
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
     * 验证单个权利人默认为单个共有。 配置为false时，允许单个权利人存在共同共有的情况
     */
    @Value("${default.gyfsdgyz:true}")
    private boolean gyfsdgyz;

    /**
     * @param sqrid 申请人人ID
     * @return 不动产受理申请人
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据申请人人ID获取不动产受理申请人
     */
    @Override
    public BdcSlSqrDO queryBdcSlSqrBySqrid(String sqrid) {
        if (StringUtils.isBlank(sqrid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return entityMapper.selectByPrimaryKey(BdcSlSqrDO.class, sqrid);
    }

    /**
     * @param xmid 项目ID
     * @return 不动产受理申请人
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID获取不动产受理申请人
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
     * @param bdcSlSqrDO 不动产受理申请人
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理申请人
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
     * @param bdcSlSqrDO 不动产受理申请人
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理申请人
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
     * @param sqrid 申请人人ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据申请人人ID删除不动产受理申请人
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
     * @param xmid 项目ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID删除不动产受理申请人
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
                //根据申请人找到家庭成员删除
                bdcSlJtcyService.deleteBdcSlJtcyBySqrid(bdcSlSqrDO.getSqrid());
            }
            //删除申请人
            deleteBdcSlSqrByXmid(xmid,sqrlb);
        }

    }

    /**
     * 根据不动产受理申请人QO查询受理申请人信息
     *
     * @param bdcSlSqrQO
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [bdcSlSqrQO] 受理申请人QO
     * @return: List<BdcSlSqrDO> 受理申请人DO
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
            throw new NullPointerException("xmid为空");
        }
        YcslYmxxDTO ycslYmxxDTO =new YcslYmxxDTO();
        //权利人信息
        ycslYmxxDTO.setBdcQlrGroupDTOLists(getSqrxxGroupByJtWithSqrlb(xmid,CommonConstantUtils.QLRLB_QLR,true));
        //义务人信息
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
        //批量更新,根据其中一个判断是否更新还是新增
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
            //删除家庭成员
            bdcSlJtcyService.delBatchJtcyxx(gzlslid, sqrlb);
            //删除申请人
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
                    //非空时过滤
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
                //更新冗余字段
                updateSqrRyzdPl(xmids);
            }
        }
        return listQlr;

    }

    @Override
    public void deleteBdcSqrsBySqrxx(String sqrmc, String zjh, String gzlslid, String sqrlb, String qllx, String djxl) {
        //如果人名、工作流实例id没值则抛异常
        if (StringUtils.isBlank(sqrmc) || StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失参数");
        }

        List<String> xmids = new ArrayList<>();
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            for (BdcSlXmDO slXmDO : bdcSlXmDOList) {
                //非空时过滤
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
            throw new NullPointerException("空参数异常！");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        //处理组合申请人同步
        dealZhSqr(jsonObject, gzlslid, Constants.FUN_SAVE, djxl);
        //批量保存当前申请人
        return saveBatchBdcSlSqr(json, gzlslid, qllx, djxl);
    }

    @Override
    public void deleteBdcSqrsBySqrxxWithZh(String sqrid, String gzlslid, String qllx, String djxl) {
        if (StringUtils.isBlank(sqrid) || StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失参数");
        }
        BdcSlSqrDO bdcSlSqrDO = queryBdcSlSqrBySqrid(sqrid);
        if (bdcSlSqrDO != null) {
            if (StringUtils.isBlank(bdcSlSqrDO.getSqrmc())) {
                throw new AppException("申请人名称为空,请检查数据");
            }
            //处理组合申请人同步
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
     * 批量修改不动产受理申请人信息
     */
    private void batchUpdateBdcSlSqr(BdcSlSqrDO bdcSlSqrDO, String gzlslid){
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(bdcSlSqrDO));
        // 批量更新移除 xmid 与 申请人id
        jsonObject.remove("xmid");
        jsonObject.remove("sqrid");
        String statement = SqlUtils.getBatchUpdateStatement(jsonObject, BdcSlSqrDO.class.getName());
        if(statement.contains("SET")) {
            Map map = new HashMap();
            map.put("statement", statement);
            // 更新条件
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
     * 批量删除受理申请人信息
     * <p>根据 xmid、sqrmc、zjh、sqrlb 进行批量删除</p>
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
     * @description 批量保存申请人
     */
    private List<BdcSlSqrDO> saveBatchBdcSlSqr(String json, String gzlslid, String qllx, String djxl) {
        if (StringUtils.isBlank(json) || StringUtils.isBlank(gzlslid)) {
            throw new NullPointerException("空参数异常！");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        BdcSlSqrDO bdcSlSqrDO = JSONObject.parseObject(json, BdcSlSqrDO.class);
        if (StringUtils.isNotBlank(bdcSlSqrDO.getSqrid())) {
            BdcSlSqrDO ysqr = queryBdcSlSqrBySqrid(bdcSlSqrDO.getSqrid());
            deleteBdcSqrsBySqrxx(ysqr.getSqrmc(), ysqr.getZjh(), gzlslid, ysqr.getSqrlb(), qllx, djxl);
            //将需要修改的内容赋值给ysqr,组成完整的申请人对象
            CommonUtil.cloneJsonObject(jsonObject, ysqr);
            BeanUtils.copyProperties(ysqr, bdcSlSqrDO);
        }
        return insertBatchSqr(gzlslid, qllx, bdcSlSqrDO, djxl);

    }


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 处理组合流程权利人数据，不处理当前项目的数据，例如:产权抵押组合，修改产权的权利人，当前方法只处理抵押的义务人数据
     * @date : 2021/2/1 14:23
     */
    private void dealZhSqr(JSONObject obj, String gzlslid, String type, String djxl) {
        if (obj.get("xmid") != null && StringUtils.isNotBlank(gzlslid)) {
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
            if (bdcSlJbxxDO != null) {
                List<BdcSlXmDO> bdcSlXmDOList = new ArrayList<>();
                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
                if (CommonConstantUtils.QLRLB_QLR.equals(obj.getString("sqrlb"))) {
                    //权利人，查找同流程的下一手历史关系
                    bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx("", obj.getString("xmid"), CommonConstantUtils.SF_F_DM);

                } else if (CommonConstantUtils.QLRLB_YWR.equals(obj.getString("sqrlb"))) {
                    //义务人，查找同流程的上一手历史关系
                    bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx(obj.getString("xmid"), "", CommonConstantUtils.SF_F_DM);
                }
                //查找当前流程需要同步申请人的项目
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


                //组合流程处理权利人数据
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
                            //获取sqrid
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
     * @param xmid 项目ID
     * @param sqrlb 申请人类别
     * @param sfhb 是否合并多个申请人
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据家庭分组获取申请人信息
     */
    @Override
    public List<List<BdcSlJtcyGroupDTO>> getSqrxxGroupByJtWithSqrlb(String xmid,String sqrlb,Boolean sfhb){
        List<List<BdcSlJtcyGroupDTO>> lists =new ArrayList<>();
        List<BdcSlSqrDO> bdcSlSqrDOList =listBdcSlSqrByXmid(xmid,sqrlb);
        if(CollectionUtils.isNotEmpty(bdcSlSqrDOList)){
            //存储申请人证件号集合
            List<String> zjhList =new ArrayList<>();
            for(BdcSlSqrDO bdcSlSqrDO:bdcSlSqrDOList){
                //每个家庭分组证件号集合
                List<String> jtzjhList =new ArrayList<>();
                //判断当前申请人是否在家庭分组中
                if(StringUtils.isBlank(bdcSlSqrDO.getZjh()) ||!zjhList.contains(bdcSlSqrDO.getZjh())) {
                    //存储申请人
                    List<BdcSlJtcyGroupDTO> bdcSlJtcyGroupDTOList = new ArrayList<>();
                    BdcSlJtcyGroupDTO bdcSlJtcyGroupDTO = new BdcSlJtcyGroupDTO();
                    acceptDozerMapper.map(bdcSlSqrDO, bdcSlJtcyGroupDTO);
                    bdcSlJtcyGroupDTOList.add(bdcSlJtcyGroupDTO);
                    if(StringUtils.isNotBlank(bdcSlSqrDO.getZjh())) {
                        zjhList.add(bdcSlSqrDO.getZjh());
                        jtzjhList.add(bdcSlSqrDO.getZjh());

                        //房屋套次不为空,
                        // 是否合并为false时，不需要进行家庭成员合并
                        if(StringUtils.isNotBlank(bdcSlSqrDO.getFwtc()) && sfhb != null &&sfhb) {
                            //查找当前申请人是否为当前项目其他申请人的家庭成员
                            BdcSlJtcyQO bdcSlJtcyQO = new BdcSlJtcyQO();
                            bdcSlJtcyQO.setJtcymc(bdcSlSqrDO.getSqrmc());
                            bdcSlJtcyQO.setZjh(bdcSlSqrDO.getZjh());
                            bdcSlJtcyQO.setXmid(bdcSlSqrDO.getXmid());
                            bdcSlJtcyQO.setSqrlb(bdcSlSqrDO.getSqrlb());
                            List<BdcSlJtcyDO> bdcSlJtcyDOList = bdcSlJtcyService.listBdcSlJtcy(bdcSlJtcyQO);
                            if (CollectionUtils.isNotEmpty(bdcSlJtcyDOList)) {
                                //存在数据并且房屋套次相同,则家庭成员对应的申请人分组为同一家庭
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

                    //获取当前家庭所有的申请人ID集合
                    List<String> sqridList = bdcSlJtcyGroupDTOList.stream().map(BdcSlJtcyGroupDTO::getId).collect(Collectors.toList());


                    //存储其他非申请人家庭成员
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

                    //赋值申请人ID集合
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
     * @description  更新申请人冗余字段
     */
    private void updateSqrRyzdPl(List<String> xmids){
        if(CollectionUtils.isNotEmpty(xmids) &&StringUtils.isNotBlank(xmids.get(0))) {
            BdcSlXmDO bdcSlXmDO = bdcSlXmService.queryBdcSlXmByXmid(xmids.get(0));
            if(bdcSlXmDO != null) {
                List<BdcSlSqrDO> ywrList = listBdcSlSqrByXmid(xmids.get(0), CommonConstantUtils.QLRLB_YWR);
                String ywr = StringToolUtils.resolveBeanToAppendStr(ywrList, "getSqrmc", ",");
                if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcSlXmDO.getQllx())) {
                    //抵押更新债务人
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
            throw new AppException("删除参数为空" + JSONObject.toJSONString(bdcSlDeleteCsDTO));
        }
        bdcSlSqrMapper.batchDeleteBdcSlSqr(bdcSlDeleteCsDTO);
    }

    /**
     * @param bdcSlSqrDOList
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 改变受理申请人共有方式
     * @date : 2022/9/20 9:23
     */
    @Override
    public List<BdcSlSqrDO> changeSlSqrGyfs(List<BdcSlSqrDO> bdcSlSqrDOList, String xmid) {
        //判断程序是否修改共有方式，确认修改才进行更新
        Boolean isChange = false;
        //组合流程不能用批量更新的方法，分开更新
        if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
            //2 一个权利人时
            if (bdcSlSqrDOList.size() == 1) {
                // 2019-11-02 lyq 24806 如果只有一个权利人，并且为默认共有方式为共同共有的登记小类
                if (checkGyfsDefaultGtgy(xmid)) {
                    //配置默认共同共有,如果共有方式为空,默认共同共有（允许为其他共有方式）
                    if (bdcSlSqrDOList.get(0).getGyfs() == null) {
                        bdcSlSqrDOList.get(0).setGyfs(CommonConstantUtils.GYFS_GTGY);
                        bdcSlSqrDOList = updateBdcSlSqrGyfs(bdcSlSqrDOList);
                    }
                } else if (!CommonConstantUtils.GYFS_DDSY.equals(bdcSlSqrDOList.get(0).getGyfs()) && gyfsdgyz) {
                    // 单个权利人默认为单个共有验证配置，true开启验证，验证单个权利人必须为单个共有
                    //判断原有共有方式是否正确，正确则不更新
                    bdcSlSqrDOList.get(0).setGyfs(CommonConstantUtils.GYFS_DDSY);
                    bdcSlSqrDOList = updateBdcSlSqrGyfs(bdcSlSqrDOList);
                }
            } else if (bdcSlSqrDOList.size() == 2) {
                //两个权利人时共有方式不能存在单独所有，且两个共有方式保持一致
                if (bdcSlSqrDOList.get(0).getGyfs() != null && bdcSlSqrDOList.get(1).getGyfs() != null) {
                    if (CommonConstantUtils.GYFS_DDSY.equals(bdcSlSqrDOList.get(0).getGyfs()) && !CommonConstantUtils.GYFS_DDSY.equals(bdcSlSqrDOList.get(1).getGyfs())) {
                        bdcSlSqrDOList.get(0).setGyfs(bdcSlSqrDOList.get(1).getGyfs());
                        isChange = true;
                    } else if (!CommonConstantUtils.GYFS_DDSY.equals(bdcSlSqrDOList.get(0).getGyfs()) && CommonConstantUtils.GYFS_DDSY.equals(bdcSlSqrDOList.get(1).getGyfs())) {
                        bdcSlSqrDOList.get(1).setGyfs(bdcSlSqrDOList.get(0).getGyfs());
                        isChange = true;
                    }
                } else {
                    //共有方式为空或者为单独所有
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
                //多个权利人时共有方式不能存在单独所有,若存在默认赋值为共同共有
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
                    //共有比例不为空，默认按份共有
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
     * @description 验证是否为共有方式默认为共同共有
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
