package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcGxjkPdfDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.BdcDysjPzService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDypzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.inquiry.service.BdcGxJkService;
import cn.gtmap.realestate.inquiry.util.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/2/4
 * @description
 */
@Service
public class BdcGxJkServiceImpl implements BdcGxJkService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGxJkServiceImpl.class);

    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    private UserManagerUtils userManagerUtils;

    /**
     * Redis??????
     */
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    BdcPrintFeignService bdcPrintFeignService;

    @Autowired
    BdcDypzFeignService bdcDypzFeignService;

    @Autowired
    BdcDysjPzService bdcDysjPzService;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    @Override
    public String saveGxjkDataToRedis(List<Map> dataList) {
        // ?????????Redis
        String key = CommonConstantUtils.GXJK_DATA_ + UUIDGenerator.generate();
        // ?????????
        if (CollectionUtils.isNotEmpty(dataList)) {
            LOGGER.info("???????????????{}??????????????????redis?????????{}", key, dataList);
            redisUtils.addHashValue(key, Constants.GXJK_DATA, JSON.toJSONString(dataList), 120);
        }
        return key;

    }

    @Override
    public String getPrintXmlOfGxjk(BdcGxjkPdfDTO bdcGxjkPdfDTO) {
        if (StringUtils.isBlank(bdcGxjkPdfDTO.getKey())) {
            throw new AppException("????????????????????????????????????");
        }
        //??????????????????
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx(bdcGxjkPdfDTO.getDylx());
        List<BdcDysjPzDO> bdcDysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
        if (CollectionUtils.isEmpty(bdcDysjPzDOList)) {
            throw new AppException("????????????????????????????????????,???????????????");
        }
        //????????????????????????????????????
        String json = redisUtils.getHashValue(bdcGxjkPdfDTO.getKey(), Constants.GXJK_DATA);
        LOGGER.info("??????????????????redis,key???{},????????????{}", bdcGxjkPdfDTO.getKey(), json);
        //??????????????????
        Map<String,String> parentData = new HashMap<>();
        parentData.put("gzlslid",bdcGxjkPdfDTO.getGzlslid());
        //???????????????????????????sql????????????????????????
        if(StringUtils.isNotBlank(bdcDysjPzDOList.get(0).getDysjy())) {
            Map sjldatas = bdcDysjPzService.queryPrintDatasList(parentData, "bdcInquiryConfigMapper", bdcDysjPzDOList);
            if (sjldatas != null) {
                parentData.putAll(sjldatas);
            }
        }

        //??????????????????
        Multimap<String,List> childData = ArrayListMultimap.create();
        if (StringUtils.isNotBlank(json)) {
            List<Map> dataList = JSONArray.parseArray(json,Map.class);
            if(CollectionUtils.isNotEmpty(dataList)){
                //????????????
                int zbxh =0;
                for(Map<Object,Object> zbData:dataList) {
                    zbxh++;
                    for (Map.Entry entry : zbData.entrySet()) {
                        Object entryValue = entry.getValue();
                        if (entryValue instanceof List) {
                            //??????zbxh
                            List<Map> zbList =(List) entryValue;
                            int finalZbxh = zbxh;
                            zbList.forEach(zb -> zb.put("zbxh", finalZbxh));
                            childData.put("ZB_" + entry.getKey().toString(), zbList);

                        }
                    }
                    //?????????????????????sql????????????????????????
                    BdcDysjZbPzDO bdcDysjZbPzDO = new BdcDysjZbPzDO();
                    bdcDysjZbPzDO.setDylx(bdcGxjkPdfDTO.getDylx());
                    List<BdcDysjZbPzDO> bdcDysjZbPzDOList = bdcDypzFeignService.listBdcDysjZbPz(bdcDysjZbPzDO);
                    if(CollectionUtils.isNotEmpty(bdcDysjZbPzDOList) &&StringUtils.isNotBlank(bdcDysjZbPzDOList.get(0).getDyzbsjy())) {
                        zbData.put("gzlslid",bdcGxjkPdfDTO.getGzlslid());
                        Multimap detail = bdcDysjPzService.queryPrintDetailList(zbData, "bdcInquiryConfigMapper", bdcDysjZbPzDOList);
                        if(detail != null) {
                            try {
                                Map<String, List<List<Map<String, Object>>>> map = detail.asMap();
                                if (MapUtils.isNotEmpty(map)) {
                                    map.keySet().forEach(key -> {
                                        List<List<Map<String, Object>>> list = map.get(key);
                                        if (CollectionUtils.isNotEmpty(list)) {
                                            zbData.putAll(list.get(0).get(0));
                                        }

                                    });
                                }
                            }catch (Exception e){
                                LOGGER.error("???????????????ID:{}??????PDF????????????????????????",bdcGxjkPdfDTO.getGzlslid(),e);
                            }
                        }
                    }
                }
                childData.put("ZT_"+bdcGxjkPdfDTO.getDylx(), dataList);
            }
        }
        // ????????????????????????
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>(1);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj(JSONObject.toJSONString(parentData));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(childData));
        bdcDysjDTO.setDyzd(bdcDysjPzDOList.get(0).getDyzd());
        bdcDysjDTOList.add(bdcDysjDTO);

        //????????????XML??????
        return bdcPrintFeignService.printDatas(bdcDysjDTOList);


    }

    @Override
    public String saveGxjkPdfFile(BdcGxjkPdfDTO bdcGxjkPdfDTO) {
        if (null == bdcGxjkPdfDTO || StringUtils.isAnyBlank(bdcGxjkPdfDTO.getGzlslid(), bdcGxjkPdfDTO.getPdfFilePath())) {
            throw new AppException("??????????????????????????????PDF??????????????????,??????: ?????????????????????!");
        }

        File pdfFile = new File(bdcGxjkPdfDTO.getPdfFilePath());
        if (!pdfFile.exists()) {
            throw new AppException("??????????????????????????????PDF??????????????????,??????: ?????????PDF??????!");
        }

        try {
            String pdfBase64Str = "data:application/pdf;base64," + Base64Utils.encodeByteToBase64Str(FileUtils.readFileToByteArray(pdfFile));
            return bdcUploadFileUtils.uploadBase64File(pdfBase64Str, bdcGxjkPdfDTO.getGzlslid(), "??????????????????", bdcGxjkPdfDTO.getPdffjmc(), ".pdf");
        } catch (IOException e) {
            LOGGER.error("??????????????????????????????PDF?????????????????????{}", e);
            throw new AppException("??????????????????????????????PDF??????????????????!");
        } finally {
            if (pdfFile.exists()) {
                pdfFile.delete();
                LOGGER.info("????????????????????????????????????PDF??????????????????????????????ID???{}??????????????????{}", bdcGxjkPdfDTO.getGzlslid(), bdcGxjkPdfDTO.getPdfFilePath());
            }
        }

    }

    /**
     * @param bdcGxjkPdfDTO PDF???????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ???????????????PDF??????
     */
    private void deleteExistPdfFile(BdcGxjkPdfDTO bdcGxjkPdfDTO) {
        // ???
        List<StorageDto> existPdfFiles = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID, bdcGxjkPdfDTO.getGzlslid(), "", "??????????????????", null, null);
        // ???
        if (CollectionUtils.isNotEmpty(existPdfFiles)) {
            List<StorageDto> listFile = storageClient.listAllSubsetStorages(existPdfFiles.get(0).getId(), StringUtils.EMPTY, 1, null,0,null);
            List<String> fids =new ArrayList<>();
            if(CollectionUtils.isNotEmpty(listFile)) {
                for (StorageDto storageDto : listFile) {
                    if (StringUtils.isNotBlank(storageDto.getId()) && StringUtils.contains(storageDto.getName(), bdcGxjkPdfDTO.getPdffjmc())) {
                        fids.add(storageDto.getId());
                    }
                }
            }
            if(CollectionUtils.isNotEmpty(fids)) {
                storageClient.deleteStorages(fids);
                LOGGER.info("????????????PDF???????????????????????????????????????ID???{}?????????ID ???{}", bdcGxjkPdfDTO.getGzlslid(), fids);
            }
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????pdf
     */
    private String savePdfFile(BdcGxjkPdfDTO bdcGxjkPdfDTO, String userId, StorageDto folder, MultipartFile multipartFile) throws IOException {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setName(bdcGxjkPdfDTO.getPdffjmc());
        multipartDto.setNodeId(folder.getId());
        multipartDto.setSpaceCode(bdcGxjkPdfDTO.getGzlslid());
        multipartDto.setClientId("clientId");
        multipartDto.setContentType("application/octet-stream");
        multipartDto.setData(multipartFile.getBytes());
        multipartDto.setSize(multipartFile.getSize());
        multipartDto.setOriginalFilename(bdcGxjkPdfDTO.getPdffjmc() + CommonConstantUtils.WJHZ_PDF);
        multipartDto.setOwner(userId);

        StorageDto storageDto = storageClient.multipartUpload(multipartDto);
        if (null == storageDto || StringUtils.isBlank(storageDto.getId())) {
            throw new AppException("??????????????????????????????PDF??????????????????,??????: ?????????????????????PDF??????!");
        }

        LOGGER.info("??????????????????????????????PDF??????????????????????????????ID???{}????????????????????????{}", bdcGxjkPdfDTO.getGzlslid(), JSON.toJSONString(storageDto));
        return storageDto.getId();
    }
}
