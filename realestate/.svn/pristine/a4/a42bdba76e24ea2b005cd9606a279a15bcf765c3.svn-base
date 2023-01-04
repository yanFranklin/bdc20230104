package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.ZipUtils;
import cn.gtmap.realestate.exchange.core.convert.GcspSystemConvert;
import cn.gtmap.realestate.exchange.core.domain.gcsp.*;
import cn.gtmap.realestate.exchange.core.vo.GcspDownloadProjectInfoVO;
import cn.gtmap.realestate.exchange.core.vo.GcspQueryProjectInfoVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "工程审批系统接口")
@RequestMapping("/realestate-exchange/gcsp")
public class BengbuGcspRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BengbuGcspRestController.class);

    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    private GcspSystemConvert gcspSystemConvert;

    @Value("${gcsp.temp.file.path:/Users/wahaha-zhanshi/Desktop/}")
    private String loaclTempFilePath;

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 工程审批系统分页查询项目信息
     */
    @ResponseBody
    @GetMapping("/list/project")
    public Object listGcspXmxxByPageJson(@LayuiPageable Pageable pageable, String applyinstCode, String localCode, String gcbm, String projName, String dtmc, String certinstCode) {
        GcspQueryProjectInfoVO gcspQueryProjectInfoVO = GcspQueryProjectInfoVO.GcspQueryProjectInfoVOBuilder.aGcspQueryProjectInfoVO().withApplyinstCode(applyinstCode).withCertinstCode(certinstCode).withDtmc(dtmc).withGcbm(gcbm).withLocalCode(localCode).withProjName(projName).build();
        LOGGER.info("工程审批系统分页查询项目信息查询入参：{}", gcspQueryProjectInfoVO);
        GcspQueryProjectInfoRequest gcspQueryProjectInfoRequest = gcspSystemConvert.getGcspQueryProjectInfoRequestBygcspQueryProjectInfoVO(gcspQueryProjectInfoVO);
        if (pageable != null) {
            gcspQueryProjectInfoRequest.setPageNum(Integer.toString(pageable.getPageNumber()));
            gcspQueryProjectInfoRequest.setPageSize(Integer.toString(pageable.getPageSize()));
            Object response = exchangeInterfaceFeignService.requestInterface("getProjectInfos", gcspQueryProjectInfoRequest);
//            Object response = "";
            if (response != null) {
                GcspCommonResponse<GcspQueryProjectInfoPageResponse> gcspCommonResponse = JSON.parseObject(JSON.toJSONString(response), new TypeReference<GcspCommonResponse<GcspQueryProjectInfoPageResponse>>() {});
//                GcspCommonResponse<GcspQueryProjectInfoPageResponse> gcspCommonResponse = JSON.parseObject((String) response, new TypeReference<GcspCommonResponse<GcspQueryProjectInfoPageResponse>>() {});
                if (gcspCommonResponse != null && gcspCommonResponse.isSuccess()) {
                    return dealWithResponse(gcspCommonResponse.getContent());
                }else {
                    throw new RuntimeException("查询对端接口异常:"+gcspCommonResponse.getMessage());
                }
            }else {
                throw new RuntimeException("查询对端接口异常:无返回");
            }
        }else {
            throw new RuntimeException("入参缺少分页参数");
        }
    }

    /**
     * {
     * "code": 0,
     * "msg": "",
     * "count": 1000,
     * "data": [{}, {}]
     * }
     *
     * @param gcspQueryProjectInfoPageResponse
     */
    private static Map<String, Object> dealWithResponse(GcspQueryProjectInfoPageResponse gcspQueryProjectInfoPageResponse) {
        Map<String, Object> layuiResponse = new HashMap<>(4);
        layuiResponse.put("code", 0);
        layuiResponse.put("msg", "success");
        layuiResponse.put("count", gcspQueryProjectInfoPageResponse.getTotal());
        layuiResponse.put("content", gcspQueryProjectInfoPageResponse.getList());
        return layuiResponse;
    }

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 工程审批系统获取项目的相关附件信息
     */
    @PostMapping(value = "/download")
    public void getProjectMats(@ModelAttribute  GcspDownloadProjectInfoVO gcspDownloadProjectInfoVO, HttpServletResponse response) {
        LOGGER.info("工程审批系统获取项目的相关附件信息入参：{}", JSON.toJSONString(gcspDownloadProjectInfoVO));
        if (StringUtils.isNotBlank(gcspDownloadProjectInfoVO.getGcbm()) || StringUtils.isNotBlank(gcspDownloadProjectInfoVO.getCertinstCode())) {
            String uuid = UUIDGenerator.generate();
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename=" + uuid + ".zip");
            ZipOutputStream out = null;
            try {
                out = new ZipOutputStream(response.getOutputStream());
                Map<String, String> requestParam = new HashMap<>(1);
                if (StringUtils.isNotBlank(gcspDownloadProjectInfoVO.getGcbm())) {
                    requestParam.put("gcbm", gcspDownloadProjectInfoVO.getGcbm());
                } else if (StringUtils.isNotBlank(gcspDownloadProjectInfoVO.getCertinstCode())) {
                    requestParam.put("certinstCode", gcspDownloadProjectInfoVO.getCertinstCode());
                }
                Object projectMats = exchangeInterfaceFeignService.requestInterface("getProjectMats", requestParam);
                String projectMatsStr = JSON.toJSONString(projectMats);
//                Object projectMats = new JSONObject();
//                String projectMatsStr = "";
                LOGGER.info("工程审批系统获取项目的相关附件信息返回：{}", projectMatsStr);
                if (projectMats != null) {
                    //获取返回参数中的附件信息压缩打包返回
                    GcspCommonResponse<GcspQueryProjectMatInfoResponse> gcspCommonResponse = JSON.parseObject(projectMatsStr, new TypeReference<GcspCommonResponse<GcspQueryProjectMatInfoResponse>>() {
                    });
                    if (gcspCommonResponse.isSuccess()) {
                        //下载附件到本地
                        GcspQueryProjectMatInfoResponse content = gcspCommonResponse.getContent();
                        String tempFolderPath = loaclTempFilePath + File.separator + uuid;
                        File tempFolder = new File(tempFolderPath);
                        if (!tempFolder.exists()) {
                            tempFolder.mkdir();
                        }
                        if (content != null) {
                            List<GcspQueryProjectFolderInfo> matVo = content.getMatVo();
                            if (CollectionUtils.isNotEmpty(matVo)) {
                                for (GcspQueryProjectFolderInfo folderInfo : matVo) {
                                    List<GcspQueryProjectMatsFileInfo> fileInfos = folderInfo.getMatInst();
                                    if (CollectionUtils.isNotEmpty(fileInfos)) {
                                        for (GcspQueryProjectMatsFileInfo fileInfo : fileInfos) {
                                            if (StringUtils.isNotBlank(fileInfo.getUrl()) && StringUtils.isNotBlank(fileInfo.getMatinstName())) {
                                                String urlFileName = fileInfo.getMatinstName() + ".jpg";
                                                File diskFile = new File(tempFolder + File.separator + urlFileName);
                                                if (!diskFile.exists()) {
                                                    /// 不存在直接下载
                                                    try {
                                                        FileUtils.copyURLToFile(new URIBuilder(fileInfo.getUrl()).build().toURL(), diskFile, 5000, 30000);
                                                    } catch (Exception e) {
                                                        LOGGER.error("系统导出PDF失败，因为模板文件下载失败：{}", fileInfo.getUrl(), e);
                                                        throw new AppException("系统导出PDF报错，处理终止");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        //压缩附件
                        //返回
                        ZipUtils.zipfile(tempFolder, tempFolderPath, out);
                    } else {
                        throw new RuntimeException("工程审批系统返回:" + gcspCommonResponse.getMessage());
                    }
                }
            } catch (Exception e) {
                LOGGER.error("工程审批系统获取项目的相关附件信息失败:", e);
                throw new RuntimeException(e);
            } finally {
                if (out != null) {
                    try {
                        out.finish();
                        out.close();
                    } catch (IOException e) {
                        LOGGER.error("工程审批系统获取项目的相关附件信息失败:", e);
                    }

                }
            }

        }
    }

}

