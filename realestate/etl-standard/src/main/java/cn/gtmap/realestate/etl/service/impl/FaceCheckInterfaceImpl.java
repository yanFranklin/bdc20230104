package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.etl.BdcCzRlsbDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcQlrXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcYwsjHxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcRyzdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.etl.core.convert.CzRlsbConstansEnum;
import cn.gtmap.realestate.etl.core.convert.FaceCheckConverter;
import cn.gtmap.realestate.etl.core.domian.BdcDsfRlsbDO;
import cn.gtmap.realestate.etl.core.dto.CheckFacePictureDTO;
import cn.gtmap.realestate.etl.core.vo.BdcDsfRlsbVO;
import cn.gtmap.realestate.etl.service.FaceCheckInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.WebService;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zedeqaing@gtmap.cn">zedq</a>
 * @version 2020/12/17,1.0
 * @description ????????????webservice
 */
@Service
@WebService(name = "FaceCheckInterface",
        targetNamespace = "http://service.etl.realestate.gtmap.cn",
        endpointInterface = "cn.gtmap.realestate.etl.service.FaceCheckInterface")
public class FaceCheckInterfaceImpl implements FaceCheckInterface {

    private final Logger log = LoggerFactory.getLogger(FaceCheckInterfaceImpl.class);

    @Autowired
    @Qualifier("bdcEntityMapper")
    @Lazy
    private EntityMapper bdcEntityMapper;

    @Autowired
    @Lazy
    private StorageClientMatcher storageClient;

    @Autowired
    @Lazy
    private FaceCheckConverter faceCheckConverter;

    @Autowired
    @Lazy
    private BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    @Lazy
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    @Lazy
    private BdcXmfbFeignService bdcXmfbFeignService;

    @Autowired
    @Lazy
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    @Lazy
    private BdcQlrFeignService qlrFeignService;

    @Autowired
    @Lazy
    private BdcRyzdFeignService bdcRyzdFeignService;
    @Autowired
    @Lazy
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;

    @Autowired
    @Lazy
    BdcSlQlrFeignService bdcSlQlrFeignService;

    @Autowired
    @Lazy
    FaceDeleteServiceImpl faceDeleteService;

    @Autowired
    @Lazy
    BdcQlrFeignService bdcQlrFeignService;
    /**
     * ??????????????????????????????????????????????????????ID,???????????????,?????????
     */
    @Value("${tbqlrtofdcq.gzldyid:}")
    private String tdsyqrGzldyids;

    @Value("${spfclfdjxl:2000401,2000407,2000801???2000402???2000802???2000601}")
    private String spfclfdjxl;

    @Value("${sfxzslcl:false}")
    private boolean sfxzslcl;

    /**
     * ???????????????????????????????????????????????????????????????????????????????????????????????????/"?????????
     *
     * @param json         ????????????????????????JSON
     * @param processInsId ???????????????ID
     */
    private void notifySyncTdsyqr(String json, String processInsId) {
        if (StringUtils.isBlank(tdsyqrGzldyids)) {
            return;
        }
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if (CollectionUtils.isEmpty(bdcXmDTOList)) {
            return;
        }
        String gzldyid = bdcXmDTOList.get(0).getGzldyid();
        if (StringUtils.isNotBlank(gzldyid) && tdsyqrGzldyids.indexOf(gzldyid) > -1) {
            this.bdcSlQlrFeignService.syncTdsyqr(json, processInsId);
        }
    }

    /**
     * ????????????
     * <p>
     * ???????????????
     * ???????????????????????????id?????????????????????????????????
     * ????????????????????????????????????????????????
     *
     * @param param
     * @return
     */
    @Override
//    @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
    public String checkResultInfo(String param) throws Exception {
        log.info("??????????????????:{}", param);
        JSONObject responesJsonObject = new JSONObject();
        List<BdcDsfRlsbVO> rlsbVOList = new ArrayList<>(4);
        if (StringUtils.isNotBlank(param) && !isJson(param)) {
            //????????????id????????????????????????
            //??????????????????
            Example queryXmInfoExample = new Example(BdcXmDO.class);
            queryXmInfoExample.createCriteria().andEqualTo("xmid", param);
            List<BdcXmDO> xmInfo = bdcEntityMapper.selectByExample(queryXmInfoExample);

            //??????????????????????????????
            Example queryBdcDsfRlsbParam = new Example(BdcDsfRlsbDO.class);
            queryBdcDsfRlsbParam.createCriteria().andEqualTo("ywnum", param);
            List<BdcDsfRlsbDO> personInfo = bdcEntityMapper.selectByExample(queryBdcDsfRlsbParam);
            if (CollectionUtils.isNotEmpty(xmInfo)) {
                if (CollectionUtils.isNotEmpty(personInfo)) {
                    //??????????????????
                    personInfo.forEach(bdcDsfRlsbDO -> {
                        if (StringUtils.isNotBlank(bdcDsfRlsbDO.getCardimage())) {
                            BaseResultDto baseResultDto = storageClient.downloadBase64(bdcDsfRlsbDO.getCardimage());
                            if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
                                if (StringUtils.isNotBlank(baseResultDto.getMsg())) {
                                    String[] baseArry = baseResultDto.getMsg().split(",");
                                    bdcDsfRlsbDO.setCardimage(baseArry[1]);
                                }
                            } else {
                                bdcDsfRlsbDO.setCardimage("");
                            }
                        }
                        if (StringUtils.isNotBlank(bdcDsfRlsbDO.getPersonimage())) {
                            BaseResultDto baseResultDto = storageClient.downloadBase64(bdcDsfRlsbDO.getPersonimage());
                            if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
                                if (StringUtils.isNotBlank(baseResultDto.getMsg())) {
                                    String[] baseArry = baseResultDto.getMsg().split(",");
                                    bdcDsfRlsbDO.setPersonimage(baseArry[1]);
                                }
                            } else {
                                bdcDsfRlsbDO.setPersonimage("");
                            }
                        }
                        if (StringUtils.isNotBlank(bdcDsfRlsbDO.getFacefeature())) {
                            BaseResultDto baseResultDto = storageClient.downloadBase64(bdcDsfRlsbDO.getFacefeature());
                            if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
                                if (StringUtils.isNotBlank(baseResultDto.getMsg())) {
                                    String[] baseArry = baseResultDto.getMsg().split(",");
                                    bdcDsfRlsbDO.setFacefeature(baseArry[1]);
                                }
                            } else {
                                bdcDsfRlsbDO.setFacefeature("");
                            }
                        }
                    });
                    List<BdcDsfRlsbVO> list = faceCheckConverter.getBdcDsfRlsbVOListByBdcDsfRlsbDOList(personInfo);
                    responesJsonObject.put("personEntitys", list);
                    log.info("?????????????????????????????????{}", JSON.toJSONString(responesJsonObject));

                    return JSONObject.toJSONString(responesJsonObject, SerializerFeature.WriteNullStringAsEmpty);
                }
                log.info("????????????????????????{}", JSON.toJSONString(rlsbVOList));

