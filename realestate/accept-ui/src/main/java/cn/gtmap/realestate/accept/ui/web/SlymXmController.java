package cn.gtmap.realestate.accept.ui.web;


import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.*;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlWxjjxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcLcJbxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcYsggDTO;
import cn.gtmap.realestate.common.core.dto.accept.XwblResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcLsgxXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcSyqxPlDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.*;
import cn.gtmap.realestate.common.core.qo.register.BdcBdcdyQO;
import cn.gtmap.realestate.common.core.qo.register.BdcLsgxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcGzlsjFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcXmFwzkfbVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcBdcdyVO;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 2019/1/17
 * @description ?????????????????????????????????
 */
@Controller
@RequestMapping("/slym/xm")
@ConfigurationProperties(prefix = "default")
public class SlymXmController extends BaseController {
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcEntityFeignService bdcEntityFeignService;
    @Autowired
    BdcBdcdyFeignService bdcBdcdyFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcSlxxHxFeignService bdcSlxxHxFeignService;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;
    @Autowired
    BdcZsInitFeignService bdcZsInitFeignService;
    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    @Autowired
    BdcSynchFeignService bdcSynchFeignService;
    @Autowired
    private LogMessageClient logMessageClient;

    @Autowired
    BdcSlXmLsgxFeignService bdcSlXmLsgxFeignService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    private BdcSlWxzjFeignService bdcSlWxzjFeignService;
    @Autowired
    BdcInitFeignService bdcInitFeignService;
    @Autowired
    private BdcSlFwxxFeignService bdcSlFwxxFeignService;
    @Value("${cfdqJfGzldyid.gzldyid:}")
    private String cfdqJfGzldyid;


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ???????????????????????????????????????
     */
    @Value("${updateZs.gxycqzh:false}")
    private Boolean gxycqzh;

    /**
     * ????????????????????????????????????????????????
     */
    private Map<String, String> djyyAuthority;

    public Map<String, String> getDjyyAuthority() {
        return djyyAuthority;
    }

    public void setDjyyAuthority(Map<String, String> djyyAuthority) {
        this.djyyAuthority = djyyAuthority;
    }

