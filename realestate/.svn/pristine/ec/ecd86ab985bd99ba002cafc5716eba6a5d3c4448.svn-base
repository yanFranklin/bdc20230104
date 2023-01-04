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
     * 查封业务办结推送一体化信息平台
     *
     * @param processInsId
     * @param currentUserName
     */
    @Override
    public CommonResponse ythcfxxts(String processInsId, String currentUserName) {
        //判断是否为查封业务
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        List<BdcXmDO> bdcCfXms = new ArrayList<>();
        Map<String, BdcCfDO> bdcCfQls = new HashMap<>();
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            return CommonResponse.fail("参数错误");
        }
        for (BdcXmDO bdcXmDO : bdcXmDOS) {
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
            if (bdcQl instanceof BdcCfDO) {
                bdcCfXms.add(bdcXmDO);
                bdcCfQls.put(bdcXmDO.getXmid(), (BdcCfDO) bdcQl);
            }
        }

        if (CollectionUtils.isEmpty(bdcCfXms)) {
            return CommonResponse.fail("参数错误");
        }
        //添加一个无状态的记录
        addModifyCzrz(bdcCfXms.get(0), -2);

        CfxxDto cfxxDto = new CfxxDto();
        cfxxDto.setYWBH(bdcCfXms.get(0).getSlbh());
        cfxxDto.setSJYWMC("查封");
        getCfxx(bdcCfXms, cfxxDto, bdcCfQls);

        //推送数据
        CommonResponse commonResponse = notice(cfxxDto);
        //记录结果
        if (commonResponse.isSuccess()) {
            addModifyCzrz(bdcCfXms.get(0), CommonConstantUtils.SF_S_DM);
        } else {
            addModifyCzrz(bdcCfXms.get(0), CommonConstantUtils.SF_F_DM);
        }
        return commonResponse;
    }

    /**
     * 解封业务办结推送一体化信息平台
     *
     * @param processInsId
     * @param currentUserName
     */
    @Override
    public CommonResponse ythjfxxts(String processInsId, String currentUserName) {
        //判断是否为解封业务
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        List<BdcXmDO> bdcJfXms = new ArrayList<>();
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            return CommonResponse.fail("参数错误");
        }
        //根据单元号进行去重
        Map<String, List<BdcXmDO>> bdcXmGroup = bdcXmDOS
                .stream()
                .collect(Collectors.groupingBy(BdcXmDO::getBdcdyh));
        for (Map.Entry<String, List<BdcXmDO>> stringListEntry : bdcXmGroup.entrySet()) {
            BdcQl bdcQl = bdcQllxFeignService.queryBdcYqlxx(stringListEntry.getValue().get(0).getXmid());
            if (bdcQl instanceof BdcCfDO) {
                //原权利是查封 -- 会出现 一个解封文号对一个单元号上的多次查封批量解封的情况，此时取其中一个即可
                bdcJfXms.add(stringListEntry.getValue().get(0));
            }
        }

        if (CollectionUtils.isEmpty(bdcJfXms)) {
            return CommonResponse.fail("参数错误");
        }
        //添加一个无状态的记录
        addModifyCzrz(bdcJfXms.get(0), -2);

        CfJfxxDto jfxxDto = new CfJfxxDto();
        jfxxDto.setYWBH(bdcJfXms.get(0).getSlbh());
        jfxxDto.setSJYWMC("解封");
        List<Fwxx> fwxxList = getFwxxes(bdcJfXms);
        jfxxDto.setFwxx(fwxxList);

        //推送数据
        CommonResponse commonResponse = notice(jfxxDto);
        //记录结果
        if (commonResponse.isSuccess()) {
            addModifyCzrz(bdcJfXms.get(0), CommonConstantUtils.SF_S_DM);
        } else {
            addModifyCzrz(bdcJfXms.get(0), CommonConstantUtils.SF_F_DM);
        }
        return commonResponse;
    }

    /**
     * 手动推送
     *
     * @param processInsId
     */
    @Override
    public CommonResponse ythxxsdts(String processInsId) {
        try {
            log.info("执行盐城一体化查解封推送,执行手动推送任务{}", processInsId);
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(processInsId);
            List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isEmpty(bdcXmDOS)) {
                return CommonResponse.fail("参数错误");
            }
            //判断是查封还是解封
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDOS.get(0).getXmid());
            if (bdcQl instanceof BdcCfDO) {
                return ythcfxxts(processInsId, "");
            } else {
                return ythjfxxts(processInsId, "");
            }
        } catch (Exception e) {
            log.info("执行盐城一体化查解封推送,执行手动推送任务{}失败,原因{}",
                    processInsId, e.getMessage());
            e.printStackTrace();
            return CommonResponse.fail("参数错误");
        }
    }

    /**
     * 查封信息
     *
     * @param bdcCfXms
     * @param cfxxDto
     * @return
     */
    private CfxxDto getCfxx(List<BdcXmDO> bdcCfXms, CfxxDto cfxxDto, Map<String, BdcCfDO> bdcCfQls) {
        //房屋信息
        List<KttFwH> KttFwH = new ArrayList<>();
        List<QlfQlCfdj> QlfQlCfdj = new ArrayList<>();
        List<ZttGyQlr> qlrList = new ArrayList<>();
        //字典信息
        List<Map> zdMap = bdcZdFeignService.queryBdcZd("qlrlb");

        //查封信息 -- 只看当前这一手的查封,同一个工作流对应的查封信息是一样的,所以只需要取一个就行
        CfQlWithXmQlrDTO cfQlWithXmQlrDTO = new CfQlWithXmQlrDTO();
        //解封机关设置为第一个权利人
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

        //将查封机关设置为第一个权利人
        getCfjgQlr(bdcCfXms, qlrList, cfQlWithXmQlrDTO);
        //房屋信息和权利人信息
        for (BdcXmDO bdcCfXm : bdcCfXms) {
            KttFwH kttFw_h = new KttFwH();
            kttFw_h.setBDCDYH(bdcCfXm.getBdcdyh());
            kttFw_h.setYWH(bdcCfXm.getSlbh());
            kttFw_h.setZL(bdcCfXm.getZl());
            KttFwH.add(kttFw_h);

            /**
             *
             * 1、无任何产权或预告登记信息，取权籍库的zd_qlr；
             * 2、如果只有产权登记信息，取产权的权利人信息；
             * 3、如果只有预告登记信息，取预告的的权利人信息；
             * 4、如果预告和产权都有登记信息，取产权和预告的权利人信息
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
     * 房屋信息
     *
     * @param bdcCfXms
     * @return
     */
    private List<Fwxx> getFwxxes(List<BdcXmDO> bdcCfXms) {
        List<Fwxx> fwxxList = new ArrayList<>();
        //字典信息
        List<Map> zdMap = bdcZdFeignService.queryBdcZd("qlrlb");
        //处理解封数据
        for (BdcXmDO bdcjfXm : bdcCfXms) {

            Fwxx fwxx = new Fwxx();
            List<QlfQlJfdj> QlfQlJfdj = new ArrayList<>();
            List<QlfQlCfdj> QlfQlCfdj = new ArrayList<>();
            List<ZttGyQlr> qlrList = new ArrayList<>();
            List<KttFwH> KttFwH = new ArrayList<>();

            //房屋信息
            KttFwH = getKttFwHS(bdcjfXm);
            fwxx.setKTT_FW_H(KttFwH);

            //查封信息 解封信息  -- 只取当前这一手的数据
            //取到对于查封的权利信息
            BdcCfDO bdcql = (BdcCfDO) bdcQllxFeignService.queryBdcYqlxx(bdcjfXm.getXmid());
            if (StringUtils.isEmpty(bdcql.getJfwh())) {
                continue;
            }
            //解封信息
            QlfQlJfdj jfdj = getQlfQlJfdj(bdcjfXm, bdcql);
            QlfQlJfdj.add(jfdj);

            //解封机关设置为第一个权利人
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            bdcQlrQO.setXmid(bdcjfXm.getXmid());
            List<BdcQlrDO> qlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            CfQlWithXmQlrDTO jfQlWithXmQlrDTO = new CfQlWithXmQlrDTO();
            jfQlWithXmQlrDTO.setBdcql(bdcql);
            jfQlWithXmQlrDTO.setBdcXmDO(bdcjfXm);
            jfQlWithXmQlrDTO.setBdcQlrList(qlrDOList);
            //解封暂时不需要查机构
            //getCfjgQlr(Collections.singletonList(bdcjfXm), qlrList, jfQlWithXmQlrDTO);


            //获取解封文号下所有的查封文号
            Example example = new Example(BdcCfDO.class);
            example.createCriteria()
                    .andEqualTo("jfwh", bdcql.getJfwh())
                    .andEqualTo("bdcdyh", bdcql.getBdcdyh());
            List<BdcCfDO> bdcCfDOS = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcCfDOS)) {
                for (BdcCfDO cfxx : bdcCfDOS) {
                    //获取查封对应的项目信息
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
             * 1、无任何产权或预告登记信息，取权籍库的zd_qlr；
             * 2、如果只有产权登记信息，取产权的权利人信息；
             * 3、如果只有预告登记信息，取预告的的权利人信息；
             * 4、如果预告和产权都有登记信息，取产权和预告的权利人信息
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
     * 获取其他权利人
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
        //通过单元号获取其他权利人
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
        log.info("查封解封权利人信息 getYgOrFdcqQlr : {}", JSON.toJSONString(qlrList));
        //没有取到除了查封机关以外的权利人，尝试重宗地中获取
        if (CollectionUtils.isEmpty(currentQlrList)) {
            //宗地权利人信息
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
     * 查封信息
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
        //房屋信息
        List<KttFwH> KttFwH = new ArrayList<>();
        KttFwH kttFw_h = new KttFwH();
        kttFw_h.setBDCDYH(bdcjfXm.getBdcdyh());
        kttFw_h.setYWH(bdcjfXm.getSlbh());
        kttFw_h.setZL(bdcjfXm.getZl());
        KttFwH.add(kttFw_h);
        return KttFwH;
    }

    /**
     * 提交请求
     *
     * @param jfxxDto
     * @return
     */
    private CommonResponse notice(Object jfxxDto) {
        log.info("查解封信息推送一体化平台 http请求参数:{}", JSON.toJSONString(jfxxDto));
        try {
            Object response = exchangeBeanRequestService.request("yc_ythtscjfxx", jfxxDto);
            if (response != null) {
                log.info("查解封信息推送一体化平台:{}", JSON.toJSONString(response));
                YthCommonResponse ythCommonResponse = JSON.parseObject(JSON.toJSONString(response), YthCommonResponse.class);
                if (YthCommonResponse.SUCCESS.equals(ythCommonResponse.getType())) {
                    return CommonResponse.ok();
                } else {
                    return CommonResponse.fail(ythCommonResponse.getData());
                }
            } else {
                return CommonResponse.fail("查解封信息推送一体化平台无返回结果");
            }
        } catch (Exception e) {
            return CommonResponse.fail(e.getMessage());
        } finally {

        }
    }

    /**
     * 添加操作日志
     *
     * @param bdcXmDO
     * @param status
     */
    private void addModifyCzrz(BdcXmDO bdcXmDO, Integer status) {
        // 构建操作日志实体类
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
            bdcCzrzDO.setCzyy("查解封信息推送一体化平台");
            bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
        } else {
            BdcCzrzDO bdcCzrzDO = bdcCzrzDOS.get(0);
            bdcCzrzDO.setCzzt(status);
            bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
        }
    }
}
