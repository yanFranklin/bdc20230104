package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.common.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.common.core.dto.etl.EtlBatchNationalAccessRequestDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.exchange.core.vo.WsxNationalAccessVO;
import cn.gtmap.realestate.exchange.service.national.WsxNationalAccessService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-24
 * @description 外市县 汇报相关服务
 */
@Service
public class WsxNationalAccessServiceImpl implements WsxNationalAccessService {

    @Resource(name = "exchangeRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    private static final String ETL_APP_PREFIX = "ETL-APP";

    /**
     * 外市县 构造上报实体
     */
    private static final String WSX_MESSAGEMODEL = "/realestate-etl/rest/v1.0/messagemodel/{ywh}?bdcdyh={bdcdyh}";

    /**
     * 外市县 批量上报
     */
    private static final String PL_WSX_ACCESS = "/realestate-etl/rest/v1.0/placcess";


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param qxdm
     * @param ywh
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.exchange.MessageModel
     * @description 获取外市县的上报结构
     */
    @Override
    public MessageModel getWsxMessageModel(String qxdm, String ywh, String bdcdyh){
        // 根据QXDM 获取 ETL 的 URI
        String etlUri = getUri(qxdm);
        String requestUrl = etlUri + WSX_MESSAGEMODEL;
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("ywh",ywh);
        paramMap.put("bdcdyh",bdcdyh);
        return restTemplate.getForObject(requestUrl,MessageModel.class,paramMap);
    }

    /**
     * @param wsxNationalAccessVO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量执行外市县上报
     */
    @Override
    public void wsxPlNationalAccess(WsxNationalAccessVO wsxNationalAccessVO) {
        if(CollectionUtils.isNotEmpty(wsxNationalAccessVO.getRequestList())){
            // 根据QXDM 获取 ETL 的 URI
            String etlUri = getUri(wsxNationalAccessVO.getQxdm());
            String requestUrl = etlUri + PL_WSX_ACCESS;
            EtlBatchNationalAccessRequestDTO requestDTO = new EtlBatchNationalAccessRequestDTO();
            requestDTO.setRequestDTOList(wsxNationalAccessVO.getRequestList());
            restTemplate.postForObject(requestUrl, requestDTO, Object.class);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param qxdm
     * @return java.lang.String
     * @description 根据QXDM 获取ETL 的 URI
     */
    private String getUri(String qxdm){
        String appNameSuffix = qxdm;
        if(CommonUtil.isHefeiFcAccessQxdm(qxdm)){
            appNameSuffix = Constants.HEFEI_ACCESS_QXDM;
        }
        String appName = ETL_APP_PREFIX + "-" + appNameSuffix;
        List<ServiceInstance> instances = discoveryClient.getInstances(appName);
        if(CollectionUtils.isNotEmpty(instances)){
            ServiceInstance serviceInstance = instances.get(0);
            URI uri = serviceInstance.getUri();
            return uri.toString();
        }else{
            throw new AppException("注册中心找不到APPNAME为"+appName+"的应用");
        }
    }
}
