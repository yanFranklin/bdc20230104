package cn.gtmap.realestate.certificate.web.maintain;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzLogMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzLogDO;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.certificate.web.main.DzzzController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/18
 */
@Controller
@Api("电子证照日志")
@RequestMapping(value = "/realestate-e-certificate/log")
public class BdcDzzzLogController extends DzzzController {

    @Resource
    private BdcDzzzLogMapper bdcDzzzLogMapper;


    /*@RequestMapping(value = "/toZzxxLogList", method = RequestMethod.GET)
    @ApiOperation(value = "电子证照日志台账展示", notes = "电子证照日志台账展示")
    public String toZzxxCzList(HttpServletRequest request, Model model) {
        logger.info("电子证照日志台账展示：{}，请求时间：{}", request.getRequestURI(), DateUtil.now());
        model.addAttribute("listToken", bdcDzzzTokenMapper.listBdcDzzzToken(new HashMap<>())) ;
        return "log/bdcDzzzZzxxLogList";
    }*/

    @ResponseBody
    @RequestMapping(value = "/getZzxxLogDetail", method = RequestMethod.GET)
    @ApiOperation(value = "电子证照日志详细展示", notes = "电子证照日志详细展示")
    public Object getZzxxLogDetail(String logId) {
        Map result = null;
        logger.info("电子证照日志详细展示，请求时间：{}", DateUtil.now());
        //if (StringUtils.isNotBlank(logId)) {
            //String result = bdcDzzzJestClientService.searchQuery("logindex", "log", "logId", logId);
            //if (StringUtils.isNotBlank(result)) {
                BdcDzzzLogDO bdcDzzzLogDO = bdcDzzzLogMapper.queryBdcDzzzLogByLogid(logId);
                if (null != bdcDzzzLogDO) {
                    result = new HashMap<>(4);
                    result.put("paramJson",replace(bdcDzzzLogDO.getParamJson()));
                    String value = Constants.responseMap.get(bdcDzzzLogDO.getMessage());
                    result.put("message",replace(StringUtils.isNotEmpty(value)?value:bdcDzzzLogDO.getMessage()));
                }
            //}
        //}
        //List array = EnumUtils.getEnumList(ResponseEnum.class);
        return result;
    }

    private String replace(String str){
        if (StringUtils.isNotBlank(str)) {
            str = str.replaceAll("\\{","&#123;");
            str = str.replaceAll("\\}","&#125;");
            str = str.replaceAll("\\r","&nbsp;");
            str = str.replaceAll("\\n","&nbsp;");
            str = str.replaceAll("\\t","&nbsp;");
            str = str.replaceAll("\\\"","&quot;");
        }
        return str;
    }

    @ResponseBody
    @RequestMapping(value = "/listZzxxLogByPageJson", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照日志台账数据搜索", notes = "电子证照日志台账数据搜索")
    public Object listZzxxLogByPageJson(@LayuiPageable Pageable pageable, String username, String controller, String bdcqzh, String zzbs, String status
            , String dwdm) {
        Map paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(username)) {
            paramMap.put("username", StringUtils.deleteWhitespace(username));
        }
        if (StringUtils.isNotBlank(controller)) {
            paramMap.put("controller", StringUtils.deleteWhitespace(controller));
        }
        if (StringUtils.isNotBlank(bdcqzh)) {
            paramMap.put("bdcqzh", StringUtils.deleteWhitespace(bdcqzh));
        }
        if (StringUtils.isNotBlank(zzbs)) {
            paramMap.put("zzbs", StringUtils.deleteWhitespace(zzbs));
        }
        if (StringUtils.isNotBlank(status)) {
            paramMap.put("status", StringUtils.deleteWhitespace(status));
        }
        if (StringUtils.isNotBlank(dwdm)) {
            paramMap.put("dwdm", StringUtils.deleteWhitespace(dwdm));
        }
        return bdcDzzzService.selectPaging("listZzxxLogByPage", paramMap, pageable);
    }

}
