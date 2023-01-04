package cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.exchange.*;
import cn.gtmap.realestate.common.core.domain.exchange.sjpt.BdcExchangeZddz;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.ResponseHead;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzzzFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.domain.BdcExchangeDefaultValueDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthYwxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.ZttGyQlrDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.*;
import cn.gtmap.realestate.exchange.core.mapper.server.*;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.impl.YthCfInitParamServiceImpl;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.impl.YthDyaInitParamServiceImpl;
import cn.gtmap.realestate.exchange.service.national.BdcExchangeZddzService;
import cn.gtmap.realestate.exchange.service.national.access.AccessDefaultValueService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public abstract class AbstractYthInitParamService implements YthInitParamService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractYthInitParamService.class);

    @Autowired
    private DjfDjSzMapper djfDjSzMapper;
    @Autowired
    private DjfDjFzMapper djfDjFzMapper;
    @Autowired
    private DjfDjShMapper djfDjShMapper;
    @Autowired
    private DjtDjSlsqMapper djtDjSlsqMapper;
    @Autowired
    private DjfDjSfMapper djfDjSfMapper;
    @Autowired
    private BdcdjMapper bdcdjMapper;
    @Autowired
    private BdcYthyrcfMapper bdcYthyrcfMapper;
    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;
    @Autowired
    private BdcDzzzFeignService bdcDzzzFeignService;
    @Autowired
    private BdcExchangeZddzService bdcExchangeZddzService;
    @Autowired
    private AccessDefaultValueService accessDefaultValueService;
    @Autowired
    private YthInitParamServiceChoose ythInitParamServiceChoose;
    /**
     * 获取token的应用名称
     */
    @Value("${eCertificate.tokenYymc:bdcdj}")
    private String tokenYymc;

    @Override
    public void initIssueCertificateCommonRequestParam(YthYwxxDTO ythYwxxDTO, BdcXmDO bdcXmDO) {
        Map<String, Object> initSqlParam = initSqlParam(bdcXmDO);
        Map<String, Object> queryBdcXxMap = new HashMap<>();
        queryBdcXxMap.put("xmid", bdcXmDO.getXmid());
        //缮证信息
        List<DjfDjSzOldDO> djfDjSzDOS = djfDjSzMapper.queryDjfDjSzListOld(initSqlParam);
        if (CollectionUtils.isNotEmpty(djfDjSzDOS)) {
            if (ythYwxxDTO.getDjfDjSzDO() != null) {
                ythYwxxDTO.getDjfDjSzDO().addAll(djfDjSzDOS);
            } else {
                ythYwxxDTO.setDjfDjSzDO(djfDjSzDOS);
            }
        }
        //判断是否需要发证信息
        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcdjMapper.getSffzflagByXmid(queryBdcXxMap);
        if (bdcCshFwkgSlDO == null || bdcCshFwkgSlDO.getSfsczs() == 1) {
            //发证信息
            List<DjfDjFzOldDO> djfDjFzDOS = djfDjFzMapper.queryDjfDjFzListOld(initSqlParam);
            if (CollectionUtils.isNotEmpty(djfDjFzDOS)) {
                if (ythYwxxDTO.getDjfDjFzDO() != null) {
                    ythYwxxDTO.getDjfDjFzDO().addAll(djfDjFzDOS);
                } else {
                    ythYwxxDTO.setDjfDjFzDO(djfDjFzDOS);
                }
            }
        }
        //权利人信息
        List<ZttGyQlrDTO> zttGyQlrDOS = bdcYthyrcfMapper.queryZttGyQlrListForYcYth(initSqlParam);
        String handleServiceKey = ythInitParamServiceChoose.getHandleServiceKey(bdcXmDO);
        if (CollectionUtils.isNotEmpty(zttGyQlrDOS)) {
            zttGyQlrDOS.forEach(bdcQlrDO -> {
                String qlrlb = bdcQlrDO.getQlrlb();
                if (StringUtils.isNotBlank(qlrlb)) {
                    if (YthCfInitParamServiceImpl.CF_YTH_QL_INIT_PARAM_SERVICE_KEY.equals(handleServiceKey)) {
                        if (CommonConstantUtils.QLRLB_QLR.equals(qlrlb)) {
                            bdcQlrDO.setJs("3010");
                        }
                        if (CommonConstantUtils.QLRLB_YWR.equals(qlrlb)) {
                            bdcQlrDO.setJs("3011");
                        }
                    } else if (YthDyaInitParamServiceImpl.DYA_YTH_QL_INIT_PARAM_SERVICE_KEY.equals(handleServiceKey)) {
                        if (CommonConstantUtils.QLRLB_QLR.equals(qlrlb)) {
                            bdcQlrDO.setJs("2010");
                        }
                        if (CommonConstantUtils.QLRLB_YWR.equals(qlrlb)) {
                            bdcQlrDO.setJs("2011");
                        }
                    } else {
                        if (CommonConstantUtils.QLRLB_QLR.equals(qlrlb)) {
                            bdcQlrDO.setJs("1010");
                        }
                        if (CommonConstantUtils.QLRLB_YWR.equals(qlrlb)) {
                            bdcQlrDO.setJs("1011");
                        }
                    }
                }
            });
        }

//        List<ZttGyQlrDO> zttGyQlrDOS = zttGyQlrMapper.queryZttGyQlrList(initSqlParam);
        if (CollectionUtils.isNotEmpty(zttGyQlrDOS)) {
            if (ythYwxxDTO.getZttGyQlrDO() != null) {
                ythYwxxDTO.getZttGyQlrDO().addAll(zttGyQlrDOS);
            } else {
                ythYwxxDTO.setZttGyQlrDO(zttGyQlrDOS);
            }

        }
        List<BdcZsDO> zsbsxxByXmid = bdcdjMapper.getZsbsxxByXmid(queryBdcXxMap);
        if (CollectionUtils.isNotEmpty(zsbsxxByXmid)) {
            List<FjFOldDO> fjFDOS = getFjFDOList(zsbsxxByXmid);
            if (ythYwxxDTO.getFjFDOList() != null) {
                ythYwxxDTO.getFjFDOList().addAll(fjFDOS);
            } else {
                ythYwxxDTO.setFjFDOList(fjFDOS);
            }

        }
    }

    @NotNull
    private List<FjFOldDO> getFjFDOList(List<BdcZsDO> zsbsxxByXmid) {
        List<FjFOldDO> fjFDOS = new ArrayList<>();
        for (BdcZsDO bdcZsDO : zsbsxxByXmid) {

            Map paramMap = new HashMap();
            paramMap.put(Constants.KEY_ZZBS, bdcZsDO.getZzbs());
            Map dataMap = new HashMap();
            dataMap.put(Constants.KEY_DATA, paramMap);
            DzzzResponseModel dzzzResponseModel = bdcDzzzFeignService.zzxxxz2(tokenYymc, JSONObject.toJSONString(dataMap));
            FjFOldDO fjFDO = new FjFOldDO();
            if (null != dzzzResponseModel) {
                ResponseHead head = dzzzResponseModel.getHead();
                Object data = dzzzResponseModel.getData();
                if (StringUtils.equals(Constants.STATUS_SUCCESS, head.getStatus())) {
//                    LOGGER.info("电子证照下载返回 Data 数据：{}", dzzzResponseModel.getData());
                    if (data instanceof Map) {
                        fjFDO.setFjnr(MapUtils.getString((Map) data, Constants.KEY_CONTENT));
                    }
                    if (data instanceof String) {
                        JSONObject jsonObject = JSON.parseObject((String) data);
                        fjFDO.setFjnr(jsonObject.get(Constants.KEY_CONTENT).toString());
                    }
                }
            }
            fjFDO.setFjmc(bdcZsDO.getBdcqzh());
            fjFDO.setFjlx("PDF");
            fjFDOS.add(fjFDO);
        }
        return fjFDOS;
    }

    @Override
    public void initYthParamZddzAndDefaultValue(YthYwxxDTO ythYwxxDTO, BdcXmDO bdcXmDO, Map<String, List<BdcExchangeZddz>> bdcExchangeZddzMap, List<BdcExchangeDefaultValueDO> defaultValueDOList) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = ythYwxxDTO.getClass().getMethods();
        List dataList = new ArrayList();
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                Object obj = method.invoke(ythYwxxDTO);
                if (obj != null && obj instanceof List) {
                    dataList.addAll((List) obj);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (Object data : dataList) {
                if (CollectionUtils.isNotEmpty(defaultValueDOList) && data != null) {
                    bdcExchangeZddzService.doObj(data, bdcExchangeZddzMap, null);
                    accessDefaultValueService.setObjDefaulValue(data, defaultValueDOList, bdcXmDO.getGzldyid(), bdcXmDO.getDjxl());
                }
            }
        }
    }

    @Override
    public void initYthShxxRequestParam(YthYwxxDTO ythYwxxDTO, BdcXmDO bdcXmDO) {
        Map<String, Object> queryParam = initSqlParam(bdcXmDO);
        List<DjfDjShOldDO> djfDjShDOList = this.djfDjShMapper.queryDjfDjShListOld(queryParam);
        if (CollectionUtils.isNotEmpty(djfDjShDOList)) {
            if (Objects.nonNull(ythYwxxDTO.getDjfDjShDO())) {
                ythYwxxDTO.getDjfDjShDO().addAll(djfDjShDOList);
            } else {
                ythYwxxDTO.setDjfDjShDO(djfDjShDOList);
            }
        }
    }

    @Override
    public void initYthSlxxRequestParam(YthYwxxDTO ythYwxxDTO, BdcXmDO bdcXmDO) {
        List<DjtDjSlsqOldDO> djtDjSlsqDOList = this.djtDjSlsqMapper.queryDjtDjSlsqListOld(initSqlParam(bdcXmDO));
        LOGGER.info("受理申请信息列表size：{}", djtDjSlsqDOList.size());
        if (CollectionUtils.isNotEmpty(djtDjSlsqDOList)) {
            if (Objects.nonNull(ythYwxxDTO.getDjtDjSlsqDO())) {
                ythYwxxDTO.getDjtDjSlsqDO().addAll(djtDjSlsqDOList);
            } else {
                ythYwxxDTO.setDjtDjSlsqDO(djtDjSlsqDOList);
            }
        }
    }

    @Override
    public void initYthSlxxForTjRequestParam(YthYwxxDTO ythYwxxDTO, BdcXmDO bdcXmDO) {
        List<DjtDjSlsqOldDO> djtDjSlsqDOList = this.djtDjSlsqMapper.queryDjtDjSlsqListForTjOld(initSqlParam(bdcXmDO));
        LOGGER.info("退件受理申请信息列表size：{}", djtDjSlsqDOList.size());
        if (CollectionUtils.isNotEmpty(djtDjSlsqDOList)) {
            if (Objects.nonNull(ythYwxxDTO.getDjtDjSlsqDO())) {
                ythYwxxDTO.getDjtDjSlsqDO().addAll(djtDjSlsqDOList);
            } else {
                ythYwxxDTO.setDjtDjSlsqDO(djtDjSlsqDOList);
            }
        }
    }

    /**
     * 初始化盐城一体化收费信息
     **/
    @Override
    public void initYthSfxxRequestParam(YthYwxxDTO ythYwxxDTO, BdcXmDO bdcXmDO,Boolean isFilterSfxx) {
        List<DjfDjSfOldDO> djfDjSfDOList = this.djfDjSfMapper.queryDjfDjSfListOld(initSqlParam(bdcXmDO));
        if (CollectionUtils.isNotEmpty(djfDjSfDOList)) {
            if (Objects.nonNull(ythYwxxDTO.getDjfDjSfDO())) {
                ythYwxxDTO.getDjfDjSfDO().addAll(djfDjSfDOList);
            } else {
                ythYwxxDTO.setDjfDjSfDO(djfDjSfDOList);
            }
        }
    }

    @Override
    public Map<String, Object> initSqlParam(BdcXmDO bdcXmDO) {
        Map<String, Object> sqlParam = new HashMap<>(1);
        sqlParam.put("ywh", bdcXmDO.getXmid());
        return sqlParam;
    }


}
