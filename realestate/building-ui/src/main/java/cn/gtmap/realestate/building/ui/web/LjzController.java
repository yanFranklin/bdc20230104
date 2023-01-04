package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.gtc.clients.ElementClient;
import cn.gtmap.realestate.building.ui.core.vo.BuildLpbVO;
import cn.gtmap.realestate.building.ui.util.Constants;
import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dto.building.LpbGJRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.yth.YthTsyclpDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.building.*;
import cn.gtmap.realestate.common.core.service.feign.exchange.YanchengYthFeignService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2018/12/11
 * @description
 */
@Controller
@Validated
@RequestMapping("ljz")
public class LjzController extends BaseController {
    @Autowired
    FwLjzFeginService fwLjzFeginService;

    @Autowired
    private ZrzFeignService zrzFeignService;

    @Autowired
    private LpbGjFeignService lpbGjFeignService;

    @Autowired
    private FwFcqlrFeignService fwFcqlrFeignService;

    @Autowired
    private EntityFeignService entityFeignService;

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private ElementClient elementCient;

    @Autowired
    FwYcHsFeignService fwYcHsFeignService;

    @Autowired
    YanchengYthFeignService yanchengYthFeignService;


    /**
     * 逻辑幢列表页面
     *
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model, String moduleCode, boolean sfyc) {
        model.addAttribute("moduleCode", moduleCode);
        model.addAttribute("sfyc", sfyc);
        return getFtlPath("/version/ljz/queryLjzList");
    }



    /**
     * 宗地，自然幢，逻辑幢目录树结构
     *
     * @param model
     * @param fwDcbIndex
     * @param djh
     * @return
     */
    @RequestMapping(value = "/saveorupdateljz")
    public String tree(Model model, String fwDcbIndex, String djh, String fwXmxxIndex) {
        model.addAttribute("fwDcbIndex", fwDcbIndex);
        model.addAttribute("fwXmxxIndex", fwXmxxIndex);
        model.addAttribute("djh", djh);
        return "ljz/ljzForm";
    }

    /**
     * @param model
     * @param fwDcbIndex
     * @param bdcdyfwlx
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构建楼盘表页面
     */
    @RequestMapping("/buildlpbview")
    public String buildLpbView(Model model, String fwDcbIndex, String bdcdyfwlx,String qjgldm) {
        model.addAttribute("fwDcbIndex", fwDcbIndex);
        model.addAttribute("bdcdyfwlx", bdcdyfwlx);
        model.addAttribute("qjgldm", qjgldm);
        return "ljz/buildLpb";
    }

