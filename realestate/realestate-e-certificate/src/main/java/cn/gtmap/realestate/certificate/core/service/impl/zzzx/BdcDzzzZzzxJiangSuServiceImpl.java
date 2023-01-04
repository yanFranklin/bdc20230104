package cn.gtmap.realestate.certificate.core.service.impl.zzzx;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzZzxxMapper;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileConfigService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;


/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/28 创建电子证照
 */
public class BdcDzzzZzzxJiangSuServiceImpl implements BdcDzzzZzzxService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzZzzxJiangSuServiceImpl.class);

    @Autowired
    private BdcDzzzZzzxConfigService bdcDzzzZzzxConfigService;
    @Resource
    private BdcDzzzZzxxMapper bdcDzzzZzxxMapper;
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzWaterMarkService bdcDzzzWaterMarkService;
    @Autowired
    private BdcDzzzZzxxService bdcDzzzZzxxService;
    @Autowired
    private BdcDzzzFileConfigService bdcDzzzFileConfigService;

    @Override
    public DzzzResponseModel dzzzZzzx(Object o) {
        BdcDzzzZzxx bdcDzzzZzxx = (BdcDzzzZzxx)o;

        DzzzResponseModel beforeCheckModel = bdcDzzzZzzxConfigService.bdcDzzzZzzxBeforeCheck(bdcDzzzZzxx);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), beforeCheckModel.getHead().getStatus())) {
            return beforeCheckModel;
        }

        BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcDzzzZzxxMapper.queryBdcDzzzZzxxByZzbs(bdcDzzzZzxx.getZzbs());
        DzzzResponseModel infoCheckModel = bdcDzzzZzzxConfigService.bdcDzzzZzzxInfoCheck(bdcDzzzZzxxDO);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), infoCheckModel.getHead().getStatus())) {
            return infoCheckModel;
        }

        bdcDzzzZzxxDO.setZzbgyy(bdcDzzzZzxx.getZzbgyy());
        bdcDzzzZzxx = bdcDzzzZzxxService.getBdcDzzzZzxxFromBdcDzzzZzxxDO(bdcDzzzZzxxDO, null);
        //完善电子证照数据
        bdcDzzzZzxx = bdcDzzzZzxxService.getPerfectBdcDzzzZzxx(bdcDzzzZzxx);
        bdcDzzzZzxx.setZzzt(2);
        bdcDzzzZzxx.setZzbgsj(DateUtil.now());

        // 下载已盖章文件
        byte[] signArr = bdcDzzzFileConfigService.downloadByZzwjlj(bdcDzzzZzxxDO.getZzqzlj());
        if (null == signArr) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getCode(), null);
        }

        // 盖章文件增加注销水印
        byte[] markArr = bdcDzzzWaterMarkService.addWatermarkZzzx(signArr, bdcDzzzZzxxDO.getZzlxdm(), bdcDzzzZzxx.getZzbgyy());
        if (null == markArr) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_WATERMARK_ERROR.getCode(), null);
        }

        // 上传文件中心
        String zzqzlj = bdcDzzzFileConfigService.sendFileCenter(markArr, bdcDzzzZzxx.getZzid(), bdcDzzzZzxx.getBdcqzh(), Constants.BDC_DZZZ);
        if (StringUtils.isBlank(zzqzlj)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_UPLOAD_FILE_CENTER_ERROR.getCode(), null);
        }

        // 更新注销信息
        return bdcDzzzZzzxConfigService.updateDzzzZxxxInfo(bdcDzzzZzxxDO.getZzid(), bdcDzzzZzxx.getZzbgyy(), bdcDzzzZzxx.getZzbgsj());
    }
}
