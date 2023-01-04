package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXzxxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXzxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXzxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: realestate
 * @description: 修正流程页面跳转Controller
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-09-26 16:23
 **/
@Controller
@RequestMapping("rest/v1.0/xzlc")
public class SlymXzlcForwardController extends BaseController {
    private static final String SLYMXZXX_URL = "/view/xzxx/slymxz.html";

    private static final String SLYMXQXXLIST_URL = "/view/xzxx/slymxzList.html";

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcSlXzxxFeignService bdcSlXzxxFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;


    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String forwardXqxxHtml(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam(name = "processInsId") String processInsId) throws IOException {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        LOGGER.info("gzlslid获取项目类型，gzlslid为：{}", processInsId);
        List<BdcSlXmDO> bdcSlXmDTOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(processInsId);
        String xzlcymlx = httpServletRequest.getParameter("xzlcymlx");
        Map<String, String> resMap = getYxmxx(processInsId, xzlcymlx);
        String url = httpServletRequest.getQueryString();
        url = replaceAccessTokenReg(url, "processInsId", resMap.get("gzlslid"));
        if (StringUtils.isNotBlank(resMap.get("readOnly"))) {
            url = replaceAccessTokenReg(url, "readOnly", resMap.get("readOnly"));
        }
        if (CollectionUtils.isNotEmpty(bdcSlXmDTOList) && CollectionUtils.size(bdcSlXmDTOList) == 1) {
            LOGGER.info("重定向修正页面地址{}", httpServletRequest.getContextPath() + SLYMXZXX_URL + "?" + url + "&xmid=" + resMap.get("xmid"));
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + SLYMXZXX_URL + "?" + url + "&xmid=" + resMap.get("xmid"));
            return SLYMXZXX_URL;
        } else {
            LOGGER.info("重定向修正页面地址{}", httpServletRequest.getContextPath() + SLYMXQXXLIST_URL + "?" + url + "&xmid=" + resMap.get("xmid"));
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + SLYMXQXXLIST_URL + "?" + url + "&xmid=" + resMap.get("xmid"));
            return SLYMXQXXLIST_URL;
        }
    }

    public static String replaceAccessTokenReg(String url, String name, String value) {
        if (StringUtils.isNotBlank(url)) {
            url = url.replaceAll("(" + name + "=[^&]*)", name + "=" + value);
        }
        return url;
    }

    public Map<String, String> getYxmxx(String gzlslid, String xzlcYmlx) {
        Map<String, String> resultMap = new HashMap<>(1);
        String xmid = "";
        Integer xzly = 0;
        List<String> xmidList = new ArrayList<>(1);
        if (StringUtils.isNotBlank(xzlcYmlx)) {
            BdcSlXzxxQO bdcSlXzxxQO = new BdcSlXzxxQO();
            bdcSlXzxxQO.setGzlslid(gzlslid);
            List<BdcSlXzxxDO> bdcSlXzxxDOList = bdcSlXzxxFeignService.listBdcSlXzxx(bdcSlXzxxQO);
            if (CollectionUtils.isNotEmpty(bdcSlXzxxDOList)) {
//                xmid= bdcSlXzxxDOList.get(0).getYxmid();
                xzly = bdcSlXzxxDOList.get(0).getXzly();
                xmidList = bdcSlXzxxDOList.stream().filter(bdcSlXzxxDO -> StringUtils.isNotBlank(bdcSlXzxxDO.getYxmid())).map(BdcSlXzxxDO::getYxmid).collect(Collectors.toList());
                xmid = StringUtils.join(xmidList, CommonConstantUtils.ZF_YW_DH);
            }
            //台账创建,且没有正在办理的业务
            if (Objects.equals(CommonConstantUtils.XZLY_XZTZ, xzly)) {
                //临时的页面展示空白--现势的页面展示上一手数据
                if (StringUtils.equals("ls", xzlcYmlx)) {
                    //临时的展示空白页面,且不允许编辑
                    resultMap.put("readOnly", CommonConstantUtils.BOOL_TRUE);
                    xmid = UUIDGenerator.generate16();
                }
            }
            //流程内创建
            if (Objects.equals(CommonConstantUtils.XZLY_LCNCJ, xzly) || Objects.equals(CommonConstantUtils.XZLY_XZTZ_ZZBL, xzly)) {
                //查现势的-- 当前修正的上一手的上一手数据
                if (StringUtils.equals("xs", xzlcYmlx) && StringUtils.isNotBlank(xmid)) {
                    LOGGER.info("当前修正流程修正来源为流程创建,修正上一手xmid{}", xmid);
                    //查现势的--当前修正的上一手的上一手数据
                    List<String> yyxmidList = new ArrayList<>(CollectionUtils.size(xmidList));
                    for (String yxmid : xmidList) {
                        String yyxmid = "";
                        BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
                        bdcXmLsgxQO.setXmid(yxmid);
                        List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
                        if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                            yyxmid = bdcXmLsgxDOList.get(0).getYxmid();
                            LOGGER.info("当前修正流程流程创建-现势数据，yyxmid{}", yyxmid);
                        } else {
                            LOGGER.info("当前修正流程未找到现势数据信息，随机生成xmid方便查询");
                            //找不到修正的现势数据展示为空白页面
                            yyxmid = UUIDGenerator.generate16();
                        }
                        if (!xmidList.contains(yyxmid)) {
                            yyxmidList.add(yyxmid);
                        }
                    }
                    xmid = StringUtils.join(yyxmidList, CommonConstantUtils.ZF_YW_DH);
                }
            }
            //无数据创建
            if (Objects.equals(CommonConstantUtils.XZLY_WSJ, xzly)) {
                resultMap.put("readOnly", CommonConstantUtils.BOOL_TRUE);
            }
            if (StringUtils.isBlank(xmid)) {
                xmid = UUIDGenerator.generate16();
            }
            resultMap.put("xmid", xmid);
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmidList(Arrays.asList(xmid.split(CommonConstantUtils.ZF_YW_DH)));
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                resultMap.put("gzlslid", bdcXmDOList.get(0).getGzlslid());
            } else {
                resultMap.put("gzlslid", UUIDGenerator.generate16());
            }
        }
        return resultMap;
    }
}
