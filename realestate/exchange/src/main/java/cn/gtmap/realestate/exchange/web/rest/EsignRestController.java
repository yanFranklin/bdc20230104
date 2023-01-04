package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYqFjxxDO;
import cn.gtmap.realestate.common.core.dto.exchange.standard.esign.qsztjs.EsignQszyDataDTO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlYqFjxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.service.impl.inf.standard.EsignServiceImpl;
import com.alibaba.fastjson.JSONObject;
import com.spire.ms.System.Exception;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/10/21
 * @description 签名包公共服务处理
 */
@OpenController(consumer = "签名包公共服务处理相关服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/esign")
public class EsignRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsignRestController.class);

    @Autowired
    private BdcSlYqFjxxFeignService bdcSlYqFjxxFeignService;

    @Autowired
    private EsignServiceImpl esignService;


    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcUploadFileUtils bdcUploadFileUtils;

    @OpenApi(apiDescription = "签署状态通知接口", apiName = "", openLog = false)
    @PostMapping("/hdtz")
    @DsfLog(logEventName = "签署状态通知接口", dsfFlag = "ESIGN", requester = "BDC", responser = "ESIGN")
    public Map<String, String> esignHdtz(@RequestBody MultipartFile[] file, @RequestParam(value = "requestJson") String requestJson) {
        Map resultMap = new HashMap();
        resultMap.put("code", "0000");
        resultMap.put("msg", "成功");
        if (StringUtils.isNotBlank(requestJson)) {
            LOGGER.info("回调参数", requestJson);
            String req = JSONObject.parseObject(requestJson).getString("data");
            EsignQszyDataDTO esignQszyDataDTO = JSONObject.parseObject(req, EsignQszyDataDTO.class);
            LOGGER.info("回调参数1：{}", esignQszyDataDTO.toString());
            if (StringUtils.isBlank(esignQszyDataDTO.getSlbh()) && StringUtils.isBlank(esignQszyDataDTO.getLsh())) {
                resultMap.put("code", "9999");
                resultMap.put("msg", "失败：受理编号或流水号为空");
            } else {
                if (StringUtils.isBlank(esignQszyDataDTO.getSlbh())) {
                    //根据lsh查询项目
                    BdcXmDO bdcXmDO = bdcXmFeignService.queryBdcXmByXmid(esignQszyDataDTO.getLsh());
                    if (null != bdcXmDO) {
                        esignQszyDataDTO.setSlbh(bdcXmDO.getSlbh());
                    }
                }
                try {
                    this.updateQsztAndDownloadFile(esignQszyDataDTO);

                } catch (Exception | IOException e) {
                    resultMap.put("code", "9999");
                    resultMap.put("msg", "更新签署状态，下载附件内容失败，" + e.getMessage());
                }
            }
        } else {
            resultMap.put("code", "9999");
            resultMap.put("msg", "失败：参数为空");
        }
        return resultMap;
    }

    //更新文件签署状态并下载文件
    private List<BdcSlYqFjxxDO> updateQsztAndDownloadFile(EsignQszyDataDTO esignQszyDataDTO) throws IOException {
        // 查询不动产云签附件信息
        BdcSlYqFjxxDO bdcSlYqFjxxDO = new BdcSlYqFjxxDO();
        bdcSlYqFjxxDO.setSlbh(esignQszyDataDTO.getSlbh());
        List<BdcSlYqFjxxDO> bdcSlYqFjxxDOList = this.bdcSlYqFjxxFeignService.listBdcSlYqFjxx(bdcSlYqFjxxDO);

        if (CollectionUtils.isNotEmpty(bdcSlYqFjxxDOList)) {
            for (BdcSlYqFjxxDO fjxx : bdcSlYqFjxxDOList) {
                LOGGER.info("文件名：{}，文件id:{},gzlslid：{}", fjxx.getFjmc(), fjxx.getWjzxid(), fjxx.getGzlslid());
                // 更新文件的签署状态
                fjxx.setQszt(esignQszyDataDTO.getQszt());
                // 下载文件
                this.esignService.esignDownlod(esignQszyDataDTO.getLsh(), fjxx);
            }
            this.bdcSlYqFjxxFeignService.batchSaveBdcSlYqFjxx(bdcSlYqFjxxDOList);
        }
        return bdcSlYqFjxxDOList;
    }

    // 调用云签接口下载文件
    private void wjxz(BdcSlYqFjxxDO bdcSlYqFjxxDO, String lsh) throws IOException {
//        this.esignService.esignDownlod(lsh, bdcSlYqFjxxDO.getWjzxid(), bdcSlYqFjxxDO);
        /*if(Objects.isNull(inputStream)){
            throw new AppException(ErrorCode.CUSTOM, "未获取到附件id为：" + bdcSlYqFjxxDO.getWjzxid() +" 的文件流信息");
        }
        MultipartFile multipartFile = this.convertInputStreamToMultipartFile(inputStream, bdcSlYqFjxxDO.getFjmc());
        BdcPdfDTO bdcPdfDTO = new BdcPdfDTO();
        bdcPdfDTO.setFoldName(bdcSlYqFjxxDO.getWjjmc());
        bdcPdfDTO.setGzlslid(bdcSlYqFjxxDO.getGzlslid());
        bdcPdfDTO.setFileSuffix(CommonConstantUtils.WJHZ_PDF);
        String fjmc = bdcSlYqFjxxDO.getId();
        if(StringUtils.isNotBlank(bdcSlYqFjxxDO.getFjmc())){
            fjmc = bdcSlYqFjxxDO.getFjmc().substring(0, bdcSlYqFjxxDO.getFjmc().indexOf("."));
        }
        bdcPdfDTO.setPdffjmc(fjmc);
        bdcUploadFileUtils.upload(bdcPdfDTO, multipartFile);*/
    }

    private MultipartFile convertInputStreamToMultipartFile(InputStream inputStream, String fileName) throws IOException {
        FileItem item = null;
        OutputStream os = null;
        try {
            FileItemFactory factory = new DiskFileItemFactory(16, null);
            String textFieldName = "uploadfile";
            item = factory.createItem(textFieldName, ContentType.APPLICATION_OCTET_STREAM.toString(), false, fileName);
            os = item.getOutputStream();

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException("文件下载失败", e);
        } finally {
            if (null != os) {
                os.close();
            }
        }
        return new CommonsMultipartFile(item);
    }

}
