package cn.gtmap.realestate.certificate.core.service.impl.create;


import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzDzqzMapper;
import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzZzxxMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzCreateConfigService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzService;
import cn.gtmap.realestate.certificate.core.service.BdcFdjywDzqzService;
import cn.gtmap.realestate.certificate.core.service.BdcGgDzqzService;
import cn.gtmap.realestate.certificate.core.service.create.BdcDzqzCreateService;
import cn.gtmap.realestate.certificate.core.service.create.BdcDzzzCreateService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileCenterService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzStorageService;
import cn.gtmap.realestate.certificate.util.Base64Util;
import cn.gtmap.realestate.certificate.util.BdcDzzzPdfUtil;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Objects;


/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/28 创建电子证照
 */
public class BdcDzzzCreateConfigServiceImpl implements BdcDzzzCreateConfigService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzCreateConfigServiceImpl.class);
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzFileCenterService bdcDzzzFileCenterService;
    private Map<String, BdcDzzzCreateService> bdcDzzzCreateMap;
    @Autowired
    private BdcDzzzZzxxMapper bdcDzzzZzxxMapper;
    @Autowired
    BdcDzzzStorageService bdcDzzzStorageService;
    @Autowired
    BdcDzqzCreateService bdcDzqzCreateService;
    @Autowired
    BdcGgDzqzService bdcGgDzqzService;
    @Autowired
    BdcDzzzDzqzMapper bdcDzzzDzqzMapper;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    BdcFdjywDzqzService bdcFdjywDzqzService;

    @Override
    public DzzzResponseModel createDzzz(Object o) {
        return getCreateDzzzService().createDzzz(o);
    }

    @Override
    public DzzzResponseModel createDzqz(Object o) {
        return bdcDzqzCreateService.createDzqz(o);
    }

    /**
     * @param o@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 非登记业务数据无证书相关信息创建电子签章数据
     * @date : 2022/8/24 9:50
     */
    @Override
    public DzzzResponseModel createFdjywDzqz(Object o) {
        return bdcFdjywDzqzService.createFdjDzqz(o);
    }

    /**
     * @param o
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 推送公告电子签章服务
     * @date : 2022/3/14 15:15
     */
    @Override
    public DzzzResponseModel createGgDzqz(Object o) {
        return bdcGgDzqzService.createGgDzqz(o);
    }

    @Override
    public BdcDzzzCreateService getCreateDzzzService(String dwdm) {
        BdcDzzzCreateService bdcDzzzCreateService = bdcDzzzCreateMap.get(dwdm);
        if (null == bdcDzzzCreateService) {
            throw new NullPointerException("该单位代码" + BdcDzzzPdfUtil.DZZZ_DWDM + "没有对应的创建证照实现类！");
        }
        return bdcDzzzCreateService;
    }

    @Override
    public DzzzResponseModel uploadFlImg(BdcDzzzZzxx bdcDzzzZzxx) {
        byte[] flImgData = Base64Util.decodeBase64StrToByte(bdcDzzzZzxx.getFlImgBase64());
        if (null == flImgData) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.BASE64_CONVERT_FILE_ERROR.getCode(), null);
        }

        // 上传文件中心
        String zzwjlj = bdcDzzzFileCenterService.sendFileCenter(flImgData, bdcDzzzZzxx.getZzid()
                , bdcDzzzZzxx.getBdcqzh() + Constants.BDC_DZZZ_FILENAME_FLIMG + Constants.BDC_DZZZ_FORMAT_JPG, Constants.BDC_DZZZ);
        if (StringUtils.isBlank(zzwjlj)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_UPLOAD_FILE_CENTER_ERROR.getCode(), null);
        }
        bdcDzzzZzxx.setZzwjlj(zzwjlj);
        return bdcDzzzService.dzzzResponseSuccess(ResponseEnum.SUCCESS.getMsg(), null);
    }

    /**
     * @param bdcDzzzZzxx@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 上传到大云文件中心
     * @date : 2022/3/10 11:22
     */
    @Override
    public DzzzResponseModel uploadFlImgToStorage(BdcDzzzZzxx bdcDzzzZzxx) {
        byte[] flImgData = Base64Util.decodeBase64StrToByte(bdcDzzzZzxx.getFlImgBase64());
        if (null == flImgData) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.BASE64_CONVERT_FILE_ERROR.getCode(), null);
        }

        // 上传文件中心
        String zzwjlj = bdcDzzzStorageService.sendFileCenter(flImgData, bdcDzzzZzxx.getZzid()
                , bdcDzzzZzxx.getBdcqzh() + Constants.BDC_DZZZ_FILENAME_FLIMG + Constants.BDC_DZZZ_FORMAT_JPG, Constants.BDC_DZZZ);
        if (StringUtils.isBlank(zzwjlj)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_UPLOAD_FILE_CENTER_ERROR.getCode(), null);
        }
        bdcDzzzZzxx.setZzwjlj(zzwjlj);
        return bdcDzzzService.dzzzResponseSuccess(ResponseEnum.SUCCESS.getMsg(), null);
    }

    @Override
    public DzzzResponseModel updateDzzzSignAndUploadInfo(BdcDzzzZzxx bdcDzzzZzxx) {
        BdcDzzzZzxxDO bdcDzzz = new BdcDzzzZzxxDO();
        bdcDzzz.setZzid(bdcDzzzZzxx.getZzid());
        bdcDzzz.setZzqzlj(bdcDzzzZzxx.getZzqzlj());
        bdcDzzz.setQzzt(bdcDzzzZzxx.getQzzt());
        bdcDzzz.setZzqzr(bdcDzzzZzxx.getZzqzr());
        bdcDzzz.setZzqzsj(bdcDzzzZzxx.getZzqzsj());
        int count = bdcDzzzZzxxMapper.updateBdcDzzzByZzid(bdcDzzz);
        if (count == 0) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.DATABASE_UPDATE.getCode(), null);
        }
        return bdcDzzzService.dzzzResponseSuccess(null);
    }

    /**
     * @param zzbs
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据签章标识获取文件信息
     * @date : 2022/3/15 14:31
     */
    @Override
    public StorageDto queryDzqzfj(String zzbs) {
        if (StringUtils.isNotBlank(zzbs)) {
            BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcDzzzDzqzMapper.queryBdcDzzzQzxxByZzbs(zzbs);
            if (Objects.nonNull(bdcDzzzZzxxDO) && StringUtils.isNotBlank(bdcDzzzZzxxDO.getZzqzlj())) {
                String nodeId = bdcDzzzFileCenterService.getNodeIdByZzwjlj(bdcDzzzZzxxDO.getZzqzlj());
                if (StringUtils.isNotBlank(nodeId)) {
                    StorageDto storageDto = storageClient.findById(nodeId);
                    return storageDto;
                }
            }
        }
        return null;
    }

    /**
     * @param zzid
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 根据签章id获取签章信息
     * @date : 2022/7/8 14:31
     */
    @Override
    public String getQzxx(String zzid) {
        if (StringUtils.isNotBlank(zzid)) {
            BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcDzzzDzqzMapper.queryBdcDzzzQzxxByZzbs(zzid);
            if(bdcDzzzZzxxDO != null && bdcDzzzZzxxDO.getQzzt() != null){
                return bdcDzzzZzxxDO.getQzzt().toString();
            }
        }
        return "";
    }

    public BdcDzzzCreateService getCreateDzzzService() {
        BdcDzzzCreateService bdcDzzzCreateService = bdcDzzzCreateMap.get(BdcDzzzPdfUtil.DZZZ_DWDM);
        if (null == bdcDzzzCreateService) {
            throw new NullPointerException("该单位代码" + BdcDzzzPdfUtil.DZZZ_DWDM + "没有对应的创建证照实现类！");
        }
        return bdcDzzzCreateService;
    }

    public Map<String, BdcDzzzCreateService> getBdcDzzzCreateMap() {
        return bdcDzzzCreateMap;
    }

    public void setBdcDzzzCreateMap(Map<String, BdcDzzzCreateService> bdcDzzzCreateMap) {
        this.bdcDzzzCreateMap = bdcDzzzCreateMap;
    }
}
