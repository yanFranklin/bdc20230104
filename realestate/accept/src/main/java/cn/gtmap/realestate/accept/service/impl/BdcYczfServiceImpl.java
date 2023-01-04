package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlHsxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlSfssDdxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlSfxmService;
import cn.gtmap.realestate.accept.core.service.BdcSlSfxxService;
import cn.gtmap.realestate.accept.service.BdcDdxxAbstractService;
import cn.gtmap.realestate.accept.service.BdcYczfService;
import cn.gtmap.realestate.accept.service.impl.ddxx.BdcDdxxFactory;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfssDdxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlHsxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfssDdxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcYczfVO;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BdcYczfServiceImpl implements BdcYczfService {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYczfServiceImpl.class);

    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcSlSfxmService bdcSlSfxmService;
    @Autowired
    BdcSlHsxxService bdcSlHsxxService;
    @Autowired
    BdcSlSfxxService bdcSlSfxxService;
    @Autowired
    BdcSlSfssDdxxService bdcSlSfssDdxxService;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcDdxxFactory bdcDdxxFactory;

    @Autowired
    RedisUtils redisUtils;
    /**
     * 订单服务厂商，默认为jsyh，其余为ykq 用于区分不同的厂商接口，调用不同的实现类
     */
    @Value("${ddfw.cs:jsyh}")
    private String ddxxcs;

    /**
     * 一次支付订单失效时间，默认30分钟
     */
    @Value("${yczf.sczfdd.timeout:1800}")
    private Long sczfddTimeout;

    /**
     * 是否验证缴费书编码， 默认： true 验证， false 不验证
     * <p>验证： 收费信息缴费书编码是否为空，为空时推送建行生成订单时，不生成缴费信息</p>
     * <p>不验证： 收费信息缴费书编码是否为空，为空时推送建行生成订单时，也生成缴费信息</p>
     */
    @Value("${yczf.sczfdd.sfyzjfsbm:true}")
    private boolean sfyzjfsbm;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object sczfdd(String gzlslid, String lx, String qlrlb) {
        if(StringUtils.isAnyBlank(gzlslid, lx, qlrlb)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数：工作流实例ID、生成订单类型或权利人类别");
        }
        BdcDdxxAbstractService bdcDdxxAbstractService = this.bdcDdxxFactory.getDdxxService(ddxxcs);
        if(null == bdcDdxxAbstractService){
            throw new AppException(ErrorCode.MISSING_ARG, "未获到"+ddxxcs+"厂商的接口实现类。");
        }
        // 检查redis中是否已存在订单信息
        String key = "yczf_sczfdd_" + gzlslid + "_" + qlrlb;
        String payOrderNo = redisUtils.getStringValue(key);
        if (StringUtils.isNotBlank(payOrderNo)) {
            throw new AppException(ErrorCode.ENTITY_EXISTS, "订单已生成，请等待订单过期再重新生成，过期时间:" + sczfddTimeout/60 + "分钟");
        }
        // 如果存在已有订单，先作废原有一次支付订单信息
        BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
        bdcSlSfssDdxxQO.setGzlslid(gzlslid);
        bdcSlSfssDdxxQO.setQlrlb(qlrlb);
        zfYczfDdxx(bdcSlSfssDdxxQO);
        // 生成新的订单
        Object result = bdcDdxxAbstractService.sczfdd(gzlslid, lx, qlrlb);
        Map<String, String> resultMap = (Map<String, String>) result;
        // 将订单编号存在redis
        payOrderNo = resultMap.get("payOrderNo");
        redisUtils.addStringValue(key, payOrderNo, sczfddTimeout);
        return result;
    }

    @Override
    public CommonResponse cxjfjg(String gzlslid, String qlrlb) {
        if(StringUtils.isAnyBlank(gzlslid, qlrlb)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数：工作流实例ID或权利人类别。");
        }
        BdcDdxxAbstractService bdcDdxxAbstractService = this.bdcDdxxFactory.getDdxxService(ddxxcs);
        if(null == bdcDdxxAbstractService){
            throw new AppException(ErrorCode.MISSING_ARG, "未获到"+ddxxcs+"厂商的接口实现类。");
        }
        return bdcDdxxAbstractService.cxjfjg(gzlslid,qlrlb);
    }

    /**
     * 根据工作流实例ID和权利人类别查询收费收费订单信息
     */
    private BdcSlSfssDdxxDO queryBdcSlSfssDdxx(String gzlslid, String qlrlb){
        BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
        bdcSlSfssDdxxQO.setGzlslid(gzlslid);
        bdcSlSfssDdxxQO.setQlrlb(qlrlb);
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = this.bdcSlSfssDdxxService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
        if(CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)){
            return bdcSlSfssDdxxDOList.get(0);
        }
        return null;
    }

    @Override
    public BdcYczfVO queryBdcYczfSfssxx(String gzlslid, String qlrlb) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        // 组合流程获取房地产权的登记费信息
        int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        String cqXmid = "";
        if (lclx == 2 || lclx == 4) {
            List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                for(BdcXmDTO bdcXmDTO : bdcXmDTOList){
                    if(ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, bdcXmDTO.getQllx())){
                        cqXmid = bdcXmDTO.getXmid();
                    }
                }
            }
        }

        BdcYczfVO bdcYczfVO = new BdcYczfVO();
        if(StringUtils.isBlank(qlrlb)){
            // 权利人类别为空时，查询权利人与义务人的收费、收税信息
            bdcYczfVO.setBdcQlrSftjxxDTO(this.getYczfSfssxx(gzlslid, cqXmid, CommonConstantUtils.QLRLB_QLR));
            bdcYczfVO.setBdcYwrSftjxxDTO(this.getYczfSfssxx(gzlslid, cqXmid, CommonConstantUtils.QLRLB_YWR));
        }else{
            if(StringUtils.equals(CommonConstantUtils.QLRLB_QLR, qlrlb)){
                bdcYczfVO.setBdcQlrSftjxxDTO(this.getYczfSfssxx(gzlslid, cqXmid, CommonConstantUtils.QLRLB_QLR));
            }
            if(StringUtils.equals(CommonConstantUtils.QLRLB_YWR, qlrlb)){
                bdcYczfVO.setBdcYwrSftjxxDTO(this.getYczfSfssxx(gzlslid, cqXmid, CommonConstantUtils.QLRLB_YWR));
            }
        }
        return bdcYczfVO;
    }

    private  BdcSqrSftjxxDTO getYczfSfssxx(String gzlslid, String xmid, String qlrlb){
        BdcSqrSftjxxDTO bdcSqrSftjxxDTO = new BdcSqrSftjxxDTO();
        bdcSqrSftjxxDTO.setQlrlb(qlrlb);
        // 获取权利人名称和电话
        List<BdcQlrDO> bdcQlrDoList = new ArrayList<>();
        if (StringUtils.isBlank(xmid)) {
            bdcQlrDoList = bdcQlrFeignService.listAllBdcQlr(gzlslid, qlrlb, null);
        } else {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            bdcQlrQO.setQlrlb(qlrlb);
            bdcQlrDoList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        }

        if (CollectionUtils.isNotEmpty(bdcQlrDoList)) {
            String qlrmc = StringToolUtils.resolveBeanToAppendStr(bdcQlrDoList, "getQlrmc", ",");
            String qlrzjh = StringToolUtils.resolveBeanToAppendStr(bdcQlrDoList, "getZjh", ",");
            bdcSqrSftjxxDTO.setQlrmc(qlrmc);
            bdcSqrSftjxxDTO.setZjh(qlrzjh);
            bdcSqrSftjxxDTO.setLxdh(bdcQlrDoList.get(0).getDh());
        }
        // 计算收费信息
        // 单个、批量流程取一条收费信息，组合、批量组合流程获取产权收费信息
        BdcSfxxDTO bdcSfxxDTO = this.queryBdcSlSfxxSfxm(gzlslid, xmid, qlrlb);
        List<BdcSftjDTO> bdcSftjDTOList = new ArrayList<>(2);
        if (Objects.nonNull(bdcSfxxDTO) && Objects.nonNull(bdcSfxxDTO.getBdcSlSfxxDO())) {
            String jfsbm = this.getBdcSlSfxxJfsbm(bdcSfxxDTO);
            // 判断是否验证缴费书编码, 验证：则缴费书编码不能为空， 不验证：则缴费书编码能为空
            boolean jfsbmyz = sfyzjfsbm ? StringUtils.isNotBlank(jfsbm) : true;
            if(jfsbmyz){
                BigDecimal hj = BigDecimal.valueOf(Optional.ofNullable(bdcSfxxDTO.getBdcSlSfxxDO().getHj()).orElse(0.00));
                BdcSftjDTO bdcSftjDTO = new BdcSftjDTO();
                bdcSftjDTO.setSfxmdm("登记费");
                bdcSftjDTO.setYsje(NumberUtil.formatDigit(hj.doubleValue(), 2));
                bdcSftjDTO.setSfzt(bdcSfxxDTO.getBdcSlSfxxDO().getSfzt());
                bdcSftjDTO.setJfsbm(jfsbm);
                if(CollectionUtils.isNotEmpty(bdcSfxxDTO.getBdcSlSfxmDOS())){
                    bdcSftjDTO.setBdcSlSfxmDOList(bdcSfxxDTO.getBdcSlSfxmDOS());
                }
                bdcSftjDTOList.add(bdcSftjDTO);
            }
        }

        // 计算收税信息
        List<BdcSlHsxxDO> bdcSlHsxxDOList = this.queryBdcSlHsxx(gzlslid, xmid, qlrlb);
        if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
            for(BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList){
                BigDecimal sjyzhj = BigDecimal.valueOf(Optional.ofNullable(bdcSlHsxxDO.getSjyzhj()).orElse(0.00));
                BdcSftjDTO bdcSftjDTO = new BdcSftjDTO();
                bdcSftjDTO.setSfxmdm("税费");
                bdcSftjDTO.setSfzt(bdcSlHsxxDO.getWszt());
                bdcSftjDTO.setYsje(NumberUtil.formatDigit(sjyzhj.doubleValue(), 2));
                bdcSftjDTO.setNsrsbh(bdcSlHsxxDO.getNsrsbh());
                bdcSftjDTO.setDzsphm(bdcSlHsxxDO.getSphm());
                bdcSftjDTO.setSkssjgdm(bdcSlHsxxDO.getSwjgdm());
                bdcSftjDTOList.add(bdcSftjDTO);
            }
        }

        bdcSqrSftjxxDTO.setBdcSftjDTOList(bdcSftjDTOList);

        // 获取税费统缴订单信息
        BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
        bdcSlSfssDdxxQO.setGzlslid(gzlslid);
        bdcSlSfssDdxxQO.setQlrlb(qlrlb);
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = this.bdcSlSfssDdxxService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
        if(CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)){
            bdcSqrSftjxxDTO.setBdcSlSfssDdxxDOList(bdcSlSfssDdxxDOList);
        }
        return bdcSqrSftjxxDTO;
    }

    /**
     * 获取缴费书编码，先获取收费信息中的jfsbm, 没有获取到则从收费项目中获取jfsbm
     */
    private String getBdcSlSfxxJfsbm(BdcSfxxDTO bdcSfxxDTO){
        if(StringUtils.isNotBlank(bdcSfxxDTO.getBdcSlSfxxDO().getJfsbm())){
            return bdcSfxxDTO.getBdcSlSfxxDO().getJfsbm();
        }
        List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSfxxDTO.getBdcSlSfxmDOS();
        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
            if(StringUtils.isNotBlank(bdcSlSfxmDOList.get(0).getJfsbm())){
                return bdcSlSfxmDOList.get(0).getJfsbm();
            }
        }
        return "";
    }

    /**
     * 单个、批量流程取一条收费信息，组合、批量组合流程获取产权收费信息
     */
    private BdcSfxxDTO queryBdcSlSfxxSfxm(String gzlslid, String xmid, String qlrlb){
        BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
        if(StringUtils.isNotBlank(gzlslid)){
            bdcSlSfxxQO.setGzlslid(gzlslid);
        }
        if(StringUtils.isNotBlank(xmid)){
            bdcSlSfxxQO.setXmid(xmid);
        }
        bdcSlSfxxQO.setQlrlb(qlrlb);
        List<BdcSfxxDTO> bdcSfxxDTOList = this.bdcSlSfxxService.queryBdcSlSfxxAndSfxm(bdcSlSfxxQO);
        if(CollectionUtils.isNotEmpty(bdcSfxxDTOList)){
            return bdcSfxxDTOList.get(0);
        }
        return null;
    }

    /**
     * 根据工作流实例ID、项目ID、权利人类别 查询收税信息
     */
    private List<BdcSlHsxxDO> queryBdcSlHsxx(String gzlslid, String xmid, String qlrlb){
        List<BdcSlHsxxDO> bdcSlHsxxDOList = new ArrayList<>(10);
        if(StringUtils.isBlank(xmid)){
            bdcSlHsxxDOList = this.bdcSlHsxxService.listBdcSlHsxxByGzlslidAndSqrlb(gzlslid, qlrlb);
        }else{
            BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
            bdcSlHsxxQO.setXmid(xmid);
            bdcSlHsxxQO.setSqrlb(qlrlb);
            bdcSlHsxxDOList = this.bdcSlHsxxService.listBdcSlHsxxByHsxxQo(bdcSlHsxxQO);
        }
        // 过滤核税信息数据，只获取核税信息为空或核税信息为0的数据
        if(CollectionUtils.isNotEmpty(bdcSlHsxxDOList)){
            bdcSlHsxxDOList = bdcSlHsxxDOList.stream()
                    .filter(t -> StringUtils.isBlank(t.getHsxxlx()) || StringUtils.equals("0", t.getHsxxlx()))
                    .collect(Collectors.toList());
        }
        return bdcSlHsxxDOList;
    }


    @Override
    public CommonResponse posZfcgtz(String gzlslid, String qlrlb) {
        if(StringUtils.isAnyBlank(gzlslid, qlrlb)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数：工作流实例ID或权利人类别");
        }
        BdcSlSfssDdxxDO bdcSlSfssDdxxDO = this.queryBdcSlSfssDdxx(gzlslid, qlrlb);
        if(Objects.isNull(bdcSlSfssDdxxDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到一次支付订单信息");
        }

        Map<String, Object> map = new HashMap<>(3);
        map.put("ittPartyJrnlNo", UUIDGenerator.generate16());
        map.put("pyOrdrNo", bdcSlSfssDdxxDO.getDsfddbh());
        map.put("tAmt", bdcSlSfssDdxxDO.getDdje());
        LOGGER.info("调用政融支付平台支付成功通知接口，请求参数：{}", map.toString());
        Object response = exchangeInterfaceFeignService.requestInterface("zr_posPaySuccess", map);
        LOGGER.info("调用政融支付平台支付成功通知接口，返回值信息：{}", response.toString());
        if (Objects.nonNull(response)) {
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(response));
            String zfzt = object.getString("PsRlt_Cd");
            if(StringUtils.equals("1", zfzt)){
                this.modifyHsxxSfxxAndDdxxZt(gzlslid, qlrlb, BdcSfztEnum.YJF.key(), null);
                return CommonResponse.ok();
            }
        }
        return CommonResponse.fail("通知政融平台支付成功失败");
    }

    @Override
    public CommonResponse xstksq(String gzlslid, String qlrlb) {
        if(StringUtils.isAnyBlank(gzlslid, qlrlb)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数：工作流实例ID或权利人类别");
        }
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcXmDTOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产项目信息");
        }
        BdcSlSfssDdxxDO bdcSlSfssDdxxDO = this.queryBdcSlSfssDdxx(gzlslid, qlrlb);
        if(Objects.isNull(bdcSlSfssDdxxDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到一次支付订单信息");
        }
        Map<String, Object> map = new HashMap<>(3);
        map.put("ittPartyJrnlNo", UUIDGenerator.generate16());
        map.put("pyOrdrNo", bdcSlSfssDdxxDO.getDsfddbh());
        map.put("cstPtyCmdtyOrdrNo",bdcSlSfssDdxxDO.getDdbh());
        map.put("rfndAmt", bdcSlSfssDdxxDO.getDdje());
        LOGGER.info("调用政融支付平台退款申请接口，请求参数：{}", map.toString());
        Object response = exchangeInterfaceFeignService.requestInterface("zr_xstksq", map);
        LOGGER.info("调用政融支付平台退款申请接口，返回值信息：{}", response.toString());
        if (Objects.nonNull(response)) {
            // 更新订单信息，退款单号
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(response));
            String tkdh = object.getString("Rfnd_TrcNo");
            String tkzt = object.getString("Rfnd_StCd");
            String tkje = object.getString("Rfnd_Amt");

            Map<String, String> param = new HashMap<>(3);
            param.put("tkdh", tkdh);
            param.put("tkzt", tkzt);
            param.put("tkje", tkje);

            bdcSlSfssDdxxDO.setTkdh(tkdh);
            bdcSlSfssDdxxDO.setDdzt(Integer.valueOf(tkzt));
            this.bdcSlSfssDdxxService.updateBdcSlSfssDdxx(bdcSlSfssDdxxDO);
            return CommonResponse.ok(param);
        }
        return CommonResponse.fail("退款申请失败");
    }

    @Override
    public CommonResponse xstkjgcx(String gzlslid, String qlrlb) {
        if(StringUtils.isAnyBlank(gzlslid, qlrlb)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数：工作流实例ID或权利人类别");
        }
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcXmDTOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产项目信息");
        }
        BdcSlSfssDdxxDO bdcSlSfssDdxxDO = this.queryBdcSlSfssDdxx(gzlslid, qlrlb);
        if(Objects.isNull(bdcSlSfssDdxxDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到一次支付订单信息");
        }
        Map<String, Object> map = new HashMap<>(3);
        map.put("ittPartyJrnlNo", UUIDGenerator.generate16());
        map.put("rfndTrcNo", bdcSlSfssDdxxDO.getTkdh());
        LOGGER.info("调用政融支付平台退款结果查询接口，请求参数：{}", map.toString());
        Object response = exchangeInterfaceFeignService.requestInterface("zr_xstkjgcx", map);
        LOGGER.info("调用政融支付平台退款结果查询接口，返回值信息：{}", response.toString());
        if (Objects.nonNull(response)) {
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(response));
            String zddList = object.getString("LIST1");
            List<JSONObject> jsonArray = JSONArray.parseArray(zddList, JSONObject.class);
            if(!jsonArray.isEmpty()){
                for(JSONObject obj: jsonArray){
                    if(!obj.isEmpty()){
                        if(StringUtils.equals(bdcSlSfssDdxxDO.getDdbh(), obj.getString("Py_Sub_Ordr_No"))){
                            bdcSlSfssDdxxDO.setDdzt(Integer.valueOf(obj.getString("Sub_Ordr_Rfnd_StCd")));
                            this.bdcSlSfssDdxxService.updateBdcSlSfssDdxx(bdcSlSfssDdxxDO);
                        }
                    }
                }
                return CommonResponse.ok();
            }
        }
        return CommonResponse.fail("调用政融支付平台退款结果查询返回值为空");
    }

    private void modifyHsxxSfxxAndDdxxZt(String gzlslid, String qlrlb, Integer zfzt, Integer ddzt){
        LOGGER.info("更新支付状态：{}，订单状态：{}", zfzt, ddzt);
        // 支付状态 和 订单状态都为空时，不执行更新操作
        if(Objects.isNull(zfzt) && Objects.isNull(ddzt)){
            return;
        }
        // 更新核税信息完税状态
        {
            List<BdcSlHsxxDO> bdcSlHsxxDOList = this.queryBdcSlHsxx(gzlslid, null, qlrlb);
            if(CollectionUtils.isNotEmpty(bdcSlHsxxDOList)){
                for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
                    if(Objects.nonNull(zfzt)){
                        bdcSlHsxxDO.setJfzt(zfzt);
                        if(Objects.equals(BdcSfztEnum.YJF.key(), zfzt)){
                            bdcSlHsxxDO.setWszt(CommonConstantUtils.SF_S_DM);
                        }else{
                            bdcSlHsxxDO.setWszt(CommonConstantUtils.SF_F_DM);
                        }
                    }
                }
                entityMapper.batchSaveSelective(bdcSlHsxxDOList);
            }
        }
        { // 更新收费信息表 收费状态
            BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
            bdcSlSfxxQO.setGzlslid(gzlslid);
            bdcSlSfxxQO.setQlrlb(qlrlb);
            List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxService.queryBdcSlSfxx(bdcSlSfxxQO);
            if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)){
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    if(Objects.nonNull(zfzt)){
                        Integer sfzt = BdcSfztEnum.WJF.key();
                        if(Objects.equals(BdcSfztEnum.YJF.key(), zfzt)){
                            sfzt = BdcSfztEnum.YJF.key();
                        }

                        boolean sfxxJfsbmYz = sfyzjfsbm ? StringUtils.isNotBlank(bdcSlSfxxDO.getJfsbm()) : true;
                        if(sfxxJfsbmYz){
                            bdcSlSfxxDO.setSfzt(sfzt);
                            this.entityMapper.updateByPrimaryKey(bdcSlSfxxDO);
                        }
                        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
                            for(BdcSlSfxmDO bdcSlSfxmDO: bdcSlSfxmDOList){
                                boolean sfxmJfsbmYz = sfyzjfsbm ? StringUtils.isNotBlank(bdcSlSfxmDO.getJfsbm()) : true;
                                if(sfxmJfsbmYz){
                                    bdcSlSfxmDO.setSfzt(sfzt);
                                }
                            }
                            this.entityMapper.batchSaveSelective(bdcSlSfxmDOList);
                        }
                    }
                }
            }
        }
        { // 更新订单信息状态
            BdcSlSfssDdxxDO bdcSlSfssDdxxDO = this.queryBdcSlSfssDdxx(gzlslid, qlrlb);
            if(Objects.nonNull(zfzt)){
                if(Objects.equals(BdcSfztEnum.YJF.key(), zfzt)){
                    bdcSlSfssDdxxDO.setJfzt(BdcSfztEnum.YJF.key());
                }else{
                    bdcSlSfssDdxxDO.setJfzt(BdcSfztEnum.WJF.key());
                }
            }
            if(Objects.nonNull(ddzt)){
                bdcSlSfssDdxxDO.setDdzt(ddzt);
            }
            this.bdcSlSfssDdxxService.updateBdcSlSfssDdxx(bdcSlSfssDdxxDO);
        }
    }

    /**
     * @description 作废一次支付订单信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/11/11 16:13
     * @param bdcSlSfssDdxxQO
     * @return void
     */
    private void zfYczfDdxx(BdcSlSfssDdxxQO bdcSlSfssDdxxQO){
        if(StringUtils.isAnyBlank(bdcSlSfssDdxxQO.getGzlslid(), bdcSlSfssDdxxQO.getQlrlb())){
            throw new AppException("作废订单缺失参数：工作流实例ID或权利人类别");
        }
        // 获取订单信息内容
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = bdcSlSfssDdxxService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
        if (CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)) {
            // 验证订单是否已完成缴费
            List<BdcSlSfssDdxxDO> yjfDdxxList = bdcSlSfssDdxxDOList.stream()
                    .filter(t -> CommonConstantUtils.SFZT_BFJF.equals(t.getJfzt()) ||CommonConstantUtils.SFZT_YJF.equals(t.getJfzt())).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(yjfDdxxList)){
                throw new AppException("存在已缴费订单,不允许生成订单。");
            }
            LOGGER.info("删除原有一次支付订单信息，工作流实例ID：{}，权利人类别：{}", bdcSlSfssDdxxQO.getGzlslid(), bdcSlSfssDdxxQO.getQlrlb());
            //删除订单信息
            bdcSlSfssDdxxService.deleteBdcSlSfssDdxx(bdcSlSfssDdxxQO);
        }
    }

}
