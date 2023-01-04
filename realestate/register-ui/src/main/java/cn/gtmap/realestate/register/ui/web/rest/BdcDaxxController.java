package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0, 2019/2/21
 * @description 档案信息查询
 */
@RestController
@RequestMapping("/rest/v1.0/daxx")
public class BdcDaxxController extends BaseController{

    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;

    /**
     * @return Map 基本信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据受理编号和证号获取档案信息 type（type 1是房产，不动产档案查询，2是抵押档案查询。）
     */
    @GetMapping("/hefei/dajbxx")
    public Object getDajbxx(@RequestParam(value = "slbh", required = false) String slbh,
                            @RequestParam(value = "bdcqzh", required = false) String bdcqzh,
                            @RequestParam(value = "type", required = false) String type) {
        Map param = new HashMap();
        if (StringUtils.isNotBlank(slbh)) {
            param.put("docId", slbh);
        }
        if (null != type) {
            param.put("type", type);
        }
        if (StringUtils.isNotBlank(bdcqzh)) {
            try {
                param.put("zqzh", URLDecoder.decode(bdcqzh, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("", e);
            }
        } else {
            param.put("zqzh", "");

        }
        if (MapUtils.isEmpty(param)) {
            throw new MissingArgumentException("查询参数缺失！");
        }
        return exchangeInterfaceFeignService.requestInterface("hfDaJbxx", param);
    }

    /**
     * @return 目录信息集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据档案id获取目录 type（type 1是房产，不动产档案查询，2是抵押档案查询。）
     */
    @GetMapping("/hefei/damlxx")
    public Object getDamlxx(@RequestParam("archid") String archid,
                            @RequestParam(value = "type", required = false) String type) {
        if (StringUtils.isBlank(archid) && null != type) {
            throw new MissingArgumentException("查询参数缺失！");
        }

        Map param = new HashMap();
        param.put("archid", archid);
        if (null != type) {
            param.put("type", type);
        }
        return exchangeInterfaceFeignService.requestInterface("hfDaMlxx", param);
    }

    /**
     * @return 附件信息集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据档案id获取单页图像 type（type 1是房产，不动产档案查询，2是抵押档案查询。）
     */
    @GetMapping("/hefei/dafjxx")
    public Object getDafjxx(@RequestParam("archid") String archid, @RequestParam("currentpage") Integer currentpage,
                            @RequestParam(value = "type", required = false) String type) {
        if (StringUtils.isBlank(archid) || currentpage == null || null == type) {
            throw new MissingArgumentException("查询参数缺失！");
        }
        Map param = new HashMap();
        param.put("username", userManagerUtils.getCurrentUserName());
        param.put("archid", archid);
        param.put("currentpage", currentpage);
        param.put("type", type);
        return exchangeInterfaceFeignService.requestInterface("hfDaFjxx", param);
    }


}
