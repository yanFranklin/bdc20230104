package cn.gtmap.realestate.register.ui.web.rest.xxbl;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.LogMsgDTO;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcQlPageResponseDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcYwsjHxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.core.service.rest.init.BdcInitRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.LogCompareUtils;
import cn.gtmap.realestate.common.util.RSAEncryptUtils;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 不动产信息批量信息补录流程服务接口
 *
 * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
 */
@RestController
@RequestMapping("/rest/v1.0/plblxx")
public class BdcPlBllcController extends BaseController {

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcInitRestService bdcInitFeignService;
    @Autowired
    LogMessageClient logMessageClient;
    @Value("${html.version:}")
    private String version;
    @Autowired
    BdcYwsjHxFeignService bdcYwsjHxFeignService;
    @Autowired
    private BdcEntityFeignService bdcEntityFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;

    /**
     * 权利信息列表页面
     **/
    private static final String QLXX_LIST_URL = "";
    /**
     * 权利信息单个页面
     **/
    private static final String QLXX_URL = "";
    /**
     * 信息补录流程基本信息页面lc_home.html是否需要自动保存登簿人信息
     */
    @Value("${xxbl.lc.sfxydbr: true}")
    private Boolean sfxydbr;

    /**
     * 根据工作流实例 id 获取项目信息
     *
     * @param processInstanceId 工作流实例 id
     * @return bdcXmDOList
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    @GetMapping("/xmxx")
    public List<BdcXmDO> queryXmxxByProcessInsId(@RequestParam(value = "processInstanceId") String processInstanceId,
                                                 @RequestParam(value = "xmid") String xmid, String lclx) {
        if (StringUtils.isBlank(processInstanceId)) {
            throw new MissingArgumentException("查询项目信息缺少 gzlslid");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInstanceId);
        if (StringUtils.isNotBlank(xmid) && StringUtils.isNotBlank(lclx) && StringUtils.equals("jdlc", lclx)) {
            bdcXmQO.setXmid(xmid);
        }
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        return bdcXmDOList;
    }

    @GetMapping("/qlxxForward")
    public String getUrl(@RequestParam(value = "processInstanceId") String processInstanceId) {
        if (StringUtils.isBlank(processInstanceId)) {
            throw new MissingArgumentException("跳转权利信息页面缺少gzlslid");
        }
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(processInstanceId);
        if (CommonConstantUtils.LCLX_PL.equals(xmlx)) {
            return QLXX_LIST_URL;
        } else {
            return QLXX_URL;
        }
    }


    /**
     * 保存不动产项目信息, 同时记录操作日志
     *
     * @param bdcXmDOList 不动产项对象List
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/plxmxx")
    public void updateBdcXm(@RequestBody List<BdcXmDO> bdcXmDOList, @RequestParam(value = "xgyy", required = false) String xgyy) throws Exception {
        for (BdcXmDO bdcXmDO : bdcXmDOList) {
            String xmid = bdcXmDO.getXmid();
            // 查询出修改前的业务信息
            BdcYwxxDTO bdcYwxxDTOBefore = bdcInitFeignService.queryYwxx(xmid);
            // 如果日期没有变化不更新改字段
            if (bdcXmDO.getDjsj() != null) {
                BdcXmDO xm = bdcYwxxDTOBefore.getBdcXm();
                if (xm.getDjsj() != null) {
                    boolean sameDay = DateUtils.isSameDay(xm.getDjsj(), bdcXmDO.getDjsj());
                    if (sameDay) {
                        bdcXmDO.setDjsj(null);
                    }
                }
            }
            // 盐城 信息补录保存时如果登记时间和登簿人为空，默认填入当前时间和当前人（为了审核时能正常审核）
            if (StringUtils.isNotBlank(version) && StringUtils.equals(version, "yancheng")) {
                if (bdcXmDO.getDjsj() == null) {
                    if (bdcYwxxDTOBefore.getBdcXm() != null && bdcYwxxDTOBefore.getBdcXm().getDjsj() == null) {
                        bdcXmDO.setDjsj(new Date());
                    }
                }
                if (StringUtils.isBlank(bdcXmDO.getDbr()) && sfxydbr) {
                    if (bdcYwxxDTOBefore.getBdcXm() != null && StringUtils.isBlank(bdcYwxxDTOBefore.getBdcXm().getDbr())) {
                        bdcXmDO.setDbr(userManagerUtils.getCurrentUserName());
                    }
                }
            }

            // 更新业务信息
            bdcXmFeignService.updateBdcXm(bdcXmDO);
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
            if(Objects.nonNull(bdcQl)) {
                JSONObject qlObj = new JSONObject();
                qlObj.put("dbr",bdcXmDO.getDbr());
                qlObj.put("djsj",bdcXmDO.getDjsj());
                qlObj.put("djyy",bdcXmDO.getDjyy());
                BdcDjxxUpdateQO bdcQlxxUpdateQO = new BdcDjxxUpdateQO();
                bdcQlxxUpdateQO.setJsonStr(JSON.toJSONString(qlObj));
                Map qlxxWhereMap = new HashMap<>();
                qlxxWhereMap.put("xmids", Collections.singletonList(xmid));
                bdcQlxxUpdateQO.setClassName(bdcQl.getClass().getName());
                bdcQlxxUpdateQO.setWhereMap(qlxxWhereMap);
                bdcQllxFeignService.updateBatchBdcQl(bdcQlxxUpdateQO);
            }
            // 查询过修改后的结果
            BdcYwxxDTO bdcYwxxDTOAfter = bdcInitFeignService.queryYwxx(xmid);
            Map<String, String> data = LogCompareUtils.initDataString(xmid, bdcYwxxDTOBefore, bdcYwxxDTOAfter);
            if (StringUtils.isNotBlank(xgyy) && StringUtils.isNotBlank(version) && StringUtils.equals(version, "yancheng")) {
                data.put("xgyy", xgyy);
            }
            if (StringUtils.isNotBlank(RSAEncryptUtils.decrypt(data.get("change").toString()))) {
                LogMsgDTO logMsgDTO = new LogMsgDTO();
                logMsgDTO.setPrincipal(userManagerUtils.getCurrentUserName());
                logMsgDTO.setTags(data);
                logMsgDTO.setEvent(CommonConstantUtils.XXBL);
                logMessageClient.save(logMsgDTO);
            }
            //回写大云
            bdcYwsjHxFeignService.saveBdcYwsj(bdcXmDO.getGzlslid());
        }

    }


    /**
    * @param bdcQlQO 权利信息查询QO
    * @return
    * @description 信息补录批量数据，查询
    **/
    @GetMapping(value = "/getQlxxList/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Object getXxblQllist(@LayuiPageable Pageable pageable, BdcQlQO bdcQlQO) {
        Page<BdcQlPageResponseDTO> bdcQlPageResponseDTOS = bdcXmFeignService.bdcQlPageByPageJson(pageable, JSON.toJSONString(bdcQlQO));
        return super.addLayUiCode(bdcQlPageResponseDTOS);
    }

    /**
     * 更新权利冗余字段
     *
     * @param json  权利
     */
    @PatchMapping("/updateQlRyzd")
    public void updateQlRyzd(@RequestBody String json) {
        List<BdcQl> bdcQls = JSON.parseArray(json, BdcQl.class);
        JSONArray objects = JSON.parseArray(json);
        for (int i = 0; i < objects.size(); i++) {
            JSONObject jsonObject = objects.getJSONObject(i);
            String xmid = (String) jsonObject.get("xmid");
            BdcQl bdcQlxx = bdcQllxFeignService.queryQlxx(xmid);
            jsonObject.put("qlid", bdcQlxx.getQlid());
            bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), bdcQlxx.getClass().getName());

        }

    }

}
