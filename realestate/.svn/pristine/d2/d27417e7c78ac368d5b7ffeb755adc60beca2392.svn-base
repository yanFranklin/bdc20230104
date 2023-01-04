package cn.gtmap.realestate.certificate.web.maintain;

import cn.gtmap.realestate.certificate.core.service.BdcDzzzMlxxService;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.certificate.web.main.DzzzController;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/18
 */
@Controller
@Api("电子证照门户")
@RequestMapping(value = "/realestate-e-certificate")
public class BdcDzzzIndexController extends DzzzController {

    @Autowired
    private BdcDzzzMlxxService bdcDzzzMlxxService;

    /*@RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        List<Map> zzxxResultList = bdcDzzzZzxxService.countBdcDzzzZzxx(new HashMap<>(0));
        if (CollectionUtils.isNotEmpty(zzxxResultList) && zzxxResultList.size() == 1) {
            model.addAttribute("total_count_zzxx", zzxxResultList.get(0).get("TOTAL_COUNT"));
            model.addAttribute("zs_count_zzxx", zzxxResultList.get(0).get("ZS_COUNT"));
            model.addAttribute("zms_count_zzxx", zzxxResultList.get(0).get("ZMS_COUNT"));
        }
        List<Map> mlxxResultList = bdcDzzzMlxxService.countBdcDzzzMlxx(new HashMap<>(0));
        if (CollectionUtils.isNotEmpty(mlxxResultList) && mlxxResultList.size() == 1) {
            model.addAttribute("total_count_mlxx", mlxxResultList.get(0).get("TOTAL_COUNT"));
            model.addAttribute("zs_count_mlxx", mlxxResultList.get(0).get("ZS_COUNT"));
            model.addAttribute("zms_count_mlxx", mlxxResultList.get(0).get("ZMS_COUNT"));
        }
        model.addAttribute("username", null);
        return "index/index";
    }*/

    /*@RequestMapping(value = "/toUpdatePassword", method = RequestMethod.GET)
    @ApiOperation(value = "电子证照修改密码页面", notes = "电子证照修改密码页面")
    public String toUpdatePassword(Model model) {
        model.addAttribute("username",null);
        return "index/updatePassword";
    }*/

    /*@ResponseBody
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照修改密码", notes = "电子证照修改密码")
    public String updatePassword(Model model, String password, String newPassword, String requirePassword) {
        return "修改密码成功！";
    }*/

    @ResponseBody
    @RequestMapping(value = "/countDzzzZzslZzk", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照证照库数量统计", notes = "电子证照证照库数量统计")
    public Object countDzzzZzslZzk() {
        logger.info("电子证照证照库数量统计：{}" + DateUtil.now());
        JSONObject result = new JSONObject();
        List<Map> zzxxResultList = bdcDzzzZzxxService.countBdcDzzzZzxx(new HashMap<>(0));
        if (CollectionUtils.isNotEmpty(zzxxResultList) && zzxxResultList.size() == 1) {
            result.put("zzkZj", zzxxResultList.get(0).get("TOTAL_COUNT"));
            result.put("zzkZs", zzxxResultList.get(0).get("ZS_COUNT"));
            result.put("zzkZms", zzxxResultList.get(0).get("ZMS_COUNT"));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/countDzzzZzslMlk", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照目录库数量统计", notes = "电子证照目录库数量统计")
    public Object countDzzzZzslMlk() {
        logger.info("电子证照目录库数量统计：{}" + DateUtil.now());
        JSONObject result = new JSONObject();
        List<Map> mlxxResultList = bdcDzzzMlxxService.countBdcDzzzMlxx(new HashMap<>(0));
        if (CollectionUtils.isNotEmpty(mlxxResultList) && mlxxResultList.size() == 1) {
            result.put("mlkZj", mlxxResultList.get(0).get("TOTAL_COUNT"));
            result.put("mlkZs", mlxxResultList.get(0).get("ZS_COUNT"));
            result.put("mlkZms", mlxxResultList.get(0).get("ZMS_COUNT"));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/dzzzQuantitativeDistribution", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照各地区数量分布", notes = "电子证照各地区数量分布")
    public String dzzzQuantitativeDistribution(Model model) {
        Map paramMap = new HashMap<>(1);
        return bdcDzzzMlxxService.countMlxxQuantitativeDistribution(paramMap);
    }

    @ResponseBody
    @RequestMapping(value = "/countDzzzJzjxxUse", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照按月使用次数", notes = "电子证照按月使用次数")
    public String countDzzzJzjxxUse(Model model, String way) {

        return bdcDzzzQqjlService.countRecordUse(new HashMap<>(0), way);
    }

    @ResponseBody
    @RequestMapping(value = "/countDzzzJzjxx", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照按部门使用次数", notes = "电子证照按部门使用次数")
    public String countDzzzJzjxx(Model model) {

        return bdcDzzzQqjlService.countDepart(new HashMap<>(0));
    }

    /*@ResponseBody
    @RequestMapping(value = "/getMenusByUserInfo", method = RequestMethod.GET)
    public Object getMenusByUserInfo() {
        List<BdcDzzzStandTree> firstStandTreeList = new ArrayList<>(10);
        List<BdcDzzzStandTree> secondStandTreeList = new ArrayList<>(10);
        List<Map> menuVoList = null;//sysMenuService.getMenusByUserInfo(null);
        if (CollectionUtils.isNotEmpty(menuVoList)) {
            for (Map map:menuVoList) {
                String url = (String)map.get("RESOURCE_URL");
                if (StringUtils.isNotBlank(url)) {
                    url = url.substring(url.indexOf("/"));
                    String urlName = interfacePermission.getStandingBookMap().get(url);
                    if (StringUtils.isNotBlank(urlName)) {
                        BdcDzzzStandTree secondStandTree = new BdcDzzzStandTree();
                        secondStandTree.setMark("basic");
                        secondStandTree.setName((String)map.get("RESOURCE_NAME"));
                        secondStandTree.setUrl(serverContextPath + url);
                        secondStandTree.setChildTree(new ArrayList<BdcDzzzStandTree>(0));
                        secondStandTreeList.add(secondStandTree);
                    }
                }

            }
        }
        if (CollectionUtils.isNotEmpty(secondStandTreeList)) {
            BdcDzzzStandTree firstStandTree = new BdcDzzzStandTree();
            firstStandTree.setName("证照管理");
            firstStandTree.setChildTree(secondStandTreeList);
            firstStandTreeList.add(firstStandTree);
        }
        return firstStandTreeList;
    }*/

}
