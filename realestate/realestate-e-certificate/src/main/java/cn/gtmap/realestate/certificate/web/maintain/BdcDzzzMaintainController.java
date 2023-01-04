package cn.gtmap.realestate.certificate.web.maintain;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzTokenMapper;
import cn.gtmap.realestate.certificate.core.service.appear.BdcDzzzCityService;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.ResponseHead;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzTokenDo;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZdBmDo;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzZdBmService;
import cn.gtmap.realestate.certificate.util.*;
import cn.gtmap.realestate.certificate.web.main.DzzzController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/18
 */
@Controller
@Api("电子证照对内维护接口")
@RequestMapping(value = "/realestate-e-certificate")
public class BdcDzzzMaintainController extends DzzzController {

    @Autowired
    private BdcDzzzZdBmService bdcDzzzZdBmService;
    @Autowired
    private BdcDzzzCityService bdcDzzzCityService;
    @Autowired
    private BdcDzzzTokenMapper bdcDzzzTokenMapper;

    /**
     * @param jsonString
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 申请秘钥接口
     */
    @ResponseBody
    @RequestMapping(value = "/addDzzzToken", method = RequestMethod.POST)
    public DzzzResponseModel getToken(@RequestBody String jsonString) {
        BdcDzzzTokenDo bdcDzzzTokenDo = JSON.parseObject(jsonString, BdcDzzzTokenDo.class);

        // 修改
        if (StringUtils.isNotBlank(bdcDzzzTokenDo.getYyid())) {
            return bdcDzzzTokenService.updateBdcDzzzTokenById(bdcDzzzTokenDo);
        }
        return bdcDzzzTokenService.addDzzzToken(bdcDzzzTokenDo);
    }
/*
    @RequestMapping(value = "/bdcDzzzTokenForm", method = RequestMethod.GET)
    @ApiOperation(value = "电子证照应用信息页面", notes = "电子证照应用信息增加页面")
    public String toDzzzTokenForm(Model model, String yyid) {
        List<BdcDzzzZdBmDo> listDepart = bdcDzzzZdBmService.listBdcDzzzZdBm();
        if (CollectionUtils.isNotEmpty(listDepart)) {
            model.addAttribute("listDepart", listDepart);
        }
        if (StringUtils.isNotBlank(yyid)) {
            model.addAttribute("yyid", yyid);
        }
        model.addAttribute("yyqxMap", interfacePermission.getYyqxMap());
        return "token/bdcDzzzTokenForm";
    }

    @RequestMapping(value = "/toZzxxList", method = RequestMethod.GET)
    @ApiOperation(value = "电子证照台账展示", notes = "电子证照台账展示")
    public String toZzxxList(HttpServletRequest request, Model model) {
        logger.info("电子证照台账展示：{}，请求时间：{}", request.getRequestURI(), DateUtil.now());
        model.addAttribute("saveFileType", BdcDzzzPdfUtil.DZZZ_SAVEFILE_TYPE);
        return "dzzzgl/bdcDzzzZzxxList";
    }

    @RequestMapping(value = "/toDzzzTokenList", method = RequestMethod.GET)
    @ApiOperation(value = "电子证照应用信息台账展示", notes = "电子证照应用信息台账展示")
    public String toDzzzTokenList(HttpServletRequest request, Model model) {
       // logger.info("电子证照应用信息账展示：{}，请求时间：{}", request.getRequestURI(), DateUtil.now());
        return "token/bdcDzzzTokenList";
    }

    @RequestMapping(value = "/js/toZzxxSyncList", method = RequestMethod.GET)
    @ApiOperation(value = "电子证照同步台账展示", notes = "电子证照同步台账展示")
    public String toZzxxSyncList(HttpServletRequest request , Model model) {
        logger.info("电子证照同步台账展示：{}，请求时间：{}", request.getRequestURI(), DateUtil.now());
        model.addAttribute("saveFileType", BdcDzzzPdfUtil.DZZZ_SAVEFILE_TYPE);
        return "dzzzgl/jiangsu/bdcDzzzSyncList";
    }*/

