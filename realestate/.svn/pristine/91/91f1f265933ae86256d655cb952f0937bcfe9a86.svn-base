package cn.gtmap.realestate.certificate.core.service.impl.zzzx;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzDzqzMapper;
import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzZzxxMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.model.template.DzzzPdf;
import cn.gtmap.realestate.certificate.core.model.template.DzzzSignPosition;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileCenterService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileConfigService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.Map;


/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/28 创建电子证照
 */
public class BdcDzzzZzzxCzServiceImpl implements BdcDzzzZzzxService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzZzzxCzServiceImpl.class);

    @Autowired
    private BdcDzzzZzzxConfigService bdcDzzzZzzxConfigService;
    @Resource
    private BdcDzzzZzxxMapper bdcDzzzZzxxMapper;
    @Autowired
    private BdcDzzzFileCenterService bdcDzzzFileCenterService;
    @Autowired
    BdcDzzzFileConfigService bdcDzzzFileConfigService;
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzWaterMarkService bdcDzzzWaterMarkService;
    @Autowired
    private BdcDzzzZzxxPdfService bdcDzzzZzxxPdfService;
    @Autowired
    BdcDzzzDzqzMapper bdcDzzzDzqzMapper;
    @Value("${dzqz.gg.gglx:}")
    private String gglxMapString;

    @Override
    public DzzzResponseModel dzzzZzzx(Object o) {
        BdcDzzzZzxx bdcDzzzZzxx = (BdcDzzzZzxx) o;

        DzzzResponseModel beforeCheckModel = bdcDzzzZzzxConfigService.bdcDzzzZzzxBeforeCheck(bdcDzzzZzxx);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), beforeCheckModel.getHead().getStatus())) {
            return beforeCheckModel;
        }

        BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcDzzzDzqzMapper.queryBdcDzzzQzxxByZzbs(bdcDzzzZzxx.getZzbs());
        DzzzResponseModel infoCheckModel = bdcDzzzZzzxConfigService.bdcDzzzZzzxInfoCheck(bdcDzzzZzxxDO);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), infoCheckModel.getHead().getStatus())) {
            return infoCheckModel;
        }

        // 下载已盖章文件,根据配置读取大云数据
        byte[] signArr = bdcDzzzFileConfigService.downloadByZzwjlj(bdcDzzzZzxxDO.getZzqzlj());
        if (null == signArr) {
            logger.warn("当前证照标识{}未获取到文件信息", bdcDzzzZzxx.getZzbs());
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getCode(), null);
        }

        // 已盖章文件上传文件中心替换未盖章前文件
        String zzwjlj = bdcDzzzZzxxPdfService.sendStorage(bdcDzzzZzxxDO.getZzid(), bdcDzzzZzxxDO.getBdcqzh(), signArr);
        if (StringUtils.isBlank(zzwjlj)) {
            logger.warn("没有成功上传文件中心证照id{}", bdcDzzzZzxxDO.getZzid());
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_UPLOAD_FILE_CENTER_ERROR.getCode(), null);
        }

        DzzzPdf dzzzPdf = bdcDzzzZzxxPdfService.getDzzzPdf(bdcDzzzZzxxDO.getZzlxdm());
        if (null == dzzzPdf) {
            if (StringUtils.isNotBlank(gglxMapString)) {
                Map<String, String> gglxMap = JSON.parseObject(gglxMapString, Map.class);
                Map<String, Integer> qzxxMap = JSON.parseObject(gglxMap.get(bdcDzzzZzxx.getZzlxdm()), Map.class);
                dzzzPdf = new DzzzPdf();
                DzzzSignPosition dzzzSignPosition = new DzzzSignPosition();
                dzzzSignPosition.setPage(qzxxMap.get("page"));
                dzzzSignPosition.setX(qzxxMap.get("X"));
                dzzzSignPosition.setY(qzxxMap.get("Y"));
                dzzzPdf.setSign(dzzzSignPosition);
            } else {
                logger.warn("未获取到模板证照类型代码{}", bdcDzzzZzxxDO.getZzlxdm());
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_TEMPLATE_ACQUIRED_ERROR.getCode(), null);
            }
        }

        // 已盖章文件加盖注销水印
        byte[] markArr = bdcDzzzWaterMarkService.addWatermarkZzzx(signArr, dzzzPdf, bdcDzzzZzxx.getZzbgyy());
        if (null == markArr) {
            logger.warn("未成功加盖水印{}", bdcDzzzZzxxDO.getZzbs());
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_WATERMARK_ERROR.getCode(), null);
        }

        // 加盖注销水印文件上传文件中心替换盖章文件
        String savePdfName = bdcDzzzZzxxDO.getBdcqzh() + Constants.BDC_DZZZ_FILENAME_SIGNA;
        String zzqzlj = bdcDzzzZzxxPdfService.sendStorage(bdcDzzzZzxxDO.getZzid(), savePdfName, markArr);
        if (StringUtils.isBlank(zzqzlj)) {
            logger.warn("未成功上传文件中心加盖注销信息{}", bdcDzzzZzxxDO.getZzbs());
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_UPLOAD_FILE_CENTER_ERROR.getCode(), null);
        }

        // 更新注销信息
        return bdcDzzzZzzxConfigService.updateDzqzZxxxInfo(bdcDzzzZzxxDO.getZzid(), bdcDzzzZzxx.getZzbgyy(), bdcDzzzZzxx.getZzbgsj(), zzqzlj);
    }
}
