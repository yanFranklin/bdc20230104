package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.core.domain.BdcDsQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcFctdPpgxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclmxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.UpdateSlztRequestBody;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.UpdateSlztRequestData;
import cn.gtmap.realestate.common.core.enums.HlwSlztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.EntityService;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcPpFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.etl.core.domian.wwsq.*;
import cn.gtmap.realestate.etl.core.dto.*;
import cn.gtmap.realestate.etl.core.qo.WwsqHtmlQO;
import cn.gtmap.realestate.etl.core.qo.WwsqQO;
import cn.gtmap.realestate.etl.core.service.HlwYwxxDataService;
import cn.gtmap.realestate.etl.service.EtlPrintService;
import cn.gtmap.realestate.etl.service.HlwYwxxService;
import cn.gtmap.realestate.etl.util.Constants;
import cn.gtmap.realestate.etl.util.FtpUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2020/5/11
 * @description
 */
@Service
public class HlwYwxxServiceImpl implements HlwYwxxService {
    /**
     * ??????
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HlwYwxxServiceImpl.class);

    private static final String FUNCTION = "????????????????????????";

    private static final String SQLY_YH = "6";

    @Autowired
    private Repo hlwRepository;

    @Resource(name = "dozerMapper")
    private DozerBeanMapper dozerMapper;
    @Autowired
    private HlwYwxxDataService hlwYwxxDataService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    EtlPrintService etlPrintService;
    @Autowired
    HttpClientService httpClientService;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    private EntityService entityService;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    BdcPpFeignService bdcPpFeignService;
    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    protected UserManagerUtils userManagerUtils;


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  ??????????????????????????????(url/ftp)
     */
    @Value("${wwsq.fjxx.bcfs:url}")
    private String wwsqfjxxbcfs;

    /**
     * ????????????FTP??????????????????????????? IP username password port
     */
    @Value("${wwsq.ftp.ip:}")
    private String ip;

    @Value("${wwsq.ftp.username:}")
    private String username;

    @Value("${wwsq.ftp.password:}")
    private String password;

    @Value("${wwsq.ftp.port:}")
    private String port;

    /**
     * ????????????FTP?????????FTP?????????????????????
     */
    @Value("${wwsq.ftp.ftpFilePath:}")
    private String ftpFilePath;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  ??????????????????
     */
    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;

    /**
     * ETL ????????? eureka ??????????????????
     */
    @Value("${spring.application.name:etl-app}")
    private String etlAppName;

    @Override
    public Page<Map> listHlwYwxxByPage(Pageable pageable, WwsqQO wwsqQO) {

        if(CollectionUtils.isNotEmpty(wwsqQO.getGzldyids())){
            wwsqQO.setSqlxList(wwsqQO.getGzldyids());
        }

        Page<Map> result = hlwRepository.selectPaging("listHlwYwxxByPageOrder", wwsqQO, pageable);
        if (result != null && CollectionUtils.isNotEmpty(result.getContent())) {
            for (Map data : result.getContent()) {
                //????????????????????????????????????????????????
                getSfpp(MapUtils.getString(data,"ZSXMID"),data);

            }
        }
        return result;
    }

