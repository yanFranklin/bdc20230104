package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.utils.Constants;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.init.BdcDeleteYwxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.HlwSlztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.*;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.building.AcceptBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.BdcYctbFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.rest.init.BdcInitRestService;
import cn.gtmap.realestate.common.core.support.pdf.service.impl.OfficePdfServiceImpl;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.TaskUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import lombok.SneakyThrows;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * @program: realestate
 * @description: ??????????????????
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-06-24 14:52
 **/
@Controller
@RequestMapping("/ycsl")
@Validated
public class YcslXxController extends BaseController {
    @Autowired
    private BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    private BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    private BdcSlJyxxFeignService bdcSlJyxxFeignService;
    @Autowired
    private BdcYcslFeignService bdcYcslFeignService;
    @Autowired
    BdcSlFwxxFeignService bdcSlFwxxFeignService;
    @Autowired
    BdcSlFwcxFeignService bdcSlFwcxFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcSlFeignService bdcSlFeignService;
    @Autowired
    BdcBhFeignService bdcBhFeignService;
    @Autowired
    BdcSlSqrFeignService bdcSlSqrFeignService;
    @Autowired
    BdcSlHsxxFeignService bdcSlHsxxFeignService;
    @Autowired
    BdcSlHsxxMxFeignService bdcSlHsxxMxFeignService;
    @Autowired
    BdcSlEntityFeignService bdcSlEntityFeignService;
    @Autowired
    AcceptBdcdyFeignService acceptBdcdyFeignService;
    @Autowired
    BdcSlJyxxCezsFeignService bdcSlJyxxCezsFeignService;
    @Autowired
    BdcGzyzFeignService bdcGzyzFeignService;
    @Autowired
    private BdcInitRestService bdcInitFeignService;
    @Autowired
    BdcYctbFeignService bdcYctbFeignService;

    @Autowired
    private TaskUtils taskUtils;

    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    OfficePdfServiceImpl officePdfService;

    @Value("${ycsl.taxurl:}")
    public String taxurl;

    @Value("${print.path:}")
    private String printPath;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    /**
     * ????????????????????????
     */
    private static final String YCSL_HTML = "/view/ycsl/ycym.html";
    /**
     * ????????????????????????
     */
    private static final String YCSL_PL_HTML = "/view/ycsl/ycymList.html";

    /**
     * ??????????????????????????????
     */
    private static final String SWXX_HTML = "/view/ycsl/swxx.html";
    /**
     * ??????????????????????????????
     */
    private static final String SWXX_PL_HTML = "/view/ycsl/swxxList.html";

    private static final List<Integer> sssplyList = new ArrayList<>(4);

    static {
        sssplyList.add(CommonConstantUtils.SPLY_YCSL);
        sssplyList.add(CommonConstantUtils.SPLY_WWSQ_YCSL);
        sssplyList.add(CommonConstantUtils.SPLY_YCTB_SS);
    }

