package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.FwHsBgxxDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.building.FwHsHbZhsRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.FwhsBgRequestDTO;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwYcHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwhsBgFeignService;
import cn.gtmap.realestate.common.core.vo.building.FwHsHbVO;
import cn.gtmap.realestate.common.core.vo.building.FwhsBgVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-18
 * @description 户室变更相关页面
 */
@Controller
@RequestMapping("/fwhsbg")
public class FwHsBgController extends BaseController {
    @Autowired
    private FwhsBgFeignService fwhsBgFeignService;
    @Autowired
    private FwHsFeignService fwHsFeignService;
    @Autowired
    private CheckBgController checkBgController;
    @Autowired
    FwYcHsFeignService fwYcHsFeignService;

    /**
     * @param model
     * @param fwHsIndexList
     * @return
     */
    @RequestMapping("/inithb")
    public String initHbView(Model model, @NotEmpty(message = "户室数据不能为空")
    @RequestParam(name = "fwHsIndexList", required = false)
            List<String> fwHsIndexList) {
        model.addAttribute("fwhsIndexList", fwHsIndexList);
        return "fwhs/fwhsHb";
    }

    /**
     * @param model
     * @param fwHsBgxxDO
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋灭失页面
     */
    @RequestMapping("/initms")
    public String initMsView(Model model, FwHsBgxxDO fwHsBgxxDO) {
        model.addAttribute("fwHsBgxxDO", fwHsBgxxDO);
        return "/fwhs/fwhsMs";
    }

    /**
     * @param model
     * @param fwHsIndex
     * @param bgbh
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋户室基本信息变更页面
     */
    @RequestMapping("/initbg")
    public String initBgView(Model model, String fwHsIndex, String bgbh) {
        model.addAttribute("fwHsIndex", fwHsIndex);
        model.addAttribute("bgbh", bgbh);
        return "/fwhs/fwhsBg";
    }

    /**
     * @param model
     * @param fwHsIndex
     * @param bgbh
     * @return
     */
    @RequestMapping("/initcf")
    public String initcf(Model model, @NotBlank(message = "拆分户室主键不能为空") String fwHsIndex, @NotBlank(message = "变更编号不能为空") String bgbh) {
        model.addAttribute("fwHsIndex", fwHsIndex);
        model.addAttribute("bgbh", bgbh);
        return "fwhs/fwhsCf";
    }

    /**
     * @param fwHsIndexList
     * @return
     */
    @RequestMapping("/hblist")
    @ResponseBody
    public Page<FwHsDO> listFwhsByIndex(@NotEmpty(message = "户室数据不能为空")
                                        @RequestParam(name = "fwHsIndexList", required = false)
                                                List<String> fwHsIndexList) {
        // 转String
        Map paramMap = new HashMap();
        paramMap.put("fwhsIndexList", fwHsIndexList);
        PageRequest pageRequest = new PageRequest(0, 300);
        return fwHsFeignService.listFwScYcHsByPageJson(pageRequest, JSONObject.toJSONString(paramMap));
    }

