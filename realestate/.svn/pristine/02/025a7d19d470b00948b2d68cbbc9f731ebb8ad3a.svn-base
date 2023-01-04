package cn.gtmap.realestate.register.ui.web.forward;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.ex.EntityNotFoundException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcZsInitFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/01/17
 * @description 证书页面转发Controller
 */
@Controller
@RequestMapping("/rest/v1.0/bdcZs")
public class BdcZsForwardController {
    private static final String ZS_LIST_URL = "/view/zsxx/bdcZsList.html";
    private static final String ZS_URL = "/view/zsxx/bdcZs.html";
    private static final String ZM_URL = "/view/zsxx/bdcZm.html";
    private static final String ZMD_URL = "/view/zsxx/bdcSczmd.html";
    private static final String REDIRECT_URL = "/realestate-register-ui";
    /**
     * 未生成证书时预览证书的地址
     */
    private static final String ZS_PREVIEW_URL = "/view/zsxx/zsPreview.html";
    private static final String ZM_PREVIEW_URL = "/view/zsxx/zmPreview.html";
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  无证书预览数据
     */
    private static final String ZM_PREVIEW_NONE_URL = "/view/zsxx/zsPreviewNone.html";

    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcZsInitFeignService bdcZsInitFeignService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String forwardZsHtml(@RequestParam(name = "processInsId", required = false) String processInsId,
                                @RequestParam(name = "xmid", required = false) String xmid,
                                @RequestParam(name = "zsmodel", required = false) String zsmodel,
                                @RequestParam(name = "zsyl", required = false) boolean zsyl,
                                HttpServletResponse response) throws Exception {
        if (zsyl) {
            String url = getZsPreviewUrl(processInsId, xmid, zsmodel);
            if (StringUtils.indexOf(url, REDIRECT_URL) > -1) {
                response.sendRedirect(getZsPreviewUrl(processInsId, xmid, zsmodel));
            }
            return url;
        } else {
            return getZsUrl(processInsId, zsmodel, null);
        }
    }

    /**
     * @param xmid     项目ID
     * @param readOnly 是否只读
     * @param zsyl     证书预览模式
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取要展示证书列表还是页面后重定向
     */
    @GetMapping("/redirect")
    public String redirectZsHtml(@RequestParam(name = "xmid") String xmid,
                                 @RequestParam("readOnly") boolean readOnly,
                                 @RequestParam("zsyl") boolean zsyl,
                                 @RequestParam("lsxm") boolean lsxm,
                                 @RequestParam("print") boolean print,
                                 @RequestParam(name = "processInsId",required = false) String processInsId) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("项目ID不能为空！");
        }

        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setXmid(xmid);
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);

        if (CollectionUtils.isNotEmpty(bdcZsDOList) && CollectionUtils.size(bdcZsDOList) == 1) {
            Integer zslx = bdcZsDOList.get(0).getZslx();
            if (CommonConstantUtils.ZSLX_ZS.equals(zslx)) {
                return "redirect:" + ZS_URL + "?readOnly=" + readOnly + "&zsid=" + bdcZsDOList.get(0).getZsid() +"&print=" +print +"&processInsId="+processInsId;
            } else if (CommonConstantUtils.ZSLX_ZM.equals(zslx)) {
                return "redirect:" + ZM_URL + "?readOnly=" + readOnly + "&zsid=" + bdcZsDOList.get(0).getZsid() +"&print=" +print+"&processInsId="+processInsId;
            }
        }
        return "redirect:" + ZS_LIST_URL + "?zsmodel&readOnly=" + readOnly + "&zsyl=" + zsyl + "&xmid=" + xmid + "&lsxm=" + lsxm +"&print=" +print+"&processInsId="+processInsId;
    }

    /**
     * @param processInsId 工作流实例ID
     * @param zsmodel      证书样式
     * @param bdcZsDOList
     * @return String 证书地址
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取证书地址
     */
    private String getZsUrl(String processInsId, String zsmodel, List<BdcZsDO> bdcZsDOList) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        if (CollectionUtils.isEmpty(bdcZsDOList)) {
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setGzlslid(processInsId);
            bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
        }
        if (CollectionUtils.isNotEmpty(bdcZsDOList) && CollectionUtils.size(bdcZsDOList) == 1) {
            Integer zslx = bdcZsDOList.get(0).getZslx();
            if (CommonConstantUtils.ZS_MODAL_SCZMD.equals(zsmodel) || CommonConstantUtils.ZSLX_ZMD.equals(zslx)) {
                return ZMD_URL;
            }
            if (CommonConstantUtils.ZSLX_ZS.equals(zslx)) {
                return ZS_URL;
            }
            if (CommonConstantUtils.ZSLX_ZM.equals(zslx)) {
                return ZM_URL;
            }
        }
        return ZS_LIST_URL;
    }

    /**
     * @param processInsId 工作流实例ID
     * @param xmid         项目ID（不为空，取当前项目ID。为空根据gzlslid取流程所有证书）
     * @param zsmodel
     * @return String 证书预览地址
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取证书预览的地址
     */
    private String getZsPreviewUrl(String processInsId, String xmid, String zsmodel) throws Exception {
        /**
         *  项目ID不为空时默认以项目ID查询证书，以项目为主。项目ID为空时，查询当前流程所有的证书信息
         */

        if (StringUtils.isBlank(processInsId) && StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        List<BdcZsDO> bdcZsDOList;
        // 预览情况下，先判断是否已经提前生成证书
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(processInsId);
        bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
            // 证书表里有数据则不走预览逻辑
            return REDIRECT_URL + this.getZsUrl(processInsId, zsmodel, bdcZsDOList) + "?zsmodel=" + zsmodel + "&zsyl=" + false + "&xmid=" + xmid + "&processInsId=" + processInsId + "&readOnly=true";
        }

        if (StringUtils.isNotBlank(xmid)) {
            bdcZsDOList = bdcZsInitFeignService.initBdcqz(xmid, true);
        } else {
            bdcZsDOList = bdcZsInitFeignService.initBdcqzs(processInsId, true);
        }
        if (CollectionUtils.isEmpty(bdcZsDOList)) {
            return ZM_PREVIEW_NONE_URL;
        }
        List<BdcXmDO> bdcXmDOList = new ArrayList();
        if (StringUtils.isBlank(xmid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(processInsId);
            bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        }
        // 生成一本证书，或者当前项目只有一条时，跳转到单证书页面
        if (StringUtils.isNotBlank(xmid) || CollectionUtils.isNotEmpty(bdcZsDOList) && CollectionUtils.size(bdcZsDOList) == 1 || CollectionUtils.isNotEmpty(bdcXmDOList) && CollectionUtils.size(bdcXmDOList) == 1) {
            Integer zslx = bdcZsDOList.get(0).getZslx();
            if (CommonConstantUtils.ZS_MODAL_SCZMD.equals(zsmodel) || CommonConstantUtils.ZSLX_ZMD.equals(zslx)) {
                return ZMD_URL;
            }
            if (CommonConstantUtils.ZSLX_ZS.equals(zslx)) {
                return ZS_PREVIEW_URL;
            }
            if (CommonConstantUtils.ZSLX_ZM.equals(zslx)) {
                return ZM_PREVIEW_URL;
            }
        }
        return ZS_LIST_URL;
    }
}
