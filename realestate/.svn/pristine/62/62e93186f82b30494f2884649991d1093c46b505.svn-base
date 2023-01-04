package cn.gtmap.realestate.exchange.service.impl.inf.wwsq;

import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzzzFeignService;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.qo.DzzzXzQO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("dzzzstandardServiceImpl")
public class DzzzstandardServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(DzzzstandardServiceImpl.class);

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    private BdcDzzzFeignService bdcDzzzFeignService;

    /**
     * 根据请求参数，下载电子证照base64
     *
     * @param
     * @return
     * @Date 2021/5/20
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Object zzxzforbase64(DzzzXzQO dzzzXzQO) {

        // 组装接口返回参数 status: 0 成功  1失败
        Map<String, Object> result = new HashedMap(4);
        Map<String, Object> head = new HashedMap(4);
        String errorMsg = "";

        //校验参数
        if (StringUtils.isNotBlank(dzzzXzQO.getBdcdyh())) {
//            List<Object> dataList = new ArrayList<>();
            List<Object> dataList =  new ArrayList<>();
            List<BdcZsDO> bdcZsDOList = bdcdjMapper.queryBdczsZzbs(dzzzXzQO);
            if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                //zzbs不为空，则查电子证照文件
                for (BdcZsDO zsDO : bdcZsDOList) {
                    if (StringUtils.isNotBlank(zsDO.getZzbs())) {
                        DzzzResponseModel<List<Map>> dzzzResponseModel = this.getDzzzxz(dzzzXzQO.getYymc(), zsDO.getZzbs());
                        if (StringUtils.equals("0", dzzzResponseModel.getHead().getStatus()) ) {
                            Object  dzzzxx = dzzzResponseModel.getData();
                            if (dzzzxx instanceof Map) {
                                Map dzzzMap = (Map)dzzzxx;
                                dzzzMap.put("zzmc",zsDO.getBdcqzh());
                                dataList.add(dzzzMap);
                            }

                        } else {
                            // 记录接口请求的异常信息，用于未获取到证照信息时，返回错误信息
                            //3为查不到记录，无数据，不能算请求失败
                            errorMsg = dzzzResponseModel.getHead().getMessage();
                        }
                    }
                }
                if(CollectionUtils.isNotEmpty(dataList)){
                    head.put("status", "0");
                    head.put("message", "success");
                }else {
                    head.put("status", "1");
                    head.put("message", errorMsg);
                }
                result.put("data", dataList);
                result.put("head", head);
            }else {
                head.put("status", "1");
                head.put("message", "3");
                result.put("data", dataList);
                result.put("head", head);
            }

        } else {
            head.put("status", "1");
            head.put("message", "4");
            result.put("data", null);
            result.put("head", head);
        }
        return result;
    }


    /**
     * 调用电子证照接口获取证照信息
     *
     * @param yymc 应用名称
     * @param zzbs 不动产权证号
     * @return 电子证照信息
     */
    private DzzzResponseModel<List<Map>> getDzzzxz(String yymc, String zzbs) {
        Map paramMap = new HashMap(2);
        paramMap.put("zzbs", zzbs);
        Map dataMap = new HashMap(2);
        dataMap.put("data", paramMap);
        return bdcDzzzFeignService.zzxxxz2(yymc, JSONObject.toJSONString(dataMap));
    }

}
