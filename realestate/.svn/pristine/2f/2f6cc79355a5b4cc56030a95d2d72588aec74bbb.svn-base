package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.dto.accept.BdcGdspxxFjDTO;
import cn.gtmap.realestate.common.core.dto.accept.ListFileDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 一张图controller
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2021-03-02 08:33
 **/
@Controller
@RequestMapping("rest/v1.0/yzt")
public class SlymYztForwardController extends BaseController {
    private static final String GDSPXX_URL = "/view/yzt/gdspxx.html";
    private static final String YZTFJ_URL = "/view/yzt/yztfj.html";

    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String forwardYztHtml(@RequestParam(name = "processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        LOGGER.info("gzlslid获取项目类型，gzlslid为：{}",processInsId);
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if(CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            String bdcdyh = bdcXmDTOList.get(0).getBdcdyh();
            if(StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() > 19){
                Map map = new HashMap();
                String djh = bdcdyh.substring(0,19);
                map.put("codeKey","djh");
                map.put("codeValue",djh);
                LOGGER.info("调用yzt_fj_by_specialparam接口入参：{}",map.toString());
                Object obj = exchangeInterfaceFeignService.requestInterface("yzt_fj_by_specialparam",map);
                if(null != obj){
                    LOGGER.info("调用yzt_fj_by_specialparam接口返回值：{}",JSONObject.toJSONString(obj));
                    JSONArray js = JSONObject.parseArray(JSONObject.toJSONString(obj));
                    if(CollectionUtils.isNotEmpty(js)){
                        String jsonString = JSONObject.toJSONString(js.get(0));
                        JSONObject jsonObject = JSON.parseObject(jsonString);
                        if(null != jsonObject){
                            ListFileDTO listFileDTO = JSONObject.parseObject(JSONObject.toJSONString(jsonObject),ListFileDTO.class);
                            if(null != listFileDTO && CollectionUtils.isNotEmpty(listFileDTO.getProcessList())){
                                return YZTFJ_URL;
                            }
                        }
                    }
                }
            }
        }
        return GDSPXX_URL;
    }
}
