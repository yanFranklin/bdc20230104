package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlJbxxService;
import cn.gtmap.realestate.accept.service.BdcByslYwfwService;
import cn.gtmap.realestate.accept.service.BdcSlBysldjService;
import cn.gtmap.realestate.common.core.domain.accept.BdcByslDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.DzqzCsDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.EZsDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.FdjywDzqzDTO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzqzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.FileUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @program: realestate
 * @description: 不予受理/登记业务服务实现方法
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-08-24 09:28
 **/
@Service
public class BdcSlByslYwfwServiceImpl implements BdcByslYwfwService {

    private final Logger LOGGER = LoggerFactory.getLogger(BdcSlByslYwfwServiceImpl.class);

    @Value("${print.path:}")
    private String printPath;

    @Value("${bysldj.qzwjjmc:电子签章}")
    private String byslWjjmc;

    @Autowired
    BdcPrintFeignService bdcPrintFeignService;

    @Autowired
    BdcSlJbxxService bdcSlJbxxService;

    @Autowired
    PdfController pdfController;

    @Autowired
    FileUtils fileUtils;

    @Autowired
    BdcDzqzFeignService bdcDzqzFeignService;

    @Autowired
    ECertificateFeignService eCertificateFeignService;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    @Autowired
    BdcSlBysldjService bdcSlBysldjService;


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 推送不予受理信息到签章台账
     * @date : 2022/8/24 9:24
     */
    @Override
    public void pushBysldjToDzqz(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            String slbh = "";
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
            if (Objects.nonNull(bdcSlJbxxDO)) {
                slbh = bdcSlJbxxDO.getSlbh();
            }
            //1. 根据工作流实例id查询不予受理的信息
            BdcByslDO bdcByslDO = new BdcByslDO();
            List<BdcByslDO> bdcByslDOList = bdcSlBysldjService.queryBdcByslDOBygzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcByslDOList)) {
                bdcByslDO = bdcByslDOList.get(0);
            }
            //2. 返回结果中 lx=1 是bysl lx=2 是bydj；
            String dylx = "byslxx";
            if (!StringUtils.equals("1", bdcByslDO.getLx())) {
                dylx = "bydjxx";
            }
            //3. 传参打印，获取数据生成pdf 转base64 推送签章台账
            List<Map> maps = new ArrayList<>(1);
            Map<String, List<Map>> paramMap = Maps.newHashMap();
            Map map = new HashMap(1);
            map.put("gzlslid", gzlslid);
            maps.add(map);
            paramMap.put(dylx, maps);
            String xml = bdcPrintFeignService.print(paramMap);
            LOGGER.warn("当前流程实例id={}生成xml入参{}推送签章生成的xml数据{}", gzlslid, paramMap, xml);
            OfficeExportDTO officeExportDTO = new OfficeExportDTO();
            String modelPath = printPath + dylx + CommonConstantUtils.WJHZ_DOCX;
            officeExportDTO.setModelName(modelPath);
            officeExportDTO.setXmlData(xml);
            officeExportDTO.setFileName(dylx + slbh);
            String path = pdfController.generatePdfFile(officeExportDTO);
            byte[] bytes = fileUtils.fileToByte(new File(path));
            JSONObject param = new JSONObject();
            FdjywDzqzDTO fdjywDzqzDTO = new FdjywDzqzDTO();
            fdjywDzqzDTO.setPdfByte(bytes);
            DzqzCsDTO dzqzCsDTO = new DzqzCsDTO();
            //set 相关信息
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                dzqzCsDTO.setBdcdyh(bdcSlXmDOList.get(0).getBdcdyh());
            }
            if (StringUtils.equals("1", bdcByslDO.getLx())) {
                dzqzCsDTO.setBdcqzh("不予受理业务" + slbh);
                dzqzCsDTO.setQzlx("byslqz");
            } else {
                dzqzCsDTO.setBdcqzh("不予登记业务" + slbh);
                dzqzCsDTO.setQzlx("bydjqz");
            }
            dzqzCsDTO.setDjjg(bdcSlJbxxDO.getDjjg());
            dzqzCsDTO.setDjbmdm(bdcSlJbxxDO.getDjbmdm());
            dzqzCsDTO.setQxdm(bdcSlJbxxDO.getQxdm());
            dzqzCsDTO.setSlbh(slbh);
            //取不予受理表的坐落字段
            dzqzCsDTO.setZl(bdcByslDO.getZl());
            EZsDTO eZsDTO = eCertificateFeignService.generateDzqzxx(dzqzCsDTO);
            BeanUtils.copyProperties(eZsDTO, fdjywDzqzDTO);
            //取不予受理的否定申请人
            fdjywDzqzDTO.setCzzt(bdcByslDO.getFdsxsqr());
            param.put("data", fdjywDzqzDTO);
            bdcDzqzFeignService.pushDzqz(JSON.toJSONString(param));
        }
    }


    /**
     * @param bdcPdfDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新不予受理流程的附件信息
     * @date : 2022/8/25 11:18
     */
    @Override
    public void updateByslFjxx(BdcPdfDTO bdcPdfDTO) {
        if (Objects.nonNull(bdcPdfDTO) && StringUtils.isNotBlank(bdcPdfDTO.getBase64str()) && StringUtils.isNotBlank(bdcPdfDTO.getSlbh())) {
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxBySlbh(bdcPdfDTO.getSlbh(), "");
            if (Objects.nonNull(bdcSlJbxxDO)) {
                //查询是否有不予受理或者登记的数据
                List<BdcByslDO> bdcByslDOList = bdcSlBysldjService.queryBdcByslDOBygzlslid(bdcSlJbxxDO.getGzlslid());
                if (CollectionUtils.isNotEmpty(bdcByslDOList)) {
                    LOGGER.warn("签章后更新不予受理附件信息受理编号{}", bdcPdfDTO.getSlbh());
                    try {
                        bdcUploadFileUtils.uploadBase64File(CommonConstantUtils.BASE64_QZ_PDF + bdcPdfDTO.getBase64str(), bdcSlJbxxDO.getGzlslid(), byslWjjmc, "电子签章" + bdcPdfDTO.getSlbh(), ".pdf");
                    } catch (IOException e) {
                        LOGGER.error("更新电子签章信息上传附件失败{}", bdcPdfDTO.getSlbh(), e);
                    }
                }
            }
        }
    }
}
