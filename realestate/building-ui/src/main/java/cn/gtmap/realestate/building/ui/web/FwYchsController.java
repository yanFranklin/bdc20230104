package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.util.Constants;
import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.service.feign.building.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-17
 * @description 预测户室页面服务
 */
@Controller
@RequestMapping("/fwychs")
public class FwYchsController extends BaseController {
    public static final String GET_BDCDYH_FAIL = "获取不动产单元号失败";

    @Autowired
    private BdcdyFeignService bdcdyFeignService;

    @Autowired
    private BdcdyhFeignService bdcdyhFeignService;

    @Autowired
    private FwYcHsFeignService fwYcHsFeignService;

    @Autowired
    private FwLjzFeginService fwLjzFeginService;

    @Autowired
    private FwFcqlrFeignService fwFcqlrFeignService;

    @Autowired
    private EntityFeignService entityFeignService;

    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询预测户室
     */
    @RequestMapping("/list")
    public String list() {
        return "/fwychs/fwychsList";
    }

    /**
     * 同一逻辑幢下的预测房屋户室列表分页
     *
     * @param model
     * @param fwDcbIndex
     * @return
     */
    @RequestMapping(value = "/listbyljz")
    public String listbyljz(Model model, @NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex) {
        model.addAttribute("fwDcbIndex", fwDcbIndex);
        return "fwychs/fwychsListByljz";
    }

    /**
     * @param pageable
     * @param bdcdyh
     * @param zl
     * @param fwDcbIndex
     * @param fjh
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询数据
     */
    @ResponseBody
    @RequestMapping("/listbypage")
    public Object listByPage(@LayuiPageable Pageable pageable, String bdcdyh, String zl, String fwDcbIndex, String fjh, String dyh) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(zl)) {
            paramMap.put("zl", zl);
        }
        if (StringUtils.isNotBlank(bdcdyh)) {
            paramMap.put("bdcdyh", StringUtils.deleteWhitespace(bdcdyh));
        }
        if (StringUtils.isNotBlank(fjh)) {
            paramMap.put("fjh", fjh);
        }
        if (StringUtils.isNotBlank(dyh)) {
            paramMap.put("dyh", dyh);
        }
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            paramMap.put("fwDcbIndex", fwDcbIndex);
        }
        paramMap.put("bdcdyhNotNull", "false");
        paramMap.put("bdczt", Constants.LIST_BDCDY_ALL_BDCZT_FLAG);
        return bdcdyFeignService.listYcFwHsBdcdy(pageable, JSONObject.toJSONString(paramMap));
    }


    /**
     * @param fwHsIndexList
     * @return java.util.Map
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @RequestMapping("/delbyindex")
    @ResponseBody
    public Map delByIndex(@NotEmpty(message = "户室列表不能为空")
                          @RequestParam(name = "fwHsIndexList", required = false) List<String> fwHsIndexList) {
        for (String fwHsIndex : fwHsIndexList) {
            fwYcHsFeignService.deleteYcHsByFwHsIndex(fwHsIndex,"");
        }
        return returnHtmlResult(true, "删除成功");
    }


    /**
     * 房屋预测户室填报页面
     *
     * @param model
     * @param fwHsIndex
     * @param fwDcbIndex
     * @return
     */
    @RequestMapping(value = "/info")
    public String info(Model model, String fwHsIndex, String fwDcbIndex) {
        model.addAttribute("fwHsIndex", fwHsIndex);
        model.addAttribute("fwDcbIndex", fwDcbIndex);
        return "fwychs/fwychsForm";
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param jsonData
     * @return java.util.Map
     * @description 新增或修改预测户室实体
     */
    @ResponseBody
    @RequestMapping(value = "/saveorupdate")
    public Map saveOrUpdateFwhs(@NotBlank(message = "参数不能为空") String jsonData){
        Map resultMap = new HashMap();
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        JSONObject fwhsObject = jsonObject.getJSONObject("info");
        JSONArray qlrJsonArr = jsonObject.getJSONArray("qlrList");
        if(fwhsObject != null){
            String fwHsIndex = fwhsObject.getString("fwHsIndex");
            if(StringUtils.isNotBlank(fwHsIndex)){
                // 更新
                entityFeignService.updateEntityByJson(fwhsObject.toJSONString(),FwYchsDO.class.getName());
            }else{
                // 新增
                FwYchsDO fwYchsDO = fwhsObject.toJavaObject(FwYchsDO.class);
                FwYchsDO result = fwYcHsFeignService.insertFwYcHs(fwYchsDO,"");
                if(result != null && StringUtils.isNotBlank(result.getFwHsIndex())){
                    fwHsIndex = result.getFwHsIndex();
                    resultMap.put("data", result);
                }
            }
            if(StringUtils.isNotBlank(fwHsIndex)){
                // 先删除
                fwFcqlrFeignService.deleteFcQlrByFwIndex(fwHsIndex,"");
                if(CollectionUtils.isNotEmpty(qlrJsonArr)){
                    List<FwFcqlrDO> insertList = new ArrayList<>();
                    for(int i = 0 ; i < qlrJsonArr.size() ; i++){
                        JSONObject qlrObject = qlrJsonArr.getJSONObject(i);
                        qlrObject.put("qlrbh",i+1);
                        qlrObject.put("fwIndex",fwHsIndex);
                        FwFcqlrDO fwFcqlrDO = qlrObject.toJavaObject(FwFcqlrDO.class);
                        insertList.add(fwFcqlrDO);
                    }
                    if(CollectionUtils.isNotEmpty(insertList)){
                        fwFcqlrFeignService.batchInsert(insertList);
                    }
                }
            }
        }else{
            resultMap.putAll(returnHtmlResult(false,"参数异常"));
            return resultMap;
        }
        resultMap.putAll(returnHtmlResult(true,"提交成功"));
        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = "/infofwychs")
    public FwYchsDO infoFwHs(@NotBlank(message = "主键不能为空") String fwHsIndex) {
        return fwYcHsFeignService.queryFwYcHsByFwHsIndex(fwHsIndex,"");
    }

    /**
     * @param pageable
     * @param bdcdyh
     * @param zl
     * @param fwDcbIndex
     * @param fjh
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 关联分页查询数据
     */
    @ResponseBody
    @RequestMapping("/gl/listbypage")
    public Object glListByPage(@LayuiPageable Pageable pageable, String bdcdyh,
                                  String zl, @NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex, String fjh, String fwbm,String qjgldm) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(zl)) {
            paramMap.put("zl", zl);
        }
        if (StringUtils.isNotBlank(bdcdyh)) {
            paramMap.put("bdcdyh", StringUtils.deleteWhitespace(bdcdyh));
        }
        if (StringUtils.isNotBlank(fjh)) {
            paramMap.put("fjh", fjh);
        }
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            paramMap.put("fwDcbIndex", fwDcbIndex);
        }
        if (StringUtils.isNotBlank(fwbm)) {
            paramMap.put("fwbms", Arrays.asList(StringUtils.split(fwbm, ",")));
        }
        if (StringUtils.isNotBlank(qjgldm)) {
            paramMap.put("qjgldm", qjgldm);
        }
        return fwYcHsFeignService.glListYchsByPage(pageable,JSONObject.toJSONString(paramMap));
    }
}
