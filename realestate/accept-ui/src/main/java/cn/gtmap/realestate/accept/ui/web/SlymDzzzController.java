package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.accept.ui.utils.Constants;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSjclDzzzDzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSjclDzzzFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcDzzzVO;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ImageToPdf;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @program: realestate
 * @description: 受理页面获取电子证照信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-12-30 14:15
 **/
@Controller
@RequestMapping("/dzzz")
public class SlymDzzzController extends BaseController {

    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    BdcSjclDzzzFeignService bdcSjclDzzzFeignService;

    @Value("${dzzz.querytype:1}")
    private String querytype;
    @Value("${dzzz.userform:1}")
    private String userform;
    @Value("${dzzz.lvl:C}")
    private String lvl;
    @Value("${dzzz.maxnum:3}")
    private String maxnum;
    @Value("${dzzz.type:3}")
    private String type;
    @Value("${dzzz.isapp:false}")
    private String isapp;
    @Value("${dzzz.format:png}")
    private String format;
    @Value("${dzzz.userrange:不动产登记}")
    private String userrange;
    @Value("${dzzz.downsize:500}")
    private Integer downsize;
    @Value("${print.path:}")
    private String printPath;


    @ResponseBody
    @GetMapping("/dzgx")
    public int queryDzgx() {
        BdcSjclDzzzDzDO bdcSjclDzzzDzDO = new BdcSjclDzzzDzDO();
        List<BdcSjclDzzzDzDO> bdcSjclDzzzDzDOList = bdcSjclDzzzFeignService.querySjclDzzzDz(bdcSjclDzzzDzDO);
        return bdcSjclDzzzDzDOList.size();
    }

    @ResponseBody
    @GetMapping("/dzgxxz")
    public int downloadDzgx() {
        int count = 0;
        String beanName = "dzzz_cllx";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageSize", downsize);
        paramMap.put("currentPageNo", 1);
        paramMap.put("queryType", querytype);
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
        LOGGER.info("电子证照code名称对照值:{}", JSONObject.toJSONString(response));
        DzzzDTO dzzzDTO = JSONObject.parseObject(JSONObject.toJSONString(response), DzzzDTO.class);
        if (dzzzDTO != null) {
            DzzzCllxPageDTO dzzzCllxPageDTO = dzzzDTO.getData();
            if (dzzzCllxPageDTO != null) {
                List<DzzzCllxDTO> dzzzCllxDTOList = dzzzCllxPageDTO.getResult();
                if (CollectionUtils.isNotEmpty(dzzzCllxDTOList)) {
                    //根据收件材料名称去对照表获取数据，若不存在则新增，若已存在则更新
                    BdcSjclDzzzDzDO bdcSjclDzzzDzQO = new BdcSjclDzzzDzDO();
                    List<BdcSjclDzzzDzDO> bdcSjclDzzzDzDOList = bdcSjclDzzzFeignService.querySjclDzzzDz(bdcSjclDzzzDzQO);
                    //表中数据为空把获取到的数据全部存进去
                    if (CollectionUtils.isEmpty(bdcSjclDzzzDzDOList)) {
                        for (DzzzCllxDTO dzzzCllxDTO : dzzzCllxDTOList) {
                            BdcSjclDzzzDzDO bdcSjclDzzzDzDO = new BdcSjclDzzzDzDO();
                            bdcSjclDzzzDzDO.setDzzzdm(dzzzCllxDTO.getTypeCode());
                            bdcSjclDzzzDzDO.setDzzzmc(dzzzCllxDTO.getTypeName());
                            count += bdcSjclDzzzFeignService.saveBdcSjclDzzz(bdcSjclDzzzDzDO);
                        }
                    }
                }
            }
        }
        return count;
    }

