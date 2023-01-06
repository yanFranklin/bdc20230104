package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.accept.BdcDsfSfssDdxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfssDdxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfssDdxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/5/28
 * @description 不动产收费收税订单信息服务
 */
@Service
public class BdcSlSfssDdxxServiceImpl implements BdcSlSfssDdxxService {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BdcSlSfssDdxxServiceImpl.class);

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcSlSfxxService bdcSlSfxxService;
    @Autowired
    BdcSlHsxxService bdcSlHsxxService;
    @Autowired
    BdcYjdhSfxxGxService bdcYjdhSfxxGxService;
    @Autowired
    BdcYjSfDdxxService bdcYjSfDdxxService;
    @Autowired
    BdcSlWxjjxxService bdcSlWxjjxxService;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 订单信息有效时间
     */
    @Value("${ykqddxx.yxsj:600}")
    private Integer ddyxsj;

    @Override
    public List<BdcSlSfssDdxxDO> listBdcSlSfssDdxx(BdcSlSfssDdxxQO bdcSlSfssDdxxQO){
        if (!CheckParameter.checkAnyParameter(bdcSlSfssDdxxQO,"gzlslid","sfglid","ddbh","qlrlb","dsfddbh","yjdh")) {
            LOGGER.error("查询缺失参数异常,bdcSlSfssDdxxQO:{}", bdcSlSfssDdxxQO);
            throw new AppException("缺失参数异常");
        }
        BdcSlSfssDdxxDO bdcSlSfssDdxxDO = new BdcSlSfssDdxxDO();
        BeanUtils.copyProperties(bdcSlSfssDdxxQO, bdcSlSfssDdxxDO);
        return entityMapper.selectByObj(bdcSlSfssDdxxDO);
    }

    @Override
    public List<BdcSlSfssDdxxDO> queryBdcSlSfssDdxxByYjdh(String yjdh) {
        if(StringUtils.isBlank(yjdh)){
            throw new AppException(ErrorCode.CUSTOM, "缺失参数月结单号");
        }
        Example example = new Example(BdcSlSfssDdxxDO.class);
        example.createCriteria().andEqualTo("yjdh", yjdh);
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = entityMapper.selectByExample(example);
        return bdcSlSfssDdxxDOList;
    }

    @Override
    public void deleteBdcSlSfssDdxx(BdcSlSfssDdxxQO bdcSlSfssDdxxQO){
        if (!CheckParameter.checkAnyParameter(bdcSlSfssDdxxQO,"gzlslid","sfglid","dsfddbh")) {
            LOGGER.error("删除缺失参数异常,bdcSlSfssDdxxQO:{}", bdcSlSfssDdxxQO);
            throw new AppException("删除缺失参数异常");
        }
        BdcSlSfssDdxxDO bdcSlSfssDdxxDO = new BdcSlSfssDdxxDO();
        BeanUtils.copyProperties(bdcSlSfssDdxxQO, bdcSlSfssDdxxDO);
        Example example = entityMapper.objToExample(bdcSlSfssDdxxDO);
        if (example == null) {
            throw new AppException("删除缺失参数异常");
        }
        entityMapper.deleteByExampleNotNull(example);
    }

    @Override
    public int insertBatchBdcSlSfssDdxx(List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList){
        int result = 0;
        if(CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)){
            result =entityMapper.insertBatchSelective(bdcSlSfssDdxxDOList);
        }
        return result;

    }

    /**
     * @param bdcSlSfssDdxxDO 不动产收费收税订单信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新订单信息
     * @date : 2020/5/28 17:42
     */
    @Override
    public int updateBdcSlSfssDdxx(BdcSlSfssDdxxDO bdcSlSfssDdxxDO) {
        int result = 0;
        if (bdcSlSfssDdxxDO != null && StringUtils.isNotBlank(bdcSlSfssDdxxDO.getId())) {
            result = entityMapper.updateByPrimaryKeySelective(bdcSlSfssDdxxDO);
        }
        return result;
    }

    @Override
    public List<BdcSlSfssDdxxDO> saveAndCreateDdxx(String jkfs, List<BdcSlSfssDdxxDTO> bdcSlSfssDdxxDTOList) {
        String slbh = this.bdcXmFeignService.querySlbh(bdcSlSfssDdxxDTOList.get(0).getGzlslid());
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("缺失参数受理编号。");
        }

        //判断总金额是否为空,为空取流程收费收税总金额
        Double sfssze = null;
        if (bdcSlSfssDdxxDTOList.get(0).getZe() == null) {
            sfssze = this.computeSfze(bdcSlSfssDdxxDTOList.get(0).getGzlslid(), null);
        }

        List<BdcSlSfssDdxxDO> qlrDdxxDOList = new ArrayList<>();
        List<BdcSlSfssDdxxDO> ywrDdxxDOList = new ArrayList<>();
        List<BdcSlSfssDdxxDO> hbDdxxList = new ArrayList<>();
        List<BdcSlSfssDdxxDO> list = new ArrayList<>();
        for (BdcSlSfssDdxxDTO bdcSlSfssDdxxDTO : bdcSlSfssDdxxDTOList) {
            Double ze = bdcSlSfssDdxxDTO.getZe();
            if (Objects.isNull(ze) && Objects.nonNull(sfssze)) {
                ze = sfssze;
            }
            if (Objects.nonNull(ze) && !ze.equals((double) 0)) {
                String gzlslid = bdcSlSfssDdxxDTO.getGzlslid();
                if (CollectionUtils.isEmpty(bdcSlSfssDdxxDTO.getCfddxxList())) {
                    BdcSlSfssDdxxDO cfddxx = new BdcSlSfssDdxxDO();
                    cfddxx.setDdje(ze);
                    List<BdcSlSfssDdxxDO> cfddxxList = new ArrayList<>();
                    cfddxxList.add(cfddxx);
                    bdcSlSfssDdxxDTO.setCfddxxList(cfddxxList);
                }
                for (BdcSlSfssDdxxDO cfddxx : bdcSlSfssDdxxDTO.getCfddxxList()) {
                    if (cfddxx.getDdje() != null) {
                        BdcSlSfssDdxxDO createDdxx = new BdcSlSfssDdxxDO();
                        createDdxx.setId(UUIDGenerator.generate16());
                        createDdxx.setDdje(cfddxx.getDdje());
                        createDdxx.setDsfddbh(cfddxx.getDsfddbh());
                        createDdxx.setZe(ze);
                        createDdxx.setGzlslid(gzlslid);
                        if (StringUtils.isNotBlank(bdcSlSfssDdxxDTO.getQlrlb())) {
                            createDdxx.setSfglidlx(CommonConstantUtils.SFGLID_LX_SFSSXXZJ);
                            createDdxx.setSfglid(bdcSlSfssDdxxDTO.getXmid());
                            createDdxx.setQlrlb(bdcSlSfssDdxxDTO.getQlrlb());
                        } else {
                            createDdxx.setSfglidlx(CommonConstantUtils.SFGLID_LX_QT);
                            createDdxx.setSfglid(gzlslid);
                        }

                        // 合一支付时，税费关联ID使用（受理编号+权利人类别），生成订单时nwslbh也采用（受理编号+权利人类别），后续互联网使用（受理编号+权利人类别）获取缴费明细和缴费状态
                        if( StringUtils.equals(jkfs, String.valueOf(CommonConstantUtils.JKTJ_HYZF))){
                            createDdxx.setSfglidlx(CommonConstantUtils.SFGLID_LX_SFSSXXZJ);
                            createDdxx.setSfglid(slbh + bdcSlSfssDdxxDTO.getQlrlb());
                        }

                        if (CommonConstantUtils.QLRLB_QLR.equals(bdcSlSfssDdxxDTO.getQlrlb())) {
                            qlrDdxxDOList.add(createDdxx);
                        } else if (CommonConstantUtils.QLRLB_YWR.equals(bdcSlSfssDdxxDTO.getQlrlb())) {
                            ywrDdxxDOList.add(createDdxx);
                        } else {
                            hbDdxxList.add(createDdxx);
                        }
                    }
                }
            }
        }

        String xmid = bdcSlSfssDdxxDTOList.get(0).getXmid();
        // 创建权利人订单成功后，保存订单数据。 后面可以优化为开启线程进行订单创建与保存
        if (CollectionUtils.isNotEmpty(qlrDdxxDOList)) {
            if(StringUtils.equals(jkfs, String.valueOf(CommonConstantUtils.JKTJ_HYZF))){
                qlrDdxxDOList = createHyzfSplitOrder(qlrDdxxDOList, slbh, CommonConstantUtils.QLRLB_QLR, xmid, CommonConstantUtils.SF_F_DM.toString());
            }else{
                qlrDdxxDOList = createOrder(qlrDdxxDOList, slbh, xmid, CommonConstantUtils.SF_F_DM.toString());
            }
            if (CollectionUtils.isEmpty(qlrDdxxDOList)) {
                throw new AppException(ErrorCode.CUSTOM, "权利人未生成订单信息");
            }
        }

        // 创建义务人订单成功后，保存订单数据
        if (CollectionUtils.isNotEmpty(ywrDdxxDOList)) {
            if(StringUtils.equals(jkfs, String.valueOf(CommonConstantUtils.JKTJ_HYZF))){
                ywrDdxxDOList = createHyzfSplitOrder(ywrDdxxDOList, slbh, CommonConstantUtils.QLRLB_YWR, xmid, CommonConstantUtils.SF_F_DM.toString());
            }else{
                ywrDdxxDOList = createOrder(ywrDdxxDOList, slbh, xmid, CommonConstantUtils.SF_F_DM.toString());
            }
            if (CollectionUtils.isEmpty(ywrDdxxDOList)) {
                throw new AppException(ErrorCode.CUSTOM, "义务人未生成订单信息");
            }
        }

        // 创建权利人义务人合并订单成功后，保存订单数据
        if (CollectionUtils.isNotEmpty(hbDdxxList)) {
            hbDdxxList = createOrder(hbDdxxList, slbh, xmid, CommonConstantUtils.SF_F_DM.toString());
            if (CollectionUtils.isEmpty(hbDdxxList)) {
                throw new AppException(ErrorCode.CUSTOM, "未生成订单信息");
            }
        }

        if (CollectionUtils.isNotEmpty(qlrDdxxDOList) && StringUtils.equals(jkfs, String.valueOf(CommonConstantUtils.JKTJ_HYZF))){
            BdcSlSfssDdxxDO bdcSlSfssDdxxDO = new BdcSlSfssDdxxDO();
            BeanUtils.copyProperties(qlrDdxxDOList.get(0), bdcSlSfssDdxxDO);
            list.add(bdcSlSfssDdxxDO);
            for(BdcSlSfssDdxxDO ddxx : qlrDdxxDOList){
                ddxx.setJfurl("");
            }
        }

        if (CollectionUtils.isNotEmpty(ywrDdxxDOList) && StringUtils.equals(jkfs, String.valueOf(CommonConstantUtils.JKTJ_HYZF))){
            BdcSlSfssDdxxDO bdcSlSfssDdxxDO = new BdcSlSfssDdxxDO();
            BeanUtils.copyProperties(ywrDdxxDOList.get(0), bdcSlSfssDdxxDO);
            list.add(bdcSlSfssDdxxDO);
            for(BdcSlSfssDdxxDO ddxx : ywrDdxxDOList){
                ddxx.setJfurl("");
            }
        }

        if (CollectionUtils.isNotEmpty(hbDdxxList) && StringUtils.equals(jkfs, String.valueOf(CommonConstantUtils.JKTJ_HYZF))){
            BdcSlSfssDdxxDO bdcSlSfssDdxxDO = new BdcSlSfssDdxxDO();
            BeanUtils.copyProperties(hbDdxxList.get(0), bdcSlSfssDdxxDO);
            list.add(bdcSlSfssDdxxDO);
            for(BdcSlSfssDdxxDO ddxx : hbDdxxList){
                ddxx.setJfurl("");
            }
        }
        //新增新生成的订单数据
        if (CollectionUtils.isNotEmpty(qlrDdxxDOList)) {
            this.entityMapper.batchSaveSelective(qlrDdxxDOList);
        }
        if (CollectionUtils.isNotEmpty(ywrDdxxDOList)) {
            this.entityMapper.batchSaveSelective(ywrDdxxDOList);
        }
        if (CollectionUtils.isNotEmpty(hbDdxxList)) {
            this.entityMapper.batchSaveSelective(hbDdxxList);
        }
        return list;
    }

    // 调用创建订单接口创建订单
    private List<BdcSlSfssDdxxDO> createOrder(List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList, String slbh, String xmid, String sfyj) {
        Map<String, Object> param = new HashMap<>(6);
        param.put("slbh", slbh);
        param.put("xmid", xmid);
        param.put("ze", bdcSlSfssDdxxDOList.get(0).getZe());
        param.put("jffs", CommonConstantUtils.JFFS_POS);
        param.put("qd", CommonConstantUtils.JKQD_XX);
        param.put("sfyj", sfyj);
        param.put("jfType", "");
        param.put("zflx", "");
        //根据收费信息的hyzfjftj判断缴费类型是银行还是得宽接口
        Integer hyzfjftj = null, hyzfjflx = null;
        if (StringUtils.isNotBlank(slbh)) {
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxBySlbh(slbh);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(bdcXmDTOList.get(0).getGzlslid());
                if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                    hyzfjftj = bdcSlSfxxDOList.get(0).getHyzfjftj();
                    hyzfjflx = bdcSlSfxxDOList.get(0).getHyzfjflx();
                }
            }
        }
        // 设置缴费途径
        if (Objects.equals(CommonConstantUtils.JKTJ_SYYH, hyzfjftj)) {
            param.put("jfType", "");
        } else if (Objects.equals(CommonConstantUtils.JKTJ_HYZF, hyzfjftj)) {
            param.put("jfType", "DK");
        }
        // 设置缴费类型, 合一支付缴费类型 1.登记费2.税费统缴； 一卡清支付类型：0：税费统缴,1：缴纳税,2：缴纳费
        if(Objects.equals(1, hyzfjflx)){
            param.put("zflx", CommonConstantUtils.DDXX_DDLX_JNF);
        }else if(Objects.equals(2, hyzfjflx)){
            param.put("zflx", CommonConstantUtils.DDXX_DDLX_SFTJ);
        }
        // 月结缴费生成订单，支付类型默认只缴纳登记费
        if(StringUtils.equals(sfyj, String.valueOf(CommonConstantUtils.SF_S_DM))){
            param.put("zflx", CommonConstantUtils.DDXX_DDLX_JNF);
        }

        List<Map<String, Object>> ddxxList = new ArrayList<>(bdcSlSfssDdxxDOList.size());
        for (BdcSlSfssDdxxDO bdcSlSfssDdxxDO : bdcSlSfssDdxxDOList) {
            Map<String, Object> ddxxMap = new HashMap<>(2);
            ddxxMap.put("id", bdcSlSfssDdxxDO.getId());
            ddxxMap.put("ddje", bdcSlSfssDdxxDO.getDdje());
            ddxxList.add(ddxxMap);
        }
        param.put("ddxx", ddxxList);
        return this.scdd(param, bdcSlSfssDdxxDOList, slbh);
    }

    // 合一支付拆单创建订单
    private List<BdcSlSfssDdxxDO> createHyzfSplitOrder(List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList, String slbh,String qlrlb, String xmid, String sfyj) {
        // 合一支付生成订单时，先生成订单信息，后续进行更新
        this.entityMapper.insertBatchSelective(bdcSlSfssDdxxDOList);

        Map<String, Object> param = new HashMap<>(6);
        param.put("slbh", slbh + qlrlb);
        param.put("xmid", xmid);
        param.put("ze", bdcSlSfssDdxxDOList.get(0).getZe());
        param.put("jffs", CommonConstantUtils.JFFS_POS);
        param.put("qd", CommonConstantUtils.JKQD_XX);
        param.put("sfyj", sfyj);
        param.put("jfType", "");
        param.put("zflx", "");
        //根据收费信息的hyzfjftj判断缴费类型是银行还是得宽接口
        Integer hyzfjftj = null, hyzfjflx = null;
        if (StringUtils.isNotBlank(slbh)) {
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxBySlbh(slbh);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(bdcXmDTOList.get(0).getGzlslid());
                if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                    hyzfjftj = bdcSlSfxxDOList.get(0).getHyzfjftj();
                    hyzfjflx = bdcSlSfxxDOList.get(0).getHyzfjflx();
                }
            }
        }
        // 设置缴费途径
        if (Objects.equals(CommonConstantUtils.JKTJ_SYYH, hyzfjftj)) {
            param.put("jfType", "");
        } else if (Objects.equals(CommonConstantUtils.JKTJ_HYZF, hyzfjftj)) {
            param.put("jfType", "DK");
        }
        // 设置缴费类型, 合一支付缴费类型 1.登记费2.税费统缴； 一卡清支付类型：0：税费统缴,1：缴纳税,2：缴纳费
        if(Objects.equals(1, hyzfjflx)){
            param.put("zflx", CommonConstantUtils.DDXX_DDLX_JNF);
        }else if(Objects.equals(2, hyzfjflx)){
            param.put("zflx", CommonConstantUtils.DDXX_DDLX_SFTJ);
        }
        List<Map<String, Object>> ddxxList = new ArrayList<>(bdcSlSfssDdxxDOList.size());
        for (BdcSlSfssDdxxDO bdcSlSfssDdxxDO : bdcSlSfssDdxxDOList) {
            Map<String, Object> ddxxMap = new HashMap<>(2);
            ddxxMap.put("id", bdcSlSfssDdxxDO.getId());
            ddxxMap.put("ddje", bdcSlSfssDdxxDO.getDdje());
            ddxxList.add(ddxxMap);
        }
        param.put("ddxx", ddxxList);
        return this.scdd(param, bdcSlSfssDdxxDOList, slbh);
    }

    /**
     *  调用exchange 的 beanName: wwsqScddh 生成订单信息
     * @param param  生成订单接口请求参数
     * @param bdcSlSfssDdxxDOList  订单信息
     * @param slbh 受理编号或月结单号
     * @return 生成后的订单信息
     */
    private List<BdcSlSfssDdxxDO> scdd(Map<String, Object> param, List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList, String slbh){
        LOGGER.info("当前流程受理编号{}创建订单接口请求参数为：{}", slbh, param);
        Object response = exchangeInterfaceFeignService.requestInterface("wwsqScddh", param);
        LOGGER.info("当前流程受理编号{}创建订单接口返回参数为：{}", slbh, response);
        if (Objects.nonNull(response)) {
            List<BdcSlSfssDdxxDO> responseDdxxList = JSONObject.parseArray(JSONObject.toJSONString(response), BdcSlSfssDdxxDO.class);
            for (BdcSlSfssDdxxDO responseDdxx : responseDdxxList) {
                for (BdcSlSfssDdxxDO bdcSlSfssDdxxDO : bdcSlSfssDdxxDOList) {
                    if (bdcSlSfssDdxxDO.getId().equals(responseDdxx.getId())) {
                        if (StringUtils.isBlank(responseDdxx.getDdbh()) && StringUtils.isBlank(responseDdxx.getDsfddbh())) {
                            throw new AppException("生成订单失败,未返回订单号，第三方订单号");
                        }
                        if (StringUtils.isNotBlank(bdcSlSfssDdxxDO.getDdbh())) {
                            //如果原来已存在订单,需要删掉原有订单
                            BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
                            bdcSlSfssDdxxQO.setDsfddbh(bdcSlSfssDdxxDO.getDsfddbh());
                            List<BdcSlSfssDdxxDO> yddxxList = listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
                            if (CollectionUtils.isNotEmpty(yddxxList) && !CommonConstantUtils.SFZT_YJF.equals(yddxxList.get(0).getJfzt())) {
                                deleteBdcSlSfssDdxx(bdcSlSfssDdxxQO);
                            }
                        }
                        bdcSlSfssDdxxDO.setJfurl(responseDdxx.getJfurl());
                        bdcSlSfssDdxxDO.setDdbh(responseDdxx.getDdbh());
                        bdcSlSfssDdxxDO.setDsfddbh(responseDdxx.getDsfddbh());
                        bdcSlSfssDdxxDO.setDdscsj(new Date());
                    }
                }
            }
            return bdcSlSfssDdxxDOList;
        }
        return Collections.emptyList();
    }

    @Override
    public Object computeQlrAndYwrSfje(String xmid, String gzlslid) {
        if (StringUtils.isAnyBlank(xmid, gzlslid)) {
            throw new AppException("未获取到项目ID信息或工作流实例ID。");
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList;
        // 判断当前流程类型，组合与批量组合流程采用XMID查询收费信息
        int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        BdcSlSfxxDO sfxxQO =new BdcSlSfxxDO();
        BdcSlWxjjxxDO wxjjxxDO = new BdcSlWxjjxxDO();
        if (lclx == CommonConstantUtils.LCLX_ZH || lclx == CommonConstantUtils.LCLX_PLZH) {
            sfxxQO.setXmid(xmid);
            wxjjxxDO.setXmid(xmid);
        } else {
            sfxxQO.setGzlslid(gzlslid);
            wxjjxxDO.setGzlslid(gzlslid);
        }
        //排除月结收费
        sfxxQO.setSfyj(CommonConstantUtils.SF_F_DM);
        bdcSlSfxxDOList = this.bdcSlSfxxService.queryBdcSlSfxx(sfxxQO);
        BigDecimal qlrZje = new BigDecimal(0);
        BigDecimal ywrZje = new BigDecimal(0);
        Integer hyzfjflx = null;
        // 计算收费金额
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            hyzfjflx = bdcSlSfxxDOList.get(0).getHyzfjflx();
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                BigDecimal hj = new BigDecimal(Optional.ofNullable(bdcSlSfxxDO.getHj()).orElse(0.00));
                if (CommonConstantUtils.QLRLB_QLR.equals(bdcSlSfxxDO.getQlrlb())) {
                    qlrZje = qlrZje.add(hj);
                }
                if (CommonConstantUtils.QLRLB_YWR.equals(bdcSlSfxxDO.getQlrlb())) {
                    ywrZje = ywrZje.add(hj);
                }
            }
        }
        // 计算收税金额
        if (Objects.isNull(hyzfjflx) || Objects.equals(2, hyzfjflx)) {
            BdcSlHsxxDO bdcSlHsxxDOQuery = new BdcSlHsxxDO();
            bdcSlHsxxDOQuery.setXmid(xmid);
            List<BdcSlHsxxDO> bdcSlHsxxDOList = this.bdcSlHsxxService.listBdcSlHsxx(bdcSlHsxxDOQuery);
            if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
                    BigDecimal hj = new BigDecimal(Optional.ofNullable(bdcSlHsxxDO.getSjyzhj()).orElse(0.00));
                    if (CommonConstantUtils.QLRLB_QLR.equals(bdcSlHsxxDO.getSqrlb())) {
                        qlrZje = qlrZje.add(hj);
                    }
                    if (CommonConstantUtils.QLRLB_YWR.equals(bdcSlHsxxDO.getSqrlb())) {
                        ywrZje = ywrZje.add(hj);
                    }
                }
            }
        }

        //计算维修资金
        List<BdcSlWxjjxxDO> bdcSlWxjjxxDOList = this.bdcSlWxjjxxService.queryBdcSlWxjjxx(wxjjxxDO);
        if (CollectionUtils.isNotEmpty(bdcSlWxjjxxDOList)) {
            for (BdcSlWxjjxxDO bdcSlWxjjxxDO : bdcSlWxjjxxDOList) {
                BigDecimal hj = new BigDecimal(Optional.ofNullable(bdcSlWxjjxxDO.getHj()).orElse(0.00));
                qlrZje = qlrZje.add(hj);
            }
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("qlrzje", qlrZje.doubleValue());
        map.put("ywrzje", ywrZje.doubleValue());
        return map;
    }

    /**
     * @param bdcSlSfssDdxxQO 收费收税订单信息查询QO对象
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 据受理编号查询收费状态并且更新订单信息和收费收税信息
     * @date : 2020/5/29 8:47
     */
    @Override
    public void queryAndUpdateSfzt(BdcSlSfssDdxxQO bdcSlSfssDdxxQO) {
        if(!CheckParameter.checkAnyParameter(bdcSlSfssDdxxQO, "gzlslid","yjdh")){
            throw new AppException("查询订单缺失参数工作流实例ID或月结单号信息。");
        }

        String slbh = "", jfType ="";
        if(StringUtils.isNotBlank(bdcSlSfssDdxxQO.getGzlslid())){
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(bdcSlSfssDdxxQO.getGzlslid());
            if(CollectionUtils.isEmpty(bdcSlSfxxDOList)){
                throw new AppException(ErrorCode.CUSTOM, "未获取到不动产收费信息，工作流实例ID:" + bdcSlSfssDdxxQO.getGzlslid());
            }
            if (Objects.equals(CommonConstantUtils.JKTJ_HYZF, bdcSlSfxxDOList.get(0).getHyzfjftj())) {
                jfType = "DK";
                BdcSlSfssDdxxQO param = new BdcSlSfssDdxxQO();
                param.setGzlslid(bdcSlSfssDdxxQO.getGzlslid());
                List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = this.listBdcSlSfssDdxx(param);
                if(CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList) && bdcSlSfssDdxxDOList.size() > 1){
                    // 合一支付，并且为拆单的情况，循环订单信息，查询订单信息
                    for(BdcSlSfssDdxxDO bdcSlSfssDdxxDO : bdcSlSfssDdxxDOList){
                        this.ykqSfztCx(jfType, bdcSlSfssDdxxDO.getSfglid(), bdcSlSfssDdxxQO.getGzlslid(), bdcSlSfssDdxxQO.getYjdh());
                    }
                    return;
                }
            }
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(bdcSlSfssDdxxQO.getGzlslid());
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                throw new AppException("参数缺失，未获取到受理编号信息。");
            }
            slbh = bdcXmDOList.get(0).getSlbh();
        }
        // 月结缴费信息，slbh传月结单号信息
        if (StringUtils.isNotBlank(bdcSlSfssDdxxQO.getYjdh())) {
            slbh = bdcSlSfssDdxxQO.getYjdh();
            List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = this.queryBdcSlSfssDdxxByYjdh(bdcSlSfssDdxxQO.getYjdh());
            if(CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)){
                Integer hyzfjftj = bdcSlSfssDdxxDOList.get(0).getHyzfjftj();
                if(Objects.equals(hyzfjftj, CommonConstantUtils.JKTJ_HYZF)){
                    jfType = "DK";
                }
            }
        }

        this.ykqSfztCx(jfType, slbh, bdcSlSfssDdxxQO.getGzlslid(), bdcSlSfssDdxxQO.getYjdh());
    }

    // 调用Exchange查询缴费状态接口
    private void ykqSfztCx(String jfType, String slbh, String gzlslid, String yjdh){
        Map paramMap = new HashMap(2);
        paramMap.put("slbh", slbh);
        paramMap.put("jfType", jfType);
        LOGGER.warn("slbh:{}缴费状态查询接口入参{}", slbh, JSON.toJSONString(paramMap));
        Object response = exchangeInterfaceFeignService.requestInterface("wwsq_ykqjfztcx", paramMap);
//        String response = "[{\"ddje\":\"160\",\"dsfddbh\":\"null\",\"qlrlb\":\"2\",\"sfglidlx\":\"null\",\"jfurl\":\"1\",\"jfzt\":\"2\",\"ddbh\":\"202211163\",\"sfglid\":\"null\",\"ze\":\"160\",\"jkm\":\"1\"}]";
        LOGGER.info("slbh:{}缴费状态查询接口返回信息：{}", slbh, response);
        if (null == response) {
            throw new AppException("未获取到缴费状态。");
        }
        List<BdcSlSfssDdxxDO> responseDdxxList = JSONObject.parseArray(JSONObject.toJSONString(response), BdcSlSfssDdxxDO.class);
        if (CollectionUtils.isEmpty(responseDdxxList)) {
            throw new AppException("未获取到缴费状态。");
        }
        //实时生成订单信息并更新状态
        saveAndUpdateDdxxByDdbh(responseDdxxList, gzlslid, yjdh);
    }

    @Override
    public void saveDsfDdxx(BdcDsfSfssDdxxDTO bdcDsfSfssDdxxDTO) {
        if (StringUtils.isBlank(bdcDsfSfssDdxxDTO.getSlbh()) || bdcDsfSfssDdxxDTO.getZe() == null) {
            throw new AppException("接收第三方收费收税订单信息缺失参数");
        }
        List<BdcSlSfssDdxxDO> qlrDdxxList = new ArrayList<>();
        List<BdcSlSfssDdxxDO> ywrDdxxList = new ArrayList<>();
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxBySlbh(bdcDsfSfssDdxxDTO.getSlbh());
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            if (CollectionUtils.isNotEmpty(bdcDsfSfssDdxxDTO.getBdcSlSfssDdxxDOList())) {
                for (BdcSlSfssDdxxDO bdcSlSfssDdxxDO : bdcDsfSfssDdxxDTO.getBdcSlSfssDdxxDOList()) {
                    bdcSlSfssDdxxDO.setGzlslid(bdcXmDTOList.get(0).getGzlslid());
                    bdcSlSfssDdxxDO.setSfglid(bdcXmDTOList.get(0).getXmid());
                    bdcSlSfssDdxxDO.setSfglidlx(CommonConstantUtils.SFGLID_LX_SFSSXXZJ);
                    bdcSlSfssDdxxDO.setZe(bdcDsfSfssDdxxDTO.getZe());
                    bdcSlSfssDdxxDO.setId(UUIDGenerator.generate16());
                    if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcSlSfssDdxxDO.getQlrlb())) {
                        qlrDdxxList.add(bdcSlSfssDdxxDO);
                    } else if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, bdcSlSfssDdxxDO.getQlrlb())) {
                        ywrDdxxList.add(bdcSlSfssDdxxDO);
                    }

                }
                if (qlrDdxxList.size() == 1 || ywrDdxxList.size() == 1) {
                    //订单只存在一条,更新主表订单信息
                    List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(bdcXmDTOList.get(0).getGzlslid());
                    if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                        for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                            if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcSlSfxxDO.getQlrlb()) && qlrDdxxList.size() == 1) {
                                bdcSlSfxxDO.setJfsbm(qlrDdxxList.get(0).getDdbh());
                                bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                            } else if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, bdcSlSfxxDO.getQlrlb()) && ywrDdxxList.size() == 1) {
                                bdcSlSfxxDO.setJfsbm(ywrDdxxList.get(0).getDdbh());
                                bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                            }

                        }


                    }

                }
                insertBatchBdcSlSfssDdxx(bdcDsfSfssDdxxDTO.getBdcSlSfssDdxxDOList());
            }


        }

    }

    @Override
    public void saveAndUpdateDdxxByDdbh(List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList, String gzlslid, String yjdh) {
        Integer hyzf = 0;
        if (StringUtils.isNotBlank(gzlslid)){
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                if (Objects.equals(CommonConstantUtils.JKTJ_HYZF, bdcSlSfxxDOList.get(0).getHyzfjftj())) {
                        hyzf = bdcSlSfxxDOList.get(0).getHyzfjftj();
                    }
                }
            }

        //需要新增的订单信息
        List<BdcSlSfssDdxxDO> insertDdxxList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)) {
            for (BdcSlSfssDdxxDO bdcSlSfssDdxxDO : bdcSlSfssDdxxDOList) {
                if (StringUtils.isNotBlank(bdcSlSfssDdxxDO.getDdbh())) {
                    BdcSlSfssDdxxQO queryParam = new BdcSlSfssDdxxQO();
                    queryParam.setDdbh(bdcSlSfssDdxxDO.getDdbh());
                    List<BdcSlSfssDdxxDO> slSfssDdxxDOS = listBdcSlSfssDdxx(queryParam);
                    if (CollectionUtils.isEmpty(slSfssDdxxDOS)) {
                        if (CommonConstantUtils.SFZT_YJF.equals(bdcSlSfssDdxxDO.getJfzt()) && StringUtils.isNotBlank(gzlslid) ) {
                            //只新增已缴费订单
                            if (StringUtils.isBlank(bdcSlSfssDdxxDO.getSfglid()) || bdcSlSfssDdxxDO.getSfglidlx() == null) {
                                LOGGER.error("查询订单信息存在返回订单信息不全:{}", bdcSlSfssDdxxDO);
                                bdcSlSfssDdxxDO.setGzlslid(gzlslid);
                                bdcSlSfssDdxxDO.setId(UUIDGenerator.generate16());
                                //无法区分的订单信息税费关联ID统一存工作流实例ID,类型为其他
                                bdcSlSfssDdxxDO.setSfglidlx(CommonConstantUtils.SFGLID_LX_QT);
                                bdcSlSfssDdxxDO.setSfglid(gzlslid);
                                insertDdxxList.add(bdcSlSfssDdxxDO);
                            } else {
                                bdcSlSfssDdxxDO.setGzlslid(gzlslid);
                                bdcSlSfssDdxxDO.setId(UUIDGenerator.generate16());
                                insertDdxxList.add(bdcSlSfssDdxxDO);
                            }
                        }
                    } else if (bdcSlSfssDdxxDO.getJfzt() != null && !bdcSlSfssDdxxDO.getJfzt().equals(slSfssDdxxDOS.get(0).getJfzt())) {
                        BdcSlSfssDdxxDO yddxx = slSfssDdxxDOS.get(0);
                        yddxx.setJfzt(bdcSlSfssDdxxDO.getJfzt());
                        if (StringUtils.isNotBlank(bdcSlSfssDdxxDO.getJkm())){
                            yddxx.setJkm(bdcSlSfssDdxxDO.getJkm());
                        }
                        //更新订单信息
                        updateBdcSlSfssDdxx(yddxx);

                        // 更新月结订单状态
                        this.modifyYjSfDdxxDdzt(yjdh, bdcSlSfssDdxxDO.getJfzt());
                    }
                }
            }

            if (CollectionUtils.isNotEmpty(insertDdxxList)) {
                insertBatchBdcSlSfssDdxx(insertDdxxList);
            }

            //处理主表状态
            dealYkqDdZbzt(gzlslid, yjdh, hyzf);
        }
    }

    /**
     * @param bdcSlSfssDdxxQO 收费收税订单信息查询QO对象
     * @return 订单信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 退款申请
     */
    @Override
    public List<BdcSlSfssDdxxDO> ddxxTksq(BdcSlSfssDdxxQO bdcSlSfssDdxxQO) {
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = new ArrayList<>();
        // 收费订单
        if(StringUtils.isNotBlank(bdcSlSfssDdxxQO.getGzlslid())){
            bdcSlSfssDdxxDOList = this.ddxxTksqByGzlslid(bdcSlSfssDdxxQO.getGzlslid());
        }
        // 月结订单
        if(StringUtils.isNotBlank(bdcSlSfssDdxxQO.getYjdh())){
            bdcSlSfssDdxxDOList = this.ddxxTksqByYjdh(bdcSlSfssDdxxQO.getYjdh());
        }
        return bdcSlSfssDdxxDOList;
    }

    /**
     * 订单退款申请
     * @param gzlslid  工作流实例ID
     * @return 不动产收费收税订单信息DO
     */
    private List<BdcSlSfssDdxxDO> ddxxTksqByGzlslid(String gzlslid){
        if (StringUtils.isAnyBlank(gzlslid)) {
            throw new MissingArgumentException("缺失接口参数工作流实例ID");
        }
        // 已缴费收费信息列表
        List<BdcSlSfxxDO> yjfSfxxList = new ArrayList<>();
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
        // 去除月结数据
        bdcSlSfxxDOList = bdcSlSfxxDOList.stream().filter(t->!CommonConstantUtils.SF_S_DM.equals(t.getSfyj())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            for (BdcSlSfxxDO sfxxDO : bdcSlSfxxDOList) {
                if (BdcSfztEnum.YJF.key().equals(sfxxDO.getSfzt()) || BdcSfztEnum.BFJF.key().equals(sfxxDO.getSfzt())) {
                    yjfSfxxList.add(sfxxDO);
                }
            }
        }
        if (CollectionUtils.isEmpty(yjfSfxxList)) {
            throw new AppException("当前非已缴费状态");
        }

        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDTOList)) {
            throw new AppException("项目集合为空");
        }
        // 返回的退款信息
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = this.requestTksq(bdcXmDTOList.get(0).getSlbh(), bdcXmDTOList.get(0).getXmid());
        if(CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)){
            //更新收费信息为退款中
            for (BdcSlSfxxDO yjfSfxx : yjfSfxxList) {
                yjfSfxx.setSfzt(bdcSlSfssDdxxDOList.get(0).getJfzt());
                bdcSlSfxxService.updateBdcSlSfxx(yjfSfxx);
            }
        }
        return bdcSlSfssDdxxDOList;
    }

    /**
     * 月结订单退款申请
     * @param yjdh  月结单号
     * @return 不动产收费收税订单信息DO
     */
    private List<BdcSlSfssDdxxDO> ddxxTksqByYjdh(String yjdh){
        BdcYjSfDdxxDO bdcYjSfDdxxDO = this.bdcYjSfDdxxService.queryBdcYjSfDdxxByYjdh(yjdh);
        if(Objects.isNull(bdcYjSfDdxxDO)){
            throw new AppException("未获取到月结订单信息");
        }
        if(!BdcSfztEnum.YJF.key().equals(bdcYjSfDdxxDO.getDdzt())){
            throw new AppException("当前非已缴费状态");
        }
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = this.requestTksq(yjdh, "");
        if(CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)){
            //更新收费信息为退款中
            List<String> sfxxidList = this.bdcYjdhSfxxGxService.queryYjSfxxidList(yjdh);
            if(CollectionUtils.isNotEmpty(sfxxidList)){
                this.bdcSlSfxxService.modifyBdcSlSfxxSfztPl(sfxxidList, bdcSlSfssDdxxDOList.get(0).getJfzt());
                this.modifyYjSfDdxxDdzt(yjdh, bdcSlSfssDdxxDOList.get(0).getJfzt());
            }
        }
        return bdcSlSfssDdxxDOList;
    }

    /**
     * 请求退款申请
     */
    private List<BdcSlSfssDdxxDO> requestTksq(String slbh, String xmid){
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = new ArrayList<>();
        Map paramMap = new HashMap();
        paramMap.put("slbh", slbh);
        paramMap.put("xmid", xmid);
        Object response = exchangeInterfaceFeignService.requestInterface("wwsq_ykqtkcz", paramMap);
        if (response != null) {
            LOGGER.info("受理编号:{},退款申请接口调用成功,返回结果：{}", slbh, response);
            bdcSlSfssDdxxDOList = JSONObject.parseArray(JSONObject.toJSONString(response), BdcSlSfssDdxxDO.class);
            if (CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)) {
                for (BdcSlSfssDdxxDO bdcSlSfssDdxxDO : bdcSlSfssDdxxDOList) {
                    if (StringUtils.isBlank(bdcSlSfssDdxxDO.getDdbh())) {
                        throw new AppException("接口返回信息缺失订单编号");
                    }
                    if (StringUtils.isBlank(bdcSlSfssDdxxDO.getTkdh())) {
                        throw new AppException("接口返回信息缺失退款单号");
                    }
                    BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
                    bdcSlSfssDdxxQO.setDdbh(bdcSlSfssDdxxDO.getDdbh());
                    List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOS = listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
                    if (CollectionUtils.isEmpty(bdcSlSfssDdxxDOS)) {
                        throw new AppException("当前订单编号未查询到对应的订单信息");
                    }
                    BdcSlSfssDdxxDO sfssDdxxDO = bdcSlSfssDdxxDOS.get(0);
                    bdcSlSfssDdxxDO.setId(sfssDdxxDO.getId());
                    BeanUtils.copyProperties(bdcSlSfssDdxxDO, sfssDdxxDO);
                    updateBdcSlSfssDdxx(sfssDdxxDO);
                }
            }
        }
        return bdcSlSfssDdxxDOList;
    }

    @Override
    public BdcSlSfxxDO queryTkqk(BdcSlSfssDdxxQO bdcSlSfssDdxxQO) {
        if(StringUtils.isBlank(bdcSlSfssDdxxQO.getGzlslid())&&StringUtils.isBlank(bdcSlSfssDdxxQO.getYjdh()) ){
            throw new AppException("缺失参数：工作流实例ID或月结单号。");
        }
        // 获取订单信息内容
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
        if (CollectionUtils.isEmpty(bdcSlSfssDdxxDOList)) {
            throw new AppException("不存在收费信息。");
        }
        // 获取存在退款单号订单
        List<BdcSlSfssDdxxDO> tkDdxxList = bdcSlSfssDdxxDOList.stream()
                .filter(t -> StringUtils.isNotBlank(t.getTkdh())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(tkDdxxList)) {
            throw new MissingArgumentException("未获取到退款信息。");
        }

        if(StringUtils.isNotBlank(bdcSlSfssDdxxQO.getGzlslid())){
            return this.tkqkForGzlslid(bdcSlSfssDdxxQO.getGzlslid(), tkDdxxList);
        }else{
            return this.tkqkForYjdh(bdcSlSfssDdxxQO.getYjdh(), tkDdxxList);
        }
    }

    /**
     *  收费订单退款情况查询
     * @param gzlslid  工作流实例ID
     * @param tkDdxxList 退款订单信息
     * @return 收费信息
     */
    public BdcSlSfxxDO tkqkForGzlslid(String gzlslid, List<BdcSlSfssDdxxDO> tkDdxxList){
        // 收费订单通过工作流实例ID获取受理编号；月结订单根据月结单号获取受理编号信息
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDTOList)) {
            throw new AppException("项目集合为空");
        }
        // 循环订单集合，获取订单的退款状态，并更新订单的缴费状态
        for (BdcSlSfssDdxxDO tkddxx : tkDdxxList) {
            requestTkztcx(bdcXmDTOList.get(0).getXmid(), bdcXmDTOList.get(0).getSlbh(), tkddxx);
            updateBdcSlSfssDdxx(tkddxx);
        }
        Integer tkzt = getZbTkzt(tkDdxxList);
        // 获取所有非月结收费信息并更新
        BdcSlSfxxDO sfxxQO = new BdcSlSfxxDO();
        sfxxQO.setGzlslid(gzlslid);
        sfxxQO.setSfyj(CommonConstantUtils.SF_F_DM);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.queryBdcSlSfxx(sfxxQO);
        if (null != tkzt && CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            for (BdcSlSfxxDO sfxxDO : bdcSlSfxxDOList) {
                sfxxDO.setSfzt(tkzt);
                bdcSlSfxxService.updateBdcSlSfxx(sfxxDO);
            }
        }
        return CollectionUtils.isNotEmpty(bdcSlSfxxDOList) ?bdcSlSfxxDOList.get(0):new BdcSlSfxxDO();
    }

    /**
     *  月结订单退款情况查询
     * @param yjdh  月结单号
     * @param tkDdxxList 退款订单信息
     * @return 收费信息
     */
    public BdcSlSfxxDO tkqkForYjdh(String yjdh, List<BdcSlSfssDdxxDO> tkDdxxList){
        // 循环订单集合，获取订单的退款状态，并更新订单的缴费状态
        for (BdcSlSfssDdxxDO tkddxx : tkDdxxList) {
            requestTkztcx(yjdh, "", tkddxx);
            updateBdcSlSfssDdxx(tkddxx);
        }
        Integer tkzt = getZbTkzt(tkDdxxList);
        BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
        List<String> sfxxidList = this.bdcYjdhSfxxGxService.queryYjSfxxidList(yjdh);
        if(CollectionUtils.isNotEmpty(sfxxidList)){
            this.bdcSlSfxxService.modifyBdcSlSfxxSfztPl(sfxxidList, tkzt);
            this.modifyYjSfDdxxDdzt(yjdh, tkzt);
            bdcSlSfxxDO.setSfzt(tkzt);
        }
        return bdcSlSfxxDO;
    }

    @Override
    public List<BdcSlSfssDdxxDO> hbjf(String jkfs, String gzlslid) {
        //获取权利人义务人税费总和
        Double sfssze = this.computeSfze(gzlslid, null);
        if(sfssze.equals((double) 0)){
            throw new AppException(ErrorCode.CUSTOM, "收费收税金额额为0");
        }
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOS = new ArrayList<>();
        List<BdcSlSfssDdxxDO> list = new ArrayList<>();
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);

        //创建订单
        BdcSlSfssDdxxDO sfssDdxxDO = new BdcSlSfssDdxxDO();
        sfssDdxxDO.setId(UUIDGenerator.generate16());
        sfssDdxxDO.setDdje(NumberUtil.formatDigit(sfssze, 2));
        sfssDdxxDO.setZe(NumberUtil.formatDigit(sfssze, 2));
        sfssDdxxDO.setGzlslid(gzlslid);
        sfssDdxxDO.setSfglid(gzlslid);
        sfssDdxxDO.setSfglidlx(CommonConstantUtils.SFGLID_LX_QT);
        bdcSlSfssDdxxDOS.add(sfssDdxxDO);
        bdcSlSfssDdxxDOS = createOrder(bdcSlSfssDdxxDOS, bdcXmDTOList.get(0).getSlbh(), bdcXmDTOList.get(0).getXmid(),CommonConstantUtils.SF_F_DM.toString());
        if (CollectionUtils.isEmpty(bdcSlSfssDdxxDOS)) {
            throw new AppException(ErrorCode.CUSTOM, "调用一卡请生成订单接口失败，未生成订单信息");
        }

        BdcSlSfssDdxxDO bdcSlSfssDdxxDO = new BdcSlSfssDdxxDO();
        BeanUtils.copyProperties(bdcSlSfssDdxxDOS.get(0), bdcSlSfssDdxxDO);
        list.add(bdcSlSfssDdxxDO);

        //若为pos缴费合一支付时jfurl不保存数据库， 合一支付返回的为 base64编码 StringUtils.isNotBlank(jkfs) && jkfs.equals("2")
        if (StringUtils.equals(jkfs, String.valueOf(CommonConstantUtils.JKTJ_HYZF))&& CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOS)){
            for(BdcSlSfssDdxxDO ddxxDO : bdcSlSfssDdxxDOS){
                ddxxDO.setJfurl("");
            }
        }
        //新增新生成的订单数据
        if (CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOS)) {
            this.insertBatchBdcSlSfssDdxx(bdcSlSfssDdxxDOS);
        }
        return list;
    }

    @Override
    public boolean checkDdxxIsSx(BdcSlSfssDdxxQO bdcSlSfssDdxxQO) {
        boolean issx = false;
        if (StringUtils.isBlank(bdcSlSfssDdxxQO.getDdbh()) && StringUtils.isBlank(bdcSlSfssDdxxQO.getDsfddbh())) {
            throw new AppException("判断订单是否失效缺失参数：订单编号和第三方订单编号为空");
        }

        BdcSlSfssDdxxDO bdcSlSfssDdxxDO = null;
        //1.查询唯一订单
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
        if (CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)) {
            bdcSlSfssDdxxDO = bdcSlSfssDdxxDOList.get(0);
        }
        if (bdcSlSfssDdxxDO == null) {
            throw new AppException("未查询到订单信息,订单编号与第三方订单编号：" + bdcSlSfssDdxxQO.getDdbh() + "," + bdcSlSfssDdxxQO.getDsfddbh());
        }

        //2.判断订单是否已支付,非已支付并且当前时间与订单生成时间时间差是否大于有效时间为失效订单
        Integer jfzt = bdcSlSfssDdxxDO.getJfzt();

        // 退款中和退款成功的状态直接认定为失效订单
        if (null !=jfzt &&(CommonConstantUtils.SFZT_TKZ.equals(jfzt) || CommonConstantUtils.SFZT_TKCG.equals(jfzt))) {
            return true;
        }
        // 缴费状态是空 未缴费 已核验 并且 前系统时间-生成订单时间>10分钟  视为已失效
        if (null == jfzt || CommonConstantUtils.SFZT_WJF.equals(jfzt) || CommonConstantUtils.SFZT_YHY.equals(jfzt)) {
            if (bdcSlSfssDdxxDO.getDdscsj() == null) {
                throw new AppException("订单生成时间为空,请检查");
            }
            if (DateUtils.addSeconds(bdcSlSfssDdxxDO.getDdscsj(), ddyxsj).before(new Date())) {
                issx = true;
            }
        }
        return issx;


    }

    @Override
    public List<BdcSlSfssDdxxDO> createYjSfOrder(String yjdh, String hyzfjftj, String jkqd) {
        if(StringUtils.isBlank(yjdh)){
            throw new MissingArgumentException("缺失参数月结单号。");
        }
        List<String> sfxxidList = bdcYjdhSfxxGxService.queryYjSfxxidList(yjdh);
        if(CollectionUtils.isEmpty(sfxxidList)){
            throw new NullPointerException("未获取到收费信息");
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxPl("","",sfxxidList, CommonConstantUtils.SF_S_DM,true);
        if(CollectionUtils.isEmpty(bdcSlSfxxDOList)){
            throw new NullPointerException("未获取到月结收费信息");
        }
        Double zje = 0.00;
        zje += bdcSlSfxxDOList.stream().filter(sfxxDO -> sfxxDO.getHj() != null).mapToDouble(BdcSlSfxxDO::getHj).sum();

        if(zje.equals((double) 0)){
            throw new AppException("需缴费金额为0");
        }
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = new ArrayList<>();
        BdcSlSfssDdxxDO sfssDdxxDO = new BdcSlSfssDdxxDO();
        sfssDdxxDO.setId(UUIDGenerator.generate16());
        sfssDdxxDO.setDdje(NumberUtil.formatDigit(zje, 2));
        sfssDdxxDO.setZe(NumberUtil.formatDigit(zje, 2));
        sfssDdxxDO.setSfglidlx(CommonConstantUtils.SFGLID_LX_QT);
        sfssDdxxDO.setSfglid(yjdh);
        sfssDdxxDO.setYjdh(yjdh);
        bdcSlSfssDdxxDOList.add(sfssDdxxDO);
        // 组织生成订单接口，请求的参数
        Map<String, Object> param = new HashMap<>(6);
        param.put("slbh", yjdh);
        param.put("xmid", "");
        param.put("ze", bdcSlSfssDdxxDOList.get(0).getZe());
        param.put("jffs", CommonConstantUtils.JFFS_POS);
        param.put("sfyj", CommonConstantUtils.SF_S_DM.toString());
        param.put("qd", CommonConstantUtils.JKQD_XX);
        param.put("zflx", "");
        param.put("jfType", "");
        // 月结缴费（扫码下单传：0线上支付， pos下单传：1线下支付）
        if(StringUtils.isNotBlank(jkqd)){
            param.put("qd", jkqd);
        }
        // 合一支付缴费途径（1：商业银行， 2：合一支付）
        if(StringUtils.isNotBlank(hyzfjftj)){
            param.put("zflx", CommonConstantUtils.DDXX_DDLX_JNF);
            if (StringUtils.equals(hyzfjftj, String.valueOf(CommonConstantUtils.JKTJ_SYYH))) {
                param.put("jfType", "");
            } else if (StringUtils.equals(hyzfjftj, String.valueOf(CommonConstantUtils.JKTJ_HYZF))) {
                param.put("jfType", "DK");
            }
        }

        List<Map<String, Object>> ddxxList = new ArrayList<>(bdcSlSfssDdxxDOList.size());
        for (BdcSlSfssDdxxDO bdcSlSfssDdxxDO : bdcSlSfssDdxxDOList) {
            Map<String, Object> ddxxMap = new HashMap<>(2);
            ddxxMap.put("id", bdcSlSfssDdxxDO.getId());
            ddxxMap.put("ddje", bdcSlSfssDdxxDO.getDdje());
            ddxxList.add(ddxxMap);
        }
        param.put("ddxx", ddxxList);
        // 调用生成订单接口，生成订单信息
        bdcSlSfssDdxxDOList = this.scdd(param, bdcSlSfssDdxxDOList, yjdh);
        if (CollectionUtils.isEmpty(bdcSlSfssDdxxDOList)) {
            throw new AppException("未生成订单信息");
        }

        // 合一支付生成订单后，返回的是base64二维码信息。我们只记录订单信息，base64不记录数据库，直接页面展示。
        List<BdcSlSfssDdxxDO> list = new ArrayList<>(bdcSlSfssDdxxDOList.size());
        if (CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList) && StringUtils.equals(hyzfjftj, String.valueOf(CommonConstantUtils.JKTJ_HYZF))){
            for(BdcSlSfssDdxxDO ddxx : bdcSlSfssDdxxDOList){
                BdcSlSfssDdxxDO bdcSlSfssDdxxDO = new BdcSlSfssDdxxDO();
                BeanUtils.copyProperties(ddxx, bdcSlSfssDdxxDO);
                list.add(bdcSlSfssDdxxDO);
                ddxx.setJfurl("");
                ddxx.setHyzfjftj(CommonConstantUtils.JKTJ_HYZF);
            }
        }

        //新增新生成的订单数据
        if (CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)) {
            this.insertBatchBdcSlSfssDdxx(bdcSlSfssDdxxDOList);
        }
        return list;
    }

    @Override
    public void zfDdxx(BdcSlSfssDdxxQO bdcSlSfssDdxxQO){
        if(StringUtils.isBlank(bdcSlSfssDdxxQO.getGzlslid())){
            throw new AppException("作废订单缺失参数：工作流实例ID");
        }
        // 获取订单信息内容
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
        if (CollectionUtils.isEmpty(bdcSlSfssDdxxDOList)) {
            throw new AppException("不存在订单信息,无需作废。");
        }
        //后台验证
        List<BdcSlSfssDdxxDO> yjfDdxxList = bdcSlSfssDdxxDOList.stream()
                .filter(t -> CommonConstantUtils.SFZT_BFJF.equals(t.getJfzt()) ||CommonConstantUtils.SFZT_YJF.equals(t.getJfzt())).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(yjfDdxxList)){
            throw new AppException("存在已缴费订单,不允许作废。");
        }
        //删除订单信息
        deleteBdcSlSfssDdxx(bdcSlSfssDdxxQO);

    }

    @Override
    public void udpateYjDdxxSfzt(BdcSlSfssDdxxQO bdcSlSfssDdxxQO) {
        if(StringUtils.isBlank(bdcSlSfssDdxxQO.getYjdh()) || Objects.isNull(bdcSlSfssDdxxQO.getJfzt())){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数月结单号或缴费状态");
        }
        // 1、更新收费收税订单信息表支付状态
        this.updateBdcSlSfssDdxxJfztByYjdh(bdcSlSfssDdxxQO.getYjdh(), bdcSlSfssDdxxQO.getJfzt());

        // 2、更新月结订单缴费状态
        this.modifyYjSfDdxxDdzt(bdcSlSfssDdxxQO.getYjdh(), bdcSlSfssDdxxQO.getJfzt());

        // 3、更新月结单号关联的收费信息的收费状态
        List<String> sfxxidList = this.bdcYjdhSfxxGxService.queryYjSfxxidList(bdcSlSfssDdxxQO.getYjdh());
        if(CollectionUtils.isNotEmpty(sfxxidList)){
            BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
            bdcSlSfxxQO.setSfxxidList(sfxxidList);
            bdcSlSfxxQO.setSfyj(CommonConstantUtils.SF_S_DM);
            bdcSlSfxxQO.setYhjkrkzt(bdcSlSfssDdxxQO.getYhjkrkzt());
            bdcSlSfxxQO.setJfsewmurl(bdcSlSfssDdxxQO.getDsfddbh());
            bdcSlSfxxQO.setSfzt(bdcSlSfssDdxxQO.getJfzt());
            this.bdcSlSfxxService.batchUpdateBdcSlSfxxBySfxxidList(bdcSlSfxxQO);
        }
    }

    @Override
    public Double computeSfze(String gzlslid, String qlrlb) {
        //获取权利人义务人税费总和
        Double sfssze = 0.00;
        //排除月结收费信息
        BdcSlSfxxDO sfxxQO =new BdcSlSfxxDO();
        sfxxQO.setGzlslid(gzlslid);
        sfxxQO.setSfyj(CommonConstantUtils.SF_F_DM);
        if (StringUtils.isNotBlank(qlrlb)) {
            sfxxQO.setQlrlb(qlrlb);
        }
        // 计算收费信息金额
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.queryBdcSlSfxx(sfxxQO);
        Integer hyzfjflx = null;
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            hyzfjflx = bdcSlSfxxDOList.get(0).getHyzfjflx();
            sfssze += bdcSlSfxxDOList.stream().filter(sfxxDO -> sfxxDO.getHj() != null).mapToDouble(BdcSlSfxxDO::getHj).sum();
        }
        //根据gzlslid查询bdc_sl_sfxx的数据，当hyzfjflx =1 的时候查登记费，当hyzfjflx= 2或者空的时候税费统缴
        if (Objects.isNull(hyzfjflx) || Objects.equals(2, hyzfjflx)) {
            // 计算税费金额
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                List<BdcSlHsxxDO> bdcSlHsxxDOList = this.queryBdcSlHsxxBySlbhAndQlrlb(bdcXmDTOList.get(0).getSlbh(), qlrlb);
                if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                    sfssze += bdcSlHsxxDOList.stream().filter(hsxxDO -> hsxxDO.getSjyzhj() != null).mapToDouble(BdcSlHsxxDO::getSjyzhj).sum();
                }
            }
        }
        // 计算维修资金金额
        BdcSlWxjjxxDO queryParam = new BdcSlWxjjxxDO();
        queryParam.setGzlslid(gzlslid);
        if(StringUtils.isNotBlank(qlrlb)){
            queryParam.setQlrlb(qlrlb);
        }
        List<BdcSlWxjjxxDO> bdcSlWxjjxxDOList = this.bdcSlWxjjxxService.queryBdcSlWxjjxx(queryParam);
        if (CollectionUtils.isNotEmpty(bdcSlWxjjxxDOList)) {
            sfssze += bdcSlWxjjxxDOList.stream().filter(t -> t.getHj() != null).mapToDouble(BdcSlWxjjxxDO::getHj).sum();
        }
        return NumberUtil.formatDigit(sfssze, 2);
    }

    // 根据受理编号和权利人类别获取核税信息金额
    private List<BdcSlHsxxDO> queryBdcSlHsxxBySlbhAndQlrlb(String slbh, String qlrlb){
        List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxxBySlbh(slbh);
        if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList) && StringUtils.isNotBlank(qlrlb)) {
            bdcSlHsxxDOList = bdcSlHsxxDOList.stream().filter(t-> StringUtils.equals(t.getSqrlb(), qlrlb)).collect(Collectors.toList());
        }
        return bdcSlHsxxDOList;
    }

    /**
     * 根据月结单号更新缴费状态
     */
    private int updateBdcSlSfssDdxxJfztByYjdh(String yjdh, Integer jfzt){
        int result = 0;
        if(StringUtils.isBlank(yjdh) || Objects.isNull(jfzt)){
            Example example= new Example(BdcSlSfssDdxxDO.class,false);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("yjdh", yjdh);

            BdcSlSfssDdxxDO bdcSlSfssDdxxDO = new BdcSlSfssDdxxDO();
            bdcSlSfssDdxxDO.setJfzt(jfzt);
            result = entityMapper.updateByExampleSelectiveNotNull(bdcSlSfssDdxxDO, example);
        }
        return result;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理主表状态
     */
    private void dealYkqDdZbzt(String gzlslid, String yjdh, Integer hyzf) {
        // 查询订单信息
        BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
        bdcSlSfssDdxxQO.setGzlslid(gzlslid);
        bdcSlSfssDdxxQO.setYjdh(yjdh);
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = this.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);

        // 普通缴费更新主表
        if (CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList) && StringUtils.isNotBlank(gzlslid)) {
            Map<String, List<BdcSlSfssDdxxDO>> ddxxMap = bdcSlSfssDdxxDOList.stream().collect(Collectors.groupingBy(BdcSlSfssDdxxDO::getSfglid));
            if (MapUtils.isNotEmpty(ddxxMap)) {
                //整理分组后的数据
                for (Map.Entry<String, List<BdcSlSfssDdxxDO>> entry : ddxxMap.entrySet()) {
                    List<BdcSlSfssDdxxDO> ddxxDOList = entry.getValue();
                    String sfglid = ddxxDOList.get(0).getSfglid();
                    String jkm = ddxxDOList.get(0).getJkm();
                    Double ze = ddxxDOList.stream().filter(ddxx -> ddxx.getDdje() != null && BdcSfztEnum.YJF.key().equals(ddxx.getJfzt())).mapToDouble(BdcSlSfssDdxxDO::getDdje).sum();
                    ze = NumberUtil.formatDigit(ze, 2);
                    int zdzt = CommonConstantUtils.SFZT_WJF;
                    if (ddxxDOList.get(0).getSfglidlx() == 1) {
                        //收费
                        BdcSlSfxxDO sfxxDO = bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfglid);
                        if (sfxxDO != null) {
                            if (!ze.equals((double) 0)) {
                                if (ze < sfxxDO.getHj()) {
                                    zdzt = CommonConstantUtils.SFZT_BFJF;
                                } else {
                                    zdzt = CommonConstantUtils.SFZT_YJF;
                                }
                            }
                            BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
                            bdcSlSfxxDO.setSfxxid(sfglid);
                            bdcSlSfxxDO.setSfzt(zdzt);
                            //当缴款方式为合一支付时更新缴费状态，缴库状态以及缴费书编码
                            if (Objects.equals(CommonConstantUtils.JKTJ_HYZF, hyzf) && 2 == zdzt && StringUtils.isNotBlank(jkm)){
                                bdcSlSfxxDO.setJfsbm(jkm);
                                bdcSlSfxxDO.setYhjkrkzt(1);
                            }
                            //缴款方式默认线上缴费
                            bdcSlSfxxDO.setJkfs(CommonConstantUtils.JKFS_XSJF);
                            bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                        } else {
                            LOGGER.error("当前税费关联id未查询到收费信息:{}", sfglid);
                        }
                    } else if (ddxxDOList.get(0).getSfglidlx() == 3) {
                        //收税
                        BdcSlHsxxDO slHsxxQO = new BdcSlHsxxDO();
                        slHsxxQO.setHsxxid(sfglid);
                        List<BdcSlHsxxDO> slHsxxDOList = bdcSlHsxxService.listBdcSlHsxx(slHsxxQO);
                        if (CollectionUtils.isNotEmpty(slHsxxDOList)) {
                            if (!ze.equals((double) 0)) {
                                if (ze < slHsxxDOList.get(0).getSjyzhj()) {
                                    zdzt = CommonConstantUtils.SFZT_BFJF;
                                } else {
                                    zdzt = CommonConstantUtils.SFZT_YJF;
                                }
                            }
                            BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
                            bdcSlHsxxDO.setHsxxid(sfglid);
                            bdcSlHsxxDO.setJfzt(zdzt);
                            //当缴款方式为合一支付时更新税务的缴费状态，缴库状态
                            if (Objects.equals(CommonConstantUtils.JKTJ_HYZF, hyzf) && 2 == zdzt){
                                bdcSlHsxxDO.setYhjkrkzt(1);
                            }
                            bdcSlHsxxService.updateBdcSlHsxx(bdcSlHsxxDO);
                        } else {
                            LOGGER.error("当前税费关联id未查询到核税信息:{}", sfglid);
                        }
                    } else if (CommonConstantUtils.SFGLID_LX_QT.equals(ddxxDOList.get(0).getSfglidlx())) {
                        //其他
                        //如果已缴费金额等于权利人义务人税费总和，更新状态
                        //获取权利人义务人税费总和
                        Double sfssze = 0.00;
                        BdcSlSfxxDO sfxxQO =new BdcSlSfxxDO();
                        sfxxQO.setGzlslid(gzlslid);
                        sfxxQO.setSfyj(CommonConstantUtils.SF_F_DM);
                        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.queryBdcSlSfxx(sfxxQO);
                        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                            sfssze += bdcSlSfxxDOList.stream().filter(sfxxDO -> sfxxDO.getHj() != null).mapToDouble(BdcSlSfxxDO::getHj).sum();
                        }
                        String slbh = bdcXmFeignService.querySlbh(gzlslid);
                        List<BdcSlHsxxDO> bdcSlHsxxDOList = new ArrayList<>();
                        if (StringUtils.isNotBlank(slbh)) {
                            bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxxBySlbh(slbh);
                            if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                                sfssze += bdcSlHsxxDOList.stream().filter(hsxxDO -> hsxxDO.getSjyzhj() != null).mapToDouble(BdcSlHsxxDO::getSjyzhj).sum();
                            }
                        }
                        sfssze =NumberUtil.formatDigit(sfssze, 2);
                        if (!ze.equals((double) 0)) {
                            if (ze < sfssze) {
                                zdzt = CommonConstantUtils.SFZT_BFJF;
                            } else {
                                zdzt = CommonConstantUtils.SFZT_YJF;
                            }
                        }
                        //更新收费
                        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                                bdcSlSfxxDO.setSfzt(zdzt);

                                //当缴款方式为合一支付时更新缴费状态，缴库状态以及缴费书编码
                                if (Objects.equals(CommonConstantUtils.JKTJ_HYZF, hyzf) && 2 == zdzt && StringUtils.isNotBlank(jkm) ){
                                    bdcSlSfxxDO.setJfsbm(jkm);
                                    bdcSlSfxxDO.setYhjkrkzt(1);
                                }
                                //缴款方式默认线上缴费
                                bdcSlSfxxDO.setJkfs(CommonConstantUtils.JKFS_XSJF);
                                bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                            }

                        }
                        //更新收税
                        if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                            for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
                                bdcSlHsxxDO.setJfzt(zdzt);
                                //当缴款方式为合一支付时更新税务的缴费状态，缴库状态
                                if (Objects.equals(CommonConstantUtils.JKTJ_HYZF, hyzf) && 2 == zdzt){
                                    bdcSlHsxxDO.setYhjkrkzt(1);
                                }
                                bdcSlHsxxService.updateBdcSlHsxx(bdcSlHsxxDO);
                            }

                        }

                    } else if (CommonConstantUtils.SFGLID_LX_SFSSXXZJ.equals(ddxxDOList.get(0).getSfglidlx())) {
                        //税费,sfglid为项目ID,按权利人类别更新状态
                        List<BdcSlSfssDdxxDO> qlrsfddxxList = new ArrayList<>();
                        List<BdcSlSfssDdxxDO> ywrsfddxxList = new ArrayList<>();
                        if (CollectionUtils.isNotEmpty(ddxxDOList)) {
                            for (BdcSlSfssDdxxDO bdcSlSfssDdxxDO : ddxxDOList) {
                                if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcSlSfssDdxxDO.getQlrlb())) {
                                    qlrsfddxxList.add(bdcSlSfssDdxxDO);
                                } else if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, bdcSlSfssDdxxDO.getQlrlb())) {
                                    ywrsfddxxList.add(bdcSlSfssDdxxDO);

                                }

                            }
                        }

                        //更新权利人收费信息和核税信息
                        updateSfztAndHsjfzt(qlrsfddxxList, CommonConstantUtils.QLRLB_QLR, gzlslid, sfglid, hyzf);
                        //更新义务人收费信息和核税信息
                        updateSfztAndHsjfzt(ywrsfddxxList, CommonConstantUtils.QLRLB_YWR, gzlslid, sfglid, hyzf);

                    }
                }
            }
        }

        // 月结缴费订单，查询月结收费信息关系，更新收费信息状态
        if(CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList) && StringUtils.isNotBlank(yjdh)){
            BdcSlSfssDdxxDO bdcSlSfssDdxxDO = bdcSlSfssDdxxDOList.get(0);
            if(BdcSfztEnum.YJF.key().equals(bdcSlSfssDdxxDO.getJfzt())){
                List<String> sfxxidList = this.bdcYjdhSfxxGxService.queryYjSfxxidList(yjdh);
                if(CollectionUtils.isNotEmpty(sfxxidList)){
                    BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
                    bdcSlSfxxQO.setSfzt(BdcSfztEnum.YJF.key());
                    //当缴款方式为合一支付时更新缴费状态，缴库状态以及缴费书编码
                    if (Objects.equals(CommonConstantUtils.JKTJ_HYZF, bdcSlSfssDdxxDO.getHyzfjftj()) && StringUtils.isNotBlank(bdcSlSfssDdxxDO.getJkm())){
                        bdcSlSfxxQO.setYhjkrkzt(CommonConstantUtils.SF_S_DM);
                        bdcSlSfxxQO.setJfsbm(bdcSlSfssDdxxDO.getJkm());
                    }
                    if(sfxxidList.size() > 500){
                        List<List> subList = ListUtils.subList(sfxxidList, 500);
                        for (List data : subList) {
                            List copyList = new ArrayList(data);
                            bdcSlSfxxQO.setSfxxidList(copyList);
                            this.bdcSlSfxxService.batchUpdateBdcSlSfxxBySfxxidList(bdcSlSfxxQO);
                        }
                    }else{
                        bdcSlSfxxQO.setSfxxidList(sfxxidList);
                        this.bdcSlSfxxService.batchUpdateBdcSlSfxxBySfxxidList(bdcSlSfxxQO);
                    }
                }
            }
        }

    }

    /**
     * 更新月结收费订单信息状态
     * @param yjdh  月结单号
     */
    private void modifyYjSfDdxxDdzt(String yjdh, Integer jfzt){
        if(StringUtils.isNotBlank(yjdh)){
            BdcYjSfDdxxDO bdcYjSfDdxxDO = new BdcYjSfDdxxDO();
            bdcYjSfDdxxDO.setYjdh(yjdh);
            bdcYjSfDdxxDO.setDdzt(jfzt);
            bdcYjSfDdxxDO.setZtxgsj(new Date());
            this.bdcYjSfDdxxService.updateYjSfDdxxByYjdh(bdcYjSfDdxxDO);
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 判断是否已经全部缴费成功，成功即自动缴库入库
     */
    private void autoJkrk(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    if (!CommonConstantUtils.SFZT_YJF.equals(bdcSlSfxxDO.getSfzt())) {
                        LOGGER.info("未缴费成功,不自动缴库，工作流实例ID:{}", gzlslid);
                        return;

                    }
                }
            }
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxxBySlbh(bdcXmDTOList.get(0).getSlbh());
                if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                    for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
                        if (!CommonConstantUtils.SFZT_YJF.equals(bdcSlHsxxDO.getJfzt())) {
                            LOGGER.info("未缴税成功,不自动缴库，工作流实例ID:{}", gzlslid);
                            return;

                        }
                    }
                }
                //推送缴库入库
                tsjkrk(bdcXmDTOList.get(0).getXmid(), bdcXmDTOList.get(0).getSlbh());
            }
        }
    }

    /**
     * @param xmid 项目ID
     * @param slbh 受理编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 推送缴库入库
     */
    private void tsjkrk(String xmid, String slbh) {
        Map paramMap = new HashMap();
        paramMap.put("xmid", xmid);
        paramMap.put("slbh", slbh);
        Object response = exchangeInterfaceFeignService.requestInterface("getSysAndJkrk", paramMap);
        LOGGER.info("缴库接口返回值：{},对应受理编号：{}", response, slbh);
    }

    private void updateSfztAndHsjfzt(List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList, String qlrlb, String gzlslid, String xmid, Integer hyzf) {
        if (CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)) {
            Double sfssze = 0.00;
            //获取待缴总金额
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByXmid(xmid);
            if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
                bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
            }
            // 获取待缴税额
            BdcSlHsxxDO bdcSlHsxxQO = new BdcSlHsxxDO();
            bdcSlHsxxQO.setSqrlb(qlrlb);
            bdcSlHsxxQO.setXmid(xmid);
            List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxx(bdcSlHsxxQO);
            if(CollectionUtils.isEmpty(bdcSlHsxxDOList)){
                bdcSlHsxxDOList = this.bdcSlHsxxService.listBdcSlHsxxByGzlslidAndSqrlb(gzlslid, qlrlb);
            }

            //排除月结
            List<BdcSlSfxxDO> sfxxDOList = bdcSlSfxxDOList.stream().filter(bdcSlSfxxDO ->
                    StringUtils.equals(qlrlb, bdcSlSfxxDO.getQlrlb()) &&!CommonConstantUtils.SF_S_DM.equals(bdcSlSfxxDO.getSfyj())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(sfxxDOList)) {
                sfssze += sfxxDOList.stream().filter(sfxxDO -> sfxxDO.getHj() != null).mapToDouble(BdcSlSfxxDO::getHj).sum();
            }
            if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                sfssze += bdcSlHsxxDOList.stream().filter(hsxxDO -> hsxxDO.getSjyzhj() != null).mapToDouble(BdcSlHsxxDO::getSjyzhj).sum();
            }
            sfssze = NumberUtil.formatDigit(sfssze, 2);
            //获取已缴费总金额
            int zdzt = CommonConstantUtils.SFZT_WJF;
            Double ze = bdcSlSfssDdxxDOList.stream().filter(ddxx -> ddxx.getDdje() != null && BdcSfztEnum.YJF.key().equals(ddxx.getJfzt())).mapToDouble(BdcSlSfssDdxxDO::getDdje).sum();
            String jkm = bdcSlSfssDdxxDOList.get(0).getJkm();
            ze = NumberUtil.formatDigit(ze, 2);
            if (!ze.equals((double) 0)) {
                if (ze < sfssze) {
                    zdzt = CommonConstantUtils.SFZT_BFJF;
                } else {
                    zdzt = CommonConstantUtils.SFZT_YJF;
                }
            }
            //更新收费
            if (CollectionUtils.isNotEmpty(sfxxDOList)) {
                for (BdcSlSfxxDO bdcSlSfxxDO : sfxxDOList) {
                    bdcSlSfxxDO.setSfzt(zdzt);
                    //当缴款方式为合一支付时更新缴费状态，缴库状态以及缴费书编码
                    if (Objects.equals(CommonConstantUtils.JKTJ_HYZF, hyzf) && 2 == zdzt && StringUtils.isNotBlank(jkm) ){
                        bdcSlSfxxDO.setJfsbm(jkm);
                        bdcSlSfxxDO.setYhjkrkzt(1);
                    }
                    //缴款方式默认线上缴费
                    bdcSlSfxxDO.setJkfs(CommonConstantUtils.JKFS_XSJF);
                    bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                }
            }
            //更新收税
            if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
                    bdcSlHsxxDO.setJfzt(zdzt);
                    //当缴款方式为合一支付时更新税务的缴费状态，缴库状态
                    if (Objects.equals(CommonConstantUtils.JKTJ_HYZF, hyzf) && 2 == zdzt){
                        bdcSlHsxxDO.setYhjkrkzt(1);
                    }
                    bdcSlHsxxService.updateBdcSlHsxx(bdcSlHsxxDO);
                }
            }
        }
    }

    // 调用Exchange查询退款状态,并更改传入 tkddxx 的jfzt字段
    private void requestTkztcx(String slbh, String xmid, BdcSlSfssDdxxDO tkddxx){
        Map paramMap = new HashMap();
        paramMap.put("slbh", slbh);
        paramMap.put("xmid", xmid);
        paramMap.put("tkdh", tkddxx.getTkdh());
        Object response = exchangeInterfaceFeignService.requestInterface("wwsq_ykqtkztcx", paramMap);
        LOGGER.info("受理编号:{},退款结果查询接口调用成功,返回结果：{}", slbh, response);
        if(null != response){
            Map responesMap = (Map) response;
            if(responesMap.containsKey("sfzt") && null!=responesMap.get("sfzt")){
                tkddxx.setJfzt(Integer.parseInt(responesMap.get("sfzt").toString()));
            }
        }
    }

    // 获取主表的退款状态
    private Integer getZbTkzt(List<BdcSlSfssDdxxDO> ddxxList){
        List<Integer> tkztList = ddxxList.stream().map(t->t.getJfzt()).collect(Collectors.toList());
        if(tkztList.contains(BdcSfztEnum.TKZ.key())){
            return BdcSfztEnum.TKZ.key();
        }
        if(tkztList.contains(BdcSfztEnum.TKSB.key())){
            return BdcSfztEnum.TKSB.key();
        }
        if(tkztList.contains(BdcSfztEnum.TKCG.key())){
            return BdcSfztEnum.TKCG.key();
        }
        return null;
    }

}
