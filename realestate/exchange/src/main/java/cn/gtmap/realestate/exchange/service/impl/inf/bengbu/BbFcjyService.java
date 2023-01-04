package cn.gtmap.realestate.exchange.service.impl.inf.bengbu;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.building.FwHsHouseIdDTO;
import cn.gtmap.realestate.common.core.dto.etl.EtlClfHtbaListDTo;
import cn.gtmap.realestate.common.core.dto.etl.EtlClfHtbaResponseDTo;
import cn.gtmap.realestate.common.core.dto.etl.EtlSpfHtbaListDTo;
import cn.gtmap.realestate.common.core.dto.etl.EtlSpfHtbaResponseDTo;
import cn.gtmap.realestate.common.core.dto.exchange.bengbu.HtbaxxParamRequestDTo;
import cn.gtmap.realestate.common.core.dto.exchange.bengbu.HtbaxxRequestDTo;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.exchange.core.dto.bengbu.fcjy.FcjyQlrResponseModel;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcJyGxMapper;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-03-25
 * @description 读取房产合同信息 相关服务
 */
@Service(value = "bbFcjyService")
public class BbFcjyService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BbFcjyService.class);
    /**
     * 蚌埠房产交易url
     */
    @Value("${fcjy.spfclf.url:}")
    private String fcjySpfClfUrl;

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;
    @Autowired
    BdcJyGxMapper bdcJyGxMapper;
    @Autowired
    FwHsFeignService fwHsFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    private HttpClientService httpClientService;

    public List<BdcQlrDO> dealqlr(FcjyQlrResponseModel fcjyQlrResponseModel) {
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if (fcjyQlrResponseModel != null && StringUtils.isNotBlank(fcjyQlrResponseModel.getQlr())) {
            List<Map> qlrList = getQlrMaps(fcjyQlrResponseModel);
            if (CollectionUtils.isNotEmpty(qlrList)) {
                for (Map map : qlrList) {
                    BdcQlrDO bdcQlrDO = new BdcQlrDO();
                    dozerBeanMapper.map(map, bdcQlrDO, "bb_fcjy_htqlr");
                    bdcQlrDOList.add(bdcQlrDO);
                }
            }
        }
        return bdcQlrDOList;
    }

    public EtlSpfHtbaResponseDTo dealspfFhxx(EtlSpfHtbaListDTo dTo) {
        if (dTo != null) {
            return dTo.getData();
        }
        return null;
    }

    public EtlClfHtbaResponseDTo dealclfFhxx(EtlClfHtbaListDTo dTo) {
        if (dTo != null) {
            return dTo.getData();
        }
        return null;
    }

    public List<BdcSlSqrDO> dealsqr(FcjyQlrResponseModel fcjyQlrResponseModel) {
        List<BdcSlSqrDO> bdcSlSqrDOList = new ArrayList<>();
        if (fcjyQlrResponseModel != null && StringUtils.isNotBlank(fcjyQlrResponseModel.getQlr())) {
            List<Map> qlrList = getQlrMaps(fcjyQlrResponseModel);
            if (CollectionUtils.isNotEmpty(qlrList)) {
                for (Map map : qlrList) {
                    BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                    dozerBeanMapper.map(map, bdcSlSqrDO, "bb_fcjy_htsqr");
                    bdcSlSqrDOList.add(bdcSlSqrDO);
                }
            }
        }
        return bdcSlSqrDOList;
    }

    public String queryDjbhByBdcdyh(String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException("不动产单元号不能为空");
        }
        List<Map> bdcGxJyList = bdcJyGxMapper.queryBdcJyGxList(bdcdyh);
        if (CollectionUtils.isNotEmpty(bdcGxJyList)) {
            return MapUtils.getString(bdcGxJyList.get(0), "DJBH");
        }
        return "";
    }

    public String initHtxxParam(JSONObject json) {
        if (MapUtils.isEmpty(json)) {
            throw new MissingArgumentException("查询参数不能为空");
        }
        HtbaxxParamRequestDTo htbaxxParamRequestDTo = JSONObject.parseObject(JSON.toJSONString(json), HtbaxxParamRequestDTo.class);
        HtbaxxRequestDTo htbaxxRequestDTo = new HtbaxxRequestDTo();
        htbaxxRequestDTo.setParam(htbaxxParamRequestDTo);
        return XmlEntityConvertUtil.entityToXmlStatic(htbaxxRequestDTo);
    }

    /**
     * @description 查询房产交易商品房备案信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/17 14:50
     * @param contractNo 合同编号
     * @param bdcdyh 不动产单元号
     * @return JSONObject
     */
    public JSONObject queryFcjySpfBaxx(String contractNo, String bdcdyh) {
        return queryFcjySpfClfHtxx("504", "1", contractNo, bdcdyh);
    }

    /**
     * @description 查询房产交易存量房合同信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/17 14:51
     * @param contractNo 合同编号
     * @param bdcdyh 不动产单元号
     * @return JSONObject
     */
    public JSONObject queryFcjyClfHtxx(String contractNo, String bdcdyh) {
        return queryFcjySpfClfHtxx("504", "2", contractNo, bdcdyh);
    }

    public String queryFwbmByBdcdyh(String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException("不动产单元号不能为空");
        }
        List<Map> bdcGxJyList = bdcJyGxMapper.queryBdcJyGxList(bdcdyh);
        //先查表，没有查权籍fw_hs
        if (CollectionUtils.isNotEmpty(bdcGxJyList) && StringUtils.isNotBlank(MapUtils.getString(bdcGxJyList.get(0), "FWBM"))) {
            return MapUtils.getString(bdcGxJyList.get(0), "FWBM");
        } else {
            FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcdyh,"");
            if (fwHsDO != null) {
                return fwHsDO.getFwbm();
            }
        }
        return "";
    }

    /**
     * @description 查询houseId
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/17 17:05
     * @param bdcdyh
     * @return String
     */
    public String queryHouseIdByBdcdyh(String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException("不动产单元号不能为空");
        }
        FwHsHouseIdDTO fwHsHouseIdDTO = fwHsFeignService.queryFwhsHouseIdByBdcdyh(bdcdyh,"");
        if (fwHsHouseIdDTO != null) {
            return fwHsHouseIdDTO.getHouseId();
        }
        return "";
    }

    private List<Map> getQlrMaps(FcjyQlrResponseModel fcjyQlrResponseModel) {
        List<Map> qlrList = new ArrayList<>();
        String[] qlrmcList = StringUtils.split(fcjyQlrResponseModel.getQlr(), "/");
        String[] qlrzjhList = StringUtils.split(fcjyQlrResponseModel.getQlrzjh(), "/");
        String[] qlrlxdhList = StringUtils.split(fcjyQlrResponseModel.getQlrlxdh(), "/");
        String[] qlrzjlbList = StringUtils.split(fcjyQlrResponseModel.getQlrzjlb(), "/");
        for (int i = 0; i < qlrmcList.length; i++) {
            Map map = new HashMap();
            map.put("qlrmc", qlrmcList[i]);
            if (arrayHasInIndex(qlrzjhList, i)) {
                map.put("qlrzjh", qlrzjhList[i]);
            }
            if (arrayHasInIndex(qlrlxdhList, i)) {
                map.put("qlrdh", qlrlxdhList[i]);
            }
            if (arrayHasInIndex(qlrzjlbList, i) && NumberUtils.isNumber(qlrzjlbList[i])) {
                map.put("zjzl", Integer.valueOf(qlrzjlbList[i]));
            } else if (arrayHasInIndex(qlrzjlbList, 0) && NumberUtils.isNumber(qlrzjlbList[0])) {
                map.put("zjzl", Integer.valueOf(qlrzjlbList[0]));
            }
            qlrList.add(map);
        }
        return qlrList;
    }

    private boolean arrayHasInIndex(String[] qlrzjhList, int i) {
        return qlrzjhList != null && qlrzjhList.length > i && StringUtils.isNotBlank(qlrzjhList[i]);
    }

    /**
     * @description 查询房产交易商品房、存量房合同信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/17 14:51
     * @param flag 接口标志
     * @param type 类型
     * @param contractNo 合同编号
     * @param bdcdyh 不动产单元号
     * @return JSONObject
     */
    private JSONObject queryFcjySpfClfHtxx(String flag, String type, String contractNo, String bdcdyh) {
        if (StringUtils.isAnyBlank(flag, type)) {
            throw new MissingArgumentException("查询参数flag和type不能为空");
        }
        JSONObject requestParams = new JSONObject();
        JSONObject responseData = new JSONObject();
        requestParams.put("flag", flag);
        requestParams.put("type", type);
        // 通过合同号查询
        if (StringUtils.isNotBlank(contractNo)) {
            requestParams.put("contractNo", contractNo);
            responseData = sendRequest(requestParams);
        } else if (StringUtils.isNotBlank(bdcdyh)) {
            // 通过房屋编码或不动产单元号查询
            String houseId = queryHouseIdByBdcdyh(bdcdyh);
            requestParams.put("houseId", houseId);
            if ("2".equals(type)) {
                // 存量房需要传idCard和qzh，获取现势产权权利人列表，遍历权利人列表查询房产交易存量房信息
                List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listCqQlr(bdcdyh);
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                        String idcard = bdcQlrDO.getZjh();
                        String qzh = bdcQlrDO.getBdcqzh();
                        requestParams.put("idcard", idcard);
                        requestParams.put("qzh", qzh);
                        responseData = sendRequest(requestParams);
                        if (Objects.nonNull(responseData)) {
                            break;
                        }
                    }
                }
                // 如果房屋编码查询不到，再通过不动产单元号查询
                if (Objects.isNull(responseData)) {
                    JSONObject requestParamsByBdcdyh = new JSONObject();
                    requestParamsByBdcdyh.put("flag", flag);
                    requestParamsByBdcdyh.put("type", type);
                    requestParamsByBdcdyh.put("bdcdyh", bdcdyh);
                    if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                        for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                            String idcard = bdcQlrDO.getZjh();
                            String qzh = bdcQlrDO.getBdcqzh();
                            requestParamsByBdcdyh.put("idcard", idcard);
                            requestParamsByBdcdyh.put("qzh", qzh);
                            responseData = sendRequest(requestParamsByBdcdyh);
                            if (Objects.nonNull(responseData)) {
                                break;
                            }
                        }
                    }
                }
            } else {
                responseData = sendRequest(requestParams);
                // 如果房屋编码查询不到，再通过不动产单元号查询
                if (Objects.isNull(responseData)) {
                    JSONObject requestParamsByBdcdyh = new JSONObject();
                    requestParamsByBdcdyh.put("flag", flag);
                    requestParamsByBdcdyh.put("type", type);
                    requestParamsByBdcdyh.put("bdcdyh", bdcdyh);
                    responseData = sendRequest(requestParamsByBdcdyh);
                }
            }
        }
        return responseData;
    }

    /**
     * @description 向房产交易发送请求
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/17 14:51
     * @param requestParams 请求参数
     * @return JSONObject
     */
    private JSONObject sendRequest(JSONObject requestParams) {
        // 发送请求下单
        HttpPost httpPost = new HttpPost(fcjySpfClfUrl);
        StringEntity stringEntity = new StringEntity(JSON.toJSONString(requestParams), "utf-8");
        stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
        String responseStr = "";
        LOGGER.error("蚌埠商品房交易,请求地址:{}，请求参数:{}", fcjySpfClfUrl, JSON.toJSONString(requestParams));
        try {
            responseStr = httpClientService.doPost(httpPost, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("蚌埠商品房交易请求异常，请求地址:{}，请求参数:{}", fcjySpfClfUrl, JSON.toJSONString(requestParams), e);
            throw new AppException("httpPost请求异常");
        }
        LOGGER.info("蚌埠商品房交易请求成功，请求地址：{}，返回结果为：{}", fcjySpfClfUrl, responseStr);
        if (Objects.nonNull(responseStr)) {
            JSONObject responseData = JSONObject.parseObject(responseStr, JSONObject.class);
            String result = responseData.getString("result");
            if (StringUtils.isNotBlank(result)) {
                return responseData;
            }
        }
        return null;
    }
}
