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
 * @description 舒城共享接口请求
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

    @Value("${jgysbafjxx.clmc:房屋已竣工的材料}")
    private String jgysbafjxxClmc;
    @Value("${jsghxkz.clmc:建设工程符合规划的材料}")
    private String jsghxkzClmc;
    @Value("${jsghxkz.accessToken:059DDBCA50844E31B86A4A583CE45408}")
    private String accessToken;
    @Value("${jgysbafjxx.url:http://127.0.0.1:8080}")
    private String jgysbafjxxUrl;
    @Value("${gzaj.jgbh:00323875-1}")
    private String jgbh;

    private static final String MLPZ_PREFIX = "gxjkml.";

    /**
     * 共享业务系统获取附件接口,上传文件夹名称
     */
    private static final String  WJJ_NBGXCL= "内部共享材料";

    /**
     * 获取竣工验收备案附件信息列表
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
            throw new MissingArgumentException("分页参数不可为空！");
        }
        List data = new ArrayList<>();
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("jgysbaFjxx", info);
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("---获取竣工验收备案附件信息列表查询接口:{}", jsonObject);
            if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonObject.getString("code"))) {
                String reponseData = jsonObject.getString("data");
                JSONObject jsonResponse = JSON.parseObject(reponseData);
                if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonResponse.getString("state"))) {
                    LOGGER.info("---获取竣工验收备案附件信息列表查询接口成功!");
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
     * 规划许可电子证照列表
     *
     * @param
     * @return object
     * @Date 2022/3/2
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/queryGhxk/list")
    public Object queryGhxkList(@RequestParam("queryMap") String queryMap) {
        if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(queryMap)) {
            throw new MissingArgumentException("参数不可为空！");
        }
        List data = new ArrayList<>();
        Map hashMap = new HashMap<>();
        hashMap.put("accessToken", accessToken);
        hashMap.put("queryMap", queryMap);

        Object object = exchangeInterfaceFeignService.sjptRequestInterface("ghxkzList", hashMap);
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("---获取规划许可电子证照列表查询接口:{}", jsonObject);
            if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonObject.getString("code"))) {
                String reponseData = jsonObject.getString("data");
                JSONObject jsonResponse = JSON.parseObject(reponseData);
                if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonResponse.getString("statusCode"))) {
                    LOGGER.info("---获取规划许可电子证照列表查询接口成功!");
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
     * 获取电子证照base64
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
     * 获取竣工验收备案附件信息列表
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
            return BdcCommonResponse.fail("缺失工作流实例id！");
        }

        if (StringUtils.isNotBlank(downUrl)) {
            BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "", StringUtils.trim(jgysbafjxxClmc), StringUtils.trim(jgysbafjxxClmc), ".doc");
            bdcPdfDTO.setPdfUrl(downUrl);
            bdcUploadFileUtils.uploadPdfByUrl(bdcPdfDTO);

        } else if (StringUtils.isNotBlank(licenseNo)) {
            String base64 = "";
            //调用下载电子证照文件
            Map hashMap = new HashMap<>();
            hashMap.put("accessToken", accessToken);
            hashMap.put("licenseNo", licenseNo);

            Object object = exchangeInterfaceFeignService.sjptRequestInterface("ghxkzFile", hashMap);
            if (null != object) {
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
                LOGGER.info("---获取规划许可电子证照文件获取接口:{}", jsonObject);
                String reponseData = jsonObject.getString("data");
                JSONObject jsonResponse = JSON.parseObject(reponseData);
                base64 = jsonResponse.getString("base64");
            }

            // 判断base64字符串是否拥有头信息，没有添加pdf的base64头信息
            if (StringUtils.isNotBlank(base64) && !base64.startsWith("data:")) {
                base64 = "data:image/ofd;base64," + base64;
                bdcUploadFileUtils.uploadBase64File(base64, gzlslid, jsghxkzClmc, "", ".ofd");
            }
        }
        return BdcCommonResponse.ok("保存成功！");
    }

    /**
     * 下载文件
     */
    @ResponseBody
    @GetMapping("/download/file")
    public void downloadFile(@RequestParam String downUrl, HttpServletResponse response) throws Exception {
        if (StringUtils.isBlank(downUrl)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到文件下载地址");
        }
        try {
            LOGGER.info("获取外部附件 url:{}", downUrl);
            InputStream is = httpClientService.doGetReturnStream(downUrl);
            if (is != null) {
                //浏览器下载
                String fileName = URLEncoder.encode(jgysbafjxxClmc + ".doc", "utf-8");
                response.setContentType("application/msword");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.getOutputStream().flush();
                IOUtils.copy(is, response.getOutputStream());
                IOUtils.closeQuietly(is);
                IOUtils.closeQuietly(response.getOutputStream());
            } else {
                LOGGER.error("未获取到预览附件流");
            }
        } catch (Exception e) {
            LOGGER.error("获取到预览附件流异常：{}", e.getMessage(), e);
        }
    }


    /**
     * 舒城-收养信息查询
     *
     * @param param
     * @return
     */
    @PostMapping("/bjjk/sycx")
    public Object sycx(@RequestBody JSONObject param) {
        if (Objects.isNull(param)) {
            throw new MissingArgumentException("参数不可为空！");
        }
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("bjjk_sycx", param);
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("---获取收养信息查询接口:{}", jsonObject);
            if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonObject.getString("code"))) {
                String reponseData = jsonObject.getString("data");
                return JSON.parseArray(reponseData);
            }
        }
        return null;
    }

    /**
     * 舒城-死亡证明
     *
     * @param param
     * @return
     */
    @PostMapping("/bjjk/swzm")
    public Object swzm(@RequestBody JSONObject param) {
        if (Objects.isNull(param)) {
            throw new MissingArgumentException("参数不可为空！");
        }
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("bjjk_swzmcx", param);
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("---获取死亡证明查询接口:{}", jsonObject);
            if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonObject.getString("code"))) {
                String reponseData = jsonObject.getString("data");
                return JSON.parseArray(reponseData);
            }
        }
        return null;
    }

    /**
     * 舒城-2.1 机构编号
     *
     * @return
     */
    @PostMapping("/bjjk/jgbh")
    public String jgbh() {
        return jgbh;
    }

    /**
     * 舒城-2.1 公证案件基本信息查询
     *
     * @param param
     * @return
     */
    @PostMapping("/bjjk/gzajjbxx")
    public Object gzajCx(@RequestBody JSONObject param) {

        if (Objects.isNull(param)) {
            throw new MissingArgumentException("参数不可为空！");
        }
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("bjjk_gzajgzjbxx", param);
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("---公证案件基本信息查询:{}", jsonObject);
            if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonObject.getString("code"))) {
                String reponseData = jsonObject.getString("data");
                return JSON.parseObject(reponseData);
            }
        }
        return null;
    }

    /**
     * 舒城- 2.2 公证案件书信息查询
     *
     * @param param
     * @return
     */
    @PostMapping("/bjjk/gzajsxx")
    public Object gzajsCx(@RequestBody JSONObject param) {

        if (Objects.isNull(param)) {
            throw new MissingArgumentException("参数不可为空！");
        }
        String gzlslid = param.getString("gzlslid");
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("参数不可为空！");
        }
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("bjjk_gzajgzsxx", param);
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("---公证案件书信息查询:{}", jsonObject);
            if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonObject.getString("code"))) {
                JSONObject data = jsonObject.getJSONObject("data");
                if (CommonConstantUtils.RESPONSE_ZTM_SUCCESS.equals(data.getString("ztm"))) {
                    String fileContent = data.getString("file");
                    LOGGER.info("根据文件id下载文件返回：{}", fileContent);
                    if (StringUtils.isNotBlank(fileContent)) {
                        uploadFile(gzlslid, fileContent, "公证书信息");
                    }
                }
                return JSON.parseObject(jsonObject.getString("data"));
            }
        }
        return null;
    }

    /**
     * 舒城-2.3 公证案件其他信息查询
     *
     * @param param
     * @return
     */
    @PostMapping("/bjjk/gzajqtxx")
    public Object gzajqtxxCx(@RequestBody JSONObject param) {

        if (Objects.isNull(param)) {
            throw new MissingArgumentException("参数不可为空！");
        }
        String gzlslid = param.getString("gzlslid");
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("参数不可为空！");
        }
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("bjjk_gzajqtxx", param);
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("---公证案件其他信息查询:{}", jsonObject);
            if (CommonConstantUtils.RESPONSE_SUCCESS.equals(jsonObject.getString("code"))) {
                JSONObject data = jsonObject.getJSONObject("data");
                if (CommonConstantUtils.RESPONSE_ZTM_SUCCESS.equals(data.getString("ztm"))) {
                    String fileContent = data.getString("file");
                    LOGGER.info("根据文件id下载文件返回：{}", fileContent);
                    if (StringUtils.isNotBlank(fileContent)) {
                        uploadFile(gzlslid, fileContent, "其它案件相关证明文书信息");
                    }
                }
                return JSON.parseObject(jsonObject.getString("data"));
            }
        }
        return null;
    }

    /**
     * 舒城- 共享业务系统查询接口
     *
     * @param requestDTO
     * @return
     */
    @PostMapping("/querygxyw")
    public Object queryGxyw(@RequestBody BdcGxywRequestDTO requestDTO) {
        if (Objects.isNull(requestDTO)) {
            throw new MissingArgumentException("参数不可为空！");
        }
        if (StringUtils.isBlank(requestDTO.getBdcdyh()) && StringUtils.isBlank(requestDTO.getGxywh()) && StringUtils.isBlank(requestDTO.getQlrxm())) {
            throw new MissingArgumentException("共享业务号、权利人名称、不动产单元号三个参数任意一个不为空");
        }
        LOGGER.info("舒城-共享业务系统查询接口,查询参数为：{}", JSON.toJSONString(requestDTO));
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("queryGxyw", JSON.toJSONString(requestDTO));
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("舒城-共享业务系统查询接口,返回数据为:{}", jsonObject);
            if ("1".equals(jsonObject.getString("code"))) {
                JSONArray data = jsonObject.getJSONArray("data");
                List<BdcGxywResponseDTO> list = JSON.parseArray(data.toJSONString(), BdcGxywResponseDTO.class);
                return list;
            }
        }
        return null;
    }


    /**
     * 舒城-共享业务系统获取附件接口,上传附件到大云
     *
     * @param gxywh   共享业务号
     * @param gzlslid 工作流实例id
     * @return
     */
    @GetMapping("/gxfjxz")
    public Object gxfjxz(@RequestParam(value = "gxywh") String gxywh, @RequestParam(value = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gxywh) && StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("共享业务号和工作流实例id不为空!");
        }
        LOGGER.info("舒城-共享业务系统获取附件接口,查询参数为gxywh：{},gzlslid:{}", gxywh, gzlslid);
        Map<String, String> map = new HashMap<>();
        map.put("gxywh", gxywh);
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("downGxxtFjxx", map);
        if (null != object) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            LOGGER.info("舒城-共享业务系统获取附件接口,返回数据为:{}", jsonObject);
            if ("1".equals(jsonObject.getString("code"))) {
                // 更新项目表的sply和spxtywh
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(gzlslid);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isEmpty(bdcXmDOList)) {
                    throw new AppException("不动产项目不存在，工作流实例id:" + gzlslid);
                }
                bdcXmDOList.forEach(bdcXmDO -> {
                    bdcXmDO.setSply(CommonConstantUtils.SPLY_GXXT);
                    bdcXmDO.setSpxtywh(gxywh);
                    bdcXmFeignService.updateBdcXm(bdcXmDO);
                });
                // 上传附件到大云
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
                            LOGGER.error("内部共享资料上传大云失败，请求参数BdcPdfDTO:{} ，异常信息：{}", pdfDTO.toString(), e);
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
            //创建根目录
            StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, fileName, "");
            fileContent = "data:application/pdf;base64," + fileContent;
            LOGGER.info("根据文件id下载文件返回：{}", fileContent);
            //文件上传到大云
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
            throw new MissingArgumentException("文件上次失败！");
        }
    }
}
