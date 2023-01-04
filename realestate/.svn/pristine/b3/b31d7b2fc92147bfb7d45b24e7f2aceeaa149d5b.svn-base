package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.bzfwhhxx.request.BzfwhhxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.csyxzm.request.CsyxzmRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxxhygr.request.HyxxhygrRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxxhysf.request.HyxxhysfRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.shttfrdjzs.request.ShttfrdjzsdjzsrRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.swyxzm.request.SwyxzmRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.sydjxxcx.request.SydjxxcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.xuancheng.gxcx.GxcxCsyxzmResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.xuancheng.gxcx.GxcxSwyxzmResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.config.BdcTsywPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.ExchangeInterfaceRestService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.PageUtils;
import cn.gtmap.realestate.inquiry.ui.core.annotation.GxInterfaceLog;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author wangyinghao
 * @version 1.0
 * @date 2020/11/6 09:40
 * @description 宣城大市共享接口(和其他地区调用方式不同)
 */
@RestController
@RequestMapping(value = "/rest/v1.0/gx/gjpt/")
public class BdcXgjptGxController extends BaseController {

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    ExchangeInterfaceRestService exchangeInterfaceRestService;

    @Autowired
    BdcTsywPzFeignService bdcTsywPzFeignService;

    @Autowired
    StorageClientMatcher storageClient;

    /**
     * 3.1 卫健委（宣城）-出生医学证明查询接口
     *
     * @param
     * @return
     * @author wangyinghao
     * @Date
     */
    @PostMapping("/naturalResources/csyxzm")
    @GxInterfaceLog(interfaceCode = "shengjjk_csyxzm")
    public Object csyxzmCx(@RequestBody CsyxzmRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List data = new ArrayList<>();

        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xc_xgbmcx_csyxzm", info);
        LOGGER.info("---卫健委-出生医学证明查询接口:{}", JSON.toJSONString(object));
        GxcxCsyxzmResponseDTO csyxzmResponseDTO = JSON.parseObject(JSON.toJSONString(object), GxcxCsyxzmResponseDTO.class);

        if (!"0".equals(csyxzmResponseDTO.getResultinfo().getSuccess())) {
            throw new AppException(csyxzmResponseDTO.getResultinfo().getMessage());
        }

        data.addAll(csyxzmResponseDTO.getBody().getData());
        if (info != null && info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(data, pageable, 1);
            return addLayUiCode(page);
        } else {
            return data;
        }
    }


    /**
     * 3.2 卫健委（宣城)-死亡医学证明查询接口
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:27 2020/11/12
     */
    @PostMapping("/naturalResources/swyxzm")
    @GxInterfaceLog(interfaceCode = "shengjjk_swyxzm")
    public Object swyxzmCx(@RequestBody SwyxzmRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List data = new ArrayList<>();

        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xc_xgbmcx_swyxzm", info);
        LOGGER.info("---卫健委-死亡医学证明查询接口:{}", JSON.toJSONString(object));
        GxcxSwyxzmResponseDTO swyxzmResponseDTO = JSON.parseObject(JSON.toJSONString(object), GxcxSwyxzmResponseDTO.class);

        if (!"0".equals(swyxzmResponseDTO.getResultinfo().getSuccess())) {
            throw new AppException(swyxzmResponseDTO.getResultinfo().getMessage());
        }

        data.addAll(swyxzmResponseDTO.getBody().getData());

        if (info != null && info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(data, pageable, 1);
            return addLayUiCode(page);
        } else {
            return data;
        }
    }


    /**
     * 3.6 民政部（宣城）-婚姻登记信息核验（个人）接口
     *
     * @param
     * @return
     * @author wangyinghao
     * @Date
     */
    @PostMapping("/naturalResources/hyxxhygr")
    @GxInterfaceLog(interfaceCode = "shengjjk_hyxxhygr")
    public Object csyxzmCx(@RequestBody HyxxhygrRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List data = new ArrayList<>();

        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xc_xgbmcx_grhyhy", info);

        if (Objects.isNull(object)) {
            throw new AppException("beanid:xc_xgbmcx_grhyhy响应结果为空!");
        }

        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(object));
        LOGGER.info("---民政部-婚姻登记信息核验（个人）接口:{}", JSON.toJSONString(object));

