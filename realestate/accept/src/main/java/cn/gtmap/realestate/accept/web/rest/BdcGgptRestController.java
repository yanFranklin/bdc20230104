package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.service.BdcGgptxxService;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.dto.inquiry.GgptxxDTO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcGgptRestService;
import cn.gtmap.realestate.common.util.LogConstans;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.gtmap.realestate.common.util.LogActionConstans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 1.0, 2022/11/03
 * @description 获取工改平台信息
 */
@RestController
@Api(tags = "获取工改平台数据接口")
public class BdcGgptRestController implements BdcGgptRestService {
    @Autowired
    BdcGgptxxService bdcGgptxxService;

    /**
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 获取工改平台数据
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "查询工改平台数据接口",  action = LogActionConstans.QUERY)
    public List<GgptxxDTO> queryGgptxx(@RequestBody String param) {
        return bdcGgptxxService.queryGgptxx(param);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "保存附件", action = LogActionConstans.OTHER )
    public Object downloadGgfj(@RequestBody GgptxxDTO ggptxxDTO, @RequestParam("gzlslid") String gzlslid) throws IOException {
        return bdcGgptxxService.downloadGgfj(ggptxxDTO, gzlslid);
    }
}

