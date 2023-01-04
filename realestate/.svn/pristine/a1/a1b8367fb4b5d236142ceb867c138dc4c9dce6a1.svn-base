package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcdyxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcdyxxQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcdyXxCxFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.inquiry.ui.util.IpUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2019-05-22
 * @description 已登记不动产单元查询
 */
@RestController
@RequestMapping(value = "/rest/v1.0/bdcdyxx")
public class BdcdyXxCxController extends BaseController {

    @Autowired
    private BdcdyXxCxFeignService bdcdyXxCxFeignService;

    /**
     * 分页查询已登记不动产单元信息
     *
     * @param pageable  分页参数
     * @param bdcdyxxQO 查询QO
     * @return Object
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/listbypage")
    public Object listBdcdyXxByPage(@LayuiPageable Pageable pageable, BdcdyxxQO bdcdyxxQO, HttpServletRequest request) {
        bdcdyxxQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("bdcdycx","",""));
        String ip = IpUtils.getIpFromRequest(request);
        bdcdyxxQO.setClientIp(ip);
        Page<BdcdyxxDTO> bdcdyxxDTOPage = bdcdyXxCxFeignService.listBdcdyByPage(pageable, JSON.toJSONString(bdcdyxxQO));
        return super.addLayUiCode(bdcdyxxDTOPage);
    }
}
