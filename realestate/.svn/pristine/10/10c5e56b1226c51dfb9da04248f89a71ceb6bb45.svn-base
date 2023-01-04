package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.common.core.vo.building.FwXmxxBgVO;
import cn.gtmap.realestate.common.core.vo.building.FwXmxxHbVO;
import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import cn.gtmap.realestate.common.core.dto.building.XmxxBgRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.XmxxResponseDTO;
import cn.gtmap.realestate.common.core.service.feign.building.FwXmxxBgFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwXmxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwhsBgFeignService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
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

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/27
 * @description
 */
@Controller
@Validated
@RequestMapping("xmxxbg")
public class XmxxBgController extends BaseController {

    @Autowired
    private FwhsBgFeignService fwhsBgFeignService;

    @Autowired
    private FwXmxxBgFeignService fwXmxxBgFeignService;

    @Autowired
    private FwXmxxFeignService fwXmxxFeignService;
    @Autowired
    private CheckBgController checkBgController;


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param model
     * @param lszd
     * @param fwXmxxIndex
     * @param bgbh
     * @return java.lang.String
     * @description 基本信息变更页面
     */
    @RequestMapping("/jbxxbgview")
    public String xmxxJbxxbg(Model model,
                             @NotBlank(message = "lszd") String lszd,
                             @NotBlank(message = "fwXmxxIndex") String fwXmxxIndex,
                             @NotBlank(message = "bgbh") String bgbh){
        model.addAttribute("fwXmxxIndex", fwXmxxIndex);
        model.addAttribute("lszd", lszd);
        model.addAttribute("bgbh", bgbh);
        return "xmxx/fwXmxxInfo";
    }

    /**
     * @param yFwXmxxIndex
     * @return java.util.Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 项目信息灭失
     */
    @ResponseBody
    @RequestMapping(value = "/ms")
    public Map xmxxMs(String yFwXmxxIndex, String bgbh) {
        if (StringUtils.isNotBlank(yFwXmxxIndex)) {
            XmxxBgRequestDTO xmxxBgRequestDTO = new XmxxBgRequestDTO();
            List<String> yFwXmxxIndexList = new ArrayList<>();
            yFwXmxxIndexList.add(yFwXmxxIndex);
            xmxxBgRequestDTO.setyFwXmxxIndexList(yFwXmxxIndexList);
            xmxxBgRequestDTO.setBgbh(bgbh);
            Map map = checkBgController.checkXmxxBgByBdcdyh(yFwXmxxIndexList);
            if (MapUtils.getBooleanValue(map, "success")) {
                fwXmxxBgFeignService.xmxxMs(xmxxBgRequestDTO);
                return returnHtmlResult(true, "灭失成功");
            } else {
                return returnHtmlResult(false, "不动产单元号异常");
            }

        }
        return returnHtmlResult(false, "灭失失败");
    }


    /**
     * @param fwXmxxBgVO
     * @return java.util.Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 项目信息变更
     */
    @ResponseBody
    @RequestMapping(value = "/xmjbxxbg")
    public Map xmjbxxbg(@NotNull(message = "变更信息不能为空") FwXmxxBgVO fwXmxxBgVO) {
        FwXmxxDO fwXmxxDO = fwXmxxFeignService.queryXmxxByFwXmxxIndex(fwXmxxBgVO.getFwXmxxIndex(),"");
        BeanUtils.copyProperties(fwXmxxBgVO, fwXmxxDO);
        //获取原项目信息主键
        List<String> yFwXmxxIndex = new ArrayList<>();
        yFwXmxxIndex.add(fwXmxxDO.getFwXmxxIndex());
        XmxxBgRequestDTO xmxxBgRequestDTO = new XmxxBgRequestDTO();
        xmxxBgRequestDTO.setBgbh(fwXmxxBgVO.getBgbh());
        xmxxBgRequestDTO.setyFwXmxxIndexList(yFwXmxxIndex);
        xmxxBgRequestDTO.setFwXmxxDO(fwXmxxDO);
        Map map = checkBgController.checkXmxxBgByBdcdyh(xmxxBgRequestDTO.getyFwXmxxIndexList());
        if (MapUtils.getBooleanValue(map, "success")) {
            fwXmxxBgFeignService.xmxxBg(xmxxBgRequestDTO);
            return returnHtmlResult(true, "变更成功");
        } else {
            return returnHtmlResult(false, "不动产单元号异常");
        }

    }


    /**
     * @param model
     * @param fwXmxxIndexList
     * @return
     */
    @RequestMapping("/inithb")
    public String initHbView(Model model, @NotEmpty(message = "项目信息数据不能为空")
    @RequestParam(name = "fwXmxxIndexList", required = false)
            List<String> fwXmxxIndexList) {
        model.addAttribute("fwXmxxIndexList", fwXmxxIndexList);
        return "xmxx/xmxxHb";
    }


    /**
     * @param fwXmxxIndexList
     * @return
     */
    @RequestMapping("/hblist")
    @ResponseBody
    public Page<XmxxResponseDTO> listXmxxByIndex(@NotEmpty(message = "项目信息数据不能为空")
                                                 @RequestParam(name = "fwXmxxIndexList", required = false)
                                                         List<String> fwXmxxIndexList) {
        // 转String
        Map paramMap = new HashMap();
        paramMap.put("fwXmxxIndexList", fwXmxxIndexList);
        PageRequest pageRequest = new PageRequest(0, 300);
        return fwXmxxFeignService.listXmxxByPageJson(pageRequest, JSONObject.toJSONString(paramMap));
    }


    /**
     * @param fwXmxxHbVO
     * @return
     */
    @ResponseBody
    @RequestMapping("/xmxxhb")
    public Map xmxxhb(@Validated FwXmxxHbVO fwXmxxHbVO) {
        XmxxBgRequestDTO xmxxBgRequestDTO = new XmxxBgRequestDTO();
        xmxxBgRequestDTO.setBgbh(fwXmxxHbVO.getBgbh());
        xmxxBgRequestDTO.setyFwXmxxIndexList(fwXmxxHbVO.getyXmxxIndexList());
        FwXmxxDO fwXmxxDO = fwXmxxFeignService.queryXmxxByFwXmxxIndex(fwXmxxHbVO.getMainIndex(),"");
        if (fwXmxxDO != null) {
            xmxxBgRequestDTO.setFwXmxxDO(fwXmxxDO);
        }
        Map map = checkBgController.checkXmxxBgByBdcdyh(xmxxBgRequestDTO.getyFwXmxxIndexList());
        if (MapUtils.getBooleanValue(map, "success")) {
            fwXmxxBgFeignService.xmxxHb(xmxxBgRequestDTO);
            return returnHtmlResult(true, "合并成功");
        } else {
            return returnHtmlResult(false, "不动产单元号异常");
        }

    }
}