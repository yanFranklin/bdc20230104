package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.etl.core.convert.CzRlsbConstansEnum;
import cn.gtmap.realestate.etl.core.convert.FaceCheckConverter;
import cn.gtmap.realestate.etl.core.domian.nantong.BdcDsfRlsbDO;
import cn.gtmap.realestate.etl.core.vo.BdcDsfRlsbVO;
import cn.gtmap.realestate.etl.service.FaceCheckInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Qualifier("entityMapper")
    private EntityMapper entityMapper;

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private FaceCheckConverter faceCheckConverter;

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
    @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
    public String checkResultInfo(String param) throws Exception {
        log.info("人脸识别入参:{}", param);
        JSONObject responesJsonObject = new JSONObject();
        List<BdcDsfRlsbVO> rlsbVOList = new ArrayList<>(4);
        if (StringUtils.isNotBlank(param) && !isJson(param)) {
            //根据项目id返回买卖双方信息
            //获取项目信息
            Example queryXmInfoExample = new Example(BdcXmDO.class);
            queryXmInfoExample.createCriteria().andEqualTo("xmid", param);
            List<BdcXmDO> xmInfo = entityMapper.selectByExample(queryXmInfoExample);

            //获取当前相关人员信息
            Example queryBdcDsfRlsbParam = new Example(BdcDsfRlsbDO.class);
            queryBdcDsfRlsbParam.createCriteria().andEqualTo("ywnum", param);
            List<BdcDsfRlsbDO> personInfo = entityMapper.selectByExample(queryBdcDsfRlsbParam);

            if (CollectionUtils.isNotEmpty(xmInfo)) {
                //商品房转移特殊处理
                if ("200".equals(xmInfo.get(0).getDjlx()) && ("2000401".equals(xmInfo.get(0).getDjxl()) || "2000407".equals(xmInfo.get(0).getDjxl()) || "2000801".equals(xmInfo.get(0).getDjxl()))) {
                    if (CollectionUtils.isNotEmpty(personInfo)) {
                        //过滤义务人信息
                        List<BdcDsfRlsbDO> collect = personInfo.stream().filter(bdcDsfRlsbDO -> 1 == bdcDsfRlsbDO.getRole()).collect(Collectors.toList());
                        //转换照片信息
                        collect.forEach(bdcDsfRlsbDO -> {
                            if (StringUtils.isNotBlank(bdcDsfRlsbDO.getCardimage())) {
                                BaseResultDto baseResultDto = storageClient.downloadBase64(bdcDsfRlsbDO.getCardimage());
                                if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
                                    bdcDsfRlsbDO.setCardimage(baseResultDto.getMsg());
                                } else {
                                    bdcDsfRlsbDO.setCardimage(null);
                                }
                            }
                            if (StringUtils.isNotBlank(bdcDsfRlsbDO.getPersonimage())) {
                                BaseResultDto baseResultDto = storageClient.downloadBase64(bdcDsfRlsbDO.getPersonimage());
                                if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
                                    bdcDsfRlsbDO.setPersonimage(baseResultDto.getMsg());
                                } else {
                                    bdcDsfRlsbDO.setPersonimage(null);
                                }
                            }
                            if (StringUtils.isNotBlank(bdcDsfRlsbDO.getFacefeature())) {
                                BaseResultDto baseResultDto = storageClient.downloadBase64(bdcDsfRlsbDO.getFacefeature());
                                if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
                                    bdcDsfRlsbDO.setFacefeature(baseResultDto.getMsg());
                                } else {
                                    bdcDsfRlsbDO.setFacefeature(null);
                                }
                            }
                        });
                        List<BdcDsfRlsbVO> list = faceCheckConverter.getBdcDsfRlsbVOListByBdcDsfRlsbDOList(collect);
                        responesJsonObject.put("personEntitys", list);
                        return responesJsonObject.toJSONString();
                    } else {
                        //获取人员信息，从权利人表
                        Example queryQlrExample = new Example(BdcQlrDO.class);
                        queryQlrExample.createCriteria().andEqualTo("xmid", xmInfo.get(0).getXmid()).andEqualTo("qlrlb", "1");
                        List<BdcQlrDO> qlrList = entityMapper.selectByExample(queryQlrExample);
                        if (CollectionUtils.isNotEmpty(qlrList)) {
                            List<BdcDsfRlsbVO> bdcDsfRlsbVOList = faceCheckConverter.getBdcDsfRlsbVOListByBdcQlrDOList(qlrList);
                            bdcDsfRlsbVOList.forEach(
                                    bdcDsfRlsbVO -> {
                                        if (bdcDsfRlsbVO.getRole()==null ||bdcDsfRlsbVO.getRole() != 1) {
                                            bdcDsfRlsbVO.setRole(1);
                                        }
                                    }
                            );
                            responesJsonObject.put("personEntitys", bdcDsfRlsbVOList);
                            return responesJsonObject.toJSONString();
                        }
                    }
                    BdcDsfRlsbVO rlsbVO = BdcDsfRlsbVO.BdcDsfRlsbVOBuilder.aBdcDsfRlsbVO().withYwnum(param).withRole(1).build();
                    rlsbVOList.add(rlsbVO);
                    responesJsonObject.put("personEntitys", rlsbVOList);
                    return responesJsonObject.toJSONString();
                } else if ("200".equals(xmInfo.get(0).getDjlx()) && ("2000402".equals(xmInfo.get(0).getDjxl()) || "2000802".equals(xmInfo.get(0).getDjxl()) || "2000601".equals(xmInfo.get(0).getDjxl()))) {
                    if (CollectionUtils.isNotEmpty(personInfo)) {
                        //转换照片信息
                        personInfo.forEach(bdcDsfRlsbDO -> {
                            if (StringUtils.isNotBlank(bdcDsfRlsbDO.getCardimage())) {
                                BaseResultDto baseResultDto = storageClient.downloadBase64(bdcDsfRlsbDO.getCardimage());
                                if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
                                    bdcDsfRlsbDO.setCardimage(baseResultDto.getMsg());
                                } else {
                                    bdcDsfRlsbDO.setCardimage(null);
                                }
                            }
                            if (StringUtils.isNotBlank(bdcDsfRlsbDO.getPersonimage())) {
                                BaseResultDto baseResultDto = storageClient.downloadBase64(bdcDsfRlsbDO.getPersonimage());
                                if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
                                    bdcDsfRlsbDO.setPersonimage(baseResultDto.getMsg());
                                } else {
                                    bdcDsfRlsbDO.setPersonimage(null);
                                }
                            }
                            if (StringUtils.isNotBlank(bdcDsfRlsbDO.getFacefeature())) {
                                BaseResultDto baseResultDto = storageClient.downloadBase64(bdcDsfRlsbDO.getFacefeature());
                                if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
                                    bdcDsfRlsbDO.setFacefeature(baseResultDto.getMsg());
                                } else {
                                    bdcDsfRlsbDO.setFacefeature(null);
                                }
                            }
                        });
                        List<BdcDsfRlsbVO> list = faceCheckConverter.getBdcDsfRlsbVOListByBdcDsfRlsbDOList(personInfo);
                        responesJsonObject.put("personEntitys", list);
                        return responesJsonObject.toJSONString();
                    } else {
                        //获取人员信息，从权利人表
                        Example queryQlrExample = new Example(BdcQlrDO.class);
                        queryQlrExample.createCriteria().andEqualTo("xmid", xmInfo.get(0).getXmid()).andEqualTo("qlrlb", "1");
                        List<BdcQlrDO> qlrList = entityMapper.selectByExample(queryQlrExample);
                        Example queryYwrExample = new Example(BdcQlrDO.class);
                        queryYwrExample.createCriteria().andEqualTo("xmid", xmInfo.get(0).getXmid()).andEqualTo("qlrlb", "2");
                        List<BdcQlrDO> ywrList = entityMapper.selectByExample(queryYwrExample);
                        if (CollectionUtils.isNotEmpty(qlrList) || CollectionUtils.isNotEmpty(ywrList)) {
                            List<BdcDsfRlsbVO> bdcDsfRlsbVOQlrList = faceCheckConverter.getBdcDsfRlsbVOListByBdcQlrDOList(qlrList);
                            if (CollectionUtils.isNotEmpty(bdcDsfRlsbVOQlrList)){
                                bdcDsfRlsbVOQlrList.forEach(
                                        bdcDsfRlsbVO -> {
                                            if (bdcDsfRlsbVO.getRole()==null ||bdcDsfRlsbVO.getRole() != 1) {
                                                bdcDsfRlsbVO.setRole(0);
                                            }
                                        }
                                );
                                rlsbVOList.addAll(bdcDsfRlsbVOQlrList);
                            }
                            List<BdcDsfRlsbVO> bdcDsfRlsbVOYwrList = faceCheckConverter.getBdcDsfRlsbVOListByBdcQlrDOList(ywrList);
                            if (CollectionUtils.isNotEmpty(bdcDsfRlsbVOYwrList)){
                                bdcDsfRlsbVOYwrList.forEach(
                                        bdcDsfRlsbVO -> {
                                            bdcDsfRlsbVO.setRole(3);
                                        }
                                );
                                rlsbVOList.addAll(bdcDsfRlsbVOYwrList);
                            }
                            responesJsonObject.put("personEntitys", rlsbVOList);
                            return responesJsonObject.toJSONString();
                        }
                    }
                    BdcDsfRlsbVO qlrVo = BdcDsfRlsbVO.BdcDsfRlsbVOBuilder.aBdcDsfRlsbVO().withYwnum(param).withRole(1).build();
                    BdcDsfRlsbVO ywrVo = BdcDsfRlsbVO.BdcDsfRlsbVOBuilder.aBdcDsfRlsbVO().withYwnum(param).withRole(3).build();
                    rlsbVOList.add(qlrVo);
                    rlsbVOList.add(ywrVo);
                    responesJsonObject.put("personEntitys", rlsbVOList);
                    return responesJsonObject.toJSONString();
                } else if ("900".equals(xmInfo.get(0).getDjlx()) &&CommonConstantUtils.QLLX_DYAQ_DM.equals(xmInfo.get(0).getQllx())) {
                    if (CollectionUtils.isNotEmpty(personInfo)) {
                        //过滤权利人信息
                        List<BdcDsfRlsbDO> collect = personInfo.stream().filter(bdcDsfRlsbDO -> 3 == bdcDsfRlsbDO.getRole()).collect(Collectors.toList());
                        //转换照片信息
                        collect.forEach(bdcDsfRlsbDO -> {
                            if (StringUtils.isNotBlank(bdcDsfRlsbDO.getCardimage())) {
                                BaseResultDto baseResultDto = storageClient.downloadBase64(bdcDsfRlsbDO.getCardimage());
                                if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
                                    bdcDsfRlsbDO.setCardimage(baseResultDto.getMsg());
                                } else {
                                    bdcDsfRlsbDO.setCardimage(null);
                                }
                            }
                            if (StringUtils.isNotBlank(bdcDsfRlsbDO.getPersonimage())) {
                                BaseResultDto baseResultDto = storageClient.downloadBase64(bdcDsfRlsbDO.getPersonimage());
                                if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
                                    bdcDsfRlsbDO.setPersonimage(baseResultDto.getMsg());
                                } else {
                                    bdcDsfRlsbDO.setPersonimage(null);
                                }
                            }
                            if (StringUtils.isNotBlank(bdcDsfRlsbDO.getFacefeature())) {
                                BaseResultDto baseResultDto = storageClient.downloadBase64(bdcDsfRlsbDO.getFacefeature());
                                if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
                                    bdcDsfRlsbDO.setFacefeature(baseResultDto.getMsg());
                                } else {
                                    bdcDsfRlsbDO.setFacefeature(null);
                                }
                            }
                        });
                        List<BdcDsfRlsbVO> list = faceCheckConverter.getBdcDsfRlsbVOListByBdcDsfRlsbDOList(collect);
                        responesJsonObject.put("personEntitys", list);
                        return responesJsonObject.toJSONString();
                    } else {
                        //获取人员信息，从权利人表
                        Example queryYwrExample = new Example(BdcQlrDO.class);
                        queryYwrExample.createCriteria().andEqualTo("xmid", xmInfo.get(0).getXmid()).andEqualTo("qlrlb", "2");
                        List<BdcQlrDO> bdcYwrDOList = entityMapper.selectByExample(queryYwrExample);
                        if (CollectionUtils.isNotEmpty(bdcYwrDOList)) {
                            List<BdcDsfRlsbVO> bdcDsfRlsbVOList = faceCheckConverter.getBdcDsfRlsbVOListByBdcQlrDOList(bdcYwrDOList);
                            bdcDsfRlsbVOList.forEach(
                                    bdcDsfRlsbVO -> {
                                        bdcDsfRlsbVO.setRole(3);
                                    }
                            );
                            responesJsonObject.put("personEntitys", bdcDsfRlsbVOList);
                            return responesJsonObject.toJSONString();
                        }
                    }
                    BdcDsfRlsbVO ywrVo = BdcDsfRlsbVO.BdcDsfRlsbVOBuilder.aBdcDsfRlsbVO().withYwnum(param).withRole(3).build();
                    rlsbVOList.add(ywrVo);
                    responesJsonObject.put("personEntitys", rlsbVOList);
                    return responesJsonObject.toJSONString();
                } else {
                    if (CollectionUtils.isNotEmpty(personInfo)) {
                        //转换照片信息
                        personInfo.forEach(bdcDsfRlsbDO -> {
                            if (StringUtils.isNotBlank(bdcDsfRlsbDO.getCardimage())) {
                                BaseResultDto baseResultDto = storageClient.downloadBase64(bdcDsfRlsbDO.getCardimage());
                                if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
                                    bdcDsfRlsbDO.setCardimage(baseResultDto.getMsg());
                                } else {
                                    bdcDsfRlsbDO.setCardimage(null);
                                }
                            }
                            if (StringUtils.isNotBlank(bdcDsfRlsbDO.getPersonimage())) {
                                BaseResultDto baseResultDto = storageClient.downloadBase64(bdcDsfRlsbDO.getPersonimage());
                                if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
                                    bdcDsfRlsbDO.setPersonimage(baseResultDto.getMsg());
                                } else {
                                    bdcDsfRlsbDO.setPersonimage(null);
                                }
                            }
                            if (StringUtils.isNotBlank(bdcDsfRlsbDO.getFacefeature())) {
                                BaseResultDto baseResultDto = storageClient.downloadBase64(bdcDsfRlsbDO.getFacefeature());
                                if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
                                    bdcDsfRlsbDO.setFacefeature(baseResultDto.getMsg());
                                } else {
                                    bdcDsfRlsbDO.setFacefeature(null);
                                }
                            }
                        });
                        List<BdcDsfRlsbVO> list = faceCheckConverter.getBdcDsfRlsbVOListByBdcDsfRlsbDOList(personInfo);
                        responesJsonObject.put("personEntitys", list);
                        return responesJsonObject.toJSONString();
                    } else {
                        //获取人员信息，从权利人表
                        Example queryQlrExample = new Example(BdcQlrDO.class);
                        queryQlrExample.createCriteria().andEqualTo("xmid", xmInfo.get(0).getXmid()).andEqualTo("qlrlb", "1");
                        List<BdcQlrDO> qlrList = entityMapper.selectByExample(queryQlrExample);
                        Example queryYwrExample = new Example(BdcQlrDO.class);
                        queryYwrExample.createCriteria().andEqualTo("xmid", xmInfo.get(0).getXmid()).andEqualTo("qlrlb", "2");
                        List<BdcQlrDO> ywrList = entityMapper.selectByExample(queryYwrExample);
                        if (CollectionUtils.isNotEmpty(qlrList) || CollectionUtils.isNotEmpty(ywrList)) {
                            List<BdcDsfRlsbVO> bdcDsfRlsbVOQlrList = faceCheckConverter.getBdcDsfRlsbVOListByBdcQlrDOList(qlrList);
                            if (CollectionUtils.isNotEmpty(bdcDsfRlsbVOQlrList)){
                                bdcDsfRlsbVOQlrList.forEach(
                                        bdcDsfRlsbVO -> {
                                            if (bdcDsfRlsbVO.getRole()==null || bdcDsfRlsbVO.getRole() != 1) {
                                                bdcDsfRlsbVO.setRole(0);
                                            }
                                        }
                                );
                                rlsbVOList.addAll(bdcDsfRlsbVOQlrList);
                            }

                            List<BdcDsfRlsbVO> bdcDsfRlsbVOYwrList = faceCheckConverter.getBdcDsfRlsbVOListByBdcQlrDOList(ywrList);
                            if (CollectionUtils.isNotEmpty(bdcDsfRlsbVOYwrList)){
                                bdcDsfRlsbVOYwrList.forEach(
                                        bdcDsfRlsbVO -> {
                                            bdcDsfRlsbVO.setRole(3);
                                        }
                                );
                                rlsbVOList.addAll(bdcDsfRlsbVOYwrList);
                            }
                            responesJsonObject.put("personEntitys", rlsbVOList);
                            return responesJsonObject.toJSONString();
                        }
                    }
                    BdcDsfRlsbVO qlrVo = BdcDsfRlsbVO.BdcDsfRlsbVOBuilder.aBdcDsfRlsbVO().withYwnum(param).withRole(1).build();
                    BdcDsfRlsbVO ywrVo = BdcDsfRlsbVO.BdcDsfRlsbVOBuilder.aBdcDsfRlsbVO().withYwnum(param).withRole(3).build();
                    rlsbVOList.add(qlrVo);
                    rlsbVOList.add(ywrVo);
                    responesJsonObject.put("personEntitys", rlsbVOList);
                    return responesJsonObject.toJSONString();
                }
            } else {
                return "未关联到相关项目信息";
            }
        }else if (StringUtils.isNotBlank(param) && isJson(param)){
            JSONObject jsonObject = JSON.parseObject(param);
            JSONArray checkFacdResultJsonArray = jsonObject.getJSONArray("personEntitys");
            if (checkFacdResultJsonArray != null && checkFacdResultJsonArray.size() > 0) {
                String xmid = checkFacdResultJsonArray.getJSONObject(0).getString("ywnum");
                //删除qlr表相关信息
                if (StringUtils.isNotBlank(xmid)) {
                    List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
                    log.info("删除qlr信息入参:{}", xmid);
                    Example deleteQlrExample = new Example(BdcQlrDO.class);
                    deleteQlrExample.createCriteria().andEqualTo("xmid", xmid).andEqualTo("qlrlb", "1");
                    entityMapper.deleteByExample(deleteQlrExample);
                    log.info("删除bdcDsfRlsb信息入参:{}", xmid);
                    Example deleteBdcDsfRlsbExample = new Example(BdcDsfRlsbDO.class);
                    deleteBdcDsfRlsbExample.createCriteria().andEqualTo("ywnum", xmid);
                    entityMapper.deleteByExample(deleteBdcDsfRlsbExample);
                    log.info("创建人脸识别文件夹");
                    Example queryXmInfoExample = new Example(BdcXmDO.class);
                    queryXmInfoExample.createCriteria().andEqualTo("xmid", xmid);
                    List<BdcXmDO> xmDOList = entityMapper.selectByExample(queryXmInfoExample);
                    if (CollectionUtils.isNotEmpty(xmDOList) && StringUtils.isNotBlank(xmDOList.get(0).getGzlslid())) {
                        //校验文件夹是否存在
                        //获取当前相关人员信息
                        Example queryBdcDsfRlsbParam = new Example(BdcDsfRlsbDO.class);
                        queryBdcDsfRlsbParam.createCriteria().andEqualTo("ywnum", xmid);
                        List<BdcDsfRlsbDO> personInfo = entityMapper.selectByExample(queryBdcDsfRlsbParam);
                        StorageDto storageDto;
                        Map<String, String> fileMap = null;
                        if (CollectionUtils.isNotEmpty(personInfo)){
                            storageDto = storageClient.findById(personInfo.get(0).getParentnodeid());
                            fileMap = storageDto.getChildren().stream().collect(Collectors.toMap(StorageDto::getName, StorageDto::getId));
                        }else {
                            storageDto = storageClient.createRootFolder("clientId", xmDOList.get(0).getGzlslid(), "人脸识别", null);
                        }

                        int sxh = 2;
                        int buyzqrFlag = 0;
                        int sellzqrFlag = 0;
                        for (int i = 0; i < checkFacdResultJsonArray.size(); i++) {
                            BdcDsfRlsbVO dsfRlsbVO = checkFacdResultJsonArray.getObject(i, BdcDsfRlsbVO.class);
                            JSONObject checkFacdResultJsonObject = checkFacdResultJsonArray.getJSONObject(i);
                            if ("1".equals(checkFacdResultJsonObject.getString("role"))) {
                                bdcQlrDOList.add(initBdcQlrDO(xmid, checkFacdResultJsonObject, 1));
                                if (buyzqrFlag++ >0){
                                    return "买方主权人设置不正确";
                                }
                            } else if ("0".equals(checkFacdResultJsonObject.getString("role"))) {
                                bdcQlrDOList.add(initBdcQlrDO(xmid, checkFacdResultJsonObject, sxh++));
                            } else if ("2".equals(checkFacdResultJsonObject.getString("role"))) {
                                log.info("更新义务人信息");
                                BdcQlrDO ywrDo = BdcQlrDO.BdcQlrDOBuilder.aBdcQlrDO().withXmid(xmid).withZjh(checkFacdResultJsonObject.getString("idCode")).withQlrlx(1).withQlrlb("2").build();
                                Example updateYwrExample = new Example(BdcQlrDO.class);
                                updateYwrExample.createCriteria().andEqualTo("xmid", xmid)
                                        .andEqualTo("zjh", ywrDo.getZjh())
                                        .andEqualTo("qlrlb", ywrDo.getQlrlb())
                                        .andEqualTo("qlrlx", ywrDo.getQlrlx());
                                entityMapper.updateByExample(ywrDo, updateYwrExample);
                            }else if ( "3".equals(checkFacdResultJsonObject.getString("ROLE"))){
                                log.info("更新主义务人信息");
                                if (sellzqrFlag++ >0){
                                    BdcQlrDO ywrDo = BdcQlrDO.BdcQlrDOBuilder.aBdcQlrDO().withXmid(xmid).withZjh(checkFacdResultJsonObject.getString("idCode")).withQlrlx(1).withQlrlb("2").build();
                                    Example updateYwrExample = new Example(BdcQlrDO.class);
                                    updateYwrExample.createCriteria().andEqualTo("xmid", xmid)
                                            .andEqualTo("zjh", ywrDo.getZjh())
                                            .andEqualTo("qlrlb", ywrDo.getQlrlb())
                                            .andEqualTo("qlrlx", ywrDo.getQlrlx());
                                    entityMapper.updateByExample(ywrDo, updateYwrExample);
                                }else {
                                    return "卖方主权人设置不正确";
                                }
                            }
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
                            BdcDsfRlsbDO bdcDsfRlsbDO = faceCheckConverter.getBdcDsfRlsbDOByBdcDsfRlsbVO(dsfRlsbVO);
                            bdcDsfRlsbDO.setParentnodeid(storageDto.getId());
                            bdcDsfRlsbDO.setId(UUIDGenerator.generate());
                            entityMapper.insert(bdcDsfRlsbDO);
                        }
                        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                            int i = entityMapper.insertBatchSelective(bdcQlrDOList);
                            log.info("插入成功数量:{}", i);
                            Example queryAllXmInfo = new Example(BdcXmDO.class);
                            queryAllXmInfo.createCriteria().andEqualTo("gzlslid", xmDOList.get(0).getGzlslid());
                            List<BdcXmDO> allXmList = entityMapper.selectByExample(queryAllXmInfo);
                            if (CollectionUtils.isNotEmpty(allXmList) && allXmList.size()>1){
                                Integer qllx = allXmList.get(0).getQllx();
                                log.info("处理批量流程其他项目信息开始");
                                if(allXmList.stream().allMatch(bdcXmDO -> qllx.equals(bdcXmDO.getQllx()))){
                                    allXmList.forEach(bdcXmDO -> {
                                        if(!xmid.equals(bdcXmDO.getXmid())){
                                            log.info("删除qlr信息入参:{}", bdcXmDO.getXmid());
                                            Example deleteOtherQlrExample = new Example(BdcQlrDO.class);
                                            deleteOtherQlrExample.createCriteria().andEqualTo("xmid", bdcXmDO.getXmid()).andEqualTo("qlrlb", "1");
                                            entityMapper.deleteByExample(deleteOtherQlrExample);
                                            log.info("删除bdcDsfRlsb信息入参:{}", bdcXmDO.getXmid());
                                            Example deleteOtherBdcDsfRlsbExample = new Example(BdcDsfRlsbDO.class);
                                            deleteOtherBdcDsfRlsbExample.createCriteria().andEqualTo("ywnum", bdcXmDO.getXmid());
                                            entityMapper.deleteByExample(deleteOtherBdcDsfRlsbExample);
                                            ArrayList<BdcQlrDO> collect = bdcQlrDOList.stream().collect(ArrayList::new, (list, item) -> {
                                                item.setXmid(bdcXmDO.getXmid());
                                                list.add(item);
                                            }, ArrayList::addAll);
                                            int tempCount = entityMapper.insertBatchSelective(collect);
                                            log.info("插入成功数量:{}", tempCount);
                                        }
                                    });
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
        }else {
            return "参数为空";
        }
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
        }else if (checkFacdResultJsonObject.getString("gender").equals(CzRlsbConstansEnum.WOMAN.getDescription())) {
            bdcQlrDO.setXb(CzRlsbConstansEnum.WOMAN.getValue());
        }else if (checkFacdResultJsonObject.getString("gender").equals(CzRlsbConstansEnum.UNKNOW.getDescription())) {
            bdcQlrDO.setXb(CzRlsbConstansEnum.UNKNOW.getValue());
        }else {
            bdcQlrDO.setXb(checkFacdResultJsonObject.getInteger("gender"));
        }
        return bdcQlrDO;
    }


    private String uploadFileToFilecenter(StorageDto storageDto, String fileName, String gzlslId, MultipartFile file, boolean isUpdate, Map<String, String> fileMap) throws IOException {
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
        if(StringUtils.isEmpty(content)){
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
        if(!isJsonObject && !isJsonArray){ //不是json格式
            return false;
        }
        return true;
    }


}
