package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.gtmap.realestate.common.core.dto.accept.BdcMkPjqRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.mkpjq.MkPjqDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0
 * @description 评价器人证比对
 */
@Service
public class PjqServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(PjqServiceImpl.class);

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    /**
     * 发起别，并获取数据
     *
     * @param
     * @return
     * @Date 2021/11/19
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public MkPjqDTO queryPjqRzdb(BdcMkPjqRequestDTO bdcMkPjqRequestDTO) {
        MkPjqDTO mkPjqDTO = new MkPjqDTO();
        //发起比对
        com.alibaba.fastjson.JSONObject jsonObject = new JSONObject();

        jsonObject.put("serviceId", bdcMkPjqRequestDTO.getServiceId());
        jsonObject.put("sysIp", bdcMkPjqRequestDTO.getSysIp());
        jsonObject.put("idCard", bdcMkPjqRequestDTO.getIdCard());
        Object result = exchangeBeanRequestService.request("mk_fqrzdb", jsonObject);
        JSONObject response = JSONObject.parseObject(JSONObject.toJSONString(result));
        //成功就继续请求获取数据，失败返回给高利宁
        if (CommonConstantUtils.SF_S_DM.equals(response.get("code"))) {
            com.alibaba.fastjson.JSONObject rzbd = new JSONObject();
            rzbd.put("serviceId", bdcMkPjqRequestDTO.getServiceId());
            rzbd.put("idCard", bdcMkPjqRequestDTO.getIdCard());
            Object rzbdRespon = exchangeBeanRequestService.request("mk_hqrzdb", rzbd);
            mkPjqDTO = JSONObject.parseObject(JSONObject.toJSONString(rzbdRespon), MkPjqDTO.class);
        } else {
            mkPjqDTO.setCode((Integer) response.get("code"));
            mkPjqDTO.setMessage((String) response.get("message"));
        }


        return mkPjqDTO;
    }

    /**
     * 发起身份证获取请求，并获取数据
     *
     * @param
     * @return
     * @Date 2022/06/17
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public MkPjqDTO queryPjqSfzxx(BdcMkPjqRequestDTO bdcMkPjqRequestDTO) {
        MkPjqDTO mkPjqDTO = new MkPjqDTO();
        //发起读取身份证功能
        com.alibaba.fastjson.JSONObject jsonObject = new JSONObject();

        jsonObject.put("serviceId", bdcMkPjqRequestDTO.getServiceId());
        jsonObject.put("sysIp", bdcMkPjqRequestDTO.getSysIp());
        Object result = exchangeBeanRequestService.request("mk_fqsfzxx", jsonObject);
        JSONObject response = JSONObject.parseObject(JSONObject.toJSONString(result));
        //成功就继续请求获取数据，失败返回给高利宁
        if (CommonConstantUtils.SF_S_DM.equals(response.get("code"))) {
            com.alibaba.fastjson.JSONObject sfzxx = new JSONObject();
            sfzxx.put("serviceId", bdcMkPjqRequestDTO.getServiceId());
            Object rzbdRespon = exchangeBeanRequestService.request("mk_hqsfzxx", sfzxx);
            mkPjqDTO = JSONObject.parseObject(JSONObject.toJSONString(rzbdRespon), MkPjqDTO.class);
        } else {
            mkPjqDTO.setCode((Integer) response.get("code"));
            mkPjqDTO.setMessage((String) response.get("message"));
        }


        return mkPjqDTO;
    }

}
