package cn.gtmap.realestate.inquiry.ui.web.rest.shucheng;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.idcheck.PoliceCheckIdRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.shucheng.BdcGxywFjDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.shucheng.BdcGxywRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.shucheng.BdcGxywResponseDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.ExchangeInterfaceRestService;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.inquiry.ui.core.qo.JgysFjxxQO;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.internal.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;


/**
 * @author <a href="mailto:zhongjinpe@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/6 09:40
 * @description ????????????????????????
 */
@RestController
@RequestMapping(value = "/rest/v1.0/gx/shucheng")
public class BdcShuchengGxController extends BaseController {


    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    ExchangeInterfaceRestService exchangeInterfaceRestService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    @Value("${html.version:}")
    private String version;

    @Value("${jgysbafjxx.clmc:????????????????????????}")
    private String jgysbafjxxClmc;
    @Value("${jsghxkz.clmc:?????????????????????????????????}")
    private String jsghxkzClmc;
    @Value("${jsghxkz.accessToken:059DDBCA50844E31B86A4A583CE45408}")
    private String accessToken;
    @Value("${jgysbafjxx.url:http://127.0.0.1:8080}")
    private String jgysbafjxxUrl;
    @Value("${gzaj.jgbh:00323875-1}")
    private String jgbh;

    private static final String MLPZ_PREFIX = "gxjkml.";

    /**
     * ????????????????????????????????????,?????????????????????
     */
    private static final String  WJJ_NBGXCL= "??????????????????";

