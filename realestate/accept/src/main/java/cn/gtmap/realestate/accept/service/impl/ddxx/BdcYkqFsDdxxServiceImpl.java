package cn.gtmap.realestate.accept.service.impl.ddxx;

import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.service.BdcDdxxAbstractService;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlHsxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfssDdxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.NumberUtil;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/11/28
 * @description 一卡清非税订单信息服务实现类
 */
@Service(value = "ykqfs")
public class BdcYkqFsDdxxServiceImpl implements BdcDdxxAbstractService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYkqFsDdxxServiceImpl.class);

    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcSlJyxxService bdcSlJyxxService;
    @Autowired
    BdcSlSfxxService bdcSlSfxxService;
    @Autowired
    BdcSlHsxxService bdcSlHsxxService;
    @Autowired
    BdcSlHsxxMxService bdcSlHsxxMxService;
    @Autowired
    BdcSlWxjjxxService bdcSlWxjjxxService;
    @Autowired
    BdcSlSfssDdxxService bdcSlSfssDdxxService;
    @Autowired
    BdcSlSfxmService bdcSlSfxmService;
    @Autowired
    BdcSlJbxxService bdcSlJbxxService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    private DozerBeanMapper acceptDozerMapper;
    @Autowired
    EntityMapper entityMapper;

    @Override
    public Object sczfdd(String gzlslid, String lx, String qlrlb) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产项目信息");
        }
        BdcXmDO cqxm = this.getCqBdcXm(bdcXmDOList);

        // 组织订单信息数据
        BdcDdxxDTO bdcDdxxDTO = new BdcDdxxDTO();
        bdcDdxxDTO.setNwslbh(bdcXmDOList.get(0).getSlbh());
        bdcDdxxDTO.setJkrlxdm(qlrlb);
        bdcDdxxDTO.setHtbh(this.queryBdcSlJyxxHtbhByXmid(cqxm.getXmid()));
        bdcDdxxDTO.setQxdm(cqxm.getQxdm());
        // 0:税费统缴，1：缴纳税 ，2：缴纳费
        bdcDdxxDTO.setZflx(CommonConstantUtils.DDXX_ZFLX_JNF);
        // 类型 1: 线上,2:线下， 需要exchange对照处理
        bdcDdxxDTO.setQd(lx);
        bdcDdxxDTO.setSfsftj(String.valueOf(CommonConstantUtils.SF_F_DM));
        // 财政厂家传qxdm, exchange中对照表配置对应的厂家
        bdcDdxxDTO.setCzcj(cqxm.getQxdm());

        // 组织订单收费收税信息
        BdcDdxxSfmxDTO bdcDdxxSfmxDTO = new BdcDdxxSfmxDTO();
        bdcDdxxSfmxDTO.setSlbh(cqxm.getSlbh());
        bdcDdxxSfmxDTO.setXmid(cqxm.getXmid());
        bdcDdxxSfmxDTO.setQxdm(cqxm.getQxdm());
        bdcDdxxSfmxDTO.setQlrlb(qlrlb);

        // 获取权利人名称、证件号和电话
        List<BdcSlSqrDO> bdcSlSqrDOList = this.queryBdcSlSqrxx(cqxm.getXmid(), qlrlb);
        bdcDdxxSfmxDTO.setBdcSlSqrDOS(bdcSlSqrDOList);
        if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
            String qlrmc = StringToolUtils.resolveBeanToAppendStr(bdcSlSqrDOList, "getSqrmc", ",");
            String qlrzjh = StringToolUtils.resolveBeanToAppendStr(bdcSlSqrDOList, "getZjh", ",");
            bdcDdxxDTO.setJkrqc(qlrmc);
            bdcDdxxDTO.setJkrzjh(qlrzjh);
        }

        // 获取收费信息
        List<BdcSfxxDTO> bdcSfxxDTOS = this.queryBdcSfxxSfxmList(gzlslid, cqxm.getXmid(), qlrlb);

        // 设置订单信息中非税缴款码
        bdcDdxxDTO.setFsjkm(this.getFsjkm(bdcSfxxDTOS));
        // 设置订单收费收税信息中：缴费状态、缴费名称
        this.handlerDdSfmxJfztAndJfmc(bdcDdxxSfmxDTO, bdcSfxxDTOS);

        List<BdcYczfSfxxDTO> bdcYczfSfxxDTOList = this.handlerBdcSlSfxxDTO(bdcSfxxDTOS);
        bdcDdxxSfmxDTO.setBdcSfxxDTOS(bdcYczfSfxxDTOList);
        bdcDdxxSfmxDTO.setBdcSwxxDTOS(this.queryBdcSlHsxxAndMx(gzlslid, cqxm.getXmid(), qlrlb));
        bdcDdxxSfmxDTO.setBdcWxjjxxDTOS(this.queryBdcSlWxjjxx(gzlslid,cqxm.getXmid(), qlrlb));
        bdcDdxxSfmxDTO.setSfyj(String.valueOf(CommonConstantUtils.SF_F_DM));
        List<BdcDdxxSfmxDTO> bdcDdxxSfmxDTOS = new ArrayList<>(1);
        bdcDdxxSfmxDTOS.add(bdcDdxxSfmxDTO);
        bdcDdxxDTO.setSfmx(bdcDdxxSfmxDTOS);
        // 计算总金额
        bdcDdxxDTO.setZe(this.countZe(bdcDdxxSfmxDTOS));

        LOGGER.info("一卡清生成订单，请求参数：{}", JSON.toJSONString(bdcDdxxDTO));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_scdd", bdcDdxxDTO);
        LOGGER.info("一卡清生成订单，返回值信息：{}", JSON.toJSONString(response));
        if (Objects.nonNull(response)) {
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(response));
            JSONArray jsonArray = object.getJSONArray("data");
            if(CollectionUtils.isEmpty(jsonArray)){
                JSONObject headJson = object.getJSONObject("head");
                throw new AppException(ErrorCode.CUSTOM, "请求一卡清生成订单接口异常：" + headJson.getString("msg"));
            }
            if(CollectionUtils.isNotEmpty(jsonArray)){
                JSONObject responseJson = jsonArray.getJSONObject(0);
//                this.saveBdcSlSfxx(responseJson, gzlslid, qlrlb);
                this.saveBdcSlSfssDdxx(responseJson, gzlslid, qlrlb, bdcDdxxDTO);
                Map<String, String> result = new HashMap<>(2);
                result.put("jfsbm", responseJson.getString("jkm"));
                result.put("jfsewmurl", responseJson.getString("jfurl"));
                return result;
            }
        }
        return null;
    }

    private List<BdcYczfSfxxDTO> handlerBdcSlSfxxDTO(List<BdcSfxxDTO> bdcSfxxDTOS){
        List<BdcYczfSfxxDTO> bdcYczfSfxxDTOList = new ArrayList<>(bdcSfxxDTOS.size());
        if(CollectionUtils.isNotEmpty(bdcSfxxDTOS)){
            for(BdcSfxxDTO bdcSfxxDTO : bdcSfxxDTOS){
                BdcYczfSfxxDTO bdcYczfSfxxDTO = new BdcYczfSfxxDTO();
                bdcYczfSfxxDTO.setBdcSlSfxxDO(bdcSfxxDTO.getBdcSlSfxxDO());
                List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSfxxDTO.getBdcSlSfxmDOS();
                List<BdcYczfSlSfxmDTO> bdcYczfSlSfxmDTOList = new ArrayList<>(bdcSlSfxmDOList.size());
                for(BdcSlSfxmDO bdcSlSfxmDO: bdcSlSfxmDOList){
                    BdcYczfSlSfxmDTO bdcYczfSlSfxmDTO = new BdcYczfSlSfxmDTO();
                    BeanUtils.copyProperties(bdcSlSfxmDO, bdcYczfSlSfxmDTO);
                    if(Objects.nonNull(bdcSlSfxmDO.getSl())){
                        String sl = bdcSlSfxmDO.getSl().toString();
                        String slString = sl.substring(0, sl.indexOf('.'));
                        bdcYczfSlSfxmDTO.setSl(Integer.parseInt(slString));
                    }
                    bdcYczfSlSfxmDTOList.add(bdcYczfSlSfxmDTO);
                }
                bdcYczfSfxxDTO.setBdcSlSfxmDOS(bdcYczfSlSfxmDTOList);
                bdcYczfSfxxDTOList.add(bdcYczfSfxxDTO);
            }
        }
        return bdcYczfSfxxDTOList;
    }

    // 获取受理申请人信息
    private List<BdcSlSqrDO> queryBdcSlSqrxx(String xmid, String qlrlb){
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(qlrlb);
        List<BdcQlrDO> qlrxxList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        List<BdcSlSqrDO> bdcSlSqrDOList = new ArrayList<>(CollectionUtils.size(qlrxxList));
        if (CollectionUtils.isNotEmpty(qlrxxList)) {
            for (BdcQlrDO bdcQlrDO : qlrxxList) {
                BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                acceptDozerMapper.map(bdcQlrDO, bdcSlSqrDO);
                bdcSlSqrDOList.add(bdcSlSqrDO);
            }
        }
        return bdcSlSqrDOList;
    }

    // 组合流程获取产权项目信息
    private BdcXmDO getCqBdcXm(List<BdcXmDO> bdcXmDOList){
        BdcXmDO cqxm = bdcXmDOList.get(0);
        // 组合流程获取房地产权的登记费信息
        int lclx = bdcXmFeignService.makeSureBdcXmLx(bdcXmDOList);
        if (lclx == 2 || lclx == 4) {
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                for(BdcXmDO bdcXm : bdcXmDOList){
                    if(ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, bdcXm.getQllx())){
                        cqxm = bdcXm;
                    }
                }
            }
        }
        return cqxm;
    }

    // 获取不动产受理交易信息合同编号
    private String queryBdcSlJyxxHtbhByXmid(String xmid){
        List<BdcSlJyxxDO> bdcSlJyxxDOList = this.bdcSlJyxxService.listBdcSlJyxxByXmid(xmid);
        if(CollectionUtils.isNotEmpty(bdcSlJyxxDOList)){
            return bdcSlJyxxDOList.get(0).getHtbh();
        }
        return null;
    }

    // 查询不动产收费信息和收费项目
    private List<BdcSfxxDTO> queryBdcSfxxSfxmList(String gzlslid, String xmid, String qlrlb){
        BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
        if(StringUtils.isNotBlank(gzlslid)){
            bdcSlSfxxQO.setGzlslid(gzlslid);
        }
        if(StringUtils.isNotBlank(xmid)){
            bdcSlSfxxQO.setXmid(xmid);
        }
        bdcSlSfxxQO.setQlrlb(qlrlb);
        List<BdcSfxxDTO> bdcSfxxDTOList = this.bdcSlSfxxService.queryBdcSlSfxxAndSfxm(bdcSlSfxxQO);
        return bdcSfxxDTOList;
    }

    // 获取不动产受理核税信息和核税明细信息
    private List<BdcSwxxDTO> queryBdcSlHsxxAndMx(String gzlslid, String xmid, String qlrlb){
        List<BdcSlHsxxDO> bdcSlHsxxDOList = new ArrayList<>(10);
        if(StringUtils.isBlank(xmid)){
            bdcSlHsxxDOList = this.bdcSlHsxxService.listBdcSlHsxxByGzlslidAndSqrlb(gzlslid, qlrlb);
        }else{
            BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
            bdcSlHsxxQO.setXmid(xmid);
            bdcSlHsxxQO.setSqrlb(qlrlb);
            bdcSlHsxxDOList = this.bdcSlHsxxService.listBdcSlHsxxByHsxxQo(bdcSlHsxxQO);
        }
        // 过滤核税信息数据，只获取核税信息类型为空或核税信息为0的数据
        if(CollectionUtils.isNotEmpty(bdcSlHsxxDOList)){
            bdcSlHsxxDOList = bdcSlHsxxDOList.stream().filter(t -> StringUtils.isBlank(t.getHsxxlx()) || StringUtils.equals("0", t.getHsxxlx()))
                    .collect(Collectors.toList());
        }
        List<BdcSwxxDTO> bdcSwxxDTOList = new ArrayList<>(10);
        if(CollectionUtils.isNotEmpty(bdcSlHsxxDOList)){
            for(BdcSlHsxxDO hsxx: bdcSlHsxxDOList){
                BdcSwxxDTO bdcSwxxDTO = new BdcSwxxDTO();
                bdcSwxxDTO.setBdcSlHsxxDO(hsxx);
                List<BdcSlHsxxMxDO> bdcSlHsxxMxDOS = this.bdcSlHsxxMxService.listBdcSlHsxxMxByHsxxid(hsxx.getHsxxid());
                if(CollectionUtils.isNotEmpty(bdcSlHsxxMxDOS)){
                    bdcSwxxDTO.setBdcSlHsxxMxDOList(bdcSlHsxxMxDOS);
                }
                bdcSwxxDTOList.add(bdcSwxxDTO);
            }
        }
        return bdcSwxxDTOList;
    }

    // 获取不动产维修基金信息
    private List<BdcWxjjxxDTO> queryBdcSlWxjjxx(String gzlslid, String xmid, String qlrlb){
        BdcSlWxjjxxDO bdcSlWxjjxxDO = new BdcSlWxjjxxDO();
        bdcSlWxjjxxDO.setGzlslid(gzlslid);
        bdcSlWxjjxxDO.setXmid(xmid);
        bdcSlWxjjxxDO.setQlrlb(qlrlb);
        List<BdcSlWxjjxxDO> bdcSlWxjjxxDOList = this.bdcSlWxjjxxService.queryBdcSlWxjjxx(bdcSlWxjjxxDO);

        List<BdcWxjjxxDTO> bdcWxjjxxDTOList = new ArrayList<>(10);
        if(CollectionUtils.isNotEmpty(bdcSlWxjjxxDOList)){
            for(BdcSlWxjjxxDO wxjjxxDO : bdcSlWxjjxxDOList){
                BdcWxjjxxDTO bdcWxjjxxDTO = new BdcWxjjxxDTO();
                bdcWxjjxxDTO.setBdcSlWxjjxxDO(wxjjxxDO);
                bdcWxjjxxDTOList.add(bdcWxjjxxDTO);
            }
        }
        return bdcWxjjxxDTOList;
    }

    // 获取非税缴款码
    private String getFsjkm(List<BdcSfxxDTO> bdcSfxxDTOS){
        if(CollectionUtils.isNotEmpty(bdcSfxxDTOS)){
            BdcSfxxDTO bdcSfxxDTO = bdcSfxxDTOS.get(0);
            if(StringUtils.isNotBlank(bdcSfxxDTO.getBdcSlSfxxDO().getJfsbm())){
                return bdcSfxxDTO.getBdcSlSfxxDO().getJfsbm();
            }
            List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSfxxDTO.getBdcSlSfxmDOS();
            if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
                if(StringUtils.isNotBlank(bdcSlSfxmDOList.get(0).getJfsbm())){
                    return bdcSlSfxmDOList.get(0).getJfsbm();
                }
            }
        }
        return "";
    }

    // 设置订单收费明细缴费状态、缴费名称
    private void handlerDdSfmxJfztAndJfmc(BdcDdxxSfmxDTO bdcDdxxSfmxDTO, List<BdcSfxxDTO> bdcSfxxDTOS){
        if(CollectionUtils.isNotEmpty(bdcSfxxDTOS)){
            BdcSfxxDTO bdcSfxxDTO = bdcSfxxDTOS.get(0);
            if(Objects.nonNull(bdcSfxxDTO.getBdcSlSfxxDO().getSfzt())){
                bdcDdxxSfmxDTO.setJfzt(String.valueOf(bdcSfxxDTO.getBdcSlSfxxDO().getSfzt()));
                bdcDdxxSfmxDTO.setJfztmc(BdcSfztEnum.getNameByKey(bdcSfxxDTO.getBdcSlSfxxDO().getSfzt()));
            }
        }
    }

    // 计算总金额
    private String countZe(List<BdcDdxxSfmxDTO> sfmxList){
        Double zje = 0.0;
        if(CollectionUtils.isNotEmpty(sfmxList)){
            for(BdcDdxxSfmxDTO bdcDdxxSfmxDTO :sfmxList){
                List<BdcYczfSfxxDTO> bdcSfxxDTOList = bdcDdxxSfmxDTO.getBdcSfxxDTOS();
                if(CollectionUtils.isNotEmpty(bdcSfxxDTOList)){
                    zje += bdcSfxxDTOList.stream().filter(t-> Objects.nonNull(t.getBdcSlSfxxDO()))
                            .mapToDouble(t-> Optional.ofNullable(t.getBdcSlSfxxDO().getHj()).orElse(0.0)).sum();
                }
                List<BdcSwxxDTO> bdcSwxxDTOS = bdcDdxxSfmxDTO.getBdcSwxxDTOS();
                if(CollectionUtils.isNotEmpty(bdcSwxxDTOS)){
                    zje += bdcSwxxDTOS.stream().filter(t->Objects.nonNull(t.getBdcSlHsxxDO()))
                            .mapToDouble(t-> Optional.ofNullable(t.getBdcSlHsxxDO().getSjyzhj()).orElse(0.0)).sum();
                }

                List<BdcWxjjxxDTO> bdcWxjjxxDTOList = bdcDdxxSfmxDTO.getBdcWxjjxxDTOS();
                if(CollectionUtils.isNotEmpty(bdcWxjjxxDTOList)){
                    zje += bdcWxjjxxDTOList.stream().filter(t->Objects.nonNull(t.getBdcSlWxjjxxDO()))
                            .mapToDouble(t-> Optional.ofNullable(t.getBdcSlWxjjxxDO().getHj()).orElse(0.0)).sum();
                }
            }

        }
        return String.valueOf(NumberUtil.formatDigit(zje.doubleValue(), 2));
    }

    /**
     * 更新
     * @param response 接口返回值
     * @param gzlslid 工作流实例ID
     * @param qlrlb  权利人类别
     */
    private void saveBdcSlSfxx(JSONObject response, String gzlslid, String qlrlb){
        String jfurl = response.getString("jfurl");
        String jkm = response.getString("jkm");
        BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
        bdcSlSfxxDO.setGzlslid(gzlslid);
        bdcSlSfxxDO.setQlrlb(qlrlb);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxService.queryBdcSlSfxx(bdcSlSfxxDO);
        if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)){
            for(BdcSlSfxxDO sfxx: bdcSlSfxxDOList){
                sfxx.setJfsewmurl(jfurl);
                sfxx.setJfsbm(jkm);
                List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxx.getSfxxid());
                if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
                    for(BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList){
                        bdcSlSfxmDO.setJfsewmurl(jfurl);
                        bdcSlSfxmDO.setJfsbm(jkm);
                    }
                    this.bdcSlSfxmService.batchUpdateBdcSlSfxm(bdcSlSfxmDOList);
                }
            }
            this.bdcSlSfxxService.batchUpdateBdcSlSfxx(bdcSlSfxxDOList);
        }

    }

    /**
     * 生成收费收税订单信息
     * @param response 接口返回值
     * @param gzlslid 工作流实例ID
     * @param qlrlb  权利人类别
     * @param bdcDdxxDTO 订单信息DTO
     */
    private void saveBdcSlSfssDdxx(JSONObject response, String gzlslid, String qlrlb, BdcDdxxDTO bdcDdxxDTO){
        String ddbh = response.getString("ddbh");
        String dsfddbh = response.getString("dsfddbh");
        String jfurl = response.getString("jfurl");
        String jfzt = response.getString("jfzt");
        // 生成收费收税订单信息
        BdcSlSfssDdxxDO sfssDdxxDO = new BdcSlSfssDdxxDO();
        sfssDdxxDO.setId(UUIDGenerator.generate16());
        sfssDdxxDO.setDdbh(ddbh);
        sfssDdxxDO.setDsfddbh(dsfddbh);
        sfssDdxxDO.setJfurl(jfurl);
        sfssDdxxDO.setGzlslid(gzlslid);
        sfssDdxxDO.setDdscsj(new Date());
        sfssDdxxDO.setDdje(new BigDecimal(bdcDdxxDTO.getZe()).doubleValue());
        sfssDdxxDO.setZe(new BigDecimal(bdcDdxxDTO.getZe()).doubleValue());
        sfssDdxxDO.setJfzt(Integer.valueOf(jfzt));
        sfssDdxxDO.setQlrlb(qlrlb);
        sfssDdxxDO.setDdscsj(new Date());
        sfssDdxxDO.setDdlx(CommonConstantUtils.DDXX_DDLX_JNF);
        sfssDdxxDO.setSfglidlx(CommonConstantUtils.SFGLID_LX_SFXXID);
        List<BdcDdxxSfmxDTO> bdcDdxxSfmxDTOS = bdcDdxxDTO.getSfmx();
        if(CollectionUtils.isNotEmpty(bdcDdxxSfmxDTOS)){
            if(CollectionUtils.isNotEmpty(bdcDdxxSfmxDTOS.get(0).getBdcSfxxDTOS())){
                List<BdcYczfSfxxDTO> bdcSfxxDTOList = bdcDdxxSfmxDTOS.get(0).getBdcSfxxDTOS();
                sfssDdxxDO.setSfglidlx(CommonConstantUtils.SFGLID_LX_SFXXID);
                sfssDdxxDO.setSfglid(bdcSfxxDTOList.get(0).getBdcSlSfxxDO().getSfxxid());
            }
        }
        if(StringUtils.isNotBlank(sfssDdxxDO.getSfglid())){
            entityMapper.insertSelective(sfssDdxxDO);
        }else{
            LOGGER.error("登记费为空，不生成订单信息。");
        }
    }

    /**
     *  查询缴费结果
     * @param gzlslid 工作流实例ID
     * @param qlrlb 权利人类别
     * @return
     */
    @Override
    public CommonResponse cxjfjg(String gzlslid, String qlrlb) {
        if(StringUtils.isAnyBlank(gzlslid, qlrlb)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数：工作流实例ID或权利人类别。");
        }
        String slbh = "", qxdm = "";
        List<BdcXmDTO> bdcXmDTOList =  this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcXmDTOList)){
            BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
            if(Objects.isNull(bdcSlJbxxDO)){
                throw new AppException(ErrorCode.CUSTOM, "未获取到不动产项目数据和不动产受理基本信息。");
            }
            slbh = bdcSlJbxxDO.getSlbh();
            qxdm = bdcSlJbxxDO.getQxdm();
        }else{
            slbh = bdcXmDTOList.get(0).getSlbh();
            qxdm = bdcXmDTOList.get(0).getQxdm();
        }
        // 获取订单编号信息
        BdcSlSfssDdxxDO bdcSlSfssDdxxDO = this.queryBdcSlSfssDdxx(gzlslid, qlrlb);
        Map<String, Object> map = new HashMap<>(2);
        if(Objects.nonNull(bdcSlSfssDdxxDO)){
            map.put("ddbh", bdcSlSfssDdxxDO.getDdbh());
        }
        map.put("slbh", slbh);
        map.put("czcj", qxdm);
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_cxsfzt", map);
        LOGGER.info("一卡清查询订单状态，返回值信息：{}", JSON.toJSONString(response));
        if (Objects.nonNull(response)) {
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(response));
            JSONArray jsonArray = object.getJSONArray("data");
            if(CollectionUtils.isEmpty(jsonArray)){
                JSONObject headJson = object.getJSONObject("head");
                throw new AppException(ErrorCode.CUSTOM, "请求一卡清生成订单接口异常：" + headJson.getString("msg"));
            }
            if(CollectionUtils.isNotEmpty(jsonArray)){
                int sfzt = BdcSfztEnum.YJF.key();
                for(int i = 0; i< jsonArray.size(); i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String zfzt = obj.getString("sfjf");
                    if (StringUtils.isNotBlank(zfzt)){
                        if (!Objects.equals(BdcSfztEnum.YJF.key(), Integer.parseInt(zfzt))) {
                            // 存在一条未缴费则整个收费状态为未缴费
                            sfzt = BdcSfztEnum.WJF.key();
                        }
                        // 更新收费信息收费状态
                        this.modifySfxxZt(gzlslid, qlrlb, Integer.parseInt(zfzt));
                    }
                }
                return CommonResponse.ok(sfzt);
            }
        }
        return CommonResponse.fail("未查询到缴款结果");
    }

    /**
     * 根据工作流实例ID和权利人类别查询收费收费订单信息
     */
    private BdcSlSfssDdxxDO queryBdcSlSfssDdxx(String gzlslid, String qlrlb){
        BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
        bdcSlSfssDdxxQO.setGzlslid(gzlslid);
        bdcSlSfssDdxxQO.setQlrlb(qlrlb);
        bdcSlSfssDdxxQO.setDdlx(CommonConstantUtils.DDXX_DDLX_JNF);
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = this.bdcSlSfssDdxxService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
        if(CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)){
            return bdcSlSfssDdxxDOList.get(0);
        }
        return null;
    }

    private void modifySfxxZt(String gzlslid, String qlrlb, Integer zfzt){
        LOGGER.info("更新支付状态：{}", zfzt);
        // 支付状态 和 订单状态都为空时，不执行更新操作
        if(Objects.isNull(zfzt)){
            return;
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
                        bdcSlSfxxDO.setSfzt(sfzt);
                        this.entityMapper.updateByPrimaryKey(bdcSlSfxxDO);

                        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
                            for(BdcSlSfxmDO bdcSlSfxmDO: bdcSlSfxmDOList){
                                bdcSlSfxmDO.setSfzt(sfzt);
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

    }
}
