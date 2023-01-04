package cn.gtmap.realestate.exchange.service.impl.inf.wwsq;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.exchange.core.convert.ZsyzConvert;
import cn.gtmap.realestate.exchange.core.dto.wwsq.zsyz.request.ZsyzRequestBody;
import cn.gtmap.realestate.exchange.core.dto.wwsq.zsyz.response.ZsyzCfxxResponseData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.zsyz.response.ZsyzDyxxResponseData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.zsyz.response.ZsyzResponseData;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.service.inf.wwsq.ZsyzService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-27
 * @description 证书验证相关服务
 */
@Service
public class ZsyzServiceImpl implements ZsyzService {

    private static final Logger logger = LoggerFactory.getLogger(ZsyzService.class);

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;

    @Autowired
    private ZsyzConvert zsyzConvert;

    @Autowired
    private BdcXmMapper bdcXmMapper;

    @Value("${data.version}")
    private String dataVersion;

    /**
     * @param zsid
     * @return cn.gtmap.realestate.common.core.domain.BdcXmDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据证书ID查询项目实体
     */
    @Override
    public List<ZsyzResponseData> queryZsyzListByZsid(String zsid) {
        List<ZsyzResponseData> resultList = new ArrayList<>();
        if (StringUtils.isBlank(zsid)) {
            return resultList;
        }
        Example example = new Example(BdcXmZsGxDO.class);
        example.createCriteria().andEqualTo("zsid", zsid);
        List<BdcXmZsGxDO> bdcXmZsGxDOList = entityMapper.selectByExample(example);
        Example zsxxExample = new Example(BdcZsDO.class);
        zsxxExample.createCriteria().andEqualTo("zsid", zsid);
        List<BdcZsDO> bdcZsInfoList = entityMapper.selectByExample(zsxxExample);
        List<String> bdcdyhList = new ArrayList<>(4);
        if (CollectionUtils.isNotEmpty(bdcXmZsGxDOList) && CollectionUtils.isNotEmpty(bdcZsInfoList)) {
            Map<String, BdcXmDO> xmidMap = new HashMap<>(bdcXmZsGxDOList.size());
            for (BdcXmZsGxDO bdcXmZsGxDO : bdcXmZsGxDOList) {
                if (StringUtils.isNotBlank(bdcXmZsGxDO.getXmid())) {
                    BdcXmQO bdcXmQO = new BdcXmQO();
                    bdcXmQO.setXmid(bdcXmZsGxDO.getXmid());
                    // 查询 项目 列表
                    List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isNotEmpty(bdcXmList)) {
                        ZsyzResponseData response = new ZsyzResponseData();
                        BdcXmDO bdcXmDO = bdcXmList.get(0);
                        if (bdcXmDO.getQszt() != null
                                && Constants.QSZT_XS == bdcXmDO.getQszt()) {
                            dozerBeanMapper.map(bdcXmDO, response);
                            // 循环项目列表 查询权利
                            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
                            if (bdcQl instanceof BdcFdcqDO) {
                                dozerBeanMapper.map(bdcQl, response);
                            }
                            // 证号 与证书 单独处理
                            response.setCertid(zsid);
                            // 处理权利人
                            setQlrmcAndCqzh(response);
                            //处理宗地代码，地籍区，地籍子区
                            String bdcdyh = bdcXmDO.getBdcdyh();
                            response.setZddm(bdcdyh.substring(0,19));
                            response.setDjq(bdcdyh.substring(0,9));
                            response.setDjzq(bdcdyh.substring(0,12));
                            response.setQlqtzk(bdcZsInfoList.get(0).getQlqtzk());
                            resultList.add(response);
                            bdcdyhList.add(response.getBdcdyh());
                            xmidMap.put(response.getBdcdyh(), bdcXmDO);
                        }
                    }
                }
            }
            List<BdcdyhZtResponseDTO> bdcdyhZtDTOList = bdcdyZtFeignService.commonListBdcdyhZtBybdcdyh(bdcdyhList, "");
            Map<String, BdcdyhZtResponseDTO> bdcdyhZtDTOMap = bdcdyhZtDTOList.stream().collect(Collectors.toMap(BdcdyhZtResponseDTO::getBdcdyh, bdcdyhZtResponseDTO -> bdcdyhZtResponseDTO));
            BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
            bdcZdDsfzdgxDO.setZdbs("DSF_ZD_QSZT");
            bdcZdDsfzdgxDO.setBdczdz("1");
            bdcZdDsfzdgxDO.setDsfxtbs("WWSQ");
            BdcZdDsfzdgxDO qsztZd = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
            resultList.forEach(zsyzResponseData -> {
                //当限制状态不正常时
                if (!checkBdcdyIsNormal(bdcdyhZtDTOMap.get(zsyzResponseData.getBdcdyh()))) {
                    //查询对应的查封信息和抵押信息
                    if (dataVersion.equals("yancheng")) {
                        Example cfExample = new Example(BdcCfDO.class);
                        cfExample.createCriteria().andEqualTo("bdcdyh", zsyzResponseData.getBdcdyh());
                        List<BdcCfDO> bdcCfDOList = entityMapper.selectByExample(cfExample);
                        List<ZsyzCfxxResponseData> zsyzCfxxResponseDataList = zsyzConvert.getZsyzCfxxResponseDataListByBdcCfDOList(bdcCfDOList, qsztZd);
                        Example dyaExample = new Example(BdcDyaqDO.class);
                        dyaExample.createCriteria().andEqualTo("bdcdyh", zsyzResponseData.getBdcdyh());
                        List<BdcDyaqDO> bdcDyaqDOList = entityMapper.selectByExample(dyaExample);
                        List<ZsyzDyxxResponseData> zsyzDyxxResponseDataList = zsyzConvert.getZsyzDyxxResponseDataListByBdcDyaqDOList(bdcDyaqDOList, qsztZd);
                        if (CollectionUtils.isNotEmpty(zsyzCfxxResponseDataList)) {
                            zsyzCfxxResponseDataList.forEach(zsyzCfxxResponseData -> zsyzCfxxResponseData.setCqzh(xmidMap.get(zsyzResponseData.getBdcdyh()).getBdcqzh()));
//                            zsyzResponseData.setCfxx(JSON.toJSONString(zsyzCfxxResponseDataList));
                            zsyzResponseData.setCfxx(zsyzCfxxResponseDataList);
                        }
                        if (CollectionUtils.isNotEmpty(zsyzDyxxResponseDataList)) {
                            zsyzDyxxResponseDataList.forEach(
                                    zsyzDyxxResponseData -> zsyzDyxxResponseData.setCqzh(xmidMap.get(zsyzResponseData.getBdcdyh()).getBdcqzh())
                            );
//                            zsyzResponseData.setDyxx(JSON.toJSONString(zsyzDyxxResponseDataList));
                            zsyzResponseData.setDyxx(zsyzDyxxResponseDataList);

                        }
                    } else {

                        if (StringUtils.isNotBlank(xmidMap.get(zsyzResponseData.getBdcdyh()).getXmid())) {
                            Map<String, Object> xmlsgxMap = new HashMap<>();
                            xmlsgxMap.put("xmid", xmidMap.get(zsyzResponseData.getBdcdyh()).getXmid());
                            List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcXmMapper.queryBdcXmLsgxByyxmidAndZxyqlIsZeroOrNvl(xmlsgxMap);
                            if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                                Example cfExample = new Example(BdcCfDO.class);
                                List<Object> xmidList = bdcXmLsgxDOList.stream().map(BdcXmLsgxDO::getXmid).collect(Collectors.toList());
                                cfExample.createCriteria().andIn("xmid", xmidList);
                                List<BdcCfDO> bdcCfDOList = entityMapper.selectByExample(cfExample);
                                List<ZsyzCfxxResponseData> zsyzCfxxResponseDataList = zsyzConvert.getZsyzCfxxResponseDataListByBdcCfDOList(bdcCfDOList, qsztZd);
                                Example dyaExample = new Example(BdcDyaqDO.class);
                                dyaExample.createCriteria().andIn("xmid", xmidList);
                                List<BdcDyaqDO> bdcDyaqDOList = entityMapper.selectByExample(dyaExample);
                                List<ZsyzDyxxResponseData> zsyzDyxxResponseDataList = zsyzConvert.getZsyzDyxxResponseDataListByBdcDyaqDOList(bdcDyaqDOList, qsztZd);
                                if (CollectionUtils.isNotEmpty(zsyzCfxxResponseDataList)) {
                                    zsyzCfxxResponseDataList.forEach(
                                            zsyzCfxxResponseData -> zsyzCfxxResponseData.setCqzh(xmidMap.get(zsyzResponseData.getBdcdyh()).getBdcqzh())
                                    );
//                            zsyzResponseData.setCfxx(JSON.toJSONString(zsyzCfxxResponseDataList));
                                    zsyzResponseData.setCfxx(zsyzCfxxResponseDataList);
                                }
                                if (CollectionUtils.isNotEmpty(zsyzDyxxResponseDataList)) {
                                    zsyzDyxxResponseDataList.forEach(
                                            zsyzDyxxResponseData -> zsyzDyxxResponseData.setCqzh(xmidMap.get(zsyzResponseData.getBdcdyh()).getBdcqzh())
                                    );
//                            zsyzResponseData.setDyxx(JSON.toJSONString(zsyzDyxxResponseDataList));
                                    zsyzResponseData.setDyxx(zsyzDyxxResponseDataList);

                                }
                            }
                        }
                    }
                }
            });
        }

        return resultList;
    }

    /**
     * 校验项目的限制状态
     *
     * @param bdcdyhZtResponseDTO
     * @return
     */
    private boolean checkBdcdyIsNormal(BdcdyhZtResponseDTO bdcdyhZtResponseDTO) {
        if(bdcdyhZtResponseDTO ==null) {
            return false;
        }
        if (BooleanUtils.toBoolean(bdcdyhZtResponseDTO.getCf())) {
            return false;
        }
        if (BooleanUtils.toBoolean(bdcdyhZtResponseDTO.getYcf())) {
            return false;
        }
        if (BooleanUtils.toBoolean(bdcdyhZtResponseDTO.getDya())) {
            return false;
        }
        if (BooleanUtils.toBoolean(bdcdyhZtResponseDTO.getYdya())) {
            return false;
        }
        if (BooleanUtils.toBoolean(bdcdyhZtResponseDTO.getYy())) {
            return false;
        }
        if (BooleanUtils.toBoolean(bdcdyhZtResponseDTO.getYg())) {
            return false;
        }
        if (BooleanUtils.toBoolean(bdcdyhZtResponseDTO.getDyi())) {
            return false;
        }
        return !BooleanUtils.toBoolean(bdcdyhZtResponseDTO.getSd());
    }

    /**
     * @param response
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 向证书响应实体保存权利人名称 和 产权证号
     */
    private void setQlrmcAndCqzh(ZsyzResponseData response) {
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        String zsid = response.getCertid();
        if (StringUtil.isBlank(zsid)) {
            return;
        }
        bdcQlrQO.setZsid(zsid);
        List<BdcQlrDO> bdcQlrList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        StringBuilder qlrSb = new StringBuilder("");
        StringBuilder qlrzjhSb = new StringBuilder("");
        if (CollectionUtils.isNotEmpty(bdcQlrList)) {
            for (BdcQlrDO bdcQlrDO : bdcQlrList) {
                if (StringUtil.isNotEmpty(bdcQlrDO.getQlrmc())) {
                    qlrSb.append(bdcQlrDO.getQlrmc()).append(",");
                    qlrzjhSb.append(bdcQlrDO.getZjh()).append(",");
                }
            }
        }
        String qlr = qlrSb.toString();
        if (qlr.endsWith(",")) {
            qlr = qlr.substring(0, qlr.length() - 1);
            response.setQlrmc(qlr);
        }

        String qlrzjh = qlrzjhSb.toString();
        if (qlrzjh.endsWith(",")) {
            qlrzjh = qlrzjh.substring(0, qlrzjh.length() - 1);
            response.setQlrzjhm(qlrzjh);
        }
        BdcZsDO zsDO = bdcZsFeignService.queryBdcZs(zsid);
        if (zsDO != null) {
            response.setCqzh(zsDO.getBdcqzh());
            if (zsDO.getGyfs() != null) {
                String value = "";
                List<Map> zdMapList = bdcZdFeignService.queryBdcZd("gyfs");
                for (Map map : zdMapList) {
                    if (StringUtils.equals(zsDO.getGyfs().toString(), MapUtils.getString(map, "DM"))) {
                        value = map.get("MC").toString();
                        break;
                    }
                }
                response.setGyfs(value);
            }
        }
    }

    /**
     * @param cqzh
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.wwsq.zsyz.response.ZsyzResponseData>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据产权证号查询
     */
    @Override
    public List<ZsyzResponseData> queryZsyzListByCqzh(String cqzh, String cqzhmh) {
        List<ZsyzResponseData> resultList = new ArrayList<>();
        BdcZsQO zsQO = new BdcZsQO();
        if (StringUtils.isNotBlank(cqzh)) {
            zsQO.setBdcqzh(cqzh);
        }
        //盐城需求38032 要求添加bdcqzh字段全模糊查询
        if (StringUtils.isNotBlank(cqzhmh)) {
            zsQO.setBdcqzhmh(cqzhmh);
        }
        List<BdcZsDO> zsList = bdcZsFeignService.listBdcZs(zsQO);
        if (CollectionUtils.isNotEmpty(zsList)) {
            for (BdcZsDO bdcZsDO : zsList) {
                resultList.addAll(queryZsyzListByZsid(bdcZsDO.getZsid()));
            }
        }
        return resultList;
    }

    /**
     * @param body
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.wwsq.zsyz.response.ZsyzResponseData>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根·据证书ID 或者产权证号 查询 证书 （存在一证多房场景）
     */
    @Override
    public List<ZsyzResponseData> queryZs(ZsyzRequestBody body) {
        if (StringUtils.isNotBlank(body.getCertId())) {
            // 证书ID 不为空 使用证书ID 查询
            return queryZsyzListByZsid(body.getCertId());
        } else if (StringUtils.isNotBlank(body.getCqzh())) {
            // 产权证号不为空 使用产权证号查询
            return queryZsyzListByCqzh(body.getCqzh(), null);
        } else if (StringUtils.isNotBlank(body.getCqzhmh()) || StringUtils.isNotBlank(body.getBdcdyh()) || StringUtils.isNotBlank(body.getZl())
                || StringUtils.isNotBlank(body.getQlrmc())) {
            return queryZsyzListByParam( body);
        }
        return null;
    }

    @Override
    public List<ZsyzResponseData> queryZsyzListByParam(ZsyzRequestBody body) {
        List<ZsyzResponseData> resultList = new ArrayList<>();
        BdcZsQO zsQO = new BdcZsQO();

        //盐城需求38032 要求添加bdcqzh字段全模糊查询
        if (StringUtils.isNotBlank(body.getCqzhmh())) {
            zsQO.setBdcqzhmh(body.getCqzhmh());
        }
        if (StringUtils.isNotBlank(body.getZl())) {
            zsQO.setZljq(body.getZl());
        }
        if (StringUtils.isNotBlank(body.getQlrmc())) {
            zsQO.setQlr(body.getQlrmc());
        }
        if (StringUtils.isNotBlank(body.getBdcdyh())) {
            zsQO.setBdcdyh(body.getBdcdyh());
        }
        List<BdcZsDO> zsList = bdcZsFeignService.listBdcZs(zsQO);
        if (CollectionUtils.isNotEmpty(zsList)) {
            for (BdcZsDO bdcZsDO : zsList) {
                resultList.addAll(queryZsyzListByZsid(bdcZsDO.getZsid()));
            }
        }
        return resultList;
    }
}