    /**
     * 逻辑幢列表分页
     *
     * @param pageable
     * @param bdcdyh
     * @param fwXmxxIndex
     * @param lszd
     * @param zldz
     * @param zrzh
     * @param fwmc
     * @param ljzh
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listbypage")
    public Object listLjzByPageJson(@LayuiPageable Pageable pageable, String bdcdyh,
                                    String lszd, String zldz,
                                    String zrzh, String fwmc,
                                    String ljzh, String fwXmxxIndex,
                                    String notfwXmxxIndex, boolean sfyc, String zdqlr,String qjgldm) {
        //处理前台传递的分页参数
        Map<String, String> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            paramMap.put("bdcdyh", StringUtils.deleteWhitespace(bdcdyh));
        }
        if (StringUtils.isNotBlank(lszd)) {
            paramMap.put("lszd", StringUtils.deleteWhitespace(lszd));
        }
        if (StringUtils.isNotBlank(zldz)) {
            paramMap.put("zldz", zldz);
        }
        if (StringUtils.isNotBlank(zrzh)) {
            paramMap.put("zrzh", zrzh);
        }
        if (StringUtils.isNotBlank(fwmc)) {
            paramMap.put("fwmc", fwmc);
        }
        if (StringUtils.isNotBlank(ljzh)) {
            paramMap.put("ljzh", ljzh);
        }
        if (StringUtils.isNotBlank(fwXmxxIndex)) {
            paramMap.put("fwXmxxIndex", fwXmxxIndex);
        }
        if (StringUtils.isNotBlank(notfwXmxxIndex)) {
            paramMap.put("notfwXmxxIndex", notfwXmxxIndex);
        }
        if (StringUtils.isNotBlank(zdqlr)) {
            paramMap.put("zdqlr", StringUtils.deleteWhitespace(zdqlr));
        }
        if (StringUtils.isNotBlank(qjgldm)) {
            paramMap.put("qjgldm", StringUtils.deleteWhitespace(qjgldm));
        }
        paramMap.put("bdcdyhmh", "4");
        paramMap.put("dhmh", "4");
        paramMap.put("lszdmh", "4");
        paramMap.put("fwmcmh", "4");
        paramMap.put("zldzmh", "4");
        if (sfyc) {
            return fwLjzFeginService.listYcLjzByPageJson(pageable, JSONObject.toJSONString(paramMap));
        }
        return fwLjzFeginService.listLjzByPageJson(pageable, JSONObject.toJSONString(paramMap));
    }

    /**
     * 根据逻辑幢id获取逻辑幢信息
     *
     * @param fwDcbIndex
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/infoljz")
    public FwLjzDO infoljz(@NotBlank(message = "主键不能为空") String fwDcbIndex) {
        return fwLjzFeginService.queryLjzByFwDcbIndex(fwDcbIndex,"");
    }

    /**
     * 根据主键删除逻辑幢
     *
     * @param fwDcbIndex
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delbyfwdcbindex")
    public Map deleteLjzByFwDcbIndex(@NotBlank(message = "主键不能为空") String fwDcbIndex) {
        fwLjzFeginService.deleteLjzByFwDcbIndex(fwDcbIndex,"");
        return returnHtmlResult(true, "成功");
    }

    /**
     * 新增或修改单个房屋户室
     *
     * @param fwLjzDO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertorupdate")
    public Map insertOrUpdateFwHs(FwLjzDO fwLjzDO) {
        Map resultMap = new HashMap();
        if (StringUtils.isNoneBlank(fwLjzDO.getLszd(), fwLjzDO.getZrzh())) {
            if (StringUtils.isBlank(fwLjzDO.getFwDcbIndex())) {
                resultMap.put("data", fwLjzFeginService.insertFwLjz(fwLjzDO,""));
            } else {
                fwLjzFeginService.updateFwLjz(fwLjzDO, false,"");
            }
            resultMap.putAll(returnHtmlResult(true, "成功"));
        } else {
            resultMap.putAll(returnHtmlResult(false, "请先挂接宗地与自然幢"));
        }
        return resultMap;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param jsonData
     * @return java.util.Map
     * @description 新增或修改预测户室实体
     */
    @ResponseBody
    @RequestMapping(value = "/saveorupdate")
    public Map saveorupdate(@NotBlank(message = "参数不能为空") String jsonData){

        Map resultMap = new HashMap();
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        JSONObject ljzObject = jsonObject.getJSONObject("info");
        JSONArray qlrJsonArr = jsonObject.getJSONArray("qlrList");
        String zrzh = ljzObject.getString("zrzh");
        String lszd = ljzObject.getString("lszd");
        if(StringUtils.isBlank(zrzh) && StringUtils.isNotBlank(lszd)){
            zrzh = (zrzFeignService.getMaxZrzhByDjh(lszd,"") + 1) + "";
            ljzObject.put("zrzh",zrzh);
        }
        if(ljzObject != null){
            String fwDcbIndex = ljzObject.getString("fwDcbIndex");
            if(StringUtils.isNotBlank(fwDcbIndex)){
                FwLjzDO yFwLjz = fwLjzFeginService.queryLjzByFwDcbIndex(fwDcbIndex,"");
                if(yFwLjz != null){
                    // 更新
                    entityFeignService.updateEntityByJson(ljzObject.toJSONString(),FwLjzDO.class.getName());

                    // 判断是否有必要刷新BDCDYH
                    boolean notSameDjh = !StringUtils.equals(yFwLjz.getLszd(),lszd);
                    boolean notSameZrzh = !StringUtils.equals(yFwLjz.getZrzh(),zrzh);
                    if(notSameDjh || notSameZrzh){
                        FwLjzDO fwLjzDO = new FwLjzDO();
                        fwLjzDO.setFwDcbIndex(fwDcbIndex);
                        fwLjzDO.setBdcdyfwlx(yFwLjz.getBdcdyfwlx());
                        fwLjzDO.setLszd(lszd);
                        fwLjzDO.setZrzh(zrzh);
                        fwLjzFeginService.fwljzRelevanceZd(fwLjzDO,"");
                    }
                    resultMap.put("data", fwLjzFeginService.queryLjzByFwDcbIndex(fwDcbIndex,""));
                }else{
                    return returnHtmlResult(false,"更新逻辑幢不存在");
                }
            }else{
                // 新增
                FwLjzDO fwLjzDO = ljzObject.toJavaObject(FwLjzDO.class);
                FwLjzDO result = fwLjzFeginService.insertFwLjz(fwLjzDO,"");
                if(result != null && StringUtils.isNotBlank(result.getFwDcbIndex())){
                    fwDcbIndex = result.getFwDcbIndex();
                    resultMap.put("data", result);
                }
            }
            if(StringUtils.isNotBlank(fwDcbIndex)){
                // 先删除
                fwFcqlrFeignService.deleteFcQlrByFwIndex(fwDcbIndex,"");
                if(CollectionUtils.isNotEmpty(qlrJsonArr)){
                    List<FwFcqlrDO> insertList = new ArrayList<>();
                    for(int i = 0 ; i < qlrJsonArr.size() ; i++){
                        JSONObject qlrObject = qlrJsonArr.getJSONObject(i);
                        qlrObject.put("qlrbh",i+1);
                        qlrObject.put("fwIndex",fwDcbIndex);
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
     * @param buildLpbVO
     * @return java.util.Map
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @ResponseBody
    @RequestMapping("/build")
    public Map buildLpb(@Validated BuildLpbVO buildLpbVO) {
        LpbGJRequestDTO lpbGJRequestDTO = new LpbGJRequestDTO();
        BeanUtils.copyProperties(buildLpbVO, lpbGJRequestDTO);
        if (StringUtils.equals(Constants.LPBGJ_DYHSDTGJ, buildLpbVO.getGjfs())) {
            String dtgjJson = buildLpbVO.getDtgjJson();
            JSONArray jsonArray = JSONObject.parseArray(dtgjJson);
            if (CollectionUtils.isNotEmpty(jsonArray)) {
                List<String> hsdtgjList = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    Map map = jsonArray.getObject(i, Map.class);
                    hsdtgjList.add(MapUtils.getString(map, "dyh") + "," + MapUtils.getString(map, "hs"));
                }
                if (CollectionUtils.isNotEmpty(hsdtgjList)) {
                    lpbGJRequestDTO.setHsdtgj(hsdtgjList);
                }
            }
        }
        lpbGjFeignService.lpbGj(lpbGJRequestDTO);
        return returnHtmlResult(true, "构建成功");
    }

    /**
     * @param lszd
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取逻辑幢号
     */
    @ResponseBody
    @RequestMapping("/getljzh")
    public String getLjzh(String lszd) {
        return fwLjzFeginService.creatLjzh(lszd,"");
    }


    /**
     * @param model
     * @return java.lang.String
     * @author
     * @description 逻辑幢分页查询弹窗
     */
    @RequestMapping(value = "/picklist")
    public String pickList(Model model, String fwDcbIndex ,String zrzh ,String lszd,String showQuery,String qjgldm) {
        model.addAttribute("zrzh", zrzh);
        model.addAttribute("lszd", lszd);
        model.addAttribute("fwDcbIndex", fwDcbIndex);
        model.addAttribute("showQuery",showQuery);
        model.addAttribute("qjgldm",qjgldm);
        return "ljz/pickLjzlist";
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwDcbIndex
     * @return java.util.Map
     * @description 取消挂接宗地
     */
    @RequestMapping(value = "/qxgjzd")
    @ResponseBody
    public Map qxgjzd(@NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex){
        fwLjzFeginService.fwljzQxgjzd(fwDcbIndex,"");
        return returnHtmlResult(true, "取消成功");
    }

    /**
     * @param fwDcbIndexList
     * @return java.util.Map
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据主键删除逻辑幢
     */
    @ResponseBody
    @RequestMapping("/delbyindex")
    public Map delByIndex(@NotEmpty(message = "逻辑幢列表不能为空")
                          @RequestParam(name = "fwDcbIndexList", required = false) List<String> fwDcbIndexList,String qjgldm) {
        for (String fwDcbIndex : fwDcbIndexList) {
            fwLjzFeginService.deleteLjzByFwDcbIndex(fwDcbIndex,qjgldm);
        }
        return returnHtmlResult(true, "删除成功");
    }


    /**
     * @param moduleCode 页面编号
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取模块的权限状态
     */
    @ResponseBody
    @GetMapping("/moduleAuthority/{moduleCode}")
    public Object queryModuleState(@PathVariable(name = "moduleCode") String moduleCode) {
        String currentUserName = userManagerUtils.getCurrentUserName();
        if (StringUtils.isBlank(currentUserName)) {
            throw new MissingArgumentException("无法获取到当前用户信息");
        }
        return elementCient.getAuthorities(currentUserName, moduleCode);
    }

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/12/29 9:52
     */
    @ResponseBody
    @GetMapping("/tsycljz")
    public Object tsYcLjz(String fwDcbIndex,String qjgldm) {
        if (StringUtils.isBlank(fwDcbIndex)) {
            throw new AppException("推送预测楼盘表缺失fw_dcb_index");
        }
        LOGGER.info("推送预测楼盘传入参数内容{}", fwDcbIndex);
        YthTsyclpDTO ythTsyclpDTO = new YthTsyclpDTO();
        ythTsyclpDTO.setFwDcbIndex(fwDcbIndex);
        ythTsyclpDTO.setQjgldm(qjgldm);
        CommonResponse commonResponse = yanchengYthFeignService.ythTsyclp(ythTsyclpDTO);
        LOGGER.info("推送预测楼盘传入返回结果{}", JSONObject.toJSONString(commonResponse));
        if (Objects.nonNull(commonResponse)) {
            return commonResponse;
        }
        return null;
    }
}