                responesJsonObject.put("personEntitys", rlsbVOList);
                return JSONObject.toJSONString(rlsbVOList, SerializerFeature.WriteNullStringAsEmpty);
            } else {
                return "??????????????????????????????";
            }
        } else if (StringUtils.isNotBlank(param) && isJson(param)) {
            log.info("json??????????????????{}", param);
            JSONObject jsonObject = JSON.parseObject(param);
            JSONArray checkFacdResultJsonArray = jsonObject.getJSONArray("personEntitys");
            if (checkFacdResultJsonArray != null && checkFacdResultJsonArray.size() > 0) {
                String xmid = checkFacdResultJsonArray.getJSONObject(0).getString("ywnum");
                if (StringUtils.isNotBlank(xmid)) {
                    //?????????????????????qlr?????????????????????
                    List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
                    List<BdcDsfRlsbDO> bdcYwrDOList = new ArrayList<>();
                    List<BdcDsfRlsbDO> bdcDyrDOList = new ArrayList<>();
                    List<BdcDsfRlsbDO> bdcQlrDsfRlsbList = new ArrayList<>();
                    BdcQlrDO qlrDO = new BdcQlrDO();
                    int sxh = 2;
                    int buyzqrFlag = 0;
                    int dyazqrFlag = 0;
                    int sellzqrFlag = 0;
                    //????????????????????????????????????????????????????????????????????????
                    for (int i = 0; i < checkFacdResultJsonArray.size(); i++) {
                        JSONObject checkFacdResultJsonObject = checkFacdResultJsonArray.getJSONObject(i);
                        if ("1".equals(checkFacdResultJsonObject.getString("role"))) {
                            buyzqrFlag++;
                            log.info("buyzqrFlag?????????{}", buyzqrFlag);

                        } else if ("3".equals(checkFacdResultJsonObject.getString("role"))) {
                            sellzqrFlag++;
                            log.info("sellzqrFlag?????????{}", sellzqrFlag);

                        } else if ("5".equals(checkFacdResultJsonObject.getString("role"))) {
                            dyazqrFlag++;
                            log.info("dyazqrFlag?????????{}", dyazqrFlag);
                        }
                    }

                    //?????????????????????????????????????????????????????????????????????
                    log.info("???????????????????????????");
                    Example queryXmInfoExample = new Example(BdcXmDO.class);
                    queryXmInfoExample.createCriteria().andEqualTo("xmid", xmid);
                    List<BdcXmDO> xmDOList = bdcEntityMapper.selectByExample(queryXmInfoExample);
                    Example queryAllXmInfo = new Example(BdcXmDO.class);
                    queryAllXmInfo.createCriteria().andEqualTo("gzlslid", xmDOList.get(0).getGzlslid());
                    List<BdcXmDO> allXmList = bdcEntityMapper.selectByExample(queryAllXmInfo);
                    if (CollectionUtils.isNotEmpty(xmDOList) && StringUtils.isNotBlank(xmDOList.get(0).getGzlslid())) {
                        String gzlslid = xmDOList.get(0).getGzlslid();

                        // //??????gzldyid??????????????????????????????
                        BdcCzRlsbDTO czRlsbDTO = getBdcCzRlsbDTO(xmDOList.get(0));
                        if (null != czRlsbDTO) {
                            if ("1".equals(czRlsbDTO.getYwlx())) {
                                if (buyzqrFlag > 1) {
                                    return "??????????????????????????????!";
                                }
                                if (sellzqrFlag > 1) {
                                    return "??????????????????????????????!";
                                }
                            }
                            if ("2".equals(czRlsbDTO.getYwlx())) {
                                if (buyzqrFlag != 1) {
                                    return "??????????????????????????????!";
                                }
                            }
                            if ("3".equals(czRlsbDTO.getYwlx())) {
                                if (dyazqrFlag > 1) {
                                    return "?????????????????????????????????";
                                }
                            }
                        } else {
                            return "??????????????????????????????";
                        }

                        //???????????????????????????
                        //??????????????????????????????
                        Example queryBdcDsfRlsbParam = new Example(BdcDsfRlsbDO.class);
                        queryBdcDsfRlsbParam.createCriteria().andEqualTo("ywnum", xmid);
                        List<BdcDsfRlsbDO> personInfo = bdcEntityMapper.selectByExample(queryBdcDsfRlsbParam);
                        StorageDto storageDto;
                        Map<String, String> fileMap = null;
                        if (CollectionUtils.isNotEmpty(personInfo)) {
                            storageDto = storageClient.findById(personInfo.get(0).getParentnodeid());
                            fileMap = storageDto.getChildren().stream().collect(Collectors.toMap(StorageDto::getName, StorageDto::getId));
                        } else {
                            int lclx = bdcXmFeignService.makeSureBdcXmLx(allXmList);
                            if (lclx == 1 || lclx == 3) {
                                storageDto = storageClient.createRootFolder("clientId", xmDOList.get(0).getGzlslid(), "????????????", null);
                            } else {
                                //????????????????????????????????????????????????
                                List<Map> zdList = bdcZdFeignService.queryBdcZd("djxl");
                                List<StorageDto> storageDtos = storageClient.queryMenus("clientId", xmDOList.get(0).getGzlslid(), "", null, null, null, null, null);
                                String parentFolder = StringToolUtils.convertBeanPropertyValueOfZdByString(xmDOList.get(0).getDjxl(), zdList);
                                Optional<StorageDto> storangOptional = storageDtos.stream().filter(storageDto1 -> storageDto1.getName().equals(parentFolder)).findAny();
                                if (storangOptional.isPresent()) {
                                    storageDto = storageClient.createFolder("clientId", xmDOList.get(0).getGzlslid(), storangOptional.get().getId(), "????????????", null);
                                } else {
                                    StorageDto xmFolder = storageClient.createRootFolder("clientId", xmDOList.get(0).getGzlslid(), parentFolder, null);
                                    storageDto = storageClient.createFolder("clientId", xmDOList.get(0).getGzlslid(), xmFolder.getId(), "????????????", null);
                                }
                            }
                        }
                        if (sfxzslcl) {
                            //??????????????????????????????????????????????????????
                            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(xmDOList.get(0).getGzlslid(), "????????????");
                            if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
                                BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
                                bdcSlSjclDO.setClmc("????????????");
                                bdcSlSjclDO.setGzlslid(xmDOList.get(0).getGzlslid());
                                bdcSlSjclDO.setWjzxid(storageDto.getId());
                                bdcSlSjclDO.setDjxl(xmDOList.get(0).getDjxl());
                                bdcSlSjclDO.setFs(1);
                                bdcSlSjclDO.setYs(1);
                                bdcSlSjclDO.setSjlx(1);
                                bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
                            }
                        }


                        for (int i = 0; i < checkFacdResultJsonArray.size(); i++) {
                            BdcDsfRlsbVO dsfRlsbVO = checkFacdResultJsonArray.getObject(i, BdcDsfRlsbVO.class);
                            JSONObject checkFacdResultJsonObject = checkFacdResultJsonArray.getJSONObject(i);

                            String cardImage = checkFacdResultJsonObject.getString("cardImage");
                            String personImage = checkFacdResultJsonObject.getString("personImage");
                            String faceFeature = checkFacdResultJsonObject.getString("faceFeature");
                            if (StringUtils.isNotBlank(cardImage)) {
                                dsfRlsbVO.setCardImage(uploadFileToFilecenter(storageDto, checkFacdResultJsonObject.getString("idCode") + "_1", xmDOList.get(0).getGzlslid(), toMultipartFile(cardImage), CollectionUtils.isNotEmpty(personInfo), fileMap));
                            }
                            if (StringUtils.isNotBlank(personImage)) {
                                dsfRlsbVO.setPersonImage(uploadFileToFilecenter(storageDto, checkFacdResultJsonObject.getString("idCode") + "_2", xmDOList.get(0).getGzlslid(), toMultipartFile(personImage), CollectionUtils.isNotEmpty(personInfo), fileMap));
                            }
                            if (StringUtils.isNotBlank(faceFeature)) {
                                dsfRlsbVO.setFaceFeature(uploadFileToFilecenter(storageDto, checkFacdResultJsonObject.getString("idCode") + "_3", xmDOList.get(0).getGzlslid(), toMultipartFile(faceFeature), CollectionUtils.isNotEmpty(personInfo), fileMap));
                            }

                            if ("1".equals(checkFacdResultJsonObject.getString("role"))) {
                                qlrDO = initBdcQlrDO(xmid, checkFacdResultJsonObject, 1);
                                bdcQlrDOList.add(initBdcQlrDO(xmid, checkFacdResultJsonObject, 1));
                                bdcQlrDsfRlsbList.add(faceCheckConverter.getBdcDsfRlsbDOByBdcDsfRlsbVO(dsfRlsbVO));
                            } else if ("0".equals(checkFacdResultJsonObject.getString("role"))) {
                                bdcQlrDOList.add(initBdcQlrDO(xmid, checkFacdResultJsonObject, sxh++));
                                bdcQlrDsfRlsbList.add(faceCheckConverter.getBdcDsfRlsbDOByBdcDsfRlsbVO(dsfRlsbVO));

                            } else if ("2".equals(checkFacdResultJsonObject.getString("role"))) {
                                log.info("?????????????????????");
                                BdcDsfRlsbDO bdcDsfRlsbDO = faceCheckConverter.getBdcDsfRlsbDOByBdcDsfRlsbVO(dsfRlsbVO);
                                bdcYwrDOList.add(bdcDsfRlsbDO);

                            } else if ("3".equals(checkFacdResultJsonObject.getString("role"))) {
                                log.info("????????????????????????");
                                BdcDsfRlsbDO bdcDsfRlsbDO = faceCheckConverter.getBdcDsfRlsbDOByBdcDsfRlsbVO(dsfRlsbVO);
                                bdcYwrDOList.add(bdcDsfRlsbDO);
                            } else if ("5".equals(checkFacdResultJsonObject.getString("role"))) {
                                log.info("?????????????????????");
                                BdcDsfRlsbDO bdcDsfRlsbDO = faceCheckConverter.getBdcDsfRlsbDOByBdcDsfRlsbVO(dsfRlsbVO);
                                bdcDyrDOList.add(bdcDsfRlsbDO);

                            } else if ("4".equals(checkFacdResultJsonObject.getString("role"))) {
                                log.info("?????????????????????role=4???");
                                BdcDsfRlsbDO bdcDsfRlsbDO = faceCheckConverter.getBdcDsfRlsbDOByBdcDsfRlsbVO(dsfRlsbVO);
                                bdcDyrDOList.add(bdcDsfRlsbDO);

                            }
                        }
                        //?????????????????????,?????????
                        try {
                            faceDeleteService.deleteRlsbQlr(xmid);
                        } catch (Exception e) {
                            log.info("???????????????????????????!,xmid:{}", xmid);
                            throw new AppException("??????????????????????????????");
                        }

                        //??????????????????????????????rlsb???,qlr????????????rlsb
                        if (CollectionUtils.isNotEmpty(bdcDyrDOList)) {
                            for (BdcDsfRlsbDO dsfDyrRlsbDO : bdcDyrDOList) {
                                dsfDyrRlsbDO.setId(UUIDGenerator.generate());
                                dsfDyrRlsbDO.setParentnodeid(storageDto.getId());
                                bdcEntityMapper.insert(dsfDyrRlsbDO);
                            }
                        }
                        if (CollectionUtils.isNotEmpty(bdcYwrDOList)) {
                            for (BdcDsfRlsbDO dsfYwrRlsbDO : bdcYwrDOList) {
                                dsfYwrRlsbDO.setId(UUIDGenerator.generate());
                                dsfYwrRlsbDO.setParentnodeid(storageDto.getId());
                                bdcEntityMapper.insert(dsfYwrRlsbDO);
                            }
                        }
                        if (CollectionUtils.isNotEmpty(bdcQlrDsfRlsbList)) {
                            for (BdcDsfRlsbDO dsfQlrRlsbDO : bdcQlrDsfRlsbList) {
                                dsfQlrRlsbDO.setId(UUIDGenerator.generate());
                                dsfQlrRlsbDO.setParentnodeid(storageDto.getId());
                                bdcEntityMapper.insert(dsfQlrRlsbDO);
                            }
                        }

                        /**
                         *  1??????????????????????????????????????????????????????????????????????????????????????????????????????
                         *  2.	?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                         */
                        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                            //????????????????????????????????????????????????
                            int lclx = bdcXmFeignService.makeSureBdcXmLx(allXmList);
                            //????????????
                            if (lclx == 1) {
                                if (CommonConstantUtils.QLLX_DYAQ_DM.equals(xmDOList.get(0).getQllx())) {
                                    log.info("???xmid?????????????????????????????????????????????????????????????????????xmid:{}", xmid);
                                  /*  //?????????????????????
                                    try {
                                        bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
                                    } catch (Exception e) {
                                        log.info(e.getMessage());
                                    }
                                    return "success";*/
                                } else if (CommonConstantUtils.DJLX_ZYDJ_DM.equals(xmDOList.get(0).getDjlx()) && spfclfdjxl.indexOf(xmDOList.get(0).getDjxl()) > -1) {
                                    /**
                                     * ?????????,???????????????????????????
                                     * ????????????????????????????????????????????????????????????????????????saveQlrlist???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????saveQlrList
                                     */

                                    if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                                        List<BdcQlrDO> saveQlrList = new ArrayList<>();
                                        for (BdcQlrDO qlrDO1 : bdcQlrDOList) {
                                            BdcQlrQO qlrQO = new BdcQlrQO();
                                            qlrQO.setXmid(xmid);
                                            qlrQO.setQlrlb("1");
                                            qlrQO.setZjh(qlrDO1.getZjh());
                                            List<BdcQlrDO> allQlrDoList = bdcQlrFeignService.listBdcQlr(qlrQO);
                                            if (CollectionUtils.isNotEmpty(allQlrDoList)) {
                                                log.info("??????????????????????????????????????????!,xmid:{}.zjh:{}", xmid, qlrDO1.getZjh());
                                                allQlrDoList.get(0).setQlrid(UUIDGenerator.generate16());
                                                saveQlrList.addAll(allQlrDoList);
                                            } else {
                                                saveQlrList.add(qlrDO1);
                                            }
                                        }
                                        //???????????????????????????????????????
                                        try {
                                            faceDeleteService.deleteBdcQlr(xmid);
                                        } catch (Exception e) {
                                            log.info("????????????????????????????????????!,xmid:{}", xmid);
                                            throw new AppException("???????????????????????????????????????");
                                        }
                                        //????????????????????????????????????
                                        dgxmRyqlrxx(saveQlrList, xmDOList);
                                        //?????????????????????
                                        try {
                                            bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
                                        } catch (Exception e) {
                                            log.info(e.getMessage());
                                        }
                                    }

                                } else {
                                    //??????????????????????????? ???????????????????????????????????????
                                    try {
                                        faceDeleteService.deleteBdcQlr(xmid);
                                    } catch (Exception e) {
                                        log.info("??????????????????????????????????????????!,xmid:{}", xmid);
                                        throw new AppException("?????????????????????????????????????????????");
                                    }
                                    //????????????????????????????????????
                                    dgxmRyqlrxx(bdcQlrDOList, xmDOList);
                                    //?????????????????????
                                    try {
                                        bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
                                    } catch (Exception e) {
                                        log.info(e.getMessage());
                                    }
                                }

                            }
                            //????????????
                            if (lclx == 2) {
                                log.info("????????????gzlslid:{}", gzlslid);
                                log.info("??????????????????????????????xmid??????????????????????????????????????????");
                                if (CommonConstantUtils.QLLX_DYAQ_DM.equals(xmDOList.get(0).getQllx())) {
                                    log.info("???xmid?????????????????????????????????????????????????????????????????????xmid:{}", xmid);

                                    return "success";
                                } else if (CommonConstantUtils.DJLX_ZYDJ_DM.equals(xmDOList.get(0).getDjlx()) && spfclfdjxl.indexOf(xmDOList.get(0).getDjxl()) > -1) {
                                    //???????????????????????????
                                    //????????????????????????????????????????????????????????????????????????saveQlrlist???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????saveQlrList
                                    if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                                        List<BdcQlrDO> saveQlrList = new ArrayList<>();
                                        for (BdcQlrDO qlrDO1 : bdcQlrDOList) {
                                            BdcQlrQO qlrQO = new BdcQlrQO();
                                            qlrQO.setXmid(xmid);
                                            qlrQO.setQlrlb("1");
                                            qlrQO.setZjh(qlrDO1.getZjh());
                                            List<BdcQlrDO> allQlrDoList = bdcQlrFeignService.listBdcQlr(qlrQO);
                                            if (CollectionUtils.isNotEmpty(allQlrDoList)) {
                                                allQlrDoList.get(0).setQlrid(UUIDGenerator.generate16());
                                                saveQlrList.addAll(allQlrDoList);
                                            } else {
                                                saveQlrList.add(qlrDO1);
                                            }
                                        }
                                        //???????????????????????????????????????
                                        try {
                                            log.info("?????????????????????????????????????????????!,xmid:{}", xmid);
                                            faceDeleteService.deleteBdcQlr(xmid);
                                        } catch (Exception e) {
                                            log.info("???????????????????????????!,xmid:{}", xmid);
                                            throw new AppException("??????????????????????????????");
                                        }
                                        //????????????????????????????????????
                                        dgxmRyqlrxx(saveQlrList, xmDOList);
                                        //?????????????????????
                                        try {
                                            bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
                                        } catch (Exception e) {
                                            log.info(e.getMessage());
                                        }

                                    }

                                } else {
                                    //??????????????????????????????????????????????????????????????????????????????
                                    //???????????????????????????????????????
                                    try {
                                        log.info("???????????????????????????????????????!,xmid:{}", xmid);
                                        faceDeleteService.deleteBdcQlr(xmid);
                                    } catch (Exception e) {
                                        log.info("?????????????????????????????????????????????!,xmid:{}", xmid);
                                        throw new AppException("???????????????????????????");
                                    }
                                    List<JSONObject> jsonObjectList = qlrFeignService.listZhBdcQlr(JSONObject.parseObject(JSONObject.toJSONString(qlrDO)), CommonConstantUtils.FUN_INSERT);
                                    if (CollectionUtils.isNotEmpty(jsonObjectList)) {
                                        for (JSONObject object : jsonObjectList) {
                                            BdcQlrDO bdcQlrDO = JSONObject.toJavaObject(object, BdcQlrDO.class);
                                            if (bdcQlrDO != null && bdcQlrDO.getQlrly() == null) {
                                                bdcQlrDO.setQlrly(CommonConstantUtils.QLRLY_SD);
                                            }
                                            BdcQlrDO bdcqlr = qlrFeignService.insertBdcQlr(bdcQlrDO);
                                        }
                                        if (StringUtils.isNotBlank(gzlslid)) {
                                            bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(gzlslid);
                                            bdcRyzdFeignService.updateGyqkWithGzlslid(gzlslid);
                                            //?????????????????????
                                            try {
                                                bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
                                            } catch (Exception e) {
                                                log.info(e.getMessage());
                                            }
                                            this.notifySyncTdsyqr(JSON.toJSONString(jsonObjectList), gzlslid);
                                        }
                                    }
                                }
                            }
                            //????????????
                            if (lclx == 3) {
                                log.info("????????????gzlslid:{}", gzlslid);
                                if (CommonConstantUtils.QLLX_DYAQ_DM.equals(xmDOList.get(0).getQllx())) {
                                    log.info("???xmid?????????????????????????????????????????????????????????????????????xmid:{}", xmid);
                                    return "success";
                                } else if (CommonConstantUtils.DJLX_ZYDJ_DM.equals(xmDOList.get(0).getDjlx()) && spfclfdjxl.indexOf(xmDOList.get(0).getDjxl()) > -1) {
                                    //???????????????????????????
                                    //????????????????????????????????????????????????????????????????????????saveQlrlist???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????saveQlrList
                                    if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                                        List<BdcQlrDO> saveQlrList = new ArrayList<>();
                                        for (BdcQlrDO qlrDO1 : bdcQlrDOList) {
                                            BdcQlrQO qlrQO = new BdcQlrQO();
                                            qlrQO.setXmid(xmid);
                                            qlrQO.setQlrlb("1");
                                            qlrQO.setZjh(qlrDO1.getZjh());
                                            List<BdcQlrDO> allQlrDoList = bdcQlrFeignService.listBdcQlr(qlrQO);
                                            if (CollectionUtils.isNotEmpty(allQlrDoList)) {
                                                allQlrDoList.get(0).setQlrid(UUIDGenerator.generate16());
                                                saveQlrList.addAll(allQlrDoList);
                                            } else {
                                                saveQlrList.add(qlrDO1);
                                            }
                                        }
                                        //???????????????????????????????????????
                                        try {
                                            log.info("?????????????????????????????????????????????!,xmid:{}", xmid);
                                            faceDeleteService.deleteBdcQlr(xmid);
                                        } catch (Exception e) {
                                            log.info("???????????????????????????!,xmid:{}", xmid);
                                            throw new AppException("??????????????????????????????");
                                        }
                                        //????????????????????????????????????
                                        dgxmRyqlrxx(saveQlrList, xmDOList);
                                        //?????????????????????
                                        try {
                                            bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
                                        } catch (Exception e) {
                                            log.info(e.getMessage());
                                        }
                                    }

                                } else {
                                    //????????????????????????
                                    if (null != qlrDO) {
                                        //????????????????????????????????????,??????????????????????????????
                                        qlrFeignService.deleteBdcQlrsByQlrxx(qlrDO.getQlrmc(), qlrDO.getZjh(), gzlslid, qlrDO.getQlrlb(), "", new ArrayList<>());
                                        if (qlrDO.getQlrly() == null) {
                                            qlrDO.setQlrly(CommonConstantUtils.QLRLY_SD);
                                        }
                                        List<BdcQlrDO> batchBdcQlr = qlrFeignService.insertBatchBdcQlr(qlrDO, gzlslid, "");
                                        if (CollectionUtils.isNotEmpty(batchBdcQlr)) {
                                            if (StringUtils.isNotBlank(gzlslid)) {
                                                //?????????????????????????????????????????????
                                                updateQlrxxPl(gzlslid, "", batchBdcQlr.get(0).getQlrlb());
                                                // ??????????????????????????????
                                                this.notifySyncTdsyqr(JSON.toJSONString(batchBdcQlr), gzlslid);
                                            }

                                        }
                                    }
                                }
                            }
                            //????????????
                            if (lclx == 4) {
                                log.info("????????????gzlslid:{}", gzlslid);

                                //??????????????????????????????
                                BdcQlrDO bdcQlrDO = bdcSlQlrFeignService.insertPlzhBdcQlr(JSONObject.toJSONString(qlrDO), gzlslid, xmDOList.get(0).getDjxl(), syncTdsyqr(gzlslid));
                                log.info("????????????bdcQlrDO:{}", bdcQlrDO.toString());


                            }
                            log.info("??????????????????????????????????????????");
                        }
                    }
                } else {
                    return "??????????????????????????????????????????";
                }
            } else {
                return "ywnum????????????";
            }
        } else {
            return "personEntitys????????????";
        }
        return "success";
    }

    /**
     * ???????????????????????????
     *
     * @param
     * @return
     * @Date 2022/2/17
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    private void saveRlsbTable(List<BdcDsfRlsbDO> bdcYwrDOList, List<BdcDsfRlsbDO> bdcDyrDOList, List<BdcDsfRlsbDO> bdcQlrDsfRlsbList, StorageDto storageDto) {
        //??????????????????????????????rlsb???,qlr????????????rlsb
        if (CollectionUtils.isNotEmpty(bdcDyrDOList)) {
            for (BdcDsfRlsbDO dsfDyrRlsbDO : bdcDyrDOList) {
                dsfDyrRlsbDO.setId(UUIDGenerator.generate());
                dsfDyrRlsbDO.setParentnodeid(storageDto.getId());
                bdcEntityMapper.insert(dsfDyrRlsbDO);
            }
        }
        if (CollectionUtils.isNotEmpty(bdcYwrDOList)) {
            for (BdcDsfRlsbDO dsfYwrRlsbDO : bdcYwrDOList) {
                dsfYwrRlsbDO.setId(UUIDGenerator.generate());
                dsfYwrRlsbDO.setParentnodeid(storageDto.getId());
                bdcEntityMapper.insert(dsfYwrRlsbDO);
            }
        }
        if (CollectionUtils.isNotEmpty(bdcQlrDsfRlsbList)) {
            for (BdcDsfRlsbDO dsfQlrRlsbDO : bdcQlrDsfRlsbList) {
                dsfQlrRlsbDO.setId(UUIDGenerator.generate());
                dsfQlrRlsbDO.setParentnodeid(storageDto.getId());
                bdcEntityMapper.insert(dsfQlrRlsbDO);
            }
        }
    }

    /**
     * ??????????????????????????????????????????xm???????????????
     *
     * @param xmDOList mid???????????????????????????
     * @return
     * @Date 2022/2/17
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    private void dgxmRyqlrxx(List<BdcQlrDO> bdcQlrDOList, List<BdcXmDO> xmDOList) {
        int i = bdcEntityMapper.insertBatchSelective(bdcQlrDOList);
        log.info("??????????????????:{}", i);
        if (CollectionUtils.isNotEmpty(xmDOList)) {
            Integer qllx = xmDOList.get(0).getQllx();
            log.info("????????????????????????????????????");
            log.info("????????????????????????????????????");
            StringBuilder qlrmcSub = new StringBuilder("");
            StringBuilder qlrzjhSub = new StringBuilder("");
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                for (BdcQlrDO qlrDO1 : bdcQlrDOList) {
                    qlrmcSub.append(qlrDO1.getQlrmc()).append(",");
                    qlrzjhSub.append(qlrDO1.getZjh()).append(",");
                }
            }
            String qlr = qlrmcSub.toString();
            String qlrzjh = qlrzjhSub.toString();
            if (qlr.endsWith(",")) {
                qlr = qlr.substring(0, qlr.length() - 1);
            }
            if (qlrzjh.endsWith(",")) {
                qlrzjh = qlrzjh.substring(0, qlrzjh.length() - 1);
            }
            BdcXmDO xmDO = xmDOList.get(0);
            Example updateByExampleSelective = new Example(BdcXmDO.class);
            xmDO.setQlrzjh(qlrzjh);
            xmDO.setQlr(qlr);
            updateByExampleSelective.createCriteria()
                    .andEqualTo("xmid", xmDO.getXmid());
            bdcEntityMapper.updateByExampleSelective(xmDO, updateByExampleSelective);

        }
    }

    /**
     * ??????????????????????????????????????????????????????id????????????
     *
     * @param param@return
     * @Date 2021/11/5
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public CommonResponse saveCheckFacePicture(String param) throws Exception {
        CheckFacePictureDTO checkFacePictureDTO = JSON.parseObject(param, CheckFacePictureDTO.class);
        BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
        bdcXmFbQO.setXmid(checkFacePictureDTO.getYwnum());
        List<BdcXmFbDO> fbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
        if (CollectionUtils.isNotEmpty(fbDOList)) {
            BdcXmFbDO fbDO = fbDOList.get(0);
            //?????????????????????????????????????????????id????????????
            StorageDto storageDto = storageClient.createRootFolder("clientId", fbDO.getGzlslid(), "????????????", null);
            if (Objects.nonNull(storageDto)) {
                fbDO.setXczpimg(uploadFileToFilecenterForRlsbHb(storageDto, "????????????????????????", fbDO.getGzlslid(), toMultipartFile(checkFacePictureDTO.getXczpimg()), true, fbDO));
                //?????????id????????????????????????
                Example updateByExampleSelective = new Example(BdcXmDO.class);
                updateByExampleSelective.createCriteria()
                        .andEqualTo("xmid", fbDO.getXmid());
                bdcEntityMapper.updateByExampleSelective(fbDO, updateByExampleSelective);
                return CommonResponse.ok("???????????????");
            }
        }

        return CommonResponse.fail("?????????????????????????????????????????????????????????");
    }

    /**
     * ???????????????????????????????????????
     *
     * @param ywnum@return
     * @Date 2021/11/5
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public String queryCheckFacePicture(String ywnum) {
        String xczpimg = "";
        BdcXmFbQO fbQO = new BdcXmFbQO();
        fbQO.setXmid(ywnum);
        List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(fbQO);
        if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
            BdcXmFbDO bdcXmFbDO = bdcXmFbDOList.get(0);
            if (StringUtils.isNotBlank(bdcXmFbDO.getXczpimg())) {
                BaseResultDto baseResultDto = storageClient.downloadBase64(bdcXmFbDO.getXczpimg());
                if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
                    if (StringUtils.isNotBlank(baseResultDto.getMsg())) {
                        String[] baseArry = baseResultDto.getMsg().split(",");
                        xczpimg = baseArry[1];
                        return xczpimg;
                    }
                }
            }
            return "???????????????????????????????????????????????????";
        }
        return "";
    }


    private BdcQlrDO initBdcQlrDO(String xmid, JSONObject checkFacdResultJsonObject, int sxh) {
        BdcQlrDO bdcQlrDO = BdcQlrDO.BdcQlrDOBuilder.aBdcQlrDO().withXmid(xmid)
                .withQlrmc(checkFacdResultJsonObject.getString("personName"))
                .withMz(checkFacdResultJsonObject.getString("family"))
                .withTxdz(checkFacdResultJsonObject.getString("address"))
                .withZjh(checkFacdResultJsonObject.getString("idCode"))
                .withZjzl(1)
                .withCsrq(checkFacdResultJsonObject.getString("birth"))
                .withQlrzjqfjg(checkFacdResultJsonObject.getString("department"))
                .withQlrlx(1)
                .withQlrlb("1")
                .withSxh(sxh)
//                .withRlsbppd(checkFacdResultJsonObject.getString("similar"))
                .withQlrid(UUIDGenerator.generate16())
                .build();
        if (checkFacdResultJsonObject.getString("gender").equals(CzRlsbConstansEnum.MAN.getDescription())) {
            bdcQlrDO.setXb(CzRlsbConstansEnum.MAN.getValue());
        } else if (checkFacdResultJsonObject.getString("gender").equals(CzRlsbConstansEnum.WOMAN.getDescription())) {
            bdcQlrDO.setXb(CzRlsbConstansEnum.WOMAN.getValue());
        } else if (checkFacdResultJsonObject.getString("gender").equals(CzRlsbConstansEnum.UNKNOW.getDescription())) {
            bdcQlrDO.setXb(CzRlsbConstansEnum.UNKNOW.getValue());
        } else {
            bdcQlrDO.setXb(checkFacdResultJsonObject.getInteger("gender"));
        }
        return bdcQlrDO;
    }


    private String uploadFileToFilecenter(StorageDto storageDto, String fileName, String gzlslId, MultipartFile
            file, boolean isUpdate, Map<String, String> fileMap) throws IOException {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setName(fileName);
        multipartDto.setNodeId(storageDto.getId());
        multipartDto.setSpaceCode(gzlslId);
        multipartDto.setClientId("clientId");
        multipartDto.setContentType("application/octet-stream");
        multipartDto.setData(file.getBytes());
        multipartDto.setSize(file.getSize());
        multipartDto.setOriginalFilename(fileName + ".jpg");
        multipartDto.setOwner("");
        log.info("?????????????????????filename{}???id{}", fileName, storageDto.getId());
        if (isUpdate) {
            if (fileMap.containsKey(fileName + ".jpg")) {
                StorageDto storageDto1 = storageClient.replaceUpload(fileMap.get(fileName + ".jpg"), multipartDto);
                return storageDto1.getId();
            } else {
                StorageDto storageDto1 = storageClient.multipartUpload(multipartDto);
                return storageDto1.getId();
            }
        } else {
            StorageDto storageDto1 = storageClient.multipartUpload(multipartDto);
            return storageDto1.getId();
        }
    }

    /**
     * ??????????????????????????????
     *
     * @param
     * @return
     * @Date 2021/11/6
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    private String uploadFileToFilecenterForRlsbHb(StorageDto storageDto, String fileName, String
            gzlslId, MultipartFile file, boolean isUpdate, BdcXmFbDO bdcXmFbDO) throws IOException {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setName(fileName);
        multipartDto.setNodeId(storageDto.getId());
        multipartDto.setSpaceCode(gzlslId);
        multipartDto.setClientId("clientId");
        multipartDto.setContentType("application/octet-stream");
        multipartDto.setData(file.getBytes());
        multipartDto.setSize(file.getSize());
        multipartDto.setOriginalFilename(fileName + ".jpg");
        multipartDto.setOwner("");
        log.info("???????????????????????????????????????filename{}???id{},xczpimgID{}", fileName, storageDto.getId(), bdcXmFbDO.getXczpimg());
        if (isUpdate) {
            if (StringUtils.isNotBlank(bdcXmFbDO.getXczpimg())) {
                StorageDto storageDto1 = storageClient.replaceUpload(bdcXmFbDO.getXczpimg(), multipartDto);
                return storageDto1.getId();
            } else {
                StorageDto storageDto1 = storageClient.multipartUpload(multipartDto);
                return storageDto1.getId();
            }
        } else {
            StorageDto storageDto1 = storageClient.multipartUpload(multipartDto);
            return storageDto1.getId();
        }
    }

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description base64??????MultipartFile
     */
    private MultipartFile toMultipartFile(String base64Str) throws IOException {
        String jpgBase64Str = "data:image/jpg;base64," + base64Str;
        MultipartFile multipartFile = Base64Utils.base64ToMultipart(jpgBase64Str);
        if (null == multipartFile) {
            throw new AppException("????????????????????????????????????,??????: base64???MultipartFile??????!");
        }
        return multipartFile;
    }

    /**
     * ?????????????????????json
     *
     * @param content
     * @return
     */
    public static boolean isJson(String content) {
        if (StringUtils.isEmpty(content)) {
            return false;
        }
        boolean isJsonObject = true;
        boolean isJsonArray = true;
        try {
            JSONObject.parseObject(content);
        } catch (Exception e) {
            isJsonObject = false;
        }
        try {
            JSONObject.parseArray(content);
        } catch (Exception e) {
            isJsonArray = false;
        }
        //??????json??????
        if (!isJsonObject && !isJsonArray) {
            return false;
        }
        return true;
    }

    public void dateToString(List<BdcDsfRlsbDO> dsfRlsbDOS) {
        if (CollectionUtils.isNotEmpty(dsfRlsbDOS)) {
            for (BdcDsfRlsbDO dsfRlsbDO : dsfRlsbDOS) {
                if (null == dsfRlsbDO.getCreatetime()) {
//                    dsfRlsbDO.setCreatetime("");
                }
            }
        }
    }

    private BdcCzRlsbDTO getBdcCzRlsbDTO(BdcXmDO bdcXmDO) {
        log.info("??????????????????id??????????????????????????????????????????:{}", bdcXmDO.getGzldyid());
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setDsfxtbs("czrlsb");
        bdcZdDsfzdgxDO.setZdbs("BDC_CZ_RLSB_YWLX");
        List<BdcZdDsfzdgxDO> zdList = bdcZdFeignService.queryDsfZdxBybs(bdcZdDsfzdgxDO);
        if (CollectionUtils.isNotEmpty(zdList)) {
            Optional<BdcZdDsfzdgxDO> any = zdList.stream().filter(zdDsfzdgxDo -> bdcXmDO.getGzldyid().equals(zdDsfzdgxDo.getBdczdz())).findAny();
            if (any.isPresent()) {
                log.info("??????????????????id????????????????????????????????????????????????");
                return BdcCzRlsbDTO.BdcCzRlsbDTOBuilder.aBdcCzRlsbDTO().withXmid(bdcXmDO.getXmid()).withYwlx(any.get().getDsfzdz()).build();
            }
        }
        return null;
    }

    /**
     * @param gzlslid ???????????????ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????????????????????????????????????????????????????????????????????????????????????????
     */
    private void updateQlrxxPl(String gzlslid, String djxl, String qlrlb) {
        //???????????????????????????????????????????????????
        List<BdcQlrXmDTO> bdcQlrXmDTOList = new ArrayList<>();
        BdcQlrXmDTO bdcQlrXmDTO = new BdcQlrXmDTO();
        if (StringUtils.isNotBlank(qlrlb)) {
            bdcQlrXmDTO.setQlrlb(Integer.parseInt(qlrlb));
        } else {
            bdcQlrXmDTO.setQlrlb(CommonConstantUtils.QLRLB_QLR_DM);
        }
        List<String> xmids = new ArrayList<>();
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                //????????????????????????????????????
                if (StringUtils.isBlank(djxl) || StringUtils.equals(bdcXmDTO.getDjxl(), djxl)) {
                    xmids.add(bdcXmDTO.getXmid());
                }
            }
            bdcQlrXmDTO.setXmidList(xmids);
            bdcQlrXmDTOList.add(bdcQlrXmDTO);
            bdcRyzdFeignService.updateRyzdQlrXm(bdcQlrXmDTOList);
        }
        //?????????????????????
        try {
            bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????????????????
     */
    private boolean syncTdsyqr(String processInsId) {
        if (StringUtils.isBlank(tdsyqrGzldyids)) {
            return false;
        }
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if (CollectionUtils.isEmpty(bdcXmDTOList)) {
            return false;
        }
        String gzldyid = bdcXmDTOList.get(0).getGzldyid();
        return (tdsyqrGzldyids.indexOf(gzldyid) > -1);
    }
}
