package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.gtc.starter.gcas.config.GTAutoConfiguration;
import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlQjdcsqDO;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dto.accept.JypjqDTO;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcQjdcFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.*;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019/1/25
 * @description
 */
@Controller
@RequestMapping("/djdcb")
public class DjdcbController extends BaseController {
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    private ZdFeignService zdFeignService;
    @Autowired
    private FwFcqlrFeignService fwFcqlrFeignService;
    @Autowired
    private FwHsFeignService fwHsFeignService;
    @Autowired
    private BdcdyFeignService bdcdyFeignService;
    @Autowired
    private DjxxFeignService djxxFeignService;

    @Autowired
    private ZdQsdcFeignService zdQsdcFeignService;

    @Autowired
    private DjdcbFeignService djdcbFeignService;

    @Autowired
    private FwHstFeignService fwHstFeignService;

    @Autowired
    private CbzdFeignService cbzdFeignService;

    @Autowired
    private LqFeignService lqFeignService;

    @Autowired
    BdcQjdcFeignService bdcQjdcFeignService;
    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @RequestMapping("/initview")
    public String initView(Model model, String bdcdyh, String qjgldm) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            String dzwtzm = bdcdyh.substring(19, 20);
            model.addAttribute("dzwtzm", dzwtzm);
            model.addAttribute("bdcdyh", bdcdyh);
            model.addAttribute("qjgldm", qjgldm);
            if (cbzdFeignService.yzbdcdyh(bdcdyh)) {
                model.addAttribute("cbzd", "true");
            }
            if(djdcbFeignService.hasJyqdkDcb(bdcdyh)){
                model.addAttribute("jyq","true");
            }
        }
        model.addAttribute("tabNameList",djdcbFeignService.listTabName());
        return "djdcb/bdcdjdcb";
    }

    @RequestMapping("/zdtViewTable")
    public String zdtView(Model model, String bdcdyh, String qjgldm) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            model.addAttribute("bdcdyh", bdcdyh);
            model.addAttribute("qjgldm", qjgldm);
        }
        model.addAttribute("tabNameList",djdcbFeignService.listTabName());
        return "djdcb/zdtViewTable";
    }

    @RequestMapping("/fromljz")
    public String initViewLjz(Model model, String bdcdyh,String qjgldm){
        if(StringUtils.isBlank(bdcdyh) || StringUtils.length(bdcdyh) !=28) {
            throw new AppException("????????????????????????????????????");
        }
        if(StringUtils.isNotBlank(bdcdyh)){
            model.addAttribute("dzwtzm", CommonConstantUtils.DZWTZM_FW);
            model.addAttribute("bdcdyh", bdcdyh);
            model.addAttribute("qjgldm", qjgldm);
        }
        model.addAttribute("tabNameList",djdcbFeignService.listTabNameFromLjz());
        return "djdcb/bdcdjdcb";
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.ZdQsdcDO
     * @description ????????????????????????
     */
    @RequestMapping("/zdqsdc")
    @ResponseBody
    public ZdQsdcDO queryZdQsdcByBdcdyh(@NotBlank String bdcdyh,String qjgldm){
        return zdQsdcFeignService.queryZdQsdcByBdcdyh(bdcdyh,qjgldm);
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjDcbAndQlrResponseDTO
     * @description ??????????????????
     */
    @ResponseBody
    @RequestMapping("/zdxxinfo")
    public DjDcbAndQlrResponseDTO queryDjxxByBdcdyh(String bdcdyh,String qjgldm){
        return djxxFeignService.getDjdcbAndQlrByBdcdyh(bdcdyh,qjgldm);
    }

    /**
     * @param pageable
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.DjdcbJzxxResponseDTO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description ????????????????????????
     */
    @ResponseBody
    @RequestMapping("/jzxx/listbypage")
    public Page<DjdcbJzxxResponseDTO> jzxxListByPage(@LayuiPageable Pageable pageable, String bdcdyh,String qjgldm) {
        //?????????????????????????????????
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            paramMap.put("bdcdyh", StringUtils.deleteWhitespace(bdcdyh));
        }
        if(StringUtils.isNotBlank(qjgldm)){
            paramMap.put("qjgldm",qjgldm);
        }
        return zdFeignService.listZdjzxxByPageJson(pageable, JSONObject.toJSONString(paramMap));
    }


    /**
     * @param pageable
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.DjdcbJzxxResponseDTO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description ?????????????????????
     */
    @ResponseBody
    @RequestMapping("/qlr/listbypage")
    public Page<DjdcbFwQlrResponseDTO> qlrListByPage(@LayuiPageable Pageable pageable, String bdcdyh) {
        //?????????????????????????????????
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            paramMap.put("bdcdyh", StringUtils.deleteWhitespace(bdcdyh));
        }
        return fwFcqlrFeignService.listQlrByPageJson(pageable, JSONObject.toJSONString(paramMap));
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjdcbFwhsResponseDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description ??????????????????
     */
    @ResponseBody
    @RequestMapping("/fwhsinfo")
    public DjdcbFwhsResponseDTO queryDjdcbFwhsByBdcdyh(String bdcdyh,String qjgldm) {
        return fwHsFeignService.queryDjdcbFwhsByBdcdyh(bdcdyh,qjgldm);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @description ????????????BDCDY??????
     */
    @ResponseBody
    @RequestMapping("/fwbdcdyinfo")
    public BdcdyResponseDTO queryFwBdcdyByBdcdyh(String bdcdyh,String qjgldm){
        return bdcdyFeignService.queryBdcdy(bdcdyh,null,qjgldm);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return map
     * @description ?????????
     */
    @ResponseBody
    @RequestMapping("/zdt")
    public ZdtResponseDTO getZdtBaseCode(String bdcdyh,String qjgldm) throws IOException {
        return zdFeignService.queryZdtByBdcdyh(bdcdyh,qjgldm);
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djid
     * @param fwDcbIndex
     * @return java.lang.String
     * @description
     */
    @ResponseBody
    @RequestMapping("/hst")
    public String getFwHstBase64Code(@NotBlank(message = "????????????????????????") String djid,String fwDcbIndex,String qjgldm){
        // fwDcbIndex ????????? ???  fwDcbIndex == djid ??????????????????????????? ??????
        // ??????????????? fwDcbIndex ??? FW_HST?????? FW_HST_INDEX
        if(StringUtils.isNotBlank(fwDcbIndex) && StringUtils.equals(djid,fwDcbIndex)){
            return fwHstFeignService.queryLjzPmtBase64(fwDcbIndex,qjgldm);
        }else{
            return fwHstFeignService.queryFwHstBase64(djid,qjgldm);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.LqDcbDO
     * @description  ????????????????????? ?????? ?????????????????????
     */
    @ResponseBody
    @RequestMapping("/lqdcb")
    public LqDcbDO queryLqDcbByBdcdyh(@NotBlank(message = "??????????????????????????????") String bdcdyh,String qjgldm){
        return lqFeignService.queryLqByBdcdyh(bdcdyh,qjgldm);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.LqDcbDO
     * @description  ?????????????????? ???????????????????????????????????????????????????????????????
     */
    @ResponseBody
    @RequestMapping("/cbzd")
    public Map<String,Object> queryCbzdByBdcdyh(@NotBlank(message = "??????????????????????????????") String bdcdyh,String qjgldm){
        Map<String,Object> result = new HashMap<>();
        // ??????????????????????????????
        CbzdDcbDO cbzdDcbDO = cbzdFeignService.queryCbzdByBdcdyh(bdcdyh,qjgldm);
        if(cbzdDcbDO != null){
            result.put("cbzdDcb",cbzdDcbDO);
            // ?????????????????????
            List<CbzdCbfDO> cbfList = cbzdFeignService.listCbfByBdcdyh(bdcdyh,qjgldm);
            if(CollectionUtils.isNotEmpty(cbfList)){
                List<CbzdCbfAndJtcyDTO> cbzdCbfAndJtcyDTOList =new ArrayList<>();
                for(CbzdCbfDO cbzdCbfDO:cbfList){
                    CbzdCbfAndJtcyDTO cbzdCbfAndJtcyDTO =new CbzdCbfAndJtcyDTO();
                    cbzdCbfAndJtcyDTO.setCbzdCbfDO(cbzdCbfDO);
                    // ???????????????????????????
                    List<NydJtcyDO> jtcyList = cbzdFeignService.listCbzdJtcyByJtIndex(cbzdCbfDO.getCbzdCbfIndex(),qjgldm);
                    if(CollectionUtils.isNotEmpty(jtcyList)){
                        cbzdCbfAndJtcyDTO.setNydJtcyDOList(jtcyList);
                    }
                    cbzdCbfAndJtcyDTOList.add(cbzdCbfAndJtcyDTO);
                }
                result.put("cbfAndJtcyList",cbzdCbfAndJtcyDTOList);
            }

            // ?????????????????????
            List<CbzdFbfDO> fbfList = cbzdFeignService.listFbfByBdcdyh(bdcdyh,qjgldm);
            if(CollectionUtils.isNotEmpty(fbfList)){
                CbzdFbfDO fbfDO = fbfList.get(0);
                result.put("fbf",fbfDO);
            }
        }
        return result;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydZdmjDO>
     * @description ??????????????????????????????
     */
    @ResponseBody
    @RequestMapping("/cbzdflmj")
    public Page listCbzdFlmjByBdcdyh(@LayuiPageable Pageable pageable, @NotBlank(message = "??????????????????????????????") String bdcdyh, String qjgldm) {
        // ??????????????????????????????
        List<NydZdmjDO> flmjList = new ArrayList<>();
        CbzdDcbDO cbzdDcbDO = cbzdFeignService.queryCbzdByBdcdyh(bdcdyh, qjgldm);
        if (cbzdDcbDO != null && StringUtils.isNotBlank(cbzdDcbDO.getZddcbIndex())) {
            flmjList = cbzdFeignService.listNydZdmjByDjdcbIndex(cbzdDcbDO.getZddcbIndex(), qjgldm);
        }
        return new GTAutoConfiguration.DefaultPageImpl(flmjList, 1, 10000, flmjList.size());
    }


    @RequestMapping("/qjdc")
    public String forwardQjdc(Model model, @RequestParam(name = "processInsId") String processInsId) {
        if (StringUtils.isNotBlank(processInsId)) {
            //?????????????????????id?????????????????????
            List<BdcSlQjdcsqDO> bdcSlQjdcsqDOList = bdcQjdcFeignService.listSlQjdc(processInsId);
            if (CollectionUtils.isNotEmpty(bdcSlQjdcsqDOList)) {
                String bdcdyh = bdcSlQjdcsqDOList.get(0).getBdcdyh();
                if (StringUtils.isBlank(bdcdyh) || StringUtils.length(bdcdyh) != 28) {
                    throw new AppException("????????????????????????????????????");
                }
                if (StringUtils.isNotBlank(bdcdyh)) {
                    model.addAttribute("dzwtzm", CommonConstantUtils.DZWTZM_FW);
                    model.addAttribute("bdcdyh", bdcdyh);
                    model.addAttribute("qjgldm", "");
                }
                model.addAttribute("tabNameList", djdcbFeignService.listTabNameFromLjz());
                return "djdcb/bdcdjdcb";
            } else {
                throw new AppException("??????????????????????????????????????????");
            }
        }
        return "";
    }

    /**
     *
     * @param bdcdyh
     * @param qjgldm
     * @return ???????????????
     */
    @ResponseBody
    @RequestMapping("/hsthsanddz")
    public String queryHstBase64ForHsAndDz(@NotBlank(message = "??????????????????????????????") String bdcdyh, String qjgldm) {
        return fwHstFeignService.queryHstBase64ForHsAndDz(bdcdyh, qjgldm);
    }

    /**
     *
     * @param bdcdyh
     * @param qjgldm
     * @return ???????????????(??????)
     */
    @ResponseBody
    @RequestMapping("/hefei/gethst")
    public List<String> queryHstBase64Hefei(@NotBlank(message = "??????????????????????????????") String bdcdyh, String qjgldm) {
        return fwHstFeignService.queryHstBase64Hefei(bdcdyh, qjgldm);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????
     * @date : 2022/2/11 13:58
     */
    @ResponseBody
    @GetMapping("/hefei/dajbxx")
    public Object getDajbxx(@RequestParam(value = "slbh", required = false) String slbh,
                            @RequestParam(value = "bdcqzh", required = false) String bdcqzh,
                            @RequestParam(value = "type", required = false) String type) {
        Map param = new HashMap();
        if (StringUtils.isNotBlank(slbh)) {
            param.put("docId", slbh);
        }
        if (null != type) {
            param.put("type", type);
        }
        if (StringUtils.isNotBlank(bdcqzh)) {
            try {
                param.put("zqzh", URLDecoder.decode(bdcqzh, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("", e);
            }
        } else {
            param.put("zqzh", "");

        }
        if (MapUtils.isEmpty(param)) {
            throw new MissingArgumentException("?????????????????????");
        }
        return exchangeInterfaceFeignService.requestInterface("hfDaJbxx", param);
    }

    /**
     * @param archid ??????id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????????????????
     * @date : 2022/2/11 14:02
     */
    @ResponseBody
    @GetMapping("/hefei/damlxx")
    public Object getDamlxx(@RequestParam("archid") String archid,
                            @RequestParam(value = "type", required = false) String type) {
        if (StringUtils.isBlank(archid) && null != type) {
            throw new MissingArgumentException("?????????????????????");
        }

        Map param = new HashMap();
        param.put("archid", archid);
        return exchangeInterfaceFeignService.requestInterface("hfFcfhtMlxx", param);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????
     * @date : 2022/2/11 16:16
     */
    @ResponseBody
    @GetMapping("/hefei/fcfht")
    public Object getDafjxx(@RequestParam("archid") String archid, @RequestParam("currentpage") Integer currentpage,
                            @RequestParam(value = "type", required = false) String type) {
        if (StringUtils.isBlank(archid) || currentpage == null || null == type) {
            throw new MissingArgumentException("?????????????????????");
        }
        Map param = new HashMap();
        param.put("username", userManagerUtils.getCurrentUserName());
        param.put("archid", archid);
        param.put("currentpage", currentpage);
        param.put("type", type);
        return exchangeInterfaceFeignService.requestInterface("hfDaFjxx", param);
    }

    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description ????????????????????????ftp
     * @date : 2023/1/3
     */
    @ResponseBody
    @PostMapping("/fcfht/download")
    public void downloadFcfht(@RequestBody FhtDTO fhtDTO) throws IOException {
        fwHstFeignService.downloadFcfhtHefei(fhtDTO);
    }

}