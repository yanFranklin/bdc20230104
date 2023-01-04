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
 * @description 受理页面项目相关控制层
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
     * @description 更新证书处理原产权证号处理
     */
    @Value("${updateZs.gxycqzh:false}")
    private Boolean gxycqzh;

    /**
     * 获取登记原因与字段对应权限的配置
     */
    private Map<String, String> djyyAuthority;

    public Map<String, String> getDjyyAuthority() {
        return djyyAuthority;
    }

    public void setDjyyAuthority(Map<String, String> djyyAuthority) {
        this.djyyAuthority = djyyAuthority;
    }

    /**
     * @param processInsId 工作流实例ID
     * @return 项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流实例ID查询不动产xm（用于简单流程、批量流程、组合流程）
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
            throw new AppException("缺失工作流实例ID");
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
     * @param xmid 项目ID
     * @return 项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID查询不动产项目（用于批量流程不动产单元详细）
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
     * @return 项目
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description 根据bdcdyh查询不动产项目维修基金信息（用于批量流程不动产维修基金详细）
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
      * @description 查询原项目信息
      */
    @ResponseBody
    @GetMapping("/yxm")
    public Object listYxmByYxmQO(BdcYxmQO bdcYxmQO) {
        if(bdcYxmQO == null ||(StringUtils.isBlank(bdcYxmQO.getGzlslid()) &&CollectionUtils.isEmpty(bdcYxmQO.getXmidList()))){
            throw new AppException("查询原项目缺失参数");
        }
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listYxmByYxmQO(bdcYxmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            return bdcXmDOList.get(0);
        }
        return null;
    }

    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 分页查询不动产单元
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
     * @description 分页查询当前项目不动产单元
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
     * @param bdcBdcdyQO 项目信息
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前流程的不动产单元信息列表
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
     * @param json         前台传输项目集合JSON
     * @param processInsId 工作流实例ID
     * @return 修改数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 批量修改项目（用于简单流程、批量流程）
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
     * @param bdcDjxxUpdateQO 登记信息更新查询参数
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新不动产项目
     */
    @ResponseBody
    @PatchMapping("/updateBatchBdcXm")
    public Integer updateBatchBdcXm(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO) throws Exception {
        if (bdcDjxxUpdateQO == null || StringUtils.isBlank(bdcDjxxUpdateQO.getJsonStr()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            throw new NullPointerException("空参数异常！");
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
     * @param bdcDjxxUpdateQO 登记受理信息更新查询参数
     * @return 更新数量
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description 批量更新不动产登记受理项目
     */
    @ResponseBody
    @PatchMapping("/updateBatchBdcSlXm")
    public Integer updateBatchBdcSlXm(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO) throws Exception {
        if (bdcDjxxUpdateQO == null || StringUtils.isBlank(bdcDjxxUpdateQO.getJsonStr()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            throw new NullPointerException("空参数异常！");
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
     * @param bdcDjxxUpdateQO 登记信息更新查询参数
     * @return 更新数量
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description 批量更新受理房屋信息
     */
    @ResponseBody
    @PatchMapping("/updateBatchBdcSlFwxx")
    public Integer updateBatchBdcQl(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO) throws Exception {
        if (bdcDjxxUpdateQO == null || StringUtils.isAnyBlank(bdcDjxxUpdateQO.getJsonStr(), bdcDjxxUpdateQO.getClassName()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            throw new NullPointerException("空参数异常！");
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
            throw new NullPointerException("批量更新项目附表异常!");
        }
        return bdcXmfbFeignService.updateBatchBdcxmFb(bdcDjxxUpdateQO);
    }


    @ResponseBody
    @PatchMapping("/updateBdcXmAndFdcq")
    public Integer updateBatchBdcXm(@RequestBody List<BdcDjxxUpdateQO> bdcDjxxUpdateQOList) throws Exception {
        if (CollectionUtils.isEmpty(bdcDjxxUpdateQOList)) {
            throw new NullPointerException("空参数异常！");
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
     * @param json 前台传输项目集合JSON
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量修改项目（用于组合流程）
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
     * @param json 前台传输项目集合JSON
     * @return 修改数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 修改项目（用于批量流程不动产单元详细）
     */
    @ResponseBody
    @PatchMapping("")
    public Integer updateBdcXm(@RequestBody String json) {
        return bdcEntityFeignService.updateByJsonEntity(json, BdcXmDO.class.getName());
    }

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存项目附表信息
     * @date : 2020/6/11 17:36
     */
    @ResponseBody
    @PatchMapping("/fb")
    public Integer updatBdcXmfb(@RequestBody String json) {
        return bdcEntityFeignService.updateByJsonEntity(json, BdcXmFbDO.class.getName());
    }

    /**
     * @param json 前台传输基本信息JSON
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 修改基本信息
     */
    @ResponseBody
    @PatchMapping("/jbxx")
    public Integer updateBdcSlJbxx(@RequestBody String json) throws Exception {
        BdcSlJbxxDO bdcSlJbxxDO = JSONObject.parseObject(json, BdcSlJbxxDO.class);
        int count = bdcSlJbxxFeignService.updateBdcSlJbxx(bdcSlJbxxDO);
        if (bdcSlJbxxDO != null && StringUtils.isNotBlank(bdcSlJbxxDO.getGzlslid())) {
            //回写信息
            bdcSlxxHxFeignService.hxBdcSlxx(bdcSlJbxxDO.getGzlslid());
        }
        return count;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据登记小类获取登记原因集合
     */
    @ResponseBody
    @GetMapping("queryDjxlDjyy")
    public Object queryDjxlDjyy(BdcDjxlDjyyQO bdcDjxlDjyyQO) {
        return bdcXmFeignService.listBdcDjxlDjyyGxWithParam(bdcDjxlDjyyQO);
    }

    /**
     * @param xmid 项目id
     * @return
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 根据项目id查询外联证书证号及状态
     */
    @ResponseBody
    @GetMapping("/queryWlzs")
    public String queryWlzs(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new AppException("项目id为空");
        }
        StringBuilder wlzs;
        String qszt = "";
        // 查询外联项目
        BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
        bdcXmLsgxQO.setXmid(xmid);
        bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_S_DM);
        List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
        // 当存在外联历史关系时 查询外联项目
        Set<String> wlzsList = new HashSet<>();
        if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
            for (BdcXmLsgxDO bdcXmLsgxDO : bdcXmLsgxDOList) {
                wlzs = new StringBuilder();
                if (StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())) {
                    BdcXmQO bdcXmQO = new BdcXmQO();
                    bdcXmQO.setXmid(bdcXmLsgxDO.getYxmid());
                    List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    // 当存在外联项目时
                    if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                        List<Map> qsztZdMap = bdcZdFeignService.queryBdcZd("qszt");
                        qszt = StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDOList.get(0).getQszt(), qsztZdMap);
                        // 组织外联证书字段
                        if (StringUtils.isNotBlank(bdcXmDOList.get(0).getBdcqzh())) {
                            wlzs.append(bdcXmDOList.get(0).getBdcqzh());
                            String status = "";
                            if (bdcXmLsgxDO.getZxyql() != null && CommonConstantUtils.SF_S_DM.equals(bdcXmLsgxDO.getZxyql())) {
                                status  = "/注销";
                            } else {
                                status  = "/不注销";
                            }
                            // 31461 合肥版本外联证书为历史状态的也展示为注销
                            if(CommonConstantUtils.SYSTEM_VERSION_HF.equals(systemVersion)
                                    && CommonConstantUtils.QSZT_HISTORY.equals(bdcXmDOList.get(0).getQszt())){
                                status = "/注销";
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
     * @param gzlslid 工作流实例ID
     * @return 是否
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 判断是否为虚拟单元号
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
                    //存在虚拟单元号即返回
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param djyy 登记原因
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据登记原因获取元素权限
     */
    @ResponseBody
    @GetMapping("getAuthorityByDjyy")
    public String getAuthorityByDjyy(String djyy) {
        return MapUtils.getString(djyyAuthority, djyy);
    }

    /**
     * @param gzlslid 工作流实例id
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据登记原因获取元素权限
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
     * @return 房屋状况附表
     * @author <a href="mailto:yanjiaqiang@gtmap.cn">yanjiaqiang</a>
     * @description 根据工作流实例id获取房屋状况附表
     */
    @ResponseBody
    @GetMapping("getFwzkfb")
    public List<BdcXmFwzkfbVO> getFwzkfb(BdcXmDO bdcXmDO) {
        // 通过gzlslid确认当前流程或项目是否生成权利（生成为true）
        boolean sfscql = checkSfscql(bdcXmDO);
        // 获取项目的权利类型
        List<BdcXmDTO> bdcXmDOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcXmDO.getGzlslid());
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            return null;
        }
        Integer qllx = bdcXmDOList.get(0).getQllx();
        //获取字典
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
                // 当幢号相同时，比较名义层
                int myc1 = getIntVlaue(o1.getMyc());
                int myc2 = getIntVlaue(o2.getMyc());
                return myc1 > myc2 ? -1 : 1;
            }

            // -1为幢号和名义层中未含有数据，排序排在最后
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

            // 对于1幢 2幢 这样的数据获取其中的数字
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
     * @param bdcXmDO 项目信息
     * @return true 生成 ，false 不生成
     * @author <a href="mailto:yanjiaqiang@gtmap.cn">yanjiaqiang</a>
     * @description 根据xmid或者gzlslid判断项目是否生成了权利信息
     */
    @GetMapping(value = "/sfscql")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkSfscql(BdcXmDO bdcXmDO) {
        if (StringUtils.isBlank(bdcXmDO.getGzlslid())) {
            throw new MissingArgumentException("缺失gzlslid参数！");
        }
        if (StringUtils.isNotBlank(bdcXmDO.getGzlslid())) {
            //获取当前流程生成的权利的权利类型
            List<String> qllxList = bdcQllxFeignService.listQllxByProcessInsId(bdcXmDO.getGzlslid());
            // 流程生成权利
            if (CollectionUtils.isNotEmpty(qllxList)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 地籍调查表判断是否是单个的不动产单元号
     *
     * @param processInsId processInsId
     * @return 单个不动产单元号 直接返回 bdcdyh ，否则返回 ""
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/djdcb")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String queryDjdcbBdcdyh(@RequestParam("processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("缺失gzlslid参数！");
        }
        List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if (CollectionUtils.isEmpty(bdcXmDTOS) && null != bdcXmDTOS.get(0)) {
            throw new AppException("未查询到对应的项目信息！");
        }
        Set<String> bdcdyhs = new HashSet<>();
        for (BdcXmDTO bdcXmDTO : bdcXmDTOS) {
            // 判断是否 包含多个不动产单元号
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
            throw new MissingArgumentException("缺失gzlslid参数！");
        }
        List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if (CollectionUtils.isEmpty(bdcXmDTOS)) {
            throw new AppException("未查询到对应的项目信息！");
        }
        Map<String, String> dcbMap = new HashMap<>(3);
        Set<String> bdcdyhs = new HashSet<>();
        for (BdcXmDTO bdcXmDTO : bdcXmDTOS) {
            // 判断是否 包含多个不动产单元号
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
     * 档案调用判断是否是单个的项目
     *
     * @param processInsId processInsId
     * @return 单个项目返回 bdcXmDO；多个不动产单元号返回 "" ；未查询到对应的产权信息返回 "empty"
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/dady")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object queryDadyXmids(@RequestParam("processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("缺失gzlslid参数！");
        }

        List<BdcXmDTO> bdcXmDOTemps = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if (CollectionUtils.isEmpty(bdcXmDOTemps) || null == bdcXmDOTemps.get(0)) {
            throw new AppException("未查询到对应的项目信息！");
        }
        Set<String> bdcdyhs = new HashSet<>();
        for (BdcXmDTO bdcXmDO : bdcXmDOTemps) {
            // 判断是否 包含多个不动产单元号
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
            // 未查询到对应的产权信息返回 "empty"
            return "empty";
        }
        return bdcLsgxXmDTOS.get(0);
    }

    /**
     * 档案调用判断是否是单个的项目
     *
     * @param bdcdyh bdcdyh
     * @return 单个项目返回 bdcXmDO；多个不动产单元号返回 "" ；未查询到对应的产权信息返回 "empty"
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/dady/more")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object queryDadyDetail(@RequestParam("bdcdyh") String bdcdyh, @RequestParam("xmid") String xmid) {
        // 查询当前的项目
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOs = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOs) || null == bdcXmDOs.get(0)) {
            throw new AppException("未查询到对应的项目信息！");
        }
        BdcLsgxQO bdcLsgxQO = new BdcLsgxQO();
        bdcLsgxQO.setBdcdyh(bdcdyh);
        bdcLsgxQO.setSlbh(bdcXmDOs.get(0).getSlbh());
        List<BdcLsgxXmDTO> bdcLsgxXmDTOS = bdcBdcdyFeignService.listBdcdyLsgxLastCq(bdcLsgxQO);
        if (CollectionUtils.isEmpty(bdcLsgxXmDTOS)) {
            // 未查询到对应的产权信息返回 "empty"
            return "empty";
        }
        return bdcLsgxXmDTOS.get(0);
    }

    /**
     * 地籍调查表列表分页接口 (档案调用同样调用此接口)
     *
     * @param pageable 分页参数
     * @param bdcXmDO  gzlslid 是必传的属性
     * @return Obejct 分页对象
     */
    @ResponseBody
    @GetMapping("/djdcb/page")
    public Object listDjdcbByPageJson(@LayuiPageable Pageable pageable, BdcXmDO bdcXmDO, String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("缺失gzlslid参数！");
        }
        bdcXmDO.setGzlslid(processInsId);
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        return addLayUiCode(bdcBdcdyFeignService.listDistinctBdcdyByPage(pageable.getPageNumber(), pageable.getPageSize(), sort, bdcXmDO));
    }

    /**
     * @return 判断是哪种档案
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
     * @param djyy 登记原因
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据登记原因获取元素权限
     */
    @ResponseBody
    @GetMapping("authorityMapByDjyy")
    public Object authorityMapByDjyy(String djyy) {
        return djyyAuthority;
    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 生成证书的流程返还承诺期限到平台
     * @date : 2020/1/15 9:37
     */
    @ResponseBody
    @GetMapping("cnlzrq")
    public boolean returnCnqxToPortal(String gzlslid) throws Exception {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("缺失查询承诺期限的工作流实例id");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcXmDO.getXmid());
            //获取房屋开关受理表的是否生成证书
            if (CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfsczs())) {
                //只有生成证书才会回写承诺领证日期到平台
                Date lzrq = bdcSlXmFeignService.getLzrq(bdcXmDO);
                if (lzrq != null) {
                    try {
                        Map<String, Object> processInsExtendDto = new HashMap<>();
                        processInsExtendDto.put("PROC_INS_ID", bdcXmDO.getGzlslid());
                        processInsExtendDto.put("LZRQ", lzrq);
                        bdcYwsjHxFeignService.updateBdcYwsj(bdcXmDO.getGzlslid(), processInsExtendDto);
                        return true;
                    } catch (Exception e) {
                        LOGGER.error("回写大云字段异常！gzlslid={},回写字段lzrq={}", gzlslid, lzrq);
                        return false;
                    }

                }
            }
            //不生成证书不会写数据，返回true
            return true;
        }
        return false;
    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 遗失证明页面信息
     * @date : 2020/4/23 8:57
     */
    @ResponseBody
    @GetMapping("/yszm")
    public Object queryYsgg(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("查询遗失公告项目缺失gzlslid");
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
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新证书信息
     * @date : 2020/6/16 16:44
     */
    @ResponseBody
    @GetMapping("/zsxx")
    public Object updateZsxx(String gzlslid) throws Exception {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("获取证书信息缺失工作流实例id");
        }
        //1.先判断当前流程是否生成了证书
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(gzlslid);
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
        //2.生成了证书的可以更新，未生证书不更新
        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
            UserDto userDto = userManagerUtils.getCurrentUser();
            LOGGER.info("操作人:{},点击受理页面项目工作流实例id{}更新证书按钮",userDto != null ? userDto.getAlias() : "",gzlslid);
            List<BdcZsDO> bdcZsDOS = bdcZsInitFeignService.initBdcqzs(gzlslid, false);
            //生成证书后重新生成证号
            bdcZsFeignService.generateBdcqzhOfPro(gzlslid);
            if(Boolean.TRUE.equals(gxycqzh)) {
                //处理ycqzh,以及权利其他状况追加原不动产权证号
                bdcSynchFeignService.synchYzh(gzlslid);
                bdcZsInitFeignService.initCommonYbdcqzhToQlqtzk(gzlslid);
            }
            return bdcZsDOS;
        }
        return null;
    }

    /**
     * 判断当前流程是否是查封到期解封流程
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
     * @description 根据项目id查询初始化服务开关实例 简单 组合 批量 批量组合
     */
    @GetMapping("/getBdcCshFwkgSlDO")
    @ResponseBody
    public BdcCshFwkgSlDO getBdcCshFwkgSlDO(String xmid){
        if(StringUtils.isBlank(xmid)){
            throw new AppException("获取初始化服务开关实例缺少项目id！");
        }
        return  bdcXmFeignService.queryFwkgsl(xmid);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [xmid, gzlslid, sfhz, lclx]
     * @return void
     * @description 更新是否换证
     */
    @PostMapping("/sfhz")
    public void saveSfhz(String xmid, String gzlslid, String sfhz, String lclx){
        if(StringUtils.isAnyBlank(xmid, gzlslid, sfhz)) {
            throw new AppException("更新是否换证缺乏必要参数！");
        }
        if(StringUtils.isNotBlank(lclx) && StringUtils.equals(lclx, "zhlc")){
            BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(xmid);
            if(bdcCshFwkgSlDO == null){
                throw new AppException("未查询到初始化服务开关实例，项目id为：" + xmid);
            }
            bdcCshFwkgSlDO.setSfhz(Integer.parseInt(sfhz));
            bdcXmFeignService.updateCshFwkgSl(bdcCshFwkgSlDO);
        }else{
            // 获取项目登记小类
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isEmpty(bdcXmDOList)) {
                throw new AppException("未查询到项目信息，项目id为：" + xmid);
            }
            String djxl = bdcXmDOList.get(0).getDjxl();

            BdcXmQO xm = new BdcXmQO();
            xm.setGzlslid(gzlslid);
            xm.setDjxl(djxl);
            List<BdcXmDO> needUpdateBdcXmDOList = bdcXmFeignService.listBdcXm(xm);
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                throw new AppException("未查询到项目信息!工作流实例id：" + gzlslid);
            }
            List<String> xmids = needUpdateBdcXmDOList.stream().map(BdcXmDO::getXmid).collect(Collectors.toList());
            bdcXmFeignService.batchUpdateCshFwkgSlSfhz(xmids, sfhz);
        }
    }


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询基本信息，工作流定义id，xmid，slbh等参数
     * @date : 2021/3/18 16:07
     */
    @ResponseBody
    @GetMapping("/lcjbxx")
    public Object queryLcJbxx(String gzlslid,@RequestParam(name = "selectXmids", required = false) String selectXmids) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("查询流程基本信息缺失工作流参数");
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
     * @description 查询业务流程新建验证忽略、转发验证忽略
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
            LOGGER.info("获取的日志信息:{},",result);
            return result;
        }
        return null;
    }

    /**
     * 获取日志中的验证信息忽略内容
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
     * 同步项目附表收费用途信息
     * @param bdcXmFbDO 不动产项目附表DO
     */
    @ResponseBody
    @PostMapping("/fb/syncsfyt")
    public void syncXmFb(@RequestBody BdcXmFbDO bdcXmFbDO) {
        if(StringUtils.isBlank(bdcXmFbDO.getGzlslid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcXmFbDO.getGzlslid());
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            // 按 bdcdyh 进行分组
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
                    LOGGER.error("更新项目附表收费用途出错, {}", e.getMessage());
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
                suffix = "等";
            }
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.sdf_ymdhms);
                xwblResponseDTO.setSlsj(simpleDateFormat.format(bdcXmDOList.get(0).getSlsj()));
                xwblResponseDTO.setZl(bdcXmDOList.get(0).getZl() + suffix);
                xwblResponseDTO.setLcmc(bdcXmDOList.get(0).getGzldymc());
                xwblResponseDTO.setBdcqzh(StringUtils.isNotBlank(bdcXmDOList.get(0).getYcqzh()) ? bdcXmDOList.get(0).getYcqzh() : "" + suffix);
                //权利人数据处理
                List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listAllBdcQlr(gzlslid, "", "");
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    //名称加证件号去重
                    bdcQlrDOList = bdcQlrDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getQlrmc() + ";" + o.getZjh()))), ArrayList::new));
                    //排序
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
                    //更新项目
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
     * @description 查询收件材料提交方式
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
        //更新权利信息
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
     * @description 获取南通批量流程主房项目信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/8/26 16:44
     * @param gzlslid
     * @return Object
     */
    @ResponseBody
    @GetMapping("/pllc/zfxmid")
    public Object getPllcZfxmid(String gzlslid) {
        if (StringUtils.isAnyBlank(gzlslid)) {
            throw new AppException("缺失工作流实例ID");
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
        //如果存在主房数据，优先读取主房的信息展现，否则随意读取一条项目展现
        if (StringUtils.isNotBlank(id)) {
            bdcXmQO.setXmid(id);
        }
        List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            //只展现其中一条权利即可
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
     * @param json 前台传输项目集合JSON
     * @return 修改数量
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 修改项目,用于信息补录修改
     */
    @ResponseBody
    @PatchMapping("/saveXxblxm")
    public Integer updateXxblBdcXm(@RequestBody String json) throws Exception {
        if(StringUtils.isNotBlank(json)){
            BdcXmDO bdcXmDO = JSON.parseObject(json, BdcXmDO.class);
            BdcYwxxDTO bdcYwxxDTOBefore = bdcInitFeignService.queryYwxx(bdcXmDO.getXmid());
            //更新业务信息
            int count = bdcEntityFeignService.updateByJsonEntity(json, BdcXmDO.class.getName());
            // 查询过修改后的结果
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
        // 查询出修改前的业务信息

        return null;
    }

    /**
     * @param xmid xmid
     * @return slfs
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 常州增加设立情形
     */
    @ResponseBody
    @GetMapping("/slfs")
    public BdcJzqDO querySlfs(@RequestParam("xmid") String xmid){
        if (StringUtils.isBlank(xmid)){
            throw new MissingArgumentException("缺失必要参数xmid");
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
        LOGGER.info("居住权信息:{}", bdcJzqDO);
        return bdcJzqDO;

    }

}