    /**
     * ??????????????????????????????????????????
     *
     * @param
     * @return object
     * @Date 2022/3/2
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/queryJgys/list")
    public Object queryJgysFjxxList(@LayuiPageable Pageable pageable, JgysFjxxQO info) {
        info.setRows(pageable.getPageSize());
        info.setPage(pageable.getPageNumber() + 1);
        if (null == info.getPage() || null == info.getRows()) {
            throw new MissingArgumentException("???????????????????????????");
        }
        List data = new ArrayList<>();
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("jgysbaFjxx", info);
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("---??????????????????????????????????????????????????????:{}", jsonObject);
            if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonObject.getString("code"))) {
                String reponseData = jsonObject.getString("data");
                JSONObject jsonResponse = JSON.parseObject(reponseData);
                if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonResponse.getString("state"))) {
                    LOGGER.info("---????????????????????????????????????????????????????????????!");
                    JSONArray jgysFjxxArr = jsonResponse.getJSONArray("data");
                    if (CollectionUtils.isNotEmpty(jgysFjxxArr)) {
                        for (int i = 0; i < jgysFjxxArr.size(); i++) {
                            data.add(JSON.parse(jgysFjxxArr.getString(i)));
                        }

                    }
                }
                Pageable pageable1 = new PageRequest(jsonResponse.getInteger("page"), jsonResponse.getInteger("rows"), null);
                Page page = PageUtils.listToPageWithTotal(data, pageable1, jsonResponse.getInteger("total"));
                return addLayUiCode(page);
            }
        }
        return data;
    }

    /**
     * ??????????????????????????????
     *
     * @param
     * @return object
     * @Date 2022/3/2
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/queryGhxk/list")
    public Object queryGhxkList(@RequestParam("queryMap") String queryMap) {
        if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(queryMap)) {
            throw new MissingArgumentException("?????????????????????");
        }
        List data = new ArrayList<>();
        Map hashMap = new HashMap<>();
        hashMap.put("accessToken", accessToken);
        hashMap.put("queryMap", queryMap);

        Object object = exchangeInterfaceFeignService.sjptRequestInterface("ghxkzList", hashMap);
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("---????????????????????????????????????????????????:{}", jsonObject);
            if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonObject.getString("code"))) {
                String reponseData = jsonObject.getString("data");
                JSONObject jsonResponse = JSON.parseObject(reponseData);
                if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonResponse.getString("statusCode"))) {
                    LOGGER.info("---??????????????????????????????????????????????????????!");
                    JSONArray jsonArray = jsonResponse.getJSONArray("licenseArray");
                    if (CollectionUtils.isNotEmpty(jsonArray)) {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            data.add(JSON.parse(jsonArray.getString(i)));
                        }
                    }
                }
                Pageable pageable = new PageRequest(0, data.size(), null);
                Page page = PageUtils.listToPageWithTotal(data, pageable, data.size());
                return addLayUiCode(page);
            }

        }
        return data;
    }

    /**
     * ??????????????????base64
     *
     * @param licenseNo
     * @return
     */
    @PostMapping("/getDzzz/base64")
    public String getDzzzBase64(@RequestParam(value = "licenseNo", required = false) String licenseNo) {
        Map hashMap = new HashMap<>();
        hashMap.put("accessToken", accessToken);
        hashMap.put("licenseNo", licenseNo);
        String base64 = "";
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("ghxkzFile", hashMap);
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            String reponseData = jsonObject.getString("data");
            JSONObject jsonResponse = JSON.parseObject(reponseData);
            base64 = jsonResponse.getString("base64");
        }
        return base64;
    }


    /**
     * ??????????????????????????????????????????
     *
     * @param
     * @return object
     * @Date 2022/3/2
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/Savefjxx")
    public BdcCommonResponse saveJgysFjxx(@RequestParam("gzlslid") String gzlslid, @RequestParam(value = "downUrl", required = false) String downUrl,
                                          @RequestParam(value = "licenseNo", required = false) String licenseNo) throws IOException {
        if (StringUtils.isBlank(gzlslid)) {
            return BdcCommonResponse.fail("?????????????????????id???");
        }

        if (StringUtils.isNotBlank(downUrl)) {
            BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "", StringUtils.trim(jgysbafjxxClmc), StringUtils.trim(jgysbafjxxClmc), ".doc");
            bdcPdfDTO.setPdfUrl(downUrl);
            bdcUploadFileUtils.uploadPdfByUrl(bdcPdfDTO);

        } else if (StringUtils.isNotBlank(licenseNo)) {
            String base64 = "";
            //??????????????????????????????
            Map hashMap = new HashMap<>();
            hashMap.put("accessToken", accessToken);
            hashMap.put("licenseNo", licenseNo);

            Object object = exchangeInterfaceFeignService.sjptRequestInterface("ghxkzFile", hashMap);
            if (null != object) {
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
                LOGGER.info("---????????????????????????????????????????????????:{}", jsonObject);
                String reponseData = jsonObject.getString("data");
                JSONObject jsonResponse = JSON.parseObject(reponseData);
                base64 = jsonResponse.getString("base64");
            }

            // ??????base64?????????????????????????????????????????????pdf???base64?????????
            if (StringUtils.isNotBlank(base64) && !base64.startsWith("data:")) {
                base64 = "data:image/ofd;base64," + base64;
                bdcUploadFileUtils.uploadBase64File(base64, gzlslid, jsghxkzClmc, "", ".ofd");
            }
        }
        return BdcCommonResponse.ok("???????????????");
    }

    /**
     * ????????????
     */
    @ResponseBody
    @GetMapping("/download/file")
    public void downloadFile(@RequestParam String downUrl, HttpServletResponse response) throws Exception {
        if (StringUtils.isBlank(downUrl)) {
            throw new AppException(ErrorCode.MISSING_ARG, "??????????????????????????????");
        }
        try {
            LOGGER.info("?????????????????? url:{}", downUrl);
            InputStream is = httpClientService.doGetReturnStream(downUrl);
            if (is != null) {
                //???????????????
                String fileName = URLEncoder.encode(jgysbafjxxClmc + ".doc", "utf-8");
                response.setContentType("application/msword");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.getOutputStream().flush();
                IOUtils.copy(is, response.getOutputStream());
                IOUtils.closeQuietly(is);
                IOUtils.closeQuietly(response.getOutputStream());
            } else {
                LOGGER.error("???????????????????????????");
            }
        } catch (Exception e) {
            LOGGER.error("?????????????????????????????????{}", e.getMessage(), e);
        }
    }


    /**
     * ??????-??????????????????
     *
     * @param param
     * @return
     */
    @PostMapping("/bjjk/sycx")
    public Object sycx(@RequestBody JSONObject param) {
        if (Objects.isNull(param)) {
            throw new MissingArgumentException("?????????????????????");
        }
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("bjjk_sycx", param);
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("---??????????????????????????????:{}", jsonObject);
            if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonObject.getString("code"))) {
                String reponseData = jsonObject.getString("data");
                return JSON.parseArray(reponseData);
            }
        }
        return null;
    }

    /**
     * ??????-????????????
     *
     * @param param
     * @return
     */
    @PostMapping("/bjjk/swzm")
    public Object swzm(@RequestBody JSONObject param) {
        if (Objects.isNull(param)) {
            throw new MissingArgumentException("?????????????????????");
        }
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("bjjk_swzmcx", param);
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("---??????????????????????????????:{}", jsonObject);
            if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonObject.getString("code"))) {
                String reponseData = jsonObject.getString("data");
                return JSON.parseArray(reponseData);
            }
        }
        return null;
    }

    /**
     * ??????-2.1 ????????????
     *
     * @return
     */
    @PostMapping("/bjjk/jgbh")
    public String jgbh() {
        return jgbh;
    }

    /**
     * ??????-2.1 ??????????????????????????????
     *
     * @param param
     * @return
     */
    @PostMapping("/bjjk/gzajjbxx")
    public Object gzajCx(@RequestBody JSONObject param) {

        if (Objects.isNull(param)) {
            throw new MissingArgumentException("?????????????????????");
        }
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("bjjk_gzajgzjbxx", param);
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("---??????????????????????????????:{}", jsonObject);
            if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonObject.getString("code"))) {
                String reponseData = jsonObject.getString("data");
                return JSON.parseObject(reponseData);
            }
        }
        return null;
    }

    /**
     * ??????- 2.2 ???????????????????????????
     *
     * @param param
     * @return
     */
    @PostMapping("/bjjk/gzajsxx")
    public Object gzajsCx(@RequestBody JSONObject param) {

        if (Objects.isNull(param)) {
            throw new MissingArgumentException("?????????????????????");
        }
        String gzlslid = param.getString("gzlslid");
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("?????????????????????");
        }
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("bjjk_gzajgzsxx", param);
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("---???????????????????????????:{}", jsonObject);
            if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonObject.getString("code"))) {
                JSONObject data = jsonObject.getJSONObject("data");
                if (CommonConstantUtils.RESPONSE_ZTM_SUCCESS.equals(data.getString("ztm"))) {
                    String fileContent = data.getString("file");
                    LOGGER.info("????????????id?????????????????????{}", fileContent);
                    if (StringUtils.isNotBlank(fileContent)) {
                        uploadFile(gzlslid, fileContent, "???????????????");
                    }
                }
                return JSON.parseObject(jsonObject.getString("data"));
            }
        }
        return null;
    }

    /**
     * ??????-2.3 ??????????????????????????????
     *
     * @param param
     * @return
     */
    @PostMapping("/bjjk/gzajqtxx")
    public Object gzajqtxxCx(@RequestBody JSONObject param) {

        if (Objects.isNull(param)) {
            throw new MissingArgumentException("?????????????????????");
        }
        String gzlslid = param.getString("gzlslid");
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("?????????????????????");
        }
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("bjjk_gzajqtxx", param);
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("---??????????????????????????????:{}", jsonObject);
            if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonObject.getString("code"))) {
                JSONObject data = jsonObject.getJSONObject("data");
                if (CommonConstantUtils.RESPONSE_ZTM_SUCCESS.equals(data.getString("ztm"))) {
                    String fileContent = data.getString("file");
                    LOGGER.info("????????????id?????????????????????{}", fileContent);
                    if (StringUtils.isNotBlank(fileContent)) {
                        uploadFile(gzlslid, fileContent, "????????????????????????????????????");
                    }
                }
                return JSON.parseObject(jsonObject.getString("data"));
            }
        }
        return null;
    }

    /**
     * ??????- ??????????????????????????????
     *
     * @param requestDTO
     * @return
     */
    @PostMapping("/querygxyw")
    public Object queryGxyw(@RequestBody BdcGxywRequestDTO requestDTO) {
        if (Objects.isNull(requestDTO)) {
            throw new MissingArgumentException("?????????????????????");
        }
        if (StringUtils.isBlank(requestDTO.getBdcdyh()) && StringUtils.isBlank(requestDTO.getGxywh()) && StringUtils.isBlank(requestDTO.getQlrxm())) {
            throw new MissingArgumentException("???????????????????????????????????????????????????????????????????????????????????????");
        }
        LOGGER.info("??????-??????????????????????????????,??????????????????{}", JSON.toJSONString(requestDTO));
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("queryGxyw", JSON.toJSONString(requestDTO));
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("??????-??????????????????????????????,???????????????:{}", jsonObject);
            if ("1".equals(jsonObject.getString("code"))) {
                JSONArray data = jsonObject.getJSONArray("data");
                List<BdcGxywResponseDTO> list = JSON.parseArray(data.toJSONString(), BdcGxywResponseDTO.class);
                return list;
            }
        }
        return null;
    }


    /**
     * ??????-????????????????????????????????????,?????????????????????
     *
     * @param gxywh   ???????????????
     * @param gzlslid ???????????????id
     * @return
     */
    @GetMapping("/gxfjxz")
    public Object gxfjxz(@RequestParam(value = "gxywh") String gxywh, @RequestParam(value = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gxywh) && StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("?????????????????????????????????id?????????!");
        }
        LOGGER.info("??????-????????????????????????????????????,???????????????gxywh???{},gzlslid:{}", gxywh, gzlslid);
        Map<String, String> map = new HashMap<>();
        map.put("gxywh", gxywh);
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("downGxxtFjxx", map);
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("??????-????????????????????????????????????,???????????????:{}", jsonObject);
            if ("1".equals(jsonObject.getString("code"))) {
                // ??????????????????sply???spxtywh
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(gzlslid);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isEmpty(bdcXmDOList)) {
                    throw new AppException("??????????????????????????????????????????id:" + gzlslid);
                }
                bdcXmDOList.forEach(bdcXmDO -> {
                    bdcXmDO.setSply(CommonConstantUtils.SPLY_GXXT);
                    bdcXmDO.setSpxtywh(gxywh);
                    bdcXmFeignService.updateBdcXm(bdcXmDO);
                });
                // ?????????????????????
                JSONObject data = jsonObject.getJSONObject("data");
                List<BdcGxywFjDTO> fjDTOS = JSON.parseArray(data.getString("file"), BdcGxywFjDTO.class);
                List<BdcPdfDTO> pdfDTOS = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(fjDTOS)) {
                    for (BdcGxywFjDTO fjDTO : fjDTOS) {
                        String fileName = fjDTO.getFileName();
                        String fileSuffix = fileName.substring(fileName.lastIndexOf(CommonConstantUtils.ZF_YW_JH));
                        String pdffjmc = fileName.substring(0, fileName.lastIndexOf(CommonConstantUtils.ZF_YW_JH));
                        BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "", pdffjmc, WJJ_NBGXCL, fileSuffix);
                        bdcPdfDTO.setPdfUrl(fjDTO.getFileDownloadPath());
                        pdfDTOS.add(bdcPdfDTO);
                    }
                }
                if (CollectionUtils.isNotEmpty(pdfDTOS)) {
                    for (BdcPdfDTO pdfDTO : pdfDTOS) {
                        try {
                            bdcUploadFileUtils.uploadPdfByUrl(pdfDTO);
                        } catch (Exception e) {
                            LOGGER.error("???????????????????????????????????????????????????BdcPdfDTO:{} ??????????????????{}", pdfDTO.toString(), e);
                        }
                    }

                }
                return JSON.parseObject(jsonObject.getString("data"));
            }
        }
        return null;
    }

    private void uploadFile(String gzlslid, String fileContent, String fileName) {
        try {
            //???????????????
            StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, fileName, "");
            fileContent = "data:application/pdf;base64," + fileContent;
            LOGGER.info("????????????id?????????????????????{}", fileContent);
            //?????????????????????
            MultipartFile file = Base64Utils.base64ToMultipart(fileContent);
            MultipartDto multipartDto = new MultipartDto();
            multipartDto.setClientId(CommonConstantUtils.WJZX_CLIENTID);
            multipartDto.setName(fileName + ".pdf");
            multipartDto.setNodeId(storageDto.getId());
            multipartDto.setSpaceCode(gzlslid);
            multipartDto.setData(file.getBytes());
            multipartDto.setContentType(file.getContentType());
            multipartDto.setSize(file.getSize());
            multipartDto.setOriginalFilename(fileName + ".pdf");
            storageClient.multipartUpload(multipartDto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MissingArgumentException("?????????????????????");
        }
    }
}
