package cn.gtmap.realestate.inquiry.ui.web.rest.changzhou;

import cn.gtmap.realestate.common.core.service.feign.inquiry.changzhou.BdcXxcxFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/8/25 17:24
 */
@RestController
@RequestMapping("/changzhou/xxcx")
public class BdcXxcxController {

    private static final Logger logger = LoggerFactory.getLogger(BdcXxcxController.class);

    private final static String BDCQZH_PREFIX = "20";

    @Autowired
    PdfController pdfController;

    @Autowired
    private BdcXxcxFeignService bdcXxcxFeignService;

    @Value("${print.path:}")
    private String printPath;


}