    @ResponseBody
    @RequestMapping(value = "/getBdcDepartDict", method = RequestMethod.GET)
    @ApiOperation(value = "电子证照部门字典项", notes = "电子证照部门字典项")
    public Object getBdcDepartDict() {
        List<BdcDzzzZdBmDo> listDepart = bdcDzzzZdBmService.listBdcDzzzZdBm();
        if (CollectionUtils.isNotEmpty(listDepart)) {
           return listDepart;
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/getBdcYyqx", method = RequestMethod.GET)
    @ApiOperation(value = "电子证照应用权限", notes = "电子证照应用权限")
    public Object getBdcYyqx() {
        return interfacePermission.getYyqxLst();
    }

    @ResponseBody
    @RequestMapping(value = "/getBdcTokenDict", method = RequestMethod.GET)
    @ApiOperation(value = "电子证照应用名称字典项", notes = "电子证照应用名称字典项")
    public Object getBdcTokenDict() {
        List<BdcDzzzTokenDo> listTokenDo = bdcDzzzTokenMapper.listBdcDzzzToken(new HashMap<>());
        if (CollectionUtils.isNotEmpty(listTokenDo)) {
            return listTokenDo;
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/getTokenByTokenId", method = RequestMethod.GET)
    @ApiOperation(value = "电子证照获取应用信息", notes = "电子证照获取应用信息")
    public BdcDzzzTokenDo getTokenByTokenId(String yyid) {
        return bdcDzzzTokenService.queryBdcDzzzToken(yyid);
    }


    @ResponseBody
    @RequestMapping(value = "/listDzzzTokenByPageJson", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照应用信息台账数据搜索", notes = "电子证照应用信息台账数据搜索")
    public Object listDzzzTokenByPageJson(@LayuiPageable Pageable pageable, String yymc) {
        Map<String, String> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(yymc)) {
            paramMap.put(Constants.YYMC, StringUtils.deleteWhitespace(yymc));
        }
        return bdcDzzzService.selectPaging("getBdcDzzzTokenByPage", paramMap, pageable);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteBdcDzzzToken", method = RequestMethod.GET)
    @ApiOperation(value = "删除电子证照应用信息", notes = "删除电子证照应用信息接口")
    public DzzzResponseModel deleteBdcDzzzToken(String yyid) {
        // 日志保存方法
        logger.info("电子证照管理-删除电子证照信息接口：{}，请求时间：{}", yyid, DateUtil.now());

        //删除证照信息
        int deleteResult = bdcDzzzTokenService.deleteTokenByTokenId(yyid);
        if (deleteResult == 1) {
            return new DzzzResponseModel(new ResponseHead(ResponseEnum.SUCCESS.getCode(), "删除证照应用信息成功！"), null);
        }
        return new DzzzResponseModel(new ResponseHead(ResponseEnum.FALSE.getCode(), "删除证照应用信息失败！"), null);
    }


    @ResponseBody
    @RequestMapping(value = "/createPdfBdcDzzzByZzid", method = RequestMethod.POST)
    @ApiOperation(value = "生成不动产电子证照PDF", notes = "生成PDF电子证照接口")
    public DzzzResponseModel createPdfBdcDzzzByZzid(String zzid) {
        // 日志保存方法
        logger.info("电子证照管理-生成PDF电子证照接口请求参数：{}，请求时间：{}", zzid, DateUtil.now());

        return bdcDzzzZzxxService.createPdfBdcDzzzByZzid(zzid);
    }


    @ResponseBody
    @RequestMapping(value = "/listDzzzZzxxByPageJson", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照台账数据搜索", notes = "电子证照台账数据搜索")
    public Object listDzzzZzxxByPageJson(@LayuiPageable Pageable pageable, String bdcdyh, String czzt, String sort) {
        Map<String, String> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            paramMap.put("bdcdyh", StringUtils.deleteWhitespace(bdcdyh));
        }
        if (StringUtils.isNotBlank(czzt)) {
            paramMap.put("czzt", StringUtils.deleteWhitespace(czzt));
        }
        if (StringUtils.isNotBlank(sort)) {
            paramMap.put("sort", StringUtils.deleteWhitespace(sort));
        }
        return bdcDzzzService.selectPaging("getBdcDzzzZzxxByPage", paramMap, pageable);
    }

    @ResponseBody
    @RequestMapping(value = "/syncDzzzPdf", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照市级同步省级证照文件", notes = "电子证照市级同步省级证照文件")
    public DzzzResponseModel syncDzzzPdf(String zzids) {
        logger.info("电子证照市级同步省级证照文件：{}，请求时间：{}", zzids, DateUtil.now());
        return bdcDzzzCityService.syncDzzzPdf(zzids);
    }

    @ResponseBody
    @RequestMapping(value = "/checkUnsynchronizedData", method = RequestMethod.POST)
    @ApiOperation(value = "检查登记数据与电子证照系统未同步数据", notes = "检查登记数据与电子证照系统未同步数据")
    public DzzzResponseModel checkUnsynchronizedData(@RequestBody String jsonString) {
        List<String> result = new ArrayList<>(100);
        List<Map> checkList = JSON.parseArray(jsonString, Map.class);
        if (CollectionUtils.isNotEmpty(checkList)) {
            for (Map map : checkList) {
                String bdcqzh = (String)map.get("BDCQZH");
                BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcDzzzZzxxService.queryBdcDzzzZzxxByBdcqzh(bdcqzh);
                if (null == bdcDzzzZzxxDO) {
                    result.add(bdcqzh);
                }
            }
        }
        return new DzzzResponseModel(new ResponseHead(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg()),result);
    }

    @ResponseBody
    @RequestMapping(value = "/base64Decode", method = RequestMethod.POST)
    @ApiOperation(value = "base64转码内部接口", notes = "base64转码内部接口功能")
    public Object base64Decode(@RequestBody String jsonString) {

        JSONObject jSONObject = JSON.parseObject(jsonString);
        JSONObject dataJon = jSONObject.getJSONObject("data");
        String type = dataJon.getString("type");
        String path = dataJon.getString("path");
        String data = dataJon.getString("data");
        if (StringUtils.equals("str", type)) {
            return Base64Util.decodeBase64StrToStr(data);
        } else if (StringUtils.equals("file", type)) {
            return Base64Util.decodeBase64StrToFile(data, path);
        }

        return "type 未知";
    }
}
