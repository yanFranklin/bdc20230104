package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.common.core.vo.building.BatchUpdateFwhsVO;
import cn.gtmap.realestate.building.ui.util.Constants;
import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryDTO;
import cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryNewDTO;
import cn.gtmap.realestate.common.core.dto.building.FwhsBatchUpdateRequestDTO;
import cn.gtmap.realestate.common.core.service.feign.building.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2018/12/11
 * @description
 */
@Controller
@RequestMapping("fwhs")
public class FwHsController extends BaseController {
    public static final String GET_BDCDYH_FAIL = "获取不动产单元号失败";
    @Autowired
    private FwHsFeignService fwHsFeignService;

    @Autowired
    private BdcdyFeignService bdcdyFeignService;

    @Autowired
    private FwFcqlrFeignService fwFcqlrFeignService;

    @Autowired
    private BdcdyhFeignService bdcdyhFeignService;

    @Autowired
    private FwLjzFeginService fwLjzFeginService;

    @Autowired
    private EntityFeignService entityFeignService;

    @Autowired
    private FwHsHistroyFeignService fwHsHistroyFeignService;

    @Autowired
    private FwhsBgFeignService fwhsBgFeignService;

    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询房屋户室
     */
    @RequestMapping("/list")
    public String list() {
        return "fwhs/fwhsList";
    }


    /**
     * @param model
     * @param fwHsIndexList
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 批量修改房屋户室面积页面
     */
    @RequestMapping(value = "/plgxmj")
    public String plgxmj(Model model,
                         @RequestParam(name = "fwHsIndexList", required = false)
                         List<String> fwHsIndexList,String fwDcbIndex) {
        model.addAttribute("fwHsIndexList", fwHsIndexList);
        model.addAttribute("fwDcbIndex", fwDcbIndex);
        return "fwhs/fwhsPlgxMj";
    }

    /**
     * @param model
     * @param fwHsIndexList
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 批量修改房屋户室属性页面
     */
    @RequestMapping(value = "/plgxsx")
    public String plgxsx(Model model,
                         @RequestParam(name = "fwHsIndexList", required = false)
                         List<String> fwHsIndexList,
                         String fwDcbIndex) {
        model.addAttribute("fwHsIndexList", fwHsIndexList);
        model.addAttribute("fwDcbIndex",fwDcbIndex);
        return "fwhs/fwhsPlgxSx";
    }

    /**
     * @param fwDcbIndex
     * @param lszd
     * @param model
     * @param zrzh
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改逻辑幢页面
     */
    @RequestMapping(value = "/updateLjzView")
    public String updateLjzView(Model model, @NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex
            , @NotBlank(message = "自然幢号不能为空") String zrzh
            , @NotBlank(message = "隶属宗地不能为空") String lszd) {
        model.addAttribute("zrzh", zrzh);
        model.addAttribute("lszd", lszd);
        model.addAttribute("fwDcbIndex", fwDcbIndex);
        model.addAttribute("showQuery","ljzh");
        return "ljz/pickLjzlist";
    }


    /**
     * @param fwHsIndexList
     * @return java.util.Map
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据主键删除户室
     */
    @ResponseBody
    @RequestMapping("/delbyindex")
    public Map delByIndex(@NotEmpty(message = "户室列表不能为空")
                          @RequestParam(name = "fwHsIndexList", required = false) List<String> fwHsIndexList) {
        for (String fwHsIndex : fwHsIndexList) {
            fwHsFeignService.deleteHsByFwHsIndex(fwHsIndex,"");
        }
        return returnHtmlResult(true, "删除成功");
    }

