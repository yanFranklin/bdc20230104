package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.service.rest.etl.ShareYcslDataRestService;
import cn.gtmap.realestate.etl.service.ShareYcslHandlerService;
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
@Api(tags = "一窗数据共享")
public class ShareYcslDataRestController implements ShareYcslDataRestService {


    private static Logger LOGGER = LoggerFactory.getLogger(ShareYcslDataRestController.class);

    @Autowired
    ShareYcslHandlerService shareYcslHandlerService;

    /**
     * @param processInsId 工作流实例id
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据工作流实例id 共享当前一窗受理项目
     */
    @Override
    public void shareAllXmByProcessInsId(@RequestParam(value = "processInsId") String processInsId) {
        shareYcslHandlerService.shareYcslDataModel(processInsId);
    }
}
