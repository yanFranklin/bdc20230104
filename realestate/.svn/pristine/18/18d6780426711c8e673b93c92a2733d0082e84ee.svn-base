package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcCfxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCfxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcJfxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJyxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcCfxxFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.inquiry.ui.config.HtmlVersionConfig;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/07/10
 * @description
 */
@RestController
@RequestMapping(value = "/cfxx")
public class BdcCfxxController extends BaseController {

    @Autowired
    private BdcCfxxFeignService bdcCfxxFeignService;
    @Autowired
    private HtmlVersionConfig htmlVersionConfig;
    @Autowired
    private BdcSlJyxxFeignService bdcSlJyxxFeignService;

    /**
     * 淮安房产交易查询台账推送
     */
    @Value("${huaian.fcjy.cxtzts:false}")
    private boolean cxtzts;


    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public Object cfxxListByPage(@LayuiPageable Pageable pageable, BdcCfxxQO cfxxQO) {
        Page<BdcCfxxDTO> bdcCfxxDTOPage = bdcCfxxFeignService.listBdcCfByPage(pageable, JSON.toJSONString(cfxxQO));
        return super.addLayUiCode(bdcCfxxDTOPage);
    }

    @GetMapping(value = "/xf/list/{xmid}")
    @ResponseStatus(HttpStatus.OK)
    public Object xfxxList(BdcCfxxQO cfxxQO) {
        return bdcCfxxFeignService.listBdcXf(JSON.toJSONString(cfxxQO));
    }

    @GetMapping(value = "/jf")
    @ResponseStatus(HttpStatus.OK)
    public void jf(BdcJfxxQO jfxxQO) {
        bdcCfxxFeignService.jfBdcCf(JSON.toJSONString(jfxxQO));

        // 淮安解封时需要向住建推送交易信息
        if (cxtzts) {
            try {
                // 获取项目信息
                String xmids = jfxxQO.getXmid();
                if (StringUtils.isNotBlank(xmids)) {
                    bdcSlJyxxFeignService.tsHaFcjyTsYwxx("", "1", xmids);
                }
            } catch (Exception e) {
                LOGGER.error("推送淮安房产交易通知业务信息失败", e);
            }

        }

    }

    @GetMapping(value = "/edit")
    @ResponseStatus(HttpStatus.OK)
    public void edit(BdcJfxxQO jfxxQO) {
        bdcCfxxFeignService.editBdcCf(JSON.toJSONString(jfxxQO));
    }

    @GetMapping(value = "/queryWdqXfByBdcyhs")
    @ResponseStatus(HttpStatus.OK)
    public Object queryWdqXfByBdcyhs(String bdcdyhs,String xmids) {
        return bdcCfxxFeignService.queryWdqXfByBdcyhs(bdcdyhs,xmids);
    }
}
