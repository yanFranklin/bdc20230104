package cn.gtmap.realestate.certificate.core.service.impl.appear;


import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzJzjxxMapper;
import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzMlxxMapper;
import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzUseConditionMapper;
import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzZzxxMapper;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.domain.*;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.SyncDzzzBo;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.core.service.appear.BdcDzzzAppearService;
import cn.gtmap.realestate.certificate.core.service.create.BdcDzzzJiangSuCreateService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileConfigService;
import cn.gtmap.realestate.certificate.core.service.impl.zzzx.BdcDzzzZzzxJiangSuServiceImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019-9-2
 */
@Service
public class BdcDzzzAppearServiceImpl implements BdcDzzzAppearService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BdcDzzzZzxxPdfService bdcDzzzZzxxPdfService;
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzZzzxConfigService bdcDzzzZzzxConfigService;
    @Resource
    private BdcDzzzZzxxMapper bdcDzzzZzxxMapper;
    @Autowired
    private BdcDzzzWaterMarkService bdcDzzzWaterMarkService;
    @Autowired
    private BdcDzzzZzxxService bdcDzzzZzxxService;
    @Autowired
    private BdcDzzzYwxxService bdcDzzzYwxxService;
    @Autowired
    private BdcDzzzZzxxCzztService bdcDzzzZzxxCzztService;
    @Autowired
    private BdcDzzzMlxxMapper bdcDzzzMlxxMapper;
    @Autowired
    private BdcDzzzZzzxJiangSuServiceImpl bdcDzzzZzzxJiangSuService;
    /*@Autowired
    private BdcDzzzRocketMqService bdcDzzzRocketMqService;*/
    @Autowired
    private BdcDzzzJiangSuCreateService bdcDzzzJiangSuCreateService;
    @Autowired
    private BdcDzzzJzjxxMapper bdcDzzzJzjxxMapper;
    @Autowired
    private BdcDzzzUseConditionMapper bdcDzzzUseConditionMapper;

    @Override
    public DzzzResponseModel zzpdf(BdcDzzzZzxx bdcDzzzZzxx) {

        // 验证
        DzzzResponseModel checkBdcDzzzZzxxModel = bdcDzzzZzxxPdfService.checkBdcDzzz(bdcDzzzZzxx);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkBdcDzzzZzxxModel.getHead().getStatus())) {
            return checkBdcDzzzZzxxModel;
        }

        if (bdcDzzzZzxx.isSfyb()) {
            //bdcDzzzRocketMqService.sendBdcDzzzZzxx(bdcDzzzZzxx);
        } else {
            DzzzResponseModel createBdcDzzzZzxxModel = bdcDzzzJiangSuCreateService.createOne(bdcDzzzZzxx);
            if (StringUtils.equals(ResponseEnum.FALSE.getCode(), createBdcDzzzZzxxModel.getHead().getStatus())) {
                return createBdcDzzzZzxxModel;
            }
        }

        return bdcDzzzZzxxPdfService.insertBdcDzzz(bdcDzzzZzxx);
    }

    @Override
    public DzzzResponseModel syncDzzzDownloadInfo(String zzbs) {
        JSONObject result = new JSONObject();
        BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcDzzzZzxxService.queryBdcDzzzZzxxByZzbs(zzbs);
        if (null == bdcDzzzZzxxDO) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getCode(), "bdcDzzzZzxxDO is null");
        }
        result.put("bdcqzh",bdcDzzzZzxxDO.getBdcqzh());

        List<BdcDzzzJzjxxDO> jzjxxDOList = bdcDzzzJzjxxMapper.queryJzjxxByZzid(bdcDzzzZzxxDO.getZzid());
        if (CollectionUtils.isNotEmpty(jzjxxDOList)) {
            result.put("syncDzzzJzjxx", JSON.toJSONString(jzjxxDOList));
        }

        List<BdcDzzzUseConditionDo> conditionDoList = bdcDzzzUseConditionMapper.getUseConditionByZzbs(zzbs);
        if (CollectionUtils.isNotEmpty(conditionDoList)) {
            result.put("syncDzzzUseCondition", JSON.toJSONString(conditionDoList));
        }

        return bdcDzzzService.dzzzResponseSuccess(ResponseEnum.SUCCESS.getMsg(), JSON.toJSONString(result));
    }

    @Override
    public DzzzResponseModel syncDzzz(String zzbs) {
        SyncDzzzBo syncDzzzBo = new SyncDzzzBo();

        BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcDzzzZzxxService.queryBdcDzzzZzxxByZzbs(zzbs);
        if (null == bdcDzzzZzxxDO) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getCode(), "bdcDzzzZzxxDO is null");
        }
        syncDzzzBo.setBdcDzzzZzxxDO(bdcDzzzZzxxDO);

        BdcDzzzYwxxDo bdcDzzzYwxxDo = bdcDzzzYwxxService.queryYwxxByZzid(bdcDzzzZzxxDO.getZzid());
        if (null == bdcDzzzYwxxDo) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getCode(), "bdcDzzzYwxxDo is null");
        }
        syncDzzzBo.setBdcDzzzYwxxDo(bdcDzzzYwxxDo);

        List<BdcDzzzZzxxCzztDo> bdcDzzzZzxxCzztDoList = bdcDzzzZzxxCzztService.queryBdcDzzzZzxxCzztDoByZzid(bdcDzzzZzxxDO.getZzid());
        if (CollectionUtils.isEmpty(bdcDzzzZzxxCzztDoList)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getCode(), "BdcDzzzZzxxCzztDo is null");
        }
        syncDzzzBo.setBdcDzzzZzxxCzztDoList(bdcDzzzZzxxCzztDoList);


        BdcDzzzMlxxDO bdcDzzzMlxxDO = bdcDzzzMlxxMapper.queryBdcDzzzMlxxByZzid(bdcDzzzZzxxDO.getZzid());
        if (null == bdcDzzzMlxxDO) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getCode(), "bdcDzzzMlxxDO is null");
        }
        syncDzzzBo.setBdcDzzzMlxxDO(bdcDzzzMlxxDO);

        return bdcDzzzService.dzzzResponseSuccess(ResponseEnum.SUCCESS.getMsg(), JSON.toJSONString(syncDzzzBo));
    }

    @Override
    public DzzzResponseModel syncDzzzPdf(String zzids) {
        if (StringUtils.isBlank(zzids)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getMsg(), null);
        }

        String[] zzidArr = zzids.split(",");
        JSONArray resultJoa = new JSONArray();
        for (String zzid : zzidArr) {
            JSONObject resultJob = new JSONObject();
            BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcDzzzZzxxService.queryBdcDzzzZzxx(zzid);
            if (null == bdcDzzzZzxxDO) {
                logger.error("市级同步省级证照文件,证照id：{}，数据同步错误:未找到证照信息" + zzid);
                continue;
            }
            resultJob.put("zzid",zzid);
            resultJob.put("bdcqzh",bdcDzzzZzxxDO.getBdcqzh());
            resultJob.put("zzqzlj",bdcDzzzZzxxDO.getZzqzlj());
            resultJoa.add(resultJob);
        }
        return bdcDzzzService.dzzzResponseSuccess(ResponseEnum.SUCCESS.getMsg(), JSON.toJSONString(resultJoa));
    }

    @Override
    public DzzzResponseModel syncDzzzInfo(String cityDwdm){
        Map param = new HashMap(3);
        param.put("tbzt",1);
        if (StringUtils.isNotBlank(cityDwdm)) {
            param.put("dwdmArr",cityDwdm.split(","));
        }
        List<BdcDzzzZzxxDO> zzxxDOList = bdcDzzzZzxxMapper.getBdcDzzzZzxxDoByMap(param);
        List<Map<String,Object>> resultList = new ArrayList<>(10);
        if (CollectionUtils.isNotEmpty(zzxxDOList)) {
            for (BdcDzzzZzxxDO zzxxDO : zzxxDOList) {
                Map<String,Object> resultMap = new HashMap<>(2);
                resultMap.put("zzbs",zzxxDO.getZzbs());
                resultList.add(resultMap);
            }
        }
        return bdcDzzzService.dzzzResponseSuccess(ResponseEnum.SUCCESS.getMsg(), resultList);
    }

}
