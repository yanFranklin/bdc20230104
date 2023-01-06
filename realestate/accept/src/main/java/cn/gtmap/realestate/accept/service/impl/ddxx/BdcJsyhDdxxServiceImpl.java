package cn.gtmap.realestate.accept.service.impl.ddxx;

import cn.gtmap.realestate.accept.core.service.BdcSlHsxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlSfssDdxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlSfxmService;
import cn.gtmap.realestate.accept.core.service.BdcSlSfxxService;
import cn.gtmap.realestate.accept.service.BdcDdxxAbstractService;
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
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/11/3
 * @description 不动产建设银行订单信息服务实现类
 */
@Service(value = "jsyh")
public class BdcJsyhDdxxServiceImpl implements BdcDdxxAbstractService {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcJsyhDdxxServiceImpl.class);

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

    /**
     * 是否区分卖方税和买方税的费项代码
     */
    @Value("${yczf.sczfdd.sfqffxdm:false}")
    private Boolean sfqffxdm;

    /**
     * 是否验证缴费书编码， 默认： true 验证， false 不验证
     * <p>验证： 收费信息缴费书编码是否为空，为空时推送建行生成订单时，不生成缴费信息</p>
     * <p>不验证： 收费信息缴费书编码是否为空，为空时推送建行生成订单时，也生成缴费信息</p>
     */
    @Value("${yczf.sczfdd.sfyzjfsbm:true}")
    private boolean sfyzjfsbm;

    @Override
    public Object sczfdd(String gzlslid, String lx, String qlrlb) {
        Map<String, String> result = new HashMap<>();
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            BdcXmDTO bdcXmDTO = bdcXmDTOList.get(0);
            ZrScddDTO zrScddDTO = new ZrScddDTO();
            zrScddDTO.setFqsj(DateUtils.formateTime(new Date(), DateUtils.DATE_FORMATYMDHMSF));
            // 类型 1:扫码下单,2:pos下单
            zrScddDTO.setFqly(lx);
            //  lx=2-线下支付(pos，现金)时，操作员号必填
            if ("2".equals(lx)) {
                zrScddDTO.setCzyh(lx);
            }
            zrScddDTO.setSlbh(bdcXmDTO.getSlbh());
            // 客户订单号
            String ddbh = UUIDGenerator.generate16();
            zrScddDTO.setKhddh(ddbh);

            // 根据权利人类别获取税费统缴信息
            BdcSqrSftjxxDTO bdcSqrSftjxxDTO = this.getSqrSftjxx(gzlslid, qlrlb);
            BigDecimal zje = new BigDecimal("0.00");
            if(Objects.nonNull(bdcSqrSftjxxDTO) && CollectionUtils.isNotEmpty(bdcSqrSftjxxDTO.getBdcSftjDTOList())){
                List<ZrScddTaxDTO> taxDTOS = new ArrayList<>(1);
                List<ZrScddYbDTO> ybDTOList = new ArrayList<>(10);
                int sfsxh = 1;
                for (BdcSftjDTO bdcSftjDTO : bdcSqrSftjxxDTO.getBdcSftjDTOList()) {
                    if ("税费".equals(bdcSftjDTO.getSfxmdm()) && bdcSftjDTO.getYsje() >0) {
                        zje = zje.add(BigDecimal.valueOf(bdcSftjDTO.getYsje()));
                        ZrScddTaxDTO zrScddYbDTO = new ZrScddTaxDTO();
                        zrScddYbDTO.setSn(String.valueOf(sfsxh++));
                        if (sfqffxdm) {
                            zrScddYbDTO.setQxdm(bdcXmDTO.getQxdm() + "_" + qlrlb);
                        } else {
                            zrScddYbDTO.setQxdm(bdcXmDTO.getQxdm());
                        }
                        zrScddYbDTO.setJe(BigDecimal.valueOf(bdcSftjDTO.getYsje()));
                        zrScddYbDTO.setDzsph(bdcSftjDTO.getDzsphm());
                        zrScddYbDTO.setClAhrCd(bdcSftjDTO.getSkssjgdm());
                        zrScddYbDTO.setNsrsbh(bdcSftjDTO.getNsrsbh());
                        taxDTOS.add(zrScddYbDTO);
                    }
                    if("登记费".equals(bdcSftjDTO.getSfxmdm()) && CollectionUtils.isNotEmpty(bdcSftjDTO.getBdcSlSfxmDOList())){
                        // 判断是否验证缴费书编码, 验证：则缴费书编码不能为空， 不验证：则缴费书编码能为空
                        boolean jfsbmyz = sfyzjfsbm ? StringUtils.isNotBlank(bdcSftjDTO.getJfsbm()) : true;
                        if(jfsbmyz){
                            zje = zje.add(BigDecimal.valueOf(bdcSftjDTO.getYsje()));
                            int sn = 1;
                            for(BdcSlSfxmDO bdcSlSfxmDO : bdcSftjDTO.getBdcSlSfxmDOList()){
                                ZrScddYbDTO zrScddYbDTO = new ZrScddYbDTO();
                                zrScddYbDTO.setSn(String.valueOf(sn++));
                                zrScddYbDTO.setJe(BigDecimal.valueOf(Optional.ofNullable(bdcSlSfxmDO.getSsje()).orElse(0.00)));
                                zrScddYbDTO.setQxdm(bdcXmDTO.getQxdm());
                                zrScddYbDTO.setSfxmmc(bdcSftjDTO.getSfxmdm());
                                zrScddYbDTO.setJfsbm(bdcSftjDTO.getJfsbm());
                                ybDTOList.add(zrScddYbDTO);
                            }
                        }
                    }
                }
                zrScddDTO.setZje(zje);
                zrScddDTO.setTaxList(taxDTOS);
                zrScddDTO.setYbfyList(ybDTOList);
            }
            Object response = exchangeInterfaceFeignService.requestInterface("zr_payOrder", zrScddDTO);
            if (Objects.nonNull(response)) {
                JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(response));
                String payOrderNo = object.getString("Py_Ordr_No");
                String payUrl = object.getString("Py_URL");
                result.put("payOrderNo", payOrderNo);
                result.put("payUrl", payUrl);
                this.saveBdcSlSfssDdxx(object, ddbh, gzlslid, bdcXmDTOList.get(0).getXmid(), qlrlb, zrScddDTO);
            }
        }
        return result;
    }

    @Override
    public CommonResponse cxjfjg(String gzlslid, String qlrlb) {
        if(StringUtils.isAnyBlank(gzlslid, qlrlb)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数：工作流实例ID或权利人类别。");
        }
        BdcSlSfssDdxxDO bdcSlSfssDdxxDO = this.queryBdcSlSfssDdxx(gzlslid, qlrlb, CommonConstantUtils.DDXX_DDLX_SFTJ);
        if(Objects.isNull(bdcSlSfssDdxxDO)){
            throw new AppException(ErrorCode.CUSTOM, "未获取到一次支付订单信息。");
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("cmstyOrdrNo", bdcSlSfssDdxxDO.getDdbh());
        map.put("pyOrdeNo",bdcSlSfssDdxxDO.getDsfddbh());
        Object response = exchangeInterfaceFeignService.requestInterface("zr_payResult", map);
        LOGGER.info("调用建行查询缴费状态接口，返回值信息：{}", JSON.toJSONString(response));
        if (Objects.nonNull(response)) {
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(response));
            String zfzt = object.getString("Py_StCd");
            String ddzt = object.getString("Ordr_StCd");
            // 建行接口返回支付状态代码值：1待支付、2成功、3失败、4不确定
            // 订单状态返回值代码：1-待缴费、2-成功、3-失败 、4-全部退费、5-部分退费、6-失效 、9-取消
            if (StringUtils.isNotBlank(zfzt)) {
                Integer state = BdcSfztEnum.WJF.key();
                if(Objects.equals(BdcSfztEnum.YJF.key(), Integer.parseInt(zfzt))){
                    state = BdcSfztEnum.YJF.key();
                }
                this.modifyHsxxSfxxAndDdxxZt(gzlslid, qlrlb, state, Integer.parseInt(ddzt));
                return CommonResponse.ok(state);
            }
        }
        return CommonResponse.fail("未查询到缴款结果");
    }

    /**
     * 根据工作流实例ID和权利人类别查询收费收费订单信息
     */
    private BdcSlSfssDdxxDO queryBdcSlSfssDdxx(String gzlslid, String qlrlb, Integer ddlx){
        BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
        bdcSlSfssDdxxQO.setGzlslid(gzlslid);
        bdcSlSfssDdxxQO.setQlrlb(qlrlb);
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = this.bdcSlSfssDdxxService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
        if(CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)){
            bdcSlSfssDdxxDOList = bdcSlSfssDdxxDOList.stream().filter(t-> Objects.isNull(t.getDdlx()) || Objects.equals(t.getDdlx(),ddlx))
                    .collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)){
                return bdcSlSfssDdxxDOList.get(0);
            }
        }
        return null;
    }

    /**
     * 生成收费收税订单信息
     * @param response 接口返回值
     * @param ddbh   登记订单编号
     * @param gzlslid 工作流实例ID
     * @param xmid   项目ID
     * @param qlrlb  权利人类别
     * @param zrScddDTO 政融接口参数（用于判断有无登记费、税费，生成订单信息时关系收费或收税）
     */
    private void saveBdcSlSfssDdxx(JSONObject response, String ddbh, String gzlslid, String xmid, String qlrlb, ZrScddDTO zrScddDTO){
        // 解析返回值内容
        JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(response));
        String payOrderNo = object.getString("Py_Ordr_No");
        String payUrl = object.getString("Py_URL");
        String orderGenTime = object.getString("Ordr_Gen_Tm");
        String tAmt = object.getString("TAmt");
        String orderState = object.getString("Ordr_StCd");

        // 生成收费收税订单信息
        BdcSlSfssDdxxDO sfssDdxxDO = new BdcSlSfssDdxxDO();
        sfssDdxxDO.setId(UUIDGenerator.generate16());
        sfssDdxxDO.setDdbh(ddbh);
        sfssDdxxDO.setDsfddbh(payOrderNo);
        sfssDdxxDO.setJfurl(payUrl);
        sfssDdxxDO.setGzlslid(gzlslid);
        sfssDdxxDO.setDdscsj(DateUtil.parse(orderGenTime, DatePattern.PURE_DATETIME_PATTERN));
        sfssDdxxDO.setDdje(new BigDecimal(tAmt).doubleValue());
        sfssDdxxDO.setZe(new BigDecimal(tAmt).doubleValue());
        sfssDdxxDO.setJfzt(Integer.valueOf(orderState));
        sfssDdxxDO.setQlrlb(qlrlb);
        sfssDdxxDO.setDdscsj(new Date());
        sfssDdxxDO.setDdlx(CommonConstantUtils.DDXX_DDLX_SFTJ);

        // 存在税费
        if(CollectionUtils.isNotEmpty(zrScddDTO.getTaxList())){
            List<BdcSlHsxxDO> bdcSlHsxxDOList = this.queryBdcSlHsxx(null, xmid, qlrlb);
            if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                sfssDdxxDO.setSfglidlx(CommonConstantUtils.SFGLID_LX_HSXXID);
                sfssDdxxDO.setSfglid(bdcSlHsxxDOList.get(0).getHsxxid());
            }
        }else if(CollectionUtils.isNotEmpty(zrScddDTO.getYbfyList())){
            BdcSlSfxxQO param = new BdcSlSfxxQO();
            param.setQlrlb(qlrlb);
            param.setGzlslid(gzlslid);
            param.setXmid(xmid);
            List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxService.queryBdcSlSfxx(param);
            if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)){
                sfssDdxxDO.setSfglidlx(CommonConstantUtils.SFGLID_LX_SFXXID);
                sfssDdxxDO.setSfglid(bdcSlSfxxDOList.get(0).getSfxxid());
            }
        }
        if(StringUtils.isNotBlank(sfssDdxxDO.getSfglid())){
            entityMapper.insertSelective(sfssDdxxDO);
        }else{
            LOGGER.error("登记费和税费都为空，不生成订单信息。");
        }
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

    /**
     * 根据权利人类别获取对应的税费统缴信息
     */
    private BdcSqrSftjxxDTO getSqrSftjxx(String gzlslid, String qlrlb){
        // 获取权利人、义务人税费统缴信息
        BdcYczfVO bdcYczfVO = this.queryBdcYczfSfssxx(gzlslid, qlrlb);
        if(StringUtils.equals(CommonConstantUtils.QLRLB_QLR, qlrlb)){
            return bdcYczfVO.getBdcQlrSftjxxDTO();
        }
        if(StringUtils.equals(CommonConstantUtils.QLRLB_YWR, qlrlb)){
            return bdcYczfVO.getBdcYwrSftjxxDTO();
        }
        return null;
    }

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
            if(StringUtils.isNotBlank(jfsbm)){
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
                BdcSftjDTO bdcSftjDTO = new BdcSftjDTO();
                bdcSftjDTO.setSfxmdm("税费");
                bdcSftjDTO.setSfzt(bdcSlHsxxDO.getWszt());
                bdcSftjDTO.setYsje(NumberUtil.formatDigit(bdcSlHsxxDO.getSjyzhj(), 2));
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

                            // 判断收费项目是全部已缴费，全部已缴费时，同步更新收费信息 sfzt
                            boolean allYjf = true;
                            for(BdcSlSfxmDO bdcSlSfxmDO: bdcSlSfxmDOList){
                                if(!Objects.equals(bdcSlSfxmDO.getSfzt(), BdcSfztEnum.YJF.key())){
                                    allYjf = false;
                                    break;
                                }
                            }
                            if(allYjf){
                                bdcSlSfxxDO.setSfzt(BdcSfztEnum.YJF.key());
                                this.entityMapper.updateByPrimaryKey(bdcSlSfxxDO);
                            }
                        }
                    }
                }
            }
        }
        { // 更新订单信息状态
            BdcSlSfssDdxxDO bdcSlSfssDdxxDO = this.queryBdcSlSfssDdxx(gzlslid, qlrlb, CommonConstantUtils.DDXX_DDLX_SFTJ);
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
}