    /**
     * @param processInsId ???????????????ID
     * @return ??????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ?????????????????????ID???????????????xm??????????????????????????????????????????????????????
     */
    @ResponseBody
    @GetMapping("")
    public Object queryBdcXmByProcessInsId(String processInsId) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                if (StringUtils.isNotBlank(bdcXmDO.getYcqzh())) {
                    return bdcXmDO;
                }
            }
            return bdcXmDOList.get(0);
        }
        return null;
    }


    @ResponseBody
    @GetMapping("/listBdcXm")
    public Object listBdcXm(BdcXmQO bdcXmQO) {
        if (!CheckParameter.checkAnyParameter(bdcXmQO)) {
            throw new MissingArgumentException(JSONObject.toJSONString(bdcXmQO));
        }
        return bdcXmFeignService.listBdcXm(bdcXmQO);
    }

    @ResponseBody
    @GetMapping("/bfxx/gzlslid")
    public Object listBdcXmBfxx(String gzlslid) {
        if (StringUtils.isAnyBlank(gzlslid)) {
            throw new AppException("?????????????????????ID");
        }
        return bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
    }

    @ResponseBody
    @GetMapping("/listYcslXm")
    public Object listYcslXm(BdcXmQO bdcXmQO) {
        if (StringUtils.isAnyBlank(bdcXmQO.getGzlslid())) {
            throw new MissingArgumentException(JSONObject.toJSONString(bdcXmQO));
        }
        List<BdcXmDO> bdcXmDOList = new ArrayList<>();
        List<BdcXmDO> xmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(xmList)) {
            for (BdcXmDO bdcXmDO : xmList) {
                if (CheckWwsqOrYcslUtils.checkIsYcsl(bdcXmDO.getSply())) {
                    bdcXmDOList.add(bdcXmDO);
                }
            }
        }
        return bdcXmDOList;

    }

    @ResponseBody
    @GetMapping("/queryBdcSlJbxxByGzlslid")
    public BdcSlJbxxDO queryBdcSlJbxxByGzlslid(@RequestParam("processInsId") String processInsId) {
        return bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(processInsId);
    }

    /**
     * @param xmid ??????ID
     * @return ??????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ????????????ID??????????????????????????????????????????????????????????????????
     */
    @ResponseBody
    @GetMapping("/xx")
    public Object queryBdcXmByXmid(String xmid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            return bdcXmDOList.get(0);
        }
        return null;
    }

    /**
     * @param bdcdyh
     * @return ??????
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description ??????bdcdyh??????????????????????????????????????????????????????????????????????????????????????????
     */
    @ResponseBody
    @GetMapping("/wxjjxx")
    public Object queryWxjjxxByXmid(String bdcdyh) {
        BdcSlWxjjxxDO bdcSlWxjjxxDO = new BdcSlWxjjxxDO();
        bdcSlWxjjxxDO.setBdcdyh(bdcdyh);
        List<BdcSlWxjjxxDO> bdcSlWxjjxxDOList = bdcSlWxzjFeignService.queryBdcSlWxjjxx(bdcSlWxjjxxDO);
        if (CollectionUtils.isNotEmpty(bdcSlWxjjxxDOList)) {
            return bdcSlWxjjxxDOList.get(0);
        }
        return null;
    }

    @ResponseBody
    @GetMapping("/fb")
    public Object queryBdcXmfb(String xmid) {
        BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
        bdcXmFbQO.setXmid(xmid);
        List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
        if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
            return bdcXmFbDOList.get(0);
        }
        return new BdcXmFbDO();
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description ?????????????????????
      */
    @ResponseBody
    @GetMapping("/yxm")
    public Object listYxmByYxmQO(BdcYxmQO bdcYxmQO) {
        if(bdcYxmQO == null ||(StringUtils.isBlank(bdcYxmQO.getGzlslid()) &&CollectionUtils.isEmpty(bdcYxmQO.getXmidList()))){
            throw new AppException("???????????????????????????");
        }
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listYxmByYxmQO(bdcYxmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            return bdcXmDOList.get(0);
        }
        return null;
    }

    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ???????????????????????????
     */
    @ResponseBody
    @GetMapping("/listBdcdyByPageJson")
    public Object listXmByPageJson(@LayuiPageable Pageable pageable, BdcBdcdyQO bdcBdcdyQO) {
        bdcBdcdyQO.setZl(StringUtils.deleteWhitespace(bdcBdcdyQO.getZl()));
        bdcBdcdyQO.setBdcdyh(StringUtils.deleteWhitespace(bdcBdcdyQO.getBdcdyh()));
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        Page<BdcBdcdyVO> bdcdyVOPage = bdcBdcdyFeignService.listBdcdyByPage(pageable.getPageNumber(), pageable.getPageSize(), sort, bdcBdcdyQO);
        return addLayUiCode(bdcdyVOPage);
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ???????????????????????????????????????
     */
    @ResponseBody
    @GetMapping("/listNowBdcdyByPageJson")
    public Object listNowXmByPageJson(@LayuiPageable Pageable pageable, BdcQlQO bdcQlQO) {
        bdcQlQO.setZl(StringUtils.deleteWhitespace(bdcQlQO.getZl()));
        bdcQlQO.setBdcdyh(StringUtils.deleteWhitespace(bdcQlQO.getBdcdyh()));
        bdcQlQO.setGlfdcq(true);
        String bdcQlQoStr = JSON.toJSONString(bdcQlQO);
        return addLayUiCode(bdcXmFeignService.bdcQlPageByPageJson(pageable, bdcQlQoStr));
    }

    /**
     * @param bdcBdcdyQO ????????????
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ????????????????????????????????????????????????
     */
    @PostMapping(value = "/listBdcdy/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object queryBdcdyList(@RequestBody BdcBdcdyQO bdcBdcdyQO) {
        bdcBdcdyQO.setZl(StringUtils.deleteWhitespace(bdcBdcdyQO.getZl()));
        bdcBdcdyQO.setBdcdyh(StringUtils.deleteWhitespace(bdcBdcdyQO.getBdcdyh()));
        return bdcBdcdyFeignService.queryBdcdyList(bdcBdcdyQO);
    }

    /**
     * @param json         ????????????????????????JSON
     * @param processInsId ???????????????ID
     * @return ????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ?????????????????????????????????????????????????????????
     */
    @ResponseBody
    @PatchMapping("/list")
    public Integer updateBdcXm(@RequestBody String json, @RequestParam("processInsId") String processInsId, String xmids, String djxl) {
        JSONObject obj = JSONObject.parseObject(json);
        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
        bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(obj));
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(xmids)) {
            map.put("xmids", xmids.split(CommonConstantUtils.ZF_YW_DH));
        } else {
            map.put("gzlslid", processInsId);
        }
        if (StringUtils.isNotBlank(djxl)) {
            map.put("djxl", djxl);
        }
        bdcDjxxUpdateQO.setWhereMap(map);
        return bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);

    }

    /**
     * @param bdcDjxxUpdateQO ??????????????????????????????
     * @return ????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ???????????????????????????
     */
    @ResponseBody
    @PatchMapping("/updateBatchBdcXm")
    public Integer updateBatchBdcXm(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO) throws Exception {
        if (bdcDjxxUpdateQO == null || StringUtils.isBlank(bdcDjxxUpdateQO.getJsonStr()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            throw new NullPointerException("??????????????????");
        }
        List<String> xmidList =new ArrayList<>();
        if(bdcDjxxUpdateQO.getWhereMap().containsKey("xmids")){
            xmidList =(List<String>) bdcDjxxUpdateQO.getWhereMap().get("xmids");

        }
        int count=0;
        if(CollectionUtils.isNotEmpty(xmidList)){
            List<List> lists = ListUtils.subList(xmidList, 1000);

            for (List partList : lists) {
                bdcDjxxUpdateQO.getWhereMap().put("xmids", partList);
                count +=bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);
            }
        }else{
            count =bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);
        }
        return count;
    }


    /**
     * @param bdcDjxxUpdateQO ????????????????????????????????????
     * @return ????????????
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description ???????????????????????????????????????
     */
    @ResponseBody
    @PatchMapping("/updateBatchBdcSlXm")
    public Integer updateBatchBdcSlXm(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO) throws Exception {
        if (bdcDjxxUpdateQO == null || StringUtils.isBlank(bdcDjxxUpdateQO.getJsonStr()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            throw new NullPointerException("??????????????????");
        }
        List<String> xmidList =new ArrayList<>();
        if(bdcDjxxUpdateQO.getWhereMap().containsKey("xmids")){
            xmidList =(List<String>) bdcDjxxUpdateQO.getWhereMap().get("xmids");
        }
        if(CollectionUtils.isEmpty(xmidList)){
            String processInsId = (String) bdcDjxxUpdateQO.getWhereMap().get("gzlslid");
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(processInsId);
            xmidList = bdcSlXmDOList.stream().map(BdcSlXmDO::getXmid).collect(Collectors.toList());
        }

        int count=0;
        if(CollectionUtils.isNotEmpty(xmidList)){
            List<List> lists = ListUtils.subList(xmidList, 1000);

            for (List partList : lists) {
                bdcDjxxUpdateQO.getWhereMap().put("xmids", partList);
                count +=bdcSlXmFeignService.updateBatchBdcSlXm(bdcDjxxUpdateQO);
            }
        }else{
            count =bdcSlXmFeignService.updateBatchBdcSlXm(bdcDjxxUpdateQO);
        }
        return count;
    }

    /**
     * @param bdcDjxxUpdateQO ??????????????????????????????
     * @return ????????????
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description ??????????????????????????????
     */
    @ResponseBody
    @PatchMapping("/updateBatchBdcSlFwxx")
    public Integer updateBatchBdcQl(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO) throws Exception {
        if (bdcDjxxUpdateQO == null || StringUtils.isAnyBlank(bdcDjxxUpdateQO.getJsonStr(), bdcDjxxUpdateQO.getClassName()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            throw new NullPointerException("??????????????????");
        }
        List<String> xmidList = new ArrayList<>();
        if (bdcDjxxUpdateQO.getWhereMap().containsKey("xmids")) {
            xmidList = (List<String>) bdcDjxxUpdateQO.getWhereMap().get("xmids");
        }
        if(CollectionUtils.isEmpty(xmidList)){
            String processInsId = (String) bdcDjxxUpdateQO.getWhereMap().get("gzlslid");
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(processInsId);
            xmidList = bdcSlXmDOList.stream().map(BdcSlXmDO::getXmid).collect(Collectors.toList());
        }

        int count = 0;
        if (CollectionUtils.isNotEmpty(xmidList)) {
            List<List> lists = ListUtils.subList(xmidList, 1000);
            for (List partList : lists) {
                bdcDjxxUpdateQO.getWhereMap().put("xmids", partList);
                count += bdcSlFwxxFeignService.updateBatchBdcSlFwxx(bdcDjxxUpdateQO);
            }
        } else {
            count = bdcSlFwxxFeignService.updateBatchBdcSlFwxx(bdcDjxxUpdateQO);
        }
        return count;

    }


    @ResponseBody
    @PatchMapping("/plxgxmfb")
    public Integer updateBatchBdcXmfb(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO) throws Exception {
        if (bdcDjxxUpdateQO == null || StringUtils.isBlank(bdcDjxxUpdateQO.getJsonStr()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            throw new NullPointerException("??????????????????????????????!");
        }
        return bdcXmfbFeignService.updateBatchBdcxmFb(bdcDjxxUpdateQO);
    }


    @ResponseBody
    @PatchMapping("/updateBdcXmAndFdcq")
    public Integer updateBatchBdcXm(@RequestBody List<BdcDjxxUpdateQO> bdcDjxxUpdateQOList) throws Exception {
        if (CollectionUtils.isEmpty(bdcDjxxUpdateQOList)) {
            throw new NullPointerException("??????????????????");
        }
        int i = 0;
        for (BdcDjxxUpdateQO bdcDjxxUpdateQO : bdcDjxxUpdateQOList) {
            if ("BdcXmDO".equals(bdcDjxxUpdateQO.getClassName())) {
                i += bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);
            }
            if ("BdcFdcqDO".equals(bdcDjxxUpdateQO.getClassName())) {
                bdcDjxxUpdateQO.setClassName(BdcFdcqDO.class.getName());
                i += this.bdcQllxFeignService.updateBatchBdcQl(bdcDjxxUpdateQO);
            }
        }
        return i;
    }

    /**
     * @param json ????????????????????????JSON
     * @return ????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????????????????????????????????????????
     */
    @ResponseBody
    @PatchMapping("/list/zhlc")
    public Integer updateZhBdcXm(@RequestBody String json) {
        Integer count = 0;
        JSONArray jsonArray = JSONObject.parseArray(json);
        if (CollectionUtils.isNotEmpty(jsonArray)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                count += bdcEntityFeignService.updateByJsonEntity(JSONObject.toJSONString(obj), BdcXmDO.class.getName());
            }
        }

        return count;
    }

    /**
     * @param json ????????????????????????JSON
     * @return ????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ?????????????????????????????????????????????????????????
     */
    @ResponseBody
    @PatchMapping("")
    public Integer updateBdcXm(@RequestBody String json) {
        return bdcEntityFeignService.updateByJsonEntity(json, BdcXmDO.class.getName());
    }

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????
     * @date : 2020/6/11 17:36
     */
    @ResponseBody
    @PatchMapping("/fb")
    public Integer updatBdcXmfb(@RequestBody String json) {
        return bdcEntityFeignService.updateByJsonEntity(json, BdcXmFbDO.class.getName());
    }

    /**
     * @param json ????????????????????????JSON
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????????????????
     */
    @ResponseBody
    @PatchMapping("/jbxx")
    public Integer updateBdcSlJbxx(@RequestBody String json) throws Exception {
        BdcSlJbxxDO bdcSlJbxxDO = JSONObject.parseObject(json, BdcSlJbxxDO.class);
        int count = bdcSlJbxxFeignService.updateBdcSlJbxx(bdcSlJbxxDO);
        if (bdcSlJbxxDO != null && StringUtils.isNotBlank(bdcSlJbxxDO.getGzlslid())) {
            //????????????
            bdcSlxxHxFeignService.hxBdcSlxx(bdcSlJbxxDO.getGzlslid());
        }
        return count;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????????????????????????????????????????
     */
    @ResponseBody
    @GetMapping("queryDjxlDjyy")
    public Object queryDjxlDjyy(BdcDjxlDjyyQO bdcDjxlDjyyQO) {
        return bdcXmFeignService.listBdcDjxlDjyyGxWithParam(bdcDjxlDjyyQO);
    }

    /**
     * @param xmid ??????id
     * @return
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description ????????????id?????????????????????????????????
     */
    @ResponseBody
    @GetMapping("/queryWlzs")
    public String queryWlzs(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new AppException("??????id??????");
        }
        StringBuilder wlzs;
        String qszt = "";
        // ??????????????????
        BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
        bdcXmLsgxQO.setXmid(xmid);
        bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_S_DM);
        List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
        // ?????????????????????????????? ??????????????????
        Set<String> wlzsList = new HashSet<>();
        if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
            for (BdcXmLsgxDO bdcXmLsgxDO : bdcXmLsgxDOList) {
                wlzs = new StringBuilder();
                if (StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())) {
                    BdcXmQO bdcXmQO = new BdcXmQO();
                    bdcXmQO.setXmid(bdcXmLsgxDO.getYxmid());
                    List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    // ????????????????????????
                    if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                        List<Map> qsztZdMap = bdcZdFeignService.queryBdcZd("qszt");
                        qszt = StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDOList.get(0).getQszt(), qsztZdMap);
                        // ????????????????????????
                        if (StringUtils.isNotBlank(bdcXmDOList.get(0).getBdcqzh())) {
                            wlzs.append(bdcXmDOList.get(0).getBdcqzh());
                            String status = "";
                            if (bdcXmLsgxDO.getZxyql() != null && CommonConstantUtils.SF_S_DM.equals(bdcXmLsgxDO.getZxyql())) {
                                status  = "/??????";
                            } else {
                                status  = "/?????????";
                            }
                            // 31461 ????????????????????????????????????????????????????????????
                            if(CommonConstantUtils.SYSTEM_VERSION_HF.equals(systemVersion)
                                    && CommonConstantUtils.QSZT_HISTORY.equals(bdcXmDOList.get(0).getQszt())){
                                status = "/??????";
                            }
                            wlzs.append(status).append(" ").append(qszt);
                        }
                        wlzsList.add(wlzs.toString());
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(wlzsList)) {
            return String.join(CommonConstantUtils.ZF_YW_DH, wlzsList);
        }
        return "";
    }

    /**
     * @param gzlslid ???????????????ID
     * @return ??????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????????????????????????????
     */
    @ResponseBody
    @GetMapping("checkXndyh")
    public Boolean checkXndyh(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");

        }
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                if (BdcdyhToolUtils.checkXnbdcdyh(bdcXmDTO.getBdcdyh())) {
                    //??????????????????????????????
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param djyy ????????????
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????????????????
     */
    @ResponseBody
    @GetMapping("getAuthorityByDjyy")
    public String getAuthorityByDjyy(String djyy) {
        return MapUtils.getString(djyyAuthority, djyy);
    }

    /**
     * @param gzlslid ???????????????id
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????????????????
     */
    @ResponseBody
    @GetMapping("/getlclx")
    public Integer getlclx(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            return bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        } else {
            return null;
        }
    }

    /**
     * @param bdcXmDO
     * @return ??????????????????
     * @author <a href="mailto:yanjiaqiang@gtmap.cn">yanjiaqiang</a>
     * @description ?????????????????????id????????????????????????
     */
    @ResponseBody
    @GetMapping("getFwzkfb")
    public List<BdcXmFwzkfbVO> getFwzkfb(BdcXmDO bdcXmDO) {
        // ??????gzlslid?????????????????????????????????????????????????????????true???
        boolean sfscql = checkSfscql(bdcXmDO);
        // ???????????????????????????
        List<BdcXmDTO> bdcXmDOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcXmDO.getGzlslid());
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            return null;
        }
        Integer qllx = bdcXmDOList.get(0).getQllx();
        //????????????
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        List<BdcXmFwzkfbVO> fwzkfbVOPage = null;
        if (sfscql) {
            bdcXmDO.setQllx(qllx);
            fwzkfbVOPage = bdcXmFeignService.listFwzkbByPage(bdcXmDO);
        } else {
            bdcXmDO.setQllx(qllx);
            fwzkfbVOPage = bdcXmFeignService.listYxmFwzkbByPage(bdcXmDO);
        }
        for (BdcXmFwzkfbVO bdcXmFwzkfbVO : fwzkfbVOPage) {
            if (bdcXmFwzkfbVO != null) {
                if (bdcXmFwzkfbVO.getFwjg() != null) {
                    bdcXmFwzkfbVO.setFwjg(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bdcXmFwzkfbVO.getFwjg()), zdMap.get("fwjg")));
                }
                if (bdcXmFwzkfbVO.getGhyt() != null) {
                    bdcXmFwzkfbVO.setGhyt(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bdcXmFwzkfbVO.getGhyt()), zdMap.get("fwyt")));
                }
            }
        }
        sortFwzkfbList(fwzkfbVOPage);
        return fwzkfbVOPage;
    }

    private void sortFwzkfbList(List<BdcXmFwzkfbVO> fwzkfbVOList) {
        fwzkfbVOList.sort(new Comparator<BdcXmFwzkfbVO>() {
            @Override
            public int compare(BdcXmFwzkfbVO o1, BdcXmFwzkfbVO o2) {
                int zh1 = getIntVlaue(o1.getZh());
                int zh2 = getIntVlaue(o2.getZh());
                if (zh1 != zh2) {
                    return compareValue(zh1, zh2);
                }
                // ????????????????????????????????????
                int myc1 = getIntVlaue(o1.getMyc());
                int myc2 = getIntVlaue(o2.getMyc());
                return myc1 > myc2 ? -1 : 1;
            }

            // -1????????????????????????????????????????????????????????????
            private int compareValue(int o1, int o2) {
                if (o1 == o2) {
                    return 0;
                }
                if (-1 == o1) {
                    return 1;
                }
                if (-1 == o2) {
                    return -1;
                }
                if (o1 > o2) {
                    return 1;
                } else {
                    return -1;
                }
            }

            // ??????1??? 2??? ????????????????????????????????????
            private int getIntVlaue(String value) {
                int number = -1;
                if (StringUtils.isNotBlank(value)) {
                    String regEx = "[^0-9]+";
                    Pattern pattern = Pattern.compile(regEx.trim());

                    String[] numbers = pattern.split(value);

                    for (int i = 0; i < numbers.length; i++) {
                        if (StringUtils.isNotBlank(numbers[i])) {
                            number = Integer.valueOf(numbers[i]);
                            break;
                        }
                    }
                    return number;
                }
                return number;
            }

        });
    }

    /**
     * @param bdcXmDO ????????????
     * @return true ?????? ???false ?????????
     * @author <a href="mailto:yanjiaqiang@gtmap.cn">yanjiaqiang</a>
     * @description ??????xmid??????gzlslid???????????????????????????????????????
     */
    @GetMapping(value = "/sfscql")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkSfscql(BdcXmDO bdcXmDO) {
        if (StringUtils.isBlank(bdcXmDO.getGzlslid())) {
            throw new MissingArgumentException("??????gzlslid?????????");
        }
        if (StringUtils.isNotBlank(bdcXmDO.getGzlslid())) {
            //????????????????????????????????????????????????
            List<String> qllxList = bdcQllxFeignService.listQllxByProcessInsId(bdcXmDO.getGzlslid());
            // ??????????????????
            if (CollectionUtils.isNotEmpty(qllxList)) {
                return true;
            }
        }
        return false;
    }

    /**
     * ?????????????????????????????????????????????????????????
     *
     * @param processInsId processInsId
     * @return ???????????????????????? ???????????? bdcdyh ??????????????? ""
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/djdcb")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String queryDjdcbBdcdyh(@RequestParam("processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("??????gzlslid?????????");
        }
        List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if (CollectionUtils.isEmpty(bdcXmDTOS) && null != bdcXmDTOS.get(0)) {
            throw new AppException("????????????????????????????????????");
        }
        Set<String> bdcdyhs = new HashSet<>();
        for (BdcXmDTO bdcXmDTO : bdcXmDTOS) {
            // ???????????? ??????????????????????????????
            bdcdyhs.add(bdcXmDTO.getBdcdyh());
            if (bdcdyhs.size() > 1) {
                return "";
            }
        }
        return bdcXmDTOS.get(0).getBdcdyh();
    }

    @GetMapping("/dcblx")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object queryDcbBdcdyh(@RequestParam("processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("??????gzlslid?????????");
        }
        List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if (CollectionUtils.isEmpty(bdcXmDTOS)) {
            throw new AppException("????????????????????????????????????");
        }
        Map<String, String> dcbMap = new HashMap<>(3);
        Set<String> bdcdyhs = new HashSet<>();
        for (BdcXmDTO bdcXmDTO : bdcXmDTOS) {
            // ???????????? ??????????????????????????????
            bdcdyhs.add(bdcXmDTO.getBdcdyh());
            if (bdcdyhs.size() > 1) {
                dcbMap.put("tzm", BdcdyhToolUtils.queryTzmByBdcdyh(bdcXmDTO.getBdcdyh()));
                dcbMap.put("bdcdyh", "");
                dcbMap.put("xmid", bdcXmDTO.getXmid());
                return dcbMap;
            }
        }
        BdcXmDTO bdcXmDTO = bdcXmDTOS.get(0);
        dcbMap.put("tzm", BdcdyhToolUtils.queryTzmByBdcdyh(bdcXmDTO.getBdcdyh()));
        dcbMap.put("bdcdyh", bdcXmDTO.getBdcdyh());
        dcbMap.put("xmid", bdcXmDTO.getXmid());
        return dcbMap;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param processInsId processInsId
     * @return ?????????????????? bdcXmDO????????????????????????????????? "" ?????????????????????????????????????????? "empty"
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/dady")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object queryDadyXmids(@RequestParam("processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("??????gzlslid?????????");
        }

        List<BdcXmDTO> bdcXmDOTemps = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if (CollectionUtils.isEmpty(bdcXmDOTemps) || null == bdcXmDOTemps.get(0)) {
            throw new AppException("????????????????????????????????????");
        }
        Set<String> bdcdyhs = new HashSet<>();
        for (BdcXmDTO bdcXmDO : bdcXmDOTemps) {
            // ???????????? ??????????????????????????????
            bdcdyhs.add(bdcXmDO.getBdcdyh());
            if (bdcdyhs.size() > 1) {
                return "";
            }
        }
        BdcLsgxQO bdcLsgxQO = new BdcLsgxQO();
        bdcLsgxQO.setBdcdyh(bdcXmDOTemps.get(0).getBdcdyh());
        bdcLsgxQO.setSlbh(bdcXmDOTemps.get(0).getSlbh());
        List<BdcLsgxXmDTO> bdcLsgxXmDTOS = bdcBdcdyFeignService.listBdcdyLsgxLastCq(bdcLsgxQO);
        if (CollectionUtils.isEmpty(bdcLsgxXmDTOS)) {
            // ??????????????????????????????????????? "empty"
            return "empty";
        }
        return bdcLsgxXmDTOS.get(0);
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param bdcdyh bdcdyh
     * @return ?????????????????? bdcXmDO????????????????????????????????? "" ?????????????????????????????????????????? "empty"
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/dady/more")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object queryDadyDetail(@RequestParam("bdcdyh") String bdcdyh, @RequestParam("xmid") String xmid) {
        // ?????????????????????
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOs = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOs) || null == bdcXmDOs.get(0)) {
            throw new AppException("????????????????????????????????????");
        }
        BdcLsgxQO bdcLsgxQO = new BdcLsgxQO();
        bdcLsgxQO.setBdcdyh(bdcdyh);
        bdcLsgxQO.setSlbh(bdcXmDOs.get(0).getSlbh());
        List<BdcLsgxXmDTO> bdcLsgxXmDTOS = bdcBdcdyFeignService.listBdcdyLsgxLastCq(bdcLsgxQO);
        if (CollectionUtils.isEmpty(bdcLsgxXmDTOS)) {
            // ??????????????????????????????????????? "empty"
            return "empty";
        }
        return bdcLsgxXmDTOS.get(0);
    }

    /**
     * ????????????????????????????????? (?????????????????????????????????)
     *
     * @param pageable ????????????
     * @param bdcXmDO  gzlslid ??????????????????
     * @return Obejct ????????????
     */
    @ResponseBody
    @GetMapping("/djdcb/page")
    public Object listDjdcbByPageJson(@LayuiPageable Pageable pageable, BdcXmDO bdcXmDO, String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("??????gzlslid?????????");
        }
        bdcXmDO.setGzlslid(processInsId);
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        return addLayUiCode(bdcBdcdyFeignService.listDistinctBdcdyByPage(pageable.getPageNumber(), pageable.getPageSize(), sort, bdcXmDO));
    }

    /**
     * @return ?????????????????????
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseBody
    @GetMapping("/tellTdFromBdc")
    public Object tellTdFromBdc(@RequestParam("xmid") String xmid) {
        return bdcBdcdyFeignService.queryXmly(xmid);
    }

    @ResponseBody
    @GetMapping("queryZdFwytByGzlslid")
    public Object queryZdFwytByGzlslid(String gzlslid) {
        return bdcXmFeignService.queryZdFwytByGzlslid(gzlslid);
    }

    /**
     * @param djyy ????????????
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????????????????
     */
    @ResponseBody
    @GetMapping("authorityMapByDjyy")
    public Object authorityMapByDjyy(String djyy) {
        return djyyAuthority;
    }

    /**
     * @param gzlslid ???????????????id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????????????????????????????
     * @date : 2020/1/15 9:37
     */
    @ResponseBody
    @GetMapping("cnlzrq")
    public boolean returnCnqxToPortal(String gzlslid) throws Exception {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("??????????????????????????????????????????id");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcXmDO.getXmid());
            //????????????????????????????????????????????????
            if (CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfsczs())) {
                //?????????????????????????????????????????????????????????
                Date lzrq = bdcSlXmFeignService.getLzrq(bdcXmDO);
                if (lzrq != null) {
                    try {
                        Map<String, Object> processInsExtendDto = new HashMap<>();
                        processInsExtendDto.put("PROC_INS_ID", bdcXmDO.getGzlslid());
                        processInsExtendDto.put("LZRQ", lzrq);
                        bdcYwsjHxFeignService.updateBdcYwsj(bdcXmDO.getGzlslid(), processInsExtendDto);
                        return true;
                    } catch (Exception e) {
                        LOGGER.error("???????????????????????????gzlslid={},????????????lzrq={}", gzlslid, lzrq);
                        return false;
                    }

                }
            }
            //???????????????????????????????????????true
            return true;
        }
        return false;
    }

    /**
     * @param gzlslid ???????????????id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????
     * @date : 2020/4/23 8:57
     */
    @ResponseBody
    @GetMapping("/yszm")
    public Object queryYsgg(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("??????????????????????????????gzlslid");
        }
        BdcYsggDTO bdcYsggDTO = new BdcYsggDTO();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            bdcYsggDTO.setBdcXmDOList(bdcXmDOList);
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setGzlslid(gzlslid);
            bdcZsQO.setZxyql(CommonConstantUtils.SF_F_DM);
            List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryYxmZs(bdcZsQO);
            if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                List<Map> zslxZdMap = bdcZdFeignService.queryBdcZd("zslx");
                Integer zslx = bdcZsDOList.get(0).getZslx();
                String zslxmc = StringToolUtils.convertBeanPropertyValueOfZd(zslx != null ? zslx : 1, zslxZdMap);
                bdcYsggDTO.setZslx(zslxmc);
            }
        }
        return bdcYsggDTO;
    }


    /**
     * @param gzlslid ???????????????ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????
     * @date : 2020/6/16 16:44
     */
    @ResponseBody
    @GetMapping("/zsxx")
    public Object updateZsxx(String gzlslid) throws Exception {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("???????????????????????????????????????id");
        }
        //1.??????????????????????????????????????????
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(gzlslid);
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
        //2.??????????????????????????????????????????????????????
        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
            UserDto userDto = userManagerUtils.getCurrentUser();
            LOGGER.info("?????????:{},???????????????????????????????????????id{}??????????????????",userDto != null ? userDto.getAlias() : "",gzlslid);
            List<BdcZsDO> bdcZsDOS = bdcZsInitFeignService.initBdcqzs(gzlslid, false);
            //?????????????????????????????????
            bdcZsFeignService.generateBdcqzhOfPro(gzlslid);
            if(Boolean.TRUE.equals(gxycqzh)) {
                //??????ycqzh,???????????????????????????????????????????????????
                bdcSynchFeignService.synchYzh(gzlslid);
                bdcZsInitFeignService.initCommonYbdcqzhToQlqtzk(gzlslid);
            }
            return bdcZsDOS;
        }
        return null;
    }

    /**
     * ???????????????????????????????????????????????????
     * @param gzlslid
     * @return
     */
    @ResponseBody
    @GetMapping("/cfdqJflc")
    public Object cfdqJflc(String gzlslid) {
        if(StringUtils.isEmpty(gzlslid)){
            return false;
        }
        List<BdcXmDTO> listXm = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if(CollectionUtils.isNotEmpty(listXm)){
            String gzldyid = listXm.get(0).getGzldyid();
            return StringUtils.equals(gzldyid,cfdqJfGzldyid);
        }
        return false;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [xmid]
     * @return cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO
     * @description ????????????id????????????????????????????????? ?????? ?????? ?????? ????????????
     */
    @GetMapping("/getBdcCshFwkgSlDO")
    @ResponseBody
    public BdcCshFwkgSlDO getBdcCshFwkgSlDO(String xmid){
        if(StringUtils.isBlank(xmid)){
            throw new AppException("?????????????????????????????????????????????id???");
        }
        return  bdcXmFeignService.queryFwkgsl(xmid);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [xmid, gzlslid, sfhz, lclx]
     * @return void
     * @description ??????????????????
     */
    @PostMapping("/sfhz")
    public void saveSfhz(String xmid, String gzlslid, String sfhz, String lclx){
        if(StringUtils.isAnyBlank(xmid, gzlslid, sfhz)) {
            throw new AppException("???????????????????????????????????????");
        }
        if(StringUtils.isNotBlank(lclx) && StringUtils.equals(lclx, "zhlc")){
            BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(xmid);
            if(bdcCshFwkgSlDO == null){
                throw new AppException("????????????????????????????????????????????????id??????" + xmid);
            }
            bdcCshFwkgSlDO.setSfhz(Integer.parseInt(sfhz));
            bdcXmFeignService.updateCshFwkgSl(bdcCshFwkgSlDO);
        }else{
            // ????????????????????????
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isEmpty(bdcXmDOList)) {
                throw new AppException("?????????????????????????????????id??????" + xmid);
            }
            String djxl = bdcXmDOList.get(0).getDjxl();

            BdcXmQO xm = new BdcXmQO();
            xm.setGzlslid(gzlslid);
            xm.setDjxl(djxl);
            List<BdcXmDO> needUpdateBdcXmDOList = bdcXmFeignService.listBdcXm(xm);
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                throw new AppException("????????????????????????!???????????????id???" + gzlslid);
            }
            List<String> xmids = needUpdateBdcXmDOList.stream().map(BdcXmDO::getXmid).collect(Collectors.toList());
            bdcXmFeignService.batchUpdateCshFwkgSlSfhz(xmids, sfhz);
        }
    }


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????????????????id???xmid???slbh?????????
     * @date : 2021/3/18 16:07
     */
    @ResponseBody
    @GetMapping("/lcjbxx")
    public Object queryLcJbxx(String gzlslid,@RequestParam(name = "selectXmids", required = false) String selectXmids) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("?????????????????????????????????????????????");
        }
        BdcLcJbxxDTO bdcLcJbxxDTO = new BdcLcJbxxDTO();
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (Objects.nonNull(bdcSlJbxxDO) && StringUtils.isNotBlank(bdcSlJbxxDO.getJbxxid())) {
            bdcLcJbxxDTO.setGzldyid(bdcSlJbxxDO.getGzldyid());
            bdcLcJbxxDTO.setGzlslid(gzlslid);
            bdcLcJbxxDTO.setSlbh(bdcSlJbxxDO.getSlbh());
            bdcLcJbxxDTO.setJbxxid(bdcSlJbxxDO.getJbxxid());
        }
        if(StringUtils.isNotBlank(selectXmids)){
            bdcLcJbxxDTO.setXmids(selectXmids);
        }else {
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                String xmids = StringToolUtils.resolveBeanToAppendStr(bdcXmDTOList, "getXmid", CommonConstantUtils.ZF_YW_DH);
                if (StringUtils.isNotBlank(xmids)) {
                    bdcLcJbxxDTO.setXmids(xmids);
                }
            }
        }
        return bdcLcJbxxDTO;
    }

    /**
     * @param slbh
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description ?????????????????????????????????????????????????????????
     * @date : 2021/3/18 16:07
     */
    @ResponseBody
    @GetMapping("/hlxx")
    public Object queryHlxx( @RequestParam("slbh") String slbh){
        if(StringUtils.isNotBlank(slbh)){
           List<String> result = Lists.newArrayList();
            List<QueryLogCondition> conditionList = Lists.newArrayList();
            QueryLogCondition queryLogCondition = new QueryLogCondition();
            queryLogCondition.setValue(slbh);
            queryLogCondition.setType(CommonConstantUtils.TYPE_LIKE);
            queryLogCondition.setKey(CommonConstantUtils.SLBH);
            conditionList.add(queryLogCondition);
            Page<AuditLogDto> hyLogDtoPage = logMessageClient.listLogs(0, 99,
                    CommonConstantUtils.GZYZ_HL,
                    null, null, null, null, conditionList);
            /*Page<AuditLogDto> ZfLogDtoPage = logMessageClient.listLogs(0, 99,
                    CommonConstantUtils.GZYZ_HL_ZF,
                    null, null, null, null, conditionList);
            result.addAll(getHlnr(ZfLogDtoPage));*/
            result.addAll(getHlnr(hyLogDtoPage));
            LOGGER.info("?????????????????????:{},",result);
            return result;
        }
        return null;
    }

    /**
     * ??????????????????????????????????????????
     * @param XjLogDtoPage
     * @return
     */
    private List<String> getHlnr(Page<AuditLogDto> XjLogDtoPage){
        List<String> hlnrList = Lists.newArrayList();
        if (XjLogDtoPage.hasContent()) {
            Iterator<AuditLogDto> auditLogIterator = XjLogDtoPage.iterator();
            while (auditLogIterator.hasNext()) {
                AuditLogDto auditLog = auditLogIterator.next();
                List<DataValue> dataValueList = auditLog.getBinaryAnnotations();
                if(CollectionUtils.isNotEmpty(dataValueList)){
                    for(DataValue dataValue : dataValueList){
                        if (StringUtils.equals(CommonConstantUtils.HLNR,dataValue.getKey().toUpperCase())) {
                            hlnrList.add(dataValue.getValue());
                            continue;
                        }
                    }
                }

            }
        }
        return hlnrList;
    }

    /**
     * ????????????????????????????????????
     * @param bdcXmFbDO ?????????????????????DO
     */
    @ResponseBody
    @PostMapping("/fb/syncsfyt")
    public void syncXmFb(@RequestBody BdcXmFbDO bdcXmFbDO) {
        if(StringUtils.isBlank(bdcXmFbDO.getGzlslid())){
            throw new AppException(ErrorCode.MISSING_ARG, "???????????????????????????ID");
        }
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcXmFbDO.getGzlslid());
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            // ??? bdcdyh ????????????
            Map<String, List<BdcXmDTO>> map = bdcXmDTOList.stream().collect(Collectors.groupingBy(BdcXmDTO::getBdcdyh));
            String currentBdcdyh = "";
            for(BdcXmDTO bdcXmDTO : bdcXmDTOList){
                if(StringUtils.equals(bdcXmDTO.getXmid(), bdcXmFbDO.getXmid())){
                    currentBdcdyh = bdcXmDTO.getBdcdyh();
                    break;
                }
            }
            List<BdcXmDTO> groupXmList = map.get(currentBdcdyh);
            if(CollectionUtils.isNotEmpty(groupXmList)){
                BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
                Map whereMap = new HashMap<>();
                whereMap.put("xmids", groupXmList.stream().map(BdcXmDTO::getXmid).collect(Collectors.toList()));
                bdcDjxxUpdateQO.setWhereMap(whereMap);
                BdcXmFbDO xmfbxx = new BdcXmFbDO();
                xmfbxx.setSfyt(bdcXmFbDO.getSfyt());
                bdcDjxxUpdateQO.setJsonStr(JSON.toJSONString(xmfbxx));
                try {
                    bdcXmfbFeignService.updateBatchBdcxmFb(bdcDjxxUpdateQO);
                } catch (Exception e) {
                    LOGGER.error("????????????????????????????????????, {}", e.getMessage());
                }
            }
        }
    }

    @ResponseBody
    @GetMapping("/xwbl")
    public Object queryXwblxx(String gzlslid) {
        XwblResponseDTO xwblResponseDTO = new XwblResponseDTO();
        if (StringUtils.isNotBlank(gzlslid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
            String suffix = "";
            if (CommonConstantUtils.LCLX_PL.equals(lclx) || CommonConstantUtils.LCLX_PLZH.equals(lclx)) {
                suffix = "???";
            }
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.sdf_ymdhms);
                xwblResponseDTO.setSlsj(simpleDateFormat.format(bdcXmDOList.get(0).getSlsj()));
                xwblResponseDTO.setZl(bdcXmDOList.get(0).getZl() + suffix);
                xwblResponseDTO.setLcmc(bdcXmDOList.get(0).getGzldymc());
                xwblResponseDTO.setBdcqzh(StringUtils.isNotBlank(bdcXmDOList.get(0).getYcqzh()) ? bdcXmDOList.get(0).getYcqzh() : "" + suffix);
                //?????????????????????
                List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listAllBdcQlr(gzlslid, "", "");
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    //????????????????????????
                    bdcQlrDOList = bdcQlrDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getQlrmc() + ";" + o.getZjh()))), ArrayList::new));
                    //??????
                    bdcQlrDOList = bdcQlrDOList.stream().sorted(Comparator.comparing(BdcQlrDO::getQlrlb, Comparator.nullsFirst(String::compareTo))
                            .thenComparing(BdcQlrDO::getXmid).thenComparing(BdcQlrDO::getSxh, Comparator.nullsFirst(Integer::compareTo))).collect(Collectors.toList());
                    xwblResponseDTO.setQlrywr(StringToolUtils.resolveBeanToAppendStr(bdcQlrDOList, "getQlrmc", CommonConstantUtils.ZF_YW_DH));
                }
            }
        }
        return xwblResponseDTO;
    }

    @ResponseBody
    @GetMapping("/yxmqszt")
    public void changeYxmQszt(String gzlslid) throws Exception {
        if(StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
            if(CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                List<String> yxmidList = new ArrayList<>(CollectionUtils.size(bdcSlXmDOList));
                for(BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                    List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(bdcSlXmDO.getXmid(),"",CommonConstantUtils.SF_F_DM);
                    if(CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                        String yxmid = bdcSlXmLsgxDOList.get(0).getYxmid();
                        yxmidList.add(yxmid);
                    }
                }
                if(CollectionUtils.isNotEmpty(yxmidList)) {
                    //????????????
                    if(CollectionUtils.size(yxmidList) > 500) {
                        List<List> partXmidList = ListUtils.subList(yxmidList,500);
                        for(List list : partXmidList) {
                            updateQszt(list);
                        }
                    } else {
                        updateQszt(yxmidList);
                    }

                }
            }
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????????????????
     * @date : 2022/5/23 14:13
     */
    @ResponseBody
    @GetMapping("/sjcltjfs")
    public Object querySjclTjfs(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return "";
        }
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (Objects.nonNull(bdcSlJbxxDO)) {
            return bdcSlJbxxDO;
        }
        return "";
    }

    @ResponseBody
    @GetMapping("/listSlFwxxByPage")
    public Object listFwxxBypzge(@LayuiPageable Pageable pageable, BdcBdcdyQO bdcBdcdyQO) {
        return addLayUiCode(bdcSlXmFeignService.listBdcSlFwxxByPageJson(pageable, JSON.toJSONString(bdcBdcdyQO)));
    }

    private void updateQszt(List list) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("qszt", CommonConstantUtils.QSZT_HISTORY);
        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
        bdcDjxxUpdateQO.setJsonStr(JSON.toJSONString(obj));
        Map whereMap = new HashMap<>();
        whereMap.put("xmids", list);
        bdcDjxxUpdateQO.setWhereMap(whereMap);
        bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);
        //??????????????????
        for(Object o: list) {
            String xmid = (String) o;
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
            if(Objects.nonNull(bdcQl)) {
                JSONObject qlObj = new JSONObject();
                qlObj.put("qszt",CommonConstantUtils.QSZT_HISTORY);
                BdcDjxxUpdateQO bdcQlxxUpdateQO = new BdcDjxxUpdateQO();
                bdcQlxxUpdateQO.setJsonStr(JSON.toJSONString(qlObj));
                Map qlxxWhereMap = new HashMap<>();
                qlxxWhereMap.put("xmids", Collections.singletonList(xmid));
                bdcQlxxUpdateQO.setClassName(bdcQl.getClass().getName());
                bdcQlxxUpdateQO.setWhereMap(qlxxWhereMap);
                bdcQllxFeignService.updateBatchBdcQl(bdcQlxxUpdateQO);
            }
        }
    }


    /**
     * @description ??????????????????????????????????????????
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/8/26 16:44
     * @param gzlslid
     * @return Object
     */
    @ResponseBody
    @GetMapping("/pllc/zfxmid")
    public Object getPllcZfxmid(String gzlslid) {
        if (StringUtils.isAnyBlank(gzlslid)) {
            throw new AppException("?????????????????????ID");
        }
        String id = "";
        List<BdcCshFwkgSlDO> bdcCshFwkgSlDOList = bdcXmFeignService.querySfzfKgslByGzlslid(gzlslid, CommonConstantUtils.SF_S_DM);
        if (CollectionUtils.isNotEmpty(bdcCshFwkgSlDOList)) {
            id = bdcCshFwkgSlDOList.stream().filter(bdcCshFwkgSlDO -> bdcCshFwkgSlDO.getQllx() == 4).collect(Collectors.toList()).get(0).getId();
        }

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<Integer> qllxs = new ArrayList();
        qllxs.add(4);
        bdcXmQO.setQllxs(qllxs);
        //???????????????????????????????????????????????????????????????????????????????????????????????????
        if (StringUtils.isNotBlank(id)) {
            bdcXmQO.setXmid(id);
        }
        List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            //?????????????????????????????????
            for (BdcXmDO bdcXmDO : bdcXmList) {
                if (StringUtils.isNotBlank(bdcXmDO.getYcqzh())) {
                    return bdcXmDO;
                }
            }
            return bdcXmList.get(0);
        }
        return null;
    }

    @ResponseBody
    @GetMapping("/ylc")
    public Object getYxmxx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return null;
        }
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(bdcSlXmDOList.get(0).getXmid(), "", CommonConstantUtils.SF_F_DM);
            if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                String yxmid = bdcSlXmLsgxDOList.get(0).getYxmid();
                if (StringUtils.isNotBlank(yxmid)) {
                    BdcXmQO bdcXmQO = new BdcXmQO(yxmid);
                    List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                        return bdcXmDOList.get(0);
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param json ????????????????????????JSON
     * @return ????????????
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description ????????????,????????????????????????
     */
    @ResponseBody
    @PatchMapping("/saveXxblxm")
    public Integer updateXxblBdcXm(@RequestBody String json) throws Exception {
        if(StringUtils.isNotBlank(json)){
            BdcXmDO bdcXmDO = JSON.parseObject(json, BdcXmDO.class);
            BdcYwxxDTO bdcYwxxDTOBefore = bdcInitFeignService.queryYwxx(bdcXmDO.getXmid());
            //??????????????????
            int count = bdcEntityFeignService.updateByJsonEntity(json, BdcXmDO.class.getName());
            // ???????????????????????????
            BdcYwxxDTO bdcYwxxDTOAfter = bdcInitFeignService.queryYwxx(bdcXmDO.getXmid());
            Map<String, String> data = LogCompareUtils.initDataString(bdcXmDO.getXmid(), bdcYwxxDTOBefore, bdcYwxxDTOAfter);
            if (StringUtils.isNotBlank(RSAEncryptUtils.decrypt(data.get("change").toString()))) {
                LogMsgDTO logMsgDTO = new LogMsgDTO();
                logMsgDTO.setPrincipal(userManagerUtils.getCurrentUserName());
                logMsgDTO.setTags(data);
                logMsgDTO.setEvent(CommonConstantUtils.XXBL);
                logMessageClient.save(logMsgDTO);
            }
            return count;
        }
        // ?????????????????????????????????

        return null;
    }

    /**
     * @param xmid xmid
     * @return slfs
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description ????????????????????????
     */
    @ResponseBody
    @GetMapping("/slfs")
    public BdcJzqDO querySlfs(@RequestParam("xmid") String xmid){
        if (StringUtils.isBlank(xmid)){
            throw new MissingArgumentException("??????????????????xmid");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
        Integer qllx = 0;
        if (CollectionUtils.isNotEmpty(list)){
            qllx = list.get(0).getQllx();
        }
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
        BdcJzqDO bdcJzqDO = new BdcJzqDO();
        if (Objects.nonNull(bdcQl) && CommonConstantUtils.QLLX_JZQ.equals(qllx)){
            bdcJzqDO = (BdcJzqDO) bdcQl;
        }
        LOGGER.info("???????????????:{}", bdcJzqDO);
        return bdcJzqDO;

    }

}
