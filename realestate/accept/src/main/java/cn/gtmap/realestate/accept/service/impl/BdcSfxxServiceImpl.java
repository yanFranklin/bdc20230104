package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.gtc.workflow.clients.manage.ProcessInsCustomExtendClient;
import cn.gtmap.realestate.accept.config.BdcSfxxConfig;
import cn.gtmap.realestate.accept.core.mapper.BdcSlSfxmMapper;
import cn.gtmap.realestate.accept.core.mapper.BdcSlSfxxMapper;
import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.core.thread.BdcAutoCountSfxxSfxmThread;
import cn.gtmap.realestate.accept.core.thread.HxPljfxxThread;
import cn.gtmap.realestate.accept.service.BdcSfService;
import cn.gtmap.realestate.accept.service.BdcSfxxService;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.sfxx.DzpjResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sfxx.JfqrResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sfxx.PjzfResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.QuerySwxxHsxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.QuerySwxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDyaqQlrDTO;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSfxxCzQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJfblQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxmQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDyaQo;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtFphFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXtJgFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlSfxmVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcdjjfglVO;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BdcSfxxServiceImpl implements BdcSfxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSfxxServiceImpl.class);

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcSlSfxxService bdcSlSfxxService;
    @Autowired
    BdcSlSfxmService bdcSlSfxmService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    protected UserManagerUtils userManagerUtils;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcLcTsjfGxService bdcLcTsjfGxService;
    @Autowired
    BdcSlJbxxService bdcSlJbxxService;
    @Autowired
    ProcessInsCustomExtendClient processInsCustomExtendClient;
    @Autowired
    BdcXtFphFeignService bdcXtFphFeignService;
    @Autowired
    BdcSfService bdcSfService;
    @Autowired
    BdcXtJgFeignService bdcXtJgFeignService;
    @Autowired
    BdcBdcdyFeignService bdcBdcdyFeignService;
    @Autowired
    BdcSlCxcsService bdcSlCxcsService;
    @Autowired
    BdcSlxxHxService bdcSlxxHxService;
    @Autowired
    ThreadEngine threadEngine;
    @Autowired
    BdcSlSfxxMapper bdcSlSfxxMapper;
    @Autowired
    BdcSlSfxmMapper bdcSlSfxmMapper;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;
    @Autowired
    BdcSlFpxxService bdcSlFpxxService;
    @Autowired
    BdcSfxxConfig bdcSfxxConfig;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcSlHsxxService bdcSlHsxxService;

    /**
     * 土地使用金的收费代码
     */
    @Value("${sfxx.sfxm.tdsyqsfxmdm:}")
    private String tdsyqsfxmdm;

    /**
     * 作废二维码
     */
    @Override
    public void abolishEwm(BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isNotBlank(bdcSfxxCzQO.getSfxxid())) {
            this.abolishEwmBySfxxid(bdcSfxxCzQO.getSfxxid(), bdcSfxxCzQO.isSftdsyj());
        }else if(StringUtils.isNotBlank(bdcSfxxCzQO.getGzlslid())){
            BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
            bdcSlSfxxDO.setGzlslid(bdcSfxxCzQO.getGzlslid());
            if (StringUtils.isNotBlank(bdcSfxxCzQO.getXmid())) {
                bdcSlSfxxDO.setXmid(bdcSfxxCzQO.getXmid());
            }
            List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxService.queryBdcSlSfxx(bdcSlSfxxDO);
            if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)){
                for(BdcSlSfxxDO sfxx : bdcSlSfxxDOList){
                    this.abolishEwmBySfxxid(sfxx.getSfxxid(), bdcSfxxCzQO.isSftdsyj());
                }
            }
        }
    }

    private void abolishEwmBySfxxid(String sfxxid, boolean sftdsyj){
        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);
        bdcSlSfxmDOList = this.filterSfxmBySftdsyz(bdcSlSfxmDOList, sftdsyj);
        // 请求废除二维码信息
        this.requestAbolishEwm(bdcSlSfxmDOList.get(0).getSfxmid(), bdcSlSfxmDOList.get(0).getJfsbm());
        // 清空收费项目缴费书编码
        this.cleanSfxmJfsbmxx(bdcSlSfxmDOList);

        // 更新收费信息收费状态
        BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
        bdcSlSfxxDO.setSfxxid(sfxxid);
        bdcSlSfxxDO.setSfzt(BdcSfztEnum.WJF.key());
        this.bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
    }


    @Override
    public void abolishEwmDyapl(BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isNotBlank(bdcSfxxCzQO.getGzlslid())){
            List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = this.bdcLcTsjfGxService.listLcTsjfGx(bdcSfxxCzQO.getGzlslid());
            if(CollectionUtils.isEmpty(bdcLcTsjfGxDOList)){
                throw new AppException(ErrorCode.CUSTOM, "未获取到批量缴费信息");
            }

            List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcLcTsjfGxDOList.get(0).getSfxxid());
            if(CollectionUtils.isEmpty(bdcSlSfxmDOList)){
                throw new AppException(ErrorCode.CUSTOM, "未获取到收费项目信息");
            }
            //验证是否已完成缴费
            if(Objects.equals(bdcSlSfxmDOList.get(0).getSfzt(), BdcSfztEnum.YJF.key())){
                throw new AppException(ErrorCode.CUSTOM, "已缴费不可废除二维码,请先退费");
            }else{
                bdcSfxxCzQO.setTsid(bdcLcTsjfGxDOList.get(0).getTsid());
                bdcSfxxCzQO.setSfxxid(bdcLcTsjfGxDOList.get(0).getSfxxid());
                BdcQuerySfztDTO bdcQuerySfztDTO = this.dyaplQuerySfztByTsid(bdcSfxxCzQO);
                if(BdcSfztEnum.YJF.key().equals(bdcQuerySfztDTO.getSfzt())){
                    throw new AppException(ErrorCode.CUSTOM, "已缴费不可废除二维码,请先退费");
                }else{
                    // 作废二维码
                    this.requestAbolishEwm(bdcLcTsjfGxDOList.get(0).getTsid(), bdcSlSfxmDOList.get(0).getJfsbm());
                    // 将抵押批量关联的所有的项目收费项目二维码置空
                    for(BdcLcTsjfGxDO bdcLcTsjfGxDO : bdcLcTsjfGxDOList){
                        this.cleanSfxmJfsbmxx(this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcLcTsjfGxDO.getSfxxid()));

                        // 更新收费信息收费状态
                        BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
                        bdcSlSfxxDO.setSfxxid(bdcLcTsjfGxDO.getSfxxid());
                        bdcSlSfxxDO.setSfzt(BdcSfztEnum.WJF.key());
                        this.bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                    }
                }
            }
        }
    }

    /**
     * 请求作废二维码接口
     */
    private void requestAbolishEwm(String tsid, String jfsbm){
        Map paramMap = new HashMap();
        paramMap.put("sfxxid", tsid);
        paramMap.put("billNo", jfsbm);
        paramMap.put("requesttime", DateUtils.formateTime(new Date(), DateUtils.DATE_FORMATYMDHMS));
        LOGGER.info(" 调用作废接口入参:{}", paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface("jfpt_pjzf", paramMap);
        LOGGER.info("作废接口返回值{}", response.toString());
    }

    /**
     * 清空收费项目中的缴费书编码和缴费书编码URL信息
     */
    private void cleanSfxmJfsbmxx(List<BdcSlSfxmDO> bdcSlSfxmDOList){
        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
            for(BdcSlSfxmDO sfxm : bdcSlSfxmDOList){
                sfxm.setJfsewmurl(StringUtils.EMPTY);
                sfxm.setJfsbm(StringUtils.EMPTY);
                sfxm.setSfzt(BdcSfztEnum.WJF.key());
            }
            this.bdcSlSfxmService.batchUpdateBdcSlSfxm(bdcSlSfxmDOList);
        }
    }

    /**
     * 根据是否土地使用金，筛选收费项目信息
     * @param bdcSlSfxmDOList 收费项目集合
     * @param sftdsyj 是否土地使用金
     * @return 收费项目集合
     */
    private List<BdcSlSfxmDO> filterSfxmBySftdsyz(List<BdcSlSfxmDO> bdcSlSfxmDOList, boolean sftdsyj){
        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
            if (sftdsyj) {
                // 土地收益金的收费项目信息
                bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdsyqsfxmdm)).collect(Collectors.toList());
            } else {
                // 登记费、工本费的收费项目信息
                bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> !StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdsyqsfxmdm)).collect(Collectors.toList());
            }
        }
        return bdcSlSfxmDOList;
    }

    /**
     * 根据收费信息ID查询收费信息并推送待缴费信息
     */
    @Override
    public BdcTsdjfxxResponseDTO cxtsdjfxx(BdcSfxxCzQO bdcSfxxCzQO) {
        BdcTsdjfxxResponseDTO bdcTsdjfxxResponseDTO = new BdcTsdjfxxResponseDTO();
        if(StringUtils.isBlank(bdcSfxxCzQO.getSfxxid())){
            return bdcTsdjfxxResponseDTO;
        }
        // 查询收费信息
        List<BdcSfxxDTO> bdcSfxxDTOList = this.queryBdcSfxx("", "", Arrays.asList(bdcSfxxCzQO.getSfxxid()), null, bdcSfxxCzQO.isSftdsyj());
        if (CollectionUtils.isEmpty(bdcSfxxDTOList)) {
            throw new AppException("未查询到收费信息");
        }
        //获取缴费人信息
        BdcSfxxDTO bdcSfxxDTO = bdcSfxxDTOList.get(0);
        if(CollectionUtils.isNotEmpty(bdcSfxxDTO.getBdcSlSfxmVOList())){
            for(BdcSlSfxmVO bdcSlSfxmVO : bdcSfxxDTO.getBdcSlSfxmVOList()){
                if(StringUtils.isNotBlank(bdcSlSfxmVO.getJfsbm())){
                    throw new AppException(ErrorCode.MISSING_ARG, "已生成缴费二编码，请勿再次生成。");
                }
            }
        }
        if(bdcSfxxDTO.getBdcSlSfxxDO() != null &&StringUtils.isNotBlank(bdcSfxxDTO.getBdcSlSfxxDO().getQlrlb())){
            String xmid = bdcSfxxDTO.getBdcSlSfxxDO().getXmid();
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcSfxxDTO.getBdcSlSfxxDO().getGzlslid());
            if (StringUtils.isBlank(xmid) && StringUtils.isNotBlank(bdcSfxxDTO.getBdcSlSfxxDO().getGzlslid())) {
                if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                    xmid = bdcXmDTOList.get(0).getXmid();
                }
            }
            if (StringUtils.isNotBlank(xmid)) {
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(xmid);
                bdcQlrQO.setQlrlb(bdcSfxxDTO.getBdcSlSfxxDO().getQlrlb());
                List<BdcQlrDO> qlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if (CollectionUtils.isNotEmpty(qlrDOList)) {
                    // 获取权利人电话、号码
                    BdcQlrDO qlrxx = this.getQlrDhAndZjh(qlrDOList);
                    DsfSfxxDTO dsfSfxxDTO = bdcSfxxDTO.getDsfSfxxDTO() != null ? bdcSfxxDTO.getDsfSfxxDTO() : new DsfSfxxDTO();
                    dsfSfxxDTO.setJfrdh(qlrxx.getDh());
                    dsfSfxxDTO.setJfrzjh(qlrxx.getZjh());
                    //接口传入受理编号
                    if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                        String xslbh = "";
                        List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = bdcLcTsjfGxService.listLcTsjfGxBySfxxid(bdcSfxxCzQO.getSfxxid());
                        if (CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)) {
                            String gzlslid = bdcLcTsjfGxDOList.get(0).getGzlslid();
                            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
                            if (Objects.nonNull(bdcSlJbxxDO)) {
                                xslbh = bdcSlJbxxDO.getSlbh();
                            }
                        }
                        if (StringUtils.equals(CommonConstantUtils.SF_TSLY_SFYM, bdcSfxxCzQO.getTsly())) {
                            dsfSfxxDTO.setSftype("0");
                            dsfSfxxDTO.setSlbh("受理编号:" + bdcXmDTOList.get(0).getSlbh());
                        } else {
                            dsfSfxxDTO.setSftype("1");
                            dsfSfxxDTO.setSlbh("大受理编号:" + xslbh + ";小受理编号:" + bdcXmDTOList.get(0).getSlbh());
                        }
                        dsfSfxxDTO.setQxdm(bdcXmDTOList.get(0).getQxdm());
                    } else {
                        dsfSfxxDTO.setSlbh("受理编号:");
                    }
                    if (StringUtils.isBlank(bdcSfxxCzQO.getPjdm())) {
                        bdcSfxxCzQO.setPjdm(CommonConstantUtils.SF_PJDM);
                    }
                    dsfSfxxDTO.setPjdm(bdcSfxxCzQO.getPjdm());
                    // 缴费人电话取收费信息的jfrdh
                    if (StringUtils.isNotBlank(bdcSfxxDTO.getBdcSlSfxxDO().getJfrdh())) {
                        dsfSfxxDTO.setJfrdh(bdcSfxxDTO.getBdcSlSfxxDO().getJfrdh());
                    }
                    dsfSfxxDTO.setJfrxm(bdcSfxxDTO.getBdcSlSfxxDO().getJfrxm());
                    // 缴费人电话取收费信息的jfrdh
                    if (StringUtils.isNotBlank(bdcSfxxDTO.getBdcSlSfxxDO().getJfrdh())) {
                        dsfSfxxDTO.setJfrdh(bdcSfxxDTO.getBdcSlSfxxDO().getJfrdh());
                    }
                    bdcSfxxDTO.setDsfSfxxDTO(dsfSfxxDTO);
                    // 设置推送收费信息的唯一标识为 收费项目ID
                    if(CollectionUtils.isNotEmpty(bdcSfxxDTO.getBdcSlSfxmVOList())){
                        dsfSfxxDTO.setSfxmid(bdcSfxxDTO.getBdcSlSfxmVOList().get(0).getSfxmid());
                    }
                    // 常州区县代码是320499，让选择是市本级还是经开区
                    if(StringUtils.isNotBlank(bdcSfxxCzQO.getQxdm())){
                        dsfSfxxDTO.setQxdm(bdcSfxxCzQO.getQxdm());
                    }
                    // 处理收费项目代码，添加qxdm信息
                    this.handlerSfxmdmByQxdm(bdcSfxxDTO);
                }
            }
        }

        // 根据qxdm 配置对应的税务同步接口
        String beanName = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm("fs.config.ts.beanName", "fs_jftb", bdcSfxxDTO.getDsfSfxxDTO().getQxdm());
        LOGGER.info("生成二维码sfxxid{}, qxdm：{}, beanName: {}, 调用接口入参{}", bdcSfxxDTO.getDsfSfxxDTO().getSfxmid(), bdcSfxxDTO.getDsfSfxxDTO().getQxdm(), beanName, JSON.toJSONString(bdcSfxxDTO));
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, bdcSfxxDTO);
        if (response != null) {
            LOGGER.info("收费项目ID:{},缴费平台推送待缴信息接口调用成功,返回结果：{}", bdcSfxxDTO.getDsfSfxxDTO().getSfxmid(), response);
            if(StringUtils.equals(beanName , "fs_jftb")){
                bdcTsdjfxxResponseDTO = JSONObject.parseObject(JSONObject.toJSONString(response), BdcTsdjfxxResponseDTO.class);
            }else{
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(response));
                bdcTsdjfxxResponseDTO = JSONObject.parseObject(jsonObject.getString("data"), BdcTsdjfxxResponseDTO.class);
            }
            dealTsdjfxxForSfxm(bdcTsdjfxxResponseDTO, bdcSfxxCzQO.getSfxxid(), bdcSfxxCzQO.isSftdsyj());
        }
        return bdcTsdjfxxResponseDTO;
    }

    /**
     * 处理收费项目代码，添加区县代码内容
     * <p>收费项目拼接成 sfxmdm@qxdm </p>
     */
    private void handlerSfxmdmByQxdm(BdcSfxxDTO bdcSfxxDTO){
        if(Objects.nonNull(bdcSfxxDTO.getDsfSfxxDTO())){
            String qxdm = bdcSfxxDTO.getDsfSfxxDTO().getQxdm();
            if(StringUtils.isNotBlank(qxdm) && CollectionUtils.isNotEmpty(bdcSfxxDTO.getBdcSlSfxmVOList())){
                for(BdcSlSfxmVO bdcSlSfxmVO : bdcSfxxDTO.getBdcSlSfxmVOList()){
                    bdcSlSfxmVO.setSfxmdmAndQxdm(bdcSlSfxmVO.getSfxmdm() + "@" + qxdm);
                }
            }
        }
    }

    /**
     * 获取权利人证件号，获取逻辑：先权利人、在代理人
     */
    private BdcQlrDO getQlrDhAndZjh(List<BdcQlrDO> bdcQlrDOList){
        if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
            BdcQlrDO bdcQlrDO = bdcQlrDOList.get(0);
            if(StringUtils.isBlank(bdcQlrDO.getDh())){
                List<String> dhList = bdcQlrDOList.stream().filter(t -> StringUtils.isNotBlank(t.getDh())).map(BdcQlrDO::getDh)
                        .collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(dhList)){
                    bdcQlrDO.setDh(dhList.get(0));
                }else{
                    dhList = bdcQlrDOList.stream().filter(t-> StringUtils.isNotBlank(t.getDlrdh())).map(BdcQlrDO::getDlrdh)
                            .collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(dhList)){
                        bdcQlrDO.setDh(dhList.get(0));
                    }
                }
            }
            if(StringUtils.isBlank(bdcQlrDO.getZjh())){
                List<String> zjhList = bdcQlrDOList.stream().filter(t -> StringUtils.isNotBlank(t.getZjh())).map(BdcQlrDO::getZjh)
                        .collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(zjhList)){
                    bdcQlrDO.setZjh(zjhList.get(0));
                }else{
                    zjhList = bdcQlrDOList.stream().filter(t-> StringUtils.isNotBlank(t.getDlrzjh())).map(BdcQlrDO::getDlrzjh)
                            .collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(zjhList)){
                        bdcQlrDO.setZjh(zjhList.get(0));
                    }
                }
            }
            return bdcQlrDO;
        }
        return new BdcQlrDO();
    }

    /**
     * 查询收费信息
     */
    private List<BdcSfxxDTO> queryBdcSfxx(String xmid, String sqrlb, List<String> sfxxidList, Integer sfyj, boolean sftdsyj) {
        List<BdcSfxxDTO> bdcSfxxDTOList = new ArrayList<>();
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxPl(xmid, sqrlb, sfxxidList, sfyj, true);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                BdcSfxxDTO bdcSfxxDTO = new BdcSfxxDTO();
                List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                    if (sftdsyj) {
                        bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdsyqsfxmdm)).collect(Collectors.toList());
                    } else {
                        bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> !StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdsyqsfxmdm)).collect(Collectors.toList());
                    }

                    bdcSlSfxxDO.setHj(NumberUtil.formatDigit(bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> Objects.nonNull(bdcSlSfxmDO.getSsje())).mapToDouble(BdcSlSfxmDO::getSsje).sum(), 2));
                    bdcSfxxDTO.setBdcSlSfxxDO(bdcSlSfxxDO);
                    //处理单价数据
                    List<BdcSlSfxmVO> bdcSlSfxmVOList = new ArrayList<>(bdcSlSfxmDOList.size());
                    for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {
                        BdcSlSfxmVO bdcSlSfxmVO = new BdcSlSfxmVO();
                        BeanUtils.copyProperties(bdcSlSfxmDO, bdcSlSfxmVO);
                        if (Objects.isNull(bdcSlSfxmDO.getSfxmdj())) {
                            double dj = bdcSlSfxmDO.getYsje() / (Objects.nonNull(bdcSlSfxmDO.getSl()) ? bdcSlSfxmDO.getSl() : 1);
                            bdcSlSfxmVO.setSfxmdj(dj);
                        } else {
                            bdcSlSfxmVO.setSfxmdj(bdcSlSfxmDO.getSfxmdj());
                        }
                        bdcSlSfxmVOList.add(bdcSlSfxmVO);
                    }
                    bdcSfxxDTO.setBdcSlSfxmVOList(bdcSlSfxmVOList);
                }
                DsfSfxxDTO dsfSfxxDTO = new DsfSfxxDTO();
                dsfSfxxDTO.setUserName(userManagerUtils.getCurrentUserName());
                bdcSfxxDTO.setDsfSfxxDTO(dsfSfxxDTO);
                bdcSfxxDTOList.add(bdcSfxxDTO);
            }
        }
        return bdcSfxxDTOList;
    }

    /**
     * 处理推送缴费信息返回结果信息
     * @param bdcTsdjfxxResponseDTO 推送返回信息
     * @param sfxxid 收费信息
     * @param sftdsyj 是否含有土地收益金
     */
    private void dealTsdjfxxForSfxm(BdcTsdjfxxResponseDTO bdcTsdjfxxResponseDTO, String sfxxid, boolean sftdsyj){
        if (bdcTsdjfxxResponseDTO != null &&StringUtils.isNotBlank(sfxxid)
                &&(StringUtils.isNotBlank(bdcTsdjfxxResponseDTO.getJfsbm()) ||StringUtils.isNotBlank(bdcTsdjfxxResponseDTO.getJfsewmurl()))) {
            List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);
            if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
                if (sftdsyj) {
                    bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdsyqsfxmdm)).collect(Collectors.toList());
                } else {
                    bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> !StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdsyqsfxmdm)).collect(Collectors.toList());
                }
            }
            bdcSlSfxmDOList.forEach(t->{
                t.setJfsbm(bdcTsdjfxxResponseDTO.getJfsbm());
                t.setJfsewmurl(bdcTsdjfxxResponseDTO.getJfsewmurl());
                t.setJkmsj(new Date());
            });
            // 批量更新收费项目的缴款码信息
            this.entityMapper.batchSaveSelective(bdcSlSfxmDOList);
        }
    }

    /**
     * 查询财政收费状态
     */
    @Override
    public BdcQuerySfztDTO querySfzt(BdcSfxxCzQO bdcSfxxCzQO) {
        BdcQuerySfztDTO bdcQuerySfztDTO = new BdcQuerySfztDTO();
        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSfxxCzQO.getSfxxid());
        // 根据是否土地收益金，过滤收费项目。
        bdcSlSfxmDOList = this.filterSfxmBySftdsyz(bdcSlSfxmDOList, bdcSfxxCzQO.isSftdsyj());
        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
            BdcSlSfxmDO bdcSlSfxmDO = bdcSlSfxmDOList.get(0);
            if(StringUtils.isNotBlank(bdcSlSfxmDO.getJfsbm())){
                String qxdm = this.getQxdm(bdcSfxxCzQO.getSfxxid());
                String beanName = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm("fs.config.jfztcx.beanName", "jfpt_jfztcx", qxdm);
                Map<String, Object> paramMap = new HashMap<>(4);
                paramMap.put("billNo", bdcSlSfxmDO.getJfsbm());
                paramMap.put("sfxxid", bdcSlSfxmDO.getSfxmid());
                paramMap.put("requesttime", DateUtils.formateTime(new Date(), DateUtils.DATE_FORMATYMDHMS));
                paramMap.put("qxdm", qxdm);
                Object response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
                LOGGER.info("收费信息ID:{},获取财政收费状态接口调用成功,返回结果：{}", bdcSlSfxmDO.getSfxmid(), JSON.toJSONString(response));
                if (response != null) {
                    bdcQuerySfztDTO = JSONObject.parseObject(JSONObject.toJSONString(response), BdcQuerySfztDTO.class);
                    if (bdcQuerySfztDTO != null && bdcQuerySfztDTO.getSfzt() != null) {
                        bdcQuerySfztDTO.setSfztMc(BdcSfztEnum.getNameByKey(bdcQuerySfztDTO.getSfzt()));
                        // 更新收费项目的收费状态
                        this.modifySfxmSfzt(bdcQuerySfztDTO.getSfzt(), bdcSlSfxmDOList);

                        // 同步收费信息表收费状态
                        this.syncSfxxSfzt(bdcSfxxCzQO.getSfxxid(), bdcQuerySfztDTO.getZfsj());
                    }
                }
            }else{
                throw new AppException("未获取到对应的缴费编号,请检查是否已经推送缴费信息");
            }
        }
        return bdcQuerySfztDTO;
    }

    // 获取区县代码
    private String getQxdm(String sfxxid){
        BdcSlSfxxDO bdcSlSfxxDO = this.bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfxxid);
        if(Objects.nonNull(bdcSlSfxxDO) && StringUtils.isNotBlank(bdcSlSfxxDO.getGzlslid())){
            List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcSlSfxxDO.getGzlslid());
            if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                return bdcXmDTOList.get(0).getQxdm();
            }
        }
        return "";
    }

    @Override
    public BdcQuerySfztDTO dyaplQuerySfztByTsid(BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isBlank(bdcSfxxCzQO.getTsid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到推送信息ID");
        }
        BdcQuerySfztDTO bdcQuerySfztDTO = new BdcQuerySfztDTO();
        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSfxxCzQO.getSfxxid());
        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
            BdcSlSfxmDO bdcSlSfxmDO = bdcSlSfxmDOList.get(0);
            if(StringUtils.isNotBlank(bdcSlSfxmDO.getJfsbm())){
                // 根据qxdm获取对应的缴费查询的beanName
                String qxdm = this.getQxdm(bdcSfxxCzQO.getSfxxid());
                String beanName = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm("fs.config.jfztcx.beanName", "jfpt_jfztcx", qxdm);
                Map<String, Object> paramMap = new HashMap<>(4);
                paramMap.put("billNo", bdcSlSfxmDO.getJfsbm());
                paramMap.put("sfxxid", bdcSfxxCzQO.getTsid());
                paramMap.put("requesttime", DateUtils.formateTime(new Date(), DateUtils.DATE_FORMATYMDHMS));
                paramMap.put("qxdm", qxdm);
                Object response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
                LOGGER.info("收费信息ID:{},获取财政收费状态接口调用成功,返回结果：{}", bdcSlSfxmDO.getSfxmid(), JSON.toJSONString(response));
                if (response != null) {
                    bdcQuerySfztDTO = JSONObject.parseObject(JSONObject.toJSONString(response), BdcQuerySfztDTO.class);
                    if (bdcQuerySfztDTO != null && bdcQuerySfztDTO.getSfzt() != null) {
                        bdcQuerySfztDTO.setSfztMc(BdcSfztEnum.getNameByKey(bdcQuerySfztDTO.getSfzt()));

                        // 批量更新状态
                        BdcLcTsjfGxDO queryParam = new BdcLcTsjfGxDO();
                        queryParam.setTsid(bdcSfxxCzQO.getTsid());
                        queryParam.setGzlslid(bdcSfxxCzQO.getGzlslid());
                        List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = this.bdcLcTsjfGxService.listLcTsjfGxByParam(queryParam);
                        for(BdcLcTsjfGxDO bdcLcTsjfGxDO : bdcLcTsjfGxDOList){
                            if(StringUtils.isNotBlank(bdcLcTsjfGxDO.getSfxxid())){
                                List<BdcSlSfxmDO> sfxmList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcLcTsjfGxDO.getSfxxid());
                                // 更新收费项目的收费状态
                                this.modifySfxmSfzt(bdcQuerySfztDTO.getSfzt(), sfxmList);
                                // 同步收费信息表收费状态
                                this.syncSfxxSfzt(bdcLcTsjfGxDO.getSfxxid(), bdcQuerySfztDTO.getZfsj());
                            }
                        }
                    }
                }
            }else{
                throw new AppException("未获取到对应的缴费编号,请检查是否已经推送缴费信息");
            }
        }
        return bdcQuerySfztDTO;
    }

    /**
     * 更新收费项目收费状态
     */
    private void modifySfxmSfzt(Integer sfzt, List<BdcSlSfxmDO> bdcSlSfxmDOList){
        for(BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList){
            bdcSlSfxmDO.setSfzt(sfzt);
        }
        this.bdcSlSfxmService.batchUpdateBdcSlSfxm(bdcSlSfxmDOList);
    }

    /**
     * 更新大云回写字段
     */
    private void hxYwxx(String gzlslid, Integer sfzt){
        if(StringUtils.isNotBlank(gzlslid)){
            List<Map<String, Object>> list = processInsCustomExtendClient.getProcessInsCustomExtend(gzlslid);
            if (CollectionUtils.isNotEmpty(list)) {
                Map<String, Object>  processInsExtendDto = list.get(0);
                processInsExtendDto.put("JFZT", sfzt);
                processInsCustomExtendClient.updateProcessInsCustomExtend(gzlslid, processInsExtendDto);
            }
        }
    }

    /**
     * 同步收费信息表中的收费状态。根据收费信息ID，查询所有收费项目，
     * 判断所有收费项目收费状态为 已收费时，更新收费信息表收费状态为已收费，否则为未收费。
     */
    @Override
    public void syncSfxxSfzt(String sfxxid, String jksj) {
        if (StringUtils.isNotBlank(sfxxid)) {
            List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);
            bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(t -> !Objects.equals(t.getSfzt(), BdcSfztEnum.YJF.key())).collect(Collectors.toList());
            BdcSlSfxxDO bdcSlSfxxDO = this.bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfxxid);
            if (Objects.nonNull(bdcSlSfxxDO)) {
                if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                    bdcSlSfxxDO.setSfzt(BdcSfztEnum.WJF.key());
                } else {
                    bdcSlSfxxDO.setSfzt(BdcSfztEnum.YJF.key());
                }
                if (StringUtils.isNotBlank(jksj)) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                    LocalDateTime ldt = LocalDateTime.parse(jksj, dtf);
                    DateTimeFormatter fa = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String datetime = ldt.format(fa);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date jkrq = null;
                    try {
                        jkrq = simpleDateFormat.parse(datetime);
                        LOGGER.info("转换后的日期{}", jkrq);
                    } catch (ParseException e) {
                        LOGGER.error("转换日期格式失败");
                    }
                    bdcSlSfxxDO.setJkrq(jkrq);
                }
                this.bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);

                // 更新大云回写JFZT字段
                this.hxYwxx(bdcSlSfxxDO.getGzlslid(), bdcSlSfxxDO.getSfzt());
            }
        }
    }

    @Override
    public Map tksq(BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isBlank(bdcSfxxCzQO.getSfxxid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        // 获取收费信息和收费项目
        BdcSlSfxxDO bdcSlSfxxDO = this.bdcSlSfxxService.queryBdcSlSfxxBySfxxid(bdcSfxxCzQO.getSfxxid());
        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSfxxCzQO.getSfxxid());
        bdcSlSfxmDOList = this.filterSfxmBySftdsyz(bdcSlSfxmDOList, bdcSfxxCzQO.isSftdsyj());
        if(Objects.isNull(bdcSlSfxxDO) || CollectionUtils.isEmpty(bdcSlSfxmDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费项目信息");
        }
        // 计算项目合计金额
        Double hj = NumberUtil.formatDigit(bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> Objects.nonNull(bdcSlSfxmDO.getSsje())).mapToDouble(BdcSlSfxmDO::getSsje).sum(), 2);
        Map paramMap = new HashMap();
        paramMap.put("sfxxid", bdcSlSfxmDOList.get(0).getSfxmid());
        paramMap.put("je", hj);
        paramMap.put("jkfs", bdcSlSfxxDO.getJkfs());
        paramMap.put("xmid", bdcSlSfxxDO.getXmid());
        Object response = exchangeInterfaceFeignService.requestInterface("cz_ddtk", paramMap);
        LOGGER.info("退款申请结果返回信息:{}", response);
        if (Objects.nonNull(response)) {
            Map<String, String> map = (Map<String, String>) response;
            if (StringUtils.equals("1", map.get("orderStatus"))) {
                //1代表成功，保存平台退款订单号，收费状态改为退款
                this.modifySfxmTkqk(bdcSlSfxmDOList,  map.get("refundNo"), BdcSfztEnum.TKCG.key());
                // 同步收费信息收费状态
                this.syncSfxxSfzt(bdcSlSfxxDO.getSfxxid(), "");
            }
            return map;
        }
        return null;
    }

    /**
     * 更新收费项目退款信息、退款状态
     */
    private void modifySfxmTkqk(List<BdcSlSfxmDO> bdcSlSfxmDOList, String tkdh, Integer sfzt){
        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
            for(BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList){
                bdcSlSfxmDO.setTkdh(tkdh);
                bdcSlSfxmDO.setSfzt(BdcSfztEnum.TKCG.key());
            }
            this.bdcSlSfxmService.batchUpdateBdcSlSfxm(bdcSlSfxmDOList);
        }
    }

    @Override
    public void modifySfxmSfzt(BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isBlank(bdcSfxxCzQO.getSfxxid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        // 根据是否土地收益金，筛选收费项目
        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSfxxCzQO.getSfxxid());
        bdcSlSfxmDOList = this.filterSfxmBySftdsyz(bdcSlSfxmDOList, bdcSfxxCzQO.isSftdsyj());
        // 更新收费项目的收费状态
        this.modifySfxmSfzt(bdcSfxxCzQO.getSfzt(), bdcSlSfxmDOList);
        // 同步收费信息的收费状态
        this.syncSfxxSfzt(bdcSfxxCzQO.getSfxxid(), "");
    }

    @Override
    public Map<String, String> getSfEwmAndTdsyjEwm(String sfxxid) {
        if(StringUtils.isBlank(sfxxid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);
        // 过滤收费项目，区分出登记费收费项目 与 土地收益金收费项目
        Map<String, String> resultMap = new HashMap<>(2);
        String djEwm = "";
        String tdsyjEwm = "";
        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
            List<BdcSlSfxmDO> djfList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> !StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdsyqsfxmdm)).collect(Collectors.toList());
            List<BdcSlSfxmDO> tdsyjList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdsyqsfxmdm)).collect(Collectors.toList());

            if(CollectionUtils.isNotEmpty(djfList)){
                djEwm = djfList.get(0).getJfsewmurl();
            }
            if(CollectionUtils.isNotEmpty(tdsyjList)){
                tdsyjEwm = tdsyjList.get(0).getJfsewmurl();
            }
        }
        resultMap.put("djewm", djEwm);
        resultMap.put("tdsyjewm", tdsyjEwm);
        return resultMap;
    }

    @Override
    public String lqfph(BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isBlank(bdcSfxxCzQO.getSfxxid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSfxxCzQO.getSfxxid());
        bdcSlSfxmDOList = this.filterSfxmBySftdsyz(bdcSlSfxmDOList, bdcSfxxCzQO.isSftdsyj());

        if(CollectionUtils.isEmpty(bdcSlSfxmDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费项目");
        }

        List<BdcFphDO> bdcFphDOList = this.bdcXtFphFeignService.getBdcFphxx(1);
        if(CollectionUtils.isEmpty(bdcFphDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到发票号");
        }
        BdcFphDO bdcFphDO = bdcFphDOList.get(0);
        String fph = bdcFphDO.getFph();

        for(BdcSlSfxmDO sfxm : bdcSlSfxmDOList){
            sfxm.setFph(fph);
            sfxm.setHqfphsj(new Date());
            bdcFphDO.setSfxmid(sfxm.getSfxmid());
        }
        // 更新收费信息
        this.bdcSlSfxmService.batchUpdateBdcSlSfxm(bdcSlSfxmDOList);
        // 更新发票号中收费项目
        if(StringUtils.isNotBlank(bdcSfxxCzQO.getGzlslid())){
            List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcSfxxCzQO.getGzlslid());
            if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                bdcFphDO.setSlbh(bdcXmDTOList.get(0).getSlbh());
            }
        }
        this.bdcXtFphFeignService.updateBdcFphxx(bdcFphDO);
        return fph;
    }

    @Override
    public void zffph(BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isBlank(bdcSfxxCzQO.getSfxxid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSfxxCzQO.getSfxxid());
        bdcSlSfxmDOList = this.filterSfxmBySftdsyz(bdcSlSfxmDOList, bdcSfxxCzQO.isSftdsyj());

        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
            String fph = bdcSfxxCzQO.getFph();
            if(StringUtils.isBlank(fph)){
                fph = bdcSlSfxmDOList.get(0).getFph();
            }
            this.bdcXtFphFeignService.zfBdcFph(fph);

            // 清空发票号
            for(BdcSlSfxmDO sfxm : bdcSlSfxmDOList){
                sfxm.setFph("");
                sfxm.setHqfphsj(null);
            }
            this.bdcSlSfxmService.batchUpdateBdcSlSfxm(bdcSlSfxmDOList);
        }
    }

    @Override
    public void qxfph(BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isBlank(bdcSfxxCzQO.getSfxxid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSfxxCzQO.getSfxxid());
        bdcSlSfxmDOList = this.filterSfxmBySftdsyz(bdcSlSfxmDOList, bdcSfxxCzQO.isSftdsyj());

        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
            String fph = bdcSfxxCzQO.getFph();
            if(StringUtils.isBlank(fph)){
                fph = bdcSlSfxmDOList.get(0).getFph();
            }
            if(StringUtils.isNotBlank(fph)){
                this.bdcXtFphFeignService.qxBdcFph(fph);

                // 清空发票号
                for(BdcSlSfxmDO sfxm : bdcSlSfxmDOList){
                    sfxm.setFph("");
                    sfxm.setHqfphsj(null);
                }
                this.bdcSlSfxmService.batchUpdateBdcSlSfxm(bdcSlSfxmDOList);
            }
        }
    }

    @Override
    public void rechangeSfxxid(List<BdcSfxxCzQO> bdcSfxxCzQOList) {
        if(CollectionUtils.isEmpty(bdcSfxxCzQOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        for(BdcSfxxCzQO bdcSfxxCzQO : bdcSfxxCzQOList){
            //1、更改收费信息中的收费信息ID
            String newSfxxid = UUIDGenerator.generate16();
            BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
            bdcSlSfxxDO.setSfxxid(newSfxxid);
            Example example = new Example(BdcSlSfxxDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("sfxxid", bdcSfxxCzQO.getSfxxid());
            entityMapper.updateByExampleSelectiveNotNull(bdcSlSfxxDO, example);

            //2、更改收费项目中的收费信息ID、收费项目ID
            this.modifyBdcSlSfxmxx(bdcSfxxCzQO.getSfxxid(), newSfxxid);

            //3、更改推送流程缴费关系中的收费信息ID
            List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = this.bdcLcTsjfGxService.listLcTsjfGxBySfxxid(bdcSfxxCzQO.getSfxxid());
            if(CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)){
                for(BdcLcTsjfGxDO bdcLcTsjfGxDO : bdcLcTsjfGxDOList){
                    bdcLcTsjfGxDO.setSfxxid(newSfxxid);
                }
                this.bdcLcTsjfGxService.batchUpdateLcTsjfGx(bdcLcTsjfGxDOList);
            }
        }
    }

    private void modifyBdcSlSfxmxx(String sfxxid, String newSfxxid){
        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);
        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
            for(BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList){
                bdcSlSfxmDO.setSfxxid(newSfxxid);
                bdcSlSfxmDO.setSfxmid(UUIDGenerator.generate16());
            }
            // 删除原有收费信息的收费项目
            this.bdcSlSfxmService.deleteBdcSlSfxmBySfxxid(sfxxid);
            // 新增新收费信息ID的收费项目
            this.entityMapper.batchSaveSelective(bdcSlSfxmDOList);
        }
    }

    @Override
    public void changeJkfsModifySfzt(BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isAnyBlank(bdcSfxxCzQO.getSfxxid(), bdcSfxxCzQO.getJkfs())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID或缴款方式");
        }
        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSfxxCzQO.getSfxxid());
        bdcSlSfxmDOList = this.filterSfxmBySftdsyz(bdcSlSfxmDOList, bdcSfxxCzQO.isSftdsyj());
        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
            Integer sfzt = null;
            Date jkrq = null;
            if(bdcSfxxCzQO.getJkfs().equals("1")){
                sfzt = BdcSfztEnum.WJF.key();
            }else{
                sfzt = BdcSfztEnum.YJF.key();
                jkrq = new Date();
            }
            for(BdcSlSfxmDO sfxm : bdcSlSfxmDOList){
                sfxm.setSfzt(sfzt);
            }

            this.bdcSlSfxmService.batchUpdateBdcSlSfxm(bdcSlSfxmDOList);

            // 更新缴款方式、缴款状态、交款日期
            BdcSlSfxxDO bdcSlSfxxDO = this.bdcSlSfxxService.queryBdcSlSfxxBySfxxid(bdcSfxxCzQO.getSfxxid());
            bdcSlSfxxDO.setSfxxid(bdcSfxxCzQO.getSfxxid());
            bdcSlSfxxDO.setJkrq(jkrq);
            bdcSlSfxxDO.setJkfs(bdcSfxxCzQO.getJkfs());
            this.entityMapper.updateByPrimaryKey(bdcSlSfxxDO);
            // 同步收费状态
            this.syncSfxxSfzt(bdcSfxxCzQO.getSfxxid(), "");
        }
    }

    @Override
    public void modifySfzt(BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isBlank(bdcSfxxCzQO.getSfxxid()) || Objects.isNull(bdcSfxxCzQO.getSfzt())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID或缴款状态");
        }
        LOGGER.error("手动更新缴费状态，参数：{}", JSON.toJSONString(bdcSfxxCzQO));
        // 更新收费项目收费状态
        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSfxxCzQO.getSfxxid());
        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
            for(BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList){
                bdcSlSfxmDO.setSfzt(bdcSfxxCzQO.getSfzt());
            }
            this.bdcSlSfxmService.batchUpdateBdcSlSfxm(bdcSlSfxmDOList);
        }
        // 更新收费信息收费状态
        BdcSlSfxxDO bdcSlSfxxDO = this.bdcSlSfxxService.queryBdcSlSfxxBySfxxid(bdcSfxxCzQO.getSfxxid());
        if(Objects.nonNull(bdcSlSfxxDO)){
            bdcSlSfxxDO.setSfzt(bdcSfxxCzQO.getSfzt());
            this.bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
        }
    }

    @Override
    public void plModifySfzt(BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isBlank(bdcSfxxCzQO.getGzlslid()) || Objects.isNull(bdcSfxxCzQO.getSfzt())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID或缴款状态");
        }
        List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = this.bdcLcTsjfGxService.listLcTsjfGx(bdcSfxxCzQO.getGzlslid());
        if (CollectionUtils.isEmpty(bdcLcTsjfGxDOList)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到关联的收费信息");
        }
        List<String> sfxxids = bdcLcTsjfGxDOList.stream().map(BdcLcTsjfGxDO::getSfxxid).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(sfxxids)){
            if(sfxxids.size() > 500){
                List<List> partList = ListUtils.subList(sfxxids, 400);
                for (List partSfxxidList : partList) {
                    // 更新收费项目收费状态
                    this.bdcSlSfxmService.plxgSfxmSfzt(partSfxxidList, bdcSfxxCzQO.getSfzt());
                    // 更新收费信息收费状态
                    this.bdcSlSfxxService.modifyBdcSlSfxxSfztPl(partSfxxidList, bdcSfxxCzQO.getSfzt());
                }
            } else {
                // 更新收费项目收费状态
                this.bdcSlSfxmService.plxgSfxmSfzt(sfxxids, bdcSfxxCzQO.getSfzt());
                // 更新收费信息收费状态
                this.bdcSlSfxxService.modifyBdcSlSfxxSfztPl(sfxxids, bdcSfxxCzQO.getSfzt());
            }
        }
    }

    @Override
    public List<BdcSlSfxmDO> queryBdcSlSfxmBySfxxidWithFilter(String sfxxid, boolean sftdsyj) {
        if(StringUtils.isBlank(sfxxid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);
        bdcSlSfxmDOList = this.filterSfxmBySftdsyz(bdcSlSfxmDOList, sftdsyj);
        return bdcSlSfxmDOList;
    }

    /**
     * 根据权利人名称和证件号，获取项目ID信息
     */
    private List<String> getXmidListByQlrxx(String qlrmc, String qlrzjh, String mhlx){
        if (StringUtils.isNotBlank(qlrmc) || StringUtils.isNotBlank(qlrzjh)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrmc(qlrmc);
            bdcQlrQO.setZjh(qlrzjh);
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            bdcQlrQO.setMhlx("1");
            List<String> xmidList = bdcSlSfxxService.listWjfXmidByQlrxx(bdcQlrQO);
            return xmidList;
        }
        return new ArrayList<>();
    }

    /**
     * 判断查询参数中是否都没有值，true: 都没有; false: 有查询参数有值
     */
    private boolean NoQueryParam(BdcSlSfxxQO bdcSlSfxxQO){
        return CollectionUtils.isEmpty(bdcSlSfxxQO.getSlbhList())  && StringUtils.isBlank(bdcSlSfxxQO.getQlrmc()) && StringUtils.isBlank(bdcSlSfxxQO.getQlrzjh());
    }

    /**
     * 生成产权批量推送的收费信息
     */
    @Override
    public BdcLcTsjjsDTO listCqTssfDTO(BdcSlSfxxQO bdcSlSfxxQO) {
        BdcLcTsjjsDTO bdcLcTsjjsDTO =new BdcLcTsjjsDTO().init();
        // 1、查询参数没有值时，直接查询 关系表中的数据
        if (StringUtils.isNotBlank(bdcSlSfxxQO.getGzlslid()) && NoQueryParam(bdcSlSfxxQO)) {
            return listLcTsjf(bdcSlSfxxQO.getGzlslid(), false, true);
        }
        // 2、根据受理编号、权利人名称、证件号 查询收费信息
        if (CollectionUtils.isNotEmpty(bdcSlSfxxQO.getSlbhList())
                || StringUtils.isNotBlank(bdcSlSfxxQO.getQlrmc()) || StringUtils.isNotBlank(bdcSlSfxxQO.getQlrzjh())) {
            // 2.1、根据权利人名称、证件号，获取对应的项目信息
            List<String> xmids = this.getXmidListByQlrxx(bdcSlSfxxQO.getQlrmc(), bdcSlSfxxQO.getQlrzjh(), bdcSlSfxxQO.getMhlx());
            // 2.2、没有找到合适的查询条件，返回数据库关系查询结果
            if (CollectionUtils.isEmpty(bdcSlSfxxQO.getSlbhList()) && CollectionUtils.isEmpty(xmids)) {
                return listLcTsjf(bdcSlSfxxQO.getGzlslid(), false, true);
            }
            // 2.3、获取收费信息，查询权属状态为：0，收费状态为：未缴费 的收费信息数据
            List<BdcdjjfglVO> bdcdjjfglVOList = new ArrayList<>();
            BdcSlSfxxQO queryParam = new BdcSlSfxxQO();
            queryParam.setQsztList(Arrays.asList(CommonConstantUtils.QSZT_TEMPORY, CommonConstantUtils.QSZT_VALID));
            queryParam.setSlbhList(bdcSlSfxxQO.getSlbhList());
            queryParam.setSfzt(BdcSfztEnum.WJF.key());
            queryParam.setQxdmList(bdcSlSfxxQO.getQxdmList());

            if(xmids.size() > 1000){
                List<List> partList = ListUtils.subList(xmids, 500);
                for (List partXmidList : partList) {
                    queryParam.setXmidList(partXmidList);
                    bdcdjjfglVOList.addAll(this.bdcSlSfxxService.listPlTsJfxx(queryParam));
                }
            }else{
                queryParam.setXmidList(xmids);
                bdcdjjfglVOList = this.bdcSlSfxxService.listPlTsJfxx(queryParam);
            }

            if(CollectionUtils.isNotEmpty(bdcdjjfglVOList)){
                // 2.4 去除抵押权利数据
                bdcdjjfglVOList = bdcdjjfglVOList.stream().filter(t -> !Objects.equals(CommonConstantUtils.QLLX_DYAQ_DM, t.getQllx())).collect(Collectors.toList());

                // 2.5 新增流程缴费关联关系
                this.dealLcTsjfGx(bdcdjjfglVOList, bdcSlSfxxQO, CommonConstantUtils.PLSF_TSLX_CQ);

                // 2.6 处理关联收费信息数据
                return this.listLcTsjf(bdcSlSfxxQO.getGzlslid(), true, true);
            }
        }
        return bdcLcTsjjsDTO;
    }

    /**
     *  查询当前流程关联的需要推送的收费信息
     *  @param clsfxm 是否处理收费项目收费金额
     *  @param orderInput 是否按输入的受理编号排序
     */
    private BdcLcTsjjsDTO listLcTsjf(String gzlslid, boolean clsfxm, boolean orderInput){
        // 1、查询关联的收费信息数据
        List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = bdcLcTsjfGxService.listLcTsjfGx(gzlslid);
        // 2、初始化页面返回信息
        BdcLcTsjjsDTO bdcLcTsjjsDTO = new BdcLcTsjjsDTO().init();
        BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if(StringUtils.isNotBlank(bdcSlJbxxDO.getSlbh())){
            bdcLcTsjjsDTO.setLcslbh(bdcSlJbxxDO.getSlbh());
        }
        // 3、通过关系信息获取收费信息数据
        List<BdcdjjfglVO> bdcdjjfglVOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)) {
            List<String> sfxxidList = bdcLcTsjfGxDOList.stream().filter(bdcLcTsjfGxDO -> StringUtils.isNotBlank(bdcLcTsjfGxDO.getSfxxid())).map(BdcLcTsjfGxDO::getSfxxid).collect(Collectors.toList());
            // 对超过1000条数据进行分段查询
            bdcdjjfglVOList  = this.queryJfxx(sfxxidList);

            if(CollectionUtils.isNotEmpty(bdcdjjfglVOList) && clsfxm){
                // 3.1 开启多线程，处理没有生成收费项目的数据
                this.executeSfxxNoSfxmData(bdcdjjfglVOList);

                // 3.2 数据处理完成后，重新查询数据（为了获取生成后的收费金额）
                bdcdjjfglVOList  = this.queryJfxx(sfxxidList);
            }
            bdcLcTsjjsDTO.setCode(0);
            bdcLcTsjjsDTO.setTotalElements(bdcdjjfglVOList.size());
            bdcLcTsjjsDTO.setContent(bdcdjjfglVOList);
        }
        // 4、计算收费总合计、流程数量
        dealTsjfHjxx(bdcLcTsjjsDTO, bdcdjjfglVOList);
        if(CollectionUtils.isEmpty(bdcLcTsjjsDTO.getContent())){
            bdcLcTsjjsDTO.setCode(0);
        }else{
            if(orderInput){
                // 根据输入的受理编号进行排序
                this.orderData(gzlslid, bdcLcTsjjsDTO);
            }else{
                // 按关系表数据进行排序
                bdcLcTsjjsDTO.setContent(bdcLcTsjjsDTO.getContent().stream()
                        .sorted(Comparator.comparing(t-> t.getXh(),  Comparator.nullsLast(Integer::compareTo))).collect(Collectors.toList()));
            }
            // 5、回写大云权利人名称和坐落
            this.hxPljfxx(bdcLcTsjjsDTO.getContent(), bdcSlJbxxDO);
        }
        return bdcLcTsjjsDTO;
    }

    private List<BdcdjjfglVO> queryJfxx(List<String> sfxxidList){
        List<BdcdjjfglVO> bdcdjjfglVOList = new ArrayList<>(sfxxidList.size());
        if(CollectionUtils.isEmpty(sfxxidList)){
            return bdcdjjfglVOList;
        }
        if(sfxxidList.size() > 1000){
            List<List> partList = ListUtils.subList(sfxxidList, 500);
            for (List partSfxxidList : partList) {
                BdcSlSfxxQO queryParam = new BdcSlSfxxQO();
                queryParam.setSfxxidList(partSfxxidList);
                bdcdjjfglVOList.addAll(this.bdcSlSfxxService.listPlTsJfxx(queryParam));
            }
        }else{
            BdcSlSfxxQO queryParam = new BdcSlSfxxQO();
            queryParam.setSfxxidList(sfxxidList);
            bdcdjjfglVOList = this.bdcSlSfxxService.listPlTsJfxx(queryParam);
        }
        return bdcdjjfglVOList;
    }

    // 根据输入的受理编号进行排序
    private void orderData(String gzlslid, BdcLcTsjjsDTO bdcLcTsjjsDTO){
        if(StringUtils.isNotBlank(gzlslid)){
            List<BdcSlCxcsDO> bdcSlCxcsDOList = this.bdcSlCxcsService.queryBdcSlCxcsByGzlslid(gzlslid);
            if(CollectionUtils.isNotEmpty(bdcSlCxcsDOList) && StringUtils.isNotBlank(bdcSlCxcsDOList.get(0).getCxcs())){
                Map<String, Object> map = JSON.parseObject(bdcSlCxcsDOList.get(0).getCxcs(), Map.class);
                if(MapUtils.isNotEmpty(map) && map.containsKey("slbh")){
                    List<String> slbhList = (List<String>) map.get("slbh");
                    if(CollectionUtils.isNotEmpty(slbhList)){
                        for(BdcdjjfglVO bdcdjjfglVO : bdcLcTsjjsDTO.getContent()){
                            int xh = slbhList.indexOf(bdcdjjfglVO.getSlbh()) > -1 ? slbhList.indexOf(bdcdjjfglVO.getSlbh()) : slbhList.size();
                            bdcdjjfglVO.setXh(xh);
                        }
                        bdcLcTsjjsDTO.setContent(bdcLcTsjjsDTO.getContent().stream()
                                .sorted(Comparator.comparing(t-> t.getXh(),  Comparator.nullsLast(Integer::compareTo))).collect(Collectors.toList()));
                    }
                }
            }else{
                // 按关系表数据进行排序
                bdcLcTsjjsDTO.setContent(bdcLcTsjjsDTO.getContent().stream()
                        .sorted(Comparator.comparing(t-> t.getXh(),  Comparator.nullsLast(Integer::compareTo))).collect(Collectors.toList()));
            }
        }
    }

    // 回写大云权利人名称、坐落信息
    private void hxPljfxx(List<BdcdjjfglVO>  bdcdjjfglVOList, BdcSlJbxxDO bdcSlJbxxDO){
        if(CollectionUtils.isNotEmpty(bdcdjjfglVOList)){
            String qlrmc = bdcdjjfglVOList.get(0).getQlr();
            String zl = bdcdjjfglVOList.get(0).getZl();
            List<HxPljfxxThread> hxPljfxxThreads = new ArrayList<>(1);
            hxPljfxxThreads.add(new HxPljfxxThread(qlrmc, zl, bdcSlJbxxDO, bdcSlxxHxService, bdcSlJbxxService));
            threadEngine.excuteThread(hxPljfxxThreads,true);
        }
    }

    /**
     * 生成抵押权批量推送的收费信息
     */
    @Override
    public BdcLcTsjjsDTO listDyaqTssfDTO(BdcSlSfxxQO bdcSlSfxxQO) {
        // 1、查询参数没有受理编号时，直接查询 关系表中的数据
        if (StringUtils.isNotBlank(bdcSlSfxxQO.getGzlslid()) && CollectionUtils.isEmpty(bdcSlSfxxQO.getSlbhList()) && StringUtils.isBlank(bdcSlSfxxQO.getDyaqr())) {
            return listLcTsjf(bdcSlSfxxQO.getGzlslid(), false, false);
        }
        // 2、当抵押权人和受理编号都为空时,直接查询数据库数据
        if (StringUtils.isBlank(bdcSlSfxxQO.getDyaqr()) && CollectionUtils.isEmpty(bdcSlSfxxQO.getSlbhList())) {
            return listLcTsjf(bdcSlSfxxQO.getGzlslid(), false, false);
        }

        // 3、设置收费信息查询参数： 收费信息（未缴费）、权属状态（根据权利人判断是月结银行则为现势，否则临时）、权利类型（组合流程只取抵押的收费）
        bdcSlSfxxQO.setSfzt(BdcSfztEnum.WJF.key());
        bdcSlSfxxQO.setQsztList(this.getQueryParamQsztByDyqr(bdcSlSfxxQO));
        bdcSlSfxxQO.setQllx(CommonConstantUtils.QLLX_DYAQ_DM);

        // 4、查询参数存在抵押方式不为空时，查询权利信息表进行数据筛选后，在查询收费信息视图
        if(Objects.nonNull(bdcSlSfxxQO.getDyfs()) && CollectionUtils.isNotEmpty(bdcSlSfxxQO.getSlbhList())){
            // 此查询过滤掉了 bdcdyh 中带 W 的数据
            List<String> xmidList = this.getXmidListParamFromDyaxx(bdcSlSfxxQO);
            if(CollectionUtils.isEmpty(xmidList)){
                return listLcTsjf(bdcSlSfxxQO.getGzlslid(), false, false);
            }
            bdcSlSfxxQO.setXmidList(xmidList);
        }

        // 5、查询收费信息，并将搜索的数据添加到流程推送收费关系数据中
        if(CollectionUtils.isNotEmpty(bdcSlSfxxQO.getXmidList())) {
            List<List> partList = ListUtils.subList(bdcSlSfxxQO.getXmidList(), 500);
            for (List partXmidList : partList) {
                bdcSlSfxxQO.setXmidList(partXmidList);
                List<BdcdjjfglVO> bdcdjjfglVOList = this.bdcSlSfxxService.listPlTsJfxx(bdcSlSfxxQO);
                dealLcTsjfGx(bdcdjjfglVOList, bdcSlSfxxQO, CommonConstantUtils.PLSF_TSLX_DYAQ);
            }
        }else{
            List<BdcdjjfglVO> bdcdjjfglVOList = this.bdcSlSfxxService.listPlTsJfxx(bdcSlSfxxQO);
            dealLcTsjfGx(bdcdjjfglVOList, bdcSlSfxxQO, CommonConstantUtils.PLSF_TSLX_DYAQ);
        }
        return listLcTsjf(bdcSlSfxxQO.getGzlslid(), true, false);
    }

    /**
     * 同步未缴费信息
     *
     * @param jftbsj 日期
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public void wjftb(String jftbsj) {
        Date date = DateUtils.formatDate(jftbsj, DateUtils.sdf);
        //查询出收费时间在指定日期内但是应收金额为空的数据
        List<String> gzlslids = bdcSlSfxmMapper.listNoSfxxYsjeData(DateUtil.beginOfDay(date),
                DateUtil.endOfDay(date));
        if (CollectionUtils.isNotEmpty(gzlslids)){
            //调用
            gzlslids.forEach(gzlslid -> {
                try {
                    bdcSfService.autoCountSfxxSfxmForZf(gzlslid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询当前流程合一支付的二维码信息
     * @date : 2022/5/17 16:46
     */
    @Override
    public Map querHyzfEwm(String gzlslid, String xmid) {
        if (StringUtils.isBlank(gzlslid)) {
            return null;
        }
        Map<String, String> resultMap = new HashMap<>(2);
        resultMap.put("msg", "");
        //获取收费项目和核税信息的明细数量
        //注意，卖方不收登记费，不需要查
        //1.买卖任意一方大于五条，直接返回msg=提示线下缴费
        //2.总共数量大于五条，买卖双方分开请求
        //3. 否则一起请求
        //查询收费项目信息
        BdcSlSfxmQO bdcSlSfxmQO = new BdcSlSfxmQO();
        bdcSlSfxmQO.setGzlslid(gzlslid);
        bdcSlSfxmQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        bdcSlSfxmQO.setXmid(xmid);
        List<BdcSlSfxmDO> qlrSlSfxmDOList = bdcSlSfxmMapper.listBdcSlSfxmByGzlslid(bdcSlSfxmQO);
        bdcSlSfxmQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
        List<BdcSlSfxmDO> ywrSlSfxmDOList = bdcSlSfxmMapper.listBdcSlSfxmByGzlslid(bdcSlSfxmQO);
        int qlrZfsl = CollectionUtils.size(qlrSlSfxmDOList);
        int ywrZfsl = CollectionUtils.size(ywrSlSfxmDOList);
        if (qlrZfsl > 5 || ywrZfsl > 5) {
            resultMap.put("msg", "买卖双方税款种类超过5个,请线下缴费");
            return resultMap;
        }
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
        bdcSlSfxxQO.setGzlslid(gzlslid);
        bdcSlSfxxQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        bdcSlSfxxQO.setXmid(xmid);
        List<BdcSlSfxxDO> qlrSfxxDOList = bdcSlSfxxMapper.listBdcSlSfxxDO(bdcSlSfxxQO);
        bdcSlSfxxQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
        List<BdcSlSfxxDO> ywrSfxxDOList = bdcSlSfxxMapper.listBdcSlSfxxDO(bdcSlSfxxQO);
        //项目信息
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if ((qlrZfsl + ywrZfsl) > 5) {
            //先请求买方信息，有买方支付链接后，请求卖方
            if (Objects.nonNull(bdcSlJbxxDO)) {
                //买方的收费信息
                if (CollectionUtils.isNotEmpty(qlrSfxxDOList)) {
                    BdcSlSfxxDO bdcSlSfxxDO = qlrSfxxDOList.get(0);
                    BdcHyzfDTO bdcHyzfDTO = new BdcHyzfDTO();
                    bdcHyzfDTO.setFwuuid(bdcSlJbxxDO.getFwuuid());
                    bdcHyzfDTO.setAmount(String.valueOf(bdcSlSfxxDO.getHj()));
                    bdcHyzfDTO.setZrfcsfbz("1");
                    if(CollectionUtils.isNotEmpty(bdcXmDOList) &&CommonConstantUtils.DYBDCLX_CTD.equals(bdcXmDOList.get(0).getBdclx())){
                        bdcHyzfDTO.setTdbz(CommonConstantUtils.SF_S_DM.toString());
                    }else{
                        bdcHyzfDTO.setTdbz(CommonConstantUtils.SF_F_DM.toString());
                    }
                    //税费同缴传0 ，只传登记费 2
                    if (StringUtils.isBlank(bdcSlJbxxDO.getFwuuid())) {
                        bdcHyzfDTO.setZflx(2);
                    } else {
                        bdcHyzfDTO.setZflx(0);
                    }
                    bdcHyzfDTO.setSfswhqjkm(1);
                    bdcHyzfDTO.setBillDate(DateUtils.formateDateToString(bdcSlSfxxDO.getSlsj(), DateTimeFormatter.ofPattern(DateUtils.DATE_NYR, Locale.CHINA)));
                    //查权利人信息
                    if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                        bdcHyzfDTO.setAdmDivCode(bdcXmDOList.get(0).getQxdm());
                        //权利人只去一条即可
                        BdcQlrQO bdcQlrQO = new BdcQlrQO();
                        bdcQlrQO.setXmid(bdcXmDOList.get(0).getXmid());
                        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                            bdcHyzfDTO.setPayUser(bdcQlrDOList.get(0).getQlrmc());
                            bdcHyzfDTO.setPayUserId(bdcQlrDOList.get(0).getZjh());
                            bdcHyzfDTO.setPayerType(String.valueOf(bdcQlrDOList.get(0).getQlrlx()));
                        }
                    }
                    bdcHyzfDTO.setAmt(String.valueOf(bdcSlSfxxDO.getHj()));
                    //摘要和数量，拼接展现，先对照代码和名称 项目编码 1|项目名称1|2.0#
                    //数量拼接
                    List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                    setInfo(bdcSlSfxmDOList, bdcHyzfDTO);

                    LOGGER.warn("当前流程实例id{}开始查询权利人合一支付二维码接口入参{}", gzlslid, JSON.toJSONString(bdcHyzfDTO));
                    Object response = exchangeInterfaceFeignService.requestInterface("sw_getZflj", bdcHyzfDTO);
                    LOGGER.warn("当前流程实例id{}权利人合一支付获取支付链接查询结果{}", gzlslid, JSON.toJSONString(response));
                    if (Objects.nonNull(response)) {
                        resultMap.put("qlrHyzfUrl", updateHyzfxx(response, bdcSlSfxxDO, gzlslid, "权利人"));
                    }
                }
                //权利人获取到支付链接后请求卖方
                if (StringUtils.isNotBlank(resultMap.get("qlrHyzfUrl"))) {
                    if (CollectionUtils.isNotEmpty(ywrSfxxDOList)) {
                        BdcSlSfxxDO bdcSlSfxxDO = ywrSfxxDOList.get(0);
                        BdcHyzfDTO bdcHyzfDTO = new BdcHyzfDTO();
                        bdcHyzfDTO.setFwuuid(bdcSlJbxxDO.getFwuuid());
                        bdcHyzfDTO.setAmount(String.valueOf(bdcSlSfxxDO.getHj()));
                        bdcHyzfDTO.setZrfcsfbz("0");
                        //税费同缴传0 ，只传登记费 2
                        if (StringUtils.isBlank(bdcSlJbxxDO.getFwuuid())) {
                            bdcHyzfDTO.setZflx(2);
                        } else {
                            bdcHyzfDTO.setZflx(0);
                        }
                        bdcHyzfDTO.setSfswhqjkm(1);
                        bdcHyzfDTO.setBillDate(DateUtils.formateDateToString(bdcSlSfxxDO.getSlsj(), DateTimeFormatter.ofPattern(DateUtils.DATE_NYR, Locale.CHINA)));
                        //查权利人信息
                        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                            bdcHyzfDTO.setAdmDivCode(bdcXmDOList.get(0).getQxdm());
                            //权利人只去一条即可
                            BdcQlrQO bdcQlrQO = new BdcQlrQO();
                            bdcQlrQO.setXmid(bdcXmDOList.get(0).getXmid());
                            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                            List<BdcQlrDO> bdcYwrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                            if (CollectionUtils.isNotEmpty(bdcYwrDOList)) {
                                bdcHyzfDTO.setPayUser(bdcYwrDOList.get(0).getQlrmc());
                                bdcHyzfDTO.setPayUserId(bdcYwrDOList.get(0).getZjh());
                                bdcHyzfDTO.setPayerType(String.valueOf(bdcYwrDOList.get(0).getQlrlx()));
                            }
                        }
                        bdcHyzfDTO.setAmt(String.valueOf(bdcSlSfxxDO.getHj()));
                        //摘要和数量，拼接展现，先对照代码和名称 项目编码 1|项目名称1|2.0#
                        //数量拼接
                        List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                        setInfo(bdcSlSfxmDOList, bdcHyzfDTO);
                        LOGGER.warn("当前流程实例id{}开始查询义务人合一支付二维码接口入参{}", gzlslid, JSON.toJSONString(bdcHyzfDTO));
                        Object ywrResponse = exchangeInterfaceFeignService.requestInterface("sw_getZflj", bdcHyzfDTO);
                        if (Objects.nonNull(ywrResponse)) {
                            resultMap.put("ywrHyzfUrl", updateHyzfxx(ywrResponse, bdcSlSfxxDO, gzlslid, "义务人"));
                        }
                    }
                }
            }
        } else if ((qlrZfsl + ywrZfsl) > 0 && (qlrZfsl + ywrZfsl) <= 5) {
            //买卖双方同时请求
            if (CollectionUtils.isNotEmpty(qlrSfxxDOList)) {
                BdcSlSfxxDO qlrSfxxDO = qlrSfxxDOList.get(0);
                double hj = qlrSfxxDO.getHj();
                BdcSlSfxxDO ywrSfxxDO = null;
                if (CollectionUtils.isNotEmpty(ywrSfxxDOList)) {
                    hj += ywrSfxxDOList.get(0).getHj();
                    ywrSfxxDO = ywrSfxxDOList.get(0);
                }
                BdcHyzfDTO bdcHyzfDTO = new BdcHyzfDTO();
                bdcHyzfDTO.setFwuuid(bdcSlJbxxDO.getFwuuid());
                bdcHyzfDTO.setAmount(String.valueOf(hj));
                bdcHyzfDTO.setZrfcsfbz("2");
                if(CollectionUtils.isNotEmpty(bdcXmDOList) &&CommonConstantUtils.DYBDCLX_CTD.equals(bdcXmDOList.get(0).getBdclx())){
                    bdcHyzfDTO.setTdbz(CommonConstantUtils.SF_S_DM.toString());
                }else{
                    bdcHyzfDTO.setTdbz(CommonConstantUtils.SF_F_DM.toString());
                }
                //税费同缴传0 ，只传登记费 2
                if (StringUtils.isBlank(bdcSlJbxxDO.getFwuuid())) {
                    bdcHyzfDTO.setZflx(2);
                } else {
                    bdcHyzfDTO.setZflx(0);
                }
                bdcHyzfDTO.setSfswhqjkm(1);
                bdcHyzfDTO.setBillDate(DateUtils.formateDateToString(qlrSfxxDO.getSlsj(), DateTimeFormatter.ofPattern(DateUtils.DATE_NYR, Locale.CHINA)));
                //查权利人信息
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    bdcHyzfDTO.setAdmDivCode(bdcXmDOList.get(0).getQxdm());
                    //权利人只去一条即可
                    BdcQlrQO bdcQlrQO = new BdcQlrQO();
                    bdcQlrQO.setXmid(bdcXmDOList.get(0).getXmid());
                    bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                    if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                        bdcHyzfDTO.setPayUser(bdcQlrDOList.get(0).getQlrmc());
                        bdcHyzfDTO.setPayUserId(bdcQlrDOList.get(0).getZjh());
                        bdcHyzfDTO.setPayerType(String.valueOf(bdcQlrDOList.get(0).getQlrlx()));
                    }
                }
                bdcHyzfDTO.setAmt(String.valueOf(hj));
                //摘要和数量，拼接展现，先对照代码和名称 项目编码 1|项目名称1|2.0#
                //数量拼接
                List<BdcSlSfxmDO> sfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(qlrSfxxDO.getSfxxid());
                if (Objects.nonNull(ywrSfxxDO)) {
                    List<BdcSlSfxmDO> ywrSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(ywrSfxxDO.getSfxxid());
                    sfxmDOList.addAll(ywrSfxmDOList);
                }
                //先取出所有金额不为0 的数据
                sfxmDOList = sfxmDOList.stream().filter(bdcSlSfxmDO -> !Objects.equals(0.0, bdcSlSfxmDO.getSsje())).collect(Collectors.toList());
                //所有的收费项目根据收费项目标准分组
                Map<String, List<BdcSlSfxmDO>> groupSfxmMap = sfxmDOList.stream().collect(Collectors.groupingBy(BdcSlSfxmDO::getSfxmbz));
                List<String> sfxmSlList = new ArrayList<>(1);
                List<String> sfxmBzList = new ArrayList<>(1);
                if (MapUtils.isNotEmpty(groupSfxmMap)) {
                    for (Map.Entry<String, List<BdcSlSfxmDO>> sfxmMapEntry : groupSfxmMap.entrySet()) {
                        List<BdcSlSfxmDO> bdcSlSfxmDOList = sfxmMapEntry.getValue();
                        if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                            //数量，金额 求和
                            double slhj = bdcSlSfxmDOList.stream().filter(t -> Objects.nonNull(t.getSl())).mapToDouble(BdcSlSfxmDO::getSl).sum();
                            double jehj = bdcSlSfxmDOList.stream().filter(t -> Objects.nonNull(t.getSsje())).mapToDouble(BdcSlSfxmDO::getSsje).sum();
                            BdcSlSfxmDO bdcSlSfxmDO = bdcSlSfxmDOList.get(0);
                            StringBuilder sfxmbzBuilder = new StringBuilder();
                            //根据第三方字典值对照
                            BdcZdDsfzdgxDO bdcZdDsfzdgxQO = new BdcZdDsfzdgxDO();
                            bdcZdDsfzdgxQO.setZdbs("DSF_ZD_SFXMDM");
                            bdcZdDsfzdgxQO.setBdczdz(bdcSlSfxmDO.getSfxmbz());
                            bdcZdDsfzdgxQO.setDsfxtbs("jfpt");
                            BdcZdDsfzdgxDO dmdz = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxQO);
                            //sfxmdm对照 dm
                            if (Objects.nonNull(dmdz)) {
                                sfxmbzBuilder.append(dmdz.getDsfzdz()).append("|");
                            } else {
                                sfxmbzBuilder.append(bdcSlSfxmDO.getSfxmdm()).append("|");
                            }
                            bdcZdDsfzdgxQO.setZdbs("DSF_ZD_SFXMMC");
                            bdcZdDsfzdgxQO.setBdczdz(bdcSlSfxmDO.getSfxmbz());
                            bdcZdDsfzdgxQO.setDsfxtbs("jfpt");
                            BdcZdDsfzdgxDO mcdz = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxQO);
                            //sfxmmc对照 mc
                            if (Objects.nonNull(mcdz)) {
                                sfxmbzBuilder.append(mcdz.getDsfzdz()).append("|").append(jehj);
                            } else {
                                sfxmbzBuilder.append(bdcSlSfxmDO.getSfxmmc()).append("|").append(jehj);
                            }
                            sfxmSlList.add(String.valueOf((int) Math.round(slhj)));
                            sfxmBzList.add(String.valueOf(sfxmbzBuilder));
                        }
                    }
                    //赋值
                    bdcHyzfDTO.setPayInfo(StringUtils.join(sfxmBzList, "#"));
                    bdcHyzfDTO.setProjectNo(StringUtils.join(sfxmSlList, "#"));
                }
                LOGGER.warn("当前流程实例id{}买卖双方合并请求支付链接入参{}", gzlslid, JSON.toJSONString(bdcHyzfDTO));
                Object response = exchangeInterfaceFeignService.requestInterface("sw_getZflj", bdcHyzfDTO);
                LOGGER.warn("当前流程实例id{}买卖双方合并请求支付连接返回值{}", gzlslid, JSON.toJSONString(response));
                if (Objects.nonNull(response)) {
                    resultMap.put("hyzfUrl", updateHyzfxx(response, qlrSfxxDO, gzlslid, "买卖双方-权利人"));
                    if (Objects.nonNull(ywrSfxxDO)) {
                        updateHyzfxx(response, ywrSfxxDO, gzlslid, "买卖双方-义务人");
                    }
                }
            }
        }
        return resultMap;
    }

    private void setInfo(List<BdcSlSfxmDO> bdcSlSfxmDOList, BdcHyzfDTO bdcHyzfDTO) {
        List<String> sfxmSlList = new ArrayList<>(1);
        List<String> sfxmBzList = new ArrayList<>(1);
        if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
            for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {
                if (Objects.nonNull(bdcSlSfxmDO.getSsje()) && !Objects.equals(0.0, bdcSlSfxmDO.getSsje())) {
                    StringBuilder sfxmbzBuilder = new StringBuilder();
                    //根据第三方字典值对照
                    BdcZdDsfzdgxDO bdcZdDsfzdgxQO = new BdcZdDsfzdgxDO();
                    bdcZdDsfzdgxQO.setZdbs("DSF_ZD_SFXMDM");
                    bdcZdDsfzdgxQO.setBdczdz(bdcSlSfxmDO.getSfxmbz());
                    bdcZdDsfzdgxQO.setDsfxtbs("jfpt");
                    BdcZdDsfzdgxDO dmdz = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxQO);
                    //sfxmdm对照 dm
                    if (Objects.nonNull(dmdz)) {
                        sfxmbzBuilder.append(dmdz.getDsfzdz()).append("|");
                    } else {
                        sfxmbzBuilder.append(bdcSlSfxmDO.getSfxmdm()).append("|");
                    }
                    bdcZdDsfzdgxQO.setZdbs("DSF_ZD_SFXMMC");
                    bdcZdDsfzdgxQO.setBdczdz(bdcSlSfxmDO.getSfxmbz());
                    bdcZdDsfzdgxQO.setDsfxtbs("jfpt");
                    BdcZdDsfzdgxDO mcdz = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxQO);
                    //sfxmmc对照 mc
                    if (Objects.nonNull(mcdz)) {
                        sfxmbzBuilder.append(mcdz.getDsfzdz()).append("|").append(bdcSlSfxmDO.getSsje());
                    } else {
                        sfxmbzBuilder.append(bdcSlSfxmDO.getSfxmmc()).append("|").append(bdcSlSfxmDO.getSsje());
                    }
                    sfxmSlList.add(String.valueOf((int) Math.round(bdcSlSfxmDO.getSl())));
                    sfxmBzList.add(String.valueOf(sfxmbzBuilder));
                }
            }
            //赋值
            bdcHyzfDTO.setPayInfo(StringUtils.join(sfxmBzList, "#"));
            bdcHyzfDTO.setProjectNo(StringUtils.join(sfxmSlList, "#"));
        }
    }

    /**
     * @param gzlslid@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询合一支付的缴费状态并更新
     * @date : 2022/5/17 17:00
     */
    @Override
    public void queryHyzfJfzt(String gzlslid, String xmid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            //查询收费信息
            BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
            bdcSlSfxxQO.setGzlslid(gzlslid);
            bdcSlSfxxQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            bdcSlSfxxQO.setXmid(xmid);
            List<BdcSlSfxxDO> qlrSfxxDOList = bdcSlSfxxMapper.listBdcSlSfxxDO(bdcSlSfxxQO);
            bdcSlSfxxQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
            List<BdcSlSfxxDO> ywrSfxxDOList = bdcSlSfxxMapper.listBdcSlSfxxDO(bdcSlSfxxQO);
            Map<String, String> queryMap = new HashMap<>(1);
            if (CollectionUtils.isNotEmpty(qlrSfxxDOList)) {
                //支付接口返回的orderid买卖双方是一样的，取权利人即可
                queryMap.put("orderId", qlrSfxxDOList.get(0).getHyzfddid());
                LOGGER.warn("当前流程实例id={}查询合一支付状态入参{}", gzlslid, queryMap);
                Object response = exchangeInterfaceFeignService.requestInterface("sw_getZfzt",queryMap);
                LOGGER.warn("当前流程实例id={}查询合一支付接口返回值{}", gzlslid, JSON.toJSONString(response));
                if (Objects.nonNull(response)) {
                    JSONObject responseObject = JSON.parseObject(JSON.toJSONString(response));
                    JSONObject resultObject = responseObject.getJSONObject("result");
                    if (Objects.nonNull(resultObject)) {
                        Map body = (Map) resultObject.get("body");
                        String zfzt = MapUtils.getString(body, "zfzt", "");
                        if (CommonConstantUtils.JK_RESPONSE_SUCCESS.equals(zfzt)) {
                            //支付状态成功，更新jfzt和YHJKRKZT
                            BdcSlSfxxDO qlrSfxxDO = qlrSfxxDOList.get(0);
                            qlrSfxxDO.setSfzt(BdcSfztEnum.YJF.key());
                            qlrSfxxDO.setYhjkrkzt(CommonConstantUtils.SF_S_DM);
                            qlrSfxxDO.setSfztczsj(new Date());
                            LOGGER.warn("当前流程实例id{}合一支付接口返回SUCCESS，更新收费状态和缴库入库状态", gzlslid);
                            bdcSlSfxxService.updateBdcSlSfxx(qlrSfxxDO);
                            if (CollectionUtils.isNotEmpty(ywrSfxxDOList)) {
                                BdcSlSfxxDO ywrSlSfxxDO = ywrSfxxDOList.get(0);
                                ywrSlSfxxDO.setSfzt(BdcSfztEnum.YJF.key());
                                ywrSlSfxxDO.setYhjkrkzt(CommonConstantUtils.SF_S_DM);
                                ywrSlSfxxDO.setSfztczsj(new Date());
                                bdcSlSfxxService.updateBdcSlSfxx(ywrSlSfxxDO);
                            }
                        }
                    }
                }
            }

        }
    }

    /**
     * @param bdcSlJfblQO
     * @param bdcSlJfblQO
     * @return
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 缴费办理
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BdcSfjfblResponseDTO jfbl(BdcSlJfblQO bdcSlJfblQO) {
        if (Objects.isNull(bdcSlJfblQO) || StringUtils.isBlank(bdcSlJfblQO.getSfxxid())) {
            throw new MissingArgumentException("请求参数为空或收费信息id为空");
        }
        String sfxxid = bdcSlJfblQO.getSfxxid();
        //批量查询收费信息
        List<BdcSlSfxxDO> bdcSlSfxxDOList = plcxsfxx(sfxxid);
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            throw new AppException("未查询到收费信息");
        }
        bdcSlSfxxDOList.forEach(sfxxItem -> {
            if (StringUtils.isNotBlank(sfxxItem.getJfsbm())) {
                throw new AppException("收费信息ID:" + sfxxItem.getSfxxid() + "缴款码信息不为空");
            }
        });
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxDOList.get(0);
        if (Objects.isNull(bdcSlSfxxDO)) {
            throw new AppException("未查询到收费信息");
        }
        BdcSfxxDTO bdcSfxxDTO = new BdcSfxxDTO();
        DsfSfxxDTO dsfSfxxDTO = new DsfSfxxDTO();
        // 获取区县代码
        String xmid = bdcSlSfxxDO.getXmid();
        if (StringUtil.isNotBlank(xmid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                throw new AppException("未查询到项目信息");
            }
            dsfSfxxDTO.setQxdm(bdcXmDOList.get(0).getQxdm());
        }
        // 设置缴费人信息
        if (Objects.nonNull(bdcSlJfblQO)) {
            dsfSfxxDTO.setJfrxm(bdcSlJfblQO.getJfrxm());
            dsfSfxxDTO.setJfrdh(bdcSlJfblQO.getJfrdh());
        }
        String userAlias = userManagerUtils.getUserAlias();
        if (StringUtil.isNotBlank(userAlias)) {
            dsfSfxxDTO.setUserName(StringUtils.upperCase(PinyinUtil.getPinYinHeadChar(userAlias)));
        }

        List<BdcSlSfxmDO> bdcSlSfxmDOList = new ArrayList<>();
        for (BdcSlSfxxDO sfxxItem : bdcSlSfxxDOList) {
            // 设置收费合计
            String sfxxidItem = sfxxItem.getSfxxid();
            List<BdcSlSfxmDO> bdcSlSfxmBySfxxidList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxidItem);
            if (CollectionUtils.isNotEmpty(bdcSlSfxmBySfxxidList)) {
                bdcSlSfxmDOList.addAll(bdcSlSfxmBySfxxidList);
                if (CollectionUtils.isNotEmpty(bdcSlSfxmBySfxxidList)) {
                    Double sfxmHj = NumberUtil.formatDigit(bdcSlSfxmBySfxxidList.stream()
                            .filter(bdcSlSfxmDO -> Objects.nonNull(bdcSlSfxmDO.getSsje()))
                            .mapToDouble(BdcSlSfxmDO::getSsje).sum(), 2);
                    sfxxItem.setHj(sfxmHj);
                }
            }
            sfxxItem.setSfsj(new Date());
            // 设置交易凭证码为xmid，将交易凭证码作为财政BUSINESS_ID，方便批量查询缴费情况和作废缴款通知书
            sfxxItem.setJypzh(xmid);
            // 设置缴费人信息
            if (Objects.nonNull(bdcSlJfblQO)) {
                sfxxItem.setJfrxm(bdcSlJfblQO.getJfrxm());
                sfxxItem.setJfrdh(bdcSlJfblQO.getJfrdh());
            }
        }

        Double plsfhj = NumberUtil.formatDigit(bdcSlSfxxDOList.stream()
                .filter(sfxxItem -> Objects.nonNull(sfxxItem.getHj()))
                .mapToDouble(BdcSlSfxxDO::getHj).sum(), 2);
        bdcSfxxDTO.setPlsfhj(plsfhj);
        bdcSfxxDTO.setBdcSlSfxxDO(bdcSlSfxxDO);
        bdcSfxxDTO.setDsfSfxxDTO(dsfSfxxDTO);

        if (StringUtil.isNotBlank(dsfSfxxDTO.getQxdm())) {
            Map<String, String> dwxxs = bdcSfxxConfig.getDwxx().get(dsfSfxxDTO.getQxdm());
            if (MapUtils.isNotEmpty(dwxxs)) {
                if(StringUtils.isNotBlank(dwxxs.get("dwdm")) && StringUtils.isNotBlank(dwxxs.get("dwsecret"))){
                    bdcSfxxDTO.setDwdm(dwxxs.get("dwdm"));
                    bdcSfxxDTO.setMd5String(dwxxs.get("dwsecret"));
                }
            }
        }
        // 根据区县代码调用beanid
        String beanid = getJfxxBeanid(dsfSfxxDTO.getQxdm(), "jfbl");
        if (StringUtils.isBlank(beanid)) {
            throw new AppException("收费项目ID:" + sfxxid + "未配置缴费办理区县代码beanid对应配置项");
        }
        LOGGER.info("缴费办理beanid:{},收费信息入参{}", beanid, JSON.toJSONString(bdcSfxxDTO));
        Object response = exchangeInterfaceFeignService.requestInterface(beanid, bdcSfxxDTO);
        BdcSfjfblResponseDTO bdcSfjfblResponseDTO = new BdcSfjfblResponseDTO();
        if (response != null) {
            LOGGER.info("收费信息ID:{},缴费办理调用成功,返回结果：{}", sfxxid, response);
            bdcSfjfblResponseDTO = JSONObject.parseObject(JSONObject.toJSONString(response), BdcSfjfblResponseDTO.class);
            BdcSfjfblResponseDTO finalBdcSfjfblResponseDTO = bdcSfjfblResponseDTO;
            // 批量更新收费信息缴款码和二维码
            bdcSlSfxxDOList.forEach(sfxxItem -> {
                sfxxItem.setJfsbm(finalBdcSfjfblResponseDTO.getJfsbm());
                sfxxItem.setJfsewmurl(finalBdcSfjfblResponseDTO.getJfsewmurl());
                sfxxItem.setSfsj(new Date());
            });
            bdcSlSfxxService.batchUpdateBdcSlSfxx(bdcSlSfxxDOList);
            if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                bdcSlSfxmDOList.forEach(t->{
                    t.setJfsbm(finalBdcSfjfblResponseDTO.getJfsbm());
                    t.setJfsewmurl(finalBdcSfjfblResponseDTO.getJfsewmurl());
                    t.setJkmsj(new Date());
                });
                // 批量更新收费项目的缴款码和二维码
                bdcSlSfxmService.batchUpdateBdcSlSfxm(bdcSlSfxmDOList);
            }
        }
        return bdcSfjfblResponseDTO;
    }

    /**
     * 根据区县代码调用beanid
     * @param qxdm
     * @param dyfs
     * @return
     */
    private String getJfxxBeanid(String qxdm, String dyfs) {
        if (StringUtil.isNotBlank(qxdm) && StringUtil.isNotBlank(dyfs)) {
            Map<String, String> beanidmap = new HashMap<>(1);
            switch (dyfs) {
                case "jfbl":
                    beanidmap = bdcSfxxConfig.getJfblbeanidmap();
                    break;
                case "cxjfqk":
                    beanidmap = bdcSfxxConfig.getJfqrbeanidmap();
                    break;
                case "fskp":
                    beanidmap = bdcSfxxConfig.getFskpbeanidmap();
                    break;
                case "zfjktzs":
                    beanidmap = bdcSfxxConfig.getZfjktzsbeanidmap();
                    break;
                default:
                    LOGGER.info("无此调用方");
            }
            if (MapUtils.isNotEmpty(beanidmap)) {
                return beanidmap.get(qxdm);
            }
        }

        return null;
    }


    /**
     * 设置单位编码和单位秘钥MD5
     * @param qxdm
     * @param param
     */
    private void setJfxxDwxx(String qxdm,Map<String, String> param) {
        if (StringUtil.isNotBlank(qxdm)) {
            Map<String, String> dwxxs = bdcSfxxConfig.getDwxx().get(qxdm);
            if (MapUtils.isNotEmpty(dwxxs)) {
                if(StringUtils.isNotBlank(dwxxs.get("dwdm")) && StringUtils.isNotBlank(dwxxs.get("dwsecret"))){
                    param.put("dwdm",dwxxs.get("dwdm"));
                    param.put("md5String",dwxxs.get("dwsecret"));

                }
            }
        }
    }

    /**
     * 获取区县代码
     * @param xmid
     * @return
     */
    private String getQxdmByXmid(String xmid) {
        if (StringUtil.isNotBlank(xmid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                return bdcXmDOList.get(0).getQxdm();
            }
        }
        return null;
    }

    /**
     * 修改参数的编码
     * @param param
     * @return
     */
    private void changeParamCharset(Map<String, String> param) {
        if (Objects.nonNull(param)) {
            for (Map.Entry<String, String> stringStringEntry : param.entrySet()) {
                String value = stringStringEntry.getValue();
                try {
                    param.put(stringStringEntry.getKey(), new String(value.getBytes("UTF-8"), "GB2312"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param bdcSlJfblQO
     * @param sfyj
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 查询缴费情况
     * @date : 2022/5/26 18:43
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cxjfqk(BdcSlJfblQO bdcSlJfblQO, boolean sfyj) {
        if (Objects.isNull(bdcSlJfblQO) || StringUtils.isBlank(bdcSlJfblQO.getSfxxid())) {
            throw new MissingArgumentException("请求参数为空或收费信息id为空");
        }
        String sfxxid = bdcSlJfblQO.getSfxxid();
        // 批量查询收费信息
        List<BdcSlSfxxDO> bdcSlSfxxDOList = plcxsfxx(sfxxid);
        // 收费信息缴款码验证
        bdcSlSfxxDOList = haFsjkYz(bdcSlSfxxDOList);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxDOList.get(0);
            // 单个sfxxid时business_id为xmid,批量查询时business_id为交易凭证号字段
            String businessId = StringUtils.isNotBlank(bdcSlSfxxDO.getJypzh()) ? bdcSlSfxxDO.getJypzh() : bdcSlSfxxDO.getXmid();
            Map<String, String> map = new HashMap<>(4);
            map.put("business_id", businessId);
            map.put("businessnumber", bdcSlSfxxDO.getJfsbm());
            map.put("hold1", "");
            map.put("hold2", "");
            String qxdm = getQxdmByXmid(bdcSlSfxxDO.getXmid());
            setJfxxDwxx(qxdm,map);
            //changeParamCharset(map);
            if (StringUtil.isNotBlank(qxdm)) {
                String beanid = getJfxxBeanid(qxdm, "cxjfqk");
                if (StringUtils.isBlank(beanid)) {
                    throw new AppException("收费项目ID:" + sfxxid + "未配置查询缴费情况区县代码beanid对应配置项");
                }
                LOGGER.info("查询缴费情况入参{}, {}, {}", sfxxid, map, beanid);
                Object response = exchangeInterfaceFeignService.requestInterface(beanid, map);
                if (null != response) {
                    LOGGER.info("收费信息ID:{},查询缴费情况调用成功,返回结果：{}", sfxxid, response);
                    JfqrResponseDTO jfqrResponseDTO = JSONObject.parseObject(JSONObject.toJSONString(response), JfqrResponseDTO.class);
                    if (null != jfqrResponseDTO
                            && "00000".equals(jfqrResponseDTO.getCode())
                            && null != jfqrResponseDTO.getData()) {
                        // 查询成功保存缴费状态
                        String isSuccess = jfqrResponseDTO.getData().getIsSuccess();
                        // 更新已缴款
                        if ("1".equals(isSuccess)) {
                            bdcSlSfxxDOList.stream().forEach(sfxxItem -> sfxxItem.setSfzt(BdcSfztEnum.YJF.key()));
                            // 更新是否月结
                            if (sfyj) {
                                bdcSlSfxxDOList.stream().forEach(sfxxItem -> sfxxItem.setSfyj(CommonConstantUtils.SF_S_DM));
                            }
                            bdcSlSfxxService.batchUpdateBdcSlSfxx(bdcSlSfxxDOList);
                        }
                    }
                }
            }
        }

    }

    /**
     * @param bdcSlJfblQO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 非税开票
     * @date : 2022/5/26 18:43
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> fskp(BdcSlJfblQO bdcSlJfblQO) {
        if (Objects.isNull(bdcSlJfblQO) || StringUtils.isBlank(bdcSlJfblQO.getGzlslid())) {
            throw new MissingArgumentException("请求参数为空或工作流实例id为空");
        }
        String gzlslid = bdcSlJfblQO.getGzlslid();
        Map<String, String> result = new HashMap<>(2);
        if (StringUtil.isBlank(gzlslid)) {
            result.put("code", "1");
            result.put("msg", "缺少参数gzlslid");
            return result;
        }
        String[] gzlslidArr = gzlslid.split(CommonConstantUtils.ZF_YW_DH);
        List<String> gzlslidList = Arrays.asList(gzlslidArr);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = new ArrayList<>();
        if (gzlslidList.size() > 500) {
            List<List> partList = ListUtils.subList(gzlslidList, 500);
            for (List<String> gzlslidPartList : partList) {
                BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
                bdcSlSfxxQO.setGzlslidList(gzlslidPartList);
                bdcSlSfxxQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                List<BdcSlSfxxDO> bdcSlSfxxDOPartList = bdcSlSfxxService.listBdcSlSfxxBySlbh(bdcSlSfxxQO);
                if (CollectionUtils.isNotEmpty(bdcSlSfxxDOPartList)) {
                    bdcSlSfxxDOList.addAll(bdcSlSfxxDOPartList);
                }
            }
        } else {
            BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
            bdcSlSfxxQO.setGzlslidList(gzlslidList);
            bdcSlSfxxQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxBySlbh(bdcSlSfxxQO);
        }

        BdcSlSfxxDO bdcSlSfxxDO = null;
        // 收费信息缴款码验证
        bdcSlSfxxDOList = haFsjkYz(bdcSlSfxxDOList);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            bdcSlSfxxDO = bdcSlSfxxDOList.get(0);
        }
        if (null == bdcSlSfxxDO || null == bdcSlSfxxDO.getJfsbm()) {
            result.put("code", "1");
            result.put("msg", "未查询到缴款码信息");
            return result;
        }
        // 单个sfxxid时business_id为xmid,批量查询时business_id为交易凭证号字段
        String businessId = StringUtils.isNotBlank(bdcSlSfxxDO.getJypzh()) ? bdcSlSfxxDO.getJypzh() : bdcSlSfxxDO.getXmid();
        Map<String, String> map = new HashMap<>(4);
        map.put("business_id", businessId);
        map.put("businessnumber", bdcSlSfxxDO.getJfsbm());
        map.put("hold1", "");
        map.put("hold2", "");
        String qxdm = getQxdmByXmid(bdcSlSfxxDO.getXmid());
        setJfxxDwxx(qxdm,map);
        //changeParamCharset(map);

        if (StringUtil.isNotBlank(qxdm)) {
            String beanid = getJfxxBeanid(qxdm, "fskp");
            if (StringUtil.isNotBlank(beanid)) {
                LOGGER.info("非税开票入参{}, {}，{}", bdcSlSfxxDO.getSfxxid(), map, beanid);
                Object response = exchangeInterfaceFeignService.requestInterface(beanid, map);
                if (response != null) {
                    LOGGER.info("收费信息ID:{},非税开票调用成功,返回结果：{}", bdcSlSfxxDO.getSfxxid(), response);
                    DzpjResponseDTO dzpjResponseDTO = JSONObject.parseObject(JSONObject.toJSONString(response), DzpjResponseDTO.class);
                    if (null != dzpjResponseDTO
                            && "00000".equals(dzpjResponseDTO.getCode())
                            && null != dzpjResponseDTO.getData()) {
                        // 保存电子票据url并转pdf至附件
                        String url = dzpjResponseDTO.getData().getUrl();
                        String base64Str = dzpjResponseDTO.getData().getPdfFileData();

                        if (StringUtils.isNotBlank(url)) {
                            url = url.replaceAll("&amp;","&");
                        }
                        saveFpxx(bdcSlSfxxDOList, dzpjResponseDTO.getData().getBusinessNumber(), url);

                        if (StringUtil.isNotBlank(base64Str)) {
                            // 判断base64字符串是否拥有头信息，没有添加pdf的base64头信息
                            if (!base64Str.startsWith("data:")) {
                                base64Str = CommonConstantUtils.BASE64_QZ_PDF + base64Str;
                            }
                            String finalBase64Str = base64Str;
                            bdcSlSfxxDOList.forEach(sfxx -> {
                                try {
                                    BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(sfxx.getGzlslid(), "", "", "非税税票", CommonConstantUtils.WJHZ_PDF);
                                    bdcPdfDTO.setBase64str(finalBase64Str);
                                    bdcUploadFileUtils.uploadBase64File(bdcPdfDTO);
                                }
                                catch (Exception e) {
                                    LOGGER.error("非税开票上传大云失败,异常信息：", e);
                                }
                            });
                        }
                        result.put("code", "0");
                        result.put("msg", "非税票据查询成功！");
                        return result;
                    }
                }
            }
        }
        result.put("code", "1");
        result.put("msg", "非税票据查询失败！");
        return result;
    }

    /**
     * @description 保存发票信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/12/15 19:02
     * @param bdcSlSfxxDOList
     * @param businessNumber
     * @param url
     * @return void
     */
    private void saveFpxx(List<BdcSlSfxxDO> bdcSlSfxxDOList, String businessNumber, String url){
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            List<BdcSlFpxxDO> bdcSlFpxxDOList = new ArrayList<>();

            List<String> sfxxidList = bdcSlSfxxDOList.stream().map(sfxx -> sfxx.getSfxxid()).collect(Collectors.toList());
            // 删除原有发票信息
            if (CollectionUtils.isNotEmpty(sfxxidList)) {
                if (sfxxidList.size() > 500) {
                    List<List> partList = ListUtils.subList(sfxxidList, 500);
                    for (List<String> sfxxidPartList : partList) {
                        bdcSlFpxxService.batchDeleteBdcSlFpxxBySfxxid(sfxxidPartList);
                    }
                } else {
                    bdcSlFpxxService.batchDeleteBdcSlFpxxBySfxxid(sfxxidList);
                }
            }
            bdcSlSfxxDOList.forEach(bdcSlSfxxDO -> {
                if (Objects.nonNull(bdcSlSfxxDO) && StringUtils.isNotBlank(bdcSlSfxxDO.getSfxxid())) {

                    // 新增发票信息
                    BdcSlFpxxDO bdcSlFpxxDO = new BdcSlFpxxDO();
                    bdcSlFpxxDO.setFpxxid(UUIDGenerator.generate16());
                    bdcSlFpxxDO.setFph(businessNumber);
                    bdcSlFpxxDO.setFpurl(url);
                    if (StringUtils.isNotBlank(bdcSlSfxxDO.getXmid())){
                        bdcSlFpxxDO.setXmid(bdcSlSfxxDO.getXmid());
                    }
                    bdcSlFpxxDO.setSfxxid(bdcSlSfxxDO.getSfxxid());
                    bdcSlFpxxDOList.add(bdcSlFpxxDO);
                }
            });
            if (CollectionUtils.isNotEmpty(bdcSlFpxxDOList)) {
                entityMapper.batchSaveSelective(bdcSlFpxxDOList);
            }
        }

    }

    /**
     * @param bdcSlJfblQO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 作废缴款通知书
     * @date : 2022/5/26 18:43
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> zfjktzs(BdcSlJfblQO bdcSlJfblQO) {
        if (Objects.isNull(bdcSlJfblQO) || StringUtils.isBlank(bdcSlJfblQO.getSfxxid())) {
            throw new MissingArgumentException("请求参数为空或收费信息id为空");
        }
        String sfxxid = bdcSlJfblQO.getSfxxid();
        Map<String, String> result = new HashMap<>(2);

        // 批量查询收费信息
        List<BdcSlSfxxDO> bdcSlSfxxDOList = plcxsfxx(sfxxid);
        // 收费信息缴款码验证
        bdcSlSfxxDOList = haFsjkYz(bdcSlSfxxDOList);
        if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxDOList.get(0);
            // 非未缴费状态。不允许作废
            for (BdcSlSfxxDO sfxxItem : bdcSlSfxxDOList) {
                if (sfxxItem.getSfzt() != null && !BdcSfztEnum.WJF.key().equals(sfxxItem.getSfzt())) {
                    result.put("code", "1");
                    result.put("msg", "非未缴费状态，不允许作废！");
                    return result;
                }
            }

            // 单个sfxxid时business_id为xmid,批量查询时business_id为交易凭证号字段
            String businessId = StringUtils.isNotBlank(bdcSlSfxxDO.getJypzh()) ? bdcSlSfxxDO.getJypzh() : bdcSlSfxxDO.getXmid();
            Map<String, String> paramMap = new HashMap<>(4);
            paramMap.put("business_id", businessId);
            paramMap.put("businessnumber", bdcSlSfxxDO.getJfsbm());
            paramMap.put("hold1", "");
            paramMap.put("hold2", "");
            String qxdm = getQxdmByXmid(bdcSlSfxxDO.getXmid());
            setJfxxDwxx(qxdm,paramMap);
            //changeParamCharset(paramMap);
            if (StringUtil.isNotBlank(qxdm)) {
                String beanid = getJfxxBeanid(qxdm, "zfjktzs");
                if (StringUtil.isNotBlank(beanid)) {
                    LOGGER.info("票据作废入参{}, {}，{}", sfxxid, paramMap, beanid);
                    Object response = exchangeInterfaceFeignService.requestInterface(beanid, paramMap);
                    if (response != null) {
                        LOGGER.info("收费信息ID:{},票据作废调用成功,返回结果：{}", sfxxid, response);
                        PjzfResponseDTO pjzfResponseDTO = JSONObject.parseObject(JSONObject.toJSONString(response), PjzfResponseDTO.class);

                        if (null != pjzfResponseDTO
                                && "00000".equals(pjzfResponseDTO.getCode())) {
                            // 作废成功，清空收费信息和项目缴款码、二维码
                            result.put("code", "0");
                            result.put("msg", "作废成功！");
                            bdcSlSfxxDOList.forEach(sfxxItem -> {
                                sfxxItem.setJypzh(null);
                                sfxxItem.setJfsbm(null);
                                sfxxItem.setJfsewmurl(null);
                                List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxItem.getSfxxid());
                                if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                                    bdcSlSfxmDOList.forEach(t->{
                                        t.setJfsbm(null);
                                        t.setJfsewmurl(null);
                                        entityMapper.updateByPrimaryKeyNull(t);
                                    });
                                }
                                entityMapper.updateByPrimaryKeyNull(sfxxItem);
                            });
                            return result;
                        }
                    }
                }
            }
        }

        result.put("code", "1");
        result.put("msg", "作废失败！");
        return result;
    }

    /**
     * @description 批量查询收费信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/12/15 18:46
     * @param sfxxid
     * @return List<BdcSlSfxxDO>
     */
    private List<BdcSlSfxxDO> plcxsfxx(String sfxxid) {
        if (StringUtil.isBlank(sfxxid)) {
            throw new MissingArgumentException("缺失收费信息ID参数！");
        }
        // 兼容批量查询缴费情况
        String[] sfxxidArr = sfxxid.split(CommonConstantUtils.ZF_YW_DH);
        List<String> sfxxidList = Arrays.asList(sfxxidArr);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = new ArrayList<>();
        // 批量查询收费信息
        if (sfxxidList.size() > 500) {
            List<List> partList = ListUtils.subList(sfxxidList, 500);
            for (List<String> sfxxidPartList : partList) {
                List<BdcSlSfxxDO> bdcSlSfxxDOPartList = bdcSlSfxxService.listBdcSlSfxxPl(null, null, sfxxidPartList, null, false);
                if (CollectionUtils.isNotEmpty(bdcSlSfxxDOPartList)) {
                    bdcSlSfxxDOList.addAll(bdcSlSfxxDOPartList);
                }
            }

        } else {
            bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxPl(null, null, sfxxidList, null, false);
        }
        return bdcSlSfxxDOList;
    }

    /**
     * @description 淮安非税缴款验证
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/11/23 16:28
     * @param bdcSlSfxxDOList
     * @return List<BdcSlSfxxDO>
     */
    private List<BdcSlSfxxDO> haFsjkYz(List<BdcSlSfxxDO> bdcSlSfxxDOList) {
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            throw new AppException("未查询到收费信息");
        }

        bdcSlSfxxDOList.forEach(sfxxItem -> {
            if (StringUtils.isBlank(sfxxItem.getJfsbm())) {
                throw new AppException("收费信息ID:" + sfxxItem.getSfxxid() + "未查询到缴款码信息");
            }
        });
        // 批量查询时校验缴费书编码是否相同
        if (bdcSlSfxxDOList.size() > 1) {
            int jfsbmSize = bdcSlSfxxDOList.stream().map(sfxxItem -> sfxxItem.getJfsbm()).distinct().collect(Collectors.toList()).size();
            if (jfsbmSize > 1) {
                throw new AppException("缴款码不一致");
            }
            int jypzhSize = bdcSlSfxxDOList.stream().map(sfxxItem -> sfxxItem.getJypzh()).distinct().collect(Collectors.toList()).size();
            if (jypzhSize > 1) {
                throw new AppException("交易凭证号不一致");
            }
        }
        // 根据缴款码查询出该缴款码的所有收费信息
        String jfsbm = bdcSlSfxxDOList.get(0).getJfsbm();
        if (StringUtils.isNotBlank(jfsbm)) {
            BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
            bdcSlSfxxDO.setJfsbm(jfsbm);
            bdcSlSfxxDOList = bdcSlSfxxService.queryBdcSlSfxx(bdcSlSfxxDO);
        }
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            throw new AppException("未查询到收费信息");
        }
        return bdcSlSfxxDOList;
    }

    private String updateHyzfxx(Object response, BdcSlSfxxDO bdcSlSfxxDO, String gzlslid, String type) {
        JSONObject responseObject = JSON.parseObject(JSON.toJSONString(response));
        JSONObject resultObject = responseObject.getJSONObject("result");
        String payUrl = "";
        if (Objects.nonNull(resultObject)) {
            Map body = (Map) resultObject.get("body");
            String orderId = MapUtils.getString(body, "orderId", "");
            payUrl = MapUtils.getString(body, "payUrl", "");
            String jkm = MapUtils.getString(body, "jkm", "");
            if (StringUtils.isNotBlank(payUrl)) {
                LOGGER.warn("当前流程实例id{}type={}合一支付地址{}订单id{}缴款码{}", gzlslid, type, payUrl, orderId, jkm);
                //更新买方的收费信息，并返回url地址
                bdcSlSfxxDO.setHyzfurl(payUrl);
                bdcSlSfxxDO.setHyzfddid(orderId);
                //保存缴款书二维码url
                bdcSlSfxxDO.setJfsewmurl(jkm);
                bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
            }
        }
        return payUrl;
    }

    /**
     * 多线程处理收费信息没有收费项目的数据
     */
    private void executeSfxxNoSfxmData(List<BdcdjjfglVO> bdcdjjfglVOList) {
        bdcdjjfglVOList = bdcdjjfglVOList.stream().filter(t -> StringUtils.isNotBlank(t.getQlr())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(bdcdjjfglVOList)) {
            //判断是否不走多线程计数限制
            boolean sfbjs = (bdcdjjfglVOList.size() <= 1);

            List<BdcAutoCountSfxxSfxmThread> listThread = new ArrayList<>(10);
            for(BdcdjjfglVO bdcdjjfglVO : bdcdjjfglVOList){
                BdcAutoCountSfxxSfxmThread bdcAutoCountSfxxSfxmThread = new BdcAutoCountSfxxSfxmThread(bdcdjjfglVO.getGzlslid(), bdcSfService);
                bdcAutoCountSfxxSfxmThread.setSfbjs(sfbjs);
                listThread.add(bdcAutoCountSfxxSfxmThread);
            }
            //多线程处理操作
            threadEngine.excuteThread(listThread, true);
        }
    }

    /**
     * 处理推送收费合计信息、流程总数
     */
    private void dealTsjfHjxx(BdcLcTsjjsDTO bdcLcTsjjsDTO, List<BdcdjjfglVO> bdcdjjfglVOList){
        Set<String> slbhSet = new HashSet();
        Double hj =0.00;
        if(CollectionUtils.isNotEmpty(bdcdjjfglVOList)){
            for(BdcdjjfglVO bdcdjjfglVO:bdcdjjfglVOList){
                if(StringUtils.isNotBlank(bdcdjjfglVO.getSlbh())) {
                    slbhSet.add(bdcdjjfglVO.getSlbh());
                }
            }
            hj = bdcdjjfglVOList.stream().filter(bdcdjjfglVO -> null != bdcdjjfglVO.getHj()).map(t -> new BigDecimal(String.valueOf(t.getHj())))
                    .reduce((m, n) -> m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
        }
        bdcLcTsjjsDTO.setHj(hj);
        bdcLcTsjjsDTO.setLcsl(slbhSet.size());
    }

    /**
     *  插入流程和缴费关系
     */
    private void dealLcTsjfGx(List<BdcdjjfglVO> bdcdjjfglVOList, BdcSlSfxxQO bdcSlSfxxQO, String tslx) {
        if (CollectionUtils.isNotEmpty(bdcdjjfglVOList)) {
            List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = bdcLcTsjfGxService.listLcTsjfGx(bdcSlSfxxQO.getGzlslid());
            // 比对是否存在
            List<BdcLcTsjfGxDO> addList = new ArrayList<>(bdcdjjfglVOList.size());
            if(CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)){
                List<String> sfxxidList = bdcLcTsjfGxDOList.stream().filter(t -> StringUtils.isNotBlank(t.getSfxxid())).map(BdcLcTsjfGxDO::getSfxxid).collect(Collectors.toList());
                int xh = sfxxidList.size();
                for(BdcdjjfglVO bdcdjjfglVO : bdcdjjfglVOList){
                    if(!sfxxidList.contains(bdcdjjfglVO.getSfxxid())){
                        addList.add(this.generateGxData(bdcdjjfglVO, bdcSlSfxxQO.getGzlslid(), tslx, xh++));
                    }
                }
            }else{
                int xh = 0;
                for(BdcdjjfglVO bdcdjjfglVO : bdcdjjfglVOList){
                    addList.add(this.generateGxData(bdcdjjfglVO, bdcSlSfxxQO.getGzlslid(), tslx, xh++));
                }
            }
            // 批量更新与修改
            if(CollectionUtils.isNotEmpty(addList)){
                this.entityMapper.insertBatchSelective(addList);
            }
        }
    }

    /**
     * 生成关系数据
     */
    private BdcLcTsjfGxDO generateGxData(BdcdjjfglVO bdcdjjfglVO, String gzlslid, String tslx, int xh){
        BdcLcTsjfGxDO bdcLcTsjfGxDO = new BdcLcTsjfGxDO();
        bdcLcTsjfGxDO.setGxid(UUIDGenerator.generate16());
        bdcLcTsjfGxDO.setGzlslid(gzlslid);
        bdcLcTsjfGxDO.setSfxxid(bdcdjjfglVO.getSfxxid());
        bdcLcTsjfGxDO.setSlbh(bdcdjjfglVO.getSlbh());
        bdcLcTsjfGxDO.setTslx(tslx);
        bdcLcTsjfGxDO.setQlrmc(bdcdjjfglVO.getQlr());
        bdcLcTsjfGxDO.setDlrmc(bdcdjjfglVO.getQlrdlr());
        bdcLcTsjfGxDO.setXh(xh);
        return bdcLcTsjfGxDO;
    }

    /**
     * 根据权利人名称判断是否月结银行，是月结银行则查询权属状态为：1, 2 的数据，否则查询权属状态为：0 的数据
     */
    private List<Integer> getQueryParamQsztByDyqr(BdcSlSfxxQO bdcSlSfxxQO){
        String qlrmc = bdcSlSfxxQO.getDyaqr();
        if(StringUtils.isBlank(qlrmc) && CollectionUtils.isNotEmpty(bdcSlSfxxQO.getSlbhList())){
            // 获取抵押的权利人信息
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setSlbh(bdcSlSfxxQO.getSlbhList().get(0));
            bdcXmQO.setQllxs(Arrays.asList(CommonConstantUtils.QLLX_DYAQ_DM));
            List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(bdcXmDOList.get(0).getXmid());
                bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    qlrmc = bdcQlrDOList.get(0).getQlrmc();
                }
            }
        }
        if (StringUtils.isNotBlank(qlrmc)) {
            List<BdcXtJgDO> bdcXtJgDOList = bdcXtJgFeignService.listAyjsBdcXtJgYhmc(qlrmc);
            if (CollectionUtils.isNotEmpty(bdcXtJgDOList)) {
                return Arrays.asList(CommonConstantUtils.QSZT_VALID, CommonConstantUtils.QSZT_HISTORY) ;
            }
        }
        return Arrays.asList(CommonConstantUtils.QSZT_TEMPORY);
    }

    /**
     * 查询抵押权信息（过滤了bdcdyh中存在<code>W</code>的数据）
     */
    private List<String> getXmidListParamFromDyaxx(BdcSlSfxxQO bdcSlSfxxQO){
        List<String> xmidList = new ArrayList<>();
        BdcDyaQo bdcDyaQo = new BdcDyaQo();
        bdcDyaQo.setQlrmc(bdcSlSfxxQO.getDyaqr());
        bdcDyaQo.setDyfs(bdcSlSfxxQO.getDyfs());
        bdcDyaQo.setDlr(bdcSlSfxxQO.getDlrmc());
        bdcDyaQo.setSlbhList(bdcSlSfxxQO.getSlbhList());
        bdcDyaQo.setQxdmList(bdcSlSfxxQO.getQxdmList());
        bdcDyaQo.setQszt(bdcSlSfxxQO.getQszt());
        List<BdcDyaqQlrDTO> bdcDyaqDOList = bdcBdcdyFeignService.listBdcDyaqByQlrmc(bdcDyaQo);
        if(CollectionUtils.isNotEmpty(bdcDyaqDOList)){ xmidList = bdcDyaqDOList.stream().map(BdcDyaqQlrDTO::getXmid).collect(Collectors.toList());
        }
        return xmidList;
    }

    @Override
    public BdcLcTsjjsDTO listXwqySfxx(BdcSlSfxxQO bdcSlSfxxQO) {
        BdcLcTsjjsDTO bdcLcTsjjsDTO =new BdcLcTsjjsDTO().init();
        // 1、查询参数没有值时，直接查询 关系表中的数据
        if (StringUtils.isNotBlank(bdcSlSfxxQO.getGzlslid()) && NoQueryParam(bdcSlSfxxQO)) {
            return generateXwqySfxx(bdcSlSfxxQO.getGzlslid(),false, false);
        }
        // 2、根据受理编号、权利人名称、证件号 查询收费信息
        if (CollectionUtils.isNotEmpty(bdcSlSfxxQO.getSlbhList()) || StringUtils.isNotBlank(bdcSlSfxxQO.getQlrmc()) || StringUtils.isNotBlank(bdcSlSfxxQO.getQlrzjh())) {
            // 2.1、根据权利人名称、证件号，获取对应的项目信息
            List<String> xmids = this.getXmidListByQlrxx(bdcSlSfxxQO.getQlrmc(), bdcSlSfxxQO.getQlrzjh(), bdcSlSfxxQO.getMhlx());
            // 2.2、没有找到合适的查询条件，返回数据库关系查询结果
            if (CollectionUtils.isEmpty(bdcSlSfxxQO.getSlbhList()) && CollectionUtils.isEmpty(xmids)) {
                return generateXwqySfxx(bdcSlSfxxQO.getGzlslid(),false,false);
            }
            // 2.3、获取收费信息，查询权属状态为：0和1 ，收费状态为：已收费和 未缴费 的收费信息数据
            List<BdcdjjfglVO> bdcdjjfglVOList = new ArrayList<>();
            bdcSlSfxxQO.setQsztList(Arrays.asList(CommonConstantUtils.QSZT_TEMPORY, CommonConstantUtils.QSZT_VALID));
            bdcSlSfxxQO.setJmyynot("111");

            if(xmids.size() > 1000){
                List<List> partList = ListUtils.subList(xmids, 500);
                for (List partXmidList : partList) {
                    bdcSlSfxxQO.setXmidList(partXmidList);
                    bdcdjjfglVOList.addAll(this.bdcSlSfxxService.listPlTsJfxx(bdcSlSfxxQO));
                }
            }else{
                bdcSlSfxxQO.setXmidList(xmids);
                bdcdjjfglVOList = this.bdcSlSfxxService.listPlTsJfxx(bdcSlSfxxQO);
            }

            if(CollectionUtils.isNotEmpty(bdcdjjfglVOList)){
                // 2.4 去除抵押权利数据
                //bdcdjjfglVOList = bdcdjjfglVOList.stream().filter(t -> !Objects.equals(CommonConstantUtils.QLLX_DYAQ_DM, t.getQllx())).collect(Collectors.toList());

                // 2.5 增流程缴费关联关系
                this.dealLcTsjfGx(bdcdjjfglVOList, bdcSlSfxxQO, CommonConstantUtils.PLSF_TSLX_XWQY);

                // 2.6 处理关联收费信息数据
                return this.generateXwqySfxx(bdcSlSfxxQO.getGzlslid(), true,true);
            }
        }
        return bdcLcTsjjsDTO;
    }

    /**
     *  查询当前流程关联的小微企业信息
     *  @param gzlslid 工作流实例ID
     *  @param clsfxm 是否处理收费项目收费金额
     *  @param orderInput 是否按输入的受理编号排序
     */
    private BdcLcTsjjsDTO generateXwqySfxx(String gzlslid, boolean clsfxm, boolean orderInput){
        // 1、查询关联的收费信息数据
        List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = bdcLcTsjfGxService.listLcTsjfGx(gzlslid);

        // 2、初始化页面返回信息
        BdcLcTsjjsDTO bdcLcTsjjsDTO = new BdcLcTsjjsDTO().init();
        BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if(bdcSlJbxxDO != null && StringUtils.isNotBlank(bdcSlJbxxDO.getSlbh())){
            bdcLcTsjjsDTO.setLcslbh(bdcSlJbxxDO.getSlbh());
        }

        // 3、通过关系信息获取收费信息数据
        List<BdcdjjfglVO> bdcdjjfglVOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)) {
            List<String> sfxxidList = bdcLcTsjfGxDOList.stream().filter(bdcLcTsjfGxDO -> StringUtils.isNotBlank(bdcLcTsjfGxDO.getSfxxid())).map(BdcLcTsjfGxDO::getSfxxid).collect(Collectors.toList());
            // 对超过1000条数据进行分段查询
            bdcdjjfglVOList  = this.queryJfxx(sfxxidList);
            if(CollectionUtils.isNotEmpty(bdcdjjfglVOList) && clsfxm){
                // 3.1 开启多线程，处理没有生成收费项目的数据
                this.executeSfxxNoSfxmData(bdcdjjfglVOList);

                // 3.2 数据处理完成后，重新查询数据（为了获取生成后的收费金额）
                bdcdjjfglVOList  = this.queryJfxx(sfxxidList);
            }

            bdcLcTsjjsDTO.setCode(0);
            bdcLcTsjjsDTO.setTotalElements(bdcdjjfglVOList.size());
            bdcLcTsjjsDTO.setContent(bdcdjjfglVOList);
        }

        // 4、计算收费总合计、流程数量
        this.dealTsjfHjxx(bdcLcTsjjsDTO, bdcdjjfglVOList);
        if(CollectionUtils.isEmpty(bdcLcTsjjsDTO.getContent())){
            bdcLcTsjjsDTO.setCode(0);
        }else{
            if(orderInput){
                // 根据输入的受理编号进行排序
                this.orderData(gzlslid, bdcLcTsjjsDTO);
            }else{
                // 按关系表数据进行排序
                bdcLcTsjjsDTO.setContent(bdcLcTsjjsDTO.getContent().stream()
                        .sorted(Comparator.comparing(t-> t.getXh(),  Comparator.nullsLast(Integer::compareTo))).collect(Collectors.toList()));
            }
            // 5、回写大云权利人名称和坐落
            this.hxPljfxx(bdcLcTsjjsDTO.getContent(), bdcSlJbxxDO);
        }
        return bdcLcTsjjsDTO;
    }

    @Override
    public void modifySfxxJmyyAndSfsdjf(BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isAnyBlank(bdcSfxxCzQO.getGzlslid(), bdcSfxxCzQO.getJmyy()) ){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID或减免原因");
        }
        if(CollectionUtils.isNotEmpty(bdcSfxxCzQO.getSfxxidList())){
            this.plxgSfxxJmyy(bdcSfxxCzQO.getSfxxidList(), bdcSfxxCzQO.getJmyy());
        }else{
            List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = this.bdcLcTsjfGxService.listLcTsjfGx(bdcSfxxCzQO.getGzlslid());
            if (CollectionUtils.isEmpty(bdcLcTsjfGxDOList)) {
                throw new AppException(ErrorCode.MISSING_ARG, "未获取到关联的收费信息");
            }
            List<String> sfxxids = bdcLcTsjfGxDOList.stream().map(BdcLcTsjfGxDO::getSfxxid).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(sfxxids)){
                if(sfxxids.size() > 500){
                    List<List> partList = ListUtils.subList(sfxxids, 500);
                    for (List partSfxxidList : partList) {
                        this.plxgSfxxJmyy(partSfxxidList, bdcSfxxCzQO.getJmyy());
                    }
                }
            }
        }
    }

    /**
     * @param slbh
     * @param sfxxid
     * @param qlrlb
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 挂接收费信息
     * @date : 2022/8/1 14:08
     */
    @Override
    public void gjSfxx(String slbh, String sfxxid, String qlrlb) {
        //1. 先根据受理编号查收费信息
        if (StringUtils.isNoneBlank(slbh, sfxxid, qlrlb)) {
            BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
            bdcSlSfxxQO.setSlbh(slbh);
            bdcSlSfxxQO.setQlrlb(qlrlb);
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxBySlbh(bdcSlSfxxQO);
            if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
                throw new AppException("未找到受理编号" + slbh + "对应的收费信息");
            }
            //2.判断当前收费信息合计和需要挂接的数据是否一致，不一致不允许挂接
            BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfxxid);
            if (Objects.nonNull(bdcSlSfxxDO)) {
                BdcSlSfxxDO gjSfxx = bdcSlSfxxDOList.get(0);
                if (!Objects.equals(bdcSlSfxxDO.getHj(), gjSfxx.getHj())) {
                    throw new AppException("挂接受理编号" + slbh + "对应的收费信息合计值为" + gjSfxx.getHj() + "，与当前流程不一致无法挂接");
                }
                //3. 判断当前流程是否已缴费，jfsbm不为空
                if (StringUtils.isNotBlank(bdcSlSfxxDO.getJfsbm())) {
                    throw new AppException("当前流程已存在缴费信息不允许挂接");
                }
                //4. 判断缴费书编码是否已经被使用--判断逻辑：根据缴费书编码查询收费信息sfsc ！= 1 的数据如果已存在不允许挂接
                if (StringUtils.isNotBlank(gjSfxx.getJfsbm())) {
                    bdcSlSfxxQO = new BdcSlSfxxQO();
                    bdcSlSfxxQO.setJfsbm(gjSfxx.getJfsbm());
                    bdcSlSfxxQO.setQlrlb(qlrlb);
                    bdcSlSfxxQO.setSfsc(CommonConstantUtils.SF_F_DM);
                    List<BdcSlSfxxDO> bdcSlSfxxDOS = bdcSlSfxxService.listBdcSlSfxxBySlbh(bdcSlSfxxQO);
                    if (CollectionUtils.isNotEmpty(bdcSlSfxxDOS)) {
                        for (BdcSlSfxxDO bdcSlSfxx : bdcSlSfxxDOS) {
                            if (!StringUtils.equals(gjSfxx.getSfxxid(), bdcSlSfxx.getSfxxid())) {
                                throw new AppException("挂接受理编号" + slbh + "对应的缴费书编码" + gjSfxx.getJfsbm() + "已经被使用，不允许重复挂接");
                            }
                        }
                    }
                }
                bdcSlSfxxDO.setSfsj(gjSfxx.getSfsj());
                bdcSlSfxxDO.setSfztczsj(gjSfxx.getSfztczsj());
                bdcSlSfxxDO.setFph(gjSfxx.getFph());
                bdcSlSfxxDO.setSfkp(gjSfxx.getSfkp());
                bdcSlSfxxDO.setJfsbm(gjSfxx.getJfsbm());
                bdcSlSfxxDO.setJfsewmurl(gjSfxx.getJfsewmurl());
                bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
            }
        }
    }

    @Override
    public List<BdcYhkkDTO> getYhkkxx(String htbh, String gzlslid) {
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        List<BdcYhkkDTO> bdcYhkkDTOList = new ArrayList<>(16);
        int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        if (lclx == 2 || lclx == 4) {
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                bdcXmDTOList = bdcXmDTOList.stream().filter(xmDO -> ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, xmDO.getQllx())).collect(Collectors.toList());
            }
        }
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            String qxdm = bdcXmDTOList.get(0).getQxdm();
            List<BdcQlrDO> bdcQlrDOS = this.getQlrxx(bdcXmDTOList.get(0).getXmid(), CommonConstantUtils.QLRLB_QLR);
            if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
                String zjh = bdcQlrDOS.get(0).getZjh();
                Map<String, Object> map = new HashMap<>(3);
                map.put("htbh", htbh);
                map.put("qxdm", qxdm);
                map.put("zjh", zjh);
                LOGGER.info("查询银行端扣款数据接口请求参数：{}", map);
                Object response = exchangeInterfaceFeignService.requestInterface("sw_getYhdkkxx", map);
                LOGGER.info("查询银行端扣款数据接口返回：{}", response);
                if (Objects.nonNull(response)) {
                    QuerySwxxResponseDTO querySwxxResponseDTO = JSON.parseObject(JSON.toJSONString(response), QuerySwxxResponseDTO.class);
                    if ("1".equals(querySwxxResponseDTO.getResponseCode())) {
                        List<QuerySwxxHsxxDTO> hsxxList = querySwxxResponseDTO.getHsxxList();
                        if (CollectionUtils.isNotEmpty(hsxxList)) {
                            List<BdcSlHsxxDO> bdcSlHsxxDOList = new ArrayList<>(hsxxList.size());
                            int xh = 0;
                            for (QuerySwxxHsxxDTO querySwxxHsxxDTO : hsxxList) {
                                BdcSlHsxxDO bdcSlHsxxDO = querySwxxHsxxDTO.getBdcSlHsxxDO();
                                String bz = querySwxxHsxxDTO.getBz();
                                BdcYhkkDTO bdcYhkkDTO = new BdcYhkkDTO();
                                xh ++;
                                bdcYhkkDTO.setXh(xh);
                                bdcYhkkDTO.setNsrmc(bdcSlHsxxDO.getNsrmc());
                                bdcYhkkDTO.setNsrsbh(bdcSlHsxxDO.getNsrsbh());
                                bdcYhkkDTO.setYbtse(bdcSlHsxxDO.getYnsehj());
                                bdcYhkkDTO.setDzsph(bdcSlHsxxDO.getSphm());
                                bdcYhkkDTO.setSkssjg(bdcSlHsxxDO.getSwjgdm());
                                bdcYhkkDTO.setZrfcsfbs(bdcSlHsxxDO.getSqrlb());
                                bdcYhkkDTO.setKkfhxx(bz);
                                bdcYhkkDTOList.add(bdcYhkkDTO);
                                bdcSlHsxxDO.setXmid(bdcXmDTOList.get(0).getXmid());
                                bdcSlHsxxDO.setJypzh(htbh);
                                bdcSlHsxxDOList.add(bdcSlHsxxDO);
                            }
                            // 保存完税信息
                            if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                                entityMapper.batchSaveSelective(bdcSlHsxxDOList);
                            }
                        }
                    }
                }
            }
        }
        return bdcYhkkDTOList;
    }

    /**
     * 获取权利人信息
     * @param xmid  项目ID
     * @param qlrlb  权利人类别
     */
    private List<BdcQlrDO> getQlrxx(String xmid, String qlrlb){
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(qlrlb);
        return bdcQlrFeignService.listBdcQlr(bdcQlrQO);
    }

    @Override
    public CommonResponse hqsp(String gzlslid, String qlrlb) {
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcXmDTOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未查询到项目信息");
        }
        List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxxByGzlslidAndSqrlb(gzlslid, qlrlb);
        if(CollectionUtils.isEmpty(bdcSlHsxxDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到核税信息");
        }
        String htbh = bdcSlHsxxDOList.get(0).getJypzh();
        String qxdm = bdcXmDTOList.get(0).getQxdm();
        List<BdcQlrDO> bdcQlrDOS = this.getQlrxx(bdcXmDTOList.get(0).getXmid(), qlrlb);
        if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
            Map<String, Object> map = new HashMap<>(3);
            map.put("htbh", htbh);
            map.put("qxdm", qxdm);
            map.put("zjh", bdcQlrDOS.get(0).getZjh());
            LOGGER.info("查询房产交易完税信息：{}", map);
            Object response = exchangeInterfaceFeignService.requestInterface("sw_getZkwszm", map);
            LOGGER.info("查询房产交易完税信息：{}", response);
            if (Objects.nonNull(response)) {
                QuerySwxxResponseDTO querySwxxResponseDTO = JSON.parseObject(JSON.toJSONString(response), QuerySwxxResponseDTO.class);
                if ("1".equals(querySwxxResponseDTO.getResponseCode())) {
                    List<QuerySwxxHsxxDTO> hsxxList = querySwxxResponseDTO.getHsxxList();
                    if (CollectionUtils.isNotEmpty(hsxxList)) {
                        List<BdcSwspFjDTO> swspFjDTOS = hsxxList.get(0).getSwspFjDTOS();
                        if (CollectionUtils.isNotEmpty(swspFjDTOS)) {
                            try {
                                bdcUploadFileUtils.uploadBase64File(CommonConstantUtils.BASE64_QZ_PDF + swspFjDTOS.get(0).getFile(), gzlslid, "电子税票"+qlrlb, "", ".pdf");
                                return CommonResponse.ok();
                            } catch (IOException e) {
                                e.printStackTrace();
                                return CommonResponse.fail("上传税票信息失败，" + e.getMessage());
                            }
                        }
                    }
                }
            }
        }
        return CommonResponse.fail("获取税票失败！");
    }

    /**
     * 批量修改收费信息减免原因
     * <p>
     * 批量修改收费信息减免原因，减免原因为：小微企业时，修改是否收登记费为：否
     * （1）将BDC_SL_SFXM表中的登记费的实收金额置为0
     * （2）重新计算 BDC_SL_SFXX 的HJ 字段值
     * </p>
     *
     * @param sfxxidList 收费信息ID集合
     * @param jmyy       减免原因
     */
    private void plxgSfxxJmyy(List<String> sfxxidList, String jmyy) {
        // 当减免原因为小微企业是更改
        if(StringUtils.equals(jmyy, "111")){
            // 1、批量修改收费项目的登记费实收金额为0
            this.bdcSlSfxmService.plxgDjfSfxmSsje(sfxxidList, new Double(0));

            // 2、批量更新sfxx表 减免原因、是否收登记费、 hj
            BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
            bdcSlSfxxQO.setSfxxidList(sfxxidList);
            bdcSlSfxxQO.setJmyy(jmyy);
            bdcSlSfxxQO.setSfsdjf(CommonConstantUtils.SF_F_DM);
            this.bdcSlSfxxMapper.plxgSfxxJmyyAndCountHj(bdcSlSfxxQO);
        }
    }
}
