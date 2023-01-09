package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.service.BdcGgptxxService;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.GgptxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.GgptxxQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.filter.MissingImageReaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 1.0, 2022/11/03
 * @description 获取工改平台信息
 */
@Service
public class BdcGgptxxServiceImpl implements BdcGgptxxService {
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    private static Logger LOGGER = LoggerFactory.getLogger(BdcGgptxxServiceImpl.class);

    @Override
    public List<GgptxxDTO> queryGgptxx(String param) {
        List<GgptxxDTO> list = new ArrayList<>();
        if (StringUtils.isBlank(param)) {
            throw new MissingArgumentException("查询条件为空");
        }
        JSONObject jsonObject = JSONObject.parseObject(param);
        GgptxxQO ggptxxQO = JSONObject.parseObject(String.valueOf(jsonObject.get("obj")), GgptxxQO.class);
        if (StringUtils.isBlank(ggptxxQO.getAppKey())) {
            throw new MissingArgumentException("文件类型不能为空");
        }
        String beanName = "ggpt_gcjsxmcx";
        Object object = exchangeInterfaceFeignService.requestInterface(beanName, ggptxxQO);

        if (null != object) {
            JSONObject jsonObj = JSONObject.parseObject(JSONObject.toJSONString(object));
            list = JSONObject.parseArray(String.valueOf(jsonObj.get("content")), GgptxxDTO.class);
        }
        return list;
    }

    @Override
    public Object downloadGgfj(GgptxxDTO ggptxxDTO, String gzlslid) throws IOException {
        BdcPdfDTO bdcPdfDTO = new BdcPdfDTO();
        bdcPdfDTO.setFoldName("工改附件");
        //大云附件id
        String dyfjid = "";
        //附件id
        String fjid = "";
        //附件名称
        String fjmc = "";
        //文件后缀
        String fileSuffix = "";
        //base64文件类型头
        String str = "";
        //传参赋值
        if (Objects.nonNull(ggptxxDTO)) {
            fjid = ggptxxDTO.getFjid();
            fjmc = ggptxxDTO.getFjmc();
            String mc = fjmc.substring(1,fjmc.indexOf("."));
            bdcPdfDTO.setPdffjmc(mc);
        }
        //获取文件后缀
        fileSuffix = fjmc.substring(fjmc.lastIndexOf("."));
        if (StringUtils.isNotBlank(fileSuffix)){
            str = this.getFileBase(fileSuffix);
            bdcPdfDTO.setFileSuffix(fileSuffix);
        }else {
            throw new AppException("附件无后缀名，无法判断文件类型，无法保存");
        }

        if (StringUtils.isNotBlank(gzlslid)) {
            bdcPdfDTO.setGzlslid(gzlslid);
        }

        String beanName = "ggpt_gcjsfjcx";
        Map paramMap = new HashMap(1);
        paramMap.put("fjid", fjid);
        LOGGER.info("查询参数fjid,gzlslid,base64需要组织的文件头:{},{},{}", fjid, gzlslid, str);
        Object object = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);

        if (null != object) {
            JSONObject jsonObj = JSONObject.parseObject(JSONObject.toJSONString(object));
            String base64 = String.valueOf(jsonObj.get("content"));
            if (StringUtils.isNotBlank(base64) && StringUtils.isNotBlank(str)) {
                String[] baseStrs = base64.split(",");
                if (baseStrs.length > 1) {
                    bdcPdfDTO.setBase64str(base64);
                } else {
                    base64 = str + base64;
                    bdcPdfDTO.setBase64str(base64);
                }
                dyfjid = bdcUploadFileUtils.uploadBase64File(bdcPdfDTO);
            }
        }
        if (StringUtils.isBlank(dyfjid)) {
            throw new MissingArgumentException("上传至大云附件失败");
        }
        return dyfjid;
    }

    private String getFileBase(String fileSuffix) {
        String fileBase = "";
        switch (fileSuffix) {
            case CommonConstantUtils.WJHZ_PDF: case CommonConstantUtils.WJHZ_DX_PDF:
                fileBase = "data:application/pdf;base64,";
                break;
            case CommonConstantUtils.WJHZ_JPG: case CommonConstantUtils.WJHZ_DX_JPG:
                fileBase = "data:image/jpeg;base64,";
                break;
            case CommonConstantUtils.WJHZ_PNG:
                fileBase = "data:image/png;base64,";
                break;
            case CommonConstantUtils.WJHZ_DOC:
                fileBase = "data:application/msword;base64";
                break;
            case CommonConstantUtils.WJHZ_DOCX:
                fileBase = "data:application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64,";
                break;
            case CommonConstantUtils.WJHZ_ZIP:
                fileBase = "data:application/x-zip-compressed;base64,";
                break;
            case CommonConstantUtils.WJHZ_RAR:
                fileBase = "data:application/octet-stream;base64,";
                break;
            default:
                LOGGER.info("静态常量里，未包含所要下载的文件类型");
        }
        return fileBase;
    }
}
