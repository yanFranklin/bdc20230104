package cn.gtmap.realestate.certificate.core.service.impl;


/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description 不动产电子证照加注件信息
 */

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzJzjxxMapper;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzJzjxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileConfigService;
import cn.gtmap.realestate.certificate.util.BdcDzzzPdfUtil;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BdcDzzzJzjxxServiceImpl implements BdcDzzzJzjxxService {

    @Autowired
    private BdcDzzzJzjxxMapper bdcDzzzJzjxxMapper;
    @Autowired
    protected BdcDzzzZzxxService bdcDzzzZzxxService;
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzZzxxPdfService bdcDzzzZzxxPdfService;
    @Autowired
    private BdcDzzzFileConfigService bdcDzzzFileConfigService;
    @Autowired
    private BdcDzzzSignConfigService bdcDzzzSignConfigService;

    @Override
    public DzzzResponseModel insertBdcDzzzJzjxx(BdcDzzzZzxx bdcDzzzZzxx) throws DataAccessException {
        BdcDzzzJzjxxDO bdcDzzzJzjxxDO = bdcDzzzZzxxService.getBdcDzzzJzjxxDOFromBdcDzzzZzxx(bdcDzzzZzxx);
        int result = bdcDzzzJzjxxMapper.insertBdcDzzzJzjxx(bdcDzzzJzjxxDO);
        if (result != 1) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.FALSE.getMsg(), null);
        } else {
            return bdcDzzzService.dzzzResponseSuccess(null);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBdcDzzzJzjxxByZzid(String zzid) {
        if (StringUtils.isNotEmpty(zzid)) {
            List<BdcDzzzJzjxxDO> bdcDzzzJzjxxDOList = bdcDzzzJzjxxMapper.queryJzjxxByZzid(zzid);
            if (CollectionUtils.isNotEmpty(bdcDzzzJzjxxDOList)) {
                for (BdcDzzzJzjxxDO bdcDzzzJzjxxDO:bdcDzzzJzjxxDOList) {
                    deleteBdcDzzzJzjxxFile(bdcDzzzJzjxxDO);
                }
                bdcDzzzJzjxxMapper.deleteBdcDzzzJzjxxByZzid(zzid);
            }
        }
    }

    @Override
    public void deleteBdcDzzzJzjxxFile(BdcDzzzJzjxxDO bdcDzzzJzjxxDO) {
        bdcDzzzFileConfigService.deleteFileByzzid(bdcDzzzJzjxxDO.getJzjid());
    }

    @Override
    public DzzzResponseModel createBdcDzzzJzjxx(BdcDzzzZzxx bdcDzzzZzxx, String dzzzsymd, String jzjzzz, Date jzjyxqjzsj) {
        Map<String, Object> result = null;
        if (null != bdcDzzzZzxx && StringUtils.isNotBlank(dzzzsymd)) {
            Date jzjzzsj = DateUtil.now();
            bdcDzzzZzxx.setJzjid(UUIDGenerator.generate());
            bdcDzzzZzxx.setJzjzzsj(jzjzzsj);
            bdcDzzzZzxx.setCjsj(jzjzzsj);
            bdcDzzzZzxx.setJzjzzsy(dzzzsymd);
            bdcDzzzZzxx.setJzjzzz(jzjzzz);
            bdcDzzzZzxx.setJzjyxqjzsj(jzjyxqjzsj);
            // pdf生成
            byte[] createPdfArr = bdcDzzzZzxxPdfService.createPdfBdcDzzz(bdcDzzzZzxx);
            if (null == createPdfArr) {
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_CREATE_PDF_ERROR.getCode(), null);
            }

            // 签章
            byte[] signArr = bdcDzzzSignConfigService.sign(bdcDzzzZzxx, createPdfArr, BdcDzzzPdfUtil.SIGN_COMPANY);
            if (null == signArr) {
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SIGNATURE_PDF_ERROR.getCode(), null);
            }

            // 上传文件中心
            String zzqzlj = bdcDzzzFileConfigService.sendFileCenter(signArr, bdcDzzzZzxx.getJzjid(), bdcDzzzZzxx.getBdcqzh(), Constants.BDC_DZZZ);
            if (StringUtils.isBlank(zzqzlj)) {
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_UPLOAD_FILE_CENTER_ERROR.getCode(), null);
            }
            bdcDzzzZzxx.setZzqzlj(zzqzlj);

            DzzzResponseModel insertBdcDzzzJzjxx = insertBdcDzzzJzjxx(bdcDzzzZzxx);
            if (StringUtils.equals(ResponseEnum.FALSE.getCode(), insertBdcDzzzJzjxx.getHead().getStatus())) {
                return bdcDzzzService.dzzzResponseFalse("加注件信息插入失败！", null);
            }

            result = new HashMap<>(5);
            result.put("signArr", signArr);
            result.put("zzqzlj", zzqzlj);
        }
        return bdcDzzzService.dzzzResponseSuccess(result);
    }

}
