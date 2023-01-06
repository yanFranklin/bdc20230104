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
            throw new AppException("获取地籍调查表单元号异常");
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
     * @description 宗地权属调查信息
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
     * @description 获取宗地信息
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
     * @description 分页查询界址信息
     */
    @ResponseBody
    @RequestMapping("/jzxx/listbypage")
    public Page<DjdcbJzxxResponseDTO> jzxxListByPage(@LayuiPageable Pageable pageable, String bdcdyh,String qjgldm) {
        //处理前台传递的分页参数
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
     * @description 分页查询权利人
     */
    @ResponseBody
    @RequestMapping("/qlr/listbypage")
    public Page<DjdcbFwQlrResponseDTO> qlrListByPage(@LayuiPageable Pageable pageable, String bdcdyh) {
        //处理前台传递的分页参数
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
     * @description 户室信息数据
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
     * @description 查询房屋BDCDY信息
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
     * @description 宗地图
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
    public String getFwHstBase64Code(@NotBlank(message = "权籍主键不能为空") String djid,String fwDcbIndex,String qjgldm){
        // fwDcbIndex 不为空 且  fwDcbIndex == djid 说明当前户室类型为 独幢
        // 查询平面图 fwDcbIndex 为 FW_HST表的 FW_HST_INDEX
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
     * @description  查询林权调查表 用于 森林林木信息表
     */
    @ResponseBody
    @RequestMapping("/lqdcb")
    public LqDcbDO queryLqDcbByBdcdyh(@NotBlank(message = "不动产单元号不能为空") String bdcdyh,String qjgldm){
        return lqFeignService.queryLqByBdcdyh(bdcdyh,qjgldm);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.LqDcbDO
     * @description  查询承包宗地 用于土地承包经营权、农用地其他使用权调查表
     */
    @ResponseBody
    @RequestMapping("/cbzd")
    public Map<String,Object> queryCbzdByBdcdyh(@NotBlank(message = "不动产单元号不能为空") String bdcdyh,String qjgldm){
        Map<String,Object> result = new HashMap<>();
        // 获取承包宗地基本信息
        CbzdDcbDO cbzdDcbDO = cbzdFeignService.queryCbzdByBdcdyh(bdcdyh,qjgldm);
        if(cbzdDcbDO != null){
            result.put("cbzdDcb",cbzdDcbDO);
            // 获取承包方信息
            List<CbzdCbfDO> cbfList = cbzdFeignService.listCbfByBdcdyh(bdcdyh,qjgldm);
            if(CollectionUtils.isNotEmpty(cbfList)){
                List<CbzdCbfAndJtcyDTO> cbzdCbfAndJtcyDTOList =new ArrayList<>();
                for(CbzdCbfDO cbzdCbfDO:cbfList){
                    CbzdCbfAndJtcyDTO cbzdCbfAndJtcyDTO =new CbzdCbfAndJtcyDTO();
                    cbzdCbfAndJtcyDTO.setCbzdCbfDO(cbzdCbfDO);
                    // 承包方家庭成员信息
                    List<NydJtcyDO> jtcyList = cbzdFeignService.listCbzdJtcyByJtIndex(cbzdCbfDO.getCbzdCbfIndex(),qjgldm);
                    if(CollectionUtils.isNotEmpty(jtcyList)){
                        cbzdCbfAndJtcyDTO.setNydJtcyDOList(jtcyList);
                    }
                    cbzdCbfAndJtcyDTOList.add(cbzdCbfAndJtcyDTO);
                }
                result.put("cbfAndJtcyList",cbzdCbfAndJtcyDTOList);
            }

            // 获取发包方信息
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
     * @description 查询承包宗地分类面积
     */
    @ResponseBody
    @RequestMapping("/cbzdflmj")
    public Page listCbzdFlmjByBdcdyh(@LayuiPageable Pageable pageable, @NotBlank(message = "不动产单元号不能为空") String bdcdyh, String qjgldm) {
        // 获取承包宗地基本信息
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
            //根据工作流实例id找权籍调查数据
            List<BdcSlQjdcsqDO> bdcSlQjdcsqDOList = bdcQjdcFeignService.listSlQjdc(processInsId);
            if (CollectionUtils.isNotEmpty(bdcSlQjdcsqDOList)) {
                String bdcdyh = bdcSlQjdcsqDOList.get(0).getBdcdyh();
                if (StringUtils.isBlank(bdcdyh) || StringUtils.length(bdcdyh) != 28) {
                    throw new AppException("获取地籍调查表单元号异常");
                }
                if (StringUtils.isNotBlank(bdcdyh)) {
                    model.addAttribute("dzwtzm", CommonConstantUtils.DZWTZM_FW);
                    model.addAttribute("bdcdyh", bdcdyh);
                    model.addAttribute("qjgldm", "");
                }
                model.addAttribute("tabNameList", djdcbFeignService.listTabNameFromLjz());
                return "djdcb/bdcdjdcb";
            } else {
                throw new AppException("未获取到受理权籍调查申请信息");
            }
        }
        return "";
    }

    /**
     *
     * @param bdcdyh
     * @param qjgldm
     * @return 获取户室图
     */
    @ResponseBody
    @RequestMapping("/hsthsanddz")
    public String queryHstBase64ForHsAndDz(@NotBlank(message = "不动产单元号不能为空") String bdcdyh, String qjgldm) {
        return fwHstFeignService.queryHstBase64ForHsAndDz(bdcdyh, qjgldm);
    }

    /**
     *
     * @param bdcdyh
     * @param qjgldm
     * @return 获取户室图(合肥)
     */
    @ResponseBody
    @RequestMapping("/hefei/gethst")
    public List<String> queryHstBase64Hefei(@NotBlank(message = "不动产单元号不能为空") String bdcdyh, String qjgldm) {
        return fwHstFeignService.queryHstBase64Hefei(bdcdyh, qjgldm);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 合肥档案基本信息
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
            throw new MissingArgumentException("查询参数缺失！");
        }
        return exchangeInterfaceFeignService.requestInterface("hfDaJbxx", param);
    }

    /**
     * @param archid 档案id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 合肥获取档案目录信息
     * @date : 2022/2/11 14:02
     */
    @ResponseBody
    @GetMapping("/hefei/damlxx")
    public Object getDamlxx(@RequestParam("archid") String archid,
                            @RequestParam(value = "type", required = false) String type) {
        if (StringUtils.isBlank(archid) && null != type) {
            throw new MissingArgumentException("查询参数缺失！");
        }

        Map param = new HashMap();
        param.put("archid", archid);
        return exchangeInterfaceFeignService.requestInterface("hfFcfhtMlxx", param);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查看分层分户图
     * @date : 2022/2/11 16:16
     */
    @ResponseBody
    @GetMapping("/hefei/fcfht")
    public Object getDafjxx(@RequestParam("archid") String archid, @RequestParam("currentpage") Integer currentpage,
                            @RequestParam(value = "type", required = false) String type) {
        if (StringUtils.isBlank(archid) || currentpage == null || null == type) {
            throw new MissingArgumentException("查询参数缺失！");
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
     * @description 下载分层分户图至ftp
     * @date : 2023/1/3
     */
    @ResponseBody
    @PostMapping("/fcfht/download")
    public void downloadFcfht(@RequestBody FhtDTO fhtDTO) throws IOException {
        fwHstFeignService.downloadFcfhtHefei(fhtDTO);
    }

}