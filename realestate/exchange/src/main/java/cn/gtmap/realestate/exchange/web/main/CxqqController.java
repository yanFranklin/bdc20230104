package cn.gtmap.realestate.exchange.web.main;

import cn.gtmap.gtc.formclient.common.result.TreeModel;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.StringUtil;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.core.domain.sjpt.*;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.gx.GxCxqqService;
import cn.gtmap.realestate.exchange.util.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
 * @version 1.0
 * @description 查询请求
 */
@Controller
@RequestMapping("/realestate-exchange/cxqq")
public class CxqqController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CxqqController.class);

    @Autowired
    private GxCxqqService gxCxqqService;

    @Resource(name = "sjptEntityMapper")
    private EntityMapper entityMapper;

    @Resource(name = "sjptRepository")
    private Repo repository;

    /**
     * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
     * @description 查询请求
     */
    @ResponseBody
    @RequestMapping("/list/getByPage")
    public Object getByPage(@LayuiPageable Pageable pageable, String cxsqdh, String cxjgbs, String cxywlb, String zt, String kssj, String jssj){
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotBlank(cxsqdh)) {
            map.put("cxsqdh", cxsqdh);
        }
        if (StringUtils.isNotBlank(cxjgbs)) {
            map.put("cxjgbs", cxjgbs);
        }
        if (StringUtils.isNotBlank(cxywlb)) {
            map.put("cxywlb", cxywlb);
        }
        if (StringUtils.isNotBlank(zt)) {
            map.put("zt", zt);
        }
        if (StringUtils.isNotBlank(kssj)) {
            map.put("kssj", kssj);
        }
        if (StringUtils.isNotBlank(jssj)) {
            map.put("jssj", jssj);
        }
        return gxCxqqService.listCxqqByPages(pageable,map);
    }

    /**
     * @param pageable 分页参数
     * @param cxsqdh 查询申请单号
     * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
     * @description 查询人信息
     */
    @ResponseBody
    @RequestMapping("/list/getCxrByPage")
    public Object getCqrByPage(@LayuiPageable Pageable pageable, String cxsqdh) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotBlank(cxsqdh)) {
            map.put("cxsqdh", cxsqdh);
        }
        return gxCxqqService.getCxrByPage(pageable, map);
    }


    /**
     * @param pageable
     * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
     * @description 获取日志
     */
    @ResponseBody
    @RequestMapping("/list/getRzByPage")
    public Object getRzByPage(@LayuiPageable Pageable pageable, String czr, String czlx, String kssj, String jssj) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotBlank(czr)) {
            map.put("czr", czr);
        }
        if (StringUtils.isNotBlank(czlx)) {
            map.put("czlx", czlx);
        }
        if (StringUtils.isNotBlank(kssj)) {
            map.put("kssj", kssj);
        }
        if (StringUtils.isNotBlank(jssj)) {
            map.put("jssj", jssj);
        }
        return gxCxqqService.getRzByPage(pageable,map);
    }


    /**
     * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
     * @description 获取日志操作类型
     */
    @RequestMapping("/list/getCzlx")
    @ResponseBody
    public Map<String, Object> getCzlx() {
        Map<String, Object> result = new HashMap<>();
        List<GxPeZdrzczlx> gxPeZdRzczlxList = gxCxqqService.getCzlx();
        result.put("gxPeZdRzczlxList",gxPeZdRzczlxList);
        return result;
    }

    @ResponseBody
    @RequestMapping("/query/apply")
    public Map<String,String> queryApply() {
        LOGGER.info("********************手动获取请求开始，时间："+ DateUtil.getCurTime());
        Map<String,String> resultMap = new HashMap();
        try{
            ExchangeBean exchangeBean = ExchangeBean.getExchangeBean("sjpt_saveCxsqWithLog");
            if(exchangeBean != null){
                InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,new HashMap<>());
                Object responseBody = requestBuilder.invoke();
                if (responseBody != null) {
                    JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(responseBody));
                    if (jsonObject.get("head") instanceof Map) {
                        Map dataObj = (Map)jsonObject.get("head");
                        if (MapUtils.isNotEmpty(dataObj)) {
                            if(dataObj.get("code")!=null && StringUtils.equals(dataObj.get("code").toString(),"0000")){
                                resultMap.put("msg","获取成功！");
                            } else {
                                LOGGER.error(dataObj.toString());
                                resultMap.put("msg",dataObj.get("msg").toString());
                            }
                        }
                    }
                } else {
                    resultMap.put("msg","获取失败！");
                }
            }else{
                resultMap.put("msg","无法获取名称为：sjpt_saveCxsqWithLog的配置信息");
            }
        } catch (Exception e){
            LOGGER.error("********************手动获取请求异常",e);
            resultMap.put("msg","获取失败！");
        }
        LOGGER.info("********************手动获取请求结束，时间："+ DateUtil.getCurTime());
        return resultMap;
    }

    /**
     * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
     * @param cxsqdh
     */
    @RequestMapping("/query/commit")
    @ResponseBody
    public Map<String, String> executeCommit(String cxsqdh) {
        Map<String,String> resultMap = new HashMap();
        LOGGER.info("********************手动上报查询请求开始，时间："+ DateUtil.getCurTime());
        Object response = gxCxqqService.executeCommit(cxsqdh);
        String responseJsonString = response instanceof String ? (String)response : JSONObject.toJSONString(response);
        JSONObject responseJson = null;
        try{
            responseJson = JSONObject.parseObject(responseJsonString);
        }catch (Exception e){
            JSONArray responseArr = JSONArray.parseArray(responseJsonString);
            if(CollectionUtils.isNotEmpty(responseArr)){
                responseJson = responseArr.getJSONObject(0);
            }
        }
        if(responseJson != null && responseJson.getJSONObject("head") != null){
            JSONObject headJson = responseJson.getJSONObject("head");
            resultMap.put("msg",headJson.getString("msg"));
        } else {
            resultMap.put("msg","上报失败！");
        }
        LOGGER.info("********************手动上报查询请求结束，时间："+ DateUtil.getCurTime());
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/query/commit/cxsqdhList")
    public void executeCommitList( @RequestBody List<String> cxsqdhList){
        gxCxqqService.executeCommitList(cxsqdhList);
    }

    @RequestMapping("/query/executeQuery")
    @ResponseBody
    public Map<String, String> executeQuery(String cxsqdh) {
        Map<String,String> resultMap = new HashMap();
        LOGGER.info("省厅查询：手动查询请求开始, 查询申请单号：{}", cxsqdh);
        if (StringUtils.isNotBlank(cxsqdh)) {
            try {
                gxCxqqService.executeQuery(cxsqdh);
                resultMap.put("msg","成功!");
            } catch (Exception e) {
                resultMap.put("msg","查询异常！"+e.getMessage());
                LOGGER.error(e.getMessage());
            }
        } else {
            resultMap.put("msg","查询申请单号为空!");
        }
        LOGGER.info("省厅查询：手动查询请求结束, 查询申请单号：{}", cxsqdh);
        return resultMap;
    }

    /**
     * @param cxsqdhList
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 省厅批量手动查询
     */
    @RequestMapping("/query/executeQueryList")
    @ResponseBody
    public Map<String, String> executeQueryList( @RequestBody List<String> cxsqdhList){
        Map<String,String> resultMap = new HashMap();
        String msg = "查询申请单号为空!";
        // 查询成功数
        int successCount = 0;
        // 查询失败数
        int failCount = 0;
        if (CollectionUtils.isNotEmpty(cxsqdhList)) {
            for(String cxsqdh:cxsqdhList){
                try {
                    gxCxqqService.executeQuery(cxsqdh);
                    successCount++;
                } catch (Exception e) {
                    failCount++;
                    LOGGER.error("查询异常！查询单号：" + cxsqdh + e.getMessage());
                }
            }
            msg = "查询成功"+ successCount +"条，查询失败" + failCount +"条";
        }
        resultMap.put("msg",msg);
        return resultMap;
    }

    @RequestMapping("/query/cxhqWsxx")
    @ResponseBody
    public Map<String, String> cxhqWsxx(String cxsqdh,String wsbh){
        Map<String,String> resultMap = new HashMap();
        LOGGER.info("********************手动重新获取文书请求开始，时间："+ DateUtil.getCurTime());
        Map paramMap = new HashMap();
        paramMap.put("cxsqdh",cxsqdh);
        paramMap.put("wsbh",wsbh);
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean("sjpt_cxws");
        if(exchangeBean != null){
            InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,paramMap);
            Object responseBody = requestBuilder.invoke();
            if (responseBody != null) {
                JSONObject jsonObject = JSONObject.parseObject(responseBody.toString());
                if (jsonObject.get("head") instanceof Map) {
                    Map dataObj = (Map)jsonObject.get("head");
                    if (MapUtils.isNotEmpty(dataObj)) {
                        if(dataObj.get("code")!=null && StringUtils.equals(dataObj.get("code").toString(),"0000")){
                            resultMap.put("msg","获取成功！");
                        } else {
                            resultMap.put("msg",dataObj.get("msg").toString());
                        }
                    }
                }
            } else {
                resultMap.put("msg","获取失败！");
            }
        }else{
            resultMap.put("msg","无法获取名称为：sjpt_cxws的配置信息");
        }
        LOGGER.info("********************手动重新获取文书请求结束，时间："+ DateUtil.getCurTime());
        return resultMap;
    }

    @RequestMapping("/query/executeQueryXm")
    @ResponseBody
    public Map<String, String> executeQueryXm(String xmid){
        Map<String,String> resultMap = new HashMap();
        LOGGER.info("********************手动查询项目请求开始，时间："+ DateUtil.getCurTime());
        String msg;
        if (StringUtils.isNotBlank(xmid)) {
            msg = gxCxqqService.executeQueryCxqqXm(xmid);
        } else {
            msg = "数据错误!";
        }
        resultMap.put("msg",msg);
        LOGGER.info("********************手动查询项目请求结束，时间："+ DateUtil.getCurTime());
        return resultMap;
    }

    @RequestMapping("/cxjg/loadTree")
    @ResponseBody
    public Object loadCxjgTree(String cxsqdh,String xmid){

        List<TreeModel> treeModelList = new ArrayList<>();

        if(StringUtils.isNotBlank(cxsqdh)){
            //初始化最高层树
            TreeModel topModel = new TreeModel();
            topModel.setId("0");
            topModel.setName(cxsqdh);
            topModel.setIsParent("true");
            treeModelList.add(topModel);

            List<GxPeCxqqXm> gxPeCxqqXmList = new ArrayList<>();

            if(StringUtils.isNotBlank(xmid)){
                GxPeCxqqXm gxPeCxqqXm = entityMapper.selectByPrimaryKey(GxPeCxqqXm.class, xmid);
                gxPeCxqqXmList.add(gxPeCxqqXm);
            }else{
                Example example = new Example(GxPeCxqqXm.class);
                example.createCriteria().andEqualTo("cxsqdh", cxsqdh);
                gxPeCxqqXmList = entityMapper.selectByExample(example);
            }
            treeModelList = initCxjgTree(gxPeCxqqXmList,treeModelList);
        }
        return treeModelList;
    }

    private List<TreeModel> initCxjgTree(List<GxPeCxqqXm> gxPeCxqqXmList,List<TreeModel> treeModelList) {
        if(CollectionUtils.isNotEmpty(gxPeCxqqXmList)){
            for(GxPeCxqqXm gxPeCxqqXm : gxPeCxqqXmList){
                TreeModel gxPeCxqqXmModel = new TreeModel();
                gxPeCxqqXmModel.setId(gxPeCxqqXm.getXmid());
                gxPeCxqqXmModel.setIsParent("true");
                gxPeCxqqXmModel.setName(gxPeCxqqXm.getQlrmc());
                gxPeCxqqXmModel.setpId("0");
                treeModelList.add(gxPeCxqqXmModel);
                //加载权利
                treeModelList = gxCxqqService.initQlTreeModel(gxPeCxqqXm,treeModelList);
            }
        }
        return treeModelList;
    }

    @ResponseBody
    @RequestMapping("/qlxx/getByPage")
    public Object getByPage(@LayuiPageable Pageable pageable, String qllx,String xmid){
        if(StringUtils.isNotBlank(qllx)
                && StringUtils.isNotBlank(xmid)){
            GxPeCxqqXm gxPeCxqqXm = entityMapper.selectByPrimaryKey(GxPeCxqqXm.class,xmid);
            if(gxPeCxqqXm!=null){
                Map queryMap = new HashMap();
                queryMap.put("cxsqdh",gxPeCxqqXm.getCxsqdh());
                queryMap.put("wsbh",gxPeCxqqXm.getWsbh());
                String table = getTableByQllx(qllx);
                if(StringUtils.isNotBlank(table)){
                    queryMap.put("table",table);
                    Page<HashMap> dataPaging = repository.selectPaging("getQlxxByPage", queryMap, pageable);
                    return dataPaging;
                }

            }
        }
        return null;
    }

    private String getTableByQllx(String qllx) {
        String table = "";
        if(StringUtils.equals("tdsyq",qllx)){
            table = "gx_pe_tdsyq";
        }else if(StringUtils.equals("jsydsyq",qllx)){
            table = "gx_pe_jsydsyq";
        }else if(StringUtils.equals("fdcq",qllx)){
            table = "gx_pe_fdcq";
        }else if(StringUtils.equals("hysyq",qllx)){
            table = "gx_pe_hysyq";
        }else if(StringUtils.equals("gjzwsyq",qllx)){
            table = "gx_pe_gjzwsyq";
        }else if(StringUtils.equals("lq",qllx)){
            table = "gx_pe_lq";
        }else if(StringUtils.equals("dyaq",qllx)){
            table = "gx_pe_dyaq";
        }else if(StringUtils.equals("ygdj",qllx)){
            table = "gx_pe_yg";
        }else if(StringUtils.equals("cfdj",qllx)){
            table = "gx_pe_cf";
        }else if(StringUtils.equals("yydj",qllx)){
            table = "gx_pe_yy";
        }else if(StringUtils.equals("nydsyq",qllx)){
            table = "gx_pe_nydsyq";
        }
        return table;
    }

    @RequestMapping("/query/getWsxxList")
    @ResponseBody
    public Map<String, String> getWsxxList(String cxsqdh, String wsbh){
        Map<String, String> resultMap = new HashMap<>();
        List<GxPeWsxxJg> gxPeWsxxJgList = null;
        if (StringUtils.isNotBlank(cxsqdh)
                && StringUtils.isNotBlank(wsbh)) {
            Example example = new Example(GxPeWsxxJg.class);
            example.createCriteria().andEqualTo("cxsqdh", cxsqdh).andEqualTo("wsbh",wsbh);
            gxPeWsxxJgList = entityMapper.selectByExample(example);
        }
        if(CollectionUtils.isEmpty(gxPeWsxxJgList)){
            resultMap.put("msg","");
            return resultMap;
        }
        // 国图大云文件中心的 URL
        String url = EnvironmentConfig.getEnvironment().getProperty("gtdy.file.url") + File.separator +gxPeWsxxJgList.get(0).getWsnr();
        resultMap.put("msg",url);
        return resultMap;
    }




}