    @Override
    public JSONObject queryHlwJsonByHlwXmid(String hlwxmid,String slr) {
        JSONObject jsonObject = new JSONObject();
        GxWwSqxmDo gxWwSqxmDo = hlwYwxxDataService.gxWwsqxmByHlwXmid(hlwxmid);
        List<GxWwSqxxDo> gxWwSqxxDoList = hlwYwxxDataService.listGxWwSqxxByXmid(hlwxmid);
        if (gxWwSqxmDo == null || CollectionUtils.isEmpty(gxWwSqxxDoList)) {
            throw new AppException("???????????????????????????id????????????????????????????????????");
        }
        jsonObject.put("spxtywh", gxWwSqxmDo.getSqslbh());
        List<FjclDTO> fjclDTOList = new ArrayList<>();
        WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO = new WwsqCjBdcXmRequestDTO();
        if(!StringUtils.equals(SQLY_YH,gxWwSqxmDo.getSqly())) {
            //?????????????????????
            wwsqCjBdcXmRequestDTO.setSendmsg(true);
        }
        //???????????????
        dozerMapper.map(gxWwSqxmDo, wwsqCjBdcXmRequestDTO);

        BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();
        wwsqCjBdcXmRequestDTO.setBdcSlxxDTO(bdcSlxxDTO);
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);
        //??????????????????
        dozerMapper.map(gxWwSqxmDo, bdcSlJbxxDO);
        if(StringUtils.equals(CommonConstantUtils.SF_S_DM.toString(),gxWwSqxmDo.getZdzf())){
            //????????????????????????gx_ww_sqxm ???sqrmc
            bdcSlJbxxDO.setSlr(gxWwSqxmDo.getSqrmc());
            //?????????????????????,?????????????????????????????????????????????
            wwsqCjBdcXmRequestDTO.setZdzfslr(slr);
            LOGGER.error("???????????????{}???????????????????????????:{}",gxWwSqxmDo.getSqslbh(),slr);
        }else if(StringUtils.isNotBlank(slr)){
            bdcSlJbxxDO.setSlr(slr);
        }else{
            //???????????????????????????????????????,????????????????????????????????????
            BdcXmQO bdcXmQO =new BdcXmQO();
            bdcXmQO.setSpxtywh(gxWwSqxmDo.getSqslbh());
            List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                bdcXmDOList.sort(Comparator.comparing(BdcXmDO::getSlsj));
                String slrid =bdcXmDOList.get(bdcXmDOList.size()-1).getSlrid();
                if(StringUtils.isNotBlank(slrid)) {
                    UserDto userDto =userManagerUtils.getUserByUserid(slrid);
                    if(userDto != null) {
                        bdcSlJbxxDO.setSlr(userDto.getUsername());
                    }
                }
            }
        }
        //????????????BdcSlXmDTO
        List<BdcSlXmDTO> xmDTOList = new ArrayList<>();
        bdcSlxxDTO.setBdcSlXmList(xmDTOList);
        List<GxWwSqxxClxxDo> gxWwSqxxClxxDoList = new ArrayList<>();
        Map<String, Integer> dyhSxhMap = new HashMap<>();
        for (GxWwSqxxDo gxWwSqxxDo : gxWwSqxxDoList) {
            BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();

            BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
            dozerMapper.map(gxWwSqxxDo, bdcSlXmDO);
            bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);
            //???????????????????????????????????????????????????
            if (StringUtils.isNotBlank(gxWwSqxxDo.getZsxmid())) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(gxWwSqxxDo.getZsxmid());
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                    bdcSlXmDO.setBdcdyh(bdcXmDOList.get(0).getBdcdyh());
                }else{
                    LOGGER.error("zsxmid:{}??????????????????????????????",gxWwSqxxDo.getZsxmid());
                    throw new AppException("??????????????????????????????");

                }
            }

            DsfSlxxDTO dsfSlxxDTO = new DsfSlxxDTO();
            dozerMapper.map(gxWwSqxmDo, dsfSlxxDTO);
            dozerMapper.map(gxWwSqxxDo, dsfSlxxDTO);
            //???????????????
            dsfSlxxDTO.setSxh(deaSxh(dyhSxhMap, gxWwSqxxDo.getBdcdyh()));
            if (dsfSlxxDTO.getSxh() >1) {
                dsfSlxxDTO.setGlYxm(true);
            }
            bdcSlXmDTO.setDsfSlxxDTO(dsfSlxxDTO);

            //????????????
            //??????
            if(CommonConstantUtils.QLLX_CF.equals(bdcSlXmDO.getQllx())){
                BdcSlCfjfDO bdcSlCfjfDO =new BdcSlCfjfDO();
                bdcSlXmDTO.setBdcSlQl(bdcSlCfjfDO);
            }

            BdcSlJyxxDO bdcSlJyxxDO = new BdcSlJyxxDO();
            dozerMapper.map(gxWwSqxxDo, bdcSlJyxxDO);
            bdcSlXmDTO.setBdcSlJyxxDO(bdcSlJyxxDO);

            // ????????????????????????
            if (StringUtils.isNotBlank(gxWwSqxxDo.getZsxmid()) &&dsfSlxxDTO.getSxh() ==1) {
                BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO();
                bdcSlXmLsgxDO.setYxmid(gxWwSqxxDo.getZsxmid());
                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOS = new ArrayList<>();
                bdcSlXmLsgxDOS.add(bdcSlXmLsgxDO);
                bdcSlXmDTO.setBdcSlXmLsgxList(bdcSlXmLsgxDOS);
            }
            //?????????
            List<GxWwSqxxQlrDo> gxWwSqxxQlrDoList = hlwYwxxDataService.listGxWwSqxxQlrBySqxxid(gxWwSqxxDo.getSqxxid());
            if (CollectionUtils.isNotEmpty(gxWwSqxxQlrDoList)) {
                bdcSlXmDTO.setBdcSlSqrDOList(new ArrayList<>());
                for (GxWwSqxxQlrDo qlrxx : gxWwSqxxQlrDoList) {
                    //?????????
                    if(StringUtils.equals("jkr",qlrxx.getQlrlx())){
                        BdcDsQlrDO bdcDsQlrDO = new BdcDsQlrDO();
                        dozerMapper.map(qlrxx, bdcDsQlrDO);
                        if (CheckParameter.checkAnyParameter(bdcDsQlrDO)) {
                            if(CollectionUtils.isEmpty(bdcSlXmDTO.getBdcDsQlrDOList())){
                                bdcSlXmDTO.setBdcDsQlrDOList(new ArrayList<>());
                            }
                            bdcDsQlrDO.setQlrlb(CommonConstantUtils.DSQLR_QLRLB_ZWR);
                            bdcDsQlrDO.setQlrid(UUIDGenerator.generate16());
                            bdcSlXmDTO.getBdcDsQlrDOList().add(bdcDsQlrDO);
                        }
                    }else {
                        BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                        dozerMapper.map(qlrxx, bdcSlSqrDO);
                        if (CheckParameter.checkAnyParameter(bdcSlSqrDO)) {
                            bdcSlXmDTO.getBdcSlSqrDOList().add(bdcSlSqrDO);
                        }
                    }
                }
            }
            //????????????jkr?????????gx_ww_sqxx???zwr??????(gxWwSqxxDo)
            if(CollectionUtils.isEmpty(bdcSlXmDTO.getBdcDsQlrDOList()) && StringUtils.isNotBlank(gxWwSqxxDo.getZwr())){
                bdcSlXmDTO.setBdcDsQlrDOList(new ArrayList<>());
                BdcDsQlrDO bdcDsQlrDO = new BdcDsQlrDO();
                bdcDsQlrDO.setQlrmc(gxWwSqxxDo.getZwr());
                bdcDsQlrDO.setQlrlb(CommonConstantUtils.DSQLR_QLRLB_ZWR);
                bdcDsQlrDO.setQlrid(UUIDGenerator.generate16());
                bdcSlXmDTO.getBdcDsQlrDOList().add(bdcDsQlrDO);
            }

            //??????
            List<GxWwSqxxClxxDo> wwSqxxClxxDoList = hlwYwxxDataService.listGxWwSqxxClxxBySqxxid(gxWwSqxxDo.getSqxxid());
            if (CollectionUtils.isNotEmpty(wwSqxxClxxDoList)) {
                gxWwSqxxClxxDoList.addAll(wwSqxxClxxDoList);
            }
            //????????????
            List<GxWwSwxxDo> gxWwSwxxDoList = hlwYwxxDataService.listGxWwSwxxBySqxxid(gxWwSqxxDo.getSqxxid());
            if (CollectionUtils.isNotEmpty(gxWwSwxxDoList)) {
                wwsqCjBdcXmRequestDTO.setSfss(true);
                bdcSlXmDTO.setBdcSwxxDTOList(new ArrayList<>());
                for (GxWwSwxxDo gxWwSwxxDo : gxWwSwxxDoList) {
                    BdcSwxxDTO bdcSwxxDTO = new BdcSwxxDTO();
                    BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
                    dozerMapper.map(gxWwSwxxDo, bdcSlHsxxDO);
                    bdcSwxxDTO.setBdcSlHsxxDO(bdcSlHsxxDO);
                    if (CollectionUtils.isNotEmpty(gxWwSwxxDo.getSwmx())) {
                        bdcSwxxDTO.setBdcSlHsxxMxDOList(new ArrayList<>());
                        for (GxWwSwmxDo gxWwSwmxDo : gxWwSwxxDo.getSwmx()) {
                            BdcSlHsxxMxDO bdcSlHsxxMxDO = new BdcSlHsxxMxDO();
                            dozerMapper.map(gxWwSwmxDo, bdcSlHsxxMxDO);
                            bdcSwxxDTO.getBdcSlHsxxMxDOList().add(bdcSlHsxxMxDO);
                        }
                    }
                    if (CheckParameter.checkAnyParameter(bdcSwxxDTO)) {
                        bdcSlXmDTO.getBdcSwxxDTOList().add(bdcSwxxDTO);
                    }
                }
            }
            //????????????
            List<GxWwSqxxWlxxDO> gxWwSqxxWlxxDOList = hlwYwxxDataService.listGxWwSqxxWlxxBySqxxid(gxWwSqxxDo.getSqxxid());
            if(CollectionUtils.isNotEmpty(gxWwSqxxWlxxDOList)){
                BdcSlYjxxDO bdcSlYjxxDO =new BdcSlYjxxDO();
                dozerMapper.map(gxWwSqxxWlxxDOList.get(0), bdcSlYjxxDO);
                bdcSlxxDTO.setBdcSlYjxxDO(bdcSlYjxxDO);
                dozerMapper.map(gxWwSqxxWlxxDOList.get(0), dsfSlxxDTO);
            }
            //????????????
            List<GxWwSqxxSfxxDO> gxWwSqxxSfxxDOList = hlwYwxxDataService.listGxWwSqxxSfxxBySqxxid(gxWwSqxxDo.getSqxxid());
            if(CollectionUtils.isNotEmpty(gxWwSqxxSfxxDOList)){
                List<BdcSfxxDTO> bdcSfxxDTOList =new ArrayList<>();
                for(GxWwSqxxSfxxDO gxWwSqxxSfxxDO:gxWwSqxxSfxxDOList){
                    BdcSfxxDTO bdcSfxxDTO =new BdcSfxxDTO();
                    BdcSlSfxxDO bdcSlSfxxDO =new BdcSlSfxxDO();
                    dozerMapper.map(gxWwSqxxSfxxDO,bdcSlSfxxDO);
                    //???????????????????????????
                    bdcSlSfxxDO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    bdcSfxxDTO.setBdcSlSfxxDO(bdcSlSfxxDO);
                    bdcSfxxDTOList.add(bdcSfxxDTO);

                }
                bdcSlXmDTO.setBdcSfxxDTOList(bdcSfxxDTOList);
            }

            xmDTOList.add(bdcSlXmDTO);
        }
        if (CollectionUtils.isNotEmpty(gxWwSqxxClxxDoList)) {
            String appUrl = getEtlAppUrl();
            LOGGER.info("hlwxmid:{},????????????:{}???????????????????????????{},??????etlapp?????????{}",hlwxmid,gxWwSqxmDo.getSqslbh(),gxWwSqxxClxxDoList,appUrl);
            for (GxWwSqxxClxxDo gxWwSqxxClxxDo : gxWwSqxxClxxDoList) {
                FjclDTO fjclDTO = new FjclDTO();
                dozerMapper.map(gxWwSqxxClxxDo, fjclDTO);
                if (CollectionUtils.isNotEmpty(gxWwSqxxClxxDo.getFjxx())) {
                    fjclDTO.setFjclmxDTOList(new ArrayList<>());
                    for (GxWwSqxxFjxxDo gxWwSqxxFjxxDo : gxWwSqxxClxxDo.getFjxx()) {
                        if(StringUtils.isNotBlank(appUrl)){
                            gxWwSqxxFjxxDo.setFjlj(appUrl + "/realestate-etl/wwsq/fjxx/download?fjid=" + gxWwSqxxFjxxDo.getFjid());
                        }
                        FjclmxDTO fjclmxDTO = new FjclmxDTO();
                        dozerMapper.map(gxWwSqxxFjxxDo, fjclmxDTO);
                        fjclDTO.getFjclmxDTOList().add(fjclmxDTO);
                    }
                }
                fjclDTOList.add(fjclDTO);
            }
        }
        jsonObject.put("fjclList", JSONObject.toJSONString(fjclDTOList));
        jsonObject.put("acceptCjParam", JSONObject.toJSONString(wwsqCjBdcXmRequestDTO));
        jsonObject.put("zdzf",gxWwSqxmDo.getZdzf());
        return jsonObject;
    }

    /**
     * ?????? ETL-APP ??? ip:port
     */
    private String getEtlAppUrl(){
        List<ServiceInstance> instances = discoveryClient.getInstances(etlAppName);
        if(CollectionUtils.isNotEmpty(instances)){
            ServiceInstance serviceInstance = instances.get(0);
            URI uri = serviceInstance.getUri();
            return uri.toString();
        }
        return "";
    }

    @Override
    public String getHlwSqxxPrintXml(WwsqExportDTO wwsqExportDTO){
        Map<String,Object> configParam =Maps.newHashMap();
        configParam.put("wwxmid", wwsqExportDTO.getWwxmid());
        return etlPrintService.generateXmlData(configParam, wwsqExportDTO.getDylx(),"hlwConfigMapper");
    }

    @Override
    public WwsqDTO queryWwsqDTO(WwsqHtmlQO wwsqHtmlQO){
        if(StringUtils.isBlank(wwsqHtmlQO.getWwxmid())){
            throw new MissingArgumentException("????????????????????????????????????ID");
        }
        WwsqDTO wwsqDTO =new WwsqDTO();
        GxWwSqxmDo gxWwSqxmDo =hlwYwxxDataService.gxWwsqxmByHlwXmid(wwsqHtmlQO.getWwxmid());
        if(gxWwSqxmDo != null){
            wwsqDTO.setGxWwSqxmDo(gxWwSqxmDo);
            List<GxWwSqxxDo> gxWwSqxxDos =hlwYwxxDataService.listGxWwSqxxByXmid(wwsqHtmlQO.getWwxmid());
            if(CollectionUtils.isNotEmpty(gxWwSqxxDos)){
                List<GxWwsqxxDTO> gxWwsqxxDTOS =new ArrayList<>();
                for(GxWwSqxxDo gxWwSqxxDo:gxWwSqxxDos){
                    GxWwsqxxDTO gxWwsqxxDTO =new GxWwsqxxDTO();
                    gxWwsqxxDTO.setGxWwSqxxDo(gxWwSqxxDo);
                    GxWwsqBdcZdzDTO gxWwsqBdcZdzDTO =new GxWwsqBdcZdzDTO();
                    dozerMapper.map(gxWwSqxmDo, gxWwsqBdcZdzDTO);
                    dozerMapper.map(gxWwSqxxDo, gxWwsqBdcZdzDTO);
                    gxWwsqxxDTO.setGxWwsqBdcZdzDTO(gxWwsqBdcZdzDTO);
                    List<GxWwSqxxQlrDo> gxWwSqxxQlrDos =hlwYwxxDataService.listGxWwSqxxQlrBySqxxid(gxWwSqxxDo.getSqxxid());
                    if(CollectionUtils.isNotEmpty(gxWwSqxxQlrDos)){
                        gxWwsqxxDTO.setGxWwSqxxQlrDoList(gxWwSqxxQlrDos);
                    }


                    gxWwsqxxDTOS.add(gxWwsqxxDTO);
                }
                wwsqDTO.setGxWwsqxxDTOList(gxWwsqxxDTOS);
                List<GxWwShxxDO> gxWwShxxDOList =hlwYwxxDataService.listGxWwShxxByWwslbh(gxWwSqxmDo.getSqslbh());
                if(CollectionUtils.isNotEmpty(gxWwShxxDOList)){
                    wwsqDTO.setThyj(gxWwShxxDOList.get(0).getShyj());
                }
            }
            List<GxWwSqxxClxxDo> gxWwSqxxClxxDos =hlwYwxxDataService.listGxWwSqxxClxxByXmid(gxWwSqxmDo.getXmid());
            if(CollectionUtils.isNotEmpty(gxWwSqxxClxxDos)){
                wwsqDTO.setGxWwSqxxClxxDoList(gxWwSqxxClxxDos);
            }
        }
        return wwsqDTO;



    }

    @Override
    public String generateWwsqFjxx(String hlwxmid, String wwslbh,String parentPath) throws Exception{
        String zipPath =null;
        if(StringUtils.isNotBlank(hlwxmid)){
            List<GxWwSqxxClxxDo> gxWwSqxxClxxDoList =hlwYwxxDataService.listGxWwSqxxClxxByXmid(hlwxmid);
            if(CollectionUtils.isEmpty(gxWwSqxxClxxDoList)){
                throw new AppException(wwslbh +"??????????????????????????????");
            }
            // ??????????????????
            File parentfile = new File(parentPath);
            if (!parentfile.exists()) {
                parentfile.mkdirs();
            }
            FTPClient ftpClient =null;
            if(StringUtils.equals("ftp",wwsqfjxxbcfs)){
                //??????ftp????????????
                ftpClient = getConnectFtpServer();
            }
            for(GxWwSqxxClxxDo gxWwSqxxClxxDo:gxWwSqxxClxxDoList){
                if(CollectionUtils.isNotEmpty(gxWwSqxxClxxDo.getFjxx())){
                    if(StringUtils.isNotBlank(gxWwSqxxClxxDo.getClmc())) {
                        // ??????????????????
                        String path =parentPath + File.separator+gxWwSqxxClxxDo.getSqxxid() +File.separator+gxWwSqxxClxxDo.getClmc();
                        File file = new File(path);
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        for(GxWwSqxxFjxxDo gxWwSqxxFjxxDo:gxWwSqxxClxxDo.getFjxx()){
                            if(StringUtils.isNotBlank(gxWwSqxxFjxxDo.getFjmc()) &&StringUtils.isNotBlank(gxWwSqxxFjxxDo.getFjlj())) {
                                LOGGER.info("?????????????????? url:{},hlwxmid:{}", gxWwSqxxFjxxDo.getFjlj(), hlwxmid);
                                //??????????????????
                                downloadFile(gxWwSqxxFjxxDo.getFjmc(),gxWwSqxxFjxxDo.getFjlj(), path + File.separator + URLEncoder.encode(gxWwSqxxFjxxDo.getFjmc(),"utf-8"),ftpClient);
                            }else{
                                LOGGER.error("????????????????????????,???????????????{},???????????????{}",gxWwSqxxFjxxDo.getFjmc(),gxWwSqxxFjxxDo.getFjlj());
                            }
                        }
                    }
                }
            }
            if(ftpClient != null){
                try {
                    ftpClient.disconnect();
                } catch (IOException i) {
                    LOGGER.error("??????FTP????????????");
                }
            }

            //????????????
            zipPath = ZipUtils.zip(parentPath, printPath + "temp" + File.separator + wwslbh + ".zip");
        }
        return zipPath;
    }

    @Override
    public void hxDjxx(JSONObject jsonObject){
        if(Constants.SJHXLX_CJHX.equals(jsonObject.getInteger("sjhxlx"))) {
            //??????????????????????????????
            if (StringUtils.isNotBlank(jsonObject.getString("gzlslid"))) {
                jsonObject.put("bdcdjslbh", bdcXmFeignService.querySlbh(jsonObject.getString("gzlslid")));
            }
            hlwYwxxDataService.updateCjjl(jsonObject.getString("xmid"),jsonObject.getString("wwslbh"),jsonObject.getString("gzlslid"),jsonObject.getString("byslyy"));

        }
        if(StringUtils.isNotBlank(jsonObject.getString("byslyy"))){
            //???????????????
            noticeHlwSlzt(jsonObject.getString("xmid"),jsonObject.getString("byslyy"),jsonObject.getString("czrmc"));
        }
        //??????????????????
        entityService.updateByJsonEntity(JSONObject.toJSONString(jsonObject),GxWwSqxmDo.class);

    }

    @Override
    public void modifyHlwShzt(String processInsId) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            // ??????????????????????????????????????????
            if(CheckWwsqOrYcslUtils.checkIsWwsq(bdcXmDO.getSply())){
                //??????????????????
                Example example = new Example(GxWwSqxmDo.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("bdcdjslbh", bdcXmDO.getSlbh());
                GxWwSqxmDo gxWwSqxmDo = new GxWwSqxmDo();
                gxWwSqxmDo.setShzt(String.valueOf(CommonConstantUtils.SF_S_DM));
                entityMapper.updateByExampleSelectiveNotNull(gxWwSqxmDo, example);
            }
        }
    }

    @Override
    public InputStream getHlwFjxxStream(GxWwSqxxFjxxDo gxWwSqxxFjxxDo) throws Exception {
        if(Objects.nonNull(gxWwSqxxFjxxDo)){
            FTPClient ftpClient =null;
            if(StringUtils.equals("ftp",wwsqfjxxbcfs)){
                //??????ftp????????????
                ftpClient = this.getConnectFtpServer();
            }

            if(StringUtils.equals("url",wwsqfjxxbcfs)) {
                return new URL(gxWwSqxxFjxxDo.getFjlj()).openStream();
            }else if(StringUtils.equals("ftp",wwsqfjxxbcfs)) {
                if (StringUtils.isBlank(ftpFilePath)) {
                    LOGGER.info("??????????????????????????????????????????????????????????????????");
                    throw new AppException("??????????????????????????????,???????????????????????????");
                }
                String fjlj = gxWwSqxxFjxxDo.getFjlj();
                LOGGER.info("???????????????{}", fjlj);
                LOGGER.info("???????????????{}=====????????????{}", ftpFilePath + fjlj.replace(gxWwSqxxFjxxDo.getFjmc(), ""), gxWwSqxxFjxxDo.getFjmc());
                return FtpUtil.downloadFtpFile(ftpClient, ftpFilePath + fjlj.replace(gxWwSqxxFjxxDo.getFjmc(), ""), gxWwSqxxFjxxDo.getFjmc());
            }else{
                throw new AppException("??????????????????????????????,??????????????????????????????????????????");
            }
        }
        return null;
    }

    /**
     * @param hlwxmid
     * @param wwslbh
     * @param slr
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????????????????ID??????????????????
     */
    @Override
    public Map<String, Object> cjBdcXm(String hlwxmid, String wwslbh, String slr) {
        return cjBdcXm(hlwxmid,wwslbh,slr,false);
    }

    @Override
    public Map<String, Object> cjBdcXm(String hlwxmid,String wwslbh,String slr,Boolean cxcj){
        if (StringUtils.isBlank(hlwxmid)) {
            throw new MissingArgumentException("??????????????? id ???????????????");
        }
        if(!cxcj) {
            try {
                //?????????????????????
                hlwYwxxDataService.insertCjjl(hlwxmid);
            } catch (Exception e) {
                LOGGER.error("hlwxmid:{}????????????????????????", hlwxmid, e);
                return new HashMap<>();
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hlwxmid", hlwxmid);
        jsonObject.put("slr", slr);
        LOGGER.info("????????????????????????????????? ???{}", jsonObject);
        //????????????
        String errormsg ="";
        Object response =null;
        try {
            response = exchangeInterfaceFeignService.requestInterface("initWwsqXxByEtl", jsonObject);
        }catch(Exception e){
            errormsg =ExceptionUtils.getFeignErrorMsg(e);
        }
        LOGGER.info("??????????????????????????? ???{}", response);
        Map<String, Object> result =(Map<String, Object>) response;
        if(result ==null){
            result =new HashMap<>();

        }
        String msg = (String) result.get("msg");
        if (StringUtils.isNotBlank(msg) && StringUtils.isBlank((String) result.get("gzlslid"))) {
            errormsg = msg;
        }
        result.put("msg",errormsg);
        //??????????????????
        JSONObject hxObject =new JSONObject();
        hxObject.put("xmid",hlwxmid);
        hxObject.put("wwslbh",wwslbh);
        hxObject.put("byslyy",errormsg);
        hxObject.put("gzlslid",result.get("gzlslid"));
        hxObject.put("sjhxlx",Constants.SJHXLX_CJHX);
        if(StringUtils.isNotBlank(errormsg)) {
            hxObject.put("shzt", Constants.SHZT_ABANDON);
        }
        hxDjxx(hxObject);
        return result;

    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  ?????????????????????
      */
    @Override
    public void noticeHlwSlzt(String wwxmid,String sbyy,String czrmc){
        GxWwSqxmDo gxWwSqxmDo = hlwYwxxDataService.gxWwsqxmByHlwXmid(wwxmid);
        if (gxWwSqxmDo != null) {
            LOGGER.info("?????????????????????????????????hlwslh:{}", gxWwSqxmDo.getSqslbh());
            UpdateSlztRequestBody updateSlztRequestBody = new UpdateSlztRequestBody();
            UpdateSlztRequestData updateSlztRequestData = new UpdateSlztRequestData();
            updateSlztRequestData.setSlbh(gxWwSqxmDo.getSqslbh());
            updateSlztRequestData.setSpyj(sbyy);
            updateSlztRequestData.setSlzt(HlwSlztEnum.ABANDON.getSlzt());
            updateSlztRequestData.setSlztmc(HlwSlztEnum.getDescriptionBySlzt(HlwSlztEnum.ABANDON.getSlzt()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            updateSlztRequestData.setCzsj(sdf.format(new Date()));
            updateSlztRequestData.setCzrmc(czrmc);
            updateSlztRequestBody.setData(updateSlztRequestData);
            LOGGER.info("?????????????????????????????????hlwslh:{},????????????:{}", gxWwSqxmDo.getSqslbh(), updateSlztRequestData);
            if (StringUtils.equals(SQLY_YH, gxWwSqxmDo.getSqly())) {
                exchangeInterfaceFeignService.requestInterface("wwsqupdateslzt_yhxt", updateSlztRequestBody);
            } else {
                exchangeInterfaceFeignService.requestInterface("wwsqupdateslzt_hlw", updateSlztRequestBody);
            }
        }
    }

    @Override
    public Map<String, String> checkHlwBjzt(String bdcdyh){
        Map<String, String> result = new HashMap<>();
        result.put("code","0");
        if(StringUtils.isBlank(bdcdyh)){
            throw new AppException("???????????????????????????bdcdyh");
        }
        List<GxWwBlztDTO> gxWwBlztDTOList =hlwYwxxDataService.listGxWwBlztDTOByBdcdyh(bdcdyh);
        if(CollectionUtils.isEmpty(gxWwBlztDTOList)){
            result.put("code","1");
            result.put("msg","????????????????????????,???????????????");
        }else{
            //????????????????????????????????????
            List<GxWwBlztDTO> wcjList = gxWwBlztDTOList.stream().filter(gxWwBlztDTO -> !StringUtils.equals(Constants.SHZT_ABANDON.toString(),gxWwBlztDTO.getShzt()) &&CommonConstantUtils.SF_F_DM.equals(gxWwBlztDTO.getSfcj())).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(wcjList)){
                result.put("blzt","WCJ");
                result.put("msg","????????????????????????");
                return result;
            }
            List<GxWwBlztDTO> thList = gxWwBlztDTOList.stream().filter(gxWwBlztDTO -> StringUtils.equals(Constants.SHZT_ABANDON.toString(),gxWwBlztDTO.getShzt())).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(thList)){
                result.put("blzt","TH");
                result.put("msg","??????????????????????????????????????????");
                return result;
            }
            result.put("code","1");
            result.put("msg","????????????");
            return result;
        }
        return result;


    }

    /**
     * ??????????????????????????????
     * @param fjlj ????????????
     */
    private void downloadFile(String fjmc,String fjlj, String filePath,FTPClient ftpClient) throws AppException {
        if(StringUtils.equals("url",wwsqfjxxbcfs)) {
            downloadFileFromUrl(fjlj, filePath);
        }else if(StringUtils.equals("ftp",wwsqfjxxbcfs)){
            //ftp
            downloadFileFromFtp(fjmc,fjlj, filePath, ftpClient);
        }else{
            throw new AppException("??????????????????????????????,??????????????????????????????????????????");

        }
    }

    /**
      * @param filePath ??????????????????
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description ????????????URL????????????
      */
    private void downloadFileFromUrl(String fjlj, String filePath) throws AppException{
        try (InputStream inputStream = new URL(fjlj).openStream();
             OutputStream outputStream = new FileOutputStream(new File(filePath))) {
            IOUtils.copy(inputStream, outputStream);
            IOUtils.closeQuietly(outputStream);
        } catch (Exception e) {
            LOGGER.error("[{}]??????,???????????????{}",FUNCTION, fjlj, e);
            throw new AppException("??????????????????????????????");
        }

    }

    /**
     * @param filePath ??????????????????
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????ftp??????????????????
     */
    private void downloadFileFromFtp(String fjmc,String fjlj, String filePath,FTPClient ftpClient) throws AppException{
        if (StringUtils.isBlank(ftpFilePath)) {
            LOGGER.info("??????????????????????????????????????????????????????????????????");
            throw new AppException("??????????????????????????????,???????????????????????????");
        }

        InputStream inputStream = FtpUtil.downloadFtpFile(ftpClient, ftpFilePath+fjlj.replace(fjmc,""), fjmc);
        if(null == inputStream) {
            LOGGER.error("??????????????????FTP????????????");
            throw new AppException("??????????????????FTP????????????");
        }

        try {
            IOUtils.copy(inputStream, new FileOutputStream(new File(filePath)));
            LOGGER.info("{}??????FTP????????????????????????{}", FUNCTION, filePath);
        } catch (Exception exception) {
            LOGGER.error("??????????????????FTP????????????:", exception);
            throw new AppException("??????????????????????????????");
        }finally {
            try{
                inputStream.close();
            }catch(IOException e){
                LOGGER.error("?????????????????????");
            }
        }

    }

    /**
     * ??????FTP???????????????
     * @return FTPClient
     */
    private FTPClient getConnectFtpServer() throws IOException {
        if (StringUtils.isAnyBlank(ip, username, password, port)) {
            LOGGER.error("FTP???????????????????????????????????????????????????????????????");
            throw new AppException();
        }

        FTPClient ftpClient = FtpUtil.getFTPClient(ip, username, password, Integer.parseInt(port));
        FtpUtil.getLocalCharSet(ftpClient);

        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            LOGGER.info("{}?????????????????????FTP??????", FUNCTION);
            throw new AppException();
        }

        LOGGER.info("{}??????FTP???????????????",FUNCTION);
        return ftpClient;
    }

    /**
      * @param zsxmid ????????????ID
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description
      */
    private void getSfpp(String zsxmid,Map data){
        if(StringUtils.isBlank(zsxmid)){
            //???????????????
            data.put("DJBDCDYH", MapUtils.getString(data,"BDCDYH"));
        }else {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(zsxmid);
            List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                data.put("DJBDCDYH", bdcXmDO.getBdcdyh());
                //??????????????????????????????
                if (CommonConstantUtils.XMLY_CLGD_DM.equals(bdcXmDO.getXmly())) {
                    if (!Boolean.TRUE.equals(BdcdyhToolUtils.checkXnbdcdyh(bdcXmDO.getBdcdyh()))) {
                        List<BdcFctdPpgxDO> bdcFctdPpgxDOList = null;
                        if (CommonConstantUtils.DYBDCLX_FDYT.equals(bdcXmDO.getBdclx())) {
                            //??????
                            bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgx(zsxmid);
                        } else if (CommonConstantUtils.DYBDCLX_CTD.equals(bdcXmDO.getBdclx())) {
                            //??????
                            bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgxByTdxmid(zsxmid);
                        }
                        if (CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)) {
                            data.put("SFPP", CommonConstantUtils.SF_S_DM);
                        } else if (StringUtils.isNotBlank(bdcXmDO.getBdcdyh())) {
                            //??????????????????????????????????????????,????????????
                            bdcXmQO = new BdcXmQO();
                            bdcXmQO.setBdcdyh(bdcXmDOList.get(0).getBdcdyh());
                            bdcXmQO.setBdclx(CommonConstantUtils.DYBDCLX_FDYT.equals(bdcXmDO.getBdclx()) ? CommonConstantUtils.DYBDCLX_CTD : CommonConstantUtils.DYBDCLX_FDYT);
                            bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
                            bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                                data.put("SFPP", CommonConstantUtils.SF_S_DM);
                            }
                        }
                    } else {
                        data.put("SFPP", CommonConstantUtils.SF_F_DM);
                    }
                }
            }
        }

    }

    /**
     * @param dyhSxhMap ??????????????????
     * @return ?????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ???????????????,???????????????????????????????????????,???????????????????????????
     */
    private Integer deaSxh(Map<String, Integer> dyhSxhMap, String bdcdyh) {
        if (dyhSxhMap.containsKey(bdcdyh)) {
            dyhSxhMap.put(bdcdyh, dyhSxhMap.get(bdcdyh) + 1);
        } else {
            dyhSxhMap.put(bdcdyh, 1);
        }
        return dyhSxhMap.get(bdcdyh);
    }




}
