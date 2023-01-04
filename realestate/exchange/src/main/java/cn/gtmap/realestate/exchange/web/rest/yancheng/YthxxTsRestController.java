package cn.gtmap.realestate.exchange.web.rest.yancheng;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.service.rest.exchange.YthxxTsRestService;
import cn.gtmap.realestate.exchange.service.inf.yancheng.YthxxTsService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class YthxxTsRestController implements YthxxTsRestService {
    @Autowired
    YthxxTsService ythxxTsService;

    /**
     * 查封业务办结推送一体化信息平台
     *
     * @param processInsId
     * @param currentUserName
     */
    @Override
    public void ythcfxxts(@RequestParam(name = "processInsId") String processInsId,
                          @RequestParam(name = "processInsId") String currentUserName) {
        ythxxTsService.ythcfxxts(processInsId, currentUserName);
    }

    /**
     * 解封业务办结推送一体化信息平台
     *
     * @param processInsId
     * @param currentUserName
     */
    @Override
    public void ythjfxxts(@RequestParam(name = "processInsId") String processInsId,
                          @RequestParam(name = "processInsId") String currentUserName) {
        ythxxTsService.ythjfxxts(processInsId, currentUserName);
    }

    /**
     * 手动推送信息
     *
     * @param processInsId
     */
    @Override
    public CommonResponse ythxxsdts(@RequestParam(name = "processInsId") String processInsId) {
       return ythxxTsService.ythxxsdts(processInsId);
    }


}