    @ResponseBody
    @GetMapping("/listCljyxxByPage")
    public Object listCljyxxByPageJson(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("电子证照调用获取收件材料参数为空");
        }
        List<Object> jsonList = new ArrayList<>();
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(gzlslid);
        List<String> dzzzDmList = new ArrayList<>(bdcSlSjclDOList.size());
        if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
            for (BdcSlSjclDO bdcSlSjclDO : bdcSlSjclDOList) {
                BdcSjclDzzzDzDO bdcSjclDzzzDzDO = new BdcSjclDzzzDzDO();
                bdcSjclDzzzDzDO.setClmc(bdcSlSjclDO.getClmc());
                List<BdcSjclDzzzDzDO> bdcSjclDzzzDzDOList = bdcSjclDzzzFeignService.querySjclDzzzDz(bdcSjclDzzzDzDO);
                if (CollectionUtils.isNotEmpty(bdcSjclDzzzDzDOList)) {
                    dzzzDmList.add(bdcSjclDzzzDzDOList.get(0).getDzzzdm());
                }
            }
        }
        String code = StringUtils.join(dzzzDmList, ",");
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        Set<BdcQlrDO> allQlrSet = new TreeSet<>(Comparator.comparing(BdcQlrDO::getZjh));
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            //根据登记小类去重获取项目的数量
            Set<BdcXmDO> newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getDjxl));
            newBdcXm.addAll(bdcXmDOList);
            for (BdcXmDO bdcXmDO : newBdcXm) {
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                bdcQlrQO.setXmid(bdcXmDO.getXmid());
                List<BdcQlrDO> bdcQlrList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if (CollectionUtils.isNotEmpty(bdcQlrList)) {
                    bdcQlrDOList.addAll(bdcQlrList);
                }
            }
            //获取所有的义务人；
            List<BdcQlrDO> bdcYwrList = bdcQlrFeignService.listAllBdcQlr(gzlslid, CommonConstantUtils.QLRLB_YWR, "");
            //获取到证件号不为空的义务人
            bdcYwrList = bdcYwrList.stream().filter(bdcQlrDO -> StringUtils.isNotBlank(bdcQlrDO.getZjh())).collect(Collectors.toList());
            //所有的义务人根据证件号去重
            Set<BdcQlrDO> ywrSet = new TreeSet<>(Comparator.comparing(BdcQlrDO::getZjh));
            ywrSet.addAll(bdcYwrList);
            bdcQlrDOList.addAll(ywrSet);
        }
        //组合流程转移权利人是抵押义务人，根据证件号去重
        allQlrSet.addAll(bdcQlrDOList);
        if (CollectionUtils.isNotEmpty(allQlrSet)) {
            List<BdcQlrDO> qlrList = new ArrayList<>(allQlrSet);
            Map<String, Object> jyxxMap = new HashMap<>();
            String typeCode = StringUtils.isNotBlank(code) ? code : "";
            //获取电子证照信息按照权利人一个一个获取，typecode可以拼接
            for (BdcQlrDO bdcQlrDO : qlrList) {
                jyxxMap.put("typeCode", typeCode);
                jyxxMap.put("ownerId", bdcQlrDO.getZjh());
                jyxxMap.put("ownerName", bdcQlrDO.getQlrmc());
                jyxxMap.put("useForm", userform);
                jyxxMap.put("lvl", lvl);
                LOGGER.info("获取材料信息的权利人参数:{}", JSONObject.toJSONString(jyxxMap, SerializerFeature.WRITE_MAP_NULL_FEATURES));
                Object response = exchangeInterfaceFeignService.requestInterface("dzzz_cljyxx", jyxxMap);
                DzzzJyclxxResponseDTO dzzzJyclxxResponseDTO = JSONObject.parseObject(JSONObject.toJSONString(response), DzzzJyclxxResponseDTO.class);
                LOGGER.info("材料简要信息:{}", JSONObject.toJSONString(JSONObject.toJSONString(response)));
                if (dzzzJyclxxResponseDTO != null && CollectionUtils.isNotEmpty(dzzzJyclxxResponseDTO.getData())) {
                    dzzzJyclxxResponseDTO.getData().forEach(dzzzJyclxxDTO -> {
                        dzzzJyclxxDTO.setBdcQlrDO(bdcQlrDO);
                    });
                    jsonList.add(JSON.toJSON(dzzzJyclxxResponseDTO.getData()));
                }
            }
        }
        return jsonList;
    }

    @ResponseBody
    @PostMapping("/download")
    public Integer downloadDzzz(@RequestBody BdcDzzzVO bdcDzzzVO) throws IOException {
        if (bdcDzzzVO == null) {
            throw new AppException("获取电子证照材料信息缺失必要参数");
        }
        Integer count = 0;
        UserDto userDto = userManagerUtils.getCurrentUser();
        Map<String, Object> clxxMap = new HashMap<>();
        clxxMap.put("personName", userDto.getAlias());
        clxxMap.put("personPhone", userDto.getMobile());
        clxxMap.put("typeCode", bdcDzzzVO.getTypecode());
        clxxMap.put("userRange", userrange);
        clxxMap.put("useForm", userform);
        clxxMap.put("maxNum", maxnum);
        clxxMap.put("lvl", lvl);
        clxxMap.put("token",bdcDzzzVO.getToken());
        LOGGER.info("获取证照材料信息参数:{}",JSONObject.toJSONString(clxxMap, SerializerFeature.WRITE_MAP_NULL_FEATURES));
        Object clxxResponse = exchangeInterfaceFeignService.requestInterface("dzzz_zzclxx", clxxMap);
        LOGGER.info("typecode获取材料code:{},获取到的材料信息数据:{}", bdcDzzzVO.getTypecode(), JSONObject.toJSONString(clxxResponse));
        DzzzClxxResponseDTO dzzzClxxResponseDTO = JSONObject.parseObject(JSONObject.toJSONString(clxxResponse), DzzzClxxResponseDTO.class);
        List<DzzzClxxDTO> dzzzClxxDTOList = dzzzClxxResponseDTO.getData();
        //根据id获取base64文件上传
        if (CollectionUtils.isNotEmpty(dzzzClxxDTOList)) {
            for (DzzzClxxDTO dzzzClxxDTO : dzzzClxxDTOList) {
                Map<String, Object> clxzMap = new HashMap<>();
                clxzMap.put("id", dzzzClxxDTO.getId());
                clxzMap.put("format", format);
                clxzMap.put("areaCode", dzzzClxxDTO.getAreaCode());
                clxzMap.put("type", type);
                clxzMap.put("token",bdcDzzzVO.getToken());
                LOGGER.info("材料下载参数:{}", JSONObject.toJSONString(clxzMap));
                Object clxzResponse = exchangeInterfaceFeignService.requestInterface("dzzz_clxz", clxzMap);
                ClxzResponseDTO clxzResponseDTO = JSONObject.parseObject(JSONObject.toJSONString(clxzResponse), ClxzResponseDTO.class);
                LOGGER.info("材料下载code:{},材料下载信息:{}", bdcDzzzVO.getTypecode(), JSONObject.toJSONString(clxzResponse));
                if (clxzResponseDTO != null) {
                    List<ClxzDTO> clxzDTOList = clxzResponseDTO.getData();
                    if (CollectionUtils.isNotEmpty(clxzDTOList)) {
                        for (ClxzDTO clxzDTO : clxzDTOList) {
                            LOGGER.info("材料下载文件信息:{}", JSONObject.toJSONString(clxzDTO));
                            String baseCode = "data:image/" + clxzDTO.getFormat() + ";base64,";
                            MultipartFile file = Base64Utils.base64ToMultipart(baseCode + clxzDTO.getDataStr());
                            if (file != null) {
                                //name根据typecode对照获取到名称
                                BdcSjclDzzzDzDO bdcSjclDzzzDzDO = new BdcSjclDzzzDzDO();
                                bdcSjclDzzzDzDO.setDzzzdm(bdcDzzzVO.getTypecode());
                                List<BdcSjclDzzzDzDO> bdcSjclDzzzDzDOList = bdcSjclDzzzFeignService.querySjclDzzzDz(bdcSjclDzzzDzDO);
                                if (CollectionUtils.isNotEmpty(bdcSjclDzzzDzDOList) && StringUtils.isNotBlank(bdcSjclDzzzDzDOList.get(0).getClmc())) {
                                    StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, bdcDzzzVO.getGzlslid(), bdcSjclDzzzDzDOList.get(0).getClmc(), userDto != null ? userDto.getId() : "");
                                    MultipartDto multipartDto = new MultipartDto();
                                    multipartDto.setClientId(Constants.WJZX_CLIENTID);
                                    multipartDto.setName(clxzDTO.getFileName());
                                    multipartDto.setNodeId(storageDto.getId());
                                    multipartDto.setData(file.getBytes());
                                    multipartDto.setContentType(file.getContentType());
                                    multipartDto.setSize(file.getSize());
                                    multipartDto.setOriginalFilename(clxzDTO.getFileName() + "." + clxzDTO.getFormat());
                                    StorageDto dto = storageClient.multipartUpload(multipartDto);
                                    count++;
                                    LOGGER.info("文件信息:{}", JSONObject.toJSONString(dto));
                                }
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    @ResponseBody
    @GetMapping("/clmc")
    public BdcSjclDzzzDzDO querySjclDzzz(String typecode) {
        BdcSjclDzzzDzDO bdcSjclDzzzDzDO = new BdcSjclDzzzDzDO();
        bdcSjclDzzzDzDO.setDzzzdm(typecode);
        List<BdcSjclDzzzDzDO> bdcSjclDzzzDzDOList = bdcSjclDzzzFeignService.querySjclDzzzDz(bdcSjclDzzzDzDO);
        if (CollectionUtils.isNotEmpty(bdcSjclDzzzDzDOList)) {
            return bdcSjclDzzzDzDOList.get(0);
        }
        return new BdcSjclDzzzDzDO();
    }

    @ResponseBody
    @PostMapping("/token")
    public Object queryDzzzToken(@RequestBody BdcDzzzVO bdcDzzzVO) {
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("applyId", bdcDzzzVO.getQlrzjh());
        tokenMap.put("applyName", bdcDzzzVO.getQlrmc());
        tokenMap.put("applyPhone", bdcDzzzVO.getLxdh());
        tokenMap.put("isApp", isapp);
        Object tokenResponse = exchangeInterfaceFeignService.requestInterface("dzzz_token", tokenMap);
        DzzzTokenResponseDTO dzzzTokenResponseDTO = JSONObject.parseObject(JSONObject.toJSONString(tokenResponse), DzzzTokenResponseDTO.class);
        LOGGER.info("电子证照token信息:{}", JSONObject.toJSONString(tokenResponse));
        if (dzzzTokenResponseDTO != null && StringUtils.equals(dzzzTokenResponseDTO.getFlag(), "200")) {
            return dzzzTokenResponseDTO.getData();
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/ewmpdf")
    public String queryEwmPdfPath(@RequestBody BdcDzzzVO bdcDzzzVO, HttpServletRequest httpServletRequest) {
        String xmlStr = printPath + "temp\\" + "dzzzewm.jpg";
//      获取到的二维码图片先转为图片
        Base64Utils.decodeBase64StrToFile(bdcDzzzVO.getEwmnr(), xmlStr);
        //图片转pdf
        ImageToPdf.imgOfPdf(xmlStr, httpServletRequest);
        //返回pdf路径
        return xmlStr.substring(0, xmlStr.lastIndexOf(".")) + ".pdf";
    }

    /**
     * 获取电子证照信息，并下载电子证照文件上传至大云文档中心
     * @param bdcDzzzVO  电子证照VO对象
     */
    @ResponseBody
    @PostMapping("/getDzzzxx")
    public void getDzzzxx(@RequestBody BdcDzzzVO bdcDzzzVO){
        if(StringUtils.isAnyBlank(bdcDzzzVO.getTypecode(),bdcDzzzVO.getQlrzjh(),bdcDzzzVO.getGzlslid())){
            throw new MissingArgumentException("缺少电子证照代码或证件号信息。");
        }
        Map<String,String> paramMap = new HashMap(4);
        paramMap.put("styleCode", bdcDzzzVO.getTypecode());
        paramMap.put("ownerId", bdcDzzzVO.getQlrzjh());
        paramMap.put("format", format);
        LOGGER.info("调用电子证照查询接口，请求参数为:{}", paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface("dzzz_zzcx", paramMap);
        String responseStr = JSONObject.toJSONString(response);
        LOGGER.info("电子证照查询接口，返回参数:{}", responseStr);
        DzzzZzxxResponseDTO dzzzZzxxResponseDTO = JSON.parseObject(responseStr, DzzzZzxxResponseDTO.class);
        if(!"200".equals(dzzzZzxxResponseDTO.getFlag())){
            throw new AppException("查询电子证照信息失败," + dzzzZzxxResponseDTO.getResult());
        }
        // 下载文件并上传文件至大云平台
        downloadDzzz(dzzzZzxxResponseDTO, bdcDzzzVO);
    }

    /**
     * 下载文件并上传文件至大云平台
     */
    private void downloadDzzz(DzzzZzxxResponseDTO dzzzZzxxResponseDTO, BdcDzzzVO bdcDzzzVO) {
        List<DzzzZzxxDTO> dzzzZzxxDTOList = dzzzZzxxResponseDTO.getData();
        if(CollectionUtils.isEmpty(dzzzZzxxDTOList)){
            throw new AppException("未获取到电子证照信息。");
        }
        // 获取文件名称与材料名称
        BdcSjclDzzzDzDO queryParam = new BdcSjclDzzzDzDO();
        queryParam.setDzzzdm(bdcDzzzVO.getTypecode());
        List<BdcSjclDzzzDzDO> bdcSjclDzzzDzDOList = bdcSjclDzzzFeignService.querySjclDzzzDz(queryParam);
        if(CollectionUtils.isEmpty(bdcSjclDzzzDzDOList)){
            throw new AppException("未获取到收件材料与电子证照对照信息。");
        }
        // 获取对应材料名称，所在文件中心的nodeId
        BdcSjclDzzzDzDO bdcSjclDzzzDzDO = bdcSjclDzzzDzDOList.get(0);
        List<BdcSlSjclDO> sjclList = bdcSlSjclFeignService.listBdcSlSjclByClmc(bdcDzzzVO.getGzlslid(),bdcSjclDzzzDzDO.getClmc());
        if(CollectionUtils.isNotEmpty(sjclList) && StringUtils.isNotBlank(sjclList.get(0).getWjzxid())){
            // 下载证照内容并同步上传至文件中心
            String currentUserName = userManagerUtils.getCurrentUserName();
            String nodeId = sjclList.get(0).getWjzxid();
            for(DzzzZzxxDTO dzzzZzxxDTO : dzzzZzxxDTOList){
                String dzzzUrl = dzzzZzxxDTO.getDownloadUrl();
                try {
                    MultipartFile file = Base64Utils.createFileItem(new URL(dzzzUrl), format);
                    MultipartDto multipartDto = getUploadParamDto(currentUserName, nodeId, file, dzzzZzxxDTO.getStyleName());
                    StorageDto storageDto = storageClient.multipartUpload(multipartDto);
                    LOGGER.info("电子证照上传大云成功，文件名:{},storageid:{}", dzzzZzxxDTO.getStyleName(), storageDto.getId());
                } catch (Exception e) {
                    LOGGER.error("电子证照上传大云失败，文件名： {} ，异常信息：{}", dzzzZzxxDTO.getStyleName(), e);
                }
            }
        }
    }

    /**
     * 组成文件中心上传所需的multipartDto对象
     */
    private MultipartDto getUploadParamDto(String currentUserName, String nodeId, MultipartFile file, String fileName) throws IOException {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(nodeId);
        multipartDto.setClientId(Constants.WJZX_CLIENTID);
        multipartDto.setData(file.getBytes());
        multipartDto.setOwner(currentUserName);
        multipartDto.setContentType(file.getContentType());
        multipartDto.setSize(file.getSize());
        multipartDto.setOriginalFilename(fileName + "." + format);
        multipartDto.setName(fileName);
        return multipartDto;
    }

}
