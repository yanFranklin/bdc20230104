package cn.gtmap.realestate.certificate.web.maintain;

import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzConfigService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzZdZzlxService;
import cn.gtmap.realestate.certificate.core.service.sign.BdcDzzzSignCzService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.certificate.util.PublicUtil;
import cn.gtmap.realestate.certificate.web.main.DzzzController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlWjscDTO;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/18
 */
@Controller
@Api("电子证照常州")
@RequestMapping(value = "/realestate-e-certificate/Cz")
public class BdcDzzzChangZhouController extends DzzzController {


    private static final String wjglqqx = "{\"CanRefresh\":1,\"CanCreateNewFolder\":0,\"CanDelete\":0,\"CanRename\":0,\"CanPrint\":1,\"CanDownload\":1,\"CanUpload\":0,\"CanTakePhoto\":0,\"CanScan\":0,\"CanEdit\":-1}";
    @Autowired
    private BdcDzzzZdZzlxService bdcDzzzZdZzlxService;
    @Autowired
    private BdcDzzzConfigService bdcDzzzConfigService;
    @Autowired
    private BdcDzzzSignCzService bdcDzzzSignCzService;


    @RequestMapping(value = "/toZzxxCzList", method = RequestMethod.GET)
    @ApiOperation(value = "常州电子证照台账展示", notes = "常州电子证照台账展示")
    public String toZzxxCzList(HttpServletRequest request, Model model) {
        logger.info("常州电子证照台账展示：{}，请求时间：{}", request.getRequestURI(), DateUtil.now());
        model.addAttribute("listZdZzlx", bdcDzzzZdZzlxService.listBdcDzzzZdZzlx());
        model.addAttribute("listConfig", bdcDzzzConfigService.listBdcDzzzConfig());
        return "dzzzgl/changzhou/dzzzZzxxCzList";
    }
    @RequestMapping(value = "/toZzxxCzSignList", method = RequestMethod.GET)
    @ApiOperation(value = "常州电子证照台账展示", notes = "常州电子证照台账展示")
    public String toZzxxCzSignList(HttpServletRequest request, Model model) {
        logger.info("常州电子证照签章台账展示：{}，请求时间：{}", request.getRequestURI(), DateUtil.now());
        model.addAttribute("listZdZzlx", bdcDzzzZdZzlxService.listBdcDzzzZdZzlx());
        return "dzzzgl/changzhou/dzzzZzxxCzSignList";
    }

    @RequestMapping(value = "/toZzxxCzCount", method = RequestMethod.GET)
    @ApiOperation(value = "常州电子证照统计台账展示", notes = "常州电子证照统计台账展示")
    public String toZzxxCzCount(HttpServletRequest request, Model model) {
        logger.info("常州电子证照统计台账展示：{}，请求时间：{}", request.getRequestURI(), DateUtil.now());
        return "dzzzgl/changzhou/dzzzZzxxCzCount";
    }

    @ResponseBody
    @RequestMapping(value = "/dzzzQuantitativeDistribution", method = RequestMethod.POST)
    @ApiOperation(value = "常州电子证照各地区数量分布", notes = "常州电子证照各地区数量分布")
    public String dzzzQuantitativeDistribution(Model model, String cjsjqssj, String cjsjjssj) {
        Map paramMap = new HashMap<>(2);
        paramMap.put("zzbfjgdmGroup", "dwdm");
        if (StringUtils.isNotBlank(cjsjqssj)) {
            paramMap.put("cjsjqssj", PublicUtil.convertStrTodate(cjsjqssj));
        }
        if (StringUtils.isNotBlank(cjsjjssj)) {
            paramMap.put("cjsjjssj", PublicUtil.convertStrTodate(cjsjjssj));
        }
        return bdcDzzzZzxxService.countDzzzQuantitativeDistribution(paramMap);
    }

    @ResponseBody
    @RequestMapping(value = "/listDzzzZzxxCzByPage", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照台账数据搜索", notes = "电子证照台账数据搜索")
    public Object listDzzzZzxxCzByPage(@LayuiPageable Pageable pageable, String sort
            , String bdcdyh, String bdcqzh
            , String zzlxdm, String dwdm) {
        Map<String, String> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            paramMap.put("bdcdyh", bdcdyh);
        }
        if (StringUtils.isNotBlank(bdcqzh)) {
            paramMap.put("bdcqzh", bdcqzh);
        }
        if (StringUtils.isNotBlank(zzlxdm)) {
            paramMap.put("zzlxdm", zzlxdm);
        }
        if (StringUtils.isNotBlank(dwdm)) {
            paramMap.put("dwdm", dwdm);
        }
        return bdcDzzzService.selectPaging("getBdcDzzzZzxxByPage", paramMap, pageable);
    }

