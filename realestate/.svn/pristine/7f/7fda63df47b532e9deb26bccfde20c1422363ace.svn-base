package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.TdlpbFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.ZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.ZdJsydLhxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcJsydLhxxFeignService;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/08/04
 * @Description:
 */
@Controller
@Validated
@RequestMapping("/tdlpb")
public class TdlpbController extends BaseController {

    @Autowired
    private ZdFeignService zdFeignService;
    @Value("${lpb.view.chscount:8}")
    private Integer chsCount;
    @Autowired
    private TdlpbFeignService tdlpbFeignService;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    ZdJsydLhxxFeignService zdJsydLhxxFeignService;

    @Autowired
    BdcJsydLhxxFeignService bdcJsydLhxxFeignService;
    /**
     * 土地楼盘表列表页面
     *
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model, String moduleCode, boolean sfyc) {
        model.addAttribute("moduleCode", moduleCode);
        model.addAttribute("sfyc", sfyc);
        return getFtlPath("/version/tdlpb/queryTdlpbList");
    }

    /**
     * 土地楼盘表列表页面
     *
     * @return
     */
    @RequestMapping(value = "/view")
    public String TdlpbView(Model model, String djh,String code) {
        if (StringUtils.isBlank(code)) {
            code = "default";
        }
        String mhsl = redisUtils.getStringValue(userManagerUtils.getCurrentUserName() + "_mhsl_redis");
        if (StringUtils.isNotBlank(mhsl) && NumberUtils.isNumber(mhsl)) {
            chsCount = Integer.decode(mhsl);
        }
        model.addAttribute("chscount", chsCount);
        model.addAttribute("djh", djh);
        ZdDjdcbDO zdDjdcbDO = zdFeignService.queryZdByDjh(djh,"");
        if(zdDjdcbDO!=null){
            model.addAttribute("zl", zdDjdcbDO.getTdzl());
        }
        return getFtlPath("/version/tdlpb/tdlpbView");
    }

    @Autowired
    private BdcdyFeignService bdcdyFeignService;

    @ResponseBody
    @RequestMapping(value = "/listbypage")
    public Object listLjzByPageJson(@LayuiPageable Pageable pageable,String djh, String zdqlr,String zl){
        //处理前台传递的分页参数
        Map<String, String> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(djh)) {
            paramMap.put("djh", StringUtils.deleteWhitespace(djh));
        }
        if (StringUtils.isNotBlank(zdqlr)) {
            paramMap.put("zdqlr", StringUtils.deleteWhitespace(zdqlr));
        }
        if (StringUtils.isNotBlank(zl)) {
            paramMap.put("zl", StringUtils.deleteWhitespace(zl));
        }
        //return bdcdyFeignService.listScFwHsBdcdy(pageable, JSONObject.toJSONString(paramMap));
        return zdFeignService.listZdxxByPageJson(pageable, JSONObject.toJSONString(paramMap));
    }
    
    @ResponseBody
    @RequestMapping(value = "/listTdlpbxx")
    public Object listTdlpbxx(@RequestParam(value = "djh", required = false) String djh){
        // 判断宗地是否存在现势抵押
        boolean flag = bdcJsydLhxxFeignService.checkXsDyaByLszd(djh);
        List<Map<String,Object>> tdlpbxxList = tdlpbFeignService.listTdlpbxx(djh);
        // 判断宗地所有的逻辑幢量化首登状态是否全部为空
        List<Map<String,Object>> lhsdztList = tdlpbxxList.stream().filter(map->map.get("LHDYZT") != null).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(lhsdztList)){
            return tdlpbxxList;
        }
        if(CollectionUtils.isNotEmpty(tdlpbxxList)){
            for(Object object : tdlpbxxList){
                Map<String,Object> map = (Map<String,Object>)object;
                //FW_JSYDZRZXX中没有数据，该宗地现势抵押存在，则展示量化抵押和量化首登
               if(map.get("LHDYZT")==null){
                   if(flag){
                       map.put("LHDYZT","1");
                       map.put("LHSDZT","0");
                   }else{
                       map.put("LHDYZT","0");
                       map.put("LHSDZT","1");
                   }
                }
            }
        }
        return tdlpbxxList;
    }

    @ResponseBody
    @GetMapping("/hssl")
    public void changeHssl(Integer hssl) {
        if (Objects.nonNull(hssl)) {
            //存到redis中以用户名为key
            String userName = userManagerUtils.getCurrentUserName();
            redisUtils.deleteKey(userName + "_mhsl_redis");
            redisUtils.addStringValue(userName + "_mhsl_redis", hssl.toString());
        }
    }

}
