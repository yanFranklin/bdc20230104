package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.core.vo.BdcdyFwlxBgVO;
import cn.gtmap.realestate.building.ui.util.Constants;
import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.ZdQlrDO;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/16
 * @description
 */
@Controller
@Validated
@RequestMapping("xmxx")
public class FwXmxxController extends BaseController {
    @Autowired
    private FwXmxxFeignService fwXmxxFeignService;

    @Autowired
    private BdcdyhFeignService bdcdyhFeignService;

    @Autowired
    private EntityFeignService entityFeignService;

    @Autowired
    private FwFcqlrFeignService fwFcqlrFeignService;

    @Autowired
    private  ZdFeignService zdFeignService;

    /**
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 项目信息分页查询页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model,String lszd) {
        model.addAttribute("lszd",lszd);
        return "xmxx/fwXmxxList";
    }

    /**
     * 宗地，自然幢，逻辑幢目录树结构
     *
     * @param model
     * @param lszd
     * @return
     */
    @RequestMapping(value = "/form")
    public String form(Model model, String lszd, String fwXmxxIndex,String bdcdyhfwlxBg) {
        model.addAttribute("fwXmxxIndex", fwXmxxIndex);
        model.addAttribute("lszd", lszd);
        //跟权籍一致  不动产单元房屋类型变更，新增项目的时候默认赋值xmmc
        if(StringUtils.isNotBlank(bdcdyhfwlxBg)){
            StringBuilder stringBuilder=new StringBuilder();
            ZdDjdcbDO zdDjdcbDO=zdFeignService.queryZdByDjh(lszd,"");
            if(zdDjdcbDO !=null){
                if(StringUtils.isNotBlank(zdDjdcbDO.getTdzl())){
                    stringBuilder.append(zdDjdcbDO.getTdzl());
                }
                if(StringUtils.isNotBlank(zdDjdcbDO.getBdcdyh())){
                    List<ZdQlrDO> zdQlrDOS=zdFeignService.listZdQlrByBdcdyh(zdDjdcbDO.getBdcdyh(),"");
                    if(CollectionUtils.isNotEmpty(zdQlrDOS)){
                        for(ZdQlrDO zdQlrDO:zdQlrDOS){
                            if(StringUtils.isNotBlank(zdQlrDO.getQlrmc())){
                                stringBuilder.append(zdQlrDO.getQlrmc());
                            }
                        }
                    }
                }
                model.addAttribute("xmmc", stringBuilder.toString());
            }
        }
        return "xmxx/xmxxForm";
    }

    /**
     * 房屋类型变换为多幢后需要选择项目信息
     *
     * @param model
     * @param bginfo
     * @return
     */
    @RequestMapping(value = "/pickxmxx")
    public String pickxmxx(Model model, String lszd,
                           @NotNull(message = "变更实体不能为空") BdcdyFwlxBgVO bginfo) {
        model.addAttribute("bginfo",JSONObject.toJSONString(bginfo));
        model.addAttribute("lszd", lszd);
        return "xmxx/bdcdyfwlxPickXmxx";
    }

    /**
     * @param pageable
     * @param bdcdyh
     * @param lszd
     * @param zl
     * @return java.lang.Object
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询
     */
    @ResponseBody
    @RequestMapping(value = "/listbypage")
    public Object listXmxxByPageJson(@LayuiPageable Pageable pageable,
                                     String bdcdyh, String xmmc, String zl, String lszd) {
        //处理前台传递的分页参数
        Map paramMap = new HashMap();
        if (StringUtils.isNotBlank(bdcdyh)) {
            paramMap.put("bdcdyh", StringUtils.deleteWhitespace(bdcdyh));
        }
        if (StringUtils.isNotBlank(xmmc)) {
            paramMap.put("xmmc", xmmc);
        }
        if (StringUtils.isNotBlank(zl)) {
            paramMap.put("zl", zl);
        }
        if (StringUtils.isNotBlank(lszd)) {
            paramMap.put("lszd", StringUtils.deleteWhitespace(lszd));
        }
        return fwXmxxFeignService.listXmxxByPageJson(pageable, JSONObject.toJSONString(paramMap));
    }