    @ResponseBody
    @RequestMapping(value = "/listDzzzZzxxCzSignByPage", method = RequestMethod.POST)
    @ApiOperation(value = "电子证照签章台账数据搜索", notes = "电子证照签章台账数据搜索")
    public Object listDzzzZzxxCzSignByPage(@LayuiPageable Pageable pageable, String sort
            , String czzt, String bdcdyh, String bdcqzh, String qzzt
            , String zzlxdm, String cjsjqssj, String cjsjjssj, String zzbgyy,String szsxqc,String moduleCode,String qzlx) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(czzt)) {
            paramMap.put("czzt", czzt);
        }
        if (StringUtils.isNotBlank(qzzt)) {
            paramMap.put("qzzt", qzzt);
        }
        if (StringUtils.isNotBlank(bdcdyh)) {
            paramMap.put("bdcdyh", bdcdyh);
        }
        if (StringUtils.isNotBlank(bdcqzh)) {
            paramMap.put("bdcqzh", bdcqzh);
        }
        if (StringUtils.isNotBlank(zzlxdm)) {
            paramMap.put("zzlxdm", zzlxdm);
        }
        if (StringUtils.isNotBlank(cjsjqssj)) {
            paramMap.put("cjsjqssj", cjsjqssj);
        }
        if (StringUtils.isNotBlank(cjsjjssj)) {
            paramMap.put("cjsjjssj", cjsjjssj);
        }
        if (StringUtils.isNotBlank(zzbgyy)) {
            paramMap.put("zzbgyy", zzbgyy);
        }
        if(StringUtils.isNotBlank(szsxqc)){
            paramMap.put("szsxqc", szsxqc);
        }
        if(StringUtils.isNotBlank(qzlx)){
            paramMap.put("qzlx", qzlx);
        }
//        String dwdm = getCurrentUserDwdm();
//        if (StringUtils.isNotBlank(dwdm)) {
//            paramMap.put("dwdm", dwdm);
//        }
        paramMap.put("dwdmList",Container.getBean(BdcConfigUtils.class).qxdmFilter("dzqz","",moduleCode));
        return bdcDzzzService.selectPaging("getBdcDzzzQzxxByPage", paramMap, pageable);
    }

    @ResponseBody
    @RequestMapping(value = "/zzqzLocal", method = RequestMethod.POST)
    @ApiOperation(value = "常州电子证照本地台账签章", notes = "常州电子证照本地台账签章")
    public Object zzqzLocal(String zzids) {
       // logger.info("常州电子证照本地台账签章：{}，请求时间：{}", zzids, DateUtil.now());
        return bdcDzzzSignCzService.standSign(zzids);
    }

    @ResponseBody
    @RequestMapping(value = "/digitalSign", method = RequestMethod.POST)
    @ApiOperation(value = "常州电子证照本地台账签章_PDF签名", notes = "常州电子证照本地台账签章_PDF签名")
    public Object digitalSign(String sigedData, String cert) {
        logger.info("常州电子证照本地台账签章：signData {} , cert {}，请求时间：{}", sigedData, cert, DateUtil.now());

        return bdcDzzzSignCzService.digitalSign(sigedData, cert);
    }

    @ResponseBody
    @RequestMapping(value = "/zzzfLocal", method = RequestMethod.POST)
    @ApiOperation(value = "常州电子证照本地台账作废", notes = "常州电子证照本地台账作废")
    public Object zzzfLocal(String zzbs) {
        logger.info("常州电子证照本地台账作废：{}，请求时间：{}", zzbs, DateUtil.now());
        BdcDzzzZzxx bdcDzzzZzxx = new BdcDzzzZzxx();
        bdcDzzzZzxx.setZzbs(zzbs);
        bdcDzzzZzxx.setZzbgyy(Constants.DZZZ_ZZZT_ZX_YCZX);
        return bdcDzzzSignCzService.dzzzCancellation(bdcDzzzZzxx);
    }

    /**
     * @param nodeId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取查看文件权限信息
     * @date : 2022/3/10 16:15
     */
    @ResponseBody
    @GetMapping("/bdcSlWjscDTO")
    public Object queryWjscDto(String nodeId) {
        BdcSlWjscDTO bdcSlWjscDTO = new BdcSlWjscDTO();
        bdcSlWjscDTO.setToken(queryToken());
        bdcSlWjscDTO.setClientId("dzzz");
        bdcSlWjscDTO.setsFrmOption(wjglqqx);
        bdcSlWjscDTO.setNodeId(nodeId);
        return bdcSlWjscDTO;
    }
}