    /**
     * @param fwHsHbVO
     * @return
     */
    @ResponseBody
    @RequestMapping("/hshb")
    public Map hshb(@Validated FwHsHbVO fwHsHbVO) {

        FwhsBgRequestDTO requestDTO = new FwhsBgRequestDTO();
        requestDTO.setBgbh(fwHsHbVO.getBgbh());
        requestDTO.setyFwHsIndexList(fwHsHbVO.getYfwHsIndexList());
        //为了和权籍保持一致
        if (CollectionUtils.isNotEmpty(fwHsHbVO.getZhsList())) {
            requestDTO.setZhsList(fwHsHbVO.getZhsList());
        }
        FwHsDO fwHsDO = fwHsFeignService.queryFwHsByFwHsIndex(fwHsHbVO.getMainIndex(),"");
        if (Objects.isNull(fwHsDO)) {
            FwYchsDO fwYchsDO = fwYcHsFeignService.queryFwYcHsByFwHsIndex(fwHsHbVO.getMainIndex(),"");
            if (Objects.nonNull(fwYchsDO)) {
                List<FwYchsDO> newFwYcHsList = new ArrayList<>();
                if (fwHsHbVO.getCh() != null) {
                    fwYchsDO.setWlcs(fwHsHbVO.getCh());
                }
                fwYchsDO.setHbfx(fwHsHbVO.getHbfx());
                fwYchsDO.setHbhs((double) (fwHsHbVO.getYfwHsIndexList().size() - 1));
                newFwYcHsList.add(fwYchsDO);
                requestDTO.setHslx(CommonConstantUtils.HSLX_YC);
                requestDTO.setFwHsList(new ArrayList<>(0));
                requestDTO.setFwYchsDOList(newFwYcHsList);
            }
        } else {
            List<FwHsDO> newFwHsList = new ArrayList<>();
            // 清空原有户室主键
            if (fwHsHbVO.getCh() != null) {
                fwHsDO.setWlcs(fwHsHbVO.getCh());
            }
            fwHsDO.setHbfx(fwHsHbVO.getHbfx());
            fwHsDO.setHbhs(fwHsHbVO.getYfwHsIndexList().size() - 1);
            newFwHsList.add(fwHsDO);
            requestDTO.setHslx(CommonConstantUtils.HSLX_SC);
            requestDTO.setFwYchsDOList(new ArrayList<>(0));
            requestDTO.setFwHsList(newFwHsList);
        }
        Map map = checkBgController.checkFwhsBgByBdcdyh(requestDTO.getyFwHsIndexList());
        if (MapUtils.getBooleanValue(map, "success")) {
            fwhsBgFeignService.fwhsHeBing(requestDTO);
            String mainBdcdyh = CollectionUtils.isNotEmpty(requestDTO.getFwHsList()) ? requestDTO.getFwHsList().get(0).getBdcdyh() : (CollectionUtils.isNotEmpty(requestDTO.getFwYchsDOList()) ? requestDTO.getFwYchsDOList().get(0).getBdcdyh() : "");
            String bgbh = requestDTO.getBgbh();
            String yfwhsIndexList = fwHsHbVO.getYfwHsIndexList().toString();
            LOGGER.info("合并单元号操作，合并户室变更编号为{}，原房屋户室主键为{}，主房单元号为{}", bgbh, yfwhsIndexList, mainBdcdyh);
            addLog("合并户室变更编号为" + bgbh + "原房屋户室主键为" + yfwhsIndexList + "主房单元号为" + mainBdcdyh, CommonConstantUtils.HSHB);
            return returnHtmlResult(true, "合并成功");
        } else {
            return returnHtmlResult(false, "不动产单元号异常");
        }
    }

    /**
     * @param fwhsBgRequestDTO
     * @return java.util.Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 户室灭失
     */
    @ResponseBody
    @RequestMapping("/ms")
    public Map fwhsMs(FwhsBgRequestDTO fwhsBgRequestDTO) {
        Map map = checkBgController.checkFwhsBgByBdcdyh(fwhsBgRequestDTO.getyFwHsIndexList());
        if (MapUtils.getBooleanValue(map, "success")) {
            fwhsBgFeignService.fwhsMieShi(fwhsBgRequestDTO);
            return returnHtmlResult(true, "灭失成功");
        } else {
            return returnHtmlResult(false, "不动产单元号异常");
        }
    }

    /**
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取变更编号
     */
    @ResponseBody
    @RequestMapping("/getbgbh")
    public String getBgbh() {
        return fwhsBgFeignService.maxBgbh();
    }


