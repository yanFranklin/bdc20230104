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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.WebService;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zedeqaing@gtmap.cn">zedq</a>
 * @version 2020/12/17,1.0
 * @description 人脸识别webservice
 */
@Service
@WebService(name = "FaceCheckInterface",
        targetNamespace = "http://service.etl.realestate.gtmap.cn",
        endpointInterface = "cn.gtmap.realestate.etl.service.FaceCheckInterface")
public class FaceCheckInterfaceImpl implements FaceCheckInterface {

    private final Logger log = LoggerFactory.getLogger(FaceCheckInterfaceImpl.class);

    @Autowired
    @Qualifier("bdcEntityMapper")
    private EntityMapper bdcEntityMapper;

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private FaceCheckConverter faceCheckConverter;

    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcXmfbFeignService bdcXmfbFeignService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    private BdcQlrFeignService qlrFeignService;

    @Autowired
    private BdcRyzdFeignService bdcRyzdFeignService;
    @Autowired
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;

    @Autowired
    BdcSlQlrFeignService bdcSlQlrFeignService;

    @Autowired
    FaceDeleteServiceImpl faceDeleteService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    /**
     * 同步生成土地使用权人配置的工作流实例ID,多个采用“,”分隔
     */
    @Value("${tbqlrtofdcq.gzldyid:}")
    private String tdsyqrGzldyids;

    @Value("${spfclfdjxl:2000401,2000407,2000801，2000402，2000802，2000601}")
    private String spfclfdjxl;

    @Value("${sfxzslcl:false}")
    private boolean sfxzslcl;

