package cn.gtmap.realestate.exchange.service.impl.inf.yancheng;

import cn.gtmap.gtc.storage.clients.v1.StorageClient;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.building.ZdQlrDO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcYxmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCfxxQO;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.building.ZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcCfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.CommonMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.exchange.core.dto.common.*;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.respones.YthCommonResponse;
import cn.gtmap.realestate.exchange.core.dto.ythts.cf.*;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.build.BuildLogServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.yancheng.YthxxTsService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class YthxxTsServiceImpl implements YthxxTsService {
    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    HttpClientService httpClientService;

    @Autowired
    BuildLogServiceImpl buildLogService;

    @Autowired
    StorageClient storageClient;

    @Autowired
    CommonService commonService;

    @Autowired
    ZdFeignService zdFeignService;

    @Autowired
    ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    BdcCzrzFeignService bdcCzrzFeignService;

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    private BdcCfxxFeignService bdcCfxxFeignService;

//    @Autowired
//    EntityMapper commonMapper;

    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    /**
     * ?????????????????????????????????????????????
     *
     * @param processInsId
     * @param currentUserName
     */
    @Override
    public CommonResponse ythcfxxts(String processInsId, String currentUserName) {
        //???????????????????????????
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        List<BdcXmDO> bdcCfXms = new ArrayList<>();
        Map<String, BdcCfDO> bdcCfQls = new HashMap<>();
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            return CommonResponse.fail("????????????");
        }
        for (BdcXmDO bdcXmDO : bdcXmDOS) {
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
            if (bdcQl instanceof BdcCfDO) {
                bdcCfXms.add(bdcXmDO);
                bdcCfQls.put(bdcXmDO.getXmid(), (BdcCfDO) bdcQl);
            }
        }

        if (CollectionUtils.isEmpty(bdcCfXms)) {
            return CommonResponse.fail("????????????");
        }
        //??????????????????????????????
        addModifyCzrz(bdcCfXms.get(0), -2);

        CfxxDto cfxxDto = new CfxxDto();
        cfxxDto.setYWBH(bdcCfXms.get(0).getSlbh());
        cfxxDto.setSJYWMC("??????");
        getCfxx(bdcCfXms, cfxxDto, bdcCfQls);

        //????????????
        CommonResponse commonResponse = notice(cfxxDto);
        //????????????
        if (commonResponse.isSuccess()) {
            addModifyCzrz(bdcCfXms.get(0), CommonConstantUtils.SF_S_DM);
        } else {
            addModifyCzrz(bdcCfXms.get(0), CommonConstantUtils.SF_F_DM);
        }
        return commonResponse;
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param processInsId
     * @param currentUserName
     */
    @Override
    public CommonResponse ythjfxxts(String processInsId, String currentUserName) {
        //???????????????????????????
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        List<BdcXmDO> bdcJfXms = new ArrayList<>();
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            return CommonResponse.fail("????????????");
        }
        //???????????????????????????
        Map<String, List<BdcXmDO>> bdcXmGroup = bdcXmDOS
                .stream()
                .collect(Collectors.groupingBy(BdcXmDO::getBdcdyh));
        for (Map.Entry<String, List<BdcXmDO>> stringListEntry : bdcXmGroup.entrySet()) {
            BdcQl bdcQl = bdcQllxFeignService.queryBdcYqlxx(stringListEntry.getValue().get(0).getXmid());
            if (bdcQl instanceof BdcCfDO) {
                //?????????????????? -- ????????? ?????????????????????????????????????????????????????????????????????????????????????????????????????????
                bdcJfXms.add(stringListEntry.getValue().get(0));
            }
        }

        if (CollectionUtils.isEmpty(bdcJfXms)) {
            return CommonResponse.fail("????????????");
        }
        //??????????????????????????????
        addModifyCzrz(bdcJfXms.get(0), -2);

        CfJfxxDto jfxxDto = new CfJfxxDto();
        jfxxDto.setYWBH(bdcJfXms.get(0).getSlbh());
        jfxxDto.setSJYWMC("??????");
        List<Fwxx> fwxxList = getFwxxes(bdcJfXms);
        jfxxDto.setFwxx(fwxxList);

        //????????????
        CommonResponse commonResponse = notice(jfxxDto);
        //????????????
        if (commonResponse.isSuccess()) {
            addModifyCzrz(bdcJfXms.get(0), CommonConstantUtils.SF_S_DM);
        } else {
            addModifyCzrz(bdcJfXms.get(0), CommonConstantUtils.SF_F_DM);
        }
        return commonResponse;
    }

    /**
     * ????????????
     *
     * @param processInsId
     */
    @Override
    public CommonResponse ythxxsdts(String processInsId) {
        try {
            log.info("????????????????????????????????????,????????????????????????{}", processInsId);
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(processInsId);
            List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isEmpty(bdcXmDOS)) {
                return CommonResponse.fail("????????????");
            }
            //???????????????????????????
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDOS.get(0).getXmid());
            if (bdcQl instanceof BdcCfDO) {
                return ythcfxxts(processInsId, "");
            } else {
                return ythjfxxts(processInsId, "");
            }
        } catch (Exception e) {
            log.info("????????????????????????????????????,????????????????????????{}??????,??????{}",
                    processInsId, e.getMessage());
            e.printStackTrace();
            return CommonResponse.fail("????????????");
        }
    }

    /**
     * ????????????
     *
     * @param bdcCfXms
     * @param cfxxDto
     * @return
     */
    private CfxxDto getCfxx(List<BdcXmDO> bdcCfXms, CfxxDto cfxxDto, Map<String, BdcCfDO> bdcCfQls) {
        //????????????
        List<KttFwH> KttFwH = new ArrayList<>();
        List<QlfQlCfdj> QlfQlCfdj = new ArrayList<>();
        List<ZttGyQlr> qlrList = new ArrayList<>();
        //????????????
        List<Map> zdMap = bdcZdFeignService.queryBdcZd("qlrlb");

        //???????????? -- ??????????????????????????????,???????????????????????????????????????????????????,??????????????????????????????
        CfQlWithXmQlrDTO cfQlWithXmQlrDTO = new CfQlWithXmQlrDTO();
        //???????????????????????????????????????
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        bdcQlrQO.setXmid(bdcCfXms.get(0).getXmid());
        List<BdcQlrDO> qlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        //
        cfQlWithXmQlrDTO.setBdcQlrList(qlrDOList);
        cfQlWithXmQlrDTO.setBdcql(bdcCfQls.get(bdcCfXms.get(0).getXmid()));
        cfQlWithXmQlrDTO.setBdcXmDO(bdcCfXms.get(0));
        QlfQlCfdj cfdj = getQlfQlCfdj(cfQlWithXmQlrDTO, "");
        QlfQlCfdj.add(cfdj);
        cfxxDto.setQLF_QL_CFDJ(QlfQlCfdj);

        //??????????????????????????????????????????
        getCfjgQlr(bdcCfXms, qlrList, cfQlWithXmQlrDTO);
        //??????????????????????????????
        for (BdcXmDO bdcCfXm : bdcCfXms) {
            KttFwH kttFw_h = new KttFwH();
            kttFw_h.setBDCDYH(bdcCfXm.getBdcdyh());
            kttFw_h.setYWH(bdcCfXm.getSlbh());
            kttFw_h.setZL(bdcCfXm.getZl());
            KttFwH.add(kttFw_h);

            /**
             *
             * 1?????????????????????????????????????????????????????????zd_qlr???
             * 2??????????????????????????????????????????????????????????????????
             * 3?????????????????????????????????????????????????????????????????????
             * 4?????????????????????????????????????????????????????????????????????????????????
             */
            QueryQlRequestDTO queryQlRequestDTO = new QueryQlRequestDTO();
            queryQlRequestDTO.setWithXm(true);
            queryQlRequestDTO.setWithQlr(true);
            queryQlRequestDTO.setQszt("1");
            queryQlRequestDTO.setBdcdyh(bdcCfXm.getBdcdyh());
            getYgOrFdcqQlr(qlrList, queryQlRequestDTO, bdcCfXm, CommonConstantUtils.QLRLB_YWR);
        }

        if (CollectionUtils.isNotEmpty(qlrList)) {
            for (ZttGyQlr zttGyQlr : qlrList) {
                zttGyQlr.setJS(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(zttGyQlr.getJS()), zdMap));
            }
        }
        cfxxDto.setKTT_FW_H(KttFwH);
        cfxxDto.setZTT_GY_QLR(qlrList);
        return cfxxDto;
    }


    /**
     * ????????????
     *
     * @param bdcCfXms
     * @return
     */
    private List<Fwxx> getFwxxes(List<BdcXmDO> bdcCfXms) {
        List<Fwxx> fwxxList = new ArrayList<>();
        //????????????
        List<Map> zdMap = bdcZdFeignService.queryBdcZd("qlrlb");
        //??????????????????
        for (BdcXmDO bdcjfXm : bdcCfXms) {

            Fwxx fwxx = new Fwxx();
            List<QlfQlJfdj> QlfQlJfdj = new ArrayList<>();
            List<QlfQlCfdj> QlfQlCfdj = new ArrayList<>();
            List<ZttGyQlr> qlrList = new ArrayList<>();
            List<KttFwH> KttFwH = new ArrayList<>();

            //????????????
            KttFwH = getKttFwHS(bdcjfXm);
            fwxx.setKTT_FW_H(KttFwH);

            //???????????? ????????????  -- ??????????????????????????????
            //?????????????????????????????????
            BdcCfDO bdcql = (BdcCfDO) bdcQllxFeignService.queryBdcYqlxx(bdcjfXm.getXmid());
            if (StringUtils.isEmpty(bdcql.getJfwh())) {
                continue;
            }
            //????????????
            QlfQlJfdj jfdj = getQlfQlJfdj(bdcjfXm, bdcql);
            QlfQlJfdj.add(jfdj);

            //???????????????????????????????????????
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            bdcQlrQO.setXmid(bdcjfXm.getXmid());
            List<BdcQlrDO> qlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            CfQlWithXmQlrDTO jfQlWithXmQlrDTO = new CfQlWithXmQlrDTO();
            jfQlWithXmQlrDTO.setBdcql(bdcql);
            jfQlWithXmQlrDTO.setBdcXmDO(bdcjfXm);
            jfQlWithXmQlrDTO.setBdcQlrList(qlrDOList);
            //??????????????????????????????
            //getCfjgQlr(Collections.singletonList(bdcjfXm), qlrList, jfQlWithXmQlrDTO);


            //??????????????????????????????????????????
            Example example = new Example(BdcCfDO.class);
            example.createCriteria()
                    .andEqualTo("jfwh", bdcql.getJfwh())
                    .andEqualTo("bdcdyh", bdcql.getBdcdyh());
            List<BdcCfDO> bdcCfDOS = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcCfDOS)) {
                for (BdcCfDO cfxx : bdcCfDOS) {
                    //?????????????????????????????????
                    List<BdcXmDO> bdcCfXmDOS = bdcXmFeignService.listBdcXmByXmids(Arrays.asList(cfxx.getXmid()));
                    CfQlWithXmQlrDTO cfQlWithXmQlrDTO = new CfQlWithXmQlrDTO();
                    cfQlWithXmQlrDTO.setBdcql(cfxx);
                    cfQlWithXmQlrDTO.setBdcXmDO(bdcCfXmDOS.get(0));
                    QlfQlCfdj cfdj = getQlfQlCfdj(cfQlWithXmQlrDTO, bdcjfXm.getSlbh());
                    QlfQlCfdj.add(cfdj);
                }
            }

            fwxx.setQLF_QL_CFDJ(QlfQlCfdj);
            fwxx.setQLF_QL_JFDJ(QlfQlJfdj);


            /**
             *
             * 1?????????????????????????????????????????????????????????zd_qlr???
             * 2??????????????????????????????????????????????????????????????????
             * 3?????????????????????????????????????????????????????????????????????
             * 4?????????????????????????????????????????????????????????????????????????????????
             */
            QueryQlRequestDTO queryQlRequestDTO = new QueryQlRequestDTO();
            queryQlRequestDTO.setBdcdyh(bdcjfXm.getBdcdyh());
            queryQlRequestDTO.setQszt("1");
            queryQlRequestDTO.setWithXm(true);
            queryQlRequestDTO.setWithQlr(true);
            getYgOrFdcqQlr(qlrList, queryQlRequestDTO, bdcjfXm, "");

            fwxx.setZTT_GY_QLR(qlrList);
            if (CollectionUtils.isNotEmpty(qlrList)) {
                for (ZttGyQlr zttGyQlr : qlrList) {
                    zttGyQlr.setJS(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(zttGyQlr.getJS()), zdMap));
                }
            }
            fwxxList.add(fwxx);
        }
        return fwxxList;
    }


    /**
     * ?????????????????????
     *
     * @param qlrList
     * @param queryQlRequestDTO
     * @param xmDO
     * @param qlrlb
     */
    private void getYgOrFdcqQlr(List<ZttGyQlr> qlrList,
                                QueryQlRequestDTO queryQlRequestDTO,
                                BdcXmDO xmDO,
                                String qlrlb
    ) {
        List<ZttGyQlr> currentQlrList = new ArrayList<>();
        List<QlWithXmQlrDTO> qlWithXmQlrDTOS = new ArrayList<>();
        List<String> qlrzjh = new ArrayList<>();
        //????????????????????????????????????
        List<YgQlWithXmQlrDTO> ygQlWithXmQlrDTOS = commonService.listYgByBdcdyh(queryQlRequestDTO);
        List<FdcqQlWithXmQlrDTO> fdcqQlWithXmQlrDTOS = commonService.listFdcqByBdcdyh(queryQlRequestDTO);
        if (CollectionUtils.isNotEmpty(ygQlWithXmQlrDTOS)) {
            ygQlWithXmQlrDTOS = ygQlWithXmQlrDTOS.stream().filter(ygQlWithXmQlrDTO -> {
                return ygQlWithXmQlrDTO.getBdcql().getYgdjzl().equals(CommonConstantUtils.YGDJZL_YSSPFYG) ||
                        ygQlWithXmQlrDTO.getBdcql().getYgdjzl().equals(CommonConstantUtils.YGDJZL_QTYG);
            }).collect(Collectors.toList());
            qlWithXmQlrDTOS.addAll(ygQlWithXmQlrDTOS);
        }
        if (CollectionUtils.isNotEmpty(fdcqQlWithXmQlrDTOS)) {
            qlWithXmQlrDTOS.addAll(fdcqQlWithXmQlrDTOS);
        }
        if (CollectionUtils.isNotEmpty(qlWithXmQlrDTOS)
        ) {
            for (QlWithXmQlrDTO qlWithXmQlrDTO : qlWithXmQlrDTOS) {
                List<BdcQlrDO> bdcQlrList = qlWithXmQlrDTO.getBdcQlrList();
                bdcQlrList = bdcQlrList.stream()
                        .filter(bdcQlrDO -> bdcQlrDO.getQlrlb().equals(CommonConstantUtils.QLRLB_QLR))
                        .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(bdcQlrList)) {
                    BdcXmDO bdcXmDO = qlWithXmQlrDTO.getBdcXmDO();
                    for (BdcQlrDO bdcQlrDO : bdcQlrList) {
                        if (qlrzjh.contains(bdcQlrDO.getZjh())) {
                            continue;
                        }
                        qlrzjh.add(bdcQlrDO.getZjh());
                        ZttGyQlr qlr = new ZttGyQlr();
                        qlr.setQLRMC(bdcQlrDO.getQlrmc());
                        qlr.setZJZL(bdcQlrDO.getZjzl().toString());
                        qlr.setZJH(bdcQlrDO.getZjh());
                        if (StringUtils.isNotBlank(qlrlb)) {
                            qlr.setJS(qlrlb);
                        } else {
                            qlr.setJS(bdcQlrDO.getQlrlb());
                        }
                        qlr.setQLRLX(bdcQlrDO.getQlrlx().toString());
                        if (Objects.nonNull(bdcQlrDO.getGyfs())) {
                            qlr.setGYFS(bdcQlrDO.getGyfs().toString());
                        }
                        qlr.setGYQK(bdcQlrDO.getGyqk());
                        qlr.setQLBL(bdcQlrDO.getQlbl());
                        qlr.setBDCDYH(bdcXmDO.getBdcdyh());
                        qlr.setBDCQZH(bdcXmDO.getBdcqzh());
                        qlr.setYWH(xmDO.getSlbh());
                        qlr.setBZ(bdcXmDO.getBz());
                        currentQlrList.add(qlr);
                    }
                }
            }
        }
        qlrList.addAll(currentQlrList);
        log.info("??????????????????????????? getYgOrFdcqQlr : {}", JSON.toJSONString(qlrList));
        //???????????????????????????????????????????????????????????????????????????
        if (CollectionUtils.isEmpty(currentQlrList)) {
            //?????????????????????
            String djh = xmDO.getBdcdyh().substring(0, 19) + "W00000000";
            List<ZdQlrDO> zdQlrDOS = zdFeignService.listZdQlrByBdcdyh(djh, "");
            log.info("listZdQlrByBdcdyh djh {} : {}", djh, JSON.toJSONString(zdQlrDOS));
            if (CollectionUtils.isNotEmpty(zdQlrDOS)) {
                for (ZdQlrDO zdQlrDO : zdQlrDOS) {
                    ZttGyQlr qlr = new ZttGyQlr();
                    qlr.setQLRMC(zdQlrDO.getQlrmc());
                    qlr.setZJZL(zdQlrDO.getQlrzjlx());
                    qlr.setZJH(zdQlrDO.getQlrzjh());
                    if (StringUtils.isNotBlank(qlrlb)) {
                        qlr.setJS(qlrlb);
                    } else {
                        qlr.setJS(Constants.QLRLB_QLR);
                    }
                    qlr.setQLRLX(zdQlrDO.getQlrlx());
                    qlr.setBDCDYH(xmDO.getBdcdyh());
                    qlr.setBDCQZH(xmDO.getBdcqzh());
                    qlr.setYWH(xmDO.getSlbh());
                    qlr.setBZ(xmDO.getBz());
                    qlrList.add(qlr);
                }
            }
        }
    }

    /**
     * @param bdcCfXms
     * @param qlrList
     * @param cfQlWithXmQlrDTO
     */
    private void getCfjgQlr(List<BdcXmDO> bdcCfXms, List<ZttGyQlr> qlrList, CfQlWithXmQlrDTO cfQlWithXmQlrDTO) {
        List<BdcQlrDO> bdcQlrList = cfQlWithXmQlrDTO.getBdcQlrList();
        if (CollectionUtils.isNotEmpty(bdcQlrList)) {
            BdcQlrDO bdcQlrDO = bdcQlrList.get(0);
            ZttGyQlr cfjgQlr = new ZttGyQlr();
            cfjgQlr.setQLRMC(bdcQlrDO.getQlrmc());
            cfjgQlr.setZJZL(bdcQlrDO.getZjzl().toString());
            cfjgQlr.setZJH(bdcQlrDO.getZjh());
            cfjgQlr.setJS(CommonConstantUtils.QLRLB_QLR);
            cfjgQlr.setQLRLX(bdcQlrDO.getQlrlx().toString());
            cfjgQlr.setYWH(bdcCfXms.get(0).getSlbh());
            qlrList.add(cfjgQlr);
        }
    }

    /**
     * ????????????
     *
     * @param cfQlWithXmQlrDTO
     * @return
     */
    private QlfQlCfdj getQlfQlCfdj(CfQlWithXmQlrDTO cfQlWithXmQlrDTO, String slbh) {
        BdcCfDO bdcql = cfQlWithXmQlrDTO.getBdcql();
        BdcXmDO bdcXmDO = cfQlWithXmQlrDTO.getBdcXmDO();
        QlfQlCfdj cfdj = new QlfQlCfdj();
        if (StringUtils.isNotBlank(slbh)) {
            cfdj.setYWH(slbh);
        } else {
            cfdj.setYWH(bdcXmDO.getSlbh());
        }
        cfdj.setCFJG(bdcql.getCfjg());
        cfdj.setCFLX(bdcql.getCflx().toString());
        cfdj.setCFWJ(bdcql.getCfwj());
        cfdj.setCFWH(bdcql.getCfwh());
        cfdj.setCFQSSJ(bdcql.getCfqssj());
        cfdj.setCFJSSJ(bdcql.getCfjssj());
        cfdj.setCFFW(bdcql.getCffw());
        cfdj.setCFYY(bdcql.getCfyy());
        cfdj.setDBR(bdcql.getDbr());
        cfdj.setDJSJ(bdcql.getDjsj());
        if (StringUtils.isNotEmpty(bdcql.getJfwh())) {
            cfdj.setJFWH(bdcql.getJfwh());
        }
        cfdj.setFJ(bdcql.getFj());
        cfdj.setQXDM(bdcXmDO.getQxdm());
        cfdj.setDJJG(bdcql.getDjjg());
        return cfdj;
    }

    /**
     * @param bdcCfXm
     * @param bdcql
     * @return
     */
    private QlfQlJfdj getQlfQlJfdj(BdcXmDO bdcCfXm, BdcCfDO bdcql) {
        QlfQlJfdj jfdj = new QlfQlJfdj();
        jfdj.setYWH(bdcql.getJfywh());
        jfdj.setJFJG(bdcql.getJfjg());
        jfdj.setJFWJ(bdcql.getJfwj());
        jfdj.setJFWH(bdcql.getJfwh());
        jfdj.setJFDBR(bdcql.getJfdbr());
        jfdj.setJFDJSJ(bdcql.getJfdjsj());
        jfdj.setJFYY(bdcql.getJfyy());
        jfdj.setFJ(bdcql.getFj());
        jfdj.setQXDM(bdcCfXm.getQxdm());
        jfdj.setDJJG(bdcql.getDjjg());
        return jfdj;
    }

    /**
     * @param bdcjfXm
     * @return
     */
    private List<KttFwH> getKttFwHS(BdcXmDO bdcjfXm) {
        //????????????
        List<KttFwH> KttFwH = new ArrayList<>();
        KttFwH kttFw_h = new KttFwH();
        kttFw_h.setBDCDYH(bdcjfXm.getBdcdyh());
        kttFw_h.setYWH(bdcjfXm.getSlbh());
        kttFw_h.setZL(bdcjfXm.getZl());
        KttFwH.add(kttFw_h);
        return KttFwH;
    }

    /**
     * ????????????
     *
     * @param jfxxDto
     * @return
     */
    private CommonResponse notice(Object jfxxDto) {
        log.info("???????????????????????????????????? http????????????:{}", JSON.toJSONString(jfxxDto));
        try {
            Object response = exchangeBeanRequestService.request("yc_ythtscjfxx", jfxxDto);
            if (response != null) {
                log.info("????????????????????????????????????:{}", JSON.toJSONString(response));
                YthCommonResponse ythCommonResponse = JSON.parseObject(JSON.toJSONString(response), YthCommonResponse.class);
                if (YthCommonResponse.SUCCESS.equals(ythCommonResponse.getType())) {
                    return CommonResponse.ok();
                } else {
                    return CommonResponse.fail(ythCommonResponse.getData());
                }
            } else {
                return CommonResponse.fail("???????????????????????????????????????????????????");
            }
        } catch (Exception e) {
            return CommonResponse.fail(e.getMessage());
        } finally {

        }
    }

    /**
     * ??????????????????
     *
     * @param bdcXmDO
     * @param status
     */
    private void addModifyCzrz(BdcXmDO bdcXmDO, Integer status) {
        // ???????????????????????????
        Example example = new Example(BdcCzrzDO.class);
        example.createCriteria()
                .andEqualTo("czlx", BdcCzrzLxEnum.CZRZ_CZLX_YTHCFTS.key())
                .andEqualTo("gzlslid", bdcXmDO.getGzlslid())
        ;
        List<BdcCzrzDO> bdcCzrzDOS = entityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(bdcCzrzDOS)) {
            BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
            BeanUtils.copyProperties(bdcXmDO, bdcCzrzDO);
            bdcCzrzDO.setLcmc(bdcXmDO.getGzldymc());
            bdcCzrzDO.setCzsj(new Date());
            if (Objects.nonNull(status)) {
                bdcCzrzDO.setCzzt(status);
            }
            bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_YTHCFTS.key());
            bdcCzrzDO.setCzyy("????????????????????????????????????");
            bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
        } else {
            BdcCzrzDO bdcCzrzDO = bdcCzrzDOS.get(0);
            bdcCzrzDO.setCzzt(status);
            bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
        }
    }
}
