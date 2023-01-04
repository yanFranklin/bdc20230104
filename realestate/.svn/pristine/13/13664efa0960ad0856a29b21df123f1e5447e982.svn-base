package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.service.rest.etl.ShareDsjjDataRestService;
import cn.gtmap.realestate.etl.service.ShareDataHandlerService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:shaliyao@gtmap.cn">shaliyao</a>
 * @version 2020/0426,1.0
 * @description
 */
@RestController
@Api(tags = "共享登记数据给大数据局")
public class ShareDsjjDataRestController implements ShareDsjjDataRestService {

    @Autowired
    ShareDataHandlerService shareDataHandlerService;

    private static Logger LOGGER = LoggerFactory.getLogger(ShareDsjjDataRestController.class);

    @Override
    public void shareDsjjDataByProcessInsId(@RequestParam(value = "processInsId") String processInsId) {
        LOGGER.info("开始共享大数据局数据，processInsId:{}", processInsId);
        shareDataHandlerService.shareDsjjDataByProcessInsId(processInsId);
    }
}