    @GetMapping("/v1.0/ycym")
    @ResponseStatus(HttpStatus.OK)
    public String forwardYcslHtml(@RequestParam(name = "processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("???????????????ID???????????????");
        }
        List<BdcXmDO> bdcXmDOList = new ArrayList<>();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        bdcXmQO.setSplys(sssplyList);
        // ??????????????????????????????????????????
        List<BdcXmDO> ycslXmList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(ycslXmList)) {
            bdcXmDOList.addAll(ycslXmList);
        }
        if (bdcXmDOList.size() == 1) {
            return YCSL_HTML;
        }
        return YCSL_PL_HTML;
    }

    /**
     * ????????????????????????
     *
     * @param processInsId ???????????????id
     * @return ????????????
     */
    @GetMapping("/v1.0/swxx")
    @ResponseStatus(HttpStatus.OK)
    public String forwardSwxxHtml(@RequestParam(name = "processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("???????????????ID???????????????");
        }
        List<BdcXmDO> bdcXmDOList = new ArrayList<>();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        bdcXmQO.setSplys(sssplyList);
        // ??????????????????????????????????????????
        List<BdcXmDO> ycslXmList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(ycslXmList)) {
            bdcXmDOList.addAll(ycslXmList);
        }
        if (bdcXmDOList.size() <= 1) {
            return SWXX_HTML;
        }
        return SWXX_PL_HTML;
    }

    /**
     * @param gzlslid ???????????????ID
     * @return ??????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????ID??????????????????????????????
     */
    @ResponseBody
    @GetMapping("/bygzlid")
    public BdcSlXmDO listBdcSlXmByGzlslid(String gzlslid) {
        BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlXmDO> list = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(list)) {
                bdcSlXmDO = list.get(0);
            }
        }
        return bdcSlXmDO;
    }


    /**
     * @param gzlslid ???????????????ID
     * @return ??????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????ID??????????????????????????????
     * @description ???xmid??????????????????????????????xmid????????????????????????????????????tab???xmid???????????????????????????xmid???gzlslid??????
     */
    @ResponseBody
    @GetMapping("")
    public Object queryYcslXx(String gzlslid, String xmid) {
        if (StringUtils.isBlank(xmid)) {
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
            if (bdcSlJbxxDO != null) {
                List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
                if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                    xmid = bdcSlXmDOList.get(0).getXmid();
                }
            }
        }
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("xmid");
        }
        YcslYmxxDTO ycslYmxxDTO = bdcYcslFeignService.queryYcslYmxx(xmid);

        List<BdcSlSqrDTO> bdcSlZrfDTOList = ycslYmxxDTO.getBdcSlZrfDTOList();
        List<BdcSlSqrDTO> listQlr = setSxh(bdcSlZrfDTOList);
        ycslYmxxDTO.setBdcSlZrfDTOList(listQlr);

        List<BdcSlSqrDTO> bdcSlZcfDTOList = ycslYmxxDTO.getBdcSlZcfDTOList();
        List<BdcSlSqrDTO> listYwr = setSxh(bdcSlZcfDTOList);
        ycslYmxxDTO.setBdcSlZcfDTOList(listYwr);

        return ycslYmxxDTO;
    }

    /**
     * ??????????????????????????????
     *
     * @param gzlslid ???????????????ID
     * @return ????????????
     */
    @ResponseBody
    @GetMapping("/pl")
    public Object queryYcslXxPl(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException(ErrorCode.MISSING_ARG, "???????????????????????????ID");
        }
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (Objects.isNull(bdcSlJbxxDO)) {
            throw new AppException(ErrorCode.MISSING_ARG, "??????????????????????????????");
        }
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
        if (CollectionUtils.isEmpty(bdcSlXmDOList)) {
            throw new AppException(ErrorCode.MISSING_ARG, "??????????????????????????????");
        }
        List<YcslYmxxDTO> ycslYmxxDTOList = new ArrayList<>(bdcSlXmDOList.size());
        for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
            YcslYmxxDTO ycslYmxxDTO = bdcYcslFeignService.queryYcslYmxx(bdcSlXmDO.getXmid());

            List<BdcSlSqrDTO> bdcSlZrfDTOList = ycslYmxxDTO.getBdcSlZrfDTOList();
            List<BdcSlSqrDTO> listQlr = setSxh(bdcSlZrfDTOList);
            ycslYmxxDTO.setBdcSlZrfDTOList(listQlr);

            List<BdcSlSqrDTO> bdcSlZcfDTOList = ycslYmxxDTO.getBdcSlZcfDTOList();
            List<BdcSlSqrDTO> listYwr = setSxh(bdcSlZcfDTOList);
            ycslYmxxDTO.setBdcSlZcfDTOList(listYwr);

            ycslYmxxDTO.setBdcSlXmDO(bdcSlXmDO);
            ycslYmxxDTOList.add(ycslYmxxDTO);
        }
        return ycslYmxxDTOList;
    }

    /**
     * ?????????????????????XMID???????????????????????????
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: BdcSlXmDO ?????????????????????????????????
     * @return: Integer ????????????
     */
    @ResponseBody
    @PatchMapping("/xmxx")
    public Integer updateXmxxByXmid(@RequestBody BdcSlXmDO bdcSlXmDO) {
        return bdcSlXmFeignService.updateBdcSlXm(bdcSlXmDO);
    }

    /**
     * @param json ????????????json
     * @return ????????????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????
     */
    @ResponseBody
    @PatchMapping("/htxx")
    public BdcSlJyxxDO updateHtxx(@RequestBody String json) {

        BdcSlJyxxDO bdcSlJyxxDO = JSONObject.parseObject(json, BdcSlJyxxDO.class);
        //????????????????????????
        bdcSlJyxxDO = bdcSlJyxxFeignService.saveBdcSlJyxx(bdcSlJyxxDO);
        return bdcSlJyxxDO;
    }

    /**
     * @param json ????????????json
     * @return ????????????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????
     */
    @ResponseBody
    @PatchMapping("/fwxx")
    public Integer updateFwxx(@RequestBody String json) {

        BdcSlFwxxDO bdcSlFwxxDO = JSONObject.parseObject(json, BdcSlFwxxDO.class);
        //????????????????????????
        return bdcSlFwxxFeignService.updateBdcSlFwxx(bdcSlFwxxDO);
    }

    /**
     * ??????xmid??????????????????
     *
     * @param json ????????????json
     * @return ????????????
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PatchMapping("/fwxxbyxmid")
    public Integer updateFwxxByXmid(@RequestBody String json) {
        BdcSlFwxxDO bdcSlFwxxDO = JSONObject.parseObject(json, BdcSlFwxxDO.class);
        //????????????????????????
        return bdcSlFwxxFeignService.updateBdcSlFwxxByXmid(bdcSlFwxxDO);
    }

    /**
     * @param json ????????????json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ???????????????????????????
     * @date : 2020/5/19 14:17
     */
    @ResponseBody
    @PatchMapping("/hsxx")
    public Object updateHsxx(@RequestBody String json) {
        BdcSlHsxxDO bdcSlHsxxDO = JSONObject.parseObject(json, BdcSlHsxxDO.class);
        if (StringUtils.isBlank(bdcSlHsxxDO.getHsxxid())) {
            return bdcSlHsxxFeignService.insertBdcSlHsxxDO(bdcSlHsxxDO);
        }
        return bdcSlHsxxFeignService.updateBdcSlHsxx(bdcSlHsxxDO);
    }

    /**
     * @param json ???????????????????????????JSON
     * @return ????????????
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description ?????????????????????????????????
     */
    @ResponseBody
    @PatchMapping("/jyxxcezs")
    public BdcSlJyxxCezsDO modifyJyxxCezs(@RequestBody String json) {
        Preconditions.checkArgument(StringUtils.isNotBlank(json), "?????????????????????????????????");
        BdcSlJyxxCezsDO bdcSlJyxxCezsDO = JSONObject.parseObject(json, BdcSlJyxxCezsDO.class);
        return bdcSlJyxxCezsFeignService.saveBdcSlJyxxCezs(bdcSlJyxxCezsDO);
    }

    /**
     * @param fcjyxxQO
     * @return
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????
     */
    @ResponseBody
    @GetMapping("/fcjyxx")
    public Object queryYcslFcjyxx(FcjyxxQO fcjyxxQO) {
        Preconditions.checkArgument(StringUtils.isNotBlank(fcjyxxQO.getHtbh()), "??????????????????");
        Preconditions.checkArgument(StringUtils.isNotBlank(fcjyxxQO.getXmid()), "??????????????????ID");
        Preconditions.checkArgument(StringUtils.isNotBlank(fcjyxxQO.getLclx()), "????????????????????????");
        return bdcSlJyxxFeignService.queryFcjyClfHtxx(fcjyxxQO);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????????????????, ????????????????????????????????????????????????????????????????????????????????????
     * @date : 2022/12/15 13:45
     */
    @ResponseBody
    @GetMapping("/fcjyxx/bdcdyh")
    public Object queryFcjyxxByBdcdyh(FcjyxxQO fcjyxxQO) {
        Preconditions.checkArgument(StringUtils.isNotBlank(fcjyxxQO.getXmid()), "??????????????????ID");
        Preconditions.checkArgument(StringUtils.isNotBlank(fcjyxxQO.getLclx()), "????????????????????????");
        String htbh = fcjyxxQO.getHtbh();
        if (StringUtils.isBlank(fcjyxxQO.getHtbh())) {
            List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxFeignService.listBdcSlJyxxByXmid(fcjyxxQO.getXmid());
            if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                htbh = bdcSlJyxxDOList.get(0).getHtbh();
            }
        }
        if (StringUtils.isBlank(htbh)) {
            BdcXmQO bdcXmQO = new BdcXmQO(fcjyxxQO.getXmid());
            List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmList)) {
                Map paramMap = new HashMap(1);
                paramMap.put("bdcdyh", bdcXmList.get(0).getBdcdyh());
                Object response = exchangeInterfaceFeignService.requestInterface("xcFcjySpfQlr", paramMap);
                if (Objects.nonNull(response)) {
                    Map resultMap = (Map) response;
                    JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(resultMap.get("data")));
                    if (CollectionUtils.isNotEmpty(jsonArray)) {
                        JSONObject jsonObject = (jsonArray.getJSONObject(0));
                        htbh = jsonObject.getString("htbh");
                    }
                    Preconditions.checkArgument(StringUtils.isNotBlank(htbh), "?????????????????????????????????");
                }
            }
        }
        fcjyxxQO.setHtbh(htbh);
        fcjyxxQO.setBeanName("xcFcjySpfBaxx");
        return bdcSlJyxxFeignService.queryFcjyClfHtxx(fcjyxxQO);
    }

    /**
     * @param xmid ??????ID
     * @return ????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????????????????
     */
    @ResponseBody
    @GetMapping("/fwcx")
    public Object fwcx(String xmid) {
        BdcFwxxDTO bdcFwxxDTO = bdcSlFwcxFeignService.listFwxxByXmid(xmid);
        // ??????????????? ???????????????????????????fwtc??????
        updateOtherFwtc(xmid);
        return bdcFwxxDTO;
    }

    /**
     * @param xmid ??????ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????
     */
    @ResponseBody
    @GetMapping("/fwtcxx")
    public BdcFwxxDTO listBdcSlFwtcxxByXmid(String xmid) {
        BdcFwxxDTO bdcFwxxDTO = new BdcFwxxDTO();
        //?????????
        List<BdcSlFwtcxxDO> zrffwtcxxList = bdcSlFwcxFeignService.listBdcSlFwtcxxByXmid(xmid, CommonConstantUtils.QLRLB_QLR);
        if (CollectionUtils.isNotEmpty(zrffwtcxxList)) {
            bdcFwxxDTO.setBdcZrfZfxxDTOList(zrffwtcxxList);
        }
        //?????????
        List<BdcSlFwtcxxDO> zcffwtcxxList = bdcSlFwcxFeignService.listBdcSlFwtcxxByXmid(xmid, CommonConstantUtils.QLRLB_YWR);
        if (CollectionUtils.isNotEmpty(zcffwtcxxList)) {
            bdcFwxxDTO.setBdcZcfZfxxDTOList(zcffwtcxxList);
        }

        return bdcFwxxDTO;
    }

    /**
     * @param xmid ??????ID
     * @return ??????????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????ID??????????????????
     */
    @ResponseBody
    @GetMapping("/jyxx")
    public Object jyxx(String xmid) {

        List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxFeignService.listBdcSlJyxxByXmid(xmid);
        if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
            return bdcSlJyxxDOList.get(0);
        } else {
            return null;
        }
    }

    /**
     * @param xmid ??????ID
     * @return ??????????????????
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description ????????????ID??????????????????
     */
    @ResponseBody
    @GetMapping("/fwxx")
    public Object fwxx(String xmid) {
        List<BdcSlFwxxDO> bdcSlFwxxDOList = bdcSlFwxxFeignService.listBdcSlFwxxByXmid(xmid);
        if (CollectionUtils.isNotEmpty(bdcSlFwxxDOList)) {
            return bdcSlFwxxDOList.get(0);
        }
        return null;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????????????????????????????
     */
    @ResponseBody
    @PatchMapping("/jyxx/list")
    public int updateJyxxPl(@RequestBody String json, @RequestParam("processInsId") String processInsId, @RequestParam(name = "djxl", required = false) String djxl, String xmids) {
        List<String> xmidList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmids)) {
            xmidList = Arrays.asList(xmids.split(CommonConstantUtils.ZF_YW_DH));
        }
        return bdcSlJyxxFeignService.updateBatchBdcSlJyxx(processInsId, json, xmidList);
    }

    /**
     * @param xmid ??????ID
     * @return ???????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ???????????????????????????????????????
     */
    @ResponseBody
    @PatchMapping("/jyxx/xm")
    public int updateXmJyxx(@RequestBody String json, @RequestParam("xmid") String xmid, @RequestParam(name = "djxl", required = false) String djxl) {
        return bdcSlJyxxFeignService.updateXmSlJyxx(xmid, json, djxl);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????????????????????????????
     */
    @ResponseBody
    @GetMapping("/queryHfwxzjJnzt")
    public String queryHfwxzjJnzt(@RequestParam(name = "xmid", required = false) String xmid) {
        return bdcSlJyxxFeignService.queryHfwxzjJnzt(xmid);
    }

    /**
     * @param bdcTsDjxxRequestDTO ??????????????????????????????
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????????????????????????????
     */
    @ResponseBody
    @PostMapping("/tsBdcDjxx")
    public InitTsBdcDjxxResponseDTO initTsBdcDjxx(@RequestBody BdcTsDjxxRequestDTO bdcTsDjxxRequestDTO) throws Exception {
        return bdcSlFeignService.initTsBdcDjxx(bdcTsDjxxRequestDTO);
    }


    /**
     * ???????????????????????????
     *
     * @param xmid
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/checkBeforeTsBdcDj/{xmid}")
    public Object checkBeforeTsBdcDj(@PathVariable("xmid") String xmid) {
        // ??????bdc_sl_hsxx_mx????????????
        BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
        bdcSlHsxxQO.setXmid(xmid);
        Map map = new HashMap();
        List<BdcSlHsxxDO> list = bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
        if (CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                String hsxxid = list.get(i).getHsxxid();
                List<BdcSlHsxxMxDO> listMx = bdcSlHsxxMxFeignService.bdcSlHsxxMx(hsxxid);
                if (CollectionUtils.isEmpty(listMx)) {
                    map.put("result", false);
                    map.put("msg", "???????????????????????????????????????");
                    return map;
                }
            }
        } else {
            map.put("result", false);
            map.put("msg", "?????????????????????????????????");
            return map;
        }

        map.put("result", true);
        map.put("msg", "");
        return map;
    }


    /**
     * ??????????????????????????????????????????
     *
     * @param xmid
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/getTaxInfo/{xmid}")
    public Object payTaxInfo(@PathVariable("xmid") String xmid) {
        if (StringUtils.isEmpty(xmid)) {
            throw new AppException("????????????xmid");
        }
        Map map = new HashMap();
        // ????????????url
        map.put("url", taxurl);
        // ?????????????????????????????????
        BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
        bdcSlHsxxQO.setXmid(xmid);
        List<BdcSlHsxxDO> list = bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
        if (CollectionUtils.isNotEmpty(list)) {
            String hsxxid = list.get(0).getHsxxid();
            map.put("ytsswzt", list.get(0).getYtsswzt());
            List<BdcSlHsxxMxDO> listHsxxMx = bdcSlHsxxMxFeignService.bdcSlHsxxMx(hsxxid);
            map.put("hsxxmx", listHsxxMx);
            // ?????????????????? ?????????return
            if (CollectionUtils.isNotEmpty(listHsxxMx)) {
                return map;
            }
        } else {
            map.put("hsxxmx", null);
            map.put("ytsswzt", 0);
        }
        // ??????htbh
        List<BdcSlJyxxDO> listJyxx = bdcSlJyxxFeignService.listBdcSlJyxxByXmid(xmid);
        if (CollectionUtils.isNotEmpty(listJyxx)) {
            map.put("htbh", listJyxx.get(0).getHtbh());
        } else {
            throw new AppException("?????????????????????");
        }
        return map;
    }

    /**
     * ??????ytsswzt
     *
     * @param xmid
     */
    @ResponseBody
    @GetMapping(value = "/updateYtsswzt/{xmid}")
    public void updateYtsswzt(@PathVariable("xmid") String xmid) {
        BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
        bdcSlHsxxQO.setXmid(xmid);
        List<BdcSlHsxxDO> list = bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
        if (CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                String hsxxid = list.get(i).getHsxxid();
                BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
                bdcSlHsxxDO.setHsxxid(hsxxid);
                bdcSlHsxxDO.setYtsswzt(CommonConstantUtils.SF_S_DM);
                bdcSlHsxxFeignService.updateBdcSlHsxx(bdcSlHsxxDO);
            }
        }
    }


    /**
     * @param jyxxid ????????????ID
     * @return ????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????????????????
     */
    @ResponseBody
    @PostMapping("/generateHtbh")
    public String generateHtbh(String jyxxid) {
        if (StringUtils.isBlank(jyxxid)) {
            throw new MissingArgumentException("jyxxid");
        }
        String htbh = bdcBhFeignService.queryCommonBh(Constants.YWLX_HTBH, CommonConstantUtils.ZZSJFW_DAY, 5, "F");
        //??????????????????
        BdcSlJyxxDO bdcSlJyxxDO = new BdcSlJyxxDO();
        bdcSlJyxxDO.setJyxxid(jyxxid);
        bdcSlJyxxDO.setHtbah(htbh);
        bdcSlJyxxDO.setHtbh(htbh);
        bdcSlJyxxFeignService.saveBdcSlJyxx(bdcSlJyxxDO);
        return htbh;
    }

    /**
     * @param xmid  ??????ID
     * @param sqrlb ???????????????
     * @return ????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description <p> 1???????????????????????????????????????????????????????????????????????????????????????????????????
     * 2?????????????????????????????????????????????????????????????????????
     */
    @ResponseBody
    @PostMapping("/getFwtcxx")
    public void getFwtcxx(String xmid, String sqrlb) {
        //??????????????????????????????????????????,???????????????????????????
        bdcSlFwcxFeignService.listBdcZfxxDTONT(xmid, sqrlb, true);
    }

    /**
     * ?????????????????????????????????????????????, ???????????????????????????????????? fwtcxx ???
     *
     * @param xmid ??????ID
     * @return ??????????????????????????????
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/getAllFwtcxx/{xmid}")
    public BdcFwxxDTO getFwtcxx(@PathVariable(name = "xmid") String xmid) {
        BdcFwxxDTO bdcFwxxDTO = new BdcFwxxDTO();
        List<BdcSlFwtcxxDO> qlrFwtcxxList = bdcSlFwcxFeignService.listBdcZfxxDTONT(xmid, CommonConstantUtils.QLRLB_QLR, false);
        bdcFwxxDTO.setBdcZrfZfxxDTOList(qlrFwtcxxList);
        List<BdcSlFwtcxxDO> ywrFwtcxxList = bdcSlFwcxFeignService.listBdcZfxxDTONT(xmid, CommonConstantUtils.QLRLB_YWR, false);
        bdcFwxxDTO.setBdcZcfZfxxDTOList(ywrFwtcxxList);
        return bdcFwxxDTO;
    }

    /**
     * ????????????????????????
     *
     * @param compareFwtcxxQO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @ResponseBody
    @PostMapping("nantong/compareFwtcxx")
    public CompareFwtcxxResultDTO compareFwtcxx(@RequestBody CompareFwtcxxQO compareFwtcxxQO) {
        if (StringUtils.isBlank(compareFwtcxxQO.getXmid()) && StringUtils.isBlank(compareFwtcxxQO.getSqrid())) {
            throw new AppException("??????ID,?????????ID???????????????");
        }
        //??????????????????????????????????????????,???????????????????????????
        return bdcSlFwcxFeignService.compareFwtcxx(compareFwtcxxQO);
    }


    /**
     * @param gzlslid
     * @param loadpage ????????????
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????????????????
     */
    @ResponseBody
    @GetMapping(value = "/listYcslxxByPage")
    public Object listYcslxxByPage(@LayuiPageable Pageable pageable, String gzlslid, Boolean loadpage) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("??????????????????gzlslid");
        }
        Map map = new HashMap();
        map.put("gzlslid", gzlslid);
        map.put("qllxs", Arrays.asList(CommonConstantUtils.QLLX_FDCQ));
        String ycslxxQO = JSON.toJSONString(map);
        if (loadpage != null && loadpage) {
            return addLayUiCode(bdcSlFeignService.listYcslxxByPage(pageable, ycslxxQO));
        } else {
            return bdcSlFeignService.listYcslxxList(ycslxxQO);
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????
     */
    @ResponseBody
    @PatchMapping("/updateJyxxList")
    public Integer updateJyxxList(@RequestBody String json) {
        Integer count = 0;
        for (Object obj : JSON.parseArray(json)) {
            count += bdcSlEntityFeignService.updateByJsonEntity(JSON.toJSONString(obj), BdcSlJyxxDO.class.getName());
        }
        return count;
    }

    /**
     * ??????????????????????????????
     *
     * @param xmid
     */
    private void updateOtherFwtc(String xmid) {
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setXmid(xmid);
        List<BdcSlSqrDO> qlrList = bdcSlSqrFeignService.listBdcSlSqrByXmid(xmid,
                CommonConstantUtils.QLRLB_QLR);
        int qlrsize = qlrList.size();
        List<BdcSlSqrDO> ywrList = bdcSlSqrFeignService.listBdcSlSqrByXmid(xmid,
                CommonConstantUtils.QLRLB_YWR);
        int ywrsize = ywrList.size();
        // ????????????(??????)
        List<BdcSlXmDO> list = bdcSlXmFeignService.listBdcSlXm(bdcSlXmQO);

        if (CollectionUtils.isNotEmpty(list)) {
            String jbxxid = list.get(0).getJbxxid();
            bdcSlXmQO.setXmid(null);
            bdcSlXmQO.setJbxxid(jbxxid);
            // ????????????
            list = bdcSlXmFeignService.listBdcSlXm(bdcSlXmQO);
            // ??????????????????????????????????????????????????????sqrid??????
            for (int i = 0; i < list.size(); i++) {
                // ?????????????????????????????????fwtc
                if (!xmid.equals(list.get(i).getXmid())) {
                    // ??????????????????????????? ?????????????????????qlr
                    List<BdcSlSqrDO> listSqrQlr = bdcSlSqrFeignService.listBdcSlSqrByXmid(list.get(i).getXmid(),
                            CommonConstantUtils.QLRLB_QLR);
                    // ??????????????????size?????????????????????
                    if (listSqrQlr.size() == qlrsize) {
                        for (int j = 0; j < listSqrQlr.size(); j++) {
                            // ????????????????????? ?????????????????????
                            String sqrZjh = listSqrQlr.get(j).getZjh();
                            for (int q = 0; q < qlrList.size(); q++) {
                                String qlrzjh = qlrList.get(q).getZjh();
                                if (sqrZjh.equals(qlrzjh)) {
                                    BdcSlSqrQO bdcSlSqrQO = new BdcSlSqrQO();
                                    bdcSlSqrQO.setXmid(qlrList.get(q).getXmid());
                                    bdcSlSqrQO.setZjh(qlrzjh);
                                    bdcSlSqrQO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
                                    List<BdcSlSqrDO> listSqr = bdcSlSqrFeignService.listBdcSlSqrByBdcSlSqrQO(bdcSlSqrQO);
                                    if (CollectionUtils.isNotEmpty(listSqr)) {
                                        // ????????????????????? ????????????????????????????????????????????????
                                        updateFwtc(listSqr.get(0), list);
                                    }
                                }
                            }
                        }
                    }

                    // ??????????????????????????? ?????????????????????ywr
                    List<BdcSlSqrDO> listSqrYwr = bdcSlSqrFeignService.listBdcSlSqrByXmid(list.get(i).getXmid(),
                            CommonConstantUtils.QLRLB_YWR);
                    // ??????????????????size?????????????????????
                    if (listSqrYwr.size() == ywrsize) {
                        for (int j = 0; j < listSqrYwr.size(); j++) {
                            // ????????????????????? ?????????????????????
                            String sqrZjh = listSqrYwr.get(j).getZjh();
                            for (int q = 0; q < ywrList.size(); q++) {
                                String qlrzjh = ywrList.get(q).getZjh();
                                if (sqrZjh.equals(qlrzjh)) {
                                    BdcSlSqrQO bdcSlSqrQO = new BdcSlSqrQO();
                                    bdcSlSqrQO.setXmid(ywrList.get(q).getXmid());
                                    bdcSlSqrQO.setZjh(qlrzjh);
                                    bdcSlSqrQO.setSqrlb(CommonConstantUtils.QLRLB_YWR);
                                    List<BdcSlSqrDO> listSqr = bdcSlSqrFeignService.listBdcSlSqrByBdcSlSqrQO(bdcSlSqrQO);
                                    if (CollectionUtils.isNotEmpty(listSqr)) {
                                        updateFwtc(listSqr.get(0), list);
                                        // ????????????????????? ????????????????????????????????????????????????
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * ????????????????????????
     *
     * @param bdcSlSqrDO
     * @param list
     */
    public void updateFwtc(BdcSlSqrDO bdcSlSqrDO, List<BdcSlXmDO> list) {
        String fwtc = bdcSlSqrDO.getFwtc();
        List<BdcSlXmDO> exincludeList = new ArrayList<>();
        // ??????????????????????????????????????????????????????sqrid??????
        for (int i = 0; i < list.size(); i++) {
            // ?????????????????????????????????fwtc
            if (!bdcSlSqrDO.getXmid().equals(list.get(i).getXmid())) {
                exincludeList.add(list.get(i));
            }
        }
        if (CollectionUtils.isNotEmpty(exincludeList)) {
            for (int i = 0; i < exincludeList.size(); i++) {
                String xmid = exincludeList.get(i).getXmid();
                BdcSlSqrQO bdcSlSqrQO = new BdcSlSqrQO();
                bdcSlSqrQO.setXmid(xmid);
                bdcSlSqrQO.setZjh(bdcSlSqrDO.getZjh());
                bdcSlSqrQO.setSqrlb(bdcSlSqrDO.getSqrlb());
                List<BdcSlSqrDO> listSqr = bdcSlSqrFeignService.listBdcSlSqrByBdcSlSqrQO(bdcSlSqrQO);
                if (CollectionUtils.isNotEmpty(listSqr)) {
                    if (StringUtils.isEmpty(listSqr.get(0).getFwtc())) {
                        BdcSlSqrDO bdcSlSqrDOOther = listSqr.get(0);
                        if (Integer.parseInt(fwtc) > 1) {
                            if (i == 0 || i == 1) {
                                bdcSlSqrDOOther.setFwtc((Integer.parseInt(fwtc) + 1) + "");
                            } else {
                                bdcSlSqrDOOther.setFwtc(CommonConstantUtils.FWTC_QT);
                            }
                        } else {
                            bdcSlSqrDOOther.setFwtc(CommonConstantUtils.FWTC_QT);
                        }
                        bdcSlSqrFeignService.updateBdcSlSqr(bdcSlSqrDOOther);
                    }
                }
            }
        }
    }

    /**
     * @param bdcSqrDOList ???????????????
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description ????????????????????????
     */
    private List<BdcSlSqrDTO> setSxh(List<BdcSlSqrDTO> bdcSqrDOList) {
        if (CollectionUtils.isNotEmpty(bdcSqrDOList)) {
            int qlrsxh = 0;
            int ywrsxh = 0;
            for (int i = 0; i < bdcSqrDOList.size(); i++) {
                int sxh = 0;
                BdcSlSqrDO bdcSlSqrDO = bdcSqrDOList.get(i).getBdcSlSqrDO();
                if (StringUtils.equals(bdcSlSqrDO.getSqrlb(), CommonConstantUtils.QLRLB_QLR)) {
                    qlrsxh++;
                    sxh = qlrsxh;
                }
                if (StringUtils.equals(bdcSlSqrDO.getSqrlb(), CommonConstantUtils.QLRLB_YWR)) {
                    ywrsxh++;
                    sxh = ywrsxh;
                }
                if (bdcSlSqrDO.getSxh() == null || bdcSlSqrDO.getSxh() == 0) {
                    bdcSlSqrDO.setSxh(sxh);
                    bdcSlSqrFeignService.updateBdcSlSqr(bdcSlSqrDO);
                }
            }
        }
        return bdcSqrDOList;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????????????????????????????
     */
    @ResponseBody
    @GetMapping("/list/bdcslxm")
    public List<BdcSlXmDO> listBdcSlXm(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("?????????????????????ID");
        }
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
        /* ??????xmid?????? */
        bdcSlXmDOList.sort(Comparator.comparing(BdcSlXmDO::getXmid));
        return bdcSlXmDOList;
    }


    /**
     * @param gzlslid ???????????????ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description ?????????????????????ID???????????????????????????
     */
    @ResponseBody
    @GetMapping("/online/feedback")
    public void onlineFeedback(@RequestParam(name = "gzlslid") String gzlslid,
                               @RequestParam(name = "opinion") String opinion) {
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid), "???????????????????????????ID???");
        exchangeInterfaceFeignService.workflowSyncRequestInterface("wwsqupdateslzt", gzlslid, null, opinion, HlwSlztEnum.FJERROR.getSlzt());
    }

    /**
     * @param xmid ??????id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????
     * @date : 2020/5/19 9:03
     */
    @ResponseBody
    @GetMapping("/bengbu")
    public Object queryYcslYmxx(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new AppException("??????????????????????????????id");
        }
        BbYcslYmDTO bbYcslYmDTO = new BbYcslYmDTO();
        List<BdcSlFwxxDO> bdcSlFwxxDOList = bdcSlFwxxFeignService.listBdcSlFwxxByXmid(xmid);
        if (CollectionUtils.isNotEmpty(bdcSlFwxxDOList)) {
            bbYcslYmDTO.setBdcSlFwxxDO(bdcSlFwxxDOList.get(0));
        }
        List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxFeignService.listBdcSlJyxxByXmid(xmid);
        if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
            bbYcslYmDTO.setBdcSlJyxxDO(bdcSlJyxxDOList.get(0));
        }
        BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
        bdcSlHsxxQO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
        bdcSlHsxxQO.setXmid(xmid);
        List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
        if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
            bbYcslYmDTO.setBdcSlHsxxDO(bdcSlHsxxDOList.get(0));
        }
        return bbYcslYmDTO;
    }


    /**
     * ?????????????????????ID????????????????????????
     *
     * @param gzlslid ???????????????ID
     * @return spf ???????????? clf ?????????
     */
    @ResponseBody
    @GetMapping("/checkSpfLc")
    public String checkLcLx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("???????????????????????????ID??????");
        }
        BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (null == bdcSlJbxxDO) {
            throw new MissingArgumentException("??????????????????????????????");
        }
        if (bdcGzyzFeignService.checkSpfLc(bdcSlJbxxDO.getGzldyid())) {
            return CommonConstantUtils.FCJY_TYPE_SPF;
        }
        return CommonConstantUtils.FCJY_TYPE_CLF;
    }

    /**
     * ????????????????????????
     *
     * @param gzlslid ???????????????id
     * @return
     */
    @ResponseBody
    @GetMapping("/tsjssj")
    public Object querySwTsjssj(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("???????????????????????????ID??????");
        }
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            HashMap<String, String> map = new HashMap<>();
            BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
            bdcSlHsxxQO.setXmid(bdcSlXmDOList.get(0).getXmid());
            List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
            if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                String swjssj = bdcSlHsxxDOList.get(0).getJssksj() == null ? "" : DateFormatUtils.format(bdcSlHsxxDOList.get(0).getJssksj(), "yyyy-MM-dd HH:mm:ss");
                map.put("swjssj", swjssj);
                // ??????????????????AB???????????????
                map.put("qyjyzsbz", String.valueOf(bdcSlHsxxDOList.get(0).getQyjyzsbz() == null ? "" : bdcSlHsxxDOList.get(0).getQyjyzsbz()));
            }
            String swtssj = bdcSlXmDOList.get(0).getTsswsj() == null ? "" : DateFormatUtils.format(bdcSlXmDOList.get(0).getTsswsj(), "yyyy-MM-dd HH:mm:ss");
            map.put("swtssj", swtssj);
            return map;
        }
        return null;

    }

    @GetMapping("/shbtg")
    public void shbtg(String gzlslid, String reason) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("???????????????????????????ID??????");
        }
        try {
            BdcDeleteYwxxDTO bdcDeleteYwxxDTO = new BdcDeleteYwxxDTO();
            bdcDeleteYwxxDTO.setGzlslid(gzlslid);
            bdcDeleteYwxxDTO.setReason(reason);
            bdcDeleteYwxxDTO.setSlzt(HlwSlztEnum.ABANDON.getSlzt());
            bdcInitFeignService.deleteYwxxAllSubsystem(bdcDeleteYwxxDTO);
        } catch (Exception e) {
            LOGGER.error("???????????????????????????processInsId??????" + gzlslid, e);
            return;
        }

        try {
            // ????????????
            taskUtils.deleteTask(gzlslid, reason);
        } catch (Exception e) {
            LOGGER.error("???????????????????????????processInsId??????" + gzlslid, e);
        }

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????????????????????????????
     */
    @GetMapping("/bdxx")
    @ResponseBody
    public boolean checkYcslBdxx(String xmid, Boolean spflc) {
        return bdcGzyzFeignService.checkYcslBdxx(xmid, spflc);

    }

    /**
     * @param compareFwtcxxQO ??????????????????
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????
     */
    @ResponseBody
    @PostMapping("import/fwtcbdxx")
    public void drhybdxx(@RequestBody CompareFwtcxxQO compareFwtcxxQO) {
        bdcSlFwcxFeignService.drFwtcxx(compareFwtcxxQO);
    }


    @ResponseBody
    @GetMapping("/tzspt")
    public Object tzspt(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("???????????????????????????ID??????");
        }
        return bdcYctbFeignService.yctbAddTaxInfo(gzlslid);
    }


    /**
     * @param fjxx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/7/5 10:10
     */
    @ResponseBody
    @PostMapping("/fjxx/clf")
    public void scClfFjxx(@NotBlank(message = "???????????????????????????????????????") @RequestBody String fjxx) {
        JSONObject jsonObject = JSON.parseObject(fjxx);
        SjclUploadDTO sjclUploadDTO = new SjclUploadDTO();
        JSONArray fjArray = jsonObject.getJSONArray("fjnr");
        LOGGER.info("?????????????????????{}", JSON.toJSONString(jsonObject));
        if (CollectionUtils.isNotEmpty(fjArray)) {
            //?????????????????????????????????????????????????????????????????????????????????????????????
            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(jsonObject.getString("gzlslid"), jsonObject.getString("htbh"));
            if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                bdcSlSjclFeignService.deleteBdcSlSjclBySjclid(bdcSlSjclDOList.get(0).getSjclid());
            }
            List<TsswDataFjclXxDTO> fjList = fjArray.toJavaList(TsswDataFjclXxDTO.class);
            if (CollectionUtils.isNotEmpty(fjList)) {
                sjclUploadDTO.setFjList(fjList);
            }
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(jsonObject.getString("gzlslid"));
            String djxl = "";
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                djxl = bdcXmDTOList.get(0).getDjxl();
            } else {
                List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(jsonObject.getString("gzlslid"));
                if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                    djxl = bdcSlXmDOList.get(0).getDjxl();
                }
            }
            sjclUploadDTO.setGzlslid(jsonObject.getString("gzlslid"));
            sjclUploadDTO.setSjclmc(jsonObject.getString("htbh"));
            sjclUploadDTO.setDjxl(djxl);
            try {
                bdcSlSjclFeignService.createAndUploadFile(sjclUploadDTO);
            } catch (IOException e) {
                LOGGER.error("??????????????????", e);
            }
        }
    }

    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description ??????pdf????????????
     * @date : 2022/8/16 8:29
     */
    @ResponseBody
    @PostMapping("/fcjy/tsfjxx")
    public List<YcslFjxxDTO> queryFjxx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("??????????????????");
        }
        List<YcslFjxxDTO> list = bdcYcslFeignService.queryYcslFjxx(gzlslid);
        return list;
    }

    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description ??????pdf????????????
     * @date : 2022/8/19 13:51
     */
    @SneakyThrows
    @ResponseBody
    @GetMapping("/fcjy/ckpdf")
    public void checkFjxx(String pdfpath, HttpServletResponse response) throws IOException {
            OutputStream outputStream = response.getOutputStream();
            File pdfFile = null;
            try {
                pdfFile = new File(pdfpath);
                String file = URLEncoder.encode(pdfpath + ".pdf", "utf-8");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + file);
                // ????????????pdf????????????????????????
                FileUtils.copyFile(pdfFile, outputStream);
            } catch (Exception e) {
                LOGGER.error("????????????PDF?????????{}", e.toString());
                throw new AppException("????????????PDF?????????????????????");
            }
        }


    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description ????????????pdf????????????
     * @date : 2022/8/20 13:51
     */
    @ResponseBody
    @GetMapping("/hsxx/pdfprint")
    public void printFjxx(HttpServletResponse response, String[] json) throws IOException {
        Map map = new HashMap();

        if (null != json){
            for(int i = 0; i<json.length; i++){
                map.put(String.valueOf(i),json[i]);
            }
        }
        File pdfFile = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            String xmlStr = officePdfService.mergePdfFiles(map);
            pdfFile = new File(xmlStr);
            String file = URLEncoder.encode(xmlStr + ".pdf", "utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + file);
            // ????????????pdf????????????????????????
            FileUtils.copyFile(pdfFile, outputStream);
        } catch (Exception e) {
            LOGGER.error("????????????PDF?????????{}", e.toString());
            throw new AppException("????????????PDF?????????????????????");
        } finally {
            IOUtils.closeQuietly(outputStream);
            org.apache.commons.io.FileUtils.deleteQuietly(pdfFile);
        }
    }

    /**
     * @param gzlslid
     * @return ??????????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">sunyuzhuang</a>
     * @description ??????gzlslid??????????????????
     */
    @ResponseBody
    @GetMapping("/getJyxxByGzlslid")
    public Object getJyxxByGzlslid(String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(gzlslid);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOS)){
            for (BdcXmDO bdcXmDO : bdcXmDOS) {
                List<BdcSlJyxxDO> bdcSlJyxxDOS = bdcSlJyxxFeignService.listBdcSlJyxxByXmid(bdcXmDO.getXmid());
                if(CollectionUtils.isNotEmpty(bdcSlJyxxDOS)){
                    return bdcSlJyxxDOS.get(0);
                }
            }
        }
        return null;
    }
}
