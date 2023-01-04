package cn.gtmap.realestate.certificate.web.maintain;


import cn.gtmap.realestate.certificate.core.model.ECertificateClQO;
import cn.gtmap.realestate.certificate.web.main.DzzzController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.gtmap.realestate.common.util.PageUtils.addLayUiCode;

/**
 * 存量电子证照
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 下午5:38 2021/2/19
 */
@RestController
@Api("存量电子证照")
@RequestMapping(value = "/realestate-e-certificate/clxx")
public class BdcDzzzClxxController extends DzzzController {

    @Autowired
    private ECertificateFeignService eCertificateFeignService;

    @RequestMapping
    @ApiOperation(value = "电子证照存量分页", notes = "电子证照存量分页")
    public Object listDzzzZzxxCzByPage(@LayuiPageable Pageable pageable, ECertificateClQO eCertificateClQO) {
        return addLayUiCode(bdcDzzzService.selectPaging("listDzzzClzzByPage", eCertificateClQO, pageable));
    }

    @RequestMapping(value = "/dzzz", method = RequestMethod.POST)
    @ApiOperation(value = "存量电子证照签发", notes = "存量电子证照签发")
    public Integer dzzzClqf(@RequestBody List<String> bdcqzhs) {
        if (CollectionUtils.isEmpty(bdcqzhs)) {
            throw new MissingArgumentException("产权证号");
        }
        return eCertificateFeignService.clzzQf(bdcqzhs);
    }

    @RequestMapping(value = "/dzzz/pdf", method = RequestMethod.POST)
    @ApiOperation(value = "存量电子证照下载 pdf", notes = "存量电子证照下载 pdf")
    public Integer dzzzClpdf(@RequestBody List<String> zzbss) {
        if (CollectionUtils.isEmpty(zzbss) || null == zzbss.get(0)) {
            throw new MissingArgumentException("证照标识");
        }
        return eCertificateFeignService.clzzPdf(zzbss);
    }
}