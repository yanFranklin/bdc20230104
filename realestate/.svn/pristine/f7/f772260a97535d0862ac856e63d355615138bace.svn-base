package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzDzqzMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.down.DzzzDownRequestData;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.down.DzzzDownRequestModel;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.core.service.appear.BdcDzzzCityService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileCenterService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileConfigService;
import cn.gtmap.realestate.certificate.util.Base64Util;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/27
 */
@Service
public class BdcDzzzDownloadServiceImpl implements BdcDzzzDownloadService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzDownloadServiceImpl.class);
    @Autowired
    private BdcDzzzZzxxYsjService bdcDzzzZzxxYsjService;
    @Autowired
    private BdcDzzzJzjxxService bdcDzzzJzjxxService;
    @Autowired
    private BdcDzzzZzxxPdfService bdcDzzzZzxxPdfService;
    @Autowired
    private BdcDzzzZzxxService bdcDzzzZzxxService;
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzFileCenterService bdcDzzzFileCenterService;
    @Autowired
    private BdcDzzzSignConfigService bdcDzzzSignConfigService;
    @Autowired
    private BdcDzzzUseConditionService dzzzUseConditionService;
    @Autowired
    private BdcDzzzFileConfigService bdcDzzzFileConfigService;
    @Autowired
    private BdcDzzzCityService bdcDzzzCityService;
    @Autowired
    BdcDzzzDzqzMapper bdcDzzzDzqzMapper;
    @Value("${dzqz.version:}")
    private String dzqzVersion;


    @Override
    public DzzzResponseModel dzzzDownloadUrl(DzzzDownRequestModel dzzzDownRequestModel, HttpServletRequest request) {
        DzzzDownRequestData dataObject = dzzzDownRequestModel.getData();
        String zzbs = dataObject.getZzbs();
        if (StringUtils.isBlank(zzbs)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode()
                    , "zzbs is required fields cannot be empty or null");
        }

        Date jzjyxqjzsj = dataObject.getJzjyxqjzsj();
        if (null != jzjyxqjzsj && jzjyxqjzsj.before(DateUtil.now())) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), "加注件有效期截止时间必须大于等于当前时间");
        }

        String dzzzsymd = dataObject.getDzzzsymd();
        String jzjzzz = dataObject.getJzjzzz();
        BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcDzzzZzxxService.queryBdcDzzzZzxxByZzbs(zzbs);
        if (null == bdcDzzzZzxxDO) {
            logger.warn("当前证照标识未查询到签章信息{}", zzbs);
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getCode(), null);
        }
        //组织新的bdcDzzzZzxx，然后入库到加注件表
        BdcDzzzZzxx bdcDzzzZzxx = bdcDzzzZzxxService.getBdcDzzzZzxxFromBdcDzzzZzxxDO(bdcDzzzZzxxDO, new BdcDzzzZzxx());
        //完善电子证照数据
        bdcDzzzZzxx = bdcDzzzZzxxService.getPerfectBdcDzzzZzxx(bdcDzzzZzxx);

        String content = null;
        //先判断是否是使用目的  如果使用目的，那么认为获取的是原件，否则生成带使用目的的证照并保存加注件信息
        if (StringUtils.isNotEmpty(dzzzsymd)) {
            DzzzResponseModel createJzjxxModel = bdcDzzzJzjxxService.createBdcDzzzJzjxx(bdcDzzzZzxx,dzzzsymd,jzjzzz,jzjyxqjzsj);
            if (StringUtils.equals(ResponseEnum.FALSE.getCode(), createJzjxxModel.getHead().getStatus())) {
                return createJzjxxModel;
            }
            Map<String, Object> result = (Map<String, Object>)createJzjxxModel.getData();
            if (MapUtils.isNotEmpty(result)) {
                content = bdcDzzzFileCenterService.encodeFidByZzwjlj((String)result.get("zzqzlj"));
            }
        } else {
            content = bdcDzzzFileCenterService.encodeFidByZzwjlj(bdcDzzzZzxxDO.getZzqzlj());
        }
        if (StringUtils.isNotBlank(content)) {
            Map<String, String> data = new HashMap<>(8);
            data.put("contentType", "http");
            data.put("zzbs", zzbs);
            data.put("content", content);
            dzzzUseConditionService.insertDzzzUseCondition(bdcDzzzZzxx, (String) request.getAttribute(Constants.YYMC));
            return bdcDzzzService.dzzzResponseSuccess(data);
        }
        return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getCode(), null);
    }

    @Override
    public DzzzResponseModel dzzzDownloadFile(DzzzDownRequestModel dzzzDownRequestModel, HttpServletRequest request) {
        return dzzzDownloadFile(dzzzDownRequestModel, request, false);
    }

    public DzzzResponseModel dzzzDownloadFile(DzzzDownRequestModel dzzzDownRequestModel, HttpServletRequest request, boolean isCl) {
        DzzzDownRequestData dataObject = dzzzDownRequestModel.getData();
        String zzbs = dataObject.getZzbs();
        if (StringUtils.isBlank(zzbs)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), "zzbs is required fields cannot be empty or null");
        }

        Date jzjyxqjzsj = dataObject.getJzjyxqjzsj();
        if (null != jzjyxqjzsj && jzjyxqjzsj.before(DateUtil.now())) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), "加注件有效期截止时间必须大于等于当前时间");

        }

        String dzzzsymd = dataObject.getDzzzsymd();
        String jzjzzz = dataObject.getJzjzzz();
        BdcDzzzZzxxDO bdcDzzzZzxxDO = null;
        if (CommonConstantUtils.SYSTEM_VERSION_CZ.equals(dzqzVersion)) {
            //常州查询的是签章信息数据
            bdcDzzzZzxxDO = bdcDzzzDzqzMapper.queryBdcDzzzQzxxByZzbs(zzbs);
            if (null == bdcDzzzZzxxDO) {
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getCode(), null);
            }
            // 签章状态为2的时候才能下载
            if(!bdcDzzzZzxxDO.getQzzt().equals(2)){
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getCode(), null);
            }
        } else {
            bdcDzzzZzxxDO = bdcDzzzZzxxService.queryBdcDzzzZzxxByZzbs(zzbs);
        }
        if (null == bdcDzzzZzxxDO) {
            if (isCl) {
                // 不存在数据则先进行同步,再查询
                bdcDzzzCityService.syncDzzz(zzbs);
                bdcDzzzZzxxDO = bdcDzzzZzxxService.queryBdcDzzzZzxxByZzbs(zzbs);
                if (null == bdcDzzzZzxxDO) {
                    logger.warn("当前存量证照标识未查询到签章信息{}", zzbs);
                    return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getCode(), null);
                }
            } else {
                logger.warn("当前证照标识未查询到签章信息{}", zzbs);
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getCode(), null);
            }
        }
        //组织新的bdcDzzzZzxx，然后入库到加注件表
        BdcDzzzZzxx bdcDzzzZzxx = bdcDzzzZzxxService.getBdcDzzzZzxxFromBdcDzzzZzxxDO(bdcDzzzZzxxDO, new BdcDzzzZzxx());
        //完善电子证照数据
        bdcDzzzZzxx = bdcDzzzZzxxService.getPerfectBdcDzzzZzxx(bdcDzzzZzxx);

        String bdcDzzzZzxxYsjBase64 = null;
        String bdcDzzzZzxxPdfBase64 = null;
        if (StringUtils.isNotEmpty(dzzzsymd)) {

            DzzzResponseModel createJzjxxModel = bdcDzzzJzjxxService.createBdcDzzzJzjxx(bdcDzzzZzxx,dzzzsymd,jzjzzz,jzjyxqjzsj);
            if (StringUtils.equals(ResponseEnum.FALSE.getCode(), createJzjxxModel.getHead().getStatus())) {
                return createJzjxxModel;
            }
            Map<String, Object> result = (Map<String, Object>)createJzjxxModel.getData();
            if (MapUtils.isNotEmpty(result)) {
                bdcDzzzZzxxPdfBase64 = Base64Util.encodeByteToBase64Str((byte[])result.get("signArr"));
            }

        } else {
            byte[] result = bdcDzzzFileConfigService.downloadByZzwjlj(bdcDzzzZzxxDO.getZzqzlj());
            if (null != result) {
                bdcDzzzZzxxPdfBase64 = Base64Util.encodeByteToBase64Str(result);
            }

        }
        // 生成元数据
        String ysjXml = bdcDzzzZzxxYsjService.createBdcDzzzXml(bdcDzzzZzxx);
        if (StringUtils.isNotEmpty(ysjXml)) {
            bdcDzzzZzxxYsjBase64 = Base64Util.encodeStrToBase64Str(ysjXml);
        }
        if (StringUtils.isNotBlank(bdcDzzzZzxxYsjBase64) && StringUtils.isNotBlank(bdcDzzzZzxxPdfBase64)) {
            Map<String, Object> data = new HashMap<>(8);
            data.put("contentType", "base64");
            data.put("zzzmxx", bdcDzzzZzxxYsjBase64);
            data.put("zzbs", zzbs);
            data.put("content", bdcDzzzZzxxPdfBase64);
            dzzzUseConditionService.insertDzzzUseCondition(bdcDzzzZzxx, (String) request.getAttribute(Constants.YYMC));
            return bdcDzzzService.dzzzResponseSuccess(data);
        }

        return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getCode(), null);
    }

    @Override
    public DzzzResponseModel dzzzDownloadClFile(DzzzDownRequestModel dzzzDownRequestModel, HttpServletRequest request) {
        return dzzzDownloadFile(dzzzDownRequestModel, request, true);
    }

    @Override
    public DzzzResponseModel dzzzDownloadFileByUrl(String fid) {
        if (StringUtils.isBlank(fid)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), "fid is required fields cannot be empty or null");
        }

        byte[] result = bdcDzzzFileConfigService.download(Base64Util.decodeBase64StrToStr(fid));
        if (null != result) {
            Map<String, Object> data = new HashMap<>(4);
            data.put("contentType", "base64");
            data.put("content", Base64Util.encodeByteToBase64Str(result));
            return bdcDzzzService.dzzzResponseSuccess(data);
        }
        return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), "未下载到证照信息，请检查fid");
    }

    @Override
    public DzzzResponseModel dzzzDownloadCheck(String jsonString) {
        DzzzDownRequestData data = JSON.parseObject(jsonString, DzzzDownRequestModel.class).getData();
        if (null == data) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode()
                    , "data is required fields cannot be empty or null");
        }

        String zzbs = data.getZzbs();
        if (StringUtils.isBlank(zzbs)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode()
                    , "zzbs is required fields cannot be empty or null");
        }

        Date jzjyxqjzsj = data.getJzjyxqjzsj();
        if (null != jzjyxqjzsj && jzjyxqjzsj.before(DateUtil.now())) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode()
                    , "加注件有效期截止时间必须大于等于当前时间！");

        }
        return bdcDzzzService.dzzzResponseSuccess(ResponseEnum.SUCCESS.getCode(), null);
    }
}