    /**
     * @param pageable
     * @param zl
     * @param bdcdyh
     * @param fwDcbIndex
     * @param fjh
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询
     */
    @RequestMapping("/listbypage")
    @ResponseBody
    public Object listByPage(@LayuiPageable Pageable pageable, String zl, String bdcdyh, String fwDcbIndex, String fjh,String qjgldm) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(zl)) {
            paramMap.put("zl", StringUtils.deleteWhitespace(zl));
        }
        if (StringUtils.isNotBlank(bdcdyh)) {
            paramMap.put("bdcdyh", StringUtils.deleteWhitespace(bdcdyh));
        }
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            paramMap.put("fwDcbIndex", fwDcbIndex);
        }
        if (StringUtils.isNotBlank(fjh)) {
            paramMap.put("fjh", StringUtils.deleteWhitespace(fjh));
        }
        if (StringUtils.isNotBlank(qjgldm)) {
            paramMap.put("qjgldm", qjgldm);
        }
        paramMap.put("bdcdyhNotNull", "false");
        paramMap.put("bdczt", Constants.LIST_BDCDY_ALL_BDCZT_FLAG);
        return bdcdyFeignService.listScFwHsBdcdy(pageable, JSONObject.toJSONString(paramMap));
    }


    /**
     * 房屋户室填报页面
     *
     * @param model
     * @param fwHsIndex
     * @param fwDcbIndex
     * @param bgbh
     * @return
     */
    @RequestMapping(value = "/info")
    public String info(Model model, FwHsDO fwHsDO, String bgbh) {
        model.addAttribute("fwHsIndex", fwHsDO.getFwHsIndex());
        model.addAttribute("fwDcbIndex", fwHsDO.getFwDcbIndex());
        model.addAttribute("bgbh", bgbh);
        return "fwhs/fwhsForm";
    }

    /**
     * 同一逻辑幢下的房屋户室列表分页
     *
     * @param model
     * @param fwDcbIndex
     * @return
     */
    @RequestMapping(value = "/listbyljz")
    public String listbyljz(Model model, @NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex) {
        model.addAttribute("fwDcbIndex", fwDcbIndex);
        return "fwhs/fwhsListByljz";
    }

    /**
     * 根据主键获得房屋户室信息
     *
     * @param fwHsIndex
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/infofwhs")
    public FwHsDO infoFwHs(@NotBlank(message = "主键不能为空") String fwHsIndex) {
        return fwHsFeignService.queryFwHsByFwHsIndex(fwHsIndex,"");
    }

    /**
     * 根据逻辑幢主键获取所有户室分页
     *
     * @param pageable
     * @param dyh
     * @param fwDcbIndex
     * @param fjh
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listljzidbypage")
    public Object listLjzidByPageJson(@LayuiPageable Pageable pageable,
                                      @NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex,
                                      String fjh, String dyh) {
        //处理前台传递的分页参数
        Map paramMap = new HashMap();
        paramMap.put("fwDcbIndex", fwDcbIndex);
        if (StringUtils.isNotBlank(fjh)) {
            paramMap.put("fjh", StringUtils.deleteWhitespace(fjh));
        }
        if (StringUtils.isNotBlank(dyh)) {
            paramMap.put("dyh", StringUtils.deleteWhitespace(dyh));
        }
        return fwHsFeignService.listFwHsByPageJson(pageable, JSONObject.toJSONString(paramMap));
    }


    /**
     * 关联功能查询户室
     *
     * @param pageable
     * @param dyh
     * @param fwDcbIndex
     * @param fjh
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/gl/listbypage")
    public Object glListByPage(@LayuiPageable Pageable pageable,
                               @NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex,
                               String fjh, String dyh,String ycfwbm){
        Map paramMap = new HashMap();
        paramMap.put("fwDcbIndex", fwDcbIndex);
        if (StringUtils.isNotBlank(fjh)) {
            paramMap.put("fjh", StringUtils.deleteWhitespace(fjh));
        }
        if (StringUtils.isNotBlank(dyh)) {
            paramMap.put("dyh", StringUtils.deleteWhitespace(dyh));
        }
        if (StringUtils.isNotBlank(ycfwbm)) {
            paramMap.put("ycfwbms", Arrays.asList(StringUtils.split(ycfwbm, ",")));
        }
        return fwHsFeignService.glListFwHsByPageJson(pageable, JSONObject.toJSONString(paramMap));
    }


    /**
     * 新增或修改单个房屋户室
     *
     * @param fwHsDO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertorupdate")
    public Map insertOrUpdateFwHs(FwHsDO fwHsDO) {
        Map resultMap = new HashMap();
        if (fwHsDO != null && StringUtils.isNoneBlank(fwHsDO.getFwDcbIndex())) {
            if (StringUtils.isBlank(fwHsDO.getFwHsIndex())) {
                resultMap.put("data", fwHsFeignService.insertFwHs(fwHsDO,""));
            } else {
                fwHsFeignService.updateFwHs(fwHsDO, false,"");
            }
            resultMap.putAll(returnHtmlResult(true, "成功"));
        } else {
            resultMap.putAll(returnHtmlResult(false, "必要数据为空"));
        }
        return resultMap;
    }

    /**
     * 获得房屋户室信息所需要的字典项
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/mapszd")
    public Map mapsFwhsZd() {
        return getZdList(new Class[]{SZdBoolDO.class, SZdDldmDO.class, SZdFwhxDO.class, SZdFwlxDO.class,
                SZdFwxzDO.class, SZdFwytDO.class, SZdQtgsDO.class, SZdHxjgDO.class, SZdJczxcdDO.class, SZdTdsyqlxDO.class});
    }

    /**
     * @param vo
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 批量更新面积
     */
    @RequestMapping(value = "/batchupdate")
    @ResponseBody
    public Map batchUpdateHsMj(@Validated BatchUpdateFwhsVO vo) {
        Map<String, Object> paramMap = new HashMap<>();
        FwhsBatchUpdateRequestDTO dto = new FwhsBatchUpdateRequestDTO();
        BeanUtils.copyProperties(vo, dto);
        if (CollectionUtils.isNotEmpty(vo.getUpdateParamList())) {
            for (String param : vo.getUpdateParamList()) {
                String[] paramArr = param.split("_");
                if (paramArr.length == 2) {
                    paramMap.put(paramArr[0], StringUtils.equals("null", paramArr[1]) ? null : paramArr[1]);
                }
            }
            dto.setParamMap(paramMap);
        }
        fwHsFeignService.batchUpdateFwhs(dto);
        return returnHtmlResult(true, "修改成功");
    }

    /**
     * @param fwDcbIndex
     * @param fwHsIndexList
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改逻辑幢
     */
    @ResponseBody
    @RequestMapping("/updateLjz")
    public Map updateLjz(@NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex, @RequestParam(name = "fwHsIndexList", required = false) List<String> fwHsIndexList) {
        fwHsFeignService.updateFwHsLjz(fwHsIndexList, fwDcbIndex,"");
        return returnHtmlResult(true, "修改成功");
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param jsonData
     * @return java.util.Map
     * @description 新增或修改户室 及 权利人
     */
    @ResponseBody
    @RequestMapping("/saveorupdate")
    public Map saveOrUpdate(@NotBlank(message = "参数不能为空") String jsonData){
        Map resultMap = new HashMap();
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        JSONObject fwhsObject = jsonObject.getJSONObject("info");
        JSONArray qlrJsonArr = jsonObject.getJSONArray("qlrList");
        if(fwhsObject != null){
            String fwHsIndex = fwhsObject.getString("fwHsIndex");
            if(StringUtils.isNotBlank(fwHsIndex)){
                // 更新
                entityFeignService.updateEntityByJson(fwhsObject.toJSONString(),FwHsDO.class.getName());
            }else{
                // 新增
                FwHsDO fwHsDO = fwhsObject.toJavaObject(FwHsDO.class);
                FwHsDO result = fwHsFeignService.insertFwHs(fwHsDO,"");
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

    /**
     * @param fwHsIndex
     * @return java.util.List<java.util.List   <   cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryDTO>>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取房屋历史变更记录
     */
    @ResponseBody
    @RequestMapping(value = "/hsbghistory")
    public List<List<FwHsBgHistoryDTO>> getHsBgHistoryByFwHsIndex(String fwHsIndex) {
        List<List<FwHsBgHistoryDTO>> lists = fwHsHistroyFeignService.getHsBgHistoryByFwHsIndex(fwHsIndex,"");
        return lists;
    }

    /**
     * @param fwHsIndex
     * @return java.util.List<java.util.List   <   cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryDTO>>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取房屋历史变更记录
     */
    @ResponseBody
    @RequestMapping(value = "/hsbghistorynew")
    public List<FwHsBgHistoryNewDTO> getHsBgHistoryByBdcdyh(String bdcdyh) {
        return fwHsHistroyFeignService.getHsBgHistoryNewByBdcdyh(bdcdyh,"");
    }

    /**
     * @param model
     * @param fwHsIndex
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋历史户室页面
     */
    @RequestMapping(value = "/hsbghistoryview")
    public String view(Model model, String fwHsIndex,String bdcdyh) {
        model.addAttribute("fwHsIndex", fwHsIndex);
        model.addAttribute("bdcdyh", bdcdyh);
        return "fwhs/fwhsBgHistoryNew";
    }

    /**
     * @param model
     * @param fwHsIndex
     * @param last
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 户室变更记录页面  查看户室详情
     */
    @RequestMapping(value = "/historyhs")
    public String historyHs(Model model, String fwHsIndex, boolean last) {
        model.addAttribute("fwHsIndex", fwHsIndex);
        if (last) {
            model.addAttribute("last", "true");
        }
        return "fwhs/historyHs";
    }

    /**
     * 根据主键获得房屋户室信息
     *
     * @param fwHsIndex
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/infohistoryfwhs")
    public FwHsDO infoHistoryFwHs(@NotBlank(message = "主键不能为空") String fwHsIndex, boolean last) {
        return fwHsHistroyFeignService.getHFwHsByFwHsIndex(fwHsIndex, last,"");
    }

    /**
     * @param bgbh
     * @param bglx
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询房屋变更记录
     */
    @ResponseBody
    @RequestMapping(value = "/listhsbghistory")
    public Page<SSjHsbgljbDO> listHsbgHsitroyByPageJson(@LayuiPageable Pageable pageable, String bgbh, String bglx) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(bgbh)) {
            paramMap.put("bgbh", bgbh);
        }
        if (StringUtils.isNotBlank(bglx)) {
            paramMap.put("bglx", bglx);
        }
        return fwHsHistroyFeignService.listHsbgHsitroyByPageJson(pageable, JSONObject.toJSONString(paramMap));
    }

    /**
     * @param fwHsIndex
     * @return java.util.Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 验证房屋是否有变更记录
     */
    @ResponseBody
    @RequestMapping(value = "/checkbgjl")
    public Map checkFwHsBgJl(String fwHsIndex) {
        boolean check = fwhsBgFeignService.checkFwHsHistory(fwHsIndex,"");
        return returnHtmlResult(check, "当前户室无变更记录");
    }
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @description 根据不动产单元号查询户室信息
     */
    @ResponseBody
    @RequestMapping(value = "/bdcdyh")
    public FwHsDO queryFwhsByBdcdyh(String bdcdyh){
        return fwHsFeignService.queryFwhsByBdcdyh(bdcdyh,"");
    }


}