    /**
     * 根据逻辑幢id获取逻辑幢信息
     *
     * @param fwXmxxIndex
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/infofwxmxx")
    public FwXmxxDO infofwxmxx(@NotBlank(message = "主键不能为空") String fwXmxxIndex) {
        return fwXmxxFeignService.queryXmxxByFwXmxxIndex(fwXmxxIndex,"");

    }

    /**
     * 新增或修改单个项目信息
     *
     * @param fwXmxxDO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertorupdate")
    public Map insertOrUpdateFwHs(FwXmxxDO fwXmxxDO) {
        Map resultMap = new HashMap();
        if (fwXmxxDO != null && StringUtils.isNotBlank(fwXmxxDO.getLszd())) {
            if (StringUtils.isBlank(fwXmxxDO.getFwXmxxIndex())) {
                resultMap.put("data", fwXmxxFeignService.insertFwXmxx(fwXmxxDO,""));
            } else {
                fwXmxxFeignService.updateFwXmxx(fwXmxxDO, false,"");
            }
            resultMap.putAll(returnHtmlResult(true, "成功"));
        } else {
            resultMap.putAll(returnHtmlResult(false, "必要数据为空"));
        }
        return resultMap;
    }


    /**
     * @param jsonData
     * @return java.util.Map
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存或更新
     */
    @ResponseBody
    @RequestMapping("/saveorupdate")
    public Map saveOrUpdate(@NotBlank(message = "参数不能为空") String jsonData) {
        Map resultMap = new HashMap();
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        JSONObject xmxxObject = jsonObject.getJSONObject("info");
        JSONArray qlrJsonArr = jsonObject.getJSONArray("qlrList");
        String lszd = xmxxObject.getString("lszd");
        if (xmxxObject != null) {
            String fwXmxxIndex = xmxxObject.getString("fwXmxxIndex");
            if(StringUtils.isNotBlank(fwXmxxIndex)){
                FwXmxxDO yfwXmxxDO = fwXmxxFeignService.queryXmxxByFwXmxxIndex(fwXmxxIndex,"");
                if(yfwXmxxDO != null){
                    // 如果LSZD为空 BDCZT更新 为0
                    if(StringUtils.isBlank(lszd)){
                        xmxxObject.put("bdczt",Constants.BDCZT_BKY);
                    }
                    // 更新
                    entityFeignService.updateEntityByJson(xmxxObject.toJSONString(), FwXmxxDO.class.getName());
                    // 挂接宗地 更新 BDCDYH
                    if(StringUtils.isNotBlank(lszd)
                            && !StringUtils.equals(yfwXmxxDO.getLszd(),lszd)){
                        gjzd(yfwXmxxDO,lszd);
                    }
                    resultMap.put("data", fwXmxxFeignService.queryXmxxByFwXmxxIndex(fwXmxxIndex,""));
                }

            }else{
                // 新增
                FwXmxxDO xmxxDO = xmxxObject.toJavaObject(FwXmxxDO.class);
                FwXmxxDO result = fwXmxxFeignService.insertFwXmxx(xmxxDO,"");
                if (result != null && StringUtils.isNotBlank(result.getFwXmxxIndex())) {
                    fwXmxxIndex = result.getFwXmxxIndex();
                    resultMap.put("data", result);
                }
            }


            if (StringUtils.isNotBlank(fwXmxxIndex)) {
                // 先删除
                fwFcqlrFeignService.deleteFcQlrByFwIndex(fwXmxxIndex,"");
                if(CollectionUtils.isNotEmpty(qlrJsonArr)){
                    List<FwFcqlrDO> insertList = new ArrayList<>();
                    for (int i = 0; i < qlrJsonArr.size(); i++) {
                        JSONObject qlrObject = qlrJsonArr.getJSONObject(i);
                        qlrObject.put("qlrbh", i + 1);
                        qlrObject.put("fwIndex", fwXmxxIndex);
                        FwFcqlrDO fwFcqlrDO = qlrObject.toJavaObject(FwFcqlrDO.class);
                        insertList.add(fwFcqlrDO);
                    }
                    if (CollectionUtils.isNotEmpty(insertList)) {
                        fwFcqlrFeignService.batchInsert(insertList);
                    }
                }
            }
        } else {
            resultMap.putAll(returnHtmlResult(false, "参数异常"));
            return resultMap;
        }
        resultMap.putAll(returnHtmlResult(true, "提交成功"));
        return resultMap;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param yfwXmxxDO
     * @param lszd
     * @return void
     * @description 挂接宗地
     */
    private void gjzd(FwXmxxDO yfwXmxxDO,String lszd){
        if(yfwXmxxDO != null && StringUtils.isNotBlank(lszd)){
            String bdcdyh = bdcdyhFeignService.creatFwBdcdyhByDjhAndZrzh(lszd,Constants.BDCDYH_XMXX_ZRZH);
            FwXmxxDO fwXmxxDO = new FwXmxxDO();
            fwXmxxDO.setFwXmxxIndex(yfwXmxxDO.getFwXmxxIndex());
            fwXmxxDO.setBdcdyh(bdcdyh);
            fwXmxxFeignService.updateFwXmxx(fwXmxxDO,false,"");
        }
    }

    /**
     * @param fwXmxxIndexList
     * @return java.util.Map
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据主键删除户室
     */
    @ResponseBody
    @RequestMapping("/delbyindex")
    public Map delByIndex(@NotEmpty(message = "项目列表不能为空")
                          @RequestParam(name = "fwXmxxIndexList", required = false) List<String> fwXmxxIndexList,
                          boolean delFwK) {
        fwXmxxFeignService.batchDelFwXmxxIndex(fwXmxxIndexList,delFwK,"");
        return returnHtmlResult(true, "删除成功");
    }

    /**
     * @param model
     * @return java.lang.String
     * @author
     * @description 逻辑幢分页查询弹窗
     */
    @RequestMapping(value = "/relevanceljzlist")
    public String relevanceLjzlist(Model model,String lszd) {
        model.addAttribute("lszd",lszd);
        return "ljz/relevanceLjzlist";
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwXmxxIndex
     * @return java.lang.Integer
     * @description 项目信息关联逻辑幢
     */
    @ResponseBody
    @RequestMapping("/relevanceljz")
    public Map relevanceLjz(@NotBlank(message = "项目信息主键不能为空")String fwXmxxIndex,
                            @NotBlank(message = "逻辑幢主键不能为空")String fwDcbIndex) {

        int result = fwXmxxFeignService.relevanceLjz(fwXmxxIndex,fwDcbIndex,"");
        if(result!=0){
            return returnHtmlResult(true, "关联成功");
        }else {
            return returnHtmlResult(false, "关联失败");
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwXmxxIndex
     * @return java.util.Map
     * @description 取消挂接宗地
     */
    @RequestMapping(value = "/qxgjzd")
    @ResponseBody
    public Map qxgjzd(@NotBlank(message = "项目信息主键不能为空") String fwXmxxIndex){
        FwXmxxDO fwXmxxDO = fwXmxxFeignService.queryXmxxByFwXmxxIndex(fwXmxxIndex,"");
        if(fwXmxxDO != null){
            fwXmxxDO.setFwXmxxIndex(fwXmxxIndex);
            fwXmxxDO.setBdcdyh(null);
            fwXmxxDO.setLszd(null);
            fwXmxxFeignService.updateFwXmxx(fwXmxxDO,true,"");
            return returnHtmlResult(true,"取消关联成功");
        }else{
            return returnHtmlResult(false,"项目信息为空");
        }
    }

    /**
     * @param model
     * @return java.lang.String
     * @author
     * @description 逻辑幢分页查询弹窗
     */
    @RequestMapping(value = "/cancelrelevanceljzview")
    public String cancelRelevanceLjzView(Model model,String fwDcbIndex) {
        model.addAttribute("fwDcbIndex",fwDcbIndex);
        return "ljz/cancelRelevanceLjzView";
    }
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwDcbIndex
     * @return java.lang.Integer
     * @description 项目信息取消关联逻辑幢
     */
    @ResponseBody
    @RequestMapping("/cancelrelevanceljz")
    public Map cancelRelevanceLjz(@NotBlank(message = "逻辑幢主键不能为空")String fwDcbIndex,
                                  @NotBlank(message = "不动产单元房屋类型")String bdcdyfwlx) {

        int result=fwXmxxFeignService.cancelRelevanceLjz(fwDcbIndex,bdcdyfwlx,"");
        if(result!=0){
            return returnHtmlResult(true, "取消关联成功");
        }else {
            return returnHtmlResult(false, "取消关联失败");
        }
    }
}