package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.gtc.starter.gcas.config.GTAutoConfiguration;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxx.request.HyxxCxywcsRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxx.request.HyxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.jmsf.request.JmsfCxywcsRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.jmsf.request.JmsfRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.rkkjzxxcx.request.RkkjzxxcxCxywcsRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.rkkjzxxcx.request.RkkjzxxcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.shgx.request.ShgxCxywcsRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.shgx.request.ShgxRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.SjcxsjlDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.SjgxxxzlpfResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.exchange.BdcSjptFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ProvincialDataSharingFeignService;
import cn.gtmap.realestate.common.util.StringUtil;
import cn.gtmap.realestate.inquiry.service.BdcSjptCxService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.internal.cglib.core.$ReflectUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 省级平台查询服务实现类
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/09/03
 */
@Service
public class BdcSjptCxServiceImpl implements BdcSjptCxService {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSjptCxServiceImpl.class);

    /**
     * 与第三方接口交互服务
     */
    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    /**
     * 省级数据共享交换平台服务
     */
    @Autowired
    private BdcSjptFeignService bdcSjptFeignService;

    /**
     * 民政婚姻查询
     *
     * @param pageable             分页对象
     * @param hyxxCxywcsRequestDTO 婚姻信息请求对象
     * @return {Page} 婚姻信息 JSON
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public Page listBdcHyxxByPage(Pageable pageable, HyxxCxywcsRequestDTO hyxxCxywcsRequestDTO) {
        HyxxRequestDTO hyxxRequestDTO = new HyxxRequestDTO();
        hyxxRequestDTO.setPage(pageable.getPageNumber() + 1);
        hyxxRequestDTO.setSize(pageable.getPageSize());

        List<HyxxCxywcsRequestDTO> requestDTOS = new ArrayList<>();
        /**
         * 婚姻接口必须传递 qlrmc ，但返回结果根据 qlrzjh 查询，为避免错误返回附值给 qlrmc
         */
        if (StringUtils.isBlank(hyxxCxywcsRequestDTO.getQlrmc())) {
            hyxxCxywcsRequestDTO.setQlrmc("bdc");
        }
        requestDTOS.add(hyxxCxywcsRequestDTO);
        hyxxRequestDTO.setCxywcs(requestDTOS);

        Object response = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_hyxx", hyxxRequestDTO);
        if (response != null) {
            LOGGER.info("权利人:" + hyxxCxywcsRequestDTO.getQlrmc() + "查询婚姻信息接口调用成功，响应内容：{}", JSONObject.toJSONString(response));
        }
        return initPageFromResponse(response);
    }

    /**
     * 社会组织信息查询
     *
     * @param zzmc 社会组织名称
     * @return {Object} 社会组织信息 JSON
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public Page listBdcShzzByPage(Pageable pageable, String zzmc) {
        ShgxRequestDTO shgxRequestDTO = new ShgxRequestDTO();
        shgxRequestDTO.setPage(pageable.getPageNumber() + 1);
        shgxRequestDTO.setSize(pageable.getPageSize());

        ShgxCxywcsRequestDTO shgxCxywcsRequestDTO = new ShgxCxywcsRequestDTO();
        shgxCxywcsRequestDTO.setQlrmc(zzmc);
        List<ShgxCxywcsRequestDTO> requestDTOS = new ArrayList<>();
        requestDTOS.add(shgxCxywcsRequestDTO);
        shgxRequestDTO.setCxywcs(requestDTOS);


        Object response = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_shgx", shgxRequestDTO);
        if (response != null) {
            LOGGER.info("组织:" + shgxCxywcsRequestDTO.getQlrmc() + "，查询组织信息接口调用成功，响应内容：{}", JSONObject.toJSONString(response));
        }
        return initPageFromResponse(response);
    }

    /**
     * 居民身份信息查询
     *
     * @param jmsfCxywcsRequestDTO 居民身份请求对象
     * @return {Object}  {"head": {"code": "0000", "msg": "success"}, "data": {"cxjg": []}}
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public Object queryJmsf(JmsfCxywcsRequestDTO jmsfCxywcsRequestDTO) {
        JmsfRequestDTO jmsfRequestDTO = new JmsfRequestDTO();
        List<JmsfCxywcsRequestDTO> jmsfCxywcsRequestDTOList = new ArrayList<>();
        jmsfCxywcsRequestDTOList.add(jmsfCxywcsRequestDTO);
        jmsfRequestDTO.setCxywcs(jmsfCxywcsRequestDTOList);

        Object jmsf = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_jmsf", jmsfRequestDTO);
        if (jmsf != null) {
            LOGGER.info("查询居民身份信息接口调用成功");
        }
        return jmsf;
    }

    /**
     * 居民身份信息申请反馈
     * 公安接口需要先发起申请， 1分钟后再调用申请反馈接口获取数据
     *
     * @param jmsfCxywcsRequestDTO 居民身份请求对象
     * @return {Object} 居民身份信息 JSON
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public Page queryJmsfSqfk(JmsfCxywcsRequestDTO jmsfCxywcsRequestDTO) {
        JmsfRequestDTO jmsfRequestDTO = new JmsfRequestDTO();
        List<JmsfCxywcsRequestDTO> jmsfCxywcsRequestDTOList = new ArrayList<>();
        jmsfCxywcsRequestDTOList.add(jmsfCxywcsRequestDTO);
        jmsfRequestDTO.setCxywcs(jmsfCxywcsRequestDTOList);
        // 居民身份查询反馈接口
        Object response = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_jmsfsqfk", jmsfRequestDTO);
        if (response != null) {
            LOGGER.info("居民身份信息查询反馈接口调用成功");
        }
        return initPageFromResponse(response);
    }

    /**
     * 人口基准信息
     * @param xm
     * @param zjh
     * @return
     */
    @Override
    public Object listRkkjzxxByPage(String xm, String zjh) {
        RkkjzxxcxRequestDTO rkkjzxxcxRequest = new RkkjzxxcxRequestDTO();
        RkkjzxxcxCxywcsRequestDTO rkkjzxxcxCxywcsRequest = new RkkjzxxcxCxywcsRequestDTO();
        rkkjzxxcxCxywcsRequest.setXm(xm);
        rkkjzxxcxCxywcsRequest.setZjh(zjh);
        List<RkkjzxxcxCxywcsRequestDTO> request = new ArrayList<>();
        request.add(rkkjzxxcxCxywcsRequest);
        rkkjzxxcxRequest.setCxywcs(request);


        Object response = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_rkkjzxxcx", rkkjzxxcxRequest);
        if (response != null) {
            LOGGER.info("人口:{} 查询人口基准信息接口调用成功，响应内容：{}",xm, response);
        }
        return response;
    }

    /**
     * 人口库身份核查
     *
     * @param xm
     * @param zjh
     * @return
     */
    @Override
    public Object listRkksfhc(String xm, String zjh) {
        RkkjzxxcxRequestDTO rkkjzxxcxRequest = new RkkjzxxcxRequestDTO();
        RkkjzxxcxCxywcsRequestDTO rkkjzxxcxCxywcsRequest = new RkkjzxxcxCxywcsRequestDTO();
        rkkjzxxcxCxywcsRequest.setXm(xm);
        rkkjzxxcxCxywcsRequest.setZjh(zjh);
        List<RkkjzxxcxCxywcsRequestDTO> request = new ArrayList<>();
        request.add(rkkjzxxcxCxywcsRequest);
        rkkjzxxcxRequest.setCxywcs(request);


        Object response = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_rkksfhc", rkkjzxxcxRequest);
        if (response != null) {
            LOGGER.info("人口:{} 查询人口库身份核查接口调用成功，响应内容：{}",xm, response);
        }
        return response;
    }

    /**
     * @param kssj,jssj
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 省级查询数据量
     */
    @Override
    public SjcxsjlDTO getXxgxzlpf(String kssj, String jssj) {
        if (StringUtil.isBlank(kssj) || StringUtil.isBlank(jssj)) {
            throw new AppException("开始时间和结束时间都不能为空");
        }
        Map paramMap = new HashMap();
        paramMap.put("kssj",kssj);
        paramMap.put("jssj",jssj);
        LOGGER.info("共享信息质量评分接口参数：{}",paramMap);
        Object response = exchangeInterfaceFeignService.sjptRequestInterface("sjpt_xxgxzlpf", paramMap);
        SjgxxxzlpfResponseDTO sjgxxxzlpf = getSjgxxxzlpf(response);
        // 查询量
        Integer count = bdcSjptFeignService.querySjsjlcxCount(kssj,jssj);
        SjcxsjlDTO sjcxsjl = new SjcxsjlDTO();
        sjcxsjl.setCxl(String.valueOf(count));
        if(sjgxxxzlpf != null){
            // 下发量
            if(StringUtil.isNotBlank(sjgxxxzlpf.getYxzzsl())){
                sjcxsjl.setXfl(sjgxxxzlpf.getYxzzsl());
            }
            // 上报量
            if(StringUtil.isNotBlank(sjgxxxzlpf.getFkcgsl())){
                sjcxsjl.setSbl(sjgxxxzlpf.getFkcgsl());
            }
        }
        return sjcxsjl;
    }

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @date 2022/5/13 10:49
     * @param
     * @return
     * @description
     **/
    private SjgxxxzlpfResponseDTO getSjgxxxzlpf(Object response){
        JSONArray rows = null;
        if (response != null) {
            LOGGER.info("共享信息质量评分接口调用成功，响应内容：{}", JSONObject.toJSONString(response));
            JSONObject content = JSONObject.parseObject(JSONObject.toJSONString(response));
            String jsonString = JSONObject.toJSONString(response);
            JSONObject headJsonObject = content.getJSONObject("head");
            if(!"0000".equals(headJsonObject.getString("code"))){
                throw new AppException(headJsonObject.getString("msg"));
            }
            if (content.getJSONObject("data") != null) {
                rows = content.getJSONObject("data").getJSONArray("rows");
                if(rows!= null){
                    List<SjgxxxzlpfResponseDTO> sjzlpfResponseDTOS = JSONArray.parseArray(rows.toJSONString(), SjgxxxzlpfResponseDTO.class);
                    if(CollectionUtils.isEmpty(sjzlpfResponseDTOS)){
                        return null;
                    }else{
                        return sjzlpfResponseDTOS.get(0);
                    }

                }
            }
        }
        return null;
    }

    /**
     * 转换返回信息封装成分页信息返回
     *
     * @param response 调用省级平台返回值
     * @return {Page} 返回分页信息
     */
    private Page initPageFromResponse(Object response) {
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(response));

        JSONObject head = jsonObject.getJSONObject("head");
        // 赋默认值为了处理居民信息无分页信息返回的情况
        Integer number = head.getInteger("page") == null ? 0 : head.getInteger("page");
        Integer size = head.getInteger("pageSize") == null ? 10 : head.getInteger("pageSize");
        Long totalElements = head.getLong("records") == null ? 1 : head.getLong("records");
        // 获取到查询结果
        JSONObject data = jsonObject.getJSONObject("data");
        Object cxjg = data.get("cxjg");
        // 组织成分页数据
        return new GTAutoConfiguration.DefaultPageImpl(cxjg == null ? Lists.emptyList() : (List) cxjg, number, size, totalElements);
    }
}