        data.addAll(jsonArray);
        if (info != null && info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(data, pageable, 1);
            return addLayUiCode(page);
        } else {
            return data;
        }
    }

    /**
     * 3.7 民政部（宣城）-婚姻登记信息核验（双方）接口
     *
     * @param
     * @return
     * @author wangyinghao
     * @Date
     */
    @PostMapping("/naturalResources/hyxxhysf")
    @GxInterfaceLog(interfaceCode = "shengjjk_hyxxhysf")
    public Object hyxxhysfCx(@RequestBody HyxxhysfRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xc_xgbmcx_sfhyhy", info);

        if (Objects.isNull(object)) {
            throw new AppException("beanid:xc_xgbmcx_sfhyhy 响应结果为空!");
        }

        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(object));
        LOGGER.info("---民政部-婚姻登记信息核验（双方）返回:{}", JSON.toJSONString(object));

        List data = new ArrayList(jsonArray);
        if (info != null && info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(data, pageable, 1);
            return addLayUiCode(page);
        } else {
            return data;
        }
    }


    /**
     * 3.5 民政部（宣城）-社会团体法人登记证书查询接口
     *
     * @param
     * @return
     * @author wangyinghao
     * @Date
     */
    @PostMapping("/naturalResources/shttfrdjzs")
    @GxInterfaceLog(interfaceCode = "shengjjk_shttfrdjzs")
    public Object shttfrdjzsCx(@RequestBody ShttfrdjzsdjzsrRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);

        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xc_xgbmcx_shttfrzs", info);

        if (Objects.isNull(object)) {
            throw new AppException("beanid:xgbmcx_shttfrdjzs响应结果为空!");
        }

        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(object));
        LOGGER.info("---社会团体法人登记证书查询接口 返回:{}", JSON.toJSONString(object));

        List data = new ArrayList(jsonArray);
        if (info != null && info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(data, pageable, 1);
            return addLayUiCode(page);
        } else {
            return data;
        }
    }


    /**
     * 3.14 民政部（宣城）-收养登记信息
     *
     * @param
     * @return
     * @author wangyinghao
     * @Date
     */
    @PostMapping("/naturalResources/sydjxxcx")
    @GxInterfaceLog(interfaceCode = "shengjjk_sydjxxcx")
    public Object sydjxxcx(@RequestBody SydjxxcxRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xc_xgbmcx_sydjxx", info);

        if (Objects.isNull(object)) {
            throw new AppException("beanid:xc_xgbmcx_sydjxx 响应结果为空!");
        }

        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(object));
        LOGGER.info("---收养登记信息 返回:{}", JSON.toJSONString(object));

        List data = new ArrayList(jsonArray);

        if (info != null && info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(data, pageable, 1);
            return addLayUiCode(page);
        } else {
            return data;
        }
    }


    /**
     * 3.16 民政部（宣城）-殡葬服务火化信息
     *
     * @param
     * @return
     * @author wangyinghao
     * @Date
     */
    @PostMapping("/naturalResources/bzfwhhxx")
    @GxInterfaceLog(interfaceCode = "shengjjk_bzfwhhxx")
    public Object bzfwhhxx(@RequestBody BzfwhhxxRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xc_xgbmcx_bzhhxx", info);

        if (Objects.isNull(object)) {
            throw new AppException("beanid:xc_xgbmcx_bzhhxx响应结果为空!");
        }

        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(object));
        LOGGER.info("---收养登记信息 返回:{}", JSON.toJSONString(object));

        List dataList = new ArrayList(jsonArray);
        if (info != null && info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(dataList, pageable, 1);
            return addLayUiCode(page);
        } else {
            return dataList;
        }
    }

    /**
     * 民政部（宣城）-涉外婚姻登记信息查询
     *
     * @param
     * @return
     * @author wangyinghao
     * @Date
     */
    @PostMapping("/naturalResources/swhydjxxcx")
    @GxInterfaceLog(interfaceCode = "shengjjk_swhyxx")
    public Object swhyxx(@RequestBody JSONObject param) {
        Pageable pageable = new PageRequest(1, 10, null);
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xc_xgbmcx_swhyxx", param);

        if (Objects.isNull(object)) {
            throw new AppException("beanid:xc_xgbmcx_swhyxx响应结果为空!");
        }
        LOGGER.info("---涉外婚姻登记信息查询 返回:{}", JSON.toJSONString(object));
        return JSON.parseArray(JSON.toJSONString(object));
    }

}