    /**
     * @param fwhsBgVO
     * @return java.util.Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 户室变更
     */
    @ResponseBody
    @RequestMapping("/hsbg")
    public Map fwhsBg(FwhsBgVO fwhsBgVO) {
        FwhsBgRequestDTO fwhsBgRequestDTO = new FwhsBgRequestDTO();
        List<FwHsDO> fwHsDOList = new ArrayList<>();
        FwHsDO fwHsDO = fwHsFeignService.queryFwHsByFwHsIndex(fwhsBgVO.getFwHsIndex(),"");
        BeanUtils.copyProperties(fwhsBgVO, fwHsDO);
        fwHsDO.setFwHsIndex("");
        fwHsDOList.add(fwHsDO);
        fwhsBgRequestDTO.setFwHsList(fwHsDOList);
        fwhsBgRequestDTO.setBgbh(fwhsBgVO.getBgbh());
        List<String> yFwHsIndexList = new ArrayList<>();
        yFwHsIndexList.add(fwhsBgVO.getFwHsIndex());
        fwhsBgRequestDTO.setyFwHsIndexList(yFwHsIndexList);
        Map map = checkBgController.checkFwhsBgByBdcdyh(fwhsBgRequestDTO.getyFwHsIndexList());
        if (MapUtils.getBooleanValue(map, "success")) {
            fwhsBgFeignService.fwhsJbxxBianGeng(fwhsBgRequestDTO);
            return returnHtmlResult(true, "变更成功");
        } else {
            return returnHtmlResult(false, "不动产单元号异常");
        }

    }

    /**
     * @param fwhsBgRequestDTO
     * @return java.util.Map
     * @author sly
     * @description 户室拆分
     */
    @ResponseBody
    @RequestMapping("/cf")
    public Map fwhsCf(FwhsBgRequestDTO fwhsBgRequestDTO) {
        Map map = checkBgController.checkFwhsBgByBdcdyh(fwhsBgRequestDTO.getyFwHsIndexList());
        if (MapUtils.getBooleanValue(map, "success")) {
            fwhsBgFeignService.fwhsChaifen(fwhsBgRequestDTO);
            return returnHtmlResult(true, "拆分成功");
        } else {
            return returnHtmlResult(false, "不动产单元号异常");
        }

    }


    /**
     * @param fwHsIndex
     * @return java.util.Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 撤销变更
     */
    @ResponseBody
    @RequestMapping("/revokebg")
    public Map revokeHsBg(@NotBlank(message = "户室主键不能为空") String fwHsIndex) {
        fwhsBgFeignService.fwhsRevokeBg(fwHsIndex,"");
        return returnHtmlResult(true, "撤销成功");
    }

    /**
     * @param
     * @return String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取户室合并配置
     */
    @ResponseBody
    @RequestMapping("/gethshbconfig")
    public String getHsHbConfig() {
        return fwhsBgFeignService.getHsHbConfig();
    }

    /**
     * @param
     * @return String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 户室合并子户室列表页面
     */
    @RequestMapping("/inithshbzhs")
    public String initHsHbZhs(Model model, @RequestParam(name = "fwHsIndexList", required = false)
            List<String> fwHsIndexList, String bgbh) {
        model.addAttribute("fwhsIndexList", fwHsIndexList);
        model.addAttribute("bgbh", bgbh);
        return "fwhs/hshbZhsList";
    }

    /**
     * @param
     * @return String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 户室合并子户室列表页面查询数据
     */
    @ResponseBody
    @RequestMapping("/zhslist")
    public Page<FwHsHbZhsRequestDTO> listHsHbZhs(@NotEmpty(message = "户室数据不能为空")
                                                 @RequestParam(name = "fwHsIndexList", required = false)
                                                         List<String> fwHsIndexList) {
        // 转String
        Map paramMap = new HashMap();
        paramMap.put("fwhsIndexList", fwHsIndexList);
        PageRequest pageRequest = new PageRequest(0, 300);
        return fwHsFeignService.listHsHbZhsByPageJson(pageRequest, JSONObject.toJSONString(paramMap));
    }


}

