package cn.gtmap.realestate.exchange.service.impl.inf.wwsq;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwHstDO;
import cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.building.ZdtResponseDTO;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHstFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.ZdFeignService;
import cn.gtmap.realestate.exchange.core.dto.ExchangeDsfCommonResponse;
import cn.gtmap.realestate.exchange.core.dto.wwsq.ZdtHst.request.ZdtHstQO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.ZdtHst.response.ZdtHstFile;
import cn.gtmap.realestate.exchange.core.dto.wwsq.ZdtHst.response.ZdtHstResponseData;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/6/8.
 * @description 宗地图，户室图相关接口处理实现
 */
@Service("zdtHstServiceImpl")
public class ZdtHstServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZdtHstServiceImpl.class);

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private ZdFeignService zdFeignService;

    @Autowired
    private FwHstFeignService fwHstFeignService;


    @Autowired
    private FwHsFeignService fwHsFeignService;

    @Autowired
    private BdcXmMapper bdcXmMapper;

    /**
     * 根据查询条件查询宗地图和户室图
     * bdcdyh查，zl查，slbh和证号查
     *
     * @param
     * @return
     * @Date 2021/8/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ExchangeDsfCommonResponse queryZdtAndHst(ZdtHstQO zdtHstQO) {
        if (null == zdtHstQO) {
            return ExchangeDsfCommonResponse.fail("参数为空！请检查！");
        }
        LOGGER.info(zdtHstQO.toString());
        List<ZdtHstResponseData> zdtHstResponseDataList = new ArrayList<>();
        ZdtHstResponseData zdtHstResponseData = new ZdtHstResponseData();
        List<ZdtHstFile> fileList = new ArrayList<>();

        //bdcdyh，zl不为空时，先查户室图，再查宗地图
        if (StringUtils.isNotBlank(zdtHstQO.getBdcdyh()) || StringUtils.isNotBlank(zdtHstQO.getZl())) {
            LOGGER.info("第一逻辑");
            String bdcdyh = "";
            String zl = "";
            //查户室图
            FwHsDO fwHsDO = fwHsFeignService.queryFwhsByZlAndBdcdyh(null == zdtHstQO.getZl() ? "" : zdtHstQO.getZl(), null == zdtHstQO.getBdcdyh() ? "" : zdtHstQO.getBdcdyh(), zdtHstQO.getQjgldm());
            if (null != fwHsDO && StringUtils.isNotBlank(fwHsDO.getFwHstIndex())) {
                LOGGER.info("房屋户室不为空，开始查户室图数据");

                zl = fwHsDO.getZl();
                bdcdyh = fwHsDO.getBdcdyh();
                //组织户室图参数
                LOGGER.info("开始组织户室图参数，fwHsDO.getFwHstIndex()：{}", fwHsDO.getFwHstIndex());

                FwHstDO hstDO = fwHstFeignService.queryFwHstByIndex(fwHsDO.getFwHstIndex(), zdtHstQO.getQjgldm());
                String base64 = fwHstFeignService.queryFwHstBase64(fwHsDO.getFwHsIndex(), zdtHstQO.getQjgldm());

                if (null != hstDO && StringUtils.isNotBlank(base64)) {
                    ZdtHstFile zdtHstFile = new ZdtHstFile();
                    zdtHstFile.setFileName(hstDO.getHstmc());
                    zdtHstFile.setFileType(Constants.HST_FILETYPE);
                    zdtHstFile.setBse64str(base64);
                    fileList.add(zdtHstFile);
                }
                //再查宗地图
                ZdDjdcbDO zdDjdcbDO = zdFeignService.queryZdByZlAndBdcdyh("", bdcdyh, zdtHstQO.getQjgldm());
                if (null != zdDjdcbDO) {
                    LOGGER.info("宗地调查表不为空，开始查宗地图数据");
                    //组织宗地图参数
                    LOGGER.info("开始组织宗地图参数，bddyh：{}", bdcdyh);

                    ZdtResponseDTO zdtResponseDTO = zdFeignService.queryZdtByBdcdyh(bdcdyh, zdtHstQO.getQjgldm());
                    if (zdtResponseDTO != null && StringUtils.isNotBlank(zdtResponseDTO.getBase64())) {
                        ZdtHstFile zdtHstFile = new ZdtHstFile();
                        zdtHstFile.setBse64str(zdtResponseDTO.getBase64());
                        zdtHstFile.setFileType(Constants.ZDT_FILETYPE);
                        zdtHstFile.setFileName("宗地图.jpg");
                        fileList.add(zdtHstFile);
                    }
                }

            } else {
                //再查宗地图
                ZdDjdcbDO zdDjdcbDO = zdFeignService.queryZdByZlAndBdcdyh(null == zdtHstQO.getZl() ? "" : zdtHstQO.getZl(), null == zdtHstQO.getBdcdyh() ? "" : zdtHstQO.getBdcdyh(), zdtHstQO.getQjgldm());
                if (null != zdDjdcbDO) {
                    LOGGER.info("房屋户室为空！宗地调查表不为空，开始查宗地图数据");
                    zl = zdDjdcbDO.getTdzl();
                    bdcdyh = zdDjdcbDO.getBdcdyh();
                    //组织宗地图参数
                    LOGGER.info("开始组织宗地图参数，bddyh：{}", bdcdyh);

                    ZdtResponseDTO zdtResponseDTO = zdFeignService.queryZdtByBdcdyh(bdcdyh, zdtHstQO.getQjgldm());
                    if (zdtResponseDTO != null && StringUtils.isNotBlank(zdtResponseDTO.getBase64())) {
                        ZdtHstFile zdtHstFile = new ZdtHstFile();
                        zdtHstFile.setBse64str(zdtResponseDTO.getBase64());
                        zdtHstFile.setFileType(Constants.ZDT_FILETYPE);
                        zdtHstFile.setFileName("宗地图.jpg");
                        fileList.add(zdtHstFile);
                    }
                }
            }
            zdtHstResponseData.setFileList(fileList);
            zdtHstResponseData.setZl(zl);
            zdtHstResponseData.setBdcdyh(bdcdyh);
            zdtHstResponseDataList.add(zdtHstResponseData);
        } else if (StringUtils.isNotBlank(zdtHstQO.getSlbh()) || StringUtils.isNotBlank(zdtHstQO.getBdcqzh())) {
            //slbh和证书号查询时
            LOGGER.info("第二逻辑");

            List<BdcXmDO> xmDOList = bdcXmMapper.getXmBySlbhAndCqzh(zdtHstQO.getBdcqzh(), zdtHstQO.getSlbh());
            if (CollectionUtils.isNotEmpty(xmDOList)) {

                for (BdcXmDO xmDO : xmDOList) {
                    String zl = xmDO.getZl();
                    String bdcdyh = xmDO.getBdcdyh();
                    //查宗地图
                    LOGGER.info("开始组织宗地图参数，bddyh：{}", bdcdyh);

                    ZdtResponseDTO zdtResponseDTO = zdFeignService.queryZdtByBdcdyh(bdcdyh, zdtHstQO.getQjgldm());
                    if (zdtResponseDTO != null && StringUtils.isNotBlank(zdtResponseDTO.getBase64())) {
                        ZdtHstFile zdtHstFile = new ZdtHstFile();
                        zdtHstFile.setBse64str(zdtResponseDTO.getBase64());
                        zdtHstFile.setFileType(Constants.ZDT_FILETYPE);
                        zdtHstFile.setFileName("宗地图.jpg");
                        fileList.add(zdtHstFile);

                    }
                    //再查户室图
                    FwHsDO fwHsDO = fwHsFeignService.queryFwhsByZlAndBdcdyh(xmDO.getZl(), xmDO.getBdcdyh(), zdtHstQO.getQjgldm());
                    if (null != fwHsDO && StringUtils.isNotBlank(fwHsDO.getFwHstIndex())) {

                        LOGGER.info("开始组织户室图参数，fwHsDO.getFwHstIndex()：{}", fwHsDO.getFwHstIndex());

                        FwHstDO hstDO = fwHstFeignService.queryFwHstByIndex(fwHsDO.getFwHstIndex(), zdtHstQO.getQjgldm());
                        String base64 = fwHstFeignService.queryFwHstBase64(fwHsDO.getFwHsIndex(), zdtHstQO.getQjgldm());

                        if (null != hstDO && StringUtils.isNotBlank(base64)) {
                            ZdtHstFile zdtHstFile = new ZdtHstFile();
                            zdtHstFile.setFileName(hstDO.getHstmc());
                            zdtHstFile.setFileType(Constants.HST_FILETYPE);
                            zdtHstFile.setBse64str(base64);
                            fileList.add(zdtHstFile);
                        }
                    }
                    zdtHstResponseData.setFileList(fileList);
                    zdtHstResponseData.setZl(zl);
                    zdtHstResponseData.setBdcdyh(bdcdyh);
                    zdtHstResponseDataList.add(zdtHstResponseData);
                }
            }
        }
        return ExchangeDsfCommonResponse.ok(zdtHstResponseDataList);
//        return ExchangeDsfCommonResponse.ok(JSON.toJSONString(zdtHstResponseDataList, SerializerFeature.DisableCircularReferenceDetect));
    }
}