    /**
     * 新增、修改、删除权利人时，同步修改房地产权中的土地使用权人（使用“/"拼接）
     *
     * @param json         不动产权利人集合JSON
     * @param processInsId 工作流实例ID
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
     * 人脸识别
     * <p>
     * 该接口描述
     * 初次调用，根据项目id返回相应的买卖双方信息
     * 多次调用，返回人脸比对结果并留存
     *
     * @param param
     * @return
     */
    @Override
//    @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
    public String checkResultInfo(String param) throws Exception {
        log.info("人脸识别入参:{}", param);
        JSONObject responesJsonObject = new JSONObject();
        List<BdcDsfRlsbVO> rlsbVOList = new ArrayList<>(4);
        if (StringUtils.isNotBlank(param) && !isJson(param)) {
            //根据项目id返回买卖双方信息
            //获取项目信息
            Example queryXmInfoExample = new Example(BdcXmDO.class);
            queryXmInfoExample.createCriteria().andEqualTo("xmid", param);
            List<BdcXmDO> xmInfo = bdcEntityMapper.selectByExample(queryXmInfoExample);

            //获取当前相关人员信息
            Example queryBdcDsfRlsbParam = new Example(BdcDsfRlsbDO.class);
            queryBdcDsfRlsbParam.createCriteria().andEqualTo("ywnum", param);
            List<BdcDsfRlsbDO> personInfo = bdcEntityMapper.selectByExample(queryBdcDsfRlsbParam);
            if (CollectionUtils.isNotEmpty(xmInfo)) {
                if (CollectionUtils.isNotEmpty(personInfo)) {
                    //转换照片信息
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
                    log.info("正常获取人脸识别有数据{}", JSON.toJSONString(responesJsonObject));

                    return JSONObject.toJSONString(responesJsonObject, SerializerFeature.WriteNullStringAsEmpty);
                }
                log.info("正常获取人脸识别{}", JSON.toJSONString(rlsbVOList));

                responesJsonObject.put("personEntitys", rlsbVOList);
                return JSONObject.toJSONString(rlsbVOList, SerializerFeature.WriteNullStringAsEmpty);
            } else {
                return "未关联到相关项目信息";
            }
        } else if (StringUtils.isNotBlank(param) && isJson(param)) {
            log.info("json格式入参处理{}", param);
            JSONObject jsonObject = JSON.parseObject(param);
            JSONArray checkFacdResultJsonArray = jsonObject.getJSONArray("personEntitys");
            if (checkFacdResultJsonArray != null && checkFacdResultJsonArray.size() > 0) {
                String xmid = checkFacdResultJsonArray.getJSONObject(0).getString("ywnum");
                if (StringUtils.isNotBlank(xmid)) {
                    //先验证，后操作qlr表删除更新操作
                    List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
                    List<BdcDsfRlsbDO> bdcYwrDOList = new ArrayList<>();
                    List<BdcDsfRlsbDO> bdcDyrDOList = new ArrayList<>();
                    List<BdcDsfRlsbDO> bdcQlrDsfRlsbList = new ArrayList<>();
                    BdcQlrDO qlrDO = new BdcQlrDO();
                    int sxh = 2;
                    int buyzqrFlag = 0;
                    int dyazqrFlag = 0;
                    int sellzqrFlag = 0;
                    //先循环进行验证，无问题之后，再循环对数据进行操作
                    for (int i = 0; i < checkFacdResultJsonArray.size(); i++) {
                        JSONObject checkFacdResultJsonObject = checkFacdResultJsonArray.getJSONObject(i);
                        if ("1".equals(checkFacdResultJsonObject.getString("role"))) {
                            buyzqrFlag++;
                            log.info("buyzqrFlag个数：{}", buyzqrFlag);

                        } else if ("3".equals(checkFacdResultJsonObject.getString("role"))) {
                            sellzqrFlag++;
                            log.info("sellzqrFlag个数：{}", sellzqrFlag);

                        } else if ("5".equals(checkFacdResultJsonObject.getString("role"))) {
                            dyazqrFlag++;
                            log.info("dyazqrFlag个数：{}", dyazqrFlag);
                        }
                    }

                    //上面已经判断无问题，再循环，对数据进行分类操作
                    log.info("创建人脸识别文件夹");
                    Example queryXmInfoExample = new Example(BdcXmDO.class);
                    queryXmInfoExample.createCriteria().andEqualTo("xmid", xmid);
                    List<BdcXmDO> xmDOList = bdcEntityMapper.selectByExample(queryXmInfoExample);
                    Example queryAllXmInfo = new Example(BdcXmDO.class);
                    queryAllXmInfo.createCriteria().andEqualTo("gzlslid", xmDOList.get(0).getGzlslid());
                    List<BdcXmDO> allXmList = bdcEntityMapper.selectByExample(queryAllXmInfo);
                    if (CollectionUtils.isNotEmpty(xmDOList) && StringUtils.isNotBlank(xmDOList.get(0).getGzlslid())) {
                        String gzlslid = xmDOList.get(0).getGzlslid();

                        // //根据gzldyid对照业务类型进行判断
                        BdcCzRlsbDTO czRlsbDTO = getBdcCzRlsbDTO(xmDOList.get(0));
                        if (null != czRlsbDTO) {
                            if ("1".equals(czRlsbDTO.getYwlx())) {
                                if (buyzqrFlag > 1) {
                                    return "买方主权人设置不正确!";
                                }
                                if (sellzqrFlag > 1) {
                                    return "卖方主权人设置不正确!";
                                }
                            }
                            if ("2".equals(czRlsbDTO.getYwlx())) {
                                if (buyzqrFlag != 1) {
                                    return "买方主权人设置不正确!";
                                }
                            }
                            if ("3".equals(czRlsbDTO.getYwlx())) {
                                if (dyazqrFlag > 1) {
                                    return "卖方主权利人设置不正确";
                                }
                            }
                        } else {
                            return "未配置业务类型对照！";
                        }

                        //校验文件夹是否存在
                        //获取当前相关人员信息
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
                                storageDto = storageClient.createRootFolder("clientId", xmDOList.get(0).getGzlslid(), "人脸识别", null);
                            } else {
                                //组合流程查询当前项目关联的文件夹
                                List<Map> zdList = bdcZdFeignService.queryBdcZd("djxl");
                                List<StorageDto> storageDtos = storageClient.queryMenus("clientId", xmDOList.get(0).getGzlslid(), "", null, null, null, null, null);
                                String parentFolder = StringToolUtils.convertBeanPropertyValueOfZdByString(xmDOList.get(0).getDjxl(), zdList);
                                Optional<StorageDto> storangOptional = storageDtos.stream().filter(storageDto1 -> storageDto1.getName().equals(parentFolder)).findAny();
                                if (storangOptional.isPresent()) {
                                    storageDto = storageClient.createFolder("clientId", xmDOList.get(0).getGzlslid(), storangOptional.get().getId(), "人脸识别", null);
                                } else {
                                    StorageDto xmFolder = storageClient.createRootFolder("clientId", xmDOList.get(0).getGzlslid(), parentFolder, null);
                                    storageDto = storageClient.createFolder("clientId", xmDOList.get(0).getGzlslid(), xmFolder.getId(), "人脸识别", null);
                                }
                            }
                        }
                        if (sfxzslcl) {
                            //判读受理库是否存在文件夹，没有就新增
                            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(xmDOList.get(0).getGzlslid(), "人脸识别");
                            if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
                                BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
                                bdcSlSjclDO.setClmc("人脸识别");
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
                                log.info("更新义务人信息");
                                BdcDsfRlsbDO bdcDsfRlsbDO = faceCheckConverter.getBdcDsfRlsbDOByBdcDsfRlsbVO(dsfRlsbVO);
                                bdcYwrDOList.add(bdcDsfRlsbDO);

                            } else if ("3".equals(checkFacdResultJsonObject.getString("role"))) {
                                log.info("更新主义务人信息");
                                BdcDsfRlsbDO bdcDsfRlsbDO = faceCheckConverter.getBdcDsfRlsbDOByBdcDsfRlsbVO(dsfRlsbVO);
                                bdcYwrDOList.add(bdcDsfRlsbDO);
                            } else if ("5".equals(checkFacdResultJsonObject.getString("role"))) {
                                log.info("更新抵押人信息");
                                BdcDsfRlsbDO bdcDsfRlsbDO = faceCheckConverter.getBdcDsfRlsbDOByBdcDsfRlsbVO(dsfRlsbVO);
                                bdcDyrDOList.add(bdcDsfRlsbDO);

                            } else if ("4".equals(checkFacdResultJsonObject.getString("role"))) {
                                log.info("更新抵押人信息role=4的");
                                BdcDsfRlsbDO bdcDsfRlsbDO = faceCheckConverter.getBdcDsfRlsbDOByBdcDsfRlsbVO(dsfRlsbVO);
                                bdcDyrDOList.add(bdcDsfRlsbDO);

                            }
                        }
                        //删除人脸识别表,再保存
                        try {
                            faceDeleteService.deleteRlsbQlr(xmid);
                        } catch (Exception e) {
                            log.info("删除人脸识别表出错!,xmid:{}", xmid);
                            throw new AppException("删除人脸识别表出错！");
                        }

                        //义务人和抵押人保存到rlsb表,qlr也保存到rlsb
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
                         *  1、根据流程业务流程进行处理，抵押流程不操作权利人表，只操作人脸识别表
                         *  2.	商品房、存量房流程通过合同发起会带入联系电话，人脸识别后会清空电话，需要做出判断，证件号相同时就不更新申请人信息，只需要保存比对信息。
                         */
                        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                            //组合流程，更新另一个项目的义务人
                            int lclx = bdcXmFeignService.makeSureBdcXmLx(allXmList);
                            //单个流程
                            if (lclx == 1) {
                                if (CommonConstantUtils.QLLX_DYAQ_DM.equals(xmDOList.get(0).getQllx())) {
                                    log.info("该xmid为抵押流程，只保存人脸识别表，不更新权利人表！xmid:{}", xmid);
                                  /*  //回写信息到平台
                                    try {
                                        bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
                                    } catch (Exception e) {
                                        log.info(e.getMessage());
                                    }
                                    return "success";*/
                                } else if (CommonConstantUtils.DJLX_ZYDJ_DM.equals(xmDOList.get(0).getDjlx()) && spfclfdjxl.indexOf(xmDOList.get(0).getDjxl()) > -1) {
                                    /**
                                     * 商品房,存量房转移特殊处理
                                     * 用人脸识别的权利人数据，查权利人表，存在则存储到saveQlrlist中，等后面一起插入，不存在也放入；循环完之后，删除该项目的所有权利人，然后插入刚刚判断好的saveQlrList
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
                                                log.info("库中查到有相同证件号的权利人!,xmid:{}.zjh:{}", xmid, qlrDO1.getZjh());
                                                allQlrDoList.get(0).setQlrid(UUIDGenerator.generate16());
                                                saveQlrList.addAll(allQlrDoList);
                                            } else {
                                                saveQlrList.add(qlrDO1);
                                            }
                                        }
                                        //删除权利人表数据，重新插入
                                        try {
                                            faceDeleteService.deleteBdcQlr(xmid);
                                        } catch (Exception e) {
                                            log.info("单个流程删除权利人表出错!,xmid:{}", xmid);
                                            throw new AppException("单个流程删除权利人表出错！");
                                        }
                                        //处理项目表冗余权利人字段
                                        dgxmRyqlrxx(saveQlrList, xmDOList);
                                        //回写信息到平台
                                        try {
                                            bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
                                        } catch (Exception e) {
                                            log.info(e.getMessage());
                                        }
                                    }

                                } else {
                                    //其他单个流程处理， 删除权利人表数据，重新插入
                                    try {
                                        faceDeleteService.deleteBdcQlr(xmid);
                                    } catch (Exception e) {
                                        log.info("其他单个流程删除权利人表出错!,xmid:{}", xmid);
                                        throw new AppException("其他单个流程删除权利人表出错！");
                                    }
                                    //处理项目表冗余权利人字段
                                    dgxmRyqlrxx(bdcQlrDOList, xmDOList);
                                    //回写信息到平台
                                    try {
                                        bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
                                    } catch (Exception e) {
                                        log.info(e.getMessage());
                                    }
                                }

                            }
                            //组合流程
                            if (lclx == 2) {
                                log.info("组合流程gzlslid:{}", gzlslid);
                                log.info("组合流程开始判断当前xmid是抵押还是转移，分别逻辑处理");
                                if (CommonConstantUtils.QLLX_DYAQ_DM.equals(xmDOList.get(0).getQllx())) {
                                    log.info("该xmid为抵押流程，只保存人脸识别表，不更新权利人表！xmid:{}", xmid);

                                    return "success";
                                } else if (CommonConstantUtils.DJLX_ZYDJ_DM.equals(xmDOList.get(0).getDjlx()) && spfclfdjxl.indexOf(xmDOList.get(0).getDjxl()) > -1) {
                                    //商品房转移特殊处理
                                    //用人脸识别的权利人数据，查权利人表，存在则存储到saveQlrlist中，等后面一起插入，不存在也放入；循环完之后，删除该项目的所有权利人，然后插入刚刚判断好的saveQlrList
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
                                        //删除权利人表数据，重新插入
                                        try {
                                            log.info("组合流程流程业务删除权利人表表!,xmid:{}", xmid);
                                            faceDeleteService.deleteBdcQlr(xmid);
                                        } catch (Exception e) {
                                            log.info("删除人脸识别表出错!,xmid:{}", xmid);
                                            throw new AppException("删除人脸识别表出错！");
                                        }
                                        //处理项目表冗余权利人字段
                                        dgxmRyqlrxx(saveQlrList, xmDOList);
                                        //回写信息到平台
                                        try {
                                            bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
                                        } catch (Exception e) {
                                            log.info(e.getMessage());
                                        }

                                    }

                                } else {
                                    //其他流程业务，按原逻辑走，先删除权利人表数据，再新增
                                    //删除权利人表数据，重新插入
                                    try {
                                        log.info("其他流程业务删除权利人表表!,xmid:{}", xmid);
                                        faceDeleteService.deleteBdcQlr(xmid);
                                    } catch (Exception e) {
                                        log.info("其他流程业务删除权利人表表出错!,xmid:{}", xmid);
                                        throw new AppException("删除权利人表出错！");
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
                                            //回写信息到平台
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
                            //批量流程
                            if (lclx == 3) {
                                log.info("批量流程gzlslid:{}", gzlslid);
                                if (CommonConstantUtils.QLLX_DYAQ_DM.equals(xmDOList.get(0).getQllx())) {
                                    log.info("该xmid为抵押流程，只保存人脸识别表，不更新权利人表！xmid:{}", xmid);
                                    return "success";
                                } else if (CommonConstantUtils.DJLX_ZYDJ_DM.equals(xmDOList.get(0).getDjlx()) && spfclfdjxl.indexOf(xmDOList.get(0).getDjxl()) > -1) {
                                    //商品房转移特殊处理
                                    //用人脸识别的权利人数据，查权利人表，存在则存储到saveQlrlist中，等后面一起插入，不存在也放入；循环完之后，删除该项目的所有权利人，然后插入刚刚判断好的saveQlrList
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
                                        //删除权利人表数据，重新插入
                                        try {
                                            log.info("组合流程流程业务删除权利人表表!,xmid:{}", xmid);
                                            faceDeleteService.deleteBdcQlr(xmid);
                                        } catch (Exception e) {
                                            log.info("删除人脸识别表出错!,xmid:{}", xmid);
                                            throw new AppException("删除人脸识别表出错！");
                                        }
                                        //处理项目表冗余权利人字段
                                        dgxmRyqlrxx(saveQlrList, xmDOList);
                                        //回写信息到平台
                                        try {
                                            bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
                                        } catch (Exception e) {
                                            log.info(e.getMessage());
                                        }
                                    }

                                } else {
                                    //其他批量正常处理
                                    if (null != qlrDO) {
                                        //新增前先删除相同的权利人,防止有重复权利人数据
                                        qlrFeignService.deleteBdcQlrsByQlrxx(qlrDO.getQlrmc(), qlrDO.getZjh(), gzlslid, qlrDO.getQlrlb(), "", new ArrayList<>());
                                        if (qlrDO.getQlrly() == null) {
                                            qlrDO.setQlrly(CommonConstantUtils.QLRLY_SD);
                                        }
                                        List<BdcQlrDO> batchBdcQlr = qlrFeignService.insertBatchBdcQlr(qlrDO, gzlslid, "");
                                        if (CollectionUtils.isNotEmpty(batchBdcQlr)) {
                                            if (StringUtils.isNotBlank(gzlslid)) {
                                                //权利人保存后更新权利人相关信息
                                                updateQlrxxPl(gzlslid, "", batchBdcQlr.get(0).getQlrlb());
                                                // 同步更新土地使用权人
                                                this.notifySyncTdsyqr(JSON.toJSONString(batchBdcQlr), gzlslid);
                                            }

                                        }
                                    }
                                }
                            }
                            //批量组合
                            if (lclx == 4) {
                                log.info("批量流程gzlslid:{}", gzlslid);

                                //其他批量组合正常处理
                                BdcQlrDO bdcQlrDO = bdcSlQlrFeignService.insertPlzhBdcQlr(JSONObject.toJSONString(qlrDO), gzlslid, xmDOList.get(0).getDjxl(), syncTdsyqr(gzlslid));
                                log.info("批量流程bdcQlrDO:{}", bdcQlrDO.toString());


                            }
                            log.info("处理批量流程其他项目信息结束");
                        }
                    }
                } else {
                    return "当前项目关联的工作流实例为空";
                }
            } else {
                return "ywnum字段为空";
            }
        } else {
            return "personEntitys信息为空";
        }
        return "success";
    }

    /**
     * 保存到人脸识别表中
     *
     * @param
     * @return
     * @Date 2022/2/17
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    private void saveRlsbTable(List<BdcDsfRlsbDO> bdcYwrDOList, List<BdcDsfRlsbDO> bdcDyrDOList, List<BdcDsfRlsbDO> bdcQlrDsfRlsbList, StorageDto storageDto) {
        //义务人和抵押人保存到rlsb表,qlr也保存到rlsb
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
     * 重新插入权利人数据，更新当前xm表相关信息
     *
     * @param xmDOList mid查询到的项目表信息
     * @return
     * @Date 2022/2/17
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    private void dgxmRyqlrxx(List<BdcQlrDO> bdcQlrDOList, List<BdcXmDO> xmDOList) {
        int i = bdcEntityMapper.insertBatchSelective(bdcQlrDOList);
        log.info("插入成功数量:{}", i);
        if (CollectionUtils.isNotEmpty(xmDOList)) {
            Integer qllx = xmDOList.get(0).getQllx();
            log.info("处理流程其他项目信息开始");
            log.info("处理项目表权利人信息开始");
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
     * 保存人脸识别接口上传的图片，更新文件id到项目表
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
            //开始上传文件到文件中，保存文件id到附表中
            StorageDto storageDto = storageClient.createRootFolder("clientId", fbDO.getGzlslid(), "人脸识别", null);
            if (Objects.nonNull(storageDto)) {
                fbDO.setXczpimg(uploadFileToFilecenterForRlsbHb(storageDto, "人脸识别合并照片", fbDO.getGzlslid(), toMultipartFile(checkFacePictureDTO.getXczpimg()), true, fbDO));
                //将文件id更新到项目附表中
                Example updateByExampleSelective = new Example(BdcXmDO.class);
                updateByExampleSelective.createCriteria()
                        .andEqualTo("xmid", fbDO.getXmid());
                bdcEntityMapper.updateByExampleSelective(fbDO, updateByExampleSelective);
                return CommonResponse.ok("上传成功！");
            }
        }

        return CommonResponse.fail("项目附表查不到该项目信息！请检查数据！");
    }

    /**
     * 查询人脸识别接口上传的图片
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
            return "改项目无人脸识别合并图片，请检查！";
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
        log.info("更新文件信息：filename{}，id{}", fileName, storageDto.getId());
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
     * 上传人脸识别合并图片
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
        log.info("更新人脸识别合并文件信息：filename{}，id{},xczpimgID{}", fileName, storageDto.getId(), bdcXmFbDO.getXczpimg());
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
     * @description base64码转MultipartFile
     */
    private MultipartFile toMultipartFile(String base64Str) throws IOException {
        String jpgBase64Str = "data:image/jpg;base64," + base64Str;
        MultipartFile multipartFile = Base64Utils.base64ToMultipart(jpgBase64Str);
        if (null == multipartFile) {
            throw new AppException("上传人脸比对相关文件失败,原因: base64转MultipartFile失败!");
        }
        return multipartFile;
    }

    /**
     * 判断入参是否是json
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
        //不是json格式
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
        log.info("根据流程定义id获取对应的人脸识别的业务类型:{}", bdcXmDO.getGzldyid());
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setDsfxtbs("czrlsb");
        bdcZdDsfzdgxDO.setZdbs("BDC_CZ_RLSB_YWLX");
        List<BdcZdDsfzdgxDO> zdList = bdcZdFeignService.queryDsfZdxBybs(bdcZdDsfzdgxDO);
        if (CollectionUtils.isNotEmpty(zdList)) {
            Optional<BdcZdDsfzdgxDO> any = zdList.stream().filter(zdDsfzdgxDo -> bdcXmDO.getGzldyid().equals(zdDsfzdgxDo.getBdczdz())).findAny();
            if (any.isPresent()) {
                log.info("根据流程定义id获取对应的人脸识别的业务类型结束");
                return BdcCzRlsbDTO.BdcCzRlsbDTOBuilder.aBdcCzRlsbDTO().withXmid(bdcXmDO.getXmid()).withYwlx(any.get().getDsfzdz()).build();
            }
        }
        return null;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量流程更新权利人相关信息（包括权利人冗余字段与共有情况，权利人回写）
     */
    private void updateQlrxxPl(String gzlslid, String djxl, String qlrlb) {
        //组织数据批量更新冗余字段和共有情况
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
                //登记小类为空或者两者一致
                if (StringUtils.isBlank(djxl) || StringUtils.equals(bdcXmDTO.getDjxl(), djxl)) {
                    xmids.add(bdcXmDTO.getXmid());
                }
            }
            bdcQlrXmDTO.setXmidList(xmids);
            bdcQlrXmDTOList.add(bdcQlrXmDTO);
            bdcRyzdFeignService.updateRyzdQlrXm(bdcQlrXmDTOList);
        }
        //回写信息到平台
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
     * @description 获取是否同步土地使用权人
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
