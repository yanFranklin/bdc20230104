package cn.gtmap.realestate.register.ui.web.rest;


import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsxmFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzqzFeignService;
import cn.gtmap.realestate.common.util.FileUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.register.ui.core.vo.BdcDzqzVO;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.List;

/**
 * 电子签章打印Controller
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 */
@RestController
@RequestMapping("/rest/v1.0/dzzz")
@Api("电子签章 Controller")
public class BdcDzzzPdfController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDzzzPdfController.class);

    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private ECertificateFeignService eCertificateFeignService;

    @Autowired
    private BdcZsxmFeignService zsxmFeignService;

    @Autowired
    private BdcDzqzFeignService bdcDzqzFeignService;

    @Autowired
    private FileUtils fileUtils;

    @Value("${dzqz.pdf.url:}")
    private String dzqzUrl;

    @Value("${print.path:}")
    private String printPath;

    /**
     * 电子签章打印
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/pdf")
    public BdcDzqzVO pdf(@RequestParam(name = "gzlslid", required = false) String gzlslid, @RequestParam(name = "zsid", required = false) String zsid) {
        if(StringUtils.isBlank(gzlslid) && StringUtils.isBlank(zsid)) {
            throw new MissingArgumentException("未指定要查询签章参数信息");
        }

        BdcDzqzVO bdcDzqzVO = new BdcDzqzVO();

        BdcZsQO bdcZsQO = new BdcZsQO();
        BdcZsDO bdcZsDO = this.getZsDO(gzlslid, bdcZsQO, zsid);
        if (StringUtils.isBlank(bdcZsDO.getZzid())) {
            try {
                if(StringUtils.isBlank(gzlslid) || "null".equals(gzlslid)) {
                    List<BdcXmDO> xmDOList = zsxmFeignService.listBdcXmByZsid(zsid);
                    if(CollectionUtils.isEmpty(xmDOList) || null == xmDOList.get(0)) {
                        throw new MissingArgumentException("未查询到项目信息");
                    }
                    gzlslid = xmDOList.get(0).getGzlslid();
                }
                eCertificateFeignService.changzhouZzqz(gzlslid);
                LOGGER.warn("zzid不存在，程序触发推送签章，对应zsid：{}", bdcZsDO.getZsid());
                bdcDzqzVO.setMsg("当前签章正在核发，请耐心等待或联系管理员！");
            } catch (Exception e) {
                LOGGER.error("程序触发推送签章失败，对应zsid：{}", bdcZsDO.getZsid(), e);
                bdcDzqzVO.setMsg("未成功推送签章，请联系管理员！");
            }
            return bdcDzqzVO;
        }

        // 查询签章状态
        String qzzt = bdcDzqzFeignService.getQzxx(bdcZsDO.getZzid());
        LOGGER.info("查询出的签章状态为：{}", qzzt);
        // 如果签章状态为空或者不为2
        if(StringUtils.isBlank(qzzt) || !qzzt.equals("2")){
            bdcDzqzVO.setMsg("当前签章正在核发，请耐心等待或联系管理员！");
            return bdcDzqzVO;
        }

        File file = null;
        FileOutputStream fileOutputStream = null;
        try {
            // 重新下载
            if (StringUtils.isBlank(bdcZsDO.getZzdz())) {
                long startTime = System.currentTimeMillis();
                eCertificateFeignService.zzxxxz(gzlslid, bdcZsDO.getZsid(), null);
                long endTime = System.currentTimeMillis();
                LOGGER.info("下载签章耗时：{}", endTime - startTime);
                bdcZsDO = this.getZsDO(gzlslid, bdcZsQO, zsid);
            }
            LOGGER.info("下载签章，对应zsid：{}，zzid:{}, 证照签章地址：{}", bdcZsDO.getZsid(), bdcZsDO.getZzid(), bdcZsDO.getZzdz());

            Base64.Encoder encoder = Base64.getEncoder();
            String zzdz = bdcZsDO.getZzdz();
            if(zzdz.toLowerCase().startsWith("/storage")) {
                // 如果存储的是缺省地址，则添加IP端口，因为是服务器应用请求，所以走内网地址
                zzdz = dzqzUrl + zzdz;
            }
            LOGGER.info("电子签章预览地址：{}", zzdz);

            byte[] bytes = httpClientService.doGet(zzdz);
            String fileFlagId = UUIDGenerator.generate16();
            // 上传签章文件到大云
            fileUtils.uploadPdfFileToStorage(bytes, fileFlagId);

            bdcDzqzVO.setPdfFilePath(fileFlagId);
            bdcDzqzVO.setData(encoder.encodeToString(bytes));
            return bdcDzqzVO;
        } catch (Exception e) {
            LOGGER.error("下载签章失败，对应zsid：{}", bdcZsDO.getZsid(), e);
            bdcDzqzVO.setMsg("当前签章正在核发，请耐心等待或联系管理员！");
            return bdcDzqzVO;
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
            org.apache.commons.io.FileUtils.deleteQuietly(file);
        }
    }

    private BdcZsDO getZsDO(String gzlslid, BdcZsQO bdcZsQO, String zsid) {
        bdcZsQO.setGzlslid("null".equalsIgnoreCase(gzlslid) ? "" : gzlslid);
        bdcZsQO.setZsid("null".equalsIgnoreCase(zsid) ? "" : zsid);
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
        if (CollectionUtils.isEmpty(bdcZsDOS) || null == bdcZsDOS.get(0)) {
            throw new AppException("未查询到证书信息");
        }
        return bdcZsDOS.get(0);
    }
}
